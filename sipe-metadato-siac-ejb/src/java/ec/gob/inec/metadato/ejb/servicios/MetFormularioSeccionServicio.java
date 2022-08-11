/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetFormularioSeccion;
import ec.gob.inec.metadato.ejb.facade.MetFormularioSeccionFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetFormularioSeccionServicio implements MetFormularioSeccionServicioRemote, MetFormularioSeccionServicioLocal {

    @EJB
    private MetFormularioSeccionFacade metFormularioSeccionFacade;
    private String ENTIDAD_metFormularioSeccionFacade = "MetFormularioSeccion";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearFormularioSeccion(MetFormularioSeccion metFormularioSeccion) throws Exception {
        metFormularioSeccionFacade.crear(metFormularioSeccion);
        return "se ha creado el FormularioSeccion";
    }

    @Override
    public String editarFormularioSeccion(MetFormularioSeccion metFormularioSeccion) throws Exception {
        metFormularioSeccionFacade.editar(metFormularioSeccion);
        return "se ha modificado el FormularioSeccion";
    }

    @Override
    public String eliminarFormularioSeccion(MetFormularioSeccion metFormularioSeccion) throws Exception {
        metFormularioSeccionFacade.eliminar(metFormularioSeccion);
        return "se ha modificado el FormularioSeccion";
    }

    @Override
    public List<MetFormularioSeccion> listarTodo() throws Exception {
        return metFormularioSeccionFacade.listarOrdenada(ENTIDAD_metFormularioSeccionFacade, "codigo", "asc");
    }

    @Override
    public List<MetFormularioSeccion> listarTodosActivos() throws Exception {
        return metFormularioSeccionFacade.listarTodosActivos();
    }

    @Override
    public List<MetFormularioSeccion> listarPorId(Integer formSeccion) throws Exception {
        return  metFormularioSeccionFacade.listarPorId(formSeccion);
    }

    @Override
    public MetFormularioSeccion bucarFormSecPoridForXidSecc(Integer idFormulario, Integer idSeccion) throws Exception {
        return metFormularioSeccionFacade.bucarFormSecPoridForXidSecc(idFormulario, idSeccion);
    }

  

    

}
