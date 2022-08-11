/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.formularios;


import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.jsf.controlador.modulos.*;
import ec.gob.inec.captuta.jsf.util.ConsultaCiudadanoServicio;
import ec.gob.inec.captuta.jsf.util.UtilSiac;
import ec.gob.inec.captuta.jsf.util.UtilsDate;
import ec.gob.inec.captuta.jsf.util.ValidadorUtil;
import ec.gob.inec.ejb.utils.ReflexionEntidad;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import entidades.CiudadanoTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.PrimeFaces;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;


/**
 *
 * @author dajila
 */
@ManagedBean
@ViewScoped
public class InquietudesControlador extends BaseControlador {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(InquietudesControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{baseCapturaControlador}")
    private BaseCapturaControlador bcc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vc;
//    @ManagedProperty("#{asignacionesControlador}")
//    private AsignacionesControlador ac;
    private CaptCabecera cabeceraActual;

    private List<MetCatalogo> listaDpaProvinciaSelect;
    private List<MetCatalogo> listaDpaCantonSelect;
    private List<MetCatalogo> listaDpaCiudadSelect;
    
    private MetCatalogo provinciaSelected;
    private MetCatalogo cantonSelected;
    private MetCatalogo parroquiaSelected;
    
    private MetCatalogo tipoInfSelect;
    private MetCatalogo tipoPlatSelect;
    private MetCatalogo tipoEstadoActividadSelect;
    private MetCatalogo tipoUsuario;
    private MetCatalogo tipoIdentificacion;
    
    private boolean provinciaRequerido;
    private boolean cantonRequerido;
    
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private String correo;
    private String correo2;
    private String telefono;
    private String respuesta;
    private String nacionalidad;
    
    private List<MetCatalogo> listaTipoInfSelect;
    private List<MetCatalogo> listaPlatSelect;
    private List<MetCatalogo> listaEstadoActividadSelect;
    private List<MetCatalogo> listaTipoUsuario;
    private List<MetCatalogo> listaTipoIdentificacion;
      
    private String detalleInf;
    
    private CaptDetalleH captDetalleH;
    private List<CaptDetalleH> listarInquietudes;
//    private List<CaptDetalleH> checkOperador;
    private boolean habilitaEdicion;
    Connection conn = null;
    private boolean registroPublico = false;
    

  //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR ">
    /**
     * Creates a new instance of GeneracionFormulario
     */
    public InquietudesControlador() {
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" METODOS ">
    @PostConstruct
    public void inicializar() {
        try {
            
            cabeceraActual = new CaptCabecera();
            listaDpaProvinciaSelect = new ArrayList<>();
            listaDpaCantonSelect = new ArrayList<>();
            listaDpaCiudadSelect = new ArrayList<>();
            listaTipoInfSelect = new ArrayList<>();
            listaPlatSelect = new ArrayList<>();
            listarInquietudes = new ArrayList<>();
            listaEstadoActividadSelect = new ArrayList<>();
            listaTipoUsuario = new ArrayList<>(); 
            listaTipoIdentificacion = new ArrayList<>(); 
           
            listaDpaProvinciaSelect = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_DPA_PROV");
            listaTipoInfSelect = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIPO_REQ_INF");
            listaPlatSelect = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_PLATAFORMA");
            listaEstadoActividadSelect = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_EST_ACTIVIDAD"); 
            listaTipoUsuario = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIPOS_USUARIO");
            listaTipoIdentificacion = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIPO_IDENTIFICACION");
            cabeceraActual = bc.getCaptCabeceraServicioRemote().buscarFormCabPorClave("F001");
            
            onChangeProvincia();
            limpiarDatos();
            listarInquietudes();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void cancelar() {
         limpiarDatos();
    }
    
    public void limpiarDatos(){
        captDetalleH = new CaptDetalleH();
        
        provinciaSelected = new MetCatalogo();
        cantonSelected = new MetCatalogo();
        parroquiaSelected = new MetCatalogo();
        tipoUsuario = new MetCatalogo();
        tipoIdentificacion = new MetCatalogo();
        
        numeroDocumento = null;
        apellido = null;
        nombre = null;
        
        correo = null;
        correo2 = null;
        telefono = null;
        respuesta = null;
        nacionalidad = null;
        
        tipoInfSelect =  new MetCatalogo();
        tipoPlatSelect =  new MetCatalogo();
        detalleInf = null;
        tipoEstadoActividadSelect =  new MetCatalogo();;
        
        habilitaEdicion = false;
        registroPublico = false;
                
    }

      public void onChangeProvincia() {
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (params.get("action") != null) {
                cantonSelected = new MetCatalogo();
            }
            if (provinciaSelected != null && provinciaSelected.getIdCatalogo() != null) {
                provinciaRequerido = false;
                listaDpaCantonSelect = getMetCatalogoServicioRemote().listarCatalogosPorPadre1(provinciaSelected.getIdCatalogo());
            } else {
                listaDpaCantonSelect = new ArrayList<>();
                provinciaRequerido = true;
            }

            onChangeCanton();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
      
     public void onChangeCanton() {
        try {
            if (cantonSelected != null && cantonSelected.getIdCatalogo() != null) {
                cantonRequerido = false;
                listaDpaCiudadSelect = getMetCatalogoServicioRemote().listarCatalogosPorPadre1(cantonSelected.getIdCatalogo());
            } else {
                listaDpaCiudadSelect = new ArrayList<>();
                cantonRequerido = true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
     
    public void guardarInquietudesPublico () {
        try {
                boolean continuar = true;
                if(!ValidadorUtil.validaCedula(getNumeroDocumento()) && getTipoIdentificacion().getAlias().equals("SIAC_TPI_01")){
                    continuar = false;
                    UtilSiac.mostrarMensaje("Cédula Incorrecta, Ingrese una cédula correcta", "warning", "Advertencia, revise sus datos");
                }else if(!ValidadorUtil.validaRUC(getNumeroDocumento()) && getTipoIdentificacion().getAlias().equals("SIAC_TPI_02")){
                    continuar = false;
                    UtilSiac.mostrarMensaje("Ruc Incorrecto, Ingrese un ruc correcta", "warning", "Advertencia, revise sus datos");
                }
                
                if(continuar){
                    registroPublico = true;
                    for (MetCatalogo m : listaEstadoActividadSelect) {
                        if(m.getNombre().equals("ABIERTO")){
                            tipoEstadoActividadSelect = m;
                        }
                    }
                     if(cabeceraActual != null){
                        captDetalleH = new CaptDetalleH();
                        llenarDatosCaptDetalleH();
                        captDetalleH.setEstadoProc("A");
                        captDetalleH.setVal32(null);
                        captDetalleH.setVal33(null);
                        captDetalleH.setVal27("PUBLICO");
    //                    ASIGNACION AUTOMATICA
    //                    ac.recuperarCodProv(captDetalleH.getVal05());
    //                    captDetalleH.setVal31(ac.PruebaRegistroAsignacionesAut().getVal31());
    //                    captDetalleH.setVal32(ac.PruebaRegistroAsignacionesAut().getVal32());
    //                    captDetalleH.setVal33(ac.PruebaRegistroAsignacionesAut().getVal33());

                        //ac.PruebaRegistroAsignacionesAut();
                        bc.getCaptDetalleHServicioRemote().crearDetalleH(captDetalleH);
                        UtilSiac.mostrarMensaje("Datos almacenados correctamente", "success", "Su proceso se realizó con éxito");

                        List<CaptDetalleH> captDetalleHAux = new ArrayList<>();
                        captDetalleHAux = ( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("buscarCaptDetalleHPorCabYClave", Arrays.asList(
                        cabeceraActual.getIdCab(),captDetalleH.getClave()));
                        if(captDetalleHAux!= null && captDetalleHAux.size()>0)
                            captDetalleH = captDetalleHAux.get(0);
                        envioEmail(getCorreo(),captDetalleH);
                    }
                    limpiarDatos();
             }
           
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void check(ActionEvent e) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Your Code Is Correct !", null));
    }
    
     
    public void guardarInquietudes() {
         try {
                if(!habilitaEdicion){
                    if(cabeceraActual != null){
                        boolean continuar = true;
                        if(!ValidadorUtil.validaCedula(getNumeroDocumento()) && getTipoIdentificacion().getAlias().equals("SIAC_TPI_01")){
                            continuar = false;
                             UtilSiac.presentaMensaje(FacesMessage.SEVERITY_ERROR,"Cédula Incorrecta, Ingrese una cédula correcta");
                             PrimeFaces.current().ajax().update("form:messageCedula", "form:manage-inquietudes-content");
                        }else if(!ValidadorUtil.validaRUC(getNumeroDocumento()) && getTipoIdentificacion().getAlias().equals("SIAC_TPI_02")){
                            continuar = false;
                            UtilSiac.presentaMensaje(FacesMessage.SEVERITY_ERROR,"Ruc Incorrecta, Ingrese una Ruc correcta");
                            PrimeFaces.current().ajax().update("form:messageCedula", "form:manage-inquietudes-content");
                        }
                        if(continuar){
                            captDetalleH = new CaptDetalleH();
                            llenarDatosCaptDetalleH();
                            captDetalleH.setVal27("INEC");
                            captDetalleH.setEstadoProc("A");
                            captDetalleH.setTipoFormulario(cabeceraActual.getInfo1());                           
                            bc.getCaptDetalleHServicioRemote().crearDetalleH(captDetalleH);
    //                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("DATOS INGRESADOS CORRECTAMENTE"));
                            UtilSiac.presentaMensaje(FacesMessage.SEVERITY_INFO,"DATOS INGRESADOS CORRECTAMENTE");

                            List<CaptDetalleH> captDetalleHAux = new ArrayList<>();
                            captDetalleHAux = ( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("buscarCaptDetalleHPorCabYClave", Arrays.asList(
                            cabeceraActual.getIdCab(),captDetalleH.getClave()));
                            if(captDetalleHAux!= null && captDetalleHAux.size()>0)
                                captDetalleH = captDetalleHAux.get(0);

                            envioEmail(getCorreo(),captDetalleH);
                            listarInquietudes();
                            PrimeFaces.current().ajax().update("form:messages", "form:dt-inquietudes");
                            limpiarDatos();
                        }
                    }
                }else{
                      llenarDatosCaptDetalleH();
                      bc.getCaptDetalleHServicioRemote().editarDetalleH(captDetalleH);
                      habilitaEdicion = false;
                       if(tipoEstadoActividadSelect != null && (tipoEstadoActividadSelect.getIdCatalogo() != null && tipoEstadoActividadSelect.getIdCatalogo() > 0) && tipoEstadoActividadSelect.getNombre().equals("CERRADO")){
                            envioEmail(getCorreo(),captDetalleH);  
                       }
                
//                      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("DATOS MODIFICADOS CORRECTAMENTE"));
                      UtilSiac.presentaMensaje(FacesMessage.SEVERITY_INFO,"DATOS MODIFICADOS CORRECTAMENTE");
                      listarInquietudes();
                      PrimeFaces.current().ajax().update("form:messages", "form:dt-inquietudes");
                      limpiarDatos();
                }

               
                
          } catch (Exception ex) {
            Logger.getLogger(InquietudesControlador.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    public void modificarInquietudes(){
        try {
                llenarDatosCaptDetalleH();
                bc.getCaptDetalleHServicioRemote().editarDetalleH(captDetalleH);
                habilitaEdicion = false;
                if(tipoEstadoActividadSelect != null && (tipoEstadoActividadSelect.getIdCatalogo() != null && tipoEstadoActividadSelect.getIdCatalogo() > 0) && tipoEstadoActividadSelect.getNombre().equals("CERRADO")){
                     envioEmail(getCorreo(),captDetalleH);  
                }
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("DATOS MODIFICADOS CORRECTAMENTE"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-inquietudes");
                PrimeFaces.current().executeScript("PF('manageInquietudesDialog').hide()");
                limpiarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     
    public void llenarDatosCaptDetalleH()throws Exception{
        captDetalleH.setCodCab(cabeceraActual);
        captDetalleH.setClave(getNumeroDocumento());

        captDetalleH.setVal01(provinciaSelected.getIdCatalogo().toString());//Codigo provincias
        captDetalleH.setVal02(provinciaSelected.getNombre());

        captDetalleH.setVal03(cantonSelected.getIdCatalogo().toString());//Codigo provincias
        captDetalleH.setVal04(cantonSelected.getNombre());

        captDetalleH.setVal05(parroquiaSelected.getIdCatalogo().toString());//Codigo provincias
        captDetalleH.setVal06(parroquiaSelected.getNombre());

        
        captDetalleH.setVal07(getNumeroDocumento());
        captDetalleH.setVal08(ValidadorUtil.validarDobleEspacios(getApellido()));
        captDetalleH.setVal09(ValidadorUtil.validarDobleEspacios(getNombre()));
        captDetalleH.setVal10(getCorreo());
        captDetalleH.setVal11(ValidadorUtil.validarDobleEspacios(getDetalleInf()));

        captDetalleH.setVal12(tipoInfSelect.getIdCatalogo().toString());
        captDetalleH.setVal13(tipoInfSelect.getNombre());

        if(tipoPlatSelect != null && (tipoPlatSelect.getIdCatalogo() != null && tipoPlatSelect.getIdCatalogo() > 0)){
            captDetalleH.setVal15(tipoPlatSelect.getIdCatalogo().toString());// catalogo protaforma
            captDetalleH.setVal16(tipoPlatSelect.getNombre());
        }

        if(tipoEstadoActividadSelect != null && (tipoEstadoActividadSelect.getIdCatalogo() != null && tipoEstadoActividadSelect.getIdCatalogo() > 0)){
              captDetalleH.setVal19(tipoEstadoActividadSelect.getIdCatalogo().toString());
              captDetalleH.setVal20(tipoEstadoActividadSelect.getNombre());
        }
        
        captDetalleH.setVal21(getTelefono());
        
    
        captDetalleH.setVal25(getRespuesta());//respuesta   
        captDetalleH.setVal26(getNacionalidad());//nacionalidad   
        captDetalleH.setVal29(cabeceraActual.getClave());//NOMBRE DEL FORMULARIO INQUIETUDES
//        captDetalleH.setFechaAsignacion(UtilsDate.fechaString_Date(UtilsDate.fechaSistema()));
        captDetalleH.setTipoFormulario(cabeceraActual.getInfo1());
        captDetalleH.setVal30(vc.getCodAuditoria("SIAC_TIPO_ACTIVIDAD_01"));
        // fecha de registro y fecha de edicion
        if(!habilitaEdicion){
             captDetalleH.setVal28(UtilsDate.fechaSistema());//fecha de recepcion
        }else{
            captDetalleH.setFechahoraEdicion(UtilsDate.fechaString_Date(UtilsDate.fechaSistema()));
        }
        if(uc.getUsuarioActual() != null && uc.getUsuarioActual().getCodPersonal() != null){
            String nombreCompletos = "";
            if(uc.getUsuarioActual().getCodPersonal().getPrimerApellido() != null)
                nombreCompletos = nombreCompletos+uc.getUsuarioActual().getCodPersonal().getPrimerApellido()+" ";
//            if(uc.getUsuarioActual().getCodPersonal().getSegundoApellido() != null)
//                nombreCompletos = nombreCompletos+uc.getUsuarioActual().getCodPersonal().getSegundoApellido()+" ";
            if(uc.getUsuarioActual().getCodPersonal().getPrimerNombre() != null)
                nombreCompletos = nombreCompletos+uc.getUsuarioActual().getCodPersonal().getPrimerNombre()+" ";
//            if(uc.getUsuarioActual().getCodPersonal().getSegundoNombre() != null)
//                nombreCompletos = nombreCompletos+uc.getUsuarioActual().getCodPersonal().getSegundoNombre();

            captDetalleH.setVal32(uc.getUsuarioActual().getCodPersonal().getIdPersonal().toString());
            captDetalleH.setVal33(ValidadorUtil.validarDobleEspacios(nombreCompletos));
        }
        
        if(tipoUsuario != null ){
            captDetalleH.setTipoUsuario(tipoUsuario.getNombre());
            captDetalleH.setCodigoTipoUsuario(tipoUsuario.getIdCatalogo().toString());
        }
        if(tipoIdentificacion != null){
            captDetalleH.setTipoIdentificacion(tipoIdentificacion.getNombre());
            captDetalleH.setCodigoTipoIdentificacion(tipoIdentificacion.getIdCatalogo().toString());
            
        }
            
    }
            
    public void listarIncidenciasxFiltro() throws Exception {
        try {
            if(cabeceraActual != null) {
                listarInquietudes = bc.getCaptDetalleHServicioRemote().listarIncidenciasxFiltro(provinciaSelected.getNombre(), cantonSelected.getNombre(), parroquiaSelected.getNombre());
                if (listarInquietudes.isEmpty()) {
                    bc.addWarningMessage("No se encontraron registros");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }  
    } 
    
    public void listarInquietudes() throws Exception {
         if(cabeceraActual != null && uc.getUsuarioActual()!= null){
            listarInquietudes =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("listarIncidenciasPorCabUsuarioFecha", Arrays.asList(
                    cabeceraActual.getIdCab(),uc.getUsuarioActual().getCodPersonal().getIdPersonal().toString(),UtilsDate.fechaSistema()));
         }
    }
    
    public void recuperarDatosInquietudes(CaptDetalleH captDetalleH){
        try {
           if(captDetalleH != null){
               habilitaEdicion = true;
               this.captDetalleH = captDetalleH;
               
               if(captDetalleH.getVal03() != null)
                    cantonSelected = getMetCatalogoServicioRemote().buscarCatalogoXId(Integer.parseInt(captDetalleH.getVal03()));
               if(cantonSelected != null)
                    provinciaSelected = getMetCatalogoServicioRemote().buscarCatalogoXId(cantonSelected.getCodPadre1().getIdCatalogo());
               onChangeProvincia();
               if(captDetalleH.getVal05() != null)
                    parroquiaSelected = getMetCatalogoServicioRemote().buscarCatalogoXId(Integer.parseInt(captDetalleH.getVal05()));
               
               numeroDocumento = captDetalleH.getVal07();
               apellido = captDetalleH.getVal08();
               nombre = captDetalleH.getVal09();
               correo = captDetalleH.getVal10();
               telefono = captDetalleH.getVal21(); 
               detalleInf = captDetalleH.getVal11(); 
               nacionalidad = captDetalleH.getVal23();
               
               if(captDetalleH.getVal12() != null)
                    tipoInfSelect = getMetCatalogoServicioRemote().buscarCatalogoXId(Integer.parseInt(captDetalleH.getVal12()));
               if(captDetalleH.getVal15() != null)
                    tipoPlatSelect = getMetCatalogoServicioRemote().buscarCatalogoXId(Integer.parseInt(captDetalleH.getVal15()));
               if(captDetalleH.getVal19() != null)
                    tipoEstadoActividadSelect = getMetCatalogoServicioRemote().buscarCatalogoXId(Integer.parseInt(captDetalleH.getVal19()));
               respuesta = captDetalleH.getVal25();
               if(captDetalleH.getCodigoTipoUsuario() != null){
                   tipoUsuario = getMetCatalogoServicioRemote().buscarCatalogoXId(Integer.parseInt(captDetalleH.getCodigoTipoUsuario()));
               }
               if(captDetalleH.getCodigoTipoIdentificacion() != null){
                   tipoIdentificacion = getMetCatalogoServicioRemote().buscarCatalogoXId(Integer.parseInt(captDetalleH.getCodigoTipoIdentificacion()));
               }
               
           }
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    
    public void openNew() {
        limpiarDatos();
    }
    
    public void limpiarNombre(){
        
    }
    
     public void consultaCiudadanoPorCedula(javax.faces.event.ActionEvent actionEvent){
//    public void consultaCiudadanoPorCedula(){
        try {
             String semillaAES;
             List<AdmBaseDatos> listar = new ArrayList<>();
             ConsultaCiudadanoServicio sv = new ConsultaCiudadanoServicio();
             if(ValidadorUtil.validaCedula(getNumeroDocumento())){
                String urlBase = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("urlBaseServicioWebRes").getValor();
                String urlSecurity = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("urlBaseServicioWebResSecurity").getValor();
//                String usuario  = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("usuario").getValor();
//                String password  = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("password").getValor();
                String usuario = null;
                String password = null;
                
                listar = bc.getAdmBaseDatosServicioRemote().listarTodo();
                 for (AdmBaseDatos bd : listar) {
                     if(bd.getAlias().equals("resusuario")){
                         usuario = bd.getPassword();
                     }
                     if(bd.getAlias().equals("respwd")){
                         password = bd.getPassword();
                     }
                 }
                 
                semillaAES = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
                password = ReflexionEntidad.desencripta("AES", password, semillaAES);
                usuario = ReflexionEntidad.desencripta("AES", usuario, semillaAES);
            
                CiudadanoTO ciudadano = sv.consultaCiudadanoPorCedula(getNumeroDocumento(),urlBase, urlSecurity,usuario,password);
                if(ciudadano != null && ciudadano.getCedulado() != null){
                    setApellido(ciudadano.getCedulado().getApellido1()+" "+ciudadano.getCedulado().getApellido2());
                    setNombre(ciudadano.getCedulado().getNombre1()+" "+ciudadano.getCedulado().getNombre2());
                }else{
                    setApellido(null);
                    setNombre(null);
                }
             }else{
                 
                 setApellido(null);
                 setNombre(null);
                UtilSiac.presentaMensaje(FacesMessage.SEVERITY_ERROR,"Cédula Incorrecta, Ingrese una cédula correcta");
                PrimeFaces.current().ajax().update("form:messageCedula", "form:manage-inquietudes-content");
             }
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void confirmaEliminar(CaptDetalleH captDetalleH) {
        try {
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }
    
    public void envioEmail(String mail, CaptDetalleH captDetalleH) {
        //Variables de Envio de Correo
        String ipServer;
        String emailRemitente;
        String emailReceptor;
        String mensajeCorreo = null;
        File pdf = null;
        
        String SMTP_USERNAME;
        String SMTP_PASSWORD;
        String HOST;
        String SMTP_PORT;
        try {
           
            
            ipServer = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("MAIL_PROVIDER").getValor();
            emailRemitente = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("MAIL_FROM").getValor();
            
            SMTP_USERNAME = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("SMTP_USERNAME").getValor();
            SMTP_PASSWORD = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("SMTP_PASSWORD").getValor();
            HOST = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("HOST").getValor();
            SMTP_PORT = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("SMTP_PORT").getValor();
            
            emailReceptor = mail;
            if (emailReceptor != null) {
                 mensajeCorreo = "Estimado ciudadana/o con C.I.: " + captDetalleH.getVal07()
                        + "\n\nInstituto Nacional de Estadística y Censos INEC \n"
                        + "\n Le informa que los datos del Formulario de Registro de consulta";
                 if(tipoEstadoActividadSelect != null && (tipoEstadoActividadSelect.getIdCatalogo() != null && tipoEstadoActividadSelect.getIdCatalogo() > 0) && tipoEstadoActividadSelect.getNombre().equals("CERRADO")){
                      mensajeCorreo = mensajeCorreo
                        +"\n Ticket N° "+captDetalleH.getIdDeth()
                        +"\n Informacion de residencia "+captDetalleH.getVal02()+" - "+captDetalleH.getVal04()
                        +"\n Nombres y Apellidos: "+ captDetalleH.getVal09()+" "+captDetalleH.getVal08()
                        +"\n Detalle de consulta: "+captDetalleH.getVal11()
                        +"\n Respuesta: "+captDetalleH.getVal25()
                        +"\n\n Pará mayor información comuníquese al 1800080808";
                      
                 }else{
                       mensajeCorreo = mensajeCorreo
                        +"\n Ticket N° "+captDetalleH.getIdDeth()
                        +"\n Información de residencia "+captDetalleH.getVal02()+" - "+captDetalleH.getVal04()
                        +"\n Nombres y Apellidos: "+ captDetalleH.getVal09()+" "+captDetalleH.getVal08()
                        +"\n Detalle de consulta: "+captDetalleH.getVal11()
                        +"\n han sido almacenados satisfactoriamente. \n"
                        +"\n\n Pará mayor información comuníquese al 1800080808";
                }
                 
//                List<File> listAdjunto = new ArrayList<File>();
//                if(captDetalleH != null && captDetalleH.getIdDeth()>0){
//                     pdf = PDF1(captDetalleH);
//                    if(pdf != null){
//                        listAdjunto.add(pdf);
//                    }
//                }

               if (bc.getCaptCabeceraServicioRemote().enviarEmail(ipServer, emailRemitente, mail, mensajeCorreo, null, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, HOST) == false) {
                   if(!registroPublico) 
                        bc.addErrorMessage("Se produjo un error en el envío del email!. Permiso denegado!");
                } else {
                   if(!registroPublico)
                        bc.addSuccessMessage("Código enviado a su correo personal, revisar en bandeja de entrada o Spam");
                }
                if(pdf!= null && pdf.exists())
                    pdf.delete();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            bc.addErrorMessage("Correo no enviado");

        }
    }
    
    public File PDF1(CaptDetalleH captDetalleH) throws JRException, IOException, Exception {
        
        try {

            inicializarConn();

            AdmParametroGlobal path;
            AdmParametroGlobal pathJaspers;
            File pdf = null;
            
//            path = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("pathInecImgCab");
            InputStream is = null;
            pathJaspers = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("pathInquietudes");

            AdmBaseDatos paramBDD = bc.getActualizaBDServicioRemote().recuperaParametros("sipe_siac");

            Class.forName(paramBDD.getDriver()).newInstance();
            DriverManager.setLoginTimeout(0);//Evita que se cierre la conexion

            String cadena = bc.getActualizaBDServicioRemote().crearURL(paramBDD.getRdbms(), paramBDD.getIp(), paramBDD.getPuerto(), paramBDD.getNombrebdd());
            conn = DriverManager.getConnection(cadena, paramBDD.getUsuario(), paramBDD.getPassword());

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("ID_INCIDENCIA", captDetalleH.getIdDeth());
            is = new URL(pathJaspers.getValor()).openStream();

            try {
                JasperPrint jasperPrint = JasperFillManager.fillReport(is, param, conn);
                is.close();
                pdf = respondeServidorCorreo(jasperPrint,("" + System.currentTimeMillis()));
            } catch (JRException jRException) {
                jRException.printStackTrace();
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, jRException);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (conn != null) {
                conn.close();
            }
            
            return pdf;

        } catch (Exception ex) {
            Logger.getLogger(InquietudesControlador.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, conn);
            bc.addErrorMessage("Se registran problemas al generar el PDF");
        }
        return null;
    }
    
     public void PDF2(CaptDetalleH captDetalleH) throws JRException, IOException, Exception {
        
        try {

            inicializarConn();

            AdmParametroGlobal path;
            AdmParametroGlobal pathJaspers;
            InputStream is = null;
            pathJaspers = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("pathInquietudes");

            AdmBaseDatos paramBDD = bc.getActualizaBDServicioRemote().recuperaParametros("sipe_siac");

            Class.forName(paramBDD.getDriver()).newInstance();
            DriverManager.setLoginTimeout(0);//Evita que se cierre la conexion

            String cadena = bc.getActualizaBDServicioRemote().crearURL(paramBDD.getRdbms(), paramBDD.getIp(), paramBDD.getPuerto(), paramBDD.getNombrebdd());
            conn = DriverManager.getConnection(cadena, paramBDD.getUsuario(), paramBDD.getPassword());

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("ID_INCIDENCIA", captDetalleH.getIdDeth());
            is = new URL(pathJaspers.getValor()).openStream();

            try {
                JasperPrint jasperPrint = JasperFillManager.fillReport(is, param, conn);
                is.close();
//                pdf = respondeServidorCorreo(jasperPrint,("" + System.currentTimeMillis()));
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=" + "\"Acta_tablet_" + (System.currentTimeMillis()) + ".pdf\"");
                ServletOutputStream stream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                FacesContext.getCurrentInstance().responseComplete();
                stream.close();

            } catch (JRException jRException) {
                jRException.printStackTrace();
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, jRException);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (conn != null) {
                conn.close();
            }
            

        } catch (Exception ex) {
            Logger.getLogger(InquietudesControlador.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, conn);
            bc.addErrorMessage("Se registran problemas al generar el PDF");
        }
    }
    
     public void inicializarConn() {
        try {
            String parametroAES = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
            String tipoCifrado = "AES";
            bc.getActualizaBDServicioRemote().pasarParametrosEncriptacion(tipoCifrado, parametroAES);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }
    

    public File respondeServidorCorreo(JasperPrint jasperPrint,
			String nombreReporte) {
            try {
                    File file = File.createTempFile(nombreReporte, ".pdf");

                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    exporter.setConfiguration(configuration);

                    exporter.exportReport();
                    return file;
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return null;
    }
    
    public Date convertirFecha(String fecha){
        try {
            if(fecha != null)
                return UtilsDate.fechaString_Date(fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    public void preProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFCellStyle style = wb.createCellStyle();        
        style.setFillBackgroundColor(IndexedColors.DARK_YELLOW.getIndex());
        for (org.apache.poi.ss.usermodel.Row row : sheet) {
            for (org.apache.poi.ss.usermodel.Cell cell : row) {
                switch (cell.getCellType()) {
                case STRING:
                    cell.setCellValue(cell.getStringCellValue().toUpperCase());
                    cell.setCellStyle(style);
                    break;
                }
            }
        }
    }
    
     
//</editor-fold>
// <editor-fold defaultstate="collapsed" desc=" GETTERSYSETTERS ">

   
    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }

    public BaseCapturaControlador getBcc() {
        return bcc;
    }

    public void setBcc(BaseCapturaControlador bcc) {
        this.bcc = bcc;
    }

    public UsuarioControlador getUc() {
        return uc;
    }

    public void setUc(UsuarioControlador uc) {
        this.uc = uc;
    }

    public VistaControlador getVc() {
        return vc;
    }

    public void setVc(VistaControlador vc) {
        this.vc = vc;
    }
	
//	public AsignacionesControlador getAc() {
//        return ac;
//    }
//
//    public void setAc(AsignacionesControlador ac) {
//        this.ac = ac;
//    }
    
    public List<MetCatalogo> getListaDpaProvinciaSelect() {
        return listaDpaProvinciaSelect;
    }

    public void setListaDpaProvinciaSelect(List<MetCatalogo> listaDpaProvinciaSelect) {
        this.listaDpaProvinciaSelect = listaDpaProvinciaSelect;
    }

    public List<MetCatalogo> getListaDpaCantonSelect() {
        return listaDpaCantonSelect;
    }

    public void setListaDpaCantonSelect(List<MetCatalogo> listaDpaCantonSelect) {
        this.listaDpaCantonSelect = listaDpaCantonSelect;
    }

    public List<MetCatalogo> getListaDpaCiudadSelect() {
        return listaDpaCiudadSelect;
    }

    public void setListaDpaCiudadSelect(List<MetCatalogo> listaDpaCiudadSelect) {
        this.listaDpaCiudadSelect = listaDpaCiudadSelect;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }

    public List<MetCatalogo> getListaTipoInfSelect() {
        return listaTipoInfSelect;
    }

    public void setListaTipoInfSelect(List<MetCatalogo> listaTipoInfSelect) {
        this.listaTipoInfSelect = listaTipoInfSelect;
    }

    public String getDetalleInf() {
        return detalleInf;
    }

    public void setDetalleInf(String detalleInf) {
        this.detalleInf = detalleInf;
    }
    
     public MetCatalogo getProvinciaSelected() {
        return provinciaSelected;
    }

    public void setProvinciaSelected(MetCatalogo provinciaSelected) {
        this.provinciaSelected = provinciaSelected;
    }

    public MetCatalogo getCantonSelected() {
        return cantonSelected;
    }

    public void setCantonSelected(MetCatalogo cantonSelected) {
        this.cantonSelected = cantonSelected;
    }

    public MetCatalogo getParroquiaSelected() {
        return parroquiaSelected;
    }

    public void setParroquiaSelected(MetCatalogo parroquiaSelected) {
        this.parroquiaSelected = parroquiaSelected;
    }

    public boolean isProvinciaRequerido() {
        return provinciaRequerido;
    }

    public void setProvinciaRequerido(boolean provinciaRequerido) {
        this.provinciaRequerido = provinciaRequerido;
    }

    public boolean isCantonRequerido() {
        return cantonRequerido;
    }

    public void setCantonRequerido(boolean cantonRequerido) {
        this.cantonRequerido = cantonRequerido;
    }

    public CaptDetalleH getCaptDetalleH() {
        return captDetalleH;
    }

    public void setCaptDetalleH(CaptDetalleH captDetalleH) {
        this.captDetalleH = captDetalleH;
    }
    
     public CaptCabecera getCabeceraActual() {
        return cabeceraActual;
    }

    public void setCabeceraActual(CaptCabecera cabeceraActual) {
        this.cabeceraActual = cabeceraActual;
    }

    public List<CaptDetalleH> getListarInquietudes() {
        return listarInquietudes;
    }

//    public List<CaptDetalleH> getCheckOperador() {
//        return checkOperador;
//    }
//
//    public void setCheckOperador(List<CaptDetalleH> checkOperador) {
//        this.checkOperador = checkOperador;
//    }   
    
    public void setListarInquietudes(List<CaptDetalleH> listarInquietudes) {
        this.listarInquietudes = listarInquietudes;
    }

    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public List<MetCatalogo> getListaPlatSelect() {
        return listaPlatSelect;
    }

    public void setListaPlatSelect(List<MetCatalogo> listaPlatSelect) {
        this.listaPlatSelect = listaPlatSelect;
    }

    public List<MetCatalogo> getListaEstadoActividadSelect() {
        return listaEstadoActividadSelect;
    }

    public void setListaEstadoActividadSelect(List<MetCatalogo> listaEstadoActividadSelect) {
        this.listaEstadoActividadSelect = listaEstadoActividadSelect;
    }
    
    public MetCatalogo getTipoInfSelect() {
        return tipoInfSelect;
    }

    public void setTipoInfSelect(MetCatalogo tipoInfSelect) {
        this.tipoInfSelect = tipoInfSelect;
    }

    public MetCatalogo getTipoPlatSelect() {
        return tipoPlatSelect;
    }

    public void setTipoPlatSelect(MetCatalogo tipoPlatSelect) {
        this.tipoPlatSelect = tipoPlatSelect;
    }

    public MetCatalogo getTipoEstadoActividadSelect() {
        return tipoEstadoActividadSelect;
    }

    public void setTipoEstadoActividadSelect(MetCatalogo tipoEstadoActividadSelect) {
        this.tipoEstadoActividadSelect = tipoEstadoActividadSelect;
    }
    
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public MetCatalogo getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(MetCatalogo tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<MetCatalogo> getListaTipoUsuario() {
        return listaTipoUsuario;
    }

    public void setListaTipoUsuario(List<MetCatalogo> listaTipoUsuario) {
        this.listaTipoUsuario = listaTipoUsuario;
    }
    
    public MetCatalogo getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(MetCatalogo tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public List<MetCatalogo> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<MetCatalogo> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }
    
    
    //</editor-fold>


}

