/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaDet;
import ec.gob.inec.seguridad.ejb.facade.SegAuditoriaDetFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class SegAuditoriaDetServicio implements SegAuditoriaDetServicioRemote, SegAuditoriaDetServicioLocal {

    @EJB
    private SegAuditoriaDetFacade segAuditoriaDetFacade;
    private String ENTIDAD_segAuditoriaDetFacade = "SegAuditoriaDet";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearAuditoriaDet(SegAuditoriaDet segAuditoriaDet) throws Exception {
        segAuditoriaDetFacade.crear(segAuditoriaDet);
        return "se ha creado el AuditoriaDet";
    }

    @Override
    public String editarAuditoriaDet(SegAuditoriaDet segAuditoriaDet) throws Exception {
        segAuditoriaDetFacade.editar(segAuditoriaDet);
        return "se ha modificado el AuditoriaDet";
    }

    @Override
    public String eliminarAuditoriaDet(SegAuditoriaDet segAuditoriaDet) throws Exception {
        segAuditoriaDetFacade.eliminar(segAuditoriaDet);
        return "se ha modificado el AuditoriaDet";
    }

    @Override
    public List<SegAuditoriaDet> listarTodo() throws Exception {
        return segAuditoriaDetFacade.listarOrdenada(ENTIDAD_segAuditoriaDetFacade, "idAuditoriaCab", "asc");
    }
}
