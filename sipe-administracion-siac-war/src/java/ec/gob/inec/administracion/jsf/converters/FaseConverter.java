/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.converters;


import ec.gob.inec.proceso.ejb.entidades.ProFase;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author vespinoza
 */
@FacesConverter("faseConverter")
public class FaseConverter implements Converter {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(FaseConverter.class.getName());
    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
      //  System.out.println("getAsObject " + value);
        if (value.equals("Seleccione uno")) {
            return null;
        } else if (!value.equals("null") && value.trim().length() > 0) {
            try {
                return new ProFase(Integer.parseInt(value));
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
      //  System.out.println("getAsString " + object);
        if (object != null && object != "") {
            return String.valueOf(((ProFase) object).getIdFase());
            //return String.valueOf( object);
        } else {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
