/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import ec.gob.inec.metadato.ejb.facade.MetSeccionFacade;
import ec.gob.inec.metadato.ejb.facade.MetVariableFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetVariableServicio implements MetVariableServicioRemote, MetVariableServicioLocal {

    @EJB
    private MetVariableFacade metVariableFacade;
    private String ENTIDAD_metVariableFacade = "MetVariable";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearVariable(MetVariable var) throws Exception {
        metVariableFacade.crear(var);
        return "se ha creado variable";
    }

    @Override
    public String editarVariable(MetVariable var) throws Exception {
        metVariableFacade.editar(var);
        return "se ha modificado variable";
    }

    @Override
    public String eliminarVariable(MetVariable var) throws Exception {
        metVariableFacade.eliminar(var);
        return "se ha modificado variable";
    }

    @Override
    public List<MetVariable> listarTodo() throws Exception {
        return metVariableFacade.listarOrdenada(ENTIDAD_metVariableFacade, "idVar", "asc");
    }
    
    public List<MetVariable> listarVariablesPorSeccion(Integer codSeccion_) throws Exception{
        return metVariableFacade.listarVariablesPorSeccion(codSeccion_);
    }
    
    public String existeDuplicidadDeIdentificadorDeVariablesEnSeccion(Integer codSeccion_) throws Exception{
        return metVariableFacade.existeDuplicidadDeIdentificadorDeVariablesEnSeccion(codSeccion_);
    }
    
    public List<Object[]> listarDiccionarioVariablesCorporativas(String sql) {
        return metVariableFacade.listarDiccionarioVariablesCorporativas(sql);
    }
    
    public List<Object> listarVariablesNVPorFormulario(Integer idFormulario) {
        return metVariableFacade.listarVariablesNVPorFormulario(idFormulario);
    }
    
    public Boolean generarCrosstab(String sql) {
        return metVariableFacade.generarCrosstab(sql);
    }

    @Override
    public MetVariable buscarVariablePorIdentificador(String identificador) throws Exception {
        return metVariableFacade.buscarVariablePorIdentificador(identificador);
    }
}
