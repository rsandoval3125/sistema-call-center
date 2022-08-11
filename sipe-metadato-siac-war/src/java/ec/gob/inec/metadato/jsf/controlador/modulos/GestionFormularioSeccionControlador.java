/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetFormularioSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

/**
 * Controlador que permite administrar la pagina gestionFormularioSeccion
 *
 * @author dgarcia
 */
@ManagedBean
@ViewScoped
public class GestionFormularioSeccionControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionFormularioSeccionControlador.class.getName());
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    private boolean habilitaEdicion;
    private MetFormularioSeccion objFormSeccion;
    private List<MetFormularioSeccion> listaFormSeccion;
    private MetSeccion objSeccion;
    private MetFormulario objFormulario;
    private List<MetFormulario> listaFormularios;
    private List<MetFormulario> listaEditFormualrios; 
    private DualListModel<MetSeccion> lstFrmSeccion;
    private List<MetSeccion> seccionAsignada;
    private List<MetSeccion> seccionNoAsignada;
    private List<MetFormulario> listarEditFormulario;
   @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objFormSeccion = new MetFormularioSeccion();
            listarFormSeccionTodos();
            lstFrmSeccion = new DualListModel<MetSeccion>();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionFormularioSeccionControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
        public void refrescaNuevo() {
        try {
            objFormulario = new MetFormulario();
            seccionAsignada = new ArrayList<>();
            seccionNoAsignada = new ArrayList<>();
            lstFrmSeccion = new DualListModel<>();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarFormSeccionCampos(MetFormulario formulario) {
        try {
            objFormulario = new MetFormulario();
            objFormulario = formulario;
            listarEditFormulario = getMetFormularioServicioRemote().listarFormulariosPorId(objFormulario.getIdFormulario());
            listarSecciones();
//            listaEditFormSeccion = getMetFormularioSeccionServicioRemote().listarPorId(objFormulario.getIdFormulario());
//            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void listarFormSeccionTodos() throws Exception {
        listaFormSeccion = getMetFormularioSeccionServicioRemote().listarTodosActivos();
        listaFormularios = getMetFormularioServicioRemote().listarFormulariosTodos();
    }
    
    public void listarSecciones(){
        try {
            listaEditFormualrios = getMetFormularioServicioRemote().listarFormulariosPorId(objFormulario.getIdFormulario());
            seccionAsignada = new ArrayList<MetSeccion>();
            seccionNoAsignada = new ArrayList<MetSeccion>();
            lstFrmSeccion = new DualListModel<MetSeccion>();
            seccionAsignada = getMetSeccionServicioRemote().listarSecPorFomulario(objFormulario.getIdFormulario());
            if (seccionAsignada.isEmpty()){
                seccionNoAsignada = getMetSeccionServicioRemote().listarSoloSeccion();
            }else{
                seccionNoAsignada = getMetSeccionServicioRemote().listarSeccionesNoAsignados(seccionAsignada);
            }
            lstFrmSeccion = new DualListModel<>(seccionNoAsignada,seccionAsignada);
//            
//            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    } 
    
    public void guardarSeccionesAsignadas(){
            try {
                    if(seccionAsignada.size()>0){
                        for (MetSeccion sec : seccionAsignada){
                           objFormSeccion = getMetFormularioSeccionServicioRemote().bucarFormSecPoridForXidSecc(objFormulario.getIdFormulario(), sec.getIdSeccion());
                           objFormSeccion.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
                           objFormSeccion.setEstadoLogico(false);
                           getMetFormularioSeccionServicioRemote().editarFormularioSeccion(objFormSeccion);
                       }
                    }
                   try {
                            int a = 0;
                                for (MetSeccion sec : lstFrmSeccion.getTarget()) {
                                    a = a + 1;
                                    objFormSeccion = new MetFormularioSeccion();
                                    objFormSeccion.setEstadoLogico(true);
                                    objFormSeccion.setCodSeccion(sec);
                                    objFormSeccion.setCodFormulario(objFormulario);
                                    objFormSeccion.setOrden(a);
                                    getMetFormularioSeccionServicioRemote().crearFormularioSeccion(objFormSeccion);
                                } 
                                    addSuccessMessage("Registros Actualizados");
                        }catch (Exception e) {
                            LOGGER.log(Level.SEVERE, null, e);
                            addErrorMessage("Registros No Guardados");
                     }
                   } catch (Exception e) {
                       LOGGER.log(Level.SEVERE, null, e);
                       addWarningMessage("No se puede eliminar el registro");
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
   
    public MetFormularioSeccion getObjFormSeccion() {
        return objFormSeccion;
    }

    public void setObjFormSeccion(MetFormularioSeccion objFormSeccion) {
        this.objFormSeccion = objFormSeccion;
    }

    public List<MetFormularioSeccion> getListaFormSeccion() {
        return listaFormSeccion;
    }

    public void setListaFormSeccion(List<MetFormularioSeccion> listaFormSeccion) {
        this.listaFormSeccion = listaFormSeccion;
    }

    public List<MetFormulario> getListaFormularios() {
        return listaFormularios;
    }

    public void setListaFormularios(List<MetFormulario> listaFormularios) {
        this.listaFormularios = listaFormularios;
    }

    public MetSeccion getObjSeccion() {
        return objSeccion;
    }

    public void setObjSeccion(MetSeccion objSeccion) {
        this.objSeccion = objSeccion;
    }

    public MetFormulario getObjFormulario() {
        return objFormulario;
    }

    public void setObjFormulario(MetFormulario objFormulario) {
        this.objFormulario = objFormulario;
    }

    public List<MetFormulario> getListaEditFormualrios() {
        return listaEditFormualrios;
    }

    public void setListaEditFormualrios(List<MetFormulario> listaEditFormualrios) {
        this.listaEditFormualrios = listaEditFormualrios;
    }

    public List<MetSeccion> getSeccionAsignada() {
        return seccionAsignada;
    }

    public void setSeccionAsignada(List<MetSeccion> seccionAsignada) {
        this.seccionAsignada = seccionAsignada;
    }

    public List<MetSeccion> getSeccionNoAsignada() {
        return seccionNoAsignada;
    }

    public void setSeccionNoAsignada(List<MetSeccion> seccionNoAsignada) {
        this.seccionNoAsignada = seccionNoAsignada;
    }

    public DualListModel<MetSeccion> getLstFrmSeccion() {
        return lstFrmSeccion;
    }

    public void setLstFrmSeccion(DualListModel<MetSeccion> lstFrmSeccion) {
        this.lstFrmSeccion = lstFrmSeccion;
    }

    public List<MetFormulario> getListarEditFormulario() {
        return listarEditFormulario;
    }

    public void setListarEditFormulario(List<MetFormulario> listarEditFormulario) {
        this.listarEditFormulario = listarEditFormulario;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }
    

}
