/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.clases;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author dguano
 */
public class CaptFilaH  implements Serializable{
    private Integer idDetH;
    private Integer codCab;
    private Integer codSeccion;
    private Integer numDet;
    private Integer orden;
     private String tipoSeccion;
     private Integer columnas;
     
     private String val01;
     private String val02;
     private String val03;
     private String val04;
     private String val05;
     private String val06;
     private String val07;
     private String val08;
     private String val09;
     private String val10;
     private String val11;
     private String val12;
     private String val13;
     private String val14;
     private String val15;
     private String val16;
     private String val17;
     private String val18;
     private String val19;
     private String val20;
     private String val21;
     private String val22;
     private String val23;
     private String val24;
     private String val25;
     private String val26;
     private String val27;
     private String val28;
     private String val29;
     private String val30;
     
     private boolean conError;

    public CaptFilaH(Integer codSeccion, Integer orden, String tipoSeccion) {
        this.codSeccion = codSeccion;
        this.orden = orden;
        this.tipoSeccion = tipoSeccion;
    }

    public CaptFilaH(Integer idDetH, Integer codCab, Integer codSeccion, Integer numDet, Integer orden, String tipoSeccion, Integer columnas, String val01, String val02, String val03, String val04, String val05, String val06, String val07, String val08, String val09, String val10, String val11, String val12, String val13, String val14, String val15, String val16, String val17, String val18, String val19, String val20, String val21, String val22, String val23, String val24, String val25, String val26, String val27, String val28, String val29, String val30) {
        this.idDetH = idDetH;
        this.codCab = codCab;
        this.codSeccion = codSeccion;
        this.numDet = numDet;
        this.orden = orden;
        this.tipoSeccion = tipoSeccion;
        this.columnas = columnas;
        this.val01 = val01;
        this.val02 = val02;
        this.val03 = val03;
        this.val04 = val04;
        this.val05 = val05;
        this.val06 = val06;
        this.val07 = val07;
        this.val08 = val08;
        this.val09 = val09;
        this.val10 = val10;
        this.val11 = val11;
        this.val12 = val12;
        this.val13 = val13;
        this.val14 = val14;
        this.val15 = val15;
        this.val16 = val16;
        this.val17 = val17;
        this.val18 = val18;
        this.val19 = val19;
        this.val20 = val20;
        this.val21 = val21;
        this.val22 = val22;
        this.val23 = val23;
        this.val24 = val24;
        this.val25 = val25;
        this.val26 = val26;
        this.val27 = val27;
        this.val28 = val28;
        this.val29 = val29;
        this.val30 = val30;
    }
     
     

    public Integer getIdDetH() {
        return idDetH;
    }

    public void setIdDetH(Integer idDetH) {
        this.idDetH = idDetH;
    }

    public Integer getCodCab() {
        return codCab;
    }

    public void setCodCab(Integer codCab) {
        this.codCab = codCab;
    }

    public Integer getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(Integer codSeccion) {
        this.codSeccion = codSeccion;
    }

    public Integer getNumDet() {
        return numDet;
    }

    public void setNumDet(Integer numDet) {
        this.numDet = numDet;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTipoSeccion() {
        return tipoSeccion;
    }

    public void setTipoSeccion(String tipoSeccion) {
        this.tipoSeccion = tipoSeccion;
    }

    public String getVal01() {
        return val01;
    }

    public void setVal01(String val01) {
        this.val01 = val01;
    }

    public String getVal02() {
        return val02;
    }

    public void setVal02(String val02) {
        this.val02 = val02;
    }

    public String getVal03() {
        return val03;
    }

    public void setVal03(String val03) {
        this.val03 = val03;
    }

    public String getVal04() {
        return val04;
    }

    public void setVal04(String val04) {
        this.val04 = val04;
    }

    public String getVal05() {
        return val05;
    }

    public void setVal05(String val05) {
        this.val05 = val05;
    }

    public String getVal06() {
        return val06;
    }

    public void setVal06(String val06) {
        this.val06 = val06;
    }

    public String getVal07() {
        return val07;
    }

    public void setVal07(String val07) {
        this.val07 = val07;
    }

    public String getVal08() {
        return val08;
    }

    public void setVal08(String val08) {
        this.val08 = val08;
    }

    public String getVal09() {
        return val09;
    }

    public void setVal09(String val09) {
        this.val09 = val09;
    }

    public String getVal10() {
        return val10;
    }

    public void setVal10(String val10) {
        this.val10 = val10;
    }

    public String getVal11() {
        return val11;
    }

    public void setVal11(String val11) {
        this.val11 = val11;
    }

    public String getVal12() {
        return val12;
    }

    public void setVal12(String val12) {
        this.val12 = val12;
    }

    public String getVal13() {
        return val13;
    }

    public void setVal13(String val13) {
        this.val13 = val13;
    }

    public String getVal14() {
        return val14;
    }

    public void setVal14(String val14) {
        this.val14 = val14;
    }

    public String getVal15() {
        return val15;
    }

    public void setVal15(String val15) {
        this.val15 = val15;
    }

    public String getVal16() {
        return val16;
    }

    public void setVal16(String val16) {
        this.val16 = val16;
    }

    public String getVal17() {
        return val17;
    }

    public void setVal17(String val17) {
        this.val17 = val17;
    }

    public String getVal18() {
        return val18;
    }

    public void setVal18(String val18) {
        this.val18 = val18;
    }

    public String getVal19() {
        return val19;
    }

    public void setVal19(String val19) {
        this.val19 = val19;
    }

    public String getVal20() {
        return val20;
    }

    public void setVal20(String val20) {
        this.val20 = val20;
    }

    public String getVal21() {
        return val21;
    }

    public void setVal21(String val21) {
        this.val21 = val21;
    }

    public String getVal22() {
        return val22;
    }

    public void setVal22(String val22) {
        this.val22 = val22;
    }

    public String getVal23() {
        return val23;
    }

    public void setVal23(String val23) {
        this.val23 = val23;
    }

    public String getVal24() {
        return val24;
    }

    public void setVal24(String val24) {
        this.val24 = val24;
    }

    public String getVal25() {
        return val25;
    }

    public void setVal25(String val25) {
        this.val25 = val25;
    }

    public String getVal26() {
        return val26;
    }

    public void setVal26(String val26) {
        this.val26 = val26;
    }

    public String getVal27() {
        return val27;
    }

    public void setVal27(String val27) {
        this.val27 = val27;
    }

    public String getVal28() {
        return val28;
    }

    public void setVal28(String val28) {
        this.val28 = val28;
    }

    public String getVal29() {
        return val29;
    }

    public void setVal29(String val29) {
        this.val29 = val29;
    }

    public String getVal30() {
        return val30;
    }

    public void setVal30(String val30) {
        this.val30 = val30;
    }
    

    public Integer getColumnas() {
        return columnas;
    }

    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

    public boolean getConError() {
        return conError;
    }

    public void setConError(boolean conError) {
        this.conError = conError;
    }

    
     
     
}
