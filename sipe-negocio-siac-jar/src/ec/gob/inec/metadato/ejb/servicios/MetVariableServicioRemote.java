/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetVariableServicioRemote {

    public String crearVariable(MetVariable metVariable) throws Exception;

    public String editarVariable(MetVariable metVariable) throws Exception;

    public String eliminarVariable(MetVariable metVariable) throws Exception;

    public List<MetVariable> listarTodo() throws Exception;
    
    public List<MetVariable> listarVariablesPorSeccion(Integer codSeccion_) throws Exception;
    
     public String existeDuplicidadDeIdentificadorDeVariablesEnSeccion(Integer codSeccion_) throws Exception;
     
     public List<Object[]> listarDiccionarioVariablesCorporativas(String sql) throws Exception;
     
     public List<Object> listarVariablesNVPorFormulario(Integer idFormulario) throws Exception;
     
     public Boolean generarCrosstab(String sql) throws Exception;
     
     public MetVariable buscarVariablePorIdentificador(String identificador) throws Exception;
}
