/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoEnlaceDet;
import ec.gob.inec.metadato.ejb.facade.MetCatalogoEnlaceDetFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetCatalogoEnlaceDetServicio implements MetCatalogoEnlaceDetServicioRemote, MetCatalogoEnlaceDetServicioLocal {

    @EJB
    private MetCatalogoEnlaceDetFacade metCatalogoEnlaceDetFacade;
    private String ENTIDAD_metCatalogoEnlaceDetFacade = "MetCatalogoEnlaceDet";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCatalogoEnlaceDet(MetCatalogoEnlaceDet metCatalogoEnlaceDet) throws Exception {
        metCatalogoEnlaceDetFacade.crear(metCatalogoEnlaceDet);
        return "se ha creado el CatalogoEnlaceDet";
    }

    @Override
    public String editarCatalogoEnlaceDet(MetCatalogoEnlaceDet metCatalogoEnlaceDet) throws Exception {
        metCatalogoEnlaceDetFacade.editar(metCatalogoEnlaceDet);
        return "se ha modificado el CatalogoEnlaceDet";
    }

    @Override
    public String eliminarCatalogoEnlaceDet(MetCatalogoEnlaceDet metCatalogoEnlaceDet) throws Exception {
        metCatalogoEnlaceDetFacade.eliminar(metCatalogoEnlaceDet);
        return "se ha modificado el CatalogoEnlaceDet";
    }

    @Override
    public List<MetCatalogoEnlaceDet> listarTodo() throws Exception {
        return metCatalogoEnlaceDetFacade.listarOrdenada(ENTIDAD_metCatalogoEnlaceDetFacade, "idCatenldet", "asc");
    }
}
