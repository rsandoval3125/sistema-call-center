/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.dato.general;
import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
/**
 *
 * @author lponce
 */
public class ColumnaValor {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private AdmColumna columnaIzquierda;
    private AdmColumna columnaDerecha;
    private String valor;
    private Object valorObjeto;
    private String comparacion;
    private int parentisisInicio;
    private int parentisiFin;
    private int andor;
    //</editor-fold>

    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public AdmColumna getColumnaIzquierda() {
        return columnaIzquierda;
    }

    public void setColumnaIzquierda(AdmColumna columnaIzquierda) {
        this.columnaIzquierda = columnaIzquierda;
    }

    public AdmColumna getColumnaDerecha() {
        return columnaDerecha;
    }

    public void setColumnaDerecha(AdmColumna columnaDerecha) {
        this.columnaDerecha = columnaDerecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Object getValorObjeto() {
        return valorObjeto;
    }

    public void setValorObjeto(Object valorObjeto) {
        this.valorObjeto = valorObjeto;
    }

    public String getComparacion() {
        return comparacion;
    }

    public void setComparacion(String comparacion) {
        this.comparacion = comparacion;
    }

    public int getParentisisInicio() {
        return parentisisInicio;
    }

    public void setParentisisInicio(int parentisisInicio) {
        this.parentisisInicio = parentisisInicio;
    }

    public int getParentisiFin() {
        return parentisiFin;
    }

    public void setParentisiFin(int parentisiFin) {
        this.parentisiFin = parentisiFin;
    }

    public int getAndor() {
        return andor;
    }

    public void setAndor(int andor) {
        this.andor = andor;
    }
    //</editor-fold>

    //<editor-fold desc="Metodos" defaultstate="collapsed">
    // cambio caja blanca 27/07/2021
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        } else {
//            ColumnaValor comp = (ColumnaValor) obj;
//            if (columnaIzquierda.equals(comp.columnaIzquierda) && columnaDerecha.equals(comp.columnaDerecha) && valor.equals(comp.valor) && valorObjeto.equals(comp.valorObjeto) && comparacion.equals(comp.comparacion)) {
//                return true; //To change body of generated methods, choose Tools | Templates.
//            }
//        }
//        return false;
//    }
    
    
    //</editor-fold>

}
