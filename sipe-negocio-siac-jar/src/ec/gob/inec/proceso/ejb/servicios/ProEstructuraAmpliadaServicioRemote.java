/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.proceso.ejb.entidades.ProEstructuraAmpliada;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProEstructuraAmpliadaServicioRemote {
   public String crearProEstructuraAmpliada (ProEstructuraAmpliada estructuraAmpliada) throws Exception;
   public String editarProEstructuraAmpliada (ProEstructuraAmpliada estructuraAmpliada) throws Exception;
   public String eliminarProEstructuraAmpliada (Integer idEstructuraAmpliada)throws Exception;
   public void eliminarPorIdAnioIdEstructura(Integer idAnio, Integer idEstuctura)throws Exception;
   public List<ProEstructuraAmpliada> buscarPorCodAnioEjecucion (Integer idAnio)throws Exception;
   public ProEstructuraAmpliada buscarPorIdAnioYIdEstructura(Integer idAnio, Integer idEstuctura) throws Exception;
   public boolean existePorAnioYFinanciamineto(MetCatalogo codAnio, MetCatalogo codFinanciamiento)throws Exception;
   public boolean existePorAnioYActividad(MetCatalogo codAnio, MetCatalogo codActividad)throws Exception;
   public boolean existePorAnioYOperacionDireccion(MetCatalogo codAnio, MetCatalogo codOperacionDireccion)throws Exception;
   public boolean existePorAnioYSiglasProyecto(MetCatalogo codAnio, MetCatalogo codSiglasProyecto)throws Exception;
   public boolean existePorAnioYTipoActividad(MetCatalogo codAnio, MetCatalogo codTipoActividad)throws Exception;
   
}
