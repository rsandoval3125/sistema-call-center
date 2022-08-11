/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
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
import javax.faces.context.FacesContext;
import org.apache.commons.lang.SerializationUtils;

/**
 * Controlador que permite administrar información de Operaciones Estadísticas.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionOperacionEstadisticaControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionOperacionEstadisticaControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    AdmInstitucion institucion;
    private List<AdmInstitucion> instituciones;
    AdmOrganigrama organigrama;
    private List<AdmOrganigrama> organigramas;
    private AdmOperacionEstadistica objAdmOperacionEstadisticaActual;
    private List<AdmOperacionEstadistica> listarOperacionEstadisticas;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private List<AdmOperacionEstadistica> operacionEstadisticasPadre;
    private Map<String, Boolean> permisosBoton;
    private AdmOperacionEstadistica objAdmOperacionEstadisticaAnterior;
    private boolean institucionRequerido;
    private boolean organigramaRequerido;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objAdmOperacionEstadisticaActual = new AdmOperacionEstadistica();
            objAdmOperacionEstadisticaAnterior = new AdmOperacionEstadistica();
            fechaReg = new Date();
            permisosBoton = vistaControlador.getPermisos();

            instituciones = new ArrayList<>();
            instituciones = getAdmInstitucionServicioRemote().listarTodosActivos();
            listarOperacionEstadisticaTodos();
            // organigramas = new ArrayList<>();
            //organigramas = getAdmOrganigramaServicioRemote().listarTodosActivos();
            onChangeInstitucion();
          //  onChangeOrganigrama();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionOperacionEstadisticaControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarOperacionEstadistica() {
        try {
            if (!objAdmOperacionEstadisticaActual.getNombre().equals(objAdmOperacionEstadisticaAnterior.getNombre())) {
                if (listarOperacionEstadisticas.stream().filter(p -> Objects.nonNull(p.getNombre())).filter(p -> p.getNombre().toLowerCase().equals(objAdmOperacionEstadisticaActual.getNombre().toLowerCase())).count() > 0) {
                    addWarningMessage("Ya existe una operación estadistica con el mismo nombre.");
                    return;
                }
            }
            objAdmOperacionEstadisticaActual.setCodInstitucion(institucion);
            objAdmOperacionEstadisticaActual.setCodOrganigrama(organigrama);
            if (habilitaEdicion == false) {

                objAdmOperacionEstadisticaActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objAdmOperacionEstadisticaActual.setFechaRegistro(fechaReg);
                objAdmOperacionEstadisticaActual.setEstadoLogico(true);
                getAdmOperacionEstadisticaServicioRemote().crearOperacionEstadistica(objAdmOperacionEstadisticaActual);
                addSuccessMessage("Registro Guardado");
                listarOperacionEstadisticaTodos();
                refrescaNuevo();
            } else {
                objAdmOperacionEstadisticaActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getAdmOperacionEstadisticaServicioRemote().editarOperacionEstadistica(objAdmOperacionEstadisticaActual);
                addSuccessMessage("Registro Actualizado");
                listarOperacionEstadisticaTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objAdmOperacionEstadisticaActual = new AdmOperacionEstadistica();
            objAdmOperacionEstadisticaAnterior = new AdmOperacionEstadistica();
            institucion = new AdmInstitucion();
            organigrama = new AdmOrganigrama();
            operacionEstadisticasPadre = getAdmOperacionEstadisticaServicioRemote().listarTodosActivos();
            habilitaEdicion = false;
            onChangeInstitucion();
           // onChangeOrganigrama();
            //System.out.println("institucionRequerido="+institucionRequerido);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarOperacionEstadisticaCampos(AdmOperacionEstadistica operacionEstadistica) {
        try {
            objAdmOperacionEstadisticaActual = new AdmOperacionEstadistica();
            objAdmOperacionEstadisticaActual = (AdmOperacionEstadistica) SerializationUtils.clone(operacionEstadistica);
            objAdmOperacionEstadisticaAnterior = (AdmOperacionEstadistica) SerializationUtils.clone(operacionEstadistica);
            institucion = new AdmInstitucion();
            institucion.setIdInstitucion((operacionEstadistica.getCodInstitucion() == null) ? 0 : operacionEstadistica.getCodInstitucion().getIdInstitucion());
            organigrama = new AdmOrganigrama();
            organigrama.setIdOrganigrama((operacionEstadistica.getCodOrganigrama() == null) ? 0 : operacionEstadistica.getCodOrganigrama().getIdOrganigrama());
            // operacionEstadisticasPadre = getAdmOperacionEstadisticaServicioRemote().listarTodosActivosSinUno(objAdmOperacionEstadisticaActual.getIdOpeEst());
            onChangeInstitucion();
           // onChangeOrganigrama();
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onChangeInstitucion() {
        try {
              Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (params.get("action") != null) {
                System.out.println("onChangeInstitucion");
                organigrama = new AdmOrganigrama();
            }

            if (institucion != null && institucion.getIdInstitucion() != null) {
                institucionRequerido = false;
                organigramas = getAdmOrganigramaServicioRemote().listarTodosActivosPorInstitucion(institucion.getIdInstitucion());
            } else {
                organigramas = new ArrayList<>();
                institucionRequerido = true;
            }
            onChangeOrganigrama();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onChangeOrganigrama() {
        try {
            if (organigrama != null && organigrama.getIdOrganigrama() != null) {
                organigramaRequerido = false;
                operacionEstadisticasPadre = getAdmOperacionEstadisticaServicioRemote().listarTodosActivosPorInstitucionYOrganigrama(institucion.getIdInstitucion(), organigrama.getIdOrganigrama());
                operacionEstadisticasPadre.removeIf(p -> p.getIdOpeEst().equals(objAdmOperacionEstadisticaActual.getIdOpeEst()));
            } else {
                operacionEstadisticasPadre = new ArrayList<>();
                organigramaRequerido = true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(AdmOperacionEstadistica operacionEstadistica) {
        try {
            operacionEstadistica.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            operacionEstadistica.setEstadoLogico(false);
            getAdmOperacionEstadisticaServicioRemote().editarOperacionEstadistica(operacionEstadistica);
            addSuccessMessage("Registro Eliminado");
            listarOperacionEstadisticaTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarOperacionEstadisticaTodos() throws Exception {
        listarOperacionEstadisticas = getAdmOperacionEstadisticaServicioRemote().listarTodosActivos();
        // operacionEstadisticasPadre = getAdmOperacionEstadisticaServicioRemote().listarTodosActivos();

    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmOperacionEstadistica getObjAdmOperacionEstadisticaActual() {
        return objAdmOperacionEstadisticaActual;
    }

    public void setObjAdmOperacionEstadisticaActual(AdmOperacionEstadistica objAdmOperacionEstadisticaActual) {
        this.objAdmOperacionEstadisticaActual = objAdmOperacionEstadisticaActual;
    }

    public List<AdmOperacionEstadistica> getListarOperacionEstadisticas() {
        return listarOperacionEstadisticas;
    }

    public void setListarOperacionEstadisticas(List<AdmOperacionEstadistica> listarOperacionEstadisticas) {
        this.listarOperacionEstadisticas = listarOperacionEstadisticas;
    }

    public List<AdmInstitucion> getInstituciones() {
        return instituciones;
    }

    public void setInstituciones(List<AdmInstitucion> instituciones) {
        this.instituciones = instituciones;
    }

    public AdmInstitucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(AdmInstitucion institucion) {
        this.institucion = institucion;
    }

    public AdmOrganigrama getOrganigrama() {
        return organigrama;
    }

    public void setOrganigrama(AdmOrganigrama organigrama) {
        this.organigrama = organigrama;
    }

    public List<AdmOrganigrama> getOrganigramas() {
        return organigramas;
    }

    public void setOrganigramas(List<AdmOrganigrama> organigramas) {
        this.organigramas = organigramas;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public List<AdmOperacionEstadistica> getOperacionEstadisticasPadre() {
        return operacionEstadisticasPadre;
    }

    public void setOperacionEstadisticasPadre(List<AdmOperacionEstadistica> operacionEstadisticasPadre) {
        this.operacionEstadisticasPadre = operacionEstadisticasPadre;
    }

    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }

    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
    }

    public boolean isInstitucionRequerido() {
        return institucionRequerido;
    }

    public void setInstitucionRequerido(boolean institucionRequerido) {
        this.institucionRequerido = institucionRequerido;
    }

    public boolean isOrganigramaRequerido() {
        return organigramaRequerido;
    }

    public void setOrganigramaRequerido(boolean organigramaRequerido) {
        this.organigramaRequerido = organigramaRequerido;
    }
    //</editor-fold>

}
