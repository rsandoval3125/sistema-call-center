/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptGeoreferencia;
import ec.gob.inec.captura.ejb.facade.CaptGeoreferenciaFacade;
import ec.gob.inec.reportes.ejb.servicios.ActualizaBDServicioRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author jcerda
 */
@Stateless
public class CaptGeoreferenciaServicio implements CaptGeoreferenciaServicioLocal, CaptGeoreferenciaServicioRemote {

    @EJB
    private CaptGeoreferenciaFacade georeferenciaDao;
    private final String ENT = "CaptGeoreferencia";
    private final String ID = "idGeoref";
    private final String CLAVE = "clave";

    @Override
    public CaptGeoreferencia buscarFormGeorefPorClave(String claveForm) throws Exception {
        return georeferenciaDao.buscarPorPartes(ENT, CLAVE, claveForm);
    }

    @Override
    public String crearGeoreferencia(CaptGeoreferencia captGeoreferencia) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String editarGeoreferencia(CaptGeoreferencia captGeoreferencia) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminarGeoreferencia(CaptGeoreferencia captGeoreferencia) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CaptGeoreferencia> listarTodo() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
