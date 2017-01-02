/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.SSQEdata;

/**
 *
 * @author Pierantonio
 */
public class sectSSQE extends sect {

    protected SSQEdata sdatacasted;

    public sectSSQE(sect parent)
    {
        super(parent);
        sdata=new SSQEdata(this);
        sdatacasted=(SSQEdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("SSQE: " + sdatacasted.desc.val);
    }

}