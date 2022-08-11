/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.converters;

import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author mchasiguasin
 */
@FacesConverter("segRolConverter")
public class SegRolConverter implements Converter {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(SegRolConverter.class.getName());
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
            return new SegRol(Integer.valueOf(arg2));
        }

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((SegRol) arg2).getIdRol());
        }
    }

    @SuppressWarnings("unchecked")
    private SegRol getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<SegRol> dualList;
        try {
            dualList = (DualListModel<SegRol>) ((PickList) component).getValue();
            SegRol estructuraD = getObjectFromList(dualList.getSource(), Integer.valueOf(value));
            if (estructuraD == null) {
                estructuraD = getObjectFromList(dualList.getTarget(), Integer.valueOf(value));
            }
            return estructuraD;
        } catch (ClassCastException cce) {
            throw new ConverterException();
        } catch (NumberFormatException nfe) {
            throw new ConverterException();
        }
    }

    private SegRol getObjectFromList(final List<?> list, final Integer identifier) {
        for (final Object object : list) {
            final SegRol estructuraDatos = (SegRol) object;
            if (estructuraDatos.getIdRol().equals(identifier)) {
                return estructuraDatos;
            }
        }
        return null;
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
