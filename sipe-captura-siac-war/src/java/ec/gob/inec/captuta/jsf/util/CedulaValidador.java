/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captuta.jsf.util;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static org.apache.commons.lang.StringUtils.isBlank;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean(name = "cedulaValidador")
@RequestScoped
public class CedulaValidador implements Validator {

	String cedula;

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		cedula = (String) value;

		if (isBlank(cedula)) {
			return;
		}
                
//                String nacionalidad = (String) component.getAttributes().get("nacionalidad");
//                System.out.println("nacionalidad "+nacionalidad);
//                
//                if (!ValidadorUtil.validaCedula(cedula)) {
//                    if(nacionalidad != null && nacionalidad.compareTo("ECUATORIANA")==0){
//                        FacesMessage fm = new FacesMessage("Cédula Incorrecta, Ingrese una cédula correcta");
//                        fm.setSeverity(SEVERITY_ERROR);
//                        throw new ValidatorException(fm);
//                    }
//                   
//                }else if(nacionalidad != null && nacionalidad.compareTo("EXTRANJERO")==0){
//                        FacesMessage fm = new FacesMessage("Esta tratando de Ingresar una cedula como Pasaporte");
//			fm.setSeverity(SEVERITY_ERROR);
//			throw new ValidatorException(fm);
//                }

		if (!ValidadorUtil.validaCedula(cedula)) {
			FacesMessage fm = new FacesMessage("Cédula Incorrecta, Ingrese una cédula correcta");
                        fm.setSeverity(SEVERITY_ERROR);
                        throw new ValidatorException(fm);
		}
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
}
