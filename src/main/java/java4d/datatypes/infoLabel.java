/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.datatypes;

import java.awt.Component;
import java.awt.Dimension;
import java4d.myutil;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Pierantonio
 */
public class infoLabel extends myDataType{

    public String val;

    public infoLabel() {
        val = new String();
    }

    public infoLabel(byte[] source, int offset) {
        this();
        getFrom(source, offset);
    }

    public int dataSize() {
        return val.length() + 2;
    }

    public void toBytes(byte dest[], int offset) {
        myutil.putShort(val.length(), dest, offset);
        System.arraycopy(val.getBytes(), 0, dest, offset + 2, val.length());
    }

    public void getFrom(byte source[], int offset) {
        int len = myutil.getShort(source, offset);
        val = new String(source, offset+2, len);
    }

    public String toString()
    {
        return new String(val);
    }

    @Override
    public infoLabel clone()
    {
        infoLabel copy=new infoLabel();
        copy.val=new String(val);
        return copy;
    }

    @Override
    public Component getEditor() {
        JPanel p= new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        
        JLabel l =new JLabel("Description:");
        p.add(l);

        l.setMinimumSize(new Dimension(10000,16));
        final JTextField t=new JTextField();
        p.add(t);

        t.setText(val);

        t.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                val=t.getText();
            }
        });
        
        myutil.packPanel(p, 200);
        return p;
    }

}