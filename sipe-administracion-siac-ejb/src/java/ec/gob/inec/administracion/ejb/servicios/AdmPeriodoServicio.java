/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.administracion.ejb.facade.AdmPeriodoFacade;
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
public class AdmPeriodoServicio implements AdmPeriodoServicioRemote, AdmPeriodoServicioLocal {

    @EJB
    private AdmPeriodoFacade admPeriodoFacade;
    private String ENTIDAD_AdmPeriodo = "AdmPeriodo";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearPeriodo(AdmPeriodo admPeriodo) throws Exception {
        admPeriodoFacade.crear(admPeriodo);
        return "se ha creado el AdmPeriodo";
    }

    @Override
    public String editarPeriodo(AdmPeriodo admPeriodo) throws Exception {
        admPeriodoFacade.editar(admPeriodo);
        return "se ha modificado el AdmPeriodo";
    }

    @Override
    public String eliminarPeriodo(AdmPeriodo admPeriodo) throws Exception {
        admPeriodoFacade.eliminar(admPeriodo);
        return "se ha eliminado el AdmPeriodo";
    }

    @Override
    public List<AdmPeriodo> listarTodo() throws Exception {
        return admPeriodoFacade.listarOrdenada(ENTIDAD_AdmPeriodo, "idPeriodo", "asc");
    }

    @Override
    public List<AdmPeriodo> listarTodosActivos() throws Exception {
        return admPeriodoFacade.listarTodosActivos();
    }
    
    @Override
    public AdmPeriodo buscarXCodigoActivo(Integer codPer) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idPeriodo", codPer);
        campos.put("estadoLogico", true);
        return admPeriodoFacade.buscarPorCampos(ENTIDAD_AdmPeriodo, campos);
    }
    
    @Override
    public List<AdmPeriodo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admPeriodoFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public AdmPeriodo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admPeriodoFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }
}
