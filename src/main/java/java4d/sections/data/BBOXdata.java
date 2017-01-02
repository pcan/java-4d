/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.myBox;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class BBOXdata extends sectData {

    public myBox box;

    public BBOXdata(sect owner)
    {
        super(owner);
        box=new myBox();
    }

    @Override
    public int getSize() {
        return box.dataSize();
    }

    @Override
    public byte[] getData() {
        return box.toBytes();
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        box.getFrom(data, offset +loaded);
        loaded+=box.dataSize();

        return loaded-4;
    }
}

