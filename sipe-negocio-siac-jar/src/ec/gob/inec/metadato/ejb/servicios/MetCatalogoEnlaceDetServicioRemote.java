/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoEnlaceDet;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetCatalogoEnlaceDetServicioRemote {

    public String crearCatalogoEnlaceDet(MetCatalogoEnlaceDet metCatalogoEnlaceDet) throws Exception;

    public String editarCatalogoEnlaceDet(MetCatalogoEnlaceDet metCatalogoEnlaceDet) throws Exception;

    public String eliminarCatalogoEnlaceDet(MetCatalogoEnlaceDet metCatalogoEnlaceDet) throws Exception;

    public List<MetCatalogoEnlaceDet> listarTodo() throws Exception;
}
