/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoCiuo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rmoreano
 */
@Remote
public interface MetCatalogoCiuoServicioRemote {

    public String crearCatalogo(MetCatalogoCiuo admCatalogo) throws Exception;

    public String editarCatalogo(MetCatalogoCiuo admCatalogo) throws Exception;

    public String eliminarCatalogo(MetCatalogoCiuo admCatalogo) throws Exception;

    public List<MetCatalogoCiuo> listarTodo() throws Exception;

    public List<MetCatalogoCiuo> listarCatalogoXAlias(String aliasCat) throws Exception;

    public List<MetCatalogoCiuo> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception;

    public List<MetCatalogoCiuo> listarCatalogoNoAsignados(List<MetCatalogoCiuo> lstCats, String aliasCat) throws Exception;

    public MetCatalogoCiuo buscarCatalogoXId(Integer idCatalogo) throws Exception;

    public List<MetCatalogoCiuo> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception;

    public List<MetCatalogoCiuo> listarTodosActivos() throws Exception;

    public List<MetCatalogoCiuo> listarTodosActivos(Integer idCatalogo) throws Exception;

    public MetCatalogoCiuo buscarCatalogoXAlias(String valias) throws Exception;

    public Boolean existeCatalogoXAlias(String alias) throws Exception;

    public Boolean existeTipoCatalogoenCatalogo(MetTipoCatalogo codTipoCatalogo) throws Exception;

    public List<MetCatalogoCiuo> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public MetCatalogoCiuo buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public List<MetCatalogoCiuo> listarCatalogosPorPadre1(Integer codPadre1) throws Exception;
    
    public MetCatalogoCiuo buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre)throws Exception;
    
    public MetCatalogoCiuo buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre ) throws Exception;
}
