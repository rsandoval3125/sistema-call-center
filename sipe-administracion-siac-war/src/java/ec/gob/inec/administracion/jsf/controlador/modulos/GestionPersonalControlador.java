/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
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
 * Controlador que permite administrar informacion del personal.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionPersonalControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionPersonalControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private AdmPersonal objAdmPersonalActual;
    private List<AdmPersonal> listarPersonals;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private AdmInstitucion institucion;
    private List<AdmInstitucion> instituciones;
    private AdmOrganigrama organigrama;
    private List<AdmOrganigrama> organigramas;
    private List<MetCatalogo> cargos;
    private List<MetCatalogo> nivelesInstruccion;
    private List<MetCatalogo> tipoContratos;
    private List<MetCatalogo> nivelesSalario;

    private Map<Integer, String> mapaCargos;
    private Map<Integer, String> mapaContratos;
    private Map<String, Boolean> permisosBoton;
    private AdmPersonal objAdmPersonalAnterior;
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
            habilitaEdicion = false;
            objAdmPersonalActual = new AdmPersonal();
            objAdmPersonalAnterior = new AdmPersonal();
            fechaReg = new Date();
            permisosBoton = vistaControlador.getPermisos();
            listarPersonalesTodos();
            instituciones = new ArrayList<>();
            instituciones = getAdmInstitucionServicioRemote().listarTodosActivos();

            /* organigramas = new ArrayList<>();
            organigramas = getAdmOrganigramaServicioRemote().listarTodosActivos();*/
            onChangeInstitucion();

            cargos = new ArrayList<>();
            cargos = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIP_CAR");
            mapaCargos = new HashMap<>();
            for (MetCatalogo mc : cargos) {
                mapaCargos.put(mc.getIdCatalogo(), mc.getNombre());
            }

            nivelesInstruccion = new ArrayList<>();
            nivelesInstruccion = getMetCatalogoServicioRemote().listarCatalogoXAlias("MLT_CL_NIVEL_INSTRUCCION");

            nivelesSalario = new ArrayList<>();
            nivelesSalario = getMetCatalogoServicioRemote().listarCatalogoXAlias("NIV_SAL");

            tipoContratos = new ArrayList<>();
            tipoContratos = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIP_CONTR");
            mapaContratos = new HashMap<>();
            for (MetCatalogo mc : tipoContratos) {
                mapaContratos.put(mc.getIdCatalogo(), mc.getNombre());
            }
            provincias = getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_DPA_PROV");
            onChangeProvincia();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionPersonalControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarPersonal() {
        try {
            if (!objAdmPersonalActual.getIdentificacion().equals(objAdmPersonalAnterior.getIdentificacion())) {
                if (listarPersonals.stream().filter(p -> Objects.nonNull(p.getIdentificacion())).filter(p -> p.getIdentificacion().equals(objAdmPersonalActual.getIdentificacion())).count() > 0) {
                    addWarningMessage("Ya existe personal con esa identificación.");
                    return;
                }
            }
            /* if (!objAdmPersonalActual.getUsuario().equals(objAdmPersonalAnterior.getUsuario())) {
                System.out.println(objAdmPersonalActual.getUsuario());
                if (listarPersonals.stream().filter(p -> Objects.nonNull(p.getUsuario())).filter(p -> p.getUsuario().equals(objAdmPersonalActual.getUsuario())).count() > 0) {
                    addWarningMessage("Ya existe personal con ese usuario.");
                    return;
                }
            }*/

            objAdmPersonalActual.setCodInstitucion(institucion);
            objAdmPersonalActual.setCodOrganigrama(organigrama);

            if (habilitaEdicion == false) {
                objAdmPersonalActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objAdmPersonalActual.setFechaRegistro(fechaReg);
                objAdmPersonalActual.setEstadoLogico(true);
                getAdmPersonalServicioRemote().crearPersonal(objAdmPersonalActual);
                addSuccessMessage("Registro Guardado");
                listarPersonalesTodos();
                refrescaNuevo();
            } else {
                objAdmPersonalActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getAdmPersonalServicioRemote().editarPersonal(objAdmPersonalActual);
                addSuccessMessage("Registro Actualizado");
                listarPersonalesTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objAdmPersonalActual = new AdmPersonal();
            objAdmPersonalAnterior = new AdmPersonal();
            institucion = new AdmInstitucion();
            organigrama = new AdmOrganigrama();
            habilitaEdicion = false;
            provinciaSelected = new MetCatalogo();
            cantonSelected = new MetCatalogo();
            onChangeProvincia();

            onChangeInstitucion();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarPersonalCampos(AdmPersonal personal) {
        try {
            habilitaEdicion = true;
            objAdmPersonalActual = new AdmPersonal();
            objAdmPersonalActual = (AdmPersonal) SerializationUtils.clone(personal);
            institucion = personal.getCodInstitucion();
            organigrama = personal.getCodOrganigrama();
            objAdmPersonalAnterior = (AdmPersonal) SerializationUtils.clone(personal);
            System.out.println("PARROQUIA " + objAdmPersonalActual.getCodDpa().getIdCatalogo());
            cantonSelected = getMetCatalogoServicioRemote().buscarCatalogoXId(objAdmPersonalActual.getCodDpa().getCodPadre1().getIdCatalogo());
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
            if (institucion != null && institucion.getIdInstitucion() != null) {
                //  System.out.println("onChangeInstitucion " + objAdmOrganigramaActual.getCodInstitucion().getIdInstitucion());
                institucionRequerida = false;
                organigramas = getAdmOrganigramaServicioRemote().listarTodosActivosPorInstitucion(institucion.getIdInstitucion());
            } else {
                //  System.out.println("onChangeInstitucion NADA");
                organigramas = new ArrayList<AdmOrganigrama>();
                institucionRequerida = true;
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

    public void confirmaEliminar(AdmPersonal personal) {
        try {
            if (getAdmPersonalServicioRemote().tieneCargasPendientes(personal.getIdPersonal()) > 0) {
                addWarningMessage("No puede eliminar la información de la persona con identificación "+personal.getIdentificacion()+", por que tiene cargas pendientes.");
                return;
            }
            personal.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            personal.setEstadoLogico(false);
            getAdmPersonalServicioRemote().editarPersonal(personal);
            addSuccessMessage("Registro Eliminado");
            listarPersonalesTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarPersonalesTodos() throws Exception {
        listarPersonals = getAdmPersonalServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmPersonal getObjAdmPersonalActual() {
        return objAdmPersonalActual;
    }

    public void setObjAdmPersonalActual(AdmPersonal objAdmPersonalActual) {
        this.objAdmPersonalActual = objAdmPersonalActual;
    }

    public List<AdmPersonal> getListarPersonals() {
        return listarPersonals;
    }

    public void setListarPersonals(List<AdmPersonal> listarPersonals) {
        this.listarPersonals = listarPersonals;
    }

    public List<AdmInstitucion> getInstituciones() {
        return instituciones;
    }

    public void setInstituciones(List<AdmInstitucion> instituciones) {
        this.instituciones = instituciones;
    }

    public List<AdmOrganigrama> getOrganigramas() {
        return organigramas;
    }

    public void setOrganigramas(List<AdmOrganigrama> organigramas) {
        this.organigramas = organigramas;
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

    public List<MetCatalogo> getCargos() {
        return cargos;
    }

    public void setCargos(List<MetCatalogo> cargos) {
        this.cargos = cargos;
    }

    public List<MetCatalogo> getNivelesInstruccion() {
        return nivelesInstruccion;
    }

    public void setNivelesInstruccion(List<MetCatalogo> nivelesInstruccion) {
        this.nivelesInstruccion = nivelesInstruccion;
    }

    public List<MetCatalogo> getTipoContratos() {
        return tipoContratos;
    }

    public void setTipoContratos(List<MetCatalogo> tipoContratos) {
        this.tipoContratos = tipoContratos;
    }

    public List<MetCatalogo> getNivelesSalario() {
        return nivelesSalario;
    }

    public void setNivelesSalario(List<MetCatalogo> nivelesSalario) {
        this.nivelesSalario = nivelesSalario;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public Map<Integer, String> getMapaCargos() {
        return mapaCargos;
    }

    public void setMapaCargos(Map<Integer, String> mapaCargos) {
        this.mapaCargos = mapaCargos;
    }

    public Map<Integer, String> getMapaContratos() {
        return mapaContratos;
    }

    public void setMapaContratos(Map<Integer, String> mapaContratos) {
        this.mapaContratos = mapaContratos;
    }

    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }

    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
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
