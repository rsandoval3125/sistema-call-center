/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
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
 * Controlador que permite administrar la pagina gestionCatalogos
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionCatalogoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionCatalogoControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private MetCatalogo objCatalogoActual;
    private List<MetCatalogo> listarCatalogoes;
    private List<MetTipoCatalogo> tiposCatalgos;
    private List<MetCatalogo> catalogosPadre;
    private boolean habilitaEdicion;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objCatalogoActual = new MetCatalogo();
            //--Invocación a EJB remoto

            //--Fin envocación a EJB remoto
            listarCatalogoTodos();

            tiposCatalgos = getMetTipoCatalogoServicioRemote().listarTodo();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionCatalogoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarCatalogo() {
        try {
            if (habilitaEdicion == false) {
                if (!getMetCatalogoServicioRemote().existeCatalogoXAlias(objCatalogoActual.getAlias())) {
                    objCatalogoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                    objCatalogoActual.setEstadoLogico(true);
                    getMetCatalogoServicioRemote().crearCatalogo(objCatalogoActual);
                    addSuccessMessage("Registro Guardado");
                    listarCatalogoTodos();
                    refrescaNuevo();
                } else {
                    addErrorMessage("El Alias ya existe.");
                }
            } else {
                objCatalogoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getMetCatalogoServicioRemote().editarCatalogo(objCatalogoActual);
                addSuccessMessage("Registro Actualizado");
                listarCatalogoTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objCatalogoActual = new MetCatalogo();
            catalogosPadre = getMetCatalogoServicioRemote().listarTodosActivos();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarCatalogoCampos(MetCatalogo catalogo) {
        try {
            objCatalogoActual = new MetCatalogo();
            objCatalogoActual = catalogo;
            catalogosPadre = getMetCatalogoServicioRemote().listarTodosActivos(objCatalogoActual.getIdCatalogo());
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(MetCatalogo catalogo) {
        try {
            catalogo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            catalogo.setEstadoLogico(false);
            getMetCatalogoServicioRemote().editarCatalogo(catalogo);
            addSuccessMessage("Registro Eliminado");
            listarCatalogoTodos();
            objCatalogoActual = new MetCatalogo();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarCatalogoTodos() throws Exception {
        listarCatalogoes = getMetCatalogoServicioRemote().listarTodosActivos();
        catalogosPadre = listarCatalogoes;
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public MetCatalogo getObjCatalogoActual() {
        return objCatalogoActual;
    }

    public void setObjCatalogoActual(MetCatalogo objCatalogoActual) {
        this.objCatalogoActual = objCatalogoActual;
    }

    public List<MetCatalogo> getListarCatalogoes() {
        return listarCatalogoes;
    }

    public void setListarCatalogoes(List<MetCatalogo> listarCatalogoes) {
        this.listarCatalogoes = listarCatalogoes;
    }

    public List<MetTipoCatalogo> getTiposCatalgos() {
        return tiposCatalgos;
    }

    public void setTiposCatalgos(List<MetTipoCatalogo> tiposCatalgos) {
        this.tiposCatalgos = tiposCatalgos;
    }

    public List<MetCatalogo> getCatalogosPadre() {
        return catalogosPadre;
    }

    public void setCatalogosPadre(List<MetCatalogo> catalogosPadre) {
        this.catalogosPadre = catalogosPadre;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }
    //</editor-fold>
}
