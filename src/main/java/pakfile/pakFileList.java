/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pakfile;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.rFile;

/**
 *
 * @author Pierantonio
 */
public class pakFileList {

    private pakFileListener listener;

    private TreeSet<pakFileListItem> filelist;

    public pakFileList(final File path,pakFileListener f_listener)
    {
        class readThread implements Runnable {

            Thread t;
            long counter=0;
            long filecount;

            public readThread() {
                t = new Thread(this);
                //t.setPriority(Thread.MIN_PRIORITY);
                t.start();
            }

            public void run() {
                filecount=getFileCount(path);
                populatelist("", path);
                listener.readStopped();
            }
            private void populatelist(String basepath, File cur) {
                File l[] = cur.listFiles();
                if(l!=null)
                for (int i = 0; i < l.length; i++) {
                    listener.readIsAdvancing((++counter*100) / filecount);
                    if (l[i].isFile()) {
                        filelist.add(new pakFileListItem(basepath + l[i].getName(), l[i].length(), l[i]));
                    } else {
                        filelist.add(new pakFileListItem(basepath + l[i].getName(), 0, null));
                        populatelist(basepath + l[i].getName() + '/', l[i]);
                    }
                }
                
            }
        }

        listener=f_listener;
        filelist=new TreeSet<pakFileListItem>();
        if(path.isDirectory())
            new readThread();
        listener.readStarted();
           
    }

    private int getFileCount(File cur)
    {
        int ret=0;
        File l[] = cur.listFiles();
        if(l!=null)
        for (int i = 0; i < l.length; i++,ret++)
            if (l[i].isDirectory())
                ret+=getFileCount(l[i]);

        return ret;
    }

    public int getRecordCount()
    {
        return filelist.size();
    }

    public long getFilesDataSize()
    {
        long ret=0;
        pakFileListItem l[]=filelist.toArray( new pakFileListItem[0]);
        for(int i=0;i< l.length;i++)
            ret+=l[i].size;
        return ret;
    }

    public int getNamesSize()
    {
        int ret=0;
        pakFileListItem l[]=filelist.toArray( new pakFileListItem[0]);
        for(int i=0;i< l.length;i++)
            ret+=l[i].path.length() + 15;
        return ret;
    }

    public void writeFile(final File out)
    {
        class writeThread implements Runnable {
           public void run() {
                
                 // <editor-fold defaultstate="collapsed" desc="DATA WRITING CODE">
                try {
                    rFile f = new rFile(out, "rw");
                    long totalsize = getFilesDataSize()+ getNamesSize();
                    
                    f.setLength(totalsize);
                    f.writeIntLE(454718035);  // writes the SR header (1st part)
                    f.writeIntLE(176622093);  // writes the SR header (2nd part)
                    f.write("PACK".getBytes(), 0, 4);
                    f.writeIntLE(getNamesSize());
                    
                    long done = 16;

                    pakFileListItem t[] = filelist.toArray(new pakFileListItem[0]);
                    int offset = 0;
                    int headoffset = 0;
                    for (int i = 0; i < t.length; i++) {

                        f.writeByte(0);
                        if (t[i].path.length() > 0xff) {
                            throw new IOException("Panic: Pathname > 255");
                        }
                        f.writeByte(t[i].path.length());
                        f.writeBytes(t[i].path);
                        f.writeIntLE(offset);

                        if (t[i].file != null) {
                            if (t[i].file.length() > 0x20000000) {
                                throw new IOException("Panic: Filesize > 512 MB");
                            }
                            f.writeIntLE((int) t[i].file.length());
                            offset += (int) t[i].file.length();
                        } else {
                            f.writeIntLE(0);
                        }

                        headoffset += t[i].path.length() + 15;
                        f.writeByte(i < t.length - 1 ? 1 : 0);
                        f.writeIntLE(i < t.length - 1 ? headoffset : 0);
                        done=headoffset;
                        if (listener != null)
                        listener.writeIsAdvancing((done * 100) / totalsize);
                    }
                    rFile inputfile;
      
                    for (int i = 0; i < t.length; i++) {
                        if (t[i].file != null) {
                            inputfile = new rFile(t[i].file, "r");
                            int inputlen = (int) t[i].file.length();
                            byte buf[] = new byte[inputlen];
                            inputfile.read(buf);
                            f.write(buf);
                            inputfile.close();
                            done += inputlen;
                            if (listener != null) {
                                listener.writeIsAdvancing((done * 100) / totalsize);
                            }
                        }
                    }
                    f.close();

                } catch (IOException ex) {
                    Logger.getLogger(pakFileList.class.getName()).log(Level.SEVERE, null, ex);
                }// </editor-fold>

                listener.writeStopped();
           }
        }
        if(out!=null)
        {
            Thread t=new Thread(new writeThread());
            //t.setPriority(Thread.MIN_PRIORITY);
            t.setName("pakWriter");
            t.start();
            listener.writeStarted();
        }
    }

    public void setListener(pakFileListener plistener)
    {
        listener=plistener;
    }
}
