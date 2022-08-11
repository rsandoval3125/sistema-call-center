/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegRolPermiso;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dvaldas
 */
@Remote
public interface SegRolPermisoServicioRemote {

    public String crearRolPermiso(SegRolPermiso segRol) throws Exception;

    public String editarRolPermiso(SegRolPermiso segRol) throws Exception;

    public String eliminarRolPermiso(SegRolPermiso segRol) throws Exception;

    public List<SegRolPermiso> listarTodo() throws Exception;

    /*   public List<SegRolPermiso> listarRolXUsuarioContrasenia(String nombre, String contrasenia) throws Exception;

    public List<SegRolPermiso> listarRolesNotInFase(Integer codOpeEst, Integer codPer) throws Exception;

    public SegRolPermiso buscarXId(Integer idRol) throws Exception;*/
    public boolean existePermisoRol(Integer codRol, Integer codPermiso) throws Exception;

    public SegRolPermiso buscarPorPermisoRol(Integer codRol, Integer codPermiso) throws Exception;
}
