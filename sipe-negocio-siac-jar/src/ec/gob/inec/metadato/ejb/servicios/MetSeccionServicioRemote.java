/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetSeccionServicioRemote {

   public String crearSeccion(MetSeccion metSeccion) throws Exception;

    public String editarSeccion(MetSeccion metSeccion) throws Exception;

    public String eliminarSeccion(MetSeccion metSeccion) throws Exception;

    public List<MetSeccion> listarTodo() throws Exception;
    
     public List<MetSeccion> listarSeccionesNivel1PorFormulario(Integer codFor_) throws Exception;
     
     public boolean seccionTieneHijos(Integer idSeccion_) throws Exception;
     
      public boolean seccionTieneVariables(Integer idSeccion_) throws Exception;
     
      public List<MetSeccion> listarSeccionesHijasPorNivel(Integer idSeccion_,Integer nivel_) throws Exception;
      
      public List<MetSeccion> listarTodas() throws Exception;
      
      public List<MetSeccion> listarSeccionPorId(Integer idSeccion) throws Exception;
      
      public List<MetSeccion> listarSoloSeccion() throws Exception;
      
      public boolean accionSeccionPorNombreNivelOrden(String nombreSec, Integer nivelSec, Integer ordenSec) throws Exception;
      
      public boolean accionPorNombre(String nombreSec) throws Exception;
      
      public List<MetSeccion> listarSecPorFomulario (Integer codFormulario) throws Exception;
      
      public List<MetSeccion> listarSeccionesNoAsignados(List<MetSeccion> lstSec) throws Exception;
      
      public boolean accionSubsecciones(Integer codSeccion) throws Exception;
      
       public List<MetSeccion> listarSeccionesTipoHPorFormulario(Integer codFormulario) throws Exception;
       
       public List<MetSeccion> listarSeccRecursivoForm(Integer codFormulario) throws Exception;
       
       public MetSeccion obtenerMetSeccionPorNombre(String nombre) throws Exception ;
}
