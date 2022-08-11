/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Remote;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Remote
public interface AdmCorreoDestinatarioServicioRemote {

    public String crearCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception;

    public String editarCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception;

    public String eliminarCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception;

    public List<AdmCorreoDestinatario> listarTodo() throws Exception;

    public void enviarCorreo(String alias, Map<String, String> params) throws Exception;

    public void enviarCorreo(String alias, String direccionDestino, Map<String, String> params) throws Exception;

    public List<AdmCorreoDestinatario> consultarPorIdCorreoCab(Integer idCorreoCab) throws Exception;
}
