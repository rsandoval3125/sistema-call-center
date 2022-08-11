/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.formularios;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.captura.clases.CaptElementoControl;
import ec.gob.inec.captura.clases.CaptSeccionH;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleV;
import ec.gob.inec.captura.ejb.entidades.CaptEstado;
import ec.gob.inec.captura.ejb.entidades.CaptObservacion;
import ec.gob.inec.captura.jsf.controlador.modulos.BaseCapturaControlador;
import ec.gob.inec.captura.jsf.controlador.modulos.MetodosCaptura;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
public class EnemduControlador2107 implements Serializable {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(EnemduControlador2107.class.getName());

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
    private List<CaptVarV> listaEnemduVal;
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
    List<EnemduControlador2107.VariablesPrimerasPreguntas> primerasPreguntas;

    //Observaciones
    private String sec;
    private String pre;
    private String obs;
    private String tipErr;
    private List<CaptObservacion> lstObs;
    private String ver;

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
    public EnemduControlador2107() {
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
//                            RequestContext requestContext = RequestContext.getCurrentInstance();
//                            requestContext.execute("PF('dlgNewMiembro1').hide();");
//                            requestContext.update("frmCaptura:obsNew");
                            
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
                            
//                            RequestContext requestContext = RequestContext.getCurrentInstance();
//                            requestContext.execute("PF('dlgNewMiembro1').hide();");
//                            requestContext.update("frmCaptura:obsNew");
                            
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
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
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
//        RequestContext requestContext = RequestContext.getCurrentInstance();
//        requestContext.execute("PF('dlgNewMiembro1').show();");
//        requestContext.update("frmCaptura:obsNew");
        
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
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
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
            

        } catch (Exception ex) {
            System.out.println("Error");
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarObsVal() {
        try {
            lstObs = bc.getCaptObservacionServicioRemote().listarObservacionesPorCabYNumDetXTipoDif(cabeceraActual.getIdCab(), elementoActual, "0");
            ver = "99";
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.executeScript("PF('dlgVerObs').show();");
            
                                                               //                    PrimeFaces requestContext = PrimeFaces.current();
//                            requestContext.executeScript("PF('dlgNewMiembro1').hide();");
//                            requestContext.ajax().update("frmCaptura:obsNew");

        } catch (Exception ex) {
            System.out.println("Error");
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarElemento(String tipo) { //Verifica Guardar
        try {
            cabeceraActual.setFechahoraEdicion(new Date());
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
            bcc.guardarDetallesVTodoElementoActual(listaEnemdu);

            listaEstados = bcc.listarEstadosFormulario(cabeceraActual.getIdCab());
            //listaElementosControl = bc.getCaptDetalleVServicioRemote().listarElementosControlMultiF1PorCab(cabeceraActual.getIdCab());
            listaEnemdu = bcc.obtenerInformacionVDeElemento(cabeceraActual.getIdCab(), elementoActual, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
            cargarInicialesControlResumen();
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);

            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("enem2107_match", cabeceraActual.getIdCab());

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
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);
            bc.addSuccessMessage("Información Validada y Guardada.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean validaTieneCedula() { //Actalización Formulario Multi 2019,D.Aldas 13112019
        boolean campoLleno = false;
        try {
            try {

                int codCedF1 = Integer.parseInt(mapaVariables.get("ENEM0721_CED01A"));
                if (!listaEnemdu.get(codCedF1).getValor().equals("")) {
                    if (!listaEnemdu.get(codCedF1).getValor().equals("")) {

                        if (listaEnemdu.get(codCedF1).getValor().equals("1")) {

                            if (!listaEnemdu.get(codCedF1 + 1).getValor().equals("")) { //Posición ingreso cédula
                                String ci = listaEnemdu.get(codCedF1 + 1).getValor();// Posición ingreso cédula
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
                            campoLleno = true;
                        }
                    } else {
                        campoLleno = true;
                    }
                } else {
                    campoLleno = false;
                }
                System.out.println("Estado campo lleno:" + campoLleno);
                if (campoLleno == false) {
                    bc.addErrorMessage("Ha ingresado una cédula incorrecta o aún no ha llegado a la sección para todas las personas (vacío): finalice el formulario  ");
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
                int p03 = Integer.parseInt(mapaVariables.get("ENEM0721_P03"));System.out.println("äki"+ mapaVariables.get("ENEM0721_P41"));System.out.println("äki"+ mapaVariables.get("ENEM0721_P40"));
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
            varCel = bc.getMetVariableServicioRemote().buscarVariablePorIdentificador("ENEM0721_CED01B");
            List<CaptDetalleV> listaCedulas;
            listaCedulas = new ArrayList<>();
            listaCedulas = bc.getCaptDetalleVServicioRemote().listaValoresVariableXCab(cabeceraActual.getIdCab(), varCel.getIdVar());
            repetidas = repetidos(listaCedulas);
        } catch (Exception ex) {
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
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
                if (controlaNulos(valid.get("ENEM0721_P04")).equals("2")) {
                    conyugue = valid;

                }
                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 1 && ((Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03")))) < 12)) {
                    bc.addWarningMessage("Edad del Jefe menor de 12 años. ERROR 10");
                    campoLleno = false;
                }
                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 2 && ((Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03")))) < 12)) {
                    bc.addWarningMessage("Edad del Cónyuge menor de 12 años. ERROR 11");
                    campoLleno = false;
                }
                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 6 && ((Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03")))) < 25)) {
                    bc.addWarningMessage("Edad de Padres o suegros  menor 25 años. ERROR 14");
                    campoLleno = false;
                }
                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 8 && ((Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03")))) < 10)) {
                    bc.addWarningMessage("Edad de Empleado doméstico  menor  10 años. ERROR 15");
                    campoLleno = false;
                }

                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 3 && ((Integer.parseInt(controlaNulos(primerM.get("ENEM0721_P03"))) - Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03")))) < 13)) {
                    bc.addWarningMessage("Diferencia de edad entre el  Jefe y el Hijo  es menor de  13 años. ERROR 18");
                    campoLleno = false;
                }

                if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 5 && ((Integer.parseInt(controlaNulos(primerM.get("ENEM0721_P03"))) - Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03")))) < 25)) {
                    bc.addWarningMessage("Diferencia de edad entre el  Jefe y el Nieto/a  es menor de  25 años. ERROR 20");
                    campoLleno = false;
                }
            }
            if (!conyugue.isEmpty()) {
                for (Map<String, Object> valid : listaElementosControlEnemdu) {

                    if (Integer.parseInt(controlaNulos(valid.get("ENEM0721_P04"))) == 3 && ((Integer.parseInt(controlaNulos(conyugue.get("ENEM0721_P03"))) - Integer.parseInt(controlaNulos(valid.get("ENEM0721_P03")))) < 13)) {
                        bc.addWarningMessage("Diferencia de edad entre el  conyugue y el Hijo  es menor de  13 años. ERROR 19");
                        campoLleno = false;
                    }

                }
            }
        }
        return campoLleno;
    }

    public void validarVerificar(String estado) {
        // guardarElemento(estado); Modifica JG (no se actualiza:se mantiene lógica actual en guardados)
        try {
            validacionEnemduSoloAlertas();
            if (validaTieneCedula() && controlaIndiceConficanza() && validaCedulasFrm()) {
                if (validacionGenericaEnemdu(estado)) { //&& validaRamaSec()
                    // if (validacionesInternasMlt()) { //Verificar validaciones internas
                    validarGuardarElemento();
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
                    }
                    bc.addSuccessMessage("Información Validada y Verificada.");
                    /* } else {
                            bc.addErrorMessage(" Error, revisar que existen errores en el formulario ");
                        }*/
                } else {
                    bc.addErrorMessage(" Revisar todas las validaciones obligatorias para su rol");
                }
            } else {
                bc.addErrorMessage("!Verifique la información!");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean controlaIndiceConficanza() {//Actualiza D.A
        boolean secLlena = false;
        //int iccCodPer = Integer.parseInt(mapaVariables.get("ENEM0121_ICCCODPER"));
        int icc1 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC1"));
        int icc2 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC2"));
        int icc3 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC3"));
        int icc4 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC4A"));
        int icc5 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC5A"));
        int icc6 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC6A"));
        int icc7 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC7"));
        int icc8 = Integer.parseInt(mapaVariables.get("ENEM0721_ICC8A"));

        try {
            if (elementoActual == 1) {
                if ( //!listaEnemdu.get(iccCodPer).getValor().equals("")
                        //&& !listaEnemdu.get(iccCodPer).getValor().equals("")
                        !listaEnemdu.get(icc1).getValor().equals("")
                        && !listaEnemdu.get(icc2).getValor().equals("")
                        && !listaEnemdu.get(icc3).getValor().equals("")
                        && !listaEnemdu.get(icc4).getValor().equals("")
                        && !listaEnemdu.get(icc5).getValor().equals("")
                        && !listaEnemdu.get(icc6).getValor().equals("")
                        && !listaEnemdu.get(icc7).getValor().equals("")
                        && !listaEnemdu.get(icc8).getValor().equals("")) {
//                    if ((Integer.parseInt(listaEnemdu.get(iccCodPer).getValor()) != 99) && Integer.parseInt(listaEnemdu.get(iccCodPer).getValor()) > cabeceraActual.getNumDet()) {
//                        bc.addErrorMessage("Cod. Persona Sec.IC mayor al total de personas");
//                    } else {
//                        secLlena = true;
//                    }
                    secLlena = true; // Se aumenta para el mes de enero ya que no tiene la vaidacion Cod. Persona Sec.IC mayor al total de personas
                } else {
                    secLlena = false;
                    bc.addErrorMessage("Debe llenar todos los campos:Índice de Confianza");
                }
            } else {
                secLlena = true;
            }
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
//            RequestContext requestContext = RequestContext.getCurrentInstance();
//            requestContext.update("frmCaptura:f" + cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
//            requestContext.update("frmCaptura:tbCaptura");
//            requestContext.execute("fn_document_ready();");

                PrimeFaces requestContext = PrimeFaces.current();
                requestContext.ajax().update("frmCaptura:f" + cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
                requestContext.ajax().update("frmCaptura:tbCaptura");
                requestContext.executeScript("fn_document_ready();");

            
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

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
            } else {
                bc.addErrorMessage(msg);
            }
            posicionNuevoElemento = null;

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
            String url = "/sipe-captura-siac-war/paginas/captura/inicioCapturaEnemdu2107.xhtml";
            bc.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
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
            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("enem2107_s1_s4ced", cabeceraActual.getIdCab());
            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("enem2107_s5", cabeceraActual.getIdCab());
            bc.getCaptCabeceraServicioRemote().trasladarInfDetVHaciaBasePorIdCab("enem2107_s6", cabeceraActual.getIdCab());

            String msgValInts1s3 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("enem2107_s1_s4ced", cabeceraActual.getIdCab());
            String msgValInts5 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("enem2107_s5", cabeceraActual.getIdCab());
            String msgValInts6 = bc.getCaptCabeceraServicioRemote().obtenerValidacionIntegridadPorBaseIdCab("enem2107_s6", cabeceraActual.getIdCab());

            if (msgValInts6.length() > 0 || msgValInts5.length() > 0 || msgValInts1s3.length() > 0) {
                bc.addErrorMessage(msgValInts1s3 + " " + msgValInts5 + " " + msgValInts6);
                bc.addErrorMessage("Integridad No Validada.");
                cabeceraActual.setEstado3("2");
            } else {
                if (elementosValidados()) {
                    bc.addSuccessMessage("Integridad Validada.");

                    guardarTransaccionPorRolUsuario(estado);
                    cabeceraActual.setEstado3("1");
                } else {
                    bc.addErrorMessage("Verifique el formulario");
                }
            }
            bcc.guardarCabecera(cabeceraActual);
            fijarEstadoValidacionIntegridad(cabeceraActual);
            fijarEstadoSupIntegridad(cabeceraActual);
//            RequestContext requestContext = RequestContext.getCurrentInstance();
//            requestContext.update("frmCaptura:tbCaptura");
              
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

        cargarInicialesControlResumen();

        if (cabeceraActual.getEstado().equals("00")) {
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
                mapaControl.put("ENEM0721_COD_PER", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_COD_PER"))).getValor());
                //mapaControl.put("ENEM0121_ICCCODPER", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0121_ICCCODPER"))).getValor());
                //mapaControl.put("ENEM0121_VCODPER", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0121_VCODPER"))).getValor());

                mapaControl.put("ENEM0721_NOMBRE1", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_NOMBRE1"))).getValor());
                mapaControl.put("ENEM0721_NOMBRE2", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_NOMBRE2"))).getValor());
                mapaControl.put("ENEM0721_APELLIDO1", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_APELLIDO1"))).getValor());
                mapaControl.put("ENEM0721_APELLIDO2", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_APELLIDO2"))).getValor());
                mapaControl.put("ENEM0721_P02", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_P02"))).getValor());
                mapaControl.put("ENEM0721_P03", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_P03"))).getValor());
                mapaControl.put("ENEM0721_P04", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_P04"))).getValor());
                mapaControl.put("ENEM0721_P05A", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_P05A"))).getValor());
                mapaControl.put("ENEM0721_P05B", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_P05B"))).getValor());
                mapaControl.put("ENEM0721_P06", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_P06"))).getValor());
                mapaControl.put("ENEM0721_CED01B", listaAuxiliarResumenEnemdu.get(Integer.parseInt(mapaVariables.get("ENEM0721_CED01B"))).getValor());
                mapaControl.put("num", next.getNumDet());
                listaElementosControlEnemdu.add(mapaControl);

            } catch (Exception ex) {
                Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
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
                primerasPreg.setListaDetalles(bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(cabeceraActual.getIdCab(), next.getNumDet(), cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario(), "ENEM0721_NOMBRE1,ENEM0721_NOMBRE2,ENEM0721_APELLIDO1,ENEM0721_APELLIDO2,ENEM0721_P02,ENEM0721_P03,ENEM0721_P04,ENEM0721_P05A,ENEM0721_P05B"));

                // LS+bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(cabeceraActual.getIdCab(), next.getNumDet(), cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario(),"ENEM1020_NOMBRE1,ENEM1020_NOMBRE2,ENEM1020_APELLIDO1,ENEM1020_APELLIDO2,ENEM1020_P02,ENEM1020_P03,ENEM1020_P04,ENEM1020_P05A,ENEM1020_P05B").size());
                // for
                primerasPreg.trasladarDetAIniciales();

                primerasPreguntas.add(primerasPreg);
            } catch (Exception ex) {
                Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        listaInicial = (List<Map<String, String>>) primerasPreguntas.stream().map(x -> x.variablesEnMapa).collect(Collectors.toList());
        PrimeFaces requestContext = PrimeFaces.current();

        requestContext.ajax().update("frmCaptura:dlgMiembrosInicial");
        
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
            if (uc.usuarioTieneRol("ROL_VALID") && (usuario.equals("VC") || usuario.equals("VS") || usuario.equals("RC"))) {

                cabeceraActual.setEstado(usuario);
                cabeceraActual.setInfo7(uc.getUsuario() + " Edición: " + fechaComoCadena);
                bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);

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

        } catch (Exception ex) {
            Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
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
            listaDetalles.get(0).setValor(variablesEnMapa.get("ENEM0721_NOMBRE1"));
            listaDetalles.get(1).setValor(variablesEnMapa.get("ENEM0721_NOMBRE2"));
            listaDetalles.get(2).setValor(variablesEnMapa.get("ENEM0721_APELLIDO1"));
            listaDetalles.get(3).setValor(variablesEnMapa.get("ENEM0721_APELLIDO2"));
            listaDetalles.get(4).setValor(variablesEnMapa.get("ENEM0721_P02"));
            listaDetalles.get(5).setValor(variablesEnMapa.get("ENEM0721_P03"));
            listaDetalles.get(6).setValor(variablesEnMapa.get("ENEM0721_P04"));
            listaDetalles.get(7).setValor(variablesEnMapa.get("ENEM0721_P05A"));
            listaDetalles.get(8).setValor(variablesEnMapa.get("ENEM0721_P05B"));
        }

        public void trasladarDetAIniciales() {
            variablesEnMapa.put("ENEM0721_NOMBRE1", listaDetalles.get(0).getValor());
            variablesEnMapa.put("ENEM0721_NOMBRE2", listaDetalles.get(1).getValor());
            variablesEnMapa.put("ENEM0721_APELLIDO1", listaDetalles.get(2).getValor());
            variablesEnMapa.put("ENEM0721_APELLIDO2", listaDetalles.get(3).getValor());
            variablesEnMapa.put("ENEM0721_P02", listaDetalles.get(4).getValor());
            variablesEnMapa.put("ENEM0721_P03", listaDetalles.get(5).getValor());
            variablesEnMapa.put("ENEM0721_P04", listaDetalles.get(6).getValor());
            variablesEnMapa.put("ENEM0721_P05A", listaDetalles.get(7).getValor());
            variablesEnMapa.put("ENEM0721_P05B", listaDetalles.get(8).getValor());
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

        if (inicial.stream().filter(map -> (Objects.isNull(map.get("ENEM0721_NOMBRE1"))
                || map.get("ENEM0721_NOMBRE1").equals("")
                || Objects.isNull(map.get("ENEM0721_NOMBRE2"))
                || map.get("ENEM0721_NOMBRE2").equals("")
                || Objects.isNull(map.get("ENEM0721_APELLIDO1"))
                || map.get("ENEM0721_APELLIDO1").equals("")
                || Objects.isNull(map.get("ENEM0721_APELLIDO2"))
                || map.get("ENEM0721_APELLIDO2").equals("")
                || Objects.isNull(map.get("ENEM0721_P02"))
                || map.get("ENEM0721_P02").equals("")
                || Objects.isNull(map.get("ENEM0721_P03"))
                || map.get("ENEM0721_P03").equals("")
                || Objects.isNull(map.get("ENEM0721_P04"))
                || map.get("ENEM0721_P04").equals("")
                || Objects.isNull(map.get("ENEM0721_P05A"))
                || map.get("ENEM0721_P05A").equals("")))
                .count() == 0) {
            for (VariablesPrimerasPreguntas vari : primerasPreguntas) {

                try {

                    vari.setVariablesEnMapa(inicial.get(primerasPreguntas.indexOf(vari)));
                    vari.trasladarInicialesADet();

                    bc.getCaptDetalleVServicioRemote().actualizarDetallesVDeFormularioPorElemento(vari.getListaDetalles());
                    cabeceraActual.setEstado("G");
                    bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
                    listaEnemdu = bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElemento(cabeceraActual.getIdCab(), 1, cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
                    cargarInicialesControlResumen();
//                    RequestContext requestContext = RequestContext.getCurrentInstance();
//                    requestContext.update("frmCaptura:pnlBotones");
//                    requestContext.update("frmCaptura:lstEstados");
//                    requestContext.update("frmCaptura:pnlInfo");
//                    requestContext.update("frmCaptura:pnlElementosControl");
//                    requestContext.update("frmCaptura:f" + cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
//                    requestContext.update("frmCaptura:tbCaptura");
//                    requestContext.execute("PF('dlgMiembrosInicial').hide()");
//                    requestContext.execute("fn_document_ready();");

                        PrimeFaces requestContext = PrimeFaces.current();
                        requestContext.ajax().update("frmCaptura:pnlBotones");
                        requestContext.ajax().update("frmCaptura:lstEstados");
                        requestContext.ajax().update("frmCaptura:pnlInfo");
                        requestContext.ajax().update("frmCaptura:pnlElementosControl");
                        requestContext.ajax().update("frmCaptura:f" + cabeceraActual.getCodCarCon().getCodFormulario().getIdFormulario());
                        requestContext.ajax().update("frmCaptura:tbCaptura");
                        requestContext.executeScript("PF('dlgMiembrosInicial').hide()");
                        requestContext.executeScript("fn_document_ready();");
                    
//                    PrimeFaces requestContext = PrimeFaces.current();
//                            requestContext.executeScript("PF('dlgNewMiembro1').hide();");
//                            requestContext.ajax().update("frmCaptura:obsNew");
                    

                } catch (Exception ex) {
                    Logger.getLogger(EnemduControlador2107.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            bc.addErrorMessage("Ingrese todos los campos obligatorios porfavor");
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

// </editor-fold>
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

}
