/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.my4dfile;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java4d.myutil;
import java4d.sections.sect;
import java4d.sections.sectUnknown;

/**
 *
 * @author Pierantonio
 */
public class sectListManager {

    // load sub-sections
    public static LinkedList<sect> getList(byte data[], int offset, int toConsume,sect parent) throws SizeMismatchException, Exception {
        int Consumed = 0;
        LinkedList<sect> ret = (toConsume - Consumed > 0) ? new LinkedList<sect>() : null;

        while (toConsume - Consumed > 0) {
            String title = new String(data, offset + Consumed, 4);  //get title
            int size = myutil.getInt(data, offset + Consumed + 4);  // get payload size
            if (size > toConsume - Consumed) {
                throw new SizeMismatchException("Size mismatch in section "+ (ret.size() + 1) + "(parent: "+ parent.getTitle() +  "):  " + title);
            }
            ret.add(getSect(data, offset + Consumed, title,parent));  // append this section to the list
            Consumed += 8 + size;  // consumed = title length + size field length + payload length
        }
        return ret;
    }


    //creates the sect object that has the same "name" of the data-section and loads its data
    public static sect getSect(byte data[], int offset, String title, sect parent) throws SizeMismatchException, Exception {
        sect ret = null;
        try {
            // if a class with the same name of the section exists, instantiate it!

            ret = (sect) Class.forName("java4d.sections.sect" + title).getConstructor(sect.class).newInstance(parent);
        }
        // <editor-fold defaultstate="collapsed" desc="...Other catches...">
        catch (NoSuchMethodException ex) {
                Logger.getLogger(sectListManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(sectListManager.class.getName()).log(Level.SEVERE, null, ex);
            }        catch (IllegalArgumentException ex) {
                Logger.getLogger(sectListManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(sectListManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        catch (InstantiationException ex) {
            Logger.getLogger(my4dFileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(my4dFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }// </editor-fold>
        catch (ClassNotFoundException ex) {
            // we haven't any class with this name... using 'unknown' model
            ret = (sect) new sectUnknown(parent);
        }
        //loads section-specific data
        ret.loadData(data, offset);
        return ret;
    }
    /*
    static byte[] sectToBytes(sect s)
    {
        //sect.getsize() returns the TOTAL size of the tree (see note into sect def)
        byte ret[]=new byte[s.getSize()];
        int wr=s.toBytes(ret, 0);
        //Logger.getLogger(sectListManager.class.getName()).log(Level.INFO, "Byte scritti da s.toBytes: " +wr );
        return ret;
    }*/
}// </editor-fold>

