/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.myVertex;
import java4d.my4dfile.InvalidDataException;
import java4d.myutil;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class VERTdata extends sectData {

    public myVertex vert[];

    public VERTdata(sect owner)
    {
        super(owner);
        vert=null;
    }

    @Override
    public int getSize() {
        return vert.length * vert[0].dataSize() + 8;
    }

    @Override
    public byte[] getData() {
        byte ret[]=new byte[vert.length * vert[0].dataSize() + 8];
        myutil.putInt(vert.length, ret, 0);
        for(int i=0; i<vert.length;i++ )
            vert[i].toBytes(ret,8 + i*vert[0].dataSize());
        return ret;
    }

    
    public int loadData(byte[] data, int offset) throws InvalidDataException { //WARNING!!! first thing on the array: the size
        int loaded=4;
        vert=new myVertex[myutil.getInt(data, offset + loaded)];
        vert[0]=new myVertex(); // dummy, needed to evaluate its datasize into the for cycle
        if(myutil.getInt(data, offset + loaded+4)!=0) 
            throw new InvalidDataException("VERT section: int #2: " + myutil.getInt(data, offset + loaded+4));
        loaded+=8;

        for(int i=0; i<vert.length;i++ )
            vert[i]=new myVertex(data, offset + loaded + i*vert[0].dataSize());

        loaded+=vert.length*vert[0].dataSize();

        return loaded-4;
    }
}