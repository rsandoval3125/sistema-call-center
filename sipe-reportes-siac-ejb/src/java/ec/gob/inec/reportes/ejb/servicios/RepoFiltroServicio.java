/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoFiltro;
import ec.gob.inec.reportes.ejb.facade.RepoFiltroFacade;
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
public class RepoFiltroServicio implements RepoFiltroServicioRemote, RepoFiltroServicioLocal {

    @EJB
    private RepoFiltroFacade repoFiltroFacade;
    private String ENTIDAD_repoFiltroFacade = "RepoFiltro";

    @Override
    public String crearFiltro(RepoFiltro repoFiltro) throws Exception {
        repoFiltroFacade.crear(repoFiltro);
        return "se ha creado el Filtro";
    }

    @Override
    public String editarFiltro(RepoFiltro repoFiltro) throws Exception {
        repoFiltroFacade.edit(repoFiltro);
        return "se ha modificado el Filtro";
    }

    @Override
    public String eliminarFiltro(RepoFiltro repoFiltro) throws Exception {
        repoFiltroFacade.eliminar(repoFiltro);
        return "se ha eliminado el Filtro";
    }

    @Override
    public List<RepoFiltro> listarTodo() throws Exception {
        return repoFiltroFacade.listarOrdenada(ENTIDAD_repoFiltroFacade, "idFiltro", "asc");
    }

    @Override
    public RepoFiltro buscarPorCodigo(Integer idFiltro) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("idFiltro", idFiltro);
        return repoFiltroFacade.buscarPorCampos(ENTIDAD_repoFiltroFacade, campos);
    }

    @Override
    public RepoFiltro buscarPorNombre(String nombre) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("nombre", nombre);
        return repoFiltroFacade.buscarPorCampos(ENTIDAD_repoFiltroFacade, campos);
    }

    @Override
    public RepoFiltro buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return repoFiltroFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public List<Object[]> listarEjecutarConsulta(String select) throws Exception {
        return repoFiltroFacade.listarEjecutarConsulta(select);
    }

    @Override
    public List<Object[]> listarFiltrosSeparados(int param1, int param2, String clave) throws Exception {
        return repoFiltroFacade.listarFiltrosSeparados(param1, param2, clave);
    }

    @Override
    public List<Object[]> listarEquiposSeparados() throws Exception {
        return repoFiltroFacade.listarEquiposSeparados();
    }

    @Override
    public List<Object[]> listarFiltrosSeparados2020(int param1, int param2, String clave) throws Exception {
        return repoFiltroFacade.listarFiltrosSeparados2020(param1, param2, clave);
    }

}
