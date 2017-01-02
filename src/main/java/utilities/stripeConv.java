/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.util.LinkedList;

/**
 *
 * @author Pierantonio
 */
public class stripeConv {

    public static int[] convertToStripe(LinkedList<int[]> tri)
    {
        LinkedList<Integer> stripe = new LinkedList<Integer>();
        System.err.println("Number of faces:" + (tri.size()*3));
        int ret[]=null;
        int i=0;
        if(tri.size()>2)
        {
            int tmptri[]=tri.removeFirst();
            stripe.add(tmptri[0]);
            stripe.add(tmptri[1]);
            stripe.add(tmptri[2]);

            //tri.removeFirst();
            
            while(tri.size()>0)
            {
                int next=findNextVert(tri, stripe.get(stripe.size()-2), stripe.get(stripe.size()-1), i %2 );
                
                if(next!=-1)
                {
                    i++;
                    stripe.add(next);
                }else{

                    next=findNextVert(tri, stripe.get(0), stripe.get(1), (i-1) %2);
                    if(next!=-1)
                    {
                        i++;
                        stripe.addFirst(next);
                    }else{

                        stripe.add(stripe.get(stripe.size()-1));
                        tmptri=tri.removeFirst();
                        stripe.add(tmptri[0]);
                        stripe.add(tmptri[0]);
                        stripe.add(tmptri[1]);
                        stripe.add(tmptri[2]);
                        i+=5;
                    }
                }
                if((tri.size() % 200) == 0) System.err.println(tri.size() + " indices to go...");
            }
            ret=new int[stripe.size()];
            for(i=0;i<ret.length;i++)
                ret[i]=stripe.get(i);

            
        }
        System.err.println("Done!... Number of stripe elements:" + (i+3));
        return ret;
    }
    
    private static int findNextVert(LinkedList<int[]> tri,int va,int vb,int ord)
    {
        int tmp=va;
        if(ord==1) //dispari
        {
            va=vb;
            vb=tmp;
        }
        for(int i=0;i<tri.size();i++)
        {
            int e[]=tri.get(i);
            if(e[0]==va && e[2]==vb)
            {
                return tri.remove(i)[1];
            }
            if(e[1]==va && e[2]==vb)
            {
                return tri.remove(i)[0];
            }
            if(e[0]==va && e[1]==vb)
            {
                return tri.remove(i)[2];
            }
        }
        return -1;
    }

}
