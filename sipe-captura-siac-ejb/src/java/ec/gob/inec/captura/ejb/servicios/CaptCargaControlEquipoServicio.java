/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import ec.gob.inec.captura.ejb.facade.CaptCargaControlEquipoFacade;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vespinoza
 */
@Stateless
public class CaptCargaControlEquipoServicio implements CaptCargaControlEquipoServicioRemote, CaptCargaControlEquipoServicioLocal {

    @EJB
    private CaptCargaControlEquipoFacade captCargaControlEquipoFacadeDao;
    private String ENTIDAD_CaptCargaControlEquipo = "CaptCargaControlEquipo";

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public String crearCargaControlEquipo(CaptCargaControlEquipo captCargaControlEquipo) throws Exception {
        captCargaControlEquipoFacadeDao.crear(captCargaControlEquipo);
        return "se ha creado el CargaControlEquipo";
    }

    @Override
    public String editarCargaControlEquipo(CaptCargaControlEquipo captCargaControlEquipo) throws Exception {
        captCargaControlEquipoFacadeDao.editar(captCargaControlEquipo);
        return "se ha modificado el CargaControlEquipo";
    }

    @Override
    public String eliminarCargaControlEquipo(CaptCargaControlEquipo captCargaControlEquipo) throws Exception {
        captCargaControlEquipoFacadeDao.eliminar(captCargaControlEquipo);
        return "se ha eliminado el CargaControlEquipo";
    }

    @Override
    public List<CaptCargaControlEquipo> listarTodo() throws Exception {
        return captCargaControlEquipoFacadeDao.listarOrdenada(ENTIDAD_CaptCargaControlEquipo, "idCarConEqu", "asc");
    }

    @Override
    public List<CaptCargaControlEquipo> listarCargaEquipoPorParametros(Map<String, Object> campos) throws Exception{
        campos.put("estadoLogico", true);
        return captCargaControlEquipoFacadeDao.listarPorCampos(ENTIDAD_CaptCargaControlEquipo, campos, "idCarConEqu");
    }

    @Override
    public List<CaptCargaControlEquipo> listarPorCodCarCon(Integer codCarCon) throws Exception {
        return  captCargaControlEquipoFacadeDao.listarPorCodCarCon(codCarCon);
    }

    public List<CaptCargaControlEquipo> listarPorCodigosCargaTrabajo (List<Integer> codigos){
        return captCargaControlEquipoFacadeDao.listarPorCodigosCargaTrabajo(codigos);
    }

    public List<CaptCargaControlEquipo> listarPorUsuarioYOperativo (Integer usuario,Integer codOperativo) throws Exception{
        return captCargaControlEquipoFacadeDao.listarPorUsuarioYOperativo(usuario, codOperativo);
    }

    public List<CaptCargaControlEquipo> listarPorUsuarioYOperativoYClaveSector (Integer codUsuario,Integer codOperativo,String claveSector) throws Exception{
        return captCargaControlEquipoFacadeDao.listarPorUsuarioYOperativoYClaveSector(codUsuario, codOperativo, claveSector);
    }

    public List<CaptCargaControlEquipo> listarPorRolOperativoYClaveSector (String aliasRol,Integer codOperativo,String claveSector)throws Exception{
        return captCargaControlEquipoFacadeDao.listarPorRolOperativoYClaveSector(aliasRol, codOperativo, claveSector);
    }

    //---Metodos para la ejecución de SQL nativo
    public List<Object[]> listarObjGenerico(String sentenciaSql) throws Exception {
        return captCargaControlEquipoFacadeDao.listObjetoNativoGenerico(sentenciaSql);
    }

    public String ejecQueryNativoGenerico(String sentenciaSql) throws Exception {
        return captCargaControlEquipoFacadeDao.ejecQueryNativoGenerico(sentenciaSql);
    }
}
