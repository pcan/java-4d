/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.BBOXdata;

/**
 *
 * @author Pierantonio
 */
public class sectBBOX extends sect {

    protected BBOXdata sdatacasted;

    public sectBBOX(sect parent)
    {
        super(parent);
        sdata=new BBOXdata(this);
        sdatacasted=(BBOXdata)sdata;
    }

}