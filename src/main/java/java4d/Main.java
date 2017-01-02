/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java4d;

/**
 *
 * @author Pierantonio Cangianiello
 */
public class Main {

    public static void main(String[] args) {
        JarClassLoader jcl = new JarClassLoader();
        try {
            jcl.invokeMain("java4d.Form1", args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    } 
    
}
