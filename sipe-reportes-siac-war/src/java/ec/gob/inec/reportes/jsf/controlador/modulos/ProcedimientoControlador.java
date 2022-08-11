/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.jsf.controlador.modulos;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rmoreano
 */
@ManagedBean
@ViewScoped
public class ProcedimientoControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(ProcedimientoControlador.class.getName());
    
    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    
    private boolean habilitaEdicion;
    private RepoProcedimiento procedimientoActual;
    private List<RepoProcedimiento> listaProcedimientos;

    //</editor-fold>
    
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            procedimientoActual = new RepoProcedimiento();
            listarProcedimientoTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public ProcedimientoControlador() {
    }
    //</editor-fold>
    
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void listarProcedimientoTodos() {
        try {
            listaProcedimientos = new ArrayList<>();
            listaProcedimientos = bc.getRepoProcedimientoServicioRemote().listarTodo();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardarProcedimiento() {
        try {
            if (habilitaEdicion == false) {
                RepoProcedimiento proc = bc.getRepoProcedimientoServicioRemote().buscarPorNombre(procedimientoActual.getNombre());
                if (proc == null) {
                    bc.getRepoProcedimientoServicioRemote().crearProcedimiento(procedimientoActual);
                    bc.addSuccessMessage("Registro Guardado");
                    listarProcedimientoTodos();
                    nuevoProcedimiento();
                } else {
                    bc.addErrorMessage("Ya existe " + procedimientoActual.getNombre() + " con ese nombre.");
                }
            } else {
                bc.getRepoProcedimientoServicioRemote().editarProcedimiento(procedimientoActual);
                bc.addSuccessMessage("Registro Actualizado");
                listarProcedimientoTodos();
                nuevoProcedimiento();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            bc.addWarningMessage("No se puede guardar el registro");
        }
    }
    
    public void nuevoProcedimiento() {
        try {
            procedimientoActual = new RepoProcedimiento();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void editarProcedimiento(RepoProcedimiento procedimiento) {
        try {
            procedimientoActual = new RepoProcedimiento();
            procedimientoActual = procedimiento;
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void eliminarProcedimiento(RepoProcedimiento procedimiento) {
        try {
            bc.getRepoProcedimientoServicioRemote().eliminarProcedimiento(procedimiento);
            bc.addSuccessMessage("Registro Eliminado");
            listarProcedimientoTodos();
            procedimientoActual = new RepoProcedimiento();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            bc.addWarningMessage("No se puede eliminar el registro");
        }
    } 
    //</editor-fold>
    
    //<editor-fold desc="get and set" defaultstate="collapsed">    
    public RepoProcedimiento getProcedimientoActual() {
        return procedimientoActual;
    }

    public void setProcedimientoActual(RepoProcedimiento procedimientoActual) {
        this.procedimientoActual = procedimientoActual;
    }
    public List<RepoProcedimiento> getListaProcedimientos() {
        return listaProcedimientos;
    }

    public void setListaProcedimientos(List<RepoProcedimiento> listaProcedimientos) {
        this.listaProcedimientos = listaProcedimientos;
    }    
    //</editor-fold>    

    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }
}
