/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador que permite administrar la pagina gestionSecciones
 *
 * @author dgarcia
 */
@ManagedBean
@ViewScoped
public class GestionSeccionControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionSeccionControlador.class.getName());
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    
    private boolean habilitaEdicion;
    private MetSeccion objSeccion;
    private List<MetSeccion> listarSeccion;
    private List<AdmInstitucion> listaInsti;
    private Map<Integer, String> mapaInsti;
    private List<AdmOrganigrama> listaOrganigrama;
    private Map<Integer, String> mapaOrga;
    private List<MetSeccion> listarEditSeccion;
    private List<MetSeccion> seccionPadre;
    private Map<Integer, String> mapaSeccion;
    private boolean validar;
    private boolean validadorSeccion;
    private boolean validarNombreSec;
     @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    

    
    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            validar = true;
            //--Invocación a EJB remoto

            //--Fin envocación a EJB remoto
            listarSeccionTodos();
            objSeccion = new MetSeccion();
            seccionPadre = getMetSeccionServicioRemote().listarSoloSeccion();
            
            listaInsti = new ArrayList<>();
            listaInsti = getAdmInstitucionServicioRemote().listarTodo();
            mapaInsti = new HashMap<>();
            for(AdmInstitucion in : listaInsti){
                mapaInsti.put(in.getIdInstitucion(), in.getNombre());
            }
            
            listaOrganigrama = new ArrayList<>();
            listaOrganigrama = getAdmOrganigramaServicioRemote().listarTodo();
            mapaOrga = new HashMap<>();
            for(AdmOrganigrama or : listaOrganigrama){
                mapaOrga.put(or.getIdOrganigrama(), or.getNombre());
            }           
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionSeccionControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
   
    //Metodos Formulario
     public void listarSeccionTodos() throws Exception{
         listarSeccion = getMetSeccionServicioRemote().listarTodas();
         
    }
     
    public void recuperarSeccionCampos(MetSeccion seccion) {
        try {
            objSeccion = new MetSeccion();
            objSeccion = seccion;
            seccionPadre = getMetSeccionServicioRemote().listarSoloSeccion();
            listarEditSeccion = getMetSeccionServicioRemote().listarSeccionPorId(objSeccion.getIdSeccion());
            habilitaEdicion = true;
            validarSeccion();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void confirmaEliminarSeccion(MetSeccion seccion) {
        try {
            seccion.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            seccion.setEstadoLogico(false);
            getMetSeccionServicioRemote().editarSeccion(seccion);
            addSuccessMessage("Registro Eliminado");
            listarSeccionTodos();
            objSeccion = new MetSeccion();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    } 
    
    public void guardarSeccion() {
//        System.out.println("Datos" + objSeccion.getNombre() +"   " + 
//        objSeccion.getNivel() +"   "+ objSeccion.getOrden()+" sec/sub "+ objSeccion.getCodSeccionPadre());
                    try {
                        if (habilitaEdicion == false) {
                            try {
                                validarNombreSec = getMetSeccionServicioRemote().accionPorNombre(objSeccion.getNombre());
                                    if(validarNombreSec == false){
                                        try {
                                            validadorSeccion = getMetSeccionServicioRemote().accionSeccionPorNombreNivelOrden(objSeccion.getNombre(), objSeccion.getNivel(), objSeccion.getOrden());
                                            if(validadorSeccion == false){
                                                    objSeccion.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                                                    objSeccion.setEstadoLogico(true);
                                                    getMetSeccionServicioRemote().crearSeccion(objSeccion);
                                                    addSuccessMessage("Registro Guardado");
                                                    listarSeccionTodos();
                                                    refrescaNuevoSeccion();
                                            }else addErrorMessage("Ya existe "+objSeccion.getNombre()+" para ese nivel y orden.");
                                        } catch (Exception e) {
                                            LOGGER.log(Level.SEVERE, null, e);
                                            addWarningMessage("No se puede guardar el registro");
                                        }      
                                    }else addWarningMessage("Ya existe un nombre "+objSeccion.getNombre()+", revise el estandar ");
                            } catch (Exception e) {
                                LOGGER.log(Level.SEVERE, null, e);
                                addWarningMessage("No se puede guardar el registro");
                            }    
                       }else{
                                objSeccion.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                                getMetSeccionServicioRemote().editarSeccion(objSeccion);
                                addSuccessMessage("Registro Actualizado");
                                listarSeccionTodos();
                                refrescaNuevoSeccion();
                        }
                    }catch (Exception e) {
                        LOGGER.log(Level.SEVERE, null, e);
                        addWarningMessage("No se puede guardar el registro");
                    }       
    }
    
    public void refrescaNuevoSeccion() {
        try {
            objSeccion = new MetSeccion();
            listarEditSeccion = getMetSeccionServicioRemote().listarTodas();
            habilitaEdicion = false;
            seccionPadre = getMetSeccionServicioRemote().listarSoloSeccion();
            validar = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void validarSeccion(){
        if (objSeccion.getNivel().equals(2) ) {
            validar = false;
        } else {
            validar = true;
        }
    } 

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    
    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }
    
    public MetSeccion getObjSeccion() {
        return objSeccion;
    }

    public void setObjSeccion(MetSeccion objSeccion) {
        this.objSeccion = objSeccion;
    }

    public List<MetSeccion> getListarSeccion() {
        return listarSeccion;
    }

    public void setListarSeccion(List<MetSeccion> listarSeccion) {
        this.listarSeccion = listarSeccion;
    }

    public List<AdmInstitucion> getListaInsti() {
        return listaInsti;
    }

    public void setListaInsti(List<AdmInstitucion> listaInsti) {
        this.listaInsti = listaInsti;
    }

    public Map<Integer, String> getMapaInsti() {
        return mapaInsti;
    }

    public void setMapaInsti(Map<Integer, String> mapaInsti) {
        this.mapaInsti = mapaInsti;
    }

    public List<AdmOrganigrama> getListaOrganigrama() {
        return listaOrganigrama;
    }

    public void setListaOrganigrama(List<AdmOrganigrama> listaOrganigrama) {
        this.listaOrganigrama = listaOrganigrama;
    }

    public Map<Integer, String> getMapaOrga() {
        return mapaOrga;
    }

    public void setMapaOrga(Map<Integer, String> mapaOrga) {
        this.mapaOrga = mapaOrga;
    }

    public List<MetSeccion> getListarEditSeccion() {
        return listarEditSeccion;
    }

    public void setListarEditSeccion(List<MetSeccion> listarEditSeccion) {
        this.listarEditSeccion = listarEditSeccion;
    }

    public List<MetSeccion> getSeccionPadre() {
        return seccionPadre;
    }

    public void setSeccionPadre(List<MetSeccion> seccionPadre) {
        this.seccionPadre = seccionPadre;
    }

    public Map<Integer, String> getMapaSeccion() {
        return mapaSeccion;
    }

    public void setMapaSeccion(Map<Integer, String> mapaSeccion) {
        this.mapaSeccion = mapaSeccion;
    }

    public boolean isValidar() {
        return validar;
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }

    public boolean isvalidadorSeccion() {
        return validadorSeccion;
    }

    public void setvalidadorSeccion(boolean a) {
        this.validadorSeccion = validadorSeccion;
    }
    
    
    
    //</editor-fold>

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    
    
    
    


}
