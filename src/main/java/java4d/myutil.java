package java4d;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;




// <editor-fold defaultstate="collapsed" desc="myutil Class">
public abstract class myutil {

    public final static Rectangle screen=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
    static String lastpathload=null;
    static String lastpathsave=null;
    public static int getInt(byte data[], int offset){
	int ch1 = data[offset]   & 0xFF;
	int ch2 = data[offset+1] & 0xFF;
	int ch3 = data[offset+2] & 0xFF;
	int ch4 = data[offset+3] & 0xFF;
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    }

    public  static void putInt(int val, byte data[], int offset) {
	data[offset]   = (byte)((val >>>  0) & 0xFF);
        data[offset+1] = (byte)((val >>>  8) & 0xFF);
	data[offset+2] = (byte)((val >>> 16) & 0xFF);
	data[offset+3] = (byte)((val >>> 24) & 0xFF);
    }

    public static int getShort(byte data[], int offset){
	int ch1 = data[offset]   &  0xFF;
	int ch2 = data[offset+1] & 0xFF;
        return (ch2 << 8) + (ch1 << 0);
    }

    public  static void putShort(int val, byte data[], int offset) {
	data[offset]   = (byte)((val >>>  0) & 0xFF);
        data[offset+1] = (byte)((val >>>  8) & 0xFF);
    }

    public static void putFloat(float val, byte data[], int offset) {
	putInt(Float.floatToIntBits(val), data, offset);
    }

    public static float getFloat(byte data[], int offset) {
        return Float.intBitsToFloat(getInt(data, offset));
    }
    public static long getLong(byte data[], int offset) {
	return ((long)(getInt(data,offset + 4)) << 32) + (getInt(data,offset) & 0xFFFFFFFFL);
    }
    public static void putLong(long val, byte data[], int offset) {
        data[offset]   = (byte)((val >>>  0) & 0xFF);
        data[offset+1] = (byte)((val >>>  8) & 0xFF);
        data[offset+2] = (byte)((val >>>  16) & 0xFF);
	data[offset+3] = (byte)((val >>>  24) & 0xFF);
        data[offset+4] = (byte)((val >>>  32) & 0xFF);
        data[offset+5] = (byte)((val >>>  40) & 0xFF);
        data[offset+6] = (byte)((val >>>  48) & 0xFF);
	data[offset+7] = (byte)((val >>>  56) & 0xFF);
    }

    public static File fileOpen(Frame parent,String typename, String ext ) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                typename, ext);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(lastpathload!=null ? new File(lastpathload) : null);
        int returnVal = chooser.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            lastpathload=chooser.getSelectedFile().getPath();
            return chooser.getSelectedFile();
        }
        return null;

    }



    public static File fileSave(Frame parent,String typename, String ext) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                typename, ext);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(lastpathsave!=null ? new File(lastpathsave) : null);
        int returnVal = chooser.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            String fn=chooser.getSelectedFile().getPath() ;
            if(!fn.toUpperCase().endsWith("." + ext.toUpperCase()))
            {
                fn+="." + ext;
                chooser.setSelectedFile(new File(fn));
            }
            lastpathsave=chooser.getSelectedFile().getPath();
            return chooser.getSelectedFile();
        }
        return null;

    }

    public static void packPanel(JPanel p,int width)
    {
        int minh=0;
        for(Component c : p.getComponents())
        {
            minh+=c.getMinimumSize().getHeight();
        }

        p.setMinimumSize(new Dimension(width, minh) ) ;
        p.setMaximumSize(new Dimension(width, minh) ) ;

    }

    public static float[] mat4x4Mult(float a[],float b[])
    {
        float ret[]=new float[16];
        float ij;
        for(int i=0;i<16;i++)
        {
            ij=0;
            for(int j=0;j<4;j++)
                ij+=a[i-i%4  + j]*b[i%4 + j*4];

            ret[i]=ij;
        }
        return ret;
    }


}// </editor-fold>

