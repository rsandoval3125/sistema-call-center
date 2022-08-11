/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.formularios;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.ejb.entidades.OperadorAsignacion;
import ec.gob.inec.captura.jsf.controlador.modulos.*;
import ec.gob.inec.captuta.jsf.util.UtilsDate;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;


/**
 *
 * @author dgarcia
 */
@ManagedBean
@ViewScoped
public class AsignacionesControlador extends BaseControlador {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(AsignacionesControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{baseCapturaControlador}")
    private BaseCapturaControlador bcc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;
    @ManagedProperty("#{vistaControlador}")
    
    private VistaControlador vc;
    private CaptCabecera cabeceraActual;    
    private CaptDetalleH captDetalleH;  
    
    private static final long serialVersionUID = 1L;
         
    public int totalAsignaciones;
    private int totalInasistentes;
  
    private CaptDetalleH asignacion;
    private Integer aux=0;
    private Integer listaRolesDelUsuarioSelected;
    private SegUsuario operadorSelected;
    private AdmPersonal operadorAutomated;
    private List<MetCatalogo> listaDpaProvinciaSelect;
    private MetCatalogo provinciaSelected;
    
    private List<String> lstAsignacionesSelected = new ArrayList<>();
    private List<CaptDetalleH> checkOperador;   
    private List<Object[]> listaRolesDelUsuario; 
    private List<CaptDetalleH> listarAsignaciones;
    private String Rolseleccionado;    
    private List<CaptDetalleH> listarAsignacionesSel;
    
    String ipServer;
    String emailRemitente;
    String emailReceptor;
    String mensajeCorreo;
    File pdf = null;

    String SMTP_USERNAME;
    String SMTP_PASSWORD;
    String HOST;
    String SMTP_PORT;
    
    
    public String getRolseleccionado() {
        return Rolseleccionado;
    }

    public void setRolseleccionado(String Rolseleccionado) {
        this.Rolseleccionado = Rolseleccionado;
    }
  
    public AsignacionesControlador() {
    }

    @PostConstruct
    public void inicializar() {
        try {
            cabeceraActual = new CaptCabecera();
            captDetalleH = new CaptDetalleH();                      
            listarAsignaciones = new ArrayList<>();            
            listaRolesDelUsuario = new ArrayList<Object[]>();    
            listaDpaProvinciaSelect = new ArrayList<>(); 
            
            listaDpaProvinciaSelect = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_DPA_PROV");
            listarAsignaciones = bc.getCaptDetalleHServicioRemote().listarIncidencias("ASIGNADO",captDetalleH.getVal02(),captDetalleH.getVal04(),captDetalleH.getVal06());           
            listaRolesDelUsuario = bc.getCaptDetalleHServicioRemote().listarEjecutarConsultaObj("buscarRolxNombre", Arrays.asList());
            checkOperador = bc.getCaptDetalleHServicioRemote().listarAsignados("ASIGNADO");
            
            ipServer = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("MAIL_PROVIDER").getValor();
            emailRemitente = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("MAIL_FROM").getValor();
            
            SMTP_USERNAME = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("SMTP_USERNAME").getValor();
            SMTP_PASSWORD = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("SMTP_PASSWORD").getValor();
            HOST = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("HOST").getValor();
            SMTP_PORT = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("SMTP_PORT").getValor();
      
//          listaRolesDelUsuario = bc.getSegRolServicioRemote().listarRolxNombre();
//          listarAsignaciones();
//          listarAsignaciones = bc.getCaptDetalleHServicioRemote().listarDetHPorCab(24);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }
    
    public void seleccionarOperador(ValueChangeEvent event) {
        try {
            Integer usu = Integer.valueOf(event.getNewValue().toString());
            operadorSelected = bc.getSegUsuarioServicioRemote().buscarxID(usu);         
            listaRolesDelUsuarioSelected = operadorSelected.getCodPersonal().getIdPersonal();
            //operadorSelected = bc.getAdmPersonalServicioRemote().buscarPorCodigo(usu);
            //listaRolesDelUsuarioSelected = operadorSelected.getIdPersonal();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleChange(ValueChangeEvent event) {
        System.out.println("New value: " + event.getNewValue());
    }
    
    public  void recuperarCodProv(String codProv) {
        aux=(Integer.parseInt(codProv));
    }
          
    public CaptDetalleH PruebaRegistroAsignacionesAut(){
        try {        

            List<OperadorAsignacion> operadores = bc.getCaptDetalleHServicioRemote().contarAsignacionPorUsuario(aux);
            //CaptDetalleH operador = operadores.stream().filter(data -> data.getNumAsignaciones() < 8).findFirst().orElse(null);
            Integer id = 0;
            Integer idOperador=0;            
            
            for (int i = 0; i< operadores.size(); i++) {
                if (operadores.get(i).getNumAsignaciones() < 8) {
                    id = operadores.get(i).getIdDetalle();
                    idOperador = operadores.get(i).getIdOperador();                    
                    break;
                }
            }
               
           operadorAutomated = bc.getAdmPersonalServicioRemote().buscarPorCodigo(idOperador);
            if(operadorAutomated != null){
                CaptDetalleH operadorAut = bc.getCaptDetalleHServicioRemote().buscarDetHPorId(id);         
                operadorAut.setVal31("ASIGNADO");  
                operadorAut.setVal32(operadorAutomated.getIdPersonal().toString());
                operadorAut.setVal33(operadorAutomated.getPrimerApellido() + " " + operadorAutomated.getPrimerNombre());             
            return operadorAut;                            
                
            } else {
                bc.addErrorMessage("NO EXISTEN OPERADORES");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(AsignacionesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
         return null;  
    }
    
    public void PruebaRegistroAsignaciones() throws Exception {    
        try {
            if (listaRolesDelUsuarioSelected != null) {
                if (listarAsignacionesSel != null) {
                    List<CaptDetalleH> checkOperadorAux = new ArrayList<>();
                    List<CaptDetalleH> emailAux = new ArrayList<>();
                    for (CaptDetalleH det : listarAsignacionesSel) {
                        checkOperadorAux.add(det);
                    }
                    
                    if (!checkOperadorAux.isEmpty()) {
                        for (CaptDetalleH operador : checkOperadorAux) {
                            
                            operador.setVal31("ASIGNADO");   
                            operador.setVal26(operadorSelected.getCodPersonal().getIdPersonal().toString()); 
                            operador.setVal32(operadorSelected.getCodPersonal().getIdPersonal().toString()); 
                            operador.setVal33(operadorSelected.getCodPersonal().getPrimerApellido() + " " + operadorSelected.getCodPersonal().getPrimerNombre());
                            operador.setCorreoOperador(operadorSelected.getCodPersonal().getCorreoInstitucional());
                            operador.setFechaAsignacion(UtilsDate.fechaString_Date(UtilsDate.fechaSistema()));
                            emailAux.add(operador);
                            bc.getCaptDetalleHServicioRemote().editarDetalleH(operador);
                        }
                        
                        for (CaptDetalleH operadorEmail : emailAux) {
                            if (operadorEmail != null && operadorEmail.getVal20().equals("ABIERTO")) {
                                envioEmail(operadorEmail.getCorreoOperador(), operadorEmail);
                            }
                        }
                        
                        
                        bc.addSuccessMessage("DATOS ASIGNADOS CORRETAMENTE");
                        checkOperador = bc.getCaptDetalleHServicioRemote().listarAsignados("ASIGNADO");
                        listarAsignaciones = bc.getCaptDetalleHServicioRemote().listarIncidencias("ASIGNADO",captDetalleH.getVal02(),captDetalleH.getVal04(),captDetalleH.getVal06());
                        listarAsignacionesSel = new ArrayList<>();
                        operadorSelected = new SegUsuario();
                        listaRolesDelUsuarioSelected = null;
                        emailAux = new ArrayList<>();
                        
                        
                    }
                }
            } else {
                bc.addErrorMessage("Seleccione un operador");
            }
                
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }            
    }
    
    public void envioEmail(String mail, CaptDetalleH captDetalleH) {
           
        try {            
            emailReceptor = mail;
            if (emailReceptor != null) {
                emailReceptor = mail;
                mensajeCorreo = "Estimado ciudadana/o con C.I.: " + captDetalleH.getVal07()
                        + "\n\nInstituto Nacional de Estadística y Censos INEC \n"
                        + "\n Le informa que se le ha asignado el requerimiento " +captDetalleH.getTipoFormulario()+" con los siguientes datos:";
                if(listarAsignacionesSel != null && captDetalleH.getVal20().equals("ABIERTO"))
                       mensajeCorreo = mensajeCorreo
                        +"\n Ticket N° "+captDetalleH.getIdDeth()
                        +"\n Informacion de residencia "+captDetalleH.getVal02()+" - "+captDetalleH.getVal04()
                        +"\n Nombres y Apellidos "+ captDetalleH.getVal09()+" "+captDetalleH.getVal08()
                        +"\n Tipo Requerimiento: "+captDetalleH.getTipoFormulario()
                        +"\n Estado de Ticket: "+captDetalleH.getVal20()
                        +"\n\n Pará mayor información comuníquese al 1800080808";
                
                bc.getCaptCabeceraServicioRemote().enviarEmail(ipServer, emailRemitente, mail, mensajeCorreo, null, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, HOST);
               
//                if (bc.getCaptCabeceraServicioRemote().enviarEmail(ipServer, emailRemitente, mail, mensajeCorreo, null, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, HOST) == false) {
//                    bc.addErrorMessage("Se produjo un error en el envío del email!. Permiso denegado!");
//                } else {
//                    bc.addSuccessMessage("La información ha sido enviada a su correo personal, revisar la bandeja de entrada o Spam");
//                }
               
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            bc.addErrorMessage("Correo no enviado");

        }
    }
    
    
    public void mostrar(CaptDetalleH captDetalle){
        try {
            if(captDetalle != null){
                captDetalleH = captDetalle;
            }
           
        } catch (Exception e) {
        }
    }

    public void CheckOperador(ValueChangeEvent event) {
        String[] check = (String[]) event.getNewValue();
        for(int i = 0; i < check.length; i++) {
            //checkOperador.add(check[i]);
        }
    }
   
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

    public List<CaptDetalleH> getListarAsignaciones() {
        return listarAsignaciones;
    }

    public void setListarAsignaciones(List<CaptDetalleH> listarAsignaciones) {
        this.listarAsignaciones = listarAsignaciones;
    }  
    
    public List<String> getLstAsignacionesSelected() {
        return lstAsignacionesSelected;
    }

    public void setLstAsignacionesSelected(List<String> lstAsignacionesSelected) {
        this.lstAsignacionesSelected = lstAsignacionesSelected;
    }
    
    public int getTotalAsignaciones() {
        return totalAsignaciones;
    }

    public void setTotalAsignaciones(int totalAsignaciones) {
        this.totalAsignaciones = totalAsignaciones;
    }

    public int getTotalInasistentes() {
        return totalInasistentes;
    }

    public void setTotalInasistentes(int totalInasistentes) {
        this.totalInasistentes = totalInasistentes;
    }

    public CaptDetalleH getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(CaptDetalleH asignacion) {
        this.asignacion = asignacion;
    }

    public List<Object[]> getListaRolesDelUsuario() {
        return listaRolesDelUsuario;
    }

    public void setListaRolesDelUsuario(List<Object[]> listaRolesDelUsuario) {
        this.listaRolesDelUsuario = listaRolesDelUsuario;
    }

    public Integer getListaRolesDelUsuarioSelected() {
        return listaRolesDelUsuarioSelected;
    }

    public void setListaRolesDelUsuarioSelected(Integer listaRolesDelUsuarioSelected) {
        this.listaRolesDelUsuarioSelected = listaRolesDelUsuarioSelected;
    }

    public List<CaptDetalleH> getCheckOperador() {
        return checkOperador;
    }

    public void setCheckOperador(List<CaptDetalleH> checkOperador) {
        this.checkOperador = checkOperador;
    }  
    

    public List<CaptDetalleH> getListarAsignacionesSel() {
        return listarAsignacionesSel;
    }

    public void setListarAsignacionesSel(List<CaptDetalleH> listarAsignacionesSel) {
        this.listarAsignacionesSel = listarAsignacionesSel;
    }

    public List<MetCatalogo> getListaDpaProvinciaSelect() {
        return listaDpaProvinciaSelect;
    }

    public void setListaDpaProvinciaSelect(List<MetCatalogo> listaDpaProvinciaSelect) {
        this.listaDpaProvinciaSelect = listaDpaProvinciaSelect;
    }

    public MetCatalogo getProvinciaSelected() {
        return provinciaSelected;
    }

    public void setProvinciaSelected(MetCatalogo provinciaSelected) {
        this.provinciaSelected = provinciaSelected;
    } 
  
}

