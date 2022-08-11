/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;


import ec.gob.inec.proceso.ejb.entidades.ProActividadItem;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProActividadItemServicioRemote {
    public String crearMpeActividadItem(ProActividadItem actItem) throws Exception;
    public String editarMpeActividadItem(ProActividadItem actItem) throws Exception;
    public String eliminarMpeActividadItem(Integer codactItem) throws Exception;
    public List<ProActividadItem> listarMpeActividadItemPorActividadYTipo(Integer idAct, String tipo) throws Exception;
    
    public List<ProActividadItem> listarMpeActividadItemPorActividad(Integer idAct) throws Exception;
    public ProActividadItem buscaractItemPorId(Integer codactItem)throws Exception;
    public ProActividadItem crearActividadItemCopia(ProActividadItem itemOriginal, Integer idNuevaActividad) throws Exception;
}
