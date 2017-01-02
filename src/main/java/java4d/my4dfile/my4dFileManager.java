
package java4d.my4dfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java4d.sections.sect;
import utilities.rFile;




public class my4dFileManager {

    public static sect loadFile(File fobj) throws Not4dFileException, SizeMismatchException, Exception {
        sect s = null;

        rFile f = new rFile(fobj, "r");
        byte title[] = new byte[4];
        int size;

        // <editor-fold defaultstate="collapsed" desc="Header check, read title...">
        if (f.readIntLE() != 454718035 || f.readIntLE() != 176622093) // header check
        {
            throw new Not4dFileException();
        }

        f.read(title);   // read first section title...
        size = f.readIntLE(); // read first section size...
        f.moveCursor(-8);// get back before first section
        // </editor-fold>

        byte data[] = new byte[size + 8];
        f.read(data);

        s = sectListManager.getSect(data, 0, new String(title),null );

        f.close();

        return s;
    }


    public static void saveFile(File fobj, sect s)
    {
        if(s !=null)
        try {
            rFile f = new rFile(fobj, "rw");
            f.setLength(0);
            f.writeIntLE(454718035);  // writes the SR header (1st part)
            f.writeIntLE(176622093);  // writes the SR header (2nd part)
            
            byte buf[]=new byte[s.getSize()];
            s.toBytes(buf, 0);
            
            f.write(buf);
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(my4dFileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(my4dFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

}

