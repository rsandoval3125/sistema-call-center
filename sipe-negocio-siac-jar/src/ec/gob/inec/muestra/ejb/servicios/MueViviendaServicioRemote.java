/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.MueVivienda;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jcerda
 */
@Remote
public interface MueViviendaServicioRemote {

    public String crearVivienda(MueVivienda mueVivienda) throws Exception;

    public String editarVivienda(MueVivienda mueVivienda) throws Exception;

    public String eliminarVivienda(MueVivienda mueVivienda) throws Exception;

    public List<MueVivienda> listarTodo() throws Exception;

    public List<MueVivienda> listarTodosActivos() throws Exception;
    
    public MueVivienda buscarCodMuestra(String codMue,String idViv) throws Exception;
}
