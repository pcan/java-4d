/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.myutil;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class MATEdata extends sectData {

    public int indiCount; // Here in MATE, the number of triangles (indi / 3)
    public int vertStart;
    public int vertStop;

    public MATEdata(sect owner)
    {
        super(owner);
    }

    @Override
    public int getSize() {
        return 12;
    }

    @Override
    public byte[] getData() {
        byte ret[] = new byte[12];
        myutil.putInt(indiCount, ret, 0);
        myutil.putInt(vertStart, ret, 4);
        myutil.putInt(vertStop, ret, 8);
        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        indiCount = myutil.getInt(data, offset + loaded);
        loaded+=4;

        vertStart = myutil.getInt(data, offset + loaded);
        loaded+=4;

        vertStop = myutil.getInt(data, offset + loaded);
        loaded+=4;

        return loaded-4;
    }
}

