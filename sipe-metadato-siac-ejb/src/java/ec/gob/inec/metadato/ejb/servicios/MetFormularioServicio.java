/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.facade.MetFormularioFacade;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetFormularioServicio implements MetFormularioServicioRemote, MetFormularioServicioLocal {

    @EJB
    private MetFormularioFacade metFormularioFacade;
    private String ENTIDAD_metFormularioFacade = "MetFormulario";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearFormulario(MetFormulario metFormulario) throws Exception {
        metFormularioFacade.crear(metFormulario);
        return "se ha creado el Formulario";
    }

    @Override
    public String editarFormulario(MetFormulario metFormulario) throws Exception {
        metFormularioFacade.editar(metFormulario);
        return "se ha modificado el Formulario";
    }

    @Override
    public String eliminarFormulario(MetFormulario metFormulario) throws Exception {
        metFormularioFacade.eliminar(metFormulario);
        return "se ha modificado el Formulario";
    }

    @Override
    public List<MetFormulario> listarTodo() throws Exception {
        return metFormularioFacade.listarOrdenada(ENTIDAD_metFormularioFacade, "idFormulario", "asc");
    }
    @Override
    public List<MetFormulario> listarFormulariosPorOperativo(Integer codOpe_) throws Exception{
        return metFormularioFacade.listarFormulariosPorOperativo(codOpe_);
    }

    @Override
    public List<MetFormulario> listarFormulariosTodos() throws Exception {
        return metFormularioFacade.listarFormulariosTodos();
    }

    @Override
    public List<MetFormulario> listarFormulariosPorId(Integer formularioId) throws Exception {
        return metFormularioFacade.listarFormulariosPorId(formularioId);
    }
    
    @Override
    public List<MetFormulario> listarFormulariosPorMuestra(short muestraId) throws Exception {
        return metFormularioFacade.listarFormsPorMuestra(muestraId);
    }
  
    public MetFormulario obtenerMetFormularioPorCodificacion(String codificacion) throws Exception {
         
        
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("estadoLogico", true);
        campos.put("codificacion", codificacion);
        List<MetFormulario> lst= metFormularioFacade.listarPorCampos(ENTIDAD_metFormularioFacade, campos,"codificacion");
        if(!lst.isEmpty()){
            return lst.get(0);
        }else{
            return null;
        }


     }

    @Override
    public MetFormulario bucarFormPorAlias(String codificacion) throws Exception {
        return metFormularioFacade.bucarFormPorAlias(codificacion);
    }
}
