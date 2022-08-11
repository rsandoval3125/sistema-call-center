
package ec.gob.inec.captuta.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("conversorAMayusculas")
public class ConversorAMayusculas implements Converter {

    @Override
    public String getAsString(FacesContext context,
                              UIComponent component,
                              Object value) {

        return (String) ((value != null) ? value.toString().toUpperCase().trim() : null);
    }

    @Override
    public Object getAsObject(FacesContext context,
                              UIComponent component,
                              String value) {

        return (value != null) ? value.toUpperCase().trim() : null;
    }
}
