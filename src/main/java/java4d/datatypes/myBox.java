/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.datatypes;

import java.awt.Component;

/**
 *
 * @author Pierantonio
 */
public class myBox extends myDataType {

    public vector3f coords[];

    public myBox() {
        coords = new vector3f[8];
        for(int i=0;i<8;i++)
            coords[i]=new vector3f();
    }

    public int dataSize() {
        return 8*coords[0].dataSize();
    }

    public myBox(byte[] source, int offset) {
        this();
        getFrom(source, offset);
    }

    public void toBytes(byte dest[], int offset) {
        for(int i=0;i<8;i++)
            coords[i].toBytes(dest, offset + i * coords[0].dataSize());

    }

    public void getFrom(byte source[], int offset) {
        for(int i=0;i< 8 ;i++)
            coords[i].getFrom(source, offset + i * coords[0].dataSize());
    }

    @Override
    public myBox clone()
    {
        myBox copy=new myBox();
        for(int i=0;i< 8 ;i++)
            copy.coords[i]=coords[i].clone();
        return copy;
    }

    @Override
    public Component getEditor() {
        return null;
    }
}

