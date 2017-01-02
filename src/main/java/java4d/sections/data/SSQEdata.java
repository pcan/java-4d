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
public class SSQEdata extends sectData {

    public infoLabel desc;
    public byte unkdata[];

    public SSQEdata(sect owner)
    {
        super(owner);
        desc=new infoLabel();
    }

    @Override
    public int getSize() {
        return desc.dataSize() + unkdata.length;
    }

    @Override
    public byte[] getData() {
        byte ret[]=new byte[desc.dataSize() + 24];
        desc.toBytes(ret, 0);
        System.arraycopy(unkdata, 0, ret, desc.dataSize(), 24);
        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        desc.getFrom(data, offset + loaded);
        loaded+=desc.dataSize();

        unkdata=new byte[24];
        System.arraycopy(data, offset+loaded, unkdata, 0, 24);
        loaded+=24;

        return loaded-4;
    }

}