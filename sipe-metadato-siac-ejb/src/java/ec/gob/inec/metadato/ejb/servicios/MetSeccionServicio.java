/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.facade.MetSeccionFacade;
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
public class MetSeccionServicio implements MetSeccionServicioRemote, MetSeccionServicioLocal {

    @EJB
    private MetSeccionFacade metSeccionFacade;
    private String ENTIDAD_metSeccionFacade = "MetSeccion";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearSeccion(MetSeccion metSeccion) throws Exception {
        metSeccionFacade.crear(metSeccion);
        return "se ha creado el Seccion";
    }

    @Override
    public String editarSeccion(MetSeccion metSeccion) throws Exception {
        metSeccionFacade.editar(metSeccion);
        return "se ha modificado el Seccion";
    }

    @Override
    public String eliminarSeccion(MetSeccion metSeccion) throws Exception {
        metSeccionFacade.eliminar(metSeccion);
        return "se ha modificado el Seccion";
    }

    @Override
    public List<MetSeccion> listarTodo() throws Exception {
        return metSeccionFacade.listarOrdenada(ENTIDAD_metSeccionFacade, "idSeccion", "asc");
    }
    @Override
    public List<MetSeccion> listarSeccionesNivel1PorFormulario(Integer codFor_) throws Exception {
        return metSeccionFacade.listarSeccionesNivel1PorFormulario(codFor_);
    }
    @Override
    public boolean seccionTieneHijos(Integer idSeccion_) throws Exception {
        return metSeccionFacade.seccionTieneHijos(idSeccion_);
    }
    
    @Override
    public boolean seccionTieneVariables(Integer idSeccion_) throws Exception {
        return metSeccionFacade.seccionTieneVariables(idSeccion_);
    }
    @Override
    public List<MetSeccion> listarSeccionesHijasPorNivel(Integer idSeccion_, Integer nivel_) throws Exception {
        return metSeccionFacade.listarSeccionesHijasPorNivel(idSeccion_, nivel_);
    }
    
    @Override
    public List<MetSeccion> listarTodas() throws Exception {
        return metSeccionFacade.listarTodas();
    }

    @Override
    public List<MetSeccion> listarSeccionPorId(Integer idSeccion) throws Exception {
        return metSeccionFacade.listarSeccionPorId(idSeccion);
    }

    @Override
    public List<MetSeccion> listarSoloSeccion() throws Exception {
        return metSeccionFacade.listarSoloSeccion();
    }

    @Override
    public boolean accionSeccionPorNombreNivelOrden(String nombreSec, Integer nivelSec, Integer ordenSec) throws Exception {
        return metSeccionFacade.accionSeccionPorNombreNivelOrden(nombreSec, nivelSec, ordenSec);
    }

    @Override
    public boolean accionPorNombre(String nombreSec) throws Exception {
        return metSeccionFacade.accionPorNombre(nombreSec);
    }

    @Override
    public List<MetSeccion> listarSecPorFomulario(Integer codFormulario) throws Exception {
        return metSeccionFacade.listarSecPorFomulario(codFormulario);
    }

    @Override
    public List<MetSeccion> listarSeccionesNoAsignados(List<MetSeccion> lstSec) throws Exception {
        return metSeccionFacade.listarSeccionesNoAsignados(lstSec);
    }
    
    @Override
    public boolean accionSubsecciones(Integer codSeccion) throws Exception {
        return metSeccionFacade.accionSubsecciones(codSeccion);
    }
    
     public List<MetSeccion> listarSeccionesTipoHPorFormulario(Integer codFormulario) throws Exception{
         return metSeccionFacade.listarSeccionesTipoHPorFormulario(codFormulario);
     }
     public List<MetSeccion> listarSeccRecursivoForm(Integer codFormulario) throws Exception{
         return metSeccionFacade.listarSeccRecursivoForm(codFormulario);
     }
     
     public MetSeccion obtenerMetSeccionPorNombre(String nombre) throws Exception {
         
        
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("estadoLogico", true);
        campos.put("nombre", nombre);
        List<MetSeccion> lst= metSeccionFacade.listarPorCampos(ENTIDAD_metSeccionFacade, campos,"nombre");
        if(!lst.isEmpty()){
            return lst.get(0);
        }else{
            return null;
        }


     }
}
