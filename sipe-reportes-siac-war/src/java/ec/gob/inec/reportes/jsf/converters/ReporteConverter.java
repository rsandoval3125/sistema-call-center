/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.jsf.converters;

import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import javax.persistence.Id;
import java.util.logging.Logger;

/**
 *
 * @author jaraujo
 */

@ManagedBean(name = "reporteConverter")
@ViewScoped
public class ReporteConverter implements Converter{
    private static final Logger LOGGER = Logger.getLogger(ReporteConverter.class.getName());
    
 @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {

        if (arg2.trim().equals("")) {
            return null;
        } else {
            return new RepoReporte((Integer.parseInt(arg2)));
        }

    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((RepoReporte) arg2).getIdReporte());
        }
    } 
}
