/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lponce
 */
@Local
public interface SegAplicacionServicioLocal {

    public String crearAplicacion(SegAplicacion segAplicacion) throws Exception;

    public String editarAplicacion(SegAplicacion segAplicacion) throws Exception;

    public String eliminarAplicacion(SegAplicacion segAplicacion) throws Exception;

    public List<SegAplicacion> listarTodo() throws Exception;

    public boolean existeAplicacion(Integer vApli) throws Exception;

    public SegAplicacion buscarXNombre(String nombre) throws Exception;

    public List<SegAplicacion> listarTodosActivos() throws Exception;

    public List<SegAplicacion> listaAplicacionesPorUsuarioAsignado(Integer idUsuario) throws Exception;

    public List<SegAplicacion> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public SegAplicacion buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
}
