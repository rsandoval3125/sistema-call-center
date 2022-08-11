/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmDispositivo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface AdmDispositivoServicioLocal {

    public String crearDispositivo(AdmDispositivo admDispositivo) throws Exception;

    public String editarDispositivo(AdmDispositivo admDispositivo) throws Exception;

    public String eliminarDispositivo(AdmDispositivo admDispositivo) throws Exception;

    public List<AdmDispositivo> listarTodo() throws Exception;

    public List<AdmDispositivo> listarTodosActivos() throws Exception;

    public AdmDispositivo buscarPorCodigoActivo(Integer codDis) throws Exception;

    public List<AdmDispositivo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public AdmDispositivo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public Integer tieneCargasPendientes(String serie) throws Exception;

    public Integer tieneMiembroDeEquipo(String serie) throws Exception;
}
