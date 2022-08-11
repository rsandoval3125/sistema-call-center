/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.MuePersona;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jcerda
 */
@Remote
public interface MuePersonaServicioRemote {
    
   public String crearPersona(MuePersona muePersona) throws Exception;

    public String editarPersona(MuePersona muePersona) throws Exception;

    public String eliminarPersona(MuePersona muePersona) throws Exception;

    public List<MuePersona> listarTodo() throws Exception;

    public List<MuePersona> listarTodosActivos() throws Exception; 
    
    public MuePersona buscarCodMuestra(String codMue,String idPer) throws Exception;
}
