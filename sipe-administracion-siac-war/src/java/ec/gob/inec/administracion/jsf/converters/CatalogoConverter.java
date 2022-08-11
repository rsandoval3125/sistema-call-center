/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.converters;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author vespinoza
 */
@ManagedBean(name = "catConverter")
@ViewScoped
public class CatalogoConverter extends BaseControlador implements Converter {
    

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
 
        if (arg2.trim().equals("")|| arg2.equals("null")) {
            return null;
        } else {
            return new MetCatalogo(Integer.valueOf(arg2)); 
        } 
 
    }
 
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((MetCatalogo) arg2).getIdCatalogo());
        }
    }
}
