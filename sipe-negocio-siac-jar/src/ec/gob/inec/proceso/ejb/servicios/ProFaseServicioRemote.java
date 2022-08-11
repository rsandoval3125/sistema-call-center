/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;


import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.proceso.ejb.entidades.ProFase;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProFaseServicioRemote {
    public String crearMpeFase(ProFase fase) throws Exception;
    public String editarMpeFase(ProFase fase) throws Exception;
    public String eliminarMpeFase(Integer codfase) throws Exception;
    
    public List<ProFase> listarMpeFasePorOperativo(Integer idOperativo) throws Exception;
    
    public List<ProFase> listarMpeFasePorOperativoTodo(Integer idOperativo) throws Exception;
    
    public ProFase buscarfasePorId(Integer codfase)throws Exception;
    
    public boolean ponderacionDeFasesDeOperativoEsValida(List<ProFase> listaFasesPorOperativo) throws Exception;
    
    public ProFase crearFaseCopia(ProFase faseOriginal, Integer idNuevoOperativo) throws Exception;
    
    public List<ProFase> listarMpeFasePorOperativo(MetOperativo idOperativo) throws Exception;
    
    public List<ProFase> listarMpeFasePorOperativoConfig(MetOperativo idOperativo) throws Exception;
    
    public List<ProFase> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
       
    public ProFase buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;

    public Integer generarModeloEstandar(Integer codOpe) throws Exception;
    
    public List<ProFase> listarMpeFasePorOperativoPorComponente(MetOperativo codOper, Integer componente) throws Exception;
}
