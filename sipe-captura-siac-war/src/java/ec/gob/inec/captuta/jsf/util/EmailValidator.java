
package ec.gob.inec.captuta.jsf.util;

import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;
 
/**
 * Custom JSF Validator for Email input
 */
@FacesValidator("custom.emailValidator")
public class EmailValidator implements Validator, ClientValidator {
 
    private Pattern pattern;
  
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
         
        if(!esValido(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe ser una dirección electrónica válida", ""));
        }
    }
 
    public Map<String, Object> getMetadata() {
        return null;
    }
 
    public String getValidatorId() {
        return "custom.emailValidator";
    }
     
    public static boolean esValido(String s){
    	return Pattern.compile(EMAIL_PATTERN).matcher(s).matches();
    }
}
