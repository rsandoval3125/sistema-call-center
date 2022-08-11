/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang.SerializationUtils;

/**
 * Controlador que permite administrar información de instituciones.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionInstitucionControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionInstitucionControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private AdmInstitucion objAdmInstitucionActual;
    private AdmInstitucion objAdmInstitucionAnterior;
    private List<AdmInstitucion> listarInstitucions;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private Map<String, Boolean> permisosBoton;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objAdmInstitucionActual = new AdmInstitucion();
            objAdmInstitucionAnterior = new AdmInstitucion();
            fechaReg = new Date();
            permisosBoton = vistaControlador.getPermisos();
            listarInstitucionesTodos();
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public GestionInstitucionControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarInstitucion() {
        try {
            /*if (!getAdmInstitucionServicioRemote().validarURL(objAdmInstitucionActual.getWeb())) {
                addWarningMessage("Web inválido.");
                return;
            }*/
            if (!objAdmInstitucionActual.getNombre().equals(objAdmInstitucionAnterior.getNombre())) {
                if (listarInstitucions.stream().filter(p -> Objects.nonNull(p.getNombre())).filter(p -> p.getNombre().toLowerCase().equals(objAdmInstitucionActual.getNombre().toLowerCase())).count() > 0) {
                    addWarningMessage("Ya existe una institución con el mismo nombre.");
                    return;
                }
            }
            if (!getAdmInstitucionServicioRemote().validarCorreo(objAdmInstitucionActual.getEmail())) {
                addWarningMessage("Email inválido.");
                return;
            }
            
            if (habilitaEdicion == false) {
                objAdmInstitucionActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objAdmInstitucionActual.setFechaRegistro(fechaReg);
                objAdmInstitucionActual.setEstadoLogico(true);
                getAdmInstitucionServicioRemote().crearInstitucion(objAdmInstitucionActual);
                addSuccessMessage("Registro Guardado");
                listarInstitucionesTodos();
                refrescaNuevo();
            } else {
                objAdmInstitucionActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getAdmInstitucionServicioRemote().editarInstitucion(objAdmInstitucionActual);
                addSuccessMessage("Registro Actualizado");
                listarInstitucionesTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }
    
    public void refrescaNuevo() {
        try {
            objAdmInstitucionActual = new AdmInstitucion();
            objAdmInstitucionAnterior = new AdmInstitucion();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void recuperarInstitucionCampos(AdmInstitucion institucion) {
        try {
            objAdmInstitucionActual = new AdmInstitucion();
            objAdmInstitucionActual = (AdmInstitucion) SerializationUtils.clone(institucion);
            objAdmInstitucionAnterior = (AdmInstitucion) SerializationUtils.clone(institucion);
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        
    }
    
    public void confirmaEliminar(AdmInstitucion institucion) {
        try {
            institucion.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            institucion.setEstadoLogico(false);
            getAdmInstitucionServicioRemote().editarInstitucion(institucion);
            addSuccessMessage("Registro Eliminado");
            listarInstitucionesTodos();
            objAdmInstitucionActual = new AdmInstitucion();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }
    
    public void listarInstitucionesTodos() throws Exception {
        listarInstitucions = getAdmInstitucionServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }
    
    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }
    
    public AdmInstitucion getObjAdmInstitucionActual() {
        return objAdmInstitucionActual;
    }
    
    public void setObjAdmInstitucionActual(AdmInstitucion objAdmInstitucionActual) {
        this.objAdmInstitucionActual = objAdmInstitucionActual;
    }
    
    public List<AdmInstitucion> getListarInstitucions() {
        return listarInstitucions;
    }
    
    public void setListarInstitucions(List<AdmInstitucion> listarInstitucions) {
        this.listarInstitucions = listarInstitucions;
    }
    
    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }
    
    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }
    
    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }
    
    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
    }
    //</editor-fold>
}
