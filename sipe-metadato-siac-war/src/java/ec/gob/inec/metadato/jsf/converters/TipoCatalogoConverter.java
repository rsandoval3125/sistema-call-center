/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.converters;

import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author mchasiguasin
 */
@FacesConverter("tipoCatalogoConverter")
public class TipoCatalogoConverter implements Converter {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(TipoCatalogoConverter.class.getName());
    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return new MetTipoCatalogo(Integer.parseInt(value));
            } catch (NumberFormatException e) {
            } catch (Exception ex) {
                Logger.getLogger(TipoCatalogoConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null && object != "") {
            return String.valueOf(((MetTipoCatalogo) object).getIdTipoCatalogo());
            //return String.valueOf( object);
        } else {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">

    //</editor-fold>
}
