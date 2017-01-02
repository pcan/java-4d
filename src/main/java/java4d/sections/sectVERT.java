/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java4d.datatypes.myVertex;
import java4d.sections.data.Unknowndata;
import java4d.sections.data.VERTdata;

/**
 *
 * @author Pierantonio
 */
public class sectVERT extends sect {

    protected VERTdata sdatacasted=null;

    public sectVERT(sect parent)
    {
        super(parent);
        if(parent.getClass()==sectMESH.class)
        {
            sdata=new VERTdata(this);
            sdatacasted=(VERTdata)sdata;
        }else{
            sdata=new Unknowndata(this);
            
        }
    }

    public sectVERT(sectMESH parent,int numVertices)
    {
            this(parent);
            sdatacasted.vert=new myVertex[numVertices];
            for(int i=0;i<sdatacasted.vert.length ; i++)
            {
                sdatacasted.vert[i]=new myVertex();
            }
    }

    @Override
    public String getTipDetails()
    {
        return new String("VERT: " + getVertCount() + " vertex(es)");
    }

    public int getVertCount()
    {
        return sdatacasted !=null ? sdatacasted.vert.length : -1;
    }


}