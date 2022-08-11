/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jaraujo
 */
@Local
public interface AdmBaseDatosServicioLocal {

    public String crearConexion(AdmBaseDatos admBaseDatos) throws Exception;

    public String editarConexion(AdmBaseDatos admBaseDatos) throws Exception;

    public String eliminarConexion(AdmBaseDatos admBaseDatos) throws Exception;

    public List<AdmBaseDatos> listarTodo() throws Exception;
    
    public List<AdmBaseDatos> listarTodosActivos() throws Exception;
    
}
