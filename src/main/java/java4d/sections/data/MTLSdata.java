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
public class MTLSdata extends sectData {

    public MTLSdata(sect owner)
    {
        super(owner);
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public byte[] getData() {
        byte ret[] = new byte[4];
        myutil.putInt(owner.getChildren() != null ? owner.getChildren().size() : 0, ret, 0);
        return ret;
    }

    @Override
    public int loadData(byte[] data, int offset) { //WARNING!!! first thing on the array: the size
        int loaded=4;

        loaded+=4; // doesn't load anything,(just skips 4 bytes) because in MTLS there's
                   // only one integer that tells how many MATE or STRP subsection it owns.
                   // This check is already done by our sectListManager.sectList
        return loaded-4;
    }
}

