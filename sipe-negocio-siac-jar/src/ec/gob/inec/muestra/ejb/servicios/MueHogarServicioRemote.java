/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;


import ec.gob.inec.muestra.ejb.entidades.MueHogar;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jcerda
 */
@Remote
public interface MueHogarServicioRemote {
    public String crearHogar(MueHogar mueHogar) throws Exception;

    public String editarHogar(MueHogar mueHogar) throws Exception;

    public String eliminarHogar(MueHogar mueHogar) throws Exception;

    public List<MueHogar> listarTodo() throws Exception;

    public List<MueHogar> listarTodosActivos() throws Exception;
}
