/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.REFLdata;

/**
 *
 * @author Pierantonio
 */
public class sectREFL extends sectDIFF {

    //protected REFLdata sdatacasted;

    public sectREFL(sect parent)
    {
        super(parent);
        sdata=new REFLdata(this);
        sdatacasted=(REFLdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("REFL: " + sdatacasted.desc.val);
    }

}