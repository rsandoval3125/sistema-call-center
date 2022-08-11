/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonalCapacitacion;
import ec.gob.inec.administracion.ejb.facade.AdmPersonalCapacitacionFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class AdmPersonalCapacitacionServicio implements AdmPersonalCapacitacionServicioRemote, AdmPersonalCapacitacionServicioLocal {

    @EJB
    private AdmPersonalCapacitacionFacade admPersonalCapacitacionFacade;
    private String ENTIDAD_AdmPersonalCapacitacion = "AdmPersonalCapacitacion";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearPersonalCapacitacion(AdmPersonalCapacitacion disDispositivo) throws Exception {
        admPersonalCapacitacionFacade.crear(disDispositivo);
        return "se ha creado el AdmPersonalCapacitacion";
    }

    @Override
    public String editarPersonalCapacitacion(AdmPersonalCapacitacion disDispositivo) throws Exception {
        admPersonalCapacitacionFacade.editar(disDispositivo);
        return "se ha modificado el AdmPersonalCapacitacion";
    }

    @Override
    public String eliminarPersonalCapacitacion(AdmPersonalCapacitacion disDispositivo) throws Exception {
        admPersonalCapacitacionFacade.eliminar(disDispositivo);
        return "se ha eliminado el AdmPersonalCapacitacion";
    }

    @Override
    public List<AdmPersonalCapacitacion> listarTodo() throws Exception {
        return admPersonalCapacitacionFacade.listarOrdenada(ENTIDAD_AdmPersonalCapacitacion, "idPerCap", "asc");
    }
}
