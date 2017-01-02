/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.myutil;
import java4d.sections.sect;

/**
 *
 * @author Pierantonio
 */
public class PONTdata extends sectData {

    public float maxDist;
    public float constantAtt;
    public float lineartAtt;
    public float quadraticAtt;

    public PONTdata(sect owner)
    {
        super(owner);
    }

    @Override
    public int getSize() {
        return 16;
    }

    @Override
    public byte[] getData() {
        byte ret[]=new byte[16];
        myutil.putFloat(maxDist, ret, 0);
        myutil.putFloat(constantAtt, ret, 4);
        myutil.putFloat(lineartAtt, ret, 8);
        myutil.putFloat(quadraticAtt, ret, 12);
        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        maxDist=myutil.getFloat(data, offset + loaded + 0);
        constantAtt=myutil.getFloat(data, offset + loaded + 4);
        lineartAtt=myutil.getFloat(data, offset + loaded + 8);
        quadraticAtt=myutil.getFloat(data, offset + loaded + 12);
        loaded+=16;

        return loaded-4;
    }
}

