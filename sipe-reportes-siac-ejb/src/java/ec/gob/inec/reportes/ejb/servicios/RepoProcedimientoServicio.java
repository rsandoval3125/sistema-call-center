/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import ec.gob.inec.reportes.ejb.facade.RepoProcedimientoFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jaraujo
 */
@Stateless
public class RepoProcedimientoServicio implements RepoProcedimientoServicioRemote, RepoProcedimientoServicioLocal {

    @EJB
    private RepoProcedimientoFacade repoProcedimientoFacade;
    private String ENTIDAD_repoProcedimientoFacade = "RepoProcedimiento";

    @Override
    public String crearProcedimiento(RepoProcedimiento repoProcedimiento) throws Exception {
        repoProcedimientoFacade.crear(repoProcedimiento);
        return "se ha creado el Procedimiento";
    }
    
    @Override
    public RepoProcedimiento buscarPorNombre(String nombre) throws Exception {     
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("nombre", nombre);
        return repoProcedimientoFacade.buscarPorCampos(ENTIDAD_repoProcedimientoFacade, campos);
    }

    @Override
    public String editarProcedimiento(RepoProcedimiento repoProcedimiento) throws Exception {
        repoProcedimientoFacade.editar(repoProcedimiento);
        return "se ha modificado el Procedimiento";
    }

    @Override
    public String eliminarProcedimiento(RepoProcedimiento repoProcedimiento) throws Exception {
        repoProcedimientoFacade.eliminar(repoProcedimiento);
        return "se ha eliminado el Procedimiento";
    }

    @Override
    public List<RepoProcedimiento> listarTodo() throws Exception {
        return repoProcedimientoFacade.listarOrdenada(ENTIDAD_repoProcedimientoFacade, "idProc", "asc");
    }
    
    @Override
    public RepoProcedimiento buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception {          
        return repoProcedimientoFacade.buscarEjecutarConsulta(nombreProdecimiento ,parametrosOrdenados);
    }  
    
    @Override
    public boolean existeEjecutarSQL(String funcion) throws Exception {          
        return repoProcedimientoFacade.existeEjecutarSQL(funcion);
    }  
    
    @Override
    public RepoProcedimiento buscarProcedimiento(Integer idProc) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("idProc", idProc);
        return repoProcedimientoFacade.buscarPorCampos(ENTIDAD_repoProcedimientoFacade, campos);
    }
    @Override
    public boolean existeEjecutarFuncion(String vnombreFuncion, List<Object> parametrosOrdenados) throws Exception {
        return repoProcedimientoFacade.existeEjecutarFuncion(vnombreFuncion, parametrosOrdenados);
    }
}
