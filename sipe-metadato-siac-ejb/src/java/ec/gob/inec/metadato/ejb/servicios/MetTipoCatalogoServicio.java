/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.facade.MetTipoCatalogoFacade;
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
public class MetTipoCatalogoServicio implements MetTipoCatalogoServicioRemote, MetTipoCatalogoServicioLocal {

    @EJB
    private MetTipoCatalogoFacade metTipoCatalogoFacade;
    private String ENTIDAD_MetTipoCatalogo = "MetTipoCatalogo";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearTipoCatalogo(MetTipoCatalogo metTipoCatalogo) throws Exception {
        metTipoCatalogoFacade.crear(metTipoCatalogo);
        return "se ha creado el MetTipoCatalogo";
    }

    @Override
    public String editarTipoCatalogo(MetTipoCatalogo metTipoCatalogo) throws Exception {
        metTipoCatalogoFacade.editar(metTipoCatalogo);
        return "se ha modificado el MetTipoCatalogo";
    }

    @Override
    public String eliminarTipoCatalogo(MetTipoCatalogo metTipoCatalogo) throws Exception {
        metTipoCatalogoFacade.eliminar(metTipoCatalogo);
        return "se ha eliminado el MetTipoCatalogo";
    }

    @Override
    public List<MetTipoCatalogo> listarTodo() throws Exception {
        return metTipoCatalogoFacade.listarOrdenada(ENTIDAD_MetTipoCatalogo, "idTipoCatalogo", "asc");
    }

    public List<MetTipoCatalogo> listarTipoCatPorSecciones(List<Integer> codigosSeccion_) throws Exception {
        return metTipoCatalogoFacade.listarTipoCatPorSecciones(codigosSeccion_);
    }

    public String existeDuplicidadDeAliasTipoCatalogo() throws Exception {
        return metTipoCatalogoFacade.existeDuplicidadDeAliasTipoCatalogo();
    }

    @Override
    public MetTipoCatalogo consultaTipoCatalogoPorAlias(String alias) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("alias", alias);
        return metTipoCatalogoFacade.buscarPorCampos(ENTIDAD_MetTipoCatalogo, campos);
    }
    
    @Override
    public List<MetTipoCatalogo> listarTodosActivos() throws Exception {
        return metTipoCatalogoFacade.listarTodosActivos();
    }
    
    @Override
    public Boolean existeTipoCatalogoXAlias(String alias) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("alias", alias);
        campos.put("estadoLogico", true);
        return metTipoCatalogoFacade.existePorCampos("MetTipoCatalogo", campos, "");
    }
}
