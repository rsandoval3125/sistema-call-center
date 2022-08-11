/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.administracion.ejb.facade.AdmBaseDatosFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jaraujo
 */
@Stateless
public class AdmBaseDatosServicio implements AdmBaseDatosServicioLocal, AdmBaseDatosServicioRemote {

    @EJB
    private AdmBaseDatosFacade admBaseDatosFacadeDao;
    private String ENTIDAD_admBaseDatosFacadeDao = "AdmBaseDatos";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearConexion(AdmBaseDatos admBaseDatos) throws Exception {
        admBaseDatosFacadeDao.crear(admBaseDatos);
        return "se ha creado el AdmBaseDatos";
    }

    @Override
    public String editarConexion(AdmBaseDatos admBaseDatos) throws Exception {
        admBaseDatosFacadeDao.editar(admBaseDatos);
        return "se ha modificado el AdmBaseDatos";
    }

    @Override
    public String eliminarConexion(AdmBaseDatos admBaseDatos) throws Exception {
        admBaseDatosFacadeDao.eliminar(admBaseDatos);
        return "se ha eliminado el AdmBaseDatos";
    }

    @Override
    public List<AdmBaseDatos> listarTodo() throws Exception {
        return admBaseDatosFacadeDao.listarOrdenada(ENTIDAD_admBaseDatosFacadeDao, "idBasDat", "asc");
    }
    
    @Override
    public List<AdmBaseDatos> listarTodosActivos() throws Exception {
        return admBaseDatosFacadeDao.listarTodosActivos();
    }
}
