/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import ec.gob.inec.reportes.ejb.entidades.RepoSubreporte;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author jaraujo
 */
@Remote
public interface RepoSubreporteServicioRemote {

    public String crearSubreporte(RepoSubreporte repoSubreporte) throws Exception;

    public String editarSubreporte(RepoSubreporte repoSubreporte) throws Exception;

    public String eliminarSubreporte(RepoSubreporte repoSubreporte) throws Exception;

    public List<RepoSubreporte> listarTodo() throws Exception;

    public List<RepoSubreporte> listarSubreportes(RepoReporte codReporte) throws Exception;

    public Object[] ejecutarFuncion(String procedimiento, String argumento) throws Exception;

    public RepoSubreporte buscarPorProcedimiento(RepoProcedimiento codProc) throws Exception;
}
