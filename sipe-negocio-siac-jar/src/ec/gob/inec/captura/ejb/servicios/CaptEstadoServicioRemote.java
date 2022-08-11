/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptEstado;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface CaptEstadoServicioRemote {
    public void crearCaptEstado(CaptEstado estado)  throws Exception;
    public void editarCaptEstado(CaptEstado estado)  throws Exception;
    public void eliminarCaptEstado(CaptEstado estado) throws Exception;
    public List<CaptEstado> listarEstadosPorCabecera(Integer idCab) throws Exception;
    public void eliminarEstadosDeFormulario(Integer codCab) throws Exception;
    public void eliminarEstadosDeFormularioPorElemento(Integer codCab, Integer numElemento) throws Exception;
    public void crearEstadosInicialesPorCabeceraYElementos(Integer codCab,String clave,Integer numElementos) throws Exception;
    public List<CaptEstado> listarEstadoPorCabYNumDet(Integer codCab, Integer numDet) throws Exception;
}
