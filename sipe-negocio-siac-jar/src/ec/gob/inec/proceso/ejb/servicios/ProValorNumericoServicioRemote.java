/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.proceso.ejb.entidades.ProCedulaDipla;
import ec.gob.inec.proceso.ejb.entidades.ProNombreColumna;
import ec.gob.inec.proceso.ejb.entidades.ProValorNumerico;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProValorNumericoServicioRemote {
    public String crearValorNumerico (ProValorNumerico valorNumerico) throws Exception;
    public String editarValorNumerico (ProValorNumerico valorNumerico) throws Exception;
    public String eliminarValorNumerico (Integer idValorNumerico) throws Exception;
    public ProValorNumerico buscarValorNumerico(Integer idValorNumerico) throws Exception;
    public void eliminarValorNumericoPorIdCedulaDipla(Integer idCedulaDipla) throws Exception;
    public boolean tieneRegistrosPorIdCedulaDipla(Integer idCedulaDipla) throws Exception;
    public List<ProValorNumerico> buscarPorIdCedulaDiplaTotalSumatoriaValidacion(Integer idCedulaDipla) throws Exception;
    public ProValorNumerico actualizar (ProValorNumerico valorNumerico)throws Exception;
    public void crearSumatoria(Integer idCeduldaDipla) throws Exception;
    public List<ProValorNumerico> buscarPorIdCedulaDiplaYTotalTrue(Integer idCedulaDipla) throws Exception;
    public  ProValorNumerico buscarSumatoria(ProNombreColumna codNombreColumna, MetCatalogo codNumeroEjercicio, ProCedulaDipla codCedulaDipla, Integer numeroColumna) throws Exception;
    public void crearDiplaConsolidada(Integer idCeduldaDipla, Integer idCedulaDiplaNueva) throws Exception;
    public void formula1 (Integer idCedulaDiplaAnterior, Integer idNombreColumnaAnterior, Integer numeroColumnaAnterior, Integer idCedulaDiplaNuevo, Integer idNombreColumnaNuevo, Integer numeroColumnaNuevo ) throws Exception;
    public void formulaSumatoriaMeses (Integer idCedulaDiplaAnterior, String idsMeses, Integer idCedulaDiplaNuevo, Integer idNombreColumnaNuevo, Integer numeroColumnaNuevo ) throws Exception;
    public void formulaRestaDosColumnas (Integer idCedulaDipla, Integer idNombreColumnaMinuendo,Integer idNombreColumnaSustraendo, Integer idNombreColumnaNuevo, Integer numeroColumnaNuevo  ) throws Exception;
    public List<ProValorNumerico> formulaTotalesNoValidos (Integer idNumeroDeEjercicio )throws Exception;
    public ProValorNumerico buscarPorNombreColumnaAndNumeroEjercicioAndCedulaDiplaAndBaseConsolidada (Integer idNombreColumna, Integer idNumeroEjercicio, Integer idCedulaDipla, Integer idBaseConsolidada) throws Exception;
    public List<Integer> listaIdBaseConsolidadaDiferentesvalores(Integer idPrimero, Integer idSegundo) throws Exception;
    public List<String> leerBCdesdeBD (StringBuilder sql) throws Exception;
    public void actualizarBaseConsolidadaInicial(Integer idBaseConsolidadaInicial, Integer idCedulaDipla, Integer orden) throws Exception;
    public List<ProValorNumerico> listaPorOrdenAndCedulaDipla(Integer orden , Integer idCedulaDipla) throws Exception;
}
