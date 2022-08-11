package ec.gob.inec.presentacion.clases.reportes.spss;

import java.io.IOException;
import java.io.OutputStream;

public class SPSSBuffer
{
  public static final int COMPRESS_NOT_COMPRESSED = 253;
  private int max_length;
  private byte[] compressed;
  private byte[] data;
  private int cmp_length;
  private int dat_length;
  
  private SPSSBuffer() {}
  
  public SPSSBuffer(int paramInt)
  {
    this.max_length = (paramInt < 16 ? 16 : paramInt);
    this.compressed = new byte[paramInt];
    this.data = new byte[paramInt * 8];
    this.cmp_length = 0;
    this.dat_length = 0;
  }
  
  public void clear()
  {
    this.cmp_length = 0;
    this.dat_length = 0;
  }
  
  public void addCompressed(byte paramByte)
    throws BufferOverflowException
  {
    if (this.cmp_length >= this.max_length) {
      throw new BufferOverflowException("compressed oveflow");
    }
    this.compressed[this.cmp_length] = paramByte;
    this.cmp_length += 1;
  }
  
  public void addCompressed(byte[] paramArrayOfByte)
    throws BufferOverflowException
  {
    for (int i = 0; i < paramArrayOfByte.length; i++) {
      addCompressed(paramArrayOfByte[i]);
    }
  }
  
  public void addData(byte paramByte)
    throws BufferOverflowException
  {
    if (this.dat_length >= this.max_length * 8) {
      throw new BufferOverflowException("data overflow");
    }
    this.data[this.dat_length] = paramByte;
    this.dat_length += 1;
  }
  
  public void addData(byte[] paramArrayOfByte)
    throws BufferOverflowException
  {
    for (int i = 0; i < paramArrayOfByte.length; i++) {
      addData(paramArrayOfByte[i]);
    }
  }
  
  private void delFromCompressed(int paramInt)
  {
    if (paramInt <= 0) {
      return;
    }
    if (paramInt >= this.cmp_length)
    {
      this.cmp_length = 0;
      return;
    }
    for (int i = 0; i < this.cmp_length - paramInt; i++) {
      this.compressed[i] = this.compressed[(i + paramInt)];
    }
    this.cmp_length -= paramInt;
  }
  
  private void delFromData(int paramInt)
  {
    if (paramInt <= 0) {
      return;
    }
    if (paramInt >= this.dat_length)
    {
      this.dat_length = 0;
      return;
    }
    for (int i = 0; i < this.dat_length - paramInt; i++) {
      this.data[i] = this.data[(i + paramInt)];
    }
    this.dat_length -= paramInt;
  }
  
  private void delFromBuffer()
  {
    if (this.cmp_length <= 0) {
      return;
    }
    if (this.compressed[0] == -3) {
      delFromData(8);
    }
    delFromCompressed(1);
  }
  
  private void delFromBuffer(int paramInt)
  {
    if (paramInt <= 0) {
      return;
    }
    for (int i = 0; i < paramInt; i++) {
      delFromBuffer();
    }
  }
  
  public int getLength()
  {
    return this.cmp_length;
  }
  
  public void flushBuffer(OutputStream paramOutputStream)
    throws IOException
  {
    while (this.cmp_length >= 8)
    {
      paramOutputStream.write(this.compressed, 0, 8);
      int i = 0;
      for (int j = 0; j < 8; j++) {
        if (this.compressed[j] == -3)
        {
          paramOutputStream.write(this.data, i, 8);
          i += 8;
        }
      }
      delFromBuffer(8);
    }
    paramOutputStream.flush();
  }
}


/* Location:              C:\Users\jaraujo\Documents\NetbeansMios\exportSpss\lib\spssw-1.84.jar!\com\pmstation\spss\SPSSBuffer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */