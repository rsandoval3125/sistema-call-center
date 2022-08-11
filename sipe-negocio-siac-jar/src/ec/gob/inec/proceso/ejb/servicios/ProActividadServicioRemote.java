/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.proceso.ejb.entidades.ProActividad;
import ec.gob.inec.proceso.ejb.entidades.ProFase;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProActividadServicioRemote {
    public String crearMpeActividad(ProActividad actividad) throws Exception ;

    public String editarMpeActividad(ProActividad actividad) throws Exception;

    public String eliminarMpeActividad(Integer codactividad) throws Exception ;

    public List<ProActividad> listarMpeActividadesPorOperativo(Integer idOperativo) throws Exception;

    public List<ProActividad> listarMpeActividadesPorFase(Integer idFase) throws Exception;

    public List<ProActividad> listarMpeActividadesPorSubProceso(Integer idSubproceso) throws Exception;

    public ProActividad buscaractividadPorId(Integer codactividad) throws Exception ;

    public int contarMpeActividadPorOperativo(Integer idOperativo) throws Exception;

    public List<ProActividad> listarActividadesPorAplicacion(Integer idAplicacion) throws Exception ;

    public List<String> ponderacionDeActividadesDeOperativoEsValida(List<ProActividad> listaActividadesDeOperativo) throws Exception ;
    
    public ProActividad crearActividadCopia(ProActividad actividadOriginal, Integer idNuevoSubproceso) throws Exception;
    
    public List<ProActividad> listarActXOperConfigTodas(MetOperativo idOperativo)throws Exception;
     
    public List<ProActividad> listarActXVariasFases(List<ProFase> lstFases) throws Exception;
    
    public List<ProActividad> listarActividadesNoAsignados(List<ProActividad> lstCats, List<ProFase> lstFases) throws Exception;
    
    public List<ProActividad> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception ;  
    
    public ProActividad buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;
    
    public List<ProActividad> listarMpeActividadesPorOperativoTodo(Integer idOperativo) throws Exception;
}
