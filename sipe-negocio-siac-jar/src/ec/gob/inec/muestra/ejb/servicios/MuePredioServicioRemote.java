/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.muestra.ejb.entidades.MuePredio;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jcerda
 */
@Remote
public interface MuePredioServicioRemote {

    public String crearPredio(MuePredio muePredio) throws Exception;

    public String editarPredio(MuePredio muePredio) throws Exception;

    public String eliminarPredio(MuePredio muePredio) throws Exception;

    public List<MuePredio> listarTodo() throws Exception;

    public List<MuePredio> listarTodosActivos() throws Exception;
    
    public MuePredio buscarCodMuestra(String codMue,String idDpa) throws Exception;
    
    public List<MuePredio> listarPorIdePredioMuestra(String identificadorDpa, String codMueRef) throws Exception;
}
