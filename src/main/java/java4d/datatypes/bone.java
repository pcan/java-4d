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
public class bone extends myDataType{

    public int dumyid;
    public spaceSystem space;

    public bone()
    {
        space=new spaceSystem();
    }

    @Override
    public int dataSize() {
        return 4 + space.dataSize();
    }

    @Override
    public void toBytes(byte[] dest, int offset) {
        myutil.putInt(dumyid,dest, offset);
        space.toBytes(dest, offset + 4);
    }

    @Override
    public void getFrom(byte[] source, int offset) {
        dumyid=myutil.getInt(source, offset);
        space.getFrom(source, offset+4);
    }

    @Override
    public String toString()
    {
        return new String("Dumy id: " + dumyid);
    }

    @Override
    public bone clone()
    {
        bone copy=new bone();
        copy.dumyid=dumyid;
        copy.space=space.clone();
        return copy;
    }

    @Override
    public Component getEditor() {
        return null;
    }

}
