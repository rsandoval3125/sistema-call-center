/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author rmoreano
 */
@Local
public interface CaptCabeceraServicioLocal {

    public String crearFormCabecera(CaptCabecera captCabecera) throws Exception;

    public String editarFormCabecera(CaptCabecera captCabecera) throws Exception;

    public String eliminarFormCabecera(Integer idCabecera) throws Exception;

    public CaptCabecera buscarFormCabPorClave(String claveForm) throws Exception;

    public CaptCabecera buscarFormCabPorId(Integer pidCab) throws Exception;

    public List<CaptCabecera> listarFormCabPorClave(String calveForm) throws Exception;

    public List<CaptCabecera> listarCabecerasPorParametros(Map<String, Object> parametros) throws Exception;

    public CaptCabecera crearCaptCabecera(CaptCabecera captCabecera) throws Exception;

    public CaptCabecera editarCaptCabecera(CaptCabecera captCabecera) throws Exception;

    public CaptCabecera listarCaptCabeceraPorId(Integer idCab) throws Exception;

    public CaptCabecera buscarPorCodCarCon(Integer codCarCon) throws Exception;

    public List<CaptCabecera> listarFormulariosRepetidosPrecenso(List<String> listaClavesAGenerar) throws Exception;

    public List<Object[]> omisionesF1(Integer idCab) throws Exception;

    public List<Object[]> omisionesF3(Integer idCab) throws Exception;

    public List<Object[]> omisionesF4(Integer idCab) throws Exception;

    public List<Object[]> listarVarCatPorFormulario(Integer idFormulario) throws Exception;

    public List<Object[]> listarVarCatFueraRango(Integer idCab, Integer idVar) throws Exception;

    public List<Object[]> listarVarCatFueraRangoF1(Integer idCab, Integer idVar, Integer numDet) throws Exception;

    public List<Object[]> omisionesF2(Integer idCab) throws Exception;

    public List<Object[]> omisionesF5(Integer idCab) throws Exception;

    public CaptCabecera buscarFormCabPorCodCarConYCodForm(Object codCarCon, Object codFormulario, String nomRol) throws Exception;

    public String trasladarInfDetVHaciaBasePorIdCab(String nombreTablaBase, Integer codCab) throws Exception;

    public String obtenerValidacionIntegridadPorBaseIdCab(String nombreTablaBase, Integer codCab) throws Exception;

    public CaptCabecera buscarCabeceraPorClaveInfo4(String claveForm, String columna) throws Exception;

    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception ;
    
    public List<CaptCabecera> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

}
