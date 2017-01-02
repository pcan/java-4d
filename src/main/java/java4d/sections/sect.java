
package java4d.sections;


import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java4d.*;
import java4d.my4dfile.SizeMismatchException;
import java4d.my4dfile.sectListManager;
import java4d.sections.data.sectData;
import java4d.sections.editors.sectEditor;
import java4d.sections.editors.sectMenu;
import javax.swing.JMenu;
import javax.swing.JOptionPane;



public abstract class sect {

    protected LinkedList<sect> sectList;
    protected sectData sdata;
    protected sect parent;
    protected sectEventListener listener;


    public sect(sect myparent)
    {
        parent=myparent;
        sectList=null;
    }

    public sect getParent()
    {
        return parent;
    }

    public int getVersion()
    {
        return parent.getVersion();
    }

    public sectEventListener getEventListener()
    {
        if(parent==null)
            return listener;
        else
            return parent.getEventListener();
    }

    public void setEventListener(sectEventListener l)
    {
        if(parent==null)
            listener = l;
        else
            parent.setEventListener(l);
    }


    public String getTitle()
    {
        return this.getClass().getSimpleName().substring(4);
    }

    public String getTipDetails()
    {
        return getTitle();
    }

    public int getSize()
    {
        int ret=8; // WARNING!!! includes <title + size field (8)> size
        ret+=sdata.getSize();
        if(sectList != null)
            for(int i=0; i< sectList.size();i++)
                ret+=sectList.get(i).getSize();
        return ret;
    }


    public int toBytes(byte dest[],int offset)
    {
        int written=0; // counts the number of bytes while writing
        if(this.getSize() <= dest.length - offset)
        {  
            System.arraycopy(this.getTitle().getBytes(), 0, dest, offset, 4); //writes the title
            myutil.putInt(this.getSize()-8,dest,offset + 4);  //writes the entire section size, minus name and size field
            
            System.arraycopy(sdata.getData(), 0, dest, offset + 8,sdata.getSize()); //writes inner section data
            written +=sdata.getSize() + 8;  // inner data size + title + size field
            if(sectList != null)
                for(int i=0; i< sectList.size();i++)
                {
                    sectList.get(i).toBytes(dest, offset + written);
                    written+=sectList.get(i).getSize();
                }
        }
        return written;
    }

    public sectData getSectionData() {
        return sdata;
    }

    public boolean loadData(byte[] data, int offset) throws SizeMismatchException,Exception
    {
        int tsize=myutil.getInt(data, offset + 4);
        int consumed=sdata.loadData(data, offset + 4); //first thing on the array: the size
        if(tsize-consumed > 0 )
             sectList = sectListManager.getList(data, offset + 8 + consumed, tsize - consumed, this);

        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="getChildren#####.... methods">
    public LinkedList<sect> getChildren() {
        return sectList;
    }

    /**
     * WARNING!!! returns null if list is empty
     *
     * @param type
     * @return
     */
    public LinkedList<sect> getChildrenType(Class type) {
        LinkedList<sect> ret = null;
        if (sectList != null) {
            ret = new LinkedList();
            for (int i = 0; i < sectList.size(); i++) {
                if (sectList.get(i).getClass() == type) {
                    ret.add(sectList.get(i));

                }

            }
            if (ret.size() == 0) {
                ret = null;

            }
        }
        return ret;
    }

    /**
     * WARNING!!! returns null if list is empty
     *
     * @param type
     * @return
     */
    public LinkedList<sect> getChildrenTypeAndDerived(Class type) {
        LinkedList<sect> ret = null;
        if (sectList != null) {
            ret = new LinkedList();
            for (int i = 0; i < sectList.size(); i++) {
                if (type.isInstance(sectList.get(i)))
                {
                    ret.add(sectList.get(i));
                }

            }
            if (ret.size() == 0) {
                ret = null;

            }
        }
        return ret;
    }

    /**
     * WARNING!!! returns null if list is empty
     *
     * @param type
     * @return
     */
    public LinkedList<sect> getChildrenType(Class type[]) {
        LinkedList<sect> ret = null;
        if (sectList != null) {
            ret = new LinkedList();
            for (int i = 0; i < sectList.size(); i++) {
                for (int j = 0; j < type.length; j++) {
                    if (sectList.get(i).getClass() == type[j]) {
                        ret.add(sectList.get(i));

                    }

                }

            }
            if (ret.size() == 0) {
                ret = null;

            }
        }
        return ret;
    }

    public int getChildrenTypeCount(Class type) // maybe redundant...
    {
        int ret = 0;
        if (sectList != null) {
            for (int i = 0; i < sectList.size(); i++) {
                ret += sectList.get(i).getClass() == type ? 1 : 0;


            }

        }
        return ret;
    }

    public int getChildrenTypeCount(Class type[]) // maybe redundant...
    {
        int ret = 0;
        if (sectList != null) {
            for (int i = 0; i < sectList.size(); i++) {
                for (int j = 0; j < type.length; j++) {
                    ret += sectList.get(i).getClass() == type[j] ? 1 : 0;


                }

            }

        }
        return ret;
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="user interface helper methods">
    public void showEditor() {
        class editorLoaderThread implements Runnable {

            public void run() {
                // <editor-fold defaultstate="collapsed" desc="Thread body, takes care of confirmation">
                sectData bak = getSectionData().clone();

                sectEditor editor = getEditFrame();
                if (editor == null) {
                    return;
                }
                
                int choice = JOptionPane.CANCEL_OPTION;
                do {
                    editor.setVisible(true);
                    try {
                        Thread.currentThread().join();
                    } catch (InterruptedException ex) {
                        choice = JOptionPane.showConfirmDialog(null, "Confirm changes?");
                    }

                } while (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION);
                
                if (choice == JOptionPane.YES_OPTION) {
                   //do nothing
                }else
                {
                    sdata = bak;
                }
                
                if(getEventListener()!=null)
                    getEventListener().dataChanged();

                // </editor-fold>
            }
        }

        Thread t = new Thread(new editorLoaderThread());
        t.setName("editorLoaderThread");
        t.start();

    }

    protected sectEditor getEditFrame() {
        sectEditor ret = null;
        try {
            ret = (sectEditor)Class.forName("java4d.sections.editors." + getTitle() + "editor").getConstructor(sectData.class).newInstance(getSectionData());

        } // <editor-fold defaultstate="collapsed" desc="Catches....">
        catch (InstantiationException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {

        }// </editor-fold>
        return ret;
    }

    public JMenu getEditMenu() {
        sectMenu ret = null;
        try {

            // if a class with the same name of the section exists, instantiate it!
            ret = (sectMenu) Class.forName("java4d.sections.editors." + getTitle() + "menu").getConstructor(sect.class).newInstance(this);
        } // <editor-fold defaultstate="collapsed" desc="...Other catches...">
        catch (InvocationTargetException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(sect.class.getName()).log(Level.SEVERE, null, ex);
        }// </editor-fold>
        catch (ClassNotFoundException ex) {
            // we haven't any class with this name... using 'unknown' model
            ret = new sectMenu(this);
        }
        return ret.getMenu();
    }// </editor-fold>

    
}

