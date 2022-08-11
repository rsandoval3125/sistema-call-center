/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetValidacion;
import ec.gob.inec.metadato.ejb.facade.MetValidacionFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetValidacionServicio implements MetValidacionServicioRemote, MetValidacionServicioLocal {

   @EJB
    private MetValidacionFacade metValidacionFacade;
    private String ENTIDAD_metValidacionFacade = "MetValidacion";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearValidacion(MetValidacion metValidacion) throws Exception {
        metValidacionFacade.crear(metValidacion);
        return "se ha creado el Validacion";
    }

    @Override
    public String editarValidacion(MetValidacion metValidacion) throws Exception {
        metValidacionFacade.editar(metValidacion);
        return "se ha modificado el Validacion";
    }

    @Override
    public String eliminarValidacion(MetValidacion metValidacion) throws Exception {
        metValidacionFacade.eliminar(metValidacion);
        return "se ha modificado el Validacion";
    }

    @Override
    public List<MetValidacion> listarTodo() throws Exception {
        return metValidacionFacade.listarOrdenada(ENTIDAD_metValidacionFacade, "idValidacion", "asc");
    }

    @Override
    public List<MetValidacion> listarValidacionXSeccion(Integer codSeccion ) throws Exception {
        return metValidacionFacade.listarValidacionXSeccion(codSeccion);
    }

    @Override
    public List<Object[]> listarValidacion(Integer formulario) throws Exception {
        return metValidacionFacade.listarValidacion(formulario); //To change body of generated methods, choose Tools | Templates.
    }
        
   
}
