/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaDet;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface SegAuditoriaDetServicioRemote {

    public String crearAuditoriaDet(SegAuditoriaDet segAuditoriaDet) throws Exception;

    public String editarAuditoriaDet(SegAuditoriaDet segAuditoriaDet) throws Exception;

    public String eliminarAuditoriaDet(SegAuditoriaDet segAuditoriaDet) throws Exception;

    public List<SegAuditoriaDet> listarTodo() throws Exception;
}
