/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonalHistorico;
import ec.gob.inec.administracion.ejb.facade.AdmPersonalHistoricoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class AdmPersonalHistoricoServicio implements AdmPersonalHistoricoServicioRemote, AdmPersonalHistoricoServicioLocal {

    @EJB
    private AdmPersonalHistoricoFacade admPersonalHistoricoFacade;
    private String ENTIDAD_AdmPersonalHistorico = "AdmPersonalHistorico";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearPersonalHistorico(AdmPersonalHistorico admPersonalHistorico) throws Exception {
        admPersonalHistoricoFacade.crear(admPersonalHistorico);
        return "se ha creado el AdmPersonalHistorico";
    }

    @Override
    public String editarPersonalHistorico(AdmPersonalHistorico admPersonalHistorico) throws Exception {
        admPersonalHistoricoFacade.editar(admPersonalHistorico);
        return "se ha modificado el AdmPersonalHistorico";
    }

    @Override
    public String eliminarPersonalHistorico(AdmPersonalHistorico admPersonalHistorico) throws Exception {
        admPersonalHistoricoFacade.eliminar(admPersonalHistorico);
        return "se ha eliminado el AdmPersonalHistorico";
    }

    @Override
    public List<AdmPersonalHistorico> listarTodo() throws Exception {
        return admPersonalHistoricoFacade.listarOrdenada(ENTIDAD_AdmPersonalHistorico, "idPerHis", "asc");
    }

    
    
    
   
}
