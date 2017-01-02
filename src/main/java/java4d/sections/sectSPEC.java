/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.SPECdata;

/**
 *
 * @author Pierantonio
 */
public class sectSPEC extends sectDIFF {

    //protected SPECdata sdatacasted;

    public sectSPEC(sect parent)
    {
        super(parent);
        sdata=new SPECdata(this);
        sdatacasted=(SPECdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("REFL: " + sdatacasted.desc.val);
    }

}