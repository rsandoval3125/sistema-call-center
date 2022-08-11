/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmTabla;
import ec.gob.inec.administracion.ejb.facade.AdmTablaFacade;
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
public class AdmTablaServicio implements AdmTablaServicioRemote, AdmTablaServicioLocal {

    @EJB
    private AdmTablaFacade admTablaFacadeDao;
    private String ENTIDAD_admTablaFacadeDao = "AdmTabla";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearTabla(AdmTabla admTabla) throws Exception {
        admTablaFacadeDao.crear(admTabla);
        return "se ha creado el AdmTabla";
    }

    @Override
    public String editarTabla(AdmTabla admTabla) throws Exception {
        admTablaFacadeDao.editar(admTabla);
        return "se ha modificado el AdmTabla";
    }

    @Override
    public String eliminarTabla(AdmTabla admTabla) throws Exception {
        admTablaFacadeDao.eliminar(admTabla);
        return "se ha eliminado el AdmTabla";
    }

    @Override
    public List<AdmTabla> listarTodo() throws Exception {
        return admTablaFacadeDao.listarOrdenada(ENTIDAD_admTablaFacadeDao, "idTabla", "asc");
    }

    @Override
    public List<AdmTabla> listarTodosActivos() throws Exception {
        return admTablaFacadeDao.listarTodosActivos();
    }

    @Override
    public AdmTabla buscarPorCodigoActivo(Integer codDis) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idTabla", codDis);
        campos.put("estadoLogico", true);
        return admTablaFacadeDao.buscarPorCampos(ENTIDAD_admTablaFacadeDao, campos);
    }
}
