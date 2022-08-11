/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
import ec.gob.inec.seguridad.ejb.facade.SegRolUsuarioFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class SegRolUsuarioServicio implements SegRolUsuarioServicioRemote, SegRolUsuarioServicioLocal {

    @EJB
    private SegRolUsuarioFacade segRolUsuarioFacade;
    private String ENTIDAD_segRolUsuarioFacade = "SegRolUsuario";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearRolUsuario(SegRolUsuario segRolUsuario) throws Exception {
        segRolUsuarioFacade.crear(segRolUsuario);
        return "se ha creado el RolUsuario";
    }

    @Override
    public String editarRolUsuario(SegRolUsuario segRolUsuario) throws Exception {
        segRolUsuarioFacade.editar(segRolUsuario);
        return "se ha modificado el RolUsuario";
    }

    @Override
    public String eliminarRolUsuario(SegRolUsuario segRolUsuario) throws Exception {
        segRolUsuarioFacade.eliminar(segRolUsuario);
        return "se ha modificado el RolUsuario";
    }

    @Override
    public List<SegRolUsuario> listarTodo() throws Exception {
        return segRolUsuarioFacade.listarOrdenada(ENTIDAD_segRolUsuarioFacade, "idRolUsu", "asc");
    }

    @Override
    public SegRolUsuario buscarRolUsuarioFase(AdmPersonal vcodPersonal, SegRol vcodRol) throws Exception {
        return segRolUsuarioFacade.buscarRolUsuarioFase(vcodPersonal, vcodRol); 
    }

    @Override
    public boolean existeUsuarioRol(Integer codUsuario, Integer codRol) throws Exception {
        return segRolUsuarioFacade.existeUsuarioRol(codUsuario, codRol);
    }

    @Override
    public SegRolUsuario buscarPorUsuarioRol(Integer codUsuario, Integer codRol) throws Exception {
        return segRolUsuarioFacade.buscarPorUsuarioRol(codUsuario, codRol);
    }
    
    @Override
    public List<SegRolUsuario> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {      
        return segRolUsuarioFacade.listarEjecutarConsulta(nombreProdecimiento,parametrosOrdenados );
    }
    
    @Override
    public SegRolUsuario buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception {          
        return segRolUsuarioFacade.buscarEjecutarConsulta( nombreProdecimiento ,parametrosOrdenados );
    }
}
