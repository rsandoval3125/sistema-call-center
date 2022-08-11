/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetConcepto;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Date;

/**
 * Controlador que permite administrar la pagina gestionConceptos
 *
 * @author mandrade
 */
@ManagedBean
@ViewScoped
public class GestionConceptoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionConceptoControlador.class.getName());
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    private MetConcepto objConceptoActual;
    private List<MetConcepto> listarConceptos;
    private List<MetTipoCatalogo> tiposCatalogos;
    private List<MetCatalogo> tiposConcepto;
    private List<MetCatalogo> tiposDato;
    private List<MetCatalogo> tiposInvestigacion;
    private List<MetCatalogo> tiposClasificacion;
    private List<MetCatalogo> tiposSeguridad;
    private List<AdmInstitucion> tiposInstitucion;
    private boolean habilitaEdicion;
     @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objConceptoActual = new MetConcepto();
            //--Invocación a EJB remoto

            //--Fin invocación a EJB remoto
            listarConceptosTodos();
            listarCombos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionConceptoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarConcepto() {
        try {
            if (habilitaEdicion == false) {
                if (!getMetConceptoServicioRemote().existeConceptoXAlias(objConceptoActual.getIdentificador())) {
                    objConceptoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                    objConceptoActual.setEstadoLogico(true);
                    objConceptoActual.setFechaCreacion(new Date());
                    getMetConceptoServicioRemote().crearConcepto(objConceptoActual);
                    addSuccessMessage("Registro Guardado");
                    listarConceptosTodos();
                    refrescaNuevo();
                }
                else{
                    addErrorMessage("El Alias ya existe.");
                }
            } else {
                objConceptoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                objConceptoActual.setFechaEdicion(new Date());
                getMetConceptoServicioRemote().editarConcepto(objConceptoActual);
                addSuccessMessage("Registro Actualizado");
                listarConceptosTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objConceptoActual = new MetConcepto();
            listarCombos();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarConceptosCampos(MetConcepto concepto) {
        try {
            objConceptoActual = new MetConcepto();
            objConceptoActual = concepto;
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(MetConcepto concepto) {
        try {
            concepto.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            concepto.setEstadoLogico(false);
            objConceptoActual.setFechaEdicion(new Date());
            getMetConceptoServicioRemote().editarConcepto(concepto);
            addSuccessMessage("Registro Eliminado");
            listarConceptosTodos();
            objConceptoActual = new MetConcepto();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarConceptosTodos() throws Exception {
        listarConceptos = getMetConceptoServicioRemote().listarTodosActivos();
    }
    
    public void listarCombos() throws Exception {
        tiposCatalogos = getMetTipoCatalogoServicioRemote().listarTodosActivos();
        tiposConcepto= getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIP_CONCEPTO");
        tiposDato= getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIP_DATO");
        tiposInvestigacion= getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIP_INVESTIGACION");
        tiposClasificacion= getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIP_CLASIFICACION");
        tiposSeguridad= getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIP_SEGURIDAD");
        tiposInstitucion=getAdmInstitucionServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public MetConcepto getObjConceptoActual() {
        return objConceptoActual;
    }

    public void setObjConceptoActual(MetConcepto objConceptoActual) {
        this.objConceptoActual = objConceptoActual;
    }

    public List<MetConcepto> getListarConceptos() {
        return listarConceptos;
    }

    public void setListarCatalogoes(List<MetCatalogo> listarCatalogoes) {
        this.listarConceptos = listarConceptos;
    }

    public List<MetTipoCatalogo> getTiposCatalogos() {
        return tiposCatalogos;
    }

    public void setTiposCatalogos(List<MetTipoCatalogo> tiposCatalogos) {
        this.tiposCatalogos = tiposCatalogos;
    }

    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }
    
    public List<MetCatalogo> getTiposConcepto() {
        return tiposConcepto;
    }

    public void setTiposConcepto(List<MetCatalogo> tiposConcepto) {
        this.tiposConcepto = tiposConcepto;
    }
    
    public List<MetCatalogo> getTiposDato() {
        return tiposDato;
    }

    public void setTiposDato(List<MetCatalogo> tiposDato) {
        this.tiposDato = tiposDato;
    }
    
    public List<MetCatalogo> getTiposInvestigacion() {
        return tiposInvestigacion;
    }

    public void setTiposInvestigacion(List<MetCatalogo> tiposInvestigacion) {
        this.tiposInvestigacion = tiposInvestigacion;
    }
    
    public List<MetCatalogo> getTiposClasificacion() {
        return tiposClasificacion;
    }

    public void setTiposClasificacion(List<MetCatalogo> tiposClasificacion) {
        this.tiposClasificacion = tiposClasificacion;
    }

    public List<MetCatalogo> getTiposSeguridad() {
        return tiposSeguridad;
    }

    public void setTiposSeguridad(List<MetCatalogo> tiposSeguridad) {
        this.tiposSeguridad = tiposSeguridad;
    }

    public List<AdmInstitucion> getTiposInstitucion() {
        return tiposInstitucion;
    }

    public void setTiposInstitucion(List<AdmInstitucion> tiposInstitucion) {
        this.tiposInstitucion = tiposInstitucion;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }
    
}
