/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.servicios;


import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajo;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajoDetalle;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativo;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface EquipoTrabajoDetalleServicioRemote {
    public String crearEquipoTrabajoDetalle(DisEquipoTrabajoDetalle disEquipoTrabajoDetalle) throws Exception ;    
    public String editarEquipoTrabajoDetalle(DisEquipoTrabajoDetalle disEquipoTrabajoDetalle) throws Exception;
    public String eliminarEquipoTrabajoDetalle(DisEquipoTrabajoDetalle disEquipoTrabajoDetalle) throws Exception ;
    public List<DisEquipoTrabajoDetalle> listarTodo() throws Exception ;
    public DisEquipoTrabajoDetalle buscarPersonalEquipo(AdmPersonal vcodPersonal, DisFaseOperativo vcodFasOpeEst) throws Exception ;    
    public List<DisEquipoTrabajoDetalle> buscarXEquipoYAsigDis(DisEquipoTrabajo codEqui) throws Exception;    
    public DisEquipoTrabajoDetalle buscarXEquipoXUsuarioYFase(SegRolUsuario codRolUsu, DisFaseOperativo codFoe) throws Exception;
    public DisEquipoTrabajoDetalle buscarXCodigoActivo(Integer codEquiDet) throws Exception;
    public DisEquipoTrabajoDetalle buscarUsuarioEquipoFases(Integer vcodUsuario, List<DisFaseOperativo>  lstFasOpeEst) throws Exception;
    
    public List<DisEquipoTrabajoDetalle> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;     
    public DisEquipoTrabajoDetalle buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception ;         
    public String modificarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    public List<Object> buscarPersonalEquipo(Integer codEquiDet) throws Exception;
    
    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;       
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
}
