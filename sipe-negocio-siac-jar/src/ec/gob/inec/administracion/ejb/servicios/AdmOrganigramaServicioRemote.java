/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface AdmOrganigramaServicioRemote {

    public String crearOrganigrama(AdmOrganigrama admOrganigrama) throws Exception;

    public String editarOrganigrama(AdmOrganigrama admOrganigrama) throws Exception;

    public String eliminarOrganigrama(AdmOrganigrama admOrganigrama) throws Exception;

    public List<AdmOrganigrama> listarTodo() throws Exception;

    public List<AdmOrganigrama> listarTodosActivos() throws Exception;

    public List<AdmOrganigrama> listarTodosActivosSinUno(Integer idOrganigrama) throws Exception;

    public List<AdmOrganigrama> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public AdmOrganigrama buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public List<AdmOrganigrama> listarTodosActivosPorInstitucion(Integer idInstitucion) throws Exception;
}
