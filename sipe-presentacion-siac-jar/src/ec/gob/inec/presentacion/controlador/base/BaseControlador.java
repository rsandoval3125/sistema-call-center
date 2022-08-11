/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.base;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.servicios.AdmBaseDatosServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmColumnaServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmCorreoCabServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmCorreoDestinatarioServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmCorreoDetServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmDispositivoServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmInstitucionServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmOperacionEstadisticaServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmOrganigramaServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmParametroGlobalServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmPeriodoServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmPersonalServicioRemote;
import ec.gob.inec.administracion.ejb.servicios.AdmTablaServicioRemote;
import ec.gob.inec.cache.clases.CacheTimer;
import ec.gob.inec.captura.ejb.servicios.CaptCabeceraServicioRemote;
import ec.gob.inec.captura.ejb.servicios.CaptCargaControlEquipoServicioRemote;
import ec.gob.inec.captura.ejb.servicios.CaptCargaControlServicioRemote;
import ec.gob.inec.captura.ejb.servicios.CaptDetalleHServicioRemote;
import ec.gob.inec.captura.ejb.servicios.CaptDetalleVServicioRemote;
import ec.gob.inec.captura.ejb.servicios.CaptEstadoServicioRemote;
import ec.gob.inec.captura.ejb.servicios.CaptGeoreferenciaServicioRemote;
import ec.gob.inec.captura.ejb.servicios.CaptObservacionServicioRemote;
import ec.gob.inec.distribuciontrabajo.ejb.servicios.CargaTrabajoDetalleServicioRemote;
import ec.gob.inec.distribuciontrabajo.ejb.servicios.CargaTrabajoServicioRemote;
import ec.gob.inec.distribuciontrabajo.ejb.servicios.EquipoTrabajoDetalleServicioRemote;
import ec.gob.inec.distribuciontrabajo.ejb.servicios.EquipoTrabajoServicioRemote;
import ec.gob.inec.distribuciontrabajo.ejb.servicios.FaseOperativoServicioRemote;
import ec.gob.inec.geografia.ejb.servicios.GeoCoberturaServicioRemote;
import ec.gob.inec.geografia.ejb.servicios.GeoDpaServicioRemote;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import ec.gob.inec.metadato.ejb.servicios.MetCatalogoCiiuServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetCatalogoCiuoServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetCatalogoServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetConceptoServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetFormularioSeccionServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetFormularioServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetOperativoServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetSaltoServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetSeccionServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetTipoCatalogoServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetValidacionServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetVariableServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.AeAreaServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.AeGeneracionHistorialServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.AeGeneracionServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.AeRegistroBaseServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MueAtributoExtraServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MueEmpresaServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MueHogarServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MueModeloMuestraServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MueMuestraDetalleServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MueMuestraServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MuePersonaServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MuePredioServicioRemote;
import ec.gob.inec.muestra.ejb.servicios.MueViviendaServicioRemote;
import ec.gob.inec.presentacion.clases.DatosGenerales;
import ec.gob.inec.proceso.ejb.servicios.ProActividadItemServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProActividadServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProDiplaServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProEsigefServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProFaseServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProIndicadorServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProObjetoInformacionServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProSeguimientoArchivoServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProSeguimientoEsigefDiplaServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProSeguimientoRevisorServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProSeguimientoServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProSubprocesoServicioRemote;
import ec.gob.inec.r.ejb.servicios.EjecutaRRemote;
import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import ec.gob.inec.seguridad.ejb.servicios.SegAplicacionServicioRemote;
import ec.gob.inec.seguridad.ejb.servicios.SegPaginaServicioRemote;
import ec.gob.inec.seguridad.ejb.servicios.SegPermisoServicioRemote;
import ec.gob.inec.seguridad.ejb.servicios.SegRolPermisoServicioRemote;
import ec.gob.inec.seguridad.ejb.servicios.SegRolServicioRemote;
import ec.gob.inec.seguridad.ejb.servicios.SegRolUsuarioServicioRemote;
import ec.gob.inec.seguridad.ejb.servicios.SegSesionActivaServicioRemote;
import ec.gob.inec.seguridad.ejb.servicios.SegUsuarioServicioRemote;
import ec.gob.inec.reportes.ejb.servicios.RepoReporteServicioRemote;
import ec.gob.inec.reportes.ejb.servicios.RepoSubreporteServicioRemote;
import ec.gob.inec.reportes.ejb.servicios.RepoColumnaServicioRemote;
import ec.gob.inec.reportes.ejb.servicios.ActualizaBDServicioRemote;
import ec.gob.inec.reportes.ejb.servicios.RepoFiltroServicioRemote;
import ec.gob.inec.reportes.ejb.servicios.RepoProcedimientoServicioRemote;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.servicios.SegAuditoriaCabServicioRemote;
import ec.gob.inec.util.ejb.servicios.UtilImportarExportarServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProValorNumericoServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProValorTextoServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProBaseConsolidadaInicialServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProCargaInfoFinServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProCedulaDiplaDetalleServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProCedulaDiplaServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProEstructuraAmpliadaServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProNombreColumnaServicioRemote;
import ec.gob.inec.proceso.ejb.servicios.ProTotalesInfoFinServicioRemote;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

import org.primefaces.event.MenuActionEvent;

import org.primefaces.model.menu.MenuItem;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author rmoreano
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class BaseControlador implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(BaseControlador.class.getName());

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    @ManagedProperty("#{propiedadesControlador}")
    private PropiedadesControlador propiedadesControlador;

    private CacheTimer cacheTimer;

    private String nombreTema;
    private String urlTemaParcial;
    private String profundidadPagina;
    private DatosGenerales datosGenerales;
    private String nombreAplicacion;
    private SegAplicacion segAplicacion;

    InitialContext contextAdministracion = null;
    InitialContext contextCaptura = null;
    InitialContext contextDistribucionTrabajo = null;
    InitialContext contextGeografia = null;
    InitialContext contextMetadato = null;
    InitialContext contextMuestra = null;
    InitialContext contextProceso = null;
    InitialContext contextReportes = null;
    InitialContext contextSeguridad = null;
    InitialContext contextUtil = null;
    InitialContext contextR = null;

    //Remote Administracion
    private AdmParametroGlobalServicioRemote admParametroGlobalServicioRemote;
    private AdmOperacionEstadisticaServicioRemote admOperacionEstadisticaServicioRemote;
    private AdmPeriodoServicioRemote admPeriodoServicioRemote;
    private AdmPersonalServicioRemote admPersonalServicioRemote;
    private AdmInstitucionServicioRemote admInstitucionServicioRemote;
    private AdmOrganigramaServicioRemote admOrganigramaServicioRemote;
    private AdmDispositivoServicioRemote admDispositivoServicioRemote;
    private AdmColumnaServicioRemote admColumnaServicioRemote;
    private AdmTablaServicioRemote admTablaServicioRemote;
    private AdmCorreoCabServicioRemote admCorreoCabServicioRemote;
    private AdmCorreoDetServicioRemote admCorreoDetServicioRemote;
    private AdmCorreoDestinatarioServicioRemote admCorreoDestinatarioServicioRemote;
    private AdmBaseDatosServicioRemote admBaseDatosServicioRemote;

    //Remote Captura
    private CaptCargaControlEquipoServicioRemote captCargaControlEquipoServicioRemote;
    private CaptCargaControlServicioRemote captCargaControlServicioRemote;
    private CaptCabeceraServicioRemote captCabeceraServicioRemote;
    private CaptDetalleVServicioRemote captDetalleVServicioRemote;
    private CaptDetalleHServicioRemote captDetalleHServicioRemote;
    private CaptEstadoServicioRemote captEstadoServicioRemote;
    private CaptObservacionServicioRemote captObservacionServicioRemote;
    private CaptGeoreferenciaServicioRemote captGeoreferenciaServicioRemote;

    //Remote Distribucion Trabajo
    private FaseOperativoServicioRemote disFaseOperativoServicioRemote;
    private EquipoTrabajoServicioRemote disEquipoTrabajoServicioRemote;
    private EquipoTrabajoDetalleServicioRemote disEquipoTrabajoDetalleServicioRemote;
    private CargaTrabajoServicioRemote disCargaTrabajoServicioRemote;
    private CargaTrabajoDetalleServicioRemote disCargaTrabajoDetalleServicioRemote;

    //Remote Geografía
    private GeoCoberturaServicioRemote geoCoberturaServicioRemote;
    private GeoDpaServicioRemote geoDpaServicioRemote;

    //Remote Metadato
    private MetCatalogoCiiuServicioRemote metCatalogoCiiuServicioRemote;
    private MetCatalogoCiuoServicioRemote metCatalogoCiuoServicioRemote;
    private MetCatalogoServicioRemote metCatalogoServicioRemote;
    private MetTipoCatalogoServicioRemote metTipoCatalogoServicioRemote;
    private MetVariableServicioRemote metVariableServicioRemote;
    private MetConceptoServicioRemote metConceptoServicioRemote;
    private MetSeccionServicioRemote metSeccionServicioRemote;
    private MetOperativoServicioRemote metOperativoServicioRemote;
    private MetFormularioServicioRemote metFormularioServicioRemote;
    private MetSaltoServicioRemote metSaltoServicioRemote;
    private MetValidacionServicioRemote metValidacionServicioRemote;
    private MetFormularioSeccionServicioRemote metFormularioSeccionServicioRemote;

    //Remote Muestra
    private MueMuestraServicioRemote mueMuestraServicioRemote;
    private MueMuestraDetalleServicioRemote mueMuestraDetalleServicioRemote;
    private MueModeloMuestraServicioRemote mueModeloMuestraServicioRemote;
    private MueEmpresaServicioRemote mueEmpresaServicioRemote;
    private MueHogarServicioRemote mueHogarServicioRemote;
    private MueViviendaServicioRemote mueViviendaServicioRemote;
    private MuePersonaServicioRemote muePersonaServicioRemote;
    private MuePredioServicioRemote muePredioServicioRemote;
    private MueAtributoExtraServicioRemote mueAtributoExtraServicioRemote;

    private AeAreaServicioRemote aeAreaServicioRemote;
    private AeGeneracionHistorialServicioRemote aeGeneracionHistorialServicioRemote;
    private AeGeneracionServicioRemote aeGeneracionServicioRemote;
    private AeRegistroBaseServicioRemote aeRegistroBaseServicioRemote;

    //Remote Proceso
    private ProActividadItemServicioRemote proActividadItemServicioRemote;
    private ProActividadServicioRemote proActividadServicioRemote;
    private ProFaseServicioRemote proFaseServicioRemote;
    private ProObjetoInformacionServicioRemote proObjetoInformacionServicioRemote;
    private ProSeguimientoArchivoServicioRemote proSeguimientoArchivoServicioRemote;
    private ProSeguimientoRevisorServicioRemote proSeguimientoRevisorServicioRemote;
    private ProSeguimientoServicioRemote proSeguimientoServicioRemote;
    private ProSubprocesoServicioRemote proSubprocesoServicioRemote;
    private ProIndicadorServicioRemote proIndicadorServicioRemote;
    private ProSeguimientoEsigefDiplaServicioRemote proSeguimientoEsigefDiplaServicioRemote;
    private ProEsigefServicioRemote proEsigefServicioRemote;
    private ProDiplaServicioRemote proDiplaServicioRemote;
    private ProBaseConsolidadaInicialServicioRemote proBaseConsolidadaInicialServicioRemote;
    private ProCedulaDiplaServicioRemote proCedulaDiplaServicioRemote;
    private ProCedulaDiplaDetalleServicioRemote proCedulaDiplaDetalleServicioRemote;
    private ProNombreColumnaServicioRemote proNombreColumnaServicioRemote;
    private ProValorNumericoServicioRemote proValorNumericoServicioRemote;
    private ProValorTextoServicioRemote proValorTextoServicioRemote;
    private ProEstructuraAmpliadaServicioRemote proEstructuraAmpliadaServicioRemote;

    private ProTotalesInfoFinServicioRemote proTotalesInfoFinServicioRemote;
    private ProCargaInfoFinServicioRemote proCargaInfoFinServicioRemote;

    //Remote Reporte
    private RepoColumnaServicioRemote repoColumnaServicioRemote;
    private RepoReporteServicioRemote repoReporteServicioRemote;
    private RepoSubreporteServicioRemote repoSubreporteServicioRemote;
    private ActualizaBDServicioRemote actualizaBDServicioRemote;
    private RepoProcedimientoServicioRemote repoProcedimientoServicioRemote;
    private RepoFiltroServicioRemote repoFiltroServicioRemote;

    //Remote Seguridad
    private SegRolServicioRemote segRolServicioRemote;
    private SegRolUsuarioServicioRemote segRolUsuarioServicioRemote;
    private SegAplicacionServicioRemote segAplicacionServicioRemote;
    private SegSesionActivaServicioRemote segSesionActivaServicioRemote;
    private SegUsuarioServicioRemote segUsuarioServicioRemote;
    private SegRolPermisoServicioRemote segRolPermisoServicioRemote;
    private SegPermisoServicioRemote segPermisoServicioRemote;
    private SegPaginaServicioRemote segPaginaServicioRemote;
    private SegAuditoriaCabServicioRemote segAuditoriaCabServicioRemote;

    //Remote Util
    private UtilImportarExportarServicioRemote utilImportarExportarServicioRemote;

    //Remote R
    private EjecutaRRemote ejecutaRRemote;

    private Map<String, String> browsers = new LinkedHashMap();
    private Map<String, String> so = new LinkedHashMap();

    //variables necesarias para captura Web Precenso
    private int precCodigoOE;
    private int precCodigoForm;
    private int precCodigoSecAman;
    private int precCodigoSecDisp;

    private String parametroAES;

    //Variables necesarias para captura Web ENREFAM
    private Integer enrefamCodigoOE;
    private Integer enrefamFrm1;
    private Integer enrefamFrm2;
    private Integer enrefamFrm3;
    private Integer enrefamFrm4;
    private Integer ocupacionEnrefam;
    private Integer noEfectivaEnrefam;
    private Integer codPersonaEnrefam;
    private Integer estCivilEnrefam;
    private Integer parejaEnrefam;
    private Integer relacionEnrefam;
    private Integer nombreEnrefam;
    private Integer civilEnrefam;
    private Integer educacionEnrefam;
    private Integer efectivaUniFamiliaEnrefam;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public BaseControlador() {
    }

    @PostConstruct
    public void init() {
        try {
            // System.out.println("Inicio init Navegacion Controlador");
            //cambiaURLWS();
            cacheTimer = (CacheTimer) InitialContext.doLookup("java:global/sipe-cache-siac-ejb/CacheTimer!ec.gob.inec.cache.clases.CacheTimer");
            datosGenerales = new DatosGenerales();
            inicializaContextLookUp();
            //getProfundidad();
            getContextName();
            llenaDatosAplicacion();
            browsers = new LinkedHashMap();
            //List<MetCatalogo> listaBrowser = cacheTimer.getMetCatalogoList().stream().filter(x -> x.getCodTipoCatalogo().getAlias().equals("TIP_NAV")).collect(Collectors.toList());
            List<MetCatalogo> listaBrowser = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIP_NAV");
            for (MetCatalogo brw : listaBrowser) {
                browsers.put(brw.getValor(), brw.getNombre());
            }

            so = new LinkedHashMap();
            List<MetCatalogo> listaSO = cacheTimer.getMetCatalogoList().stream().filter(x -> x.getCodTipoCatalogo().getAlias().equals("TIP_SO")).collect(Collectors.toList());
            //List<MetCatalogo> listaSO = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIP_SO");
            for (MetCatalogo sitemaOperativo : listaSO) {
                so.put(sitemaOperativo.getValor(), sitemaOperativo.getNombre());
            }
            urlTemaParcial = "css/temas/gabo.css";
            // System.out.println("Fin init Navegacion Controlador");
        } catch (Exception ex) {
            Logger.getLogger(BaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold> 
    //<editor-fold desc="get and set" defaultstate="collapsed">

    public InitialContext getContextAdministracion() {
        return contextAdministracion;
    }

    public void setContextAdministracion(InitialContext contextAdministracion) {
        this.contextAdministracion = contextAdministracion;
    }

    public InitialContext getContextCaptura() {
        return contextCaptura;
    }

    public void setContextCaptura(InitialContext contextCaptura) {
        this.contextCaptura = contextCaptura;
    }

    public InitialContext getContextDistribucionTrabajo() {
        return contextDistribucionTrabajo;
    }

    public void setContextDistribucionTrabajo(InitialContext contextDistribucionTrabajo) {
        this.contextDistribucionTrabajo = contextDistribucionTrabajo;
    }

    public InitialContext getContextGeografia() {
        return contextGeografia;
    }

    public void setContextGeografia(InitialContext contextGeografia) {
        this.contextGeografia = contextGeografia;
    }

    public InitialContext getContextMetadato() {
        return contextMetadato;
    }

    public void setContextMetadato(InitialContext contextMetadato) {
        this.contextMetadato = contextMetadato;
    }

    public InitialContext getContextMuestra() {
        return contextMuestra;
    }

    public void setContextMuestra(InitialContext contextMuestra) {
        this.contextMuestra = contextMuestra;
    }

    public InitialContext getContextProceso() {
        return contextProceso;
    }

    public void setContextProceso(InitialContext contextProceso) {
        this.contextProceso = contextProceso;
    }

    public InitialContext getContextReportes() {
        return contextReportes;
    }

    public void setContextReportes(InitialContext contextReportes) {
        this.contextReportes = contextReportes;
    }

    public InitialContext getContextSeguridad() {
        return contextSeguridad;
    }

    public void setContextSeguridad(InitialContext contextSeguridad) {
        this.contextSeguridad = contextSeguridad;
    }

    public InitialContext getContextUtil() {
        return contextUtil;
    }

    public void setContextUtil(InitialContext contextUtil) {
        this.contextUtil = contextUtil;
    }

    public PropiedadesControlador getPropiedadesControlador() {
        return propiedadesControlador;
    }

    public void setPropiedadesControlador(PropiedadesControlador propiedadesControlador) {
        this.propiedadesControlador = propiedadesControlador;
    }

    public AdmParametroGlobalServicioRemote getAdmParametroGlobalServicioRemote() {
        return admParametroGlobalServicioRemote;
    }

    public void setAdmParametroGlobalServicioRemote(AdmParametroGlobalServicioRemote admParametroGlobalServicioRemote) {
        this.admParametroGlobalServicioRemote = admParametroGlobalServicioRemote;
    }

    public CaptCabeceraServicioRemote getCaptCabeceraServicioRemote() {
        return captCabeceraServicioRemote;
    }

    public void setCaptCabeceraServicioRemote(CaptCabeceraServicioRemote captCabeceraServicioRemote) {
        this.captCabeceraServicioRemote = captCabeceraServicioRemote;
    }

    public CaptDetalleHServicioRemote getCaptDetalleHServicioRemote() {
        return captDetalleHServicioRemote;
    }

    public void setCaptDetalleHServicioRemote(CaptDetalleHServicioRemote captDetalleHServicioRemote) {
        this.captDetalleHServicioRemote = captDetalleHServicioRemote;
    }

    public CaptGeoreferenciaServicioRemote getCaptGeoreferenciaServicioRemote() {
        return captGeoreferenciaServicioRemote;
    }

    public void setCaptGeoreferenciaServicioRemote(CaptGeoreferenciaServicioRemote captGeoreferenciaServicioRemote) {
        this.captGeoreferenciaServicioRemote = captGeoreferenciaServicioRemote;
    }

    public AdmOperacionEstadisticaServicioRemote getAdmOperacionEstadisticaServicioRemote() {
        return admOperacionEstadisticaServicioRemote;
    }

    public void setAdmOperacionEstadisticaServicioRemote(AdmOperacionEstadisticaServicioRemote admOperacionEstadisticaServicioRemote) {
        this.admOperacionEstadisticaServicioRemote = admOperacionEstadisticaServicioRemote;
    }

    public AdmPeriodoServicioRemote getAdmPeriodoServicioRemote() {
        return admPeriodoServicioRemote;
    }

    public void setAdmPeriodoServicioRemote(AdmPeriodoServicioRemote admPeriodoServicioRemote) {
        this.admPeriodoServicioRemote = admPeriodoServicioRemote;
    }

    public AdmPersonalServicioRemote getAdmPersonalServicioRemote() {
        return admPersonalServicioRemote;
    }

    public void setAdmPersonalServicioRemote(AdmPersonalServicioRemote admPersonalServicioRemote) {
        this.admPersonalServicioRemote = admPersonalServicioRemote;
    }

    public MetCatalogoServicioRemote getMetCatalogoServicioRemote() {
        return metCatalogoServicioRemote;
    }

    public void setMetCatalogoServicioRemote(MetCatalogoServicioRemote metCatalogoServicioRemote) {
        this.metCatalogoServicioRemote = metCatalogoServicioRemote;
    }

    public CargaTrabajoServicioRemote getDisCargaTrabajoServicioRemote() {
        return disCargaTrabajoServicioRemote;
    }

    public void setDisCargaTrabajoServicioRemote(CargaTrabajoServicioRemote disCargaTrabajoServicioRemote) {
        this.disCargaTrabajoServicioRemote = disCargaTrabajoServicioRemote;
    }

    public EquipoTrabajoDetalleServicioRemote getDisEquipoTrabajoDetalleServicioRemote() {
        return disEquipoTrabajoDetalleServicioRemote;
    }

    public void setDisEquipoTrabajoDetalleServicioRemote(EquipoTrabajoDetalleServicioRemote disEquipoTrabajoDetalleServicioRemote) {
        this.disEquipoTrabajoDetalleServicioRemote = disEquipoTrabajoDetalleServicioRemote;
    }

    public FaseOperativoServicioRemote getDisFaseOperativoServicioRemote() {
        return disFaseOperativoServicioRemote;
    }

    public void setDisFaseOperativoServicioRemote(FaseOperativoServicioRemote disFaseOperativoServicioRemote) {
        this.disFaseOperativoServicioRemote = disFaseOperativoServicioRemote;
    }

    public EquipoTrabajoServicioRemote getDisEquipoTrabajoServicioRemote() {
        return disEquipoTrabajoServicioRemote;
    }

    public void setDisEquipoTrabajoServicioRemote(EquipoTrabajoServicioRemote disEquipoTrabajoServicioRemote) {
        this.disEquipoTrabajoServicioRemote = disEquipoTrabajoServicioRemote;
    }

    public String getNombreTema() {
        return nombreTema;
    }

    public void setNombreTema(String nombreTema) {
        this.nombreTema = nombreTema;
    }

    public String getProfundidadPagina() {
        return profundidadPagina;
    }

    public void setProfundidadPagina(String profundidadPagina) {
        this.profundidadPagina = profundidadPagina;
    }

    public DatosGenerales getDatosGenerales() {
        return datosGenerales;
    }

    public void setDatosGenerales(DatosGenerales datosGenerales) {
        this.datosGenerales = datosGenerales;
    }

    public SegRolServicioRemote getSegRolServicioRemote() {
        return segRolServicioRemote;
    }

    public void setSegRolServicioRemote(SegRolServicioRemote segRolServicioRemote) {
        this.segRolServicioRemote = segRolServicioRemote;
    }

    public SegRolUsuarioServicioRemote getSegRolUsuarioServicioRemote() {
        return segRolUsuarioServicioRemote;
    }

    public void setSegRolUsuarioServicioRemote(SegRolUsuarioServicioRemote segRolUsuarioServicioRemote) {
        this.segRolUsuarioServicioRemote = segRolUsuarioServicioRemote;
    }

    public SegAplicacionServicioRemote getSegAplicacionServicioRemote() {
        return segAplicacionServicioRemote;
    }

    public void setSegAplicacionServicioRemote(SegAplicacionServicioRemote segAplicacionServicioRemote) {
        this.segAplicacionServicioRemote = segAplicacionServicioRemote;
    }

    public String getNombreAplicacion() {
        return nombreAplicacion;
    }

    public void setNombreAplicacion(String nombreAplicacion) {
        this.nombreAplicacion = nombreAplicacion;
    }

    public SegSesionActivaServicioRemote getSegSesionActivaServicioRemote() {
        return segSesionActivaServicioRemote;
    }

    public void setSegSesionActivaServicioRemote(SegSesionActivaServicioRemote segSesionActivaServicioRemote) {
        this.segSesionActivaServicioRemote = segSesionActivaServicioRemote;
    }

    public SegUsuarioServicioRemote getSegUsuarioServicioRemote() {
        return segUsuarioServicioRemote;
    }

    public void setSegUsuarioServicioRemote(SegUsuarioServicioRemote segUsuarioServicioRemote) {
        this.segUsuarioServicioRemote = segUsuarioServicioRemote;
    }

    public AdmInstitucionServicioRemote getAdmInstitucionServicioRemote() {
        return admInstitucionServicioRemote;
    }

    public void setAdmInstitucionServicioRemote(AdmInstitucionServicioRemote admInstitucionServicioRemote) {
        this.admInstitucionServicioRemote = admInstitucionServicioRemote;
    }

    public AdmOrganigramaServicioRemote getAdmOrganigramaServicioRemote() {
        return admOrganigramaServicioRemote;
    }

    public void setAdmOrganigramaServicioRemote(AdmOrganigramaServicioRemote admOrganigramaServicioRemote) {
        this.admOrganigramaServicioRemote = admOrganigramaServicioRemote;
    }

    public AdmDispositivoServicioRemote getAdmDispositivoServicioRemote() {
        return admDispositivoServicioRemote;
    }

    public void setAdmDispositivoServicioRemote(AdmDispositivoServicioRemote admDispositivoServicioRemote) {
        this.admDispositivoServicioRemote = admDispositivoServicioRemote;
    }

    public MetTipoCatalogoServicioRemote getMetTipoCatalogoServicioRemote() {
        return metTipoCatalogoServicioRemote;
    }

    public void setMetTipoCatalogoServicioRemote(MetTipoCatalogoServicioRemote metTipoCatalogoServicioRemote) {
        this.metTipoCatalogoServicioRemote = metTipoCatalogoServicioRemote;
    }

    public MetVariableServicioRemote getMetVariableServicioRemote() {
        return metVariableServicioRemote;
    }

    public void setMetVariableServicioRemote(MetVariableServicioRemote metVariableServicioRemote) {
        this.metVariableServicioRemote = metVariableServicioRemote;
    }

    public MetConceptoServicioRemote getMetConceptoServicioRemote() {
        return metConceptoServicioRemote;
    }

    public void setMetConceptoServicioRemote(MetConceptoServicioRemote metConceptoServicioRemote) {
        this.metConceptoServicioRemote = metConceptoServicioRemote;
    }

    public MetSeccionServicioRemote getMetSeccionServicioRemote() {
        return metSeccionServicioRemote;
    }

    public void setMetSeccionServicioRemote(MetSeccionServicioRemote metSeccionServicioRemote) {
        this.metSeccionServicioRemote = metSeccionServicioRemote;
    }

    public SegRolPermisoServicioRemote getSegRolPermisoServicioRemote() {
        return segRolPermisoServicioRemote;
    }

    public void setSegRolPermisoServicioRemote(SegRolPermisoServicioRemote segRolPermisoServicioRemote) {
        this.segRolPermisoServicioRemote = segRolPermisoServicioRemote;
    }

    public SegPermisoServicioRemote getSegPermisoServicioRemote() {
        return segPermisoServicioRemote;
    }

    public void setSegPermisoServicioRemote(SegPermisoServicioRemote segPermisoServicioRemote) {
        this.segPermisoServicioRemote = segPermisoServicioRemote;
    }

    public SegPaginaServicioRemote getSegPaginaServicioRemote() {
        return segPaginaServicioRemote;
    }

    public void setSegPaginaServicioRemote(SegPaginaServicioRemote segPaginaServicioRemote) {
        this.segPaginaServicioRemote = segPaginaServicioRemote;
    }

    public MueMuestraServicioRemote getMueMuestraServicioRemote() {
        return mueMuestraServicioRemote;
    }

    public void setMueMuestraServicioRemote(MueMuestraServicioRemote mueMuestraServicioRemote) {
        this.mueMuestraServicioRemote = mueMuestraServicioRemote;
    }

    public MueMuestraDetalleServicioRemote getMueMuestraDetalleServicioRemote() {
        return mueMuestraDetalleServicioRemote;
    }

    public void setMueMuestraDetalleServicioRemote(MueMuestraDetalleServicioRemote mueMuestraDetalleServicioRemote) {
        this.mueMuestraDetalleServicioRemote = mueMuestraDetalleServicioRemote;
    }

    public Map<String, String> getBrowsers() {
        return browsers;
    }

    public void setBrowsers(Map<String, String> browsers) {
        this.browsers = browsers;
    }

    public Map<String, String> getSo() {
        return so;
    }

    public void setSo(Map<String, String> so) {
        this.so = so;
    }

    public SegAplicacion getSegAplicacion() {
        return segAplicacion;
    }

    public void setSegAplicacion(SegAplicacion segAplicacion) {
        this.segAplicacion = segAplicacion;
    }

    public MetOperativoServicioRemote getMetOperativoServicioRemote() {
        return metOperativoServicioRemote;
    }

    public void setMetOperativoServicioRemote(MetOperativoServicioRemote metOperativoServicioRemote) {
        this.metOperativoServicioRemote = metOperativoServicioRemote;
    }

    public GeoCoberturaServicioRemote getGeoCoberturaServicioRemote() {
        return geoCoberturaServicioRemote;
    }

    public void setGeoCoberturaServicioRemote(GeoCoberturaServicioRemote geoCoberturaServicioRemote) {
        this.geoCoberturaServicioRemote = geoCoberturaServicioRemote;
    }

    public GeoDpaServicioRemote getGeoDpaServicioRemote() {
        return geoDpaServicioRemote;
    }

    public void setGeoDpaServicioRemote(GeoDpaServicioRemote geoDpaServicioRemote) {
        this.geoDpaServicioRemote = geoDpaServicioRemote;
    }

    public AdmColumnaServicioRemote getAdmColumnaServicioRemote() {
        return admColumnaServicioRemote;
    }

    public void setAdmColumnaServicioRemote(AdmColumnaServicioRemote admColumnaServicioRemote) {
        this.admColumnaServicioRemote = admColumnaServicioRemote;
    }

    public AdmTablaServicioRemote getAdmTablaServicioRemote() {
        return admTablaServicioRemote;
    }

    public void setAdmTablaServicioRemote(AdmTablaServicioRemote admTablaServicioRemote) {
        this.admTablaServicioRemote = admTablaServicioRemote;
    }

    public RepoColumnaServicioRemote getRepoColumnaServicioRemote() {
        return repoColumnaServicioRemote;
    }

    public void setRepoColumnaServicioRemote(RepoColumnaServicioRemote repoColumnaServicioRemote) {
        this.repoColumnaServicioRemote = repoColumnaServicioRemote;
    }

    public RepoReporteServicioRemote getRepoReporteServicioRemote() {
        return repoReporteServicioRemote;
    }

    public void setRepoReporteServicioRemote(RepoReporteServicioRemote repoReporteServicioRemote) {
        this.repoReporteServicioRemote = repoReporteServicioRemote;
    }

    public RepoSubreporteServicioRemote getRepoSubreporteServicioRemote() {
        return repoSubreporteServicioRemote;
    }

    public void setRepoSubreporteServicioRemote(RepoSubreporteServicioRemote repoSubreporteServicioRemote) {
        this.repoSubreporteServicioRemote = repoSubreporteServicioRemote;
    }

    public ActualizaBDServicioRemote getActualizaBDServicioRemote() {
        return actualizaBDServicioRemote;
    }

    public void setActualizaBDServicioRemote(ActualizaBDServicioRemote actualizaBDServicioRemote) {
        this.actualizaBDServicioRemote = actualizaBDServicioRemote;
    }

    public MetFormularioServicioRemote getMetFormularioServicioRemote() {
        return metFormularioServicioRemote;
    }

    public void setMetFormularioServicioRemote(MetFormularioServicioRemote metFormularioServicioRemote) {
        this.metFormularioServicioRemote = metFormularioServicioRemote;
    }

    public MetSaltoServicioRemote getMetSaltoServicioRemote() {
        return metSaltoServicioRemote;
    }

    public void setMetSaltoServicioRemote(MetSaltoServicioRemote metSaltoServicioRemote) {
        this.metSaltoServicioRemote = metSaltoServicioRemote;
    }

    public MetValidacionServicioRemote getMetValidacionServicioRemote() {
        return metValidacionServicioRemote;
    }

    public void setMetValidacionServicioRemote(MetValidacionServicioRemote metValidacionServicioRemote) {
        this.metValidacionServicioRemote = metValidacionServicioRemote;
    }

    public MetFormularioSeccionServicioRemote getMetFormularioSeccionServicioRemote() {
        return metFormularioSeccionServicioRemote;
    }

    public void setMetFormularioSeccionServicioRemote(MetFormularioSeccionServicioRemote metFormularioSeccionServicioRemote) {
        this.metFormularioSeccionServicioRemote = metFormularioSeccionServicioRemote;
    }

    public RepoProcedimientoServicioRemote getRepoProcedimientoServicioRemote() {
        return repoProcedimientoServicioRemote;
    }

    public void setRepoProcedimientoServicioRemote(RepoProcedimientoServicioRemote repoProcedimientoServicioRemote) {
        this.repoProcedimientoServicioRemote = repoProcedimientoServicioRemote;
    }

    public ProActividadItemServicioRemote getProActividadItemServicioRemote() {
        return proActividadItemServicioRemote;
    }

    public void setProActividadItemServicioRemote(ProActividadItemServicioRemote proActividadItemServicioRemote) {
        this.proActividadItemServicioRemote = proActividadItemServicioRemote;
    }

    public ProActividadServicioRemote getProActividadServicioRemote() {
        return proActividadServicioRemote;
    }

    public void setProActividadServicioRemote(ProActividadServicioRemote proActividadServicioRemote) {
        this.proActividadServicioRemote = proActividadServicioRemote;
    }

    public ProFaseServicioRemote getProFaseServicioRemote() {
        return proFaseServicioRemote;
    }

    public void setProFaseServicioRemote(ProFaseServicioRemote proFaseServicioRemote) {
        this.proFaseServicioRemote = proFaseServicioRemote;
    }

    public ProObjetoInformacionServicioRemote getProObjetoInformacionServicioRemote() {
        return proObjetoInformacionServicioRemote;
    }

    public void setProObjetoInformacionServicioRemote(ProObjetoInformacionServicioRemote proObjetoInformacionServicioRemote) {
        this.proObjetoInformacionServicioRemote = proObjetoInformacionServicioRemote;
    }

    public ProSeguimientoArchivoServicioRemote getProSeguimientoArchivoServicioRemote() {
        return proSeguimientoArchivoServicioRemote;
    }

    public void setProSeguimientoArchivoServicioRemote(ProSeguimientoArchivoServicioRemote proSeguimientoArchivoServicioRemote) {
        this.proSeguimientoArchivoServicioRemote = proSeguimientoArchivoServicioRemote;
    }

    public ProSeguimientoRevisorServicioRemote getProSeguimientoRevisorServicioRemote() {
        return proSeguimientoRevisorServicioRemote;
    }

    public void setProSeguimientoRevisorServicioRemote(ProSeguimientoRevisorServicioRemote proSeguimientoRevisorServicioRemote) {
        this.proSeguimientoRevisorServicioRemote = proSeguimientoRevisorServicioRemote;
    }

    public ProSeguimientoServicioRemote getProSeguimientoServicioRemote() {
        return proSeguimientoServicioRemote;
    }

    public void setProSeguimientoServicioRemote(ProSeguimientoServicioRemote proSeguimientoServicioRemote) {
        this.proSeguimientoServicioRemote = proSeguimientoServicioRemote;
    }

    public ProSubprocesoServicioRemote getProSubprocesoServicioRemote() {
        return proSubprocesoServicioRemote;
    }

    public void setProSubprocesoServicioRemote(ProSubprocesoServicioRemote proSubprocesoServicioRemote) {
        this.proSubprocesoServicioRemote = proSubprocesoServicioRemote;
    }

    public AdmCorreoCabServicioRemote getAdmCorreoCabServicioRemote() {
        return admCorreoCabServicioRemote;
    }

    public void setAdmCorreoCabServicioRemote(AdmCorreoCabServicioRemote admCorreoCabServicioRemote) {
        this.admCorreoCabServicioRemote = admCorreoCabServicioRemote;
    }

    public AdmCorreoDetServicioRemote getAdmCorreoDetServicioRemote() {
        return admCorreoDetServicioRemote;
    }

    public void setAdmCorreoDetServicioRemote(AdmCorreoDetServicioRemote admCorreoDetServicioRemote) {
        this.admCorreoDetServicioRemote = admCorreoDetServicioRemote;
    }

    public AdmCorreoDestinatarioServicioRemote getAdmCorreoDestinatarioServicioRemote() {
        return admCorreoDestinatarioServicioRemote;
    }

    public void setAdmCorreoDestinatarioServicioRemote(AdmCorreoDestinatarioServicioRemote admCorreoDestinatarioServicioRemote) {
        this.admCorreoDestinatarioServicioRemote = admCorreoDestinatarioServicioRemote;
    }

    public CacheTimer getCacheTimer() {
        return cacheTimer;
    }

    public void setCacheTimer(CacheTimer cacheTimer) {
        this.cacheTimer = cacheTimer;
    }

    public RepoFiltroServicioRemote getRepoFiltroServicioRemote() {
        return repoFiltroServicioRemote;
    }

    public void setRepoFiltroServicioRemote(RepoFiltroServicioRemote repoFiltroServicioRemote) {
        this.repoFiltroServicioRemote = repoFiltroServicioRemote;
    }

    public CaptCargaControlEquipoServicioRemote getCaptCargaControlEquipoServicioRemote() {
        return captCargaControlEquipoServicioRemote;
    }

    public void setCaptCargaControlEquipoServicioRemote(CaptCargaControlEquipoServicioRemote captCargaControlEquipoServicioRemote) {
        this.captCargaControlEquipoServicioRemote = captCargaControlEquipoServicioRemote;
    }

    public CaptCargaControlServicioRemote getCaptCargaControlServicioRemote() {
        return captCargaControlServicioRemote;
    }

    public void setCaptCargaControlServicioRemote(CaptCargaControlServicioRemote captCargaControlServicioRemote) {
        this.captCargaControlServicioRemote = captCargaControlServicioRemote;
    }

    public CaptDetalleVServicioRemote getCaptDetalleVServicioRemote() {
        return captDetalleVServicioRemote;
    }

    public void setCaptDetalleVServicioRemote(CaptDetalleVServicioRemote captDetalleVServicioRemote) {
        this.captDetalleVServicioRemote = captDetalleVServicioRemote;
    }

    public CaptEstadoServicioRemote getCaptEstadoServicioRemote() {
        return captEstadoServicioRemote;
    }

    public void setCaptEstadoServicioRemote(CaptEstadoServicioRemote captEstadoServicioRemote) {
        this.captEstadoServicioRemote = captEstadoServicioRemote;
    }

    public CaptObservacionServicioRemote getCaptObservacionServicioRemote() {
        return captObservacionServicioRemote;
    }

    public void setCaptObservacionServicioRemote(CaptObservacionServicioRemote captObservacionServicioRemote) {
        this.captObservacionServicioRemote = captObservacionServicioRemote;
    }

    public AdmBaseDatosServicioRemote getAdmBaseDatosServicioRemote() {
        return admBaseDatosServicioRemote;
    }

    public void setAdmBaseDatosServicioRemote(AdmBaseDatosServicioRemote admBaseDatosServicioRemote) {
        this.admBaseDatosServicioRemote = admBaseDatosServicioRemote;
    }

    public ProIndicadorServicioRemote getProIndicadorServicioRemote() {
        return proIndicadorServicioRemote;
    }

    public void setProIndicadorServicioRemote(ProIndicadorServicioRemote proIndicadorServicioRemote) {
        this.proIndicadorServicioRemote = proIndicadorServicioRemote;
    }

    public SegAuditoriaCabServicioRemote getSegAuditoriaCabServicioRemote() {
        return segAuditoriaCabServicioRemote;
    }

    public void setSegAuditoriaCabServicioRemote(SegAuditoriaCabServicioRemote segAuditoriaCabServicioRemote) {
        this.segAuditoriaCabServicioRemote = segAuditoriaCabServicioRemote;
    }

    public CargaTrabajoDetalleServicioRemote getDisCargaTrabajoDetalleServicioRemote() {
        return disCargaTrabajoDetalleServicioRemote;
    }

    public void setDisCargaTrabajoDetalleServicioRemote(CargaTrabajoDetalleServicioRemote disCargaTrabajoDetalleServicioRemote) {
        this.disCargaTrabajoDetalleServicioRemote = disCargaTrabajoDetalleServicioRemote;
    }

    public UtilImportarExportarServicioRemote getUtilImportarExportarServicioRemote() {
        return utilImportarExportarServicioRemote;
    }

    public void setUtilImportarExportarServicioRemote(UtilImportarExportarServicioRemote utilImportarExportarServicioRemote) {
        this.utilImportarExportarServicioRemote = utilImportarExportarServicioRemote;
    }

    public ProSeguimientoEsigefDiplaServicioRemote getProSeguimientoEsigefDiplaServicioRemote() {
        return proSeguimientoEsigefDiplaServicioRemote;
    }

    public void setProSeguimientoEsigefDiplaServicioRemote(ProSeguimientoEsigefDiplaServicioRemote proSeguimientoEsigefDiplaServicioRemote) {
        this.proSeguimientoEsigefDiplaServicioRemote = proSeguimientoEsigefDiplaServicioRemote;
    }

    public ProEsigefServicioRemote getProEsigefServicioRemote() {
        return proEsigefServicioRemote;
    }

    public void setProEsigefServicioRemote(ProEsigefServicioRemote proEsigefServicioRemote) {
        this.proEsigefServicioRemote = proEsigefServicioRemote;
    }

    public ProDiplaServicioRemote getProDiplaServicioRemote() {
        return proDiplaServicioRemote;
    }

    public void setProDiplaServicioRemote(ProDiplaServicioRemote proDiplaServicioRemote) {
        this.proDiplaServicioRemote = proDiplaServicioRemote;
    }

    public EjecutaRRemote getEjecutaRRemote() {
        return ejecutaRRemote;
    }

    public void setEjecutaRRemote(EjecutaRRemote ejecutaRRemote) {
        this.ejecutaRRemote = ejecutaRRemote;
    }

    public MueModeloMuestraServicioRemote getMueModeloMuestraServicioRemote() {
        return mueModeloMuestraServicioRemote;
    }

    public void setMueModeloMuestraServicioRemote(MueModeloMuestraServicioRemote mueModeloMuestraServicioRemote) {
        this.mueModeloMuestraServicioRemote = mueModeloMuestraServicioRemote;
    }

    public MueAtributoExtraServicioRemote getMueAtributoExtraServicioRemote() {
        return mueAtributoExtraServicioRemote;
    }

    public void setMueAtributoExtraServicioRemote(MueAtributoExtraServicioRemote mueAtributoExtraServicioRemote) {
        this.mueAtributoExtraServicioRemote = mueAtributoExtraServicioRemote;
    }

    public int getPrecCodigoOE() {
        return precCodigoOE;
    }

    public void setPrecCodigoOE(int precCodigoOE) {
        this.precCodigoOE = precCodigoOE;
    }

    public int getPrecCodigoForm() {
        return precCodigoForm;
    }

    public void setPrecCodigoForm(int precCodigoForm) {
        this.precCodigoForm = precCodigoForm;
    }

    public int getPrecCodigoSecAman() {
        return precCodigoSecAman;
    }

    public void setPrecCodigoSecAman(int precCodigoSecAman) {
        this.precCodigoSecAman = precCodigoSecAman;
    }

    public int getPrecCodigoSecDisp() {
        return precCodigoSecDisp;
    }

    public void setPrecCodigoSecDisp(int precCodigoSecDisp) {
        this.precCodigoSecDisp = precCodigoSecDisp;
    }

    public MueEmpresaServicioRemote getMueEmpresaServicioRemote() {
        return mueEmpresaServicioRemote;
    }

    public void setMueEmpresaServicioRemote(MueEmpresaServicioRemote mueEmpresaServicioRemote) {
        this.mueEmpresaServicioRemote = mueEmpresaServicioRemote;
    }

    public MueHogarServicioRemote getMueHogarServicioRemote() {
        return mueHogarServicioRemote;
    }

    public void setMueHogarServicioRemote(MueHogarServicioRemote mueHogarServicioRemote) {
        this.mueHogarServicioRemote = mueHogarServicioRemote;
    }

    public MueViviendaServicioRemote getMueViviendaServicioRemote() {
        return mueViviendaServicioRemote;
    }

    public void setMueViviendaServicioRemote(MueViviendaServicioRemote mueViviendaServicioRemote) {
        this.mueViviendaServicioRemote = mueViviendaServicioRemote;
    }

    public MuePersonaServicioRemote getMuePersonaServicioRemote() {
        return muePersonaServicioRemote;
    }

    public void setMuePersonaServicioRemote(MuePersonaServicioRemote muePersonaServicioRemote) {
        this.muePersonaServicioRemote = muePersonaServicioRemote;
    }

    public MuePredioServicioRemote getMuePredioServicioRemote() {
        return muePredioServicioRemote;
    }

    public void setMuePredioServicioRemote(MuePredioServicioRemote muePredioServicioRemote) {
        this.muePredioServicioRemote = muePredioServicioRemote;
    }

    public Integer getEnrefamCodigoOE() {
        return enrefamCodigoOE;
    }

    public void setEnrefamCodigoOE(Integer enrefamCodigoOE) {
        this.enrefamCodigoOE = enrefamCodigoOE;
    }

    public Integer getEnrefamFrm1() {
        return enrefamFrm1;
    }

    public void setEnrefamFrm1(Integer enrefamFrm1) {
        this.enrefamFrm1 = enrefamFrm1;
    }

    public Integer getEnrefamFrm2() {
        return enrefamFrm2;
    }

    public void setEnrefamFrm2(Integer enrefamFrm2) {
        this.enrefamFrm2 = enrefamFrm2;
    }

    public Integer getEnrefamFrm3() {
        return enrefamFrm3;
    }

    public void setEnrefamFrm3(Integer enrefamFrm3) {
        this.enrefamFrm3 = enrefamFrm3;
    }

    public Integer getEnrefamFrm4() {
        return enrefamFrm4;
    }

    public void setEnrefamFrm4(Integer enrefamFrm4) {
        this.enrefamFrm4 = enrefamFrm4;
    }

    public Integer getOcupacionEnrefam() {
        return ocupacionEnrefam;
    }

    public void setOcupacionEnrefam(Integer ocupacionEnrefam) {
        this.ocupacionEnrefam = ocupacionEnrefam;
    }

    public Integer getNoEfectivaEnrefam() {
        return noEfectivaEnrefam;
    }

    public void setNoEfectivaEnrefam(Integer noEfectivaEnrefam) {
        this.noEfectivaEnrefam = noEfectivaEnrefam;
    }

    public Integer getCodPersonaEnrefam() {
        return codPersonaEnrefam;
    }

    public void setCodPersonaEnrefam(Integer codPersonaEnrefam) {
        this.codPersonaEnrefam = codPersonaEnrefam;
    }

    public Integer getEstCivilEnrefam() {
        return estCivilEnrefam;
    }

    public void setEstCivilEnrefam(Integer estCivilEnrefam) {
        this.estCivilEnrefam = estCivilEnrefam;
    }

    public Integer getParejaEnrefam() {
        return parejaEnrefam;
    }

    public void setParejaEnrefam(Integer parejaEnrefam) {
        this.parejaEnrefam = parejaEnrefam;
    }

    public Integer getRelacionEnrefam() {
        return relacionEnrefam;
    }

    public void setRelacionEnrefam(Integer relacionEnrefam) {
        this.relacionEnrefam = relacionEnrefam;
    }

    public Integer getNombreEnrefam() {
        return nombreEnrefam;
    }

    public void setNombreEnrefam(Integer nombreEnrefam) {
        this.nombreEnrefam = nombreEnrefam;
    }

    public Integer getCivilEnrefam() {
        return civilEnrefam;
    }

    public void setCivilEnrefam(Integer civilEnrefam) {
        this.civilEnrefam = civilEnrefam;
    }

    public Integer getEducacionEnrefam() {
        return educacionEnrefam;
    }

    public void setEducacionEnrefam(Integer educacionEnrefam) {
        this.educacionEnrefam = educacionEnrefam;
    }

    public Integer getEfectivaUniFamiliaEnrefam() {
        return efectivaUniFamiliaEnrefam;
    }

    public void setEfectivaUniFamiliaEnrefam(Integer efectivaUniFamiliaEnrefam) {
        this.efectivaUniFamiliaEnrefam = efectivaUniFamiliaEnrefam;
    }

    public ProBaseConsolidadaInicialServicioRemote getProBaseConsolidadaInicialServicioRemote() {
        return proBaseConsolidadaInicialServicioRemote;
    }

    public void setProBaseConsolidadaInicialServicioRemote(ProBaseConsolidadaInicialServicioRemote proBaseConsolidadaInicialServicioRemote) {
        this.proBaseConsolidadaInicialServicioRemote = proBaseConsolidadaInicialServicioRemote;
    }

    public ProCedulaDiplaServicioRemote getProCedulaDiplaServicioRemote() {
        return proCedulaDiplaServicioRemote;
    }

    public void setProCedulaDiplaServicioRemote(ProCedulaDiplaServicioRemote proCedulaDiplaServicioRemote) {
        this.proCedulaDiplaServicioRemote = proCedulaDiplaServicioRemote;
    }

    public ProCedulaDiplaDetalleServicioRemote getProCedulaDiplaDetalleServicioRemote() {
        return proCedulaDiplaDetalleServicioRemote;
    }

    public void setProCedulaDiplaDetalleServicioRemote(ProCedulaDiplaDetalleServicioRemote proCedulaDiplaDetalleServicioRemote) {
        this.proCedulaDiplaDetalleServicioRemote = proCedulaDiplaDetalleServicioRemote;
    }

    public ProNombreColumnaServicioRemote getProNombreColumnaServicioRemote() {
        return proNombreColumnaServicioRemote;
    }

    public void setProNombreColumnaServicioRemote(ProNombreColumnaServicioRemote proNombreColumnaServicioRemote) {
        this.proNombreColumnaServicioRemote = proNombreColumnaServicioRemote;
    }

    public ProValorNumericoServicioRemote getProValorNumericoServicioRemote() {
        return proValorNumericoServicioRemote;
    }

    public void setProValorNumericoServicioRemote(ProValorNumericoServicioRemote proValorNumericoServicioRemote) {
        this.proValorNumericoServicioRemote = proValorNumericoServicioRemote;
    }

    public ProValorTextoServicioRemote getProValorTextoServicioRemote() {
        return proValorTextoServicioRemote;
    }

    public void setProValorTextoServicioRemote(ProValorTextoServicioRemote proValorTextoServicioRemote) {
        this.proValorTextoServicioRemote = proValorTextoServicioRemote;
    }

    public ProEstructuraAmpliadaServicioRemote getProEstructuraAmpliadaServicioRemote() {
        return proEstructuraAmpliadaServicioRemote;
    }

    public void setProEstructuraAmpliadaServicioRemote(ProEstructuraAmpliadaServicioRemote proEstructuraAmpliadaServicioRemote) {
        this.proEstructuraAmpliadaServicioRemote = proEstructuraAmpliadaServicioRemote;
    }

    public AeAreaServicioRemote getAeAreaServicioRemote() {
        return aeAreaServicioRemote;
    }

    public void setAeAreaServicioRemote(AeAreaServicioRemote aeAreaServicioRemote) {
        this.aeAreaServicioRemote = aeAreaServicioRemote;
    }

    public AeGeneracionHistorialServicioRemote getAeGeneracionHistorialServicioRemote() {
        return aeGeneracionHistorialServicioRemote;
    }

    public void setAeGeneracionHistorialServicioRemote(AeGeneracionHistorialServicioRemote aeGeneracionHistorialServicioRemote) {
        this.aeGeneracionHistorialServicioRemote = aeGeneracionHistorialServicioRemote;
    }

    public AeGeneracionServicioRemote getAeGeneracionServicioRemote() {
        return aeGeneracionServicioRemote;
    }

    public void setAeGeneracionServicioRemote(AeGeneracionServicioRemote aeGeneracionServicioRemote) {
        this.aeGeneracionServicioRemote = aeGeneracionServicioRemote;
    }

    public AeRegistroBaseServicioRemote getAeRegistroBaseServicioRemote() {
        return aeRegistroBaseServicioRemote;
    }

    public void setAeRegistroBaseServicioRemote(AeRegistroBaseServicioRemote aeRegistroBaseServicioRemote) {
        this.aeRegistroBaseServicioRemote = aeRegistroBaseServicioRemote;
    }

    public MetCatalogoCiiuServicioRemote getMetCatalogoCiiuServicioRemote() {
        return metCatalogoCiiuServicioRemote;
    }

    public void setMetCatalogoCiiuServicioRemote(MetCatalogoCiiuServicioRemote metCatalogoCiiuServicioRemote) {
        this.metCatalogoCiiuServicioRemote = metCatalogoCiiuServicioRemote;
    }

    public MetCatalogoCiuoServicioRemote getMetCatalogoCiuoServicioRemote() {
        return metCatalogoCiuoServicioRemote;
    }

    public void setMetCatalogoCiuoServicioRemote(MetCatalogoCiuoServicioRemote metCatalogoCiuoServicioRemote) {
        this.metCatalogoCiuoServicioRemote = metCatalogoCiuoServicioRemote;
    }

    public ProCargaInfoFinServicioRemote getProCargaInfoFinServicioRemote() {
        return proCargaInfoFinServicioRemote;
    }

    public void setProCargaInfoFinServicioRemote(ProCargaInfoFinServicioRemote proCargaInfoFinServicioRemote) {
        this.proCargaInfoFinServicioRemote = proCargaInfoFinServicioRemote;
    }

    public ProTotalesInfoFinServicioRemote getProTotalesInfoFinServicioRemote() {
        return proTotalesInfoFinServicioRemote;
    }

    public void setProTotalesInfoFinServicioRemote(ProTotalesInfoFinServicioRemote proTotalesInfoFinServicioRemote) {
        this.proTotalesInfoFinServicioRemote = proTotalesInfoFinServicioRemote;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed"> 
    private void inicializaContextLookUp() {
        try {
            obtenerContextAdministracion();
            inicializaLookUPAdministracion();
            obtenerContextCaptura(propiedadesControlador.getPropertieValue("ipApppFin"));
            inicializaLookUPCaptura();
//            obtenerContextDistribucionTrabajo(propiedadesControlador.getPropertieValue("ipApppFin"));
//            inicializaLookUPDistribucionTrabajo();
            obtenerContextMetadato(propiedadesControlador.getPropertieValue("ipApppFin"));
            inicializaLookUPMetadato();
            obtenerContextSeguridad(propiedadesControlador.getPropertieValue("ipApppFin"));
            inicializaLookUPSeguridad();
//            obtenerContextMuestra(propiedadesControlador.getPropertieValue("ipApppFin"));
//            inicializaLookUPMuestra();
            /*obtenerContextGeografia(propiedadesControlador.getPropertieValue("ipApppFin"));
            inicializaLookUPGeografia();*/

            obtenerContextReportes(propiedadesControlador.getPropertieValue("ipApppFin"));
            inicializaLookUPReportes();

//            obtenerContextProceso(propiedadesControlador.getPropertieValue("ipApppFin"));
//            inicializaLookUPProceso();

            /*obtenerContextUtil(propiedadesControlador.getPropertieValue("ipApppFin"));
            inicializaLookUPUtil();*/

 /*obtenerContextR(propiedadesControlador.getPropertieValue("ipApppFin"));
            inicializaLookUPR();*/
            //cargarParametrosEnrefam();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPAdministracion() {
        try {
            admParametroGlobalServicioRemote = (AdmParametroGlobalServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmParametroGlobalServicio!" + AdmParametroGlobalServicioRemote.class.getName());
            admOperacionEstadisticaServicioRemote = (AdmOperacionEstadisticaServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmOperacionEstadisticaServicio!" + AdmOperacionEstadisticaServicioRemote.class.getName());
            admPeriodoServicioRemote = (AdmPeriodoServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmPeriodoServicio!" + AdmPeriodoServicioRemote.class.getName());
            admPersonalServicioRemote = (AdmPersonalServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmPersonalServicio!" + AdmPersonalServicioRemote.class.getName());
            admInstitucionServicioRemote = (AdmInstitucionServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmInstitucionServicio!" + AdmInstitucionServicioRemote.class.getName());
            admOrganigramaServicioRemote = (AdmOrganigramaServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmOrganigramaServicio!" + AdmOrganigramaServicioRemote.class.getName());
            admDispositivoServicioRemote = (AdmDispositivoServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmDispositivoServicio!" + AdmDispositivoServicioRemote.class.getName());
            admColumnaServicioRemote = (AdmColumnaServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmColumnaServicio!" + AdmColumnaServicioRemote.class.getName());
            admTablaServicioRemote = (AdmTablaServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmTablaServicio!" + AdmTablaServicioRemote.class.getName());
            admCorreoCabServicioRemote = (AdmCorreoCabServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmCorreoCabServicio!" + AdmCorreoCabServicioRemote.class.getName());
            admCorreoDetServicioRemote = (AdmCorreoDetServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmCorreoDetServicio!" + AdmCorreoDetServicioRemote.class.getName());
            admCorreoDestinatarioServicioRemote = (AdmCorreoDestinatarioServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmCorreoDestinatarioServicio!" + AdmCorreoDestinatarioServicioRemote.class.getName());
            admBaseDatosServicioRemote = (AdmBaseDatosServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmBaseDatosServicio!" + AdmBaseDatosServicioRemote.class.getName());

            parametroAES = admParametroGlobalServicioRemote.buscarXNombre("AES_ACCESS").getValor();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPCaptura() {
        try {
            captCargaControlEquipoServicioRemote = (CaptCargaControlEquipoServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptCargaControlEquipoServicio!" + CaptCargaControlEquipoServicioRemote.class.getName());
            captCargaControlServicioRemote = (CaptCargaControlServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptCargaControlServicio!" + CaptCargaControlServicioRemote.class.getName());
            captCabeceraServicioRemote = (CaptCabeceraServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptCabeceraServicio!" + CaptCabeceraServicioRemote.class.getName());
            captDetalleVServicioRemote = (CaptDetalleVServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptDetalleVServicio!" + CaptDetalleVServicioRemote.class.getName());
            captDetalleHServicioRemote = (CaptDetalleHServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptDetalleHServicio!" + CaptDetalleHServicioRemote.class.getName());
            captEstadoServicioRemote = (CaptEstadoServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptEstadoServicio!" + CaptEstadoServicioRemote.class.getName());
            captObservacionServicioRemote = (CaptObservacionServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptObservacionServicio!" + CaptObservacionServicioRemote.class.getName());
            captGeoreferenciaServicioRemote = (CaptGeoreferenciaServicioRemote) contextCaptura.lookup("sipe-captura-siac-ejb/CaptGeoreferenciaServicio!" + CaptGeoreferenciaServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPDistribucionTrabajo() {
        try {
            disFaseOperativoServicioRemote = (FaseOperativoServicioRemote) contextDistribucionTrabajo.lookup("sipe-distribuciontrabajo-ejb/FaseOperativoServicio!" + FaseOperativoServicioRemote.class.getName());
            disEquipoTrabajoServicioRemote = (EquipoTrabajoServicioRemote) contextDistribucionTrabajo.lookup("sipe-distribuciontrabajo-ejb/EquipoTrabajoServicio!" + EquipoTrabajoServicioRemote.class.getName());
            disEquipoTrabajoDetalleServicioRemote = (EquipoTrabajoDetalleServicioRemote) contextDistribucionTrabajo.lookup("sipe-distribuciontrabajo-ejb/EquipoTrabajoDetalleServicio!" + EquipoTrabajoDetalleServicioRemote.class.getName());
            disCargaTrabajoServicioRemote = (CargaTrabajoServicioRemote) contextDistribucionTrabajo.lookup("sipe-distribuciontrabajo-ejb/CargaTrabajoServicio!" + CargaTrabajoServicioRemote.class.getName());
            disCargaTrabajoDetalleServicioRemote = (CargaTrabajoDetalleServicioRemote) contextDistribucionTrabajo.lookup("sipe-distribuciontrabajo-ejb/CargaTrabajoDetalleServicio!" + CargaTrabajoDetalleServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPGeografia() {
        try {
            geoCoberturaServicioRemote = (GeoCoberturaServicioRemote) contextGeografia.lookup("sipe-geografia-ejb/GeoCoberturaServicio!" + GeoCoberturaServicioRemote.class.getName());
            geoDpaServicioRemote = (GeoDpaServicioRemote) contextGeografia.lookup("sipe-geografia-ejb/GeoDpaServicio!" + GeoDpaServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPMetadato() {
        try {
            metCatalogoCiiuServicioRemote = (MetCatalogoCiiuServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetCatalogoCiiuServicio!" + MetCatalogoCiiuServicioRemote.class.getName());
            metCatalogoCiuoServicioRemote = (MetCatalogoCiuoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetCatalogoCiuoServicio!" + MetCatalogoCiuoServicioRemote.class.getName());
            metCatalogoServicioRemote = (MetCatalogoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetCatalogoServicio!" + MetCatalogoServicioRemote.class.getName());
            metTipoCatalogoServicioRemote = (MetTipoCatalogoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetTipoCatalogoServicio!" + MetTipoCatalogoServicioRemote.class.getName());
            metVariableServicioRemote = (MetVariableServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetVariableServicio!" + MetVariableServicioRemote.class.getName());
            metConceptoServicioRemote = (MetConceptoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetConceptoServicio!" + MetConceptoServicioRemote.class.getName());
            metSeccionServicioRemote = (MetSeccionServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetSeccionServicio!" + MetSeccionServicioRemote.class.getName());
            metOperativoServicioRemote = (MetOperativoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetOperativoServicio!" + MetOperativoServicioRemote.class.getName());
            metSaltoServicioRemote = (MetSaltoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetSaltoServicio!" + MetSaltoServicioRemote.class.getName());
            metValidacionServicioRemote = (MetValidacionServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetValidacionServicio!" + MetValidacionServicioRemote.class.getName());
            metFormularioServicioRemote = (MetFormularioServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetFormularioServicio!" + MetFormularioServicioRemote.class.getName());
            metFormularioSeccionServicioRemote = (MetFormularioSeccionServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetFormularioSeccionServicio!" + MetFormularioSeccionServicioRemote.class.getName());

            cargarParametrosPrecenso();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void cargarParametrosPrecenso() {
        try {
            AdmOperacionEstadistica oe = admOperacionEstadisticaServicioRemote.obtenerAdmOperacionEstadisticaPorSigla("PREC");
            if (oe != null) {
                precCodigoOE = oe.getIdOpeEst();
            }
            MetFormulario form = metFormularioServicioRemote.obtenerMetFormularioPorCodificacion("precenso");
            if (form != null) {
                precCodigoForm = form.getIdFormulario();
            }
            MetSeccion secA = metSeccionServicioRemote.obtenerMetSeccionPorNombre("PAF1.S01");
            if (secA != null) {
                precCodigoSecAman = secA.getIdSeccion();
            }
            MetSeccion secB = metSeccionServicioRemote.obtenerMetSeccionPorNombre("PDF1.S01");
            if (secB != null) {
                precCodigoSecDisp = secB.getIdSeccion();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void cargarParametrosEnrefam() {

        try {
            AdmOperacionEstadistica ope = admOperacionEstadisticaServicioRemote.obtenerAdmOperacionEstadisticaPorSigla("ERM");
            if (ope != null) {
                enrefamCodigoOE = ope.getIdOpeEst();
            }
            MetFormulario form1 = metFormularioServicioRemote.obtenerMetFormularioPorCodificacion("enrefam1");
            if (form1 != null) {
                enrefamFrm1 = form1.getIdFormulario();
            }
            MetFormulario form2 = metFormularioServicioRemote.obtenerMetFormularioPorCodificacion("enrefam2");
            if (form2 != null) {
                enrefamFrm2 = form2.getIdFormulario();
            }
            MetFormulario form3 = metFormularioServicioRemote.obtenerMetFormularioPorCodificacion("enrefam3");
            if (form3 != null) {
                enrefamFrm3 = form3.getIdFormulario();
            }
            MetFormulario form4 = metFormularioServicioRemote.obtenerMetFormularioPorCodificacion("enrefam4");
            if (form4 != null) {
                enrefamFrm4 = form4.getIdFormulario();
            }
            MetVariable var1 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s3_1");
            if (var1 != null) {
                ocupacionEnrefam = var1.getIdVar();
            }
            MetVariable var2 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s4_9");
            if (var1 != null) {
                noEfectivaEnrefam = var2.getIdVar();
            }
            MetVariable var3 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s4_11");
            if (var3 != null) {
                codPersonaEnrefam = var3.getIdVar();
            }
            MetVariable var4 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s5_1");
            if (var4 != null) {
                estCivilEnrefam = var4.getIdVar();
            }
            MetVariable var5 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s5_2");
            if (var5 != null) {
                parejaEnrefam = var5.getIdVar();
            }
            MetVariable var6 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s5_7");
            if (var6 != null) {
                relacionEnrefam = var6.getIdVar();
            }
            MetVariable var7 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s2_1_1");
            if (var7 != null) {
                nombreEnrefam = var7.getIdVar();
            }
            MetVariable var8 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s2_7");
            if (var8 != null) {
                civilEnrefam = var8.getIdVar();
            }
            MetVariable var9 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s2_16_1");
            if (var9 != null) {
                educacionEnrefam = var9.getIdVar();
            }
            MetVariable var10 = metVariableServicioRemote.buscarVariablePorIdentificador("ERM_f1_s4_4");
            if (var10 != null) {
                efectivaUniFamiliaEnrefam = var10.getIdVar();
            }

        } catch (Exception ex) {
            Logger.getLogger(BaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void inicializaLookUPMuestra() {
        try {
            mueMuestraServicioRemote = (MueMuestraServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueMuestraServicio!" + MueMuestraServicioRemote.class.getName());
            mueMuestraDetalleServicioRemote = (MueMuestraDetalleServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueMuestraDetalleServicio!" + MueMuestraDetalleServicioRemote.class.getName());
            mueModeloMuestraServicioRemote = (MueModeloMuestraServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueModeloMuestraServicio!" + MueModeloMuestraServicioRemote.class.getName());
            mueMuestraDetalleServicioRemote = (MueMuestraDetalleServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueMuestraDetalleServicio!" + MueMuestraDetalleServicioRemote.class.getName());
            mueEmpresaServicioRemote = (MueEmpresaServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueEmpresaServicio!" + MueEmpresaServicioRemote.class.getName());
            mueHogarServicioRemote = (MueHogarServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueHogarServicio!" + MueHogarServicioRemote.class.getName());
            muePersonaServicioRemote = (MuePersonaServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MuePersonaServicio!" + MuePersonaServicioRemote.class.getName());
            mueViviendaServicioRemote = (MueViviendaServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueViviendaServicio!" + MueViviendaServicioRemote.class.getName());
            muePredioServicioRemote = (MuePredioServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MuePredioServicio!" + MuePredioServicioRemote.class.getName());
            mueAtributoExtraServicioRemote = (MueAtributoExtraServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/MueAtributoExtraServicio!" + MueAtributoExtraServicioRemote.class.getName());

            aeAreaServicioRemote = (AeAreaServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/AeAreaServicio!" + AeAreaServicioRemote.class.getName());
            aeGeneracionServicioRemote = (AeGeneracionServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/AeGeneracionServicio!" + AeGeneracionServicioRemote.class.getName());
            aeGeneracionHistorialServicioRemote = (AeGeneracionHistorialServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/AeGeneracionHistorialServicio!" + AeGeneracionHistorialServicioRemote.class.getName());
            aeRegistroBaseServicioRemote = (AeRegistroBaseServicioRemote) contextMuestra.lookup("sipe-muestra-ejb/AeRegistroBaseServicio!" + AeRegistroBaseServicioRemote.class.getName());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPProceso() {
        try {
            proActividadItemServicioRemote = (ProActividadItemServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProActividadItemServicio!" + ProActividadItemServicioRemote.class.getName());
            proActividadServicioRemote = (ProActividadServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProActividadServicio!" + ProActividadServicioRemote.class.getName());
            proFaseServicioRemote = (ProFaseServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProFaseServicio!" + ProFaseServicioRemote.class.getName());
            proObjetoInformacionServicioRemote = (ProObjetoInformacionServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProObjetoInformacionServicio!" + ProObjetoInformacionServicioRemote.class.getName());
            proSeguimientoArchivoServicioRemote = (ProSeguimientoArchivoServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProSeguimientoArchivoServicio!" + ProSeguimientoArchivoServicioRemote.class.getName());
            proSeguimientoRevisorServicioRemote = (ProSeguimientoRevisorServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProSeguimientoRevisorServicio!" + ProSeguimientoRevisorServicioRemote.class.getName());
            proSeguimientoServicioRemote = (ProSeguimientoServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProSeguimientoServicio!" + ProSeguimientoServicioRemote.class.getName());
            proSubprocesoServicioRemote = (ProSubprocesoServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProSubprocesoServicio!" + ProSubprocesoServicioRemote.class.getName());
            proIndicadorServicioRemote = (ProIndicadorServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProIndicadorServicio!" + ProIndicadorServicioRemote.class.getName());
            proSeguimientoEsigefDiplaServicioRemote = (ProSeguimientoEsigefDiplaServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProSeguimientoEsigefDiplaServicio!" + ProSeguimientoEsigefDiplaServicioRemote.class.getName());
            proEsigefServicioRemote = (ProEsigefServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProEsigefServicio!" + ProEsigefServicioRemote.class.getName());
            proDiplaServicioRemote = (ProDiplaServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProDiplaServicio!" + ProDiplaServicioRemote.class.getName());
            proBaseConsolidadaInicialServicioRemote = (ProBaseConsolidadaInicialServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProBaseConsolidadaInicialServicio!" + ProBaseConsolidadaInicialServicioRemote.class.getName());
            proCedulaDiplaServicioRemote = (ProCedulaDiplaServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProCedulaDiplaServicio!" + ProCedulaDiplaServicioRemote.class.getName());
            proCedulaDiplaDetalleServicioRemote = (ProCedulaDiplaDetalleServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProCedulaDiplaDetalleServicio!" + ProCedulaDiplaDetalleServicioRemote.class.getName());
            proNombreColumnaServicioRemote = (ProNombreColumnaServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProNombreColumnaServicio!" + ProNombreColumnaServicioRemote.class.getName());
            proValorNumericoServicioRemote = (ProValorNumericoServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProValorNumericoServicio!" + ProValorNumericoServicioRemote.class.getName());
            proValorTextoServicioRemote = (ProValorTextoServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProValorTextoServicio!" + ProValorTextoServicioRemote.class.getName());
            proEstructuraAmpliadaServicioRemote = (ProEstructuraAmpliadaServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProEstructuraAmpliadaServicio!" + ProEstructuraAmpliadaServicioRemote.class.getName());

            proCargaInfoFinServicioRemote = (ProCargaInfoFinServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProCargaInfoFinServicio!" + ProCargaInfoFinServicioRemote.class.getName());
            proTotalesInfoFinServicioRemote = (ProTotalesInfoFinServicioRemote) contextProceso.lookup("sipe-proceso-ejb/ProTotalesInfoFinServicio!" + ProTotalesInfoFinServicioRemote.class.getName());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPReportes() {
        try {
            repoReporteServicioRemote = (RepoReporteServicioRemote) contextReportes.lookup("sipe-reportes-siac-ejb/RepoReporteServicio!" + RepoReporteServicioRemote.class.getName());
            repoSubreporteServicioRemote = (RepoSubreporteServicioRemote) contextReportes.lookup("sipe-reportes-siac-ejb/RepoSubreporteServicio!" + RepoSubreporteServicioRemote.class.getName());
            repoColumnaServicioRemote = (RepoColumnaServicioRemote) contextReportes.lookup("sipe-reportes-siac-ejb/RepoColumnaServicio!" + RepoColumnaServicioRemote.class.getName());
            actualizaBDServicioRemote = (ActualizaBDServicioRemote) contextReportes.lookup("sipe-reportes-siac-ejb/ActualizaBDServicio!" + ActualizaBDServicioRemote.class.getName());
            repoProcedimientoServicioRemote = (RepoProcedimientoServicioRemote) contextReportes.lookup("sipe-reportes-siac-ejb/RepoProcedimientoServicio!" + RepoProcedimientoServicioRemote.class.getName());
            repoFiltroServicioRemote = (RepoFiltroServicioRemote) contextReportes.lookup("sipe-reportes-siac-ejb/RepoFiltroServicio!" + RepoFiltroServicioRemote.class.getName());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPSeguridad() {
        try {
            segAplicacionServicioRemote = (SegAplicacionServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegAplicacionServicio!" + SegAplicacionServicioRemote.class.getName());
            segRolServicioRemote = (SegRolServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegRolServicio!" + SegRolServicioRemote.class.getName());
            segRolUsuarioServicioRemote = (SegRolUsuarioServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegRolUsuarioServicio!" + SegRolUsuarioServicioRemote.class.getName());
            segSesionActivaServicioRemote = (SegSesionActivaServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegSesionActivaServicio!" + SegSesionActivaServicioRemote.class.getName());
            segUsuarioServicioRemote = (SegUsuarioServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegUsuarioServicio!" + SegUsuarioServicioRemote.class.getName());
            segRolPermisoServicioRemote = (SegRolPermisoServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegRolPermisoServicio!" + SegRolPermisoServicioRemote.class.getName());
            segPaginaServicioRemote = (SegPaginaServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegPaginaServicio!" + SegPaginaServicioRemote.class.getName());
            segPermisoServicioRemote = (SegPermisoServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegPermisoServicio!" + SegPermisoServicioRemote.class.getName());
            segAuditoriaCabServicioRemote = (SegAuditoriaCabServicioRemote) contextSeguridad.lookup("sipe-seguridad-siac-ejb/SegAuditoriaCabServicio!" + SegAuditoriaCabServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPUtil() {
        try {
            utilImportarExportarServicioRemote = (UtilImportarExportarServicioRemote) contextUtil.lookup("sipe-util-ejb/UtilImportarExportarServicio!" + UtilImportarExportarServicioRemote.class.getName());
            System.out.println(utilImportarExportarServicioRemote.iniciaEJB());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPR() {
        try {
            ejecutaRRemote = (EjecutaRRemote) contextUtil.lookup("sipe-r-ejb/EjecutaR!" + EjecutaRRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public InitialContext obtenerContextAdministracion() {
        try {
            if (contextAdministracion == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
                jdniPro3.put(Context.PROVIDER_URL, "http-remoting://" + propiedadesControlador.getPropertieValue("ipAPP"));
                jdniPro3.put(Context.SECURITY_PRINCIPAL, propiedadesControlador.getPropertieValue("userAPP"));
                jdniPro3.put(Context.SECURITY_CREDENTIALS, propiedadesControlador.getPropertieValue("claveApp"));
                jdniPro3.put("jboss.naming.client.ejb.context", true);
                jdniPro3.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false);
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", true);
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", false);
                contextAdministracion = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            //addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextAdministracion;
    }

    public InitialContext obtenerContextCaptura(String numSer) {
        try {
            if (contextCaptura == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Captura" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Captura" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Captura" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextCaptura = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            //addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextCaptura;
    }

    public InitialContext obtenerContextDistribucionTrabajo(String numSer) {
        try {
            if (contextDistribucionTrabajo == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_DisTrab" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_DisTrab" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_DisTrab" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextDistribucionTrabajo = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            //addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextDistribucionTrabajo;
    }

    public InitialContext obtenerContextGeografia(String numSer) {
        try {
            if (contextGeografia == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Geografia" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Geografia" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Geografia" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextGeografia = new InitialContext(jdniPro3);

            }
        } catch (Exception ex) {
            addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextGeografia;
    }

    public InitialContext obtenerContextMetadato(String numSer) {
        try {
            if (contextMetadato == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Metadato" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Metadato" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Metadato" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextMetadato = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            //addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextMetadato;
    }

    public InitialContext obtenerContextMuestra(String numSer) {
        try {
            if (contextMuestra == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Muestra" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Muestra" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Muestra" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextMuestra = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextMuestra;
    }

    public InitialContext obtenerContextProceso(String numSer) {
        try {
            if (contextProceso == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Proceso" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Proceso" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Proceso" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextProceso = new InitialContext(jdniPro3);

            }
        } catch (Exception ex) {
            addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextMuestra;
    }

    public InitialContext obtenerContextReportes(String numSer) {
        try {
            if (contextReportes == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Reportes" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Reportes" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Reportes" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextReportes = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextReportes;
    }

    public InitialContext obtenerContextSeguridad(String numSer) {
        try {
            if (contextSeguridad == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Seguridad" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Seguridad" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Seguridad" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextSeguridad = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextSeguridad;
    }

    public InitialContext obtenerContextUtil(String numSer) {
        try {
            if (contextUtil == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Util" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Util" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Util" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextUtil = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextSeguridad;
    }

    public InitialContext obtenerContextR(String numSer) {
        try {
            if (contextUtil == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_R" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_R" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_R" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextUtil = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            addErrorMessage(" ");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextSeguridad;
    }

    public void getContextName() {
        /*System.out.println("Get context name: " + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        System.out.println("Get context name2: " + FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());*/
        nombreAplicacion = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath().replace("/", "");
        //return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }

    public String getUrl() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":" + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort();
    }

    public String getProfundidad() {
        profundidadPagina = getUrl() + "/";
        /*System.out.println(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getContextName());
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestContentType());
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo());
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestScheme());
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath());*/
        String[] pagina = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath().split("/");
        String prof = "";
        for (int i = 0; i < pagina.length; i++) {
            prof = prof + "../";
        }
        //System.out.println(prof);
        profundidadPagina = prof;
        nombreTema = prof + urlTemaParcial;
        return profundidadPagina;
    }

    public void llenaDatosAplicacion() {
        try {
            segAplicacion = segAplicacionServicioRemote.buscarXNombre(nombreAplicacion);

            JSONObject aplicacionConfiguracion = new JSONObject(segAplicacion.getConfiguraciones());
            datosGenerales.setPaginahomeCompleto(segAplicacion.getUrl());
            datosGenerales.setPaginahome(aplicacionConfiguracion.getString("paginainicio"));
            datosGenerales.setImageninec("imagenes/INEC.png");
            datosGenerales.setTitulo(segAplicacion.getAlias());
            datosGenerales.setDetalleSistema(segAplicacion.getDescripcion());
            datosGenerales.setImagenproyecto("imagenes/" + nombreAplicacion + ".png");

            datosGenerales.setDerechosreservados(aplicacionConfiguracion.getString("derechosreservados"));
            datosGenerales.setDireccionresponsable(aplicacionConfiguracion.getString("direccionresponsable"));
            datosGenerales.setImagenpie("imagenes/INEC.png");
            datosGenerales.setSesioncaducada(false);

            datosGenerales.setSesioncaducadaheader("Sesión Caducada");
            datosGenerales.setSesioncaducadawidgetVar("sessionTimeOutDialog");
            datosGenerales.setSesioncaducadatexto("Su Sesión ha caducado favor pulse sobre el botón para volver a acceder a la aplicación");
            datosGenerales.setSesioncaducadaboton("Regresar");

            datosGenerales.setUsuarionologueadoheader("Usuario sin Loguear");
            datosGenerales.setUsuarionologueadotexto("Usted no se ha logueado favor pulse sobre el botón para volver a acceder a la aplicación");

            datosGenerales.setDialogwidgetVar("statusDialog");
            datosGenerales.setDialogGifwidgetVar("dlgValidando");
            if (aplicacionConfiguracion.has("imagenespera")) {
                datosGenerales.setDialogimagen("imagenes/" + aplicacionConfiguracion.getString("imagenespera") + ".gif");
                datosGenerales.setDialogGifimagen("imagenes/" + aplicacionConfiguracion.getString("imagenespera") + ".gif");
            } else {
                datosGenerales.setDialogimagen("imagenes/INEC_load.gif");
                datosGenerales.setDialogGifimagen("imagenes/INEC_load.gif");
            }
            datosGenerales.setDialogheader("Espere");
            datosGenerales.setDialogGifheader("Validando...");
            datosGenerales.setDialogGiffooter("Por favor espere...");
            datosGenerales.setBackgroundImage("imagenes/INEC.png");
            String paginaInicio = aplicacionConfiguracion.getString("paginainicio");
            try {
                Map<String, Object> campos = new LinkedHashMap<String, Object>();
                campos.put("url", paginaInicio);
                SegPagina pagina = segPaginaServicioRemote.buscarPorCampos(campos, "").get(0);
                datosGenerales.setIdPagina(pagina.getIdPag());
            } catch (Exception e) {
                datosGenerales.setIdPagina(2);
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retorna la session http.
     *
     * @return session
     */
    /*public HttpSession getSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        return session;
    }*/
    /**
     * Se encarga de ejecutar una redireccion.
     *
     * @param url url de destino
     * @throws IOException en caso de no poder hacer la redireccion
     */
    public void redirect(final String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public void redireccionarAPagina(String carpeta, String pagina) {
        try {
            if (carpeta.equals("")) {
                redirect("/" + nombreAplicacion + "/paginas/" + pagina + ".xhtml");
            } else {
                redirect("/" + nombreAplicacion + "/paginas/" + carpeta + "/" + pagina + ".xhtml");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void redireccionarAPagina(String pagina) {
        try {
            redirect("/" + nombreAplicacion + pagina);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void addWarningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public void mensajeErrorComponente(String idElemento, String detalle) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(idElemento, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", detalle));
    }

    public void conseguirTema() {
        try {

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            urlTemaParcial = request.getParameter("valTema");
            //String bandera = request.getParameter("valBandera");
            /*System.err.println("tema " + tema);
            System.err.println("bandera " + bandera);*/
            //ifTema = bandera;
            //nombreTema = tema;
            //datosGenerales.setTema(tema);
            setNombreTema(profundidadPagina + urlTemaParcial);
            //System.err.println("profundidadPagina+tema " + profundidadPagina + urlTemaParcial);
            PrimeFaces reqCtx = PrimeFaces.current();
            reqCtx.ajax().addCallbackParam("returnedValue", nombreTema);
            


        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public String obtenerIp() {
        //String ipCliente = getRequest().getRemoteAddr();
        String ipCliente = getRequest().getHeader("X-FORWARDED-FOR");
        if (ipCliente == null) {
            ipCliente = getRequest().getRemoteAddr();
        }
        return ipCliente;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException(
                    "No se pudo recuperar HttpServletRequest");
        }
        return request;
    }

    public String getRequestHeader(String nombre) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return externalContext.getRequestHeaderMap().get(nombre);
    }

    //</editor-fold>
    public String getParametroAES() {
        return parametroAES;
    }

    public void setParametroAES(String parametroAES) {
        this.parametroAES = parametroAES;
    }

}
