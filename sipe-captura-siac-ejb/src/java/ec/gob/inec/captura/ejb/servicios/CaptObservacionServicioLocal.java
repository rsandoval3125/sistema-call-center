/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptObservacion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author dguano
 */
@Local
public interface CaptObservacionServicioLocal {
    
    public void crearCaptObservacion(CaptObservacion obs)  throws Exception;
    public void editarCaptObservacion(CaptObservacion obs)  throws Exception;
    public void eliminarCaptObservacion(CaptObservacion obs) throws Exception;
    public List<CaptObservacion> listarObservacionesPorCabecera(Integer idCab) throws Exception;
    public void eliminarObservacionesDeFormulario(Integer codCab) throws Exception;
    public List<CaptObservacion> listarObservacionesPorCabecera(Integer idCab,String order) throws Exception;
    public List<CaptObservacion> listarObservacionesPorCabYNumDet(Integer codCab, Integer numDet) throws Exception;
    public List<CaptObservacion> listarObservacionesPorCabYClave(Integer codCab, String clave) throws Exception;
    public List<CaptObservacion> listarObservacionesPorCabYNumDetYTipo(Integer codCab, Integer numDet, String tipo) throws Exception;
    public List<CaptObservacion> listarObservacionesPorCabYNumDetXTipoDif(Integer codCab, Integer numDet, String tipo) throws Exception;
}
