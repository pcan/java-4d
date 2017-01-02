/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.graphicRenderer;

import com.jogamp.opengl.util.gl2.GLUT;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java4d.sections.*;
import java4d.sections.data.INDIdata;
import java4d.sections.data.LITEdata;
import java4d.sections.data.MESHdata;
import java4d.sections.data.PONTdata;
import java4d.sections.data.SPOTdata;
import java4d.sections.data.VERTdata;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;


/**
 *
 * @author Pierantonio
 */
public class myGlEventListener implements GLEventListener {
    
    private sect scen=null;
    private GLU glu=new GLU();
    private JFrame owner;
    private Lock myLock=new ReentrantLock();
    private GLUT glut=new GLUT();
    int transpx,transpy,anglex=-20,angley=-20,dist=2000;
    private sect hlobject;
    private boolean flyz;
    private boolean updated=false;

    public boolean paintmesh=true,paintdumy=true,paintlite=true;

    public myGlEventListener(JFrame parent)
    {
        owner=parent;
    }

    public void updateRendering()
    {
        updated=false;
    }

    public void setHighlightedObject(sect h)
    {
        hlobject=h;
        updateRendering();
    }
    // <editor-fold defaultstate="collapsed" desc="Init method">
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor((float) 233 / 255, (float) 227 / 255, 1.0f, 0.0f);
        gl.glClearDepth(1.0f);

        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
        initLighting(gl);

    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="initLighting">
    private void initLighting(GL2 gl) {
        float LightAmbient[] = {0.5f, 0.5f, 0.5f, 1.0f};
        float LightDiffuse[] = {0.7f, 0.7f, 0.7f, 1.0f};
        float LightSpecular[] = {0.8f, 0.8f, 0.8f, 1.0f};
        float LightPosition[] = {5.0f, 5.0f, 20.0f, 1.0f};
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_AMBIENT, LightAmbient, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_DIFFUSE, LightDiffuse, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_SPECULAR, LightSpecular, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, LightPosition, 0);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glEnable(gl.GL_LIGHTING);
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="Frame reshape handler">
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        float aspect = (float) width / (float) height;
        //set fov angle, aspect ratio, min and max distance of view
        glu.gluPerspective(45.0f, aspect, 0.01f, -1);
        gl.glViewport(x, y, width, height);

    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="Display method - draws the scene">
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glPushMatrix();

        gl.glTranslatef(-transpx*5, transpy*5, -dist);

        gl.glRotatef(-angley, 1, 0, 0);
        gl.glRotatef(-anglex, 0, 1, 0);

        gl.glPushMatrix();

        drawAxis(gl, dist * 50);

        if (!updated && scen !=null) {
            myLock.lock();
            try {
                gl.glDeleteLists(1, 3);
                if(paintmesh) drawMESH(gl);
                if(paintdumy) drawDUMY(gl,0.5f);
                if(paintlite) drawLITE(gl,1);
            } finally {
                flyz=scen.getChildrenType(sectFLYZ.class) != null;
                
                myLock.unlock();
            }
        }

        updated=true;
        
        gl.glMaterialf(GL.GL_FRONT, gl.GL_SHININESS, 15);
        gl.glEnable(gl.GL_COLOR_MATERIAL);

        if(flyz)gl.glRotatef(-90, 1, 0, 0);
        gl.glColorMaterial(gl.GL_FRONT, gl.GL_AMBIENT_AND_DIFFUSE);

        if (gl.glIsList(1)) //MESH LIST
        {
            float mat_spec_mesh[] = {0.3f, 0.9f, 0.9f, 1f};
            float mat_ambi_mesh[] = {0.0f, 0.0f, 0.0f, 1f};
            gl.glMaterialfv(GL.GL_FRONT, gl.GL_AMBIENT_AND_DIFFUSE, mat_ambi_mesh, 0);
            gl.glMaterialfv(GL.GL_FRONT, gl.GL_SPECULAR, mat_spec_mesh, 0);
            gl.glCallList(1);

        }
        if (gl.glIsList(2)) //DUMY LIST
        {
            float mat_spec_dumy[] = {0.9f, 0.5f, 0.3f, 1f};
            float mat_ambi_dumy[] = {0.2f, 0.2f, 0.2f, 1f};
            gl.glMaterialfv(GL.GL_FRONT, gl.GL_AMBIENT_AND_DIFFUSE, mat_ambi_dumy, 0);
            gl.glMaterialfv(GL.GL_FRONT, gl.GL_SPECULAR, mat_spec_dumy, 0);
            gl.glCallList(2);
        }

        if (gl.glIsList(3)) //DUMY LIST
        { 
            float mat_spec_lite[] = {0, 0, 0,0};
            float mat_diff_lite[] = {0.7f, 0.7f, 0.7f, 1};
            float mat_ambi_lite[] = {0.5f, 0.5f, 0.5f, 1};
            gl.glMaterialfv(GL.GL_FRONT, gl.GL_AMBIENT, mat_ambi_lite, 0);
            gl.glMaterialfv(GL.GL_FRONT, gl.GL_DIFFUSE, mat_diff_lite, 0);
            gl.glMaterialfv(GL.GL_FRONT, gl.GL_SPECULAR, mat_spec_lite, 0);
            gl.glMaterialf(GL.GL_FRONT, gl.GL_SHININESS, 0);
            gl.glCallList(3);
        }

        //drawPontLamp(gl, 3,400);
        
        gl.glPopMatrix();
        gl.glPopMatrix();

        gl.glFlush();
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="drawPontLamp - omnidirectional light source">
    void drawPontLamp(GL2 gl, float size, float maxDist) {
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

        gl.glEnable(gl.GL_BLEND);
        gl.glPushMatrix();
        gl.glScalef(1, 1, 0.8f);
        gl.glTranslatef(0, 0, 25 * size);
        gl.glRotatef(180, 0, 1, 0);
        glut.glutSolidCone(10 * size, 25 * size, 8, 1);
        gl.glRotatef(-180, 0, 1, 0);
        glut.glutSolidCone(10 * size, 4 * size, 8, 1);
        gl.glTranslatef(0, 0, -25 * size);
        gl.glPopMatrix();
        glut.glutSolidSphere(maxDist, 80, 80);
        gl.glDisable(gl.GL_BLEND);
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="draw3Cones">
    void draw3Cones(GL2 gl, float size) {

        gl.glPushMatrix();
        glut.glutSolidCone(15 * size, 40 * size, 8, 1);
        gl.glTranslatef(5 * size, 0, 10 * size);
        gl.glRotatef(90, 0, 1, 0);
        glut.glutSolidCone(6 * size, 20 * size, 8, 1);
        gl.glRotatef(90, 0, -1, 0);
        gl.glTranslatef(-5 * size, 0, 10 * size);
        gl.glRotatef(-90, 1, 0, 0);
        glut.glutSolidCone(6 * size, 20 * size, 8, 1);
        gl.glPopMatrix();
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="drawConeRobot">
    void drawConeRobot(GL2 gl, float size) {

        gl.glPushMatrix();
        gl.glScalef(1, 1, 0.8f);
        gl.glTranslatef(0, 0, 25 * size);
        gl.glRotatef(180, 0, 1, 0);
        glut.glutSolidCone(10 * size, 25 * size, 8, 1);
        gl.glRotatef(-180, 0, 1, 0);
        glut.glutSolidCone(10 * size, 4 * size, 8, 1);
        gl.glTranslatef(0, 0,-25 * size);

        gl.glTranslatef(2 * size, 0, 10 * size);
        gl.glRotatef(100, 0, 1, 0);
        glut.glutSolidCone(3 * size, 14 * size, 8, 1);
        gl.glRotatef(-100, 0, 1, 0);
        gl.glTranslatef(-4 * size, 0,0);
        gl.glRotatef(-100, 0, 1, 0);
        glut.glutSolidCone(3 * size, 14 * size, 8, 1);


        gl.glRotatef(100, 0, 1, 0);
        gl.glTranslatef(2 * size, 0,8 * size);
        gl.glRotatef(-100, 1, 0, 0);
        glut.glutSolidCone(4 * size, 20 * size, 8, 1);
        gl.glPopMatrix();
    }// </editor-fold>


    
    // <editor-fold defaultstate="collapsed" desc="drawSpotLight">
    void drawSpotLight(GL2 gl, float size, float spotCutoff, float maxDist) {

        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

        gl.glEnable(gl.GL_BLEND);
        gl.glPushMatrix();
        glut.glutSolidCylinder(8 * size, 0, 50, 15);
        gl.glRotatef(180, 1, 0, 0);
        glut.glutSolidCylinder(8 * size, 8 * size, 50, 15);
        gl.glTranslatef(0, 0, 15 * size);

        glut.glutSolidCone(15 * size, 0, 40, 15);
        gl.glRotatef(180, 1, 0, 0);
        glut.glutSolidCone(15 * size, 15 * size, 40, 15);
        gl.glTranslatef(0, 0, 15 * size - maxDist);
        glut.glutSolidCone(Math.tan(spotCutoff) * maxDist / 2, maxDist, 50, 5);
        gl.glRotatef(180, 1, 0, 0);
        glut.glutSolidCone(Math.tan(spotCutoff) * maxDist / 2, 0.1f, 50, 5);
        gl.glPopMatrix();
        gl.glDisable(gl.GL_BLEND);

    }// </editor-fold>

    

    // <editor-fold defaultstate="collapsed" desc="Drawaxis method">
    void drawAxis(GL2 gl, float len) {
        float mat_diff[] = {1, 1, 1, 1};
        gl.glMaterialfv(GL.GL_FRONT, gl.GL_AMBIENT_AND_DIFFUSE, mat_diff, 0);

        gl.glEnable(gl.GL_COLOR_MATERIAL);
        gl.glColorMaterial(gl.GL_FRONT, gl.GL_AMBIENT_AND_DIFFUSE);

        gl.glLineWidth(1f);

        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
        //gl.glEnable(GL.GL_LINE_SMOOTH);
        //gl.glEnable(gl.GL_BLEND);

        gl.glBegin(gl.GL_LINES);
        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(-len, 0, 0);
        gl.glVertex3f(len, 0, 0);
        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(0, -len, 0);
        gl.glVertex3f(0, len, 0);
        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(0, 0, -len);
        gl.glVertex3f(0, 0, len);
        gl.glEnd();
        gl.glDisable(gl.GL_BLEND);
        gl.glDisable(GL.GL_LINE_SMOOTH);
    }// </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="transformMatrix">
    private void transformMatrix(sect s, GL2 gl) {
        try {
            int parentEnt = s.getSectionData().getClass().getField("parentEntityId").getInt(s.getSectionData());

            if (parentEnt != -1) {
                Class arg[] = {sectMESH.class, sectDUMY.class, sectLITE.class,
                               sectBSP_.class,sectSKVS.class,sectSKIN.class };
                transformMatrix(s.getParent().getChildrenType(arg).get(parentEnt), gl);
            }
            gl.glMultMatrixf(((MESHdata) (s.getSectionData())).space.getTransformMatrix(), 0);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(myGlEventListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(myGlEventListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(myGlEventListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(myGlEventListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// </editor-fold>


   
    // <editor-fold defaultstate="collapsed" desc="setScene">
    synchronized public void setScene(sect Scene) {
        myLock.lock();
        try {
            scen = Scene;
        } finally {
            myLock.unlock();
        }
        updateRendering();
    }// </editor-fold>


   
    // <editor-fold defaultstate="collapsed" desc="DrawMESH - draws all meshes">
    public void drawMESH(GL2 gl) {
        if (scen != null) {
            LinkedList l = scen.getChildrenType(sectMESH.class);
            gl.glNewList(1, gl.GL_COMPILE); //MESH LIST
            for (int i = 0; l != null && i < l.size(); i++) {
                sectMESH m = (sectMESH) l.get(i);
                VERTdata vdata = (VERTdata)((sectVERT)(m.getChildrenType(sectVERT.class).get(0))).getSectionData();
                INDIdata idata;
                if (m.getChildrenTypeCount(sectINDI.class) > 0) {
                    idata = (INDIdata) ((sectINDI) (m.getChildrenType(sectINDI.class).get(0))).getSectionData();
                } else {
                    idata = (INDIdata) ((sectFACE) (m.getChildrenType(sectFACE.class).get(0))).getSectionData();
                }

                gl.glColor4f(0.5f, 0.5f, 0.5f, 0.3f);
                if((sect)m == hlobject)
                    gl.glColor4f(0.0f, 0.5f, 0.9f, 0.3f);

                gl.glPushMatrix();

                transformMatrix(m, gl);

                if (m.isStripe()) {
                    gl.glBegin(gl.GL_TRIANGLE_STRIP);
                } else {
                    gl.glBegin(gl.GL_TRIANGLES);
                }

                for (int j = 0; j < idata.ind.length; j++) {
                    gl.glNormal3fv(vdata.vert[idata.ind[j]].normal.val, 0);
                    gl.glVertex3fv(vdata.vert[idata.ind[j]].coord.val, 0);
                }
                gl.glEnd();
                gl.glPopMatrix();
            }
            gl.glEndList();
        }
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="DrawDUMY - draws all control points">
    public void drawDUMY(GL2 gl, float dumysize) {
        if (scen != null) {
            LinkedList l = scen.getChildrenType(sectDUMY.class);
            gl.glNewList(2, gl.GL_COMPILE); //DUMY LIST
            for (int i = 0; l != null && i < l.size(); i++) {
                sectDUMY d = (sectDUMY) l.get(i);

                
                gl.glColor4f(0.5f, 0.35f, 0.2f, 0.3f);

                gl.glPushMatrix();

                transformMatrix(d, gl);

                if((sect)d == hlobject)
                {
                    gl.glColor4f(0.9f, 0.1f, 0.0f, 0.3f);
                    drawConeRobot(gl, dumysize*3);
                }
                else
                    drawConeRobot(gl, dumysize);

                gl.glPopMatrix();
            }
            gl.glEndList();
        }
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="DrawLITE - draws all light sources">
    public void drawLITE(GL2 gl, float litesize) {
        if (scen != null) {
            LinkedList l = scen.getChildrenType(sectLITE.class);
            gl.glNewList(3, gl.GL_COMPILE); //LITE LIST

            //float mat_diff[] = {1.0f, 1.0f, 1.0f, 1};
            //gl.glMaterialfv(GL.GL_FRONT, gl.GL_AMBIENT_AND_DIFFUSE, mat_diff, 0);

            for (int i = 0; l != null && i < l.size(); i++) {
                sectLITE lt = (sectLITE) l.get(i);
                LITEdata ltdata=(LITEdata)lt.getSectionData();
                
                

                gl.glColorMaterial(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT_AND_DIFFUSE);
                gl.glColor4f(ltdata.lightColor.val[0] ,
                            ltdata.lightColor.val[1],
                            ltdata.lightColor.val[2] , 0.3f);

                gl.glPushMatrix();

                transformMatrix(lt, gl);
                
                if(lt.getChildrenTypeCount(sectSPOT.class) > 0 )
                {       
                    SPOTdata spdata = (SPOTdata) lt.getChildren().get(0).getSectionData();
                    if ((sect) lt == hlobject) {
                        gl.glColor4f(ltdata.lightColor.val[0] * 1.3f,
                                ltdata.lightColor.val[1] * 1.3f,
                                ltdata.lightColor.val[2] * 1.3f, 0.8f);
                        drawSpotLight(gl, litesize * 1.7f, spdata.spotCutoff, spdata.coneLen);
                    } else
                            drawSpotLight(gl, litesize,spdata.spotCutoff,spdata.coneLen);
                }
                else if(lt.getChildrenTypeCount(sectPONT.class) > 0 )
                {
                    PONTdata ptdata = (PONTdata) lt.getChildren().get(0).getSectionData();
                    if ((sect) lt == hlobject) {
                        gl.glColor4f(ltdata.lightColor.val[0] * 1.3f,
                                ltdata.lightColor.val[1] * 1.3f,
                                ltdata.lightColor.val[2] * 1.3f, 0.8f);
                        drawPontLamp(gl, litesize * 1.7f, ptdata.maxDist);
                    } else
                        drawPontLamp(gl, litesize * 1.7f, ptdata.maxDist);
                }



                gl.glPopMatrix();
            }
            gl.glEndList();
        }
    }// </editor-fold>


    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    @Override
    public void dispose(GLAutoDrawable glad) {
        
    }

}
