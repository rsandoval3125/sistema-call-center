/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonalCapacitacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface AdmPersonalCapacitacionServicioLocal {

    public String crearPersonalCapacitacion(AdmPersonalCapacitacion admPersonalCapacitacion) throws Exception;

    public String editarPersonalCapacitacion(AdmPersonalCapacitacion admPersonalCapacitacion) throws Exception;

    public String eliminarPersonalCapacitacion(AdmPersonalCapacitacion admPersonalCapacitacion) throws Exception;

    public List<AdmPersonalCapacitacion> listarTodo() throws Exception;
}
