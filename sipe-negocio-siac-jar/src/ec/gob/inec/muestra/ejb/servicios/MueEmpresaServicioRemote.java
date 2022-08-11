/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.MueEmpresa;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jcerda
 */
@Remote
public interface MueEmpresaServicioRemote {
  
    public String crearEmpresa(MueEmpresa mueEmpresa) throws Exception;

    public String editarEmpresa(MueEmpresa mueEmpresa) throws Exception;

    public String eliminarEmpresa(MueEmpresa mueEmpresa) throws Exception;

    public List<MueEmpresa> listarTodo() throws Exception;
    
    public List<MueEmpresa> listarTodosActivos() throws Exception; 
}
