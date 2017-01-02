/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.editors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java4d.sections.data.sectData;
import java4d.sections.sect;
import java4d.sections.sectSCEN;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import utilities.IconFactory;
/**
 *
 * @author Pierantonio
 */
public class sectMenu {
    protected JMenu edit,menu;
    protected sect owner;

    public sectMenu(sect s_owner)
    {
        owner=s_owner;
        edit=new JMenu("Edit"); // they were outside "if"
        edit.add(menu = new JMenu("-"));

            // <editor-fold defaultstate="collapsed" desc="default menu">
            menu.setText(owner.getTitle());
            ImageIcon j = IconFactory.getIcon("java4d/images/icon" + owner.getTitle() + ".png");
            if (j == null) {
                j = IconFactory.getIcon("java4d/images/iconUnknown.png");

            }
            menu.setIcon(j);

            //adds "Delete this" button
            if (owner.getParent() !=null && owner.getParent().getClass() == sectSCEN.class) {
                final sect cur = owner;
                final sectSCEN curscen = ((sectSCEN) cur.getParent());
                JMenuItem mnu_del = new JMenuItem("Delete this");
                mnu_del.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        int i=JOptionPane.showOptionDialog(null, "This operation will delete all hierarchically-linked children (if any).\nAre you sure?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
                        if(i==JOptionPane.NO_OPTION) return;
                        curscen.deleteChild(cur);
                        if (owner.getEventListener() != null) {
                            owner.getEventListener().structureChanged();

                        }
                    }
                });
                menu.add(mnu_del);
            }
            
            //adds "Open #### editor" button
            JMenuItem ed=null;
            try {
                Class.forName("java4d.sections.editors." + owner.getTitle() + "editor").getConstructor(sectData.class);
                ed = new JMenuItem("Open " + owner.getTitle() + " editor");
                ed.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        owner.showEditor();
                    }
                });
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(sectMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(sectMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {

            }
            if(ed!=null)
                menu.add(ed);

            // </editor-fold>

    }
    
    public JMenu getMenu()
    {
        return edit;
    }
}
