/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.data;

import java4d.sections.*;

/**
 *
 * @author Pierantonio
 */
public class SCENdata extends sectData {

    private byte ver[];

    public SCENdata(sect owner) {
        super(owner);
        ver = new byte[4];
    }

    @Override
    public int getSize() {
        return 4;

    }

    @Override
    public byte[] getData() {
        return ver;
    }


    @Override
    public int loadData(byte[] data, int offset) //first thing on the array: the size
    {
        try {
            System.arraycopy(data, offset+4, ver, 0, 4);
        } catch (ArrayIndexOutOfBoundsException ex) {

        }
        return 4;

    }

    //-------------------- SECTION - SPECIFIC methods ---------------------//


    public int getVersionInt()
    {
        ver[0] = '1';
        int v=Integer.decode(new String(ver));
        ver[0] = 'v';
        return v - 1000;
    }

    public String getVersionStr()
    {
        return new String(ver);
    }

}


