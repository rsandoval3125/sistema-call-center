/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.base;

import ec.gob.inec.geografia.ejb.entidades.GeoCobertura;
import ec.gob.inec.geografia.ejb.entidades.GeoDpa;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegPermiso;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
//import org.primefaces.context.RequestContext;
//import org.primefaces.json.JSONArray;
//import org.primefaces.json.JSONObject;

/**
 *
 * @author lponce
 */
@ManagedBean()
@ViewScoped
public class VistaControlador {

    private static final Logger LOGGER = Logger.getLogger(VistaControlador.class.getName());

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    @ManagedProperty("#{baseControlador}")
    private BaseControlador baseControlador;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;

    private SegPagina paginaVista;
    private String auxiliar;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public VistaControlador() {
    }

    @PostConstruct
    public void init() {
        System.out.println("Inicio init Vista Controlador");
        auxiliar = "ayudaSIPE";
        try {
            Map<String, Object> campos = new LinkedHashMap<String, Object>();
            if (usuarioControlador.getPaginaActual() == null) {
                campos.put("idPag", baseControlador.getDatosGenerales().getIdPagina());
            } else {
                campos.put("idPag", usuarioControlador.getPaginaActual().getIdPag());
            }
            paginaVista = baseControlador.getSegPaginaServicioRemote().buscarPorCampos(campos, "").get(0);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        System.out.println("Fin init Vista Controlador");
    }
//</editor-fold> 
    //<editor-fold desc="get and set" defaultstate="collapsed">

    public BaseControlador getBaseControlador() {
        return baseControlador;
    }

    public void setBaseControlador(BaseControlador baseControlador) {
        this.baseControlador = baseControlador;
    }

    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public String getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(String auxiliar) {
        this.auxiliar = auxiliar;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed"> 
    public String getCodAuditoria(String alias) {
        int codigoPagina = paginaVista.getIdPag();
        int codigoEvento = baseControlador.getCacheTimer().getMetCatalogoList().stream().filter(x -> (x.getAlias() != null && x.getAlias().equals(alias))).collect(Collectors.toList()).get(0).getIdCatalogo();
        return usuarioControlador.getCodAuditoria(codigoPagina, codigoEvento);
    }

    /**
     * Consulta los permisos que tiene una página.
     * {@code vistaControlador.getPermisos()}
     *
     * @return Objeto Map con estos datos {PAG_MODIF=true, PAG_ELIM=true,
     * PAG_VER=true, PAG_INSRT=true}
     */
    public Map<String, Boolean> getPermisos() {
        Map<String, Boolean> mapPermisos = new HashMap<>();
        try {
             SegUsuario segUsuario = usuarioControlador.getUsuarioActual();
             if (segUsuario==null) {
                LOGGER.log(Level.INFO, "Usuario no autenticado.");
                return null;
            }
          /*  if (!estaAutenticado(true)) {
                return null;
            }*/
            int codigoPagina = paginaVista.getIdPag();
           
            List<SegPermiso> tmpPermisos = baseControlador.getSegPermisoServicioRemote().listarPermisosPorUsuarioYPagina(segUsuario.getNombre(), codigoPagina);
            tmpPermisos.forEach((sp) -> {
                mapPermisos.put(sp.getCodAccion().getAlias(), true);
            });
            System.out.println("permisos " + mapPermisos);
        } catch (Exception ex) {
            Logger.getLogger(VistaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapPermisos;
    }

  /*
    private boolean estaAutenticado(boolean valida) {
        SegUsuario segUsuario = usuarioControlador.getUsuarioActual();
        if (segUsuario == null) {
            LOGGER.log(Level.INFO, "Usuario no autenticado.");
            if (valida) {
                LOGGER.log(Level.INFO, "Cerrando la sesion y redireccionando a login.");
                usuarioControlador.cerrarSesion();
                return false;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }*/
    //</editor-fold>
}
