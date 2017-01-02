/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections.editors;

import java.io.File;
import java4d.myutil;
import java4d.sections.data.DUMYdata;
import java4d.sections.sect;
import java4d.sections.sectDUMY;
import java4d.sections.sectMESH;
import java4d.sections.sectSCEN;
import javax.swing.JMenuItem;
import net.java.joglutils.model.geometry.Model;
import net.java.joglutils.model.loader.MaxLoader;
import utilities.IconFactory;

/**
 *
 * @author Pierantonio
 */
public class SCENmenu extends sectMenu{
    private Model mod3ds;

    public SCENmenu(sect s_owner)
    {
        super(s_owner);
        JMenuItem addlite = new JMenuItem("Add LITE object", IconFactory.getIcon("java4d/images/iconLITE.png"));
        menu.add(addlite);
        addlite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new LITEmaker((sectSCEN)owner).setVisible(true);
            }
        });

        JMenuItem adddumy = new JMenuItem("Add DUMY object", IconFactory.getIcon("java4d/images/iconDUMY.png"));
        menu.add(adddumy);
        adddumy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectDUMY section;
                DUMYdata data;
                section=new sectDUMY(owner);
                data=new DUMYdata(section);
                data.desc.val=new String("new_dumy");
                data.space.versors[0].val[0]=1;
                data.space.versors[1].val[1]=1;
                data.space.versors[2].val[2]=1;
                parentIdEditDialog ed1=new parentIdEditDialog(null, true,data);
                ed1.setVisible(true);
                if(ed1.hasCancelled()) return;
                int targetpos=sectMaker.getTargetPosition((sectSCEN)owner);

                if(targetpos<0) return;
                section.getSectionData().copyFrom(data);
                ((sectSCEN)owner).addObjectAt(section,targetpos );
                owner.getEventListener().structureChanged();
            }
        });
        JMenuItem importmesh = new JMenuItem("Import MESH object", IconFactory.getIcon("java4d/images/iconMESH.png"));
        menu.add(importmesh);
        importmesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doImportMesh();
                //new LITEmaker((sectSCEN)owner).setVisible(true);
            }
        });
    }


    private void doImportMesh()
    {

        final File fi = myutil.fileOpen(null, "3D Studio Max 3ds files ", "3ds");
        if (fi != null)
        {
            final MaxLoader loader=new MaxLoader();

            mod3ds= loader.load(fi.getPath());
            
            sectMESH m=new sectMESH(owner);
            MESHimporter importer = new MESHimporter(null, true, m, mod3ds);
            importer.setVisible(true);
            if(!importer.isJobDone()) return;

            //mod.getMesh(0).normals.hashCode();
        }

    }

}
