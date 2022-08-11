/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang.SerializationUtils;

/**
 * Controlador que permite administrar información de organigramas.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionOrganigramaControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionOrganigramaControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private List<AdmInstitucion> instituciones;
    private AdmOrganigrama objAdmOrganigramaActual;
    private List<AdmOrganigrama> listarOrganigramas;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private Map<String, Boolean> permisosBoton;
    private List<AdmOrganigrama> organigramasPadre;
    private List<MetCatalogo> tiposOrganigrama;
    private boolean institucionRequerida;

    MetCatalogo provinciaSelected;
    MetCatalogo cantonSelected;
    MetCatalogo parroquiaSelected;
    private List<MetCatalogo> provincias;
    private List<MetCatalogo> cantones;
    private List<MetCatalogo> parroquias;
    private boolean provinciaRequerido;
    private boolean cantonRequerido;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            institucionRequerida = true;
            habilitaEdicion = false;
            objAdmOrganigramaActual = new AdmOrganigrama();
            fechaReg = new Date();
            permisosBoton = vistaControlador.getPermisos();
            tiposOrganigrama = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIP_ORG");
            listarOrganigramaTodos();

            instituciones = new ArrayList<>();
            instituciones = getAdmInstitucionServicioRemote().listarTodosActivos();
            onChangeInstitucion();

            provincias = getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_DPA_PROV");
            onChangeProvincia();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionOrganigramaControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarOrganigrama() {
        try {
            if (objAdmOrganigramaActual.getFechaInicio() != null && objAdmOrganigramaActual.getFechaFin() != null) {
                if (objAdmOrganigramaActual.getFechaInicio().after(objAdmOrganigramaActual.getFechaFin())) {
                    addWarningMessage("Fecha inicio no puede ser mayor a Fecha fin");
                    return;
                }
            }
            if (habilitaEdicion == false) {
                objAdmOrganigramaActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objAdmOrganigramaActual.setFechaRegistro(fechaReg);
                objAdmOrganigramaActual.setEstadoLogico(true);
                getAdmOrganigramaServicioRemote().crearOrganigrama(objAdmOrganigramaActual);
                addSuccessMessage("Registro Guardado");
                listarOrganigramaTodos();
                refrescaNuevo();
            } else {
                objAdmOrganigramaActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getAdmOrganigramaServicioRemote().editarOrganigrama(objAdmOrganigramaActual);
                addSuccessMessage("Registro Actualizado");
                listarOrganigramaTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objAdmOrganigramaActual = new AdmOrganigrama();
            organigramasPadre = getAdmOrganigramaServicioRemote().listarTodosActivos();
            habilitaEdicion = false;
            provinciaSelected = new MetCatalogo();
            cantonSelected = new MetCatalogo();
            onChangeProvincia();

            onChangeInstitucion();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarOrganigramaCampos(AdmOrganigrama organigrama) {
        try {
            habilitaEdicion = true;
            objAdmOrganigramaActual = new AdmOrganigrama();
            objAdmOrganigramaActual = (AdmOrganigrama) SerializationUtils.clone(organigrama);
            // organigramasPadre = getAdmOrganigramaServicioRemote().listarTodosActivosSinUno(organigrama.getIdOrganigrama());
            System.out.println("PARROQUIA " + objAdmOrganigramaActual.getCodDpa().getIdCatalogo());
            cantonSelected = getMetCatalogoServicioRemote().buscarCatalogoXId(objAdmOrganigramaActual.getCodDpa().getCodPadre1().getIdCatalogo());
            System.out.println("CANTON " + cantonSelected.getIdCatalogo());
            provinciaSelected = getMetCatalogoServicioRemote().buscarCatalogoXId(cantonSelected.getCodPadre1().getIdCatalogo());
            System.out.println("PROVINCIA " + provinciaSelected.getIdCatalogo());
            onChangeProvincia();
            onChangeInstitucion();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void onChangeInstitucion() {
        try {
            if (objAdmOrganigramaActual.getCodInstitucion() == null) {
                //  System.out.println("onChangeInstitucion NADA");
                organigramasPadre = new ArrayList<AdmOrganigrama>();
                institucionRequerida = true;
            } else {
                //  System.out.println("onChangeInstitucion " + objAdmOrganigramaActual.getCodInstitucion().getIdInstitucion());
                institucionRequerida = false;
                organigramasPadre = getAdmOrganigramaServicioRemote().listarTodosActivosPorInstitucion(objAdmOrganigramaActual.getCodInstitucion().getIdInstitucion());
                organigramasPadre.removeIf(p -> p.getIdOrganigrama().equals(objAdmOrganigramaActual.getIdOrganigrama()));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onChangeProvincia() {
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (params.get("action") != null) {
                System.out.println("onChangeProvincia");
                cantonSelected = new MetCatalogo();
            }
            if (provinciaSelected != null && provinciaSelected.getIdCatalogo() != null) {
                provinciaRequerido = false;
                cantones = getMetCatalogoServicioRemote().listarCatalogosPorPadre1(provinciaSelected.getIdCatalogo());
            } else {
                cantones = new ArrayList<>();
                provinciaRequerido = true;
            }

            onChangeCanton();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onChangeCanton() {
        try {
            if (cantonSelected != null && cantonSelected.getIdCatalogo() != null) {
                cantonRequerido = false;
                parroquias = getMetCatalogoServicioRemote().listarCatalogosPorPadre1(cantonSelected.getIdCatalogo());
            } else {
                parroquias = new ArrayList<>();
                cantonRequerido = true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(AdmOrganigrama organigrama) {
        try {
            organigrama.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            organigrama.setEstadoLogico(false);
            getAdmOrganigramaServicioRemote().editarOrganigrama(organigrama);
            addSuccessMessage("Registro Eliminado");
            listarOrganigramaTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarOrganigramaTodos() throws Exception {
        listarOrganigramas = getAdmOrganigramaServicioRemote().listarTodosActivos();
        //organigramasPadre = getAdmOrganigramaServicioRemote().listarTodosActivos();

    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmOrganigrama getObjAdmOrganigramaActual() {
        return objAdmOrganigramaActual;
    }

    public void setObjAdmOrganigramaActual(AdmOrganigrama objAdmOrganigramaActual) {
        this.objAdmOrganigramaActual = objAdmOrganigramaActual;
    }

    public List<AdmOrganigrama> getListarOrganigramas() {
        return listarOrganigramas;
    }

    public void setListarOrganigramas(List<AdmOrganigrama> listarOrganigramas) {
        this.listarOrganigramas = listarOrganigramas;
    }

    public List<AdmInstitucion> getInstituciones() {
        return instituciones;
    }

    public void setInstituciones(List<AdmInstitucion> instituciones) {
        this.instituciones = instituciones;
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

    public List<AdmOrganigrama> getOrganigramasPadre() {
        return organigramasPadre;
    }

    public void setOrganigramasPadre(List<AdmOrganigrama> organigramasPadre) {
        this.organigramasPadre = organigramasPadre;
    }

    public List<MetCatalogo> getTiposOrganigrama() {
        return tiposOrganigrama;
    }

    public void setTiposOrganigrama(List<MetCatalogo> tiposOrganigrama) {
        this.tiposOrganigrama = tiposOrganigrama;
    }

    public boolean isInstitucionRequerida() {
        return institucionRequerida;
    }

    public void setInstitucionRequerida(boolean institucionRequerida) {
        this.institucionRequerida = institucionRequerida;
    }

    public MetCatalogo getProvinciaSelected() {
        return provinciaSelected;
    }

    public void setProvinciaSelected(MetCatalogo provinciaSelected) {
        this.provinciaSelected = provinciaSelected;
    }

    public MetCatalogo getCantonSelected() {
        return cantonSelected;
    }

    public void setCantonSelected(MetCatalogo cantonSelected) {
        this.cantonSelected = cantonSelected;
    }

    public MetCatalogo getParroquiaSelected() {
        return parroquiaSelected;
    }

    public void setParroquiaSelected(MetCatalogo parroquiaSelected) {
        this.parroquiaSelected = parroquiaSelected;
    }

    public List<MetCatalogo> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<MetCatalogo> provincias) {
        this.provincias = provincias;
    }

    public List<MetCatalogo> getCantones() {
        return cantones;
    }

    public void setCantones(List<MetCatalogo> cantones) {
        this.cantones = cantones;
    }

    public List<MetCatalogo> getParroquias() {
        return parroquias;
    }

    public void setParroquias(List<MetCatalogo> parroquias) {
        this.parroquias = parroquias;
    }

    public boolean isProvinciaRequerido() {
        return provinciaRequerido;
    }

    public void setProvinciaRequerido(boolean provinciaRequerido) {
        this.provinciaRequerido = provinciaRequerido;
    }

    public boolean isCantonRequerido() {
        return cantonRequerido;
    }

    public void setCantonRequerido(boolean cantonRequerido) {
        this.cantonRequerido = cantonRequerido;
    }
    //</editor-fold>  
}
