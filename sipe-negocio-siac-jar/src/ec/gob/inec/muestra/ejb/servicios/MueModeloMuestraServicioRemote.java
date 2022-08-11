/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.MueModeloMuestra;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jcerda
 */
@Remote
public interface MueModeloMuestraServicioRemote {

    public String crearModelo(MueModeloMuestra mueModeloMuestra) throws Exception;

    public String editarModelo(MueModeloMuestra mueModeloMuestra) throws Exception;

    public String eliminarModelo(MueModeloMuestra mueModeloMuestra) throws Exception;

    public MueModeloMuestra buscarPorCodMuestra(Integer codMue) throws Exception;

    public List<MueModeloMuestra> listarTodo() throws Exception;

    public List<MueModeloMuestra> listarTodosActivos() throws Exception;

    public List<Object[]> listarModelos(Integer formulario) throws Exception;

    public List<Object[]> listarModelosDB(Integer tipoMue, Integer formulario) throws Exception;

    public List<MueModeloMuestra> listarModeloXForm(Integer codForm) throws Exception;

    public List<MueModeloMuestra> listarModeloXSeccion(Integer codSeccion) throws Exception;

    /*
   Metodos para la renumeración de sectores (Su uso posiblemene se lo aga una vez)
   Exíste una alta cantidad de SQL-nativo
     */
    public String ejecQueryNativoGenerico(String sql) throws Exception;

    public List<Object[]> listarObjGenerico(String sql) throws Exception;

}
