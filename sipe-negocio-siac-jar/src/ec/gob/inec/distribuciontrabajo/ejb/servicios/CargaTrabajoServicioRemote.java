/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.servicios;


import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisCargaTrabajo;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajoDetalle;
import ec.gob.inec.muestra.ejb.entidades.MueMuestraDetalle;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface CargaTrabajoServicioRemote {

    public String crearCargaTrabajo(DisCargaTrabajo disCargaTrabajo) throws Exception;

    public String editarCargaTrabajo(DisCargaTrabajo disCargaTrabajo) throws Exception;

    public String eliminarCargaTrabajo(DisCargaTrabajo disCargaTrabajo) throws Exception;

    public List<DisCargaTrabajo> listarTodo() throws Exception;

    public DisCargaTrabajo buscarEquiMue(DisEquipoTrabajoDetalle codEquiTD, MueMuestraDetalle mue) throws Exception;
    
    public String eliminarIdentTodo(DisEquipoTrabajoDetalle codEquiTD) throws Exception;
    
    public List<DisCargaTrabajo> listarEquipoDet(DisEquipoTrabajoDetalle codEquiTD) throws Exception;
    
    public String actualizarEstadoAsignacion(DisEquipoTrabajoDetalle codEquiTD, MueMuestraDetalle codMue , String vAsigCat) throws Exception;
    
    public List<DisCargaTrabajo> listarXAsignador(DisEquipoTrabajoDetalle codEquiTD, MueMuestraDetalle codMues) throws Exception;
    
    public List<DisCargaTrabajo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    public DisCargaTrabajo buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
    public String modificarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
    
    public String eliminarMuestraAsignada(String alias, DisEquipoTrabajoDetalle codEquiTD, String codAudit) throws Exception;
    
    public String asignacionACaptura(Integer codFor, String codUudi, String codsCarTraPad) throws Exception;
    public String depachoTerminar(String codsCarTraPad, String codUudi) throws Exception;
    
    public List<DisCargaTrabajo> listarTrabajoMuestraXFase(DisEquipoTrabajoDetalle equipoTrabajoDetalleSeleccion) throws Exception;            
    public List<Object[]> listarTrabajoMuestraXFase1(DisEquipoTrabajoDetalle equipoTrabajoDetalleSeleccion) throws Exception;            
    
    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;        
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;      

    public int contarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

}
