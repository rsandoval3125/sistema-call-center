/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmDispositivo;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.util.ArrayList;
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
 * Controlador que permite administrar información de dispositivos.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionDispositivoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionDispositivoControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private AdmDispositivo objDispositivoActual;
    private List<AdmDispositivo> listarDispositivoes;
    private boolean habilitaEdicion;
    private Map<String, Boolean> permisosBoton;
    private AdmDispositivo objDispositivoAnterior;
    private List<MetCatalogo> lstZonales;
    private MetCatalogo zonalActual;
    private SegUsuario segUsuarioActual;
    private boolean habilitaZonal;
    
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            usuarioRol();
            habilitaEdicion = false;
            habilitaZonal = false;
            objDispositivoActual = new AdmDispositivo();
            objDispositivoAnterior = new AdmDispositivo();
            permisosBoton = vistaControlador.getPermisos();
            listarDispositivosTodos();
            listarZonales();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionDispositivoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarDispositivo() {
        try {
            if (!objDispositivoActual.getSerie().equals(objDispositivoAnterior.getSerie())) {
                System.out.println(objDispositivoActual.getSerie());
                System.out.println(listarDispositivoes);
                if (listarDispositivoes.stream().filter(p -> Objects.nonNull(p.getSerie())).filter(p -> p.getSerie().equals(objDispositivoActual.getSerie())).count() > 0) {
                    addWarningMessage("Ya existe una serie con el mismo número");
                    return;
                }
            }
            if (habilitaEdicion == false) {
                objDispositivoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objDispositivoActual.setEstadoLogico(true);
                objDispositivoActual.setCodZonal(zonalActual); 
                getAdmDispositivoServicioRemote().crearDispositivo(objDispositivoActual);
                addSuccessMessage("Registro Guardado");
                listarDispositivosTodos();
                refrescaNuevo();
            } else {
                objDispositivoActual.setCodZonal(zonalActual);
                objDispositivoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getAdmDispositivoServicioRemote().editarDispositivo(objDispositivoActual);
                addSuccessMessage("Registro Actualizado");
                listarDispositivosTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objDispositivoActual = new AdmDispositivo();
            objDispositivoAnterior = new AdmDispositivo();
            habilitaEdicion = false;
             listarZonales();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarDispositivoCampos(AdmDispositivo dispositivo) {
        try {
            objDispositivoActual = new AdmDispositivo();
            objDispositivoActual = (AdmDispositivo) SerializationUtils.clone(dispositivo);
            zonalActual = objDispositivoActual.getCodZonal();
            objDispositivoAnterior = (AdmDispositivo) SerializationUtils.clone(dispositivo);
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void confirmaEliminar(AdmDispositivo dispositivo) {
        try {
            if (getAdmDispositivoServicioRemote().tieneCargasPendientes(dispositivo.getSerie()) > 0) {
                addWarningMessage("No puede eliminar el dispositivo con serie " + dispositivo.getSerie() + ", por que tiene cargas pendientes.");
                return;
            }
            if (getAdmDispositivoServicioRemote().tieneMiembroDeEquipo(dispositivo.getSerie()) > 0) {
                addWarningMessage("No puede eliminar el dispositivo con serie " + dispositivo.getSerie() + ", por que tiene miembros de equipos asignados.");
                return;
            }
            dispositivo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            dispositivo.setEstadoLogico(false);
            getAdmDispositivoServicioRemote().editarDispositivo(dispositivo);
            addSuccessMessage("Registro Eliminado");
            listarDispositivosTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }
    
    public void usuarioRol() {
        try {
            segUsuarioActual = usuarioControlador.getUsuarioActual();           
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que lista las zonales
     */
    public void listarZonales() {
        try {
            lstZonales = new ArrayList<MetCatalogo>();
            // PAG_LST_TODO            
            if (usuarioTienePermiso("PAG_LST_TODO")) {
                lstZonales = getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_COOR_ZONAL");
                habilitaZonal = true;
            } else {
                // lista la zonal del usuario logueado
                zonalActual = new MetCatalogo();
                zonalActual = segUsuarioActual.getCodPersonal().getCodDpa().getCodPadre2();
                habilitaZonal = false;
            } 
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean usuarioTienePermiso(String aliasPermiso) {
        boolean b = false;
        for (String aliasPer : permisosBoton.keySet()) {
            if (aliasPer.equals(aliasPermiso)) {
                b = true;
                break;
            }
        }
        return b;
    }

    public void listarDispositivosTodos() throws Exception {
        listarDispositivoes = getAdmDispositivoServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmDispositivo getObjDispositivoActual() {
        return objDispositivoActual;
    }

    public void setObjDispositivoActual(AdmDispositivo objDispositivoActual) {
        this.objDispositivoActual = objDispositivoActual;
    }

    public List<AdmDispositivo> getListarDispositivoes() {
        return listarDispositivoes;
    }

    public void setListarDispositivoes(List<AdmDispositivo> listarDispositivoes) {
        this.listarDispositivoes = listarDispositivoes;
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

    public List<MetCatalogo> getLstZonales() {
        return lstZonales;
    }

    public void setLstZonales(List<MetCatalogo> lstZonales) {
        this.lstZonales = lstZonales;
    }

    public MetCatalogo getZonalActual() {
        return zonalActual;
    }

    public void setZonalActual(MetCatalogo zonalActual) {
        this.zonalActual = zonalActual;
    }

    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public boolean isHabilitaZonal() {
        return habilitaZonal;
    }

    public void setHabilitaZonal(boolean habilitaZonal) {
        this.habilitaZonal = habilitaZonal;
    }
    
    
}
