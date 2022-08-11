/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.converters;

import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@FacesConverter("aplicacionConverter")
public class AplicacionConverter implements Converter {
    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">

    private static final Logger LOGGER = Logger.getLogger(AplicacionConverter.class.getName());
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
            return new SegAplicacion(Integer.valueOf(arg2));
        }

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((SegAplicacion) arg2).getIdApl());
        }
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
