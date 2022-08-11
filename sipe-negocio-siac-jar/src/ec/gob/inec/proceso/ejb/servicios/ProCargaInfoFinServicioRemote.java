/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;


import ec.gob.inec.proceso.ejb.entidades.ProCargaInfoFin;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProCargaInfoFinServicioRemote {
   public List<ProCargaInfoFin> listarCargas() throws Exception;
}
