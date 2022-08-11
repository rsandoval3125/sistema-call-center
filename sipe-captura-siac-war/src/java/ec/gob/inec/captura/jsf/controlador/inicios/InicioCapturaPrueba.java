/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.inicios;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.captura.clases.CaptFilaH;
import ec.gob.inec.captura.clases.CaptInfoMuestra;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleV;
import ec.gob.inec.captura.jsf.controlador.modulos.BaseCapturaControlador;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import ec.gob.inec.muestra.ejb.entidades.MueMuestraDetalle;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;


/**
 *
 * @author dgarcia
 */
@ManagedBean
@javax.faces.bean.ViewScoped
public class InicioCapturaPrueba {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(InicioCapturaPrueba.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{baseCapturaControlador}")
    private BaseCapturaControlador bcc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vc;

    private List<CaptCargaControl> listaCargaPendiente;
    private List<CaptCabecera> listaFormulariosCreados;
    private Integer numElementos;
    private CaptCabecera cabeceraActual;
    private CaptCargaControl cargaActual;
    private CaptInfoMuestra infoMuestra;
    private String aliasOpeEstActual;
    private String paramCongloGuardado;

    private Integer numHombres, numMujeres;

    /*CAMPOS PARA PARAMETROS DE BUSQUEDA*/
    private Integer paramIdOpeEst;
    private List<AdmOperacionEstadistica> listaOpeEst;
    private final String BUSQ_OPEEST = "codMueDet.codMuestra.codOpeEst.idOpeEst";
    private Integer paramIdPeriodo;
    private List<AdmPeriodo> listaPeriodo;
    private final String BUSQ_PERIODO = "codMueDet.codMuestra.codPeriodo.idPeriodo";

    private Integer paramIdOperativo;
    private List<MetOperativo> listaOperativoPorOpeEstyPeriodo;
    private final String BUSQ_OPERATIVO = "codFormulario.codOperativo.idOperativo";

    private Integer paramIdFormulario;
    private List<MetFormulario> listaFormulariosPorOperativo;
    private final String BUSQ_FORMULARIO = "codFormulario.idFormulario";

    private String paramTipo;
    private List<SelectItem> listaTipos;
    private final String BUSQ_TIPO = "tipo";

    private String paramZonal;
    private List<SelectItem> listaZonal;
    private final String BUSQ_ZONAL = "zonal";

    private String paramClave;
    private final String BUSQ_CLAVE = "clave";

    private String paramControl1;
    private final String BUSQ_CONTROL1 = "control1";

    private String paramControl2;
    private final String BUSQ_CONTROL2 = "control2";

    private Map<String, Object> parametrosCargaPendiente;
    private Map<String, Object> parametrosCabecerasCreadas;

    private List<Object[]> listaContactos;

    private List<SelectItem> listaResultadoEntrevista;
    private List<SelectItem> listaResultadoEntrevistaOtraRazon;
    private List<CaptCargaControlEquipo> listaCargaControlEquipo;

    private CaptCabecera cabeceraGuardada;
    /*atributos a ingresar*/
    private String atrPeriodo;
    private String atrCalle;
    private String atrLocalidad;
    private String atrLote;
    private String atrBloque;
    private String atrPatio;
    private String atrPiso;
    private String atrCasa;
    private String atrDepar;
    private String info1;
    private String info2;
    private String info3;
    private String nuevoHogar;
    private String numHogares;//varMulti 

    /* campos de control*/
    private String conNumMiembrosHog;
    private String conNumNoMienbrosHog;
    private String conNomJefeHogar;
    private int activaOtroCual;
    private CaptCabecera cabeceraNF = new CaptCabecera();
    private String codFrmN;
    private Integer numMiembro;
    private String reCreada;
    private MueMuestraDetalle objMueDetalleActual;
    //---Parametros para EnemduJulio---//
    private String fonoConvencional;
    private String fonoCelular;
    private String conglomeradoSelected;
    private String conglomeradoInput;
    private List<String> listaConglomerados;
    private String nombreRol;
    private String observaciones;
    private List<SelectItem> listaFormaCaptura;
    private List<SelectItem> listaFormaCapturaTelef;
    private String cedEncuestador;
    private String cedSupervisor;
    private String cedCritico;
    private String cedDigitador;
    private String cedValidador;
    private String piso;
    private String calle;
    private String nuMunicipio;
    private String nomJefeHogar;
    private String nomInformante;
    private String codInformante;
    private String vivReemplaza;
    private String conFechaVisita1;
    private CaptCargaControl cargaBusqueda;
    private AdmPeriodo perJornada;

    private String estadoForm;

    private String resulEntrevistaOtraRazon;

    private List<Integer> listarCodigosTrabajoPorUsuarioyOperativo(String usuario, Integer codOpe) {
        List<Integer> lst = new ArrayList<>();
        try {
            String sql = "SELECT id_car_tra,usuario FROM distribucion.v_precenso_distribucion_trabajo where usuario='" + usuario + "' and cod_operativo=" + codOpe;
            List<Object[]> result = bc.getMetVariableServicioRemote().listarDiccionarioVariablesCorporativas(sql);
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    lst.add(Integer.valueOf(String.valueOf(result.get(i)[0])));
                }
            } else {
                lst.add(0);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    /*CAMPOS PARA PARAMETROS PARA ACTUALIZAR INFORMACIÓN*/
 /*CAMPOS PARA PARAMETROS PARA ACTUALIZAR INFORMACIÓN*/
    private List<CaptCargaControl> listaFormulariosPorHogar;

    private CaptCargaControl cargaCarControlHog;

    // cambio vic 21/05/2020
    private List<Object[]> lstJornadas;
    private Integer jornadaSelect;
    private List<Object[]> lstConglomerados;
    private Integer jornadaGuardada;

    /**
     * Creates a new instance of InicioCapturaEnemdu2107
     */
    public InicioCapturaPrueba() {
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" METODOS ">
    @PostConstruct
    public void inicializar() {
        try {
            estadoForm = "";

            paramIdOpeEst = 0;
            aliasOpeEstActual = "";
            Integer idOe = (Integer) uc.getSession().getAttribute("id_ope_est");
            if (idOe != null) {
                paramIdOpeEst = idOe;
            }
            //System.out.println("paramid:" + paramIdOpeEst);
            numHombres = 0;
            numMujeres = 0;
            numElementos = 0;
            listaCargaPendiente = new ArrayList<>();
            listaFormulariosCreados = new ArrayList<>();
            listaCargaControlEquipo = new ArrayList<>();
            cabeceraActual = new CaptCabecera();
            cabeceraGuardada = new CaptCabecera();
            cargaActual = new CaptCargaControl();
            cargaActual.setCodFormulario(new MetFormulario());
            infoMuestra = new CaptInfoMuestra();
            objMueDetalleActual = new MueMuestraDetalle();
            cargarListasIniciales();
            inicializarParametrosBusqueda();
            inicializarParametrosActualizar();  //aumenta  
            // cambio vic 02/07/2020   
            Integer idOper = (Integer) uc.getSession().getAttribute("id_operativo");
            if (idOper != null) {
                paramIdOperativo = idOper;
            }
            //System.out.println("paramid_paramIdOperativo: " + paramIdOperativo);
            paramIdOperativo = 2;
            // cambio vic 21/05/2020
            lstJornadas = bc.getCaptCargaControlServicioRemote().listarEjecutarConsultaObj("listarJornadasAsignadasXUsuario", Arrays.asList(uc.getUsuarioActual().getIdUsuario(), paramIdOperativo));
            //listaConglomerados = bc.getCaptCargaControlServicioRemote().listarConglomeradosPorUsuario(uc.getUsuarioActual().getIdUsuario());
            listaContactos = new ArrayList<>();
            String tipoCifrado = "AES";
            bc.getActualizaBDServicioRemote().pasarParametrosEncriptacion(tipoCifrado, bc.getParametroAES());
            //---Parametros Enemdu
            fonoConvencional = "";
            fonoCelular = "";
            observaciones = "";
            cedEncuestador = "";
            cedSupervisor = "";
            cedCritico = "";
            cedDigitador = "";
            cedValidador = "";
            piso = "";
            calle = "";
            nuMunicipio = "";
            nomJefeHogar = "";
            nomInformante = "";
            codInformante = "";
            vivReemplaza = "";
            conFechaVisita1 = "";
            if (uc.usuarioTieneRol("ROL_ENCUE")) {
                nombreRol = "ENCUESTADOR";
            } else if (uc.usuarioTieneRol("ROL_SUPER")) {
                nombreRol = "SUPERVISOR";
            } else if (uc.usuarioTieneRol("ROL_REVIS")) {
                nombreRol = "REVISOR";
            } else if (uc.usuarioTieneRol("ROL_VALID")) {
                nombreRol = "VALIDADOR";
            } else if (uc.usuarioTieneRol("COOR_TEC_NAC")) {
                nombreRol = "COORTECNAC";
            } else if (uc.usuarioTieneRol("RES_ZON")) {
                nombreRol = "RESPZONAL";
            } else if (uc.usuarioTieneRol("ROL_OBS")) {
                nombreRol = "OBSERVADOR";
            } else if (uc.usuarioTieneRol("ROL_RESP_CRIT")) {
                nombreRol = "RESPCRIT";
            }
            //System.out.println("nombreRol: "+nombreRol);
            conglomeradoInput = "";
            String conglomerado = (String) uc.getSession().getAttribute("conglo");
            //System.out.println("valor "+conglomerado);
            // cambio vic 21/05/2020
            Integer jornada = (Integer) uc.getSession().getAttribute("jornada");
            //System.out.println("valor2 "+jornada);
            if (conglomerado != null) {
                // cambio vic 21/05/2020
                jornadaSelect = jornada;
                jornadaGuardada = jornada;
                conglomeradoSelected = conglomerado;
                paramCongloGuardado = conglomerado;
                buscarCargaPendienteG();
                buscarCabecerasCreadasG();
                buscarConglomerados();
                buscarInformacion();
                //cambio DG 21062021
                buscarInformacionValidador(conglomerado);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void definirOperacionEstadisticaEnSesion() {
        try {
            uc.getSession().setAttribute("id_ope_est", paramIdOpeEst);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void inicializarParametrosBusqueda() {
        try {
            paramIdOpeEst = null;
            paramIdPeriodo = null;
            paramIdOperativo = null;
            paramIdFormulario = null;
            paramTipo = null;
            paramZonal = null;
            paramClave = null;
            paramControl1 = null;  //aumenta
            paramControl2 = null; //aumenta
            parametrosCargaPendiente = new HashMap<String, Object>();
            parametrosCabecerasCreadas = new HashMap<String, Object>();
            listaCargaPendiente = new ArrayList<>();
            listaFormulariosCreados = new ArrayList<>();
            listaContactos = new ArrayList<>();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void inicializarParametrosActualizar() {
        cargaBusqueda = new CaptCargaControl();
    }

    public void listarOperativosPorOpeyPer() {
        try {
            if (paramIdOpeEst != null) {
                if (paramIdPeriodo != null) {
                    listaOperativoPorOpeEstyPeriodo = new ArrayList<>();
                    MetOperativo ope = bc.getMetOperativoServicioRemote().listarOperativosPorCodPeriodoYCodOpeEst(paramIdPeriodo, paramIdOpeEst);
                    if (ope != null) {
                        listaOperativoPorOpeEstyPeriodo.add(ope);
                    }
                } else {
                    listaOperativoPorOpeEstyPeriodo = bc.getMetOperativoServicioRemote().listarOperativosPorCodOpeEst(paramIdOpeEst);
                }

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarFormulariosPorOperativo() {
        try {
            if (paramIdOperativo != null) {
                listaFormulariosPorOperativo = bc.getMetFormularioServicioRemote().listarFormulariosPorOperativo(paramIdOperativo);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void buscarCargaPendiente() {
        try {
            if (uc.usuarioTieneRol("ROL_VALID") || uc.usuarioTieneRol("COOR_TEC_NAC") || uc.usuarioTieneRol("RES_ZON") || uc.usuarioTieneRol("ROL_OBS") || uc.usuarioTieneRol("ROL_RESP_CRIT") || uc.usuarioTieneRol("ADMIN_TOTAL_SIPE")) {
                conglomeradoSelected = conglomeradoInput;
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("clave", conglomeradoSelected);
                parametros.put("estadoFormulario", 1);
                parametros.put("estadoLogico", true);
                listaCargaPendiente = bc.getCaptCargaControlServicioRemote().listarCargaPendientePorParametros(parametros);
            } else {

                // cambio vic 21/05/2020
//            Map<String, Object> parametros = new HashMap<String, Object>();
//            parametros.put("clave", conglomeradoSelected);
//            parametros.put("estadoFormulario", 1);
//            parametros.put("estadoLogico", true);
//           listaCargaPendiente = bc.getCaptCargaControlServicioRemote().listarCargaPendientePorParametros(parametros);
                listaCargaPendiente = bc.getCaptCargaControlServicioRemote().listarEjecutarConsulta("listarCargaPendientePorParametros", Arrays.asList(uc.getUsuarioActual(), jornadaSelect, conglomeradoSelected));
                if (listaCargaPendiente.isEmpty() && listaFormulariosCreados.isEmpty()) {
                    if (jornadaGuardada == null) {
                    } else {
                        bc.addWarningMessage("No se ha encontrado carga pendiente.");
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarSupervisaroCriticado() {
        try {
            if (uc.usuarioTieneRol("ROL_SUPER")) {
                cabeceraActual.setEstado("VS");
                cabeceraActual.setInfo5(uc.getUsuarioActual().getNombre());
                bc.addWarningMessage("Formulario actualizado");
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
            }
            if (uc.usuarioTieneRol("ROL_REVIS")) {// && cabeceraActual.getEstado().equals("VS")ACT:D:A
                cabeceraActual.setEstado("VC");
                cabeceraActual.setInfo6(uc.getUsuarioActual().getNombre());
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
                bc.addWarningMessage("Formulario actualizado");
            }
//            else if (uc.usuarioTieneRol("ROL_REVIS") && !cabeceraActual.getEstado().equals("VS")) {
//                bc.addErrorMessage("La boleta aun no ha sido supervisada");
//            } else if (uc.usuarioTieneRol("ROL_REVIS") && cabeceraActual.getEstado().equals("VC")) {
//                bc.addErrorMessage("La boleta ya fue revisada y criticada");
//            }
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.executeScript("PF('dlgInicioCapturaEnemCovid').hide();");
            buscarInformacion();
            requestContext.ajax().update(":frmIniCap:pnlCargaPendiente");
            requestContext.ajax().update(":frmIniCap:pnlCabeceras");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void buscarCargaPendienteG() {
        try {
            // cambio vic 21/05/2020
//            Map<String, Object> parametros = new HashMap<String, Object>();
//            parametros.put("clave", paramCongloGuardado);
//            parametros.put("estadoFormulario", 1);
//            parametros.put("estadoLogico", true);
//            listaCargaPendiente = bc.getCaptCargaControlServicioRemote().listarCargaPendientePorParametros(parametros);
            listaCargaPendiente = bc.getCaptCargaControlServicioRemote().listarEjecutarConsulta("listarCargaPendientePorParametros", Arrays.asList(uc.getUsuarioActual(), jornadaGuardada, paramCongloGuardado));
            if (listaCargaPendiente.isEmpty() && listaFormulariosCreados.isEmpty()) {
                if (!nombreRol.equals("VALIDADOR") && !nombreRol.equals("COORTECNAC") && !nombreRol.equals("OBSERVADOR") && !nombreRol.equals("RESPZONAL")) {
                    bc.addWarningMessage("No se ha encontrado carga pendiente.");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void buscarCabecerasCreadasG() {
        try {

            Map<String, Object> parametros = new HashMap<String, Object>();
            if (uc.usuarioTieneRol("ROL_VALID") || uc.usuarioTieneRol("COOR_TEC_NAC")) { //OJO ANALIZAR VISTA DE REGISTROS PARA VALIDADOR
                parametros.put("clave", paramCongloGuardado);
                parametros.put("estadoLogico", true);
                listaFormulariosCreados = bc.getCaptCabeceraServicioRemote().listarCabecerasPorParametros(parametros);
            } else {
                // cambio vic 21/05/2020
//                parametros.put("clave", paramCongloGuardado);
//                parametros.put("estadoLogico", true);
//                listaFormulariosCreados = bc.getCaptCabeceraServicioRemote().listarCabecerasPorParametros(parametros);
                listaFormulariosCreados = bc.getCaptCabeceraServicioRemote().listarEjecutarConsulta("listarCabecerasPorParametros", Arrays.asList(uc.getUsuarioActual(), jornadaGuardada, paramCongloGuardado));
            }

            if (listaFormulariosCreados.isEmpty()) {
                if (!nombreRol.equals("VALIDADOR") && !nombreRol.equals("COORTECNAC") && !nombreRol.equals("OBSERVADOR") && !nombreRol.equals("RESPZONAL")) {
                    bc.addWarningMessage("No se han encontrado formularios creados");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void buscarCabecerasCreadas() {
        try {

            Map<String, Object> parametros = new HashMap<String, Object>();
            if (uc.usuarioTieneRol("ROL_VALID") || uc.usuarioTieneRol("COOR_TEC_NAC") || uc.usuarioTieneRol("RES_ZON") || uc.usuarioTieneRol("ROL_OBS") || uc.usuarioTieneRol("ROL_RESP_CRIT") || uc.usuarioTieneRol("ADMIN_TOTAL_SIPE")) {//OJO ANALIZAR VISTA DE REGISTROS PARA VALIDADOR
                parametros.put("clave", conglomeradoSelected);
                parametros.put("estadoLogico", true);
                //parametros.put("codCarCon.codFormulario.codificacion", "PRECENSO_F1_2021");
                parametros.put("codCarCon.codFormulario.codificacion", "FPRUEBA_F1_2022");
                listaFormulariosCreados = bc.getCaptCabeceraServicioRemote().listarCabecerasPorParametros(parametros);
            } else {
                // cambio vic 21/05/2020
//                parametros.put("clave", conglomeradoSelected);
//                parametros.put("estadoLogico", true);
//                listaFormulariosCreados = bc.getCaptCabeceraServicioRemote().listarCabecerasPorParametros(parametros);
                listaFormulariosCreados = bc.getCaptCabeceraServicioRemote().listarEjecutarConsulta("listarCabecerasPorParametros", Arrays.asList(uc.getUsuarioActual(), jornadaSelect, conglomeradoSelected));
            }

            if (listaFormulariosCreados.isEmpty()) {
                bc.addWarningMessage("No se han encontrado formularios creados");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void buscarInformacion() {
        try {
            if (uc.usuarioTieneRol("ROL_VALID") || uc.usuarioTieneRol("COOR_TEC_NAC") || uc.usuarioTieneRol("RES_ZON") || uc.usuarioTieneRol("ROL_OBS") || uc.usuarioTieneRol("ROL_RESP_CRIT") || uc.usuarioTieneRol("ADMIN_TOTAL_SIPE")) {
                conglomeradoSelected = conglomeradoInput;
            }
            if (conglomeradoSelected == null) {
                bc.addWarningMessage("Seleccione un conglomerado");
            } else {
                buscarCabecerasCreadas();
                buscarCargaPendiente();
            }
            limpiarObjetosEnemdu();
            // cambio vic 21/05/2020
            uc.getSession().setAttribute("jornada", jornadaSelect);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void buscarInformacionValidador(String conglo) {
        try {
            if (uc.usuarioTieneRol("ROL_VALID") || uc.usuarioTieneRol("ROL_OBS") || uc.usuarioTieneRol("ROL_RESP_CRIT")) {
                conglomeradoSelected = conglo;
            }
            if (conglomeradoSelected == null) {
                //bc.addWarningMessage("Seleccione un conglomerado");
            } else {
                buscarCabecerasCreadas();
                buscarCargaPendiente();
            }
            limpiarObjetosEnemdu();
            // cambio vic 21/05/2020
            uc.getSession().setAttribute("jornada", jornadaSelect);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    // cambio vic 21/05/2020
    public void buscarConglomerados() {
        try {
            if (jornadaSelect != null) {
                lstConglomerados = bc.getCaptCargaControlServicioRemote().listarEjecutarConsultaObj("listarConglomeradosXJornadaUsuario", Arrays.asList(uc.getUsuarioActual().getIdUsuario(), jornadaSelect));
            } else {
                System.out.println("jornadaSelect nulo");
                lstConglomerados = new ArrayList<Object[]>();
            }
            listaCargaPendiente = new ArrayList<>();
            listaFormulariosCreados = new ArrayList<>();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void irAFormulario(CaptCabecera cab) {
        try {
            String url = "/sipe-captura-siac-war/formularios/" + cab.getCodCarCon().getCodFormulario().getCodificacion() + ".xhtml";
            bc.redirect(url);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cargarListasIniciales() {
        try {
            listaOpeEst = bc.getAdmOperacionEstadisticaServicioRemote().listarTodosActivos();
            listaPeriodo = bc.getAdmPeriodoServicioRemote().listarTodosActivos();
            SelectItem itAGC = new SelectItem("CONGL", "Agrupación por Conglomerado");
            SelectItem itAGM = new SelectItem("MANZA", "Agrupación por Manzana");
            SelectItem itAGS = new SelectItem("SECDI", "Agrupación por Sector Disperso");
            SelectItem itCZN = new SelectItem("N", "CZN");
            SelectItem itCZL = new SelectItem("L", "CZL");
            SelectItem itCZC = new SelectItem("C", "CZC");
            SelectItem itCZS = new SelectItem("S", "CZS");
            listaTipos = new ArrayList<>();
            listaTipos.add(itAGC);
            listaTipos.add(itAGM);
            listaTipos.add(itAGS);
            listaZonal = new ArrayList<>();
            listaZonal.add(itCZN);
            listaZonal.add(itCZL);
            listaZonal.add(itCZC);
            listaZonal.add(itCZS);
            listaRE();
            listaREor();
            listaFC();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listaRE() {
        //F1
        listaResultadoEntrevista = new ArrayList<>();
        listaResultadoEntrevista.add(new SelectItem("01", "1.- Efectiva"));
        listaResultadoEntrevista.add(new SelectItem("02", "2.- Rechazo"));
        listaResultadoEntrevista.add(new SelectItem("03", "3.- Nadie en Casa"));
        listaResultadoEntrevista.add(new SelectItem("04", "4.- Viv.Temporal"));
        listaResultadoEntrevista.add(new SelectItem("05", "5.- Viv.Desocupada"));
        listaResultadoEntrevista.add(new SelectItem("06", "6.- Viv.Construcción"));
        listaResultadoEntrevista.add(new SelectItem("07", "7.- Viv.Inhabitable o Destruida"));
        listaResultadoEntrevista.add(new SelectItem("08", "8.- Viv.Convertida en Negocio"));
        listaResultadoEntrevista.add(new SelectItem("09", "9.- Otra Razon"));
        listaResultadoEntrevista.add(new SelectItem("10", "10.-No Contesta"));
        listaResultadoEntrevista.add(new SelectItem("11", "11.-Apagado Sin Cobertura"));
        listaResultadoEntrevista.add(new SelectItem("12", "12.-Número Equivocado"));
        listaResultadoEntrevista.add(new SelectItem("13", "13.-Número Invalido"));
    }

    public void listaREor() {
        //F1
        listaResultadoEntrevistaOtraRazon = new ArrayList<>();
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("1", "1. VIV. ABSORBIDA"));
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("2", "2. NO EXISTE LA VIVIENDA"));
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("3", "3. NO EXISTE EDIFICIO"));
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("4", "4. LOTE VACÍO"));
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("5", "5. BODEGA"));
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("7", "7. UTILIZADA EN OTRA RONDA"));
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("8", "8. INFORME NO IDÓNEO"));
        listaResultadoEntrevistaOtraRazon.add(new SelectItem("9", "9. FONDO ROTATIVO"));
    }

    public void listaFC() {
        listaFormaCaptura = new ArrayList<>();
        listaFormaCaptura.add(new SelectItem("01", "1.- Presencial / Cara a cara"));
        listaFormaCaptura.add(new SelectItem("02", "2.- Llamada Telefónica"));

        listaFormaCapturaTelef = new ArrayList<>();
        listaFormaCapturaTelef.add(new SelectItem("01", "2.1.- Con formulario"));
        listaFormaCapturaTelef.add(new SelectItem("02", "2.2.- SIPE"));
    }

    public void seleccionarCarga(CaptCargaControl cc) {
        limpiarObjetosEnemdu();
        //System.out.println("Jor: " + jornadaSelect);
        try {
            cabeceraActual = new CaptCabecera();
            cargaActual = bc.getCaptCargaControlServicioRemote().obtenerCargaControlPorId(cc.getIdCarCon());
            objMueDetalleActual = bc.getMueMuestraDetalleServicioRemote().buscarMuestraDetActiva(cc.getCodMueDet().getIdMueDet());
            cargarControlEquipo(cargaActual);
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.executeScript("PF('dlgInicioCapturaEnemDiciembre').show();");
            

            
            
            numElementos = 0;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /*
    Cargar contacto
     */
    public void cargarContactos(CaptCargaControl cc) {
        try {

            if ((cc.getCodMueDet().getVar24() != null && !cc.getCodMueDet().getVar24().isEmpty()) && (cc.getCodMueDet().getVar25() != null && !cc.getCodMueDet().getVar25().isEmpty())) {
                Object[] obj = new Object[2];
                obj[0] = cc.getCodMueDet().getVar24();
                obj[1] = cc.getCodMueDet().getVar25();
                listaContactos.add(obj);
            }
            if ((cc.getCodMueDet().getVar26() != null && !cc.getCodMueDet().getVar26().isEmpty()) && (cc.getCodMueDet().getVar27() != null && !cc.getCodMueDet().getVar27().isEmpty())) {
                Object[] obj1 = new Object[2];
                obj1[0] = cc.getCodMueDet().getVar26();
                obj1[1] = cc.getCodMueDet().getVar27();
                listaContactos.add(obj1);
            }
            if ((cc.getCodMueDet().getVar28() != null && !cc.getCodMueDet().getVar28().isEmpty()) && (cc.getCodMueDet().getVar29() != null && !cc.getCodMueDet().getVar29().isEmpty())) {
                Object[] obj2 = new Object[2];
                obj2[0] = cc.getCodMueDet().getVar28();
                obj2[1] = cc.getCodMueDet().getVar29();
                listaContactos.add(obj2);
            }
            if ((cc.getCodMueDet().getVar30() != null && !cc.getCodMueDet().getVar30().isEmpty()) && (cc.getCodMueDet().getVar31() != null && !cc.getCodMueDet().getVar31().isEmpty())) {
                Object[] obj3 = new Object[2];
                obj3[0] = cc.getCodMueDet().getVar30();
                obj3[1] = cc.getCodMueDet().getVar31();
                listaContactos.add(obj3);
            }
            if ((cc.getCodMueDet().getVar32() != null && !cc.getCodMueDet().getVar32().isEmpty()) && (cc.getCodMueDet().getVar33() != null && !cc.getCodMueDet().getVar33().isEmpty())) {
                Object[] obj4 = new Object[2];
                obj4[0] = cc.getCodMueDet().getVar32();
                obj4[1] = cc.getCodMueDet().getVar33();
                listaContactos.add(obj4);
            }
            cargaActual = bc.getCaptCargaControlServicioRemote().obtenerCargaControlPorId(cc.getIdCarCon());
            if (cargaActual.getCodMueDet().getCodMuestra().getNombre().equals("MUESTRA_ENEMDU")) {//codigo
                PrimeFaces requestContext = PrimeFaces.current();
                requestContext.executeScript("PF('dlgInicioContactos').show();");
            } else {
                PrimeFaces requestContext = PrimeFaces.current();
                requestContext.executeScript("PF('dlgInicioCaptura').show();");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void seleccionarCargaCabecera(CaptCabecera cb) {
        try {
            estadoForm = cb.getEstado();

            if (uc.usuarioTieneRol("ROL_ENCUE") && (cb.getEstado().equals("VC") || cb.getEstado().equals("VS"))) {
                bc.addErrorMessage("El formulario ya ha sido criticado o supervisado !No puede acceder!");
            } else if (uc.usuarioTieneRol("ROL_SUPER") && (cb.getEstado().equals("VC"))) {
                bc.addErrorMessage("El formulario ya ha sido criticado  !No puede acceder!");
            } else {
                seleccionarCabeceraEnemduCovid(cb);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void editarFormulario(CaptCabecera cab) {
        try {
            cabeceraActual = bc.getCaptCabeceraServicioRemote().buscarFormCabPorId(cab.getIdCab());
            uc.getSession().setAttribute("capt_cabecera_actual", cabeceraActual);
            if (uc.usuarioTieneRol("ROL_ENCUE") && (cabeceraActual.getEstado().equals("VC") || cabeceraActual.getEstado().equals("VS"))) {
                bc.addErrorMessage("El formulario ya ha sido criticado o supervisado !No puede acceder!");
            } else if (uc.usuarioTieneRol("ROL_SUPER") && (cabeceraActual.getEstado().equals("VC"))) {
                bc.addErrorMessage("El formulario ya ha sido criticado !No puede acceder!");
            }  else {
                irAFormulario(cabeceraActual);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    //Eliminación lógica de registros CaptCargaControl
    public void eliminaCargaControl(CaptCargaControl cc) {
        try {
            cc.setCodAuditoriaCab(vc.getCodAuditoria("PAG_ELIM"));
            bc.getCaptCargaControlServicioRemote().modificarEstadoLogicoCargaControl(cc, false);
            bc.addSuccessMessage("El registro ha sido eliminado satisfactoriamente!");
            buscarCargaPendiente();
        } catch (Exception ex) {
            Logger.getLogger(InicioCapturaPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//Eliminacion lógica de la Cabecera

    public void eliminaCabecera(CaptCabecera c) {
        try {
            if (c != null) {
                //Ajuste manual para la distribucion de trabajo hogares 2
                if (c.getCodCarCon().getControl2().equals("1")) {
                    //System.out.println("Hogar 1");
                    cabeceraActual = bc.getCaptCabeceraServicioRemote().buscarFormCabPorId(c.getIdCab());
                    c.setEstadoLogico(false);
                    c.setObs1("Eliminado por :" + uc.getUsuarioActual().getNombre() + " fecha : " + new Date());
                    bc.getCaptCabeceraServicioRemote().editarCaptCabecera(c);
                    //System.out.println("Desabilita Cabecera:" + c.getIdCab());
                    c.getCodCarCon().setEstadoFormulario(1);
                    bc.getCaptCargaControlServicioRemote().editarCargaControl(c.getCodCarCon());
                    //System.out.println("Desabilita Carga Control:" + c.getCodCarCon().getIdCarCon());
                    bc.addSuccessMessage("El registro ha sido ELIMINADO satisfactoriamente!");
                    buscarCabecerasCreadas();
                    buscarCargaPendiente();
                } else {
                    //System.out.println("Hogar 2");
                    String sql = "";
                    sql = "DELETE FROM captura.capt_detalle_v WHERE cod_cab =" + c.getIdCab() + " ;";
                    String ejec = bc.getMueModeloMuestraServicioRemote().ejecQueryNativoGenerico(sql);
                    if (ejec.equals("OK")) {
                    } else {
                        System.out.println("Error en eliminar DetV");
                    }
                    sql = "DELETE FROM captura.capt_estado WHERE cod_cab =" + c.getIdCab() + " ;";
                    ejec = bc.getMueModeloMuestraServicioRemote().ejecQueryNativoGenerico(sql);
                    if (ejec.equals("OK")) {
                    } else {
                        System.out.println("Error en eliminar estado");
                    }
                    sql = "DELETE FROM captura.capt_estado WHERE cod_cab =" + c.getIdCab() + " ;";
                    ejec = bc.getMueModeloMuestraServicioRemote().ejecQueryNativoGenerico(sql);
                    if (ejec.equals("OK")) {
                    } else {
                        System.out.println("Error en eliminar estado");
                    }
                    sql = "DELETE FROM captura.capt_cabecera WHERE id_cab =" + c.getIdCab() + " ;";
                    ejec = bc.getMueModeloMuestraServicioRemote().ejecQueryNativoGenerico(sql);
                    if (ejec.equals("OK")) {
                    } else {
                        System.out.println("Error en eliminar cabecera");
                    }
                    sql = "DELETE FROM captura.capt_carga_control_equipo WHERE cod_car_con =" + c.getCodCarCon().getIdCarCon() + " ;";
                    ejec = bc.getMueModeloMuestraServicioRemote().ejecQueryNativoGenerico(sql);
                    if (ejec.equals("OK")) {
                    } else {
                        System.out.println("Error en eliminar control equipo");
                    }
                    sql = "DELETE FROM captura.capt_carga_control WHERE id_car_con =" + c.getCodCarCon().getIdCarCon() + " ;";
                    ejec = bc.getMueModeloMuestraServicioRemote().ejecQueryNativoGenerico(sql);
                    if (ejec.equals("OK")) {
                    } else {
                        System.out.println("Error en eliminar control");
                    }
                    bc.addSuccessMessage("El registro ha sido ELIMINADO satisfactoriamente!");
                    buscarCabecerasCreadas();
                    buscarCargaPendiente();
                }
            }
        } catch (Exception ex) {
            bc.addSuccessMessage("Error al Eliminar registro");
            System.out.println("Verifica elimina form:" + ex);
            Logger.getLogger(InicioCapturaPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void validaFrms(CaptCabecera cabec) {
        try {
            Integer errores = 0;
            List<CaptCargaControlEquipo> lstControlF1 = new ArrayList<CaptCargaControlEquipo>();
            //Personal Responsable F1
            String perResp1_1 = "";
            String perResp1_2 = "";
            String perResp1_3 = "";
            String perResp1_4 = "";
            String perResp1_5 = "";
            if (listaFormulariosCreados.get(0) != null) {
                Map<String, Object> pc = new HashMap<String, Object>();
                pc.put("codCarCon", listaFormulariosCreados.get(0).getCodCarCon());
                lstControlF1 = bc.getCaptCargaControlEquipoServicioRemote().listarCargaEquipoPorParametros(pc);
                perResp1_1 = lstControlF1.get(0).getControl();
                perResp1_2 = lstControlF1.get(1).getControl();
                perResp1_3 = lstControlF1.get(2).getControl();
                perResp1_4 = lstControlF1.get(3).getControl();
                perResp1_5 = lstControlF1.get(4).getControl();

            }
            for (int i = 0; i < listaFormulariosCreados.size(); i++) {
                //Validación del período
                String[] inf1 = listaFormulariosCreados.get(0).getInfo1().split("~", -1);
                String[] infFrms = listaFormulariosCreados.get(i).getInfo1().split("~", -1);
                String periodoF1 = inf1[8];
                String periodoFrms = infFrms[8];

                if (!periodoF1.equals(periodoFrms)) {
                    errores++;
                    bc.addErrorMessage("El periodo del frm " + listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario().getIdFormulario() + " es diferente al del Formulario 1.");
                }
                //Validación Personal Responsable
                List<CaptCargaControlEquipo> lstControlFrms = new ArrayList<CaptCargaControlEquipo>();
                Map<String, Object> pc2 = new HashMap<String, Object>();
                pc2.put("codCarCon", listaFormulariosCreados.get(i).getCodCarCon());
                lstControlFrms = bc.getCaptCargaControlEquipoServicioRemote().listarCargaEquipoPorParametros(pc2);

                if (!perResp1_1.equals(lstControlFrms.get(0).getControl())) {
                    errores++;
                    bc.addErrorMessage("Cod. Supervisor del Formulario " + listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario().getIdFormulario() + " es diferente al del Formulario 1.");
                }
                if (!perResp1_2.equals(lstControlFrms.get(1).getControl())) {
                    errores++;
                    bc.addErrorMessage("Cod. Encuestador del Formulario " + listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario().getIdFormulario() + " es diferente al del Formulario 1.");
                }
                if (!perResp1_3.equals(lstControlFrms.get(2).getControl())) {
                    errores++;
                    bc.addErrorMessage("Cod. Crítico-Codificador del Formulario " + listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario().getIdFormulario() + " es diferente al del Formulario 1.");
                }
                if (!perResp1_4.equals(lstControlFrms.get(3).getControl())) {
                    errores++;
                    bc.addErrorMessage("Cod. Digitador del Formulario " + listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario().getIdFormulario() + " es diferente al del Formulario 1.");
                }
                if (!perResp1_5.equals(lstControlFrms.get(4).getControl())) {
                    errores++;
                    bc.addErrorMessage("Cod. Validador del Formulario " + listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario().getIdFormulario() + " es diferente al del Formulario 1.");
                }
            }
            cabec.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            if (errores == 0) {
                cabec.setEstado3("V");
                bcc.guardarCabecera(cabec);
                bc.addSuccessMessage("Los Formularios han sido validados!");
            } else {
                cabec.setEstado3("");
                bcc.guardarCabecera(cabec);
            }
        } catch (Exception ex) {
            Logger.getLogger(InicioCapturaPrueba.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listarHogar(CaptCargaControl cc) {
        if (listaCargaPendiente.size() > 0 || listaFormulariosCreados.size() > 0) {
            nuevoHogar = null;
            cargaCarControlHog = new CaptCargaControl();
            cargaCarControlHog.setClave(cc.getClave());
            cargaCarControlHog.setControl1(cc.getControl1());
            cargaCarControlHog.setControl2(cc.getControl2());
            cargaCarControlHog.setCodMueDet(cc.getCodMueDet());
            cargaCarControlHog.setControl5(cc.getControl5());
            cargaCarControlHog.setControl4(cc.getControl4());
            cargaCarControlHog.setZonal(cc.getZonal());
            //System.out.println("ObjCargado:clave: " + cargaCarControlHog.getClave() + " control1: " + cargaCarControlHog.getControl1() + " Dos: " + cargaCarControlHog.getControl2() + " cod mues: " + cargaCarControlHog.getCodMueDet().getIdMueDet() + " control5: " + cargaCarControlHog.getControl5());
            //System.out.println("listaCarpendiente:" + listaCargaPendiente.size() + "listaFormCreados:" + listaFormulariosCreados.size());
//            if (listaCargaPendiente.size() > 0) {
//                cargaCarControlHog.setCodMueDet(listaCargaPendiente.get(0).getCodMueDet());
//                cargaCarControlHog.setZonal(listaCargaPendiente.get(0).getZonal());
//                cargaCarControlHog.setControl4(listaCargaPendiente.get(0).getControl4());
//            } else {
//                cargaCarControlHog.setCodMueDet(listaFormulariosCreados.get(0).getCodMuestra());
//                cargaCarControlHog.setZonal(listaFormulariosCreados.get(0).getCodCarCon().getZonal());
//                cargaCarControlHog.setControl4(listaFormulariosCreados.get(0).getCodCarCon().getControl4());
//            }
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.executeScript("PF('dlgCrearHogarEnemdu').show();");
            bc.addSuccessMessage("Ingreso a creación de Hogar");
        } else {
            bc.addWarningMessage("No se ha encontrado resultados");
        }
    }

    private void cargarControlEquipo(CaptCargaControl cc) {
        try {
            Map<String, Object> pc = new HashMap<String, Object>();
            pc.put("codCarCon", cc);
            listaCargaControlEquipo = bc.getCaptCargaControlEquipoServicioRemote().listarCargaEquipoPorParametros(pc);
            if (listaCargaControlEquipo.isEmpty()) {
                //se crearan 5 registros por defecto
                for (int i = 1; i <= 5; i++) {
                    CaptCargaControlEquipo e = new CaptCargaControlEquipo();
                    e.setCodCarCon(cc);
                    e.setEstadoCargCont(i);
                    e.setEstadoLogico(true);
                    e.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
                    bc.getCaptCargaControlEquipoServicioRemote().crearCargaControlEquipo(e);
                }
                listaCargaControlEquipo = bc.getCaptCargaControlEquipoServicioRemote().listarCargaEquipoPorParametros(pc);

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void guardarControlEquipo() {
        try {
            if (!listaCargaControlEquipo.isEmpty()) {
                //se crearan 5 registros por defecto
                for (int i = 0; i < listaCargaControlEquipo.size(); i++) {
                    bc.getCaptCargaControlEquipoServicioRemote().editarCargaControlEquipo(listaCargaControlEquipo.get(i));
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearFormulario() {
        try {
            if (resulEntrevistaOtraRazon != null) {
                MetFormulario frm = new MetFormulario(Integer.valueOf(resulEntrevistaOtraRazon));
                cabeceraActual.setCodFormulario(frm);
            }

            if (cabeceraActual.getEstado1() != null) {
                if (Integer.valueOf(cabeceraActual.getEstado1().substring(0, 2)) >= 2) {
                    if (pasaValResultadoEntrevistaOtraRazon() && validaFecha() && pasaValidaFono() && pasaValidaEnemNoEfec()) {
                        if (cabeceraActual.getEstado2() == null) {
                            bc.addErrorMessage("!Forma de Captura:Obligatorio!");
                        } else {
                            crearFormularioNoEfectivo();
                            PrimeFaces requestContext = PrimeFaces.current();
                            requestContext.executeScript("PF('dlgInicioCapturaEnemDiciembre').hide();");
                            buscarCabecerasCreadas();
                            buscarCargaPendiente();
                            requestContext.ajax().update(":frmIniCap:pnlCargaPendiente");
                            requestContext.ajax().update(":frmIniCap:pnlCabeceras");
                            bc.addSuccessMessage("Formulario No Efectivo creado");
                        }
                    }
                } else {
                    if (pasaValidaFono() && pasaNumElementos() && pasaValModoCaptura() && validaFecha() && pasaValidaEnem()) {
                        crearFormularioEnemdu();
                    }
                    if (cabeceraActual.getIdCab() != null) {
                        uc.getSession().setAttribute("capt_cabecera_actual", cabeceraActual);
                        irAFormulario(cabeceraActual);
                    }
                }
            } else {
                bc.addErrorMessage("Seleccione el resultado de la entrevista");
            }
            limpiarObjetosEnemdu();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean pasaValidaEnem() {
        boolean pasaValidacion = false;
        int valOriginal = 0;
        int valReemplazo = 0;
        int cedula = 0;
        int atrcalle = 0;
        int atrpiso = 0;
        int atrinformante = 0;
        int muni = 0;

        if (Integer.parseInt(objMueDetalleActual.getVar20()) > 0 && Integer.parseInt(objMueDetalleActual.getVar20()) < 8 && (vivReemplaza != null && !vivReemplaza.equals(""))) {
            bc.addErrorMessage("La vivienda es original: No corresponde ingresar número de vivienda original reemplazada");
            valOriginal = 0;
        } else {
            valOriginal = 1;
        }
        if (Integer.parseInt(objMueDetalleActual.getVar20()) > 7 && Integer.parseInt(objMueDetalleActual.getVar20()) <= 10 && (vivReemplaza == null || vivReemplaza.equals("") || (Integer.parseInt(vivReemplaza) > 7) || Integer.parseInt(vivReemplaza) == 0)) {
            bc.addErrorMessage("La vivienda no es original: Codigo de reemplazo vacio o fuera de rango");
            valReemplazo = 0;
        } else {
            valReemplazo = 1;
        }
        if (cedEncuestador.length() != 10 || cedDigitador.length() != 10 || cedCritico.length() != 10 || cedSupervisor.length() != 10 || (cedValidador.length() > 0 && cedValidador.length() < 10)) {
            bc.addErrorMessage("Los campos de cédula deben tener 10 dígitos ");
            cedula = 0;
        } else {
            if (validaCedulasPersonalTecnico()) {
                cedula = 1;
            } else {
                cedula = 0;
            }
        }
        if (calle.trim() == null || calle.trim().equals("")) { //Valida Calle
            bc.addErrorMessage("Calle:No debe estar vacio");
            atrcalle = 0;
        } else {
            atrcalle = 1;
        }
        if (nuMunicipio.trim() == null || nuMunicipio.trim().equals("")) { //Valida Calle
            bc.addErrorMessage("Nro. Municipio:No debe estar vacio");
            muni = 0;
        } else {
            muni = 1;
        }
        if (piso.trim() == null || piso.trim().equals("")) { //Valida Calle
            bc.addErrorMessage("Piso:No debe estar vacio");
            atrpiso = 0;
        } else {
            atrpiso = 1;
        }
        if (Integer.parseInt(codInformante) > Integer.parseInt(conNumMiembrosHog) || (Integer.parseInt(codInformante) != 0 && Integer.parseInt(conNumMiembrosHog) == 0)) {
            bc.addErrorMessage("Código de informante:Fuera de Rango ");
            atrinformante = 0;
        } else {
            atrinformante = 1;
        }
        // System.out.println("Resul valida:" + valParentesco + valReemplazo + efectiva + cedula + calle + localidad + lote + bloque + patio + piso + casa + fonoLocal + fonoCel + depar + valCedFurc);
        if ((valOriginal + valReemplazo + cedula + atrcalle + atrpiso + atrinformante + muni) == 7) {
            pasaValidacion = true;
        }
        return pasaValidacion;
    }

    public boolean pasaValidaEnemNoEfec() {
        boolean pasaValidacion = false;
        int cedula = 0;
        int atrcalle = 0;
        int atrpiso = 0;
        int muni = 0;
        int valOriginal = 0;
        int valReemplazo = 0;

        if (Integer.parseInt(objMueDetalleActual.getVar20()) > 0 && Integer.parseInt(objMueDetalleActual.getVar20()) < 8 && (vivReemplaza != null && !vivReemplaza.equals(""))) {
            bc.addErrorMessage("La vivienda es original: No corresponde ingresar número de vivienda original reemplazada");
            valOriginal = 0;
        } else {
            valOriginal = 1;
        }
        if (Integer.parseInt(objMueDetalleActual.getVar20()) > 7 && Integer.parseInt(objMueDetalleActual.getVar20()) <= 10 && (vivReemplaza == null || vivReemplaza.equals("") || (Integer.parseInt(vivReemplaza) > 7) || Integer.parseInt(vivReemplaza) == 0)) {
            bc.addErrorMessage("La vivienda no es original: Codigo de reemplazo vacio o fuera de rango");
            valReemplazo = 0;
        } else {
            valReemplazo = 1;
        }
        if (cedEncuestador.length() != 10 || cedDigitador.length() != 10 || cedCritico.length() != 10 || cedSupervisor.length() != 10 || (cedValidador.length() > 0 && cedValidador.length() < 10)) {
            bc.addErrorMessage("Los campos de cédula deben tener 10 dígitos ");
            cedula = 0;
        } else {
            if (validaCedulasPersonalTecnico()) {
                cedula = 1;
            } else {
                cedula = 0;
            }
        }
        if (calle.trim() == null || calle.trim().equals("")) { //Valida Calle
            bc.addErrorMessage("Calle:No debe estar vacio");
            atrcalle = 0;
        } else {
            atrcalle = 1;
        }
        if (nuMunicipio.trim() == null || nuMunicipio.trim().equals("")) { //Valida Calle
            bc.addErrorMessage("Nro. Municipio:No debe estar vacio");
            muni = 0;
        } else {
            muni = 1;
        }
        if (piso.trim() == null || piso.trim().equals("")) { //Valida Calle
            bc.addErrorMessage("Piso:No debe estar vacio");
            atrpiso = 0;
        } else {
            atrpiso = 1;
        }
        // System.out.println("Resul valida:" + valParentesco + valReemplazo + efectiva + cedula + calle + localidad + lote + bloque + patio + piso + casa + fonoLocal + fonoCel + depar + valCedFurc);
        if ((cedula + atrcalle + atrpiso + muni + valOriginal + valReemplazo) == 6) {
            pasaValidacion = true;
        }
        return pasaValidacion;
    }

    public Boolean validaFecha() {
        Boolean fechaValida = false;
        Integer contEr = 0;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (conFechaVisita1 != null) {
                if (!conFechaVisita1.isEmpty()) {
                    String[] vivi = conFechaVisita1.split("-", -1);
//                    System.out.println("valor 1" + Integer.valueOf(vivi[1]));
//                    System.out.println("valor 0" + Integer.valueOf(vivi[0]));
//                    System.out.println("valor 2" + Integer.valueOf(vivi[2]));
                    if (Integer.valueOf(vivi[2]) > 31) {
                        contEr++;
                        bc.addErrorMessage("Fecha de encuesta:Día se encuentra fuera de rango");
                    }
                    if (Integer.valueOf(vivi[1]) > 12) {
                        contEr++;
                        bc.addErrorMessage("Fecha de encuesta:El mes esta fuera de rango");
                    }
                    if (Integer.valueOf(vivi[0]) != 2021) {
                        contEr++;
                        bc.addErrorMessage("Fecha de encuesta:El año no corresponde al actual");
                    }
                    if (contEr == 0) {
                        fechaValida = true;
                    }
                } else {
                    fechaValida = false;
                    bc.addErrorMessage("Fecha de la encuesta:No puede estar vacio");
                }
            } else {
                fechaValida = false;
                bc.addErrorMessage("Fecha de la encuesta:No puede estar vacio");
            }
        } catch (Exception ex) {
            bc.addErrorMessage("No se guardo la información");
            //Logger.getLogger(InicioCapturaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaValida;
    }

    public boolean validaLongitudCedulasPersonalTecnico() {
        boolean resultado = true;

        if (cedEncuestador.length() != 10 || cedDigitador.length() != 10 || cedCritico.length() != 10 || cedSupervisor.length() != 10 || (cedValidador.length() > 0 && cedValidador.length() < 10)) {
            bc.addErrorMessage("Los campos de cédula deben tener 10 dígitos ");
            resultado = false;
        }

        return resultado;
    }

    public boolean validaCedulasPersonalTecnico() { //Valida el ingreso de cedulas que estan en la base de datos
        boolean resultado = true;

        if (!validaCedulasCargo("Encuestador", cedEncuestador)) {
            resultado = false;

            bc.addErrorMessage("La cédula del Encuestador no se encuentra registrada.");
        }
        if (!validaCedulasCargo("Supervisor", cedSupervisor)) {
            resultado = false;

            bc.addErrorMessage("La cédula del Supervisor no se encuentra registrada.");
        }
        if (!validaCedulasCargo("Critico-Codificador", cedCritico)) {
            resultado = false;

            bc.addErrorMessage("La cédula del Critico-Codificador no se encuentra registrada.");
        }
        if (!validaCedulasCargo("Digitador", cedDigitador)) {
            resultado = false;

            bc.addErrorMessage("La cédula del Digitador no se encuentra registrada.");
        }
        if (cedValidador.length() == 10) {
            if (!validaCedulasCargo("Validador", cedValidador)) {
                resultado = false;

                bc.addErrorMessage("La cédula del Validador no se encuentra registrada.");
            }
        }

        return resultado;
    }

    public boolean validaCedulasCargo(String tipoCatalogo, String valorCatalogo) { //Valida el ingreso de cedulas que estan en la base de datos
        boolean existeCatalogo = false;
        try {
            List<MetCatalogo> listCatalogo = bc.getMetCatalogoServicioRemote().listarEjecutarConsulta("buscarCatalogoPorAliasAndValor", Arrays.asList(tipoCatalogo, valorCatalogo.trim()));

            if (!listCatalogo.isEmpty()) {
                existeCatalogo = true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return existeCatalogo;
    }

    public boolean validaNroViviendaOriginalCodReemplazo() {
        if (Integer.parseInt(objMueDetalleActual.getVar20()) > 0 && Integer.parseInt(objMueDetalleActual.getVar20()) < 8 && (vivReemplaza != null && !vivReemplaza.equals(""))) {
            bc.addErrorMessage("La vivienda es original: No corresponde ingresar número de vivienda original reemplazada");
            return false;
        } else {
            return true;
        }
    }

    public void actualizarCabecera() {
        try {
            if (cargaActual.getCodMueDet().getCodMuestra().getNombre().equals("MUESTRA_EPSPND")) {
                actualizaCabecaeraEnemCovid();
            }
        } catch (Exception e) {
        }
    }

    public void crearForm() {

        if (numElementos > 0) {
            crearFormularioCenso(cargaActual);
        } else {
            crearFormularioNoCenso(cargaActual);
        }

    }

    public void crearFormularioCenso(CaptCargaControl cc) {
        try {
            String val = validarCreacionEfectivoCenso(cc);
            if (val.equals("OK")) {
                guardarControlEquipo();
                cargaActual.setEstadoFormulario(2);
                cabeceraActual.setInfo1("");
                cabeceraActual.setClave(cargaActual.getClave());
                cabeceraActual.setCodCarCon(cargaActual);
//hg                cabeceraActual.setCodMuestra(cargaActual.getCodMueDet());
                cabeceraActual.setEstado("00");
                cabeceraActual.setNumDet(1);
                cabeceraActual.setFechaCreacion(new Date());
                cabeceraActual.setFechahoraGuardado(new Date());
                cabeceraActual.setEstadoLogico(true);
                //cabeceraActual.setCodFormulario(cargaActual.getCodFormulario());
                //EDICION DE CARGA CONTROL
                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);
                //CREACION DE CABECERA
                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
                cabeceraActual = bc.getCaptCabeceraServicioRemote().crearCaptCabecera(cabeceraActual);
                //CREACION DE ESTADOS
                bc.getCaptEstadoServicioRemote().crearEstadosInicialesPorCabeceraYElementos(cabeceraActual.getIdCab(), cargaActual.getClave(), 1);

                //CREACION DE DETALLES V Y H
                //las 2 listas parametros listaVarsV,listaFilasH deben venir de cache, por el momento se realiza la consulta en cada creacion
                List<CaptVarV> listaVarsV = this.listarVariablesVPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
                bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
                        listaVarsV, 1);

//                List<CaptFilaH> listaFilasH = this.listarFilasHPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
//                if (!listaFilasH.isEmpty()) {
//                    bc.getCaptDetalleHServicioRemote().crearDetallesHDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
//                            listaFilasH, 1);
//                }
                
//                //Insertar valores
//                CaptCabecera cab = new CaptCabecera();
//                cab = bc.getCaptCabeceraServicioRemote().buscarPorCodCarCon(cargaActual.getIdCarCon());
//                MetVariable var = new MetVariable();
//                List<CaptDetalleV> listV = new ArrayList<CaptDetalleV>();
//                var = bc.getMetVariableServicioRemote().buscarVariablePorIdentificador("CENSO_S4_H00");                
//                listV = bc.getCaptDetalleVServicioRemote().listaValoresVariableXCab(cab.getIdCab(), var.getIdVar());             
//                listV.get(0).setValor(cargaActual.getControl2());
//                bc.getCaptDetalleVServicioRemote().editarFormCabecera(listV.get(0));
////                bc.getCaptDetalleVServicioRemote().editarFormCabecera(listV.get(0));
////                var = bc.getMetVariableServicioRemote().buscarVariablePorIdentificador("CENSO_S4_H1302");
////                listV = bc.getCaptDetalleVServicioRemote().listaValoresVariableXCab(cab.getIdCab(), var.getIdVar());
////                listV.get(0).setValor(String.valueOf(numMujeres));
////                bc.getCaptDetalleVServicioRemote().editarFormCabecera(listV.get(0));
////                var = bc.getMetVariableServicioRemote().buscarVariablePorIdentificador("CENSO_S4_H1303");
////                listV = bc.getCaptDetalleVServicioRemote().listaValoresVariableXCab(cab.getIdCab(), var.getIdVar());
////                listV.get(0).setValor(String.valueOf(numElementos));
//
//                //System.out.println("IdCdb: " + cabeceraActual.getIdCab());

                if (cabeceraActual.getIdCab() != null) {
                    uc.getSession().setAttribute("capt_cabecera_actual", cabeceraActual);
                    irAFormulario(cabeceraActual);
                    //System.out.println("creación de formulario efectivo");
                } else {
                    System.out.println("cabeceraActual.getIdCab() = null");
                }

            } else {
                bc.addErrorMessage(val);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearFormularioNoCenso(CaptCargaControl cc) {
        try {
            //System.out.println("carga " + cc);
            //System.out.println("numPersonas en crear " + numElementos);
            String val = validarCreacionNoEfectivoCenso(cc);
            if (val.equals("OK")) {
                guardarControlEquipo();
                cargaActual.setEstadoFormulario(2);
                cabeceraActual.setInfo1("N");
                cabeceraActual.setClave(cargaActual.getClave());
                cabeceraActual.setCodCarCon(cargaActual);
//hg                cabeceraActual.setCodMuestra(cargaActual.getCodMueDet());
                cabeceraActual.setEstado("00");
                cabeceraActual.setNumDet(1);
                cabeceraActual.setFechaCreacion(new Date());
                cabeceraActual.setFechahoraGuardado(new Date());
                cabeceraActual.setEstadoLogico(true);
                //cabeceraActual.setCodFormulario(cargaActual.getCodFormulario());
                //EDICION DE CARGA CONTROL
                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);
                //CREACION DE CABECERA
                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
                cabeceraActual = bc.getCaptCabeceraServicioRemote().crearCaptCabecera(cabeceraActual);
                //CREACION DE ESTADOS
                bc.getCaptEstadoServicioRemote().crearEstadosInicialesPorCabeceraYElementos(cabeceraActual.getIdCab(), cargaActual.getClave(), 1);

                //CREACION DE DETALLES V Y H
                //las 2 listas parametros listaVarsV,listaFilasH deben venir de cache, por el momento se realiza la consulta en cada creacion
                List<CaptVarV> listaVarsV = this.listarVariablesVPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
                bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
                        listaVarsV, 1);

                List<CaptFilaH> listaFilasH = this.listarFilasHPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
                if (!listaFilasH.isEmpty()) {
                    bc.getCaptDetalleHServicioRemote().crearDetallesHDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
                            listaFilasH, 1);
                }
                //System.out.println("IdCdb: " + cabeceraActual.getIdCab());

                if (cabeceraActual.getIdCab() != null) {
                    uc.getSession().setAttribute("capt_cabecera_actual", cabeceraActual);
                    irAFormulario(cabeceraActual);
                    //System.out.println("creación de formulario NO efectivo");
                } else {
                    System.out.println("cabeceraActual.getIdCab() = null");
                }

            } else {
                bc.addErrorMessage(val);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void calcularTotalPersonas() {
        //int r = 0;
        try {
            if (numHombres != null && numMujeres != null) {
                numElementos = numHombres + numMujeres;
                //System.out.println("total H+M: " + numElementos);
            }
        } catch (Exception ex) {
            //r = 0;
            LOGGER.log(Level.SEVERE, null, ex);
        }
        //return r;
    }

    public void crearFormularioNoEfectivo() {
        try {
            guardarInfoCabEnemdu();
            buscaJornada();
            cabeceraActual.setEstadoLogico(true);
            cabeceraActual.setInfo1(info1);
            cabeceraActual.setInfo2(info2);
            cabeceraActual.setInfo3(info3);
            cabeceraActual.setObs1(observaciones);
            cabeceraActual.setObs2(perJornada.getNombre());
            cargaActual.setEstadoFormulario(2);
            cabeceraActual.setClave(cargaActual.getClave());
            cabeceraActual.setCodCarCon(cargaActual);
//hg            cabeceraActual.setCodMuestra(cargaActual.getCodMueDet());
            cabeceraActual.setEstado("00");
            cabeceraActual.setNumDet(0);
            cabeceraActual.setFechaCreacion(new Date());
            cabeceraActual.setFechahoraGuardado(new Date());
            if (uc.usuarioTieneRol("ROL_ENCUE")) {
                cabeceraActual.setInfo4(uc.getUsuarioActual().getNombre());
            }
            //EDICION DE CARGA CONTROL
            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);
            //CREACION DE CABECERA
            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
            cabeceraActual = bc.getCaptCabeceraServicioRemote().crearCaptCabecera(cabeceraActual);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private int obtenerNumeroElementos(String nmh, String nnmh) {
        int r = 0;
        try {
            r = Integer.valueOf(nmh) + Integer.valueOf(nnmh);
        } catch (Exception ex) {
            r = 0;
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return r;
    }

//    public String validarCreacionEfectivoCenso(CaptCargaControl cc, Integer numEle) {
//        String val = "OK";
//        try {
//            if (cc.getIdCarCon() == null) {
//                val = val + " NO SE HA SELECCIONADO UNA CARGA.";
//            }
//            if (cc.getCodFormulario().getTipo().equals("1E")) {
//                numElementos = 1;
//            } else if (cc.getCodFormulario().getTipo().equals("ME")) {
//                if (numEle == null || numEle == 0) {
//                    val = val + " INGRESE NÚMERO DE MIEMBROS.";
//                }
//            }
//            if (numEle > 25) {
//
//                val = val + " DEMASIADOS MIEMBROS DE HOGAR:" + numEle + ".";
//
//            }
//        } catch (Exception ex) {
//            val = "ERROR CREAR FORMULARIO";
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//        return val;
//    }
    
    public String validarCreacionEfectivoCenso(CaptCargaControl cc) {
        String val = "OK";
        try {
            if (cc.getIdCarCon() == null) {
                val = val + " NO SE HA SELECCIONADO UNA CARGA.";
            }                        
        } catch (Exception ex) {
            val = "ERROR CREAR FORMULARIO";
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return val;
    }
    
    private String validarCreacionNoEfectivoCenso(CaptCargaControl cc) {
        String val = "OK";
        try {
            if (cc.getIdCarCon() == null) {
                val = val + " NO SE HA SELECCIONADO UNA CARGA.";
            }            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return val;
    }
    

//    private String validarCreacionNoEfectivoCenso(CaptCargaControl cc, Integer numEle) {
//        String val = "OK";
//        try {
//            if (cc.getIdCarCon() == null) {
//                val = val + " NO SE HA SELECCIONADO UNA CARGA.";
//            }
//
//            if (cc.getCodFormulario().getTipo().equals("1E")) {
//                numElementos = 1;
//            } else if (cc.getCodFormulario().getTipo().equals("ME")) {
//                if (numEle == null) {
//                    val = val + " INGRESE NUMERO DE MIEMBROS.";
//                }
//            }
//            if (numEle > 20) {
//                val = val + " DEMASIADOS MIEMBROS DE HOGAR:" + numEle + ".";
//            }
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//        return val;
//    }

    private String validarCreacionEfectivo(Integer numEle) {
        String val = "OK";
        try {
            if (numEle > 25) {
                val = val + "DEMASIADOS MIEMBROS DE HOGAR:" + numEle + ".";
                bc.addErrorMessage("DEMASIADOS MIEMBROS DE HOGAR: " + numEle);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return val;
    }

    public void crearHogar() {
        //System.out.println("Ingresa creación de hogar");
        try {
            //System.out.println("Hogar enemdu");
            crearHogarEnemdu();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearHogarEnemdu() { //metMulti
        try {
            int hogar = Integer.parseInt(nuevoHogar);
            if (hogar > 1 && hogar < 10) {
                int contador;
                int max = 0;
                CaptCargaControl ObjCArgaAux = new CaptCargaControl();
                //ObjCArgaAux = bc.getCaptCargaControlServicioRemote().obtenerCargaControlPorId(cargaCarControlHog.getIdCarCon());
                //ObjCArgaAux = bc.getCaptCargaControlServicioRemote().obtenerCargaControlPorClaveCtr12PorMuestra(cargaCarControlHog.getClave(), cargaCarControlHog.getControl1(), cargaCarControlHog.getControl2(), cargaCarControlHog.getCodMueDet().getIdMueDet());
                List<CaptCargaControl> ltsObjCArgaAux = new ArrayList<CaptCargaControl>();
                ltsObjCArgaAux = bc.getCaptCargaControlServicioRemote().listarPorClaveCtr12PorMuestra(cargaCarControlHog.getClave(), cargaCarControlHog.getControl1(), cargaCarControlHog.getControl2(), cargaCarControlHog.getCodMueDet().getIdMueDet());
                if (ltsObjCArgaAux != null) {
                    if (ltsObjCArgaAux.size() == 1) {
                        for (CaptCargaControl objCarCon : ltsObjCArgaAux) {
                            ObjCArgaAux = objCarCon;
                        }
                    }
                }
                int codigoFormulario = ObjCArgaAux.getCodFormulario().getIdFormulario();

                //System.out.println("Cod formulario:" + codigoFormulario);
                listaFormulariosPorHogar = bc.getCaptCargaControlServicioRemote().listarPorClaveCtr1PorMuestraPorFrm(
                        cargaCarControlHog.getClave(), cargaCarControlHog.getControl1(), cargaCarControlHog.getCodMueDet().getIdMueDet(), codigoFormulario);
                for (int a = 0; a < listaFormulariosPorHogar.size(); a++) {
                    contador = Integer.parseInt(listaFormulariosPorHogar.get(a).getControl2());
                    if (contador > max) {
                        max = contador;
                    }
                }
                if ((max + 1) == hogar) {
                    cargaBusqueda = new CaptCargaControl();
                    cargaBusqueda = bc.getCaptCargaControlServicioRemote().listarCaptCargaControlPorClaveCtr12PorFor(
                            cargaCarControlHog.getClave(), cargaCarControlHog.getControl1(), nuevoHogar, codigoFormulario);
                    if (cargaBusqueda.getIdCarCon() == null) {
                        cargaBusqueda = cargaCarControlHog;
                        cargaBusqueda.setEstadoFormulario(1);
                        cargaBusqueda.setEstadoTransmision(1);
                        cargaBusqueda.setControl2(nuevoHogar);
                        cargaBusqueda.setControl3("1");
                        cargaBusqueda.setFechaHora(new Date());
                        cargaBusqueda.setEstadoLogico(true);
                        cargaBusqueda.setTipo("CONGL");
                        for (int i = 0; i < listaFormulariosCreados.size(); i++) {
                            if (listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario().getIdFormulario() == codigoFormulario) {
                                cargaBusqueda.setCodFormulario(listaFormulariosCreados.get(i).getCodCarCon().getCodFormulario());
                            }
                        }
                        for (int j = 0; j < listaCargaPendiente.size(); j++) {
                            if (listaCargaPendiente.get(j).getCodFormulario().getIdFormulario() == codigoFormulario) {
                                cargaBusqueda.setCodFormulario(listaCargaPendiente.get(j).getCodFormulario());
                            }
                        }
                        cargaBusqueda.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
                        bc.getCaptCargaControlServicioRemote().crearCargaControl(cargaBusqueda);
                        PrimeFaces requestContext = PrimeFaces.current();
                        requestContext.executeScript("PF('dlgCrearHogarEnemdu').hide();");
                        buscarInformacion();
                        requestContext.ajax().update(":frmIniCap:pnlCargaPendiente");
                        requestContext.ajax().update(":frmIniCap:pnlCabeceras");
                        bc.addSuccessMessage("Se ha creado el hogar");
                        
                                                

                    } else {
                        bc.addWarningMessage("Ya existe un hogar creado");
                    }
                } else {
                    bc.addErrorMessage("No se puede crear el hogar debe seguir el secuencial");
                }
            } else {
                bc.addErrorMessage("Hogar ingresado no valido");
            }
        } catch (Exception ex) {
            bc.addErrorMessage("No se pudo crear el nuevo hogar");
        }
    }

    public void actualizaCabecaeraEnemCovid() {
        try {
            if (uc.usuarioTieneRol("ROL_ENCUE") && cabeceraActual.getEstado().equals("VG")) {
                bc.addErrorMessage("No puede hacer cambios:Formulario en estado VG");
            } else if (uc.usuarioTieneRol("ROL_SUPER") && cabeceraActual.getEstado().equals("VC")) {
                bc.addErrorMessage("No puede hacer cambios:Formulario en estado VC");
            } else {
                if (Integer.parseInt(objMueDetalleActual.getVar20()) > 7 && Integer.parseInt(objMueDetalleActual.getVar20()) <= 10 && (vivReemplaza == null || vivReemplaza.equals("") || (Integer.parseInt(vivReemplaza) > 7) || Integer.parseInt(vivReemplaza) == 0)) {
                    bc.addErrorMessage("La vivienda no es original: Codigo de reemplazo vacio o fuera de rango");
                } else {

                    if (validaNroViviendaOriginalCodReemplazo() && pasaValModoCaptura() && validaLongitudCedulasPersonalTecnico() && validaCedulasPersonalTecnico()) {//Actualiza nueva variable req.14-10-22                                    
                        if (cabeceraActual.getEstado2().equals("01")) {
                            cabeceraActual.setEstado4(null);
                        } // setea el campo estado 4 en caso de modificar la forma de captura
                        guardarInfoCabEnemdu();
                        cabeceraActual.setInfo1(info1);
                        cabeceraActual.setInfo2(info2);
                        cabeceraActual.setInfo3(info3);
                        cabeceraActual.setObs1(observaciones);
                        if (uc.usuarioTieneRol("ROL_ENCUE")) {
                            cabeceraActual.setInfo4(uc.getUsuarioActual().getNombre());
                        }
                        cabeceraActual.setFechahoraEdicion(new Date());
                        cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                        cabeceraActual = bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
                        PrimeFaces requestContext = PrimeFaces.current();
                        requestContext.executeScript("PF('dlgInicioCapturaEnemDiciembre').hide();");
                        buscarInformacion();
                        requestContext.ajax().update(":frmIniCap:pnlCargaPendiente");
                        requestContext.ajax().update(":frmIniCap:pnlCabeceras");
                        bc.addSuccessMessage("Datos Actualizados");
                        limpiarObjetosEnemdu();
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(InicioCapturaPrueba.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearFormularioEnemdu() {
        try {
            String val = validarCreacionEfectivo(Integer.parseInt(conNumMiembrosHog));
            //System.out.println("Validacion nueva:" + val);
            if (val.equals("OK")) {
                guardarInfoCabEnemdu();
                buscaJornada();
                cabeceraActual.setInfo1(info1);
                cabeceraActual.setInfo2(info2);
                cabeceraActual.setInfo3(info3);
                cabeceraActual.setObs1(observaciones);
                cabeceraActual.setObs2(perJornada.getNombre());
                cargaActual.setEstadoFormulario(2);
                cabeceraActual.setClave(cargaActual.getClave());
                cabeceraActual.setCodCarCon(cargaActual);
//hg                cabeceraActual.setCodMuestra(cargaActual.getCodMueDet());
                cabeceraActual.setEstado("00");
                numElementos = Integer.parseInt(conNumMiembrosHog);
                cabeceraActual.setNumDet(numElementos);
                cabeceraActual.setFechaCreacion(new Date());
                cabeceraActual.setFechahoraGuardado(new Date());
                cabeceraActual.setEstadoLogico(true);
                if (uc.usuarioTieneRol("ROL_ENCUE")) {
                    cabeceraActual.setInfo4(uc.getUsuarioActual().getNombre());
                }
                //EDICION DE CARGA CONTROL
                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);
                //CREACION DE CABECERA
                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
                cabeceraActual = bc.getCaptCabeceraServicioRemote().crearCaptCabecera(cabeceraActual);
                //CREACION DE ESTADOS
                bc.getCaptEstadoServicioRemote().crearEstadosInicialesPorCabeceraYElementos(cabeceraActual.getIdCab(), cargaActual.getClave(), numElementos);
                //CREACION DE DETALLES V Y H
                //las 2 listas parametros listaVarsV,listaFilasH deben venir de cache, por el momento se realiza la consulta en cada creacion
                List<CaptVarV> listaVarsV = this.listarVariablesVPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
                bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
                        listaVarsV, numElementos);

                List<CaptFilaH> listaFilasH = this.listarFilasHPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
                if (!listaFilasH.isEmpty()) {
                    bc.getCaptDetalleHServicioRemote().crearDetallesHDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
                            listaFilasH, numElementos);
                }
                /* } else {
                bc.addErrorMessage(val);
            }*/
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarInfoCabEnemdu() {
        info1 = nomJefeHogar + "~" + nomInformante + "~" + codInformante + "~" + fonoConvencional + "~" + fonoCelular; // 
        info2 = piso + "~" + nuMunicipio + "~" + calle + "~" + vivReemplaza;
        info3 = cedEncuestador + "~" + cedSupervisor + "~" + cedCritico + "~" + cedDigitador + "~" + conFechaVisita1 + "~" + cedValidador;
    }

    public void buscaJornada() {
        try {
            perJornada = new AdmPeriodo();
            perJornada = bc.getAdmPeriodoServicioRemote().buscarXCodigoActivo(jornadaSelect);
        } catch (Exception ex) {
            Logger.getLogger(InicioCapturaPrueba.class.getName()).log(Level.SEVERE, null, ex);
            bc.addErrorMessage("Jornada No encontrada");
        }
    }

    public void limpiarObjetosEnemdu() {
        conNumMiembrosHog = "";
        observaciones = "";
        fonoConvencional = "";
        fonoCelular = "";
        cedEncuestador = "";
        cedSupervisor = "";
        cedCritico = "";
        cedDigitador = "";
        cedValidador = "";
        piso = "";
        calle = "";
        nuMunicipio = "";
        nomJefeHogar = "";
        nomInformante = "";
        codInformante = "";
        conFechaVisita1 = "";
        vivReemplaza = "";
        estadoForm = "";
    }

    public void seleccionarCabeceraEnemduCovid(CaptCabecera cb) {//codigoMulti
        cabeceraGuardada = new CaptCabecera();
        try {
            cabeceraGuardada = bc.getCaptCabeceraServicioRemote().listarCaptCabeceraPorId(cb.getIdCab());
            String[] inf1 = cabeceraGuardada.getInfo1().split("~", -1);
            nomJefeHogar = inf1[0];
            nomInformante = inf1[1];
            codInformante = inf1[2];
            fonoConvencional = inf1[3];
            fonoCelular = inf1[4];
            String[] inf2 = cabeceraGuardada.getInfo2().split("~", -1);
            piso = inf2[0];
            nuMunicipio = inf2[1];
            calle = inf2[2];
            vivReemplaza = inf2[3];
            String[] inf3 = cabeceraGuardada.getInfo3().split("~", -1);
            cedEncuestador = inf3[0];
            cedSupervisor = inf3[1];
            cedCritico = inf3[2];
            cedDigitador = inf3[3];
            cedValidador = inf3[5];
            conFechaVisita1 = inf3[4];
            observaciones = cabeceraGuardada.getObs1();
            conNumMiembrosHog = cabeceraGuardada.getNumDet() + "";

            if (cabeceraGuardada.getEstado1().equals("09")) {
                activaOtroCual = 1;
                resulEntrevistaOtraRazon = cabeceraGuardada.getCodFormulario().getIdFormulario().toString();
            } else {
                activaOtroCual = 0;
            }

            cargaActual = bc.getCaptCargaControlServicioRemote().obtenerCargaControlPorId(cb.getCodCarCon().getIdCarCon());
            objMueDetalleActual = bc.getMueMuestraDetalleServicioRemote().buscarMuestraDetActiva(cargaActual.getCodMueDet().getIdMueDet());
            cargarControlEquipo(cargaActual);
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.executeScript("PF('dlgInicioCapturaEnemDiciembre').show();");
            numElementos = cabeceraGuardada.getNumDet();
            cabeceraActual = cabeceraGuardada;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void activaOtroCualResultadoEntrevista() {
        try {
            if (cabeceraActual.getEstado1().equals("09")) {
                activaOtroCual = 1;
            } else {
                resulEntrevistaOtraRazon = null;
                cabeceraActual.setCodFormulario(null);
                activaOtroCual = 0;
            }
            //System.out.println("cp" + cabeceraActual.getCodFormulario());
        } catch (Exception e) {
            bc.addErrorMessage("No se guardo la información");
        }
    }

    public boolean pasaValidaFono() {
        boolean pasaValidacion = false;
        int fonoLocal = 0;
        int fonoCel = 0;
        if (fonoConvencional.equals("")) {
            fonoLocal = 0;
            bc.addErrorMessage("Telefono Convencional !No puede estar vacio!");
        } else {
            if (!fonoConvencional.equals("") && (fonoConvencional.length() < 9)) {//Valida Fono Local
                bc.addErrorMessage("Fono convencional:Debe tener al menos 9 digitos");
                fonoLocal = 0;
            } else {
                fonoLocal = 1;
            }
        }

        if (fonoCelular.equals("")) {
            fonoLocal = 0;
            bc.addErrorMessage("Telefono Celular !No puede estar vacio!");
        } else {
            if (!fonoCelular.equals("") && (fonoCelular.length() < 10)) {//Valida Fono Celular
                bc.addErrorMessage("Fono celular:Debe tener al menos 10 digitos");
                fonoCel = 0;
            } else {
                fonoCel = 1;
            }
        }

        if (fonoLocal + fonoCel == 2) {
            pasaValidacion = true;
        }
        return pasaValidacion;
    }

    public boolean pasaNumElementos() {
        boolean pasaValidacion = false;
        if (!conNumMiembrosHog.equals("") && Integer.parseInt(conNumMiembrosHog) <= 0) {//Valida Fono Local
            bc.addErrorMessage("El número de pesonas debe ser mayor a cero");
        } else {
            pasaValidacion = true;
        }
        return pasaValidacion;
    }

    public boolean pasaValResultadoEntrevistaOtraRazon() {
        boolean pasaValidacion = false;

        if (cabeceraActual.getEstado1().equals("09")) {
            if (resulEntrevistaOtraRazon == null) {
                bc.addErrorMessage("!Resultado de la entrevista 'Otra Razón': Seleccione la que corresponda!");
            } else {
                pasaValidacion = true;
            }
        } else {
            pasaValidacion = true;
        }

        return pasaValidacion;
    }

    public boolean pasaValModoCaptura() {
        boolean pasaValidacion = false;
        // System.out.println("Forma:" + cabeceraActual.getEstado2() + "Modo:" + cabeceraActual.getEstado4());
        if (cabeceraActual.getEstado2() == null) {
            bc.addErrorMessage("!Forma de Captura:Obligatorio!");
        } else {
            if (cabeceraActual.getEstado2().equals("02") && cabeceraActual.getEstado4() == null) {
                bc.addErrorMessage("Forma de Captura: Llamada Telefónica, especifique (Uso de Formulario o SIPE)");
            } else {
                pasaValidacion = true;
            }
        }
        return pasaValidacion;
    }

    public int getActivaOtroCual() {
        return activaOtroCual;
    }

    public void setActivaOtroCual(int activaOtroCual) {
        this.activaOtroCual = activaOtroCual;
    }

    // <editor-fold defaultstate="collapsed" desc=" GETTERSYSETTERS ">
    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }

    public UsuarioControlador getUc() {
        return uc;
    }

    public void setUc(UsuarioControlador uc) {
        this.uc = uc;
    }

    public VistaControlador getVc() {
        return vc;
    }

    public void setVc(VistaControlador vc) {
        this.vc = vc;
    }

    public List<CaptCargaControl> getListaCargaPendiente() {
        return listaCargaPendiente;
    }

    public void setListaCargaPendiente(List<CaptCargaControl> listaCargaPendiente) {
        this.listaCargaPendiente = listaCargaPendiente;
    }

    public List<CaptCabecera> getListaFormulariosCreados() {
        return listaFormulariosCreados;
    }

    public void setListaFormulariosCreados(List<CaptCabecera> listaFormulariosCreados) {
        this.listaFormulariosCreados = listaFormulariosCreados;
    }

    public Integer getParamIdOpeEst() {
        Integer idOe = (Integer) uc.getSession().getAttribute("id_ope_est");
        if (idOe != null) {
            paramIdOpeEst = idOe;
        }
        return paramIdOpeEst;
    }

    public void setParamIdOpeEst(Integer paramIdOpeEst) {
        this.paramIdOpeEst = paramIdOpeEst;
    }

    public List<AdmOperacionEstadistica> getListaOpeEst() {
        return listaOpeEst;
    }

    public void setListaOpeEst(List<AdmOperacionEstadistica> listaOpeEst) {
        this.listaOpeEst = listaOpeEst;
    }

    public Integer getParamIdPeriodo() {
        return paramIdPeriodo;
    }

    public void setParamIdPeriodo(Integer paramIdPeriodo) {
        this.paramIdPeriodo = paramIdPeriodo;
    }

    public List<AdmPeriodo> getListaPeriodo() {
        return listaPeriodo;
    }

    public void setListaPeriodo(List<AdmPeriodo> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }

    public Integer getParamIdOperativo() {
        return paramIdOperativo;
    }

    public void setParamIdOperativo(Integer paramIdOperativo) {
        this.paramIdOperativo = paramIdOperativo;
    }

    public List<MetOperativo> getListaOperativoPorOpeEstyPeriodo() {
        return listaOperativoPorOpeEstyPeriodo;
    }

    public void setListaOperativoPorOpeEstyPeriodo(List<MetOperativo> listaOperativoPorOpeEstyPeriodo) {
        this.listaOperativoPorOpeEstyPeriodo = listaOperativoPorOpeEstyPeriodo;
    }

    public Integer getParamIdFormulario() {
        return paramIdFormulario;
    }

    public void setParamIdFormulario(Integer paramIdFormulario) {
        this.paramIdFormulario = paramIdFormulario;
    }

    public List<MetFormulario> getListaFormulariosPorOperativo() {
        return listaFormulariosPorOperativo;
    }

    public void setListaFormulariosPorOperativo(List<MetFormulario> listaFormulariosPorOperativo) {
        this.listaFormulariosPorOperativo = listaFormulariosPorOperativo;
    }

    public String getParamTipo() {
        return paramTipo;
    }

    public void setParamTipo(String paramTipo) {
        this.paramTipo = paramTipo;
    }

    public List<SelectItem> getListaTipos() {
        return listaTipos;
    }

    public void setListaTipos(List<SelectItem> listaTipos) {
        this.listaTipos = listaTipos;
    }

    public String getParamZonal() {
        return paramZonal;
    }

    public void setParamZonal(String paramZonal) {
        this.paramZonal = paramZonal;
    }

    public List<SelectItem> getListaZonal() {
        return listaZonal;
    }

    public void setListaZonal(List<SelectItem> listaZonal) {
        this.listaZonal = listaZonal;
    }

    public String getParamClave() {
        return paramClave;
    }

    public void setParamClave(String paramClave) {
        this.paramClave = paramClave;
    }

    public String getParamControl1() {
        return paramControl1;
    }

    public void setParamControl1(String paramControl1) {
        this.paramControl1 = paramControl1;
    }

    public Integer getNumElementos() {
        return numElementos;
    }

    public void setNumElementos(Integer numElementos) {
        this.numElementos = numElementos;
    }

    public CaptCabecera getCabeceraActual() {
        return cabeceraActual;
    }

    public void setCabeceraActual(CaptCabecera cabeceraActual) {
        this.cabeceraActual = cabeceraActual;
    }

    public CaptCargaControl getCargaActual() {
        return cargaActual;
    }

    public void setCargaActual(CaptCargaControl cargaActual) {
        this.cargaActual = cargaActual;
    }

    public List<SelectItem> getListaResultadoEntrevista() {
        return listaResultadoEntrevista;
    }

    public void setListaResultadoEntrevista(List<SelectItem> listaResultadoEntrevista) {
        this.listaResultadoEntrevista = listaResultadoEntrevista;
    }

    public List<SelectItem> getListaResultadoEntrevistaOtraRazon() {
        return listaResultadoEntrevistaOtraRazon;
    }

    public void setListaResultadoEntrevistaOtraRazon(List<SelectItem> listaResultadoEntrevistaOtraRazon) {
        this.listaResultadoEntrevistaOtraRazon = listaResultadoEntrevistaOtraRazon;
    }

    public List<CaptCargaControlEquipo> getListaCargaControlEquipo() {
        return listaCargaControlEquipo;
    }

    public void setListaCargaControlEquipo(List<CaptCargaControlEquipo> listaCargaControlEquipo) {
        this.listaCargaControlEquipo = listaCargaControlEquipo;
    }

    public String getAtrPeriodo() {
        return atrPeriodo;
    }

    public void setAtrPeriodo(String atrPeriodo) {
        this.atrPeriodo = atrPeriodo;
    }

    public String getAtrCalle() {
        return atrCalle;
    }

    public void setAtrCalle(String atrCalle) {
        this.atrCalle = atrCalle;
    }

    public String getAtrLocalidad() {
        return atrLocalidad;
    }

    public void setAtrLocalidad(String atrLocalidad) {
        this.atrLocalidad = atrLocalidad;
    }

    public String getAtrLote() {
        return atrLote;
    }

    public void setAtrLote(String atrLote) {
        this.atrLote = atrLote;
    }

    public String getAtrBloque() {
        return atrBloque;
    }

    public void setAtrBloque(String atrBloque) {
        this.atrBloque = atrBloque;
    }

    public String getAtrPatio() {
        return atrPatio;
    }

    public void setAtrPatio(String atrPatio) {
        this.atrPatio = atrPatio;
    }

    public String getAtrPiso() {
        return atrPiso;
    }

    public void setAtrPiso(String atrPiso) {
        this.atrPiso = atrPiso;
    }

    public String getAtrCasa() {
        return atrCasa;
    }

    public void setAtrCasa(String atrCasa) {
        this.atrCasa = atrCasa;
    }

    public String getAtrDepar() {
        return atrDepar;
    }

    public void setAtrDepar(String atrDepar) {
        this.atrDepar = atrDepar;
    }

    public String getConNumMiembrosHog() {
        return conNumMiembrosHog;
    }

    public void setConNumMiembrosHog(String conNumMiembrosHog) {
        this.conNumMiembrosHog = conNumMiembrosHog;
    }

    public String getConNumNoMienbrosHog() {
        return conNumNoMienbrosHog;
    }

    public void setConNumNoMienbrosHog(String conNumNoMienbrosHog) {
        this.conNumNoMienbrosHog = conNumNoMienbrosHog;
    }

    public String getConNomJefeHogar() {
        return conNomJefeHogar;
    }

    public void setConNomJefeHogar(String conNomJefeHogar) {
        this.conNomJefeHogar = conNomJefeHogar;
    }

    public CaptInfoMuestra getInfoMuestra() {
        return infoMuestra;
    }

    public void setInfoMuestra(CaptInfoMuestra infoMuestra) {
        this.infoMuestra = infoMuestra;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public CaptCabecera getCabeceraGuardada() {
        return cabeceraGuardada;
    }

    public void setCabeceraGuardada(CaptCabecera cabeceraGuardada) {
        this.cabeceraGuardada = cabeceraGuardada;
    }

    public String getParamControl2() {
        return paramControl2;
    }

    public void setParamControl2(String paramControl2) {
        this.paramControl2 = paramControl2;
    }

    public String getNumHogares() {
        return numHogares;
    }

    public void setNumHogares(String numHogares) {
        this.numHogares = numHogares;
    }

    public CaptCabecera getCabeceraNF() {
        return cabeceraNF;
    }

    public void setCabeceraNF(CaptCabecera cabeceraNF) {
        this.cabeceraNF = cabeceraNF;
    }

    public String getCodFrmN() {
        return codFrmN;
    }

    public void setCodFrmN(String codFrmN) {
        this.codFrmN = codFrmN;
    }

    public Integer getNumMiembro() {
        return numMiembro;
    }

    public void setNumMiembro(Integer numMiembro) {
        this.numMiembro = numMiembro;
    }

    public String getReCreada() {
        return reCreada;
    }

    public void setReCreada(String reCreada) {
        this.reCreada = reCreada;
    }

    public String getNuevoHogar() {
        return nuevoHogar;
    }

    public void setNuevoHogar(String nuevoHogar) {
        this.nuevoHogar = nuevoHogar;
    }

    public BaseCapturaControlador getBcc() {
        return bcc;
    }

    public void setBcc(BaseCapturaControlador bcc) {
        this.bcc = bcc;
    }

    public List<CaptCargaControl> getListaFormulariosPorHogar() {
        return listaFormulariosPorHogar;
    }

    public void setListaFormulariosPorHogar(List<CaptCargaControl> listaFormulariosPorHogar) {
        this.listaFormulariosPorHogar = listaFormulariosPorHogar;
    }

    public CaptCargaControl getCargaCarControlHog() {
        return cargaCarControlHog;
    }

    public void setCargaCarControlHog(CaptCargaControl cargaCarControlHog) {
        this.cargaCarControlHog = cargaCarControlHog;
    }

    public String getAliasOpeEstActual() {
        return aliasOpeEstActual;
    }

    public void setAliasOpeEstActual(String aliasOpeEstActual) {
        this.aliasOpeEstActual = aliasOpeEstActual;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" METODOS TEMPORALES">
    /**
     * Este método debe ser programado y llamado en cache por cada formulario
     *
     * @return
     */
    private List<CaptVarV> listarVariablesVPorFormulario(Integer idFormulario) {
        List<CaptVarV> vars = new ArrayList<>();
        try {
            vars = bc.getCaptDetalleVServicioRemote().listarVariablesVPorFormulario(idFormulario);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return vars;
    }

    /**
     * Este método debe ser programado y llamado en cache por cada formulario
     *
     * @return
     */
    private List<CaptFilaH> listarFilasHPorFormulario(Integer idFormulario) {
        List<CaptFilaH> filas = new ArrayList<>();
        try {
            filas = bc.getCaptDetalleHServicioRemote().listarFilasHPorFormulario(idFormulario);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return filas;
    }
//</editor-fold>

    public MueMuestraDetalle getObjMueDetalleActual() {
        return objMueDetalleActual;
    }

    public void setObjMueDetalleActual(MueMuestraDetalle objMueDetalleActual) {
        this.objMueDetalleActual = objMueDetalleActual;
    }

    public String getFonoConvencional() {
        return fonoConvencional;
    }

    public void setFonoConvencional(String fonoConvencional) {
        this.fonoConvencional = fonoConvencional;
    }

    public String getFonoCelular() {
        return fonoCelular;
    }

    public void setFonoCelular(String fonoCelular) {
        this.fonoCelular = fonoCelular;
    }

    public List<Object[]> getListaContactos() {
        return listaContactos;
    }

    public String getConglomeradoSelected() {
        return conglomeradoSelected;
    }

    public void setConglomeradoSelected(String conglomeradoSelected) {
        this.conglomeradoSelected = conglomeradoSelected;
    }

    public List<String> getListaConglomerados() {
        return listaConglomerados;
    }

    public void setListaConglomerados(List<String> listaConglomerados) {
        this.listaConglomerados = listaConglomerados;
    }

    public void setListaContactos(List<Object[]> listaContactos) {
        this.listaContactos = listaContactos;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getConglomeradoInput() {
        return conglomeradoInput;
    }

    public void setConglomeradoInput(String conglomeradoInput) {
        this.conglomeradoInput = conglomeradoInput;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // cambio vic 21/05/2020
    public List<Object[]> getLstJornadas() {
        return lstJornadas;
    }

    public void setLstJornadas(List<Object[]> lstJornadas) {
        this.lstJornadas = lstJornadas;
    }

    public Integer getJornadaSelect() {
        return jornadaSelect;
    }

    public void setJornadaSelect(Integer jornadaSelect) {
        this.jornadaSelect = jornadaSelect;
    }

    public List<Object[]> getLstConglomerados() {
        return lstConglomerados;
    }

    public void setLstConglomerados(List<Object[]> lstConglomerados) {
        this.lstConglomerados = lstConglomerados;
    }

    // fin cambio vic 21/05/2020
    public List<SelectItem> getListaFormaCaptura() {
        return listaFormaCaptura;
    }

    public void setListaFormaCaptura(List<SelectItem> listaFormaCaptura) {
        this.listaFormaCaptura = listaFormaCaptura;
    }

    public List<SelectItem> getListaFormaCapturaTelef() {
        return listaFormaCapturaTelef;
    }

    public void setListaFormaCapturaTelef(List<SelectItem> listaFormaCapturaTelef) {
        this.listaFormaCapturaTelef = listaFormaCapturaTelef;
    }

    public String getCedEncuestador() {
        return cedEncuestador;
    }

    public void setCedEncuestador(String cedEncuestador) {
        this.cedEncuestador = cedEncuestador;
    }

    public String getCedSupervisor() {
        return cedSupervisor;
    }

    public void setCedSupervisor(String cedSupervisor) {
        this.cedSupervisor = cedSupervisor;
    }

    public String getCedCritico() {
        return cedCritico;
    }

    public void setCedCritico(String cedCritico) {
        this.cedCritico = cedCritico;
    }

    public String getCedDigitador() {
        return cedDigitador;
    }

    public void setCedDigitador(String cedDigitador) {
        this.cedDigitador = cedDigitador;
    }

    public String getCedValidador() {
        return cedValidador;
    }

    public void setCedValidador(String cedValidador) {
        this.cedValidador = cedValidador;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNuMunicipio() {
        return nuMunicipio;
    }

    public void setNuMunicipio(String nuMunicipio) {
        this.nuMunicipio = nuMunicipio;
    }

    public String getNomJefeHogar() {
        return nomJefeHogar;
    }

    public void setNomJefeHogar(String nomJefeHogar) {
        this.nomJefeHogar = nomJefeHogar;
    }

    public String getNomInformante() {
        return nomInformante;
    }

    public void setNomInformante(String nomInformante) {
        this.nomInformante = nomInformante;
    }

    public String getCodInformante() {
        return codInformante;
    }

    public void setCodInformante(String codInformante) {
        this.codInformante = codInformante;
    }

    public String getVivReemplaza() {
        return vivReemplaza;
    }

    public void setVivReemplaza(String vivReemplaza) {
        this.vivReemplaza = vivReemplaza;
    }

    public String getConFechaVisita1() {
        return conFechaVisita1;
    }

    public void setConFechaVisita1(String conFechaVisita1) {
        this.conFechaVisita1 = conFechaVisita1;
    }

    public String getEstadoForm() {
        return estadoForm;
    }

    public void setEstadoForm(String estadoForm) {
        this.estadoForm = estadoForm;
    }

    public String getResulEntrevistaOtraRazon() {
        return resulEntrevistaOtraRazon;
    }

    public void setResulEntrevistaOtraRazon(String resulEntrevistaOtraRazon) {
        this.resulEntrevistaOtraRazon = resulEntrevistaOtraRazon;
    }

    public Integer getNumHombres() {
        return numHombres;
    }

    public void setNumHombres(Integer numHombres) {
        this.numHombres = numHombres;
    }

    public Integer getNumMujeres() {
        return numMujeres;
    }

    public void setNumMujeres(Integer numMujeres) {
        this.numMujeres = numMujeres;
    }
    
}
