/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.MueAtributoExtra;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MueAtributoExtraServicioRemote {

    public String crearAtributoExtra(MueAtributoExtra mueAtributoExtra) throws Exception;

    public String editarAtributoExtra(MueAtributoExtra mueAtributoExtra) throws Exception;

    public String eliminarAtributoExtra(MueAtributoExtra mueAtributoExtra) throws Exception;

    public List<MueAtributoExtra> listarTodo() throws Exception;

    public List<MueAtributoExtra> listarTodosActivos() throws Exception;
    
    public List<MueAtributoExtra> listarPorMueDetalle(Integer codMueDet) throws Exception;
    
    public List<MueAtributoExtra> listarPorMuePorDetalleJornadaConglomerado (Integer codMuestra, String jornada, String detalle) throws Exception;
}
