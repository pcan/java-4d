/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

import java.util.LinkedList;
import java4d.sections.data.SCENdata;

/**
 *
 * @author Pierantonio
 */
public class sectSCEN extends sect {

    private SCENdata sdatacasted;
    

    public sectSCEN(sect parent)
    {
        super(null);
        sdata=new SCENdata(this);
        sdatacasted=(SCENdata)sdata;
    }

    @Override
    public int getVersion()
    {
        return sdatacasted.getVersionInt();
    }

    public String getVersionStr()
    {
        return sdatacasted.getVersionStr();
    }

    @Override
    public String getTipDetails()
    {
        return new String("SCEN - Version: " + sdatacasted.getVersionStr());
    }

    public void deleteChild(sect cursect) // safe
    {
        if (sectMESH.class.isInstance(cursect))
        {
            deleteHierarchy((sectMESH) cursect);
        } else {
            sectList.remove(cursect);
        }
    }

    private void deleteHierarchy(sectMESH cursect) // safe
    {
        LinkedList<sect> l=getChildrenTypeAndDerived(sectMESH.class);
        int curId=l.indexOf(cursect);
        if(curId!=-1)
        {
            for(sect s : l)
            {
                if(((sectMESH)s).sdatacasted.parentEntityId==curId)
                    deleteHierarchy((sectMESH)s);
                sectMESH k=((sectMESH)s);
                if(k.sdatacasted.parentEntityId != -1 && k.sdatacasted.parentEntityId > curId)
                    k.sdatacasted.parentEntityId--;
            }
            sectList.remove(cursect);
        }
    }

    private boolean isNewParentValid(sectMESH s,int newParentId)
    {
        boolean ret=true;

        if(newParentId==-1) return true;
        
        LinkedList<sect> l=getChildrenTypeAndDerived(sectMESH.class);
        if(newParentId>= l.size()) return false;

        int curId=l.indexOf(s);
        if(newParentId==curId) return false;

        sectMESH tmp = (sectMESH)l.get(newParentId);

        while(ret && tmp.sdatacasted.parentEntityId != -1)
        {
            tmp=(sectMESH)l.get(tmp.sdatacasted.parentEntityId);
            if(tmp==s) ret=false;
        }

        return ret;
    }

    public LinkedList<sectMESH> getValidParents(sectMESH s)
    {
        LinkedList<sect> l=getChildrenTypeAndDerived(sectMESH.class);
        LinkedList<sectMESH> ret=new LinkedList<sectMESH>();
        if(l!=null)
        for(sect t : l)
        {
            if( isNewParentValid(s,getEntityId((sectMESH)t)))
                ret.add(((sectMESH)t));
        }
        return ret;
    }

    public int getEntityId(sectMESH s)
    {
        LinkedList<sect> l=getChildrenTypeAndDerived(sectMESH.class);
        return l.indexOf(s);
    }

    public void addObjectAt(sectMESH child, int pos)
    {
        LinkedList<sect> l=getChildrenTypeAndDerived(sectMESH.class);
        //sectList.indexOf(l.getLast())
        if (l != null) {
            int lastindex = sectList.indexOf(l.getLast());
            int firstindex = sectList.indexOf(l.getFirst());

            if (pos < 0) {
                pos = 0;
            }

            if (pos > lastindex - firstindex) {
                pos = lastindex - firstindex;
            }


            if (pos + firstindex >= sectList.size()) {
                sectList.add(child);
            } else {
                sectList.add(pos + firstindex, child);
                l = getChildrenTypeAndDerived(sectMESH.class);
                for (sect s : l) {
                    sectMESH m = (sectMESH) s;
                    if (m.sdatacasted.parentEntityId >= pos) {
                        m.sdatacasted.parentEntityId++;
                    }
                }
            }
        }else
        {
            sectList.add(child);
        }
    }

    public int getFirstObjectIndex()
    {
        LinkedList<sect> l=getChildrenTypeAndDerived(sectMESH.class);
        if(l!=null)  //needs to be fixed...
            return sectList.indexOf(l.getFirst());
        return sectList.size()-1; //needs to be fixed...
    }
    public int getLastObjectIndex()
    {
        LinkedList<sect> l=getChildrenTypeAndDerived(sectMESH.class);
        if(l!=null)  //needs to be fixed...
            return sectList.indexOf(l.getLast());
        return sectList.size()-1; //needs to be fixed...
    }


}

