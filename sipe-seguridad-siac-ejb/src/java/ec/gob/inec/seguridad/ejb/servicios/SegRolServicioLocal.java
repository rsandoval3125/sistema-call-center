/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface SegRolServicioLocal {

    public String crearRol(SegRol segRol) throws Exception;

    public String editarRol(SegRol segRol) throws Exception;

    public String eliminarRol(SegRol segRol) throws Exception;

    public List<SegRol> listarTodo() throws Exception;

    public List<SegRol> listarRolXUsuarioContrasenia(String nombre, String contrasenia) throws Exception;

    public List<SegRol> listarRolesNotInFase(MetOperativo codOper) throws Exception;

    public SegRol buscarXId(Integer idRol) throws Exception;

    public List<SegRol> listarRolesDeUsuarioAsignados(Integer codUsuario) throws Exception;

    public List<SegRol> listarRolesDeUsuarioNoAsignados(List<SegRol> roles) throws Exception;

    // public List<SegRol> listarRolesPorAplicacion(Integer codApl) throws Exception;
    public List<SegRol> listarRolesActivos() throws Exception;

    // public List<SegRol> listarRolesDeUsuarioNoAsignadosPorAplicacion(List<SegRol> roles, Integer codApl) throws Exception;
    public List<SegRol> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public SegRol buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    
    public Integer tieneCargasPendientes(Integer idRol) throws Exception;
}
