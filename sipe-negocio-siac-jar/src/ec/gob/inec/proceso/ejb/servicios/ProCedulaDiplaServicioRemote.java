/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import javax.ejb.Remote;
import ec.gob.inec.proceso.ejb.entidades.ProCedulaDipla;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProCedulaDiplaServicioRemote {
   public String crearProCedulaDipla (ProCedulaDipla cedulaDipla) throws Exception;
   public String editarProCedulaDipla (ProCedulaDipla cedulaDipla) throws Exception;
   public String eliminarProCedulaDipla (Integer idCedulaDipla)throws Exception;
   public ProCedulaDipla buscarProCedulaDiplaPorId (Integer idCedulaDipla) throws Exception;
   public ProCedulaDipla buscarPorCodEjercicio (Integer idEjercicio)throws Exception;
   public ProCedulaDipla buscarPorAlias(String alias)throws Exception;
}
