/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProSeguimientoEsigefDipla;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProSeguimientoEsigefDiplaServicioRemote {
    public String crearSeguimientoEsigefDipla(ProSeguimientoEsigefDipla seguiminetoEsigefDipla) throws Exception;
    public String editarSeguimientoEsigefDipla(ProSeguimientoEsigefDipla seguiminetoEsigefDipla) throws Exception;
    public String eliminarSeguimientoEsigefDipla(Integer codSeguiminetoEsigefDipla) throws Exception;
   
    public ProSeguimientoEsigefDipla buscarSeguiminetoEsigefDiplaPorID (Integer idSeguiminetoEsigefDipla) throws Exception;
    public List<ProSeguimientoEsigefDipla> listarEsigefDiplaPorTipo (String tipo)throws Exception;
}
