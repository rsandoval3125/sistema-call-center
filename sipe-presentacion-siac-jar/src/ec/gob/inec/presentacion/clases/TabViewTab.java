/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases;

import java.util.List;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author lponce
 */
public class TabViewTab {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private String nombreTab;
    private String idTab;
    private List<Object[]> nombresColumnas;
    private List<Object[]> filas;
    private List<Object[]> resumenTabla;
    private List<List<Object[]>> resumenesTabla;
    private List<Object[]> nombreColumnasGroup;
    private List<Object[]> nombreColumnasFooter;
    private String anchoTabla;
    private int tieneFooter;
    private int tieneColumnasGrupo;
    private int numeroResumen;
    private int scroll;
    private int scrollWidth;
    private int paginador;
    private int filaEstilo;
    private String textoEstilo;
    private String estiloFila;
    private int tieneTabla;
    private int tieneGrafico;
    private LineChartModel lineChartModel;
    private int exportar;
    private int editar;
    private List<Object[]> accionesTabla;
    private String [] subtitulos;
    //</editor-fold>

    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public String getNombreTab() {
        return nombreTab;
    }

    public void setNombreTab(String nombreTab) {
        this.nombreTab = nombreTab;
    }

    public String getIdTab() {
        return idTab;
    }

    public void setIdTab(String idTab) {
        this.idTab = idTab;
    }

    public List<Object[]> getNombresColumnas() {
        return nombresColumnas;
    }

    public void setNombresColumnas(List<Object[]> nombresColumnas) {
        this.nombresColumnas = nombresColumnas;
    }

    public List<Object[]> getFilas() {
        return filas;
    }

    public void setFilas(List<Object[]> filas) {
        this.filas = filas;
    }

    public List<Object[]> getResumenTabla() {
        return resumenTabla;
    }

    public void setResumenTabla(List<Object[]> resumenTabla) {
        this.resumenTabla = resumenTabla;
    }

    public List<Object[]> getNombreColumnasGroup() {
        return nombreColumnasGroup;
    }

    public void setNombreColumnasGroup(List<Object[]> nombreColumnasGroup) {
        this.nombreColumnasGroup = nombreColumnasGroup;
    }

    public List<Object[]> getNombreColumnasFooter() {
        return nombreColumnasFooter;
    }

    public void setNombreColumnasFooter(List<Object[]> nombreColumnasFooter) {
        this.nombreColumnasFooter = nombreColumnasFooter;
    }

    public String getAnchoTabla() {
        return anchoTabla;
    }

    public void setAnchoTabla(String anchoTabla) {
        this.anchoTabla = anchoTabla;
    }

    public int getTieneFooter() {
        return tieneFooter;
    }

    public void setTieneFooter(int tieneFooter) {
        this.tieneFooter = tieneFooter;
    }

    public int getTieneColumnasGrupo() {
        return tieneColumnasGrupo;
    }

    public void setTieneColumnasGrupo(int tieneColumnasGrupo) {
        this.tieneColumnasGrupo = tieneColumnasGrupo;
    }

    public int getNumeroResumen() {
        return numeroResumen;
    }

    public void setNumeroResumen(int numeroResumen) {
        this.numeroResumen = numeroResumen;
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public int getScrollWidth() {
        return scrollWidth;
    }

    public void setScrollWidth(int scrollWidth) {
        this.scrollWidth = scrollWidth;
    }

    public int getPaginador() {
        return paginador;
    }

    public void setPaginador(int paginador) {
        this.paginador = paginador;
    }

    public List<List<Object[]>> getResumenesTabla() {
        return resumenesTabla;
    }

    public void setResumenesTabla(List<List<Object[]>> resumenesTabla) {
        this.resumenesTabla = resumenesTabla;
    }

    public int getFilaEstilo() {
        return filaEstilo;
    }

    public void setFilaEstilo(int filaEstilo) {
        this.filaEstilo = filaEstilo;
    }

    public String getTextoEstilo() {
        return textoEstilo;
    }

    public void setTextoEstilo(String textoEstilo) {
        this.textoEstilo = textoEstilo;
    }

    public String getEstiloFila() {
        return estiloFila;
    }

    public void setEstiloFila(String estiloFila) {
        this.estiloFila = estiloFila;
    }

    public int getTieneTabla() {
        return tieneTabla;
    }

    public void setTieneTabla(int tieneTabla) {
        this.tieneTabla = tieneTabla;
    }

    public int getTieneGrafico() {
        return tieneGrafico;
    }

    public void setTieneGrafico(int tieneGrafico) {
        this.tieneGrafico = tieneGrafico;
    }

    public LineChartModel getLineChartModel() {
        return lineChartModel;
    }

    public void setLineChartModel(LineChartModel lineChartModel) {
        this.lineChartModel = lineChartModel;
    }

    public int getExportar() {
        return exportar;
    }

    public void setExportar(int exportar) {
        this.exportar = exportar;
    }

    public int getEditar() {
        return editar;
    }

    public void setEditar(int editar) {
        this.editar = editar;
    }

    public List<Object[]> getAccionesTabla() {
        return accionesTabla;
    }

    public void setAccionesTabla(List<Object[]> accionesTabla) {
        this.accionesTabla = accionesTabla;
    }
    
    public String[] getSubtitulos() {
        return subtitulos;
    }

    public void setSubtitulos(String[] subtitulos) {
        this.subtitulos = subtitulos;
    }
    
    //</editor-fold>

    //<editor-fold desc="Metodos" defaultstate="collapsed">
    //</editor-fold> 
}
