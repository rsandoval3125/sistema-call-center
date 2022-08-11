/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
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
public class GestionFormularioControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionFormularioControlador.class.getName());
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    
    private List<MetFormulario> listarFormulario;
    private List<MetFormulario> listarEditFormulario;
    private MetFormulario objFormulario;
    private boolean habilitaEdicion;
    private List<AdmOperacionEstadistica> opeEs;
    private Map<Integer, String> mapaOpeEs;
    private List<AdmPeriodo> periodo;
    private Map<Integer, String> mapaPeriodo;
    private List<MetOperativo> operativo;
     @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
   

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            //--Invocación a EJB remoto

            //--Fin envocación a EJB remoto
            listarFormulariosTodos();
            objFormulario = new MetFormulario();
            operativo = getMetOperativoServicioRemote().listarTodo();
            
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

    public GestionFormularioControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
   
    //Metodos Formulario
     public void listarFormulariosTodos() throws Exception{
         listarFormulario = getMetFormularioServicioRemote().listarFormulariosTodos();
    }
     
    public void recuperarFormularioCampos(MetFormulario formulario) {
        try {
            objFormulario = new MetFormulario();
            objFormulario = formulario;
            listarEditFormulario = getMetFormularioServicioRemote().listarFormulariosPorId(objFormulario.getIdFormulario());
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void confirmaEliminarFormulario(MetFormulario formulario) {
        try {
            formulario.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            formulario.setEstadoLogico(false);
            getMetFormularioServicioRemote().editarFormulario(formulario);
            addSuccessMessage("Registro Eliminado");
            listarFormulariosTodos();
            objFormulario = new MetFormulario();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }
    
    public void guardarFormualario() {
        try {
            if (habilitaEdicion == false) {
                objFormulario.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objFormulario.setEstadoLogico(true);
                getMetFormularioServicioRemote().crearFormulario(objFormulario);
                addSuccessMessage("Registro Guardado");
                listarFormulariosTodos();
                refrescaNuevoFormulario();
            } else {
                objFormulario.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getMetFormularioServicioRemote().editarFormulario(objFormulario);
                addSuccessMessage("Registro Actualizado");
                listarFormulariosTodos();
                refrescaNuevoFormulario();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }
    
    public void refrescaNuevoFormulario() {
        try {
            objFormulario = new MetFormulario();
            listarEditFormulario = getMetFormularioServicioRemote().listarFormulariosTodos();
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
    
        public List<MetFormulario> getListarFormulario() {
        return listarFormulario;
    }

    public void setListarFormulario(List<MetFormulario> listarFormulario) {
        this.listarFormulario = listarFormulario;
    }

    public List<MetFormulario> getListarEditFormulario() {
        return listarEditFormulario;
    }

    public void setListarEditFormulario(List<MetFormulario> listarEditFormulario) {
        this.listarEditFormulario = listarEditFormulario;
    }

    public MetFormulario getObjFormulario() {
        return objFormulario;
    }

    public void setObjFormulario(MetFormulario objFormulario) {
        this.objFormulario = objFormulario;
    }

    public List<MetOperativo> getOperativo() {
        return operativo;
    }

    public void setOperativo(List<MetOperativo> operativo) {
        this.operativo = operativo;
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
