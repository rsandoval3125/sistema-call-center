/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.clases;

import java.io.Serializable;

/**
 *
 * @author dguano
 */
public class CaptManzana implements Serializable {

    private String codigoBdd;
    private String codigoSector;
    private String codigoManzanaOriginal;
    private String codigoManzana3Digitos;
    private boolean habilitada;
    private boolean seleccionado;
    private boolean seleccionadoUnir;
    private boolean seleccionadoDividir;
    private boolean utilizadaEnUnionOtroSector;

    public CaptManzana(String codigoSector, String codigoManzanaOriginal, String codigoBdd,boolean MOSU) {
        this.codigoSector = codigoSector;
        this.codigoManzanaOriginal = codigoManzanaOriginal;
        this.habilitada = false;
        this.codigoBdd = codigoBdd;
        this.utilizadaEnUnionOtroSector=MOSU;
    }
    public CaptManzana(String codigoSector, String codigoManzanaOriginal, String codigoBdd) {
        this.codigoSector = codigoSector;
        this.codigoManzanaOriginal = codigoManzanaOriginal;
        this.habilitada = false;
        this.codigoBdd = codigoBdd;
        this.utilizadaEnUnionOtroSector=false;
    }

    public String getCodigoSector() {
        return codigoSector;
    }

    public void setCodigoSector(String codigoSector) {
        this.codigoSector = codigoSector;
    }

    public String getCodigoManzanaOriginal() {
        return codigoManzanaOriginal;
    }

    public void setCodigoManzanaOriginal(String codigoManzanaOriginal) {
        this.codigoManzanaOriginal = codigoManzanaOriginal;
    }

    public String getCodigoManzana3Digitos() {
        Integer manz = Integer.valueOf(codigoManzanaOriginal);
        if (manz <= 99) {
            if (manz <= 9) {
                codigoManzana3Digitos = "00" + manz;
            } else {
                codigoManzana3Digitos = "0" + manz;
            }
        } else {
            codigoManzana3Digitos = "000";
        }
        return codigoManzana3Digitos;
    }

    public void setCodigoManzana3Digitos(String codigoManzana3Digitos) {
        this.codigoManzana3Digitos = codigoManzana3Digitos;
    }

    public boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean getSeleccionadoUnir() {
        return seleccionadoUnir;
    }

    public void setSeleccionadoUnir(boolean seleccionadoUnir) {
        this.seleccionadoUnir = seleccionadoUnir;
    }

    public boolean getSeleccionadoDividir() {
        return seleccionadoDividir;
    }

    public void setSeleccionadoDividir(boolean seleccionadoDividir) {
        this.seleccionadoDividir = seleccionadoDividir;
    }

    public boolean isHabilitada() {
        return habilitada;
    }

    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }

    public String getCodigoBdd() {
        return codigoBdd;
    }

    public void setCodigoBdd(String codigoBdd) {
        this.codigoBdd = codigoBdd;
    }

    public boolean isUtilizadaEnUnionOtroSector() {
        return utilizadaEnUnionOtroSector;
    }

    public void setUtilizadaEnUnionOtroSector(boolean utilizadaEnUnionOtroSector) {
        this.utilizadaEnUnionOtroSector = utilizadaEnUnionOtroSector;
    }
    
    

}
