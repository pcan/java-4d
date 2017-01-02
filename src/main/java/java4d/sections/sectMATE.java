/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java.util.LinkedList;
import java4d.sections.data.MATEdata;

/**
 *
 * @author Pierantonio
 */
public class sectMATE extends sect {

    protected MATEdata sdatacasted;

    public sectMATE(sect parent)
    {
        super(parent);
        sdata=new MATEdata(this);
        sdatacasted=(MATEdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        return new String("MATE on " + sdatacasted.indiCount + " triangle(s)");
    }

    public void addDiff(sectDIFF d)
    {
        if(sectList==null) sectList=new LinkedList<sect>();
        if(getChildrenTypeCount(sectDIFF.class) > 0 )
            sectList.remove(getChildrenType(sectDIFF.class).get(0));
        sectList.add(d);
        getEventListener().structureChanged();
    }
    public void addSpec(sectDIFF d)
    {
        if(sectList==null) sectList=new LinkedList<sect>();
        if(getChildrenTypeCount(sectSPEC.class) > 0 )
            sectList.remove(getChildrenType(sectSPEC.class).get(0));
        sectList.add(d);
        getEventListener().structureChanged();
    }
    public void addRefl(sectDIFF d)
    {
        if(sectList==null) sectList=new LinkedList<sect>();
        if(getChildrenTypeCount(sectREFL.class) > 0 )
            sectList.remove(getChildrenType(sectREFL.class).get(0));
        sectList.add(d);
        getEventListener().structureChanged();
    }
}