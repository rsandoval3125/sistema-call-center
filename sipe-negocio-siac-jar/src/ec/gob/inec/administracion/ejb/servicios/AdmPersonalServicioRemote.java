/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajo;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativo;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface AdmPersonalServicioRemote {

    public String crearPersonal(AdmPersonal admPersonal) throws Exception;

    public String editarPersonal(AdmPersonal admPersonal) throws Exception;

    public String eliminarPersonal(AdmPersonal admPersonal) throws Exception;

    public List<AdmPersonal> listarTodo() throws Exception;

    public List<AdmPersonal> listarPersonalEquipoAsignado(DisFaseOperativo disFasesOperacionEstadistica1) throws Exception;

    public List<AdmPersonal> listarPersonalEquipoAsignadoEquipo(DisFaseOperativo disFasesOperacionEstadistica1, DisEquipoTrabajo disEquipoTrabajo) throws Exception;

    public List<AdmPersonal> listarPersonalEquipoDisponible(SegRol codRol) throws Exception;

    public List<AdmPersonal> listarPersonalEquipoNoAsignado(List<AdmPersonal> lstAdmPer, SegRol codRol) throws Exception;

    public AdmPersonal buscarPorCodigo(Integer codPersonal) throws Exception;

    public List<AdmPersonal> listarPersonalEquipoDisponibleTodos(List<AdmPersonal> lstAdmPer, DisFaseOperativo disFasesOperacionEstadistica1, DisEquipoTrabajo disEquipoTrabajo, SegRol codRol) throws Exception;

    public AdmPersonal listarPersonalXRolUsu(Integer codRolUsu) throws Exception;

    public List<AdmPersonal> listarTodosActivos() throws Exception;
            
    public List<AdmPersonal> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public AdmPersonal buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public String modificarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    
    public Integer tieneCargasPendientes(Integer idPersonal) throws Exception;
}
