/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.myutil;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class Unknowndata extends sectData {

    public byte rawdata[];

    public Unknowndata(sect owner) {
        super(owner);
    }


    @Override
    public int getSize() {
        return rawdata.length;
    }

    @Override
    public byte[] getData() {
        return rawdata;
    }

    @Override
    public int loadData(byte[] data, int offset){ //first thing on the array: the size
        int loaded=0;
        try {

            loaded=myutil.getInt(data, offset);
            rawdata = new byte[loaded];  //first thing on the array: the size
            System.arraycopy(data, offset + 4, rawdata, 0, rawdata.length);
        } catch (ArrayIndexOutOfBoundsException ex) {

        }
        return loaded;

    }
}
