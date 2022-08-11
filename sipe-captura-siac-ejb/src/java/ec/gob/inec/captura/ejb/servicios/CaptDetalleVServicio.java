/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.clases.CaptElementoControl;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleV;
import ec.gob.inec.captura.ejb.facade.CaptDetalleVFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dguano
 */
@Stateless
public class CaptDetalleVServicio implements CaptDetalleVServicioLocal, CaptDetalleVServicioRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private CaptDetalleVFacade captDetallevDao;
    private final String ENT = "CaptDetalleV";
    
    public String editarFormCabecera(CaptDetalleV captDetalleV) throws Exception {
        captDetallevDao.editar(captDetalleV);
        return "Se ha editado la " + ENT + ":" + captDetalleV.getDetvId();
    }

    public void crearDetallesVDeFormulario(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numElementos) throws Exception {
        captDetallevDao.crearDetallesVDeFormulario(tipoFormulario, codCab, listaVariables, numElementos);
    }

    public void crearDetallesVDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numNuevoElemento) throws Exception {
        captDetallevDao.reordenarElementosDeCuestionarioAntesDeInsertar(codCab, numNuevoElemento);
        captDetallevDao.crearDetallesVDeFormularioPorElemento(tipoFormulario, codCab, listaVariables, numNuevoElemento);
    }

    public void actualizarDetallesVDeFormularioPorElemento(List<CaptVarV> listaVariables) throws Exception {
        captDetallevDao.actualizarDetallesVDeFormularioPorElemento(listaVariables);
    }

    public void actualizarDetallesVDeFormularioPorElementoParcial(List<CaptVarV> listaVariables, int idxInicial, int idxFinal) throws Exception {
        captDetallevDao.actualizarDetallesVDeFormularioPorElementoParcial(listaVariables, idxInicial, idxFinal);
    }

    public void eliminarDetallesVDeFormularioPorElemento(int idCab, int numElemento) throws Exception {
        captDetallevDao.eliminarElementoDeCuestionarioyReordenar(idCab, numElemento);
        //captDetallevDao.eliminarPor2Campos("CaptDetalleV", "codCab", idCab,"numDet",numElemento);
    }

    public List<CaptVarV> listarVariablesVPorFormulario(int idFormulario) throws Exception {
        return captDetallevDao.listarVariablesVPorFormulario(idFormulario);
    }

    public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElemento(int idCab, int numDet, int idFormulario) throws Exception {
        return captDetallevDao.listarVariablesVPorCabeceraCreadayNumElemento(idCab, numDet, idFormulario);
    }
 public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElementoyRangoVariables(int idCab, int numDet, int idFormulario, int idVarIni, int idVarFin) throws Exception {
        return captDetallevDao.listarVariablesVPorCabeceraCreadayNumElementoyRangoVariables(idCab, numDet, idFormulario,  idVarIni,  idVarFin);
    }
    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCab(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF1PorCab(codCab);
    }

    public List<CaptElementoControl> listarElementosControlMultiF1PorCab(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlMultiF1PorCab(codCab);
    }
public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(int idCab, int numDet, int idFormulario, String identificadores) throws Exception {
        return captDetallevDao.listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(idCab, numDet, idFormulario,identificadores);
    }
    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCabVal(Integer codCab, Integer numDet) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF1PorCabVal(codCab, numDet);
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF3PorCabVal(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF3PorCabVal(codCab);
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF4PorCabVal(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF4PorCabVal(codCab);
    }

    @Override
    public List<CaptElementoControl> listarElementosControlEnsanutF4V(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF4V(codCab);
    }

    @Override
    public List<CaptElementoControl> listarElementosControlEnsanutF2PorCabVal(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF2PorCabVal(codCab);
    }

    @Override
    public List<CaptElementoControl> listarElementosControlEnsanutF5PorCabVal(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF5PorCabVal(codCab);
    }

    @Override
    public List<CaptElementoControl> listarElementosF1Sec6(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosF1Sec6(codCab);
    }

    @Override
    public List<CaptElementoControl> listarElementosF1Sec8(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosF1Sec8(codCab);
    }

    @Override
    public List<CaptElementoControl> listarElementosF1Sec7(Integer codCab, Integer numDet) throws Exception {
        return captDetallevDao.listarElementosF1Sec7(codCab, numDet);
    }

    @Override
    public List<CaptElementoControl> listarElementosValFrm3(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosValFrm3(codCab);
    }

    @Override
    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCabParaValidar(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlEnsanutF1PorCabParaValidar(codCab);
    }

    public List<CaptElementoControl> listarElementosControlFrmConCalCampo(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlFrmConCalCampo(codCab);
    }

    public List<CaptElementoControl> listarElementosControlFrmConCalDigita(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlFrmConCalDigita(codCab);
    }

    public List<CaptElementoControl> listarElementosControlFrmConCalRevisa(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlFrmConCalRevisa(codCab);
    }

    public void updateVaciosFrmEnsanut(Integer codCab) throws Exception {
        captDetallevDao.updateVaciosFrmEnsanut(codCab);
    }
    
    public List<CaptElementoControl> listarElementosControlCodAsisPorCab(Integer codCab) throws Exception {
        return captDetallevDao.listarElementosControlCodAsisPorCab(codCab);
    }

    @Override
    public List<CaptDetalleV> listaValoresVariableXCab(Integer codCab, Integer idVar) throws Exception {
        return captDetallevDao.listaValoresVariableXCab(codCab, idVar);
    }
}
