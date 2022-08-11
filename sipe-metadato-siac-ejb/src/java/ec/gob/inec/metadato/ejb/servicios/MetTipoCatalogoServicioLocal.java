/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author vespinoza
 */
@Local
public interface MetTipoCatalogoServicioLocal {

    public String crearTipoCatalogo(MetTipoCatalogo admTipoCatalogo) throws Exception;

    public String editarTipoCatalogo(MetTipoCatalogo admTipoCatalogo) throws Exception;

    public String eliminarTipoCatalogo(MetTipoCatalogo admTipoCatalogo) throws Exception;

    public List<MetTipoCatalogo> listarTodo() throws Exception;

    public List<MetTipoCatalogo> listarTipoCatPorSecciones(List<Integer> codigosSeccion_) throws Exception;

    public String existeDuplicidadDeAliasTipoCatalogo() throws Exception;

    public MetTipoCatalogo consultaTipoCatalogoPorAlias(String alias) throws Exception;
    
    public List<MetTipoCatalogo> listarTodosActivos() throws Exception;
    
    public Boolean existeTipoCatalogoXAlias(String alias) throws Exception;
}
