/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetOperativoServicioRemote {

   public String crearOperativo(MetOperativo metOperativo) throws Exception;

    public String editarOperativo(MetOperativo metOperativo) throws Exception;

    public String eliminarOperativo(MetOperativo metOperativo) throws Exception;

    public List<MetOperativo> listarTodo() throws Exception;
    
    public List<MetOperativo> listarOperativosPorCodOpeEst(Integer codOpeEst_) throws Exception;
    
    public MetOperativo listarOperativosPorCodPeriodoYCodOpeEst(Integer codPer_, Integer codOpeEst_) throws Exception;
    
    public MetOperativo buscarXCodigoActivo(Integer codOper) throws Exception;
    
    public List<MetOperativo> listarTodosOperativo() throws Exception;
    
    public List<MetOperativo> listarTodosOperativoPorId(Integer idOperativo) throws Exception;
    
    public List<MetOperativo> listarOperativosIn(List<MetOperativo> lista) throws Exception ;
    
    public List<MetOperativo> listarTodosConModeloValidado() throws Exception;
    
    public List<MetOperativo> listarOperativosPorPeriodoYOpeEst(Integer codPer_, Integer codOpeEst_) throws Exception;  
    
    public List<MetOperativo> listarOperativosPorPeriodoYOpeEstModeloValidado(Integer codPer_, Integer codOpeEst_) throws Exception;
}
