/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.clases.CaptInfoMuestra;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface CaptCargaControlServicioLocal {

    public String crearCargaControl(CaptCargaControl captCargaControl) throws Exception;

    public String editarCargaControl(CaptCargaControl captCargaControl) throws Exception;

    public String eliminarCargaControl(CaptCargaControl captCargaControl) throws Exception;

    public List<CaptCargaControl> listarTodo() throws Exception;

    public void modificarEstadoCargaControl(CaptCargaControl captCargaControl, Integer nuevoEstado) throws Exception;

    public List<CaptCargaControl> listarCargaPendientePorParametros(Map<String, Object> parametros) throws Exception;

    public List<CaptCargaControl> listarCargaPendienteReemplazo(Map<String, Object> parametros) throws Exception;

    public CaptCargaControl obtenerCargaControlPorId(Integer idCarCon) throws Exception;

    public CaptInfoMuestra obtenerInfMuestraHogarPorIdMueDet(Integer idMueDet) throws Exception;

    public CaptCargaControl listarCaptCargaControlPorClaveCtr12PorFor(String clave, String control1, String control2, Integer codFormulario) throws Exception;

    public List<CaptCargaControl> listarPorClaveCtr12PorMuestra(String clave, String control1, String control2, Integer codMueDet) throws Exception;

    public void modificarEstadoLogicoCargaControl(CaptCargaControl captCargaControl, boolean estadoLogico) throws Exception;

    public List<CaptCargaControl> listarCaptCargaControlPorClaveCtrPorFor(String clave, String control1, String control2, Integer codFormulario) throws Exception;

    public List<CaptCargaControl> listarCaptCargaControlPorClaveCtr3PorFor(String clave, String control1, String control2, String control3, Integer codFormulario) throws Exception;

    public List<CaptCargaControl> listarPorClaveCtr1PorMuestraPorFrm(String clave, String control1, Integer codMueDet, Integer codFormulario) throws Exception;

    public List<CaptCargaControl> listarCargaRepetidaPrecenso(List<String> listaClavesAGenerar, String tipo) throws Exception;

    public CaptCargaControl obtenerCargaControlPorClave2020(String clave2020, String codFormulario) throws Exception;

    public List<CaptCargaControl> listarCargaPorClaveFormularioLike(String identificadorDPA, String codFormulario, String tipo, String aliasMuestra) throws Exception;

    public List<String> listarConglomeradosPorUsuario(Integer codUsuario) throws Exception;

    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception ;
    
    public List<CaptCargaControl> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
}
