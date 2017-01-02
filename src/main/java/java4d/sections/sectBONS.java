/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.BONSdata;

/**
 *
 * @author Pierantonio
 */
public class sectBONS extends sect {

    protected BONSdata sdatacasted;

    public sectBONS (sect parent)
    {
        super(parent);
        sdata=new BONSdata(this);
        sdatacasted=(BONSdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("BONS: " + sdatacasted.bones.length + " bones" );
    }

}