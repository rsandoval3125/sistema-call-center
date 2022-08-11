/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegSesionActiva;
import ec.gob.inec.seguridad.ejb.facade.SegSesionActivaFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author lponce
 */
@Stateless
public class SegSesionActivaServicio implements SegSesionActivaServicioRemote, SegSesionActivaServicioLocal {
    
    @EJB
    private SegSesionActivaFacade segSesionActivaFacade;
    private String ENTIDAD_segSesionActivaFacade = "SegSesionActiva";
    
    @Override
    public String crearSesionActiva(SegSesionActiva segSesionActiva) throws Exception {
        segSesionActivaFacade.crear(segSesionActiva);
        return "se ha creado la SesionActiva";
    }
    
    @Override
    public String editarSesionActiva(SegSesionActiva segSesionActiva) throws Exception {
        segSesionActivaFacade.editar(segSesionActiva);
        return "se ha modificado la SesionActiva";
    }
    
    @Override
    public String eliminarSesionActiva(SegSesionActiva segSesionActiva) throws Exception {
        segSesionActivaFacade.eliminar(segSesionActiva);
        return "se ha modificado la SesionActiva";
    }
    
    @Override
    public List<SegSesionActiva> listarTodo() throws Exception {
        return segSesionActivaFacade.listarOrdenada(ENTIDAD_segSesionActivaFacade, "idSesAct", "asc");
    }
    
    @Override
    public SegSesionActiva buxcarXIdentificador(String identificador) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("identificador", identificador);
        SegSesionActiva activa = segSesionActivaFacade.buscarPorCampos(ENTIDAD_segSesionActivaFacade, campos);
        System.out.println(activa.getIdSesAct());
        Map<String, Object> camposSesion = new HashMap<String, Object>();
        camposSesion.put("identificador", identificador);
        return segSesionActivaFacade.buscarPorCampos(ENTIDAD_segSesionActivaFacade, camposSesion);
    }
}
