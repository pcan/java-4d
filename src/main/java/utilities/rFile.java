/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;




public class rFile extends RandomAccessFile
{
    public rFile(String name, String mode) throws FileNotFoundException
    {
        super(name, mode);
    }
    public rFile(File name, String mode) throws FileNotFoundException
    {
        super(name, mode);
    }

    public final int readIntLE() throws IOException {
	int ch1 = this.read();
	int ch2 = this.read();
	int ch3 = this.read();
	int ch4 = this.read();
	if ((ch1 | ch2 | ch3 | ch4) < 0)
	    throw new EOFException();
	return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    }

    public final short readShortLE() throws IOException {
	int ch1 = this.read();
	int ch2 = this.read();
	if ((ch1 | ch2) < 0)
	    throw new EOFException();
	return (short)((ch2 << 8) + (ch1 << 0));
    }

    public final int readUnsignedShortLE() throws IOException {
	int ch1 = this.read();
	int ch2 = this.read();
	if ((ch1 | ch2) < 0)
	    throw new EOFException();
	return (ch2 << 8) + (ch1 << 0);
    }

    public final long readLongLE() throws IOException {
	return ((readInt() & 0xFFFFFFFFL + (long)(readInt()) << 32));
    }

    public final float readFloatLE() throws IOException {
	return Float.intBitsToFloat(readIntLE());
    }

    public final void writeIntLE(int v) throws IOException {
	write((v >>>  0) & 0xFF);
        write((v >>>  8) & 0xFF);
	write((v >>> 16) & 0xFF);
	write((v >>> 24) & 0xFF);
    }

    public final void writeFloatLE(float v) throws IOException {
	writeIntLE(Float.floatToIntBits(v));
    }

    public final void moveCursor(long steps) throws IOException
    {
        this.seek(steps + (this.getFilePointer()));
    }
}
