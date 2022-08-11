/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface AdmColumnaServicioRemote {

    public String crearColumna(AdmColumna admColumna) throws Exception;

    public String editarColumna(AdmColumna admColumna) throws Exception;

    public String eliminarColumna(AdmColumna admColumna) throws Exception;

    public List<AdmColumna> listarTodo() throws Exception;

    public List<AdmColumna> listarTodosActivos() throws Exception;

    public AdmColumna buscarPorCodigoActivo(Integer codDis) throws Exception;

    public String encriptarCampoPorTabla(String tabla, String campo, String tipoEncriptacion) throws Exception;

    public List<AdmColumna> consultarColumnasAEncriptarPorTabla(String tabla) throws Exception;

    public AdmColumna consultarPorTablaYColumna(String tabla, String columna) throws Exception;

}
