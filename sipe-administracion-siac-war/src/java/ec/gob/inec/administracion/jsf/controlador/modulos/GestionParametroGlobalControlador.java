/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
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
 * Controlador que permite administrar la información de Parámetros Globales.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionParametroGlobalControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionParametroGlobalControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private AdmParametroGlobal objAdmParametroGlobalActual;
    private List<AdmParametroGlobal> listarParametroGlobals;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private Map<String, Boolean> permisosBoton;
    private boolean esAdmin;
    private AdmParametroGlobal objAdmParametroGlobalAnterior;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objAdmParametroGlobalActual = new AdmParametroGlobal();
            objAdmParametroGlobalAnterior = new AdmParametroGlobal();
            fechaReg = new Date();
            permisosBoton = vistaControlador.getPermisos();

            esAdmin = vistaControlador.getUsuarioControlador().usuarioTieneRol("ADMIN_TOTAL_SIPE");
            /*  List<SegRol> lstRol = getSegRolServicioRemote().listarEjecutarConsulta("listaRolPorUsuario", Arrays.asList(vistaControlador.getUsuarioControlador().getUsuario()));
            for(SegRol sr:lstRol){
                LOGGER.log(Level.INFO,"Rol "+sr.getAlias()+ " "+ sr.getEstadoLogico());
               if( sr.getEstadoLogico() && sr.getAlias().equals("ADMIN_TOTAL_SIPE")){
                   esAdmin=true;
                   break;
               }
            }*/
            listarParametrosGlobalesTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionParametroGlobalControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarParametroGlobal() {
        try {
            if (!objAdmParametroGlobalActual.getNombre().equals(objAdmParametroGlobalAnterior.getNombre())) {
                if (listarParametroGlobals.stream().filter(p -> Objects.nonNull(p.getNombre())).filter(p -> p.getNombre().equals(objAdmParametroGlobalActual.getNombre())).count() > 0) {
                    addWarningMessage("Ya existe un parámetro global con ese nombre.");
                    return;
                }
            }
            if (objAdmParametroGlobalActual.getFechaVigenciaIni() != null && objAdmParametroGlobalActual.getFechaVigenciaFin() != null) {
                if (objAdmParametroGlobalActual.getFechaVigenciaIni().after(objAdmParametroGlobalActual.getFechaVigenciaFin())) {
                    addWarningMessage("Fecha vigencia inicio no puede ser mayor a Fecha vigencia fin");
                    return;
                }
            }
            if (habilitaEdicion == false) {
                objAdmParametroGlobalActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objAdmParametroGlobalActual.setFechaRegistro(fechaReg);
                objAdmParametroGlobalActual.setEstadoLogico(true);
                getAdmParametroGlobalServicioRemote().crearParametroGlobal(objAdmParametroGlobalActual);
                addSuccessMessage("Registro Guardado");
                listarParametrosGlobalesTodos();
                refrescaNuevo();
            } else {
                objAdmParametroGlobalActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getAdmParametroGlobalServicioRemote().editarParametroGlobal(objAdmParametroGlobalActual);
                addSuccessMessage("Registro Actualizado");
                listarParametrosGlobalesTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objAdmParametroGlobalActual = new AdmParametroGlobal();
            objAdmParametroGlobalAnterior = new AdmParametroGlobal();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarParametroGlobalCampos(AdmParametroGlobal parmGlobal) {
        try {
            objAdmParametroGlobalActual = new AdmParametroGlobal();
            objAdmParametroGlobalActual = (AdmParametroGlobal) SerializationUtils.clone(parmGlobal);
            objAdmParametroGlobalAnterior = (AdmParametroGlobal) SerializationUtils.clone(parmGlobal);
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void confirmaEliminar(AdmParametroGlobal parmGlobal) {
        try {
            parmGlobal.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            parmGlobal.setEstadoLogico(false);
            getAdmParametroGlobalServicioRemote().editarParametroGlobal(parmGlobal);
            addSuccessMessage("Registro Eliminado");
            listarParametrosGlobalesTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarParametrosGlobalesTodos() throws Exception {
        listarParametroGlobals = getAdmParametroGlobalServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmParametroGlobal getObjAdmParametroGlobalActual() {
        return objAdmParametroGlobalActual;
    }

    public void setObjAdmParametroGlobalActual(AdmParametroGlobal objAdmParametroGlobalActual) {
        this.objAdmParametroGlobalActual = objAdmParametroGlobalActual;
    }

    public List<AdmParametroGlobal> getListarParametroGlobals() {
        return listarParametroGlobals;
    }

    public void setListarParametroGlobals(List<AdmParametroGlobal> listarParametroGlobals) {
        this.listarParametroGlobals = listarParametroGlobals;
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

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
    //</editor-fold>

}
