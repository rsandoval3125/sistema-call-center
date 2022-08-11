/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetSalto;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vespinoza
 */
@Local
public interface MetSaltoServicioLocal {

    public String crearSalto(MetSalto metSalto) throws Exception;

    public String editarSalto(MetSalto metSalto) throws Exception;

    public String eliminarSalto(MetSalto metSalto) throws Exception;

    public List<MetSalto> listarTodo() throws Exception;

    public List<Object []> listarSaltos(Integer formulario) throws Exception;

    public List<MetSeccion> listarSeccionxForm(Integer codForm) throws Exception;

    public List<MetSalto> listarSaltoXSeccion(Integer codSeccion) throws Exception;
}
