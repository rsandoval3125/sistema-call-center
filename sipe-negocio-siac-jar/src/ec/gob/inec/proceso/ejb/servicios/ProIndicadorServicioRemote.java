/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProIndicador;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProIndicadorServicioRemote {
    public String crearIndicador(ProIndicador indicador) throws Exception;
    public String editarIndicador(ProIndicador indicador) throws Exception;
    public String eliminarIndicador(Integer codIndicador) throws Exception;
    
 
    public List<ProIndicador> listarIndicadorPorOperativoPorTipoIndicador(Integer idOperativo, String tipoIndicador) throws Exception;
    
    public ProIndicador buscarIndicadorPorID (Integer idIndicador) throws Exception;
    
}
