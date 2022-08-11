/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.clases.Tabla;
import ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaCab;
import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author vespinoza
 */
@Local
public interface SegAuditoriaCabServicioLocal {

    public String crearAuditoriaCab(SegAuditoriaCab segAuditoriaCab) throws Exception;

    public String editarAuditoriaCab(SegAuditoriaCab segAuditoriaCab) throws Exception;

    public String eliminarAuditoriaCab(SegAuditoriaCab segAuditoriaCab) throws Exception;

    public List<SegAuditoriaCab> listarTodo() throws Exception;

    public String activarAuditoria(String tabla) throws Exception;

    public String desactivarAuditoria(String tabla) throws Exception;

    public List<String> listarEsquemasBDD() throws Exception;

    public List<Tabla> listarTablasConTriggersAsignados() throws Exception;

    public List<Tabla> listarTablasSinTrigersNoAsignadosPorEsquema(List<String> tablas, String esquema) throws Exception;

    public List<Tabla> listarTablasSinTrigersNoAsignados(List<String> tablas) throws Exception;

    public boolean validarTablaParaAuditar(String tabla) throws Exception;
}
