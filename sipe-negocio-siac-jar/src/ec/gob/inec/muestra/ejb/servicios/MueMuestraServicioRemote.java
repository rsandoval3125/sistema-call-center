/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.MueMuestra;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MueMuestraServicioRemote {

    public String crearSeccion(MueMuestra mueMuestra) throws Exception;

    public String editarSeccion(MueMuestra mueMuestra) throws Exception;

    public List<MueMuestra> listarTodo() throws Exception;

    public MueMuestra buscarPorCodigoActivo(Integer vCodMue) throws Exception;

    public List<MueMuestra> listarTodosActivos() throws Exception;

    public List<MueMuestra> listarPorModelos() throws Exception;

    public List<MueMuestra> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public MueMuestra buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public String modificarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public int contarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public MueMuestra existeMuestraAlias(String alias) throws Exception;

}
