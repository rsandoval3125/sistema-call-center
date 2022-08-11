/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.conexion;

/**
 *
 * @author lponce
 */
public class DataColumna {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private String nombreCatalogoBD;
    private String nombreClaseJava;
    private int tamanoColumna;
    private String textoPresentacion;
    private String nombre;
    private int tipoDato;
    private String tipoDatoString;
    private int precision;
    private int escala;
    private String esquema;
    private String tabla;
    private boolean autoincremental;
    private boolean caseSensitivo;
    private boolean moneda;
    private boolean modificable;
    private int nulo;
    private boolean conSigno;
    private int posicion;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public DataColumna() {
        autoincremental = false;
        caseSensitivo = false;
        moneda = false;
        modificable = true;
        conSigno = false;
        nulo = 1;
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public String getNombreCatalogoBD() {
        return nombreCatalogoBD;
    }

    public void setNombreCatalogoBD(String nombreCatalogoBD) {
        this.nombreCatalogoBD = nombreCatalogoBD;
    }

    public String getNombreClaseJava() {
        return nombreClaseJava;
    }

    public void setNombreClaseJava(String nombreClaseJava) {
        this.nombreClaseJava = nombreClaseJava;
    }

    public int getTamanoColumna() {
        return tamanoColumna;
    }

    public void setTamanoColumna(int tamanoColumna) {
        this.tamanoColumna = tamanoColumna;
    }

    public String getTextoPresentacion() {
        return textoPresentacion;
    }

    public void setTextoPresentacion(String textoPresentacion) {
        this.textoPresentacion = textoPresentacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(int tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getTipoDatoString() {
        return tipoDatoString;
    }

    public void setTipoDatoString(String tipoDatoString) {
        this.tipoDatoString = tipoDatoString;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getEscala() {
        return escala;
    }

    public void setEscala(int escala) {
        this.escala = escala;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public boolean isAutoincremental() {
        return autoincremental;
    }

    public void setAutoincremental(boolean autoincremental) {
        this.autoincremental = autoincremental;
    }

    public boolean isCaseSensitivo() {
        return caseSensitivo;
    }

    public void setCaseSensitivo(boolean caseSensitivo) {
        this.caseSensitivo = caseSensitivo;
    }

    public boolean isMoneda() {
        return moneda;
    }

    public void setMoneda(boolean moneda) {
        this.moneda = moneda;
    }

    public boolean isModificable() {
        return modificable;
    }

    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }

    public int getNulo() {
        return nulo;
    }

    public void setNulo(int nulo) {
        this.nulo = nulo;
    }

    public boolean isConSigno() {
        return conSigno;
    }

    public void setConSigno(boolean conSigno) {
        this.conSigno = conSigno;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    //</editor-fold>
}
