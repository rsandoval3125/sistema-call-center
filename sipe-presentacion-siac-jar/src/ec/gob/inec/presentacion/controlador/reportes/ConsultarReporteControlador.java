/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.reportes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.reportes.ejb.entidades.RepoColumna;
import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import ec.gob.inec.reportes.ejb.entidades.RepoSubreporte;
import ec.gob.inec.presentacion.clases.TabViewTab;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import java.util.*;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import ec.gob.inec.presentacion.clases.reportes.excel.ExcelExporter;
import ec.gob.inec.presentacion.clases.reportes.json.convertJSON;
import ec.gob.inec.presentacion.clases.reportes.pdf.convertPDF;
import ec.gob.inec.presentacion.clases.reportes.xml.convertXML;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ec.gob.inec.presentacion.clases.reportes.spss.SPSSWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.reportes.ejb.entidades.RepoFiltro;
import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import java.lang.reflect.Type;
import java.util.Map.Entry;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.presentacion.clases.reportes.excel.ExcelExporterCarto;
import ec.gob.inec.presentacion.clases.reportes.excel.ExcelExporterGeneral;
import ec.gob.inec.presentacion.clases.reportes.excel.PDFExporter;
import ec.gob.inec.presentacion.clases.reportes.excel.PDFExporterCa06Anverso;
import ec.gob.inec.presentacion.clases.reportes.excel.PDFExporterCarto;
import ec.gob.inec.presentacion.clases.reportes.excel.PDFExporterGeneral;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
//import java.util.stream.Collectors;
//import static java.util.stream.Collectors.toMap;
import java.util.zip.ZipOutputStream;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.util.Constants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.engine.JasperCompileManager;
import javax.servlet.ServletContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author jaraujo
 */
@ManagedBean
@ViewScoped
public class ConsultarReporteControlador {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">    
    private static final Logger LOGGER = Logger.getLogger(ConsultarReporteControlador.class.getName());
    List<Object[]> resumenFiltro;
    Map<String, Object> filtros;
    private List<TabViewTab> tabViewInec;
    private RepoReporte idReporte;
    ExcelExporter exportExcel;
    ExcelExporterCarto exportExcelCartografia;
    String horaActual;
    //private List<RepoReporte> listReportes;
    public HttpSession session;
    private RepoReporte repoReporteNuevo;
    private RepoSubreporte repoSubreporteNuevo;
    private RepoProcedimiento repoProcedimientoNuevo;

    private Map<String, List<MetCatalogo>> mapFiltroReporte;
    private Map<String, MetCatalogo> mapSeleccionFiltroReporte;
    private Map<String, String> mapNombreFiltroReporte;
    //private RepoReporte reporteSeleccionado;
    //private boolean abrirPanelConsulta;
    //private boolean verPanelReporte;
    private Map<String, Map<String, Object>> mapFiltroReporteObj;
    private Map<String, Object> mapSeleccionFiltroReporteObj;
    private List<Object[]> lstCheckFor2;
    private List<Object[]> lstCheckFor2Anverso;

    private String clave;
    private int param1, param2;
    private Map<String, Object> mapSeleccionFiltroReporteRes;
    private Map<String, String> mapFiltrosObjRes, mapFiltrosObjResRes;
    private Map<String, Boolean> mapDisabledCheck, mapCheckParam;

    private ActionEvent buscarComponente;
    private Map<String, String> mapTipoFiltroReporte;
    private Map<String, Boolean> permisosBoton;
    private SegUsuario segUsuarioActual;
    private Map<String, String> mapIdTipoFiltros;
    public boolean showAlert1, showAlert2;
    private final String COMPILAR_DIR = "/paginas/jasper/";
    private String COMPILAR_NOMBRE_ARCHIVO;
    Connection conn = null;

    @ManagedProperty("#{baseControlador}")
    private BaseControlador baseControlador;

    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;

    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="constructor">
    //</editor-fold>   
    //<editor-fold defaultstate="collapsed" desc="get and set">    
    public List<TabViewTab> getTabViewInec() {
        return tabViewInec;
    }

    public void setTabViewInec(List<TabViewTab> tabViewInec) {
        this.tabViewInec = tabViewInec;
    }

    public RepoReporte getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(RepoReporte idReporte) {
        this.idReporte = idReporte;
    }

    /*public List<RepoReporte> getListReportes() {
        return listReportes;
    }

    public void setListReportes(List<RepoReporte> listReportes) {
        this.listReportes = listReportes;
    }*/
    public BaseControlador getBaseControlador() {
        return baseControlador;
    }

    public void setBaseControlador(BaseControlador baseControlador) {
        this.baseControlador = baseControlador;
    }

    public RepoReporte getRepoReporteNuevo() {
        return repoReporteNuevo;
    }

    public void setRepoReporteNuevo(RepoReporte repoReporteNuevo) {
        this.repoReporteNuevo = repoReporteNuevo;
    }

    public RepoSubreporte getRepoSubreporteNuevo() {
        return repoSubreporteNuevo;
    }

    public void setRepoSubreporteNuevo(RepoSubreporte repoSubreporteNuevo) {
        this.repoSubreporteNuevo = repoSubreporteNuevo;
    }

    public RepoProcedimiento getRepoProcedimientoNuevo() {
        return repoProcedimientoNuevo;
    }

    public void setRepoProcedimientoNuevo(RepoProcedimiento repoProcedimientoNuevo) {
        this.repoProcedimientoNuevo = repoProcedimientoNuevo;
    }

    public Map<String, List<MetCatalogo>> getMapFiltroReporte() {
        return mapFiltroReporte;
    }

    public void setMapFiltroReporte(Map<String, List<MetCatalogo>> mapFiltroReporte) {
        this.mapFiltroReporte = mapFiltroReporte;
    }

    public Map<String, MetCatalogo> getMapSeleccionFiltroReporte() {
        return mapSeleccionFiltroReporte;
    }

    public void setMapSeleccionFiltroReporte(Map<String, MetCatalogo> mapSeleccionFiltroReporte) {
        this.mapSeleccionFiltroReporte = mapSeleccionFiltroReporte;
    }

    public Map<String, String> getMapNombreFiltroReporte() {
        return mapNombreFiltroReporte;
    }

    public void setMapNombreFiltroReporte(Map<String, String> mapNombreFiltroReporte) {
        this.mapNombreFiltroReporte = mapNombreFiltroReporte;
    }

    /*public RepoReporte getReporteSeleccionado() {
        return reporteSeleccionado;
    }

    public void setReporteSeleccionado(RepoReporte reporteSeleccionado) {
        this.reporteSeleccionado = reporteSeleccionado;
    }

    public boolean isAbrirPanelConsulta() {
        return abrirPanelConsulta;
    }

    public void setAbrirPanelConsulta(boolean abrirPanelConsulta) {
        this.abrirPanelConsulta = abrirPanelConsulta;
    }

    public boolean isVerPanelReporte() {
        return verPanelReporte;
    }

    public void setVerPanelReporte(boolean verPanelReporte) {
        this.verPanelReporte = verPanelReporte;
    }*/
    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public Map<String, Map<String, Object>> getMapFiltroReporteObj() {
        return mapFiltroReporteObj;
    }

    public void setMapFiltroReporteObj(Map<String, Map<String, Object>> mapFiltroReporteObj) {
        this.mapFiltroReporteObj = mapFiltroReporteObj;
    }

    public Map<String, Object> getMapSeleccionFiltroReporteObj() {
        return mapSeleccionFiltroReporteObj;
    }

    public void setMapSeleccionFiltroReporteObj(Map<String, Object> mapSeleccionFiltroReporteObj) {
        this.mapSeleccionFiltroReporteObj = mapSeleccionFiltroReporteObj;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public List<Object[]> getLstCheckFor2() {
        return lstCheckFor2;
    }

    public void setLstCheckFor2(List<Object[]> lstCheckFor2) {
        this.lstCheckFor2 = lstCheckFor2;
    }

    public List<Object[]> getLstCheckFor2Anverso() {
        return lstCheckFor2Anverso;
    }

    public void setLstCheckFor2Anverso(List<Object[]> lstCheckFor2Anverso) {
        this.lstCheckFor2Anverso = lstCheckFor2Anverso;
    }

    public Map<String, Boolean> getMapDisabledCheck() {
        return mapDisabledCheck;
    }

    public void setMapDisabledCheck(Map<String, Boolean> mapDisabledCheck) {
        this.mapDisabledCheck = mapDisabledCheck;
    }

    public Map<String, Boolean> getMapCheckParam() {
        return mapCheckParam;
    }

    public void setMapCheckParam(Map<String, Boolean> mapCheckParam) {
        this.mapCheckParam = mapCheckParam;
    }

    public ActionEvent getBuscarComponente() {
        return buscarComponente;
    }

    public void setBuscarComponente(ActionEvent buscarComponente) {
        this.buscarComponente = buscarComponente;
    }

    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }

    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
    }

    public boolean isShowAlert1() {
        return showAlert1;
    }

    public void setShowAlert1(boolean showAlert1) {
        this.showAlert1 = showAlert1;
    }

    public boolean isShowAlert2() {
        return showAlert2;
    }

    public void setShowAlert2(boolean showAlert2) {
        this.showAlert2 = showAlert2;
    }

    //</editor-fold>  
    //<editor-fold defaultstate="collapsed" desc="métodos">    
    @PostConstruct
    public void inicializar() {
        try {
            permisosBoton = vistaControlador.getPermisos();
            idReporte = null;
            mapFiltroReporte = new LinkedHashMap<>();
            mapNombreFiltroReporte = new LinkedHashMap<>();
            mapFiltroReporteObj = new LinkedHashMap<>();
            mapSeleccionFiltroReporte = new LinkedHashMap<>();
            mapSeleccionFiltroReporteObj = new LinkedHashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            horaActual = formatter.format(java.util.Calendar.getInstance().getTime()).replace(" ", "_");

            //abrirPanelConsulta = false;
            //verPanelReporte = false;
            //listReportes = baseControlador.getRepoReporteServicioRemote().listarTodosActivos();
            mapSeleccionFiltroReporteRes = new LinkedHashMap<>();
            mapFiltrosObjRes = new LinkedHashMap<>();
            mapFiltrosObjResRes = new LinkedHashMap<>();
            mapDisabledCheck = new LinkedHashMap<>();
            mapCheckParam = new LinkedHashMap<>();
            mapIdTipoFiltros = new LinkedHashMap<>();
            inicializarConn();
            cargarReporte();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void inicializarConn() {
        try {
            //lstSistemas = baseControlador.getAdmBaseDatosServicioRemote().listarTodo();
            String parametroAES = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
            /*AdmBaseDatos bd = new AdmBaseDatos();
            String tipoCifrado = baseControlador.getAdmColumnaServicioRemote().consultarPorTablaYColumna(bd.getClass()., bd.getPassword().getClass().getName()).getCodTipoEncr().getValor();*/
            //String parametroAES = "InecDiradGiapeZ1";
            String tipoCifrado = "AES";
            baseControlador.getActualizaBDServicioRemote().pasarParametrosEncriptacion(tipoCifrado, parametroAES);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void obtenerReporte() {
        try {
            String mensaje = null;
            filtros = new LinkedHashMap<>();
            /*if (repoReporteNuevo != null) {
                if (repoReporteNuevo.getNombre() != null) {
                    idReporte = repoReporteNuevo;

                }
            }*/

            List<RepoSubreporte> listaSubReportes = baseControlador.getRepoSubreporteServicioRemote().listarSubreportes(idReporte);
            tabViewInec = new ArrayList<>();
            resumenFiltro = new ArrayList<>();
            for (RepoSubreporte subReporte : listaSubReportes) {
                RepoFiltro repoFiltro = baseControlador.getRepoFiltroServicioRemote().buscarPorCodigo(idReporte.getCodFiltro().getIdFiltro());

                TabViewTab tabV = new TabViewTab();
                tabV.setIdTab(subReporte.getIdSubr().toString());
                String nombreTab = subReporte.getNombre();
                tabV.setNombreTab(nombreTab);
                tabV.setAnchoTabla(subReporte.getAncho());
                tabV.setScroll(Integer.valueOf(String.valueOf(subReporte.getScroll())));
                tabV.setScrollWidth(Integer.valueOf(subReporte.getSwidth()));
                tabV.setPaginador(Integer.valueOf(String.valueOf(subReporte.getPaginador())));
                tabV.setFilaEstilo(Integer.valueOf(subReporte.getFilaEstilo().split("\\|")[0].split("=")[1]));
                tabV.setTextoEstilo(subReporte.getFilaEstilo().split("\\|")[1].split("=")[1]);
                tabV.setEstiloFila(subReporte.getFilaEstilo().split("\\|")[2].split("=")[1]);
                tabV.setExportar(Integer.valueOf(String.valueOf(subReporte.getExportar())));
                //tabV.setEditar(Integer.valueOf(subReporte.getCatDescripcion().split("\\|")[9].split("=")[1]));
                tabV.setSubtitulos(subReporte.getSubtitulo().split("=")[1].split(";"));

                String parametros = "";
                int tieneFooter = 0;
                int tieneGrupo = 0;
                int numeroResumen = 0;
                int tieneTabla = 0;
                int tieneGrafico = 0;
                int numPar = 0;

                String pars1 = subReporte.getFiltro().split("\\-")[0];
                String[] pars = pars1.split("\\|");
                for (String par : pars) {
                    //System.out.println("parm " + par);
                    numPar++;
                    if (numPar > 1) {
                        parametros = parametros + "|";
                    }
                    parametros = parametros + par;
                    if (!par.contains("=")) {
                        if (repoFiltro.getSql() != null) {
                            for (Map.Entry<String, Object> catalogoFiltro : mapSeleccionFiltroReporteObj.entrySet()) {
                                if (catalogoFiltro.getValue() == null) {
                                    if (catalogoFiltro.getKey().split("\\:")[1].equals("equipo")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("fasest")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("jorda")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("jorda1")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("jorda2")) {
                                        catalogoFiltro.setValue("0");
                                    } else {
                                        catalogoFiltro.setValue("¿");
                                    }
                                }
                                if (catalogoFiltro.getKey().equals(par)) {
                                    //System.out.println("parm2 " + parametros);
                                    for (Map.Entry<String, Map<String, Object>> catalogoFiltro1 : mapFiltroReporteObj.entrySet()) {
                                        if (catalogoFiltro1.getKey().equals(par)) {
                                            Map<String, Object> catalogoFiltro2 = catalogoFiltro1.getValue();
                                            if (catalogoFiltro.getValue().getClass().getSimpleName().equals("Date")) {
                                                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                String fecha = formatter.format(catalogoFiltro.getValue());
                                                parametros = parametros + "=" + "'" + fecha + "'";
                                            } else if (!catalogoFiltro2.isEmpty()) {
                                                for (Map.Entry<String, Object> catalogoFiltro3 : catalogoFiltro2.entrySet()) {
                                                    if (catalogoFiltro3.getValue() == null) {
                                                        catalogoFiltro3.setValue("¿");
                                                    }
                                                    if (catalogoFiltro.getValue().equals("¿") && catalogoFiltro3.getValue().equals("¿")) {
                                                        parametros = parametros + "=" + "'¿'";
                                                        break;
                                                    } else if (catalogoFiltro1.getKey().equals(catalogoFiltro.getKey())) {
                                                        parametros = parametros + "=" + "'" + catalogoFiltro.getValue() + "'";
                                                        break;
                                                    } else if (catalogoFiltro.getValue().equals("¿")) {
                                                        parametros = parametros + "=" + "'¿'";
                                                        break;
                                                    }
                                                }
                                            } else if (catalogoFiltro2.isEmpty()) {
                                                if (catalogoFiltro1.getKey().equals(catalogoFiltro.getKey())) {
                                                    parametros = parametros + "=" + "'" + catalogoFiltro.getValue() + "'";
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    for (Map.Entry<String, String> nombreFiltroAdd : mapNombreFiltroReporte.entrySet()) {
                                        boolean bfiltro = false;
                                        if (par.equals(nombreFiltroAdd.getKey())) {
                                            for (Object[] obj : resumenFiltro) {
                                                if (obj[0].equals(nombreFiltroAdd.getValue())) {
                                                    bfiltro = true;
                                                }
                                            }
                                            for (Map.Entry<String, Map<String, Object>> catalogoFiltro1 : mapFiltroReporteObj.entrySet()) {
                                                if (catalogoFiltro1.getKey().equals(par)) {
                                                    Map<String, Object> catalogoFiltro2 = catalogoFiltro1.getValue();
                                                    if (catalogoFiltro.getValue().getClass().getSimpleName().equals("Date")) {
                                                        if (bfiltro == false) {
                                                            Object[] obj = new Object[2];
                                                            obj[0] = nombreFiltroAdd.getValue();
                                                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                            String fecha = formatter.format(catalogoFiltro.getValue());
                                                            obj[1] = fecha;
                                                            resumenFiltro.add(obj);
                                                            filtros.put(nombreFiltroAdd.getValue(), obj[1]);
                                                        }
                                                    } else {
                                                        for (Map.Entry<String, Object> catalogoFiltro3 : catalogoFiltro2.entrySet()) {
                                                            if (bfiltro == false) {
                                                                Object[] obj = new Object[2];
                                                                obj[0] = nombreFiltroAdd.getValue();
                                                                if (catalogoFiltro.getValue().equals(catalogoFiltro3.getKey())) {
                                                                    if (catalogoFiltro.getKey().split("\\:")[1].equals("zn")
                                                                            || catalogoFiltro.getKey().split("\\:")[1].equals("stor")
                                                                            || catalogoFiltro.getKey().split("\\:")[1].equals("mz")) {
                                                                        obj[1] = catalogoFiltro3.getValue();
                                                                        resumenFiltro.add(obj);
                                                                        filtros.put(nombreFiltroAdd.getValue(), obj[1]);
                                                                        break;
                                                                    } else {
                                                                        //obj[1] = catalogoFiltro3.getKey() + " - " + catalogoFiltro3.getValue();
                                                                        obj[1] = catalogoFiltro3.getValue();
                                                                        resumenFiltro.add(obj);
                                                                        filtros.put(nombreFiltroAdd.getValue(), obj[1]);
                                                                        break;
                                                                    }
                                                                } else if (catalogoFiltro.getValue().equals("¿")) {
                                                                    obj[1] = "¿ - ¿";
                                                                    resumenFiltro.add(obj);
                                                                    filtros.put(nombreFiltroAdd.getValue(), obj[1]);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        terminarConsulta:
                        if (repoFiltro.getCatalogo() != null) {
                            for (Map.Entry<String, MetCatalogo> catalogoFiltro : mapSeleccionFiltroReporte.entrySet()) {
                                if (catalogoFiltro.getKey().equals(":parr") && catalogoFiltro.getValue() == null) {
                                    if (usuarioTienePermiso("PAG_FIL_PARROQ")) {
                                        mensaje = "Responsable Parroquial debe seleccionar los filtros hasta el nivel de parroquia";
                                        break terminarConsulta;
                                    }
                                } else if (catalogoFiltro.getKey().equals(":sede") && catalogoFiltro.getValue() == null) {
                                    if (usuarioTienePermiso("PAG_FIL_SEDE_OP")) {
                                        mensaje = "Responsable Sede Operativa debe seleccionar los filtros hasta el nivel de Sede Operativa";
                                        break terminarConsulta;
                                    }
                                } else if (catalogoFiltro.getKey().equals(":canton") && catalogoFiltro.getValue() == null) {
                                    if (usuarioTienePermiso("PAG_FIL_CANT")) {
                                        mensaje = "Responsable Cantonal debe seleccionar los filtros hasta el nivel de cantón";
                                        break terminarConsulta;
                                    }
                                } else if (catalogoFiltro.getKey().equals(":prov") && catalogoFiltro.getValue() == null) {
                                    if (usuarioTienePermiso("PAG_FIL_PROV")) {
                                        mensaje = "Responsable Provincial debe seleccionar los filtros hasta el nivel de provincia";
                                        break terminarConsulta;
                                    }
                                } else if (catalogoFiltro.getKey().equals(":zonal") && catalogoFiltro.getValue() == null) {
                                    if (usuarioTienePermiso("PAG_VER") && usuarioTienePermiso("PAG_LST_TODO")) {
                                    } else {
                                        mensaje = "Seleccione la zonal a consultar";
                                        break terminarConsulta;
                                    }
                                }
                            }

                            for (Map.Entry<String, MetCatalogo> catalogoFiltro : mapSeleccionFiltroReporte.entrySet()) {
                                if (catalogoFiltro.getValue() == null) {
                                    MetCatalogo metCatalogo = new MetCatalogo();
                                    metCatalogo.setNombre("¿");
                                    metCatalogo.setValor("¿");
                                    catalogoFiltro.setValue(metCatalogo);
                                }

                                String aliasFiltro = catalogoFiltro.getKey().split("\\:")[1];

                                if (catalogoFiltro.getKey().equals(par)) {
                                    //System.out.println("parm2 " + parametros);                                    
                                    if (!mapTipoFiltroReporte.isEmpty()) {
                                        for (Map.Entry<String, String> tipoFiltroAdd : mapTipoFiltroReporte.entrySet()) {
                                            if (catalogoFiltro.getValue().getIdCatalogo() != null) {
                                                MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(catalogoFiltro.getValue().getIdCatalogo());
                                                if (tipoFiltroAdd.getKey().equals(par)) {
                                                    switch (tipoFiltroAdd.getValue()) {
                                                        case "idCatalogo":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getIdCatalogo() + "'";
                                                            break;
                                                        case "codTipoCatalogo":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getCodTipoCatalogo() + "'";
                                                            break;
                                                        case "nombre":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getNombre() + "'";
                                                            break;
                                                        case "alias":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getAlias() + "'";
                                                            break;
                                                        case "valor":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor() + "'";
                                                            break;
                                                        default:
                                                            break;
                                                    }

                                                    if (tipoFiltroAdd.getValue().equals("ninguno")) {
                                                        if (metCatalogo.getNivel() == 1 && aliasFiltro.equals("prov")) {
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor() + "'";
                                                        } else if (metCatalogo.getNivel() == 2 && aliasFiltro.equals("canton")) {
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor().substring(2) + "'";
                                                        } else if (metCatalogo.getNivel() == 3 && aliasFiltro.equals("parr")) {
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor().substring(4) + "'";
                                                        } else if (aliasFiltro.equals("zonal")) {
                                                            //MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(catalogoFiltro.getValue().getIdCatalogo());
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor().substring(1) + "'";
                                                        } else {
                                                            parametros = parametros + "=" + catalogoFiltro.getValue().getIdCatalogo();
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (tipoFiltroAdd.getKey().equals(par)) {
                                                    switch (tipoFiltroAdd.getValue()) {
                                                        case "idCatalogo":
                                                            parametros = parametros + "=" + "'" + 0 + "'";
                                                            break;
                                                        case "codTipoCatalogo":
                                                            parametros = parametros + "=" + "'" + 0 + "'";
                                                            break;
                                                        case "nombre":
                                                            parametros = parametros + "=" + "'¿'";
                                                            break;
                                                        case "alias":
                                                            parametros = parametros + "=" + "'¿'";
                                                            break;
                                                        case "valor":
                                                            parametros = parametros + "=" + "'¿'";
                                                            break;
                                                        case "ninguno":
                                                            if (aliasFiltro.equals("prov") || aliasFiltro.equals("canton") || aliasFiltro.equals("parr")
                                                                    || aliasFiltro.equals("zonal") || aliasFiltro.equals("sede")) {
                                                                parametros = parametros + "=" + "'¿'";
                                                            } else {
                                                                parametros = parametros + "=" + "'¿'";
                                                            }
                                                            break;
                                                        default:
                                                            parametros = parametros + "=" + "'¿'";
                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    for (Map.Entry<String, String> nombreFiltroAdd : mapNombreFiltroReporte.entrySet()) {
                                        boolean bfiltro = false;
                                        if (par.equals(nombreFiltroAdd.getKey())) {
                                            for (Object[] obj : resumenFiltro) {
                                                if (obj[0].equals(nombreFiltroAdd.getValue())) {
                                                    bfiltro = true;
                                                }
                                            }
                                            if (bfiltro == false) {
                                                if (catalogoFiltro.getValue().getNombre() == null) {
                                                    MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(catalogoFiltro.getValue().getIdCatalogo());
                                                    Object[] obj = new Object[2];
                                                    obj[0] = nombreFiltroAdd.getValue();
                                                    if (catalogoFiltro.getKey().split("\\:")[1].equals("prov")
                                                            || catalogoFiltro.getKey().split("\\:")[1].equals("canton")
                                                            || catalogoFiltro.getKey().split("\\:")[1].equals("parr")) {
                                                        if (metCatalogo.getNivel() == 1) {
                                                            obj[1] = metCatalogo.getValor();
                                                        } else if (metCatalogo.getNivel() == 2) {
                                                            obj[1] = metCatalogo.getValor().substring(2);
                                                        } else if (metCatalogo.getNivel() == 3) {
                                                            obj[1] = metCatalogo.getValor().substring(4);
                                                        }
                                                    } else {
                                                        String codPar = "¿";
                                                        if (metCatalogo.getIdCatalogo() == null) {
                                                            obj[1] = codPar + " - " + metCatalogo.getNombre();
                                                        } else {
                                                            //obj[1] = metCatalogo.getIdCatalogo() + " - " + metCatalogo.getNombre();
                                                            obj[1] = metCatalogo.getNombre();
                                                        }
                                                    }
                                                    resumenFiltro.add(obj);
                                                    filtros.put(nombreFiltroAdd.getValue(), obj[1]);
                                                    break;
                                                } else {
                                                    Object[] obj = new Object[2];
                                                    obj[0] = nombreFiltroAdd.getValue();
                                                    if (catalogoFiltro.getKey().split("\\:")[1].equals("prov")
                                                            || catalogoFiltro.getKey().split("\\:")[1].equals("canton")
                                                            || catalogoFiltro.getKey().split("\\:")[1].equals("parr")) {
                                                        obj[1] = catalogoFiltro.getValue().getValor();
                                                    } else {
                                                        String codPar = "¿";
                                                        if (catalogoFiltro.getValue().getIdCatalogo() == null) {
                                                            obj[1] = codPar + " - " + catalogoFiltro.getValue().getNombre();
                                                        } else {
                                                            //obj[1] = catalogoFiltro.getValue().getIdCatalogo() + " - " + catalogoFiltro.getValue().getNombre();
                                                            obj[1] = catalogoFiltro.getValue().getNombre();
                                                        }
                                                    }
                                                    resumenFiltro.add(obj);
                                                    filtros.put(nombreFiltroAdd.getValue(), obj[1]);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println("q " + subReporte.getCodProc().getNombre());
                System.out.println("parametros: " + parametros);
                Object[] recupData = baseControlador.getRepoSubreporteServicioRemote().ejecutarFuncion(subReporte.getCodProc().getNombre(), parametros);
                String paramDsga = "";
                if (parametros.contains(":zonal")) {
                    String[] param = parametros.split("\\|");
                    for (String string : param) {
                        if (!string.contains("zonal")) {
                            paramDsga = paramDsga + string;
                        }
                    }
                }
                String valueC = paramDsga.replaceAll("[^0-9]+", ";").replace(";", "");
                lstCheckFor2 = new ArrayList();
                /*if (!valueC.isEmpty()) {
                    if (valueC.length() >= 9) {
                        if (valueC.length() == 15 || (valueC.substring(6, 9).equals("999") && valueC.length() == 12 && !idReporte.getNombre().contains("Anverso"))) {
                            CaptCabecera captCabecera = null;
                            if (idReporte.getNombre().contains("2020")) {
                                captCabecera = baseControlador.getCaptCabeceraServicioRemote().buscarCabeceraPorClaveInfo4(valueC, "info4");
                            } else {//revisar siguiente linea ca04 no devuelve reporte al seleccionar todos los filtros pese a que si hay información
                                captCabecera = baseControlador.getCaptCabeceraServicioRemote().buscarCabeceraPorClaveInfo4(valueC, "clave");
                            }

                            if (captCabecera != null) {
                                if (captCabecera.getEstado3() != null) {
                                    Object[] for2 = new Object[2];
                                    for2[0] = "1. ALTA";
                                    for2[1] = captCabecera.getEstado3().equals("1");
                                    lstCheckFor2.add(0, for2);
                                    for2 = new Object[2];
                                    for2[0] = "2. MEDIA";
                                    for2[1] = captCabecera.getEstado3().equals("2");
                                    lstCheckFor2.add(1, for2);
                                    for2 = new Object[2];
                                    for2[0] = "3. BAJA";
                                    for2[1] = captCabecera.getEstado3().equals("3");
                                    lstCheckFor2.add(2, for2);
                                    for2 = new Object[2];
                                    for2[0] = "1. TRADICIONAL";
                                    for2[1] = captCabecera.getEstado4().equals("1");
                                    lstCheckFor2.add(3, for2);
                                    for2 = new Object[2];
                                    for2[0] = "2. LANCHA";
                                    for2[1] = captCabecera.getEstado4().equals("2");
                                    lstCheckFor2.add(4, for2);
                                    for2 = new Object[2];
                                    for2[0] = "3. GUÍA";
                                    for2[1] = captCabecera.getEstado4().equals("3");
                                    lstCheckFor2.add(5, for2);
                                    for2 = new Object[2];
                                    for2[0] = "4. AVIONETA";
                                    for2[1] = captCabecera.getEstado4().equals("4");
                                    lstCheckFor2.add(6, for2);
                                    for2 = new Object[2];
                                    for2[0] = "5. A PIE";
                                    for2[1] = captCabecera.getEstado4().equals("5");
                                    lstCheckFor2.add(7, for2);
                                    for2 = new Object[2];
                                    for2[0] = "6. ACÉMILA";
                                    for2[1] = captCabecera.getEstado4().equals("6");
                                    lstCheckFor2.add(8, for2);
                                    for2 = new Object[2];
                                    for2[0] = "7. OTRO TRANSPORTE";
                                    for2[1] = captCabecera.getEstado4().equals("7");
                                    lstCheckFor2.add(9, for2);
                                }
                            }
                        }
                    }
                }*/

                RepoProcedimiento procedimiento = baseControlador.getRepoProcedimientoServicioRemote().buscarProcedimiento(subReporte.getCodProc().getIdProc());
                if (procedimiento.getVariables() != null) {
                    if (!procedimiento.getVariables().isEmpty()) {
                        String param[] = parametros.split("\\|");
                        List<Object> lstParam = new ArrayList<>();
                        lstParam.addAll(Arrays.asList(param));

                        //Object[] ejecutaFuncion = baseControlador.getRepoSubreporteServicioRemote().ejecutarFuncion(subReporte.getCodProc().getNombre(), parametros);
                        System.out.println("lstParam: " + lstParam);
                        baseControlador.getRepoProcedimientoServicioRemote().existeEjecutarFuncion(procedimiento.getVariables(), lstParam);
                    }
                }
                String conexionProcedimiento = recupData[0].toString();
                //System.err.println(">>>proConexion1 " + conexionProcedimiento);
                String select = recupData[1].toString();
                //System.err.println(">>>sentencia " + select);
                if (select.contains("'+'")) {
                    select = select.replace("'+'", "");
                } else if (select.contains("'||'")) {
                    select = select.replace("'||'", "");
                }

                select = reemplazarParamConsultaCarto(select);
                //System.err.println(">>>sentencia4 " + select);

                String tablatemporal = recupData[2].toString();
                //System.err.println(">>>sqlSelect " + tablatemporal);
                String borrar = recupData[3].toString();
                //System.err.println(">>>queryBorrar: " + borrar);

                //ejecutamos el sql en la base correspondiente creando una tabla y poniendo los datos en dicha tabla, se realiza un select into
                //int ejecutarSql = baseControlador.getActualizaBDServicioRemote().ejecutaSql(conexionProcedimiento, select);                
                //obtenemos la información extraywndola de la tabla temporal creada.
                //List<Object[]> dataList2 = baseControlador.getActualizaBDServicioRemote().recuperaInformacionListValor2(conexionProcedimiento, tablatemporal, 1, 0);
                //Reemplaza a las dos lineas anteriores
                List<Object[]> dataList2 = baseControlador.getActualizaBDServicioRemote().creaRecuperaInfTablaTmp(conexionProcedimiento, select, tablatemporal, borrar, 1, 0);

                /*if (!valueC.isEmpty()) {
                    if (valueC.length() >= 9) {
                        if ((valueC.substring(6, 9).equals("999") && valueC.length() == 12 && idReporte.getNombre().contains("Anverso"))) {
                if (dataList2 != null) {
                                if (!dataList2.isEmpty()) {
                                    for (int i = 0; i < dataList2.size(); i++) {
                                        if (dataList2.get(i).length > 9) {
                                            lstCheckFor2Anverso = new ArrayList();
                                            Object[] for2 = new Object[13];
                                            for (int j = 0; j < 13; j++) {
                                                for2[j] = dataList2.get(i)[11 + j];
                                            }
//                                            for2[0] = dataList2.get(i)[10];
//                                            for2[1] = dataList2.get(i)[11];
//                                            for2[2] = dataList2.get(i)[12];
//                                            for2[3] = dataList2.get(i)[13];
//                                            for2[4] = dataList2.get(i)[14];
//                                            for2[5] = dataList2.get(i)[15];
//                                            for2[6] = dataList2.get(i)[16];
//                                            for2[7] = dataList2.get(i)[17];
//                                            for2[8] = dataList2.get(i)[18];
//                                            for2[9] = dataList2.get(i)[19];
//                                            for2[10] = dataList2.get(i)[20];
//                                            for2[11] = dataList2.get(i)[21];
//                                            for2[12] = dataList2.get(i)[22];
                                            lstCheckFor2Anverso.add(0, for2);
                                            break;
                                        } else if (dataList2.get(i).length == 3) {
                                            Object[] for2 = new Object[1];
                                            for2[0] = dataList2.get(i)[0];
                                            lstCheckFor2Anverso.add(i + 1, for2);
                                            //break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }*/
                if (dataList2 != null) {
                    if (dataList2.size() > 0) {
                        System.err.println("datALIST +++" + dataList2.size());
                        tabV.setFilas(dataList2);

                        List<Object[]> listaGrupoColumnas = new ArrayList<>();
                        List<Object[]> listaColumnas = new ArrayList<>();
                        List<Object[]> listaResumen = new ArrayList<>();
                        List<Object[]> listaFooter = new ArrayList<>();
                        List<Object[]> listaAcciones = new ArrayList<>();
                        LineChartModel lineChartModel = new LineChartModel();

                        List<RepoColumna> columnasTabla = baseControlador.getRepoColumnaServicioRemote().listarColumnasPorSubReporte(subReporte);
                        if (columnasTabla.size() > 0) {
                            for (RepoColumna columnaTabla : columnasTabla) {
                                if (columnaTabla.getComponente().equals("resumen")) {
                                    tieneTabla = 1;
                                    numeroResumen = numeroResumen + 4;
                                    listaResumen.add(resumenReporte(columnaTabla, dataList2));
                                }
                                if (columnaTabla.getComponente().equals("header")) {
                                    tieneTabla = 1;

                                    listaColumnas.add(columnaReporte(columnaTabla, 0));
                                }
                                if (columnaTabla.getComponente().equals("footer")) {
                                    tieneTabla = 1;
                                    tieneFooter = 1;
                                    listaFooter.add(footerReporte(columnaTabla, dataList2));
                                }
                                if (columnaTabla.getComponente().equals("Grupo")) {
                                    tieneTabla = 1;
                                    tieneGrupo = 1;
                                    listaGrupoColumnas.add(columnaGrupoReporte(columnaTabla));
                                }
                                /*if (columnaTabla.getColTipo().equals("Serie")) {
                            tieneGrafico = 1;
                            lineChartModel.addSeries(graficoLinear(columnaTabla, dataList2));
                            }*/
                                if (columnaTabla.getComponente().equals("accion")) {
                                    listaAcciones.add(accionReporte(columnaTabla, dataList2));
                                }
                            }
                        } else {
                            numPar = 0;
                            if (dataList2.size() > 0) {
                                for (Object listaObjeto : dataList2.get(0)) {
                                    numPar++;
                                    Object[] objetos = new Object[7];
                                    objetos[0] = numPar;
                                    objetos[1] = "centeredColumn";
                                    objetos[2] = 30;
                                    objetos[3] = numPar;
                                    objetos[4] = numPar % 2 == 0 ? 1 : 0;
                                    objetos[5] = numPar % 2 == 0 ? 0 : 1;
                                    objetos[6] = 1;
                                    listaColumnas.add(objetos);
                                }
                            }
                        }
                        tabV.setTieneTabla(tieneTabla);
                        tabV.setTieneGrafico(tieneGrafico);
                        lineChartModel.setTitle(tabV.getNombreTab());
                        lineChartModel.setLegendPosition("e");
                        DateAxis axis = new DateAxis("Fechas");
                        axis.setTickAngle(-50);
                        //axis.setMax("2014-01-01 00:10:56");
                        axis.setTickFormat("%Y/%m/%d");
                        lineChartModel.getAxes().put(AxisType.X, axis);
                        tabV.setLineChartModel(lineChartModel);
                        tabV.setNombresColumnas(listaColumnas);
                        tabV.setTieneFooter(tieneFooter);
                        List<Object[]> listaFooter1 = new ArrayList<>();
                        /*for (Object[] ObjFooter1 : listaFooter) {
                    if (!ObjFooter1[1].equals(0)) {
                    listaFooter1.add(ObjFooter1);
                    }
                    }*/
                        listaFooter.stream().filter((ObjFooter1) -> (!ObjFooter1[1].equals(0))).forEach((ObjFooter1) -> {
                            listaFooter1.add(ObjFooter1);
                        });
                        tabV.setNombreColumnasFooter(listaFooter1);
                        tabV.setNumeroResumen(numeroResumen);
                        List<List<Object[]>> listaResumenFinal = new ArrayList<>();
                        if (numeroResumen >= 21) {
                            int resNum = (numeroResumen / 4 + 1) / 2;
                            int contador = 0;
                            List<Object[]> listaResumen1 = new ArrayList<>();
                            List<Object[]> listaResumen2 = new ArrayList<>();
                            for (Object[] objects : listaResumen) {
                                if (contador < resNum) {
                                    listaResumen1.add(objects);
                                } else {
                                    listaResumen2.add(objects);
                                }
                                contador++;
                            }
                            listaResumenFinal.add(listaResumen1);
                            listaResumenFinal.add(listaResumen2);
                        } else {
                            listaResumenFinal.add(listaResumen);
                        }
                        tabV.setResumenesTabla(listaResumenFinal);
                        tabV.setResumenTabla(listaResumen);
                        tabV.setTieneColumnasGrupo(tieneGrupo);
                        tabV.setNombreColumnasGroup(listaGrupoColumnas);
                        tabV.setAccionesTabla(listaAcciones);
                        tabViewInec.add(tabV);
                    } else {
                        System.err.println("dataList2 vacio");
                    }
                } else {
                    System.err.println("dataList2 null");
                    baseControlador.addErrorMessage(mensaje);
                }

                clave = valueC;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public String reemplazarParamConsultaCarto(String select) {
        //System.err.println(">>>sentencia2 " + select);
        int par1 = 0, par2 = 0, m = 1;
        boolean boolPar2 = false;
        List<Object[]> resumenFiltroCarto = new ArrayList<>();
        if (!resumenFiltro.isEmpty()) {
            resumenFiltro.forEach((obj) -> {
                resumenFiltroCarto.add(obj);
            });
            resumenFiltroCarto.remove(0);
            for (int i = 0; i < resumenFiltroCarto.size(); i++) {
                if (!String.valueOf(resumenFiltroCarto.get(i)[1]).contains("¿")) {
                    if (m == 1 && boolPar2 == false) {
                        par1 = 1;
                    }
                    if (m == 2 && boolPar2 == false) {
                        par1 = 3;
                    }
                    if (m == 3 && boolPar2 == false) {
                        par1 = 5;
                    }
                    if (m == 4 && boolPar2 == false) {
                        par1 = 7;
                    }
                    if (m == 5 && boolPar2 == false) {
                        par1 = 10;
                    }
                    if (m == 6 && boolPar2 == false) {
                        par1 = 13;
                    }
                    boolPar2 = true;
                    par2 = String.valueOf(resumenFiltroCarto.get(i)[1]).length() + par2;
                }
                m++;
            }
        }
        if (select.contains("'@'")) {
            select = select.replace("'@'", String.valueOf(par1));
        }
        if (select.contains("'#'")) {
            select = select.replace("'#'", String.valueOf(par2));
        }
        if (select.contains("¿")) {
            select = select.replace("¿", "");
        }
        param1 = par1;
        param2 = par2;
        //System.err.println("par1 " + par1);
        //System.err.println("par2 " + par2);
        //System.err.println(">>>sentencia3 " + select);
        return select;
    }

    private List<String> filasSubtitulos(String nombrePag, String[] parametrosCol) {
        List<String> subtitulos = new ArrayList<>();
        subtitulos.add(nombrePag);
        for (String subtitulo : parametrosCol) {
            if (!subtitulo.equals("0")) {
                String valorFiltro = subtitulo.split(":")[1].split(",")[0];
                subtitulos.add(valorFiltro);
            }
        }
        return subtitulos;
    }

    private Object[] columnaGrupoReporte(RepoColumna columnaTabla) {
        String[] parametrosCol;
        Object[] objetos = new Object[5];
        objetos[0] = columnaTabla.getNombre();
        objetos[4] = 0;
        parametrosCol = columnaTabla.getAtributo().split("\\|");
        for (String parametroCol : parametrosCol) {
            if (parametroCol.contains("colspan")) {
                objetos[3] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("anchoColumna")) {
                objetos[2] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("alineacion")) {
                objetos[1] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("visualizar")) {//solo zonales 
                //if ((baseControlador.zonalActual.getCatCodigo().equals("11") && (zonalE.getCatCodigo().equals("11") || parametroCol.split("=")[1].equals("11"))) || (!zonalE.getCatCodigo().equals("11") && parametroCol.split("=")[1].equals(zonalE.getCatCodigo())) || parametroCol.split("=")[1].equals("10")) {
                if (parametroCol.split("=")[1].equals("16")) {
                    objetos[4] = 1;
                    //} else if (parametroCol.split("=")[1].equals(baseControlador.zonalActual.getCatCodigo()) || parametroCol.split("=")[1].equals("10")) {
                } else if (parametroCol.split("=")[1].equals("10")) {
                    objetos[4] = 1;
                }
            }
        }
        return objetos;
    }

    private Object[] columnaReporte(RepoColumna columnaTabla, int columnaSuma) {
        String[] parametrosCol;
        Object[] objetos = new Object[10];
        objetos[0] = columnaTabla.getNombre();
        objetos[8] = columnaTabla.getCampo();
        objetos[6] = 0;
        parametrosCol = columnaTabla.getAtributo().split("\\|");
        for (String parametroCol : parametrosCol) {
            if (parametroCol.contains("columna")) {
                if (columnaSuma > 0) {
                    int columnaOriginal = Integer.valueOf(parametroCol.split("=")[1]);
                    objetos[3] = columnaOriginal + columnaSuma;
                } else {
                    objetos[3] = parametroCol.split("=")[1];
                }
            }
            if (parametroCol.contains("anchoColumna")) {
                objetos[2] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("alineacion")) {
                objetos[1] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("filtro")) {
                objetos[4] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("orden")) {
                objetos[5] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("visualizar")) {
                /*if ((baseControlador.zonalActual.getCatCodigo().equals("11") && (zonalE.getCatCodigo().equals("11") || parametroCol.split("=")[1].equals("11"))) || (!zonalE.getCatCodigo().equals("11") && parametroCol.split("=")[1].equals(zonalE.getCatCodigo())) || parametroCol.split("=")[1].equals("10")) {
                    objetos[6] = 1;
                }*/
                if (("12".equals("11") && ("12".equals("11") || parametroCol.split("=")[1].equals("11"))) || (!"12".equals("11") && parametroCol.split("=")[1].equals("12")) || parametroCol.split("=")[1].equals("10")) {
                    objetos[6] = 1;
                }
            }
            if (parametroCol.contains("tipoDato")) {
                objetos[7] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("clase")) {
                objetos[9] = parametroCol.split("=")[1];
            }
        }
        return objetos;
    }

    private Object[] footerReporte(RepoColumna columnaTabla, List<Object[]> listaObjetos) {
        String[] parametrosCol;
        parametrosCol = columnaTabla.getAtributo().split("\\|");
        Object[] objetos = new Object[6];
        objetos[0] = columnaTabla.getNombre();
        objetos[1] = 0;
        for (String parametroCol : parametrosCol) {
            if (parametroCol.contains("visualizar")) {
                /*if (baseControlador.zonalActual.getCatCodigo().equals("11") || parametroCol.split("=")[1].equals(baseControlador.zonalActual.getCatCodigo()) || parametroCol.split("=")[1].equals("11")) {
                    objetos[1] = 1;
                }*/
                if ("12".equals("11") || parametroCol.split("=")[1].equals("12") || parametroCol.split("=")[1].equals("10")) {
                    objetos[1] = 1;
                }
            }
            if (parametroCol.contains("colspan")) {
                objetos[2] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("alineacion")) {
                objetos[3] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("valor")) {
                String valoresSuma[] = parametroCol.split("=")[1].split(";");

                String valoresSumaContinuo = parametroCol.split("=")[1];
                if (valoresSumaContinuo.contains("...")) {
                    int numCampoIni = 0, numCampoFin = 0;
                    for (String valorSuma : valoresSuma) {

                        if (valorSuma.contains("campoIni")) {
                            numCampoIni = Integer.valueOf(valorSuma.split(":")[1]);
                        } else if (valorSuma.contains("campoFin")) {
                            numCampoFin = Integer.valueOf(valorSuma.split(":")[1]);
                        }
                    }
                    parametroCol = "valor=suma:0";
                    for (int i = numCampoIni; i <= numCampoFin; i++) {
                        parametroCol = parametroCol + ";campo" + i + ":" + i + ",xxx";
                    }
                    valoresSuma = parametroCol.split("=")[1].split(";");
                }

                String valoresFF[] = parametroCol.split("=")[1].split("campo");
                Object[] objectFF = new Object[valoresFF.length - 1];
                int j = 0;
                for (String valorSuma : valoresSuma) {
                    Integer sumaT = 0;
                    //int campoSuma = Integer.valueOf(valoresSuma[0].split(":")[1]);
                    //int campoFiltro = 0;
                    int campoSuma = 0;
                    if (valorSuma.contains("pro")) {
                        //obtiene el promedio del resultado del objeto objectFF en base a la formula 'pro:11-campo23:5,2'
                        //donde 11 viene hacer el número del array del objectFF donde se va a colocar el resultado de la operación, objectFF[11]; 12-1 respecto al objeto [valoresFF.length - 1];
                        //23 número de la columna donde se va a colocar el resultado de la operación en el excel, solo es un referencial
                        //5 número del array del objectFF, dividendo, objectFF[5]
                        //2 número del array del objetoFF, divisor, objectFF[2]
                        int campoobjectFF = Integer.valueOf(valorSuma.split("-")[0].split(":")[1]);
                        int objectFFDividendo = Integer.valueOf(valorSuma.split("-")[1].split(":")[1].split(",")[0]);
                        int objectFFDivisor = Integer.valueOf(valorSuma.split("-")[1].split(":")[1].split(",")[1]);
                        if (Integer.valueOf(objectFF[objectFFDivisor].toString()) != 0) {
                            objectFF[campoobjectFF] = String.format("%.2f", (Double.valueOf(objectFF[objectFFDividendo].toString()) / Double.valueOf(objectFF[objectFFDivisor].toString())) * 100);
                        } else {
                            objectFF[campoobjectFF] = 0;
                        }
                    } else if (valorSuma.contains("var")) {
                        //obtiene el promedio del resultado del objeto objectFF en base a la formula 'var:11-campo23:5,2'
                        //donde 11 viene hacer el número del array del objectFF donde se va a colocar el resultado de la operación, objectFF[11]; 12-1 respecto al objeto [valoresFF.length - 1];
                        //23 número de la columna donde se va a colocar el resultado de la operación en el excel, solo es un referencial
                        //5 número del array del objectFF, dividendo, objectFF[5]
                        //2 número del array del objetoFF, divisor, objectFF[2]
                        int campoobjectFF = Integer.valueOf(valorSuma.split("-")[0].split(":")[1]);
                        int objectFFDividendo = Integer.valueOf(valorSuma.split("-")[1].split(":")[1].split(",")[0]);
                        int objectFFDivisor = Integer.valueOf(valorSuma.split("-")[1].split(":")[1].split(",")[1]);
                        if (Integer.valueOf(objectFF[objectFFDivisor].toString()) != 0) {
                            objectFF[campoobjectFF] = String.format("%.2f", ((Double.valueOf(objectFF[objectFFDividendo].toString()) - Double.valueOf(objectFF[objectFFDivisor].toString())) / Double.valueOf(objectFF[objectFFDivisor].toString())) * 100);
                        } else {
                            objectFF[campoobjectFF] = 0;
                        }
                    } else if (valorSuma.contains("div")) {
                        //obtiene el promedio del resultado del objeto objectFF en base a la formula 'var:11-campo23:5,2'
                        //donde 11 viene hacer el número del array del objectFF donde se va a colocar el resultado de la operación, objectFF[11]; 12-1 respecto al objeto [valoresFF.length - 1];
                        //23 número de la columna donde se va a colocar el resultado de la operación en el excel, solo es un referencial
                        //5 número del array del objectFF, dividendo, objectFF[5]
                        //2 número del array del objetoFF, divisor, objectFF[2]
                        int campoobjectFF = Integer.valueOf(valorSuma.split("-")[0].split(":")[1]);
                        int objectFFDividendo = Integer.valueOf(valorSuma.split("-")[1].split(":")[1].split(",")[0]);
                        int objectFFDivisor = Integer.valueOf(valorSuma.split("-")[1].split(":")[1].split(",")[1]);
                        if (Integer.valueOf(objectFF[objectFFDivisor].toString()) != 0) {
                            objectFF[campoobjectFF] = String.format("%.2f", (Double.valueOf(objectFF[objectFFDividendo].toString()) / Double.valueOf(objectFF[objectFFDivisor].toString())));
                        } else {
                            objectFF[campoobjectFF] = 0;
                        }
                    } else {
                        for (int i = 0; i < listaObjetos.size(); i++) {
                            int contar = 0;
                            int sumar = 0;
                            if (valorSuma.contains("cuenta")) {
                                campoSuma = Integer.valueOf(valorSuma.split("-")[0].split(":")[1]);
                                contar = 1;
                            } else {
                                campoSuma = Integer.valueOf(valorSuma.split(":")[1].split(",")[0]);
                            }
                            if (valorSuma.contains("campo")) {
                                int campoFiltro;
                                String valorFiltro;
                                if (contar == 0) {
                                    campoFiltro = Integer.valueOf(valorSuma.split(":")[1].split(",")[0]);
                                    valorFiltro = valorSuma.split(":")[1].split(",")[1];
                                } else {
                                    campoFiltro = Integer.valueOf(valorSuma.split("-")[1].split(":")[1].split(",")[0]);
                                    valorFiltro = valorSuma.split("-")[1].split(":")[1].split(",")[1];
                                }
                                if (listaObjetos.get(i)[campoFiltro] != null) {
                                    if (contar == 0) {
                                        for (int k = 2; k < valorSuma.split(":").length; k++) {
                                            valorFiltro = valorFiltro + ":" + valorSuma.split(":")[k];
                                        }
                                    } else {
                                        for (int k = 2; k < valorSuma.split("-")[1].split(":").length; k++) {
                                            valorFiltro = valorFiltro + ":" + valorSuma.split("-")[1].split(":")[k];
                                        }
                                    }
                                    if (valorFiltro.contains("<>")) {
                                        if (!listaObjetos.get(i)[campoFiltro].equals(valorFiltro.split(">")[1])) {
                                            sumar = 1;
                                        } else {
                                            sumar = 0;
                                        }

                                    } else if (valorFiltro.contains("%")) {
                                        if (((String) listaObjetos.get(i)[campoFiltro]).contains(valorFiltro.split("%")[1])) {
                                            sumar = 1;
                                        } else {
                                            sumar = 0;
                                        }
                                    } else {
                                        String tipoDato = listaObjetos.get(i)[campoFiltro].getClass().getSimpleName();
                                        if (tipoDato.equals("BigDecimal")) {
                                            int comparacionTipo = 0;
                                            int comparacionRes = 0;
                                            if (valorFiltro.contains(">")) {
                                                if (valorFiltro.contains(">=")) {
                                                    valorFiltro = valorFiltro.split("=")[1];
                                                    comparacionTipo = 1;
                                                } else {
                                                    valorFiltro = valorFiltro.split(">")[1];
                                                    comparacionTipo = 2;
                                                }
                                            }
                                            if (valorFiltro.contains("<")) {
                                                if (valorFiltro.contains("<=")) {
                                                    valorFiltro = valorFiltro.split("=")[1];
                                                    comparacionTipo = -1;
                                                } else {
                                                    valorFiltro = valorFiltro.split("<")[1];
                                                    comparacionTipo = -2;
                                                }
                                            }
                                            BigDecimal valorComparar = new BigDecimal(valorFiltro);
                                            BigDecimal valorTabla = (BigDecimal) listaObjetos.get(i)[campoFiltro];
                                            comparacionRes = valorComparar.compareTo(valorTabla);
                                            if ((comparacionRes == 0 && (comparacionTipo == 0 || comparacionTipo == 1 || comparacionTipo == -1)) || (comparacionRes == 1 && (comparacionTipo < 0)) || (comparacionRes == -1 && (comparacionTipo > 0))) {
                                                sumar = 1;
                                            } else {
                                                sumar = 0;
                                            }

                                        } else if (listaObjetos.get(i)[campoFiltro].equals(valorFiltro) || valorFiltro.equals("xxx")) {
                                            sumar = 1;
                                        } else {
                                            sumar = 0;
                                        }
                                    }
                                }
                            }
                            //cuenta columnas de una lista en la tabla
                            if (sumar == 1) {
                                if (contar == 1) {
                                    sumaT += 1;
                                } else {
                                    sumaT += Integer.valueOf(listaObjetos.get(i)[campoSuma].toString());
                                }
                            }
                        }
                        if (valorSuma.contains("campo")) {
                            objectFF[j] = sumaT;
                            j++;
                        }
                    }
                }
                objetos[4] = objectFF;
            }
        }
        return objetos;
    }

    private Object[] resumenReporte(RepoColumna columnaTabla, List<Object[]> listaObjetos) {
        String[] parametrosCol;
        parametrosCol = columnaTabla.getAtributo().split("\\|");
        Object[] objetos = new Object[3];
        objetos[1] = columnaTabla.getNombre();
        for (String parametroCol : parametrosCol) {
            if (parametroCol.contains("imagen")) {
                objetos[0] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("valor")) {
                Integer sumaT = 0;
                String valoresSuma[] = parametroCol.split("=")[1].split(";");

                for (int i = 0; i < listaObjetos.size(); i++) {
                    int sumar;
                    int contar = 0;
                    int campoSuma = Integer.valueOf(valoresSuma[0].split(":")[1]);
                    if (valoresSuma[0].contains("cuenta")) {
                        contar = 1;
                    }
                    for (String valorSuma : valoresSuma) {
                        sumar = 0;
                        if (valorSuma.contains("campo")) {
                            int campoFiltro = Integer.valueOf(valorSuma.split(":")[1].split(",")[0]);
                            if (listaObjetos.get(i)[campoFiltro] != null) {
                                String valorFiltro = valorSuma.split(":")[1].split(",")[1];
                                for (int k = 2; k < valorSuma.split(":").length; k++) {
                                    valorFiltro = valorFiltro + ":" + valorSuma.split(":")[k];
                                }
                                if (valorFiltro.contains("<>")) {
                                    if (!listaObjetos.get(i)[campoFiltro].equals(valorFiltro.split(">")[1])) {
                                        sumar = 1;
                                    } else {
                                        sumar = 0;
                                    }

                                } else if (valorFiltro.contains("%")) {
                                    if (((String) listaObjetos.get(i)[campoFiltro]).contains(valorFiltro.split("%")[1])) {
                                        sumar = 1;
                                    } else {
                                        sumar = 0;
                                    }
                                } else {
                                    String tipoDato = listaObjetos.get(i)[campoFiltro].getClass().getSimpleName();
                                    if (tipoDato.equals("BigDecimal")) {
                                        int comparacionTipo = 0;
                                        int comparacionRes = 0;
                                        if (valorFiltro.contains(">")) {
                                            if (valorFiltro.contains(">=")) {
                                                valorFiltro = valorFiltro.split("=")[1];
                                                comparacionTipo = 1;
                                            } else {
                                                valorFiltro = valorFiltro.split(">")[1];
                                                comparacionTipo = 2;
                                            }
                                        }
                                        if (valorFiltro.contains("<")) {
                                            if (valorFiltro.contains("<=")) {
                                                valorFiltro = valorFiltro.split("=")[1];
                                                comparacionTipo = -1;
                                            } else {
                                                valorFiltro = valorFiltro.split("<")[1];
                                                comparacionTipo = -2;
                                            }
                                        }
                                        BigDecimal valorComparar = new BigDecimal(valorFiltro);
                                        BigDecimal valorTabla = (BigDecimal) listaObjetos.get(i)[campoFiltro];
                                        comparacionRes = valorComparar.compareTo(valorTabla);
                                        if ((comparacionRes == 0 && (comparacionTipo == 0 || comparacionTipo == 1 || comparacionTipo == -1)) || (comparacionRes == 1 && (comparacionTipo < 0)) || (comparacionRes == -1 && (comparacionTipo > 0))) {
                                            sumar = 1;
                                        } else {
                                            sumar = 0;
                                        }

                                    } else if (listaObjetos.get(i)[campoFiltro].equals(valorFiltro) || valorFiltro.equals("xxx")) {
                                        sumar = 1;
                                    } else {
                                        sumar = 0;
                                    }
                                }
                            }
                        }
                        if (sumar == 1) {
                            if (contar == 1) {
                                sumaT += 1;
                            } else {
                                sumaT += (Integer) listaObjetos.get(i)[campoSuma];
                            }
                        }
                    }
                    objetos[2] = sumaT;
                }
            }
        }
        return objetos;
    }

    private Object[] accionReporte(RepoColumna columnaTabla, List<Object[]> listaObjetos) {
        String[] parametrosCol;
        parametrosCol = columnaTabla.getAtributo().split("\\|");
        Object[] objetos = new Object[10];
        objetos[0] = columnaTabla.getNombre();
        for (String parametroCol : parametrosCol) {
            if (parametroCol.contains("ancho")) {
                objetos[1] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("bean")) {
                objetos[2] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("metodo")) {
                objetos[3] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("variable")) {
                objetos[4] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("valor")) {
                objetos[5] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("icon")) {
                objetos[6] = parametroCol.split("=")[1];
            }
            if (parametroCol.contains("disable")) {
                objetos[7] = parametroCol.split("=")[1];
            }

            if (parametroCol.contains("visualizar")) {//solo zonales 
                //if (parametroCol.split("=")[1].equals("16") && !baseControlador.zonalActual.getCatCodigo().equals("11")) {
                if (parametroCol.split("=")[1].equals("16")) {
                    objetos[8] = 1;
                    //} else if (parametroCol.split("=")[1].equals(baseControlador.zonalActual.getCatCodigo()) || parametroCol.split("=")[1].equals("10")) {
                } else if (parametroCol.split("=")[1].equals("10")) {
                    objetos[8] = 1;
                }
            }
            if (parametroCol.contains("tipoDato")) {
                objetos[9] = parametroCol.split("=")[1];
            }
        }
        return objetos;
    }

    public void exportar(ActionEvent event) {

        try {
            String paramCheck = "";
            if (!mapCheckParam.isEmpty()) {
                for (Entry<String, Boolean> param : mapCheckParam.entrySet()) {
                    if (param.getValue().equals(true)) {
                        paramCheck = param.getKey();
                    }
                }
            }

            if (paramCheck.isEmpty()) {
                FacesContext context = FacesContext.getCurrentInstance();
                exportExcel = new ExcelExporter();
                exportExcel.setNumeroPag(tabViewInec.size());
                System.out.println("Inicia descarga excel");
                for (int i = 0; i < tabViewInec.size(); i++) {
                    exportExcel.customFormat(null, "9", null, "BOLD", null, "9", null, "NORMAL", "5");
                    exportExcel.export(event, "dt_" + tabViewInec.get(i).getIdTab(), context, idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual, filasSubtitulos(tabViewInec.get(i).getNombreTab(), tabViewInec.get(i).getSubtitulos()), false, false, null, null, null, false, idReporte.getNombre(), resumenFiltro, lstCheckFor2);
                }
                
                System.out.println("Fin descarga excel");
                //expPdf = false;
                PrimeFaces reqCtx = PrimeFaces.current();
                reqCtx.ajax().addCallbackParam("ejecutado", '1');
                



            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext externalContext = context.getExternalContext();
                externalContext.setResponseContentType("application/zip");
                externalContext.setResponseHeader("Expires", "0");
                externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                externalContext.setResponseHeader("Pragma", "public");
                externalContext.setResponseHeader("Content-disposition", "attachment;filename=ReporteZip_" + idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + ".zip");
                externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());

                try (OutputStream out = externalContext.getResponseOutputStream(); ZipOutputStream zos = new ZipOutputStream(out)) {
                    List<Object[]> listarFilasObj;
                    //List<Object[]> lstJornadas = new ArrayList<>();
                    if (paramCheck.equals(":equipo")) {
                        listarFilasObj = baseControlador.getRepoFiltroServicioRemote().listarEquiposSeparados();
                    } else if (paramCheck.contains(":jorda")) {
                        /*for (int j = 0; j < tabViewInec.get(0).getFilas().size(); j++) {
                        if (tabViewInec.get(0).getFilas().get(j)[tabViewInec.get(0).getFilas().get(j).length - 1].equals(
                        tabViewInec.get(0).getFilas().get(j + 1)[tabViewInec.get(0).getFilas().get(j + 1).length - 1])) {
                        numjor++;
                        }
                        }*/
                        listarFilasObj = new ArrayList<>();
                        for (int j = 0; j < tabViewInec.get(0).getFilas().size(); j++) {
                            int k = 1;
                            String wordj = String.valueOf(tabViewInec.get(0).getFilas().get(j)[tabViewInec.get(0).getFilas().get(j).length - 1]);
                            for (int i = tabViewInec.get(0).getFilas().size() - 1; i > j; i--) {
                                String wordi = String.valueOf(tabViewInec.get(0).getFilas().get(i)[tabViewInec.get(0).getFilas().get(j).length - 1]);
                                if (wordi != null && wordj != null && wordi.compareTo(wordj) == 0 && i != j) {
                                    k++;
                                }
                            }
                            Object[] objJornada = new Object[2];
                            objJornada[0] = k;
                            objJornada[1] = wordj;
                            if (!listarFilasObj.isEmpty()) {
                                for (int i = 0; i < listarFilasObj.size(); i++) {
                                    if (wordj != null) {
                                        if (!wordj.equals(listarFilasObj.get(i)[1])) {
                                            listarFilasObj.add(objJornada);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                listarFilasObj.add(objJornada);
                            }
                        }
                    } else if (idReporte.getNombre().contains("2020")) {
                        listarFilasObj = baseControlador.getRepoFiltroServicioRemote().listarFiltrosSeparados2020(param1, param2, clave);
                    } else {
                        listarFilasObj = baseControlador.getRepoFiltroServicioRemote().listarFiltrosSeparados(param1, param2, clave);
                    }
                    int par1 = 0, par2;
                    for (int i = 0; i < listarFilasObj.size(); i++) {
                        exportExcelCartografia = new ExcelExporterCarto();
                        exportExcelCartografia.customFormat(null, "9", null, "BOLD", null, "9", null, "NORMAL", "5");
                        ExcelExporterGeneral exportExcelGeneral = new ExcelExporterGeneral();
                        exportExcelGeneral.customFormat(null, "10", null, "BOLD", null, "9", null, "NORMAL", "5");
                        if (i == 0) {
                            par1 = 0;
                            par2 = Integer.valueOf(String.valueOf(listarFilasObj.get(i)[0]));
                        } else {
                            par1 = par1 + Integer.valueOf(String.valueOf(listarFilasObj.get(i - 1)[0]));
                            par2 = par1 + Integer.valueOf(String.valueOf(listarFilasObj.get(i)[0]));
                        }

                        if (paramCheck.equals(":equipo")) {
                            exportExcelGeneral.filasAExportar(par1, par2, String.valueOf(listarFilasObj.get(i)[1]));
                            cargarEstadosFormulario();
                            exportExcelGeneral.exportCartografia(event, "dt_" + tabViewInec.get(0).getIdTab(), context, idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + "_" + String.valueOf(listarFilasObj.get(i)[1]), filasSubtitulos(tabViewInec.get(0).getNombreTab(), tabViewInec.get(0).getSubtitulos()), false, false, null, null, null, false, idReporte.getNombre(), resumenFiltro, lstCheckFor2, zos);

                        } else {
                            exportExcelCartografia.filasAExportar(par1, par2, String.valueOf(listarFilasObj.get(i)[1]));
                            cargarEstadosFormulario2Censo(String.valueOf(listarFilasObj.get(i)[1]));
                            exportExcelCartografia.exportCartografia(event, "dt_" + tabViewInec.get(0).getIdTab(), context, idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + "_" + String.valueOf(listarFilasObj.get(i)[1]), filasSubtitulos(tabViewInec.get(0).getNombreTab(), tabViewInec.get(0).getSubtitulos()), false, false, null, null, null, false, idReporte.getNombre(), resumenFiltro, lstCheckFor2, zos);
                        }
                    }
                }
                context.responseComplete();
                externalContext.responseFlushBuffer();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void exportarSPSS(String idTab, String tipoArchivo) {

        OutputStream out;
        Integer numPestaña = 0;
        FacesContext context;
        //HttpServletResponse response;
        ExternalContext externalContext;
        File file;
        convertJSON json = new convertJSON();
        convertXML xml = new convertXML();
        convertPDF pdf = new convertPDF();
        try {

            //json.devolverJSON("nomSistema", "nomReporte", tabViewInec.get(0).getNombreTab(), tabViewInec.get(0).getNombresColumnas(), tabViewInec.get(0).getFilas());
            context = FacesContext.getCurrentInstance();
            //response = (HttpServletResponse) context.getExternalContext().getResponse();
            externalContext = context.getExternalContext();
            //System.err.println(">>5 " + nomReporte);
            //System.err.println(">>6 " + idReporte.getNombre());

            for (int m = 0; m < tabViewInec.size(); m++) {
                //System.err.println("1>> " + idTab);
                //System.err.println("2>> " + tabViewInec.get(m).getIdTab());
                if (idTab.equals(tabViewInec.get(m).getIdTab())) {
                    numPestaña = m;
                }
            }

            file = new File(idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + "_" + numPestaña + tipoArchivo);
            // Set the response type and specify the boundary string
            /*externalContext.setResponseContentType("multipart/x-mixed-replace;boundary=END");
            // Set the content type based on the file type you need to download
            externalContext.setResponseContentType("application/octet-stream");
            //response.setContentLength((int) file.length());
            externalContext.setResponseHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));*/
            externalContext.setResponseContentType("multipart/x-mixed-replace;boundary=END");
            externalContext.setResponseContentType("application/octet-stream");
            externalContext.setResponseHeader("Expires", "0");
            externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            externalContext.setResponseHeader("Pragma", "public");
            externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + file.getName());
            externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());
            out = externalContext.getResponseOutputStream();

            if (tipoArchivo.contains("sav")) {
                // Assign SPSS output to the file
                SPSSWriter outSPSS = new SPSSWriter(out, "utf-8");
                // Creating SPSS variable description table
                outSPSS.setCalculateNumberOfCases(false);
                outSPSS.addDictionarySection(-1);
                for (int i = 0; i < tabViewInec.get(numPestaña).getNombresColumnas().size(); i++) {
                    if (tabViewInec.get(numPestaña).getNombresColumnas().get(i)[7].equals("TEXTO")) {
                        // Describing varible names and types
                        outSPSS.addStringVar(tabViewInec.get(numPestaña).getNombresColumnas().get(i)[8].toString(), 255, "Etiqueta o descripcion: Ninguna");
                    }
                    if (tabViewInec.get(numPestaña).getNombresColumnas().get(i)[7].equals("ENTERO")) {
                        outSPSS.addNumericVar(tabViewInec.get(numPestaña).getNombresColumnas().get(i)[8].toString(), 6, 0, "Etiqueta o descripcion: Ninguna");
                    }
                }
                //ValueLabels valueLabels = new ValueLabels();
                //valueLabels.putLabel
                // Create SPSS variable value define table
                outSPSS.addDataSection();
                // Add values for all defined variables
                for (int i = 0; i < tabViewInec.get(numPestaña).getFilas().size(); i++) {
                    //Add values for all defined variables
                    for (int j = 0; j < tabViewInec.get(numPestaña).getNombresColumnas().size(); j++) {
                        if (tabViewInec.get(numPestaña).getNombresColumnas().get(j)[7].equals("TEXTO")) {
                            Object cadena = tabViewInec.get(numPestaña).getFilas().get(i)[Integer.valueOf(tabViewInec.get(numPestaña).getNombresColumnas().get(j)[3].toString())];
                            if (cadena != null) {
                                outSPSS.addData(String.valueOf(cadena));
                            } else {
                                outSPSS.addData("");
                            }
                        }
                        if (tabViewInec.get(numPestaña).getNombresColumnas().get(j)[7].equals("ENTERO")) {
                            Object cadena = tabViewInec.get(numPestaña).getFilas().get(i)[Integer.valueOf(tabViewInec.get(numPestaña).getNombresColumnas().get(j)[3].toString())];

                            if (cadena != null) {
                                outSPSS.addData(new Long(String.valueOf(cadena)));
                            } else {
                                outSPSS.addData(new Long(String.valueOf("0")));
                            }
                        }
                    }
                }
                // Create SPSS ending section
                outSPSS.addFinishSection();
                outSPSS.getOut();
            } else if (tipoArchivo.contains("json")) {
                String write = json.devolverJSON(idReporte.getNombre(), tabViewInec.get(numPestaña).getNombreTab(), tabViewInec.get(numPestaña).getNombresColumnas(), tabViewInec.get(numPestaña).getFilas(), horaActual, filtros, tabViewInec.get(numPestaña).getResumenTabla(), tabViewInec.get(numPestaña).getNombreColumnasFooter(), "json");
                out.write(write.getBytes());

            } else if (tipoArchivo.contains("xml")) {
                String fileJson = json.devolverJSON(idReporte.getNombre(), tabViewInec.get(numPestaña).getNombreTab(), tabViewInec.get(numPestaña).getNombresColumnas(), tabViewInec.get(numPestaña).getFilas(), horaActual, filtros, tabViewInec.get(numPestaña).getResumenTabla(), tabViewInec.get(numPestaña).getNombreColumnasFooter(), "xml");

                String write = xml.convert_json(fileJson, json.getMapJSONXml());
                out.write(write.getBytes());
            } else if (tipoArchivo.contains("pdf")) {

                String paramCheck = "";
                if (!mapCheckParam.isEmpty()) {
                    for (Entry<String, Boolean> param : mapCheckParam.entrySet()) {
                        if (param.getValue().equals(true)) {
                            paramCheck = param.getKey();
                        }
                    }
                }

                if (clave.length() >= 9) {
                    if (clave.substring(6, 9).equals("999")) {
                        if (resumenFiltro.size() != 7) {
                            Object[] obj = new Object[2];
                            obj[0] = "";
                            obj[1] = "";
                            resumenFiltro.add(obj);
                        }
                    }
                }

                if (paramCheck.isEmpty()) {
                    PDFExporter exportPdf = new PDFExporter();
                    exportPdf.customFormat(null, "7", null, "BOLD", null, "7", null, "NORMAL", "5", "Landscape");
                    if (lstCheckFor2Anverso != null) {
                        if (!lstCheckFor2Anverso.isEmpty()) {
                            PDFExporterCa06Anverso exportPdfAnverso = new PDFExporterCa06Anverso();
                            exportPdfAnverso.customFormat(null, "8", null, "BOLD", null, "8", null, "NORMAL", "5", "Landscape");
                            exportPdfAnverso.export(buscarComponente, "dt_" + tabViewInec.get(numPestaña).getIdTab(), context, idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual, filasSubtitulos(tabViewInec.get(numPestaña).getNombreTab(), tabViewInec.get(numPestaña).getSubtitulos()), false, false, null, null, null, false, idReporte.getNombre(), resumenFiltro, lstCheckFor2Anverso);
                        }
                    } else {
                        exportPdf.export(buscarComponente, "dt_" + tabViewInec.get(numPestaña).getIdTab(), context, idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual, filasSubtitulos(tabViewInec.get(numPestaña).getNombreTab(), tabViewInec.get(numPestaña).getSubtitulos()), false, false, null, null, null, false, idReporte.getNombre(), resumenFiltro, lstCheckFor2);
                    }
                } else {
                    externalContext.setResponseContentType("application/zip");
                    externalContext.setResponseHeader("Expires", "0");
                    externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                    externalContext.setResponseHeader("Pragma", "public");
                    externalContext.setResponseHeader("Content-disposition", "attachment;filename=ReporteZip_" + idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + ".zip");
                    externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());

                    try (OutputStream outZipPdf = externalContext.getResponseOutputStream(); ZipOutputStream zos = new ZipOutputStream(outZipPdf)) {
                        List<Object[]> listarFilasObj;
                        if (paramCheck.equals(":equipo")) {
                            listarFilasObj = baseControlador.getRepoFiltroServicioRemote().listarEquiposSeparados();
                        } else if (idReporte.getNombre().contains("2020")) {
                            listarFilasObj = baseControlador.getRepoFiltroServicioRemote().listarFiltrosSeparados2020(param1, param2, clave);
                        } else {
                            listarFilasObj = baseControlador.getRepoFiltroServicioRemote().listarFiltrosSeparados(param1, param2, clave);
                        }
                        int par1 = 0, par2;
                        for (int i = 0; i < listarFilasObj.size(); i++) {
                            PDFExporterCarto exportPdfVarios = new PDFExporterCarto();
                            exportPdfVarios.customFormat(null, "7", null, "BOLD", null, "7", null, "NORMAL", "5", "Landscape");
                            PDFExporterGeneral exportPdfGeneral = new PDFExporterGeneral();
                            exportPdfGeneral.customFormat(null, "9", null, "BOLD", null, "9", null, "NORMAL", "5", "Landscape");
                            if (i == 0) {
                                par1 = 0;
                                par2 = Integer.valueOf(String.valueOf(listarFilasObj.get(i)[0]));
                            } else {
                                par1 = par1 + Integer.valueOf(String.valueOf(listarFilasObj.get(i - 1)[0]));
                                par2 = par1 + Integer.valueOf(String.valueOf(listarFilasObj.get(i)[0]));
                            }
                            if (paramCheck.equals(":equipo")) {
                                exportPdfGeneral.filasAExportar(par1, par2, String.valueOf(listarFilasObj.get(i)[1]));
                                cargarEstadosFormulario();
                                exportPdfGeneral.exportCartografia(buscarComponente, "dt_" + tabViewInec.get(0).getIdTab(), context, idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + "_" + String.valueOf(listarFilasObj.get(i)[2]).replaceAll("\\s", ""), filasSubtitulos(tabViewInec.get(0).getNombreTab(), tabViewInec.get(0).getSubtitulos()), false, false, null, null, null, false, idReporte.getNombre(), resumenFiltro, lstCheckFor2, zos);
                            } else {
                                exportPdfVarios.filasAExportar(par1, par2, String.valueOf(listarFilasObj.get(i)[1]));
                                cargarEstadosFormulario2Censo(String.valueOf(listarFilasObj.get(i)[1]));
                                exportPdfVarios.exportCartografia(buscarComponente, "dt_" + tabViewInec.get(0).getIdTab(), context, idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + "_" + String.valueOf(listarFilasObj.get(i)[1]), filasSubtitulos(tabViewInec.get(0).getNombreTab(), tabViewInec.get(0).getSubtitulos()), false, false, null, null, null, false, idReporte.getNombre(), resumenFiltro, lstCheckFor2, zos);
                            }
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    context.responseComplete();
                    externalContext.responseFlushBuffer();
                }

            } else if (tipoArchivo.contains("dta")) {
                String write = "<stata_dta><header><release>117</release><byteorder>MSF</byteorder><K>0002</K><N>0000000000000001</N><label>000bSample Data</label><timestamp>1110 Mar 2017 14:23</timestamp></header><map>0000000000000000000000000000009900000000000001410000000000000139000000000000019000000000000001ab0000000000000220000000000000034e00000000000003710000000000000384000000000000039300000000000003b000000000000003bc</map><variable_types>fff7fff9</variable_types><varnames>myfloat00........................myint00..........................</varnames><sortlist>000000000000</sortlist><formats>%9.0g00..............................%8.0g00...............................</formats><value_label_names>00................................00................................</value_label_names><variable_labels>00.................................................00................................................</variable_labels><characteristics></characteristics><data>000000000000</data><strls></strls><value_labels></value_labels></stata_dta>";
                //String write=xml.convert_json(fileJson);
                out.write(write.getBytes());
            }
            out.close();
            context.responseComplete();
            externalContext.responseFlushBuffer();
            //out.flush();

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cargarEstadosFormulario() {
        try {
            List<Object[]> lstRepoCabeceraAsg = new ArrayList<>();
            if (idReporte.getNombre().toUpperCase().contains("CALIDAD")) {
                lstRepoCabeceraAsg = baseControlador.getRepoReporteServicioRemote().lstCabeceraAsigCargas();
            } else if (idReporte.getNombre().toUpperCase().contains("CAMPO")) {
                lstRepoCabeceraAsg = baseControlador.getRepoReporteServicioRemote().lstCabeceraAsigCargasEQ();
            }

            lstCheckFor2 = new ArrayList<>();
            if (!lstRepoCabeceraAsg.isEmpty()) {
                for (Object[] objects : lstRepoCabeceraAsg) {
                    lstCheckFor2.add(objects);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cargarEstadosFormulario2Censo(String clave) {
        try {
            CaptCabecera captCabecera;
            if (idReporte.getNombre().contains("2020")) {
                captCabecera = baseControlador.getCaptCabeceraServicioRemote().buscarCabeceraPorClaveInfo4(clave, "info4");
            } else if (idReporte.getNombre().contains("2018")){
                captCabecera = baseControlador.getCaptCabeceraServicioRemote().buscarCabeceraPorClaveInfo4(clave, "clave");
            } else {
                captCabecera = baseControlador.getCaptCabeceraServicioRemote().buscarCabeceraPorClaveInfo4(clave, "clave");
            }

            lstCheckFor2 = new ArrayList<>();
            if (captCabecera != null) {
                if (captCabecera.getEstado3() != null) {
                    Object[] for2 = new Object[2];
                    for2[0] = "1. ALTA";
                    for2[1] = captCabecera.getEstado3().equals("1");
                    lstCheckFor2.add(0, for2);
                    for2 = new Object[2];
                    for2[0] = "2. MEDIA";
                    for2[1] = captCabecera.getEstado3().equals("2");
                    lstCheckFor2.add(1, for2);
                    for2 = new Object[2];
                    for2[0] = "3. BAJA";
                    for2[1] = captCabecera.getEstado3().equals("3");
                    lstCheckFor2.add(2, for2);

                    if (captCabecera.getEstado4() != null) {
                        for2 = new Object[2];
                        for2[0] = "1. TRADICIONAL";
                        for2[1] = captCabecera.getEstado4().equals("1");
                        lstCheckFor2.add(3, for2);
                        for2 = new Object[2];
                        for2[0] = "2. LANCHA";
                        for2[1] = captCabecera.getEstado4().equals("2");
                        lstCheckFor2.add(4, for2);
                        for2 = new Object[2];
                        for2[0] = "3. GUÍA";
                        for2[1] = captCabecera.getEstado4().equals("3");
                        lstCheckFor2.add(5, for2);
                        for2 = new Object[2];
                        for2[0] = "4. AVIONETA";
                        for2[1] = captCabecera.getEstado4().equals("4");
                        lstCheckFor2.add(6, for2);
                        for2 = new Object[2];
                        for2[0] = "5. A PIE";
                        for2[1] = captCabecera.getEstado4().equals("5");
                        lstCheckFor2.add(7, for2);
                        for2 = new Object[2];
                        for2[0] = "6. ACÉMILA";
                        for2[1] = captCabecera.getEstado4().equals("6");
                        lstCheckFor2.add(8, for2);
                        for2 = new Object[2];
                        for2[0] = "7. OTRO TRANSPORTE";
                        for2[1] = captCabecera.getEstado4().equals("7");
                        lstCheckFor2.add(9, for2);
                    } else {
                        lstCheckFor2 = new ArrayList<>();
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void exportarSTATA(String idTab) {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            OutputStream out;
            Integer numPestaña = 0;

            for (int m = 0; m < tabViewInec.size(); m++) {
                //System.err.println("1>> " + idTab);
                //System.err.println("2>> " + tabViewInec.get(m).getIdTab());
                if (idTab.equals(tabViewInec.get(m).getIdTab())) {
                    numPestaña = m;
                }
            }
            File file = new File(idReporte.getNombre().replace('.', '_').replace(" ", "") + "_" + horaActual + "_" + numPestaña + ".txt");
            // Set the response type and specify the boundary string
            response.setContentType("multipart/x-mixed-replace;boundary=END");
            // Set the content type based on the file type you need to download
            response.setContentType("application/octet-stream");
            //response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

            String CONTENTS = "Lorem ipsum";
            final ByteBuffer buffer = ByteBuffer.wrap(CONTENTS.getBytes());

            out = response.getOutputStream();

            int bytes = 0;
            try (FileChannel channel = ((FileOutputStream) out).getChannel()) {
                bytes = writeToChannel(channel, buffer);
            } finally {
                if (!Objects.isNull(out)) {
                    out.close();

                }
            }

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private int writeToChannel(final FileChannel channel, final ByteBuffer buffer) throws IOException {
        int bytes = 0;
        while (buffer.hasRemaining()) {
            bytes += channel.write(buffer);
        }

        return bytes;
    }

    public void cargarReporte() {
        try {
            mapSeleccionFiltroReporte = new LinkedHashMap<>();
            mapSeleccionFiltroReporteObj = new LinkedHashMap<>();
            idReporte = null;
            mapFiltroReporte = new LinkedHashMap<>();
            mapNombreFiltroReporte = new LinkedHashMap<>();
            mapTipoFiltroReporte = new LinkedHashMap<>();
            mapFiltroReporteObj = new LinkedHashMap<>();
            mapDisabledCheck = new LinkedHashMap<>();
            mapCheckParam = new LinkedHashMap<>();

            if (usuarioControlador.getAliasReporte() != null) {
                String aliasRepo = usuarioControlador.getAliasReporte();
                idReporte = baseControlador.getRepoReporteServicioRemote().consultaReportePorAlias(aliasRepo);
            }
            if (idReporte != null) {
                RepoFiltro repoFiltro = baseControlador.getRepoFiltroServicioRemote().buscarPorCodigo(idReporte.getCodFiltro().getIdFiltro());
                if (repoFiltro.getCatalogo() != null) {
                    String[] parametrosCtlgo = repoFiltro.getCatalogo().split("\\-")[0].split("\\|");
                    String[] tipoCatalogoCtlgo = repoFiltro.getCatalogo().split("\\-")[1].split("\\|");
                    String[] nombreFiltrosCtlgo = repoFiltro.getCatalogo().split("\\-")[2].split("\\|");
                    String[] tipoFiltrosCtlgo = null;
                    if (repoFiltro.getCatalogo().split("\\-").length == 4) {
                        tipoFiltrosCtlgo = repoFiltro.getCatalogo().split("\\-")[3].split("\\|");
                    }
                    for (int l = 0; l < tipoCatalogoCtlgo.length; l++) {
                        String tCatalogoAlias = tipoCatalogoCtlgo[l];
                        //salirAFor1:
                        //for (int m = 0; m < parametrosCtlgo.length; m++) {
                        String param = parametrosCtlgo[l];
                        //for (int n = 0; n < nombreFiltrosCtlgo.length; n++) {
                        String filtro = nombreFiltrosCtlgo[l];
                        String tipoFiltro = null;
                        if (tipoFiltrosCtlgo != null) {
                            tipoFiltro = tipoFiltrosCtlgo[l];
                        }
                        //if (l == m && m == n) {
                        List<MetCatalogo> lstMetCatalogo;
                        if (tCatalogoAlias.equals("CAT_DPA_PROV") || tCatalogoAlias.equals("CAT_DPA_CANTON") || tCatalogoAlias.equals("CAT_DPA_SEDE_OP") || tCatalogoAlias.equals("CAT_DPA_PARR")) {
                            lstMetCatalogo = new ArrayList<>();
                        } else {
                            lstMetCatalogo = baseControlador.getMetCatalogoServicioRemote().listarCatalogoXAlias(tCatalogoAlias);
                        }
                        if (lstMetCatalogo != null) {
                            if (!lstMetCatalogo.isEmpty()) {
                                if (tCatalogoAlias.equals("CAT_COOR_ZONAL")) {
                                    segUsuarioActual = usuarioControlador.getUsuarioActual();
                                    if (usuarioTienePermiso("PAG_LST_TODO")) {
                                    } else {
                                        // lista la zonal del usuario logueado
                                        MetCatalogo zonalActual = new MetCatalogo();
                                        if (segUsuarioActual.getCodPersonal().getCodDpa() != null) {
                                            zonalActual = segUsuarioActual.getCodPersonal().getCodDpa().getCodPadre2();
                                        }
                                        lstMetCatalogo = new ArrayList<>();
                                        lstMetCatalogo.add(zonalActual);
                                    }
                                }
                            }
                        }
                        mapFiltroReporte.put(param, lstMetCatalogo);
                        mapNombreFiltroReporte.put(param, filtro);
                        if (tipoFiltro != null) {
                            mapTipoFiltroReporte.put(param, tipoFiltro);
                            //break salirAFor1;
                        }
                        //}
                        //}
                        //}
                    }
                }
                if (repoFiltro.getSql() != null) {
                    String[] parametrosQuery;
                    parametrosQuery = repoFiltro.getSql().split("\\-")[0].split("\\|");
                    String tipoCatalogoQuery = repoFiltro.getSql().split("\\-")[1];
                    String[] nombreFiltrosQuery = repoFiltro.getSql().split("\\-")[2].split("\\|");

                    Map<String, Map<String, Object>> mapFiltro = devolverMapa(tipoCatalogoQuery);
                    int i = 0;
                    for (Map.Entry<String, Map<String, Object>> json1 : mapFiltro.entrySet()) {
                        List<Object> lstFiltro12 = new ArrayList<>();
                        Map<String, Object> jsonValue = json1.getValue();
                        if (json1.getKey().equals("obj")) {
                            for (Map.Entry<String, Object> param3 : jsonValue.entrySet()) {
                                Map<String, Object> param4 = (Map<String, Object>) param3.getValue();
                                lstFiltro12.add(Arrays.asList(param4));
                                mapFiltroReporteObj.put(param3.getKey(), param4);

                                for (String nFiltros : nombreFiltrosQuery) {
                                    Arrays.asList(nombreFiltrosQuery).indexOf(nFiltros);
                                    if (i == Arrays.asList(nombreFiltrosQuery).indexOf(nFiltros)) {
                                        mapNombreFiltroReporte.put(param3.getKey(), nFiltros);
                                        break;
                                    }
                                }
                                i++;
                            }
                        } else {
                            for (Map.Entry<String, Object> param3 : jsonValue.entrySet()) {
                                //List<Object[]> lstFiltro = baseControlador.getRepoFiltroServicioRemote().listarEjecutarConsulta(String.valueOf(param3.getValue()));
                                Map<String, Object> param4 = new LinkedHashMap();
                                List<Object[]> lstFiltro = new ArrayList<>();
                                if (!param3.getValue().toString().isEmpty()) {
                                    if (!param3.getValue().toString().contains(":")) {
                                        lstFiltro = baseControlador.getRepoFiltroServicioRemote().listarEjecutarConsulta(String.valueOf(param3.getValue()));
                                    } else {
                                        mapFiltrosObjRes.put(param3.getKey(), param3.getValue().toString());
                                        mapFiltrosObjResRes.put(param3.getKey(), param3.getValue().toString());
                                    }
                                }
                                if (lstFiltro != null) {
                                    if (lstFiltro.isEmpty()) {
                                        mapFiltroReporteObj.put(String.valueOf(param3.getKey()), param4);
                                    } else if (lstFiltro.get(0).length == 2) {
                                        for (Object[] objects : lstFiltro) {
                                            param4.put(String.valueOf(objects[0]), objects[1]); 
                                            //param4 = lstFiltro.stream().collect(Collectors.toMap(String.valueOf(objects[0]), objects[1]));
                                        }
                                        mapFiltroReporteObj.put(String.valueOf(param3.getKey()), param4);
                                    }
                                }
                                for (String nFiltros : nombreFiltrosQuery) {
                                    Arrays.asList(nombreFiltrosQuery).indexOf(nFiltros);
                                    if (i == Arrays.asList(nombreFiltrosQuery).indexOf(nFiltros)) {
                                        mapNombreFiltroReporte.put(param3.getKey(), nFiltros);
                                        break;
                                    }
                                }
                                i++;
                            }
                        }
                    }
                }
                if (!mapFiltroReporte.isEmpty()) {
                    mapFiltroReporte.entrySet().forEach((filtroReporte) -> {
                        mapDisabledCheck.put(filtroReporte.getKey(), false);
                    });
                }
                if (!mapFiltroReporteObj.isEmpty()) {
                    mapFiltroReporteObj.entrySet().forEach((filtroReporte) -> {
                        mapDisabledCheck.put(filtroReporte.getKey(), false);
                    });
                }
                //System.err.println("mapFiltroReporte " + mapFiltroReporte.size());
                //verPanelReporte = true;
                usuarioControlador.setAliasReporte(null);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /*public void consultarReportes() {
        try {
            abrirPanelConsulta = true;

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }*/
    public void limpiarReporte() {
        try {
            tabViewInec = new ArrayList<>();
            mapSeleccionFiltroReporte = new LinkedHashMap<>();
            mapSeleccionFiltroReporteObj = new LinkedHashMap<>();
            mapCheckParam = new LinkedHashMap<>();
            if (!mapDisabledCheck.isEmpty()) {
                mapDisabledCheck.entrySet().forEach((param) -> {
                    mapDisabledCheck.replace(param.getKey(), false);
                });
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, Map<String, Object>> devolverMapa(String json) {
        try {
            Map<String, Map<String, Object>> listSelectItem;
            Gson prettyGson2 = new Gson();
            Type listOfTestObject = new TypeToken<Map<String, Map<String, Object>>>() {
            }.getType();
            listSelectItem = prettyGson2.fromJson(json, listOfTestObject);
            return listSelectItem;

        } catch (JsonSyntaxException e) {
            Logger.getLogger(convertJSON.class
                    .getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void actualizarFiltrosTodos(AjaxBehaviorEvent event) {
        try {
            //Object valorActual = event.getSource();
            /*String valorAnterior = event.getOldValue().toString();
            String valorNuevo = event.getNewValue().toString();*/

            MetCatalogo valorActual = new MetCatalogo();
            String idFiltro = event.getComponent().getClientId();
            String idFiltroId = event.getComponent().getId();
            RepoFiltro repoFiltro = baseControlador.getRepoFiltroServicioRemote().buscarPorCodigo(idReporte.getCodFiltro().getIdFiltro());

            String idParte0 = idFiltro.split(idFiltroId)[0];
            boolean existe = false;
            if (!mapIdTipoFiltros.isEmpty()) {
                for (Entry<String, String> entry : mapIdTipoFiltros.entrySet()) {
                    existe = entry.getValue().split(idParte0)[1].equals(idFiltroId);
                }
                if (!existe) {
                    mapIdTipoFiltros.put(idFiltro.split("_")[1], idFiltro);
                }
            } else if (!existe) {
                mapIdTipoFiltros.put(idFiltro.split("_")[1], idFiltro);
            }

            Object param = event.getComponent().getId().split("\\_")[1];

            if (repoFiltro.getCatalogo() != null) {
                String[] tipoCatalogoCtlgo = repoFiltro.getCatalogo().split("\\-")[1].split("\\|");
                if (event.getComponent().getId().split("\\_")[0].equals("idSelOneMCatalog")) {
                    valorActual = (MetCatalogo) ((UIOutput) event.getSource()).getValue();

                    int i = 0;
                    for (Entry<String, List<MetCatalogo>> entryMapFiltro1 : mapFiltroReporte.entrySet()) {
                        i++;
                        if (entryMapFiltro1.getKey().equals(":" + param)) {
                            int j = 0;
                            if (i < tipoCatalogoCtlgo.length) {
                                for (Entry<String, String> entryMapFiltro2 : mapNombreFiltroReporte.entrySet()) {
                                    String replace = entryMapFiltro2.getKey();
                                    if (i == j) {
                                        List<MetCatalogo> lstMetCatalogoFiltroAut = new ArrayList<>();
                                        String tCatalogoAlias = tipoCatalogoCtlgo[i];
                                        if (i == 1) {
                                            if (usuarioTienePermiso("PAG_FIL_PROV")) {
                                                MetCatalogo provActual = new MetCatalogo();
                                                if (segUsuarioActual.getCodPersonal().getCodDpa() != null) {
                                                    provActual = segUsuarioActual.getCodPersonal().getCodDpa().getCodPadre1().getCodPadre1();
                                                }
                                                lstMetCatalogoFiltroAut.add(provActual);
                                            } else {
                                                lstMetCatalogoFiltroAut = baseControlador.getMetCatalogoServicioRemote().listarEjecutarConsulta("listarCatalogoPorPadre2", Arrays.asList(valorActual, tCatalogoAlias));
                                            }
                                        } else {
                                            if (usuarioTienePermiso("PAG_FIL_CANT") && replace.equals(":canton")) {
                                                MetCatalogo cantonActual = new MetCatalogo();
                                                if (segUsuarioActual.getCodPersonal().getCodDpa() != null) {
                                                    cantonActual = segUsuarioActual.getCodPersonal().getCodDpa().getCodPadre1();
                                                }
                                                lstMetCatalogoFiltroAut.add(cantonActual);
                                            } else if (usuarioTienePermiso("PAG_FIL_SEDE_OP") && replace.equals(":sede")) {
                                                MetCatalogo sedeActual = new MetCatalogo();
                                                if (segUsuarioActual.getCodPersonal().getCodDpa() != null) {
                                                    sedeActual = segUsuarioActual.getCodPersonal().getCodDpa().getCodPadre3();
                                                }
                                                lstMetCatalogoFiltroAut.add(sedeActual);
                                            } else if (usuarioTienePermiso("PAG_FIL_PARROQ") && replace.equals(":parr")) {
                                                MetCatalogo parroquiaActual = new MetCatalogo();
                                                if (segUsuarioActual.getCodPersonal().getCodDpa() != null) {
                                                    parroquiaActual = segUsuarioActual.getCodPersonal().getCodDpa();
                                                }
                                                lstMetCatalogoFiltroAut.add(parroquiaActual);
                                            } else if (tCatalogoAlias.equals("CAT_DPA_PARR") && tipoCatalogoCtlgo[i - 1].equals("CAT_DPA_SEDE_OP")) {
                                                lstMetCatalogoFiltroAut = baseControlador.getMetCatalogoServicioRemote().listarEjecutarConsulta("listarCatalogoPorPadre3", Arrays.asList(valorActual, tCatalogoAlias));
                                            } else {
                                                lstMetCatalogoFiltroAut = baseControlador.getMetCatalogoServicioRemote().listarEjecutarConsulta("listarCatalogoPorPadre1", Arrays.asList(valorActual, tCatalogoAlias));
                                            }
                                        }
                                        if (lstMetCatalogoFiltroAut.isEmpty() && tCatalogoAlias.equals("CAT_DPA_SEDE_OP")) {
                                            if (usuarioTienePermiso("PAG_FIL_PARROQ")) {
                                                MetCatalogo parroquiaActual = new MetCatalogo();
                                                if (segUsuarioActual.getCodPersonal().getCodDpa() != null) {
                                                    parroquiaActual = segUsuarioActual.getCodPersonal().getCodDpa();
                                                }
                                                lstMetCatalogoFiltroAut.add(parroquiaActual);
                                            } else {
                                                lstMetCatalogoFiltroAut.addAll(baseControlador.getMetCatalogoServicioRemote().listarEjecutarConsulta("listarCatalogoPorPadre1", Arrays.asList(valorActual, "CAT_DPA_PARR")));
                                            }
                                            replace = ":parr";
                                        }
                                        if (!lstMetCatalogoFiltroAut.isEmpty()) {
                                            mapFiltroReporte.replace(replace, lstMetCatalogoFiltroAut);
                                            //RequestContext.getCurrentInstance().update(idFiltro.split("_")[0] );
                                            PrimeFaces.current().ajax().update(idFiltro.split(idFiltroId)[0] + "idPnlGripFiltrosRepo");
                                            

                                            break;
                                        }
                                    }
                                    j++;
                                }
                            }
                        }
                    }
                }
            }
            mapSeleccionFiltroReporte.entrySet().forEach((filtroSeleccionadoCtlg) -> {
                if (filtroSeleccionadoCtlg.getValue() != null) {
                    if (filtroSeleccionadoCtlg.getValue().getIdCatalogo() != null) {
                        mapSeleccionFiltroReporteRes.put(filtroSeleccionadoCtlg.getKey(), filtroSeleccionadoCtlg.getValue().getIdCatalogo());
                    }
                } else {
                    mapSeleccionFiltroReporteRes.replace(filtroSeleccionadoCtlg.getKey(), filtroSeleccionadoCtlg.getValue());
                }
            });
            mapSeleccionFiltroReporteObj.entrySet().forEach((filtroSeleccionadoObj) -> {
                mapSeleccionFiltroReporteRes.put(filtroSeleccionadoObj.getKey(), filtroSeleccionadoObj.getValue());
            });

            if (!mapFiltrosObjResRes.isEmpty()) {
                for (Entry<String, String> filtroObjRes : mapFiltrosObjRes.entrySet()) {
                    for (Entry<String, String> param3 : mapFiltrosObjResRes.entrySet()) {
                        if (param3.getKey().equals(filtroObjRes.getKey())) {
                            mapFiltrosObjRes.replace(filtroObjRes.getKey(), param3.getValue());
                            break;
                        }
                    }
                }
            }

            if (!mapFiltrosObjRes.isEmpty()) {
                for (Entry<String, Object> filtroSeleccionadoCtlgRes : mapSeleccionFiltroReporteRes.entrySet()) {
                    if (filtroSeleccionadoCtlgRes.getValue() != null) {
                        //if (param1.getKey().equals(":" + param)) {
                        for (Entry<String, String> filtroObjRes : mapFiltrosObjRes.entrySet()) {
                            if (filtroObjRes.getValue().contains(filtroSeleccionadoCtlgRes.getKey())) {
                                String replace = filtroObjRes.getValue().replace(filtroSeleccionadoCtlgRes.getKey(), filtroSeleccionadoCtlgRes.getValue().toString());
                                mapFiltrosObjRes.replace(filtroObjRes.getKey(), replace);
                                //break;
                            }
                        }
                        //}
                    }
                }
            }
            for (Entry<String, String> filtroObjRes : mapFiltrosObjRes.entrySet()) {
                Map<String, Object> mapFiltroObj = new LinkedHashMap();
                List<Object[]> lstFiltro;
                if (!filtroObjRes.getValue().contains(":")) {
                    lstFiltro = baseControlador.getRepoFiltroServicioRemote().listarEjecutarConsulta(filtroObjRes.getValue());
                    if (lstFiltro != null) {
                        if (!lstFiltro.isEmpty()) {
                            for (Object[] objects : lstFiltro) {
                                mapFiltroObj.put(String.valueOf(objects[0]), objects[1]);
                            }
                            /*if (!mapIdTipoFiltros.isEmpty()) {
                                for (Entry<String, String> entry : mapIdTipoFiltros.entrySet()) {
                                    boolean actualiza = false;
                                    for (Entry<String, Object> select : mapSeleccionFiltroReporteObj.entrySet()) {
                                        if (entry.getKey().equals(select.getKey().split(":")[1])) {
                                            actualiza = true;
                                            break;
                                        }
                                    }

                                    if (actualiza) {
                                        mapFiltroReporteObj.put(param1.getKey(), param2);
                                        RequestContext.getCurrentInstance().update(entry.getValue().split("_")[0] + "_" + param1.getKey().split(":")[1]);
                                        break;
                                    }
                                }
                            }*/
                            if (!mapSeleccionFiltroReporteObj.isEmpty()) {
                                boolean actualiza = false;
                                for (Entry<String, Object> select : mapSeleccionFiltroReporteObj.entrySet()) {
                                    if (select.getValue() == null) {
                                        if (filtroObjRes.getKey().equals(select.getKey())) {
                                            actualiza = true;
                                            break;
                                        }
                                    }
                                }

                                if (actualiza) {
                                    mapFiltroReporteObj.put(filtroObjRes.getKey(), mapFiltroObj);
                                    PrimeFaces.current().ajax().update("frm:idReporteGeneralPanel:inecFiltro:idSelOneMObj_" + filtroObjRes.getKey().split(":")[1]);
                                    
 
                                    
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            for (Entry<String, Object> filtroSeleccionadoCtlgRes : mapSeleccionFiltroReporteRes.entrySet()) {
                for (Entry<String, Boolean> param2 : mapDisabledCheck.entrySet()) {
                    if (filtroSeleccionadoCtlgRes.getValue() != null) {
                        if (filtroSeleccionadoCtlgRes.getKey().equals(param2.getKey())) {
                            mapDisabledCheck.replace(filtroSeleccionadoCtlgRes.getKey(), true);
                            break;
                        }
                    } else {
                        mapDisabledCheck.replace(filtroSeleccionadoCtlgRes.getKey(), false);
                        break;
                    }
                }
            }
            //RequestContext.getCurrentInstance().update("frmAsig:inecFiltro:idPnlGripFiltrosRepo");
            //RequestContext.getCurrentInstance().update("frmAsig");
            PrimeFaces.current().ajax().update(idFiltro.split(idFiltroId)[0] + "idPnlGripFiltrosRepo");
            

            
            //System.err.println("columnasSeleccionadas " + param);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean usuarioTienePermiso(String aliasPermiso) {
        boolean b = false;
        for (String aliasPer : permisosBoton.keySet()) {
            if (aliasPer.equals(aliasPermiso)) {
                b = true;
                break;
            }
        }
        return b;
    }

    public void obtenerReporteJasper() {
        try {

            int numPar = 0;
            String parametros = "";
            String paramCheck = "";
            String pars1 = null;
            Map parametrosJas = new HashMap();
            String nomReporteJasper = "";
            showAlert1 = false;
            showAlert2 = false;

            RepoFiltro repoFiltro = baseControlador.getRepoFiltroServicioRemote().buscarPorCodigo(idReporte.getCodFiltro().getIdFiltro());
            if (!mapCheckParam.isEmpty()) {
                for (Entry<String, Boolean> param : mapCheckParam.entrySet()) {
                    if (param.getValue().equals(true)) {
                        paramCheck = param.getKey();
                    }
                }
            }

            if (!paramCheck.isEmpty()) {
                if (("idzonal").contains(paramCheck.split("\\:")[1])) {
                    pars1 = ":idzonal";
                    nomReporteJasper = "rGlobal";
                } else if (paramCheck.split("\\:")[1].equals("idprov")) {
                    pars1 = ":idzonal|:idprov";
                    nomReporteJasper = "rGlobalProv";
                } else if (paramCheck.split("\\:")[1].equals("idcton")) {
                    pars1 = ":idzonal|:idprov|:idcton";
                    nomReporteJasper = "rGlobalCanton";
                } else if (paramCheck.split("\\:")[1].equals("idparr")) {
                    pars1 = ":idzonal|:idprov|:idcton|:idparr";
                    nomReporteJasper = "rGlobalParr";
                } else if (paramCheck.split("\\:")[1].equals("zn")) {
                    pars1 = ":idzonal|:idprov|:idcton|:idparr|:zn";
                    nomReporteJasper = "rGlobalZona";
                } else if (paramCheck.split("\\:")[1].equals("stor")) {
                    pars1 = ":idzonal|:idprov|:idcton|:idparr|:zn|:stor";
                    nomReporteJasper = "rGlobalSector";
                }

                String[] pars = pars1.split("\\|");
                for (String par : pars) {
                    numPar++;
                    if (numPar > 1) {
                        parametros = parametros + "|";
                    }
                    parametros = parametros + par;
                    if (!par.contains("=")) {
                        if (repoFiltro.getSql() != null) {
                            for (Map.Entry<String, Object> catalogoFiltro : mapSeleccionFiltroReporteObj.entrySet()) {
                                if (catalogoFiltro.getValue() == null) {
                                    if (catalogoFiltro.getKey().split("\\:")[1].equals("equipo")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("fasest")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("jorda")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("jorda1")
                                            || catalogoFiltro.getKey().split("\\:")[1].equals("jorda2")) {
                                        catalogoFiltro.setValue("0");
                                    } else {
                                        catalogoFiltro.setValue("¿");
                                    }
                                }
                                if (catalogoFiltro.getKey().equals(par)) {
                                    for (Map.Entry<String, Map<String, Object>> catalogoFiltro1 : mapFiltroReporteObj.entrySet()) {
                                        if (catalogoFiltro1.getKey().equals(par)) {
                                            Map<String, Object> catalogoFiltro2 = catalogoFiltro1.getValue();
                                            if (catalogoFiltro.getValue().getClass().getSimpleName().equals("Date")) {
                                                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                String fecha = formatter.format(catalogoFiltro.getValue());
                                                parametros = parametros + "=" + "'" + fecha + "'";
                                                parametrosJas.put(par.split("\\:")[1], fecha);
                                            } else {
                                                for (Map.Entry<String, Object> catalogoFiltro3 : catalogoFiltro2.entrySet()) {
                                                    if (catalogoFiltro3.getValue() == null) {
                                                        catalogoFiltro3.setValue("¿");
                                                    }
                                                    if (catalogoFiltro.getValue().equals("¿") && catalogoFiltro3.getValue().equals("¿")) {
                                                        parametros = parametros + "=" + "'¿'";
                                                        parametrosJas.put(par.split("\\:")[1], "");
                                                        break;
                                                    } else if (catalogoFiltro.getValue().equals("¿")) {
                                                        parametros = parametros + "=" + "'¿'";
                                                        parametrosJas.put(par.split("\\:")[1], "");
                                                        break;
                                                    } else if (catalogoFiltro1.getKey().equals(catalogoFiltro.getKey())) {
                                                        parametros = parametros + "=" + "'" + catalogoFiltro.getValue() + "'";
                                                        parametrosJas.put(par.split("\\:")[1], catalogoFiltro.getValue());
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (repoFiltro.getCatalogo() != null) {
                            for (Map.Entry<String, MetCatalogo> catalogoFiltro : mapSeleccionFiltroReporte.entrySet()) {
                                if (catalogoFiltro.getValue() == null) {
                                    MetCatalogo metCatalogo = new MetCatalogo();
                                    metCatalogo.setNombre("¿");
                                    metCatalogo.setValor("¿");
                                    catalogoFiltro.setValue(metCatalogo);
                                }
                                if (catalogoFiltro.getKey().equals(par)) {
                                    //System.out.println("parm2 " + parametros);                                    
                                    if (catalogoFiltro.getValue().getIdCatalogo() != null) {
                                        MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(catalogoFiltro.getValue().getIdCatalogo());
                                        if (!mapTipoFiltroReporte.isEmpty()) {
                                            for (Map.Entry<String, String> tipoFiltroAdd : mapTipoFiltroReporte.entrySet()) {
                                                if (tipoFiltroAdd.getKey().equals(par)) {
                                                    switch (tipoFiltroAdd.getValue()) {
                                                        case "idCatalogo":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getIdCatalogo() + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getIdCatalogo());
                                                            break;
                                                        case "codTipoCatalogo":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getCodTipoCatalogo() + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getCodTipoCatalogo());
                                                            break;
                                                        case "nombre":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getNombre() + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getNombre());
                                                            break;
                                                        case "alias":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getAlias() + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getAlias());
                                                            break;
                                                        case "valor":
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor() + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getValor());
                                                            break;
                                                        default:
                                                            break;
                                                    }

                                                    if (tipoFiltroAdd.getValue().equals("ninguno")) {

                                                        if (metCatalogo.getNivel() == 1 && catalogoFiltro.getKey().split("\\:")[1].equals("prov")) {
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor() + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getValor());
                                                        } else if (metCatalogo.getNivel() == 2 && catalogoFiltro.getKey().split("\\:")[1].equals("canton")) {
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor().substring(2) + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getValor().substring(2));
                                                        } else if (metCatalogo.getNivel() == 3 && catalogoFiltro.getKey().split("\\:")[1].equals("parr")) {
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor().substring(4) + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getValor().substring(4));
                                                        } else if (catalogoFiltro.getKey().split("\\:")[1].equals("idzonal")) {
                                                            //MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(catalogoFiltro.getValue().getIdCatalogo());
                                                            parametros = parametros + "=" + "'" + metCatalogo.getValor().substring(1) + "'";
                                                            parametrosJas.put(par.split("\\:")[1], metCatalogo.getValor().substring(1));
                                                        } else {
                                                            parametros = parametros + "=" + catalogoFiltro.getValue().getIdCatalogo();
                                                            parametrosJas.put(par.split("\\:")[1], catalogoFiltro.getValue().getIdCatalogo());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (!mapTipoFiltroReporte.isEmpty()) {
                                        for (Map.Entry<String, String> tipoFiltroAdd : mapTipoFiltroReporte.entrySet()) {
                                            if (tipoFiltroAdd.getKey().equals(par)) {
                                                switch (tipoFiltroAdd.getValue()) {
                                                    case "idCatalogo":
                                                        parametros = parametros + "=" + "'" + 0 + "'";
                                                        parametrosJas.put(par.split("\\:")[1], 0);
                                                        break;
                                                    case "codTipoCatalogo":
                                                        parametros = parametros + "=" + "'" + 0 + "'";
                                                        parametrosJas.put(par.split("\\:")[1], 0);
                                                        break;
                                                    case "nombre":
                                                        parametros = parametros + "=" + "";
                                                        parametrosJas.put(par.split("\\:")[1], "");
                                                        break;
                                                    case "alias":
                                                        parametros = parametros + "=" + "";
                                                        parametrosJas.put(par.split("\\:")[1], "");
                                                        break;
                                                    case "valor":
                                                        parametros = parametros + "=" + "";
                                                        parametrosJas.put(par.split("\\:")[1], "");
                                                        break;
                                                    case "ninguno":
                                                        if (catalogoFiltro.getKey().split("\\:")[1].equals("idzonal")) {
                                                            parametros = parametros + "=" + "'¿'";
                                                            parametrosJas.put(par.split("\\:")[1], "");
                                                        }
                                                        break;
                                                    default:
                                                        parametros = parametros + "=" + "'¿'";
                                                        parametrosJas.put(par.split("\\:")[1], "");
                                                        break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                //baseControlador.addErrorMessage("Debe seleccionar el casillero del reporte a descargar");
                showAlert1 = true;
            }

            System.out.println("parametros: " + parametros);

            if (!parametrosJas.isEmpty()) {
                reporteJasper(parametrosJas, nomReporteJasper);
            } else {
                //baseControlador.addErrorMessage("Error al seleccionar el reporte a descargar");
                showAlert2 = true;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

//----------Jasper
    public void reporteJasper(Map mapRJ, String nomReporteJasper) {
        try {
            COMPILAR_NOMBRE_ARCHIVO = nomReporteJasper;
            FacesContext fcontext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = fcontext.getExternalContext();
            externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());
            ServletContext context = (ServletContext) externalContext.getContext();

            compilarReporte(context, COMPILAR_DIR, COMPILAR_NOMBRE_ARCHIVO);
            File reportFile = new File(getJasperPathFichero(context, COMPILAR_DIR, COMPILAR_NOMBRE_ARCHIVO + ".jasper"));

            /*Iterator it = mapRJ.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.err.println(pair.getKey() + " = " + pair.getValue());
                //it.remove(); 
            }*/
            AdmBaseDatos paramBDD = baseControlador.getActualizaBDServicioRemote().recuperaParametros("sipe201812121220");

            Class.forName(paramBDD.getDriver()).newInstance();
            DriverManager.setLoginTimeout(0);//Evita que se cierre la conexion

            String cadena = baseControlador.getActualizaBDServicioRemote().crearURL(paramBDD.getRdbms(), paramBDD.getIp(), paramBDD.getPuerto(), paramBDD.getNombrebdd());
            conn = DriverManager.getConnection(cadena, paramBDD.getUsuario(), paramBDD.getPassword());

            System.err.println(">>>>bdd " + conn.getCatalog());
            System.err.println(">>>>path " + reportFile.getPath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), mapRJ, conn);
            exportarReporteAsExcel(jasperPrint);

            if (conn != null) {
                conn.close();
            }
        } catch (JRException | SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public boolean compilarReporte(ServletContext context, String compileDir, String filename) {
        String jasperFileName = context.getRealPath(compileDir + filename + ".jasper");
        File jasperFile = new File(jasperFileName);

        //System.err.println(">>>>3 " + jasperFile.getPath());
        if (jasperFile.exists()) {
            return true;
        }
        try {
            //System.err.println(">>>> Ingresa a compilar ");
            setCompilarDirTemp(context, compileDir);

            String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
            JasperCompileManager.compileReportToFile(xmlFileName, jasperFileName);

            return true;
        } catch (JRException e) {
            LOGGER.log(Level.SEVERE, null, e);
            return false;
        }
    }

    private void setCompilarDirTemp(ServletContext context, String uri) {
        System.setProperty("jasper.reports.compile.temp", context.getRealPath(uri));
    }

    public String getJasperPathFichero(ServletContext context, String compileDir, String jasperFile) {
        return context.getRealPath(compileDir + jasperFile);
    }

    public void exportarReporteAsExcel(JasperPrint jasperPrint) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            horaActual = formatter.format(java.util.Calendar.getInstance().getTime()).replace(" ", "_");

            FacesContext context;
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addHeader("Content-disposition",
                        "attachment;filename=" + COMPILAR_NOMBRE_ARCHIVO + horaActual + ".xls");
                response.setContentType("application/vnd.ms-excel");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
                //exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                //exporterXLS.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("reportPrueba.xls"));
                SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                configuration.setOnePagePerSheet(false);
                configuration.setCollapseRowSpan(false);
                configuration.setRemoveEmptySpaceBetweenRows(true);
                configuration.setWhitePageBackground(false);
                exporterXLS.setConfiguration(configuration);
                exporterXLS.exportReport();
                response.getOutputStream().write(output.toByteArray());
            }
            context.responseComplete();

        } catch (JRException | IOException e) {
            LOGGER.log(Level.SEVERE, null, e);
            baseControlador.addErrorMessage("No se puede descargar el archivo");
        }
    }

    //-------------fin
//</editor-fold>
}
