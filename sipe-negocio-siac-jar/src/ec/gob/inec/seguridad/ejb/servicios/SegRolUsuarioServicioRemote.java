/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface SegRolUsuarioServicioRemote {

    public String crearRolUsuario(SegRolUsuario segRolUsuario) throws Exception;

    public String editarRolUsuario(SegRolUsuario segRolUsuario) throws Exception;

    public String eliminarRolUsuario(SegRolUsuario segRolUsuario) throws Exception;

    public List<SegRolUsuario> listarTodo() throws Exception;

    public SegRolUsuario buscarRolUsuarioFase(AdmPersonal vcodPersonal, SegRol vcodRol) throws Exception;

    public boolean existeUsuarioRol(Integer codUsuario, Integer codRol) throws Exception;

    public SegRolUsuario buscarPorUsuarioRol(Integer codUsuario, Integer codRol) throws Exception;
    
    public List<SegRolUsuario> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
        
    public SegRolUsuario buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
}
