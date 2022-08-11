/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.converters;

import ec.gob.inec.seguridad.ejb.entidades.SegPermiso;
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
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@FacesConverter("segPermisoConverter")
public class SegPermisoConverter implements Converter {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(SegPermisoConverter.class.getName());
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
            return new SegPermiso(Integer.valueOf(arg2));
        }

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((SegPermiso) arg2).getIdPermiso());
        }
    }

    @SuppressWarnings("unchecked")
    private SegPermiso getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<SegPermiso> dualList;
        try {
            dualList = (DualListModel<SegPermiso>) ((PickList) component).getValue();
            SegPermiso estructuraD = getObjectFromList(dualList.getSource(), Integer.valueOf(value));
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

    private SegPermiso getObjectFromList(final List<?> list, final Integer identifier) {
        for (final Object object : list) {
            final SegPermiso estructuraDatos = (SegPermiso) object;
            if (estructuraDatos.getIdPermiso().equals(identifier)) {
                return estructuraDatos;
            }
        }
        return null;
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
