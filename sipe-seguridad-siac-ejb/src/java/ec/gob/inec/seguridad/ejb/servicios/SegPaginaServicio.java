/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajoDetalle;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.facade.SegPaginaFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class SegPaginaServicio implements SegPaginaServicioRemote, SegPaginaServicioLocal {

    @EJB
    private SegPaginaFacade segPaginaFacade;
    private String ENTIDAD_segPaginaFacade = "SegPagina";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearPagina(SegPagina segPagina) throws Exception {
        segPaginaFacade.crear(segPagina);
        return "se ha creado el Pagina";
    }

    @Override
    public String editarPagina(SegPagina segPagina) throws Exception {
        segPaginaFacade.editar(segPagina);
        return "se ha modificado el Pagina";
    }

    @Override
    public String eliminarPagina(SegPagina segPagina) throws Exception {
        segPaginaFacade.eliminar(segPagina);
        return "se ha modificado el Pagina";
    }

    @Override
    public List<SegPagina> listarTodo() throws Exception {
        return segPaginaFacade.listarOrdenada(ENTIDAD_segPaginaFacade, "idPag", "asc");
    }

    @Override
    public List<SegPagina> listarPaginasActivasPorAplicacion(Integer idApl) throws Exception {
        return segPaginaFacade.listarPaginasActivasPorAplicacion(idApl);
    }

    @Override
    public List<SegPagina> listarTodosActivos() throws Exception {
        return segPaginaFacade.listarTodosActivos();
    }

    @Override
    public List<SegPagina> listarTodosActivosSinUno(Integer idPag) throws Exception {
        return segPaginaFacade.listarTodosActivosSinUno(idPag);
    }

    @Override
    public List<SegPagina> buscarPorCampos(Map<String, Object> campos, String orden) throws Exception {
        return segPaginaFacade.listarPorCampos(ENTIDAD_segPaginaFacade, campos, orden);
    }

    @Override 
    public List<SegPagina> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return segPaginaFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public SegPagina buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return segPaginaFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }
}
