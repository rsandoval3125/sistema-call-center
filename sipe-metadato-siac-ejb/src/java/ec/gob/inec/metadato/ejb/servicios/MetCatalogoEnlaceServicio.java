/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoEnlace;
import ec.gob.inec.metadato.ejb.facade.MetCatalogoEnlaceFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetCatalogoEnlaceServicio implements MetCatalogoEnlaceServicioRemote, MetCatalogoEnlaceServicioLocal {

    @EJB
    private MetCatalogoEnlaceFacade metCatalogoEnlaceFacade;
    private String ENTIDAD_metCatalogoEnlaceFacade = "MetCatalogoEnlace";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCatalogoEnlace(MetCatalogoEnlace metCatalogoEnlace) throws Exception {
        metCatalogoEnlaceFacade.crear(metCatalogoEnlace);
        return "se ha creado el CatalogoEnlace";
    }

    @Override
    public String editarCatalogoEnlace(MetCatalogoEnlace metCatalogoEnlace) throws Exception {
        metCatalogoEnlaceFacade.editar(metCatalogoEnlace);
        return "se ha modificado el CatalogoEnlace";
    }

    @Override
    public String eliminarCatalogoEnlace(MetCatalogoEnlace metCatalogoEnlace) throws Exception {
        metCatalogoEnlaceFacade.eliminar(metCatalogoEnlace);
        return "se ha modificado el CatalogoEnlace";
    }

    @Override
    public List<MetCatalogoEnlace> listarTodo() throws Exception {
        return metCatalogoEnlaceFacade.listarOrdenada(ENTIDAD_metCatalogoEnlaceFacade, "idCatenl", "asc");
    }
}
