/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptGeoreferencia;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rmoreano
 */
@Remote
public interface CaptGeoreferenciaServicioRemote {

    public String crearGeoreferencia(CaptGeoreferencia captGeoreferencia) throws Exception;

    public String editarGeoreferencia(CaptGeoreferencia captGeoreferencia) throws Exception;

    public String eliminarGeoreferencia(CaptGeoreferencia captGeoreferencia) throws Exception;

    public List<CaptGeoreferencia> listarTodo() throws Exception;

    public CaptGeoreferencia buscarFormGeorefPorClave(String claveForm) throws Exception;

}
