/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProDipla;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProDiplaServicioRemote {
    public String crearDipla (ProDipla dipla) throws Exception;
    public String editarDipla (ProDipla dipla) throws Exception;
    public String eliminarDipla (Integer idProDipla) throws Exception;
    public ProDipla buscarDipla(Integer idProDipla) throws Exception;
    public List <ProDipla> ListarDiplaPorEsigefDipla(Integer idSeguimientoED) throws Exception;
    public void crear(List<ProDipla> lista)throws Exception;
   
}
