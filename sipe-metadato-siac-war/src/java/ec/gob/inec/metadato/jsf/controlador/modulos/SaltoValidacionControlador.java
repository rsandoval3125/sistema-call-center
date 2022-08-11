/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.shaded.json.JSONArray;



/**
 *
 * @author jcerda
 * Pegar el los siguentes tag en el formulario para el funcionamiento de saltos y validaciones 
 * <br/><br/><h:inputHidden  id="saltos" value="#{saltoValidacionControlador.listarSaltos(ensanutF1Controlador.cabeceraActual.codCarCon.codFormulario.idFormulario)}" />
 * <br/><br/><h:inputHidden  id="validaciones" value="#{saltoValidacionControlador.listarValiadciones(ensanutF1Controlador.cabeceraActual.codCarCon.codFormulario.idFormulario)}" />
 * 
 */
@ManagedBean
@ViewScoped
public class SaltoValidacionControlador  {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(SaltoValidacionControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    private JSONArray listaSaltos;
     private JSONArray listaValidaciones;
     private int codFormulario;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR ">

    /**
     * Creates a new instance of SaltoValidacionControlador
     */
    public SaltoValidacionControlador() {
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" METODOS ">
    @PostConstruct
    public void inicializar() {
        try {
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

       
  
  public String listarSaltos(Integer codForm){
       String saltos="";
         System.out.println("Lista saltos form: "+ codForm);
        try {
            listaSaltos=new JSONArray(getBc().getMetSaltoServicioRemote().listarSaltos(codForm));
        saltos=listaSaltos.toString();
        // System.out.println(" salll: "+saltos);
     } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
       return saltos;
 }
  public String listarValiadciones(Integer codForm){
     String validaciones="";
     System.out.println("Vista validacion form: "+ codForm);
     try {
          listaValidaciones=new JSONArray(bc.getMetValidacionServicioRemote().listarValidacion(codForm));
     validaciones= listaValidaciones.toString();
         //System.out.println(" valll: "+validaciones);
     } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
     return validaciones;
 } 
    

    

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" GETTERSYSETTERS ">
   
    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }

// </editor-fold>
    

}
