/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.clases.CaptInfoMuestra;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import ec.gob.inec.captura.ejb.facade.CaptCargaControlFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class CaptCargaControlServicio implements CaptCargaControlServicioRemote, CaptCargaControlServicioLocal {

    @EJB
    private CaptCargaControlFacade captCargaControlFacadeDao;
    private String ENTIDAD_CaptCargaControl = "CaptCargaControl";

    @Override
    public String crearCargaControl(CaptCargaControl captCargaControl) throws Exception {
        captCargaControlFacadeDao.crear(captCargaControl);
        return "se ha creado el CargaControl";
    }

    @Override
    public String editarCargaControl(CaptCargaControl captCargaControl) throws Exception {
        captCargaControlFacadeDao.editar(captCargaControl);
        return "se ha modificado el CargaControl";
    }

    @Override
    public String eliminarCargaControl(CaptCargaControl captCargaControl) throws Exception {
        captCargaControlFacadeDao.eliminar(captCargaControl);
        return "se ha eliminado el CargaControl";
    }

    @Override
    public List<CaptCargaControl> listarTodo() throws Exception {
        return captCargaControlFacadeDao.listarOrdenada(ENTIDAD_CaptCargaControl, "idCarCon", "asc");
    }

    @Override
    public void modificarEstadoCargaControl(CaptCargaControl captCargaControl, Integer nuevoEstado) throws Exception {
        captCargaControl.setEstadoFormulario(nuevoEstado);
        this.editarCargaControl(captCargaControl);
    }

    public List<CaptCargaControl> listarCargaPendientePorParametros(Map<String, Object> parametros) throws Exception {
        return captCargaControlFacadeDao.listarPorCampos("CaptCargaControl", parametros, "clave,control1,control2,codFormulario.idFormulario");
    }

    public List<CaptCargaControl> listarCargaPendienteReemplazo(Map<String, Object> parametros) throws Exception {
        return captCargaControlFacadeDao.listarPorCamposReemplazo("CaptCargaControl", parametros, "cast(control1 as integer)");
    }

    public CaptCargaControl obtenerCargaControlPorId(Integer idCarCon) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idCarCon", idCarCon);
        return captCargaControlFacadeDao.buscarPorCampos(ENTIDAD_CaptCargaControl, campos);
    }

    public CaptInfoMuestra obtenerInfMuestraHogarPorIdMueDet(Integer idMueDet) throws Exception {
        return captCargaControlFacadeDao.obtenerInfMuestraHogarPorIdMueDet(idMueDet);
    }

    @Override
    public CaptCargaControl listarCaptCargaControlPorClaveCtr12PorFor(String clave, String control1, String control2, Integer codFormulario) throws Exception {
        return captCargaControlFacadeDao.listarCaptCargaControlPorClaveCtr12PorFor(clave, control1, control2, codFormulario);
    }

    @Override
    public List<CaptCargaControl> listarPorClaveCtr12PorMuestra(String clave, String control1, String control2, Integer codMueDet) throws Exception {
        return captCargaControlFacadeDao.listarPorClaveCtr12PorMuestra(clave, control1, control2, codMueDet);
    }

    @Override
    public void modificarEstadoLogicoCargaControl(CaptCargaControl captCargaControl, boolean estadoLogico) throws Exception {
        captCargaControl.setEstadoLogico(estadoLogico);
        this.editarCargaControl(captCargaControl);
    }

    @Override
    public List<CaptCargaControl> listarCaptCargaControlPorClaveCtrPorFor(String clave, String control1, String control2, Integer codFormulario) throws Exception {
        return captCargaControlFacadeDao.listarCaptCargaControlPorClaveCtrPorFor(clave, control1, control2, codFormulario);
    }

    @Override
    public List<CaptCargaControl> listarCaptCargaControlPorClaveCtr3PorFor(String clave, String control1, String control2, String control3, Integer codFormulario) throws Exception {
        return captCargaControlFacadeDao.listarCaptCargaControlPorClaveCtr3PorFor(clave, control1, control2, control3, codFormulario);
    }

    @Override
    public List<CaptCargaControl> listarPorClaveCtr1PorMuestraPorFrm(String clave, String control1, Integer codMueDet, Integer codFormulario) throws Exception {
        return captCargaControlFacadeDao.listarPorClaveCtr1PorMuestraPorFrm(clave, control1, codMueDet, codFormulario);
    }

    public List<CaptCargaControl> listarCargaRepetidaPrecenso(List<String> listaClavesAGenerar, String tipo) throws Exception {
        return captCargaControlFacadeDao.listarCargaRepetidaPrecenso(listaClavesAGenerar, tipo);
    }

    public CaptCargaControl obtenerCargaControlPorClave2020(String clave2020, String codFormulario) throws Exception {
        return captCargaControlFacadeDao.cargarCaptCargaControlPorClave2020(clave2020, codFormulario);
    }

    public List<CaptCargaControl> listarCargaPorClaveFormularioLike(String identificadorDPA, String codFormulario, String tipo, String aliasMuestra) throws Exception {
        return captCargaControlFacadeDao.listarCargaPorClaveFormularioLike(identificadorDPA, codFormulario, tipo, aliasMuestra);
    }

    /**
     * Lista conglomerados por usuario
     *
     * @param codUsuario Id de usuario
     * @return List<CaptCargaControl> con conglomerados.
     * @throws Exception
     */
    public List<String> listarConglomeradosPorUsuario(Integer codUsuario) throws Exception {
        return captCargaControlFacadeDao.listarConglomeradosPorUsuario(codUsuario);
    }
    
    @Override
    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {      
        return captCargaControlFacadeDao.listarEjecutarConsultaObj(nombreProdecimiento,parametrosOrdenados );
    }
    
    @Override
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception {          
        return captCargaControlFacadeDao.buscarEjecutarConsultaObj(nombreProdecimiento ,parametrosOrdenados );
    }
    
    @Override
    public List<CaptCargaControl> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {      
        return captCargaControlFacadeDao.listarEjecutarConsulta(nombreProdecimiento,parametrosOrdenados );
    }
}
