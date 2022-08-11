/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.servicios;

import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface FaseOperativoServicioRemote {

    public String crearFasesOperativo(DisFaseOperativo disFasesOperativo) throws Exception ;    
    public String editarFasesOperativo(DisFaseOperativo disFasesOperativo) throws Exception;
    public String eliminarFasesOperativo(DisFaseOperativo disFasesOperativo) throws Exception ;
    public List<DisFaseOperativo> listarTodo() throws Exception ;
    public DisFaseOperativo buscarXOperativoFase(Object codOper,  Object fase) throws Exception;
    public DisFaseOperativo buscarXOperativoRolUsu(MetOperativo codOper,  SegRol codRol) throws Exception ;
    public List<DisFaseOperativo> buscarXOperativo(MetOperativo codOper) throws Exception;
    
    public List<DisFaseOperativo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;     
    public DisFaseOperativo buscarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception ;         
    public String modificarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
    public int contarEjecutarConsulta(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;    
    public boolean existeEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    
    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;       
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception;
}
