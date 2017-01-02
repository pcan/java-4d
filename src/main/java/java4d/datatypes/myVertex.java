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
public class myVertex extends myDataType {

    public vector3f coord,
                    normal;
    public vector2f textureCoord;

    public myVertex() {
        coord=new vector3f();
        normal=new vector3f();
        textureCoord=new vector2f();
    }

    public int dataSize() {
        return coord.dataSize()
               + normal.dataSize()
               + textureCoord.dataSize();
    }

    public myVertex(byte[] source, int offset) {
        this();
        getFrom(source, offset);
    }

    public void toBytes(byte dest[], int offset) {
        coord.toBytes(dest, offset);
        normal.toBytes(dest, offset + coord.dataSize());
        textureCoord.toBytes(dest,offset + coord.dataSize()*2 );
    }

    public void getFrom(byte source[], int offset) {
         coord.getFrom(source, offset);
         normal.getFrom(source, offset + coord.dataSize());
         textureCoord.getFrom(source, offset + coord.dataSize()*2);
    }

    @Override
    public myVertex clone()
    {
        myVertex copy=new myVertex();
        copy.coord=coord.clone();
        copy.normal=normal.clone();
        copy.textureCoord=textureCoord.clone();
        return copy;
    }

    @Override
    public Component getEditor() {
        return null;
    }

}
