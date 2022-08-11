/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetFormularioServicioRemote {

    public String crearFormulario(MetFormulario metFormulario) throws Exception;

    public String editarFormulario(MetFormulario metFormulario) throws Exception;

    public String eliminarFormulario(MetFormulario metFormulario) throws Exception;

    public List<MetFormulario> listarTodo() throws Exception;
    
    public List<MetFormulario> listarFormulariosPorOperativo(Integer codOpe_) throws Exception;
     
    public List<MetFormulario> listarFormulariosTodos() throws Exception;
     
    public List<MetFormulario> listarFormulariosPorId(Integer formularioId) throws Exception;
     
    public List<MetFormulario> listarFormulariosPorMuestra(short muestraId) throws Exception;
     
    public MetFormulario obtenerMetFormularioPorCodificacion(String codificacion) throws Exception;
     
    public  MetFormulario bucarFormPorAlias(String codificacion) throws Exception;
}
