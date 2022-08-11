/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador que permite administrar la pagina gestionTiposCatalogos
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionTipoCatalogoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionTipoCatalogoControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private MetTipoCatalogo objTipoCatalogoActual;
    private List<MetTipoCatalogo> listarTipoCatalogoes;
    private boolean habilitaEdicion;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objTipoCatalogoActual = new MetTipoCatalogo();

            listarTipoCatalogoTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionTipoCatalogoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarTipoCatalogo() {
        try {
            if (habilitaEdicion == false) {
                if (!getMetTipoCatalogoServicioRemote().existeTipoCatalogoXAlias(objTipoCatalogoActual.getAlias())) {
                    objTipoCatalogoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                    objTipoCatalogoActual.setEstadoLogico(true);
                    getMetTipoCatalogoServicioRemote().crearTipoCatalogo(objTipoCatalogoActual);
                    addSuccessMessage("Registro Guardado");
                    listarTipoCatalogoTodos();
                    objTipoCatalogoActual = new MetTipoCatalogo();
                } else {
                    addErrorMessage("El Alias ya existe.");
                }
            } else {
                objTipoCatalogoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria( "PAG_MODIF"));
                getMetTipoCatalogoServicioRemote().editarTipoCatalogo(objTipoCatalogoActual);
                addSuccessMessage("Registro Actualizado");
                listarTipoCatalogoTodos();
                objTipoCatalogoActual = new MetTipoCatalogo();
                habilitaEdicion = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objTipoCatalogoActual = new MetTipoCatalogo();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarTipoCatalogoCampos(MetTipoCatalogo aplicacion) {
        try {
            objTipoCatalogoActual = new MetTipoCatalogo();
            objTipoCatalogoActual = aplicacion;
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(MetTipoCatalogo tipoCatalogo) {
        try {
            if (!getMetConceptoServicioRemote().existeTipoCatalogoenConcepto(tipoCatalogo)) {
                if (!getMetConceptoServicioRemote().existeTipoCatalogoenConcepto(tipoCatalogo)) {
                    tipoCatalogo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
                    tipoCatalogo.setEstadoLogico(false);
                    getMetTipoCatalogoServicioRemote().editarTipoCatalogo(tipoCatalogo);
                    addSuccessMessage("Registro Eliminado");
                    listarTipoCatalogoTodos();
                    objTipoCatalogoActual = new MetTipoCatalogo();
                    habilitaEdicion = false;
                }
                else{
                    addErrorMessage("El Tipo de Catálogo no puede ser eliminado porque se encuentra asignado a un Catálogo.");
                }
            } else {
                addErrorMessage("El Tipo de Catálogo no puede ser eliminado porque se encuentra asignado a un Concepto.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarTipoCatalogoTodos() throws Exception {
        listarTipoCatalogoes = getMetTipoCatalogoServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public MetTipoCatalogo getObjTipoCatalogoActual() {
        return objTipoCatalogoActual;
    }

    public void setObjTipoCatalogoActual(MetTipoCatalogo objTipoCatalogoActual) {
        this.objTipoCatalogoActual = objTipoCatalogoActual;
    }

    public List<MetTipoCatalogo> getListarTipoCatalogoes() {
        return listarTipoCatalogoes;
    }

    public void setListarTipoCatalogoes(List<MetTipoCatalogo> listarTipoCatalogoes) {
        this.listarTipoCatalogoes = listarTipoCatalogoes;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }
    //</editor-fold>
}
