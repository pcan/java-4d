/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.sections;

/**
 *
 * @author Pierantonio
 */
public class sectFACE extends sectINDI{

    public sectFACE(sect parent)
    {
        super(parent);
    }

    @Override
    public String getTipDetails()
    {
        return new String("FACE: " + getIndCount()/3 + " face(s)");
    }


}
