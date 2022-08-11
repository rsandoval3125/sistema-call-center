/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.metadato.clases.Condicional;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetValidacion;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
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
 * @author jcerda
 */
@ManagedBean
@ViewScoped
public class GestionValidacionControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionValidacionControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    
    private MetValidacion objValidacionActual;
    private MetOperativo objOperativoActual;
    private MetFormulario objFormularioActual;
    private MetSeccion objSeccionActual;
    private MetVariable objVariableActual;

    private MetVariable objVariable1;
    private MetVariable objVariable2;
    private Condicional objCondicionalActual;

    private List<MetValidacion> listarValidaciones;
    private List<Object[]> listarValidacionForm;
    private List<MetVariable> listarVariable;
    private List<MetFormulario> listarFormulario;
    private List<MetOperativo> listarOperativo;
    private List<MetSeccion> listarSeccion;

    private String cadenaValidacion;
    private boolean habilitaEdicion;
    private boolean habilitaValor;
    private boolean tipoValidacion;
    private String valorInput;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            setHabilitaEdicion(false);
            objOperativoActual = new MetOperativo();
            objFormularioActual = new MetFormulario();
            objSeccionActual = new MetSeccion();
            objValidacionActual = new MetValidacion();
            objCondicionalActual = new Condicional();
            setObjVariable1(new MetVariable());
            setObjVariable2(new MetVariable());
            listarSeccion = new ArrayList<MetSeccion>();
            listarOperativo = new ArrayList<MetOperativo>();
            listarFormulario = new ArrayList<MetFormulario>();
            listarSeccion = new ArrayList<MetSeccion>();
            listarVariable = new ArrayList<MetVariable>();
            listarValidaciones = new ArrayList<MetValidacion>();
            listarValidacionForm = new ArrayList<Object[]>();

            cadenaValidacion = "";
            valorInput = "";
            habilitaValor = false;
            tipoValidacion = false;

            //--Invocación a EJB remoto
            //--Fin envocación a EJB remoto
            listarValiadacionForm();
            listarOperativo();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionValidacionControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void listarOperativo() throws Exception {
        listarOperativo = getMetOperativoServicioRemote().listarTodo();

    }

    public void listarFormularios() {
        try {
          listarFormulario = getMetFormularioServicioRemote().listarFormulariosPorOperativo(objOperativoActual.getIdOperativo());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void listarSeccionPorFormulario() {
        try {
            listarSeccion = getMetSeccionServicioRemote().listarSeccRecursivoForm(objFormularioActual.getIdFormulario());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void listarVariablesPorSeccion() {
        try {
            listarVariable = getMetVariableServicioRemote().listarVariablesPorSeccion(objSeccionActual.getIdSeccion());
            listarValidacionPorSeccion();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    private void listarValidacionPorSeccion() throws Exception {
        try {
            listarValidaciones = getMetValidacionServicioRemote().listarValidacionXSeccion(getObjSeccionActual().getIdSeccion());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    private void listarValiadacionForm() throws Exception {
        //listarValidacionForm=getMetValidacionServicioRemote().listarValidacion(1);
    }

    public void aniadirCondicion() throws Exception {

        if (objCondicionalActual.getOperador() != null) {
            for (MetVariable v : listarVariable) {
                if (v.getIdVar().equals(objVariable1.getIdVar())) {
                    objCondicionalActual.setIdentificador1(v.getIdentificador());
                } else if (v.getIdVar().equals(objVariable2.getIdVar())) {
                    objCondicionalActual.setIdentificador2(v.getIdentificador());
                }
            }
            if (objCondicionalActual.getValorPregunta() != null) {
                valorInput = objCondicionalActual.getValorPregunta();
            } else {

                valorInput = "[" + objCondicionalActual.getIdentificador2() + "]";
            }

            if (objCondicionalActual.getConector() != null && !cadenaValidacion.isEmpty()) {
                cadenaValidacion = cadenaValidacion + " " + objCondicionalActual.getConector();
                if (objCondicionalActual.getOperador().equals("includes")) {
                    cadenaValidacion = cadenaValidacion + " '[" + valorInput + "]'." + objCondicionalActual.getOperador() + "([" + objCondicionalActual.getIdentificador1() + "])";
                } else {
                    cadenaValidacion = cadenaValidacion + " [" + objCondicionalActual.getIdentificador1() + "]" + objCondicionalActual.getOperador() + "" + valorInput + "";
                }
                objCondicionalActual = new Condicional();
                objVariable1 = new MetVariable();
                objVariable2 = new MetVariable();
                valorInput = "";
                habilitaValor = false;

            } else if (cadenaValidacion.isEmpty()) {
                if (objCondicionalActual.getOperador().equals("includes")) {
                    cadenaValidacion = cadenaValidacion + " '[" + valorInput + "]'." + objCondicionalActual.getOperador() + "([" + objCondicionalActual.getIdentificador1() + "])";
                } else {
                    cadenaValidacion = cadenaValidacion + " [" + objCondicionalActual.getIdentificador1() + "]" + objCondicionalActual.getOperador() + "" + valorInput + "";
                }
                objCondicionalActual = new Condicional();
                objVariable1 = new MetVariable();
                objVariable2 = new MetVariable();
                valorInput = "";
                habilitaValor = false;

            } else {
                addWarningMessage("¡Seleccione un conector AND/OR!");
            }
        } else {
            addWarningMessage("¡Seleccione un operador!");
        }

    }

    public void aniadirValidacion() throws Exception {

        if (!cadenaValidacion.isEmpty()) {
            objValidacionActual.setCondicion(cadenaValidacion);
            objVariable1 = new MetVariable();
            objVariable2 = new MetVariable();
            valorInput = "";
            cadenaValidacion = "";
            habilitaValor = false;

        } else {
            addWarningMessage("Ingres la validación");
        }
    }

    public void refrescaModal() {
        try {
            habilitaValor = false;
            cadenaValidacion = "";
            objVariable1 = new MetVariable();
            objVariable2 = new MetVariable();
            objCondicionalActual = new Condicional();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void guardarValidacion() {
        try {            
            if (habilitaEdicion == false) {
                if(objValidacionActual.getCodVar()!=null && objValidacionActual.getCondicion()!=null){
                objValidacionActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objValidacionActual.setEstadoLogico(true);
                if (tipoValidacion == false) {
                    getObjValidacionActual().setTipoValidacion(Short.valueOf("2"));
                } else {
                    getObjValidacionActual().setTipoValidacion(Short.valueOf("1"));
                }
                getMetValidacionServicioRemote().crearValidacion(getObjValidacionActual());
                addSuccessMessage("Registro Guardado");
                listarValidacionPorSeccion();
                refrescaNuevo();
                }else{
                addWarningMessage("Complete todos los campos ");}
            } else {
                objValidacionActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                if (tipoValidacion == false) {
                    objValidacionActual.setTipoValidacion(Short.valueOf("2"));
                } else {
                    getObjValidacionActual().setTipoValidacion(Short.valueOf("1"));
                }
                getMetValidacionServicioRemote().editarValidacion(getObjValidacionActual());
                addSuccessMessage("Registro Actualizado");
                listarValidacionPorSeccion();

                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro: " + e.getMessage());
        }
    }

    public void refrescaNuevo() {
        try {
            objValidacionActual = new MetValidacion();
            setHabilitaEdicion(false);
            objVariable1 = new MetVariable();
            objVariable2 = new MetVariable();
            objCondicionalActual = new Condicional();
            tipoValidacion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarValidacionCampos(MetValidacion validacion) {
        try {
            objValidacionActual = new MetValidacion();
            objValidacionActual = validacion;
            if (objValidacionActual.getTipoValidacion() == 2) {
                tipoValidacion = false;
            } else {
                tipoValidacion = true;
            }
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(MetValidacion validacion) {
        try {
            validacion.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            validacion.setEstadoLogico(false);
            getMetValidacionServicioRemote().editarValidacion(validacion);
            addSuccessMessage("Registro Eliminado");
            listarValidacionPorSeccion();
            objValidacionActual = new MetValidacion();
            habilitaEdicion = false;
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

    public MetValidacion getObjValidacionActual() {
        return objValidacionActual;
    }

    public void setObjValidacionActual(MetValidacion objValidacionActual) {
        this.objValidacionActual = objValidacionActual;
    }

    public MetFormulario getObjFormularioActual() {
        return objFormularioActual;
    }

    public void setObjFormularioActual(MetFormulario objFormularioActual) {
        this.objFormularioActual = objFormularioActual;
    }

    public MetSeccion getObjSeccionActual() {
        return objSeccionActual;
    }

    public void setObjSeccionActual(MetSeccion objSeccionActual) {
        this.objSeccionActual = objSeccionActual;
    }

    public MetVariable getObjVariableActual() {
        return objVariableActual;
    }

    public void setObjVariableActual(MetVariable objVariableActual) {
        this.objVariableActual = objVariableActual;
    }

    public List<MetValidacion> getListarValidaciones() {
        return listarValidaciones;
    }

    public void setListarValidaciones(List<MetValidacion> listarValidaciones) {
        this.listarValidaciones = listarValidaciones;
    }

    public List<MetVariable> getListarVariable() {
        return listarVariable;
    }

    public void setListarVariable(List<MetVariable> listarVariable) {
        this.listarVariable = listarVariable;
    }

    public List<MetFormulario> getListarFormulario() {
        return listarFormulario;
    }

    public void setListarFormulario(List<MetFormulario> listarFormulario) {
        this.listarFormulario = listarFormulario;
    }

    public List<MetSeccion> getListarSeccion() {
        return listarSeccion;
    }

    public void setListarSeccion(List<MetSeccion> listarSeccion) {
        this.listarSeccion = listarSeccion;
    }

    public String getCadenaValidacion() {
        return cadenaValidacion;
    }

    public void setCadenaValidacion(String cadenaValidacion) {
        this.cadenaValidacion = cadenaValidacion;
    }

    public boolean isHabilitaValor() {
        return habilitaValor;
    }

    public void setHabilitaValor(boolean habilitaValor) {
        this.habilitaValor = habilitaValor;
    }

    public String getValorInput() {
        return valorInput;
    }

    public void setValorInput(String valorInput) {
        this.valorInput = valorInput;
    }

    public MetVariable getObjVariable1() {
        return objVariable1;
    }

    public void setObjVariable1(MetVariable objVariable1) {
        this.objVariable1 = objVariable1;
    }

    public MetVariable getObjVariable2() {
        return objVariable2;
    }

    public void setObjVariable2(MetVariable objVariable2) {
        this.objVariable2 = objVariable2;
    }

    public Condicional getObjCondicionalActual() {
        return objCondicionalActual;
    }

    public void setObjCondicionalActual(Condicional objCondicionalActual) {
        this.objCondicionalActual = objCondicionalActual;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public MetOperativo getObjOperativoActual() {
        return objOperativoActual;
    }

    public void setObjOperativoActual(MetOperativo objOperativoActual) {
        this.objOperativoActual = objOperativoActual;
    }

    public List<MetOperativo> getListarOperativo() {
        return listarOperativo;
    }

    public void setListarOperativo(List<MetOperativo> listarOperativo) {
        this.listarOperativo = listarOperativo;
    }

    public boolean isTipoValidacion() {
        return tipoValidacion;
    }

    public void setTipoValidacion(boolean tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

    public List<Object[]> getListarValidacionForm() {
        return listarValidacionForm;
    }

    public void setListarValidacionForm(List<Object[]> listarValidacionForm) {
        this.listarValidacionForm = listarValidacionForm;
    }
    //</editor-fold>
}
