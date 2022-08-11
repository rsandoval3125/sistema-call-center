package ec.gob.inec.seguridad.jsf.controlador.modulos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

/**
 * Controlador que permite administrar la información de páginas.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionPaginaControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionPaginaControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private SegPagina objPaginaActual;
    private List<SegPagina> listarPaginas;
    private boolean habilitaEdicion;
    private List<SegAplicacion> aplicaciones;
    private List<MetCatalogo> estadoPaginas;
    private List<SegPagina> listarPaginasPadre;
    private List<MetCatalogo> tiposPaginas;
    private Map<String, Boolean> permisosBoton;
    private List<RepoReporte> lstVerReportesTodos;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objPaginaActual = new SegPagina();
            objPaginaActual.setCodApl(new SegAplicacion());
            permisosBoton = vistaControlador.getPermisos();
            //--Invocación a EJB remoto 

            //--Fin envocación a EJB remoto
            aplicaciones = getSegAplicacionServicioRemote().listarTodosActivos();
            estadoPaginas = getMetCatalogoServicioRemote().listarCatalogoXAlias("EST_PAG");
            tiposPaginas = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIPO_PAGINA");
            lstVerReportesTodos = getRepoReporteServicioRemote().listarTodosActivos();

            listarPaginaTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionPaginaControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarPagina() {
        try {

            if (habilitaEdicion == false) {
                objPaginaActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objPaginaActual.setEstadoLogico(true);
                getSegPaginaServicioRemote().crearPagina(objPaginaActual);
                addSuccessMessage("Registro Guardado");
                listarPaginaTodos();
                objPaginaActual = new SegPagina();
            } else {
                objPaginaActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getSegPaginaServicioRemote().editarPagina(objPaginaActual);
                addSuccessMessage("Registro Actualizado");
                listarPaginaTodos();
                objPaginaActual = new SegPagina();
                habilitaEdicion = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            System.out.println("refrescar nuevo.");
            objPaginaActual = new SegPagina();
            listarPaginasPadre = getSegPaginaServicioRemote().listarTodosActivos();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarPaginaCampos(SegPagina pagina) {
        try {
            objPaginaActual = new SegPagina();
            objPaginaActual = pagina;
            habilitaEdicion = true;
            listaPaginasPadres();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void listaPaginasPadres() {
        try {
            listarPaginasPadre = getSegPaginaServicioRemote().listarPaginasActivasPorAplicacion(objPaginaActual.getCodApl().getIdApl());//.listarTodosActivosSinUno(objPaginaActual.getIdPag());
            listarPaginasPadre.removeIf(p -> p.getIdPag().equals(objPaginaActual.getIdPag()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(SegPagina pagina) {
        try {
            pagina.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            pagina.setEstadoLogico(false);
            getSegPaginaServicioRemote().editarPagina(pagina);
            addSuccessMessage("Registro Eliminado");
            listarPaginaTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarPaginaTodos() throws Exception {
        listarPaginas = getSegPaginaServicioRemote().listarTodosActivos();
        listarPaginasPadre = getSegPaginaServicioRemote().listarTodosActivos();
    }
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">

    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public SegPagina getObjPaginaActual() {
        return objPaginaActual;
    }

    public void setObjPaginaActual(SegPagina objPaginaActual) {
        this.objPaginaActual = objPaginaActual;
    }

    public List<SegPagina> getListarPaginas() {
        return listarPaginas;
    }

    public void setListarPaginas(List<SegPagina> listarPaginaes) {
        this.listarPaginas = listarPaginaes;
    }

    public List<SegAplicacion> getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(List<SegAplicacion> aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public List<MetCatalogo> getEstadoPaginas() {
        return estadoPaginas;
    }

    public void setEstadoPaginas(List<MetCatalogo> estadoPaginas) {
        this.estadoPaginas = estadoPaginas;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    /*public UsuarioControlador getUsuarioControlador() {
    return usuarioControlador;
    }
    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
    this.usuarioControlador = usuarioControlador;
    }*/
    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public List<SegPagina> getListarPaginasPadre() {
        return listarPaginasPadre;
    }

    public void setListarPaginasPadre(List<SegPagina> listarPaginasPadre) {
        this.listarPaginasPadre = listarPaginasPadre;
    }

    public List<MetCatalogo> getTiposPaginas() {
        return tiposPaginas;
    }

    public void setTiposPaginas(List<MetCatalogo> tiposPaginas) {
        this.tiposPaginas = tiposPaginas;
    }

    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }

    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
    }

    public List<RepoReporte> getLstVerReportesTodos() {
        return lstVerReportesTodos;
    }

    public void setLstVerReportesTodos(List<RepoReporte> lstVerReportesTodos) {
        this.lstVerReportesTodos = lstVerReportesTodos;
    }
    //</editor-fold>
}
