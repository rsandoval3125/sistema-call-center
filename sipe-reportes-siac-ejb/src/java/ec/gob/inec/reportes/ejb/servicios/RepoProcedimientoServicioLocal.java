/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jaraujo
 */
@Local
public interface RepoProcedimientoServicioLocal {

    public String crearProcedimiento(RepoProcedimiento repoProcedimiento) throws Exception;

    public String editarProcedimiento(RepoProcedimiento repoProcedimiento) throws Exception;

    public String eliminarProcedimiento(RepoProcedimiento repoProcedimiento) throws Exception;

    public List<RepoProcedimiento> listarTodo() throws Exception;
    
    public RepoProcedimiento buscarPorNombre(String nombre) throws Exception;
    
    public RepoProcedimiento buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
    
    public boolean existeEjecutarSQL(String funcion) throws Exception;
    
    public RepoProcedimiento buscarProcedimiento(Integer idProc) throws Exception;
    
    public boolean existeEjecutarFuncion(String vnombreFuncion, List<Object> parametrosOrdenados) throws Exception;
}
