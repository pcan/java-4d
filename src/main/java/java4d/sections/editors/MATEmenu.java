/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.editors;

import java4d.sections.data.DIFFdata;
import java4d.sections.sect;
import java4d.sections.sectDIFF;
import java4d.sections.sectMATE;
import java4d.sections.sectREFL;
import java4d.sections.sectSPEC;
import javax.swing.JMenuItem;

/**
 *
 * @author Pierantonio
 */
public class MATEmenu extends sectMenu {
    public MATEmenu(sect s_owner)
    {
        super(s_owner);
        JMenuItem addDiff = new JMenuItem("Add DIFF material", null);
        menu.add(addDiff);
        addDiff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectDIFF newdiff=new sectDIFF(owner);
                ((sectMATE)owner).addDiff(newdiff);
                ((DIFFdata)newdiff.getSectionData()).desc.val="new_diff.tga";
            }
        });
        JMenuItem addRefl = new JMenuItem("Add REFL material", null);
        menu.add(addRefl);
        addRefl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectREFL newrefl=new sectREFL(owner);
                ((sectMATE)owner).addRefl(newrefl);
                ((DIFFdata)newrefl.getSectionData()).desc.val="new_refl.tga";
            }
        });
        JMenuItem addSpec = new JMenuItem("Add SPEC material", null);
        menu.add(addSpec);
        addSpec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectSPEC newspec=new sectSPEC(owner);
                ((sectMATE)owner).addSpec(newspec);
                ((DIFFdata)newspec.getSectionData()).desc.val="new_spec.tga";
            }
        });

    }
}
