/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface AdmParametroGlobalServicioRemote {

    public String crearParametroGlobal(AdmParametroGlobal admParametroGlobal) throws Exception;

    public String editarParametroGlobal(AdmParametroGlobal admParametroGlobal) throws Exception;

    public String eliminarParametroGlobal(AdmParametroGlobal admParametroGlobal) throws Exception;

    public List<AdmParametroGlobal> listarTodo() throws Exception;

    public AdmParametroGlobal buscarXNombre(String nombre) throws Exception;

    public List<AdmParametroGlobal> listarTodosActivos() throws Exception;
}
