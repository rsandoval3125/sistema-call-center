/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.converters;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
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
@FacesConverter("operacionEstadisticaConverter")
public class OperacionEstadisticaConverter implements Converter {
    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">

    private static final Logger LOGGER = Logger.getLogger(OperacionEstadisticaConverter.class.getName());
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
            return new AdmOperacionEstadistica(Integer.valueOf(arg2));
        }

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((AdmOperacionEstadistica) arg2).getIdOpeEst());
        }
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
