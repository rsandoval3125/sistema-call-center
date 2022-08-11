/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.jsf.controlador.modulos;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author jaraujo
 */
@ManagedBean
@ViewScoped
public class EjecutarAccionControlador {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">
    private static final Logger LOGGER = Logger.getLogger(EjecutarAccionControlador.class.getName());
    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    private String varComponenteAccion;
    private String name;
    private String condicion;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get and set">
    public BaseControlador getBc() {
        return bc;
    }
    
    public void setBc(BaseControlador bc) {    
        this.bc = bc;
    }

    public String getVarComponenteAccion() {
        return varComponenteAccion;
    }

    public void setVarComponenteAccion(String varComponenteAccion) {
        this.varComponenteAccion = varComponenteAccion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="constructor">
    public EjecutarAccionControlador() {
        name = "ejecutarAccionControlador";        
        condicion = "38";        
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="métodos">
    public void ejecutarMetodo() {
        try {
            System.err.println("var accion>> "+varComponenteAccion);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

}
