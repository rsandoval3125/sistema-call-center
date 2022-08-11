/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.facade.AdmOperacionEstadisticaFacade;
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
public class AdmOperacionEstadisticaServicio implements AdmOperacionEstadisticaServicioRemote, AdmOperacionEstadisticaServicioLocal {

    @EJB
    private AdmOperacionEstadisticaFacade admOperacionEstadisticaFacade;
    private String ENTIDAD_AdmOperacionEstadistica = "AdmOperacionEstadistica";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearOperacionEstadistica(AdmOperacionEstadistica admOperacionEstadistica) throws Exception {
        admOperacionEstadisticaFacade.crear(admOperacionEstadistica);
        return "se ha creado el AdmOperacionEstadistica";
    }

    @Override
    public String editarOperacionEstadistica(AdmOperacionEstadistica admOperacionEstadistica) throws Exception {
        admOperacionEstadisticaFacade.editar(admOperacionEstadistica);
        return "se ha modificado el AdmOperacionEstadistica";
    }

    @Override
    public String eliminarOperacionEstadistica(AdmOperacionEstadistica admOperacionEstadistica) throws Exception {
        admOperacionEstadisticaFacade.eliminar(admOperacionEstadistica);
        return "se ha eliminado el AdmOperacionEstadistica";
    }

    @Override
    public List<AdmOperacionEstadistica> listarTodo() throws Exception {
        return admOperacionEstadisticaFacade.listarOrdenada(ENTIDAD_AdmOperacionEstadistica, "idOpeEst", "asc");
    }

    @Override
    public List<AdmOperacionEstadistica> listarTodosActivos() throws Exception {
        return admOperacionEstadisticaFacade.listarTodosActivos();
    }

    @Override
    public List<AdmOperacionEstadistica> listarTodosActivosSinUno(Integer idOpeEst) throws Exception {
        return admOperacionEstadisticaFacade.listarTodosActivosSinUno(idOpeEst);
    }

    @Override
    public List<AdmOperacionEstadistica> listarTodosActivosPorInstitucionYOrganigrama(Integer idInstitucion, Integer idOrganigrama) throws Exception {
        return admOperacionEstadisticaFacade.listarTodosActivosPorInstitucionYOrganigrama(idInstitucion, idOrganigrama);
    }
    
    @Override
    public AdmOperacionEstadistica buscarCatalogoXId(Integer idOpeEst) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idOpeEst", idOpeEst);
        return admOperacionEstadisticaFacade.buscarPorCampos(ENTIDAD_AdmOperacionEstadistica, campos);
    }
    
     public AdmOperacionEstadistica obtenerAdmOperacionEstadisticaPorSigla(String sigla) throws Exception {
         
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("estadoLogico", true);
        campos.put("sigla", sigla);
        List<AdmOperacionEstadistica> lst= admOperacionEstadisticaFacade.listarPorCampos(ENTIDAD_AdmOperacionEstadistica, campos,"sigla");
        if(!lst.isEmpty()){
            return lst.get(0);
        }else{
            return null;
        }


     }
}
