/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegPermiso;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface SegPermisoServicioRemote {

    public String crearPermiso(SegPermiso segPermiso) throws Exception;

    public String editarPermiso(SegPermiso segPermiso) throws Exception;

    public String eliminarPermiso(SegPermiso segPermiso) throws Exception;

    public List<SegPermiso> listarTodo() throws Exception;

    public List<SegPermiso> listarPermisosDeRolAsignados(Integer codRol) throws Exception;

    public List<SegPermiso> listarPermisosDeRolNoAsignados(List<SegPermiso> permisos) throws Exception;

    public List<SegPermiso> listarPermisosActivos() throws Exception;

    public List<SegPermiso> listarPermisosPorPagina(List<SegPagina> paginas) throws Exception;

    public List<SegPermiso> listarPermisosDeRolNoAsignadosPorAplicacion(List<SegPermiso> permisos, Integer idApl) throws Exception;

    public List<SegPermiso> listarPermisosActivosPorAplicacion(Integer idApl) throws Exception;

    public List<SegPermiso> listarPermisosPorUsuarioYPagina(String usuario, Integer idPag) throws Exception;
}
