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
public class CaptVarV implements Serializable{
    private BigInteger idDetV;
    private Integer codCab;
    private Integer codVar;
    private Integer numDet;
    private String valor;
    private String tipoSeccion;
    
    private String valorAnterior;

    public CaptVarV(BigInteger idDetV, Integer codCab, Integer codVar, Integer numDet, String valor, String tipoSeccion, String valorAnterior) {
        this.idDetV = idDetV;
        this.codCab = codCab;
        this.codVar = codVar;
        this.numDet = numDet;
        this.valor = valor;
        this.tipoSeccion = tipoSeccion;
        this.valorAnterior = valorAnterior;
    }
    
    

    public Integer getCodVar() {
        return codVar;
    }

    public void setCodVar(Integer codVar) {
        this.codVar = codVar;
    }

    public String getTipoSeccion() {
        return tipoSeccion;
    }

    public void setTipoSeccion(String tipoSeccion) {
        this.tipoSeccion = tipoSeccion;
    }

    public BigInteger getIdDetV() {
        return idDetV;
    }

    public void setIdDetV(BigInteger idDetV) {
        this.idDetV = idDetV;
    }

    public Integer getCodCab() {
        return codCab;
    }

    public void setCodCab(Integer codCab) {
        this.codCab = codCab;
    }

    public Integer getNumDet() {
        return numDet;
    }

    public void setNumDet(Integer numDet) {
        this.numDet = numDet;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }
    
    
    
    
}
