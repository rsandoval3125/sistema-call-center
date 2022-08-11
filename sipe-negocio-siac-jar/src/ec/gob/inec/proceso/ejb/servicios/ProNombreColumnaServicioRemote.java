/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.proceso.ejb.entidades.ProNombreColumna;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */

@Remote
public interface ProNombreColumnaServicioRemote {
   public String crearProNombreColumna (ProNombreColumna proNombreColumna) throws Exception;
   public String editarProNombreColumna (ProNombreColumna proNombreColumna) throws Exception;
   public String eliminarProNombreColumna (Integer idNombreColumna)throws Exception;
   public List<ProNombreColumna> buscarPorCodAnioEjecucion (Integer idCatalogo)throws Exception;  
   public List<ProNombreColumna> buscarPorCodAnioEjecucionYCodTipoarchivo (Integer idCatalogoAnioEjecucion, Integer idCatalogoTipoArchivo)throws Exception;  
   public List<ProNombreColumna> buscarPorCodTipoarchivo (Integer idCatalogoTipoArchivo)throws Exception;
   public List<ProNombreColumna> buscarPorCodAnioEjecucionYCodTipoarchivoYActivos (Integer idCatalogoAnioEjecucion, Integer idCatalogoTipoArchivo)throws Exception;
   public ProNombreColumna buscarPorAnioTipoArchivoYNombre (Integer idAnio)throws Exception;
   public ProNombreColumna buscarPorAnioTipoArchivoYNombre01 (Integer idAnio, Integer idTipoarchivo )throws Exception;
   public ProNombreColumna buscarPorAnioTipoArchivoYNombre02 (Integer idAnio, Integer idTipoarchivo, String nombre )throws Exception;
   public ProNombreColumna buscarUltimoPorCodAnioYCodTipoArchivo(MetCatalogo codAnio, MetCatalogo codTipoArchivo)throws Exception;
   public List<ProNombreColumna> buscarPorCodAnioCodTipoArchivoEsigefCodTipoArchivoDiplaCodTipoDeDatoAnualYComienzaConENE (Integer idAnio, Integer idTipoArchivoEsigef, Integer idTipoArchivoDipla, Integer idTipoDeDatoAnual )throws Exception;
   public List<ProNombreColumna> listarPorAnioAndTipoArchivoAndTipoDato(Integer idAnio, Integer idTipoArchivo, Integer idtipoDato) throws Exception;
   public ProNombreColumna buscarPorId(Integer idNombreColumna) throws Exception;
}
