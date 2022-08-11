/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.servicios;

import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface EquipoTrabajoServicioRemote {
    public String crearEquipoTrabajo(DisEquipoTrabajo disEquipoTrabajo) throws Exception ;    
    public String editarEquipoTrabajo(DisEquipoTrabajo disEquipoTrabajo) throws Exception;
    public String eliminarEquipoTrabajo(DisEquipoTrabajo disEquipoTrabajo) throws Exception ;
    public List<DisEquipoTrabajo> listarTodo() throws Exception ;
    public List<DisEquipoTrabajo> listarEquipoXOper(MetOperativo vcodOper) throws Exception;
    public DisEquipoTrabajo buscarPorCodigo(Integer vCod) throws Exception ;
    public DisEquipoTrabajo buscarPorNombre(String nombreEqui)  throws Exception ;
    
    public List<DisEquipoTrabajo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception ;  
    public DisEquipoTrabajo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
}
