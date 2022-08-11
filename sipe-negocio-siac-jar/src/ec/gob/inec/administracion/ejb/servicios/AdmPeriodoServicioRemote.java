/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface AdmPeriodoServicioRemote {

    public String crearPeriodo(AdmPeriodo admPeriodo) throws Exception;

    public String editarPeriodo(AdmPeriodo admPeriodo) throws Exception;

    public String eliminarPeriodo(AdmPeriodo admPeriodo) throws Exception;

    public List<AdmPeriodo> listarTodo() throws Exception;

    public List<AdmPeriodo> listarTodosActivos() throws Exception;
    
    public AdmPeriodo buscarXCodigoActivo(Integer codPer) throws Exception;
    
    public List<AdmPeriodo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> prametrosOrdenados) throws Exception ;  
    
    public AdmPeriodo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
}
