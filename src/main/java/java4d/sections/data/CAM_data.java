/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.vector3f;
import java4d.sections.sect;

/**
 *
 * @author Pierantonio
 */
public class CAM_data extends MESHdata{

    public vector3f fov;

    public CAM_data(sect owner)
    {
        super(owner);
        fov=new vector3f();
    }

    @Override
    public int getSize() {
        return super.getSize() + fov.dataSize();
    }

    @Override
    public byte[] getData() {
        byte ret[] =new byte[this.getSize()];
        int offset=0;

        System.arraycopy(super.getData(),0, ret, 0,offset+=super.getSize());

        fov.toBytes(ret, offset);
        
        return ret;
    }

     @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=0; // i'm going to call super.loadData, it wants flat offset
        loaded+=super.loadData(data, offset+loaded);

        loaded+=4; // restore counter as if the method begins here
        
        fov.getFrom(data, offset+loaded);
        loaded+=fov.dataSize();

        return loaded-4;
    }
}
