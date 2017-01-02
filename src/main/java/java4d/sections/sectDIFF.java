/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.DIFFdata;

/**
 *
 * @author Pierantonio
 */
public class sectDIFF extends sect {

    protected DIFFdata sdatacasted;

    public sectDIFF(sect parent)
    {
        super(parent);
        sdata=new DIFFdata(this);
        sdatacasted=(DIFFdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("DIFF: " + sdatacasted.desc.val);
    }

}