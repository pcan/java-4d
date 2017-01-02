/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java.util.LinkedList;
import java4d.sections.data.MESHdata;

/**
 *
 * @author Pierantonio
 */
public class sectMESH extends sect {

    protected MESHdata sdatacasted;

    public sectMESH(sect parent)
    {
        super(parent);
        sdata=new MESHdata(this);
        sdatacasted=(MESHdata)sdata;
    }

    @Override
    public String getTipDetails()
    {
        String parentEntityName=new String("none");
        if(sdatacasted.parentEntityId != -1)
        {
            LinkedList<sect> l= parent.getChildrenTypeAndDerived(sectMESH.class);
            parentEntityName= ((sectMESH)l.get(sdatacasted.parentEntityId)).sdatacasted.desc.val;
        }
        
        return new String(this.getClass().getSimpleName().substring(4)+" " + sdatacasted.desc.val + " - Parent entity: " + parentEntityName );
    }

    public String toString()
    {
        return sdatacasted.desc.val;
    }
    public boolean isStripe()
    {
        sect t=this.getChildrenType(sectMTLS.class).get(0);
        return (((sectMTLS)t).getChildrenType(sectSTRP.class)) != null;
    }

    public void setSubSections(sectBBOX b, sectVERT v, sectINDI i,sectMTLS mtls)
    {
        if(this.getClass() == sectMESH.class)
        {
            sectList = new LinkedList<sect>();
            sectList.add(b);
            sectList.add(v);
            sectList.add(i);
            sectList.add(mtls);
        }
    }
}

