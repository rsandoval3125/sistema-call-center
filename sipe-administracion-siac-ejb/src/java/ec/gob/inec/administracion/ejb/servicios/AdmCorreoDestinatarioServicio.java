/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario;
import ec.gob.inec.administracion.ejb.facade.AdmCorreoDestinatarioFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Stateless
public class AdmCorreoDestinatarioServicio implements AdmCorreoDestinatarioServicioRemote, AdmCorreoDestinatarioServicioLocal {

    @EJB
    private AdmCorreoDestinatarioFacade admCorreoDestinatarioFacadeDao;
    private String ENTIDAD_admCorreoDestinatarioFacadeDao = "AdmCorreoDestinatario";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception {
        admCorreoDestinatarioFacadeDao.crear(admCorreoDestinatario);
        return "se ha creado el AdmCorreoDestinatario";
    }

    @Override
    public String editarCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception {
        admCorreoDestinatarioFacadeDao.editar(admCorreoDestinatario);
        return "se ha modificado el AdmCorreoDestinatario";
    }

    @Override
    public String eliminarCorreoDestinatario(AdmCorreoDestinatario admCorreoDestinatario) throws Exception {
        admCorreoDestinatarioFacadeDao.eliminar(admCorreoDestinatario);
        return "se ha eliminado el AdmCorreoDestinatario";
    }

    @Override
    public List<AdmCorreoDestinatario> listarTodo() throws Exception {
        return admCorreoDestinatarioFacadeDao.listarOrdenada(ENTIDAD_admCorreoDestinatarioFacadeDao, "idCorreoDestinatario", "asc");
    }

    @Override
    public void enviarCorreo(String alias, Map<String, String> params) throws Exception {
        admCorreoDestinatarioFacadeDao.enviarCorreo(alias, params);
    }

    @Override
    public void enviarCorreo(String alias, String direccionDestino, Map<String, String> params) throws Exception {
        admCorreoDestinatarioFacadeDao.enviarCorreo(alias, direccionDestino, params);
    }

    @Override
    public List<AdmCorreoDestinatario> consultarPorIdCorreoCab(Integer idCorreoCab) throws Exception {
        return admCorreoDestinatarioFacadeDao.consultarPorIdCorreoCab(idCorreoCab);
    }

}
