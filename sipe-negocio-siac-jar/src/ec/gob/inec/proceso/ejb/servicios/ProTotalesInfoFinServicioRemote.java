/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProTotalesInfoFin;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author gquezada
 */
@Remote
public interface ProTotalesInfoFinServicioRemote {
    
    public String crearMpeTotalesInfoFin(ProTotalesInfoFin totalesInfoFin) throws Exception;

    public String editarMpeTotalesInfoFin(ProTotalesInfoFin totalesInfoFin) throws Exception;

    public String eliminarMpeTotalesInfoFin(Integer totalesInfoFin) throws Exception;    
    
    public List<ProTotalesInfoFin> ListaPorAnio(Integer idAnio) throws Exception;
 
    
}
