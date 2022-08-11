/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.facade.SegRolFacade;
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
public class SegRolServicio implements SegRolServicioRemote, SegRolServicioLocal {

    @EJB
    private SegRolFacade segRolFacade;
    private String ENTIDAD_segRolFacade = "SegRol";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearRol(SegRol segRol) throws Exception {
        segRolFacade.crear(segRol);
        return "se ha creado el Rol";
    }

    @Override
    public String editarRol(SegRol segRol) throws Exception {
        segRolFacade.editar(segRol);
        return "se ha modificado el Rol";
    }

    @Override
    public String eliminarRol(SegRol segRol) throws Exception {
        segRolFacade.eliminar(segRol);
        return "se ha modificado el Rol";
    }

    @Override
    public List<SegRol> listarTodo() throws Exception {
        return segRolFacade.listarOrdenada(ENTIDAD_segRolFacade, "idRol", "asc");
    }

    @Override
    public List<SegRol> listarRolXUsuarioContrasenia(String nombre, String contrasenia) throws Exception {
        return segRolFacade.listarRolXUsuarioContrasenia(nombre, contrasenia);
    }

    @Override
    public List<SegRol> listarRolesNotInFase(MetOperativo codOper) throws Exception {
        return segRolFacade.listarRolesNotInFase(codOper);
    }

    @Override
    public SegRol buscarXId(Integer idRol) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idRol", idRol);
        return segRolFacade.buscarPorCampos(ENTIDAD_segRolFacade, campos);
    }

    @Override
    public List<SegRol> listarRolesDeUsuarioAsignados(Integer codUsuario) throws Exception {
        return segRolFacade.listarRolesDeUsuarioAsiganado(codUsuario);
    }

    @Override
    public List<SegRol> listarRolesDeUsuarioNoAsignados(List<SegRol> roles) throws Exception {
        return segRolFacade.listarRolesDeUsuarioNoAsiganado(roles);
    }

    /* @Override
    public List<SegRol> listarRolesPorAplicacion(Integer codApl) throws Exception {
        return segRolFacade.listarRolesPorAplicacion(codApl);
    }*/
    @Override
    public List<SegRol> listarRolesActivos() throws Exception {
        return segRolFacade.listarRolesActivos();
    }

    /* @Override
    public List<SegRol> listarRolesDeUsuarioNoAsignadosPorAplicacion(List<SegRol> roles, Integer codApl) throws Exception {
        return segRolFacade.listarRolesDeUsuarioNoAsignadosPorAplicacion(roles, codApl);
    }*/
    @Override
    public List<SegRol> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return segRolFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public SegRol buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return segRolFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public Integer tieneCargasPendientes(Integer idRol) throws Exception {
        return segRolFacade.tieneCargasPendientes(idRol);
    }
}
