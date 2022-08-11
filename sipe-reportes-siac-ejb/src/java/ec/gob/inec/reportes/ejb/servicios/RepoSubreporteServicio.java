/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import ec.gob.inec.reportes.ejb.entidades.RepoSubreporte;
import ec.gob.inec.reportes.ejb.facade.RepoSubreporteFacade;
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
public class RepoSubreporteServicio implements RepoSubreporteServicioRemote, RepoSubreporteServicioLocal {

    @EJB
    private RepoSubreporteFacade repoSubreporteFacade;
    private String ENTIDAD_repoSubreporteFacade = "RepoSubreporte";

    @Override
    public String crearSubreporte(RepoSubreporte repoSubreporte) throws Exception {
        repoSubreporteFacade.crear(repoSubreporte);
        return "se ha creado el Subreporte";
    }

    @Override
    public String editarSubreporte(RepoSubreporte repoSubreporte) throws Exception {
        repoSubreporteFacade.editar(repoSubreporte);
        return "se ha modificado el Subreporte";
    }

    @Override
    public String eliminarSubreporte(RepoSubreporte repoSubreporte) throws Exception {
        repoSubreporteFacade.eliminar(repoSubreporte);
        return "se ha eliminado el Subreporte";
    }

    @Override
    public List<RepoSubreporte> listarTodo() throws Exception {
        return repoSubreporteFacade.listarOrdenada(ENTIDAD_repoSubreporteFacade, "idSubr", "asc");
    }

    @Override
    public List<RepoSubreporte> listarSubreportes(RepoReporte codReporte) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("codReporte", codReporte);
        //return repoSubreporteFacade.listarPorCampoOrdenada(ENTIDAD_repoSubreporteFacade, codReporte, codReporte, "orden", "asc");
        return repoSubreporteFacade.listarPorCampos(ENTIDAD_repoSubreporteFacade, campos, "orden");
    }

    @Override
    public Object[] ejecutarFuncion(String procedimiento, String argumento) throws Exception {
        //localizamos el procedimiento a ejecutar y pasamos los filtros seleccionados a los parametros sel sql.
        //Se forma el select del reporte a obtener. Recuperamos el nombre de la conexión de la base local, el select formado, 
        //el nombre de la tabla temporal a crear tablatemporal y el sql de la tabla temporal a borrar.
        return repoSubreporteFacade.ejecutarFuncion(procedimiento, argumento);
    }

    @Override
    public RepoSubreporte buscarPorProcedimiento(RepoProcedimiento codProc) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("codProc", codProc);
        return repoSubreporteFacade.buscarPorCampos(ENTIDAD_repoSubreporteFacade, campos);
    }
}
