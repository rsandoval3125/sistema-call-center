/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet;
import ec.gob.inec.administracion.ejb.facade.AdmCorreoDetFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Stateless
public class AdmCorreoDetServicio implements AdmCorreoDetServicioRemote, AdmCorreoDetServicioLocal {

    @EJB
    private AdmCorreoDetFacade admCorreoDetFacadeDao;
    private String ENTIDAD_admCorreoDetFacadeDao = "AdmCorreoDet";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCorreoDet(AdmCorreoDet admCorreoDet) throws Exception {
        admCorreoDetFacadeDao.crear(admCorreoDet);
        return "se ha creado el AdmCorreoDet";
    }

    @Override
    public String editarCorreoDet(AdmCorreoDet admCorreoDet) throws Exception {
        admCorreoDetFacadeDao.editar(admCorreoDet);
        return "se ha modificado el AdmCorreoDet";
    }

    @Override
    public String eliminarCorreoDet(AdmCorreoDet admCorreoDet) throws Exception {
        admCorreoDetFacadeDao.eliminar(admCorreoDet);
        return "se ha eliminado el AdmCorreoDet";
    }

    @Override
    public List<AdmCorreoDet> listarTodo() throws Exception {
        return admCorreoDetFacadeDao.listarOrdenada(ENTIDAD_admCorreoDetFacadeDao, "idCorreoDet", "asc");
    }

    @Override
    public List<AdmCorreoDet> consultarPorIdCorreoCab(Integer idCorreoCab) throws Exception {
        return admCorreoDetFacadeDao.consultarPorIdCorreoCab(idCorreoCab);
    }
}
