/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface MetCatalogoServicioLocal {

    public String crearCatalogo(MetCatalogo admCatalogo) throws Exception;

    public String editarCatalogo(MetCatalogo admCatalogo) throws Exception;

    public String eliminarCatalogo(MetCatalogo admCatalogo) throws Exception;

    public List<MetCatalogo> listarTodo() throws Exception;

    public List<MetCatalogo> listarCatalogoXAlias(String aliasCat) throws Exception;

    public List<MetCatalogo> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception;

    public List<MetCatalogo> listarCatalogoNoAsignados(List<MetCatalogo> lstCats, String aliasCat) throws Exception;

    public MetCatalogo buscarCatalogoXId(Integer idCatalogo) throws Exception;

    public List<MetCatalogo> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception;

    public List<MetCatalogo> listarTodosActivos() throws Exception;

    public List<MetCatalogo> listarTodosActivos(Integer idCatalogo) throws Exception;

    public MetCatalogo buscarCatalogoXAlias(String valias) throws Exception;

    public Boolean existeCatalogoXAlias(String alias) throws Exception;

    public Boolean existeTipoCatalogoenCatalogo(MetTipoCatalogo codTipoCatalogo) throws Exception;

    public List<MetCatalogo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public MetCatalogo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public List<MetCatalogo> listarCatalogosPorPadre1(Integer codPadre1) throws Exception;

    public MetCatalogo buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception;

    public MetCatalogo buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception;

    public MetCatalogo buscarCatalogoValZonalPorProv(String alias, String valor) throws Exception;

    public List<MetCatalogo> listarCatalogosPorTipoCatalogo(Integer codTipoCatalogo) throws Exception;

    public MetCatalogo buscarPorIdTipoCatalogoYValor(Integer idTipoCatalogo, String valor) throws Exception;

    public List<MetCatalogo> listarCatalogosPorPadre2(Integer codPadre2, String aliasTipCat) throws Exception;

    public List<MetCatalogo> listarCatalogosIpc(String valor, String aliasTipCat) throws Exception;

    public List<MetCatalogo> listarMetadatoPorParametros(Map<String, Object> parametros) throws Exception;
}
