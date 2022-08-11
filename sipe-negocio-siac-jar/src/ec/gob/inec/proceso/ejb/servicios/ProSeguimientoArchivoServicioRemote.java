/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProSeguimientoArchivo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProSeguimientoArchivoServicioRemote {
     public String crearMpeSeguimientoArchivo(ProSeguimientoArchivo archivo) throws Exception;
    public String editarMpeSeguimientoArchivo(ProSeguimientoArchivo archivo) throws Exception;
    public String eliminarMpeSeguimientoArchivo(Integer codarchivo) throws Exception;
    
    public List<Object[]> listarDetallesArchivoPorSeguimiento(Integer idSeguimiento) throws Exception;
    public ProSeguimientoArchivo buscararchivoPorId(Integer codarchivo)throws Exception;
    public List<ProSeguimientoArchivo> listarSeguimientoArchivoPorSeguimiento(Integer idSeguimiento) throws Exception;
}
