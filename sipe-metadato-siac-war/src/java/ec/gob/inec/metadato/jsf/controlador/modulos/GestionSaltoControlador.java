/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.metadato.clases.Condicional;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetSalto;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;

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
import org.primefaces.shaded.json.JSONArray;


/**
 *
 * @author jcerda
 */
@ManagedBean
@ViewScoped
public class GestionSaltoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionSaltoControlador.class.getName());
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    
    private MetSalto objSaltoActual;
    private MetFormulario objFormularioActual;
    private MetOperativo objOperativoActual;
    private MetSeccion objSeccionActual;
    private MetVariable objVariableActual;
    private MetVariable objVariable0;
    private MetVariable objVariable1;
    private MetVariable objVariable2;
    private Condicional objCondicionalActual;

    private List<MetSalto> listarSaltos;
    private List<MetVariable> listarVariable;
    private List<MetOperativo> listarOperativo;
    private List<MetFormulario> listarFormulario;
    private List<MetSeccion> listarSeccion;
    private String cadenaSalto;
    private String cadenaCondicion;
    private String codSalto;
    private boolean habilitaEdicion;
    private boolean habilitaValor;
    private boolean habilitaSalto;
    private String valorInput;

    private List<Object[]> listarSaltosForm;
    JSONArray jsonArray;
   

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objOperativoActual = new MetOperativo();
            objSaltoActual = new MetSalto();
            objFormularioActual = new MetFormulario();
            objSeccionActual = new MetSeccion();
            objCondicionalActual = new Condicional();
            objVariableActual = new MetVariable();
            objVariable0 = new MetVariable();
            objVariable1 = new MetVariable();
            objVariable2 = new MetVariable();

            listarSaltos = new ArrayList<MetSalto>();
            listarSeccion = new ArrayList<MetSeccion>();
            listarFormulario = new ArrayList<MetFormulario>();
            listarVariable = new ArrayList<MetVariable>();
            listarOperativo = new ArrayList<MetOperativo>();
            
            codSalto = "";
            cadenaSalto = "";
            cadenaCondicion = "";
            valorInput = "";
            habilitaValor = false;
            habilitaSalto = false;

            listarSaltosForm = new ArrayList<>();
           
            //--Invocación a EJB remoto
            //--Fin envocación a EJB remoto
            //listarSaltoTodos();
            listarSaltoForm();
            listarOperativo();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionSaltoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void listarFormularios() {
        try {
            listarFormulario = getMetFormularioServicioRemote().listarFormulariosPorOperativo(objOperativoActual.getIdOperativo());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void listarSeccionPorFormulario() {
        try {
            //listarSeccion = getMetSeccionServicioRemote().listarSeccionesNivel1PorFormulario(objFormularioActual.getIdFormulario());
           listarSeccion = getMetSeccionServicioRemote().listarSeccRecursivoForm(objFormularioActual.getIdFormulario());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void listarVariablesPorSeccion() {
        try {
            listarVariable = getMetVariableServicioRemote().listarVariablesPorSeccion(objSeccionActual.getIdSeccion());
            listarSaltoPorSeccion();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    private void listarSaltoPorSeccion() throws Exception {

        listarSaltos = getMetSaltoServicioRemote().listarSaltoXSeccion(objSeccionActual.getIdSeccion());

    }

    private void listarSaltoForm() throws Exception {
        /*listarSaltosForm = getMetSaltoServicioRemote().listarSaltos(1);//cod formulario
          jsonArray = new JSONArray(listarSaltosForm);*/     
    }

    public void listarOperativo() throws Exception {
        listarOperativo = getMetOperativoServicioRemote().listarTodo();

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

            if (objCondicionalActual.getConector() != null && !cadenaCondicion.isEmpty()) {
                cadenaCondicion = cadenaCondicion + " " + objCondicionalActual.getConector();
                if (objCondicionalActual.getOperador().equals("includes")) {
                    cadenaCondicion = cadenaCondicion + " '[" + valorInput + "]'." + objCondicionalActual.getOperador() + "([" + objCondicionalActual.getIdentificador1() + "])";
                } else {
                    cadenaCondicion = cadenaCondicion + " [" + objCondicionalActual.getIdentificador1() + "]" + objCondicionalActual.getOperador() + "" + valorInput + "";
                }
                objCondicionalActual = new Condicional();
                objVariable1 = new MetVariable();
                objVariable2 = new MetVariable();
                valorInput = "";
                habilitaValor = false;

            } else if (cadenaCondicion.isEmpty()) {
                if (objCondicionalActual.getOperador().equals("includes")) {
                    cadenaCondicion = cadenaCondicion + " '[" + valorInput + "]'." + objCondicionalActual.getOperador() + "([" + objCondicionalActual.getIdentificador1() + "])";
                } else {
                    cadenaCondicion = cadenaCondicion + " [" + objCondicionalActual.getIdentificador1() + "]" + objCondicionalActual.getOperador() + "" + valorInput + "";
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

    public void aniadirSalto() throws Exception {

        if (!cadenaCondicion.isEmpty()) {

            if (!cadenaSalto.isEmpty()) {
                cadenaSalto = cadenaSalto + ";";
            }

            for (MetVariable v : listarVariable) {
                if (v.getIdVar().equals(objVariable0.getIdVar())) {
                    codSalto = v.getIdentificador();
                }
            }
            //codSalto = objVariable0.getIdentificador();
            cadenaSalto = cadenaSalto + "[" + codSalto + "]~" + cadenaCondicion + "";
            objSaltoActual.setSalto(cadenaSalto);
            objCondicionalActual = new Condicional();
            objVariable0 = new MetVariable();
            objVariable1 = new MetVariable();
            objVariable2 = new MetVariable();
            cadenaCondicion = "";
            codSalto = "";
            valorInput = "";
            habilitaValor = false;
        } else {
            addWarningMessage("Ingrese el salto");
        }
    }

    public void refrescaModal() {
        try {
            codSalto = "";
            habilitaValor = false;
            cadenaCondicion = "";
            objVariable0 = new MetVariable();
            objVariable1 = new MetVariable();
            objVariable2 = new MetVariable();
            objCondicionalActual = new Condicional();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void guardarSalto() {
        try {
            if (isHabilitaEdicion() == false) {
                if(objSaltoActual.getCodVar()!=null){
                objSaltoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objSaltoActual.setEstadoLogico(true);
                getMetSaltoServicioRemote().crearSalto(objSaltoActual);
                addSuccessMessage("Registro Guardado");
                listarSaltoPorSeccion();
                refrescaNuevo();
               }else{
                addWarningMessage("Complete todos los campos" );}
            } else {
                objSaltoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getMetSaltoServicioRemote().editarSalto(objSaltoActual);
                addSuccessMessage("Registro Actualizado");
                listarSaltoPorSeccion();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro:  " + e.getMessage());
        }
    }

    public void refrescaNuevo() {
        try {
            objSaltoActual = new MetSalto();
            objCondicionalActual = new Condicional();
            cadenaSalto = "";
            cadenaCondicion = "";
            habilitaEdicion = false;
            habilitaSalto = false;
            habilitaValor = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarSaltoCampos(MetSalto salto) {
        try {
            objSaltoActual = new MetSalto();
            objSaltoActual = salto;
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void confirmaEliminar(MetSalto salto) {
        try {
            salto.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            salto.setEstadoLogico(false);
            getMetSaltoServicioRemote().editarSalto(salto);
            addSuccessMessage("Registro Eliminado");
            listarSaltoPorSeccion();
            objSaltoActual = new MetSalto();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }
    
    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {    
        this.usuarioControlador = usuarioControlador;
    }

    public MetSalto getObjSaltoActual() {
        return objSaltoActual;
    }

    public void setObjSaltoActual(MetSalto objSaltoActual) {
        this.objSaltoActual = objSaltoActual;
    }

    public List<MetSalto> getListarSaltos() {
        return listarSaltos;
    }

    public void setListarSaltos(List<MetSalto> listarSaltos) {
        this.listarSaltos = listarSaltos;
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

    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public List<MetVariable> getListarVariable() {
        return listarVariable;
    }

    public void setListarVariable(List<MetVariable> listarVariable) {
        this.listarVariable = listarVariable;
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

    public Condicional getObjCondicionalActual() {
        return objCondicionalActual;
    }

    public void setObjCondicionalActual(Condicional objCondicionalActual) {
        this.objCondicionalActual = objCondicionalActual;
    }

    public String getCadenaSalto() {
        return cadenaSalto;
    }

    public void setCadenaSalto(String cadenaSalto) {
        this.cadenaSalto = cadenaSalto;
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

    public List<MetOperativo> getListarOperativo() {
        return listarOperativo;
    }

    public void setListarOperativo(List<MetOperativo> listarOperativo) {
        this.listarOperativo = listarOperativo;
    }

    public MetOperativo getObjOperativoActual() {
        return objOperativoActual;
    }

    public void setObjOperativoActual(MetOperativo objOperativoActual) {
        this.objOperativoActual = objOperativoActual;
    }

    public String getCadenaCondicion() {
        return cadenaCondicion;
    }

    public void setCadenaCondicion(String cadenaCondicion) {
        this.cadenaCondicion = cadenaCondicion;
    }

    public boolean isHabilitaSalto() {
        return habilitaSalto;
    }

    public void setHabilitaSalto(boolean habilitaSalto) {
        this.habilitaSalto = habilitaSalto;
    }

    public String getCodSalto() {
        return codSalto;
    }

    public void setCodSalto(String codSalto) {
        this.codSalto = codSalto;
    }

    public MetVariable getObjVariable0() {
        return objVariable0;
    }

    public void setObjVariable0(MetVariable objVariable0) {
        this.objVariable0 = objVariable0;
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

    public List<Object[]> getListarSaltosForm() {
        return listarSaltosForm;
    }

    public void setListarSaltosForm(List<Object[]> listarSaltosForm) {
        this.listarSaltosForm = listarSaltosForm;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    //</editor-fold>

    
}
