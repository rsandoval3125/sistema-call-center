/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoCab;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet;
import ec.gob.inec.administracion.ejb.facade.AdmCorreoCabFacade;
import ec.gob.inec.administracion.ejb.facade.AdmCorreoDestinatarioFacade;
import ec.gob.inec.administracion.ejb.facade.AdmCorreoDetFacade;
import java.util.HashMap;
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
public class AdmCorreoCabServicio implements AdmCorreoCabServicioRemote, AdmCorreoCabServicioLocal {
    
    @EJB
    private AdmCorreoCabFacade admCorreoCabFacadeDao;
    @EJB
    private AdmCorreoDetFacade admCorreoDetFacadeDao;
    @EJB
    private AdmCorreoDestinatarioFacade admCorreoDestinatarioFacadeDao;
    private String ENTIDAD_admCorreoCabFacadeDao = "AdmCorreoCab";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCorreoCab(AdmCorreoCab admCorreoCab) throws Exception {
        admCorreoCabFacadeDao.crear(admCorreoCab);
        return "se ha creado el AdmCorreoCab";
    }
    
    @Override
    public String editarCorreoCab(AdmCorreoCab admCorreoCab) throws Exception {
        admCorreoCabFacadeDao.editar(admCorreoCab);
        return "se ha modificado el AdmCorreoCab";
    }
    
    @Override
    public String eliminarCorreoCab(AdmCorreoCab admCorreoCab) throws Exception {
        admCorreoCabFacadeDao.eliminar(admCorreoCab);
        return "se ha eliminado el AdmCorreoCab";
    }
    
    @Override
    public List<AdmCorreoCab> listarTodo() throws Exception {
        return admCorreoCabFacadeDao.listarOrdenada(ENTIDAD_admCorreoCabFacadeDao, "idCorreoCab", "asc");
    }
    
    @Override
    public List<AdmCorreoCab> listarTodosActivos() throws Exception {
        return admCorreoCabFacadeDao.listarTodosActivos();
    }
    
    @Override
    public String crearCorreo(AdmCorreoCab admCorreoCab, AdmCorreoDet admCorreoDet, List<AdmCorreoDestinatario> listadoCorreosDestinatarios) throws Exception {
        admCorreoCabFacadeDao.crear(admCorreoCab);
        for (AdmCorreoDestinatario admCorreoDestinatario : listadoCorreosDestinatarios) {
            admCorreoDestinatario.setIdCorreoDestinatario(null);
            admCorreoDestinatario.setCodCorreoCab(admCorreoCab);
            admCorreoDestinatarioFacadeDao.crear(admCorreoDestinatario);
        }
        admCorreoDet.setCodCorreoCab(admCorreoCab);
        admCorreoDetFacadeDao.crear(admCorreoDet);
        return "se ha creado el correo";
    }
    
    @Override
    public String editarCorreo(AdmCorreoCab admCorreoCab, AdmCorreoDet admCorreoDet, List<AdmCorreoDestinatario> listadoCorreosDestinatarios) throws Exception {
        admCorreoCabFacadeDao.editar(admCorreoCab);
        //Borrar logicamente todos los registros del destinatario por correo cabecera.
        List<AdmCorreoDestinatario> tmpTodosCorreosDestinaarios = admCorreoDestinatarioFacadeDao.consultarPorIdCorreoCab(admCorreoCab.getIdCorreoCab());
        for (AdmCorreoDestinatario admCorreoDestinatario : tmpTodosCorreosDestinaarios) {
            admCorreoDestinatario.setEstadoLogico(false);
            admCorreoDestinatario.setEstadoDestinatario(false);
            admCorreoDestinatarioFacadeDao.editar(admCorreoDestinatario);
        }
        for (AdmCorreoDestinatario admCorreoDestinatario : listadoCorreosDestinatarios) {
            Map<String, Object> campos = new HashMap<>();
            campos.put("idCorreoDestinatario", admCorreoDestinatario.getIdCorreoDestinatario());
            if (admCorreoDestinatarioFacadeDao.existePorCampos("AdmCorreoDestinatario", campos, "")) {
                admCorreoDestinatarioFacadeDao.editar(admCorreoDestinatario);
            } else {
                admCorreoDestinatario.setIdCorreoDestinatario(null);
                admCorreoDestinatario.setCodCorreoCab(admCorreoCab);
                admCorreoDestinatarioFacadeDao.crear(admCorreoDestinatario);
            }
        }
        admCorreoDet.setCodCorreoCab(admCorreoCab);
        admCorreoDetFacadeDao.editar(admCorreoDet);
        return "se ha modificado el correo";
    }
    
}
