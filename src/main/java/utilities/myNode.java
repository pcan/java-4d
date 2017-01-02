/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java4d.sections.sect;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Pierantonio
 */
public class myNode extends DefaultMutableTreeNode {

    private sect obj;

    public myNode() {
        super();
        obj = null;
    }

    public myNode(String s) {
        super(s);
        obj = null;
    }

    public void setSect(sect s) {
        obj = s;
    }

    public sect getSect() {
        return obj;
    }
}// </editor-fold>

