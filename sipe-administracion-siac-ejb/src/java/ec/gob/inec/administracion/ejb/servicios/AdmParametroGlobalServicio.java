/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.administracion.ejb.facade.AdmParametroGlobalFacade;
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
public class AdmParametroGlobalServicio implements AdmParametroGlobalServicioRemote, AdmParametroGlobalServicioLocal {
    
    @EJB
    private AdmParametroGlobalFacade admParametroGlobalFacade;
    private String ENTIDAD_AdmParametroGlobal = "AdmParametroGlobal";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearParametroGlobal(AdmParametroGlobal admParametroGlobal) throws Exception {
        admParametroGlobalFacade.crear(admParametroGlobal);
        return "se ha creado el AdmParametroGlobal";
    }
    
    @Override
    public String editarParametroGlobal(AdmParametroGlobal admParametroGlobal) throws Exception {
        admParametroGlobalFacade.editar(admParametroGlobal);
        return "se ha modificado el AdmParametroGlobal";
    }
    
    @Override
    public String eliminarParametroGlobal(AdmParametroGlobal admParametroGlobal) throws Exception {
        admParametroGlobalFacade.eliminar(admParametroGlobal);
        return "se ha eliminado el AdmParametroGlobal";
    }
    
    @Override
    public List<AdmParametroGlobal> listarTodo() throws Exception {
        return admParametroGlobalFacade.listarOrdenada(ENTIDAD_AdmParametroGlobal, "idParametro", "asc");
    }
    
    @Override
    public AdmParametroGlobal buscarXNombre(String nombre) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("nombre", nombre);
        return admParametroGlobalFacade.buscarPorCampos(ENTIDAD_AdmParametroGlobal, campos);
    }
    
    @Override
    public List<AdmParametroGlobal> listarTodosActivos() throws Exception {
        return admParametroGlobalFacade.listarTodosActivos();
    }
}
