/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.sections.data.DUMYdata;

/**
 *
 * @author Pierantonio
 */
public class sectDUMY extends sectMESH {

    //DUMYdata sdatacasted;

    public sectDUMY(sect parent)
    {
        super(parent);
        sdata=new DUMYdata(this);
        sdatacasted=(DUMYdata)sdata;
    }

}