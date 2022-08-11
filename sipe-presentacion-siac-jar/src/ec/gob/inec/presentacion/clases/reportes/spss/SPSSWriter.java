package ec.gob.inec.presentacion.clases.reportes.spss;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class SPSSWriter
        implements DataConstants {

    private SPSSWriterImpl w;

    private SPSSWriter() {
    }

    public SPSSWriter(String paramString1, String paramString2)
            throws NullPointerException, IOException {
        this.w = new SPSSWriterImpl(paramString1, paramString2);
    }

    public SPSSWriter(File paramFile, String paramString)
            throws NullPointerException, IOException {
        this.w = new SPSSWriterImpl(paramFile, paramString);
    }

    public SPSSWriter(OutputStream paramOutputStream, String paramString)
            throws NullPointerException {
        this.w = new SPSSWriterImpl(paramOutputStream, paramString);
    }

    public OutputStream getOut() {
        return this.w.getOut();
    }

    public void setOut(OutputStream paramOutputStream)
            throws NullPointerException {
        this.w.setOut(paramOutputStream);
    }

    /*public void setOut(OutputStream paramOutputStream, ServletOutputStreamImplClass paramOutputStreamImpl)
            throws NullPointerException {
        this.w.setOut(paramOutputStream, paramOutputStreamImpl);
    }*/
    public String getCharset() {
        return this.w.getCharset();
    }

    public void setCharset(String paramString) {
        this.w.setCharset(paramString);
    }

    /**
     * No disponible
     * @throws java.io.IOException
     * @deprecated
     */
    public void addDictionarySection()
            throws IOException {
        this.w.addDictionarySection();
    }

    public void addDictionarySection(int paramInt)
            throws IOException {
        this.w.addDictionarySection(paramInt);
    }

    public void addDictionarySection(String paramString1, String paramString2)
            throws IOException {
        this.w.addDictionarySection(paramString1, paramString2);
    }

    public void addDictionarySection(String paramString1, int paramInt1, int paramInt2, String paramString2, boolean paramBoolean)
            throws IOException {
        this.w.addDictionarySection(paramString1, paramInt1, paramInt2, paramString2, paramBoolean);
    }

    public void addDictionarySection(String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString2, Date paramDate, boolean paramBoolean)
            throws IOException {
        this.w.addDictionarySection(paramString1, paramInt1, paramInt2, paramInt3, paramInt4, paramString2, paramDate, paramBoolean);
    }

    public void addNumericVar(String paramString1, int paramInt1, int paramInt2, String paramString2)
            throws IOException {
        this.w.addNumericVar(paramString1, paramInt1, paramInt2, paramString2);
    }

    public void addNumericVar(String paramString1, int paramInt1, int paramInt2, String paramString2, MissingValue paramMissingValue)
            throws IOException {
        this.w.addNumericVar(paramString1, paramInt1, paramInt2, paramString2, paramMissingValue);
    }

    public void addNumericVar(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, int paramInt4, int paramInt5)
            throws IOException {
        this.w.addNumericVar(paramString1, paramInt1, paramInt2, paramString2, paramInt3, paramInt4, paramInt5);
    }

    public void addNumericVar(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, int paramInt4, int paramInt5, MissingValue paramMissingValue)
            throws IOException {
        this.w.addNumericVar(paramString1, paramInt1, paramInt2, paramString2, paramInt3, paramInt4, paramInt5, paramMissingValue);
    }

    public void addCommaVar(String paramString1, int paramInt1, int paramInt2, String paramString2)
            throws IOException {
        this.w.addCommaVar(paramString1, paramInt1, paramInt2, paramString2);
    }

    public void addCommaVar(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, int paramInt4, int paramInt5)
            throws IOException {
        this.w.addCommaVar(paramString1, paramInt1, paramInt2, paramString2, paramInt3, paramInt4, paramInt5);
    }

    public void addDotVar(String paramString1, int paramInt1, int paramInt2, String paramString2)
            throws IOException {
        this.w.addDotVar(paramString1, paramInt1, paramInt2, paramString2);
    }

    public void addDotVar(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, int paramInt4, int paramInt5)
            throws IOException {
        this.w.addDotVar(paramString1, paramInt1, paramInt2, paramString2, paramInt3, paramInt4, paramInt5);
    }

    public void addDollarVar(String paramString1, int paramInt1, int paramInt2, String paramString2)
            throws IOException {
        this.w.addDollarVar(paramString1, paramInt1, paramInt2, paramString2);
    }

    public void addDollarVar(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, int paramInt4, int paramInt5)
            throws IOException {
        this.w.addDollarVar(paramString1, paramInt1, paramInt2, paramString2, paramInt3, paramInt4, paramInt5);
    }

    public void addStringVar(String paramString1, int paramInt, String paramString2)
            throws IOException {
        this.w.addStringVar(paramString1, paramInt, paramString2);
    }

    public void addStringVar(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, int paramInt4)
            throws IOException {
        this.w.addStringVar(paramString1, paramInt1, paramString2, paramInt2, paramInt3, paramInt4);
    }

    public void addStringVar(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, int paramInt4, MissingValue paramMissingValue)
            throws IOException {
        this.w.addStringVar(paramString1, paramInt1, paramString2, paramInt2, paramInt3, paramInt4, paramMissingValue);
    }

    public void addDateVar(String paramString1, int paramInt, String paramString2)
            throws IOException {
        this.w.addDateVar(paramString1, paramInt, paramString2);
    }

    public void addDateVar(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, int paramInt4, MissingValue paramMissingValue)
            throws IOException {
        this.w.addDateVar(paramString1, paramInt1, paramString2, paramInt2, paramInt3, paramInt4, paramMissingValue);
    }

    public void addDateVar(String paramString1, String paramString2, String paramString3, MissingValue paramMissingValue)
            throws IOException {
        this.w.addDateVar(paramString1, paramString2, paramString3, -1, -1, -1, paramMissingValue);
    }

    public void addDateVar(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3, MissingValue paramMissingValue)
            throws IOException {
        this.w.addDateVar(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramInt3, paramMissingValue);
    }

    public void addDataSection()
            throws IOException {
        this.w.addDataSection();
    }

    public void addData(Long paramLong)
            throws IOException {
        this.w.addData(paramLong);
    }

    public void addData(Double paramDouble)
            throws IOException {
        this.w.addData(paramDouble);
    }

    public void addData(String paramString)
            throws IOException {
        this.w.addData(paramString);
    }

    public void addData(Date paramDate)
            throws IOException {
        this.w.addData(paramDate);
    }

    public void finishCurrentLine()
            throws IOException {
        this.w.finishCurrentLine();
    }

    public void addFinishSection()
            throws IOException {
        this.w.addFinishSection();
    }

    public void addValueLabels(int paramInt, ValueLabels paramValueLabels)
            throws IOException {
        this.w.addValueLabels(paramInt, paramValueLabels);
    }

    public void setCalculateNumberOfCases(boolean paramBoolean) {
        this.w.setCalculateNumberOfCases(paramBoolean);
    }
}


/* Location:              C:\Users\jaraujo\Documents\NetbeansMios\exportSpss\lib\spssw-1.84.jar!\com\pmstation\spss\SPSSWriter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */
