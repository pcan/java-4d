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
public class SPOTdata extends sectData {

    public float coneLen;
    public float constantAtt;
    public float lineartAtt;
    public float quadraticAtt;
    public float spotExp;
    public float spotCutoff; //angle

    public SPOTdata(sect owner)
    {
        super(owner);
    }

    @Override
    public int getSize() {
        return 24;
    }

    @Override
    public byte[] getData() {
        byte ret[]=new byte[24];
        myutil.putFloat(coneLen, ret, 0);
        myutil.putFloat(constantAtt, ret, 4);
        myutil.putFloat(lineartAtt, ret, 8);
        myutil.putFloat(quadraticAtt, ret, 12);
        myutil.putFloat(spotExp, ret, 16);
        myutil.putFloat(spotCutoff, ret, 20);
        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        coneLen=myutil.getFloat(data, offset + loaded + 0);
        constantAtt=myutil.getFloat(data, offset + loaded + 4);
        lineartAtt=myutil.getFloat(data, offset + loaded + 8);
        quadraticAtt=myutil.getFloat(data, offset + loaded + 12);
        spotExp=myutil.getFloat(data, offset + loaded + 16);
        spotCutoff=myutil.getFloat(data, offset + loaded + 20);
        loaded+=24;

        return loaded-4;
    }
}
