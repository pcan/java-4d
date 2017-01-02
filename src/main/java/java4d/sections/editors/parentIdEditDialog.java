/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJDialog.java
 *
 * Created on 17-ago-2009, 21.03.22
 */

package java4d.sections.editors;

import java.util.LinkedList;
import java4d.myutil;
import java4d.sections.data.MESHdata;
import java4d.sections.sectMESH;
import java4d.sections.sectSCEN;
import javax.swing.DefaultListModel;

/**
 *
 * @author Pierantonio
 */
public class parentIdEditDialog extends javax.swing.JDialog {

    MESHdata sdata;
    LinkedList<sectMESH> meshlist;
    private boolean cancelled=false;
    /** Creates new form NewJDialog */
    private parentIdEditDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocation(myutil.screen.width/2 - getWidth()/2 , myutil.screen.height/2 - getHeight()/2);
    }

    public parentIdEditDialog(java.awt.Frame parent, boolean modal, MESHdata s_data) {
        this(parent, modal);
        sdata=s_data;
        sectSCEN scen = (sectSCEN)(sdata.getOwner().getParent());
        
        meshlist=((sectSCEN)(sdata.getOwner().getParent())).getValidParents((sectMESH)sdata.getOwner());
        jList1.setModel(new DefaultListModel());

        ((DefaultListModel)jList1.getModel()).addElement("- No parent id -");

        for(sectMESH m : meshlist)
        {
            ((DefaultListModel)jList1.getModel()).addElement(m);
            if(scen.getEntityId(m) == sdata.parentEntityId )
            {
                jList1.setSelectedIndex(jList1.getModel().getSize()-1);
            }
        }
        if(jList1.getSelectedIndex()==-1)
            jList1.setSelectedIndex(0);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);

        jLabel1.setText("Select a parent entity for this object:");

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jList1.getSelectedIndex()==0)
        {
            sdata.parentEntityId=-1;
        }else
        {
            sectMESH sel= ((sectMESH) jList1.getModel().getElementAt(jList1.getSelectedIndex()));
            sdata.parentEntityId=((sectSCEN)sel.getParent()).getEntityId(sel);
        }
        dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cancelled=true;
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    public boolean hasCancelled()
    {
        return cancelled;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}