/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProValorTexto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProValorTextoServicioRemote {
    public String crearValorTexto (ProValorTexto valorTexto) throws Exception;
    public String editarValorTexto (ProValorTexto valorTexto) throws Exception;
    public String eliminarValorTexto (Integer idValorTexto) throws Exception;
    public ProValorTexto buscarValorTexto(Integer idValorTexto) throws Exception;
    public void eliminarPorFila (Integer Fila) throws Exception;
    public void eliminarValorTextoPorIdCedulaDipla(Integer idCedulaDipla) throws Exception;
    public boolean tieneRegistrosPorIdCedulaDipla(Integer idCedulaDipla) throws Exception;
    public List<ProValorTexto> listaValortextoPorIdZonalIdEstructuraIdPartidaIdFuenteDeFinanciamiento(Integer idZonal, Integer idEstructura,Integer idPartida, Integer idFuenteDeFinanciamiento) throws Exception;
    public ProValorTexto buscarPorBaseConsolidadaInicialAndCedulaDiplaAndNumeroEjercicioAndNombreColumna(Integer idBaseConsolidadaInicial, Integer idCedulaDipla, Integer idNumeroEjercicio, Integer idNombreColumna) throws Exception;
    public List<ProValorTexto> listaPorOrdenAndCedulaDipla(Integer orden , Integer idCedulaDipla) throws Exception;
    public Integer numeroFilas(Integer idCedulaDipla)throws Exception;
    public void actualizarBaseConsolidadaInicial(Integer idBaseConsolidadaInicial, Integer idCedulaDipla, Integer orden) throws Exception;
    public List<ProValorTexto> listaSinBaseConsolidadaInicial(Integer idNumeroEjercicio, Integer idNombreColumna)throws Exception;
   public ProValorTexto buscarCedulaDiplaAndNumeroEjercicioAndNombreColumnaAndOrden(Integer idCedulaDipla, Integer idNumeroEjercicio, Integer idNombreColumna, Integer orden) throws Exception;
   
}
