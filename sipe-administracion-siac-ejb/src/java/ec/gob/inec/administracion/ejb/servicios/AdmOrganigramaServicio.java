/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
import ec.gob.inec.administracion.ejb.facade.AdmOrganigramaFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class AdmOrganigramaServicio implements AdmOrganigramaServicioRemote, AdmOrganigramaServicioLocal {

    @EJB
    private AdmOrganigramaFacade admOrganigramaFacade;
    private String ENTIDAD_AdmOrganigrama = "AdmOrganigrama";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearOrganigrama(AdmOrganigrama admOrganigrama) throws Exception {
        admOrganigramaFacade.crear(admOrganigrama);
        return "se ha creado el AdmOrganigrama";
    }

    @Override
    public String editarOrganigrama(AdmOrganigrama admOrganigrama) throws Exception {
        admOrganigramaFacade.editar(admOrganigrama);
        return "se ha modificado el AdmOrganigrama";
    }

    @Override
    public String eliminarOrganigrama(AdmOrganigrama admOrganigrama) throws Exception {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("idOrganigrm", admOrganigrama.getIdOrganigrama());
        admOrganigramaFacade.ejecutarActualizar("delete from AdmOrganigrama where idOrganigrm=:idOrganigrm", parms);
        //  admOrganigramaFacade.eliminar(admOrganigrama);
        return "se ha eliminado el AdmOrganigrama";
    }

    @Override
    public List<AdmOrganigrama> listarTodo() throws Exception {
        return admOrganigramaFacade.listarOrdenada(ENTIDAD_AdmOrganigrama, "idOrganigrama", "asc");
    }

    @Override
    public List<AdmOrganigrama> listarTodosActivos() throws Exception {
        return admOrganigramaFacade.listarTodosActivos();
    }

    @Override
    public List<AdmOrganigrama> listarTodosActivosSinUno(Integer idOrganigrama) throws Exception {
        return admOrganigramaFacade.listarTodosActivosSinUno(idOrganigrama);
    }

    @Override
    public List<AdmOrganigrama> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admOrganigramaFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public AdmOrganigrama buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admOrganigramaFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admOrganigramaFacade.existeEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public List<AdmOrganigrama> listarTodosActivosPorInstitucion(Integer idInstitucion) throws Exception {
        return admOrganigramaFacade.listarTodosActivosPorInstitucion(idInstitucion);
    }

}
