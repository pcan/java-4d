/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pakfile;

/**
 *
 * @author Pierantonio
 */
public abstract class pakFileListener
{
        abstract public void writeStarted();
        abstract public void writeStopped();
        abstract public void writeIsAdvancing(long val);
        abstract public void readStarted();
        abstract public void readStopped();
        abstract public void readIsAdvancing(long val);
}
