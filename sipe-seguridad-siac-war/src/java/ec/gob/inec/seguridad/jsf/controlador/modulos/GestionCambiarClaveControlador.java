/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.ejb.utils.ReflexionEntidad;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador que permite cambiar la clave de un usuario.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionCambiarClaveControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionCambiarClaveControlador.class.getName());
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private SegUsuario objUsuarioActual = new SegUsuario();
    private String clave;
    private String confirmacionClave;
    private boolean ok;
    private AdmColumna columnaAEcnriptar;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            ok = false;
            objUsuarioActual = usuarioControlador.getUsuarioActual();
            columnaAEcnriptar = getAdmColumnaServicioRemote().consultarPorTablaYColumna("seguridad.seg_usuario", "clave");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionCambiarClaveControlador() {
    }
    //</editor-fold> 
    //<editor-fold desc="Metodos" defaultstate="collapsed">

    public void guardarUsuario() {
        try {
            String claveEncriptada = "";
            if (columnaAEcnriptar != null && columnaAEcnriptar.getEncr()) {//Encripta
                String parametroAES = getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
                claveEncriptada = ReflexionEntidad.encripta(columnaAEcnriptar.getCodTipoEncr().getNombre(), clave, parametroAES);
            } else {
                claveEncriptada = clave;
            }
            objUsuarioActual.setClave(claveEncriptada);
            objUsuarioActual.setFechahoraEdicion(new Date());
            objUsuarioActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
            getSegUsuarioServicioRemote().editarUsuario(objUsuarioActual);
            addSuccessMessage("Registro Actualizado");
            objUsuarioActual = new SegUsuario();
            clave = "";
            confirmacionClave = "";
            ok = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public SegUsuario getObjUsuarioActual() {
        return objUsuarioActual;
    }

    public void setObjUsuarioActual(SegUsuario objUsuarioActual) {
        this.objUsuarioActual = objUsuarioActual;
    }

    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public String getConfirmacionClave() {
        return confirmacionClave;
    }

    public void setConfirmacionClave(String confirmacionClave) {
        this.confirmacionClave = confirmacionClave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    //</editor-fold>       
}
