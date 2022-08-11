/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface SegUsuarioServicioRemote {

    public SegUsuario buscarPorCorreo(String correo) throws Exception;

    public String crearUsuario(SegUsuario segUsuario) throws Exception;

    public String editarUsuario(SegUsuario segUsuario) throws Exception;

    public String eliminarUsuario(SegUsuario segUsuario) throws Exception;

    public List<SegUsuario> listarTodo() throws Exception;

    public List<Object[]> listarSoloActivos() throws Exception;

    public SegUsuario buscarxID(Integer id) throws Exception;

    public SegUsuario buscarxUsuarioContrasena(String usuario, String contrasena) throws Exception;

    public List<SegUsuario> listarTodosActivos() throws Exception;

    public SegUsuario buscarPorUsuario(String usuario) throws Exception;

    public Integer tieneCargasPendientes(Integer idUsuario) throws Exception;

    public Integer tieneCargasPendientes(Integer idUsuario, Integer idRol) throws Exception;
}
