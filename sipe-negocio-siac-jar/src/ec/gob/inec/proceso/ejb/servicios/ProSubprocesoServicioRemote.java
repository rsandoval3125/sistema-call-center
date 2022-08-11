/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;


import ec.gob.inec.proceso.ejb.entidades.ProSubproceso;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProSubprocesoServicioRemote {
     public String crearMpeSubproceso(ProSubproceso subproceso) throws Exception;
    public String editarMpeSubproceso(ProSubproceso subproceso) throws Exception;
    public String eliminarMpeSubproceso(Integer codsubproceso) throws Exception;
    
    public List<ProSubproceso> listarMpeSubprocesoPorOperativo(Integer idOperativo) throws Exception;
    
    public List<ProSubproceso> listarMpeSubprocesoPorFase(Integer idFase) throws Exception;
    public List<ProSubproceso> listarMpeSubprocesoPorFaseTodo(Integer idFase) throws Exception;
    
    public ProSubproceso buscarsubprocesoPorId(Integer codsubproceso)throws Exception;
    public boolean ponderacionDeSubProcesosDeFaseEsValida(List<ProSubproceso> listaSubprocesosDeFase);
    
    public ProSubproceso crearSubprocesoCopia(ProSubproceso subprocesoOriginal, Integer idNuevaFase);
    
    public List<ProSubproceso> listarSubProcesoPorFase(Integer codFase) throws Exception;
}
