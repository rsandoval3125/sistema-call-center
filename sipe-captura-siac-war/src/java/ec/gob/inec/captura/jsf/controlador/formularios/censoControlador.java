/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.formularios;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonParser;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.captura.clases.CaptElementoControl;
import ec.gob.inec.captura.clases.CaptFilaH;
import ec.gob.inec.captura.clases.CaptSeccionH;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleV;
import ec.gob.inec.captura.ejb.entidades.CaptEstado;
import ec.gob.inec.captura.ejb.entidades.CaptObservacion;
//import ec.gob.inec.captura.jsf.controlador.inicios.InicioCapturaCenso;
import ec.gob.inec.captura.jsf.controlador.modulos.BaseCapturaControlador;
import ec.gob.inec.captura.jsf.controlador.modulos.MetodosCaptura;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import ec.gob.inec.muestra.ejb.entidades.AeArea;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import org.apache.commons.lang.SerializationUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
//import org.primefaces.context.RequestContext;
//import org.primefaces.json.JSONArray;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.RowEditEvent;
import org.primefaces.shaded.json.JSONArray;

/**
 *
 * @author rpua
 */
@ManagedBean
@ViewScoped
public class censoControlador implements Serializable {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(censoControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{baseCapturaControlador}")
    private BaseCapturaControlador bcc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vc;
    private CaptCabecera cabeceraActual;
    private CaptCargaControl cargaActual;
    private Integer elementoActual;
    private List<CaptEstado> listaEstados;
    private List<CaptObservacion> listaObservaciones;

    private List<CaptElementoControl> listaElementosControl;
    private List<CaptElementoControl> listaElementosControlVal;

    private List<CaptVarV> listaEnemdu;

    //Para la información del formulario
    private String conglomerado;
    private String zona;
    private String sector;
    private String manzana;
    private String edificio;
    private String vivienda;
    private String hogar;
    private Integer posicionNuevoElemento;
    private boolean verificarCheck;
    private boolean integridadValidada;
    private boolean varOculta;

    private HashMap<String, String> mapaVariables;
    private List<Map<String, Object>> listaElementosControlEnemdu;
    private JSONArray listaSaltos;
    private JSONArray listaValidaciones;
    private boolean integridadSup;
    private List<Map<String, String>> listaInicial;
    List<censoControlador.VariablesPrimerasPreguntas> primerasPreguntas;

    private List<CaptVarV> listaCenso;
    private boolean registro;

    //Emigracion
    private CaptDetalleH captDetalleH;
    private List<CaptDetalleH> listaDetalleH;
    private boolean edicion;
    private boolean registroEmigracion;
    private Date fechaRegistro;

    //Mortalidad
    private boolean registroMortalidad;
    private String estadoProc;
    private Integer ordenCaptH;

    //paises
    private MetCatalogo paisSelected;
    private List<MetCatalogo> listaPaises;
    private List<MetCatalogo> listaPaisesP7P8;
    private MetCatalogo paisSelectedP7;
    private MetCatalogo paisSelectedP8;
    private MetTipoCatalogo tipoCatalogoPaises;
    private MetTipoCatalogo tipoCatalogoProv;
    private MetTipoCatalogo tipoCatalogoCanton;
    private MetTipoCatalogo tipoCatalogoParr;

    //Provincias-Cantones-Parroquias
    MetCatalogo provinciaSelectedP7;
    MetCatalogo cantonSelectedP7;
    MetCatalogo parroquiaSelectedP7;
    MetCatalogo provinciaSelectedP8;
    MetCatalogo cantonSelectedP8;
    MetCatalogo parroquiaSelectedP8;
    private List<MetCatalogo> listaProvinciasP7;
    private List<MetCatalogo> listaCantonesP7;
    private List<MetCatalogo> listaParroquiasP7;
    private List<MetCatalogo> listaProvinciasP8;
    private List<MetCatalogo> listaCantonesP8;
    private List<MetCatalogo> listaParroquiasP8;

    //Idiomas y Nacionalidades
    private MetCatalogo idiomaSelected;
    private MetCatalogo nacionalidadSelected;
    private List<MetCatalogo> listaIdiomas;
    private List<MetCatalogo> listaNacionalidades;
    private MetTipoCatalogo tipoCatalogoIdiomas;
    private MetTipoCatalogo tipoCatalogoNacionalidades;

    //Calculo de hijos nacidos vivos
    private Integer numHombresNacidos;
    private Integer numMujeresNacidos;
    private Integer totalNacidos;
    private Integer numHombresVivos;
    private Integer numMujeresVivos;
    private Integer totalVivos;

    //Calcular hombres y mujeres en hogar
    private Integer numHombresHogar;
    private Integer numMujeresHogar;
    private Integer numTotalHogar;

    //variables validaciones
    private boolean banderaExisteVivienda;
    private boolean banderaAutocenso;
    private boolean banderaVParticular;
    private boolean banderaVColectiva;

    //variables para guaradar info de S4
    private String opcTipoVivienda;
    private String opcViviendaParticular;
    private String opcViviendaColectiva;

    private List<String> listaOrdenRegistro;
    private boolean habilitaCrearMiembro;
    //Observaciones
    private String sec;
    private String pre;
    private String obs;
    private String tipErr;
    private List<CaptObservacion> lstObs;
    private String ver;
    private String Miembros;

    private String[] secciones = new String[]{"1", "2", "3", "4", "5", "6", "CED", "ced"};
    private String[] pregS1 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "8.1", "8.2", "8a", "8.3", "8.4", "8.5", "9", "10", "11", "12", "15", "15A"};
    private String[] pregS2 = new String[]{"20", "21", "22", "23", "23.1", "23.2", "23.3", "24", "25", "25.1", "26", "27", "28", "29", "29A", "30", "31", "32", "33", "34", "34.1", "35", "35.1", "36", "37", "38", "38.1", "39", "40", "40A", "41", "42", "42.1", "42.2", "42A", "43", "44", "44A", "44B", "44C", "44D", "44E", "44F", "44G", "44H", "44I", "44J", "44K", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "54A", "55", "56", "57", "58", "58A", "59", "60", "60A", "60B", "60C", "60D", "60E", "60F", "60G", "60H", "60I", "60J", "60K", "61B1"};
    private String[] pregS3 = new String[]{"63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "75A", "75A1", "75A2", "75A3", "75A4", "75A5", "76", "76D", "76A", "76A1", "76A2", "76A3", "76A4", "76A5", "76A6", "77", "78"};
    private String[] pregS4 = new String[]{"1", "2", "3"};
    private String[] pregS5 = new String[]{"1", "2", "3", "4", "5", "6", "7", "7A", "7B", "8", "9", "9a", "9b", "10", "10.1", "10.2", "10a", "11", "12", "13", "14", "14.1", "14.2", "14.3", "14.4"};
    private String[] pregS6 = new String[]{"1", "2", "3", "4a", "5a", "5b", "6a", "6b", "7", "8a", "9", "10", "11", "12", "13", "14", "15a", "16a", "17a", "18", "19", "20a", "21a"};
    private String[] pregSCED = new String[]{"1"};

    /**
     * Creates a new instance of EnemduControlador2107
     */
    public censoControlador() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" METODOS ">
    @PostConstruct
    public void inicializar() {
        try {
            primerasPreguntas = new ArrayList<>();
            cabeceraActual = new CaptCabecera();
            cargaActual = new CaptCargaControl();
            listaEstados = new ArrayList<>();
            listaElementosControl = new ArrayList<>();
            listaObservaciones = new ArrayList<>();
            listaEnemdu = new ArrayList<>();
            lstObs = new ArrayList<>();
            elementoActual = 1;
            varOculta = true;
            listaElementosControlEnemdu = new ArrayList<>();
            obtenerFormulario();
            listarValiadciones();
            listarSaltos();
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);
            iniciaObs();
            captDetalleH = new CaptDetalleH();
            edicion = false;
            listaDetalleH = new ArrayList<>();
            registroEmigracion = false;
            registroMortalidad = false;
            fechaRegistro = new Date();
            estadoProc = "0";
            listaProvinciasP7 = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_DPA_PROV");
            listaProvinciasP8 = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_DPA_PROV");
            tipoCatalogoProv = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_PROV");
            tipoCatalogoCanton = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_CANTON");
            tipoCatalogoParr = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_PARR");
            tipoCatalogoPaises = new MetTipoCatalogo();
            listaPaisesP7P8 = new ArrayList<>();
            banderaExisteVivienda = false;
            banderaAutocenso = false;
            banderaVParticular = false;
            banderaVColectiva = false;
            seteaValoresSincronizacionProvincias();
            seteaValoresSincronizacionPaises();
            seteaValoresSincroIdiomaNacionalidad();
            contieneSelectProv();
            onPais();
            contieneSelectPaises();
            //registrarPersonas();
            onIdioma();
            contieneIdioma();
            onNacionalidad();
            contieneNacionalidad();
            listarOrdenRegistro();
            habilitarCreaMiembro();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerFormulario() {
        try {
            CaptCabecera objetoCab = (CaptCabecera) uc.getSession().getAttribute("capt_cabecera_actual");
            if (objetoCab != null) {
                if (objetoCab.getIdCab() != null) {
                    if (cabeceraActual.getIdCab() == null || (!cabeceraActual.getIdCab().equals(objetoCab.getIdCab()))) {
                        cabeceraActual = objetoCab;
                        cargaActual = cabeceraActual.getCodCarCon();
                    }
                    // divDPA();
                    cargarInformacionVDeCabeceraYElemento(cabeceraActual, elementoActual);
                }
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cargarInformacionVDeCabeceraYElemento(CaptCabecera cab, Integer numElemento) {
        try {
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cab.getIdCab(), numElemento, cab.getCodCarCon().getCodFormulario().getIdFormulario());

            listaEstados = bcc.listarEstadosFormulario(cab.getIdCab());

            // listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cab.getIdCab());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public List<Map<String, Object>> getListaElementosControlEnemdu() {
        return listaElementosControlEnemdu;
    }

    public void setListaElementosControlEnemdu(List<Map<String, Object>> listaElementosControlEnemdu) {
        this.listaElementosControlEnemdu = listaElementosControlEnemdu;
    }

    public void guardarObservacion() {
        boolean existeSeccion = false;
        boolean existePregunta = false;

        try {
            if (uc.usuarioTieneRol("ROL_VALID")) {
                if (sec.equals("") || pre.equals("") || obs.equals("") || tipErr.equals("")) {
                    bc.addErrorMessage("Sección, pregunta, observación y tipo de error, son requeridos");
                } else {
                    for (int i = 0; i < secciones.length; i++) {
                        if (sec.equals(secciones[i])) {
                            existeSeccion = true;
                            break;
                        }
                    }

                    if (existeSeccion) {
                        switch (sec) {
                            case "1":
                                for (int i = 0; i < pregS1.length; i++) {
                                    if (pre.toUpperCase().equals(pregS1[i].toUpperCase())) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "2":
                                for (int i = 0; i < pregS2.length; i++) {
                                    if (pre.equals(pregS2[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "3":
                                for (int i = 0; i < pregS3.length; i++) {
                                    if (pre.equals(pregS3[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "4":
                                for (int i = 0; i < pregS4.length; i++) {
                                    if (pre.equals(pregS4[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "5":
                                for (int i = 0; i < pregS5.length; i++) {
                                    if (pre.equals(pregS5[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "6":
                                for (int i = 0; i < pregS6.length; i++) {
                                    if (pre.equals(pregS6[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "CED":
                                for (int i = 0; i < pregSCED.length; i++) {
                                    if (pre.equals(pregSCED[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "ced":
                                for (int i = 0; i < pregSCED.length; i++) {
                                    if (pre.equals(pregSCED[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                        }

                        if (existePregunta) {
                            CaptObservacion obsActual = new CaptObservacion();
                            obsActual.setObservacion(obs);
                            obsActual.setNumDet(elementoActual);
                            obsActual.setTipo(tipErr);
                            obsActual.setReferencia(uc.getUsuarioActual().getNombre());
                            obsActual.setFechahoraEdicion(new Date());
                            obsActual.setCodCab(cabeceraActual);
                            obsActual.setClave("Sec:" + sec + " Preg:" + pre);
                            bc.getCaptObservacionServicioRemote().crearCaptObservacion(obsActual);
                            bc.addSuccessMessage("Registro Guardado");
                            PrimeFaces requestContext = PrimeFaces.current();
                            requestContext.executeScript("PF('dlgNewMiembro1').hide();");
                            requestContext.ajax().update("frmCaptura:obsNew");
                            iniciaObs();
                            lstObs = bc.getCaptObservacionServicioRemote().listarObservacionesPorCabYNumDetXTipoDif(cabeceraActual.getIdCab(), elementoActual, "0");
                        } else {
                            bc.addErrorMessage("Ingrese pregunta correcta");
                        }
                    } else {
                        bc.addErrorMessage("Ingrese sección correcta");
                    }
                }
            } else {
                if (sec.equals("") || pre.equals("") || obs.equals("")) {
                    bc.addErrorMessage("Sección, pregunta y observación son requeridos");
                } else {
                    for (int i = 0; i < secciones.length; i++) {

                        if (sec.equals(secciones[i])) {
                            existeSeccion = true;
                            break;
                        }
                    }

                    if (existeSeccion) {
                        switch (sec) {
                            case "1":
                                for (int i = 0; i < pregS1.length; i++) {
                                    if (pre.equals(pregS1[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "2":
                                for (int i = 0; i < pregS2.length; i++) {
                                    if (pre.equals(pregS2[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "3":
                                for (int i = 0; i < pregS3.length; i++) {
                                    if (pre.equals(pregS3[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "4":
                                for (int i = 0; i < pregS4.length; i++) {
                                    if (pre.equals(pregS4[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "5":
                                for (int i = 0; i < pregS5.length; i++) {
                                    if (pre.equals(pregS5[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "6":
                                for (int i = 0; i < pregS6.length; i++) {
                                    if (pre.equals(pregS6[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "CED":
                                for (int i = 0; i < pregSCED.length; i++) {
                                    if (pre.equals(pregSCED[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                            case "ced":
                                for (int i = 0; i < pregSCED.length; i++) {
                                    if (pre.equals(pregSCED[i])) {
                                        existePregunta = true;
                                        break;
                                    }
                                }
                                break;
                        }

                        if (existePregunta) {
                            CaptObservacion obsActual = new CaptObservacion();
                            obsActual.setObservacion(obs);
                            obsActual.setTipo("0");
                            obsActual.setNumDet(elementoActual);
                            obsActual.setReferencia(uc.getUsuarioActual().getNombre());
                            obsActual.setFechahoraEdicion(new Date());
                            obsActual.setCodCab(cabeceraActual);
                            obsActual.setClave("Sec:" + sec + " Preg:" + pre);
                            bc.getCaptObservacionServicioRemote().crearCaptObservacion(obsActual);
                            bc.addSuccessMessage("Registro Guardado");
                            PrimeFaces requestContext = PrimeFaces.current();
                            requestContext.executeScript("PF('dlgNewMiembro1').hide();");
                            requestContext.ajax().update("frmCaptura:obsNew");
                            iniciaObs();
                            lstObs = bc.getCaptObservacionServicioRemote().listarObservacionesPorCabYNumDetYTipo(cabeceraActual.getIdCab(), elementoActual, "0");
                        } else {
                            bc.addErrorMessage("Ingrese pregunta correcta");
                        }
                    } else {
                        bc.addErrorMessage("Ingrese sección correcta");
                    }

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
            bc.addErrorMessage("Error registro no guardado");
        }
    }

    public void iniciaObs() {
        sec = "";
        pre = "";
        obs = "";
        tipErr = "";
        ver = "";
    }

    public void abriObs() {
        PrimeFaces requestContext = PrimeFaces.current();
        requestContext.executeScript("PF('dlgNewMiembro1').show();");
        requestContext.ajax().update("frmCaptura:obsNew");
        iniciaObs();
    }

    public void eliminarObs(CaptObservacion obs) {
        try {
            bc.getCaptObservacionServicioRemote().eliminarCaptObservacion(obs);
            bc.addSuccessMessage("Registro eliminado");
            lstObs = bc.getCaptObservacionServicioRemote().listarObservacionesPorCabYNumDetYTipo(cabeceraActual.getIdCab(), elementoActual, "");
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            CaptObservacion dat = new CaptObservacion();
            dat = (CaptObservacion) event.getObject();
            dat.setFechahoraEdicion(new Date());
            bc.addSuccessMessage("Registro Actualizado");
            bc.getCaptObservacionServicioRemote().editarCaptObservacion(dat);
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
            bc.addErrorMessage("Error");
        }

    }

    public void onRowCancel(RowEditEvent event) {
        bc.addWarningMessage("Cancelado");
    }

    public void listarObs() {
        try {
            lstObs = bc.getCaptObservacionServicioRemote().listarObservacionesPorCabYNumDetYTipo(cabeceraActual.getIdCab(), elementoActual, "0");
            ver = "88";
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.executeScript("PF('dlgVerObs').show();");
            
//              PrimeFaces requestContext = PrimeFaces.current();
//                            requestContext.executeScript("PF('dlgNewMiembro1').hide();");
//                            requestContext.ajax().update("frmCaptura:obsNew");
            
            
        } catch (Exception ex) {
            System.out.println("Error");
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarObsVal() {
        try {
            lstObs = bc.getCaptObservacionServicioRemote().listarObservacionesPorCabYNumDetXTipoDif(cabeceraActual.getIdCab(), elementoActual, "0");
            ver = "99";
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.executeScript("PF('dlgVerObs').show();");
            
  

        } catch (Exception ex) {
            System.out.println("Error");
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarMortalidad() {
        refrescar();
        listarMortalidad();
    }

    public void guardarMortalidad() {
        try {
            if (validarCantidadMortalidad() && validaIngresoMortalidad()) {

                MetSeccion secSel = new MetSeccion();
                secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FM_C1");
                captDetalleH.setCodCab(cabeceraActual);
                captDetalleH.setFechahoraEdicion(new Date());
                captDetalleH.setClave(cabeceraActual.getClave());
                captDetalleH.setCodSeccion(secSel);
                //captDetalleH.setEstadoProc("1");
                //int cantidadMortalidad = Integer.valueOf(listaEnemdu.get(49).getValor());
                //int cantidadLstMortalidad = listaDetalleH.size();
                if (edicion == false) {
                    //if (cantidadMortalidad != cantidadLstMortalidad) {
                    bc.getCaptDetalleHServicioRemote().crearDetalleH(captDetalleH);
                    bc.addSuccessMessage("Persona Mortalidad guardada");
                    refrescar();
                    listarMortalidad();
                } else {
                    bc.getCaptDetalleHServicioRemote().editarDetalleH(captDetalleH);
                    bc.addSuccessMessage("Persona Mortalidad actualizada");
                    refrescar();
                    listarMortalidad();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validaIngresoMortalidad() {
        boolean vPregunta1 = false;
        boolean vPregunta2 = false;
        boolean vPregunta3Mes = false;
        boolean vPregunta3Anio = false;
        boolean vPregunta4 = false;
        boolean vPregunta5 = false;

        try {
            //Pregunta 1
            if (!captDetalleH.getVal01().equals("") && !captDetalleH.getVal02().equals("") && captDetalleH.getVal03().equals("")) {
                vPregunta1 = true;
            } else if (captDetalleH.getVal01().equals("") && captDetalleH.getVal02().equals("") && !captDetalleH.getVal03().equals("")) {
                vPregunta1 = true;
            } else if ((!captDetalleH.getVal01().equals("") || !captDetalleH.getVal02().equals("")) && !captDetalleH.getVal03().equals("")) {
                bc.addErrorMessage("Pregunta1: Si ingresa nombre y apellido no debe ingresar opciones de no sabe/no responde, y viceversa");
                vPregunta1 = false;
            } else if ((!captDetalleH.getVal01().equals("") || !captDetalleH.getVal02().equals("")) && captDetalleH.getVal03().equals("")) {
                bc.addErrorMessage("Pregunta1: Complete nombre o apellido");
                vPregunta1 = false;
            } else if (captDetalleH.getVal01().equals("") && captDetalleH.getVal02().equals("") && captDetalleH.getVal03().equals("")) {
                bc.addErrorMessage("Pregunta1: Debe ingresar información");
                vPregunta1 = false;
            }
            //Pregunta 2
            if (!captDetalleH.getVal04().equals("")) {
                vPregunta2 = true;
            } else {
                bc.addErrorMessage("Pregunta2: Debe ingresar información");
                vPregunta2 = false;
            }
            //Pregunta 3 - Mes
            if (!captDetalleH.getVal05().equals("") && captDetalleH.getVal06().equals("")) {
                if ((Integer.valueOf(captDetalleH.getVal05()) < 1) || (Integer.valueOf(captDetalleH.getVal05()) > 12)) {
                    bc.addErrorMessage("Pregunta3: Mes debe estar entre 1 y 12");
                    vPregunta3Mes = false;
                } else if ((Integer.valueOf(captDetalleH.getVal05()) >= 1) && (Integer.valueOf(captDetalleH.getVal05()) <= 12)) {
                    vPregunta3Mes = true;
                }
            } else if (captDetalleH.getVal05().equals("") && !captDetalleH.getVal06().equals("")) {
                vPregunta3Mes = true;
            } else if (!captDetalleH.getVal05().equals("") && !captDetalleH.getVal06().equals("")) {
                bc.addErrorMessage("Pregunta3: Si ingresa Mes no debe ingresar opciones de no sabe/no responde, y viceversa");
                vPregunta3Mes = false;
            } else if (captDetalleH.getVal05().equals("") && captDetalleH.getVal06().equals("")) {
                bc.addErrorMessage("Pregunta3: Debe ingresar información");
                vPregunta3Mes = false;
            }
            //Pregunta 3 - Año
            if (!captDetalleH.getVal07().equals("") && captDetalleH.getVal08().equals("")) {
                if ((Integer.valueOf(captDetalleH.getVal07()) < 2020) || (Integer.valueOf(captDetalleH.getVal07()) > 2021)) {
                    bc.addErrorMessage("Pregunta3: Año debe estar entre 2020 y 2021");
                    vPregunta3Anio = false;
                } else if ((Integer.valueOf(captDetalleH.getVal07()) >= 2020) && (Integer.valueOf(captDetalleH.getVal07()) <= 2021)) {
                    vPregunta3Anio = true;
                }
            } else if (captDetalleH.getVal07().equals("") && !captDetalleH.getVal08().equals("")) {
                vPregunta3Anio = true;
            } else if (!captDetalleH.getVal07().equals("") && !captDetalleH.getVal08().equals("")) {
                bc.addErrorMessage("Pregunta3: Si ingresa Año no debe ingresar opciones de no sabe/no responde, y viceversa");
                vPregunta3Anio = false;
            } else if (captDetalleH.getVal07().equals("") && captDetalleH.getVal08().equals("")) {
                bc.addErrorMessage("Pregunta3: Debe ingresar información");
                vPregunta3Anio = false;
            }
            //Pregunta 4
            if (!captDetalleH.getVal09().equals("") && captDetalleH.getVal10().equals("")) {
                if ((Integer.valueOf(captDetalleH.getVal09()) < 0) || (Integer.valueOf(captDetalleH.getVal09()) > 120)) {
                    bc.addErrorMessage("Pregunta4: Edad debe estar entre 0 y 120");
                    vPregunta4 = false;
                } else if ((Integer.valueOf(captDetalleH.getVal09()) >= 0) && (Integer.valueOf(captDetalleH.getVal09()) <= 120)) {
                    vPregunta4 = true;
                }
            } else if (captDetalleH.getVal09().equals("") && !captDetalleH.getVal10().equals("")) {
                vPregunta4 = true;
            } else if (!captDetalleH.getVal09().equals("") && !captDetalleH.getVal10().equals("")) {
                bc.addErrorMessage("Pregunta4: Si ingresa Edad no debe ingresar opciones de no sabe/no responde, y viceversa");
                vPregunta4 = false;
            } else if (captDetalleH.getVal09().equals("") && captDetalleH.getVal10().equals("")) {
                bc.addErrorMessage("Pregunta4: Debe ingresar información");
                vPregunta4 = false;
            }
            //Pregunta 5
            if (!captDetalleH.getVal11().equals("")) {
                vPregunta5 = true;
            } else {
                bc.addErrorMessage("Pregunta5: Debe ingresar información");
                vPregunta5 = false;
            }

            //System.out.println("1:" + vPregunta1 + " 2:" + vPregunta2 + " 3 Mes:" + vPregunta3Mes + " 3 Anio:" + vPregunta3Anio + " 4:" + vPregunta4 + " 5:" + vPregunta5);
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vPregunta1 && vPregunta2 && vPregunta3Mes && vPregunta3Anio && vPregunta4 && vPregunta5;
    }

    public boolean validarMortalidad() {
        boolean campoMortalidad = true;
        try {
            if ((Integer.valueOf(captDetalleH.getVal05()) < 1) || (Integer.valueOf(captDetalleH.getVal05()) > 12)) {
                bc.addErrorMessage("Mes debe estar entre 1 y 12");
                campoMortalidad = false;
            }
            if ((Integer.valueOf(captDetalleH.getVal05()) < 2020) || (Integer.valueOf(captDetalleH.getVal05()) > 2021)) {
                bc.addErrorMessage("Año debe estar entre 2020 y 2021");
                campoMortalidad = false;
            }
            if ((Integer.valueOf(captDetalleH.getVal06()) < 0) || (Integer.valueOf(captDetalleH.getVal06()) > 120)) {
                bc.addErrorMessage("Edad debe estar entre 0 y 120");
                campoMortalidad = false;
            }

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return campoMortalidad;
    }

    public boolean validarCantidadMortalidad() {
        boolean vCantidadMortalidad = false;
        try {
            //se obtiene la cantidad de mortalidad ingresada
            int ingresoCantidadMortalidad;
            int indexP9S5 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1002"));
            String cantMortalidad = listaEnemdu.get(indexP9S5).getValor();
            if (cantMortalidad == null) {
                ingresoCantidadMortalidad = 0;
            } else {
                ingresoCantidadMortalidad = Integer.parseInt(listaEnemdu.get(indexP9S5).getValor());
            }
            //se obtiene lista de mortalidad
            MetSeccion secMor = new MetSeccion();
            List<CaptDetalleH> listaDetHMortalidad;
            secMor = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FM_C1");
            listaDetHMortalidad = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secMor.getIdSeccion());
            //System.out.println("cantidadM: " + ingresoCantidadMortalidad);

            if (ingresoCantidadMortalidad == 0) {
                bc.addErrorMessage("Primero ingrese la cantidad de fallecidos y Guarde el formulario, antes de registrar personas");
                vCantidadMortalidad = false;
            } else if (ingresoCantidadMortalidad > 0) {
                if (ingresoCantidadMortalidad < listaDetHMortalidad.size()) {
                    bc.addErrorMessage("Personas fallecidas registradas mayor a cantidad ingresada");
                    vCantidadMortalidad = false;
                } else if (ingresoCantidadMortalidad > listaDetHMortalidad.size()) {
                    vCantidadMortalidad = true;
                } else if (ingresoCantidadMortalidad == listaDetHMortalidad.size()) {
                    if (edicion == true) {
                        vCantidadMortalidad = true;
                    } else {
                        bc.addErrorMessage("Cantidad de personas fallecidas completas");
                        vCantidadMortalidad = false;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vCantidadMortalidad;
    }

    public boolean eliminarRegMortalidad() {
        boolean vRegMortalidad = true;
        try {
            int indexP9S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1001"));
            int indexCantP9S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1002"));
            int cont = 0;
            List<CaptDetalleH> detalleHM;
            MetSeccion secSel = new MetSeccion();
            secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FM_C1");
            detalleHM = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secSel.getIdSeccion());
            if (detalleHM.size() > 0 && listaEnemdu.get(indexP9S4).getValor().equals("2") && listaEnemdu.get(indexCantP9S4).getValor().equals("")) {
                for (CaptDetalleH detH : detalleHM) {
                    bc.getCaptDetalleHServicioRemote().eliminarDetalleH(detH);
                    cont++;
                }
                bc.addSuccessMessage("(P10-S4) " + cont + " Registros eliminados en Mortalidad");
            } else if (detalleHM.size() == 0) {
                //bc.addSuccessMessage("(P9-S4) No hay registros para eliminar en Mortalidad");
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vRegMortalidad;
    }

    public void listarMortalidad() {
        try {
            List<CaptDetalleH> detallesH;
            int indiceFin;
            detallesH = bc.getCaptDetalleHServicioRemote().listarDetHPorCab(cabeceraActual.getIdCab());
            Collections.sort(detallesH, (o1, o2) -> o1.getOrden().compareTo(o2.getOrden())); //orden
            MetSeccion secSel = new MetSeccion();
            secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FM_C1");
            listaDetalleH = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secSel.getIdSeccion());
            //listaDetalleH = detallesH.stream().filter(o1 -> o1.getEstadoProc().equals("1")).collect(Collectors.toList()); //filtro
            if (listaDetalleH.isEmpty()) {
                ordenCaptH = 1;
                captDetalleH.setOrden(ordenCaptH);
            } else {
                indiceFin = listaDetalleH.size();
                ordenCaptH = listaDetalleH.get(indiceFin - 1).getOrden();
                ordenCaptH++;
                captDetalleH.setOrden(ordenCaptH);
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salirMortalidad() {
        PrimeFaces requestContext = PrimeFaces.current();
        requestContext.executeScript("PF('dlgMortalidad').hide()");
        

    }

    public void editarMortalidad(CaptDetalleH detalleH) {
        edicion = true;
        captDetalleH = new CaptDetalleH();
        captDetalleH = (CaptDetalleH) SerializationUtils.clone(detalleH);
    }

    public void eliminarPersona(CaptDetalleH detalleH) {
        try {
            bc.getCaptDetalleHServicioRemote().eliminarDetalleH(detalleH);
            bc.addSuccessMessage("Persona de Mortalidad eliminada");
            refrescar();
            listarMortalidad();
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registrarMortalidad() {
        try {
            //edicion = false;
            guardarElemento("G");
            registroMortalidad = true;
            listarMortalidad();
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean eliminarRegEmigracion() {
        boolean vRegEmigracion = true;
        try {
            int indexP10S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1101"));
            int indexCantP10S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1102"));
            int cont = 0;
            List<CaptDetalleH> detalleHE;
            MetSeccion secSel = new MetSeccion();
            secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FE_C1");
            detalleHE = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secSel.getIdSeccion());
            if (detalleHE.size() > 0 && listaEnemdu.get(indexP10S4).getValor().equals("2") && listaEnemdu.get(indexCantP10S4).getValor().equals("")) {
                for (CaptDetalleH detH : detalleHE) {
                    bc.getCaptDetalleHServicioRemote().eliminarDetalleH(detH);
                    cont++;
                }
                bc.addSuccessMessage("(P11-S4) " + cont + " Registros eliminados en Emigración");
            } else if (detalleHE.size() == 0) {
                //bc.addSuccessMessage("(P9-S4) No hay registros para eliminar en Mortalidad");
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vRegEmigracion;
    }

    public void registrarEmigracion() {
        try {
            //edicion = false;
            guardarElemento("G");
            registroEmigracion = true;
            listarEmigracion();
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarEmigracion() {
        try {
            if (validarCantidadEmigracion() && validaIngresoEmigracion()) {

                MetCatalogo pais = new MetCatalogo();
                pais = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(paisSelected.getIdCatalogo());
                MetSeccion secSel = new MetSeccion();
                secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FE_C1");
                captDetalleH.setCodCab(cabeceraActual);
                captDetalleH.setFechahoraEdicion(new Date());
                captDetalleH.setClave(cabeceraActual.getClave());
                captDetalleH.setVal06(pais.getNombre());
                captDetalleH.setVal07(pais.getValor());
                captDetalleH.setCodSeccion(secSel);
                //captDetalleH.setEstadoProc("2");
                if (edicion == false) {
                    bc.getCaptDetalleHServicioRemote().crearDetalleH(captDetalleH);
                    bc.addSuccessMessage("Persona de Emigración guardada");
                    refrescar();
                    listarEmigracion();

                } else {
                    bc.getCaptDetalleHServicioRemote().editarDetalleH(captDetalleH);
                    bc.addSuccessMessage("Persona de Emigración actualizada");
                    refrescar();
                    listarEmigracion();
                }
            }
        } catch (Exception ex) {
            //LOGGER.log(Level.SEVERE, null, ex);
            bc.addErrorMessage("Persona de Emigración no guardada");
        }
    }

    public boolean validaIngresoEmigracion() {
        boolean vPregunta1 = false;
        boolean vPregunta2 = false;
        boolean vPregunta3 = false;
        boolean vPregunta4 = false;

        try {
            //Pregunta 1
            if (!captDetalleH.getVal01().equals("")) {
                vPregunta1 = true;
            } else {
                bc.addErrorMessage("Pregunta1: Debe ingresar información");
                vPregunta1 = false;
            }
            //Pregunta 2
            if (!captDetalleH.getVal02().equals("") && captDetalleH.getVal03().equals("")) {
                if ((Integer.valueOf(captDetalleH.getVal02()) < 0) || (Integer.valueOf(captDetalleH.getVal02()) > 120)) {
                    bc.addErrorMessage("Pregunta2: Edad debe estar entre 0 y 120");
                    vPregunta2 = false;
                } else if ((Integer.valueOf(captDetalleH.getVal02()) >= 0) && (Integer.valueOf(captDetalleH.getVal02()) <= 120)) {
                    vPregunta2 = true;
                }
            } else if (captDetalleH.getVal02().equals("") && !captDetalleH.getVal03().equals("")) {
                vPregunta2 = true;
            } else if (!captDetalleH.getVal02().equals("") && !captDetalleH.getVal03().equals("")) {
                bc.addErrorMessage("Pregunta2: Si ingresa Edad no debe ingresar opciones de no sabe/no responde, y viceversa");
                vPregunta2 = false;
            } else if (captDetalleH.getVal02().equals("") && captDetalleH.getVal03().equals("")) {
                bc.addErrorMessage("Pregunta2: Debe ingresar información");
                vPregunta2 = false;
            }
            //Pregunta 3
            if (!captDetalleH.getVal04().equals("") && captDetalleH.getVal05().equals("")) {
                if ((Integer.valueOf(captDetalleH.getVal04()) < 2010) || (Integer.valueOf(captDetalleH.getVal04()) > 2021)) {
                    bc.addErrorMessage("Pregunta3: Año debe estar entre 2010 y 2021");
                    vPregunta3 = false;
                } else if ((Integer.valueOf(captDetalleH.getVal04()) >= 2010) && (Integer.valueOf(captDetalleH.getVal04()) <= 2021)) {
                    vPregunta3 = true;
                }
            } else if (captDetalleH.getVal04().equals("") && !captDetalleH.getVal05().equals("")) {
                vPregunta3 = true;
            } else if (!captDetalleH.getVal04().equals("") && !captDetalleH.getVal05().equals("")) {
                bc.addErrorMessage("Pregunta3: Si ingresa Año no debe ingresar opciones de no sabe/no responde, y viceversa");
                vPregunta3 = false;
            } else if (captDetalleH.getVal04().equals("") && captDetalleH.getVal05().equals("")) {
                bc.addErrorMessage("Pregunta3: Debe ingresar información");
                vPregunta3 = false;
            }
            //Pregunta 4
            if (paisSelected != null) {
                vPregunta4 = true;
            } else {
                bc.addErrorMessage("Pregunta4: Debe ingresar información");
                vPregunta4 = false;
            }

            System.out.println("1:" + vPregunta1 + " 2:" + vPregunta2 + " 3:" + vPregunta3 + " 4:" + vPregunta4);
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vPregunta1 && vPregunta2 && vPregunta3 && vPregunta4;
    }

    public boolean validarEmigracion() {
        boolean campoEmigracion = true;
        try {
            if ((Integer.valueOf(captDetalleH.getVal02()) < 0) || (Integer.valueOf(captDetalleH.getVal02()) > 120)) {
                bc.addErrorMessage("Edad debe estar entre 0 y 120");
                campoEmigracion = false;
            }
            if ((Integer.valueOf(captDetalleH.getVal03()) < 2010) || (Integer.valueOf(captDetalleH.getVal03()) > 2021)) {
                bc.addErrorMessage("Año debe estar entre 2010 y 2021");
                campoEmigracion = false;
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return campoEmigracion;
    }

    public boolean validarCantidadEmigracion() {
        boolean vCantidadEmigracion = false;
        try {
            //se obtiene la cantidad de emigración ingresada
            int ingresoCantidadEmigracion;
            int indexP10S5 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1102"));
            String cantEmigracion = listaEnemdu.get(indexP10S5).getValor();
            if (cantEmigracion == null) {
                ingresoCantidadEmigracion = 0;
            } else {
                ingresoCantidadEmigracion = Integer.parseInt(listaEnemdu.get(indexP10S5).getValor());
            }
            //se obtiene lista de emigración
            MetSeccion secEmi = new MetSeccion();
            List<CaptDetalleH> listaDetHEmigracion;
            secEmi = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FE_C1");
            listaDetHEmigracion = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secEmi.getIdSeccion());
            System.out.println("cantidadE: " + ingresoCantidadEmigracion);

            if (ingresoCantidadEmigracion == 0) {
                bc.addErrorMessage("Primero ingrese la cantidad de personas que emigrarón y Guarde el formulario, antes de registrar personas");
                vCantidadEmigracion = false;
            } else if (ingresoCantidadEmigracion > 0) {
                if (ingresoCantidadEmigracion < listaDetHEmigracion.size()) {
                    bc.addErrorMessage("Personas de emigración registradas mayor a cantidad ingresada");
                    vCantidadEmigracion = false;
                } else if (ingresoCantidadEmigracion > listaDetHEmigracion.size()) {
                    vCantidadEmigracion = true;
                } else if (ingresoCantidadEmigracion == listaDetHEmigracion.size()) {
                    if (edicion == true) {
                        vCantidadEmigracion = true;
                    } else {
                        bc.addErrorMessage("Cantidad de personas que emigrarón completas");
                        vCantidadEmigracion = false;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vCantidadEmigracion;
    }

    public void listarEmigracion() {
        try {
            List<CaptDetalleH> detallesH;
            int indiceFin;
            detallesH = bc.getCaptDetalleHServicioRemote().listarDetHPorCab(cabeceraActual.getIdCab());
            Collections.sort(detallesH, (o1, o2) -> o1.getOrden().compareTo(o2.getOrden())); //orden
            MetSeccion secSel = new MetSeccion();
            secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FE_C1");
            listaDetalleH = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secSel.getIdSeccion());
            //listaDetalleH = detallesH.stream().filter(o1 -> o1.getEstadoProc().equals("2")).collect(Collectors.toList()); //filtro
            if (listaDetalleH.isEmpty()) {
                ordenCaptH = 1;
                captDetalleH.setOrden(ordenCaptH);
            } else {
                indiceFin = listaDetalleH.size();
                ordenCaptH = listaDetalleH.get(indiceFin - 1).getOrden();
                ordenCaptH++;
                captDetalleH.setOrden(ordenCaptH);
            }
            MetTipoCatalogo tipoCap = new MetTipoCatalogo();
            tipoCap = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_PAIS_6DIG");
            listaPaises = new ArrayList<>();
            listaPaises = bc.getMetCatalogoServicioRemote().listarCatalogosPorTipoCatalogo(tipoCap.getIdTipoCatalogo());
        } catch (Exception ex) {
            //Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarEmigracion() {
        refrescar();
        listarEmigracion();
    }

    public void refrescar() {
        try {
            captDetalleH = new CaptDetalleH();
            paisSelected = new MetCatalogo();
            edicion = false;
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salirEmigracion() {
        PrimeFaces requestContext = PrimeFaces.current();
        requestContext.executeScript("PF('dlgEmigracion').hide()");

    }

    public void cargarEmigracion(CaptDetalleH detalleH) {
        edicion = true;
        captDetalleH = new CaptDetalleH();
        captDetalleH = (CaptDetalleH) SerializationUtils.clone(detalleH);
        try {
            MetTipoCatalogo tipoCat = new MetTipoCatalogo();
            tipoCat = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_PAIS_6DIG");
            paisSelected = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCat.getIdTipoCatalogo(), captDetalleH.getVal06());
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void eliminarEmigracion(CaptDetalleH detalleH) {
        try {
            bc.getCaptDetalleHServicioRemote().eliminarDetalleH(detalleH);
            bc.addSuccessMessage("Persona de Emigracion eliminada");
            refrescar();
            listarEmigracion();
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarElementoParaBoton(String tipo) { //Verifica Guardar
        try {
            cabeceraActual.setFechahoraEdicion(new Date());
            //System.out.println("elem " + elementoActual);
            CaptEstado es = listaEstados.get((elementoActual - 1));
            es.setFechahoraEdicion(new Date());

            es.setEstado("G");
            bc.getCaptEstadoServicioRemote().editarCaptEstado(es);
            cabeceraActual.setEstado("G");//}
            guardarTransaccionPorRolUsuario("G");
            cabeceraActual.setEstado3("2"); // integridad al guardar se pone en 2 que es no validada la integridad
            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual); //Carga Actual Viene null

            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);

            listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());
            //listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), elementoActual, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            cargarInicialesControlResumen();
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);

            bc.addSuccessMessage("Información Guardada.");
            PrimeFaces requestContext = PrimeFaces.current();

            requestContext.ajax().update("frmCaptura:tbCaptura");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarElemento(String tipo) { //Verifica Guardar
        try {
            guardarProvinciaSelect();
            guardarPaisSelect();
            guardarIdiomaSelect();
            guardarNacionalidadSelect();

            if (elementoActual == 1 && cargaActual.getControl2().equals("1")) {
                guardarValoresS4();
            }

            cabeceraActual.setFechahoraEdicion(new Date());
            //System.out.println("elem " + elementoActual);
            CaptEstado es = listaEstados.get((elementoActual - 1));
            es.setFechahoraEdicion(new Date());

            es.setEstado("G");
            bc.getCaptEstadoServicioRemote().editarCaptEstado(es);
            cabeceraActual.setEstado("G");//}
            guardarTransaccionPorRolUsuario("G");
            cabeceraActual.setEstado3("2"); // integridad al guardar se pone en 2 que es no validada la integridad
            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual); //Carga Actual Viene null

            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);

            listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());
            //listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), elementoActual, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            cargarInicialesControlResumen();
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);

            bc.addSuccessMessage("Información Guardada.");
            PrimeFaces requestContext = PrimeFaces.current();

            requestContext.ajax().update("frmCaptura:tbCaptura");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarElementoNoEfectivo(String tipo) { //Verifica Guardar
        try {
            cabeceraActual.setFechahoraEdicion(new Date());
            //System.out.println("elem " + elementoActual);
            CaptEstado es = listaEstados.get((elementoActual - 1));
            es.setFechahoraEdicion(new Date());

            es.setEstado("G");
            bc.getCaptEstadoServicioRemote().editarCaptEstado(es);
            //if(tipo.equals("G")){ Modifica JG (no se actualiza:se mantiene lógica actual en guardados)
            cabeceraActual.setEstado("G");//}
            guardarTransaccionPorRolUsuario("G");
            cabeceraActual.setEstado3("2"); // integridad al guardar se pone en 2 que es no validada la integridad
            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual); //Carga Actual Viene null

            //guardarProvinciaSelect(); //valida y guarda Prov-Canton-PQ en base en S5, P7 y P8
            //guardarPaisSelect(); //valida y guarda paises en base en S5, P7 y P8
            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);

            listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());
            //listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), elementoActual, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            //cargarInicialesControlResumen();
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);

            bc.addSuccessMessage("Información Guardada.");
            PrimeFaces requestContext = PrimeFaces.current();

            requestContext.ajax().update("frmCaptura:tbCaptura");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void validarGuardarElemento() {
        try {
            cabeceraActual.setFechahoraEdicion(new Date());
            CaptEstado es = listaEstados.get((elementoActual - 1));
            es.setFechahoraEdicion(new Date());
            es.setEstado("V");
            cabeceraActual.setEstado3("2"); // integridad al guardar se pone en 2 que es no validada la integridad
            bc.getCaptEstadoServicioRemote().editarCaptEstado(es);
            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);

            listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());
            // listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), elementoActual, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            //Consultar los esdtados de todos los miembros
            List<CaptEstado> lstEstadosCab = new ArrayList<>();
            lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
            Integer contEstd = 0;
            for (int i = 0; i < lstEstadosCab.size(); i++) {
                if (lstEstadosCab.get(i).getEstado().equals("V")) {
                    contEstd++;
                }
            }
            cargarInicialesControlResumen();
            if (contEstd.equals(lstEstadosCab.size())) {
                bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
            }
            //fijarEstadoValidacionIntegridad(cabeceraActual);
            //fijarEstadoSupIntegridad(cabeceraActual);
            bc.addSuccessMessage("Información Validada y Guardada.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void validarGuardarElementoNoEfectivo() {
        try {
            cabeceraActual.setFechahoraEdicion(new Date());
            CaptEstado es = listaEstados.get((elementoActual - 1));
            es.setFechahoraEdicion(new Date());
            es.setEstado("V");
            cabeceraActual.setEstado3("2"); // integridad al guardar se pone en 2 que es no validada la integridad
            bc.getCaptEstadoServicioRemote().editarCaptEstado(es);
            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
            bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);

            listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());
            //listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), elementoActual, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            //Consultar los esdtados de todos los miembros
            List<CaptEstado> lstEstadosCab = new ArrayList<>();
            lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
            Integer contEstd = 0;
            for (int i = 0; i < lstEstadosCab.size(); i++) {
                if (lstEstadosCab.get(i).getEstado().equals("V")) {
                    contEstd++;
                }
            }

            if (contEstd.equals(lstEstadosCab.size())) {
                bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
            }
            //fijarEstadoValidacionIntegridad(cabeceraActual);
            //fijarEstadoSupIntegridad(cabeceraActual);
            bc.addSuccessMessage("Información Validada y Guardada.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validaTieneCedula() { //Actalización Formulario Multi 2019,D.Aldas 13112019
        boolean campoLleno = false;
        try {
            try {
                int codPreCedF1 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0601")); //index pregunta de cédula
                int codCedF1 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0601N")); //index campo cédula       
                if (!listaEnemdu.get(codPreCedF1).getValor().equals("")) {
                    if (!listaEnemdu.get(codCedF1).getValor().equals("")) {

                        if (listaEnemdu.get(codPreCedF1).getValor().equals("1")) {

                            if (!listaEnemdu.get(codPreCedF1 + 1).getValor().equals("")) { //Posición ingreso cédula
                                String ci = listaEnemdu.get(codPreCedF1 + 1).getValor();// Posición ingreso cédula
                                if (!ci.equals("9999999999")) {
                                    //System.out.println("Primer if:" + ci);
                                    if (!ci.equals("8888888888")) {
                                        //System.out.println("Segundo if:" + ci);
                                        if (validadorDeCedula(ci)) {
                                            campoLleno = true;
                                        }
                                    } else {
                                        campoLleno = true;
                                    }
                                } else {
                                    campoLleno = true;
                                }
                            } else {
                                campoLleno = false;
                            }
                        } else {
                            campoLleno = false;
                        }
                    } else {
                        campoLleno = false;
                    }
                } else {
                    campoLleno = false;
                }
                //System.out.println("Estado campo lleno:" + campoLleno);
                if (campoLleno == false && listaEnemdu.get(codPreCedF1).getValor().equals("1")) {
                    bc.addErrorMessage("Ha ingresado que SI tiene cédula y campo de cédula se encuentra vacío o número está incorrecto");
                } else if (campoLleno == false && listaEnemdu.get(codPreCedF1).getValor().equals("2") && listaEnemdu.get(codCedF1 + 1).getValor().equals("")) {
                    bc.addErrorMessage("Ha ingresado que NO tiene cédula y campo 2.1 vacío, complete");
                } else if (campoLleno == false && listaEnemdu.get(codPreCedF1).getValor().equals("2") && !listaEnemdu.get(codCedF1 + 1).getValor().equals("")) {
                    campoLleno = true;
                } else if (campoLleno == false && (listaEnemdu.get(codPreCedF1).getValor().equals("8") || listaEnemdu.get(codPreCedF1).getValor().equals("9"))) {
                    campoLleno = true;
                }
                if (listaEnemdu.get(codPreCedF1).getValor().equals("")) {
                    bc.addErrorMessage("(S5-P6) Aún no llega a campo cédula");
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
            return campoLleno;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return campoLleno;
    }

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                //if (tercerDigito < 6) {
                int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                int verificador = Integer.parseInt(cedula.substring(9, 10));
                int suma = 0;
                int digito = 0;
                for (int i = 0; i < (cedula.length() - 1); i++) {
                    digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                    suma += ((digito % 10) + (digito / 10));
                }
                if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                    cedulaCorrecta = true;
                } else if ((10 - (suma % 10)) == verificador) {
                    cedulaCorrecta = true;
                } else {
                    cedulaCorrecta = false;
                }
                //} else {
                //cedulaCorrecta = false;
                //}
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
        }
        if (!cedulaCorrecta) {
        }
        return cedulaCorrecta;
    }

    public boolean mlt_sec3_validaNumOrden168(String S3P21, String S3P41) {
        boolean cumple = true;

        int s3p21 = (S3P21 != null && !S3P21.equals("")) ? Integer.parseInt(S3P21) : 0;
        int s3p41 = (S3P41 != null && !S3P41.equals("")) ? Integer.parseInt(S3P41) : 0;

        if (true) {
            if (s3p21 == 7 && ((s3p41 < 7111 || s3p41 > 7549) && (s3p41 < 9111 || s3p41 < 9629))) {
                cumple = false;
            }
        }

        return cumple;
    }

    public boolean mlt_sec3_validaNumOrden228(String SP40, String SP41, String SP46) {
        boolean cumple = true;
        if (true) {
            //int edad = Integer.valueOf(listaMultiF1.get(5).getValor());
            if (true) {
                if (!SP40.equals("") && !SP41.equals("") && !SP46.equals("")) {
                    int codS3P40a1 = Integer.valueOf(SP40);
                    int codS3P41a1 = Integer.valueOf(SP41);
                    String codS3P46 = SP46;
                    if (codS3P46.equals("2") && (codS3P40a1 >= 4100 && codS3P40a1 <= 4390) && ((codS3P41a1 < 7111 || codS3P41a1 > 7133) && (codS3P41a1 < 7411 || codS3P41a1 > 7422) && codS3P41a1 != 1323 && codS3P41a1 != 2142 && codS3P41a1 != 3123 && codS3P41a1 != 7212 && codS3P41a1 != 8342 && codS3P41a1 != 9312 && codS3P41a1 != 9313 && codS3P41a1 != 7214 && codS3P41a1 != 2161 && codS3P41a1 != 8332 && codS3P41a1 != 7222)) {
                        cumple = false;
                    }
                }
            }
        }
        return cumple;
    }

    public boolean mlt_sec3_validaNumOrden160(String S3P21, String S3P40) {
        boolean cumple = true;

        int s3p21 = (S3P21 != null && !S3P21.equals("")) ? Integer.parseInt(S3P21) : 0;
        int s3p40 = (S3P40 != null && !S3P40.equals("")) ? Integer.parseInt(S3P40) : 0;

        if (s3p21 == 2 && (s3p40 < 1010 || s3p40 > 3320)) {
            cumple = false;
        }

        return cumple;
    }

    public boolean mlt_sec3_validaNumOrden68(String S3P40, String S3P41, String S3P42) {
        boolean cumple = true;

        int s3p41 = (S3P41 != null && !S3P41.equals("")) ? Integer.parseInt(S3P41) : 0;
        int s3p42 = (S3P42 != null && !S3P42.equals("")) ? Integer.parseInt(S3P42) : 0;
        int s3p40 = (S3P40 != null && !S3P40.equals("")) ? Integer.parseInt(S3P40) : 0;

        if (s3p41 == 9111 && (s3p42 != 10 && s3p42 != 9) && s3p40 == 9700) {
            cumple = false;
        }

        return cumple;
    }

    public boolean mlt_sec3_validaNumOrden29(String S3P04, String S3P42, String S3P40) {
        boolean cumple = true;

        int s3p04 = (S3P04 != null && !S3P04.equals("")) ? Integer.parseInt(S3P04) : 0;
        int s3p42 = (S3P42 != null && !S3P42.equals("")) ? Integer.parseInt(S3P42) : 0;
        int s3p40 = (S3P40 != null && !S3P40.equals("")) ? Integer.parseInt(S3P40) : 0;

        if (s3p04 == 8 && s3p42 == 10 && s3p40 != 9700) {
            cumple = false;
        }

        return cumple;
    }

    public boolean mlt_sec3_validaNumOrden169(String S3P21, String S3P40) {
        boolean cumple = true;

        int s3p21 = (S3P21 != null && !S3P21.equals("")) ? Integer.parseInt(S3P21) : 0;
        int s3p40 = (S3P40 != null && !S3P40.equals("")) ? Integer.parseInt(S3P40) : 0;

        if (s3p21 == 8 && s3p40 > 322) {
            cumple = false;
        }

        return cumple;
    }

    public boolean mlt_sec3_validaNumOrden175(String S3P25, String S3P40) {
        boolean cumple = true;

        int s3p25 = (S3P25 != null && !S3P25.equals("")) ? Integer.parseInt(S3P25) : 0;
        int s3p40 = (S3P40 != null && !S3P40.equals("")) ? Integer.parseInt(S3P40) : 0;

        if (s3p25 == 5 && ((s3p40 < 1010 || s3p40 > 3320) && (s3p40 < 4100 || s3p40 > 4390))) {
            cumple = false;
        }

        return cumple;
    }

    public boolean validaRamaSec() {//Actalización Formulario Multi 2019,D.Aldas 13112019 (No aplica)
        boolean campoLleno = true;

        try {
            try {
                int p03 = Integer.parseInt(mapaVariables.get("ENEM0721_P03"));
                System.out.println("äki" + mapaVariables.get("ENEM0721_P41"));
                System.out.println("äki" + mapaVariables.get("ENEM0721_P40"));
                int p_40 = Integer.parseInt(mapaVariables.get("ENEM0721_P40"));
                int p_04 = Integer.parseInt(mapaVariables.get("ENEM0721_P04"));
                int p_41 = Integer.parseInt(mapaVariables.get("ENEM0721_P41"));
                int p_29 = Integer.parseInt(mapaVariables.get("ENEM0721_P29"));
                int p_29A = Integer.parseInt(mapaVariables.get("ENEM0721_P29A"));
                int p_39 = Integer.parseInt(mapaVariables.get("ENEM0721_P39"));
                //int p_10 = Integer.parseInt(mapaVariables.get("ENEM0121_P10"));
                int p_46 = Integer.parseInt(mapaVariables.get("ENEM0721_P46"));
                int p_21 = Integer.parseInt(mapaVariables.get("ENEM0721_P21"));
                int p_25 = Integer.parseInt(mapaVariables.get("ENEM0721_P25"));
                int p_42 = Integer.parseInt(mapaVariables.get("ENEM0721_P42"));
                int p_12 = Integer.parseInt(mapaVariables.get("ENEM0721_P12"));
                int p_12a = Integer.parseInt(mapaVariables.get("ENEM0721_P12A"));
                int p_15a = Integer.parseInt(mapaVariables.get("ENEM0721_P15A"));
                int p_15a1 = Integer.parseInt(mapaVariables.get("ENEM0721_P15A1"));
                int p_40a = Integer.parseInt(mapaVariables.get("ENEM0721_P40A"));
                int p_40a1 = Integer.parseInt(mapaVariables.get("ENEM0721_P40A1"));
                int p_50 = Integer.parseInt(mapaVariables.get("ENEM0721_P50"));
                int p_52 = Integer.parseInt(mapaVariables.get("ENEM0721_P52"));
                int p_53 = Integer.parseInt(mapaVariables.get("ENEM0721_P53"));

                if (!listaEnemdu.get(p_12a).getValor().equals("")) {
                    if (Integer.parseInt(listaEnemdu.get(p_12a).getValor()) == 1) {
                        if (listaEnemdu.get(p_12).getValor().equals("")) {
                            campoLleno = false;

                            bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 1 pregunta 12 ");
                        } else if (listaEnemdu.get(p_12).getValor().length() < 8) {
                            campoLleno = false;

                            bc.addErrorMessage("Sección 1, pregunta 12, USO INEC: Código de título de 8 dígitos requerido");
                        } else if (!validaCodigosUsoInec("CAT_TITULOS_8DIG", listaEnemdu.get(p_12).getValor())) {//Valida codigo de titulo ingresado que sea verdadero: Parametro alias tipo catalogo y valor catalogo.
                            campoLleno = false;

                            bc.addErrorMessage("El código de título ingresado es incorrecto ");
                        }
                    }
                }

                if (!listaEnemdu.get(p_15a1).getValor().equals("")) {
                    if (Integer.parseInt(listaEnemdu.get(p_15a1).getValor()) == 2) {
                        if (listaEnemdu.get(p_15a).getValor().equals("")) {
                            campoLleno = false;

                            bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 1 pregunta 15A ");
                        } else if (!validaCodigosUsoInec("CAT_DPA_PARR", listaEnemdu.get(p_15a).getValor())) {//Valida codigo rama de actividad
                            campoLleno = false;

                            bc.addErrorMessage(" El código de la pregunta 15A no existe en DPA.");
                        }
                    } else if (Integer.parseInt(listaEnemdu.get(p_15a1).getValor()) == 3) {
                        if (listaEnemdu.get(p_15a).getValor().equals("")) {
                            campoLleno = false;

                            bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 1 pregunta 15A ");
                        } else if (!validaCodigosUsoInec("CAT_PAIS_3DIG", listaEnemdu.get(p_15a).getValor())) {//Valida codigo rde pais
                            campoLleno = false;

                            bc.addErrorMessage(" El código de país no existe.");
                        }
                    }
                }

                if (Integer.parseInt(listaEnemdu.get(p03).getValor()) > 5 && ((!listaEnemdu.get(p_29).getValor().equals("")) || (!listaEnemdu.get(p_29A).getValor().equals("")) || (!listaEnemdu.get(p_39).getValor().equals("")))) {
                    if (mlt_sec3_validaNumOrden168(listaEnemdu.get(p_21).getValor(), listaEnemdu.get(p_41).getValor()) == false) {
                        bc.addErrorMessage("Error 45 Sección 2, P41: Aprendiz remunerado y en Grupo Ocupacional diferente de artesanos ó peones");
                        campoLleno = false;
                    }
                    if (mlt_sec3_validaNumOrden160(listaEnemdu.get(p_21).getValor(), listaEnemdu.get(p_40).getValor()) == false) {
                        bc.addErrorMessage("Error 38 Sección 2, P40: Fabrica un producto y en Rama de Actividad códigos diferentes de Indust. Manufacturera");
                        campoLleno = false;
                    }
                    if (mlt_sec3_validaNumOrden175(listaEnemdu.get(p_25).getValor(), listaEnemdu.get(p_40).getValor()) == false) {
                        bc.addErrorMessage("Error 50 Sección 2, P40: Trabajó menos de 40 horas por falta de materia prima y en rama de actividad diferente de manufactura y construcción");
                        campoLleno = false;
                    }
                    if (mlt_sec3_validaNumOrden169(listaEnemdu.get(p_21).getValor(), listaEnemdu.get(p_40).getValor()) == false) {
                        bc.addErrorMessage("Error 46 Sección 2, P40: Labores agrícolas o cuidado de animales y pesca; y, en Rama de Actividad diferentes de Actividades agropecuarias");
                        campoLleno = false;
                    }
                    if (!mlt_sec3_validaNumOrden29(listaEnemdu.get(p_04).getValor(), listaEnemdu.get(p_42).getValor(), listaEnemdu.get(p_40).getValor())) {
                        bc.addErrorMessage(" Error 29 Sección 2, P40: En parentesco y en Categoría es empleada doméstica con Rama de Actividad diferente ");
                        campoLleno = false;
                    }
                    if (!mlt_sec3_validaNumOrden68(listaEnemdu.get(p_40).getValor(), listaEnemdu.get(p_41).getValor(), listaEnemdu.get(p_42).getValor())) {
                        bc.addErrorMessage(" Error 68 Sección 2, P40: Rama de Actividad: Servicio doméstico y categoría ocupacional diferente de empleada doméstica ");
                        campoLleno = false;
                    }
                    if (!mlt_sec3_validaNumOrden228(listaEnemdu.get(p_40).getValor(), listaEnemdu.get(p_41).getValor(), listaEnemdu.get(p_46).getValor())) {
                        bc.addErrorMessage(" Error 75 Sección 2, P46: Sitio de trabajo = 2 con diferente grupo de ocupación de Construcción ");
                        campoLleno = false;
                    }

                    if (listaEnemdu.get(p_40).getValor().equals("")) {
                        campoLleno = false;

                        bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 2 pregunta 40 ");
                    } else if (!validaCodigosUsoInec("CAT_CIIU_4DIG", listaEnemdu.get(p_40).getValor())) {//Valida codigo rama de actividad
                        campoLleno = false;

                        bc.addErrorMessage(" El código de la pregunta 40.USO_INEC (rama de actividad) es incorrecto ");
                    }

                    if (!listaEnemdu.get(p_40a1).getValor().equals("")) {
                        if (Integer.parseInt(listaEnemdu.get(p_40a1).getValor()) == 2) {
                            if (listaEnemdu.get(p_40a).getValor().equals("")) {
                                campoLleno = false;

                                bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 2 pregunta 40A ");
                            } else if (!validaCodigosUsoInec("CAT_DPA_PARR", listaEnemdu.get(p_40a).getValor())) {//Valida codigo rama de actividad
                                campoLleno = false;

                                bc.addErrorMessage(" El código de la pregunta 40A no existe en DPA.");
                            }
                        } else if (Integer.parseInt(listaEnemdu.get(p_40a1).getValor()) == 3) {
                            if (listaEnemdu.get(p_40a).getValor().equals("")) {
                                campoLleno = false;

                                bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 1 pregunta 40A ");
                            } else if (!validaCodigosUsoInec("CAT_PAIS_3DIG", listaEnemdu.get(p_40a).getValor())) {//Valida codigo rde pais
                                campoLleno = false;

                                bc.addErrorMessage(" El código de país no existe.");
                            }
                        }
                    }

                    if (listaEnemdu.get(p_41).getValor().equals("")) {
                        campoLleno = false;

                        bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 2 pregunta 41 ");
                    } else if (!validaCodigosUsoInec("CAT_CIUO_4DIG", listaEnemdu.get(p_41).getValor())) {//Valida codigo grupo de ocupación
                        campoLleno = false;

                        bc.addErrorMessage(" El código de la pregunta 41.USO_INEC (grupo de ocupación) es incorrecto ");
                    }

                }

                int p50 = (listaEnemdu.get(p_50).getValor() != null && !listaEnemdu.get(p_50).getValor().equals("")) ? Integer.parseInt(listaEnemdu.get(p_50).getValor()) : 0;

                if (p50 == 2) {
                    if (listaEnemdu.get(p_52).getValor().equals("")) {
                        campoLleno = false;

                        bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 2 pregunta 52 ");
                    } else if (!validaCodigosUsoInec("CAT_CIIU_4DIG", listaEnemdu.get(p_52).getValor())) {//Valida codigo rama de actividad
                        campoLleno = false;

                        bc.addErrorMessage(" El código de la pregunta 52.USO_INEC (rama de actividad) es incorrecto ");
                    }

                    if (listaEnemdu.get(p_53).getValor().equals("")) {
                        campoLleno = false;

                        bc.addErrorMessage(" Revisar que esté ingresado el campo USO INEC en la sección 2 pregunta 53 ");
                    } else if (!validaCodigosUsoInec("CAT_CIUO_4DIG", listaEnemdu.get(p_53).getValor())) {//Valida codigo grupo de ocupación
                        campoLleno = false;

                        bc.addErrorMessage(" El código de la pregunta 53.USO_INEC (grupo de ocupación) es incorrecto ");
                    }
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
            return campoLleno;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return campoLleno;
    }

    public boolean validaSinCodINEC() {
        boolean campoLleno = true;

        try {
            try {
                int p_12 = Integer.parseInt(mapaVariables.get("ENEM0721_P12"));
                int p_15a = Integer.parseInt(mapaVariables.get("ENEM0721_P15A"));

                int p_40 = Integer.parseInt(mapaVariables.get("ENEM0721_P40"));

                int p_40a = Integer.parseInt(mapaVariables.get("ENEM0721_P40A"));

                int p_41 = Integer.parseInt(mapaVariables.get("ENEM0721_P41"));

                int p_52 = Integer.parseInt(mapaVariables.get("ENEM0721_P52"));
                int p_53 = Integer.parseInt(mapaVariables.get("ENEM0721_P53"));

                if (!(listaEnemdu.get(p_12).getValor().equals("") || Objects.isNull(listaEnemdu.get(p_12).getValor()))) {
                    campoLleno = false;

                    bc.addErrorMessage("Encuestador y Supervisor no deben llenar este campo de USO INEC.");
                }

                if (!(listaEnemdu.get(p_15a).getValor().equals("") || Objects.isNull(listaEnemdu.get(p_15a).getValor()))) {
                    campoLleno = false;

                    bc.addErrorMessage("Encuestador y Supervisor no deben llenar este campo de USO INEC.");
                }

                if (!(listaEnemdu.get(p_40).getValor().equals("") || Objects.isNull(listaEnemdu.get(p_40).getValor()))) {
                    campoLleno = false;

                    bc.addErrorMessage("Encuestador y Supervisor no deben llenar este campo de USO INEC.");
                }

                if (!(listaEnemdu.get(p_40a).getValor().equals("") || Objects.isNull(listaEnemdu.get(p_40a).getValor()))) {
                    campoLleno = false;

                    bc.addErrorMessage("Encuestador y Supervisor no deben llenar este campo de USO INEC.");
                }

                if (!(listaEnemdu.get(p_41).getValor().equals("") || Objects.isNull(listaEnemdu.get(p_41).getValor()))) {
                    campoLleno = false;

                    bc.addErrorMessage("Encuestador y Supervisor no deben llenar este campo de USO INEC.");
                }

                if (!(listaEnemdu.get(p_52).getValor().equals("") || Objects.isNull(listaEnemdu.get(p_52).getValor()))) {
                    campoLleno = false;

                    bc.addErrorMessage("Encuestador y Supervisor no deben llenar este campo de USO INEC.");
                }

                if (!(listaEnemdu.get(p_53).getValor().equals("") || Objects.isNull(listaEnemdu.get(p_53).getValor()))) {
                    campoLleno = false;

                    bc.addErrorMessage("Encuestador y Supervisor no deben llenar este campo de USO INEC.");
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
            return campoLleno;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return campoLleno;
    }

    public boolean validacionGenericaEnemdu(String estado) {
        if (uc.usuarioTieneRol("ROL_VALID") || uc.usuarioTieneRol("ROL_REVIS")) {
            return (validacionEnemdu() && validaRamaSec());
        } else {
            return (validacionEnemdu() && validaSinCodINEC());
        }
    }

    public boolean validacionEnemdu() {
        boolean campoLleno = true;
        cargarInicialesControlResumen();
        Map<String, Object> primerM = new HashMap<>();
        primerM = listaElementosControlEnemdu.get(0);
        if (Integer.parseInt(controlaNulos(primerM.get("ENEM0721_P03"))) > 98) {
            bc.addErrorMessage("Persona mayor a 98 años de edad. ERROR 9");
            campoLleno = false;
        }
        Map<String, Object> conyugue = new HashMap<>();
        int contadorJefe = 0;
        int contadorConyugue = 0;
        if (cabeceraActual.getNumDet() > 1) {
            for (Map<String, Object> valid : listaElementosControlEnemdu) {
                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_COD_PER"))) > cabeceraActual.getNumDet()) {
                    bc.addErrorMessage("Número de persona informante  mayor al NÚMERO DE MIEMBROS DEL HOGAR. ERROR 3");
                    campoLleno = false;
                }

                if (Objects.isNull(valid.get("ENEM0721_NOMBRE1")) || valid.get("ENEM0721_NOMBRE1").equals("")) {
                    bc.addErrorMessage("ERROR 4");
                    campoLleno = false;
                }
                if (Objects.isNull(valid.get("ENEM0721_NOMBRE2")) || valid.get("ENEM0721_NOMBRE2").equals("")) {
                    bc.addErrorMessage("ERROR 5");
                    campoLleno = false;
                }
                if (Objects.isNull(valid.get("ENEM0721_APELLIDO1")) || valid.get("ENEM0721_APELLIDO1").equals("")) {
                    bc.addErrorMessage(" ERROR 6");
                    campoLleno = false;
                }
                if (Objects.isNull(valid.get("ENEM0721_APELLIDO2")) || valid.get("ENEM0721_APELLIDO2").equals("")) {
                    bc.addErrorMessage("ERROR 6");
                    campoLleno = false;
                }

                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03"))) > 98) {
                    bc.addErrorMessage("Persona mayor a 98 años de edad. ERROR 9");
                    campoLleno = false;
                }

                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 4 && Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03"))) < 12) {
                    bc.addErrorMessage("Yerno o Nuera y Edad menor de 12 años. ERROR 13");
                    campoLleno = false;
                }

                if (controlaNulos(valid.get("ENEM0721_P04")).equals("2")) {
                    conyugue = valid;
                    contadorConyugue++;
                }
                if (controlaNulos(valid.get("ENEM0721_P04")).equals("1")) {

                    contadorJefe++;
                }
            }
        }
        if (contadorJefe > 1) {

            bc.addErrorMessage("JEFE ya registrado . ERROR 16");
            campoLleno = false;
        }

        if (contadorConyugue > 0) {
            if (contadorConyugue > 1) {

                bc.addErrorMessage("CONYUGE ya registrado . ERROR 17");
                campoLleno = false;
            }
            if (!controlaNulos(primerM.get("ENEM0721_P06")).equals(controlaNulos(conyugue.get("ENEM0721_P06")))) {
                bc.addErrorMessage("Estado civil del Jefe y Cónyuge diferente . ERROR 21");
                campoLleno = false;
            }

            if (controlaNulos(primerM.get("ENEM0721_P02")).equals(controlaNulos(conyugue.get("ENEM0721_P02")))) {
                bc.addErrorMessage("Sexo del Jefe = Sexo del Cónyuge . ERROR 8");
                campoLleno = false;
            }
        }

//        if (Integer.parseInt(controlaNulos(primerM.get("ENEM0121_ICCCODPER"))) > cabeceraActual.getNumDet()) {
//            bc.addErrorMessage("Número de persona informante de Sección 5 mayor al NÚMERO DE MIEMBROS DEL HOGAR. ERROR 35");
//            campoLleno = false;
//        }
//        if (Integer.parseInt(controlaNulos(primerM.get("ENEM0121_VCODPER"))) > cabeceraActual.getNumDet()) {
//            bc.addErrorMessage("Número de persona informante de Sección 5 mayor al NÚMERO DE MIEMBROS DEL HOGAR. ERROR 35");
//            campoLleno = false;
//        }
        if (Integer.parseInt(controlaNulos(primerM.get("ENEM0721_COD_PER"))) > cabeceraActual.getNumDet()) {
            bc.addErrorMessage("Número de persona informante  mayor al NÚMERO DE MIEMBROS DEL HOGAR. ERROR 3");
            campoLleno = false;
        }
        if (!controlaNulos(primerM.get("ENEM0721_P04")).equals("1")) {
            bc.addErrorMessage("La primera persona debe ser el JEFE DE HOGAR. ERROR 12");
            campoLleno = false;
        }
        /*
             if(!(controlaNulos(primerM.get("ENEM1020_P04")).equals("1") && !("1,5".contains(controlaNulos(primerM.get("ENEM01020_P06")).toString()))))
          {
          bc.addErrorMessage("Estado civil no corresponde a relación de parentesco.");
          campoLleno=false;
          }
         */
        return campoLleno;
    }

    public boolean validaCedulasFrm() {
        boolean repetidas = true;
        MetVariable varCel;
        varCel = new MetVariable();
        try {
            varCel = bc.getMetVariableServicioRemote().buscarVariablePorIdentificador("CENSO_S5_P0601N");
            List<CaptDetalleV> listaCedulas;
            listaCedulas = new ArrayList<>();
            listaCedulas = bc.getCaptDetalleVServicioRemote().listaValoresVariableXCab(cabeceraActual.getIdCab(), varCel.getIdVar());
            repetidas = repetidos(listaCedulas);
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return repetidas;
    }

    public boolean repetidos(List<CaptDetalleV> listaAux) {
        boolean rep = true;
        for (int i = 0; i < listaAux.size(); i++) {
            int numVeces = 0;
            String val = listaAux.get(i).getValor();
            //System.out.println("val "+ val);
            if (val == null || val.isEmpty() || val.equals("") || val.equals("9999999999") || val.equals("8888888888")) {
            } else {
                for (int y = 0; y < listaAux.size(); y++) {
                    if (val.equals(listaAux.get(y).getValor())) {
                        numVeces++;
                        if (numVeces > 1) {
                            bc.addErrorMessage("El número Cedula : " + val + " se repite: " + numVeces + " veces en  Persona: "
                                    + listaAux.get(i).getNumDet() + " y  Persona: " + listaAux.get(y).getNumDet());
                            y = listaAux.size();
                            i = listaAux.size();
                            rep = false;

                        }
                    }
                }
            }
            //System.out.println("El número Cedula : "+val+" se repite: "+numVeces);
        }
        return rep;
    }

    public boolean validacionEnemduSoloAlertas() {
        boolean campoLleno = true;
        cargarInicialesControlResumen();
        Map<String, Object> primerM = new HashMap<>();
        primerM = listaElementosControlEnemdu.get(0);
        Map<String, Object> conyugue = new HashMap<>();
        int contadorJefe = 0;
        int contadorConyugue = 0;

        if (cabeceraActual.getNumDet() > 1) {
            for (Map<String, Object> valid : listaElementosControlEnemdu) {
                if (Integer.parseInt(controlaNulos(valid.get("CENSO_S5_P01"))) == 1 && ((Integer.parseInt(controlaNulos(valid.get("CENSO_S5_P03")))) < 12)) {
                    bc.addWarningMessage("Edad del Representante de hogar menor de 12 años. ERROR 10");
                    campoLleno = false;
                }
                if (Integer.parseInt(controlaNulos(valid.get("CENSO_S5_P01"))) == 2 && ((Integer.parseInt(controlaNulos(valid.get("CENSO_S5_P03")))) < 12)) {
                    bc.addWarningMessage("Edad del Cónyuge menor de 12 años. ERROR 11");
                    campoLleno = false;
                }
            }
        }
        return campoLleno;
    }

    public void soloValidaUnMiembro(String estado) {
        try {
            if (!cabeceraActual.getInfo1().equals("N")) {
                validarGuardarElemento();

                CaptEstado es = listaEstados.get((elementoActual - 1));
                es.setFechahoraEdicion(new Date());
                es.setEstado("V");
                bc.getCaptEstadoServicioRemote().editarCaptEstado(es);

                validarIntegridadForm1Autocenso(estado);
                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
            } else if (cabeceraActual.getInfo1().equals("N")) {
                validarGuardarElementoNoEfectivo();

                CaptEstado es = listaEstados.get((elementoActual - 1));
                es.setFechahoraEdicion(new Date());
                es.setEstado("V");
                bc.getCaptEstadoServicioRemote().editarCaptEstado(es);

                validarIntegridadForm1Autocenso(estado);
                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validaAutocenso(String estado) {
        boolean validaAuto = false;
        try {
            if (elementoActual == 1) {
                int indexAutocenso = Integer.parseInt(mapaVariables.get("CENSO_S1_C13")); //pregunta autocenso                
                if (listaEnemdu.get(indexAutocenso).getValor().equals("1") && !listaEnemdu.get(indexAutocenso + 1).getValor().equals("")) { //valida para Autocenso
                    validarGuardarElemento();

                    CaptEstado es = listaEstados.get((elementoActual - 1));
                    es.setFechahoraEdicion(new Date());
                    es.setEstado("V");
                    bc.getCaptEstadoServicioRemote().editarCaptEstado(es);

                    validarIntegridadForm1Autocenso(estado);
                    cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                    cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                    bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);

                    //bc.addSuccessMessage("Información Valida y Verificada, Autocenso");
                    validaAuto = true;
                    banderaAutocenso = true;
                } else if (listaEnemdu.get(indexAutocenso).getValor().equals("1") && listaEnemdu.get(indexAutocenso + 1).getValor().equals("")) {
                    bc.addErrorMessage("Se autocenso SI pero código vacío");
                    validaAuto = false;
                } else if (listaEnemdu.get(indexAutocenso).getValor().equals("2") && listaEnemdu.get(indexAutocenso + 1).getValor().equals("")) {
                    validaAuto = false;
                }
            } else if (elementoActual > 1) {
                //soloValidaUnMiembro(estado);
                //bc.addSuccessMessage("Siguiente miembro validado y guardado");
                validaAuto = false;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return validaAuto;
    }

    public boolean validaViviendaParticular(String estado) {
        boolean vvParticular = false;
        Integer viviendaParticular = 0;
        try {
            int indexVParticular = Integer.parseInt(mapaVariables.get("CENSO_S3_V0201"));
            if (listaEnemdu.get(indexVParticular).getValor().equals("")) {
                viviendaParticular = 0;
            } else if (!listaEnemdu.get(indexVParticular).getValor().equals("")) {
                viviendaParticular = Integer.valueOf(listaEnemdu.get(indexVParticular).getValor());
            }
            if (elementoActual == 1 && viviendaParticular > 1) {
                soloValidaUnMiembro(estado);
                vvParticular = true;
                banderaVParticular = true;
            } else if (elementoActual > 1 && viviendaParticular == 0) {
                //soloValidaUnMiembro(estado);
                //bc.addSuccessMessage("Siguiente miembro validado y guardado");
                vvParticular = false;
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return vvParticular;
    }

    public boolean validaViviendaColectiva(String estado) {
        boolean vvColectiva = false;
        Integer viviendaColectiva = 0;
        try {
            int indexVColectiva = Integer.parseInt(mapaVariables.get("CENSO_S3_V0202"));
            if (!cabeceraActual.getInfo1().equals("N")) {
                if (listaEnemdu.get(indexVColectiva).getValor().equals("")) {
                    viviendaColectiva = 0;
                } else if (!listaEnemdu.get(indexVColectiva).getValor().equals("")) {
                    viviendaColectiva = Integer.valueOf(listaEnemdu.get(indexVColectiva).getValor());
                }
                if (elementoActual == 1 && viviendaColectiva == 2) {
                    soloValidaUnMiembro(estado);
                    vvColectiva = true;
                    banderaVColectiva = true;
                } else if (elementoActual > 1 && viviendaColectiva == 0) {
                    vvColectiva = false;
                }
            } else if (cabeceraActual.getInfo1().equals("N")) {
                if (listaEnemdu.get(indexVColectiva).getValor().equals("")) {
                    viviendaColectiva = 0;
                } else if (!listaEnemdu.get(indexVColectiva).getValor().equals("")) {
                    viviendaColectiva = Integer.valueOf(listaEnemdu.get(indexVColectiva).getValor());
                }
                if (elementoActual == 1 && (viviendaColectiva == 1 || viviendaColectiva == 2)) {
                    soloValidaUnMiembro(estado);
                    vvColectiva = true;
                    banderaVColectiva = true;
                } else if (elementoActual > 1 && viviendaColectiva == 0) {
                    vvColectiva = false;
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return vvColectiva;
    }

    public boolean validaExisteVivienda(String estado) {
        boolean vExisteVivienda = false;
        try {
            int indexEVivienda = Integer.parseInt(mapaVariables.get("CENSO_S1_C12"));
            if (elementoActual == 1) {
                if (listaEnemdu.get(indexEVivienda).getValor().equals("2") && listaEnemdu.get(indexEVivienda + 1).getValor().equals("4") && !listaEnemdu.get(indexEVivienda + 2).getValor().equals("")) {
                    soloValidaUnMiembro(estado);
                    vExisteVivienda = true;
                    banderaExisteVivienda = true;
                } else if (listaEnemdu.get(indexEVivienda).getValor().equals("2") && listaEnemdu.get(indexEVivienda + 1).getValor().equals("4") && listaEnemdu.get(indexEVivienda + 2).getValor().equals("")) {
                    bc.addWarningMessage("No existe vivienda y opción igual a 4, Especifique");
                    vExisteVivienda = false;
                } else if (listaEnemdu.get(indexEVivienda).getValor().equals("2") && (listaEnemdu.get(indexEVivienda + 1).getValor().equals("1") || listaEnemdu.get(indexEVivienda + 1).getValor().equals("2")
                        || listaEnemdu.get(indexEVivienda + 1).getValor().equals("3"))) {
                    soloValidaUnMiembro(estado);
                    vExisteVivienda = true;
                    banderaExisteVivienda = true;
                } else if (listaEnemdu.get(indexEVivienda).getValor().equals("2") && listaEnemdu.get(indexEVivienda + 1).getValor().equals("")) {
                    bc.addWarningMessage("No existe vivienda, ingrese el por qué");
                    vExisteVivienda = false;
                }
            } else if (elementoActual > 1) {
                vExisteVivienda = false;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return vExisteVivienda;
    }

    public boolean validarAniosConFNacimiento() {
        boolean vAnios = false;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DecimalFormat mFormat = new DecimalFormat("00");
            mFormat.setRoundingMode(RoundingMode.DOWN);
            int indexDP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0401"));//index dia
            int indexMP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0402"));//index mes
            int indexAP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0403"));//index año
            int indexEP3S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P03"));//index edad
            int indexOpcDP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0401d"));//index opciones dia
            int indexOpcMP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0402m"));//index opciones mes
            int indexOpcAP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0403anio"));//index opciones año
            String dia = listaEnemdu.get(indexDP4S5).getValor();
            String mes = listaEnemdu.get(indexMP4S5).getValor();
            String anios = listaEnemdu.get(indexAP4S5).getValor();

            if (listaEnemdu.get(indexDP4S5).getValor().equals("")) {

            } else {
                dia = mFormat.format(Double.valueOf(listaEnemdu.get(indexDP4S5).getValor()));
            }
            if (listaEnemdu.get(indexMP4S5).getValor().equals("")) {

            } else {
                mes = mFormat.format(Double.valueOf(listaEnemdu.get(indexMP4S5).getValor()));
            }

            if ((listaEnemdu.get(indexDP4S5).getValor().equals("") && listaEnemdu.get(indexMP4S5).getValor().equals("") && listaEnemdu.get(indexAP4S5).getValor().equals(""))
                    && (listaEnemdu.get(indexOpcDP4S5).getValor().equals("") && listaEnemdu.get(indexOpcMP4S5).getValor().equals("") && listaEnemdu.get(indexOpcAP4S5).getValor().equals(""))) {
                bc.addErrorMessage("(P4-S5) Aún no ingresa fecha de nacimiento");
            } else if (listaEnemdu.get(indexDP4S5).getValor().equals("") || listaEnemdu.get(indexMP4S5).getValor().equals("") || listaEnemdu.get(indexAP4S5).getValor().equals("")) {
                bc.addWarningMessage("(P4-S5) Para validar fecha de nacimiento con edad, complete todos los campos");
                vAnios = true;
            } else if (!listaEnemdu.get(indexDP4S5).getValor().equals("") && !listaEnemdu.get(indexMP4S5).getValor().equals("") && !listaEnemdu.get(indexAP4S5).getValor().equals("")) {
                if (validaDiaConMesCorrecto(Integer.parseInt(mes))) {
                    String fechaNac = dia + "/" + mes + "/" + anios;
                    System.out.println("nacimiento: " + fechaNac);
                    LocalDate fechaNacDate = LocalDate.parse(fechaNac, fmt);
                    LocalDate ahora = LocalDate.now();
                    Period periodo = Period.between(fechaNacDate, ahora);
                    //System.out.println("años calculados: " + periodo.getYears());

                    if (periodo.getYears() == Integer.parseInt(listaEnemdu.get(indexEP3S5).getValor())) {
                        bc.addSuccessMessage("(P4-S5) Edad Correcta validada");
                        vAnios = true;
                    } else if (periodo.getYears() != Integer.parseInt(listaEnemdu.get(indexEP3S5).getValor())) {
                        bc.addErrorMessage("(P4-S5) Edad Incorrecta, verifique información");
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vAnios;
    }

    public boolean validaDiaConMesCorrecto(int mesInput) {
        boolean vDiaMes = false;
        int numeroDias = -1;
        try {
            int indexDP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0401"));//index dia            
            int indexAP4S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0403"));//index año
            String dia = listaEnemdu.get(indexDP4S5).getValor();
            String anios = listaEnemdu.get(indexAP4S5).getValor();

            switch (mesInput) {
                case 01:
                case 03:
                case 05:
                case 07:
                case 8:
                case 10:
                case 12:
                    numeroDias = 31;
                    if (Integer.parseInt(dia) > numeroDias || Integer.parseInt(dia) < 1) {
                        bc.addErrorMessage("Día excede el rango");
                    } else {
                        vDiaMes = true;
                    }
                    break;
                case 04:
                case 06:
                case 9:
                case 11:
                    numeroDias = 30;
                    if (Integer.parseInt(dia) > numeroDias || Integer.parseInt(dia) < 1) {
                        bc.addErrorMessage("Día excede el rango");
                    } else {
                        vDiaMes = true;
                    }
                    break;
                case 02:
                    if (esBisiesto(Integer.parseInt(anios))) {
                        numeroDias = 29;
                        if (Integer.parseInt(dia) > numeroDias || Integer.parseInt(dia) < 1) {
                            bc.addErrorMessage("Día excede el rango");
                        } else {
                            vDiaMes = true;
                        }
                    } else {
                        numeroDias = 28;
                        if (Integer.parseInt(dia) > numeroDias || Integer.parseInt(dia) < 1) {
                            bc.addErrorMessage("Día excede el rango");
                        } else {
                            vDiaMes = true;
                        }
                    }
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vDiaMes;
    }

    public boolean esBisiesto(int anio) {
        boolean esBisiesto = false;
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            if (calendar.isLeapYear(anio)) {
                esBisiesto = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esBisiesto;
    }

    public boolean validaFrmMortalidadCompletos() {
        boolean frmMortalidadCompleto = true;
        try {
            if (elementoActual == 1) {
                int indexP1001S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1001"));
                int indexP1002S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1002"));
                String pregMortalidad = listaEnemdu.get(indexP1001S4).getValor();
                String cantMortalidad = listaEnemdu.get(indexP1002S4).getValor();

                List<CaptDetalleH> detallesH;
                MetSeccion secSel = new MetSeccion();
                List<CaptDetalleH> lstDetallesH;
                detallesH = bc.getCaptDetalleHServicioRemote().listarDetHPorCab(cabeceraActual.getIdCab());
                secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FM_C1");
                lstDetallesH = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secSel.getIdSeccion());

                if (pregMortalidad.equals("1") && !cantMortalidad.equals("")) {
                    if (lstDetallesH.size() != Integer.parseInt(cantMortalidad)) {
                        bc.addErrorMessage("(S4-P10) Mortalidad: Complete la cantidad de formularios ingresados");
                        frmMortalidadCompleto = false;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return frmMortalidadCompleto;
    }

    public boolean validaFrmEmigracionCompletos() {
        boolean frmMortalidadCompleto = true;
        try {
            if (elementoActual == 1) {
                int indexP1101S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1101"));
                int indexP1102S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1102"));
                String pregEmigracion = listaEnemdu.get(indexP1101S4).getValor();
                String cantEmigracion = listaEnemdu.get(indexP1102S4).getValor();

                List<CaptDetalleH> detallesH;
                MetSeccion secSel = new MetSeccion();
                List<CaptDetalleH> lstDetallesH;
                detallesH = bc.getCaptDetalleHServicioRemote().listarDetHPorCab(cabeceraActual.getIdCab());
                secSel = bc.getMetSeccionServicioRemote().obtenerMetSeccionPorNombre("CENSO_FE_C1");
                lstDetallesH = bc.getCaptDetalleHServicioRemote().listarDetHPorCabXSeccion(cabeceraActual.getIdCab(), secSel.getIdSeccion());

                if (pregEmigracion.equals("1") && !cantEmigracion.equals("")) {
                    if (lstDetallesH.size() != Integer.parseInt(cantEmigracion)) {
                        bc.addErrorMessage("(S4-P11) Emigración: Complete la cantidad de formularios ingresados");
                        frmMortalidadCompleto = false;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return frmMortalidadCompleto;
    }

    public void guardarValoresS4() {
        try {
            int indexP1S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V01")); //index tipo de vivienda
            int indexP2VPS3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V0201")); //index vivienda particular
            int indexP2VCS3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V0202")); //index vivienda colectiva
            opcTipoVivienda = listaEnemdu.get(indexP1S3).getValor();
            opcViviendaParticular = listaEnemdu.get(indexP2VPS3).getValor();
            opcViviendaColectiva = listaEnemdu.get(indexP2VCS3).getValor();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validacionesCenso() {
        boolean opcionRepetida = true;
        int contConyugue = 0;
        int contRepHogar = 0;
        int contVivColectiva = 0;
        int contSinVivienda = 0;
        int indexP1S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P01")); //index de parentesco
        int indexP1S1 = Integer.parseInt(mapaVariables.get("CENSO_S1_C12")); //index de existe vivienda
        String opcParentesco = listaEnemdu.get(indexP1S5).getValor();
        String opcExisteVivienda = listaEnemdu.get(indexP1S1).getValor();

        try {
            CaptCabecera cab = new CaptCabecera();
            cab = bc.getCaptCabeceraServicioRemote().buscarPorCodCarCon(cargaActual.getIdCarCon());
            MetVariable var = new MetVariable();
            List<CaptDetalleV> listV = new ArrayList<CaptDetalleV>();
            var = bc.getMetVariableServicioRemote().buscarVariablePorIdentificador("CENSO_S5_P01");
            listV = bc.getCaptDetalleVServicioRemote().listaValoresVariableXCab(cab.getIdCab(), var.getIdVar());

            if (listV.size() == 1) {
                //Valida que el primer miembro siempre sea representante de hogar
                if (!opcTipoVivienda.equals("")) {
                    if ((Integer.parseInt(opcTipoVivienda) >= 1 && Integer.parseInt(opcTipoVivienda) <= 8) && opcViviendaParticular.equals("1")) {
                        if (!opcParentesco.equals("1")) {
                            bc.addErrorMessage("(S5-P1) El 1er miembro en registrar debe ser: Representante del hogar");
                            opcionRepetida = false;
                        }
                    }
                }
                //Valida que si es VConlectiva el parentesco sea el mismo elegido
                if (!opcTipoVivienda.equals("")) {
                    if ((Integer.parseInt(opcTipoVivienda) >= 9 && Integer.parseInt(opcTipoVivienda) <= 18) && opcViviendaColectiva.equals("1")) {
                        if (!opcParentesco.equals("1")) {
                            bc.addErrorMessage("(S5-P1) Vivienda colectiva y relación de parentesco distinta a Miembro del hogar colectivo");
                            opcionRepetida = false;
                        }
                    }
                }
                //Valida que si miembro Sin Vivienda (opc9 en Sec1) el parentesco sea el mismo elegido
                if (opcExisteVivienda.equals("9") && opcTipoVivienda.equals("19") && !opcParentesco.equals("13")) {
                    bc.addErrorMessage("(S5-P1) Persona sin vivienda y relación de parentesco distinta a Persona sin vivienda");
                    opcionRepetida = false;
                }
            }
            //CUANDO HAY MAS MIEMBROS DE HOGAR
            if (listV.size() > 1) {
                //Valida si existe mas de 1 representante de hogar en el formulario
                for (int a = 0; a < listV.size(); a++) {
                    if (listV.get(a).getValor().equals("1")) {
                        contRepHogar++;
                    }
                }
                if (contRepHogar > 1) {
                    if (!opcTipoVivienda.equals("")) {
                        if ((Integer.parseInt(opcTipoVivienda) >= 1 && Integer.parseInt(opcTipoVivienda) <= 8) && opcViviendaParticular.equals("1")) {
                            bc.addErrorMessage("(S5-P1) Representante de hogar: Existe más de un miembro con este parentesco");
                            opcionRepetida = false;
                        }
                    }
                }
                //Valida si hay mas miembros el primero sea representante de hogar
                if (!opcTipoVivienda.equals("")) {
                    if ((Integer.parseInt(opcTipoVivienda) >= 1 && Integer.parseInt(opcTipoVivienda) <= 8) && opcViviendaParticular.equals("1")) {
                        if (!listV.get(0).getValor().equals("1")) {
                            bc.addErrorMessage("(S5-P1) El 1er miembro en registrar debe ser: Representante del hogar");
                            opcionRepetida = false;
                        }
                    }
                }
                //Valida si existe mas de 1 conyugue 
                for (int a = 0; a < listV.size(); a++) {
                    if (listV.get(a).getValor().equals("2")) {
                        contConyugue++;
                    }
                }
                if (contConyugue > 1) {
                    bc.addErrorMessage("(S5-P1) Cónyugue o conviviente: Existe más de un miembro con este parentesco");
                    opcionRepetida = false;
                }
                //Valida si es vivienda colectiva entonces solo debe escoger opc 12 en parentesco
                if (!opcTipoVivienda.equals("")) {
                    if ((Integer.parseInt(opcTipoVivienda) >= 9 && Integer.parseInt(opcTipoVivienda) <= 18) && opcViviendaColectiva.equals("1")) {
                        for (int a = 0; a < listV.size(); a++) {
                            if (listV.get(a).getValor().equals("12")) {
                                contVivColectiva++;
                            }
                        }
                        if (contVivColectiva != cab.getNumDet()) {
                            bc.addErrorMessage("(S5-P1) Vivienda colectiva: Todos los miembros deben tener parentesco Miembro de hogar colectivo");
                            opcionRepetida = false;
                        }
                    }
                }
            }
            if (validaFrmMortalidadCompletos() == false) {
                opcionRepetida = false;
            }

            if (validaFrmEmigracionCompletos() == false) {
                opcionRepetida = false;
            }
            //System.out.println("lista: " + listV.size());

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opcionRepetida;
    }

    public boolean validacionesCensoParaNuevoHogar() {
        boolean opcionRepetida = true;
        int contConyugue = 0;
        int contRepHogar = 0;
        int indexP1S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P01")); //index de parentesco       
        String opcParentesco = listaEnemdu.get(indexP1S5).getValor();

        try {
            CaptCabecera cab = new CaptCabecera();
            cab = bc.getCaptCabeceraServicioRemote().buscarPorCodCarCon(cargaActual.getIdCarCon());
            MetVariable var = new MetVariable();
            List<CaptDetalleV> listV = new ArrayList<CaptDetalleV>();
            var = bc.getMetVariableServicioRemote().buscarVariablePorIdentificador("CENSO_S5_P01");
            listV = bc.getCaptDetalleVServicioRemote().listaValoresVariableXCab(cab.getIdCab(), var.getIdVar());

            if (listV.size() == 1) {
                //Valida que el primer miembro siempre sea representante de hogar
                if (!opcParentesco.equals("1")) {
                    bc.addErrorMessage("(S5-P1) El 1er miembro en registrar debe ser: Representante del hogar");
                    opcionRepetida = false;
                }
            }
            //CUANDO HAY MAS MIEMBROS DE HOGAR
            if (listV.size() > 1) {
                //Valida si existe mas de 1 representante de hogar en el formulario
                for (int a = 0; a < listV.size(); a++) {
                    if (listV.get(a).getValor().equals("1")) {
                        contRepHogar++;
                    }
                }
                if (contRepHogar > 1) {
                    bc.addErrorMessage("(S5-P1) Representante de hogar: Existe más de un miembro con este parentesco");
                    opcionRepetida = false;
                }
                //Valida si hay mas miembros el primero sea representante de hogar
                if (!listV.get(0).getValor().equals("1")) {
                    bc.addErrorMessage("(S5-P1) El 1er miembro en registrar debe ser: Representante del hogar");
                    opcionRepetida = false;
                }
                //Valida si existe mas de 1 conyugue 
                for (int a = 0; a < listV.size(); a++) {
                    if (listV.get(a).getValor().equals("2")) {
                        contConyugue++;
                    }
                }
                if (contConyugue > 1) {
                    bc.addErrorMessage("(S5-P1) Cónyugue o conviviente: Existe más de un miembro con este parentesco");
                    opcionRepetida = false;
                }
            }

            if (validaFrmMortalidadCompletos() == false) {
                opcionRepetida = false;
            }
            if (validaFrmEmigracionCompletos() == false) {
                opcionRepetida = false;
            }
            //System.out.println("lista: " + listV.size());

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return opcionRepetida;
    }

    public void validarVerificar(String estado) {
        // guardarElemento(estado); Modifica JG (no se actualiza:se mantiene lógica actual en guardados)        
        try {
            if (elementoActual == 1) {
                if (validaExisteVivienda(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, No existe vivienda");
                } else if (validaAutocenso(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, Autocenso");
                } else if (validaViviendaParticular(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, vivienda particular");
                } else if (validaViviendaColectiva(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, vivienda colectiva");
                } else if (validaCodCuen()) {
                    if (validacionesCenso()) {
                        if (eliminarRegMortalidad() && eliminarRegEmigracion() && validaTieneCedula() && validaCedulasFrm()) {
                            bc.addSuccessMessage("(S5-P6) Pregunta Cédula validada");
                            if (guardarProvinciaSelect() && guardarPaisSelect()) {
                                //bc.addSuccessMessage("preguntas P7 y P8 Validado");
                                if (guardarIdiomaSelect() && guardarNacionalidadSelect()) {
                                    validarGuardarElemento();
                                    //validacionEnemduSoloAlertas();
                                    CaptEstado es = listaEstados.get((elementoActual - 1));
                                    es.setFechahoraEdicion(new Date());
                                    es.setEstado("V");
                                    bc.getCaptEstadoServicioRemote().editarCaptEstado(es);

                                    List<CaptEstado> lstEstadosCab = new ArrayList<>();
                                    lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
                                    Integer contEstd = 0;
                                    for (int i = 0; i < lstEstadosCab.size(); i++) {
                                        if (lstEstadosCab.get(i).getEstado().equals("V")) {
                                            contEstd++;
                                        }
                                    }

                                    if (contEstd.equals(lstEstadosCab.size())) {

                                        validarIntegridadForm1(estado);
                                        cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                                        cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                                        bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
                                    } else {
                                        bc.addWarningMessage("Integridad no realizada, le falta validar más miembros");
                                    }
                                    bc.addSuccessMessage("Información Validada y Verificada.");
                                    /* } else {
                            bc.addErrorMessage(" Error, revisar que existen errores en el formulario ");
                        }*/
                                } else {
                                    bc.addErrorMessage("(S5) Verifique preguntas de idioma o nacionalidad indígena");
                                }
                            } else {
                                bc.addErrorMessage("(S5) Verifique información P8 o P9");
                            }
                        } else {
                            bc.addErrorMessage("(S5) Verifique información P4 o P6");
                        }
                    }
//else {
//                        bc.addErrorMessage("(S5-P1) Verifique información en parentesco");
//                    }
                } else {
                    bc.addErrorMessage("(S3-P11) En código CUEN debe ingresar 10 dígitos");
                }
            } else if (elementoActual > 1) {
                if (banderaExisteVivienda) {
                    soloValidaUnMiembro(estado);
                    bc.addSuccessMessage("Validado y Guardado siguiente miembro, No existe vivienda");
                } else if (banderaAutocenso) {
                    soloValidaUnMiembro(estado);
                    bc.addSuccessMessage("Validado y Guardado siguiente miembro, Autocenso");
                } else if (banderaVParticular) {
                    soloValidaUnMiembro(estado);
                    bc.addSuccessMessage("Validado y Guardado siguiente miembro, vivienda Particular");
                } else if (banderaVColectiva) {
                    soloValidaUnMiembro(estado);
                    bc.addSuccessMessage("Validado y Guardado siguiente miembro, vivienda Colectiva");
                } else if (validacionesCenso() && validaTieneCedula() && validaCedulasFrm()) {
                    bc.addSuccessMessage("(S5-P6) Pregunta Cédula validada");
                    if (guardarProvinciaSelect() && guardarPaisSelect()) {
                        //bc.addSuccessMessage("preguntas P7 y P8 Validado");
                        if (guardarIdiomaSelect() && guardarNacionalidadSelect()) {
                            validarGuardarElemento();
                            //validacionEnemduSoloAlertas();
                            CaptEstado es = listaEstados.get((elementoActual - 1));
                            es.setFechahoraEdicion(new Date());
                            es.setEstado("V");
                            bc.getCaptEstadoServicioRemote().editarCaptEstado(es);

                            List<CaptEstado> lstEstadosCab = new ArrayList<>();
                            lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
                            Integer contEstd = 0;
                            for (int i = 0; i < lstEstadosCab.size(); i++) {
                                if (lstEstadosCab.get(i).getEstado().equals("V")) {
                                    contEstd++;
                                }
                            }

                            if (contEstd.equals(lstEstadosCab.size())) {

                                validarIntegridadForm1(estado);
                                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                                bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
                            } else {
                                bc.addWarningMessage("Integridad no realizada, le falta validar más miembros");
                            }
                            bc.addSuccessMessage("Información siguiente miembro Validada y Verificada.");

                        } else {
                            bc.addErrorMessage("(S5) Verifique preguntas de idioma o nacionalidad indígena");
                        }
                    } else {
                        bc.addErrorMessage("(S5) Verifique información P8 o P9");
                    }
                } else {
                    bc.addErrorMessage("(S5) Verifique información P4 o P6");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void validarVerificarNuevoHogar(String estado) {
        try {
            if (elementoActual == 1) {
                if (validacionesCensoParaNuevoHogar()) {
                    if (eliminarRegMortalidad() && eliminarRegEmigracion() && validaTieneCedula() && validaCedulasFrm()) {
                        bc.addSuccessMessage("(S5-P6) Pregunta Cédula validada");
                        if (guardarProvinciaSelect() && guardarPaisSelect()) {
                            //bc.addSuccessMessage("preguntas P7 y P8 Validado");
                            if (guardarIdiomaSelect() && guardarNacionalidadSelect()) {
                                validarGuardarElemento();
                                //validacionEnemduSoloAlertas();
                                CaptEstado es = listaEstados.get((elementoActual - 1));
                                es.setFechahoraEdicion(new Date());
                                es.setEstado("V");
                                bc.getCaptEstadoServicioRemote().editarCaptEstado(es);

                                List<CaptEstado> lstEstadosCab = new ArrayList<>();
                                lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
                                Integer contEstd = 0;
                                for (int i = 0; i < lstEstadosCab.size(); i++) {
                                    if (lstEstadosCab.get(i).getEstado().equals("V")) {
                                        contEstd++;
                                    }
                                }

                                if (contEstd.equals(lstEstadosCab.size())) {

                                    validarIntegridadForm1(estado);
                                    cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                                    cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                                    bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
                                } else {
                                    bc.addWarningMessage("Integridad no realizada, le falta validar más miembros");
                                }
                                bc.addSuccessMessage("Información Validada y Verificada.");
                                /* } else {
                            bc.addErrorMessage(" Error, revisar que existen errores en el formulario ");
                        }*/
                            } else {
                                bc.addErrorMessage("(S5) Verifique preguntas de idioma o nacionalidad indígena");
                            }
                        } else {
                            bc.addErrorMessage("(S5) Verifique información P8 o P9");
                        }
                    } else {
                        bc.addErrorMessage("(S5) Verifique información P4 o P6");
                    }
                }
//else {
//                    bc.addErrorMessage("(S5-P1) Verifique información en parentesco");
//                }

            } else if (elementoActual > 1) {
                if (validacionesCensoParaNuevoHogar() && validaTieneCedula() && validaCedulasFrm()) {
                    bc.addSuccessMessage("(S5-P6) Pregunta Cédula validada");
                    if (guardarProvinciaSelect() && guardarPaisSelect()) {
                        //bc.addSuccessMessage("preguntas P7 y P8 Validado");
                        if (guardarIdiomaSelect() && guardarNacionalidadSelect()) {
                            validarGuardarElemento();
                            //validacionEnemduSoloAlertas();
                            CaptEstado es = listaEstados.get((elementoActual - 1));
                            es.setFechahoraEdicion(new Date());
                            es.setEstado("V");
                            bc.getCaptEstadoServicioRemote().editarCaptEstado(es);

                            List<CaptEstado> lstEstadosCab = new ArrayList<>();
                            lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
                            Integer contEstd = 0;
                            for (int i = 0; i < lstEstadosCab.size(); i++) {
                                if (lstEstadosCab.get(i).getEstado().equals("V")) {
                                    contEstd++;
                                }
                            }

                            if (contEstd.equals(lstEstadosCab.size())) {

                                validarIntegridadForm1(estado);
                                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                                bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
                            } else {
                                bc.addWarningMessage("Integridad no realizada, le falta validar más miembros");
                            }
                            bc.addSuccessMessage("Información siguiente miembro Validada y Verificada.");

                        } else {
                            bc.addErrorMessage("(S5) Verifique preguntas de idioma o nacionalidad indígena");
                        }
                    } else {
                        bc.addErrorMessage("(S5) Verifique información P8 o P9");
                    }
                } else {
                    bc.addErrorMessage("(S5) Verifique información P4 o P6");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void validarVerificarNoEfectivo(String estado) {
        try {
            int indexP14S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V15"));
            if (validarCampoLlenoNoEfectivo()) {
                if (validaExisteVivienda(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, No existe vivienda");
                } else if (validaAutocenso(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, Autocenso");
                } else if (validaViviendaParticular(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, vivienda Particular");
                } else if (validaViviendaColectiva(estado)) {
                    bc.addSuccessMessage("Validada y Guardada, vivienda Colectiva");
                } else if (validaCodCuen()) {
                    if (!listaEnemdu.get(indexP14S3).getValor().equals("")) {
                        validarGuardarElementoNoEfectivo();

                        List<CaptEstado> lstEstadosCab = new ArrayList<>();
                        lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
                        Integer contEstd = 0;
                        for (int i = 0; i < lstEstadosCab.size(); i++) {
                            if (lstEstadosCab.get(i).getEstado().equals("V")) {
                                contEstd++;
                            }
                        }

                        if (contEstd.equals(lstEstadosCab.size())) {

                            validarIntegridadForm1(estado);
                            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));

                            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                            bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);
                        }
                        bc.addSuccessMessage("Información Validada y Verificada. Formulario No Efectivo");
                    } else {
                        bc.addErrorMessage("Complete todos los campos");
                    }
                } else {
                    bc.addErrorMessage("(S3-P11) En código CUEN debe ingresar 10 dígitos");
                }
            } else {
                bc.addErrorMessage("¡Llene campos!");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean controlaIndiceConficanza() {//Actualiza D.A
        boolean secLlena = false;
        //int iccCodPer = Integer.parseInt(mapaVariables.get("ENEM0121_ICCCODPER"));
        int icc1 = Integer.parseInt(mapaVariables.get("CENSO_S5_P0001"));
        //int icc2 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC2"));
        //int icc3 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC3"));
        //int icc4 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC4A"));
        // int icc5 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC5A"));
        //int icc6 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC6A"));
        // int icc7 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC7"));
        // int icc8 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC8A"));

        try {
//            if (elementoActual == 1) {
//                if ( !listaEnemdu.get(iccCodPer).getValor().equals("")
//                        && !listaEnemdu.get(iccCodPer).getValor().equals("")
//                        !listaEnemdu.get(icc1).getValor().equals("")
//                        && !listaEnemdu.get(icc2).getValor().equals("")
//                        && !listaEnemdu.get(icc3).getValor().equals("")
//                        && !listaEnemdu.get(icc4).getValor().equals("")
//                        && !listaEnemdu.get(icc5).getValor().equals("")
//                        && !listaEnemdu.get(icc6).getValor().equals("")
//                        && !listaEnemdu.get(icc7).getValor().equals("")
//                        && !listaEnemdu.get(icc8).getValor().equals("")) {
////                    if ((Integer.parseInt(listaEnemdu.get(iccCodPer).getValor()) != 99) && Integer.parseInt(listaEnemdu.get(iccCodPer).getValor()) > cabeceraActual.getNumDet()) {
////                        bc.addErrorMessage("Cod. Persona Sec.IC mayor al total de personas");
////                    } else {
////                        secLlena = true;
////                    }
//                    secLlena = true; // Se aumenta para el mes de enero ya que no tiene la vaidacion Cod. Persona Sec.IC mayor al total de personas
//                } else {
//                    secLlena = false;
//                    bc.addErrorMessage("Debe llenar todos los campos:Índice de Confianza");
//                }
//            } else {
//                secLlena = true;
//            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

        return secLlena;

    }

    public void guardarCabecerayCargaControl() {
        cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
        cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
        bcc.guardarCabecerayCargaControl(cabeceraActual, cargaActual);//To change body of generated methods, choose Tools | Templates.
    }

    public void cambiarDeElemento() {
        try {

            System.out.println(elementoActual);
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), elementoActual, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            PrimeFaces requestContext = PrimeFaces.current();

            seteaValoresSincronizacionProvincias();
            seteaValoresSincronizacionPaises();
            seteaValoresSincroIdiomaNacionalidad();
            contieneSelectPaises();
            contieneSelectProv();
            contieneIdioma();
            contieneNacionalidad();
            if (elementoActual == 1 && cargaActual.getControl2().equals("1")) {
                guardarValoresS4();
            }

            requestContext.ajax().update("frmCaptura:f" + cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            requestContext.ajax().update("frmCaptura:tbCaptura");
            requestContext.executeScript("fn_document_ready();");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    private void cargarTotalPersonas() {
        int indexTotalPersona = Integer.parseInt(mapaVariables.get("CENSO_S4_H1203"));//Total
        int totalPersonas = cabeceraActual.getNumDet();
        listaEnemdu.get(indexTotalPersona).setValor(String.valueOf(totalPersonas));
    }

    public void crearNuevoElemento() {
        try {

            String msg = validarCreacionElemento(posicionNuevoElemento);
            //String msg = "";
            if (msg.equals("")) {
                int n = cabeceraActual.getNumDet() + 1;
                bcc.agregarElementoVFormulario(cabeceraActual, posicionNuevoElemento);
                //se setea la variable numDet para no deshacer lo realizado en el metodo agregar..
                cabeceraActual.setNumDet(n);
                cabeceraActual.setEstado("00");
                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
                bcc.crearEstadoDeNuevoElementoDeFormulario(cabeceraActual, posicionNuevoElemento);
                bc.addSuccessMessage("Elemento Creado correctamente. ");
                listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());

                //listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
                if (posicionNuevoElemento <= elementoActual) {
                    elementoActual = elementoActual + 1;

                    PrimeFaces requestContext = PrimeFaces.current();

                    requestContext.ajax().update("frmCaptura:tbCaptura");
                }
                cargarInicialesControlResumen();
                cargarIniciales();
            } else {
                bc.addErrorMessage(msg);
            }
            posicionNuevoElemento = null;
            cargarTotalPersonas();
            registrarPersonas();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }


    /*METODO QUE ELIMINA UN ELEMENTO DEL CUESTIONARIO Y REORDENA LOS QUE PERMANECEN*/
    public void eliminarMiembro(Integer elemento) {
        try {
            //String msg = validarEliminacionElemento(elemento.getNum());
            String msg = "";
            if (msg.equals("")) {

                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_ELIM"));
                bcc.eliminarElemento(cabeceraActual, elemento);
                bc.addSuccessMessage("Elemento Eliminado correctamente");
                listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());
                //listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
                //se setea la variable numDet para no deshacer lo realizado en el metodo eliminar..
                cabeceraActual.setNumDet((cabeceraActual.getNumDet() - 1));
                actualizarEstadoCabeceraPorEstadosElementos(cabeceraActual, listaEstados);
                //Se actualiza las tablas horizontales funciones para el llenado de las tablas

                cargarInicialesControlResumen();
                cargarTotalPersonas();

                if (elemento == elementoActual) {
                    System.out.println("entro");

                    cambiarDeElemento(elementoActual - 1);
                } else if (elemento < elementoActual) {
                    cambiarDeElemento(elementoActual);
                } else {

                }
            } else {
                bc.addErrorMessage(msg);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private String validarCreacionElemento(int nuevaPosicion) {
        String msg = "";
        try {

            if (nuevaPosicion <= (cabeceraActual.getNumDet() + 1) && nuevaPosicion != 1 && nuevaPosicion > 0) {

            } else {
                msg = msg + "Posición no permitida.";
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return msg;
    }

    private void actualizarEstadoCabeceraPorEstadosElementos(CaptCabecera cab, List<CaptEstado> lstEstadosCab) {
        try {
            Integer contEstd = 0;
            for (int i = 0; i < lstEstadosCab.size(); i++) {
                if (lstEstadosCab.get(i).getEstado().equals("V")) {
                    contEstd++;
                }
            }
            if (contEstd.equals(lstEstadosCab.size())) {
                cab.setEstado("V");
                bcc.guardarCabecerayCargaControl(cab, cargaActual);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /*METODO LLAMADO DESDE EL BOTON DE LA PAGINA*/
    public void cambiarDeElemento(int l) {
        try {
            elementoActual = l;
            cambiarDeElemento();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void regresarAInicio() {

        try {
            uc.getSession().setAttribute("conglo", cabeceraActual.getClave());
            String url = "/sipe-captura-siac-war/paginas/captura/inicioCapturaCenso.xhtml";
            bc.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean validaCodigosUsoInec(String tipoCatalogo, String valorCatalogo) { //Valida el ingreso de codigos INEC(CIIU-CIUO-TITULOS)
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

//-----Metodos para validación de Omisiones y Descarga de Bases de Datos---
    public boolean isIntegridadValidada() {
        return integridadValidada;
    }

    public void setIntegridadValidada(boolean integridadValidada) {
        this.integridadValidada = integridadValidada;
    }

    private void fijarEstadoSupIntegridad(CaptCabecera cab) {
        integridadSup = false;

        if (cab.getEstado() != null && (cab.getEstado().equals("VS") || cab.getEstado().equals("VC"))) {
            integridadSup = true;

        }

    }

    private void fijarEstadoValidacionIntegridad(CaptCabecera cab) {
        integridadValidada = false;

        if (cab.getEstado() != null && ((cab.getEstado().equals("VC") || cab.getEstado().equals("RC")))) {
            integridadValidada = true;

        }

    }

    public void validarIntegridadForm1(String estado) {
        try {
            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
            //bcc.guardarTodasSeccionesHElementoActual(cargarListaSeccionesHParaGuardado());
            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("censo2021_s1_s4", cabeceraActual.getIdCab());
            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("censo2021_s5", cabeceraActual.getIdCab());
            //bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("enem2107_s6", cabeceraActual.getIdCab());

            String msgValInts1s3 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("censo2021_s1_s4", cabeceraActual.getIdCab());
            String msgValInts5 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("censo2021_s5", cabeceraActual.getIdCab());
            //String msgValInts6 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("enem2107_s6", cabeceraActual.getIdCab());

            if (msgValInts5.length() > 0 || msgValInts1s3.length() > 0) {
                bc.addErrorMessage(msgValInts1s3 + " " + msgValInts5);
                bc.addErrorMessage("Integridad No Validada.");
                cabeceraActual.setEstado3("2");
            } else {
                if (elementosValidados()) {
                    bc.addSuccessMessage("Integridad Validada.");

                    guardarTransaccionPorRolUsuario(estado);
                    cabeceraActual.setEstado3("1");
                } else {
                    bc.addErrorMessage("Verifique el formulario!");
                }
            }
            bcc.guardarCabecera(cabeceraActual);
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);
            PrimeFaces requestContext = PrimeFaces.current();

            requestContext.ajax().update("frmCaptura:tbCaptura");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void validarIntegridadForm1Autocenso(String estado) {
        try {
            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);

            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("censo2021_s1_s4", cabeceraActual.getIdCab());
            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("censo2021_s5", cabeceraActual.getIdCab());

            String msgValInts1s3 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("censo2021_s1_s4", cabeceraActual.getIdCab());
            String msgValInts5 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("censo2021_s5", cabeceraActual.getIdCab());

            if (msgValInts5.length() > 0 || msgValInts1s3.length() > 0) {
                bc.addErrorMessage(msgValInts1s3 + " " + msgValInts5);
                bc.addErrorMessage("Integridad No Validada.");
                cabeceraActual.setEstado3("2");
            } else {
                bc.addSuccessMessage("Integridad Validada.");
                guardarTransaccionPorRolUsuario(estado);
                cabeceraActual.setEstado3("1");
            }
            bcc.guardarCabecera(cabeceraActual);
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);
            PrimeFaces requestContext = PrimeFaces.current();

            requestContext.ajax().update("frmCaptura:tbCaptura");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean elementosValidados() {
        boolean validadosTodos = false;
        try {
            List<CaptEstado> lstEstadosCab = new ArrayList<>();
            lstEstadosCab = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(cabeceraActual.getIdCab());
            Integer contEstd = 0;
            for (int i = 0; i < lstEstadosCab.size(); i++) {
                if (lstEstadosCab.get(i).getEstado().equals("V")) {
                    contEstd++;
                }
            }
            if (contEstd.equals(lstEstadosCab.size())) {
                validadosTodos = true;
            } else {
                validadosTodos = false;
                bc.addErrorMessage("Un elemento del formulario aun no ha sido validado");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return validadosTodos;
    }

    public String controlaNulos(Object obj) {
        return Objects.isNull(obj) ? "0" : obj.toString();

    }

    public String controlaNullString(Object[] obj, int indice) {
        String datoValor;
        if (obj == null) {
            datoValor = "";
        } else {
            datoValor = (String) obj[indice];
        }
        return datoValor;
    }

    public int controlaNullInt(Object[] obj, int indice) {
        int datoValor;
        if (obj == null) {
            datoValor = 0;
        } else {
            datoValor = (int) obj[indice];
        }
        return datoValor;
    }

    public void convertirJsonAMapa(String jsonLine) {
        mapaVariables = new HashMap<String, String>();
        JsonArray entries = (JsonArray) new JsonParser().parse(jsonLine);
        for (Iterator<JsonElement> iterator = entries.iterator(); iterator.hasNext();) {
            JsonElement next = iterator.next();
            String str = next.toString();
            str = str.substring(1, str.length() - 1);
            String[] strArray = str.split(",");
            mapaVariables.put(strArray[0].replaceAll("^\"|\"$", ""), strArray[1].replaceAll("^\"|\"$", ""));
        }

    }

    public void okey() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String movil = params.get("x");

        convertirJsonAMapa(movil);
        if (!cabeceraActual.getInfo1().equals("N")) {
            cargarInicialesControlResumen();
        }
        if (cabeceraActual.getEstado().equals("00") || cabeceraActual.getEstado().equals("G")) {
            cargarIniciales();

        }
    }

    public void mensajesValid() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String movil = params.get("y");
        bc.addErrorMessage(movil);
        PrimeFaces requestContext = PrimeFaces.current();

        requestContext.ajax().update("frmCaptura:msgs");

    }

    public void cargarInicialesControlResumen() {
        listaElementosControlEnemdu.clear();

        List<CaptVarV> listaAuxiliarResumenEnemdu = new ArrayList<>();
        for (Iterator<CaptEstado> iterator = listaEstados.iterator(); iterator.hasNext();) {
            try {

                CaptEstado next = iterator.next();
                listaAuxiliarResumenEnemdu.clear();
                listaAuxiliarResumenEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), next.getNumDet(), cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());

                Map<String, Object> mapaControl = new HashMap();
                mapaControl.put("CENSO_S5_P00", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P00"))).getValor());
                mapaControl.put("CENSO_S5_P0001", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P0001"))).getValor());
                mapaControl.put("CENSO_S5_P0002", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P0002"))).getValor());
                mapaControl.put("CENSO_S5_P0003", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P0003"))).getValor());
                mapaControl.put("CENSO_S5_P0004", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P0004"))).getValor());
                mapaControl.put("CENSO_S5_P01", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P01"))).getValor());
                mapaControl.put("CENSO_S5_P02", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P02"))).getValor());
                mapaControl.put("CENSO_S5_P03", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P03"))).getValor());
                //mapaControl.put("CENSO_S5_P0601N", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("CENSO_S5_P0601N"))).getValor());
                mapaControl.put("num", next.getNumDet());
                listaElementosControlEnemdu.add(mapaControl);

            } catch (Exception ex) {
                Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        PrimeFaces requestContext = PrimeFaces.current();

        requestContext.ajax().update("frmCaptura:lstElementosControl");

        requestContext.ajax().update("frmCaptura:tbCaptura");

    }

    public void cargarIniciales() {

        primerasPreguntas.clear();

        for (Iterator<CaptEstado> iterator = listaEstados.iterator(); iterator.hasNext();) {
            try {
                VariablesPrimerasPreguntas primerasPreg = new VariablesPrimerasPreguntas();

                CaptEstado next = iterator.next();
                // primerasPreg.setListaDetalles(bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElementoyRangoVariables(cabeceraActual.getIdCab(), next.getNumDet(), cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario(), 2, 10));
                primerasPreg.setListaDetalles(bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(cabeceraActual.getIdCab(), next.getNumDet(), cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario(), "CENSO_S5_P0001,CENSO_S5_P0002,CENSO_S5_P0003,CENSO_S5_P0004,CENSO_S5_P01,CENSO_S5_P02,CENSO_S5_P03"));

                // LS+bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(cabeceraActual.getIdCab(), next.getNumDet(), cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario(),"ENEM1020_NOMBRE1,ENEM1020_NOMBRE2,ENEM1020_APELLIDO1,ENEM1020_APELLIDO2,ENEM1020_P02,ENEM1020_P03,ENEM1020_P04,ENEM1020_P05A,ENEM1020_P05B").size());
                // for
                primerasPreg.trasladarDetAIniciales();

                primerasPreguntas.add(primerasPreg);
            } catch (Exception ex) {
                Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        listaInicial = (List<Map<String, String>>) primerasPreguntas.stream().map(x -> x.variablesEnMapa).collect(Collectors.toList());
        PrimeFaces requestContext = PrimeFaces.current();

        //requestContext.ajax().update("frmCaptura:dlgMiembrosInicial");
    }

    public void listarSaltos() {
        try {
            CaptCabecera objetoCab = (CaptCabecera) uc.getSession().getAttribute("capt_cabecera_actual");
            if (objetoCab != null) {
                if (objetoCab.getIdCab() != null) {
                    cabeceraActual = objetoCab;
                    listaSaltos = new JSONArray(bc.getMetSaltoServicioRemote().listarSaltos(cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario()));
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarValiadciones() {

        try {
            CaptCabecera objetoCab = (CaptCabecera) uc.getSession().getAttribute("capt_cabecera_actual");
            if (objetoCab != null) {
                if (objetoCab.getIdCab() != null) {
                    cabeceraActual = objetoCab;
                    listaValidaciones = new JSONArray(bc.getMetValidacionServicioRemote().listarValidacion(cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario()));
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarTransaccionPorRolUsuario(String usuario) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:SS");
        String fechaComoCadena = sf.format(new Date());
        try {
            if (uc.usuarioTieneRol("ROL_VALID") && (usuario.equals("VC") || usuario.equals("VS") || usuario.equals("VG") || usuario.equals("RC"))) {

                cabeceraActual.setEstado(usuario);
                cabeceraActual.setInfo7(uc.getUsuario() + " Edición: " + fechaComoCadena);
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
                bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);

            }

            if (uc.usuarioTieneRol("ROL_REVIS") && (usuario.equals("VC") || usuario.equals("VS"))) {
                cabeceraActual.setEstado(usuario);
                cabeceraActual.setInfo6(uc.getUsuario() + " Edición: " + fechaComoCadena);
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);

            }
            if (uc.usuarioTieneRol("ROL_SUPER") && (usuario.equals("VS") || usuario.equals("VG"))) {
                cabeceraActual.setEstado(usuario);
                cabeceraActual.setInfo5(uc.getUsuario() + " Edición: " + fechaComoCadena);
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);

            }
            if (uc.usuarioTieneRol("ROL_ENCUE") && (usuario.equals("G") || usuario.equals("VG"))) {
                cabeceraActual.setEstado(usuario);
                cabeceraActual.setInfo4(uc.getUsuario() + " Edición: " + fechaComoCadena);
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);

            }
            if (cabeceraActual.getFechahoraValidacion() == null) {
                cabeceraActual.setFechahoraValidacion(new Date());
            }

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public class VariablesPrimerasPreguntas {

        Map<String, String> variablesEnMapa;
        List<CaptVarV> listaDetalles;

        public VariablesPrimerasPreguntas() {
            variablesEnMapa = new LinkedHashMap<>();
            listaDetalles = new ArrayList<>();
        }

        public void trasladarInicialesADet() {
            listaDetalles.get(0).setValor(variablesEnMapa.get("CENSO_S5_P0001"));
            listaDetalles.get(1).setValor(variablesEnMapa.get("CENSO_S5_P0002"));
            listaDetalles.get(2).setValor(variablesEnMapa.get("CENSO_S5_P0003"));
            listaDetalles.get(3).setValor(variablesEnMapa.get("CENSO_S5_P0004"));
            listaDetalles.get(4).setValor(variablesEnMapa.get("CENSO_S5_P01"));
            listaDetalles.get(5).setValor(variablesEnMapa.get("CENSO_S5_P02"));
            listaDetalles.get(6).setValor(variablesEnMapa.get("CENSO_S5_P03"));
        }

        public void trasladarDetAIniciales() {
            variablesEnMapa.put("CENSO_S5_P0001", listaDetalles.get(0).getValor());
            variablesEnMapa.put("CENSO_S5_P0002", listaDetalles.get(1).getValor());
            variablesEnMapa.put("CENSO_S5_P0003", listaDetalles.get(2).getValor());
            variablesEnMapa.put("CENSO_S5_P0004", listaDetalles.get(3).getValor());
            variablesEnMapa.put("CENSO_S5_P01", listaDetalles.get(4).getValor());
            variablesEnMapa.put("CENSO_S5_P02", listaDetalles.get(5).getValor());
            variablesEnMapa.put("CENSO_S5_P03", listaDetalles.get(6).getValor());
        }

        public List<CaptVarV> getListaDetalles() {
            return listaDetalles;
        }

        public void setListaDetalles(List<CaptVarV> listaDetalles) {
            this.listaDetalles = listaDetalles;
        }

        public Map<String, String> getVariablesEnMapa() {
            return variablesEnMapa;
        }

        public void setVariablesEnMapa(Map<String, String> variablesEnMapa) {
            this.variablesEnMapa = variablesEnMapa;
        }

        @Override
        public String toString() {
            return "VariablesPrimerasPreguntas{" + "variablesEnMapa=" + variablesEnMapa + ", listaDetalles=" + listaDetalles + '}';
        }

    }

    public void guardarIniciales(List<Map<String, String>> inicial) {

        if (inicial.stream().filter(map -> (Objects.isNull(map.get("CENSO_S5_P0001"))
                || map.get("CENSO_S5_P0001").equals("")
                || Objects.isNull(map.get("CENSO_S5_P0003"))
                || map.get("CENSO_S5_P0003").equals("")
                || Objects.isNull(map.get("CENSO_S5_P01"))
                || map.get("CENSO_S5_P01").equals("")
                || Objects.isNull(map.get("CENSO_S5_P02"))
                || map.get("CENSO_S5_P02").equals("")
                || Objects.isNull(map.get("CENSO_S5_P03"))
                || map.get("CENSO_S5_P03").equals("")))
                .count() == 0) {

            //Sumar el total de hombres y mujeres para cargar en los input correspondientes del formulario
            //en la sección IV 
            int totalHombres = 0;
            int totalMujeres = 0;
            int indexHombresP12 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1201"));//Hombres;
            int indexMujeresP12 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1202"));//Mujeres;
            for (Map<String, String> l : inicial) {
                if (l.get("CENSO_S5_P02").equals("1")) {
                    totalHombres++;
                    listaEnemdu.get(indexHombresP12).setValor(String.valueOf(totalHombres));
                } else {
                    totalMujeres++;
                    listaEnemdu.get(indexMujeresP12).setValor(String.valueOf(totalMujeres));
                }
            }
            guardarElemento("G");

            for (VariablesPrimerasPreguntas vari : primerasPreguntas) {

                try {

                    vari.setVariablesEnMapa(inicial.get(primerasPreguntas.indexOf(vari)));
                    vari.trasladarInicialesADet();

                    bc.getCaptDetalleVServicioRemote().actualizarDetallesVDeFormularioPorElemento(vari.getListaDetalles());
                    cabeceraActual.setEstado("G");
                    bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
                    listaEnemdu = bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElemento(cabeceraActual.getIdCab(), 1, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
                    cargarInicialesControlResumen();
                    PrimeFaces requestContext = PrimeFaces.current();
                    requestContext.ajax().update("frmCaptura:pnlBotones");
                    requestContext.ajax().update("frmCaptura:lstEstados");
                    requestContext.ajax().update("frmCaptura:pnlInfo");
                    requestContext.ajax().update("frmCaptura:pnlElementosControl");
                    //requestContext.ajax().update("frmCaptura:f" + cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
                    requestContext.ajax().update("frmCaptura:tbCaptura");
                    requestContext.executeScript("PF('dlgMiembrosInicial').hide()");
                    requestContext.executeScript("fn_document_ready();");

                } catch (Exception ex) {
                    Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            habilitaCrearMiembro = true;

        } else {
            bc.addErrorMessage("Ingrese todos los campos obligatorios por favor");
        }

    }

    private void habilitarCreaMiembro() {
        try {
            int indexPrimerNombre = Integer.parseInt(mapaVariables.get("CENSO_S5_P0001"));
            if (!listaEnemdu.get(indexPrimerNombre).getValor().equals("")) {
                habilitaCrearMiembro = true;
            }
        } catch (NullPointerException e) {
        }
    }

    public void registrarPersonas() {
        try {
            if (cabeceraActual.getEstado().equals("00") || cabeceraActual.getEstado().equals("G")) {
                bc.addSuccessMessage("Por favor registre a las personas.");
                registro = true;
            } else {
                registro = false;
            }
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public void registrarPersonas() {
//        try {
//            if (cabeceraActual.getEstado().equals("00")) {
//                bc.addSuccessMessage("Por favor registre a las personas.");
//                registro = true;
//            }
//            if (cabeceraActual.getEstado().equals("G")) {
//                bc.addErrorMessage("Las personas ya fueron registradas");
//                registro = false;
//            }
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }

    public void onChangeProvinciaP7() {
        try {
            Map<String, String> paramsP7 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (paramsP7.get("actionP7") != null) {
                System.out.println("onChangeProvinciaP7");
                cantonSelectedP7 = new MetCatalogo();
            }
            if (provinciaSelectedP7 != null && provinciaSelectedP7.getIdCatalogo() != null) {
                listaCantonesP7 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(provinciaSelectedP7.getIdCatalogo());

            } else {
                listaCantonesP7 = new ArrayList<>();
            }

            onChangeCantonP7();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onChangeCantonP7() {
        try {
            if (cantonSelectedP7 != null && cantonSelectedP7.getIdCatalogo() != null) {
                listaParroquiasP7 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(cantonSelectedP7.getIdCatalogo());
            } else {

                listaParroquiasP7 = new ArrayList<>();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onChangeProvinciaP8() {
        try {
            Map<String, String> paramsP8 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (paramsP8.get("actionP8") != null) {
                System.out.println("onChangeProvinciaP8");
                cantonSelectedP8 = new MetCatalogo();
            }
            if (provinciaSelectedP8 != null && provinciaSelectedP8.getIdCatalogo() != null) {
                listaCantonesP8 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(provinciaSelectedP8.getIdCatalogo());
            } else {
                listaCantonesP8 = new ArrayList<>();
            }

            onChangeCantonP8();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onChangeCantonP8() {
        try {
            if (cantonSelectedP8 != null && cantonSelectedP8.getIdCatalogo() != null) {
                listaParroquiasP8 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(cantonSelectedP8.getIdCatalogo());
            } else {
                listaParroquiasP8 = new ArrayList<>();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void onPais() {
        try {
            tipoCatalogoPaises = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_PAIS_6DIG");
            listaPaisesP7P8 = bc.getMetCatalogoServicioRemote().listarCatalogosPorTipoCatalogo(tipoCatalogoPaises.getIdTipoCatalogo());
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void contieneSelectProv() {
        try {
            String provinciaP7 = listaEnemdu.get(87).getValor();
            if (provinciaP7 == null) {
                provinciaP7 = "";
            }
            //System.out.println("provinciaP7: " + provinciaP7);            

            if (provinciaP7.length() < 2) {
                //System.out.println("ingreso a 1er if p7");                
                provinciaSelectedP7 = new MetCatalogo();
                listaCantonesP7 = new ArrayList<>();
                listaParroquiasP7 = new ArrayList<>();
            } else if (provinciaP7.length() > 2) {
                //System.out.println("ingreso a 2do if p7");
                provinciaSelectedP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoProv.getIdTipoCatalogo(), provinciaP7);
                listaCantonesP7 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(provinciaSelectedP7.getIdCatalogo());
                onChangeCantonP7();
                cantonSelectedP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoCanton.getIdTipoCatalogo(), listaEnemdu.get(88).getValor());
                listaParroquiasP7 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(cantonSelectedP7.getIdCatalogo());
                parroquiaSelectedP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoParr.getIdTipoCatalogo(), listaEnemdu.get(89).getValor());
            }

            //P8            
            String provinciaP8 = listaEnemdu.get(94).getValor();
            if (provinciaP8 == null) {
                provinciaP8 = "";
            }
            //System.out.println("provinciaP8: " + provinciaP8);            

            if (provinciaP8.length() < 2) {
                //System.out.println("ingreso a 1er if p8");
                provinciaSelectedP8 = new MetCatalogo();
                listaCantonesP8 = new ArrayList<>();
                listaParroquiasP8 = new ArrayList<>();
            } else if (provinciaP8.length() > 2) {
                //System.out.println("ingreso a 2do if p8");
                provinciaSelectedP8 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoProv.getIdTipoCatalogo(), provinciaP8);
                listaCantonesP8 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(provinciaSelectedP8.getIdCatalogo());
                onChangeCantonP8();
                cantonSelectedP8 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoCanton.getIdTipoCatalogo(), listaEnemdu.get(95).getValor());
                listaParroquiasP8 = bc.getMetCatalogoServicioRemote().listarCatalogosPorPadre1(cantonSelectedP8.getIdCatalogo());
                parroquiaSelectedP8 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoParr.getIdTipoCatalogo(), listaEnemdu.get(96).getValor());
            }

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void contieneSelectPaises() {
        try {
            paisSelectedP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoPaises.getIdTipoCatalogo(), listaEnemdu.get(91).getValor());
            paisSelectedP8 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoPaises.getIdTipoCatalogo(), listaEnemdu.get(98).getValor());
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void seteaValoresSincronizacionPaises() {
        try {
            if (!cabeceraActual.getInfo1().equals("N")) {
                //Pais P7S5
                String paisEnDBP7 = listaEnemdu.get(91).getValor();
                //System.out.println("paisNmu: " + paisEnDBP7);
                if (paisEnDBP7 != null) {
                    boolean integerOrNotP7 = paisEnDBP7.matches("-?\\d+");
                    if (integerOrNotP7) {
                        MetTipoCatalogo tipoCatalogoPaisSincro = new MetTipoCatalogo();
                        MetCatalogo paisP7 = new MetCatalogo();
                        tipoCatalogoPaisSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_PAIS_6DIG");
                        paisP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoPaisSincro.getIdTipoCatalogo(), paisEnDBP7);
                        //System.out.println("paisP7: " + paisP7);
                        listaEnemdu.get(91).setValor(paisP7.getNombre());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }
                //Pais P8S5
                String paisEnDBP8 = listaEnemdu.get(98).getValor();
                if (paisEnDBP8 != null) {
                    boolean integerOrNotP8 = paisEnDBP8.matches("-?\\d+");
                    if (integerOrNotP8) {
                        MetTipoCatalogo tipoCatalogoPaisSincro = new MetTipoCatalogo();
                        MetCatalogo paisP8 = new MetCatalogo();
                        tipoCatalogoPaisSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_PAIS_6DIG");
                        paisP8 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoPaisSincro.getIdTipoCatalogo(), paisEnDBP8);
                        //System.out.println("paisP8: " + paisP8);
                        listaEnemdu.get(98).setValor(paisP8.getNombre());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void seteaValoresSincronizacionProvincias() {
        try {
            if (!cabeceraActual.getInfo1().equals("N")) {
                //Provincias P7S5
                String provEnDBP7 = listaEnemdu.get(87).getValor();
                //System.out.println("provNum: " + provEnDBP7);
                if (provEnDBP7 != null) {
                    boolean integerOrNotProvP7 = provEnDBP7.matches("-?\\d+");
                    if (integerOrNotProvP7) {
                        MetTipoCatalogo tipoCatalogoProvSincro = new MetTipoCatalogo();
                        MetCatalogo provP7 = new MetCatalogo();
                        tipoCatalogoProvSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_PROV");
                        provP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoProvSincro.getIdTipoCatalogo(), provEnDBP7);
                        //System.out.println("provP7: " + provP7);
                        listaEnemdu.get(87).setValor(provP7.getNombre());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }
                String cantEnDBP7 = listaEnemdu.get(88).getValor();
                //System.out.println("provNum: " + cantEnDBP7);
                if (cantEnDBP7 != null) {
                    boolean integerOrNotCantP7 = cantEnDBP7.matches("-?\\d+");
                    if (integerOrNotCantP7) {
                        MetTipoCatalogo tipoCatalogoCantSincro = new MetTipoCatalogo();
                        MetCatalogo cantP7 = new MetCatalogo();
                        tipoCatalogoCantSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_CANTON");
                        cantP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoCantSincro.getIdTipoCatalogo(), cantEnDBP7);
                        //System.out.println("cantP7: " + cantP7);
                        listaEnemdu.get(88).setValor(cantP7.getNombre());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }
                String parrEnDBP7 = listaEnemdu.get(89).getValor();
                //System.out.println("parrNum: " + parrEnDBP7);
                if (parrEnDBP7 != null) {
                    boolean integerOrNotParrP7 = parrEnDBP7.matches("-?\\d+");
                    if (integerOrNotParrP7) {
                        MetTipoCatalogo tipoCatalogoParrSincro = new MetTipoCatalogo();
                        MetCatalogo parrP7 = new MetCatalogo();
                        tipoCatalogoParrSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_PARR");
                        parrP7 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoParrSincro.getIdTipoCatalogo(), parrEnDBP7);
                        //System.out.println("parrP7: " + parrP7);
                        listaEnemdu.get(89).setValor(parrP7.getNombre());
                        listaEnemdu.get(90).setValor(parrP7.getValor());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }
                //Provincias P8S5
                String provEnDBP8 = listaEnemdu.get(94).getValor();
                //System.out.println("provNum: " + provEnDBP8);
                if (provEnDBP8 != null) {
                    boolean integerOrNotProvP8 = provEnDBP8.matches("-?\\d+");
                    if (integerOrNotProvP8) {
                        MetTipoCatalogo tipoCatalogoProvSincro = new MetTipoCatalogo();
                        MetCatalogo provP8 = new MetCatalogo();
                        tipoCatalogoProvSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_PROV");
                        provP8 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoProvSincro.getIdTipoCatalogo(), provEnDBP8);
                        //System.out.println("provP8: " + provP8);
                        listaEnemdu.get(94).setValor(provP8.getNombre());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }
                String cantEnDBP8 = listaEnemdu.get(95).getValor();
                //System.out.println("cantNum: " + cantEnDBP8);
                if (cantEnDBP8 != null) {
                    boolean integerOrNotCantP8 = cantEnDBP8.matches("-?\\d+");
                    if (integerOrNotCantP8) {
                        MetTipoCatalogo tipoCatalogoCantSincro = new MetTipoCatalogo();
                        MetCatalogo cantP78 = new MetCatalogo();
                        tipoCatalogoCantSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_CANTON");
                        cantP78 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoCantSincro.getIdTipoCatalogo(), cantEnDBP8);
                        //System.out.println("cantP8: " + cantP78);
                        listaEnemdu.get(95).setValor(cantP78.getNombre());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }
                String parrEnDBP8 = listaEnemdu.get(96).getValor();
                //System.out.println("parrNum: " + parrEnDBP8);
                if (parrEnDBP8 != null) {
                    boolean integerOrNotParrP8 = parrEnDBP8.matches("-?\\d+");
                    if (integerOrNotParrP8) {
                        MetTipoCatalogo tipoCatalogoParrSincro = new MetTipoCatalogo();
                        MetCatalogo parrP8 = new MetCatalogo();
                        tipoCatalogoParrSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_DPA_PARR");
                        parrP8 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoParrSincro.getIdTipoCatalogo(), parrEnDBP8);
                        //System.out.println("parrP8: " + parrP8);
                        listaEnemdu.get(96).setValor(parrP8.getNombre());
                        listaEnemdu.get(97).setValor(parrP8.getValor());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void seteaValoresSincroIdiomaNacionalidad() {
        try {
            if (!cabeceraActual.getInfo1().equals("N")) {
                //Idioma indigena
                String idiomaEnDBP10 = listaEnemdu.get(100).getValor();
                if (idiomaEnDBP10 != null) {
                    boolean integerOrNotP10 = idiomaEnDBP10.matches("-?\\d+");
                    if (integerOrNotP10) {
                        MetTipoCatalogo tipoCatalogoIdiomaSincro = new MetTipoCatalogo();
                        MetCatalogo idiomaP10 = new MetCatalogo();
                        tipoCatalogoIdiomaSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_IDIOMA");
                        idiomaP10 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoIdiomaSincro.getIdTipoCatalogo(), idiomaEnDBP10);
                        listaEnemdu.get(100).setValor(idiomaP10.getNombre()); //apunta a input
                        listaEnemdu.get(101).setValor(idiomaP10.getValor()); //apunta a codificacion
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }
                //Nacionalidad
                String NacionalidadEnDBP12 = listaEnemdu.get(109).getValor();
                if (NacionalidadEnDBP12 != null) {
                    boolean integerOrNotP12 = NacionalidadEnDBP12.matches("-?\\d+");
                    if (integerOrNotP12) {
                        MetTipoCatalogo tipoCatalogoNacionalidadSincro = new MetTipoCatalogo();
                        MetCatalogo nacionalidadP12 = new MetCatalogo();
                        tipoCatalogoNacionalidadSincro = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_NACIONALIDAD");
                        nacionalidadP12 = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYValor(tipoCatalogoNacionalidadSincro.getIdTipoCatalogo(), NacionalidadEnDBP12);
                        //System.out.println("paisP8: " + paisP8);
                        listaEnemdu.get(109).setValor(nacionalidadP12.getNombre());
                        listaEnemdu.get(110).setValor(nacionalidadP12.getValor());
                        bcc.guardarDetallesVTodoElementoActual(listaEnemdu);
                    }
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean guardarProvinciaSelect() {
        boolean banderaProvP7 = true;
        boolean banderaProvP8 = true;
        if (!cabeceraActual.getInfo1().equals("N")) {
            try {
                //Actualmente es P8
                if (provinciaSelectedP7 != null && listaEnemdu.get(86).getValor().equals("2")) { //index apunta a la pregunta
                    MetCatalogo provP7 = new MetCatalogo();
                    provP7 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(provinciaSelectedP7.getIdCatalogo());
                    listaEnemdu.get(87).setValor(provP7.getNombre()); //index apunta al selectonemenu de provincia                             
                    //System.out.println("pais: " + provP7.getNombre());
                    banderaProvP7 = true;
                } else if (provinciaSelectedP7 != null && !listaEnemdu.get(86).getValor().equals("2")) {
                    provinciaSelectedP7 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P8)Provincia seleccionada y opción distinta de 2");
                    banderaProvP7 = false;
                } else if (provinciaSelectedP7 == null && listaEnemdu.get(86).getValor().equals("2")) {
                    provinciaSelectedP7 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P8)Provincia No seleccionada y opción igual a 2");
                    banderaProvP7 = false;
                }

                if (provinciaSelectedP7 != null && cantonSelectedP7 != null && listaEnemdu.get(86).getValor().equals("2")) { //index apunta a la pregunta
                    MetCatalogo cantonP7 = new MetCatalogo();
                    cantonP7 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(cantonSelectedP7.getIdCatalogo());
                    listaEnemdu.get(88).setValor(cantonP7.getNombre()); //index apunta al selectonemenu de canton
                    banderaProvP7 = true;
                } else if (provinciaSelectedP7 != null && cantonSelectedP7 == null && listaEnemdu.get(86).getValor().equals("2")) {
                    bc.addErrorMessage("(S5-P8)Se requiere elegir un cantón");
                    banderaProvP7 = false;
                }

                if (provinciaSelectedP7 != null && cantonSelectedP7 != null && parroquiaSelectedP7 != null && listaEnemdu.get(86).getValor().equals("2")) { //index apunta a la pregunta
                    MetCatalogo pqP7 = new MetCatalogo();
                    pqP7 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(parroquiaSelectedP7.getIdCatalogo());
                    listaEnemdu.get(89).setValor(pqP7.getNombre()); //index apunta al selectonemenu de parroquia
                    listaEnemdu.get(90).setValor(pqP7.getValor()); //index apunta a campo de codificación
                    banderaProvP7 = true;
                } else if (provinciaSelectedP7 != null && cantonSelectedP7 != null && parroquiaSelectedP7 == null && listaEnemdu.get(86).getValor().equals("2")) {
                    //bc.addErrorMessage("(S5-P8)Se requiere elegir una parroquia");
                    banderaProvP7 = true;
                }

                //Guarda select de P9
                if (provinciaSelectedP8 != null && listaEnemdu.get(93).getValor().equals("2")) { //index apunta a la pregunta
                    MetCatalogo provP8 = new MetCatalogo();
                    provP8 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(provinciaSelectedP8.getIdCatalogo());
                    listaEnemdu.get(94).setValor(provP8.getNombre()); //index apunta al selectonemenu  
                    banderaProvP8 = true;
                } else if (provinciaSelectedP8 != null && !listaEnemdu.get(93).getValor().equals("2")) {
                    provinciaSelectedP8 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P9)Provincia seleccionada y opción distinta de 2");
                    banderaProvP8 = false;
                } else if (provinciaSelectedP8 == null && listaEnemdu.get(93).getValor().equals("2")) {
                    provinciaSelectedP8 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P9)Provincia No seleccionada y opción igual a 2");
                    banderaProvP8 = false;
                }

                if (provinciaSelectedP8 != null && cantonSelectedP8 != null && listaEnemdu.get(93).getValor().equals("2")) { //index apunta a la pregunta
                    MetCatalogo cantonP8 = new MetCatalogo();
                    cantonP8 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(cantonSelectedP8.getIdCatalogo());
                    listaEnemdu.get(95).setValor(cantonP8.getNombre()); //index apunta al selectonemenu      
                    banderaProvP8 = true;
                } else if (provinciaSelectedP8 != null && cantonSelectedP8 == null && listaEnemdu.get(93).getValor().equals("2")) {
                    cantonSelectedP8 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P9)Se requiere elegir un cantón");
                    banderaProvP8 = false;
                }

                if (provinciaSelectedP8 != null && cantonSelectedP8 != null && parroquiaSelectedP8 != null && listaEnemdu.get(93).getValor().equals("2")) { //index apunta a la pregunta
                    MetCatalogo pqP8 = new MetCatalogo();
                    pqP8 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(parroquiaSelectedP8.getIdCatalogo());
                    listaEnemdu.get(96).setValor(pqP8.getNombre()); //index apunta al selectonemenu  
                    listaEnemdu.get(97).setValor(pqP8.getValor());
                    banderaProvP8 = true;
                } else if (provinciaSelectedP8 != null && cantonSelectedP8 != null && parroquiaSelectedP8 == null && listaEnemdu.get(93).getValor().equals("2")) {
                    parroquiaSelectedP8 = new MetCatalogo();
                    //bc.addErrorMessage("(S5-P9)Se requiere elegir una parroquia");
                    banderaProvP8 = true;
                }

                if (banderaProvP7) {
                    //System.out.println("banderaProvP7= " + banderaProvP7);
                    limpiarBDPaisP7();
                }
                if (banderaProvP8) {
                    limpiarBDPaisP8();
                }
                limpiarBDTotalP7();
                limpiarBDTotalP8();

            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return banderaProvP7 && banderaProvP8;
        } else {
            return banderaProvP7 && banderaProvP8;
        }
    }

    public boolean guardarPaisSelect() {
        boolean banderaPaisP7 = true;
        boolean banderaPaisP8 = true;
        if (!cabeceraActual.getInfo1().equals("N")) {
            try {
                //Actualmente es P8
                if (paisSelectedP7 != null && listaEnemdu.get(86).getValor().equals("3")) { //index apunta a la pregunta
                    MetCatalogo paisP7 = new MetCatalogo();
                    paisP7 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(paisSelectedP7.getIdCatalogo());
                    listaEnemdu.get(91).setValor(paisP7.getNombre()); //index apunta al selectonemenu              
                    banderaPaisP7 = true;
                } else if (paisSelectedP7 != null && !listaEnemdu.get(86).getValor().equals("3")) {
                    paisSelectedP7 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P8)País seleccionado y opción distinta de 3");
                    banderaPaisP7 = false;
                } else if (paisSelectedP7 == null && listaEnemdu.get(86).getValor().equals("3")) {
                    paisSelectedP7 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P8)País No seleccionado y opción igual a 3");
                    banderaPaisP7 = false;
                }
                //P9
                if (paisSelectedP8 != null && listaEnemdu.get(93).getValor().equals("3")) { //index apunta a la pregunta
                    MetCatalogo paisP8 = new MetCatalogo();
                    paisP8 = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(paisSelectedP8.getIdCatalogo());
                    listaEnemdu.get(98).setValor(paisP8.getNombre()); //index apunta al selectonemenu              
                    banderaPaisP8 = true;
                } else if (paisSelectedP8 != null && !listaEnemdu.get(93).getValor().equals("3")) {
                    paisSelectedP8 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P9)País seleccionado y opción distinta de 3");
                    banderaPaisP8 = false;
                } else if (paisSelectedP8 == null && listaEnemdu.get(93).getValor().equals("3")) {
                    paisSelectedP8 = new MetCatalogo();
                    bc.addErrorMessage("(S5-P9)País No seleccionado y opción igual a 3");
                    banderaPaisP8 = false;
                }
                if (banderaPaisP7) {
                    limpiarBDProvP7();
                }
                if (banderaPaisP8) {
                    limpiarBDProvP8();
                }

            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return banderaPaisP7 && banderaPaisP8;

        } else {
            return banderaPaisP7 && banderaPaisP8;
        }
    }

    public void limpiarBDTotalP7() {
        if (listaEnemdu.get(86).getValor().equals("1") && provinciaSelectedP7 == null && cantonSelectedP7 == null && parroquiaSelectedP7 == null && paisSelectedP7 == null) {
            listaEnemdu.get(87).setValor(""); //provincia
            listaEnemdu.get(88).setValor(""); //canton
            listaEnemdu.get(89).setValor(""); //parroquia
            listaEnemdu.get(90).setValor(""); //codificacion

            listaEnemdu.get(91).setValor(""); //pais
            listaEnemdu.get(92).setValor(""); //año en que llego
        }
    }

    public void limpiarBDProvP7() {
        if (listaEnemdu.get(86).getValor().equals("3") && paisSelectedP7 != null) {
            listaEnemdu.get(87).setValor("");
            listaEnemdu.get(88).setValor("");
            listaEnemdu.get(89).setValor("");
            listaEnemdu.get(90).setValor("");
        }
    }

    public void limpiarBDTotalP8() {
        if (listaEnemdu.get(93).getValor().equals("1") && provinciaSelectedP8 == null && cantonSelectedP8 == null && parroquiaSelectedP8 == null && paisSelectedP8 == null) {
            listaEnemdu.get(94).setValor(""); //provincia
            listaEnemdu.get(95).setValor(""); //canton
            listaEnemdu.get(96).setValor(""); //parroquia
            listaEnemdu.get(97).setValor(""); //codificacion

            listaEnemdu.get(98).setValor(""); //pais
        }
    }

    public void limpiarBDProvP8() {
        if (listaEnemdu.get(93).getValor().equals("3") && paisSelectedP8 != null) {
            listaEnemdu.get(94).setValor("");
            listaEnemdu.get(95).setValor("");
            listaEnemdu.get(96).setValor("");
            listaEnemdu.get(97).setValor("");
        }
    }

    public void limpiarBDPaisP7() {
        if (listaEnemdu.get(86).getValor().equals("2") && provinciaSelectedP7 != null && cantonSelectedP7 != null && (parroquiaSelectedP7 != null || parroquiaSelectedP7 == null)) {
            listaEnemdu.get(91).setValor("");
            listaEnemdu.get(92).setValor("");
        }
    }

    public void limpiarBDPaisP8() {
        if (listaEnemdu.get(93).getValor().equals("2") && provinciaSelectedP8 != null && cantonSelectedP8 != null && (parroquiaSelectedP8 != null || parroquiaSelectedP8 == null)) {
            listaEnemdu.get(98).setValor("");
        }
    }

    public void calcularHijosNacidos() {
        try {
            int indexMP28S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P3001"));//Mujeres Nacidos
            int indexHP28S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P3002"));//Hombres Nacidas
            int indexTP28S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P3003"));//Total Nacidos                       
            String hijosNMujer = listaEnemdu.get(indexMP28S5).getValor();
            String hijosNHombre = listaEnemdu.get(indexHP28S5).getValor();
            if (hijosNMujer == null) {
                listaEnemdu.get(indexMP28S5).setValor("0");
            }
            if (hijosNHombre == null) {
                listaEnemdu.get(indexHP28S5).setValor("0");
            }
            if (!listaEnemdu.get(indexMP28S5).getValor().equals("") && !listaEnemdu.get(indexHP28S5).getValor().equals("")) {
                numMujeresNacidos = Integer.valueOf(listaEnemdu.get(indexMP28S5).getValor());
                numHombresNacidos = Integer.valueOf(listaEnemdu.get(indexHP28S5).getValor());
                totalNacidos = numMujeresNacidos + numHombresNacidos;

                listaEnemdu.get(indexMP28S5).setValor(String.valueOf(numMujeresNacidos));
                listaEnemdu.get(indexHP28S5).setValor(String.valueOf(numHombresNacidos));
                listaEnemdu.get(indexTP28S5).setValor(String.valueOf(totalNacidos));
            } else if (listaEnemdu.get(indexMP28S5).getValor().equals("") && !listaEnemdu.get(indexHP28S5).getValor().equals("")) {
                numMujeresNacidos = 0;
                numHombresNacidos = Integer.valueOf(listaEnemdu.get(indexHP28S5).getValor());
                totalNacidos = numMujeresNacidos + numHombresNacidos;

                listaEnemdu.get(indexMP28S5).setValor(String.valueOf(numMujeresNacidos));
                listaEnemdu.get(indexHP28S5).setValor(String.valueOf(numHombresNacidos));
                listaEnemdu.get(indexTP28S5).setValor(String.valueOf(totalNacidos));
            } else if (!listaEnemdu.get(indexMP28S5).getValor().equals("") && listaEnemdu.get(indexHP28S5).getValor().equals("")) {
                numMujeresNacidos = Integer.valueOf(listaEnemdu.get(indexMP28S5).getValor());
                numHombresNacidos = 0;
                totalNacidos = numMujeresNacidos + numHombresNacidos;

                listaEnemdu.get(indexMP28S5).setValor(String.valueOf(numMujeresNacidos));
                listaEnemdu.get(indexHP28S5).setValor(String.valueOf(numHombresNacidos));
                listaEnemdu.get(indexTP28S5).setValor(String.valueOf(totalNacidos));
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void calcularHijosVivos() {
        try {
            int indexMP29S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P3101"));//Mujeres vivas
            int indexHP29S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P3102"));//Hombres vivos
            int indexTP29S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P3103"));//Total vivos
            String hijosVMujeres = listaEnemdu.get(indexMP29S5).getValor();
            String hijosVHombres = listaEnemdu.get(indexHP29S5).getValor();
            if (hijosVMujeres == null) {
                listaEnemdu.get(indexMP29S5).setValor("0");
            }
            if (hijosVHombres == null) {
                listaEnemdu.get(indexHP29S5).setValor("0");
            }
            if (!listaEnemdu.get(indexMP29S5).getValor().equals("") && !listaEnemdu.get(indexHP29S5).getValor().equals("")) {
                numMujeresVivos = Integer.valueOf(listaEnemdu.get(indexMP29S5).getValor());
                numHombresVivos = Integer.valueOf(listaEnemdu.get(indexHP29S5).getValor());
                totalVivos = numMujeresVivos + numHombresVivos;

                listaEnemdu.get(indexMP29S5).setValor(String.valueOf(numMujeresVivos));
                listaEnemdu.get(indexHP29S5).setValor(String.valueOf(numHombresVivos));
                listaEnemdu.get(indexTP29S5).setValor(String.valueOf(totalVivos));
            } else if (listaEnemdu.get(indexMP29S5).getValor().equals("") && !listaEnemdu.get(indexHP29S5).getValor().equals("")) {
                numMujeresVivos = 0;
                numHombresVivos = Integer.valueOf(listaEnemdu.get(indexHP29S5).getValor());
                totalVivos = numMujeresVivos + numHombresVivos;

                listaEnemdu.get(indexMP29S5).setValor(String.valueOf(numMujeresVivos));
                listaEnemdu.get(indexHP29S5).setValor(String.valueOf(numHombresVivos));
                listaEnemdu.get(indexTP29S5).setValor(String.valueOf(totalVivos));
            } else if (!listaEnemdu.get(indexMP29S5).getValor().equals("") && listaEnemdu.get(indexHP29S5).getValor().equals("")) {
                numMujeresVivos = Integer.valueOf(listaEnemdu.get(indexMP29S5).getValor());
                numHombresVivos = 0;
                totalVivos = numMujeresVivos + numHombresVivos;

                listaEnemdu.get(indexMP29S5).setValor(String.valueOf(numMujeresVivos));
                listaEnemdu.get(indexHP29S5).setValor(String.valueOf(numHombresVivos));
                listaEnemdu.get(indexTP29S5).setValor(String.valueOf(totalVivos));
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void calcularPersonasHogar() {
        try {
            int indexHP11S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1201"));//Hombres
            int indexMP11S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1202"));//Mujeres
            int indexTP11S4 = Integer.parseInt(mapaVariables.get("CENSO_S4_H1203"));//Total
            String hombresHogar = listaEnemdu.get(indexHP11S4).getValor();
            String mujeresHogar = listaEnemdu.get(indexMP11S4).getValor();
            String totalHogar = listaEnemdu.get(indexTP11S4).getValor();
            if (hombresHogar == null) {
                numHombresHogar = 0;
                listaEnemdu.get(indexHP11S4).setValor("0");
            }
            if (mujeresHogar == null) {
                numMujeresHogar = 0;
                listaEnemdu.get(indexMP11S4).setValor("0");
            }
            if (totalHogar == null) {
                numTotalHogar = 0;
                listaEnemdu.get(indexTP11S4).setValor("0");
            }

            if (!listaEnemdu.get(indexHP11S4).getValor().equals("") && !listaEnemdu.get(indexMP11S4).getValor().equals("")) {
                numHombresHogar = Integer.valueOf(listaEnemdu.get(indexHP11S4).getValor());
                numMujeresHogar = Integer.valueOf(listaEnemdu.get(indexMP11S4).getValor());
                numTotalHogar = numHombresHogar + numMujeresHogar;

                listaEnemdu.get(indexHP11S4).setValor(String.valueOf(numHombresHogar));
                listaEnemdu.get(indexMP11S4).setValor(String.valueOf(numMujeresHogar));
                listaEnemdu.get(indexTP11S4).setValor(String.valueOf(numTotalHogar));

                if (numTotalHogar > 0) {
                    bc.addWarningMessage("Importante: Después de ingresar las cantidades de click en \"Crear Miembros de hogar\". "
                            + "Recordar que una vez creado los miembros, el botón mencionado no podrá ser utilizado.");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void validarFinS3P2VColectivaNoEfectivo() {
        try {
            int indexP2S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V0202"));

            if (cabeceraActual.getInfo1().equals("N")) {
                if (listaEnemdu.get(indexP2S3).getValor().equals("1")) {
                    bc.addErrorMessage("Para ingresar residentes habituales debe crear un Formulario Efectivo");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void validarFinS3P14NoEfectivo() {
        try {
            int indexP14S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V15"));
            int indexP15S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V16"));
            if (cabeceraActual.getInfo1().equals("N")) {
                if (listaEnemdu.get(indexP14S3).getValor().equals("1")) {
                    bc.addWarningMessage("Fin de formulario. Por favor guardar información.");
                } else if (listaEnemdu.get(indexP14S3).getValor().equals("2")) {
                    listaEnemdu.get(indexP15S3).setValor("");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void validarFinS3P15NoEfectivo() {
        try {
            int indexP15S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V16"));
            if (cabeceraActual.getInfo1().equals("N")) {
                if (listaEnemdu.get(indexP15S3).getValor().equals("")) {
                    listaEnemdu.get(indexP15S3).setValor("0");
                }
                if (Integer.parseInt(listaEnemdu.get(indexP15S3).getValor()) >= 2 && Integer.parseInt(listaEnemdu.get(indexP15S3).getValor()) <= 10) {
                    bc.addWarningMessage("Fin de formulario. Por favor guardar información.");
                }
//                else if (Integer.parseInt(listaEnemdu.get(indexP15S3).getValor()) < 2 || Integer.parseInt(listaEnemdu.get(indexP15S3).getValor()) > 10) {
//                    bc.addErrorMessage("No pueden ser menos de 2 hogares y más de 10");
//                }
            }
//            else if (!cabeceraActual.getInfo1().equals("N")) {
//                if (listaEnemdu.get(indexP15S3).getValor().equals("")) {
//                    listaEnemdu.get(indexP15S3).setValor("0");
//                }
//                if (Integer.parseInt(listaEnemdu.get(indexP15S3).getValor()) < 2 || Integer.parseInt(listaEnemdu.get(indexP15S3).getValor()) > 10) {
//                    bc.addErrorMessage("No pueden ser menos de 2 hogares y más de 10");
//                }
//            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarCampoLlenoNoEfectivo() {
        boolean validaCampo = true;
        try {
            if (cabeceraActual.getInfo1().equals("N")) {
                if (listaEnemdu.get(0).getValor().equals("")) {
                    //bc.addErrorMessage("Necesita llenar campos");
                    validaCampo = false;
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return validaCampo;
    }

    public void onIdioma() {
        try {
            tipoCatalogoIdiomas = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_IDIOMA");
            listaIdiomas = bc.getMetCatalogoServicioRemote().listarCatalogosPorTipoCatalogo(tipoCatalogoIdiomas.getIdTipoCatalogo());
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void contieneIdioma() { //Encuentra el idioma en base y trae al formulario si lo ubiere
        try {
            idiomaSelected = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoIdiomas.getIdTipoCatalogo(), listaEnemdu.get(100).getValor());
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean guardarIdiomaSelect() {
        boolean banderaidioma = true;
        if (!cabeceraActual.getInfo1().equals("N")) {
            try {
                int indexIdiomaP9S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P1001"));
                if (idiomaSelected != null && listaEnemdu.get(indexIdiomaP9S5).getValor().equals("1")) { //index apunta a la pregunta
                    MetCatalogo idioma = new MetCatalogo();
                    idioma = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(idiomaSelected.getIdCatalogo());
                    listaEnemdu.get(100).setValor(idioma.getNombre()); //index apunta al selectonemenu 
                    listaEnemdu.get(101).setValor(idioma.getValor());
                    banderaidioma = true;
                } else if (idiomaSelected == null && listaEnemdu.get(indexIdiomaP9S5).getValor().equals("1")) {
                    idiomaSelected = new MetCatalogo();
                    bc.addErrorMessage("(S5-P10)Idioma indígena, seleccione uno");
                    banderaidioma = false;
                } else if (idiomaSelected != null && !listaEnemdu.get(indexIdiomaP9S5).getValor().equals("1")) {
                    idiomaSelected = new MetCatalogo();
                    bc.addErrorMessage("(S5-P10)Idioma indígena seleccionado y opción distinta de 1");
                    banderaidioma = false;
                }

                if (banderaidioma == false) {
                    limpiarBDIdioma();
                }

            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return banderaidioma;

        } else {
            return banderaidioma;
        }
    }

    public void limpiarBDIdioma() {
        int indexIdiomaP9S5 = Integer.parseInt(mapaVariables.get("CENSO_S5_P1001"));
        if (!listaEnemdu.get(indexIdiomaP9S5).getValor().equals("1")) {
            listaEnemdu.get(100).setValor("");
            listaEnemdu.get(101).setValor("");
        }
    }

    public void onNacionalidad() {
        try {
            tipoCatalogoNacionalidades = bc.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("CAT_NACIONALIDAD");
            listaNacionalidades = bc.getMetCatalogoServicioRemote().listarCatalogosPorTipoCatalogo(tipoCatalogoNacionalidades.getIdTipoCatalogo());
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void contieneNacionalidad() { //Encuentra la nacionalidad indígena en base y trae al formulario si lo ubiere
        try {
            nacionalidadSelected = bc.getMetCatalogoServicioRemote().buscarPorIdTipoCatalogoYNombre(tipoCatalogoNacionalidades.getIdTipoCatalogo(), listaEnemdu.get(109).getValor());
        } catch (Exception ex) {
            Logger.getLogger(censoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean guardarNacionalidadSelect() {
        boolean banderaNacionalidad = true;
        if (!cabeceraActual.getInfo1().equals("N")) {
            try {
                if (nacionalidadSelected != null && listaEnemdu.get(106).getValor().equals("1")) { //apunta a index de preg 10
                    MetCatalogo nacionalidad = new MetCatalogo();
                    nacionalidad = bc.getMetCatalogoServicioRemote().buscarCatalogoXId(nacionalidadSelected.getIdCatalogo());
                    listaEnemdu.get(109).setValor(nacionalidad.getNombre()); //index apunta al selectonemenu 
                    listaEnemdu.get(110).setValor(nacionalidad.getValor()); //apunta a codificacion
                    banderaNacionalidad = true;
                } else if (nacionalidadSelected == null && listaEnemdu.get(106).getValor().equals("1")) { //apunta a index de preg 10
                    nacionalidadSelected = new MetCatalogo();
                    bc.addErrorMessage("(S5-P12)Nacionalidad indígena, seleccione una");
                    banderaNacionalidad = false;
                } else if (nacionalidadSelected != null && !listaEnemdu.get(106).getValor().equals("1")) {
                    nacionalidadSelected = new MetCatalogo();
                    bc.addErrorMessage("(S5-P12)Nacionalidad indígena seleccionada y pregunta 11 distinta de 1");
                    banderaNacionalidad = false;
                }

                if (banderaNacionalidad == false) {
                    limpiarBDNacionalidad();
                }

            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return banderaNacionalidad;

        } else {
            return banderaNacionalidad;
        }
    }

    public void limpiarBDNacionalidad() {
        if (!listaEnemdu.get(106).getValor().equals("1")) { //apunta a index de preg 10
            listaEnemdu.get(109).setValor(""); //apunta index a pregunta 11
            listaEnemdu.get(110).setValor(""); //apunta index a pregunta 11 - 1.2
        }
    }

    public void crearElementosEnFormulario() {
        try {
            if (numTotalHogar != null) {
                guardarElemento("G");
                String msg = validarCrearElementos(numTotalHogar);
                if (msg.equals("")) {
                    agregarDetVFormularioXElemento(cabeceraActual, numTotalHogar);//se agrega variables por elemento nuevo                
                    cabeceraActual.setNumDet(numTotalHogar);
                    cabeceraActual.setEstado("00");
                    cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                    bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);

                    crearEstadosNuevosElementosEnFormulario(cabeceraActual, numTotalHogar);//crea los estados por cada miembro nuevo
                    bc.addSuccessMessage("Elementos Creados correctamente");
                    listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());

                    cargarInicialesControlResumen();
                    cargarIniciales();

                } else if (!msg.equals("") && numTotalHogar == 1) {
                    bc.addWarningMessage("El miembro número 1 ya esta creado");
                } else if (!msg.equals("") && numTotalHogar == 0) {
                    numTotalHogar = 0;
                    bc.addErrorMessage("Cantidad no permitida");
                }
            } else {
                bc.addErrorMessage("Cantidad no permitida!");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void agregarDetVFormularioXElemento(CaptCabecera cab, Integer numNuevosElementos) {
        try {
            int nuevoTotal = numNuevosElementos - 1;
            for (int i = 1; i <= nuevoTotal; i++) {
                List<CaptVarV> listaVarsV = bc.getCaptDetalleVServicioRemote().listarVariablesVPorFormulario(cab.getCodCarCon().getCodFormulario().getIdFormulario());
                bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormularioPorElemento(cab.getCodCarCon().getCodFormulario().getTipo(), cab.getIdCab(), listaVarsV, i + 1);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearEstadosNuevosElementosEnFormulario(CaptCabecera cab, Integer numNuevoElemento) {
        try {
            int nuevoTotal = numNuevoElemento - 1;
            for (int i = 1; i <= nuevoTotal; i++) {
                CaptEstado estado = new CaptEstado();
                estado.setCodCab(cab);
                estado.setNumDet(i + 1);
                estado.setFechahoraEdicion(new Date());
                estado.setEstado("C");
                bc.getCaptEstadoServicioRemote().crearCaptEstado(estado);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private String validarCrearElementos(Integer numNuevosElementos) {
        String msg = "";
        try {
            if (numNuevosElementos > (cabeceraActual.getNumDet()) && numNuevosElementos > 1) {

            } else if (numNuevosElementos <= (cabeceraActual.getNumDet()) || numNuevosElementos <= 1) {
                msg = msg + "Cantidad no permitida";
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return msg;
    }

    private void listarOrdenRegistro() {
        listaOrdenRegistro = new ArrayList<>();
        listaOrdenRegistro.add("Representante del hogar");
        listaOrdenRegistro.add("Cónyuge o conviviente");
        listaOrdenRegistro.add("Hija/o de mayor a menor");
        listaOrdenRegistro.add("Hijastra o hijastro");
        listaOrdenRegistro.add("Nuera o yerno");
        listaOrdenRegistro.add("Nieta o nieto");
        listaOrdenRegistro.add("Madre o padre");
        listaOrdenRegistro.add("Suegra o suegro");
        listaOrdenRegistro.add("Otro pariente");
        listaOrdenRegistro.add("Otro no pariente");
        listaOrdenRegistro.add("Empleada/o doméstica/o");
        listaOrdenRegistro.add("Miembro de hogar colectivo");
        listaOrdenRegistro.add("Persona sin vivienda");
    }

    public boolean validaCodCuen() {
        boolean digitosCorrectos = true;
        try {
            int indexP11S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V11"));
            String codCue = listaEnemdu.get(indexP11S3).getValor();

            if (listaEnemdu.get(indexP11S3 - 1).getValor().equals("1") && !codCue.equals("") && listaEnemdu.get(indexP11S3 + 1).getValor().equals("")) {
                if (codCue.length() < 10) {
                    digitosCorrectos = false;
                } else if (codCue.length() == 10) {
                    digitosCorrectos = true;
                }
            } else if (listaEnemdu.get(indexP11S3 - 1).getValor().equals("1") && codCue.equals("") && !listaEnemdu.get(indexP11S3 + 1).getValor().equals("")) {
                digitosCorrectos = true;
            } else if (listaEnemdu.get(indexP11S3 - 1).getValor().equals("2") && codCue.equals("") && listaEnemdu.get(indexP11S3 + 1).getValor().equals("")) {
                digitosCorrectos = true;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return digitosCorrectos;
    }

    public void verificaCodCuen() {
        try {
            int indexP11S3 = Integer.parseInt(mapaVariables.get("CENSO_S3_V11"));
            String codCue = listaEnemdu.get(indexP11S3).getValor();

            if (!codCue.equals("")) {
                if (codCue.length() < 10) {
                    bc.addErrorMessage("En código CUEN debe ingresar 10 dígitos");
                } else if (codCue.length() == 10) {
                    bc.addSuccessMessage("Cantidad de dígitos correctos");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" GETTERSYSETTERS ">

    public BaseCapturaControlador getBcc() {
        return bcc;
    }

    public void setBcc(BaseCapturaControlador bcc) {
        this.bcc = bcc;
    }

    public UsuarioControlador getUc() {
        return uc;
    }

    public void setUc(UsuarioControlador uc) {
        this.uc = uc;
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

    public Integer getElementoActual() {
        return elementoActual;
    }

    public void setElementoActual(Integer elementoActual) {
        this.elementoActual = elementoActual;
    }

    public List<CaptEstado> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<CaptEstado> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<CaptObservacion> getListaObservaciones() {
        return listaObservaciones;
    }

    public void setListaObservaciones(List<CaptObservacion> listaObservaciones) {
        this.listaObservaciones = listaObservaciones;
    }

    public List<CaptVarV> getListaEnemdu() {
        return listaEnemdu;
    }

    public void setListaEnemdu(List<CaptVarV> listaEnemdu) {
        this.listaEnemdu = listaEnemdu;
    }

    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }

    public List<CaptElementoControl> getListaElementosControl() {
        return listaElementosControl;
    }

    public void setListaElementosControl(List<CaptElementoControl> listaElementosControl) {
        this.listaElementosControl = listaElementosControl;
    }

    public String getConglomerado() {
        return conglomerado;
    }

    public void setConglomerado(String conglomerado) {
        this.conglomerado = conglomerado;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getVivienda() {
        return vivienda;
    }

    public void setVivienda(String vivienda) {
        this.vivienda = vivienda;
    }

    public String getHogar() {
        return hogar;
    }

    public void setHogar(String hogar) {
        this.hogar = hogar;
    }

    public Integer getPosicionNuevoElemento() {
        return posicionNuevoElemento;
    }

    public void setPosicionNuevoElemento(Integer posicionNuevoElemento) {
        this.posicionNuevoElemento = posicionNuevoElemento;
    }

    public boolean isVerificarCheck() {
        return verificarCheck;
    }

    public void setVerificarCheck(boolean verificarCheck) {
        this.verificarCheck = verificarCheck;
    }

    public boolean isVarOculta() {
        return varOculta;
    }

    public void setVarOculta(boolean varOculta) {
        this.varOculta = varOculta;
    }

    public VistaControlador getVc() {
        return vc;
    }

    public void setVc(VistaControlador vc) {
        this.vc = vc;
    }

    public JSONArray getListaSaltos() {
        return listaSaltos;
    }

    public void setListaSaltos(JSONArray listaSaltos) {
        this.listaSaltos = listaSaltos;
    }

    public JSONArray getListaValidaciones() {
        return listaValidaciones;
    }

    public void setListaValidaciones(JSONArray listaValidaciones) {
        this.listaValidaciones = listaValidaciones;
    }

    public boolean isIntegridadSup() {
        return integridadSup;
    }

    public void setIntegridadSup(boolean integridadSup) {
        this.integridadSup = integridadSup;
    }

    public List<Map<String, String>> getListaInicial() {
        return listaInicial;
    }

    public void setListaInicial(List<Map<String, String>> listaInicial) {
        this.listaInicial = listaInicial;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<CaptObservacion> getLstObs() {
        return lstObs;
    }

    public void setLstObs(List<CaptObservacion> lstObs) {
        this.lstObs = lstObs;
    }

    public String getTipErr() {
        return tipErr;
    }

    public void setTipErr(String tipErr) {
        this.tipErr = tipErr;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public List<CaptVarV> getListaCenso() {
        return listaCenso;
    }

    public void setListaCenso(List<CaptVarV> listaCenso) {
        this.listaCenso = listaCenso;
    }

    public boolean isRegistro() {
        return registro;
    }

    public void setRegistro(boolean registro) {
        this.registro = registro;
    }

    // </editor-fold>      
    public CaptDetalleH getCaptDetalleH() {
        return captDetalleH;
    }

    public void setCaptDetalleH(CaptDetalleH captDetalleH) {
        this.captDetalleH = captDetalleH;
    }

    public boolean isEdicion() {
        return edicion;
    }

    public void setEdicion(boolean edicion) {
        this.edicion = edicion;
    }

    public List<CaptDetalleH> getListaDetalleH() {
        return listaDetalleH;
    }

    public void setListaDetalleH(List<CaptDetalleH> listaDetalleH) {
        this.listaDetalleH = listaDetalleH;
    }

    public boolean isRegistroEmigracion() {
        return registroEmigracion;
    }

    public void setRegistroEmigracion(boolean registroEmigracion) {
        this.registroEmigracion = registroEmigracion;
    }

    public boolean isRegistroMortalidad() {
        return registroMortalidad;
    }

    public void setRegistroMortalidad(boolean registroMortalidad) {
        this.registroMortalidad = registroMortalidad;
    }

    public String getEstadoProc() {
        return estadoProc;
    }

    public void setEstadoProc(String estadoProc) {
        this.estadoProc = estadoProc;
    }

    public MetCatalogo getPaisSelected() {
        return paisSelected;
    }

    public void setPaisSelected(MetCatalogo paisSelected) {
        this.paisSelected = paisSelected;
    }

    public List<MetCatalogo> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<MetCatalogo> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public MetCatalogo getPaisSelectedP7() {
        return paisSelectedP7;
    }

    public void setPaisSelectedP7(MetCatalogo paisSelectedP7) {
        this.paisSelectedP7 = paisSelectedP7;
    }

    public MetCatalogo getPaisSelectedP8() {
        return paisSelectedP8;
    }

    public void setPaisSelectedP8(MetCatalogo paisSelectedP8) {
        this.paisSelectedP8 = paisSelectedP8;
    }

    public List<MetCatalogo> getListaPaisesP7P8() {
        return listaPaisesP7P8;
    }

    public void setListaPaisesP7P8(List<MetCatalogo> listaPaisesP7P8) {
        this.listaPaisesP7P8 = listaPaisesP7P8;
    }

    public MetTipoCatalogo getTipoCatalogoPaises() {
        return tipoCatalogoPaises;
    }

    public void setTipoCatalogoPaises(MetTipoCatalogo tipoCatalogoPaises) {
        this.tipoCatalogoPaises = tipoCatalogoPaises;
    }

    public MetTipoCatalogo getTipoCatalogoProv() {
        return tipoCatalogoProv;
    }

    public void setTipoCatalogoProv(MetTipoCatalogo tipoCatalogoProv) {
        this.tipoCatalogoProv = tipoCatalogoProv;
    }

    public MetTipoCatalogo getTipoCatalogoCanton() {
        return tipoCatalogoCanton;
    }

    public void setTipoCatalogoCanton(MetTipoCatalogo tipoCatalogoCanton) {
        this.tipoCatalogoCanton = tipoCatalogoCanton;
    }

    public MetTipoCatalogo getTipoCatalogoParr() {
        return tipoCatalogoParr;
    }

    public void setTipoCatalogoParr(MetTipoCatalogo tipoCatalogoParr) {
        this.tipoCatalogoParr = tipoCatalogoParr;
    }

    public MetCatalogo getProvinciaSelectedP7() {
        return provinciaSelectedP7;
    }

    public void setProvinciaSelectedP7(MetCatalogo provinciaSelectedP7) {
        this.provinciaSelectedP7 = provinciaSelectedP7;
    }

    public MetCatalogo getCantonSelectedP7() {
        return cantonSelectedP7;
    }

    public void setCantonSelectedP7(MetCatalogo cantonSelectedP7) {
        this.cantonSelectedP7 = cantonSelectedP7;
    }

    public MetCatalogo getParroquiaSelectedP7() {
        return parroquiaSelectedP7;
    }

    public void setParroquiaSelectedP7(MetCatalogo parroquiaSelectedP7) {
        this.parroquiaSelectedP7 = parroquiaSelectedP7;
    }

    public MetCatalogo getProvinciaSelectedP8() {
        return provinciaSelectedP8;
    }

    public void setProvinciaSelectedP8(MetCatalogo provinciaSelectedP8) {
        this.provinciaSelectedP8 = provinciaSelectedP8;
    }

    public MetCatalogo getCantonSelectedP8() {
        return cantonSelectedP8;
    }

    public void setCantonSelectedP8(MetCatalogo cantonSelectedP8) {
        this.cantonSelectedP8 = cantonSelectedP8;
    }

    public MetCatalogo getParroquiaSelectedP8() {
        return parroquiaSelectedP8;
    }

    public void setParroquiaSelectedP8(MetCatalogo parroquiaSelectedP8) {
        this.parroquiaSelectedP8 = parroquiaSelectedP8;
    }

    public List<MetCatalogo> getListaProvinciasP7() {
        return listaProvinciasP7;
    }

    public void setListaProvinciasP7(List<MetCatalogo> listaProvinciasP7) {
        this.listaProvinciasP7 = listaProvinciasP7;
    }

    public List<MetCatalogo> getListaCantonesP7() {
        return listaCantonesP7;
    }

    public void setListaCantonesP7(List<MetCatalogo> listaCantonesP7) {
        this.listaCantonesP7 = listaCantonesP7;
    }

    public List<MetCatalogo> getListaParroquiasP7() {
        return listaParroquiasP7;
    }

    public void setListaParroquiasP7(List<MetCatalogo> listaParroquiasP7) {
        this.listaParroquiasP7 = listaParroquiasP7;
    }

    public List<MetCatalogo> getListaProvinciasP8() {
        return listaProvinciasP8;
    }

    public void setListaProvinciasP8(List<MetCatalogo> listaProvinciasP8) {
        this.listaProvinciasP8 = listaProvinciasP8;
    }

    public List<MetCatalogo> getListaCantonesP8() {
        return listaCantonesP8;
    }

    public void setListaCantonesP8(List<MetCatalogo> listaCantonesP8) {
        this.listaCantonesP8 = listaCantonesP8;
    }

    public List<MetCatalogo> getListaParroquiasP8() {
        return listaParroquiasP8;
    }

    public void setListaParroquiasP8(List<MetCatalogo> listaParroquiasP8) {
        this.listaParroquiasP8 = listaParroquiasP8;
    }

    public Integer getNumHombresNacidos() {
        return numHombresNacidos;
    }

    public void setNumHombresNacidos(Integer numHombresNacidos) {
        this.numHombresNacidos = numHombresNacidos;
    }

    public Integer getNumMujeresNacidos() {
        return numMujeresNacidos;
    }

    public void setNumMujeresNacidos(Integer numMujeresNacidos) {
        this.numMujeresNacidos = numMujeresNacidos;
    }

    public Integer getTotalNacidos() {
        return totalNacidos;
    }

    public void setTotalNacidos(Integer totalNacidos) {
        this.totalNacidos = totalNacidos;
    }

    public Integer getNumHombresVivos() {
        return numHombresVivos;
    }

    public void setNumHombresVivos(Integer numHombresVivos) {
        this.numHombresVivos = numHombresVivos;
    }

    public Integer getNumMujeresVivos() {
        return numMujeresVivos;
    }

    public void setNumMujeresVivos(Integer numMujeresVivos) {
        this.numMujeresVivos = numMujeresVivos;
    }

    public Integer getTotalVivos() {
        return totalVivos;
    }

    public void setTotalVivos(Integer totalVivos) {
        this.totalVivos = totalVivos;
    }

    public void setMiembros(String Miembros) {
        this.Miembros = Miembros;
    }

    public MetCatalogo getIdiomaSelected() {
        return idiomaSelected;
    }

    public void setIdiomaSelected(MetCatalogo idiomaSelected) {
        this.idiomaSelected = idiomaSelected;
    }

    public MetCatalogo getNacionalidadSelected() {
        return nacionalidadSelected;
    }

    public void setNacionalidadSelected(MetCatalogo nacionalidadSelected) {
        this.nacionalidadSelected = nacionalidadSelected;
    }

    public List<MetCatalogo> getListaIdiomas() {
        return listaIdiomas;
    }

    public void setListaIdiomas(List<MetCatalogo> listaIdiomas) {
        this.listaIdiomas = listaIdiomas;
    }

    public List<MetCatalogo> getListaNacionalidades() {
        return listaNacionalidades;
    }

    public void setListaNacionalidades(List<MetCatalogo> listaNacionalidades) {
        this.listaNacionalidades = listaNacionalidades;
    }

    public MetTipoCatalogo getTipoCatalogoIdiomas() {
        return tipoCatalogoIdiomas;
    }

    public void setTipoCatalogoIdiomas(MetTipoCatalogo tipoCatalogoIdiomas) {
        this.tipoCatalogoIdiomas = tipoCatalogoIdiomas;
    }

    public MetTipoCatalogo getTipoCatalogoNacionalidades() {
        return tipoCatalogoNacionalidades;
    }

    public void setTipoCatalogoNacionalidades(MetTipoCatalogo tipoCatalogoNacionalidades) {
        this.tipoCatalogoNacionalidades = tipoCatalogoNacionalidades;
    }

    public Integer getNumHombresHogar() {
        return numHombresHogar;
    }

    public void setNumHombresHogar(Integer numHombresHogar) {
        this.numHombresHogar = numHombresHogar;
    }

    public Integer getNumMujeresHogar() {
        return numMujeresHogar;
    }

    public void setNumMujeresHogar(Integer numMujeresHogar) {
        this.numMujeresHogar = numMujeresHogar;
    }

    public Integer getNumTotalHogar() {
        return numTotalHogar;
    }

    public void setNumTotalHogar(Integer numTotalHogar) {
        this.numTotalHogar = numTotalHogar;
    }

    public List<String> getListaOrdenRegistro() {
        return listaOrdenRegistro;
    }

    public boolean isHabilitaCrearMiembro() {
        return habilitaCrearMiembro;
    }

    public void setHabilitaCrearMiembro(boolean habilitaCrearMiembro) {
        this.habilitaCrearMiembro = habilitaCrearMiembro;
    }
}
