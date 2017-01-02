/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.INDIdata;

/**
 *
 * @author Pierantonio
 */
public class sectINDI extends sect {

    protected INDIdata sdatacasted;

    public sectINDI(sect parent)
    {
        super(parent);
        sdata=new INDIdata(this);
        sdatacasted=(INDIdata)sdata;
    }

    public sectINDI(sectMESH parent, int numIndices)
    {
        this(parent);
        sdatacasted.ind=new int[numIndices];
    }

    @Override
    public String getTipDetails()
    {
        return new String("INDI: " + getIndCount() + " index(es)");
    }

    public int getIndCount()
    {
        return sdatacasted.ind.length;
    }


}