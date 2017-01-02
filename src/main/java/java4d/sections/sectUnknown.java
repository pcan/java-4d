/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.Unknowndata;

/**
 *
 * @author Pierantonio
 */
public class sectUnknown extends sect {

    //private Unknowndata sdata;
    private byte title[];

    public sectUnknown(sect parent)
    {
        super(parent);
        title=new byte[4];
        sdata=new Unknowndata(this);
        sectList=null;
    }

    @Override
    public boolean loadData(byte[] data, int offset) {
        try
        {
            System.arraycopy(data, offset, title, 0, 4);
            sdata.loadData(data, offset+4);  //first thing passed on the array: the data size
        }
        catch(Exception ex)
        {
            return false;
        }

        return true;
    }

    @Override
    public String getTitle()
    {
        return new String(title);
    }

    /*@Override
    public String getTipDetails()
    {
        //return new String( new String(title) + " (Unknown)");
        return "";
    }*/

}
