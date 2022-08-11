/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.clases.CaptElementoControl;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleV;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dguano
 */
@Local
public interface CaptDetalleVServicioLocal {

    public void crearDetallesVDeFormulario(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numElementos) throws Exception;

    public void crearDetallesVDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numNuevoElemento) throws Exception;

    public void actualizarDetallesVDeFormularioPorElemento(List<CaptVarV> listaVariables) throws Exception;

    public void actualizarDetallesVDeFormularioPorElementoParcial(List<CaptVarV> listaVariables, int idxInicial, int idxFinal) throws Exception;

    public void eliminarDetallesVDeFormularioPorElemento(int idCab, int numElemento) throws Exception;

    public List<CaptVarV> listarVariablesVPorFormulario(int idFormulario) throws Exception;

    public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElemento(int idCab, int numDet, int idFormulario) throws Exception;
    
    public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElementoyRangoVariables(int idCab, int numDet, int idFormulario, int idVarIni, int idVarFin) throws Exception;
public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(int idCab, int numDet, int idFormulario, String identificadores) throws Exception;

    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCab(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosControlMultiF1PorCab(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosControlEnsanutF2PorCabVal(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosControlEnsanutF5PorCabVal(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosF1Sec6(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosF1Sec8(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosF1Sec7(Integer codCab, Integer numDet) throws Exception;

    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCabParaValidar(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosValFrm3(Integer codCab) throws Exception;

    public List<CaptElementoControl> listarElementosControlEnsanutF4V(Integer codCab) throws Exception;

    public void updateVaciosFrmEnsanut(Integer codCab) throws Exception;
    
    public List<CaptElementoControl> listarElementosControlCodAsisPorCab(Integer codCab) throws Exception;
    
    public List<CaptDetalleV> listaValoresVariableXCab(Integer codCab , Integer idVar) throws Exception;
    
    public String editarFormCabecera(CaptDetalleV captDetalleV) throws Exception;
}
