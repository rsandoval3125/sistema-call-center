package ec.gob.inec.captura.jsf.seguridad;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author inec
 *
 */
public class ViewExpiredExceptionExceptionHandlerFactory extends ExceptionHandlerFactory {
 
    private ExceptionHandlerFactory parent;
 
    /**
     *
     * @param parent
     */
    public ViewExpiredExceptionExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }
 
    /**
     *
     * @return
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new ViewExpiredExceptionExceptionHandler(result);
 
        return result;
    }
 
 
}

