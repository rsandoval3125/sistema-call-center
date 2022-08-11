/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetConcepto;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.facade.MetConceptoFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetConceptoServicio implements MetConceptoServicioRemote, MetConceptoServicioLocal {

    @EJB
    private MetConceptoFacade metConceptoFacade;
    private String ENTIDAD_metConceptoFacade = "MetConcepto";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearConcepto(MetConcepto metConcepto) throws Exception {
        metConceptoFacade.crear(metConcepto);
        return "se ha creado el Concepto";
    }

    @Override
    public String editarConcepto(MetConcepto metConcepto) throws Exception {
        metConceptoFacade.editar(metConcepto);
        return "se ha modificado el Concepto";
    }

    @Override
    public String eliminarConcepto(MetConcepto metConcepto) throws Exception {
        metConceptoFacade.eliminar(metConcepto);
        return "se ha modificado el Concepto";
    }

    @Override
    public List<MetConcepto> listarTodo() throws Exception {
        return metConceptoFacade.listarOrdenada(ENTIDAD_metConceptoFacade, "idConcepto", "asc");
    }
    
    @Override
    public List<MetConcepto> listarTodosActivos() throws Exception {
        return metConceptoFacade.listarTodosActivos();
    }
    
    @Override
    public Boolean existeConceptoXAlias(String identificador) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("identificador", identificador);
        campos.put("estadoLogico", true);
        return metConceptoFacade.existePorCampos("MetConcepto", campos, "");
    }
    
    @Override
    public Boolean existeTipoCatalogoenConcepto(MetTipoCatalogo codTipoCatalogo) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codTipoCatalogo", codTipoCatalogo);
        campos.put("estadoLogico", true);
        return metConceptoFacade.existePorCampos("MetConcepto", campos, "");
    }
}
