/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.converter.administracion;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@FacesConverter("personaConverter")
public class PersonaConverter implements Converter {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(PersonaConverter.class.getName());
    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    @Override
    public Object getAsObject(FacesContext context, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return new AdmPersonal(Integer.valueOf(value));
            } catch (NumberFormatException ex) {
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return null;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null && object != "") {
            return String.valueOf(((AdmPersonal) object).getIdPersonal());
        } else {
            return "";
        }
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
