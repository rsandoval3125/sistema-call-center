/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.formularios;

import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.jsf.controlador.modulos.*;
import ec.gob.inec.captuta.jsf.util.UtilSiac;
import ec.gob.inec.captuta.jsf.util.UtilsDate;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.util.Constants;


/**
 *
 * @author dajila
 */
@ManagedBean
@ViewScoped
public class IncidenciasBuscarControlador extends BaseControlador {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(IncidenciasBuscarControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{baseCapturaControlador}")
    private BaseCapturaControlador bcc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vc;
    private CaptCabecera cabeceraActual;

    private List<MetCatalogo> listaDpaProvinciaSelect;
    private List<MetCatalogo> listaDpaCantonSelect;
    private List<MetCatalogo> listaDpaCiudadSelect;
    
    private MetCatalogo provinciaSelected;
    private MetCatalogo cantonSelected;
    private MetCatalogo parroquiaSelected;
      
    private boolean provinciaRequerido;
    private boolean cantonRequerido;
    
    private Date fechaInicio;
    private Date fechaFin;
    
    private List<CaptDetalleH> listarIncidencias;
  

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR ">
    /**
     * Creates a new instance of GeneracionFormulario
     */
    public IncidenciasBuscarControlador() {
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
           
            listaDpaProvinciaSelect = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_DPA_PROV");
            
            onChangeProvincia();
            limpiarDatos();
//            listarIncidencias();
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpiarDatos(){

        provinciaSelected = new MetCatalogo();
        cantonSelected = new MetCatalogo();
        parroquiaSelected = new MetCatalogo();

        fechaInicio = new Date();
        fechaFin = new Date();
        
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
     
    public void listarIcidenciasPorParametro(String formulario) throws Exception {
        cabeceraActual = bc.getCaptCabeceraServicioRemote().buscarFormCabPorClave(formulario);
        if(cabeceraActual != null){
            if(!uc.usuarioTieneRol("ROL_ENCUE")){
                generarReportexFormulario(formulario);
            }else{
                 if(provinciaSelected != null && provinciaSelected.getIdCatalogo() != null && 
                    cantonSelected != null && cantonSelected.getIdCatalogo() != null && 
                    parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null && fechaInicio != null && fechaFin != null ){
                     listarIncidencias =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("listarIncidenciasPorParametrosDeBusquedad", 
                         Arrays.asList(cabeceraActual.getIdCab(),
                                       uc.getUsuarioActual().getCodPersonal().getIdPersonal().toString(),
                                       (provinciaSelected != null && provinciaSelected.getIdCatalogo() != null ? provinciaSelected.getIdCatalogo().toString() : null),
                                       (cantonSelected != null && cantonSelected.getIdCatalogo() != null ? cantonSelected.getIdCatalogo().toString() : null),
                                       (parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null ? parroquiaSelected.getIdCatalogo().toString() : null),
                                       UtilsDate.DeDateAString(fechaInicio),
                                       UtilsDate.DeDateAString(fechaFin)));

                 }else if(fechaInicio != null && fechaFin != null){
                      listarIncidencias =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("listarIncidenciasPorFechaIniFin", 
                         Arrays.asList(cabeceraActual.getIdCab(),
                                       uc.getUsuarioActual().getCodPersonal().getIdPersonal().toString(),
                                       UtilsDate.DeDateAString(fechaInicio),
                                       UtilsDate.DeDateAString(fechaFin)));
                 }
            }
            
           
         }
    }
    
    public void generarReportexFormulario(String formulario) throws Exception {
        cabeceraActual = bc.getCaptCabeceraServicioRemote().buscarFormCabPorClave(formulario);
        if(cabeceraActual != null){

                if(provinciaSelected != null && provinciaSelected.getIdCatalogo() != null && 
                   cantonSelected != null && cantonSelected.getIdCatalogo() != null && 
                   parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null && fechaInicio != null && fechaFin != null ){
                   listarIncidencias =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("generarReportesxFormulario", 
                        Arrays.asList(cabeceraActual.getIdCab(),                                  
                                      (provinciaSelected != null && provinciaSelected.getIdCatalogo() != null ? provinciaSelected.getIdCatalogo().toString() : null),
                                      (cantonSelected != null && cantonSelected.getIdCatalogo() != null ? cantonSelected.getIdCatalogo().toString() : null),
                                      (parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null ? parroquiaSelected.getIdCatalogo().toString() : null),
                                      UtilsDate.DeDateAString(fechaInicio),
                                      UtilsDate.DeDateAString(fechaFin)));

                }else if(fechaInicio != null && fechaFin != null){
                     listarIncidencias =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("generarReportesxFecha", 
                        Arrays.asList(cabeceraActual.getIdCab(),                                  
                                      UtilsDate.DeDateAString(fechaInicio),
                                      UtilsDate.DeDateAString(fechaFin)));
                }
           }

    }
        
    public void generarReporteGeneral() throws Exception {        
       
        if(cabeceraActual != null){            
            if(provinciaSelected != null && provinciaSelected.getIdCatalogo() != null && 
               cantonSelected != null && cantonSelected.getIdCatalogo() != null && 
               parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null && fechaInicio != null && fechaFin != null ){
                listarIncidencias =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("generarReporteGeneral", 
                    Arrays.asList(                           
                                  (provinciaSelected != null && provinciaSelected.getIdCatalogo() != null ? provinciaSelected.getIdCatalogo().toString() : null),
                                  (cantonSelected != null && cantonSelected.getIdCatalogo() != null ? cantonSelected.getIdCatalogo().toString() : null),
                                  (parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null ? parroquiaSelected.getIdCatalogo().toString() : null),
                                  UtilsDate.DeDateAString(fechaInicio),
                                  UtilsDate.DeDateAString(fechaFin)));
                

            }else if(fechaInicio != null && fechaFin != null){
                 listarIncidencias =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("generarReporteGeneralxFecha", 
                    Arrays.asList(                            
                                  UtilsDate.DeDateAString(fechaInicio),
                                  UtilsDate.DeDateAString(fechaFin)));           
            }
//            bc.addErrorMessage("NO SE ENCONTRARON REGISTROS CON LOS PARÁMETROS BÚSQUEDA"); 
//             PrimeFaces.current().ajax().update("form:growl", "form:dt-general reportes-filtro");        
         }    
  
    }
    
     public void filtrarAsignacionesxParametros() throws Exception {        
 
         if (cabeceraActual != null) {
             if (provinciaSelected != null && provinciaSelected.getIdCatalogo() != null
                     && cantonSelected != null && cantonSelected.getIdCatalogo() != null
                     && parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null && fechaInicio != null && fechaFin != null) {
                 listarIncidencias = (List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("filtrarAsignacionesxParametro",
                         Arrays.asList(
                                 (provinciaSelected != null && provinciaSelected.getIdCatalogo() != null ? provinciaSelected.getIdCatalogo().toString() : null),
                                 (cantonSelected != null && cantonSelected.getIdCatalogo() != null ? cantonSelected.getIdCatalogo().toString() : null),
                                 (parroquiaSelected != null && parroquiaSelected.getIdCatalogo() != null ? parroquiaSelected.getIdCatalogo().toString() : null)
                         ));
             }else if(fechaInicio != null && fechaFin != null){
                 listarIncidencias =( List<CaptDetalleH>) bc.getCaptDetalleHServicioRemote().listarEjecutarConsulta("filtrarAsignacionesxFecha", 
                    Arrays.asList(                            
                                  UtilsDate.DeDateAString(fechaInicio),
                                  UtilsDate.DeDateAString(fechaFin)));           
            }

         }
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
    
    public void openNew() {
        limpiarDatos();
    }
    
    public String validaTickes(String fechaTicket, String estado){
        try {
            int dias = 0;
            if(fechaTicket!= null && !fechaTicket.isEmpty() && estado!= null && !estado.isEmpty()){
                
                if(estado.equals("CERRADO")){
                    return "cerrado";
                }else if(estado.equals("ABIERTO")){
                    dias = UtilsDate.diferenciaEntreFechas(UtilsDate.DeStringADate(fechaTicket), UtilsDate.DeStringADate(UtilsDate.fechaSistema()));
                    if(dias<5){
                        return "abierto";
                    }else{
                        return "vencidos";
                    }
                }
            }
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public CaptCabecera getCabeceraActual() {
        return cabeceraActual;
    }

    public void setCabeceraActual(CaptCabecera cabeceraActual) {
        this.cabeceraActual = cabeceraActual;
    }

    public List<CaptDetalleH> getListarIncidencias() {
        return listarIncidencias;
    }

    public void setListarIncidencias(List<CaptDetalleH> listarIncidencias) {
        this.listarIncidencias = listarIncidencias;
    }

 
    
   public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
    
    //</editor-fold>

 

    
    

   
}

