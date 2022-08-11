/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captuta.jsf.util;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static org.apache.commons.lang.StringUtils.isBlank;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean(name = "respuestaValidador")
@RequestScoped
public class RespuestaValidador implements Validator {

    String respuesta;
    String estado;

    @Override
    public void validate(FacesContext context, UIComponent component,
                    Object value) throws ValidatorException {
        
        MetCatalogo tipoEstadoActividadSelect = (MetCatalogo) component.getAttributes().get("idEstado");
        respuesta = (String) value;
        estado = "";
        if(tipoEstadoActividadSelect != null)
             estado = tipoEstadoActividadSelect.getNombre();
        
        if((estado != null && estado.compareTo("CERRADO")==0) && ( respuesta == null || (respuesta != null && (respuesta.compareTo("")==0 || respuesta.isEmpty())))){
                FacesMessage fm = new FacesMessage("ESTADO ESTA CERRADO, POR FAVOR INGRESE UNA RESPUESTA");
                fm.setSeverity(SEVERITY_ERROR);
                throw new ValidatorException(fm);
        }
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

	
}
