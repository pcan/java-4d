/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.SPOTdata;

/**
 *
 * @author Pierantonio
 */
public class sectSPOT extends sect {

    protected SPOTdata sdatacasted;

    public sectSPOT(sect parent)
    {
        super(parent);
        sdata=new SPOTdata(this);
        sdatacasted=(SPOTdata)sdata;
    }

}