/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmDispositivo;
import ec.gob.inec.administracion.ejb.facade.AdmDispositivoFacade;
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
public class AdmDispositivoServicio implements AdmDispositivoServicioRemote, AdmDispositivoServicioLocal {

    @EJB
    private AdmDispositivoFacade admDispositivoFacadeDao;
    private String ENTIDAD_admDispositivoFacadeDao = "AdmDispositivo";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearDispositivo(AdmDispositivo admDispositivo) throws Exception {
        admDispositivoFacadeDao.crear(admDispositivo);
        return "se ha creado el AdmDispositivo";
    }

    @Override
    public String editarDispositivo(AdmDispositivo admDispositivo) throws Exception {
        admDispositivoFacadeDao.editar(admDispositivo);
        return "se ha modificado el AdmDispositivo";
    }

    @Override
    public String eliminarDispositivo(AdmDispositivo admDispositivo) throws Exception {
        admDispositivoFacadeDao.eliminar(admDispositivo);
        return "se ha eliminado el AdmDispositivo";
    }

    @Override
    public List<AdmDispositivo> listarTodo() throws Exception {
        return admDispositivoFacadeDao.listarOrdenada(ENTIDAD_admDispositivoFacadeDao, "idDispositivo", "asc");
    }

    @Override
    public List<AdmDispositivo> listarTodosActivos() throws Exception {
        return admDispositivoFacadeDao.listarTodosActivos();
    }

    @Override
    public AdmDispositivo buscarPorCodigoActivo(Integer codDis) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idDispositivo", codDis);
        campos.put("estadoLogico", true);
        return admDispositivoFacadeDao.buscarPorCampos(ENTIDAD_admDispositivoFacadeDao, campos);
    }

    @Override
    public List<AdmDispositivo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admDispositivoFacadeDao.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public AdmDispositivo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admDispositivoFacadeDao.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return admDispositivoFacadeDao.existeEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public Integer tieneCargasPendientes(String serie) throws Exception {
        return admDispositivoFacadeDao.tieneCargasPendientes(serie);
    }

    @Override
    public Integer tieneMiembroDeEquipo(String serie) throws Exception {
        return admDispositivoFacadeDao.tieneMiembroDeEquipo(serie);
    }
}
