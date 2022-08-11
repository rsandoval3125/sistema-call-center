/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.clases.Tabla;
import ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaCab;
import ec.gob.inec.seguridad.ejb.facade.SegAuditoriaCabFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class SegAuditoriaCabServicio implements SegAuditoriaCabServicioRemote, SegAuditoriaCabServicioLocal {

    @EJB
    private SegAuditoriaCabFacade segAuditoriaCabFacade;
    private String ENTIDAD_segAuditoriaCabFacade = "SegAuditoriaCab";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearAuditoriaCab(SegAuditoriaCab segAuditoriaCab) throws Exception {
        segAuditoriaCabFacade.crear(segAuditoriaCab);
        return "se ha creado el AuditoriaCab";
    }

    @Override
    public String editarAuditoriaCab(SegAuditoriaCab segAuditoriaCab) throws Exception {
        segAuditoriaCabFacade.editar(segAuditoriaCab);
        return "se ha modificado el AuditoriaCab";
    }

    @Override
    public String eliminarAuditoriaCab(SegAuditoriaCab segAuditoriaCab) throws Exception {
        segAuditoriaCabFacade.eliminar(segAuditoriaCab);
        return "se ha modificado el AuditoriaCab";
    }

    @Override
    public List<SegAuditoriaCab> listarTodo() throws Exception {
        return segAuditoriaCabFacade.listarOrdenada(ENTIDAD_segAuditoriaCabFacade, "idAuditoria", "asc");
    }

    @Override
    public boolean validarTablaParaAuditar(String tabla) throws Exception {
        return segAuditoriaCabFacade.validarTabla(tabla);

    }

    @Override
    public String activarAuditoria(String tabla) throws Exception {
        segAuditoriaCabFacade.activarAuditoria(tabla);
        return "se ha activado la auditoria en la tabla";
    }

    @Override
    public String desactivarAuditoria(String tabla) throws Exception {
        segAuditoriaCabFacade.desactivarAuditoria(tabla);
        return "se ha desactivado la auditoria en la tabla";
    }

    @Override
    public List<String> listarEsquemasBDD() throws Exception {
        return segAuditoriaCabFacade.consultarEsquemaBDD();
    }

    @Override
    public List<Tabla> listarTablasConTriggersAsignados() throws Exception {
        return segAuditoriaCabFacade.consultarTablasConTriggersBDD();
    }

    @Override
    public List<Tabla> listarTablasSinTrigersNoAsignadosPorEsquema(List<String> tablas, String esquema) throws Exception {
        return segAuditoriaCabFacade.consultarTablasSinTriggersPorEsquemaBDD(tablas, esquema);
    }

    @Override
    public List<Tabla> listarTablasSinTrigersNoAsignados(List<String> tablas) throws Exception {
        return segAuditoriaCabFacade.consultarTablasSinTriggersBDD(tablas);
    }

}
