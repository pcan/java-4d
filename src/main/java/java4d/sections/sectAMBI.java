/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.AMBIdata;

/**
 *
 * @author Pierantonio
 */
public class sectAMBI extends sect {

    private AMBIdata sdatacasted;

    public sectAMBI(sect parent)
    {
        super(parent);
        sdata=new AMBIdata(this);
        sdatacasted=(AMBIdata)sdata;
    }

}

