/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoCiuo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.facade.MetCatalogoCiuoFacade;
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
public class MetCatalogoCiuoServicio implements MetCatalogoCiuoServicioRemote, MetCatalogoCiuoServicioLocal {

    @EJB
    private MetCatalogoCiuoFacade metCatalogoCiiuFacade;
    private String ENTIDAD_MetCatalogoCiuo = "MetCatalogoCiuo";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCatalogo(MetCatalogoCiuo metCatalogoCiiu) throws Exception {
        metCatalogoCiiuFacade.crear(metCatalogoCiiu);
        return "se ha creado el MetCatalogoCiuo";
    }

    @Override
    public String editarCatalogo(MetCatalogoCiuo metCatalogoCiiu) throws Exception {
        metCatalogoCiiuFacade.editar(metCatalogoCiiu);
        return "se ha modificado el MetCatalogoCiuo";
    }

    @Override
    public String eliminarCatalogo(MetCatalogoCiuo metCatalogoCiiu) throws Exception {
        metCatalogoCiiuFacade.eliminar(metCatalogoCiiu);
        return "se ha eliminado el MetCatalogoCiuo";
    }

    @Override
    public List<MetCatalogoCiuo> listarTodo() throws Exception {
        return metCatalogoCiiuFacade.listarOrdenada(ENTIDAD_MetCatalogoCiuo, "idCatalogoCiuo", "asc");
    }

    @Override
    public List<MetCatalogoCiuo> listarCatalogoXAlias(String aliasCat) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogoXAlias(aliasCat);
    }

    @Override
    public List<MetCatalogoCiuo> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogoFasesXOperTodas(codOper);
    }

    @Override
    public List<MetCatalogoCiuo> listarCatalogoNoAsignados(List<MetCatalogoCiuo> lstCats, String nombreCat) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogoNoAsignados(lstCats, nombreCat);
    }

    @Override
    public MetCatalogoCiuo buscarCatalogoXId(Integer idCatalogoCiuo) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idCatalogoCiuo", idCatalogoCiuo);
        return metCatalogoCiiuFacade.buscarPorCampos(ENTIDAD_MetCatalogoCiuo, campos);
    }

    public List<MetCatalogoCiuo> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogosPorTipos(codigosTipos_);
    }

    @Override
    public List<MetCatalogoCiuo> listarTodosActivos() throws Exception {
        return metCatalogoCiiuFacade.listarTodosActivos();
    }

    @Override
    public List<MetCatalogoCiuo> listarTodosActivos(Integer idCatalogo) throws Exception {
        return metCatalogoCiiuFacade.listarTodosActivosDistintoAlId(idCatalogo);
    }

    @Override
    public MetCatalogoCiuo buscarCatalogoXAlias(String valias) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("alias", valias);
        campos.put("estadoLogico", true);
        return metCatalogoCiiuFacade.buscarPorCampos(ENTIDAD_MetCatalogoCiuo, campos);
    }

    @Override
    public Boolean existeCatalogoXAlias(String alias) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("alias", alias);
        campos.put("estadoLogico", true);
        return metCatalogoCiiuFacade.existePorCampos("MetCatalogoCiuo", campos, "");
    }

    @Override
    public Boolean existeTipoCatalogoenCatalogo(MetTipoCatalogo codTipoCatalogo) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codTipoCatalogo", codTipoCatalogo);
        campos.put("estadoLogico", true);
        return metCatalogoCiiuFacade.existePorCampos("MetCatalogoCiuo", campos, "");
    }

    @Override
    public List<MetCatalogoCiuo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return metCatalogoCiiuFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public MetCatalogoCiuo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return metCatalogoCiiuFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public List<MetCatalogoCiuo> listarCatalogosPorPadre1(Integer codPadre1) throws Exception {
        return metCatalogoCiiuFacade.listarCatalogosPorPadre1(codPadre1);
    }

   @Override
   public MetCatalogoCiuo buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
      return metCatalogoCiiuFacade.buscarPorIdTipoCatalogoYNombre(idTipoCatalogo, nombre);
   }

   @Override
   public MetCatalogoCiuo buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
      return metCatalogoCiiuFacade.buscarCatalogoPorIdTipoCatalogoYNombre(idTipoCatalogo, nombre);
   }
}
