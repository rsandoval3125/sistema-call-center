/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios;

import ec.gob.inec.seguridad.ejb.entidades.SegControlTexto;
import ec.gob.inec.seguridad.ejb.facade.SegControlTextoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class SegControlTextoServicio implements SegControlTextoServicioRemote, SegControlTextoServicioLocal {

    @EJB
    private SegControlTextoFacade segControlTextoFacade;
    private String ENTIDAD_segControlTextoFacade = "SegControlTexto";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearControlTexto(SegControlTexto segControlTexto) throws Exception {
        segControlTextoFacade.crear(segControlTexto);
        return "se ha creado el ControlTexto";
    }

    @Override
    public String editarControlTexto(SegControlTexto segControlTexto) throws Exception {
        segControlTextoFacade.editar(segControlTexto);
        return "se ha modificado el ControlTexto";
    }

    @Override
    public String eliminarControlTexto(SegControlTexto segControlTexto) throws Exception {
        segControlTextoFacade.eliminar(segControlTexto);
        return "se ha modificado el ControlTexto";
    }

    @Override
    public List<SegControlTexto> listarTodo() throws Exception {
        return segControlTextoFacade.listarOrdenada(ENTIDAD_segControlTextoFacade, "idConTxt", "asc");
    }
}
