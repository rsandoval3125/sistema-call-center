/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.conexion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lponce
 */
public class DataTabla {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private List<Object> tabla;
    private Map<Integer, String> columnas;
    private List<DataColumna> dataColumnas;
    private List<String> dataStringBuilder;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public DataTabla() {
        tabla = new ArrayList<>();
        columnas = new HashMap<>();
        dataStringBuilder=new ArrayList<>();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public List<Object> getTabla() {
        return tabla;
    }

    public void setTabla(List<Object> tabla) {
        this.tabla = tabla;
    }

    public Map<Integer, String> getColumnas() {
        return columnas;
    }

    public void setColumnas(Map<Integer, String> columnas) {
        this.columnas = columnas;
    }

    public List<DataColumna> getDataColumnas() {
        return dataColumnas;
    }

    public void setDataColumnas(List<DataColumna> dataColumnas) {
        this.dataColumnas = dataColumnas;
    }

    public List<String> getDataStringBuilder() {
        return dataStringBuilder;
    }

    public void setDataStringBuilder(List<String> dataStringBuilder) {
        this.dataStringBuilder = dataStringBuilder;
    }
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    //</editor-fold>
}
