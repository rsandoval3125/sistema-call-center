/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.converters;

import ec.gob.inec.seguridad.clases.Tabla;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@FacesConverter("tablaConverter")
public class TablaConverter implements Converter {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(TablaConverter.class.getName());
    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {

        if (arg2.trim().equals("") || arg2.equals("null")) {
            return null;
        } else {           
            String[] nombreCompletoTabla = arg2.split("\\.");            
            return new Tabla(nombreCompletoTabla[0], nombreCompletoTabla[1]);
        }

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            Tabla t = (Tabla) arg2;
            return t.toString();
        }
    }

    @SuppressWarnings("unchecked")
    private Tabla getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Tabla> dualList;
        try {
            dualList = (DualListModel<Tabla>) ((PickList) component).getValue();
            Tabla estructuraD = getObjectFromList(dualList.getSource(), value);
            if (estructuraD == null) {
                estructuraD = getObjectFromList(dualList.getTarget(), value);
            }
            return estructuraD;
        } catch (ClassCastException cce) {
            throw new ConverterException();
        } catch (NumberFormatException nfe) {
            throw new ConverterException();
        }
    }

    private Tabla getObjectFromList(final List<?> list, final String identifier) {
        for (final Object object : list) {
            final Tabla estructuraDatos = (Tabla) object;
            if (estructuraDatos.getTableName().equals(identifier)) {
                return estructuraDatos;
            }
        }
        return null;
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
