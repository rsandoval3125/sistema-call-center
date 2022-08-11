/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.captura.clases.CaptFilaH;
import ec.gob.inec.captura.clases.CaptSeccionH;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.ejb.entidades.OperadorAsignacion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rmoreano
 */
@Remote
public interface CaptDetalleHServicioRemote {

    public void crearDetalleH(CaptDetalleH captDetalleH) throws Exception;

    public void editarDetalleH(CaptDetalleH captDetalleH) throws Exception;

    public void eliminarDetalleH(CaptDetalleH captDetalleH) throws Exception;

    public List<CaptDetalleH> listarTodo() throws Exception;

    public String crearFormDetalle(CaptDetalleH captDetalle) throws Exception;

    public String editarFormDetalle(CaptDetalleH captDetalle) throws Exception;

    public String eliminarFormDetalle(Integer idDetalle) throws Exception;

    public List<CaptDetalleH> listarFormDetPorCodCab(CaptCabecera captCabecera) throws Exception;

    public boolean existeFormPorEdificio(String clave, String vEdif) throws Exception;

    public int contarFormPorEdificio(String clave, String vEdif) throws Exception;

    public void crearDetallesHDeFormulario(String tipoFormulario, Integer codCab, List<CaptFilaH> listaVariables, Integer numElementos) throws Exception;

    public void crearDetallesHDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptFilaH> listaVariables, Integer numNuevoElemento) throws Exception;

    public void actualizarDetallesHDeFormularioPorSeccion(CaptSeccionH seccionH) throws Exception;

    public void actualizarDetallesHDeFormulario(List<CaptSeccionH> listaSeccionH) throws Exception;

    public void eliminarDetallesHDeFormularioyElemento(Integer codCab, Integer numElemento) throws Exception;

    public List<CaptSeccionH> listarSeccionesHPorListaFilasFormulario(List<CaptFilaH> listaFilasHFormulario) throws Exception;

    public List<CaptFilaH> listarFilasHPorFormulario(int idFormulario) throws Exception;

    public List<CaptFilaH> listarFilasHPorCabeceraCreadayElemento(int idCab, int numDet) throws Exception;

    public List<CaptFilaH> listarRegistrosPorCabPrecenso(Integer codCab) throws Exception;

    public void reordenamientoRegistrosPrecenso(Integer codCab, Integer numDetActual, Integer ordenActual, String colsOrdena, String tipoReordenamiento, String signoReord) throws Exception;

    public void eliminarDetallesHPorId(Integer idDeth) throws Exception;

    public void eliminarYReordenarRegistrosPrecenso(CaptFilaH registroAEliminar, String tipoReg) throws Exception;

    public List<CaptDetalleH> listarDetHPorCab(Integer codCab) throws Exception;

    public CaptDetalleH buscarDetHPorId(Integer idDeth) throws Exception;

     public List<CaptDetalleH> listarDetHPorCabOrderIpc(Integer codCab) throws Exception;
     
     public List<CaptDetalleH> listarDetHPorCabXSeccion(Integer codCab, Integer codSeccion) throws Exception;
     
     public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
     
     public List<CaptDetalleH> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
     
     public List<CaptDetalleH> listarIncidencias(String varEstado, String prov, String can, String ciu) throws Exception;
     
     public List<CaptDetalleH> listarAsignados(String varEstado) throws Exception;
     
     public List<CaptDetalleH> listarIncidenciasxFiltro(String prov, String can, String ciu) throws Exception;
     
     public List<OperadorAsignacion> contarAsignacionPorUsuario(Integer codProvincia) throws Exception;  
     
//     public Integer buscarUsuarioRandom() throws Exception;    
    
}
