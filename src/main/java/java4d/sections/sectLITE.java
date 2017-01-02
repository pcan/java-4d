/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java.util.LinkedList;
import java4d.sections.data.LITEdata;

/**
 *
 * @author Pierantonio
 */
public class sectLITE extends sectMESH {

    //protected LITEdata sdatacasted;

    public sectLITE(sect parent)
    {
        super(parent);
        sdata=new LITEdata(this);
        sdatacasted=(LITEdata)sdata;
    }

    public void setSpot(sectSPOT spot)
    {
        sectList=new LinkedList<sect>();
        sectList.add(spot);
    }

    public void setPont(sectPONT pont)
    {
        sectList=new LinkedList<sect>();
        sectList.add(pont);
    }

}