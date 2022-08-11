/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoEnlace;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface MetCatalogoEnlaceServicioLocal {

    public String crearCatalogoEnlace(MetCatalogoEnlace metCatalogoEnlace) throws Exception;

    public String editarCatalogoEnlace(MetCatalogoEnlace metCatalogoEnlace) throws Exception;

    public String eliminarCatalogoEnlace(MetCatalogoEnlace metCatalogoEnlace) throws Exception;

    public List<MetCatalogoEnlace> listarTodo() throws Exception;
}
