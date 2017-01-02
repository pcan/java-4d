/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.myutil;


public class sectFLYZ extends sect {

    public sectFLYZ(sect parent)
    {
        super(parent);
        sectList=null;
        sdata=null;
    }

    @Override
    public boolean loadData(byte[] data, int offset) {
        return true;
    }

    @Override
    public String getTitle()
    {
        return new String("FLYZ");
    }

    public int toBytes(byte dest[],int offset)
    {
        System.arraycopy(this.getTitle().getBytes(), 0, dest, offset, 4); //writes the title
        myutil.putInt(0,dest,offset + 4);  //writes the entire section size, minus name and size field
        return 8;
    }

    public int getSize()
    {
        return 8;
    }
}

