package ec.gob.inec.seguridad.jsf.controlador.modulos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.administracion.ejb.entidades.AdmTabla;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

/**
 * Controlador que permite administrar las columnas a cifrar de una tabla.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionEncriptarControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionEncriptarControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    private AdmColumna objColumnaActual;
    private List<AdmColumna> listarColumnaes;
    private boolean habilitaEdicion;
    private List<MetCatalogo> tiposEncriptacion;
    private List<AdmTabla> tablas;
    private boolean encr;
    private int tipoEncr;
    private Map<Integer, String> mapaTiposEncriptacion;
    private Map<String, Boolean> permisosBoton;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objColumnaActual = new AdmColumna();
            //--Invocación a EJB remoto 

            //--Fin envocación a EJB remoto
            permisosBoton = vistaControlador.getPermisos();
            tiposEncriptacion = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIPO_ENCRIPTACION");
            mapaTiposEncriptacion = new HashMap<>();
            for (MetCatalogo catTipoEnciptacion : tiposEncriptacion) {
                mapaTiposEncriptacion.put(catTipoEnciptacion.getIdCatalogo(), catTipoEnciptacion.getNombre());
            }
            tablas = getAdmTablaServicioRemote().listarTodosActivos();
            listarColumnaTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionEncriptarControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarColumna() {
        try {
            if (objColumnaActual.getEncr()) {//Tiene que encriptar la columna
                getAdmColumnaServicioRemote().encriptarCampoPorTabla(objColumnaActual.getCodTabla().getNombre(), objColumnaActual.getNombre(), mapaTiposEncriptacion.get(objColumnaActual.getCodTipoEncr().getIdCatalogo()));
            }
            getAdmColumnaServicioRemote().editarColumna(objColumnaActual);
            addSuccessMessage("Registro Actualizado");
            listarColumnaTodos();
            objColumnaActual = new AdmColumna();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            objColumnaActual.setEncr(encr);
            objColumnaActual.setCodTipoEncr(new MetCatalogo(tipoEncr));
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            System.out.println("refrescar nuevo.");
            objColumnaActual = new AdmColumna();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarColumnaCampos(AdmColumna columna) {
        try {
            objColumnaActual = new AdmColumna();
            objColumnaActual = columna;
            encr = (columna.getEncr() == null) ? false : columna.getEncr();
            tipoEncr = (columna.getCodTipoEncr() == null) ? -1 : columna.getCodTipoEncr().getIdCatalogo();
            habilitaEdicion = !encr;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void confirmaEliminar(AdmColumna columna) {
        try {
            getAdmColumnaServicioRemote().editarColumna(columna);
            addSuccessMessage("Registro Eliminado");
            listarColumnaTodos();
            objColumnaActual = new AdmColumna();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarColumnaTodos() throws Exception {
        listarColumnaes = getAdmColumnaServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmColumna getObjColumnaActual() {
        return objColumnaActual;
    }

    public void setObjColumnaActual(AdmColumna objColumnaActual) {
        this.objColumnaActual = objColumnaActual;
    }

    public List<AdmColumna> getListarColumnaes() {
        return listarColumnaes;
    }

    public void setListarColumnaes(List<AdmColumna> listarColumnaes) {
        this.listarColumnaes = listarColumnaes;
    }

    public List<MetCatalogo> getTiposEncriptacion() {
        return tiposEncriptacion;
    }

    public void setTiposEncriptacion(List<MetCatalogo> tiposEncriptacion) {
        this.tiposEncriptacion = tiposEncriptacion;
    }

    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public List<AdmTabla> getTablas() {
        return tablas;
    }

    public void setTablas(List<AdmTabla> tablas) {
        this.tablas = tablas;
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
}
