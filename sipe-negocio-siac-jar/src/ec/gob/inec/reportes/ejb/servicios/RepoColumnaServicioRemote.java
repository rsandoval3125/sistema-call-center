/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import java.util.List;
import javax.ejb.Remote;
import ec.gob.inec.reportes.ejb.entidades.RepoColumna;
import ec.gob.inec.reportes.ejb.entidades.RepoSubreporte;
import java.util.Map;

/**
 *
 * @author jaraujo
 */
@Remote
public interface RepoColumnaServicioRemote {

    public String crearColumna(RepoColumna repoColumna) throws Exception;

    public String editarColumna(RepoColumna repoColumna) throws Exception;

    public String eliminarColumna(RepoColumna repoColumna) throws Exception;

    public List<RepoColumna> listarTodo() throws Exception;
    
    public List<RepoColumna> listarColumnasPorSubReporte(RepoSubreporte codSubr) throws Exception;

}
