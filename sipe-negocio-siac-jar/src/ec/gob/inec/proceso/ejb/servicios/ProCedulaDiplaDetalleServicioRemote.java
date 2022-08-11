/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProCedulaDiplaDetalle;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProCedulaDiplaDetalleServicioRemote {
   public String crearCedulaDiplaDetalle (ProCedulaDiplaDetalle cedulaDiplaDetalle) throws Exception;
   public String editarCedulaDiplaDetalle (ProCedulaDiplaDetalle cedulaDiplaDetalle) throws Exception;
   public String EliminarCedulaDiplaDetalle (Integer idCedulaDiplaDetalle)throws Exception;
   public String buscarCedulaDiplaDetalle (Integer idCedulaDiplaDetalle)throws Exception;
   public List<ProCedulaDiplaDetalle> buscarPorIdCedulaDipla(Integer idCedulaDiplaDetalle) throws Exception;
   public List<ProCedulaDiplaDetalle> buscarPorIdCedulaDiplaOrdenada(Integer idCedulaDiplaDetalle) throws Exception;
   public boolean tieneRegistrosPorIdCedulaDipla(Integer idCedulaDipla) throws Exception;
   public void eliminarPorIdCedulaDipla(Integer idCedulaDipla) throws Exception;
}
