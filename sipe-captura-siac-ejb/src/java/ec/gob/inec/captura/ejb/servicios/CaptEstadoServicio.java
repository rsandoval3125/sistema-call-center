/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptEstado;
import ec.gob.inec.captura.ejb.facade.CaptEstadoFacade;
import java.util.Date;
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
public class CaptEstadoServicio implements CaptEstadoServicioLocal , CaptEstadoServicioRemote{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private CaptEstadoFacade captEstadoDao;
    
    public void crearCaptEstado(CaptEstado estado)  throws Exception{
        captEstadoDao.crear(estado);
    }
    public void editarCaptEstado(CaptEstado estado)  throws Exception{
        captEstadoDao.editar(estado);
    }
    
    public void eliminarCaptEstado(CaptEstado estado) throws Exception{
        captEstadoDao.eliminarGenerico("CaptEstado", "idEstado", estado.getIdEstado());
    }
    
    public List<CaptEstado> listarEstadosPorCabecera(Integer idCab) throws Exception{
         Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codCab.idCab", idCab);
        return captEstadoDao.listarPorCampos("CaptEstado", campos, "numDet");
    }
    
    public void eliminarEstadosDeFormulario(Integer codCab) throws Exception{
          captEstadoDao.eliminarGenerico("CaptEstado", "codCab", codCab);
    }
    
    public void eliminarEstadosDeFormularioPorElemento(Integer codCab, Integer numElemento) throws Exception{
          captEstadoDao.eliminarPor2Campos("CaptEstado", "codCab", codCab,"numDet",numElemento);
    }
    
    public void crearEstadosInicialesPorCabeceraYElementos(Integer codCab,String clave,Integer numElementos) throws Exception{
        for(int i=1;i<=numElementos;i++){
            CaptEstado es=new CaptEstado();
            es.setClave(clave);
            es.setCodCab(new CaptCabecera(codCab));
            es.setEstado("C");
            es.setNumDet(i);
            es.setFechahoraEdicion(new Date());
            this.crearCaptEstado(es);
        }
    }
    
    @Override
    public List<CaptEstado> listarEstadoPorCabYNumDet(Integer codCab, Integer numDet) throws Exception {
        return captEstadoDao.listarEstadoPorCabYNumDet(codCab, numDet);
    }
}
