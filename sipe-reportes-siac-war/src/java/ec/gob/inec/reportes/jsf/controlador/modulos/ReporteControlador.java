/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.jsf.controlador.modulos;

/*import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;*/
import com.google.gson.Gson;
import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.reportes.ejb.entidades.RepoColumna;
import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import ec.gob.inec.reportes.ejb.entidades.RepoSubreporte;
import ec.gob.inec.reportes.jsf.clases.TabViewTab;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import java.util.*;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
//import org.primefaces.context.RequestContext;
import org.primefaces.PrimeFaces;
import ec.gob.inec.presentacion.clases.reportes.excel.ExcelExporter;
import javax.servlet.http.HttpSession;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.faces.bean.ViewScoped;

import ec.gob.inec.reportes.jsf.sql.sentencias.Clause;
import ec.gob.inec.reportes.jsf.sql.sentencias.Column;
import ec.gob.inec.reportes.jsf.sql.sentencias.Select;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.reportes.ejb.entidades.RepoFiltro;
import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import java.util.Map.Entry;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabCloseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.inec.presentacion.clases.reportes.excel.ExcelExporterCarto;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author jaraujo
 */
@ManagedBean
@ViewScoped
public class ReporteControlador {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">    
    private static final Logger LOGGER = Logger.getLogger(ReporteControlador.class.getName());
    private static String[] args1 = new String[1];
    List<Object[]> resumenFiltro;
    Map<String, Object> filtros;
    private boolean verFiltroPeriodo;
    private boolean verFiltroZonal;
    private boolean verFiltroUsuario;
    private boolean verFiltroIndicador;
    private boolean verFiltroPeriodoIni;
    private boolean verFiltroPeriodoFin;
    private List<TabViewTab> tabViewInec;
    private RepoReporte idReporte;
    ExcelExporter exportExcel;
    ExcelExporterCarto exportExcelCartografia;
    String horaActual;
    public HttpSession session;
    private boolean expPdf = false;
    private List<String> lisTab;
    private String columna;
    private List<SelectItem> categorias;
    private String nomTabla;
    List<SelectItem> itemTabla;
    private TreeNode treeNode;
    private TreeNode selectedNode;
    Object object[];
    private List<String> lstNomTablas;
    private List<String> lstNomColumnas;
    private List<String> lstNomColumnasSeleccionadas;
    private boolean renderSlcTabla, renderTipoCtl;
    TreeNode nivel0;
    TreeNode nivel1;
    TreeNode nivel2;
    private Integer nivelNodo;
    List<String> padres = new ArrayList<>();
    List<Object[]> objPadres = new ArrayList<>();
    List<Object[]> joins = new ArrayList<>();
    private String esquema;
    List<Object[]> lstReferencias;
    String query;
    private String nombreFiltroParametro;
    private MetCatalogo seleccionLstTipoDatos;
    private Map<String, Object[]> lstFiltrosIngresados;
    private List<MetCatalogo> lstTipoDatos;
    private List<MetCatalogo> lstOperadoresLogicos;
    private List<MetCatalogo> lstOperadoresRelacionales;
    private String columnaAsignada;
    private MetCatalogo opRelacionalSeleccionado;
    private String parametroFiltroSeleccionado;
    private MetCatalogo opLogicoSeleccionado;
    private Select sql;
    private List<MetTipoCatalogo> lstTipoCatalogo;
    private MetTipoCatalogo seleccionLstTipoCatalogo;
    List<MetTipoCatalogo> lstContieneTipoCatalogo;
    List<MetCatalogo> lstMetadatoFocus;
    private RepoReporte repoReporteNuevo;
    private RepoSubreporte repoSubreporteNuevo;
    private RepoProcedimiento repoProcedimientoNuevo;
    private RepoColumna repoColumnaNuevo;
    private RepoFiltro repoFiltroNuevo;
    private List<RepoFiltro> lstRepoFiltros;
    private int contarFiltrosAsignados;
    private boolean renderOpLogico;
    private String conexion;
    private String[] parametrosDeFiltrosCtlgo;
    private String filtroSubreporte;
    private String[] nomFiltros;
    private String[] tipoFiltros;
    private boolean verFiltroCatalogo;
    //private Map<String, List<MetCatalogo>> mapFiltroReporte;
    //private Map<String, MetCatalogo> mapSeleccionFiltroReporte;
    private String nombreFiltro;
    //private Map<String, String> mapNombreFiltroReporte;
    private String[] filtroTipoCatalogo;
    private boolean addNuevoReporte;
    private List<RepoReporte> lstRepoCreados;
    private boolean verPanelReporte;
    private int ordenColumna;
    private int anchoColumna;
    private String alineacionColumna;
    private boolean filtroColumna;
    private boolean ordenarTablaPorColumna;
    private String textAreaColumna;
    private List<Object[]> lstObjColumnas;
    //private Map<String, Map<String, Object>> mapFiltroReporteObj;
    //private Map<String, Object> mapSeleccionFiltroReporteObj;
    private Map<String, String> mapTblSeleccionadas;
    private String sqlReporteMensaje;
    private List<AdmBaseDatos> lstSistemas;
    private Map<String, List<Object[]>> mapSisSchema;
    private Map<String, Object[]> lstFiltrosSQLTabla;
    private String queryFiltro;
    private Map<String, String> lstFiltrosParaAsignar;
    private String[] parametrosDeFiltrosQuery;
    private String filtrosQuery;
    private String[] nomFiltrosQuery;
    private boolean required;
    private String activarAccordion;
    private boolean activarquery, activarseleccion;
    private String creatualizaQuery;
    private String esquemaOriginal;
    private String[] arregloColumnas;
    private List<Object[]> lstCheckFor2;
    private String nomAgrupaCol;
    private Integer numAgrupaCol, anchoAgrupaCol;
    private List<Object[]> lstAgrupObjColumnas;
    private List<String> lstAgrupaColumnasSeleccionadas;
    private List<String> lstColumnasSeleccionadas;
    private List<MetCatalogo> lstEstilosCSSColumna;
    private String funcion, nomFuncion;
    private String tipoFiltro;

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

    public boolean isVerFiltroPeriodo() {
        return verFiltroPeriodo;
    }

    public void setVerFiltroPeriodo(boolean verFiltroPeriodo) {
        this.verFiltroPeriodo = verFiltroPeriodo;
    }

    public boolean isVerFiltroZonal() {
        return verFiltroZonal;
    }

    public void setVerFiltroZonal(boolean verFiltroZonal) {
        this.verFiltroZonal = verFiltroZonal;
    }

    public boolean isVerFiltroUsuario() {
        return verFiltroUsuario;
    }

    public void setVerFiltroUsuario(boolean verFiltroUsuario) {
        this.verFiltroUsuario = verFiltroUsuario;
    }

    public boolean isVerFiltroIndicador() {
        return verFiltroIndicador;
    }

    public void setVerFiltroIndicador(boolean verFiltroIndicador) {
        this.verFiltroIndicador = verFiltroIndicador;
    }

    public boolean isVerFiltroPeriodoIni() {
        return verFiltroPeriodoIni;
    }

    public void setVerFiltroPeriodoIni(boolean verFiltroPeriodoIni) {
        this.verFiltroPeriodoIni = verFiltroPeriodoIni;
    }

    public boolean isVerFiltroPeriodoFin() {
        return verFiltroPeriodoFin;
    }

    public void setVerFiltroPeriodoFin(boolean verFiltroPeriodoFin) {
        this.verFiltroPeriodoFin = verFiltroPeriodoFin;
    }

    public List<String> getLisTab() {
        return lisTab;
    }

    public void setLisTab(List<String> lisTab) {
        this.lisTab = lisTab;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public List<SelectItem> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }

    public BaseControlador getBaseControlador() {
        return baseControlador;
    }

    public void setBaseControlador(BaseControlador baseControlador) {
        this.baseControlador = baseControlador;
    }

    public String getNomTabla() {
        return nomTabla;
    }

    public void setNomTabla(String nomTabla) {
        this.nomTabla = nomTabla;
    }

    public List<SelectItem> getItemTabla() {
        return itemTabla;
    }

    public void setItemTabla(List<SelectItem> itemTabla) {
        this.itemTabla = itemTabla;
    }

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Object[] getObject() {
        return object;
    }

    public void setObject(Object[] object) {
        this.object = object;
    }

    public List<String> getLstNomTablas() {
        return lstNomTablas;
    }

    public void setLstNomTablas(List<String> lstNomTablas) {
        this.lstNomTablas = lstNomTablas;
    }

    public List<String> getLstNomColumnas() {
        return lstNomColumnas;
    }

    public void setLstNomColumnas(List<String> lstNomColumnas) {
        this.lstNomColumnas = lstNomColumnas;
    }

    public boolean isRenderSlcTabla() {
        return renderSlcTabla;
    }

    public void setRenderSlcTabla(boolean renderSlcTabla) {
        this.renderSlcTabla = renderSlcTabla;
    }

    public boolean isRenderTipoCtl() {
        return renderTipoCtl;
    }

    public void setRenderTipoCtl(boolean renderTipoCtl) {
        this.renderTipoCtl = renderTipoCtl;
    }

    public Integer getNivelNodo() {
        return nivelNodo;
    }

    public void setNivelNodo(Integer nivelNodo) {
        this.nivelNodo = nivelNodo;
    }

    public List<String> getLstNomColumnasSeleccionadas() {
        return lstNomColumnasSeleccionadas;
    }

    public void setLstNomColumnasSeleccionadas(List<String> lstNomColumnasSeleccionadas) {
        this.lstNomColumnasSeleccionadas = lstNomColumnasSeleccionadas;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getNombreFiltroParametro() {
        return nombreFiltroParametro;
    }

    public void setNombreFiltroParametro(String nombreFiltroParametro) {
        this.nombreFiltroParametro = nombreFiltroParametro;
    }

    public MetCatalogo getSeleccionLstTipoDatos() {
        return seleccionLstTipoDatos;
    }

    public void setSeleccionLstTipoDatos(MetCatalogo seleccionLstTipoDatos) {
        this.seleccionLstTipoDatos = seleccionLstTipoDatos;
    }

    public List<MetCatalogo> getLstTipoDatos() {
        return lstTipoDatos;
    }

    public void setLstTipoDatos(List<MetCatalogo> lstTipoDatos) {
        this.lstTipoDatos = lstTipoDatos;
    }

    /*public Map<String, Object[]> getLstFiltrosIngresados() {
        return lstFiltrosIngresados;
    }*/
    public List<Entry<String, Object[]>> getLstFiltrosIngresados() {
        /*Set<Map.Entry<MetCatalogo, String>> tipoDatoSet = 
                     mapFiltrosIngresados.entrySet();*/
        return new ArrayList(lstFiltrosIngresados.entrySet());
    }

    public void setLstFiltrosIngresados(Map<String, Object[]> lstFiltrosIngresados) {
        this.lstFiltrosIngresados = lstFiltrosIngresados;
    }

    public List<MetCatalogo> getLstOperadoresLogicos() {
        return lstOperadoresLogicos;
    }

    public void setLstOperadoresLogicos(List<MetCatalogo> lstOperadoresLogicos) {
        this.lstOperadoresLogicos = lstOperadoresLogicos;
    }

    public List<MetCatalogo> getLstOperadoresRelacionales() {
        return lstOperadoresRelacionales;
    }

    public void setLstOperadoresRelacionales(List<MetCatalogo> lstOperadoresRelacionales) {
        this.lstOperadoresRelacionales = lstOperadoresRelacionales;
    }

    public String getColumnaAsignada() {
        return columnaAsignada;
    }

    public void setColumnaAsignada(String columnaAsignada) {
        this.columnaAsignada = columnaAsignada;
    }

    public String getParametroFiltroSeleccionado() {
        return parametroFiltroSeleccionado;
    }

    public void setParametroFiltroSeleccionado(String parametroFiltroSeleccionado) {
        this.parametroFiltroSeleccionado = parametroFiltroSeleccionado;
    }

    public MetCatalogo getOpRelacionalSeleccionado() {
        return opRelacionalSeleccionado;
    }

    public void setOpRelacionalSeleccionado(MetCatalogo opRelacionalSeleccionado) {
        this.opRelacionalSeleccionado = opRelacionalSeleccionado;
    }

    public MetCatalogo getOpLogicoSeleccionado() {
        return opLogicoSeleccionado;
    }

    public void setOpLogicoSeleccionado(MetCatalogo opLogicoSeleccionado) {
        this.opLogicoSeleccionado = opLogicoSeleccionado;
    }

    public List<MetTipoCatalogo> getLstTipoCatalogo() {
        return lstTipoCatalogo;
    }

    public void setLstTipoCatalogo(List<MetTipoCatalogo> lstTipoCatalogo) {
        this.lstTipoCatalogo = lstTipoCatalogo;
    }

    public MetTipoCatalogo getSeleccionLstTipoCatalogo() {
        return seleccionLstTipoCatalogo;
    }

    public void setSeleccionLstTipoCatalogo(MetTipoCatalogo seleccionLstTipoCatalogo) {
        this.seleccionLstTipoCatalogo = seleccionLstTipoCatalogo;
    }

    public List<MetTipoCatalogo> getLstContieneTipoCatalogo() {
        return lstContieneTipoCatalogo;
    }

    public void setLstContieneTipoCatalogo(List<MetTipoCatalogo> lstContieneTipoCatalogo) {
        this.lstContieneTipoCatalogo = lstContieneTipoCatalogo;
    }

    public List<MetCatalogo> getLstMetadatoFocus() {
        return lstMetadatoFocus;
    }

    public void setLstMetadatoFocus(List<MetCatalogo> lstMetadatoFocus) {
        this.lstMetadatoFocus = lstMetadatoFocus;
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

    public RepoColumna getRepoColumnaNuevo() {
        return repoColumnaNuevo;
    }

    public void setRepoColumnaNuevo(RepoColumna repoColumnaNuevo) {
        this.repoColumnaNuevo = repoColumnaNuevo;
    }

    public RepoFiltro getRepoFiltroNuevo() {
        return repoFiltroNuevo;
    }

    public void setRepoFiltroNuevo(RepoFiltro repoFiltroNuevo) {
        this.repoFiltroNuevo = repoFiltroNuevo;
    }

    public List<RepoFiltro> getLstRepoFiltros() {
        return lstRepoFiltros;
    }

    public void setLstRepoFiltros(List<RepoFiltro> lstRepoFiltros) {
        this.lstRepoFiltros = lstRepoFiltros;
    }

    public boolean isRenderOpLogico() {
        return renderOpLogico;
    }

    public void setRenderOpLogico(boolean renderOpLogico) {
        this.renderOpLogico = renderOpLogico;
    }

    public boolean isVerFiltroCatalogo() {
        return verFiltroCatalogo;
    }

    public void setVerFiltroCatalogo(boolean verFiltroCatalogo) {
        this.verFiltroCatalogo = verFiltroCatalogo;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public boolean isAddNuevoReporte() {
        return addNuevoReporte;
    }

    public void setAddNuevoReporte(boolean addNuevoReporte) {
        this.addNuevoReporte = addNuevoReporte;
    }

    public List<RepoReporte> getLstRepoCreados() {
        return lstRepoCreados;
    }

    public void setLstRepoCreados(List<RepoReporte> lstRepoCreados) {
        this.lstRepoCreados = lstRepoCreados;
    }

    public boolean isVerPanelReporte() {
        return verPanelReporte;
    }

    public void setVerPanelReporte(boolean verPanelReporte) {
        this.verPanelReporte = verPanelReporte;
    }

    public int getAnchoColumna() {
        return anchoColumna;
    }

    public void setAnchoColumna(int anchoColumna) {
        this.anchoColumna = anchoColumna;
    }

    public String getAlineacionColumna() {
        return alineacionColumna;
    }

    public void setAlineacionColumna(String alineacionColumna) {
        this.alineacionColumna = alineacionColumna;
    }

    public int getOrdenColumna() {
        return ordenColumna;
    }

    public void setOrdenColumna(int ordenColumna) {
        this.ordenColumna = ordenColumna;
    }

    public boolean isFiltroColumna() {
        return filtroColumna;
    }

    public void setFiltroColumna(boolean filtroColumna) {
        this.filtroColumna = filtroColumna;
    }

    public boolean isOrdenarTablaPorColumna() {
        return ordenarTablaPorColumna;
    }

    public void setOrdenarTablaPorColumna(boolean ordenarTablaPorColumna) {
        this.ordenarTablaPorColumna = ordenarTablaPorColumna;
    }

    public String getTextAreaColumna() {
        return textAreaColumna;
    }

    public void setTextAreaColumna(String textAreaColumna) {
        this.textAreaColumna = textAreaColumna;
    }

    public List<Object[]> getLstObjColumnas() {
        return lstObjColumnas;
    }

    public void setLstObjColumnas(List<Object[]> lstObjColumnas) {
        this.lstObjColumnas = lstObjColumnas;
    }

    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public String getSqlReporteMensaje() {
        return sqlReporteMensaje;
    }

    public void setSqlReporteMensaje(String sqlReporteMensaje) {
        this.sqlReporteMensaje = sqlReporteMensaje;
    }

    public Map<String, List<Object[]>> getMapSisSchema() {
        return mapSisSchema;
    }

    public void setMapSisSchema(Map<String, List<Object[]>> mapSisSchema) {
        this.mapSisSchema = mapSisSchema;
    }

    /*public Map<String, Object[]> getLstFiltrosSQLTabla() {
        return lstFiltrosSQLTabla;
    }*/
    public List<Entry<String, Object[]>> getLstFiltrosSQLTabla() {
        /*Set<Map.Entry<MetCatalogo, String>> tipoDatoSet = 
                     mapFiltrosIngresados.entrySet();*/
        return new ArrayList(lstFiltrosSQLTabla.entrySet());
    }

    public void setLstFiltrosSQLTabla(Map<String, Object[]> lstFiltrosSQLTabla) {
        this.lstFiltrosSQLTabla = lstFiltrosSQLTabla;
    }

    public String getQueryFiltro() {
        return queryFiltro;
    }

    public void setQueryFiltro(String queryFiltro) {
        this.queryFiltro = queryFiltro;
    }

    public Map<String, String> getLstFiltrosParaAsignar() {
        return lstFiltrosParaAsignar;
    }

    public void setLstFiltrosParaAsignar(Map<String, String> lstFiltrosParaAsignar) {
        this.lstFiltrosParaAsignar = lstFiltrosParaAsignar;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getActivarAccordion() {
        return activarAccordion;
    }

    public void setActivarAccordion(String activarAccordion) {
        this.activarAccordion = activarAccordion;
    }

    public boolean isActivarquery() {
        return activarquery;
    }

    public void setActivarquery(boolean activarquery) {
        this.activarquery = activarquery;
    }

    public boolean isActivarseleccion() {
        return activarseleccion;
    }

    public void setActivarseleccion(boolean activarseleccion) {
        this.activarseleccion = activarseleccion;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public String getCreatualizaQuery() {
        return creatualizaQuery;
    }

    public void setCreatualizaQuery(String creatualizaQuery) {
        this.creatualizaQuery = creatualizaQuery;
    }

    public List<Object[]> getLstCheckFor2() {
        return lstCheckFor2;
    }

    public void setLstCheckFor2(List<Object[]> lstCheckFor2) {
        this.lstCheckFor2 = lstCheckFor2;
    }

    public String getNomAgrupaCol() {
        return nomAgrupaCol;
    }

    public void setNomAgrupaCol(String nomAgrupaCol) {
        this.nomAgrupaCol = nomAgrupaCol;
    }

    public Integer getNumAgrupaCol() {
        return numAgrupaCol;
    }

    public void setNumAgrupaCol(Integer numAgrupaCol) {
        this.numAgrupaCol = numAgrupaCol;
    }

    public Integer getAnchoAgrupaCol() {
        return anchoAgrupaCol;
    }

    public void setAnchoAgrupaCol(Integer anchoAgrupaCol) {
        this.anchoAgrupaCol = anchoAgrupaCol;
    }

    public List<Object[]> getLstAgrupObjColumnas() {
        return lstAgrupObjColumnas;
    }

    public void setLstAgrupObjColumnas(List<Object[]> lstAgrupObjColumnas) {
        this.lstAgrupObjColumnas = lstAgrupObjColumnas;
    }

    public List<String> getLstAgrupaColumnasSeleccionadas() {
        return lstAgrupaColumnasSeleccionadas;
    }

    public void setLstAgrupaColumnasSeleccionadas(List<String> lstAgrupaColumnasSeleccionadas) {
        this.lstAgrupaColumnasSeleccionadas = lstAgrupaColumnasSeleccionadas;
    }

    public List<MetCatalogo> getLstEstilosCSSColumna() {
        return lstEstilosCSSColumna;
    }

    public void setLstEstilosCSSColumna(List<MetCatalogo> lstEstilosCSSColumna) {
        this.lstEstilosCSSColumna = lstEstilosCSSColumna;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getNomFuncion() {
        return nomFuncion;
    }

    public void setNomFuncion(String nomFuncion) {
        this.nomFuncion = nomFuncion;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    //</editor-fold>  
    //<editor-fold defaultstate="collapsed" desc="métodos">    
    @PostConstruct
    public void inicializar() {
        try {
            idReporte = null;
            lstTipoDatos = new ArrayList<>();
            lstOperadoresRelacionales = new ArrayList<>();
            lstFiltrosParaAsignar = new LinkedHashMap<>();
            lstOperadoresLogicos = new ArrayList<>();
            renderSlcTabla = true;
            renderTipoCtl = true;
            renderOpLogico = true;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            horaActual = formatter.format(java.util.Calendar.getInstance().getTime()).replace(" ", "_");

            verFiltroPeriodo = false;
            verFiltroZonal = false;
            verFiltroUsuario = false;
            verFiltroIndicador = false;
            verFiltroPeriodoIni = false;
            verFiltroPeriodoFin = false;
            verFiltroCatalogo = false;
            addNuevoReporte = false;
            //abrirPanelConsulta = false;
            verPanelReporte = false;
            sqlReporteMensaje = null;
            lstFiltrosSQLTabla = new LinkedHashMap<>();
            lstFiltrosIngresados = new LinkedHashMap<>();
            lstAgrupObjColumnas = new ArrayList<>();
            lstColumnasSeleccionadas = new ArrayList<>();
            if (usuarioControlador.getAliasReporte() != null) {
                switch (usuarioControlador.getAliasReporte()) {
                    case "R_SUBMENU_QUERY":
                        activarquery = false;
                        activarseleccion = true;
                        break;
                    case "R_SUBMENU_SELEC":
                        activarquery = true;
                        activarseleccion = false;
                        break;
                    default:
                        //cargarReporte();
                        break;
                }
            }

            //listReportes = baseControlador.getRepoReporteServicioRemote().listarTodosActivos();
            lstEstilosCSSColumna = baseControlador.getMetCatalogoServicioRemote().listarCatalogoXAlias("EST_CSS_REPO_HEADER");
            inicializarConn();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void inicializarConn() {
        try {
            //lstSistemas = baseControlador.getAdmBaseDatosServicioRemote().listarTodo();

            String parametroAES = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
            AdmBaseDatos bd = new AdmBaseDatos();
            //String tipoCifrado = baseControlador.getAdmColumnaServicioRemote().consultarPorTablaYColumna(bd.getClass()., bd.getPassword().getClass().getName()).getCodTipoEncr().getValor();*/
            //String parametroAES = "InecDiradGiapeZ1";
            String tipoCifrado = "AES";
            baseControlador.getActualizaBDServicioRemote().pasarParametrosEncriptacion(tipoCifrado, parametroAES);
            mapSisSchema = new LinkedHashMap<>();

            /*String nomBDD = baseControlador.getPropiedadesControlador().getPropertieValue("baseBaseDatosInicial");
            String userBDD = baseControlador.getPropiedadesControlador().getPropertieValue("userBaseDatosInicial");
            String passBDD = baseControlador.getPropiedadesControlador().getPropertieValue("passBaseDatosInicial");
            String ippBDD = baseControlador.getPropiedadesControlador().getPropertieValue("ipBaseDatosInicial");
            boolean crearConn = false;

            for (AdmBaseDatos base : lstSistemas) {
                if (nomBDD.equals(base.getNombrebdd()) && ippBDD.equals(base.getIp())) {
                    if (userBDD.equals(base.getUsuario())) {
                        if (passBDD.equals(base.getPassword())) {
                            if (base.getEstadoLogico() == false) {
                                base.setEstadoLogico(true);
                                baseControlador.getAdmBaseDatosServicioRemote().editarConexion(base);
                                crearConn = false;
                                break;
                            }
                            crearConn = false;
                            break;
                        } else {
                            crearConn = true;
                        }
                    } else {
                        crearConn = true;
                    }
                } else {
                    crearConn = true;
                }
            }
            if (crearConn) {
                AdmBaseDatos admBaseDatos = new AdmBaseDatos();
                admBaseDatos.setNombre("SIPE_" + horaActual.replaceAll("[^0-9]+", ""));
                admBaseDatos.setDriver("org.postgresql.Driver");
                admBaseDatos.setRdbms("postgres");
                admBaseDatos.setIp(baseControlador.getPropiedadesControlador().getPropertieValue("ipBaseDatosInicial"));
                admBaseDatos.setPuerto(Integer.valueOf(baseControlador.getPropiedadesControlador().getPropertieValue("puertoBaseDatosInicial")));
                admBaseDatos.setUsuario(baseControlador.getPropiedadesControlador().getPropertieValue("userBaseDatosInicial"));
                admBaseDatos.setPassword(baseControlador.getPropiedadesControlador().getPropertieValue("passBaseDatosInicial"));
                admBaseDatos.setNombrebdd(baseControlador.getPropiedadesControlador().getPropertieValue("baseBaseDatosInicial"));
                admBaseDatos.setEstadoLogico(true);
                admBaseDatos.setAlias("sipe" + horaActual.replaceAll("[^0-9]+", ""));
                baseControlador.getAdmBaseDatosServicioRemote().crearConexion(admBaseDatos);
            }*/
            lstSistemas = baseControlador.getAdmBaseDatosServicioRemote().listarTodosActivos();
            if (lstSistemas != null) {
                for (AdmBaseDatos base : lstSistemas) {
                    List<Object[]> lstEsquemas = baseControlador.getActualizaBDServicioRemote().recuperarEsquemas(base.getAlias());
                    mapSisSchema.put(base.getAlias() + "." + base.getNombre(), lstEsquemas);
                }
            } else {
                baseControlador.addWarningMessage("No existen conexiones a la base de datos");
                baseControlador.addWarningMessage("Contactese con el Administrador de Conexiones");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void recuperaConn(String conn) {
        try {
            if (activarquery == false) {

                idReporte = null;
                query = null;
                sqlReporteMensaje = null;
                lstFiltrosSQLTabla = new LinkedHashMap<>();
                lstFiltrosIngresados = new LinkedHashMap<>();
                //System.err.println("RECUPERA conn " + conn);
                conexion = conn;
                repoSubreporteNuevo = new RepoSubreporte();
                repoColumnaNuevo = new RepoColumna();
                repoProcedimientoNuevo = new RepoProcedimiento();
                repoReporteNuevo = new RepoReporte();
                repoFiltroNuevo = new RepoFiltro();
                filtroSubreporte = "";
                nomFuncion = null;
                funcion = null;
                if (conexion != null) {
                    baseControlador.addSuccessMessage("Conexión seleccionada: " + conexion);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            baseControlador.addErrorMessage("Error al seleccionar la conexión");
        }
    }

    public void recuperaSistema(String conn, String sq) {
        try {
            //System.err.println("conn " + conn);
            //System.err.println("esquema " + sq);
            //limpiarReporte();            
            idReporte = null;
            conexion = conn;
            esquema = sq;
            addNuevoReporte = false;
            repoSubreporteNuevo = new RepoSubreporte();
            repoColumnaNuevo = new RepoColumna();
            repoProcedimientoNuevo = new RepoProcedimiento();
            repoReporteNuevo = new RepoReporte();
            repoFiltroNuevo = new RepoFiltro();
            lstNomTablas = new ArrayList<>();
            lstNomColumnas = new ArrayList<>();
            lstRepoFiltros = new ArrayList<>();
            lstFiltrosIngresados = new LinkedHashMap<>();
            filtroSubreporte = "";
            List<Object[]> listTablas = baseControlador.getActualizaBDServicioRemote().recuperaNombresTbl(conexion, esquema);
            for (int i = 0; i < listTablas.size(); i++) {
                lstNomTablas.add(listTablas.get(i)[0].toString());
            }
            if (treeNode != null) {
                treeNode.getChildren().clear();
                nivel1 = null;
                expandirArbol(treeNode, true);
            }
            padres.clear();
            objPadres.clear();
            renderSlcTabla = true;
            joins.clear();
            if (lstReferencias != null) {
                lstReferencias.clear();
            }
            query = "";
            sql = new Select();
            verPanelReporte = false;
            //abrirPanelConsulta = false;
            textAreaColumna = "";
            lstFiltrosSQLTabla = new LinkedHashMap<>();
            baseControlador.addSuccessMessage("Esquema seleccionado: " + sq);
            nomFuncion = null;
            funcion = null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void seleccionarTabla() {
        //System.err.println("nomTabla " + nomTabla);
        try {
            if (addNuevoReporte) {
                esquema = esquemaOriginal;
            } else {
                esquemaOriginal = esquema;
            }
            contarFiltrosAsignados = 0;
            renderSlcTabla = false;
            treeNode = new DefaultTreeNode("padre", null);
            nivel1 = new DefaultTreeNode(nomTabla, treeNode);
            addDependencias(nivel1, "seleccionar");
            expandirArbol(treeNode, true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void expandirTabla() {
        try {
            //System.err.println("selectedNode " + selectedNode);
            nomTabla = selectedNode.getData().toString();

            if (treeNode.getData().equals(selectedNode.getParent().getData()) && !treeNode.getChildren().isEmpty()) {
                /*treeNode.getChildren().clear();
                nivel1 = null;
                lstNomColumnas.clear();
                padres.clear();
                objPadres.clear();
                renderSlcTabla = true;
                joins.clear();
                lstReferencias.clear();*/
                if (addNuevoReporte) {
                    resetPagina();
                } else {
                    recuperaSistema(conexion, esquema);
                }
            } else if (selectedNode.getChildren().isEmpty()) {
                addDependencias(selectedNode, "expandir");
                removeNodos(treeNode, selectedNode);
            } else {
                for (int i = 0; i < lstNomColumnas.size(); i++) {
                    if (lstNomColumnas.get(i).split("\\.")[0].equals(selectedNode.getChildren().get(0).toString())) {
                        lstNomColumnas.subList(i, lstNomColumnas.size()).clear();
                    }
                }
                recoverNodo(treeNode, nomTabla);
            }
            query = "";
            expandirArbol(treeNode, true);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void addDependencias(TreeNode nodoN, String condicion) {
        try {
            List<Object[]> lstDependencias;
            padres.add(nomTabla);
            removerDuplicados(padres);
            //Object[] objTbl = new Object[2]; objTbl[0] = nomTabla; objTbl[1] = esquema;
            for (Iterator it = nodoN.getParent().getChildren().iterator(); it.hasNext();) {
                TreeNode nodoAnterior = (TreeNode) it.next();
                //padres.add(nodoAnterior.getParent().getData().toString());  
                Object[] objTbl = new Object[2];
                if (nodoAnterior.getParent().getData().toString().equals("padre")) {
                    objTbl[0] = esquema;
                    objTbl[1] = nomTabla;
                    objPadres.add(objTbl);
                }

                if (nomTabla.equals(nodoAnterior.getData())) {
                    /*for (Map.Entry<String, String> map : objPadres.entrySet()) {
                    if (nomTabla.equals(map.getValue())) {
                    esquema = map.getKey();
                    break;
                    }
                    }*/
                    for (Object[] object1 : objPadres) {
                        if (nomTabla.equals(object1[1])) {
                            esquema = object1[0].toString();
                            break;
                        }
                    }

                    if (lstReferencias != null && !condicion.equals("recover")) {
                        boolean avy = false;
                        for (int i = 0; i < lstNomColumnas.size(); i++) {
                            if (lstNomColumnas.get(i).split("\\.")[0].equals(nomTabla)) {
                                avy = true;
                                break;
                            }
                        }
                        if (avy == false) {
                            for (int i = 0; i < lstReferencias.size(); i++) {
                                if (nomTabla.equals(lstReferencias.get(i)[1].toString())) {
                                    Object[] organizar = new Object[6];
                                    organizar[0] = lstReferencias.get(i)[3];
                                    organizar[1] = lstReferencias.get(i)[4];
                                    organizar[2] = lstReferencias.get(i)[5];
                                    organizar[3] = lstReferencias.get(i)[0];
                                    organizar[4] = lstReferencias.get(i)[1];
                                    organizar[5] = lstReferencias.get(i)[2];
                                    joins.add(organizar);
                                } else if (nomTabla.equals(lstReferencias.get(i)[4].toString())) {
                                    joins.add(lstReferencias.get(i));
                                    //System.err.println("dep " + join.get(i)[2] + " " + join.get(i)[5]);
                                }
                            }
                        }
                    }

                    lstDependencias = baseControlador.getActualizaBDServicioRemote().recuperaDependencias(conexion, nomTabla, esquema);
                    lstReferencias = baseControlador.getActualizaBDServicioRemote().columnaReferencias(conexion, nomTabla, esquema);

                    //System.err.println("size1 " + lstDependencias.size());
                    //System.err.println("size2 " + lstReferencias.size());
                    for (int i = 0; i < lstDependencias.size(); i++) {
                        Object[] objTbl1 = new Object[2];
                        if (nomTabla.equals(lstDependencias.get(i)[1].toString()) && !padres.contains(lstDependencias.get(i)[3].toString())) {
                            //nodoN = new DefaultTreeNode(new Tabla(lstDependencias.get(i)[3].toString(),lstDependencias.get(i)[0].toString()), nodoAnterior);
                            nodoN = new DefaultTreeNode(lstDependencias.get(i)[3].toString(), nodoAnterior);
                            objTbl1[0] = lstDependencias.get(i)[2].toString();
                            objTbl1[1] = lstDependencias.get(i)[3].toString();
                        } else if (nomTabla.equals(lstDependencias.get(i)[3].toString()) && !padres.contains(lstDependencias.get(i)[1].toString())) {
                            //nodoN = new DefaultTreeNode(new Tabla(lstDependencias.get(i)[1].toString(),lstDependencias.get(i)[2].toString()), nodoAnterior);
                            nodoN = new DefaultTreeNode(lstDependencias.get(i)[1].toString(), nodoAnterior);
                            objTbl1[0] = lstDependencias.get(i)[0].toString();
                            objTbl1[1] = lstDependencias.get(i)[1].toString();
                        }
                        if (objTbl1[0] != null) {
                            //objPadres.add(objTbl1);
                            boolean addObj = false;
                            /*for (Map.Entry<String, String> map : objPadres.entrySet()) {
                            if (objTbl1[1].equals(map.getValue())) {
                            addObj = true;
                            break;
                            }
                            }
                            if (addObj == false) {
                            objPadres.put(String.valueOf(objTbl1[0]), String.valueOf(objTbl1[1]));
                            }*/
                            for (int m = 0; m < objPadres.size(); m++) {
                                if (objTbl1[1].equals(objPadres.get(m)[1])) {
                                    addObj = true;
                                    break;
                                }
                            }
                            if (addObj == false) {
                                objPadres.add(objTbl1);
                            }
                        }
                    }
                    if (lstNomColumnas.isEmpty()) {
                        List<Object[]> lstColumnas = baseControlador.getActualizaBDServicioRemote().recuperaNombresColumnas(conexion, nomTabla, esquema);
                        //System.err.println("lstColumnas2 " + lstColumnas.size());
                        for (int j = 0; j < lstColumnas.size(); j++) {
                            lstNomColumnas.add(nomTabla + "." + lstColumnas.get(j)[0].toString());
                        }
                    } else {
                        boolean avz = false;
                        for (int i = 0; i < lstNomColumnas.size(); i++) {
                            if (lstNomColumnas.get(i).split("\\.")[0].equals(nomTabla)) {
                                avz = true;
                                break;
                            }
                        }
                        if (avz == false) {
                            List<Object[]> lstColumnas = baseControlador.getActualizaBDServicioRemote().recuperaNombresColumnas(conexion, nomTabla, esquema);
                            //System.err.println("lstColumnas3 " + lstColumnas.size());
                            for (int j = 0; j < lstColumnas.size(); j++) {
                                lstNomColumnas.add(nomTabla + "." + lstColumnas.get(j)[0].toString());

                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void recoverNodo(TreeNode nodoPadre, String nameNodo) {
        try {
            List<TreeNode> nodosHijo = nodoPadre.getChildren();
            for (TreeNode hijo : nodosHijo) {
                if (hijo.getData().equals(nameNodo)) {
                    hijo.getChildren().clear();
                    padres.subList(padres.indexOf(nameNodo) + 1, padres.size()).clear();
                    for (int i = 0; i < joins.size(); i++) {
                        if (joins.get(i)[1].equals(nameNodo)) {
                            joins.subList(i, joins.size()).clear();
                            break;
                        }
                    }
                    addDependencias(hijo, "recover");
                    //System.err.println("NodoBorrado en recover " + hijo);
                    break;
                }
                recoverNodo(hijo, nameNodo);

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void removeNodos(TreeNode nodoPadre, TreeNode nodoABorrar) {
        try {
            List<TreeNode> nodosHijo = nodoPadre.getChildren();
            for (Iterator<TreeNode> it = nodosHijo.iterator(); it.hasNext();) {
                TreeNode hijo = it.next();
                if (!hijo.equals(nodoABorrar)) {
                    if (hijo.isLeaf()) {
                        it.remove();
                        //System.err.println("Nodoborrado en remove " + hijo);
                    } else {
                        /*for (TreeNode hijoNuevo : nodoPadre.getChildren()) {
                            if (hijoNuevo.getChildCount() > 0) {
                                removeNodos(hijoNuevo, nodoABorrar);
                            }

                        }*/
                        
                        
                        
                        nodosHijo.stream().filter(hijoNuevo -> hijoNuevo.getChildCount() >0).forEach((hijoNuevo) -> {
                            removeNodos(hijoNuevo, nodoABorrar);
                        });
                        
                        
//                        nodoPadre.getChildren().stream().filter((hijoNuevo) -> (hijoNuevo.getChildCount() > 0)).forEach((hijoNuevo) -> {
//                            removeNodos(hijoNuevo, nodoABorrar);
//                        });
                        
                        
                        
                        
                        
                    }

                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void removerDuplicados(List<?> al) {
        for (int i = 0; i < al.size(); i++) {
            for (int j = i + 1; j < al.size(); j++) {
                if (al.get(i).equals(al.get(j))) {
                    al.remove(j);
                    j--;
                }
            }
        }
    }

    private void expandirArbol(final TreeNode nodo, final boolean expandir) {
        try {
            /*for (final TreeNode child : nodo.getChildren()) {
                expandirArbol(child, expandir);
            }*/
            nodo.getChildren().stream().forEach((child) -> {
                expandirArbol((TreeNode) child, expandir);
            });
            nodo.setExpanded(expandir);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void construirSql() {
        try {
            sql = new Select();
            query = "";
            lstObjColumnas = new ArrayList<>();
            mapTblSeleccionadas = new LinkedHashMap<>();
            String addEsquema = null;
            String aliasTabla = null;
            String aliasTabla2 = null;
            String[] abecedario = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

            //System.err.println("columnasTam " + lstNomColumnasSeleccionadas.size());
            List<Column> lstColumnasSQL = new ArrayList<>();

            for (int j = 0; j < padres.size(); j++) {
                mapTblSeleccionadas.put(abecedario[j], padres.get(j));
            }

            for (int i = 0; i < lstNomColumnasSeleccionadas.size(); i++) {
                String[] tblCol = lstNomColumnasSeleccionadas.get(i).split("\\.");

                for (Object[] object1 : objPadres) {
                    if (tblCol[0].equals(object1[1])) {
                        addEsquema = object1[0].toString();
                        break;
                    }
                }
                for (Map.Entry<String, String> param : mapTblSeleccionadas.entrySet()) {
                    if (tblCol[0].equals(param.getValue())) {
                        aliasTabla = param.getKey();
                        break;
                    }
                }
                //Column colN = new Column(addEsquema + "." + tblCol[0], tblCol[1] + " " + tblCol[0].substring(0, 1) + tblCol[1].replace("_", ""));
                //Column colN = new Column(tblCol[1] + " " + tblCol[0].substring(0, 1) + tblCol[1].replace("_", ""));
                Column colN = new Column(addEsquema + "." + tblCol[0], tblCol[1]).tableAs(aliasTabla);
                lstColumnasSQL.add(colN);
            }
            sql.addColumn(lstColumnasSQL);

            for (int i = 1; i < joins.size() + 1; i++) {
                for (Map.Entry<String, String> param : mapTblSeleccionadas.entrySet()) {
                    if (joins.get(i - 1)[1].equals(param.getValue())) {
                        aliasTabla = param.getKey();
                    }
                    if (joins.get(i - 1)[4].equals(param.getValue())) {
                        aliasTabla2 = param.getKey();
                    }
                }
                sql.innerJoin(new Column(joins.get(i - 1)[0].toString() + "." + joins.get(i - 1)[1].toString(), joins.get(i - 1)[2].toString()).tableAs(aliasTabla), new Column(joins.get(i - 1)[3].toString() + "." + joins.get(i - 1)[4].toString(), joins.get(i - 1)[5].toString()).tableAs(aliasTabla2));
            }
            /*for (Object[] join : joins) {                    
                    sql.innerJoin(new Column(join[0].toString() + "." + join[1].toString(), join[2].toString()), new Column(join[3].toString() + "." + join[4].toString(), join[5].toString()));
                }*/
 /*joins.stream().forEach((join) -> {
                    sql.innerJoin(new Column(join[0].toString() + "." + join[1].toString(), join[2].toString()), new Column(join[3].toString() + "." + join[4].toString(), join[5].toString()));
                    //sql.innerJoin(new Column(join[2].toString()), new Column(join[5].toString()));
                });*/

            query = sql.toString();
            //System.out.println(query);
            //lstColumnas = lstNomColumnasSeleccionadas;
            asignarEditarColumnas();
            lstNomColumnasSeleccionadas = new ArrayList<>();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cargar la lista lstObjColumnas que servira en el dialog de la edición de
     * columnas
     */
    public void asignarEditarColumnas() {
        try {
            for (int i = 0; i < lstNomColumnasSeleccionadas.size(); i++) {
                //String[] tblCol = lstNomColumnasSeleccionadas.get(i).split("\\.");
                Object[] objColum = new Object[8];
                /*if (tblCol.length == 2) {
                    objColum[0] = tblCol[1];
                } else {
                    objColum[0] = tblCol[0];
                }*/
                objColum[0] = lstNomColumnasSeleccionadas.get(i);
                objColum[1] = i + 1;
                objColum[2] = 40;
                objColum[3] = "leftColumn";
                objColum[4] = false;
                objColum[5] = false;
                objColum[6] = "TEXTO";
                objColum[7] = "ninguno";
                lstObjColumnas.add(i, objColum);
            }
            compararColumnas(lstObjColumnas);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Asigna un alias a las columnas que se repitan en caso de no tener uno,
     * esto es solo para la edición NO modifica el query ingresado.
     */
    public void compararColumnas(List<Object[]> lstObjArray) {
        try {
            for (int j = 0; j < lstObjColumnas.size(); j++) {
                int k = 0;
                String wordj = String.valueOf(lstObjColumnas.get(j)[0]);
                for (int i = lstObjColumnas.size() - 1; i > j; i--) {
                    String wordi = String.valueOf(lstObjColumnas.get(i)[0]);
                    if (wordi != null && wordj != null && wordi.compareTo(wordj) == 0 && i != j) {
                        k++;
                        lstObjColumnas.get(i)[0] = wordj + "_" + k;
                        lstObjColumnas.set(i, lstObjColumnas.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void seleccionarColumnas() {
        try {
            /*HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String columnasSeleccionadas = request.getParameter("texto");*/
            String columnasSeleccionadas = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("texto");

            //System.err.println("columnasSeleccionadas " + columnasSeleccionadas);
            arregloColumnas = null;
            if (columnasSeleccionadas != null) {
                //arregloColumnas = columnasSeleccionadas.toLowerCase().trim().split(",");
                //List<String> lstColumnas = new ArrayList<>(Arrays.asList(arregloColumnas));
                lstColumnasSeleccionadas.add(columnasSeleccionadas.toLowerCase().trim());
                //arregloColumnas = lstColumnas.toArray(new String[0]);
                baseControlador.addSuccessMessage(lstColumnasSeleccionadas.size() + " columna ingresada");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void implementarSql() {
        try {
            lstNomColumnasSeleccionadas = new ArrayList<>();
            lstObjColumnas = new ArrayList<>();
            filtroSubreporte = "";
            if (conexion != null && query != null) {

                if (creatualizaQuery.equals("crear") || (creatualizaQuery.equals("actualizar") && !lstFiltrosParaAsignar.isEmpty())) {
                    //System.err.println("connipl " + conexion);
                    //System.err.println("esqipl " + query);
                    //sqlReporteMensaje = baseControlador.getActualizaBDServicioRemote().ejecutaSql2(conexion, sqlReporte);
                    query = query.trim();
                    String sqlReporte2 = query;
                    //sqlReporte2 = sqlReporte2.toUpperCase();
                    //System.err.println("sql " + sqlReporte2);
                    if (!lstColumnasSeleccionadas.isEmpty()) {
                        arregloColumnas = lstColumnasSeleccionadas.toArray(new String[0]);
                    }

                    String columnasSQL = sqlReporte2.substring(6, sqlReporte2.indexOf("FROM")).trim();
                    //String[] arrColumnas = null;
                    if (columnasSQL.contains("*")) {
                        if (arregloColumnas == null) {
                            baseControlador.addErrorMessage("No se entiende la selección de columnas");
                            baseControlador.addErrorMessage("Seleccione las columnas e ingrese la consulta");
                        }
                    } else if (!columnasSQL.contains("*")) {
                        if (arregloColumnas == null) {
                            arregloColumnas = columnasSQL.toLowerCase().trim().split(", ");
                        }
                    }

                    if (arregloColumnas != null) {
                        //System.err.println("num col " + arregloColumnas.length);
                        for (String arrColumna : arregloColumnas) {
                            if (arrColumna.contains("as")) {
                                String contieneAS = arrColumna.split(" as ")[1].replaceAll("\\s", "");
                                if (contieneAS.contains("\"")) {
                                    lstNomColumnasSeleccionadas.add(contieneAS.replace("\"", ""));
                                } else {
                                    lstNomColumnasSeleccionadas.add(contieneAS);
                                }
                            } else if (arrColumna.contains(".")) {
                                lstNomColumnasSeleccionadas.add(arrColumna.split("\\.")[1].replaceAll("\\s", ""));
                            } else {
                                lstNomColumnasSeleccionadas.add(arrColumna.trim().replaceAll("\\s", ""));
                            }
                        }
                        asignarEditarColumnas();

                        /*for (int i = 0; i < lstFiltrosParaAsignar.size(); i++) {
                    if (query.contains(lstFiltrosParaAsignar.get(i))) {
                        if (i >= 1) {
                            filtroSubreporte = filtroSubreporte + "|";
                        }
                        filtroSubreporte = filtroSubreporte + lstFiltrosParaAsignar.get(i);
                    }
                }*/
                        int i = 0;
                        for (Map.Entry<String, String> filtroIng : lstFiltrosParaAsignar.entrySet()) {
                            if (nomFuncion == null) {
                                if (query.contains(filtroIng.getValue())) {
                                    if (i >= 1) {
                                        filtroSubreporte = filtroSubreporte + "|";
                                    }
                                    filtroSubreporte = filtroSubreporte + filtroIng.getValue();
                                    i++;
                                }
                            } else {
                                if (i >= 1) {
                                    filtroSubreporte = filtroSubreporte + "|";
                                }
                                filtroSubreporte = filtroSubreporte + filtroIng.getValue();
                                i++;
                            }
                        }
                        if (creatualizaQuery.equals("crear")) {
                            //lstTipoDatos = baseControlador.getCacheTimer().getCatalogoxTipo(99);
                            lstTipoDatos = baseControlador.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIP_DATO");
                            //System.err.println("cargarTipoDatos " + lstTipoDatos.size());
                            baseControlador.addSuccessMessage(arregloColumnas.length + " Columnas ingresadas ");
                            baseControlador.addSuccessMessage("Consulta ingresada");
                        } else {
                            baseControlador.addSuccessMessage(arregloColumnas.length + " Columnas ingresadas ");
                            baseControlador.addSuccessMessage("Consulta actualizada");
                        }
                    } else {
                        baseControlador.addErrorMessage("Consulta NO Ingresada");
                    }
                } else if (creatualizaQuery.equals("actualizar") && lstFiltrosParaAsignar.isEmpty()) {
                    baseControlador.addWarningMessage("No se puede actualizar!");
                    baseControlador.addWarningMessage("Lista de Filtros para Asignar vacía!");
                } else {
                    baseControlador.addErrorMessage("Error");
                }
            } else if (conexion == null) {
                sqlReporteMensaje = "Seleccione una conexión";
                baseControlador.addWarningMessage(sqlReporteMensaje);
            } else if (query == null) {
                sqlReporteMensaje = "Ingrese una consulta";
                baseControlador.addWarningMessage(sqlReporteMensaje);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            sqlReporteMensaje = "Error: " + ex;
            baseControlador.addErrorMessage(sqlReporteMensaje);
        }
    }

    /*public void cargarTipoDatos() {
        try {
            lstTipoCatalogo = baseControlador.getCacheTimer().getMetTipoCatalogoList();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }*/
    public void actualizarTipoCatalogo() {
        try {
            //System.err.println("aaa + " + nombreFiltroParametro);

            renderTipoCtl = nombreFiltroParametro.equals("");

            lstContieneTipoCatalogo = new ArrayList<>();
            /*for (MetTipoCatalogo metTipoCatalogo : lstTipoCatalogo) {
                if (metTipoCatalogo.getNombre().toUpperCase().contains(nombreFiltroParametro.toUpperCase())) {
                    lstContieneTipoCatalogo.add(metTipoCatalogo);
                }
            }*/
            lstTipoCatalogo.stream().filter((metTipoCatalogo) -> (metTipoCatalogo.getNombre().toUpperCase().contains(nombreFiltroParametro.toUpperCase()))).forEach((metTipoCatalogo) -> {
                lstContieneTipoCatalogo.add(metTipoCatalogo);
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void onFocusMetadato() {
        try {
            //List<Integer> li = new ArrayList<>();
            //System.err.println("seleccionLstTipoCatalogo " + seleccionLstTipoCatalogo.getIdTipoCatalogo());
            //li.add(seleccionLstTipoCatalogo.getIdTipoCatalogo());
            lstMetadatoFocus = baseControlador.getCacheTimer().getCatalogoxTipo(seleccionLstTipoCatalogo.getIdTipoCatalogo());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void addFiltrosListCatalogo() {
        try {
            MetTipoCatalogo metTipoCatalogo;
            if (seleccionLstTipoCatalogo == null) {
                metTipoCatalogo = new MetTipoCatalogo();
            } else {
                metTipoCatalogo = baseControlador.getCacheTimer().getTipoCatalogoxID(seleccionLstTipoCatalogo.getIdTipoCatalogo());
            }
            Object[] lstDatosFiltro = new Object[5];
            lstDatosFiltro[0] = nombreFiltro;
            lstDatosFiltro[1] = ":" + nombreFiltroParametro.replaceAll("\\s", "").toLowerCase();
            lstDatosFiltro[2] = metTipoCatalogo.getNombre();
            lstDatosFiltro[3] = metTipoCatalogo.getAlias();
            if (tipoFiltro != null) {
                if (tipoFiltro.length() != 0) {
                    lstDatosFiltro[4] = tipoFiltro;
                }
            } else {
                lstDatosFiltro[4] = "";
            }
            /*lstFiltrosIngresados.add(lstDatosFiltro);
            lstFiltrosParaAsignar.add(String.valueOf(lstDatosFiltro[1]));*/
            lstFiltrosIngresados.put(lstDatosFiltro[1].toString(), lstDatosFiltro);
            lstFiltrosParaAsignar.put(lstDatosFiltro[1].toString(), lstDatosFiltro[1].toString());
            //System.err.println("ingresarFiltros " + lstFiltrosIngresados.size());

            nombreFiltroParametro = "";
            nombreFiltro = "";
            renderTipoCtl = true;
            seleccionLstTipoDatos = new MetCatalogo();
            lstContieneTipoCatalogo = new ArrayList<>();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void addFiltrosListSQL() {
        try {
            Object[] objFiltroSql = new Object[3];
            objFiltroSql[0] = nombreFiltro.trim();
            objFiltroSql[1] = ":" + nombreFiltroParametro.replaceAll("\\s", "").toLowerCase();
            objFiltroSql[2] = queryFiltro.trim();
            //Map<String, Object> param4 = new LinkedHashMap();
            List<Object[]> lstFiltro = new ArrayList<>();
            if (!objFiltroSql[2].toString().isEmpty()) {
                if (!objFiltroSql[2].toString().contains(":")) {
                    lstFiltro = baseControlador.getRepoFiltroServicioRemote().listarEjecutarConsulta(queryFiltro);
                }
            }

            //System.err.println("num col queryFiltro " + lstFiltro.get(0).length);
            lstFiltrosSQLTabla.put(objFiltroSql[1].toString(), objFiltroSql);
            lstFiltrosParaAsignar.put(objFiltroSql[1].toString(), objFiltroSql[1].toString());
            /*if (lstFiltro.isEmpty()) {
                mapFiltroReporteObj.put(String.valueOf(objFiltroSql[1]), param4);
            } else if (lstFiltro.get(0).length == 2) {
                for (Object[] objects : lstFiltro) {
                    param4.put(String.valueOf(objects[0]), objects[1]);
                }
                mapFiltroReporteObj.put(String.valueOf(objFiltroSql[1]), param4);
            } else */
            if (!lstFiltro.isEmpty()) {
                if (lstFiltro.get(0).length < 2 || lstFiltro.get(0).length > 2) {
                    baseControlador.addErrorMessage("El query debe tener dos columnas: campo único y campo a mostrar");
                }
            }
            //mapNombreFiltroReporte.put(String.valueOf(objFiltroSql[1]), nombreFiltro);
            nombreFiltroParametro = "";
            nombreFiltro = "";
            queryFiltro = "";
            baseControlador.addSuccessMessage("Filtro Ingresado");

        } catch (Exception ex) {
            baseControlador.addErrorMessage("Error: " + ex.getMessage());
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cargarOperadores() {
        try {
            filtrosQuery = "";
            List<MetCatalogo> lstOperadores;
            Map<String, Map<String, Object>> mapQuery = new LinkedHashMap<>();
            Map<String, Object> mapParamQuery = new LinkedHashMap<>();
            if (activarquery) {
                MetTipoCatalogo mtTipoCatalogo = baseControlador.getMetTipoCatalogoServicioRemote().consultaTipoCatalogoPorAlias("TIP_OPERADOR");
                lstOperadores = baseControlador.getCacheTimer().getCatalogoxTipo(mtTipoCatalogo.getIdTipoCatalogo());
                lstOperadores.stream().forEach((operador) -> {
                    if (operador.getNivel() == 1 || operador.getNivel() == 2) {
                        lstOperadoresRelacionales.add(operador);
                    } else if (operador.getNivel() == 3) {
                        lstOperadoresLogicos.add(operador);
                    }
                });
            }
            /*if (addNuevoReporte) {
                for (Map.Entry<String, String> param : mapNombreFiltroReporte.entrySet()) {
                    //lstFiltrosParaAsignar.add(param.getKey());
                    lstFiltrosParaAsignar.put(param.getKey(), param.getKey());
                }
            }*/
            if (!lstFiltrosIngresados.isEmpty()) {
                parametrosDeFiltrosCtlgo = new String[lstFiltrosIngresados.size()];
                filtroTipoCatalogo = new String[lstFiltrosIngresados.size()];
                nomFiltros = new String[lstFiltrosIngresados.size()];
                tipoFiltros = new String[lstFiltrosIngresados.size()];
            }
            if (!lstFiltrosSQLTabla.isEmpty()) {
                parametrosDeFiltrosQuery = new String[lstFiltrosSQLTabla.size()];
                nomFiltrosQuery = new String[lstFiltrosSQLTabla.size()];
            }
            int i = 0, j = 0;
            for (Map.Entry<String, Object[]> filtrosIngresados : lstFiltrosIngresados.entrySet()) {
                parametrosDeFiltrosCtlgo[i] = String.valueOf(filtrosIngresados.getValue()[1]);
                filtroTipoCatalogo[i] = String.valueOf(filtrosIngresados.getValue()[3]);
                nomFiltros[i] = String.valueOf(filtrosIngresados.getValue()[0]);
                tipoFiltros[i] = String.valueOf(filtrosIngresados.getValue()[4]);
                i++;
            }
            for (Map.Entry<String, Object[]> filtrosIngresados : lstFiltrosSQLTabla.entrySet()) {
                parametrosDeFiltrosQuery[j] = String.valueOf(filtrosIngresados.getValue()[1]);
                mapParamQuery.put(String.valueOf(filtrosIngresados.getValue()[1]), filtrosIngresados.getValue()[2]);
                nomFiltrosQuery[j] = String.valueOf(filtrosIngresados.getValue()[0]);
                j++;
            }
            mapQuery.put("sql", mapParamQuery);
            Gson gson = new Gson();
            //Gson prettyGson2 = new GsonBuilder().setPrettyPrinting().create();
            filtrosQuery = gson.toJson(mapQuery);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void removerFiltros(String parametroFiltro) {
        try {
            lstFiltrosIngresados.remove(parametroFiltro);
            lstFiltrosParaAsignar.remove(parametroFiltro);
            //System.err.println("removerFiltros " + lstFiltrosIngresados.size());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void removerFiltrosLstQuery(String parametroFiltro) {
        try {
            lstFiltrosSQLTabla.remove(parametroFiltro);
            lstFiltrosParaAsignar.remove(parametroFiltro);
            //System.err.println("removerFiltros " + lstFiltrosIngresados.size());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarSql() {

        try {
            //System.err.println("act1 " + columnaAsignada);
            MetCatalogo opRelacionalSeleccionado2;
            MetCatalogo opLogicoSeleccionado2 = null;
            String aliasTabla = null;

            String[] tblCol = columnaAsignada.split("\\.");

            for (Object[] object1 : objPadres) {
                if (tblCol[0].equals(object1[1])) {
                    esquema = object1[0].toString();
                    break;
                }
            }
            for (Map.Entry<String, String> param : mapTblSeleccionadas.entrySet()) {
                if (tblCol[0].equals(param.getValue())) {
                    aliasTabla = param.getKey();
                    break;
                }
            }
            Column colIni = new Column(esquema + "." + tblCol[0], tblCol[1]).tableAs(aliasTabla);

            if (opRelacionalSeleccionado != null) {
                opRelacionalSeleccionado2 = baseControlador.getCacheTimer().getCatalogoxID(opRelacionalSeleccionado.getIdCatalogo());
                //System.err.println("act2 " + opRelacionalSeleccionado2.getAlias());

            } else {
                opRelacionalSeleccionado2 = new MetCatalogo();
                opRelacionalSeleccionado2.setAlias("=");
            }

            if (contarFiltrosAsignados == 0) {
                renderOpLogico = false;
            }
            if (opLogicoSeleccionado != null) {
                opLogicoSeleccionado2 = baseControlador.getCacheTimer().getCatalogoxID(opLogicoSeleccionado.getIdCatalogo());
                //System.err.println("act4 " + opLogicoSeleccionado2.getAlias());
            }

            switch (opRelacionalSeleccionado2.getAlias()) {
                case "=":
                    if (contarFiltrosAsignados == 0) {
                        sql.where(Clause.equal(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("and")) {
                        sql.and(Clause.equal(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("or")) {
                        sql.or(Clause.equal(colIni, parametroFiltroSeleccionado));
                    }
                    break;
                case "<":
                    if (contarFiltrosAsignados == 0) {
                        sql.where(Clause.lessThan(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("and")) {
                        sql.and(Clause.lessThan(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("or")) {
                        sql.or(Clause.lessThan(colIni, parametroFiltroSeleccionado));
                    }
                    break;
                case ">":
                    if (contarFiltrosAsignados == 0) {
                        sql.where(Clause.greaterThan(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("and")) {
                        sql.and(Clause.greaterThan(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("or")) {
                        sql.or(Clause.greaterThan(colIni, parametroFiltroSeleccionado));
                    }
                    break;
                case "<=":
                    if (contarFiltrosAsignados == 0) {
                        sql.where(Clause.lessEqual(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("and")) {
                        sql.and(Clause.lessEqual(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("or")) {
                        sql.or(Clause.lessEqual(colIni, parametroFiltroSeleccionado));
                    }
                    break;
                case ">=":
                    if (contarFiltrosAsignados == 0) {
                        sql.where(Clause.greaterEqual(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("and")) {
                        sql.and(Clause.greaterEqual(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("or")) {
                        sql.or(Clause.greaterEqual(colIni, parametroFiltroSeleccionado));
                    }
                    break;
                case "<>":
                    if (contarFiltrosAsignados == 0) {
                        sql.where(Clause.notEqual(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("and")) {
                        sql.and(Clause.notEqual(colIni, parametroFiltroSeleccionado));
                    } else if (opLogicoSeleccionado2.getAlias().equals("or")) {
                        sql.or(Clause.notEqual(colIni, parametroFiltroSeleccionado));
                    }
                    break;
                case "between":
                    sql.where(Clause.between(colIni, parametroFiltroSeleccionado, parametroFiltroSeleccionado));
                    break;
                case "exists":
                    sql.where(Clause.between(colIni, parametroFiltroSeleccionado, parametroFiltroSeleccionado));
                    break;
                case "in":
                    sql.where(Clause.in(colIni, parametroFiltroSeleccionado, parametroFiltroSeleccionado));
                    break;
                case "like":
                    sql.where(Clause.like(colIni, parametroFiltroSeleccionado));
                    break;
                case "not between":
                    sql.where(Clause.between(colIni, parametroFiltroSeleccionado, parametroFiltroSeleccionado));
                    break;
                case "not exists":
                    sql.where(Clause.between(colIni, parametroFiltroSeleccionado, parametroFiltroSeleccionado));
                    break;
                case "not in":
                    sql.where(Clause.notIn(colIni, parametroFiltroSeleccionado));
                    break;
                case "not like":
                    sql.where(Clause.like(colIni, parametroFiltroSeleccionado));
                    break;
                case "is":
                    sql.where(Clause.like(colIni, parametroFiltroSeleccionado));
                    break;
                case "is not":
                    sql.where(Clause.like(colIni, parametroFiltroSeleccionado));
                    break;
            }
            query = sql.toString();

            int i = 0;
            for (Map.Entry<String, String> filtroIng : lstFiltrosParaAsignar.entrySet()) {
                //for (String filtroIng : lstFiltrosParaAsignar) {
                if (nomFuncion == null) {
                    if (filtroIng.getValue().equals(parametroFiltroSeleccionado)) {
                        if (contarFiltrosAsignados >= 1) {
                            filtroSubreporte = filtroSubreporte + "|";
                        }
                        filtroSubreporte = filtroSubreporte + parametroFiltroSeleccionado;
                        lstFiltrosParaAsignar.remove(filtroIng.getKey());
                        break;
                    }
                } else {
                    if (i >= 1) {
                        filtroSubreporte = filtroSubreporte + "|";
                    }
                    filtroSubreporte = filtroSubreporte + filtroIng.getValue();
                    i++;
                }
            }
            //System.err.println("filtroSubreporte " + filtroSubreporte);
            contarFiltrosAsignados++;
            columnaAsignada = "";
            opRelacionalSeleccionado = new MetCatalogo();
            parametroFiltroSeleccionado = "";
            opLogicoSeleccionado = new MetCatalogo();
            baseControlador.addSuccessMessage("Parametro asignado");
        } catch (Exception ex) {
            baseControlador.addErrorMessage("Error: " + ex.getMessage());
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void onTabChange(TabChangeEvent e) {
        //System.out.println("The next Tab Title is :: " + e.getTab().getId());
        try {
            if (e.getTab().getId().equals("tab0")) {
                activarquery = false;
                activarseleccion = true;
            }
            if (e.getTab().getId().equals("tab1")) {
                activarquery = true;
                activarseleccion = false;
            }
            if (e.getTab().getId().equals("tab2")) {
                //cargarTipoDatos();
                lstTipoCatalogo = baseControlador.getCacheTimer().getMetTipoCatalogoList();
            } else if (e.getTab().getId().equals("tab3") && lstOperadoresRelacionales.isEmpty()) {
                cargarOperadores();
                required = true;
            } else if (!e.getTab().getId().equals("tab3")) {
                required = false;
                renderOpLogico = true;
            }
            int tamañoFiltrosAsg = lstFiltrosIngresados.size() + lstFiltrosSQLTabla.size();
            if (e.getTab().getId().equals("tab3") && lstFiltrosParaAsignar.size() == tamañoFiltrosAsg) {
                renderOpLogico = true;
            } else if (e.getTab().getId().equals("tab3") && lstFiltrosParaAsignar.size() != tamañoFiltrosAsg) {
                required = true;
                renderOpLogico = false;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void onTabClose(TabCloseEvent event) {
        try {
            activarAccordion = null;
            if (event.getTab().getId().equals("tab3")) {
                required = false;
                renderOpLogico = true;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void ingresarInfoColumna() {
        try {
            boolean colRepetidas = false;
            for (int j = 0; j < lstObjColumnas.size(); j++) {
                String wordj = String.valueOf(lstObjColumnas.get(j)[0]);
                for (int i = lstObjColumnas.size() - 1; i > j; i--) {
                    String wordi = String.valueOf(lstObjColumnas.get(i)[0]);
                    if (wordi != null && wordj != null && wordi.compareTo(wordj) == 0 && i != j) {
                        baseControlador.addWarningMessage("El nombre de la columna " + (j + 1) + " NO puede ser igual al nombre de la columna " + (i + 1));
                        colRepetidas = true;
                    }
                }
            }
            if (colRepetidas == false) {
                /*FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Columnas Actualizadas");
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);*/
                baseControlador.addSuccessMessage("Columnas Actualizadas");
                PrimeFaces.current().executeScript("PF('widgDialogEditarColumnas').hide()");
                PrimeFaces.current().ajax().update("frm:msgs");
                            
            } else {
                baseControlador.addErrorMessage("No se puede actualizar");
                PrimeFaces.current().ajax().update("frm:idAccordionReportes:messagesActualizarCol");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void grabarInfoReporte() {
        try {

            List<RepoSubreporte> listaSubReportes = new ArrayList<>();

            RepoProcedimiento repro = baseControlador.getRepoProcedimientoServicioRemote().buscarEjecutarConsulta("buscarProcMaxPorConexion", Arrays.asList(conexion));
            int secuencial = 0;
            if (repro == null) {
                secuencial = 1;
            } else {
                secuencial = repro.getIdProc() + 1;
            }

            repoProcedimientoNuevo.setNombre("proc_" + conexion.replaceAll("\\s", "").toLowerCase() + "_" + secuencial);
            repoProcedimientoNuevo.setSql(query);
            repoProcedimientoNuevo.setVariables(nomFuncion);
            repoProcedimientoNuevo.setConexion(conexion);
            baseControlador.getRepoProcedimientoServicioRemote().crearProcedimiento(repoProcedimientoNuevo);
            repoProcedimientoNuevo = baseControlador.getRepoProcedimientoServicioRemote().buscarPorNombre(repoProcedimientoNuevo.getNombre());

            if (addNuevoReporte == false) {

                RepoFiltro refi = baseControlador.getRepoFiltroServicioRemote().buscarEjecutarConsulta("buscarFiltroMax", Arrays.asList());
                int secuencialFi = 0;
                if (refi == null) {
                    secuencialFi = 1;
                } else {
                    secuencialFi = refi.getIdFiltro() + 1;
                }

                repoFiltroNuevo.setNombre("flt_" + conexion.replaceAll("\\s", "").toLowerCase() + "_" + secuencialFi);
                if (parametrosDeFiltrosCtlgo != null) {
                    String parametrosFCtlgo = String.join("|", parametrosDeFiltrosCtlgo);
                    String aliasTipoCtlgo = String.join("|", filtroTipoCatalogo);
                    String nomFiltro = String.join("|", nomFiltros);
                    String tipoFiltro = String.join("|", tipoFiltros);

                    /*if (!tipoFiltro.isEmpty()) {
                        if (tipoFiltro.equals("|")) {
                            tipoFiltro = "";
                        } else if (tipoFiltro.startsWith("|")) {
                            tipoFiltro = tipoFiltro.substring(1);
                        } else if (tipoFiltro.endsWith("|")) {
                            tipoFiltro = tipoFiltro.substring(0, tipoFiltro.lastIndexOf("|"));
                        }
                        parametrosFCtlgo = parametrosFCtlgo + "-" + aliasTipoCtlgo + "-" + nomFiltro + "-" + tipoFiltro;
                    } else {
                        parametrosFCtlgo = parametrosFCtlgo + "-" + aliasTipoCtlgo + "-" + nomFiltro;
                    }*/
                    parametrosFCtlgo = parametrosFCtlgo + "-" + aliasTipoCtlgo + "-" + nomFiltro + "-" + tipoFiltro;
                    //System.err.println("fcat " + fCat);

                    repoFiltroNuevo.setCatalogo(parametrosFCtlgo);
                }
                if (parametrosDeFiltrosQuery != null) {
                    String parametrosFCtlgo = String.join("|", parametrosDeFiltrosQuery);
                    //String aliasTipoCtlgo = String.join("|", filtrosQuery);
                    String nomFiltro = String.join("|", nomFiltrosQuery);
                    parametrosFCtlgo = parametrosFCtlgo + "-" + filtrosQuery + "-" + nomFiltro;
                    //System.err.println("fcat " + fCat);
                    repoFiltroNuevo.setSql(parametrosFCtlgo);
                }
                baseControlador.getRepoFiltroServicioRemote().crearFiltro(repoFiltroNuevo);
                repoFiltroNuevo = baseControlador.getRepoFiltroServicioRemote().buscarPorNombre(repoFiltroNuevo.getNombre());

                repoReporteNuevo.setCampo("R_" + conexion.replaceAll("\\s", "").toUpperCase() + "_" + repoFiltroNuevo.getIdFiltro());
                repoReporteNuevo.setCodFiltro(repoFiltroNuevo);
                repoReporteNuevo.setEstadoLogico(true);
                repoReporteNuevo.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                baseControlador.getRepoReporteServicioRemote().crearReporte(repoReporteNuevo);
                repoReporteNuevo = baseControlador.getRepoReporteServicioRemote().consultaReportePorAlias(repoReporteNuevo.getCampo());
            }
            listaSubReportes = baseControlador.getRepoSubreporteServicioRemote().listarSubreportes(repoReporteNuevo);
            repoSubreporteNuevo.setCodReporte(repoReporteNuevo);
            repoSubreporteNuevo.setCodProc(repoProcedimientoNuevo);
            repoSubreporteNuevo.setFiltro(filtroSubreporte);
            repoSubreporteNuevo.setOrden(listaSubReportes.size() + 1);
            repoSubreporteNuevo.setAncho("100%");
            repoSubreporteNuevo.setScroll('0');
            repoSubreporteNuevo.setSwidth("0");
            repoSubreporteNuevo.setPaginador('1');
            repoSubreporteNuevo.setFilaEstilo("fila=3|texto=TOTAL|estilo=estiloTotales estiloTextoCandaraMed");
            repoSubreporteNuevo.setExportar('1');
            repoSubreporteNuevo.setSubtitulo("subt=0");
            baseControlador.getRepoSubreporteServicioRemote().crearSubreporte(repoSubreporteNuevo);
            repoSubreporteNuevo = baseControlador.getRepoSubreporteServicioRemote().buscarPorProcedimiento(repoProcedimientoNuevo);

            int j = 1;
            for (int i = 0; i < lstObjColumnas.size(); i++) {
                repoColumnaNuevo = new RepoColumna();

                if (lstObjColumnas.get(i)[4].toString().equals("true")) {
                    lstObjColumnas.get(i)[4] = 1;
                } else {
                    lstObjColumnas.get(i)[4] = 0;
                }
                if (lstObjColumnas.get(i)[5].toString().equals("true")) {
                    lstObjColumnas.get(i)[5] = 1;
                } else {
                    lstObjColumnas.get(i)[5] = 0;
                }

                String atributo = "anchoColumna=" + lstObjColumnas.get(i)[2] + "|" + "alineacion=leftColumn" + "|" + "filtro=" + lstObjColumnas.get(i)[4]
                        + "|" + "orden=" + lstObjColumnas.get(i)[5] + "|" + "tipoDato=" + lstObjColumnas.get(i)[6] + "|" + "clase=" + lstObjColumnas.get(i)[7];
                //System.err.println("atributo " + atributo);
                repoColumnaNuevo.setCodSubr(repoSubreporteNuevo);
                repoColumnaNuevo.setCampo(lstObjColumnas.get(i)[0].toString().replaceAll("\\s", ""));
                repoColumnaNuevo.setNombre(lstObjColumnas.get(i)[0].toString());
                repoColumnaNuevo.setAtributo("visualizar=10|columna=" + (i) + "|" + atributo);
                repoColumnaNuevo.setComponente("header");
                repoColumnaNuevo.setOrden(Integer.valueOf(lstObjColumnas.get(i)[1].toString()));
                repoColumnaNuevo.setEstadoLogico(true);
                baseControlador.getRepoColumnaServicioRemote().crearColumna(repoColumnaNuevo);
                j++;
            }
            repoColumnaNuevo = new RepoColumna();
            repoColumnaNuevo.setCodSubr(repoSubreporteNuevo);
            repoColumnaNuevo.setCampo("TotalRegistros");
            repoColumnaNuevo.setNombre("Total Registros");
            repoColumnaNuevo.setAtributo("imagen=resumen1.png|valor=cuenta:0;campo1:0,xxx");
            repoColumnaNuevo.setComponente("resumen");
            repoColumnaNuevo.setOrden(j);
            repoColumnaNuevo.setEstadoLogico(true);
            baseControlador.getRepoColumnaServicioRemote().crearColumna(repoColumnaNuevo);

            if (lstAgrupObjColumnas != null) {
                if (!lstAgrupObjColumnas.isEmpty()) {
                    int i = 1;
                    for (Object[] objt : lstAgrupObjColumnas) {
                        repoColumnaNuevo = new RepoColumna();
                        repoColumnaNuevo.setCodSubr(repoSubreporteNuevo);
                        repoColumnaNuevo.setCampo(String.valueOf(objt[0]));
                        repoColumnaNuevo.setNombre(String.valueOf(objt[0]));
                        repoColumnaNuevo.setAtributo("visualizar=10|" + "colspan=" + Integer.valueOf(String.valueOf(objt[1])) + "|anchoColumna=" + Integer.valueOf(String.valueOf(objt[2])) + "|alineacion=centeredColumn");
                        repoColumnaNuevo.setComponente("Grupo");
                        repoColumnaNuevo.setOrden(i);
                        repoColumnaNuevo.setEstadoLogico(true);
                        baseControlador.getRepoColumnaServicioRemote().crearColumna(repoColumnaNuevo);
                        i++;
                    }
                }
            }

            idReporte = repoReporteNuevo;
            verFiltroCatalogo = true;
            verPanelReporte = true;
            lstObjColumnas = new ArrayList<>();
            baseControlador.addSuccessMessage("Su Reporte fue creado exitosamente..!");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void resetPagina() {
        try {
            addNuevoReporte = true;
            renderOpLogico = true;
            repoSubreporteNuevo = new RepoSubreporte();
            repoColumnaNuevo = new RepoColumna();
            repoProcedimientoNuevo = new RepoProcedimiento();
            lstNomColumnas = new ArrayList<>();

            lstTipoDatos = new ArrayList<>();
            lstOperadoresRelacionales = new ArrayList<>();
            lstOperadoresLogicos = new ArrayList<>();
            lstRepoFiltros = new ArrayList<>();

            if (treeNode != null) {
                treeNode.getChildren().clear();
                nivel1 = null;
                expandirArbol(treeNode, true);
            }
            padres.clear();
            objPadres.clear();
            renderSlcTabla = true;
            joins.clear();
            if (lstReferencias != null) {
                lstReferencias.clear();
            }
            //lstFiltrosParaAsignar = new LinkedHashMap<>();
            filtroSubreporte = "";
            query = "";
            sql = new Select();
            textAreaColumna = "";
            lstNomTablas = new ArrayList<>();
            lstAgrupObjColumnas = new ArrayList<>();
            lstColumnasSeleccionadas = new ArrayList<>();
            List<Object[]> listTablas = baseControlador.getActualizaBDServicioRemote().recuperaNombresTbl(conexion, esquemaOriginal);
            for (int i = 0; i < listTablas.size(); i++) {
                lstNomTablas.add(listTablas.get(i)[0].toString());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            DataTable idDataTable = (DataTable) event.getComponent();
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, List<Object>> nuevoRowMap = oMapper.convertValue(event.getObject(), Map.class
            );

            for (Map.Entry<String, List<Object>> cambiarRowMap : nuevoRowMap.entrySet()) {
                if (idDataTable.getId().equals("idDtaTableLstFiltros")) {
                    /*for (Entry<String, List<MetCatalogo>> filtroReporte : mapFiltroReporte.entrySet()) {
                        if (cambiarRowMap.getKey().equals(filtroReporte.getKey())) {
                            mapFiltroReporte.remove(cambiarRowMap.getKey());
                            mapFiltroReporte.put(cambiarRowMap.getValue().get(1).toString(), filtroReporte.getValue());
                            break;
                        }
                    }*/
                    lstFiltrosIngresados.remove(cambiarRowMap.getKey());
                    lstFiltrosIngresados.put(cambiarRowMap.getValue().get(1).toString(), cambiarRowMap.getValue().toArray());
                }
                if (idDataTable.getId().equals("idDtaTableFiltrosSQLTabla")) {
                    /*for (Entry<String, Map<String, Object>> filtroReporteObj : mapFiltroReporteObj.entrySet()) {
                        if (cambiarRowMap.getKey().equals(filtroReporteObj.getKey())) {
                            mapFiltroReporteObj.remove(cambiarRowMap.getKey());
                            mapFiltroReporteObj.put(cambiarRowMap.getValue().get(1).toString(), filtroReporteObj.getValue());
                            break;
                        }
                    }*/
                    lstFiltrosSQLTabla.remove(cambiarRowMap.getKey());
                    lstFiltrosSQLTabla.put(cambiarRowMap.getValue().get(1).toString(), cambiarRowMap.getValue().toArray());
                }
                lstFiltrosParaAsignar.remove(cambiarRowMap.getKey());
                lstFiltrosParaAsignar.put(cambiarRowMap.getValue().get(1).toString(), cambiarRowMap.getValue().get(1).toString());
                /*for (Entry<String, String> nombreFiltroReporte : mapNombreFiltroReporte.entrySet()) {
                    if (cambiarRowMap.getKey().equals(nombreFiltroReporte.getKey())) {
                        mapNombreFiltroReporte.remove(cambiarRowMap.getKey());
                        mapNombreFiltroReporte.put(cambiarRowMap.getValue().get(1).toString(), cambiarRowMap.getValue().get(0).toString());
                        break;
                    }
                }*/
                baseControlador.addSuccessMessage("Fila Editada");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void addFiltroQuery() {
        try {
            if (parametroFiltroSeleccionado == null) {
                parametroFiltroSeleccionado = "";
            }
            query = query + parametroFiltroSeleccionado;
            //System.err.println("sqlReporte2: " + query);
            parametroFiltroSeleccionado = "";
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void ingresarAgrupaColumna() {
        Object[] obj = new Object[3];
        if (nomAgrupaCol != null) {
            if (nomAgrupaCol.length() == 0) {
                nomAgrupaCol = "";
            }
        } else {
            nomAgrupaCol = "";
        }
        obj[0] = nomAgrupaCol;
        obj[1] = lstAgrupaColumnasSeleccionadas.size();
        obj[2] = anchoAgrupaCol;
        lstAgrupObjColumnas.add(obj);
        nomAgrupaCol = "";
        //numAgrupaCol = null;
        anchoAgrupaCol = null;
        lstAgrupaColumnasSeleccionadas = null;
    }

    public void guardarAgrupaColumna() {
        baseControlador.addSuccessMessage("Lista Guardada");
        PrimeFaces.current().executeScript("PF('widgDialogEncabezadosColumnas').hide()");
        PrimeFaces.current().ajax().update("frm:msgs");
    }

    public void cargarNumSpinner() {
        setNumAgrupaCol(lstAgrupaColumnasSeleccionadas.size());
        //System.err.println("numAgrupaCol " + numAgrupaCol);
        //PrimeFaces.current().ajax().update("frm:idAccordionReportes:idIptNumAgrupaCol");
    }

    public void guardarFuncion() {
        try {
            int ejecutarSql = baseControlador.getActualizaBDServicioRemote().ejecutaSql(conexion, funcion);
            //System.err.println("ejecutarSQL" + ejecutarSql);
            if (ejecutarSql != -1) {
                baseControlador.addSuccessMessage("Función Guardada");
                PrimeFaces.current().executeScript("PF('widgDialogFunc').hide()");
            } else {
                baseControlador.addErrorMessage("Error al guardar la función");
            }
            PrimeFaces.current().ajax().update("frm:msgs");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

//</editor-fold>
}
