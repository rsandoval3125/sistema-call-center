/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.proceso.ejb.entidades.ProFase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import org.apache.commons.lang.SerializationUtils;

/**
 * Controlador que permite administrar informacion de periodos.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionPeriodoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionPeriodoControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private List<MetCatalogo> tiposPeriodos;
    private AdmPeriodo objAdmPeriodoActual;
    private List<AdmPeriodo> listarPeriodos;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private Map<String, Boolean> permisosBoton;
    // vic 01-07-2020 cambio
    private List<MetOperativo> lstOperativo;
    private List<ProFase> lstFase;
    private MetOperativo metOperativo;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objAdmPeriodoActual = new AdmPeriodo();
            metOperativo = new MetOperativo();
            fechaReg = new Date();
            permisosBoton = vistaControlador.getPermisos();
            listarPeriodosTodos();

            tiposPeriodos = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIP_PER");
            // vic 01-07-2020 cambio
           lstOperativo = getMetOperativoServicioRemote().listarTodosOperativo();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionPeriodoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarPeriodo() {
        try {
            if (objAdmPeriodoActual.getFechaInicio() != null && objAdmPeriodoActual.getFechaFin() != null) {
                if (objAdmPeriodoActual.getFechaInicio().after(objAdmPeriodoActual.getFechaFin())) {
                    addWarningMessage("Fecha inicio no puede ser mayor a Fecha fin");
                    return;
                }
            }
            if (habilitaEdicion == false) {
                objAdmPeriodoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objAdmPeriodoActual.setFechaRegistro(fechaReg);
                objAdmPeriodoActual.setEstadoLogico(true);
                getAdmPeriodoServicioRemote().crearPeriodo(objAdmPeriodoActual);
                addSuccessMessage("Registro Guardado");
                listarPeriodosTodos();
                objAdmPeriodoActual = new AdmPeriodo();
            } else {
                objAdmPeriodoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getAdmPeriodoServicioRemote().editarPeriodo(objAdmPeriodoActual);
                addSuccessMessage("Registro Actualizado");
                listarPeriodosTodos();
                objAdmPeriodoActual = new AdmPeriodo();
                habilitaEdicion = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objAdmPeriodoActual = new AdmPeriodo();
            metOperativo = new MetOperativo();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarPeriodoCampos(AdmPeriodo periodo) {
        try {
            objAdmPeriodoActual = new AdmPeriodo();
            objAdmPeriodoActual = (AdmPeriodo) SerializationUtils.clone(periodo);
            // vic 01-07-2020 cambio
            if (objAdmPeriodoActual.getCodFase() != null) {
                metOperativo = objAdmPeriodoActual.getCodFase().getCodOperativo();
                listarFases();
            }else{
                metOperativo = new MetOperativo();
            }
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(AdmPeriodo periodo) {
        try {
            periodo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            periodo.setEstadoLogico(false);
            getAdmPeriodoServicioRemote().editarPeriodo(periodo);
            addSuccessMessage("Registro Eliminado");
            listarPeriodosTodos();
            objAdmPeriodoActual = new AdmPeriodo();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }
    
    // vic 01-07-2020 cambio
    public void listarFases() {
        try {
             lstFase = new ArrayList<ProFase>();
             lstFase = getProFaseServicioRemote().listarEjecutarConsulta("listarMpeFasePorOperativo", Arrays.asList(metOperativo));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("Error en la fase");
        }
    }

    public void listarPeriodosTodos() throws Exception {
        listarPeriodos = getAdmPeriodoServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmPeriodo getObjAdmPeriodoActual() {
        return objAdmPeriodoActual;
    }

    public void setObjAdmPeriodoActual(AdmPeriodo objAdmPeriodoActual) {
        this.objAdmPeriodoActual = objAdmPeriodoActual;
    }

    public List<AdmPeriodo> getListarPeriodos() {
        return listarPeriodos;
    }

    public void setListarPeriodos(List<AdmPeriodo> listarPeriodos) {
        this.listarPeriodos = listarPeriodos;
    }

    public List<MetCatalogo> getTiposPeriodos() {
        return tiposPeriodos;
    }

    public void setTiposPeriodos(List<MetCatalogo> tiposPeriodos) {
        this.tiposPeriodos = tiposPeriodos;
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
    
    public List<MetOperativo> getLstOperativo() {
        return lstOperativo;
    }

    public void setLstOperativo(List<MetOperativo> lstOperativo) {
        this.lstOperativo = lstOperativo;
    }

    public List<ProFase> getLstFase() {
        return lstFase;
    }

    public void setLstFase(List<ProFase> lstFase) {
        this.lstFase = lstFase;
    }
    
    public MetOperativo getMetOperativo() {
        return metOperativo;
    }

    public void setMetOperativo(MetOperativo metOperativo) {
        this.metOperativo = metOperativo;
    }
    
     //</editor-fold>
}
