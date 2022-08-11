/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetFormularioSeccion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetFormularioSeccionServicioRemote {

    public String crearFormularioSeccion(MetFormularioSeccion metFormularioSeccion) throws Exception;

    public String editarFormularioSeccion(MetFormularioSeccion metFormularioSeccion) throws Exception;

    public String eliminarFormularioSeccion(MetFormularioSeccion metFormularioSeccion) throws Exception;

    public List<MetFormularioSeccion> listarTodo() throws Exception;
    
    public List<MetFormularioSeccion> listarTodosActivos() throws Exception;
    
    public List<MetFormularioSeccion> listarPorId(Integer formSeccion) throws Exception;
    
    public MetFormularioSeccion bucarFormSecPoridForXidSecc(Integer idFormulario, Integer idSeccion) throws Exception;
}
