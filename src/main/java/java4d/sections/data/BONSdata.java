/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.bone;
import java4d.myutil;
import java4d.sections.sect;

/**
 *
 * @author Pierantonio
 */
public class BONSdata extends sectData {

    public bone bones[];

    public BONSdata(sect owner)
    {
        super(owner);
        bones=new bone[0];
    }

    @Override
    public int getSize() {
        return 4 + bones.length * bones[0].dataSize();
    }

    @Override
    public byte[] getData() {
        byte ret[]=new byte[getSize()];
        myutil.putInt(bones.length, ret,0);
        for(int i=0;i<bones.length;i++)
            bones[i].toBytes(ret, i*bones[0].dataSize()+4);
        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        bones=new bone[myutil.getInt(data, offset+loaded)];
        loaded+=4;

        for(int i=0;i<bones.length;i++)
        {
            bones[i]=new bone();
            bones[i].getFrom(data, offset + loaded);
            loaded+=bones[0].dataSize();
        }

        return loaded-4;
    }
}


