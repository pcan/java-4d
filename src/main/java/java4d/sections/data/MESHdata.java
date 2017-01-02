/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.infoLabel;
import java4d.datatypes.spaceSystem;
import java4d.myutil;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class MESHdata extends sectData {

    public infoLabel desc;
    public int parentEntityId=-1;
    public spaceSystem space;

    public MESHdata(sect owner)
    {
        super(owner);
        desc=new infoLabel();
        space=new spaceSystem();
    }

    @Override
    public int getSize() {
        int ret=  desc.dataSize()
                + 4
                + space.dataSize();

        if(owner.getVersion()==101 ) ret +=8; //versioning issue... see appunti.txt
        return ret;
    }

    @Override
    public byte[] getData() {
        byte ret[] =new byte[this.getSize()];
        int offset=0;

        desc.toBytes(ret,offset);  // MESH description
        offset+=desc.dataSize();

        myutil.putInt(parentEntityId, ret, offset);  // parent MESH (see appunti.txt)
        offset+=4;

        if(owner.getVersion()==101) //versioning issue... see appunti.txt
        {
            myutil.putInt(0xFFFFFFFF,ret,offset);
            myutil.putInt(0xFFFFFFFF,ret,offset+4);
            offset+=8;
        }

        space.toBytes(ret, offset);  // coordinate system
        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        desc.getFrom(data, offset + loaded);
        loaded+=desc.dataSize();

        parentEntityId = myutil.getInt(data, offset + loaded);
        loaded+=4;

        if(owner.getVersion()==101) loaded+=8;

        space.getFrom(data, offset+loaded);
        loaded+=space.dataSize();

        return loaded-4;
    }
}

