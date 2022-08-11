/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoCab;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet;
import java.util.List;
import javax.ejb.Local;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Local
public interface AdmCorreoCabServicioLocal {

    public String crearCorreoCab(AdmCorreoCab admCorreoCab) throws Exception;

    public String editarCorreoCab(AdmCorreoCab admCorreoCab) throws Exception;

    public String eliminarCorreoCab(AdmCorreoCab admCorreoCab) throws Exception;

    public List<AdmCorreoCab> listarTodo() throws Exception;

    public List<AdmCorreoCab> listarTodosActivos() throws Exception;

    public String crearCorreo(AdmCorreoCab admCorreoCab, AdmCorreoDet admCorreoDet, List<AdmCorreoDestinatario> listadoCorreosDestinatarios) throws Exception;

    public String editarCorreo(AdmCorreoCab admCorreoCab, AdmCorreoDet admCorreoDet, List<AdmCorreoDestinatario> listadoCorreosDestinatarios) throws Exception;
}
