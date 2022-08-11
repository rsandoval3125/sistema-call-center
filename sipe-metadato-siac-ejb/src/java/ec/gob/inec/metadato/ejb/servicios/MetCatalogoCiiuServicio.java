/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoCiiu;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.facade.MetCatalogoCiiuFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rmoreano
 */
@Stateless
public class MetCatalogoCiiuServicio implements MetCatalogoCiiuServicioRemote, MetCatalogoCiiuServicioLocal {

    @EJB
    private MetCatalogoCiiuFacade metCatalogoCiiuFacade;
    private String ENTIDAD_MetCatalogoCiiu = "MetCatalogoCiiu";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCatalogo(MetCatalogoCiiu metCatalogoCiiu) throws Exception {
        metCatalogoCiiuFacade.crear(metCatalogoCiiu);
        return "se ha creado el MetCatalogoCiiu";
    }

    @Override
    public String editarCatalogo(MetCatalogoCiiu metCatalogoCiiu) throws Exception {
        metCatalogoCiiuFacade.editar(metCatalogoCiiu);
        return "se ha modificado el MetCatalogoCiiu";
    }

    @Override
    public String eliminarCatalogo(MetCatalogoCiiu metCatalogoCiiu) throws Exception {
        metCatalogoCiiuFacade.eliminar(metCatalogoCiiu);
        return "se ha eliminado el MetCatalogoCiiu";
    }

    @Override
    public List<MetCatalogoCiiu> listarTodo() throws Exception {
        return metCatalogoCiiuFacade.listarOrdenada(ENTIDAD_MetCatalogoCiiu, "idCatalogoCiiu", "asc");
    }

    @Override
    public List<MetCatalogoCiiu> listarCatalogoXAlias(String aliasCat) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogoXAlias(aliasCat);
    }

    @Override
    public List<MetCatalogoCiiu> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogoFasesXOperTodas(codOper);
    }

    @Override
    public List<MetCatalogoCiiu> listarCatalogoNoAsignados(List<MetCatalogoCiiu> lstCats, String nombreCat) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogoNoAsignados(lstCats, nombreCat);
    }

    @Override
    public MetCatalogoCiiu buscarCatalogoXId(Integer idCatalogoCiiu) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idCatalogoCiiu", idCatalogoCiiu);
        return metCatalogoCiiuFacade.buscarPorCampos(ENTIDAD_MetCatalogoCiiu, campos);
    }

    public List<MetCatalogoCiiu> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogosPorTipos(codigosTipos_);
    }

    @Override
    public List<MetCatalogoCiiu> listarTodosActivos() throws Exception {
        return metCatalogoCiiuFacade.listarTodosActivos();
    }

    @Override
    public List<MetCatalogoCiiu> listarTodosActivos(Integer idCatalogo) throws Exception {
        return metCatalogoCiiuFacade.listarTodosActivosDistintoAlId(idCatalogo);
    }

    @Override
    public MetCatalogoCiiu buscarCatalogoXAlias(String valias) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("alias", valias);
        campos.put("estadoLogico", true);
        return metCatalogoCiiuFacade.buscarPorCampos(ENTIDAD_MetCatalogoCiiu, campos);
    }

    @Override
    public Boolean existeCatalogoXAlias(String alias) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("alias", alias);
        campos.put("estadoLogico", true);
        return metCatalogoCiiuFacade.existePorCampos("MetCatalogoCiiu", campos, "");
    }

    @Override
    public Boolean existeTipoCatalogoenCatalogo(MetTipoCatalogo codTipoCatalogo) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codTipoCatalogo", codTipoCatalogo);
        campos.put("estadoLogico", true);
        return metCatalogoCiiuFacade.existePorCampos("MetCatalogoCiiu", campos, "");
    }

    @Override
    public List<MetCatalogoCiiu> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return metCatalogoCiiuFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public MetCatalogoCiiu buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return metCatalogoCiiuFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public List<MetCatalogoCiiu> listarCatalogosPorPadre1(Integer codPadre1) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogosPorPadre1(codPadre1);
    }

   @Override
   public MetCatalogoCiiu buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
      return metCatalogoCiiuFacade.buscarPorIdTipoCatalogoYNombre(idTipoCatalogo, nombre);
   }

   @Override
   public MetCatalogoCiiu buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
      return metCatalogoCiiuFacade.buscarCatalogoPorIdTipoCatalogoYNombre(idTipoCatalogo, nombre);
   }
}
