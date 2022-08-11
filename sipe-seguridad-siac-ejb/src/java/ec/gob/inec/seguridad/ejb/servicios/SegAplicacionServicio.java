/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import ec.gob.inec.seguridad.ejb.facade.SegAplicacionFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author lponce
 */
@Stateless
public class SegAplicacionServicio implements SegAplicacionServicioRemote, SegAplicacionServicioLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private SegAplicacionFacade segAplicacionFacade;
    private String ENTIDAD_SEG_APLICACION = "SegAplicacion";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearAplicacion(SegAplicacion segAplicacion) throws Exception {
        segAplicacionFacade.crear(segAplicacion);
        return "se ha creado el Aplicacion";
    }
    
    @Override
    public String editarAplicacion(SegAplicacion segAplicacion) throws Exception {
        segAplicacionFacade.editar(segAplicacion);
        return "se ha modificado el Aplicacion";
    }
    
    @Override
    public String eliminarAplicacion(SegAplicacion segAplicacion) throws Exception {
        segAplicacionFacade.eliminar(segAplicacion);
        return "se ha modificado el Aplicacion";
    }
    
    @Override
    public List<SegAplicacion> listarTodo() throws Exception {
        return segAplicacionFacade.listarOrdenada(ENTIDAD_SEG_APLICACION, "idApl", "asc");
    }
    
    @Override
    public boolean existeAplicacion(Integer vApli) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idApl", vApli);
        return segAplicacionFacade.existePorCampos(ENTIDAD_SEG_APLICACION, campos, "");
    }
    
    @Override
    public SegAplicacion buscarXNombre(String nombre) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("nombre", nombre);
        return segAplicacionFacade.buscarPorCampos(ENTIDAD_SEG_APLICACION, campos);
    }
    
    @Override
    public List<SegAplicacion> listarTodosActivos() throws Exception {
        return segAplicacionFacade.listarTodosActivos();
    }
    
    public List<SegAplicacion> listaAplicacionesPorUsuarioAsignado(Integer idUsuario) throws Exception{
        return segAplicacionFacade.listaAplicacionesPorUsuarioAsignado(idUsuario);
    }
      @Override
    public List<SegAplicacion> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return segAplicacionFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public SegAplicacion buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return segAplicacionFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }
}
