/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.editors;

import java4d.myutil;
import java4d.sections.sectSCEN;
import javax.swing.JOptionPane;

/**
 *
 * @author Pierantonio
 */
public class sectMaker extends javax.swing.JFrame{
    sectSCEN parent;

    public sectMaker(sectSCEN s_parent)
    {
        parent=s_parent;
        setLocation(myutil.screen.width/2 - getWidth()/2 , myutil.screen.height/2 - getHeight()/2);
    }

    public static int getTargetPosition(sectSCEN parent_s)
    {
        int ret=-1;
        try{
            int max=parent_s.getLastObjectIndex()-parent_s.getFirstObjectIndex();
            String val=JOptionPane.showInputDialog("Insert the object's position into the list (0 - "+ max + ")");
            if(val!=null)
                ret=Integer.decode(val);
        }
        catch (NumberFormatException ex)
        {
            ret=-1;
        }
        return ret;
    }
}
