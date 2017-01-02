/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.datatypes;

/**
 *
 * @author Pierantonio
 */
public class vector3f extends vectorXf {


    public vector3f() {
        super(3);
    }

    public vector3f(byte[] source, int offset) {
        this();
        getFrom(source, offset);
    }

    public vector3f clone()
    {
        vector3f copy=new vector3f();
            copy.val=val.clone();
        return copy;
    }
}