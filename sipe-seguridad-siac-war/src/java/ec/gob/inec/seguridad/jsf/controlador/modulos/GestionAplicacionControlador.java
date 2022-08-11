package ec.gob.inec.seguridad.jsf.controlador.modulos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import org.apache.commons.lang.SerializationUtils;

/**
 * Controlador que permite administrar información de las aplicaciones.
 *
 * @author dvaldas
 */
@ManagedBean
@ViewScoped
public class GestionAplicacionControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionAplicacionControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    //private UsuarioControlador usuarioControlador;
    private VistaControlador vistaControlador;
    private SegAplicacion objAplActual;
    private List<SegAplicacion> listarAplicaciones;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private List<MetCatalogo> tipoAplicaciones;
    private List<MetCatalogo> estadoAplicaciones;
    private Map<String, Boolean> permisosBoton;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objAplActual = new SegAplicacion();
            fechaReg = new Date();
            //--Invocación a EJB remoto 

            //--Fin envocación a EJB remoto
            permisosBoton = vistaControlador.getPermisos();

            tipoAplicaciones = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIP_APL");
            estadoAplicaciones = getMetCatalogoServicioRemote().listarCatalogoXAlias("EST_APL");

            listarAplTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionAplicacionControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarAplicacion() {
        try {

            if (habilitaEdicion == false) {
                objAplActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objAplActual.setFechaReg(fechaReg);
                objAplActual.setEstadoLogico(true);
                getSegAplicacionServicioRemote().crearAplicacion(objAplActual);
                addSuccessMessage("Registro Guardado");
                listarAplTodos();
                objAplActual = new SegAplicacion();
            } else {
                objAplActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getSegAplicacionServicioRemote().editarAplicacion(objAplActual);
                addSuccessMessage("Registro Actualizado");
                listarAplTodos();
                objAplActual = new SegAplicacion();
                habilitaEdicion = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objAplActual = new SegAplicacion();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarAplicacionCampos(SegAplicacion aplicacion) {
        try {
            objAplActual = new SegAplicacion();
            objAplActual = (SegAplicacion) SerializationUtils.clone(aplicacion);
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void confirmaEliminar(SegAplicacion aplicacion) {
        try {
            aplicacion.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            aplicacion.setEstadoLogico(false);
            getSegAplicacionServicioRemote().editarAplicacion(aplicacion);
            addSuccessMessage("Registro Eliminado");
            listarAplTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarAplTodos() throws Exception {
        listarAplicaciones = getSegAplicacionServicioRemote().listarTodosActivos();
    }
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">

    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public SegAplicacion getObjAplActual() {
        return objAplActual;
    }

    public void setObjAplActual(SegAplicacion objAplActual) {
        this.objAplActual = objAplActual;
    }

    public List<SegAplicacion> getListarAplicaciones() {
        return listarAplicaciones;
    }

    public void setListarAplicaciones(List<SegAplicacion> listarAplicaciones) {
        this.listarAplicaciones = listarAplicaciones;
    }

    public List<MetCatalogo> getTipoAplicaciones() {
        return tipoAplicaciones;
    }

    public void setTipoAplicaciones(List<MetCatalogo> tipoAplicaciones) {
        this.tipoAplicaciones = tipoAplicaciones;
    }

    public List<MetCatalogo> getEstadoAplicaciones() {
        return estadoAplicaciones;
    }

    public void setEstadoAplicaciones(List<MetCatalogo> estadoAplicaciones) {
        this.estadoAplicaciones = estadoAplicaciones;
    }

    /*public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }*/
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
