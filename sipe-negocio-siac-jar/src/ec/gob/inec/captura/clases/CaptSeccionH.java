/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.clases;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author dguano
 */
public class CaptSeccionH implements Serializable{
    private Integer codSeccion;
    private String tipoSeccion;
    private List<CaptFilaH> filas;
    private int columnas;

    public CaptSeccionH() {
    }

    public CaptSeccionH(Integer codSeccion) {
        this.codSeccion = codSeccion;
    }

    public CaptSeccionH(Integer codSeccion, String tipoSeccion, int columnas) {
        this.codSeccion = codSeccion;
        this.tipoSeccion = tipoSeccion;
        this.columnas = columnas;
    }
    
    

    public String getTipoSeccion() {
        return tipoSeccion;
    }

    public void setTipoSeccion(String tipoSeccion) {
        this.tipoSeccion = tipoSeccion;
    }


    public List<CaptFilaH> getFilas() {
        return filas;
    }

    public void setFilas(List<CaptFilaH> filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public Integer getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(Integer codSeccion) {
        this.codSeccion = codSeccion;
    }
    
    
    
}
