/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonalHistorico;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface AdmPersonalHistoricoServicioRemote {

    public String crearPersonalHistorico(AdmPersonalHistorico admPersonalHistorico) throws Exception;

    public String editarPersonalHistorico(AdmPersonalHistorico admPersonalHistorico) throws Exception;

    public String eliminarPersonalHistorico(AdmPersonalHistorico admPersonalHistorico) throws Exception;

    public List<AdmPersonalHistorico> listarTodo() throws Exception;
}
