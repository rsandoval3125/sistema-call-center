package ec.gob.inec.presentacion.clases.reportes.spss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class ValueLabels {

    Hashtable data = new Hashtable();
    List values = new ArrayList();
    private boolean simpleLabel = true;

    public void putLabel(double paramDouble, String paramString) {
        if (this.data.containsKey(new Double(paramDouble))) {
            throw new RuntimeException("label with the same value has been already added !");
        }
        this.data.put(new Double(paramDouble), paramString);
        this.values.add(new Double(paramDouble));
    }

    public void putLabel(String paramString1, String paramString2) {
        this.simpleLabel = false;
        if (this.data.containsKey(paramString1)) {
            throw new RuntimeException("label with the same value has been already added !");
        }
        this.data.put(paramString1, paramString2);
        this.values.add(paramString1);
    }

    public Hashtable getValueLabels() {
        return this.data;
    }

    public List getValues() {
        Collections.sort(this.values);
        return this.values;
    }

    public boolean isSimpleLabel() {
        return this.simpleLabel;
    }
}


/* Location:              C:\Users\jaraujo\Documents\NetbeansMios\exportSpss\lib\spssw-1.84.jar!\com\pmstation\spss\ValueLabels.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */
