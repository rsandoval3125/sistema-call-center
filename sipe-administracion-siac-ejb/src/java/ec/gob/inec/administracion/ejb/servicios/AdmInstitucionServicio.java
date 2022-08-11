/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.administracion.ejb.facade.AdmInstitucionFacade;
import java.util.List;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class AdmInstitucionServicio implements AdmInstitucionServicioRemote, AdmInstitucionServicioLocal {

    @EJB
    private AdmInstitucionFacade admInstitucionFacade;
    private String ENTIDAD_AdmInstitucion = "AdmInstitucion";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearInstitucion(AdmInstitucion admInstitucion) throws Exception {
        admInstitucionFacade.crear(admInstitucion);
        return "se ha creado el AdmInstitucion";
    }

    @Override
    public String editarInstitucion(AdmInstitucion admInstitucion) throws Exception {
        admInstitucionFacade.editar(admInstitucion);
        return "se ha modificado el AdmInstitucion";
    }

    @Override
    public String eliminarInstitucion(AdmInstitucion admInstitucion) throws Exception {
        admInstitucionFacade.eliminar(admInstitucion);
        return "se ha eliminado el AdmInstitucion";
    }

    @Override
    public List<AdmInstitucion> listarTodo() throws Exception {
        return admInstitucionFacade.listarOrdenada(ENTIDAD_AdmInstitucion, "idInstitucion", "asc");
    }

    @Override
    public boolean validarCorreo(String correo) throws Exception {
        try {
            final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            return pattern.matcher(correo).matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public boolean validarURL(String url) throws Exception {
        try {
            //final String EMAIL_PATTERN = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";
           final String EMAIL_PATTERN =  "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            return pattern.matcher(url).matches();
        } catch (RuntimeException e) {
            return false;
        }
    }
    
    @Override
    public List<AdmInstitucion> listarTodosActivos() throws Exception {
        return admInstitucionFacade.listarTodosActivos();
    }

}
