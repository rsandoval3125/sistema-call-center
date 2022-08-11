/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.AeGeneracion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface AeGeneracionServicioRemote {
     public String crearAeGeneracion(AeGeneracion aeg) throws Exception ;
      public AeGeneracion buscarAeGeneracionPorId(Integer idGen) throws Exception;
       public List<AeGeneracion> listarAeGeneracionPorSector(String sector) throws Exception ;
       public AeGeneracion editarAeGeneracion(AeGeneracion aeg) throws Exception;
}
