/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.captura.clases.CaptFilaH;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.faces.flow.SwitchCase;
import javax.faces.model.SelectItem;
//import org.primefaces.context.RequestContext;

/**
 *
 * @author dgarcia
 */
@ManagedBean
@ViewScoped
public class InicioCapturaSiac {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(InicioCapturaSiac.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{baseCapturaControlador}")
    private BaseCapturaControlador bcc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vc;

    private String tipoOpciones;
    private List<MetCatalogo> listaTipoOpciones;
  //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR ">
    /**
     * Creates a new instance of GeneracionFormulario
     */
    public InicioCapturaSiac() {
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" METODOS ">
    @PostConstruct
    public void inicializar() {
        try {
            listaTipoOpciones = new ArrayList<>();
            cargarListasIniciales();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
   
    public void inicializarParametrosBusqueda() {
        try {
            tipoOpciones = null;

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cargarListasIniciales() {
        try {
            listaTipoOpciones = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("CAT_TIPO_ACTIVIDAD");
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void irAFormulario() {
        System.out.println("dpa: "+tipoOpciones);
         System.out.println("dpa: "+this.getTipoOpciones());
        if(this.tipoOpciones!=null){
        switch (Integer.parseInt(tipoOpciones)) {
            case 01:
                try {

                    String url = "/sipe-captura-siac-war/paginas/captura/inquietudes.xhtml";
                    bc.redirect(url);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                break;
            case 02:
                try {
                    bc.addSuccessMessage("usted ingreso a Denuncias");
                    String url = "/sipe-captura-siac-war/paginas/captura/denuncias.xhtml";
                    bc.redirect(url);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                break;
            case 03:
                try {

                    String url = "/sipe-captura-siac-war/paginas/captura/registroNoCensados.xhtml";
                    bc.redirect(url);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                break;
            case 04:
                try {

                    String url = "/sipe-captura-siac-war/paginas/captura/ayudaRegistroAc.xhtml";
                    bc.redirect(url);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                break;
        }
        }

    }

//</editor-fold>
    
//    public void editarFormulario(CaptCabecera cab) {
//        try {
//            cabeceraActual = bc.getCaptCabeceraServicioRemote().buscarFormCabPorId(cab.getIdCab());
//            uc.getSession().setAttribute("capt_cabecera_actual", cabeceraActual);
//            irAFormulario(cabeceraActual);
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }
//  
//    public void inicializarAtributos() {
//        numElementos = null;
//    }
//
//    private int prepararParametrosBusquedaCargayCabeceras() {
//        Integer requeridos = 0;
//        try {
//            parametrosCargaPendiente.put("estadoFormulario", 1);
//            parametrosCargaPendiente.put("estadoLogico", true);
//            parametrosCabecerasCreadas.put("estadoLogico", true);
//            if (paramIdOpeEst != null && paramIdOpeEst > 0) {
//                parametrosCargaPendiente.put(BUSQ_OPEEST, paramIdOpeEst);
//                parametrosCabecerasCreadas.put("codCarCon." + BUSQ_OPEEST, paramIdOpeEst);
//            } else {
//                requeridos++;
//                bc.addErrorMessage("Seleccione la Operación Estadística!");
//            }
//            if (paramClave != null && paramClave.length() > 0) {
//                parametrosCargaPendiente.put(BUSQ_CLAVE, paramClave);
//                parametrosCabecerasCreadas.put("codCarCon." + BUSQ_CLAVE, paramClave);
//            } else {
//                requeridos++;
//                bc.addErrorMessage("Ingrese un Conglomerado!");
//            }
//            if (paramControl1 != null && paramControl1.length() > 0) {
//                parametrosCargaPendiente.put(BUSQ_CONTROL1, paramControl1);
//                parametrosCabecerasCreadas.put("codCarCon." + BUSQ_CONTROL1, paramControl1);
//            } else {
//                requeridos++;
//                bc.addErrorMessage("Ingrese la Vivienda!");
//            }
//            if (paramControl2 != null && paramControl2.length() > 0) {
//                parametrosCargaPendiente.put(BUSQ_CONTROL2, paramControl2);
//                parametrosCabecerasCreadas.put("codCarCon." + BUSQ_CONTROL2, paramControl2);
//            } else {
//                requeridos++;
//                bc.addErrorMessage("Ingrese el hogar!");
//            }
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//        return requeridos;
//    }
//
//
//    //Eliminación lógica de registros CaptCargaControl
//    public void eliminaCargaControl(CaptCargaControl cc) {
//        try {
//            cc.setCodAuditoriaCab(vc.getCodAuditoria("PAG_ELIM"));
//            bc.getCaptCargaControlServicioRemote().modificarEstadoLogicoCargaControl(cc, false);
//            bc.addSuccessMessage("El registro ha sido eliminado satisfactoriamente!");
//            buscarCargaPendiente();
//        } catch (Exception ex) {
//            Logger.getLogger(InicioCapturaSiac.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
////Eliminacion lógica de la Cabecera
//
//    public void eliminaCabecera(CaptCabecera c) {
//        try {
//            if (c != null) {
//                c.setCodAuditoriaCab(vc.getCodAuditoria("PAG_ELIM"));
//                if (c.getCodCarCon().getCodFormulario().getIdFormulario() == 1) {
//                    if (listaFormulariosCreados.size() > 1) {
//                        bc.addErrorMessage("No se puede eliminar el Frm1 ya que aún existen formularios creados.");
//                    } else {
//                        c.setEstadoLogico(false);
//                        bc.getCaptCabeceraServicioRemote().editarCaptCabecera(c);
//                        c.getCodCarCon().setEstadoFormulario(1);
//                        bc.getCaptCargaControlServicioRemote().editarCargaControl(c.getCodCarCon());
//                        bc.addSuccessMessage("El registro ha sido ELIMINADO satisfactoriamente!");
//                        buscarCabecerasCreadas();
//                        buscarCargaPendiente();
//                    }
//                } else {
//                    c.setEstadoLogico(false);
//                    bc.getCaptCabeceraServicioRemote().editarCaptCabecera(c);
//                    c.getCodCarCon().setEstadoFormulario(1);
//                    bc.getCaptCargaControlServicioRemote().editarCargaControl(c.getCodCarCon());
//                    bc.addSuccessMessage("El registro ha sido ELIMINADO satisfactoriamente!");
//                    buscarCabecerasCreadas();
//                    buscarCargaPendiente();
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(InicioCapturaSiac.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void guardarControlEquipo() {
//        try {
//            if (!listaCargaControlEquipo.isEmpty()) {
//                //se crearan 5 registros por defecto
//                for (int i = 0; i < listaCargaControlEquipo.size(); i++) {
//                    bc.getCaptCargaControlEquipoServicioRemote().editarCargaControlEquipo(listaCargaControlEquipo.get(i));
//                }
//            }
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void crearFormulario() {
//        try {
//
//            if (cabeceraActual.getEstado1() != null) {
//                if (Integer.valueOf(cabeceraActual.getEstado1()) >= 2) {
//                    crearFormularioNoEfectivo();
//                    RequestContext requestContext = RequestContext.getCurrentInstance();
//                    requestContext.execute("PF('dlgInicioCaptura').show();");
//                    buscarInformacion();
//                    requestContext.update(":frmIniCap:pnlCargaPendiente");
//                    requestContext.update(":frmIniCap:pnlCabeceras");
//                }
//            } else {
//                bc.addErrorMessage("Seleccione el resultado de la entrevista");
//            }
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void crearFormularioNoEfectivo() {
//        try {
//            if (cargaActual.getCodMueDet().getCodMuestra().getNombre().equals("MUESTRA_EPSPND")) {//codigoMulti
//                //guardarInfoCabMlt();
//            } else {
//                guardarControlEquipo();
//                //guardarInfoCabEnsanut();
//            }
//            cabeceraActual.setEstadoLogico(true);
////            cabeceraActual.setInfo1(info1);
////            cabeceraActual.setInfo2(info2);
////            cabeceraActual.setInfo3(info3);
//            cargaActual.setEstadoFormulario(2);
//            cabeceraActual.setClave(cargaActual.getClave());
//            cabeceraActual.setCodCarCon(cargaActual);
//            cabeceraActual.setCodMuestra(cargaActual.getCodMueDet());
//            cabeceraActual.setEstado("00");
//            cabeceraActual.setNumDet(0);
//            cabeceraActual.setFechaCreacion(new Date());
//            cabeceraActual.setFechahoraGuardado(new Date());
//            //EDICION DE CARGA CONTROL
//            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
//            bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);
//            //CREACION DE CABECERA
//            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
//            cabeceraActual = bc.getCaptCabeceraServicioRemote().crearCaptCabecera(cabeceraActual);
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void actualizarCabeceraEnsanut() {
//        try {
//            if (cabeceraActual.getEstado1() != null) {
//
//                if (cabeceraActual.getEstado1().equals("01")) {
//                    bc.addErrorMessage("Para cambiar el Resultado de la Entrevista a Completa(Efectiva), primero debe eliminar el registro creado!");
//                } else {
//                    //poner todo el código que estaba
//                    guardarControlEquipo();
////                        guardarInfoCabEnsanut();
////                        cabeceraActual.setInfo1(info1);
////                        cabeceraActual.setInfo2(info2);
////                        cabeceraActual.setInfo3(info3);
//                    cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
//                    cabeceraActual = bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
//                    RequestContext requestContext = RequestContext.getCurrentInstance();
//                    requestContext.execute("PF('dlgInicioCaptura').hide();");
//                    buscarInformacion();
//                    requestContext.update(":frmIniCap:pnlCargaPendiente");
//                    requestContext.update(":frmIniCap:pnlCabeceras");
//                    bc.addSuccessMessage("Datos Actualizados. Cierre la pantalla por favor y presione buscar");
//                }
//
//            } else {
//                bc.addErrorMessage("Seleccione el Resultado de la Entrevista!");
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(InicioCapturaSiac.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void actualizarCabecera() {
//        try {
//            //if (validaFecha() == 0) {
//            actualizarCabeceraEnsanut();
//
//        } catch (Exception e) {
//        }
//    }
//
//    public void crearFormularioEfectivo() {
//        try {
//
////            numElementos = obtenerNumeroElementos(conNumMiembrosHog, conNumNoMienbrosHog);
//            String val = validarCreacionEfectivo(cargaActual, numElementos);
//            if (val.equals("OK")) {
//                guardarControlEquipo();
////                guardarInfoCabEnsanut();
//                cabeceraActual.setEstadoLogico(true);
////                cabeceraActual.setInfo1(info1);
////                cabeceraActual.setInfo2(info2);
////                cabeceraActual.setInfo3(info3);
//                cargaActual.setEstadoFormulario(2);
//                cabeceraActual.setClave(cargaActual.getClave());
//                cabeceraActual.setCodCarCon(cargaActual);
//                cabeceraActual.setCodMuestra(cargaActual.getCodMueDet());
//                cabeceraActual.setEstado("00");
//                cabeceraActual.setNumDet(numElementos);
//                cabeceraActual.setFechaCreacion(new Date());
//                cabeceraActual.setFechahoraGuardado(new Date());
//                //EDICION DE CARGA CONTROL
//                cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
//                bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);
//                //CREACION DE CABECERA
//                cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
//                cabeceraActual = bc.getCaptCabeceraServicioRemote().crearCaptCabecera(cabeceraActual);
//                //CREACION DE ESTADOS
//                bc.getCaptEstadoServicioRemote().crearEstadosInicialesPorCabeceraYElementos(cabeceraActual.getIdCab(), cargaActual.getClave(), numElementos);
//
//                //CREACION DE DETALLES V Y H
//                //las 2 listas parametros listaVarsV,listaFilasH deben venir de cache, por el momento se realiza la consulta en cada creacion
//                List<CaptVarV> listaVarsV = this.listarVariablesVPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
//                bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
//                        listaVarsV, numElementos);
//
//                List<CaptFilaH> listaFilasH = this.listarFilasHPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
//                if (!listaFilasH.isEmpty()) {
//                    bc.getCaptDetalleHServicioRemote().crearDetallesHDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
//                            listaFilasH, numElementos);
//                }
//
//            } else {
//                bc.addErrorMessage(val);
//            }
//
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private int obtenerNumeroElementos(String nmh, String nnmh) {
//        int r = 0;
//        try {
//            r = Integer.valueOf(nmh) + Integer.valueOf(nnmh);
//        } catch (Exception ex) {
//            r = 0;
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//        return r;
//    }
//
//    private String validarCreacionEfectivo(CaptCargaControl cc, Integer numEle) {
//        String val = "OK";
//        try {
//            if (cc.getIdCarCon() == null) {
//                val = val + " NO SE HA SELECCIONADO UNA CARGA.";
//            }
//
//            if (cc.getCodFormulario().getTipo().equals("1E")) {
//                numElementos = 1;
//            } else if (cc.getCodFormulario().getTipo().equals("ME")) {
//                if (numEle == null || numEle == 0) {
//                    val = val + " INGRESE NUMERO DE MIEMBROS.";
//                }
//            }
//            if (numEle > 20) {
//
//                val = val + " DEMASIADOS MIEMBROS DE HOGAR:" + numEle + ".";
//
//            }
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//        return val;
//    }
//
    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                //if (tercerDigito < 6) {
                int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                int verificador = Integer.parseInt(cedula.substring(9, 10));
                int suma = 0;
                int digito = 0;
                for (int i = 0; i < (cedula.length() - 1); i++) {
                    digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                    suma += ((digito % 10) + (digito / 10));
                }
                if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                    cedulaCorrecta = true;
                } else if ((10 - (suma % 10)) == verificador) {
                    cedulaCorrecta = true;
                } else {
                    cedulaCorrecta = false;
                }
                //} else {
                //cedulaCorrecta = false;
                //}
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
        }
        if (!cedulaCorrecta) {
        }
        return cedulaCorrecta;
    }
//
//    public void actualizaCabecaeraMulti() {//codigoMulti
//        try {
////            guardarInfoCabMlt();
////            cabeceraActual.setInfo1(info1);
////            cabeceraActual.setInfo2(info2);
////            cabeceraActual.setInfo3(info3);
//            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
//            cabeceraActual = bc.getCaptCabeceraServicioRemote().editarCaptCabecera(cabeceraActual);
//            RequestContext requestContext = RequestContext.getCurrentInstance();
//            requestContext.execute("PF('dlgInicioCapturaMlt').hide();");
//            buscarInformacion();
//            requestContext.update(":frmIniCap:pnlCargaPendiente");
//            requestContext.update(":frmIniCap:pnlCabeceras");
//            bc.addSuccessMessage("Datos Actualizados");
//
//        } catch (Exception ex) {
//            Logger.getLogger(InicioCapturaSiac.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void crearFormularioEfectivoMlt() {//codigoMulti
//        try {
////            guardarInfoCabMlt();
////            cabeceraActual.setInfo1(info1);
////            cabeceraActual.setInfo2(info2);
////            cabeceraActual.setInfo3(info3);
//            cargaActual.setEstadoFormulario(2);
//            cabeceraActual.setClave(cargaActual.getClave());
//            cabeceraActual.setCodCarCon(cargaActual);
//            cabeceraActual.setCodMuestra(cargaActual.getCodMueDet());
//            cabeceraActual.setEstado("00");
////            numElementos = Integer.parseInt(conNumMiembrosHog);
//            cabeceraActual.setNumDet(numElementos);
//            cabeceraActual.setFechaCreacion(new Date());
//            cabeceraActual.setFechahoraGuardado(new Date());
//            cabeceraActual.setEstadoLogico(true);
//            //EDICION DE CARGA CONTROL
//            cargaActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
//            bc.getCaptCargaControlServicioRemote().editarCargaControl(cargaActual);
//            //CREACION DE CABECERA
//            cabeceraActual.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
//            cabeceraActual = bc.getCaptCabeceraServicioRemote().crearCaptCabecera(cabeceraActual);
//            //CREACION DE ESTADOS
//            bc.getCaptEstadoServicioRemote().crearEstadosInicialesPorCabeceraYElementos(cabeceraActual.getIdCab(), cargaActual.getClave(), numElementos);
//            //CREACION DE DETALLES V Y H
//            //las 2 listas parametros listaVarsV,listaFilasH deben venir de cache, por el momento se realiza la consulta en cada creacion
//            List<CaptVarV> listaVarsV = this.listarVariablesVPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
//            bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
//                    listaVarsV, numElementos);
//
//            List<CaptFilaH> listaFilasH = this.listarFilasHPorFormulario(cargaActual.getCodFormulario().getIdFormulario());
//            if (!listaFilasH.isEmpty()) {
//                bc.getCaptDetalleHServicioRemote().crearDetallesHDeFormulario(cargaActual.getCodFormulario().getTipo(), cabeceraActual.getIdCab(),
//                        listaFilasH, numElementos);
//            }
//            /* } else {
//                bc.addErrorMessage(val);
//            }*/
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void redireccionaOpEnemdu() {
//        try {
//            definirOperacionEstadisticaEnSesion();
//            String nomPagina = "";
//            MetOperativo objOperativo = new MetOperativo();
//            objOperativo = bc.getMetOperativoServicioRemote().buscarXCodigoActivo(paramIdOperativo);
//            nomPagina = objOperativo.getTipoDato();
//            String url = "/sipe-captura-war/paginas/captura/" + nomPagina;
//            System.out.println("Url Pagina:" + url);
//            bc.redirect(url);
//            objOperativo = null;
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc=" GETTERSYSETTERS ">
//    public BaseControlador getBc() {
//        return bc;
//    }
//
//    public void setBc(BaseControlador bc) {
//        this.bc = bc;
//    }
//
//    public UsuarioControlador getUc() {
//        return uc;
//    }
//
//    public void setUc(UsuarioControlador uc) {
//        this.uc = uc;
//    }
//
//    public VistaControlador getVc() {
//        return vc;
//    }
//
//    public void setVc(VistaControlador vc) {
//        this.vc = vc;
//    }
//
//    public List<CaptCargaControl> getListaCargaPendiente() {
//        return listaCargaPendiente;
//    }
//
//    public void setListaCargaPendiente(List<CaptCargaControl> listaCargaPendiente) {
//        this.listaCargaPendiente = listaCargaPendiente;
//    }
//
//    public List<CaptCabecera> getListaFormulariosCreados() {
//        return listaFormulariosCreados;
//    }
//
//    public void setListaFormulariosCreados(List<CaptCabecera> listaFormulariosCreados) {
//        this.listaFormulariosCreados = listaFormulariosCreados;
//    }
//
//    public Integer getParamIdOpeEst() {
//        Integer idOe = (Integer) uc.getSession().getAttribute("id_ope_est");
//        if (idOe != null) {
//            paramIdOpeEst = idOe;
//        }
//        return paramIdOpeEst;
//    }
//
//    public void setParamIdOpeEst(Integer paramIdOpeEst) {
//        this.paramIdOpeEst = paramIdOpeEst;
//    }
//
//    public List<AdmOperacionEstadistica> getListaOpeEst() {
//        return listaOpeEst;
//    }
//
//    public void setListaOpeEst(List<AdmOperacionEstadistica> listaOpeEst) {
//        this.listaOpeEst = listaOpeEst;
//    }
//
//    public Integer getParamIdPeriodo() {
//        return paramIdPeriodo;
//    }
//
//    public void setParamIdPeriodo(Integer paramIdPeriodo) {
//        this.paramIdPeriodo = paramIdPeriodo;
//    }
//
//    public List<AdmPeriodo> getListaPeriodo() {
//        return listaPeriodo;
//    }
//
//    public void setListaPeriodo(List<AdmPeriodo> listaPeriodo) {
//        this.listaPeriodo = listaPeriodo;
//    }
//
//    public Integer getParamIdOperativo() {
//        return paramIdOperativo;
//    }
//
//    public void setParamIdOperativo(Integer paramIdOperativo) {
//        this.paramIdOperativo = paramIdOperativo;
//    }
//
//    public List<MetOperativo> getListaOperativoPorOpeEstyPeriodo() {
//        return listaOperativoPorOpeEstyPeriodo;
//    }
//
//    public void setListaOperativoPorOpeEstyPeriodo(List<MetOperativo> listaOperativoPorOpeEstyPeriodo) {
//        this.listaOperativoPorOpeEstyPeriodo = listaOperativoPorOpeEstyPeriodo;
//    }
//
//    public Integer getParamIdFormulario() {
//        return paramIdFormulario;
//    }
//
//    public void setParamIdFormulario(Integer paramIdFormulario) {
//        this.paramIdFormulario = paramIdFormulario;
//    }
//
//    public List<MetFormulario> getListaFormulariosPorOperativo() {
//        return listaFormulariosPorOperativo;
//    }
//
//    public void setListaFormulariosPorOperativo(List<MetFormulario> listaFormulariosPorOperativo) {
//        this.listaFormulariosPorOperativo = listaFormulariosPorOperativo;
//    }
//
//    public String getParamTipo() {
//        return paramTipo;
//    }
//
//    public void setParamTipo(String paramTipo) {
//        this.paramTipo = paramTipo;
//    }
//
//    public List<SelectItem> getListaTipos() {
//        return listaTipos;
//    }
//
//    public void setListaTipos(List<SelectItem> listaTipos) {
//        this.listaTipos = listaTipos;
//    }
//
//    public String getParamZonal() {
//        return paramZonal;
//    }
//
//    public void setParamZonal(String paramZonal) {
//        this.paramZonal = paramZonal;
//    }
//
//    public List<SelectItem> getListaZonal() {
//        return listaZonal;
//    }
//
//    public void setListaZonal(List<SelectItem> listaZonal) {
//        this.listaZonal = listaZonal;
//    }
//
//    public String getParamClave() {
//        return paramClave;
//    }
//
//    public void setParamClave(String paramClave) {
//        this.paramClave = paramClave;
//    }
//
//    public String getParamControl1() {
//        return paramControl1;
//    }
//
//    public void setParamControl1(String paramControl1) {
//        this.paramControl1 = paramControl1;
//    }
//
//    public Integer getNumElementos() {
//        return numElementos;
//    }
//
//    public void setNumElementos(Integer numElementos) {
//        this.numElementos = numElementos;
//    }
//
//    public CaptCabecera getCabeceraActual() {
//        return cabeceraActual;
//    }
//
//    public void setCabeceraActual(CaptCabecera cabeceraActual) {
//        this.cabeceraActual = cabeceraActual;
//    }
//
//    public CaptCargaControl getCargaActual() {
//        return cargaActual;
//    }
//
//    public void setCargaActual(CaptCargaControl cargaActual) {
//        this.cargaActual = cargaActual;
//    }
//
//    public List<SelectItem> getListaResultadoEntrevista() {
//        return listaResultadoEntrevista;
//    }
//
//    public void setListaResultadoEntrevista(List<SelectItem> listaResultadoEntrevista) {
//        this.listaResultadoEntrevista = listaResultadoEntrevista;
//    }
//
//    public List<CaptCargaControlEquipo> getListaCargaControlEquipo() {
//        return listaCargaControlEquipo;
//    }
//
//    public void setListaCargaControlEquipo(List<CaptCargaControlEquipo> listaCargaControlEquipo) {
//        this.listaCargaControlEquipo = listaCargaControlEquipo;
//    }
//
//    public String getParamControl2() {
//        return paramControl2;
//    }
//
//    public void setParamControl2(String paramControl2) {
//        this.paramControl2 = paramControl2;
//    }
//
//    public CaptCargaControlEquipo getCargaCarControlEquipo() {
//        return cargaCarControlEquipo;
//    }
//
//    public void setCargaCarControlEquipo(CaptCargaControlEquipo cargaCarControlEquipo) {
//        this.cargaCarControlEquipo = cargaCarControlEquipo;
//    }
//
//    public BaseCapturaControlador getBcc() {
//        return bcc;
//    }
//
//    public void setBcc(BaseCapturaControlador bcc) {
//        this.bcc = bcc;
//    }
//
//    public CaptCargaControl getCargaCarControlHog() {
//        return cargaCarControlHog;
//    }
//
//    public void setCargaCarControlHog(CaptCargaControl cargaCarControlHog) {
//        this.cargaCarControlHog = cargaCarControlHog;
//    }
//
//    public String getAliasOpeEstActual() {
//        return aliasOpeEstActual;
//    }
//
//    public void setAliasOpeEstActual(String aliasOpeEstActual) {
//        this.aliasOpeEstActual = aliasOpeEstActual;
//    }
//
//    public List<MetOperativo> getListaOperativoPorOpeEst() {
//        return listaOperativoPorOpeEst;
//    }
//
//    public void setListaOperativoPorOpeEst(List<MetOperativo> listaOperativoPorOpeEst) {
//        this.listaOperativoPorOpeEst = listaOperativoPorOpeEst;
//    }
//
//// </editor-fold>
//    //<editor-fold defaultstate="collapsed" desc=" METODOS TEMPORALES">
//    /**
//     * Este método debe ser programado y llamado en cache por cada formulario
//     *
//     * @return
//     */
//    private List<CaptVarV> listarVariablesVPorFormulario(Integer idFormulario) {
//        List<CaptVarV> vars = new ArrayList<>();
//        try {
//            vars = bc.getCaptDetalleVServicioRemote().listarVariablesVPorFormulario(idFormulario);
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//        return vars;
//    }
//
//    /**
//     * Este método debe ser programado y llamado en cache por cada formulario
//     *
//     * @return
//     */
//    private List<CaptFilaH> listarFilasHPorFormulario(Integer idFormulario) {
//        List<CaptFilaH> filas = new ArrayList<>();
//        try {
//            filas = bc.getCaptDetalleHServicioRemote().listarFilasHPorFormulario(idFormulario);
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        }
//        return filas;
//    }
////</editor-fold>
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
    

    public String getTipoOpciones() {
        return tipoOpciones;
    }

    public void setTipoOpciones(String tipoOpciones) {
        this.tipoOpciones = tipoOpciones;
    }

    public List<MetCatalogo> getListaTipoOpciones() {
        return listaTipoOpciones;
    }

    public void setListaTipoOpciones(List<MetCatalogo> listaTipoOpciones) {
        this.listaTipoOpciones = listaTipoOpciones;
    }
    

//</editor-fold>
}

