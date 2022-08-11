/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface AdmOperacionEstadisticaServicioLocal {

    public String crearOperacionEstadistica(AdmOperacionEstadistica admOperacionEstadistica) throws Exception;

    public String editarOperacionEstadistica(AdmOperacionEstadistica admOperacionEstadistica) throws Exception;

    public String eliminarOperacionEstadistica(AdmOperacionEstadistica admOperacionEstadistica) throws Exception;

    public List<AdmOperacionEstadistica> listarTodo() throws Exception;

    public List<AdmOperacionEstadistica> listarTodosActivos() throws Exception;

    public List<AdmOperacionEstadistica> listarTodosActivosSinUno(Integer idOpeEst) throws Exception;

    public List<AdmOperacionEstadistica> listarTodosActivosPorInstitucionYOrganigrama(Integer idInstitucion, Integer idOrganigrama) throws Exception;
    
    public AdmOperacionEstadistica buscarCatalogoXId(Integer idOpeEst) throws Exception;
    
    public AdmOperacionEstadistica obtenerAdmOperacionEstadisticaPorSigla(String sigla) throws Exception;
}
