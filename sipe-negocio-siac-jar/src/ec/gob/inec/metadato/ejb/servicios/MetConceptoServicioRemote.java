/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetConcepto;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MetConceptoServicioRemote {

    public String crearConcepto(MetConcepto metConcepto) throws Exception;

    public String editarConcepto(MetConcepto metConcepto) throws Exception;

    public String eliminarConcepto(MetConcepto metConcepto) throws Exception;

    public List<MetConcepto> listarTodo() throws Exception;

    public List<MetConcepto> listarTodosActivos() throws Exception;
    
    public Boolean existeConceptoXAlias(String identificador) throws Exception;
    
    public Boolean existeTipoCatalogoenConcepto(MetTipoCatalogo codTipoCatalogo) throws Exception;
}
