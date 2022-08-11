/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmTabla;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface AdmTablaServicioLocal {

    public String crearTabla(AdmTabla admTabla) throws Exception;

    public String editarTabla(AdmTabla admTabla) throws Exception;

    public String eliminarTabla(AdmTabla admTabla) throws Exception;

    public List<AdmTabla> listarTodo() throws Exception;

    public List<AdmTabla> listarTodosActivos() throws Exception;
    
    public AdmTabla buscarPorCodigoActivo(Integer codDis) throws Exception;
}
