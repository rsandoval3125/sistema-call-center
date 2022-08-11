/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.administracion.ejb.facade.AdmColumnaFacade;
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
public class AdmColumnaServicio implements AdmColumnaServicioRemote, AdmColumnaServicioLocal {

    @EJB
    private AdmColumnaFacade admColumnaFacadeDao;
    private String ENTIDAD_admColumnaFacadeDao = "AdmColumna";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearColumna(AdmColumna admColumna) throws Exception {
        admColumnaFacadeDao.crear(admColumna);
        return "se ha creado el AdmColumna";
    }

    @Override
    public String editarColumna(AdmColumna admColumna) throws Exception {
        admColumnaFacadeDao.editar(admColumna);
        return "se ha modificado el AdmColumna";
    }

    @Override
    public String eliminarColumna(AdmColumna admColumna) throws Exception {
        admColumnaFacadeDao.eliminar(admColumna);
        return "se ha eliminado el AdmColumna";
    }

    @Override
    public List<AdmColumna> listarTodo() throws Exception {
        return admColumnaFacadeDao.listarOrdenada(ENTIDAD_admColumnaFacadeDao, "idColumna", "asc");
    }

    @Override
    public List<AdmColumna> listarTodosActivos() throws Exception {
        return admColumnaFacadeDao.listarTodosActivos();
    }

    @Override
    public AdmColumna buscarPorCodigoActivo(Integer codDis) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idColumna", codDis);
        campos.put("estadoLogico", true);
        return admColumnaFacadeDao.buscarPorCampos(ENTIDAD_admColumnaFacadeDao, campos);
    }

    @Override
    public String encriptarCampoPorTabla(String tabla, String campo, String tipoEncriptacion) throws Exception {
        System.out.println("tabla=" + tabla + " campo=" + campo + " tipoEncriptacion=" + tipoEncriptacion);
        admColumnaFacadeDao.encriptarCampoPorTabla(tabla, campo, tipoEncriptacion);
        return "se ha encriptado el campo de " + tabla;
    }

    @Override
    public List<AdmColumna> consultarColumnasAEncriptarPorTabla(String tabla) throws Exception {

        return admColumnaFacadeDao.consultarColumnasAEncriptarPorTabla(tabla);
    }

    @Override
    public AdmColumna consultarPorTablaYColumna(String tabla, String columna) throws Exception {
        return admColumnaFacadeDao.consultarPorTablaYColumna(tabla, columna);
    }
}
