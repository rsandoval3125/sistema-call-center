/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.AeRegistroBase;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface AeRegistroBaseServicioRemote {
    public List<AeRegistroBase> listarRegistrosBasePorGeneracion(Integer codGeneracion) throws Exception;
    public List<AeRegistroBase> listarViviendasDeAreaPorGeneracion(Integer codGen) throws Exception;
    public List<AeRegistroBase> listarRegistrosDeAreaPorGeneracion(Integer codGen) throws Exception;
}
