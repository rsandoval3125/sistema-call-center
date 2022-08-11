/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.servicios;


import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisCargaTrabajoDetalle;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface CargaTrabajoDetalleServicioRemote {

    public String crearCargaTrabajoDetalle(DisCargaTrabajoDetalle disCargaTrabajoDetalle) throws Exception;

    public String editarCargaTrabajoDetalle(DisCargaTrabajoDetalle disCargaTrabajoDetalle) throws Exception;

    public String eliminarCargaTrabajoDetalle(DisCargaTrabajoDetalle disCargaTrabajoDetalle) throws Exception;

    public List<DisCargaTrabajoDetalle> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public DisCargaTrabajoDetalle buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public String modificarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
}
