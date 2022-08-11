/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProEsigef;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProEsigefServicioRemote {
    public String crearEsigef (ProEsigef esigef) throws Exception;
    public String editarEsigef (ProEsigef esigef) throws Exception;
    public String eliminarEsigef (Integer idProEsigef) throws Exception;
    public ProEsigef buscarEsigef(Integer idProEsigef) throws Exception;
    public List <ProEsigef> ListaeEsigefPorEsigefDipla(Integer idSeguimientoED) throws Exception;
    public void crear(List<ProEsigef> lista)throws Exception;
}
