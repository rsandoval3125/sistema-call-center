/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.r;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author lponce
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class RControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    @ManagedProperty("#{baseControlador}")
    private BaseControlador baseControlador;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public RControlador() {

    }

    @PostConstruct
    public void init() {

    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public BaseControlador getBaseControlador() {
        return baseControlador;
    }

    public void setBaseControlador(BaseControlador baseControlador) {
        this.baseControlador = baseControlador;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public List<java.lang.String> ejecutarSentencias(int indice, List<java.lang.String> sentencias) {
        return baseControlador.getEjecutaRRemote().ejecutarSentencias(indice, sentencias);
    }

    public int nuevaConexion() {
        return baseControlador.getEjecutaRRemote().nuevaConexion();
    }

    public void cerrarConexion(int indice) {
        baseControlador.getEjecutaRRemote().cerrarConexion(indice);
    }

    /*public ec.gob.inec.clases.DataTabla recuperarArchivo(String pathFile, int tipoFile) {
        cambiaURLWS();
        EjecutaRWS port = ejecutaRWS_Service.getEjecutaRWSPort();
        ec.gob.inec.r_ejb.war.EjecutaRWSC.DataTabla dataTable = port.recuperarArchivo(pathFile, tipoFile);
        ec.gob.inec.clases.DataTabla dataTablaResult = new ec.gob.inec.clases.DataTabla();
        Map<Integer, String> columnas = new LinkedHashMap<>();
        for (Entry lista : dataTable.getColumnas().getEntry()) {
            columnas.put(lista.getKey(), lista.getValue());
        }
        dataTablaResult.setColumnas(columnas);
        List<Object> dataResult = new ArrayList<>();
        for (ec.gob.inec.r_ejb.war.EjecutaRWSC.AnyTypeArray anyTypeArray : dataTable.getTabla()) {
            List<Object> arrayResult = new ArrayList<>();
            arrayResult = anyTypeArray.getItem();
            dataResult.add(arrayResult);
        }
        dataTablaResult.setTabla(dataResult);
        return dataTablaResult;
    }*/
    //</editor-fold>
}
