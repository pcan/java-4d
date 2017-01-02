/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.datatypes.vector3f;
import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class AMBIdata extends sectData {

    public vector3f vectAMBI;

    public AMBIdata(sect owner)
    {
        super(owner);
    }

    @Override
    public int getSize() {
        return vectAMBI.dataSize();
    }

    @Override
    public byte[] getData() {
        return vectAMBI.toBytes();
    }

    @Override
    public int loadData(byte[] data, int offset) { //first thing on the array: the size
        vectAMBI = new vector3f(data, offset+4);
        return vectAMBI.dataSize();
    }
}

