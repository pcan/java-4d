/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.SSQSdata;

/**
 *
 * @author Pierantonio
 */
public class sectSSQS extends sect {

    protected SSQSdata sdatacasted;

    public sectSSQS(sect parent)
    {
        super(parent);
        sdata=new SSQSdata(this);
        sdatacasted=(SSQSdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("SSQS (" + sectList.size() + " anims)");
    }

}