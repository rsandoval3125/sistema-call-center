/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador que permite administrar la pagina gestionCatalogos
 *
 * @author dgarcia
 */
@ManagedBean
@ViewScoped
public class GestionOperativoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionOperativoControlador.class.getName());
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    private MetOperativo objOperativo;
    private List<MetOperativo> listarOperativo;
    private List<AdmOperacionEstadistica> tiposOpE;
    private List<MetOperativo> listarEditOperativo;
    private List<AdmOperacionEstadistica> opeEs;
    private List<AdmPeriodo> periodo;
    private boolean habilitaEdicion;
    private Map<Integer, String> mapaOpeEs;
    private Map<Integer, String> mapaPeriodo;
     @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
   

    

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objOperativo = new MetOperativo();
            //--Invocación a EJB remoto

            //--Fin envocación a EJB remoto
            listarOperacionesTodos();
           
            opeEs = new ArrayList<>();
            opeEs = getAdmOperacionEstadisticaServicioRemote().listarTodo();
            mapaOpeEs = new HashMap<>();
            for(AdmOperacionEstadistica oe : opeEs){
                mapaOpeEs.put(oe.getIdOpeEst(), oe.getNombre());
            }
            
            periodo = new ArrayList<>();
            periodo = getAdmPeriodoServicioRemote().listarTodo();
            mapaPeriodo = new HashMap<>();
            for(AdmPeriodo per : periodo){
                mapaPeriodo.put(per.getIdPeriodo(), per.getNombre());
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionOperativoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
   
    //Metodos Operativo
     public void listarOperacionesTodos() throws Exception{
         listarOperativo = getMetOperativoServicioRemote().listarTodosOperativo();
         listarEditOperativo = listarOperativo;
    }
     
    public void recuperarOperativoCampos(MetOperativo operativo) {
        try {
            objOperativo = new MetOperativo();
            objOperativo = operativo;
            listarEditOperativo = getMetOperativoServicioRemote().listarTodosOperativoPorId(objOperativo.getIdOperativo());
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void confirmaEliminarOperativo(MetOperativo operativo) {
        try {
            operativo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            operativo.setEstadoLogico(false);
            getMetOperativoServicioRemote().editarOperativo(operativo);
            addSuccessMessage("Registro Eliminado");
            listarOperacionesTodos();
            objOperativo = new MetOperativo();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }
    
    public void guardarOperativo() {
        try {
            if (habilitaEdicion == false) {
                objOperativo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objOperativo.setEstadoLogico(true);
                getMetOperativoServicioRemote().crearOperativo(objOperativo);
                addSuccessMessage("Registro Guardado");
                listarOperacionesTodos();
                refrescaNuevoOperativo();
            } else {
                objOperativo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getMetOperativoServicioRemote().editarOperativo(objOperativo);
                addSuccessMessage("Registro Actualizado");
                listarOperacionesTodos();
                refrescaNuevoOperativo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }
    
    public void refrescaNuevoOperativo() {
        try {
            objOperativo = new MetOperativo();
            listarEditOperativo = getMetOperativoServicioRemote().listarTodosOperativo();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    
    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public List<AdmOperacionEstadistica> getTiposOpE() {
        return tiposOpE;
    }
    
    public void setTiposOpE(List<AdmOperacionEstadistica> tiposOpE) {
        this.tiposOpE = tiposOpE;
    }

    public MetOperativo getObjOperativo() {
        return objOperativo;
    }

    public void setObjoperativo(MetOperativo objOperativo) {
        this.objOperativo = objOperativo;
    }

    public List<MetOperativo> getListarOperativo() {
        return listarOperativo;
    }

    public void setListarOperativo(List<MetOperativo> listarOperativo) {
        this.listarOperativo = listarOperativo;
    }

    public List<MetOperativo> getListarEditOperativo() {
        return listarEditOperativo;
    }

    public void setListarEditOperativo(List<MetOperativo> listarEditOperativo) {
        this.listarEditOperativo = listarEditOperativo;
    }

    public List<AdmOperacionEstadistica> getOpeEs() {
        return opeEs;
    }

    public void setOpeEs(List<AdmOperacionEstadistica> opeEs) {
        this.opeEs = opeEs;
    }

    public Map<Integer, String> getMapaOpeEs() {
        return mapaOpeEs;
    }

    public void setMapaOpeEs(Map<Integer, String> mapaOpeEs) {
        this.mapaOpeEs = mapaOpeEs;
    }

    public List<AdmPeriodo> getPeriodo() {
        return periodo;
    }

    public void setPeriodo(List<AdmPeriodo> periodo) {
        this.periodo = periodo;
    }

    public Map<Integer, String> getMapaPeriodo() {
        return mapaPeriodo;
    }

    public void setMapaPeriodo(Map<Integer, String> mapaPeriodo) {
        this.mapaPeriodo = mapaPeriodo;
    }
    
    
    //</editor-fold>

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }
}
