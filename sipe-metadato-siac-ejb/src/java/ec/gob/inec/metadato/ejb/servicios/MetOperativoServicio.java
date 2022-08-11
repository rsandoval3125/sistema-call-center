/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.facade.MetOperativoFacade;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetOperativoServicio implements MetOperativoServicioRemote, MetOperativoServicioLocal {

    @EJB
    private MetOperativoFacade metOperativoFacade;
    private String ENTIDAD_metOperativoFacade = "MetOperativo";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearOperativo(MetOperativo metOperativo) throws Exception {
        metOperativoFacade.crear(metOperativo);
        return "se ha creado el Operativo";
    }

    @Override
    public String editarOperativo(MetOperativo metOperativo) throws Exception {
        metOperativoFacade.editar(metOperativo);
        return "se ha modificado el Operativo";
    }

    @Override
    public String eliminarOperativo(MetOperativo metOperativo) throws Exception {
        metOperativoFacade.eliminar(metOperativo);
        return "se ha modificado el Operativo";
    }

    @Override
    public List<MetOperativo> listarTodo() throws Exception {
        return metOperativoFacade.listarOrdenada(ENTIDAD_metOperativoFacade, "idOperativo", "asc");
    }
    @Override
    public List<MetOperativo> listarOperativosPorCodOpeEst(Integer codOpeEst_) throws Exception{
        return metOperativoFacade.listarOperativosPorCodOpeEst(codOpeEst_);
    }
    @Override
    public MetOperativo listarOperativosPorCodPeriodoYCodOpeEst(Integer codPer_, Integer codOpeEst_) throws Exception{
        return metOperativoFacade.listarOperativosPorCodPeriodoYCodOpeEst(codPer_, codOpeEst_);
    }
    
    @Override
    public MetOperativo buscarXCodigoActivo(Integer codOper) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idOperativo", codOper);
        campos.put("estadoLogico", true);
        return metOperativoFacade.buscarPorCampos(ENTIDAD_metOperativoFacade, campos);
    }
    
      @Override
    public List<MetOperativo> listarTodosOperativo() throws Exception{
        return metOperativoFacade.listarTodosOperativo();    
    }
     
     @Override
    public List<MetOperativo> listarTodosConModeloValidado() throws Exception{
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("tieneModeloValidado", true);
        campos.put("estadoLogico", true);
        return metOperativoFacade.listarPorCampos(ENTIDAD_metOperativoFacade, campos, "codOpe.nombre");    
    }
    
    @Override
    public List<MetOperativo> listarTodosOperativoPorId(Integer idOperativo) throws Exception{
        return metOperativoFacade.listarTodosOperativoPorId(idOperativo);
    }    
    
    public List<MetOperativo> listarOperativosIn(List<MetOperativo> lista) throws Exception {
        return metOperativoFacade.listarOperativosIn(lista);
    }

    @Override
    public List<MetOperativo> listarOperativosPorPeriodoYOpeEst(Integer codPer_, Integer codOpeEst_) throws Exception {
        return  metOperativoFacade.listarOperativosPorPeriodoYOpeEst(codPer_, codOpeEst_);
    }

    @Override
    public List<MetOperativo> listarOperativosPorPeriodoYOpeEstModeloValidado(Integer codPer_, Integer codOpeEst_) throws Exception {
        return metOperativoFacade.listarOperativosPorPeriodoYOpeEstModeloValidado(codPer_, codOpeEst_);
    }
}
