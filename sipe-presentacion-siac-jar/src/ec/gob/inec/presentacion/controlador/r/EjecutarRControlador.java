/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.r;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author lponce
 */
@ManagedBean
@ViewScoped
public class EjecutarRControlador {
    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    List<String> resultadoList;
    List<String> sentenciasList;
    int indice;
    String script;
    String resultado;
    @ManagedProperty("#{rControlador}")
    RControlador rControlador;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public EjecutarRControlador() {
        
    }

    @PostConstruct
    protected void initialize() {
        resultadoList = new ArrayList<String>();
        sentenciasList = new ArrayList<String>();
        indice = rControlador.nuevaConexion();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">

    public RControlador getrControlador() {
        return rControlador;
    }

    public void setrControlador(RControlador rControlador) {
        this.rControlador = rControlador;
    }

    public List<String> getResultadoList() {
        return resultadoList;
    }

    public void setResultadoList(List<String> resultadoList) {
        this.resultadoList = resultadoList;
    }

    public List<String> getSentenciasList() {
        return sentenciasList;
    }

    public void setSentenciasList(List<String> sentenciasList) {
        this.sentenciasList = sentenciasList;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void ejecutarSentenciasR() {
        sentenciasList = new ArrayList<String>();
        resultado = "";
        String[] scriptAr = script.split("\r\n");
        sentenciasList.addAll(Arrays.asList(scriptAr));
        resultadoList = rControlador.ejecutarSentencias(indice, sentenciasList);
        for (String res : resultadoList) {
            resultado = resultado + res + "\r\n";
        }
    }

    /*public DataTabla recuperaDatosArchivo(String pathFile, int tipoIndice) {
        return rController.recuperarArchivo(pathFile, tipoIndice);
    }*/
    //</editor-fold>
}
