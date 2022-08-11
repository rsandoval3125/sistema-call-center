/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegRolPermiso;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
import ec.gob.inec.seguridad.ejb.facade.SegRolPermisoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dvaldas
 */
@Stateless
public class SegRolPermisoServicio implements SegRolPermisoServicioRemote, SegRolPermisoServicioLocal {

    @EJB
    private SegRolPermisoFacade segRolPermisoFacade;
    private String ENTIDAD_segRolPermiso = "SegRolPermiso";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearRolPermiso(SegRolPermiso segRolPermiso) throws Exception {
        segRolPermisoFacade.crear(segRolPermiso);
        return "se ha creado el registro";
    }

    @Override
    public String editarRolPermiso(SegRolPermiso segRolPermiso) throws Exception {
        segRolPermisoFacade.editar(segRolPermiso);
        return "se ha modificado el registro";
    }

    @Override
    public String eliminarRolPermiso(SegRolPermiso segRolPermiso) throws Exception {
        segRolPermisoFacade.eliminar(segRolPermiso);
        return "se ha modificado el registro";
    }

    @Override
    public List<SegRolPermiso> listarTodo() throws Exception {
        return segRolPermisoFacade.listarOrdenada(ENTIDAD_segRolPermiso, "idUsuario", "asc");
    }

    @Override
    public boolean existePermisoRol(Integer codRol, Integer codPermiso) throws Exception {
        return segRolPermisoFacade.existePermisoRol(codRol, codPermiso);
    }

    @Override
    public SegRolPermiso buscarPorPermisoRol(Integer codRol, Integer codPermiso) throws Exception {
        return segRolPermisoFacade.buscarPorPermisoRol(codRol, codPermiso);
    }
}
