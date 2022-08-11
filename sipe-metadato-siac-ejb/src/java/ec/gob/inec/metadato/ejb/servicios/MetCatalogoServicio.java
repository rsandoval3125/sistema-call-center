/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.facade.MetCatalogoFacade;
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
public class MetCatalogoServicio implements MetCatalogoServicioRemote, MetCatalogoServicioLocal {

    @EJB
    private MetCatalogoFacade metCatalogoFacade;
    private String ENTIDAD_MetCatalogo = "MetCatalogo";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //METODOS
    @Override
    public String crearCatalogo(MetCatalogo metCatalogo) throws Exception {
        metCatalogoFacade.crear(metCatalogo);
        return "se ha creado el MetCatalogo";
    }

    @Override
    public String editarCatalogo(MetCatalogo metCatalogo) throws Exception {
        metCatalogoFacade.editar(metCatalogo);
        return "se ha modificado el MetCatalogo";
    }

    @Override
    public String eliminarCatalogo(MetCatalogo metCatalogo) throws Exception {
        metCatalogoFacade.eliminar(metCatalogo);
        return "se ha eliminado el MetCatalogo";
    }

    @Override
    public List<MetCatalogo> listarTodo() throws Exception {
        return metCatalogoFacade.listarOrdenada(ENTIDAD_MetCatalogo, "idCatalogo", "asc");
    }

    @Override
    public List<MetCatalogo> listarCatalogoXAlias(String aliasCat) throws Exception {
        return metCatalogoFacade.listarCatalogoXAlias(aliasCat);
    }

    @Override
    public List<MetCatalogo> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception {
        return metCatalogoFacade.listarCatalogoFasesXOperTodas(codOper);
    }

    @Override
    public List<MetCatalogo> listarCatalogoNoAsignados(List<MetCatalogo> lstCats, String nombreCat) throws Exception {
        return metCatalogoFacade.listarCatalogoNoAsignados(lstCats, nombreCat);
    }

    @Override
    public MetCatalogo buscarCatalogoXId(Integer idCatalogo) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idCatalogo", idCatalogo);
        return metCatalogoFacade.buscarPorCampos(ENTIDAD_MetCatalogo, campos);
    }

    public List<MetCatalogo> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception {
        return metCatalogoFacade.listarCatalogosPorTipos(codigosTipos_);
    }

    @Override
    public List<MetCatalogo> listarTodosActivos() throws Exception {
        return metCatalogoFacade.listarTodosActivos();
    }

    @Override
    public List<MetCatalogo> listarTodosActivos(Integer idCatalogo) throws Exception {
        return metCatalogoFacade.listarTodosActivosDistintoAlId(idCatalogo);
    }

    @Override
    public MetCatalogo buscarCatalogoXAlias(String valias) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("alias", valias);
        campos.put("estadoLogico", true);
        return metCatalogoFacade.buscarPorCampos(ENTIDAD_MetCatalogo, campos);
    }

    @Override
    public Boolean existeCatalogoXAlias(String alias) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("alias", alias);
        campos.put("estadoLogico", true);
        return metCatalogoFacade.existePorCampos("MetCatalogo", campos, "");
    }

    @Override
    public Boolean existeTipoCatalogoenCatalogo(MetTipoCatalogo codTipoCatalogo) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codTipoCatalogo", codTipoCatalogo);
        campos.put("estadoLogico", true);
        return metCatalogoFacade.existePorCampos("MetCatalogo", campos, "");
    }

    @Override
    public List<MetCatalogo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return metCatalogoFacade.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public MetCatalogo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return metCatalogoFacade.buscarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public List<MetCatalogo> listarCatalogosPorPadre1(Integer codPadre1) throws Exception {
        return metCatalogoFacade.listarCatalogosPorPadre1(codPadre1);
    }

    @Override
    public MetCatalogo buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
        return metCatalogoFacade.buscarPorIdTipoCatalogoYNombre(idTipoCatalogo, nombre);
    }

    @Override
    public MetCatalogo buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
        return metCatalogoFacade.buscarCatalogoPorIdTipoCatalogoYNombre(idTipoCatalogo, nombre);
    }

    @Override
    public MetCatalogo buscarCatalogoValZonalPorProv(String alias, String valor) throws Exception {
        return metCatalogoFacade.buscarCatalogoValZonalPorCodProvincia(alias, valor);
    }

    @Override
    public List<MetCatalogo> listarCatalogosPorTipoCatalogo(Integer codTipoCatalogo) throws Exception {
        return metCatalogoFacade.listarCatalogosPorTipoCatalogo(codTipoCatalogo);
    }

    @Override
    public MetCatalogo buscarPorIdTipoCatalogoYValor(Integer idTipoCatalogo, String valor) throws Exception {
        return metCatalogoFacade.buscarPorIdTipoCatalogoYValor(idTipoCatalogo, valor);
    }

    @Override
    public List<MetCatalogo> listarCatalogosPorPadre2(Integer codPadre2, String aliasTipCat) throws Exception {
        return metCatalogoFacade.listarCatalogosPorPadre2(codPadre2, aliasTipCat);
    }

    @Override
    public List<MetCatalogo> listarCatalogosIpc(String valor, String aliasTipCat) throws Exception {
        return metCatalogoFacade.listarCatalogosIpc(valor, aliasTipCat);
    }

    public List<MetCatalogo> listarMetadatoPorParametros(Map<String, Object> parametros) throws Exception {
        return metCatalogoFacade.listarPorCampos("MetCatalogo", parametros, "valor,codTipoCatalogo.alias,nombre,estadoLogico,estilo");
    }
}
