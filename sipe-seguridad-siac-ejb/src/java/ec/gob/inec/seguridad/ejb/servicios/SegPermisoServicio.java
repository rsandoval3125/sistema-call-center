/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegPermiso;
import ec.gob.inec.seguridad.ejb.facade.SegPermisoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class SegPermisoServicio implements SegPermisoServicioRemote, SegPermisoServicioLocal {

    @EJB
    private SegPermisoFacade segPermisoFacade;
    private String ENTIDAD_segPermiso = "SegPermiso";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearPermiso(SegPermiso segPermiso) throws Exception {
        segPermisoFacade.crear(segPermiso);
        return "se ha creado el Permiso";
    }

    @Override
    public String editarPermiso(SegPermiso segPermiso) throws Exception {
        segPermisoFacade.editar(segPermiso);
        return "se ha modificado el Permiso";
    }

    @Override
    public String eliminarPermiso(SegPermiso segPermiso) throws Exception {
        segPermisoFacade.eliminar(segPermiso);
        return "se ha modificado el Permiso";
    }

    @Override
    public List<SegPermiso> listarTodo() throws Exception {
        return segPermisoFacade.listarOrdenada(ENTIDAD_segPermiso, "idPermiso", "asc");
    }

    @Override
    public List<SegPermiso> listarPermisosDeRolAsignados(Integer codRol) throws Exception {
        return segPermisoFacade.listarPermisosDeRolAsignados(codRol);
    }

    @Override
    public List<SegPermiso> listarPermisosDeRolNoAsignados(List<SegPermiso> permisos) throws Exception {
        return segPermisoFacade.listarPermisosDeRolNoAsignados(permisos);
    }

    @Override
    public List<SegPermiso> listarPermisosActivos() throws Exception {
        return segPermisoFacade.listarPermisosActivos();
    }

    @Override
    public List<SegPermiso> listarPermisosPorPagina(List<SegPagina> paginas) throws Exception {
        return segPermisoFacade.listarPermisosPorPagina(paginas);
    }

    @Override
    public List<SegPermiso> listarPermisosDeRolNoAsignadosPorAplicacion(List<SegPermiso> permisos, Integer idApl) throws Exception {
        return segPermisoFacade.listarPermisosDeRolNoAsignadosPorAplicacion(permisos, idApl);
    }

    @Override
    public List<SegPermiso> listarPermisosActivosPorAplicacion(Integer idApl) throws Exception {
        return segPermisoFacade.listarPermisosActivosPorAplicacion(idApl);
    }

    @Override
    public List<SegPermiso> listarPermisosPorUsuarioYPagina(String usuario, Integer idPag) throws Exception {
        return segPermisoFacade.listarPermisosPorUsuarioYPagina(usuario, idPag);
    }
}
