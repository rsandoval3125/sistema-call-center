/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.geografia.ejb.servicios;

import ec.gob.inec.geografia.ejb.entidades.GeoCobertura;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author lponce
 */
@Remote
public interface GeoCoberturaServicioRemote {

    public List<GeoCobertura> listarPorCampos(Map<String, Object> campos, String orden) throws Exception;
}
