/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.administracion.ejb.facade.AdmPersonalFacade;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajo;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativo;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
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
public class AdmPersonalServicio implements AdmPersonalServicioRemote, AdmPersonalServicioLocal {
    
    @EJB
    private AdmPersonalFacade admPersonalFacade;
    private String ENTIDAD_AdmPersonal = "AdmPersonal";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearPersonal(AdmPersonal disDispositivo) throws Exception {
        admPersonalFacade.crear(disDispositivo);
        return "se ha creado el AdmPersonal";
    }
    
    @Override
    public String editarPersonal(AdmPersonal disDispositivo) throws Exception {
        admPersonalFacade.editar(disDispositivo);
        return "se ha modificado el AdmPersonal";
    }
    
    @Override
    public String eliminarPersonal(AdmPersonal disDispositivo) throws Exception {
        admPersonalFacade.eliminar(disDispositivo);
        return "se ha eliminado el AdmPersonal";
    }
    
    @Override
    public List<AdmPersonal> listarTodo() throws Exception {
        return admPersonalFacade.listarOrdenada(ENTIDAD_AdmPersonal, "idPersonal", "asc");
    }
    
    @Override
    public List<AdmPersonal> listarPersonalEquipoAsignado(DisFaseOperativo disFaseOperativo1) throws Exception {
        return admPersonalFacade.listarPersonalEquipoAsignado(disFaseOperativo1);
    }
    
    @Override
    public List<AdmPersonal> listarPersonalEquipoDisponible(SegRol codRol) throws Exception {
        return admPersonalFacade.listarPersonalEquipoDisponible(codRol);
    }
    
    @Override
    public List<AdmPersonal> listarPersonalEquipoNoAsignado(List<AdmPersonal> lstAdmPer, SegRol codRol) throws Exception {
        return admPersonalFacade.listarPersonalEquipoNoAsignado(lstAdmPer, codRol);
    }
    
    @Override
    public AdmPersonal buscarPorCodigo(Integer codPersonal) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idPersonal", codPersonal);
        return admPersonalFacade.buscarPorCampos(ENTIDAD_AdmPersonal, campos);
    }
    
    @Override
    public List<AdmPersonal> listarPersonalEquipoAsignadoEquipo(DisFaseOperativo disFaseOperativo1, DisEquipoTrabajo disEquipoTrabajoCab) throws Exception {
        return admPersonalFacade.listarPersonalEquipoAsignadoEquipo(disFaseOperativo1, disEquipoTrabajoCab);
    }
    
    @Override
    public List<AdmPersonal> listarPersonalEquipoDisponibleTodos(List<AdmPersonal> lstAdmPer, DisFaseOperativo disFaseOperativo1, DisEquipoTrabajo disEquipoTrabajoCab, SegRol codRol) throws Exception {
        return admPersonalFacade.listarPersonalEquipoDisponibleTodos(lstAdmPer, disFaseOperativo1, disEquipoTrabajoCab, codRol);
    }
    
    @Override
    public AdmPersonal listarPersonalXRolUsu(Integer codRolUsu) throws Exception {
        return admPersonalFacade.listarPersonalXRolUsu(codRolUsu);
    }
    
    @Override
    public List<AdmPersonal> listarTodosActivos() throws Exception {
        return admPersonalFacade.listarTodosActivos();
    }
    
    @Override
    public List<AdmPersonal> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {      
        return admPersonalFacade.listarEjecutarConsulta(nombreProdecimiento,parametrosOrdenados );
    }
    
    @Override
    public AdmPersonal buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception {          
        return admPersonalFacade.buscarEjecutarConsulta( nombreProdecimiento ,parametrosOrdenados );
    }
    
    @Override
    public String modificarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception {          
        return admPersonalFacade.modificarEjecutarConsulta(nombreProdecimiento ,parametrosOrdenados);    
    }
      
    @Override
    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admPersonalFacade.existeEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }
    
    @Override
    public Integer tieneCargasPendientes(Integer idPersonal) throws Exception {
        return admPersonalFacade.tieneCargasPendientes(idPersonal);
    }
}
