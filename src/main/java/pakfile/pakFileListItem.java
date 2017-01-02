/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pakfile;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierantonio
 */
public class pakFileListItem implements Comparable {
    public String path;
    public long size;
    public File file=null;
    public pakFileListItem(String fpath,long fsize,File f_file)
    {
        try {
            path = new String(fpath);
            size = fsize;
            if (f_file != null) {
                file = new File(f_file.getCanonicalPath());
            }
        } catch (IOException ex) {
            Logger.getLogger(pakFileListItem.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public String toString()
    {
        return new String(path + " " + size);
    }
    

    public int compareTo(Object o) {
        return path.toLowerCase().compareTo(((pakFileListItem)o).path.toLowerCase());
    }
}
