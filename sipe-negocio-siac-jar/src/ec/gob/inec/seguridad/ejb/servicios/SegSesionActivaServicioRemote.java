/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegSesionActiva;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author lponce
 */
@Remote
public interface SegSesionActivaServicioRemote {

    public String crearSesionActiva(SegSesionActiva segSesionActiva) throws Exception;

    public String editarSesionActiva(SegSesionActiva segSesionActiva) throws Exception;

    public String eliminarSesionActiva(SegSesionActiva segSesionActiva) throws Exception;

    public List<SegSesionActiva> listarTodo() throws Exception;
    
    public SegSesionActiva buxcarXIdentificador(String identificador) throws Exception;
}
