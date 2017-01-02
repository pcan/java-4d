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
public class INDIdata extends sectData {

    public int ind[];  // Array of UNSIGNED SHORT!!!!

    public INDIdata(sect owner)
    {
        super(owner);
        ind=null;
    }

    @Override
    public int getSize() {
        return ind.length * 2 + 4;
    }

    @Override
    public byte[] getData() {
        byte ret[]=new byte[ind.length * 2 + 4];
        myutil.putInt(owner.getClass()==sectINDI.class ? ind.length : ind.length/3, ret, 0);
        for(int i=0; i<ind.length;i++ )
            myutil.putShort(ind[i], ret, 4 + i*2);
       return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        int indlen= myutil.getInt( data, offset + loaded);
        ind=new int[owner.getClass()==sectINDI.class ? indlen : indlen * 3];
        loaded+=4;

        for(int i=0; i<ind.length;i++ )
            ind[i]=myutil.getShort(data, offset + loaded + i*2);
        loaded+=ind.length * 2;

        return loaded-4;
    }
}
