/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package java4d.datatypes;

/**
 *
 * @author Pierantonio
 */
public class vector2f extends vectorXf {


    public vector2f() {
        super(2);
    }

    public vector2f(byte[] source, int offset) {
        this();
        getFrom(source, offset);
    }

    @Override
    public vector2f clone()
    {
        vector2f copy=new vector2f();
            copy.val=val.clone();
        return copy;
    }

}