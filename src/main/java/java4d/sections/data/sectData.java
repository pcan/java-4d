
package java4d.sections.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java4d.datatypes.myDataType;
import java4d.my4dfile.InvalidDataException;
import java4d.sections.*;



public abstract class sectData implements Cloneable{
    protected sect owner;
    protected static sect clipboard;
    
    public sectData(sect ownerSection)
    {
        this.owner=ownerSection;
    }
    abstract public int getSize();

    abstract public byte[] getData();

    public sect getOwner()
    {
        return owner;
    }

     //returns the number of bytes consumed:
    abstract public int loadData(byte[] data, int offset) throws InvalidDataException;//WARNING!!! first thing on the array: the size


    public void copyFrom(sectData source)
    {

        if(this.getClass().isInstance(source))
        try {
            for (Field f : this.getClass().getFields()) {
                Object val = f.get(source);
                if (myDataType.class.isInstance(val)) {
                    f.set(this, ((myDataType) val).clone());
                }else if(val.getClass().isArray()){
                    f.set(this, ((myDataType) val).clone());
                }
                else {
                    f.set(this, val);
                }
            }
        } catch (SecurityException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public sectData clone() {  // pure magic ;)
        Object copy = null;

        try {
            copy = this.getClass().getConstructor(sect.class).newInstance(owner);

            for (Field f : this.getClass().getFields()) {
                Object val = f.get(this);
                if (myDataType.class.isInstance(val)) {
                    f.set(copy, ((myDataType) val).clone());
                }else if(val.getClass().isArray()){
                    f.set(copy, ((myDataType) val).clone());
                }
                else {
                    f.set(copy, val);
                }

            }
        } catch (InstantiationException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(sectData.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (sectData) copy;
    }

}
