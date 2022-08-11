/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vespinoza
 */
@Local
public interface SegPaginaServicioLocal {

    public String crearPagina(SegPagina segPagina) throws Exception;

    public String editarPagina(SegPagina segPagina) throws Exception;

    public String eliminarPagina(SegPagina segPagina) throws Exception;

    public List<SegPagina> listarTodo() throws Exception;

    public List<SegPagina> listarPaginasActivasPorAplicacion(Integer idApl) throws Exception;

    public List<SegPagina> listarTodosActivos() throws Exception;

    public List<SegPagina> listarTodosActivosSinUno(Integer idPag) throws Exception;

    public List<SegPagina> buscarPorCampos(Map<String, Object> campos, String orden) throws Exception;

    public List<SegPagina> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public SegPagina buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
}
