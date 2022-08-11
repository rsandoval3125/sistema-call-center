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
public class CaptInfoMuestra implements Serializable{
    private String area;
    private String prov;
    private String cant;
    private String parr;
    private String cong;
    private String oviv;
    private String nhog;
    private String thog;
    private String zona;
    private String sect;
    private String manz;
    private String edif;
    private String nviv;

    public CaptInfoMuestra() {
    }
    
    

    public CaptInfoMuestra(String area, String prov, String cant, String parr, String cong, String oviv, String nhog, String thog, String zona, String sect, String manz, String edif, String nviv) {
        this.area = area;
        this.prov = prov;
        this.cant = cant;
        this.parr = parr;
        this.cong = cong;
        this.oviv = oviv;
        this.nhog = nhog;
        this.thog = thog;
        this.zona = zona;
        this.sect = sect;
        this.manz = manz;
        this.edif = edif;
        this.nviv = nviv;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getParr() {
        return parr;
    }

    public void setParr(String parr) {
        this.parr = parr;
    }

    public String getCong() {
        return cong;
    }

    public void setCong(String cong) {
        this.cong = cong;
    }

    public String getOviv() {
        return oviv;
    }

    public void setOviv(String oviv) {
        this.oviv = oviv;
    }

    public String getNhog() {
        return nhog;
    }

    public void setNhog(String nhog) {
        this.nhog = nhog;
    }

    public String getThog() {
        return thog;
    }

    public void setThog(String thog) {
        this.thog = thog;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSect() {
        return sect;
    }

    public void setSect(String sect) {
        this.sect = sect;
    }

    public String getManz() {
        return manz;
    }

    public void setManz(String manz) {
        this.manz = manz;
    }

    public String getEdif() {
        return edif;
    }

    public void setEdif(String edif) {
        this.edif = edif;
    }

    public String getNviv() {
        return nviv;
    }

    public void setNviv(String nviv) {
        this.nviv = nviv;
    }

    
    
}
