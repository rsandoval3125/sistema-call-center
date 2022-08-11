/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Local
public interface AdmCorreoDestinatarioServicioLocal {

    public String crearCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception;

    public String editarCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception;

    public String eliminarCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception;

    public List<AdmCorreoDestinatario> listarTodo() throws Exception;

    public void enviarCorreo(String alias, Map<String, String> params) throws Exception;

    public void enviarCorreo(String alias, String direccionDestino, Map<String, String> params) throws Exception;

    public List<AdmCorreoDestinatario> consultarPorIdCorreoCab(Integer idCorreoCab) throws Exception;
}
