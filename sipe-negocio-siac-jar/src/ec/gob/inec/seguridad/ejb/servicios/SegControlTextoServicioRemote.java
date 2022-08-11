/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegControlTexto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface SegControlTextoServicioRemote {

    public String crearControlTexto(SegControlTexto segControlTexto) throws Exception;

    public String editarControlTexto(SegControlTexto segControlTexto) throws Exception;

    public String eliminarControlTexto(SegControlTexto segControlTexto) throws Exception;

    public List<SegControlTexto> listarTodo() throws Exception;
}
