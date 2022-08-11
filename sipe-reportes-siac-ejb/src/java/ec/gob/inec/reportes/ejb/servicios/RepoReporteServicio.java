/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import ec.gob.inec.reportes.ejb.facade.RepoReporteFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jaraujo
 */
@Stateless
public class RepoReporteServicio implements RepoReporteServicioRemote, RepoReporteServicioLocal {

    @EJB
    private RepoReporteFacade repoReporteFacade;
    private String ENTIDAD_repoReporteFacade = "RepoReporte";

    @Override
    public String crearReporte(RepoReporte repoReporte) throws Exception {
        repoReporteFacade.crear(repoReporte);
        return "se ha creado el Reporte";
    }

    @Override
    public String editarReporte(RepoReporte repoReporte) throws Exception {
        repoReporteFacade.editar(repoReporte);
        return "se ha modificado el Reporte";
    }

    @Override
    public String eliminarReporte(RepoReporte repoReporte) throws Exception {
        repoReporteFacade.eliminar(repoReporte);
        return "se ha eliminado el Reporte";
    }

    @Override
    public List<RepoReporte> listarTodo() throws Exception {
        return repoReporteFacade.listarOrdenada(ENTIDAD_repoReporteFacade, "idReporte", "asc");
    }

    @Override
    public RepoReporte buscarPorCodigo(Integer idReporte) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("idReporte", idReporte);
        return repoReporteFacade.buscarPorCampos(ENTIDAD_repoReporteFacade, campos);
    }

    @Override
    public List<RepoReporte> listarReportesPorUsuario(String usuario) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("filtro", usuario);
        return repoReporteFacade.listarPorCampos(ENTIDAD_repoReporteFacade, campos, "idReporte");
    }

    @Override
    public RepoReporte buscarPorNombre(String nombre) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("nombre", nombre);
        return repoReporteFacade.buscarPorCampos(ENTIDAD_repoReporteFacade, campos);
    }

    @Override
    public RepoReporte consultaReportePorAlias(String alias) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("campo", alias);
        return repoReporteFacade.buscarPorCampos(ENTIDAD_repoReporteFacade, campos);
    }

    @Override
    public List<RepoReporte> listarTodosActivos() throws Exception {
        return repoReporteFacade.listarTodosActivos();
    }

    @Override
    public List<Object[]> lstCabeceraAsigCargas() throws Exception {
        return repoReporteFacade.lstCabeceraAsigCargas();
    }
    
    @Override
    public List<Object[]> lstCabeceraAsigCargasEQ() throws Exception {
        return repoReporteFacade.lstCabeceraAsigCargasEQ();
    }
    
    public List<Object[]> listarRegistrosSelect(String sentenciaSql) throws Exception{
        return repoReporteFacade.listObjetoNativoGenerico(sentenciaSql);
    }
     public List<Map<String,Object>> listarRegistrosSelectDetalles(String sentenciaSql) throws Exception{
        return repoReporteFacade.listObjetoNativoColumnasYTipo(sentenciaSql);
    }
    
    public String ejecutarSentencia(String sqlNoResults)  throws Exception{
        return repoReporteFacade.ejecQueryNativoGenerico(sqlNoResults);
    }
}
