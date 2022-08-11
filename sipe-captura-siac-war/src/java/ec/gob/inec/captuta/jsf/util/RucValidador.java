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

@ManagedBean(name = "rucValidador")
@RequestScoped
public class RucValidador implements Validator {

	String ruc;

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		ruc = (String) value;

		if (isBlank(ruc)) {
			return;
		}
                
		if (!ValidadorUtil.validaRUC(ruc)) {
			FacesMessage fm = new FacesMessage("Ruc Incorrecta, Ingrese una ruc correcta");
                        fm.setSeverity(SEVERITY_ERROR);
                        throw new ValidatorException(fm);
		}
	}

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

	
}
