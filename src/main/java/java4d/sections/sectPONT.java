/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.*;

/**
 *
 * @author Pierantonio
 */
public class sectPONT extends sect {

    protected PONTdata sdatacasted;

    public sectPONT(sect parent)
    {
        super(parent);
        sdata=new PONTdata(this);
        sdatacasted=(PONTdata)sdata;
    }

}