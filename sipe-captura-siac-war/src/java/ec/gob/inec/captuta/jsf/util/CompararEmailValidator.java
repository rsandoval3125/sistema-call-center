
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

@ManagedBean(name = "compararEmailValidator")
@RequestScoped
public class CompararEmailValidator implements Validator {
    
    String email2;
  
    @Override
      public void validate(FacesContext context, UIComponent component,
                    Object value) throws ValidatorException {
          
       String email1 = (String) component.getAttributes().get("idCorreo1");
       email2 = (String) value;
       
        if((email1 != null && !email1.isEmpty())){
            if((email2 != null && !email2.isEmpty())){
                if(!email1.equals(email2)){
                     FacesMessage fm = new FacesMessage("El correo electronico no coinciden. Intentelo de nuevo");
                     fm.setSeverity(SEVERITY_ERROR);
                     throw new ValidatorException(fm);
                }
           }else{
                 FacesMessage fm = new FacesMessage("El correo electronico no coinciden. Intentelo de nuevo");
                 fm.setSeverity(SEVERITY_ERROR);
                 throw new ValidatorException(fm);
            }
        }else{
            FacesMessage fm = new FacesMessage("El correo electronico no coinciden. Intentelo de nuevo");
            fm.setSeverity(SEVERITY_ERROR);
            throw new ValidatorException(fm);
        }
       
       
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }
    
}
