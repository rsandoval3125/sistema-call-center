/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author jaraujo
 */
@Remote
public interface RepoReporteServicioRemote {

    public String crearReporte(RepoReporte repoReporte) throws Exception;

    public String editarReporte(RepoReporte repoReporte) throws Exception;

    public String eliminarReporte(RepoReporte repoReporte) throws Exception;

    public List<RepoReporte> listarTodo() throws Exception;

    public RepoReporte buscarPorCodigo(Integer idReporte) throws Exception;

    public List<RepoReporte> listarReportesPorUsuario(String usuario) throws Exception;

    public RepoReporte buscarPorNombre(String nombre) throws Exception;

    public RepoReporte consultaReportePorAlias(String alias) throws Exception;

    public List<RepoReporte> listarTodosActivos() throws Exception;

    public List<Object[]> lstCabeceraAsigCargas() throws Exception;
    
    public List<Object[]> lstCabeceraAsigCargasEQ() throws Exception;
    
    public List<Object[]> listarRegistrosSelect(String sentenciaSql) throws Exception;
    
     public List<Map<String,Object>> listarRegistrosSelectDetalles(String sentenciaSql) throws Exception;
    public String ejecutarSentencia(String sqlNoResults)  throws Exception;

}
