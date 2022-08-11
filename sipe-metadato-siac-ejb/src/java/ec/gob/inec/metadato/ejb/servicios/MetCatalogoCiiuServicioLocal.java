/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoCiiu;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreano
 */
@Local
public interface MetCatalogoCiiuServicioLocal {

    public String crearCatalogo(MetCatalogoCiiu admCatalogo) throws Exception;

    public String editarCatalogo(MetCatalogoCiiu admCatalogo) throws Exception;

    public String eliminarCatalogo(MetCatalogoCiiu admCatalogo) throws Exception;

    public List<MetCatalogoCiiu> listarTodo() throws Exception;

    public List<MetCatalogoCiiu> listarCatalogoXAlias(String aliasCat) throws Exception;

    public List<MetCatalogoCiiu> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception;

    public List<MetCatalogoCiiu> listarCatalogoNoAsignados(List<MetCatalogoCiiu> lstCats, String aliasCat) throws Exception;

    public MetCatalogoCiiu buscarCatalogoXId(Integer idCatalogo) throws Exception;

    public List<MetCatalogoCiiu> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception;

    public List<MetCatalogoCiiu> listarTodosActivos() throws Exception;

    public List<MetCatalogoCiiu> listarTodosActivos(Integer idCatalogo) throws Exception;

    public MetCatalogoCiiu buscarCatalogoXAlias(String valias) throws Exception;

    public Boolean existeCatalogoXAlias(String alias) throws Exception;

    public Boolean existeTipoCatalogoenCatalogo(MetTipoCatalogo codTipoCatalogo) throws Exception;

    public List<MetCatalogoCiiu> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public MetCatalogoCiiu buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public List<MetCatalogoCiiu> listarCatalogosPorPadre1(Integer codPadre1) throws Exception;
    
    public MetCatalogoCiiu buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre)throws Exception;
    
    public MetCatalogoCiiu buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre ) throws Exception;
}
