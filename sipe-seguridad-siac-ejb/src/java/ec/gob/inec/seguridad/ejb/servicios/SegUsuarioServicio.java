/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import ec.gob.inec.seguridad.ejb.facade.SegUsuarioFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class SegUsuarioServicio implements SegUsuarioServicioRemote, SegUsuarioServicioLocal {

    @EJB
    private SegUsuarioFacade segUsuarioFacade;
    private String ENTIDAD_segUsuarioFacade = "SegUsuario";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearUsuario(SegUsuario segUsuario) throws Exception {
        segUsuarioFacade.crear(segUsuario);
        return "se ha creado el Usuario";
    }

    @Override
    public String editarUsuario(SegUsuario segUsuario) throws Exception {
        segUsuarioFacade.editar(segUsuario);
        return "se ha modificado el Usuario";
    }

    @Override
    public String eliminarUsuario(SegUsuario segUsuario) throws Exception {
        segUsuarioFacade.eliminar(segUsuario);
        return "se ha modificado el Usuario";
    }

    @Override
    public List<SegUsuario> listarTodo() throws Exception {
        return segUsuarioFacade.listarOrdenada(ENTIDAD_segUsuarioFacade, "idUsuario", "asc");
    }

    @Override
    public SegUsuario buscarxID(Integer id) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idUsuario", id);
        return segUsuarioFacade.buscarPorCampos(ENTIDAD_segUsuarioFacade, campos);
    }

    @Override
    public SegUsuario buscarxUsuarioContrasena(String usuario, String contrasena) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("nombre", usuario);
        campos.put("clave", contrasena);
        return segUsuarioFacade.buscarPorCampos(ENTIDAD_segUsuarioFacade, campos);
    }

    @Override
    public List<SegUsuario> listarTodosActivos() throws Exception {
        return segUsuarioFacade.listarTodosActivos();
    }

    @Override
    public List<Object[]> listarSoloActivos() throws Exception {
        return segUsuarioFacade.listarSoloActivos();
    }

    @Override
    public SegUsuario buscarPorUsuario(String usuario) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("nombre", usuario);
        return segUsuarioFacade.buscarPorCampos(ENTIDAD_segUsuarioFacade, campos);
    }

    @Override
    public SegUsuario buscarPorCorreo(String correo) throws Exception {
        return segUsuarioFacade.buscarPorCorreo(correo);
    }

    @Override
    public Integer tieneCargasPendientes(Integer idUsuario) throws Exception {
        return segUsuarioFacade.tieneCargasPendientes(idUsuario);
    }

    @Override
    public Integer tieneCargasPendientes(Integer idUsuario, Integer idRol) throws Exception {
        return segUsuarioFacade.tieneCargasPendientes(idUsuario, idRol);
    }
}
