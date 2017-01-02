/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.infoLabel;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class DIFFdata extends sectData {

    public infoLabel desc;

    public DIFFdata(sect owner)
    {
        super(owner);
        desc=new infoLabel();
    }

    @Override
    public int getSize() {
        return desc.dataSize();
    }

    @Override
    public byte[] getData() {
        return desc.toBytes();
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        desc.getFrom(data, offset + loaded);
        loaded+=desc.dataSize();

        return loaded-4;
    }

}