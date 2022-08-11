/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface CaptCargaControlEquipoServicioRemote {

    public String crearCargaControlEquipo(CaptCargaControlEquipo captCargaControlEquipo) throws Exception;

    public String editarCargaControlEquipo(CaptCargaControlEquipo captCargaControlEquipo) throws Exception;

    public String eliminarCargaControlEquipo(CaptCargaControlEquipo captCargaControlEquipo) throws Exception;

    public List<CaptCargaControlEquipo> listarTodo() throws Exception;

    public List<CaptCargaControlEquipo> listarCargaEquipoPorParametros(Map<String, Object> campos) throws Exception;

    public List<CaptCargaControlEquipo> listarPorCodCarCon(Integer codCarCon) throws Exception;

    public List<CaptCargaControlEquipo> listarPorCodigosCargaTrabajo(List<Integer> codigos) throws Exception;

    public List<CaptCargaControlEquipo> listarPorUsuarioYOperativo(Integer codUsuario, Integer codOperativo) throws Exception;

    public List<CaptCargaControlEquipo> listarPorUsuarioYOperativoYClaveSector(Integer codUsuario, Integer codOperativo, String claveSector) throws Exception;

    public List<CaptCargaControlEquipo> listarPorRolOperativoYClaveSector(String aliasRol, Integer codOperativo, String claveSector) throws Exception;
 
    /*  
   Metodos para el uso de SQL nativo D.A.01/08/2019
     */
    public String ejecQueryNativoGenerico(String sql) throws Exception;

    public List<Object[]> listarObjGenerico(String sql) throws Exception;
}
