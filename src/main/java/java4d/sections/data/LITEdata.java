/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.vector3f;
import java4d.myutil;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class LITEdata extends MESHdata {

    public String version;
    public vector3f lightColor;
    public float lightMult;

    public LITEdata(sect owner)
    {
        super(owner);
        lightColor=new vector3f();
    }

    @Override
    public int getSize() {
        return super.getSize() + lightColor.dataSize() + 8;
    }

    @Override
    public byte[] getData() {
        byte ret[] =new byte[this.getSize()];
        int loaded=0;

        System.arraycopy(super.getData(), 0, ret, 0, loaded+=super.getSize());

        System.arraycopy(version.getBytes(),0,ret,loaded,4);
        loaded+=4;

        lightColor.toBytes(ret,loaded );
        loaded+=lightColor.dataSize();

        myutil.putFloat(lightMult, ret, loaded);

        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=0; // i'm going to call super.loadData, it wants flat offset
        loaded+=super.loadData(data, offset + loaded);

        loaded+=4; // restore counter as if the method begins here

        version=new String(data,offset+loaded,4);
        loaded+=version.length();

        lightColor.getFrom(data, offset + loaded);
        loaded+=lightColor.dataSize();

        lightMult = myutil.getFloat(data, offset + loaded);
        loaded+=4;

        return loaded-4;
    }
}