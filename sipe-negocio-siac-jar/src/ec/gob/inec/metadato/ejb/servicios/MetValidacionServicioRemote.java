/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetValidacion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetValidacionServicioRemote {

    public String crearValidacion(MetValidacion metValidacion) throws Exception;

    public String editarValidacion(MetValidacion metValidacion) throws Exception;

    public String eliminarValidacion(MetValidacion metValidacion) throws Exception;

    public List<MetValidacion> listarTodo() throws Exception;
    
    public List<Object[]> listarValidacion(Integer formulario) throws Exception;

    public List<MetValidacion> listarValidacionXSeccion(Integer codSeccion) throws Exception;
}
