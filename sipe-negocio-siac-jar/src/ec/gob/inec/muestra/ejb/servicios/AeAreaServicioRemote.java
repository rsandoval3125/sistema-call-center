/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.AeArea;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface AeAreaServicioRemote {
    public AeArea crearArea(AeArea area) throws Exception;
    public List<AeArea> listarAreasPorGeneracion(Integer codGen) throws Exception;
}
