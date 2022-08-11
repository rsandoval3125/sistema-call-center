/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptEstado;
import ec.gob.inec.captura.ejb.entidades.CaptObservacion;
import ec.gob.inec.captura.ejb.facade.CaptEstadoFacade;
import ec.gob.inec.captura.ejb.facade.CaptObservacionFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dguano
 */
@Stateless
public class CaptObservacionServicio implements CaptObservacionServicioLocal,CaptObservacionServicioRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private CaptObservacionFacade captObservacionDao;
    
    public void crearCaptObservacion(CaptObservacion obs)  throws Exception{
        captObservacionDao.crear(obs);
    }
    public void editarCaptObservacion(CaptObservacion obs)  throws Exception{
        captObservacionDao.editar(obs);
    }
    
    public void eliminarCaptObservacion(CaptObservacion obs) throws Exception{
        captObservacionDao.eliminarGenerico("CaptObservacion", "idObservacion", obs.getIdObservacion());
    }
    
    public List<CaptObservacion> listarObservacionesPorCabecera(Integer idCab) throws Exception{
         Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codCab.idCab", idCab);
        return captObservacionDao.listarPorCampos("CaptObservacion", campos, "idObservacion");
    }
    public List<CaptObservacion> listarObservacionesPorCabecera(Integer idCab,String order) throws Exception{
         Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codCab.idCab", idCab);
        return captObservacionDao.listarPorCampos("CaptObservacion", campos, order);
    }
    public void eliminarObservacionesDeFormulario(Integer codCab) throws Exception{
          captObservacionDao.eliminarGenerico("CaptObservacion", "codCab", codCab);
    }

    @Override
    public List<CaptObservacion> listarObservacionesPorCabYNumDet(Integer codCab, Integer numDet) throws Exception {
        return captObservacionDao.listarObservacionesPorCabYNumDet(codCab, numDet);
    }

    @Override
    public List<CaptObservacion> listarObservacionesPorCabYClave(Integer codCab, String clave) throws Exception {
        return captObservacionDao.listarObservacionesPorCabYClave(codCab, clave);
    }

    @Override
    public List<CaptObservacion> listarObservacionesPorCabYNumDetYTipo(Integer codCab, Integer numDet, String tipo) throws Exception {
        return captObservacionDao.listarObservacionesPorCabYNumDetYTipo(codCab, numDet, tipo);
    }

    @Override
    public List<CaptObservacion> listarObservacionesPorCabYNumDetXTipoDif(Integer codCab, Integer numDet, String tipo) throws Exception {
        return captObservacionDao.listarObservacionesPorCabYNumDetXTipoDif(codCab, numDet, tipo);
    }
}
