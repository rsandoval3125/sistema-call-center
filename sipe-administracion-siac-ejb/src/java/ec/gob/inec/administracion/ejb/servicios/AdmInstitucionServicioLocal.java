/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface AdmInstitucionServicioLocal {

    public String crearInstitucion(AdmInstitucion admInstitucion) throws Exception;

    public String editarInstitucion(AdmInstitucion admInstitucion) throws Exception;

    public String eliminarInstitucion(AdmInstitucion admInstitucion) throws Exception;

    public List<AdmInstitucion> listarTodo() throws Exception;

    public boolean validarCorreo(String correo) throws Exception;

    public boolean validarURL(String url) throws Exception;

    public List<AdmInstitucion> listarTodosActivos() throws Exception;
}
