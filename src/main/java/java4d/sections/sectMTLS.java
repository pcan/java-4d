/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java.util.LinkedList;
import java4d.sections.data.MTLSdata;

/**
 *
 * @author Pierantonio
 */
public class sectMTLS extends sect {

    protected MTLSdata sdatacasted;

    public sectMTLS(sect parent)
    {
        super(parent);
        sdata=new MTLSdata(this);
        sdatacasted=(MTLSdata)sdata;
    }

    public void setMaterial(sectMATE mate)
    {
        sectList = new LinkedList<sect>();
        sectList.add(mate);
    }

}