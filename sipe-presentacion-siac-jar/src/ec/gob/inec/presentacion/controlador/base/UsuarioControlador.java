/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.base;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.ejb.utils.GeneradorClave;
import ec.gob.inec.ejb.utils.ReflexionEntidad;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;

import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegSesionActiva;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import ec.gob.inec.ejb.utils.SegUsuarioServicioLdap;
import ec.gob.inec.seguridad.ejb.entidades.SegPermiso;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
//import org.primefaces.model.menu.DefaultMenuItem;
//import org.primefaces.model.menu.DefaultMenuModel;
//import org.primefaces.model.menu.DefaultSubMenu;
//import org.primefaces.model.menu.MenuItem;
//import org.primefaces.model.menu.MenuModel;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author rmoreano
 */
@ManagedBean()
@SessionScoped
public class UsuarioControlador extends BaseControlador implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioControlador.class.getName());

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    @ManagedProperty("#{baseControlador}")
    private BaseControlador baseControlador;
    
    private String usuario;
    private String contrasena;
    private SegUsuario usuarioActual;
    private MenuModel menuModel;
    private boolean visibleMenu;
    
    private List<SegAplicacion> aplicacionesList;
    private MenuModel menuModelAplicacion;
    private boolean visibleMenuAplicacion;
    
    private SegSesionActiva sesionActiva;
    private String sessionId;
    private int redireccionado;
    private String nombreBrowser;
    private String nombreSO;
    private int verMensajeErrorLdap;
    private String correoUsuario;
    private SegPagina paginaActual;
    private String nuevaClave;
    private String confirmacionNuevaClave;
    private String aliasReporte;
    
    private List<SegRol> listaRolesDelUsuario;
    private List<SegPermiso> listaPermisosDelUsuario;
    
    private boolean logeadoEnLdap;
    private String semillaAES;//Semilla usada para encriptar o desencriptar.

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public UsuarioControlador() {
    }
    
    @PostConstruct
    public void init() {
        // System.out.println("Inicio init Usuario Controlador");
        //cambiaURLWS();
        try {
            AdmParametroGlobal objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS");
            semillaAES = objParametros.getValor();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "No puede recuperar la semillaAES.", ex);
        }
        
        redireccionado = 0;
        visibleMenu = false;
        visibleMenuAplicacion = false;
        listaRolesDelUsuario = new ArrayList<>();
        listaPermisosDelUsuario = new ArrayList<>();
        sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
        if (baseControlador.getNombreAplicacion().equals(baseControlador.getPropiedadesControlador().getPropertieValue("appini"))) {
            guardaSession();
        } else {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (!request.getParameterMap().isEmpty()) {
                try {
                    if (request.getParameterMap().containsKey("a")) {
                        String idSession = request.getParameter("a");
                        String idUsuario = request.getParameter("b");
                        String password = request.getParameter("c");
                        //System.out.println("paras: " + idSession + ":::" + idUsuario);
                        sesionActiva = baseControlador.getSegSesionActivaServicioRemote().buxcarXIdentificador(idSession);
                        System.out.println("paras: " + sesionActiva);
                        if (sesionActiva.getCodUsu().equals(Integer.valueOf(idUsuario))) {
                            redireccionado = 1;
                            usuarioActual = baseControlador.getSegUsuarioServicioRemote().buscarxID(Integer.valueOf(idUsuario));
                            usuario = usuarioActual.getNombre();
                            contrasena = ReflexionEntidad.desencripta("AES", password, semillaAES);
                            LOGGER.info("contrasena=******");
                            inicioSesion();
                        } else {
                            LOGGER.info("Cod Usuario no esta registrado.");
                        }
                    } else {
                        guardaSession();
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, null, e);
                }
            } else {
                guardaSession();
            }
        }
        
        //System.out.println("UsuarioControlador id session: " + sessionId);
        //insertar la session
        // System.out.println("Fin init Navegacion Controlador");
    }
//</editor-fold> 
    //<editor-fold desc="get and set" defaultstate="collapsed">

    public String getCorreoUsuario() {
        return correoUsuario;
    }
    
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public SegUsuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public void setUsuarioActual(SegUsuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    public MenuModel getMenuModel() {
        return menuModel;
    }
    
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }
    
    public BaseControlador getBaseControlador() {
        return baseControlador;
    }
    
    public void setBaseControlador(BaseControlador baseControlador) {
        this.baseControlador = baseControlador;
    }
    
    public boolean isVisibleMenu() {
        return visibleMenu;
    }
    
    public void setVisibleMenu(boolean visibleMenu) {
        this.visibleMenu = visibleMenu;
    }
    
    public List<SegAplicacion> getAplicacionesList() {
        return aplicacionesList;
    }
    
    public void setAplicacionesList(List<SegAplicacion> aplicacionesList) {
        this.aplicacionesList = aplicacionesList;
    }
    
    public MenuModel getMenuModelAplicacion() {
        return menuModelAplicacion;
    }
    
    public void setMenuModelAplicacion(MenuModel menuModelAplicacion) {
        this.menuModelAplicacion = menuModelAplicacion;
    }
    
    public boolean isVisibleMenuAplicacion() {
        return visibleMenuAplicacion;
    }
    
    public void setVisibleMenuAplicacion(boolean visibleMenuAplicacion) {
        this.visibleMenuAplicacion = visibleMenuAplicacion;
    }
    
    public String getNombreBrowser() {
        return nombreBrowser;
    }
    
    public void setNombreBrowser(String nombreBrowser) {
        this.nombreBrowser = nombreBrowser;
    }
    
    public String getNombreSO() {
        return nombreSO;
    }
    
    public void setNombreSO(String nombreSO) {
        this.nombreSO = nombreSO;
    }
    
    public SegPagina getPaginaActual() {
        return paginaActual;
    }
    
    public void setPaginaActual(SegPagina paginaActual) {
        this.paginaActual = paginaActual;
    }
    
    public String getNuevaClave() {
        return nuevaClave;
    }
    
    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }
    
    public String getConfirmacionNuevaClave() {
        return confirmacionNuevaClave;
    }
    
    public void setConfirmacionNuevaClave(String confirmacionNuevaClave) {
        this.confirmacionNuevaClave = confirmacionNuevaClave;
    }
    
    public String getAliasReporte() {
        return aliasReporte;
    }
    
    public void setAliasReporte(String aliasReporte) {
        this.aliasReporte = aliasReporte;
    }
    
    public List<SegRol> getListaRolesDelUsuario() {
        return listaRolesDelUsuario;
    }
    
    public void setListaRolesDelUsuario(List<SegRol> listaRolesDelUsuario) {
        this.listaRolesDelUsuario = listaRolesDelUsuario;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void validaNavegacionPagina() {
        esPaginaPrivada(FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath(), true);
    }

    /**
     * Verifica si una pagina es privada o no.
     *
     * @param pagina URL de la pagina web
     * @param navegaALogin True si quiere que navege al Login en caso de que el
     * usuario no esta autenticado, y False caso contrario
     * @return True si es privado y Fase si es publico.
     */
    private boolean esPaginaPrivada(String pagina, boolean navegaALogin) {
        List<String> paginasPublicas = new ArrayList();
        paginasPublicas.add("/index.xhtml");
        if (paginasPublicas.stream().filter(x -> x.contains(pagina)).count() > 0) {
            LOGGER.log(Level.INFO, "Es página publica, no valida autenticacion a: " + pagina);
            return false;
        }
        if (usuarioActual == null) {
            LOGGER.log(Level.INFO, "Usuario no autenticado.");
            if (navegaALogin) {
                LOGGER.log(Level.INFO, "Cerrando la sesion y redireccionando a login.");
                cerrarSesion();
                return true;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    
    public void guardaSession() {
        try {
            sesionActiva = new SegSesionActiva();
            sesionActiva.setFechahoraReg(new Date());
            sesionActiva.setIdentificador(sessionId);
            baseControlador.getSegSesionActivaServicioRemote().crearSesionActiva(sesionActiva);
            sesionActiva = baseControlador.getSegSesionActivaServicioRemote().buxcarXIdentificador(sessionId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    boolean autenticacionLdap() {
        logeadoEnLdap = false;
        try {
            AdmParametroGlobal objParametros = new AdmParametroGlobal();
            objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("LDAP_PROVIDER");
            String paramIpSerLdap = objParametros.getValor();
            objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("LDAP_PORT");
            String paramPuertoLdap = objParametros.getValor();
            objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("LDAP_PROVIDER_CREDENTIAL");
            String paramUserLdapGenerico = objParametros.getDescripcion();
            String paramPassLdapGeneico = objParametros.getValor();
            objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("LDAP_BASE_DOMINIO");
            String paramBaseDominio = objParametros.getValor();
            objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("LDAP_FILTRO_DOMINIO");
            String paramFiltroDominio = objParametros.getValor();
            objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("LDAP_USER_DOMINIO");
            String paramFiltroUserDominio = objParametros.getValor();
            SegUsuarioServicioLdap objLdap = new SegUsuarioServicioLdap();
            objLdap.autenticacionPermanete(paramUserLdapGenerico, paramPassLdapGeneico, paramIpSerLdap, Integer.parseInt(paramPuertoLdap));
            // baseControlador.getSegUsuarioServicioLdapRemote().autenticacionPermanete(paramUserLdapGenerico, paramPassLdapGeneico, paramIpSerLdap, Integer.parseInt(paramPuertoLdap));
            switch (objLdap.buscarExistenciaUsuario(paramFiltroDominio, paramBaseDominio, usuario, contrasena, paramIpSerLdap, Integer.parseInt(paramPuertoLdap), paramFiltroUserDominio)) {
                case 4: // Usuario Logueado Autenticado
                    logeadoEnLdap = true;
                    break;
                case 5: // Usuario Logueado Credenciales no validas error 5 - Usuario o contraseña incorrectos
                    break;
                case 6: // Parametros de conexion no validos - Parámetros de acceso al servidor incorrectos error 6
                    break;
                case 7: // Usuarion consta en la base de datos pero no esta en el dominio                                       
                    break;
                case 8: // Contraseña incorrrecta
                    break;
            }
            objParametros = null;
            objLdap.desconectaLdap();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        System.out.println("Existe ldap:" + logeadoEnLdap);
        return logeadoEnLdap;
    }
    
    public boolean autenticacionBaseLocal() {
        boolean logeadoEnBase = false;
        try {
            AdmParametroGlobal objParametros = new AdmParametroGlobal();
            objParametros = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS");
            String parametroAES = objParametros.getValor();
            String campoencriptado = ReflexionEntidad.encripta("AES", contrasena, parametroAES);
            SegUsuario objUsuario = new SegUsuario();
            objUsuario = baseControlador.getSegUsuarioServicioRemote().buscarxUsuarioContrasena(usuario, campoencriptado);
            try {
                if (objUsuario.getIdUsuario() != null) {
                    logeadoEnBase = true;
                }
            } catch (Exception e) {
                logeadoEnBase = false;
            }
            objUsuario = null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        // System.out.println("Existe Local:" + logeadoEnBase);
        return logeadoEnBase;
    }
    
    public void inicioSesion() {
        try {
            if (redireccionado == 0) {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                usuario = request.getParameter("val1");
                contrasena = request.getParameter("val2");
            }
            if (autenticacionBaseLocal()) {
                SegUsuario objUsuario = baseControlador.getSegUsuarioServicioRemote().buscarPorUsuario(usuario);
                //Si el usuario se encuentra en estado Pendiente, se muestra un modal panel para obligar al usuario que cambie la clave.
                if (objUsuario.getCodEstadoUsuario().getAlias().equals("PEND")) {
                    //  System.out.println("El usuario " + usuario + " tiene el estado PENDIENTE. Cambio de clave......");
                    PrimeFaces.current().executeScript("cambiarclave();");
//                    RequestContext.getCurrentInstance().execute("cambiarclave();");
                    
                    return;
                }
                //------Asigna rol permiso-----               
                cargarPermisoRol();
            } else if (autenticacionLdap()) {
                LOGGER.info("Finaliza LDAP.");
                cargarPermisoRol();
            } else {
                addWarningMessage("Usuario o clave Incorrectos");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }


        /*listaProyectos = new ArrayList<>();
        lstNomProyecto = new ArrayList<>();
        lstCodProyecto = new ArrayList<>();
        try {
            
            List<AnyTypeArray> lstProy;
            lstProy = cacheController.CatfindbyUsuario(user);
            //lstProy = cacheController.CatfindbyUsuario("rsevicuna");
            //lstProy = findByUsuario("rsevicuna");

            lstProy.stream().forEach((cargaObject) -> {
                listaProyectos.add(cargaObject.getItem().toArray());
            });
            usuario = user;
            contrasena = password;
            //int log = lOGINECCOntroller.buscarUsuarioLDAP(usuario,contrasena);
            //System.err.println("loginec:" + log);
            if (lOGINECCOntroller.buscarUsuarioLDAP(usuario,contrasena) == 4) {
                if (!listaProyectos.isEmpty()) {
                    visibleMenu = true;
                    redirect(getContextName() + "/paginas/indice.xhtml");
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Usuario", usuario);    
                    //****this.getSession().setAttribute("Usuario", usuario);
                    datosGenerales.setUsuarioLogueado("Usuario: " + usuario);
                    //datosGenerales.setExisteusuario(false);
                    logeado = true;

                    irPagina(listaProyectos);
                    cargarMenu();
                } else {
                    addWarningMessage("El Usuario ingresado no esta relacionado a ningún proyecto actual");
                }
            } else if (lOGINECCOntroller.buscarUsuarioLDAP(usuario,contrasena) == 5) {
                addWarningMessage("El Usuario o la Contraseña son incorrectos");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }*/
    }
    
    public void cargarPermisoRol() {
        LOGGER.info("cargarPermisoRol()");
        try {
            // List<SegRol> lstRol = baseControlador.getSegRolServicioRemote().listarRolXUsuarioContrasenia(usuario, contrasena);
            //System.out.println("tamaño: " + lstRol.size());
            List<SegRol> lstRol = baseControlador.getSegRolServicioRemote().listarEjecutarConsulta("listaRolPorUsuario", Arrays.asList(usuario));
            //  System.out.println("tamaño: " + lstRol.size());
//            List<SegPagina> lisPag = baseControlador.getSegPaginaServicioRemote().listarEjecutarConsulta("listarPaginaPorRol", Arrays.asList(usuario, "PAG_VER"));
//            System.out.println("tamaño: " + lisPag.size());
            if (lstRol.size() > 0) {
                definirListaRolesyPermisosDelUsuario(lstRol);
                usuarioActual = baseControlador.getSegUsuarioServicioRemote().buscarPorUsuario(usuario);
                try {
                    sesionActiva.setCodUsu(usuarioActual.getIdUsuario());
                    LOGGER.info("usuarioActual.getIdUsuario()=" + usuarioActual.getIdUsuario());
                    baseControlador.getSegSesionActivaServicioRemote().editarSesionActiva(sesionActiva);
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, null, e);
                }
                obtieneAplicaciones();
                construirMenu();
                visibleMenu = true;
                visibleMenuAplicacion = true;
                baseControlador.redireccionarAPagina(baseControlador.getDatosGenerales().getPaginahome());
            } else {
                addWarningMessage("El usuario ingresado no tiene un rol asignado");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cierra la sesion del usuario logueado.
     */
    public void cerrarSesion() {
        try {
            System.out.println("cerrar session");
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.FALSE);
            if (session != null) {
                session.invalidate();
                baseControlador.redirect("/" + baseControlador.getNombreAplicacion() + "/index.xhtml?faces-redirect=true&redirected=true");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void construirMenu() {
        try {
            menuModel = new DefaultMenuModel();
            /*Map<String, Object> campos = new LinkedHashMap<>();
            campos.put("codApl", baseControlador.getSegAplicacion());
            campos.put("nivel", Short.valueOf("0"));*/
            //  List<SegPagina> segPaginaList = baseControlador.getSegPaginaServicioRemote().buscarPorCampos(campos, "ordnImprime");
            List<SegPagina> segPaginaList;
            segPaginaList = baseControlador.getSegPaginaServicioRemote().listarEjecutarConsulta("listarPaginaPorRolPermiso", Arrays.asList(usuario, "PAG_VER", baseControlador.getSegAplicacion()));
            /*DefaultMenuItem itemInicio = new DefaultMenuItem("Inicio");
            itemInicio.setIcon("fa fa-home");
            //itemInicio.setCommand("#{baseControlador.redireccionarAPagina('captura','cap_cabecera')}");
            itemInicio.setCommand("#{baseControlador.redireccionarAPagina('" + baseControlador.getDatosGenerales().getPaginahome() + "')}");
            menuModel.addElement(itemInicio);*/
            if (segPaginaList != null) {
                if (!segPaginaList.isEmpty()) {
                    for (SegPagina segPagina : segPaginaList) {
                        construirSubMenu(segPagina, menuModel, 0);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param segPaginaPar
     * @param menu
     * @param nivel
     */
    public void construirSubMenu(SegPagina segPaginaPar, Object menu, int nivel) {
        try {
            UIParameter iParameter = null;
            /*Map<String, Object> campos = new LinkedHashMap<>();
            campos.put("codPadre", segPaginaPar);
            List<SegPagina> segPaginaList = baseControlador.getSegPaginaServicioRemote().buscarPorCampos(campos, "ordnImprime");*/
            List<SegPagina> segPaginaList;
            segPaginaList = baseControlador.getSegPaginaServicioRemote().listarEjecutarConsulta("listarPaginaPorPermisoCodPadre", Arrays.asList(usuario, "PAG_VER", segPaginaPar));
            boolean subMenu = false;
            if (segPaginaList != null) {
                if (!segPaginaList.isEmpty()) {
                    subMenu = true;
                }
            }
            if (subMenu) {
                int nivelS = nivel + 1;
                
                 DefaultSubMenu defaultSubMenu = DefaultSubMenu.builder()
                .label(segPaginaPar.getNombre())
                .build();
                       
//                DefaultSubMenu defaultSubMenu = new DefaultSubMenu(segPaginaPar.getNombre());
                for (SegPagina segPagina : segPaginaList) {
                    construirSubMenu(segPagina, defaultSubMenu, nivelS);
                }
                /*if (nivel == 0) {
                    ((DefaultMenuModel) menu).addElement(defaultSubMenu);
                }*/
                if (menu.getClass().getCanonicalName().equals(DefaultMenuModel.class.getCanonicalName())) {
                    ((DefaultMenuModel) menu).getElements().add(defaultSubMenu);
                } else {
                    ((DefaultSubMenu) menu).getElements().add(defaultSubMenu);
                }
            } else {
//                DefaultMenuItem itemS = new DefaultMenuItem(segPaginaPar.getNombre());
                    DefaultMenuItem itemS = DefaultMenuItem.builder()
                                  .value(segPaginaPar.getNombre()).build();

                JSONObject atributos = new JSONObject(segPaginaPar.getAtributo());
                itemS.setIcon(atributos.getString("icono"));
                itemS.setCommand("#{usuarioControlador.redireccionarAPagina}");
                //itemS.setCommand("#{baseControlador.redireccionarAPagina('" + segPaginaPar.getUrl() + "')}");
                iParameter = new UIParameter();
                iParameter.setValue(segPaginaPar.getIdPag());
                iParameter.setName("x");
                itemS.setParam("x", segPaginaPar.getIdPag());
                itemS.setOnclick("reportesAyuda([{name:'reporte',value:'" + segPaginaPar.getAlias() + "'}])");
                //itemS.getChildren().add(iParameter);
                if (menu.getClass().getCanonicalName().equals(DefaultMenuModel.class.getCanonicalName())) {
                    ((DefaultMenuModel) menu).getElements().add(itemS);
                } else {
                    ((DefaultSubMenu) menu).getElements().add(itemS);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void redireccionarAPagina(ActionEvent event) {
        try {
            MenuItem menuItem = ((MenuActionEvent) event).getMenuItem();
            Map<String, Object> campos = new LinkedHashMap<String, Object>();
            campos.put("idPag", Integer.valueOf(menuItem.getParams().get("x").get(0)));
            paginaActual = baseControlador.getSegPaginaServicioRemote().buscarPorCampos(campos, "").get(0);
            // System.out.println("pruebaa: " + menuItem.getParams().get("x").get(0));
            baseControlador.redireccionarAPagina(paginaActual.getUrl());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que envia por correo la nueva clave generada automaticamente. Es
     * usado en la opcion ¿Has olvidado tu contraseña?
     */
    public void recuperarClave() {
        try {
            SegUsuario segUsuario = baseControlador.getSegUsuarioServicioRemote().buscarPorCorreo(correoUsuario);
            if (segUsuario == null) {
                addErrorMessage("Usted no tiene registrado una dirección de correo.");
                PrimeFaces.current().executeScript("PF('recuperarClaveDlg').show();");
                
                return;
            }
            String nombreCompleto = segUsuario.getCodPersonal().getPrimerNombre() + ((segUsuario.getCodPersonal().getSegundoNombre() == null) ? "" : segUsuario.getCodPersonal().getSegundoNombre()) + " " + segUsuario.getCodPersonal().getPrimerApellido() + ((segUsuario.getCodPersonal().getSegundoApellido() == null) ? "" : segUsuario.getCodPersonal().getSegundoApellido());
            
            String claveGenerada = GeneradorClave.getClave();
            String claveEncriptada = encriptarClave(claveGenerada);
            MetCatalogo pendiente = baseControlador.getMetCatalogoServicioRemote().buscarCatalogoXAlias("PEND");
            MetCatalogo modifica = baseControlador.getMetCatalogoServicioRemote().buscarCatalogoXAlias("PAG_MODIF");
            segUsuario.setCodAuditoriaCab(getCodAuditoria(paginaActual == null ? 0 : paginaActual.getIdPag(), modifica.getIdCatalogo()));
            segUsuario.setCodEstadoUsuario(pendiente);
            segUsuario.setClave(claveEncriptada);
            baseControlador.getSegUsuarioServicioRemote().editarUsuario(segUsuario);
            Map<String, String> params = new LinkedHashMap<>();
            params.put("APLICACION", "SIPE");
            String formatoNormal = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(new Date());
            params.put("CONT_FECHA", "Fecha: " + formatoNormal);
            params.put("CONT_NOMBRE", "Nombres: " + nombreCompleto);
            params.put("CONT_CLAVE", "Nueva clave: " + claveGenerada);
            baseControlador.getAdmCorreoDestinatarioServicioRemote().enviarCorreo("RECUPERAR_CLAVE", correoUsuario, params);
            encerarDireccionCorreo();
            addSuccessMessage("Su clave fue enviado al correo.");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage("Error al procesar su información.");
            PrimeFaces.current().executeScript("recuperarclave()");
            

        }
    }

    /**
     * Método usado para cambiar la clave de un usuario.
     */
    public void cambiarClave() {
        try {
            SegUsuario objUsuario = baseControlador.getSegUsuarioServicioRemote().buscarPorUsuario(usuario);
            String claveEncriptada = encriptarClave(nuevaClave);
            MetCatalogo activo = baseControlador.getMetCatalogoServicioRemote().buscarCatalogoXAlias("USU_ACT");
            MetCatalogo modifica = baseControlador.getMetCatalogoServicioRemote().buscarCatalogoXAlias("PAG_MODIF");
            objUsuario.setCodAuditoriaCab(getCodAuditoria(paginaActual == null ? 0 : paginaActual.getIdPag(), modifica.getIdCatalogo()));
            objUsuario.setCodEstadoUsuario(activo);
            objUsuario.setClave(claveEncriptada);
            baseControlador.getSegUsuarioServicioRemote().editarUsuario(objUsuario);
            encerarDireccionCorreo();
            addSuccessMessage("Su clave fue cambiado satisfactoriamente.");
            cargarPermisoRol();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage("Error al procesar su información.");
            PrimeFaces.current().executeScript("cambiarclave()");
            
        }
    }
    
    private String encriptarClave(String clave) throws Exception {
        AdmColumna admColumnaClave = baseControlador.getAdmColumnaServicioRemote().consultarPorTablaYColumna("seguridad.seg_usuario", "clave");
        String claveEncriptada = "";
        if (admColumnaClave != null && admColumnaClave.getEncr()) {//Encripta
            String parametroAES = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
            claveEncriptada = ReflexionEntidad.encripta(admColumnaClave.getCodTipoEncr().getNombre(), clave, parametroAES);
        } else {
            claveEncriptada = clave;
        }
        return claveEncriptada;
    }
    
    public void encerarDireccionCorreo() {
        correoUsuario = "";
    }
    
    public void obtieneAplicaciones() {
        try {
            aplicacionesList = baseControlador.getSegAplicacionServicioRemote().listarEjecutarConsulta("listarAplPorUsuario", Arrays.asList(usuario, "PAG_VER"));
            //  System.out.println("Numero de palicaciones:" + aplicacionesList.size());
            /*menuModelAplicacion = new DefaultMenuModel();

            for (SegAplicacion segAplicacion : aplicacionesList) {
                DefaultMenuItem itemS = new DefaultMenuItem(segAplicacion.getAlias());
                itemS.setIcon(segAplicacion.getNombre());
                itemS.setCommand("#{baseControlador.redirect(\"" + segAplicacion.getUrl() + "\")}");
                itemS.setUrl("http://" + baseControlador.getUrl() + segAplicacion.getUrl() + "?a=" + sesionActiva.getIdentificador() + "&b=" + usuarioActual.getIdUsuario() + "");
                menuModelAplicacion.addElement(itemS);
            }*/
        } catch (Exception e) {
            Logger.getLogger(BaseControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public String getSessionActivaURL() {
        try {
            String campoencriptado = ReflexionEntidad.encripta("AES", contrasena, semillaAES);
            return "?a=" + sesionActiva.getIdentificador() + "&b=" + usuarioActual.getIdUsuario() + "&c=" + campoencriptado;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la URL de la sesion activa.", e);
            return "";
        }
    }

    /**
     * Recupera el código de auditoria en el formato correcto para registrar la
     * auditoria.
     *
     * @param codigoPagina Código de la página que se encuentra actualmente
     * activa.
     * @param int Código del evento <strong>Ej. INSERTADO=0, BORRADO=1,
     * ACTUALIZADO=2, CARGADO=3, DESCARGADO=4</strong>
     * @return Objeto String, con el código de auditoria
     * <strong>Ej.
     * 1|1|127.0.0.1|dvaldas|hiivi-8Mu0D0dGBH5MrZQAVBDt_OJT8ymSxmNfV1|Firefox|Windows
     * 7</strong>
     */
    public String getCodAuditoria(int codigoPagina, int codigoEvento) {
        StringBuilder respuesta = new StringBuilder();
        respuesta.append(codigoPagina).append("|");
        respuesta.append(codigoEvento).append("|");
        respuesta.append(baseControlador.obtenerIp()).append("|");
        respuesta.append((usuarioActual == null) ? "SIN USUARIO" : usuarioActual.getNombre()).append("|");
        respuesta.append(sessionId).append("|");
        respuesta.append(getBrowserName()).append("|");
        respuesta.append(getSO());
        LOGGER.log(Level.INFO, "Código para auditoria: codAuditoria[" + respuesta.toString() + "]");
        return respuesta.toString();
    }

    /**
     * Recupera el nombre del broser
     *
     * @return Obketo String, con el nombre del browser.
     */
    public String getBrowserName() {
        String userAgent = baseControlador.getRequestHeader("User-Agent");
        for (Map.Entry<String, String> entry : baseControlador.getBrowsers().entrySet()) {
            if (userAgent.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        String respDesconocido = "DESCONOCIDO_NAV";
        baseControlador.getBrowsers().put(userAgent, respDesconocido);
        guardaCatalogo(respDesconocido, userAgent, "TIP_NAV");
        return respDesconocido;
    }

    /**
     * Recupera el nombre del Sistema Operativo.
     *
     * @return Objeto String, con el nombre del sistema operativo.
     */
    public String getSO() {
        String userAgent = baseControlador.getRequestHeader("User-Agent");
        String so = "";
        boolean soConocido = false;
        for (Map.Entry<String, String> entry : baseControlador.getSo().entrySet()) {
            if (userAgent.contains(entry.getKey())) {
                soConocido = true;
                so = entry.getValue();
            }
        }
        if (soConocido) {
            return so;
        } else {
            String respDesconocido = "DESCONOCIDO_SO";
            //baseControlador.getSo().put(userAgent, respDesconocido);
            //guardaCatalogo(respDesconocido, userAgent, "TIP_SO");
            return respDesconocido;
        }
        
    }
    
    private void guardaCatalogo(String nombre, String valor, String aliasTipoCatalogo) {
        try {
            MetTipoCatalogo metTipoCatalogo = baseControlador.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias(aliasTipoCatalogo);
            MetCatalogo medCatalogo = new MetCatalogo();
            medCatalogo.setCodTipoCatalogo(metTipoCatalogo);
            medCatalogo.setNombre(nombre);
            medCatalogo.setAlias(nombre);
            medCatalogo.setDescripcion(valor);
            medCatalogo.setValor(valor.substring(0, 50));
            medCatalogo.setOrden(BigInteger.valueOf(100));
            medCatalogo.setNivel(1);
            medCatalogo.setEstadoLogico(true);
            medCatalogo.setCodAuditoriaCab("0|37|" + baseControlador.obtenerIp() + "|" + ((usuarioActual == null) ? "SIN USUARIO" : usuarioActual.getNombre()) + "|" + sessionId + "|DESCONOCIDO|DESCONOCIDO");
            baseControlador.getMetCatalogoServicioRemote().crearCatalogo(medCatalogo);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void navegacionReportes() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String reporte = (String) map.get("reporte");
        //System.out.println("---->Pagina---" + reporte);
        aliasReporte = reporte;
    }
    
    public HttpSession getSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        return session;
    }
    
    private void definirListaRolesyPermisosDelUsuario(List<SegRol> lstRol) {
        try {
            listaRolesDelUsuario = lstRol;
            //pendiente definir listadepermisos
            //listaPermisosDelUsuario=

        } catch (Exception ex) {
            Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean usuarioTieneRol(String aliasRol) {
        boolean b = false;
        for (SegRol r : listaRolesDelUsuario) {
            if (r.getAlias().equals(aliasRol)) {
                b = true;
            }
        }
        return b;
    }
    
    public boolean usuarioTienePermiso(String aliasPermiso) {
        boolean b = false;
        for (SegPermiso p : listaPermisosDelUsuario) {
            if (p.getAlias().equals(aliasPermiso)) {
                b = true;
            }
        }
        return b;
    }
    //</editor-fold>
}
