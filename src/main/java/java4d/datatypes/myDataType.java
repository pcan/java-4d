
package java4d.datatypes;

import java.awt.Component;


public abstract class myDataType implements Cloneable{

    abstract public int dataSize();

    abstract public void toBytes(byte dest[], int offset);

    abstract public void getFrom(byte source[], int offset);

    public byte[] toBytes() {
        byte ret[] = new byte[this.dataSize()];
        this.toBytes(ret, 0);
        return ret;
    }
    @Override
    abstract public myDataType clone();

    abstract public Component getEditor();

}










