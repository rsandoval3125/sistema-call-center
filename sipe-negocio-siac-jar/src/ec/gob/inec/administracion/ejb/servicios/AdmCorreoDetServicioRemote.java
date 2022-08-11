/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet;
import java.util.List;
import javax.ejb.Remote;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Remote
public interface AdmCorreoDetServicioRemote {

    public String crearCorreoDet(AdmCorreoDet admCorreoDet) throws Exception;

    public String editarCorreoDet(AdmCorreoDet admCorreoDet) throws Exception;

    public String eliminarCorreoDet(AdmCorreoDet admCorreoDet) throws Exception;

    public List<AdmCorreoDet> listarTodo() throws Exception;

    public List<AdmCorreoDet> consultarPorIdCorreoCab(Integer idCorreoCab) throws Exception;
}
