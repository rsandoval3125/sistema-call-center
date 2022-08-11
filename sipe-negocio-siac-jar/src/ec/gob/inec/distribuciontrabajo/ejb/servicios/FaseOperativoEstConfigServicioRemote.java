/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.servicios;

import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativoEstConfig;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface FaseOperativoEstConfigServicioRemote {

    public String crearFasesOperativoEstConfig(DisFaseOperativoEstConfig disFasesOperativo) throws Exception ;    
    public String editarFasesOperativoEstConfig(DisFaseOperativoEstConfig disFasesOperativo) throws Exception;
    public String eliminarFasesOperativoEstConfig(DisFaseOperativoEstConfig disFasesOperativo) throws Exception ;

    
    public List<DisFaseOperativoEstConfig> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;     
    public DisFaseOperativoEstConfig buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception ;         
    public String modificarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
    public int contarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;    
    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    
    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;       
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
}
