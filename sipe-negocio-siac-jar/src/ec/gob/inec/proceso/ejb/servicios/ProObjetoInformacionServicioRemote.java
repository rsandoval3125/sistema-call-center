/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProObjetoInformacion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProObjetoInformacionServicioRemote {
    public String crearMpeObjetoInformacion(ProObjetoInformacion objInf) throws Exception;
    public String editarMpeObjetoInformacion(ProObjetoInformacion objInf) throws Exception;
    public String eliminarMpeObjetoInformacion(String codobjInf) throws Exception;
    public List<ProObjetoInformacion> listarMpeObjetoInformacion() throws Exception;
    
    public ProObjetoInformacion buscarObjInfPorId(String codobjInf)throws Exception;
}
