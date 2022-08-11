/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoFiltro;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jaraujo
 */
@Local
public interface RepoFiltroServicioLocal {

    public String crearFiltro(RepoFiltro repoFiltro) throws Exception;

    public String editarFiltro(RepoFiltro repoFiltro) throws Exception;

    public String eliminarFiltro(RepoFiltro repoFiltro) throws Exception;

    public List<RepoFiltro> listarTodo() throws Exception;

    public RepoFiltro buscarPorCodigo(Integer idFiltro) throws Exception;

    public RepoFiltro buscarPorNombre(String nombre) throws Exception;

    public RepoFiltro buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public List<Object[]> listarEjecutarConsulta(String select) throws Exception;

    public List<Object[]> listarFiltrosSeparados(int param1, int param2, String clave) throws Exception;

    public List<Object[]> listarFiltrosSeparados2020(int param1, int param2, String clave) throws Exception;

    public List<Object[]> listarEquiposSeparados() throws Exception;
}
