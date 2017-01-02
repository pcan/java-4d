/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.STRPdata;

/**
 *
 * @author Pierantonio
 */
public class sectSTRP extends sectMATE {

    //protected STRPdata sdatacasted;

    public sectSTRP(sect parent)
    {
        super(parent);
        sdata=new STRPdata(this);
        sdatacasted=(STRPdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("STRP on " + sdatacasted.indiCount + " index(es)");
    }

}