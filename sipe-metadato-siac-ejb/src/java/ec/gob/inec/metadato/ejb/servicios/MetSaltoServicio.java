/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetSalto;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.facade.MetSaltoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetSaltoServicio implements MetSaltoServicioRemote, MetSaltoServicioLocal {

    @EJB
    private MetSaltoFacade metSaltoFacade;
    private String ENTIDAD_metSaltoFacade = "MetSalto";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearSalto(MetSalto metSalto) throws Exception {
        metSaltoFacade.crear(metSalto);
        return "se ha creado el Salto";
    }

    @Override
    public String editarSalto(MetSalto metSalto) throws Exception {
        metSaltoFacade.editar(metSalto);
        return "se ha modificado el Salto";
    }

    @Override
    public String eliminarSalto(MetSalto metSalto) throws Exception {
        metSaltoFacade.eliminar(metSalto);
        return "se ha modificado el Salto";
    }

    @Override
    public List<MetSalto> listarTodo() throws Exception {
        return metSaltoFacade.listarOrdenada(ENTIDAD_metSaltoFacade, "idSalto", "asc");
    }
    
     @Override
    public List<Object []> listarSaltos(Integer formulario) throws Exception {
        return metSaltoFacade.listarSaltos(formulario);
    }
   
    @Override
    public List<MetSalto> listarSaltoXSeccion(Integer codSeccion) throws Exception {
        return metSaltoFacade.listarSaltoXSeccion(codSeccion);
    }
     @Override
    public List<MetSeccion> listarSeccionxForm(Integer codForm) throws Exception {
        return metSaltoFacade.listarSeccionXForm(codForm);
    }
}

