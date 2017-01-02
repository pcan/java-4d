/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.datatypes;

import java.awt.Component;
import java4d.myutil;

/**
 *
 * @author Pierantonio
 */
public class vectorXf extends myDataType {

    public float val[];

    public vectorXf(int cardinality) {
        val = new float[cardinality];
    }

    public int dataSize() {
        return val.length * 4;
    }

    public vectorXf(byte[] source, int offset, int cardinality) {
        this(cardinality);
        getFrom(source, offset);
    }

    public void toBytes(byte dest[], int offset) {
        for(int i=0;i<val.length;i++)
            myutil.putFloat(val[i], dest, offset + i*4);
    }

    public void getFrom(byte source[], int offset) {
        for(int i=0;i<val.length;i++)
            val[i] = myutil.getFloat(source, offset + i*4);

    }

    public String toString()
    {
        String ret=new String("( ");
        for(float f : val)
            ret+=String.format("%.2f ", f);

        return ret + ") ";
    }

    @Override
    public vectorXf clone()
    {
        vectorXf copy=new vectorXf(val.length);
            copy.val=val.clone();
        return copy;
    }

    @Override
    public Component getEditor() {
        return null;
    }

}