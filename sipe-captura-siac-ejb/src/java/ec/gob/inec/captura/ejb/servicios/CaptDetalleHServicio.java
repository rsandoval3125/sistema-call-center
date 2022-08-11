/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.captura.clases.CaptFilaH;
import ec.gob.inec.captura.clases.CaptSeccionH;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.ejb.entidades.OperadorAsignacion;
import ec.gob.inec.captura.ejb.facade.CaptDetalleHFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rmoreano
 */
@Stateless
public class CaptDetalleHServicio implements CaptDetalleHServicioLocal, CaptDetalleHServicioRemote {

    @EJB
    private CaptDetalleHFacade detalleHDao;
    private final String ENT = "CaptDetalleH";
    private final String ID = "idDetH";
    private final String NUMDET = "numDet";
    private final String CODCAB = "codCab";
    

    public void crearDetalleH(CaptDetalleH captDetalleH) throws Exception {
        detalleHDao.crear(captDetalleH);
    }
    
    public void editarDetalleH(CaptDetalleH captDetalleH) throws Exception {
        detalleHDao.editar(captDetalleH);
    }
   
    public void eliminarDetalleH(CaptDetalleH captDetalleH) throws Exception {
        detalleHDao.eliminar(captDetalleH);
    }

    @Override
    public List<CaptDetalleH> listarFormDetPorCodCab(CaptCabecera captCabecera) throws Exception {
        return detalleHDao.listarContienePorCampoOrdenada(ENT, CODCAB, captCabecera, NUMDET, "asc");
    }

    @Override
    public boolean existeFormPorEdificio(String clave, String vEdif) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("clave", clave);
        campos.put("val01", vEdif);
        return detalleHDao.existePorCampos(ENT, campos, NUMDET);
    }

    @Override
    public int contarFormPorEdificio(String clave, String vEdif) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("clave", clave);
        campos.put("val01", vEdif);
        return detalleHDao.contarPorCampos(ENT, campos);
    }    
  
    @Override
    public List<CaptDetalleH> listarTodo() throws Exception {
        return detalleHDao.listarTodo();       
    }
    
    public void crearDetallesHDeFormulario(String tipoFormulario, Integer codCab, List<CaptFilaH> listaVariables, Integer numElementos) throws Exception{
        detalleHDao.crearDetallesHDeFormulario(tipoFormulario, codCab, listaVariables, numElementos);
    }
    
     public void crearDetallesHDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptFilaH> listaVariables, Integer numNuevoElemento) throws Exception{
         detalleHDao.crearDetallesHDeFormularioPorElemento(tipoFormulario, codCab, listaVariables, numNuevoElemento);
     }
     
     public void actualizarDetallesHDeFormularioPorSeccion(CaptSeccionH seccionH) throws Exception{
         detalleHDao.actualizarDetallesHDeFormularioPorSeccion(seccionH);
     }
     
      public void actualizarDetallesHDeFormulario(List<CaptSeccionH> listaSeccionH) throws Exception{
          detalleHDao.actualizarDetallesHDeFormulario(listaSeccionH);
      }
      
      public void eliminarDetallesHDeFormularioyElemento(Integer codCab,Integer numElemento) throws Exception{
          detalleHDao.eliminarPor2Campos(ENT, "codCab", codCab, "numDet", numElemento);
      }
      
      public void eliminarDetallesHPorId(Integer idDeth) throws Exception{
          detalleHDao.eliminarGenerico(ENT, "idDeth", idDeth);
      }
      
      public List<CaptSeccionH> listarSeccionesHPorListaFilasFormulario(List<CaptFilaH> listaFilasHFormulario) throws Exception{
          List<CaptSeccionH> seccionesH=new ArrayList<>();
          CaptSeccionH sh=new CaptSeccionH(listaFilasHFormulario.get(0).getCodSeccion(),listaFilasHFormulario.get(0).getTipoSeccion(),listaFilasHFormulario.get(0).getColumnas());
          List<CaptFilaH> filasSH=new ArrayList<>();
          for(int i=0;i<listaFilasHFormulario.size();i++){
             if(!sh.getCodSeccion().equals(listaFilasHFormulario.get(i).getCodSeccion())){
                 sh.setFilas(filasSH);
                 seccionesH.add(sh);
                 sh=new CaptSeccionH(listaFilasHFormulario.get(i).getCodSeccion(),listaFilasHFormulario.get(i).getTipoSeccion(),listaFilasHFormulario.get(i).getColumnas());
                 filasSH=new ArrayList<>();
                 filasSH.add(listaFilasHFormulario.get(i));
             }else{
                 filasSH.add(listaFilasHFormulario.get(i));
             }
             
          }
          sh.setFilas(filasSH);
          seccionesH.add(sh);
           
           
          return seccionesH;
      }
      public List<CaptFilaH> listarFilasHPorFormulario(int idFormulario) throws Exception{
          return detalleHDao.listarFilasHPorFormulario(idFormulario);
      }
       public List<CaptFilaH> listarFilasHPorCabeceraCreadayElemento(int idCab, int numDet) throws Exception{
           return detalleHDao.listarFilasHPorCabeceraCreadayElemento(idCab, numDet);
       }
       
      public List<CaptFilaH> listarRegistrosPorCabPrecenso(Integer codCab) throws Exception{
           return detalleHDao.listarRegistrosPorCabPrecenso(codCab);
       }
      
      public void reordenamientoRegistrosPrecenso(Integer codCab, Integer numDetActual, Integer ordenActual, String colsOrdena, String colsWhere, String signoReord) throws Exception{
          detalleHDao.reordenamientoRegistrosPrecenso(codCab, numDetActual, ordenActual,  colsOrdena, colsWhere,signoReord);
          
      }
      public void eliminarYReordenarRegistrosPrecenso(CaptFilaH registroAEliminar,String tipoReg) throws Exception{
          detalleHDao.eliminarYReordenarRegistrosPrecenso(registroAEliminar,tipoReg);
      }
      
      @Override
    public List<CaptDetalleH> listarDetHPorCab(Integer codCab) throws Exception {
        return detalleHDao.listarDetHPorCab(codCab);
    }

    @Override
    public CaptDetalleH buscarDetHPorId(Integer idDeth) throws Exception {
        return detalleHDao.buscarDetHPorId(idDeth);
    }

    @Override
    public String crearFormDetalle(CaptDetalleH captDetalle) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String editarFormDetalle(CaptDetalleH captDetalle) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminarFormDetalle(Integer idDetalle) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       @Override
    public List<CaptDetalleH> listarDetHPorCabOrderIpc(Integer codCab) throws Exception { //
        return detalleHDao.listarDetHPorCabOrderIpc(codCab);
    }

    @Override
    public List<CaptDetalleH> listarDetHPorCabXSeccion(Integer codCab, Integer codSeccion) throws Exception {
        return  detalleHDao.listarDetHPorCabXSeccion(codCab, codSeccion);
    }
    
    @Override
    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return detalleHDao.listarEjecutarConsultaObj(nombreProdecimiento, parametrosOrdenados);
    }
    
    @Override
    public List<CaptDetalleH> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {
        return detalleHDao.listarEjecutarConsulta(nombreProdecimiento, parametrosOrdenados);
    }

    @Override
    public List<CaptDetalleH> listarIncidencias(String varEstado, String prov, String can, String ciu) throws Exception {
        return  detalleHDao.listarIncidencias(varEstado,prov,can,ciu);
    }
    
    @Override
    public List<CaptDetalleH> listarAsignados(String varEstado) throws Exception {
        return  detalleHDao.listarAsignados(varEstado);
    }
    
    @Override
    public List<CaptDetalleH> listarIncidenciasxFiltro(String prov, String can, String ciu) throws Exception {
       return  detalleHDao.listarIncidenciasxFiltro(prov, can, ciu);
    }
    
    @Override
    public List<OperadorAsignacion> contarAsignacionPorUsuario(Integer codProvincia) throws Exception{
       return detalleHDao.contarAsignacionPorUsuario(codProvincia);
    }

//    @Override
//    public Integer buscarUsuarioRandom() throws Exception {
//        return detalleHDao.buscarUsuarioRandom();
//    }

    

}
