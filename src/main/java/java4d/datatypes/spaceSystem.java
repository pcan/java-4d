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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/** 3versors-system, plus the absolute application point (coord)
 *
 * @author Pierantonio
 */
public

class spaceSystem extends myDataType {

    public vector3f versors[],
                    coord;
    protected float[] tmpmatr;
    private JSpinner cx,cy,cz,ax,ay,az;//,spscale;

    public spaceSystem() {
        versors = new vector3f[3];
        for(int i=0;i<versors.length;i++)
            versors[i]=new vector3f();
        coord = new vector3f();
    }

    public spaceSystem(byte[] source, int offset) {
        this();
        getFrom(source, offset);
    }

    public int dataSize() {
        return coord.dataSize() * 4;
    }

    @Override
    public void toBytes(byte[] dest, int offset) {
        for(int i=0;i<versors.length;i++)
            versors[i].toBytes(dest, offset + i* coord.dataSize() );
        coord.toBytes(dest, offset + coord.dataSize() * versors.length);
    }

    @Override
    public void getFrom(byte[] source, int offset) {
        for(int i=0;i<versors.length;i++)
            versors[i].getFrom(source, offset + i* coord.dataSize() );
        coord.getFrom(source, offset + coord.dataSize() * versors.length);
    }

    public float[] getTransformMatrix()
    {
        float matr[]=new float[16];
        for(int i=0; i<3;i++)
        {
        matr[4*i  ]=versors[i].val[0];
        matr[4*i+1]=versors[i].val[1];
        matr[4*i+2]=versors[i].val[2];
        matr[12+ i]=coord.val[i];
        }
        matr[15]=1;
        return matr;
    }

    public void setTransformMatrix(float matr[])
    {

        for(int i=0; i<3;i++)
        {
        versors[i].val[0]=matr[4*i  ];
        versors[i].val[1]=matr[4*i+1];
        versors[i].val[2]=matr[4*i+2];
        coord.val[i]=matr[12+ i];
        }
    }

    public String toString()
    {
        String ret=new String();
        for(vector3f v : versors)
            ret+=v.toString() + " ";
        ret+=" -  " + coord.toString();
        return ret;
    }

    @Override
    public spaceSystem clone()
    {
        spaceSystem copy=new spaceSystem();
        for(int i=0;i<versors.length;i++)
            copy.versors[i]=versors[i].clone();
        copy.coord=coord.clone();
        return copy;
    }

    @Override
    public Component getEditor() {
        tmpmatr=getTransformMatrix().clone();

        final JPanel p = new JPanel();
        JPanel p1= new JPanel();
        JPanel p2= new JPanel();
        //JPanel p3= new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p1.setLayout(new BoxLayout(p1,BoxLayout.LINE_AXIS));
        p2.setLayout(new BoxLayout(p2,BoxLayout.LINE_AXIS));
        //p3.setLayout(new BoxLayout(p3,BoxLayout.PAGE_AXIS));

        Dimension exp1=new Dimension(10000,16);
        JLabel l1=new JLabel("Coordinates (x,y,z):");
        l1.setMinimumSize(exp1);
        p.add(l1);
        
        cx = new JSpinner(new SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1f)));
        cy = new JSpinner(new SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1f)));
        cz = new JSpinner(new SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1f)));

        cx.getModel().setValue(Float.valueOf(coord.val[0]));
        cy.getModel().setValue(Float.valueOf(coord.val[1]));
        cz.getModel().setValue(Float.valueOf(coord.val[2]));

        cx.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                changePosition();
                if(p.getParent()!=null)
                p.getParent().firePropertyChange("renderer", 0, 1);
            }
        });
        cy.addChangeListener(cx.getChangeListeners()[0]);
        cz.addChangeListener(cx.getChangeListeners()[0]);

        p1.add(cx);
        p1.add(cy);
        p1.add(cz);

        p.add(p1);

        JLabel l2=new JLabel("Axis rotation (x,y,z):");
        l1.setMinimumSize(exp1);
        p.add(l2);

        ax = new JSpinner(new SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1f)));
        ax.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                changeRotation();
                if(p.getParent()!=null)
                p.getParent().firePropertyChange("renderer", 0, 1);
            }
        });
        ay = new JSpinner(new SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1f)));
        ay.addChangeListener(ax.getChangeListeners()[0]);
        az = new JSpinner(new SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1f)));
        az.addChangeListener(ax.getChangeListeners()[0]);

        p2.add(ax);
        p2.add(ay);
        p2.add(az);

        p.add(p2);


        /*JLabel l=new JLabel("Scale:");
        l.setMinimumSize(new Dimension(10000,16));

        p3.add(l);
        spscale = new JSpinner();
        spscale.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(0.05f)));
        spscale.setValue(Float.valueOf(1f));
        spscale.setMaximumSize(new Dimension(150,(int)spscale.getMinimumSize().getHeight()));
        spscale.addChangeListener(ax.getChangeListeners()[0]);
        p3.add(spscale);

        p.add(p3);*/
        myutil.packPanel(p, 350);

        return p;
    }

    private void changeRotation()
    {
        float rotx[]=new float[16];
        rotx[5]=rotx[15]=1;
        rotx[0]=rotx[10]=(float) Math.cos(((Float)((SpinnerNumberModel)ax.getModel()).getValue()) * Math.PI / 180  );
        rotx[8]=(float) Math.sin(((Float)((SpinnerNumberModel)ax.getModel()).getValue()) * Math.PI / 180);
        rotx[2]=-rotx[8];
        float roty[]=new float[16];
        roty[0]=roty[15]=1;
        roty[5]=roty[10]=(float) Math.cos((Float)(((SpinnerNumberModel)ay.getModel()).getValue()) * Math.PI / 180);
        roty[6]=(float) Math.sin(((Float)((SpinnerNumberModel)ay.getModel()).getValue()) * Math.PI / 180);
        roty[9]=-roty[6];
        float rotz[]=new float[16];
        rotz[10]=rotz[15]=1;
        rotz[0]=rotz[5]=(float) Math.cos((Float)(((SpinnerNumberModel)az.getModel()).getValue()) * Math.PI / 180);
        rotz[1]=(float) Math.sin(((Float)((SpinnerNumberModel)az.getModel()).getValue()) * Math.PI / 180);
        rotz[4]=-rotz[1];

        /*float scale[]=new float[16];
        scale[0]=(Float)((SpinnerNumberModel)spscale.getModel()).getValue();
        scale[5]=(Float)((SpinnerNumberModel)spscale.getModel()).getValue();
        scale[10]=(Float)((SpinnerNumberModel)spscale.getModel()).getValue();
        scale[15]=1;*/

        //setTransformMatrix(myutil.mat4x4Mult(myutil.mat4x4Mult(myutil.mat4x4Mult(myutil.mat4x4Mult(tmpmatr, rotx),roty),rotz),scale));
        setTransformMatrix(myutil.mat4x4Mult(myutil.mat4x4Mult(myutil.mat4x4Mult(tmpmatr, rotx),roty),rotz));
        changePosition();
    }

    private void changePosition()
    {
        coord.val[0]= (Float)((SpinnerNumberModel)cx.getModel()).getValue();
        coord.val[1]= (Float)((SpinnerNumberModel)cy.getModel()).getValue();
        coord.val[2]= (Float)((SpinnerNumberModel)cz.getModel()).getValue();
    }

}