/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.converters;

import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author dgarcia
 */
@ManagedBean(name = "seccionConverter")
@ViewScoped
public class SeccionConverter extends BaseControlador implements Converter {

       
        @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {

        if (arg2.trim().equals("") || arg2.equals("null")) {
            return null;
        } else {
            return new MetSeccion(Integer.valueOf(arg2));
        }

    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((MetSeccion) arg2).getIdSeccion());
        }
    }
    
    
    @SuppressWarnings("unchecked")
    private MetSeccion getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<MetSeccion> dualList;
        try {
            dualList = (DualListModel<MetSeccion>) ((PickList) component).getValue();
            MetSeccion acc = getObjectFromList(dualList.getSource(), Integer.valueOf(value));
            if (acc == null) {
                acc = getObjectFromList(dualList.getTarget(), Integer.valueOf(value));
            }
            return acc;
        } catch (ClassCastException cce) {
            throw new ConverterException();
        } catch (NumberFormatException nfe) {
            throw new ConverterException();
        }
    }

    private MetSeccion getObjectFromList(final List<?> list, final Integer identifier) {
        for (final Object object : list) {
            final MetSeccion per = (MetSeccion) object;
            if (per.getIdSeccion().equals(identifier)) {
                return per;
            }
        }
        return null;
    }
}
