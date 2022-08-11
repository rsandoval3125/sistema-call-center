/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProSeguimientoRevisor;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProSeguimientoRevisorServicioRemote {
    public String crearMpeSeguimientoRevisor(ProSeguimientoRevisor seguimrev) throws Exception;

    public String editarMpeSeguimientoRevisor(ProSeguimientoRevisor seguimrev) throws Exception;
    public String eliminarMpeSeguimientoRevisor(Integer codseguimrev) throws Exception ;

    public List<ProSeguimientoRevisor> listarMpeSeguimientoRevisorPorSeguimiento(Integer idSeguimiento) throws Exception;
    
    public ProSeguimientoRevisor buscarSeguimientoRevisorPorId(Integer codseguimrev) throws Exception;
    
    public boolean existeSeguimientoRevisorPorSeguimientoYNivel(Integer idSeguimiento, Integer nivel) throws Exception;
    
    
    public List<ProSeguimientoRevisor> listarMpeSeguimientoRevisorPorOperativo(Integer idOperativo) throws Exception ;
    public int contarMpeSeguimientoRevisorPorSeguimiento(Integer idSeguimiento) throws Exception;
    
    public int contarMpeSeguimientoRevisorPorRevisor(Integer idRevisor) throws Exception ;
    public List<ProSeguimientoRevisor> listarMpeSeguimientoRevisorPorRevisor(Integer idRevisor) throws Exception;
    
    public List<ProSeguimientoRevisor> listarMpeSeguimientoRevisorPendientePorOperativo(Integer idOperativo, Integer idRevisor, String estado) throws Exception;

    public List<ProSeguimientoRevisor> listarMpeSeguimientoRevisorPorSubproceso(Integer idSubp) throws Exception ;
    
    public List<ProSeguimientoRevisor> listarSeguimientoRevisorPorSeguimientoYNivel(Integer codSeg, Integer nivel) throws Exception;
}
