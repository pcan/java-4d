/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.editors;

import java4d.myutil;
import java4d.sections.data.sectData;

/**
 *
 * @author Pierantonio
 */
public class sectEditor extends javax.swing.JFrame {

    protected sectData sdata;
    protected final Thread caller=Thread.currentThread();

    public sectEditor(sectData toEdit)
    {
        sdata=toEdit;
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        setLocation(myutil.screen.width/2 - getWidth()/2 , myutil.screen.height/2 - getHeight()/2);
    }

    private final void formWindowClosed(java.awt.event.WindowEvent evt) {
        caller.interrupt();
    }

}
