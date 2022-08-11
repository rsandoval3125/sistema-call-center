/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.metadato.clases.Catalogo;
import ec.gob.inec.metadato.clases.ItemCatalogo;
import ec.gob.inec.metadato.clases.JSFFilaSeccion;
import ec.gob.inec.metadato.clases.JSFFormulario;
import ec.gob.inec.metadato.clases.JSFSeccion;
import ec.gob.inec.metadato.clases.JSFVariable;
import ec.gob.inec.metadato.clases.SPSSVariable;
import ec.gob.inec.metadato.clases.ValorVistaPrevia;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dguano
 */
@ManagedBean
@ViewScoped
public class GeneracionFormularioControlador {

    // <editor-fold defaultstate="collapsed" desc=" ATRIBUTOS-PROPIEDADES ">
    private static final Logger LOGGER = Logger.getLogger(GeneracionFormularioControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;

    private boolean soloCodigo;

    private String iniPagina;
    private String codigoJSFGenerado;
    private String finPagina;

    private MetOperativo operativoActual;
    private List<MetOperativo> listaOperativo;

    private MetFormulario formularioActual;
    private List<MetFormulario> listaFormularios;

    private String nombreControlador;
    private String nombreObjetoValoresVertical;
    private String nombreObjetosListasValoresHorizontal;
    private int contadorVariables;
    private List<ValorVistaPrevia> valoresVertical;
    private List<List<ValorVistaPrevia>> listasValoresHorizontal;
    private List<MetSeccion> listaSeccionesH;

    private String nombreArchivoDescarga;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
    private String nl = "\n";
    private List<String> listaErrores;

    private String sintaxisSPSS;
    private String sintaxisInicialesGenerales;
    
    private int codFormOmisiones;
    private String codSeccionesOmisiones;
    private String tablaOmisiones;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR ">
    /**
     * Creates a new instance of GeneracionFormulario
     */
    public GeneracionFormularioControlador() {
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" METODOS ">
    @PostConstruct
    public void inicializar() {
        try {
            nombreControlador = "generacionFormulario";
            listaOperativo = new ArrayList<>();
            listaOperativo = bc.getMetOperativoServicioRemote().listarTodo();

            operativoActual = new MetOperativo();
            formularioActual = new MetFormulario();
            definirIniyFinPagina();
            listaErrores = new ArrayList<>();
            definirSintaxisInicialesGenerales();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarNombreArchivo() {
        nombreArchivoDescarga = "";
    }

    public void definirNombresVistaPrevia() {
        nombreControlador = "generacionFormulario";
        nombreObjetoValoresVertical = "valoresVertical";
    }

    public void definirNombresListasHorizontal(List<MetSeccion> seccionesH) {
        try {
            nombreObjetosListasValoresHorizontal = "";
            String htmlBR = "";
            if (!seccionesH.isEmpty()) {
                for (MetSeccion s : seccionesH) {
                    nombreObjetosListasValoresHorizontal = nombreObjetosListasValoresHorizontal + "private CaptSeccionH " + "lista" + s.getAlias().replace(".", "") + ";<br/>";
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarFormulariosPorOperativo() {
        try {

            listaFormularios = new ArrayList<>();
            if (operativoActual.getIdOperativo() != null) {
                if (bc.getMetFormularioServicioRemote() != null) {
                    listaFormularios = bc.getMetFormularioServicioRemote().listarFormulariosPorOperativo(operativoActual.getIdOperativo());

                } else {
                    System.out.println("servicio nulo");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void generarFormulario() {
        try {
            listaErrores = new ArrayList<>();
            nombreArchivoDescarga = "";
            if (formularioActual.getIdFormulario() != null) {

                if (soloCodigo) {
                    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    construirNombreArchivo();
                    File archivoDatos = new File(request.getSession().getServletContext().getRealPath("/archivos/" + nombreArchivoDescarga + ".txt"));
                    Writer ftxt = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(archivoDatos), "UTF-8"));
                    System.out.println(nombreArchivoDescarga);
                    generarListaDeSeccionesH();
                    generarCodigoJSFFormulario();

                    if (listaErrores.isEmpty()) {
                        definirNombresListasHorizontal(listaSeccionesH);
                        System.out.println(codigoJSFGenerado.length());
                        ftxt.write("<!-- generado por:" + uc.getUsuario() + " " + nombreArchivoDescarga + "-->" + nl);
                        ftxt.write(codigoJSFGenerado);
                        System.out.println("termino de escribir");
                        ftxt.close();
                    } else {

                        bc.addErrorMessage("Verifique las variables");

                    }
                } else {
                    //vistaPrevia
                    definirNombresVistaPrevia();

                    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    construirNombreArchivo();
                    File archivoDatos = new File(request.getSession().getServletContext().getRealPath("/archivos/" + nombreArchivoDescarga + ".xhtml"));
                    Writer ftxt = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(archivoDatos), "UTF-8"));
                    System.out.println(nombreArchivoDescarga);
                    generarListaDeSeccionesH();
                    generarCodigoJSFFormulario();

                    generarObjetoListaValoresPruebaHorizontal();
                    generarObjetoListaValoresPruebaVertical();

                    if (listaErrores.isEmpty()) {
                        System.out.println(codigoJSFGenerado.length());
                        ftxt.write(iniPagina + "\n");
                        ftxt.write(idFormularioOcultoParaCargarSaltos(formularioActual.getIdFormulario()) + "\n");
                        ftxt.write(cargarSaltos(formularioActual.getIdFormulario()) + "\n");
                        ftxt.write(cargarValidaciones(formularioActual.getIdFormulario()) + "\n");                                                 
                        ftxt.write(codigoJSFGenerado);
                        ftxt.write(finPagina);
                        System.out.println("termino de escribir");
                        ftxt.close();
                    } else {

                        bc.addErrorMessage("Verifique las variables");

                    }
                }

            } else {

                bc.addErrorMessage("Seleccione un formulario");

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void generarSPSSFormulario() {
        try {
            listaErrores = new ArrayList<>();
            nombreArchivoDescarga = "";
            if (formularioActual.getIdFormulario() != null) {

                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                construirNombreArchivo();
                nombreArchivoDescarga = "sintaxis_spss_" + nombreArchivoDescarga;
                File archivoDatos = new File(request.getSession().getServletContext().getRealPath("/archivos/" + nombreArchivoDescarga + ".txt"));
                Writer ftxt = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(archivoDatos), "UTF-8"));
                System.out.println(nombreArchivoDescarga);
                generarSintaxisDeFormulario();
                if (listaErrores.isEmpty()) {
                    //ftxt.write("<!-- generado por:" + uc.getUsuario() + " " + nombreArchivoDescarga + "-->" + nl);
                    ftxt.write("* Encoding: UTF-8." + nl);
                    ftxt.write(sintaxisInicialesGenerales + nl);
                    ftxt.write(sintaxisSPSS);
                    System.out.println("termino de escribir");
                    ftxt.close();
                } else {
                    bc.addErrorMessage("Verifique las variables");
                }
            } else {
                bc.addErrorMessage("Seleccione un formulario");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void generarCodigoJSFFormulario() {
        contadorVariables = 0;
        try {
            if (formularioActual.getIdFormulario() != null) {
                List<MetSeccion> listaSecciones1 = bc.getMetSeccionServicioRemote().listarSeccionesNivel1PorFormulario(formularioActual.getIdFormulario());

                if (!listaSecciones1.isEmpty()) {
                    List<JSFSeccion> lss1 = new ArrayList<>();
                    for (MetSeccion sec1 : listaSecciones1) {
                        String varDup1 = bc.getMetVariableServicioRemote().existeDuplicidadDeIdentificadorDeVariablesEnSeccion(sec1.getIdSeccion());
                        if (!varDup1.equals("")) {
                            listaErrores.add("Variable Duplicada en Sección:" + sec1.getNombre() + "(" + varDup1 + ")");
                        }

                        List<JSFFilaSeccion> lfs1 = new ArrayList<>();
                        List<MetVariable> listaVariablesSec1 = bc.getMetVariableServicioRemote().listarVariablesPorSeccion(sec1.getIdSeccion());
                        if (!listaVariablesSec1.isEmpty()) {

                            List<JSFVariable> listaJSFVariablesPorFila = new ArrayList<>();
                            int fila = 0;
                            for (MetVariable v1 : listaVariablesSec1) {
                                if (fila != v1.getFila()) {
                                    if (!listaJSFVariablesPorFila.isEmpty()) {
                                        JSFFilaSeccion fs = new JSFFilaSeccion(listaJSFVariablesPorFila);
                                        lfs1.add(fs);
                                    }
                                    listaJSFVariablesPorFila = new ArrayList<>();
                                    fila = v1.getFila();
                                }
                                JSFVariable jsfVar = new JSFVariable(v1, obtenerCatalogoDeVariable(v1), sec1.getTipo(), nombreControlador, obtenerNombreObjetoValores(sec1.getTipo(), sec1.getAlias(), soloCodigo), obtenerIdxVerticalDeVariable(sec1.getTipo(), contadorVariables, v1.getFila()), v1.getColumna());
                                listaJSFVariablesPorFila.add(jsfVar);
                                if (sec1.getTipo().contains("V") && !v1.getTipoComp().equals("LBL")) {
                                    contadorVariables++;
                                }
                            }
                            if (!listaJSFVariablesPorFila.isEmpty()) {
                                JSFFilaSeccion fs = new JSFFilaSeccion(listaJSFVariablesPorFila);
                                lfs1.add(fs);
                            }

                        }
                        List<JSFSeccion> lss2 = new ArrayList<>();
                        if (bc.getMetSeccionServicioRemote().seccionTieneHijos(sec1.getIdSeccion())) {
                            List<MetSeccion> listaSecciones2 = bc.getMetSeccionServicioRemote().listarSeccionesHijasPorNivel(sec1.getIdSeccion(), 2);

                            if (!listaSecciones2.isEmpty()) {

                                for (MetSeccion sec2 : listaSecciones2) {
                                    String varDup2 = bc.getMetVariableServicioRemote().existeDuplicidadDeIdentificadorDeVariablesEnSeccion(sec2.getIdSeccion());
                                    if (!varDup2.equals("")) {
                                        listaErrores.add("Variable Duplicada en Sección:" + sec2.getNombre() + "(" + varDup2 + ")");
                                    }
                                    List<JSFFilaSeccion> lfs2 = new ArrayList<>();
                                    List<MetVariable> listaVariablesSec2 = bc.getMetVariableServicioRemote().listarVariablesPorSeccion(sec2.getIdSeccion());
                                    if (!listaVariablesSec2.isEmpty()) {

                                        List<JSFVariable> listaJSFVariablesPorFila = new ArrayList<>();
                                        int fila = 0;
                                        for (MetVariable v2 : listaVariablesSec2) {
                                            if (fila != v2.getFila()) {
                                                if (!listaJSFVariablesPorFila.isEmpty()) {
                                                    JSFFilaSeccion fs = new JSFFilaSeccion(listaJSFVariablesPorFila);
                                                    lfs2.add(fs);
                                                }
                                                listaJSFVariablesPorFila = new ArrayList<>();
                                                fila = v2.getFila();
                                            }
                                            JSFVariable jsfVar = new JSFVariable(v2, obtenerCatalogoDeVariable(v2), sec2.getTipo(), nombreControlador, obtenerNombreObjetoValores(sec2.getTipo(), sec2.getAlias(), soloCodigo), obtenerIdxVerticalDeVariable(sec2.getTipo(), contadorVariables, v2.getFila()), v2.getColumna());
                                            listaJSFVariablesPorFila.add(jsfVar);

                                            if (sec2.getTipo().contains("V") && !v2.getTipoComp().equals("LBL")) {
                                                contadorVariables++;
                                            }
                                        }
                                        if (!listaJSFVariablesPorFila.isEmpty()) {
                                            JSFFilaSeccion fs = new JSFFilaSeccion(listaJSFVariablesPorFila);
                                            lfs2.add(fs);
                                        }

                                    }

                                    List<JSFSeccion> lss3 = new ArrayList<>();
                                    if (bc.getMetSeccionServicioRemote().seccionTieneHijos(sec2.getIdSeccion())) {
                                        List<MetSeccion> listaSecciones3 = bc.getMetSeccionServicioRemote().listarSeccionesHijasPorNivel(sec2.getIdSeccion(), 3);
                                        if (!listaSecciones3.isEmpty()) {

                                            for (MetSeccion sec3 : listaSecciones3) {
                                                String varDup3 = bc.getMetVariableServicioRemote().existeDuplicidadDeIdentificadorDeVariablesEnSeccion(sec3.getIdSeccion());
                                                if (!varDup3.equals("")) {
                                                    listaErrores.add("Variable Duplicada en Sección:" + sec3.getNombre() + "(" + varDup3 + ")");
                                                }
                                                List<JSFFilaSeccion> lfs3 = new ArrayList<>();
                                                List<MetVariable> listaVariablesSec3 = bc.getMetVariableServicioRemote().listarVariablesPorSeccion(sec3.getIdSeccion());
                                                if (!listaVariablesSec3.isEmpty()) {

                                                    List<JSFVariable> listaJSFVariablesPorFila = new ArrayList<>();
                                                    int fila = 0;
                                                    for (MetVariable v3 : listaVariablesSec3) {
                                                        if (fila != v3.getFila()) {
                                                            if (!listaJSFVariablesPorFila.isEmpty()) {
                                                                JSFFilaSeccion fs = new JSFFilaSeccion(listaJSFVariablesPorFila);
                                                                lfs1.add(fs);
                                                            }
                                                            listaJSFVariablesPorFila = new ArrayList<>();
                                                            fila = v3.getFila();
                                                        }
                                                        JSFVariable jsfVar = new JSFVariable(v3, obtenerCatalogoDeVariable(v3), sec3.getTipo(), nombreControlador, obtenerNombreObjetoValores(sec3.getTipo(), sec3.getAlias(), soloCodigo), obtenerIdxVerticalDeVariable(sec3.getTipo(), contadorVariables, v3.getFila()), v3.getColumna());
                                                        listaJSFVariablesPorFila.add(jsfVar);

                                                        if (sec3.getTipo().contains("V") && !v3.getTipoComp().equals("LBL")) {
                                                            contadorVariables++;
                                                        }
                                                    }
                                                    if (!listaJSFVariablesPorFila.isEmpty()) {
                                                        JSFFilaSeccion fs = new JSFFilaSeccion(listaJSFVariablesPorFila);
                                                        lfs3.add(fs);
                                                    }                                                    //debe generarse una lista de filas seccion

                                                    JSFSeccion s3 = new JSFSeccion(sec3, lfs3, null);

                                                    lss3.add(s3);

                                                }

                                            }
                                        }
                                    }

                                    JSFSeccion s2 = new JSFSeccion(sec2, lfs2, lss3);

                                    lss2.add(s2);
                                }
                            }

                        }
                        JSFSeccion s1 = new JSFSeccion(sec1, lfs1, lss2);

                        lss1.add(s1);

                    }

                    JSFFormulario form = new JSFFormulario(lss1, formularioActual.getIdFormulario());

                    codigoJSFGenerado = form.obtenerFormulario();

                }

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void generarSintaxisDeFormulario() {
        sintaxisSPSS = "";
        try {
            if (formularioActual.getIdFormulario() != null) {
                List<MetSeccion> listaSecciones1 = bc.getMetSeccionServicioRemote().listarSeccionesNivel1PorFormulario(formularioActual.getIdFormulario());

                if (!listaSecciones1.isEmpty()) {
                    for (MetSeccion sec1 : listaSecciones1) {
                        String varDup1 = bc.getMetVariableServicioRemote().existeDuplicidadDeIdentificadorDeVariablesEnSeccion(sec1.getIdSeccion());
                        if (!varDup1.equals("")) {
                            listaErrores.add("Variable Duplicada en Sección:" + sec1.getNombre() + "(" + varDup1 + ")");
                        }
                        List<MetVariable> listaVariablesSec1 = bc.getMetVariableServicioRemote().listarVariablesPorSeccion(sec1.getIdSeccion());
                        if (!listaVariablesSec1.isEmpty()) {
                            for (MetVariable v1 : listaVariablesSec1) {
                                if (!v1.getTipoComp().equals("LBL")) {

                                    SPSSVariable spssv = new SPSSVariable(v1, obtenerCatalogoDeVariable(v1));
                                    sintaxisSPSS = sintaxisSPSS + spssv.obtenerSintaxisVariableSPSS();
                                }
                            }
                        }
                        if (bc.getMetSeccionServicioRemote().seccionTieneHijos(sec1.getIdSeccion())) {
                            List<MetSeccion> listaSecciones2 = bc.getMetSeccionServicioRemote().listarSeccionesHijasPorNivel(sec1.getIdSeccion(), 2);

                            if (!listaSecciones2.isEmpty()) {
                                for (MetSeccion sec2 : listaSecciones2) {
                                    String varDup2 = bc.getMetVariableServicioRemote().existeDuplicidadDeIdentificadorDeVariablesEnSeccion(sec2.getIdSeccion());
                                    if (!varDup2.equals("")) {
                                        listaErrores.add("Variable Duplicada en Sección:" + sec2.getNombre() + "(" + varDup2 + ")");
                                    }
                                    List<MetVariable> listaVariablesSec2 = bc.getMetVariableServicioRemote().listarVariablesPorSeccion(sec2.getIdSeccion());
                                    if (!listaVariablesSec2.isEmpty()) {
                                        for (MetVariable v2 : listaVariablesSec2) {
                                            if (!v2.getTipoComp().equals("LBL")) {
                                                SPSSVariable spssv = new SPSSVariable(v2, obtenerCatalogoDeVariable(v2));
                                                sintaxisSPSS = sintaxisSPSS + spssv.obtenerSintaxisVariableSPSS();
                                            }
                                        }
                                    }
                                    if (bc.getMetSeccionServicioRemote().seccionTieneHijos(sec2.getIdSeccion())) {
                                        List<MetSeccion> listaSecciones3 = bc.getMetSeccionServicioRemote().listarSeccionesHijasPorNivel(sec2.getIdSeccion(), 3);
                                        if (!listaSecciones3.isEmpty()) {
                                            for (MetSeccion sec3 : listaSecciones3) {
                                                String varDup3 = bc.getMetVariableServicioRemote().existeDuplicidadDeIdentificadorDeVariablesEnSeccion(sec3.getIdSeccion());
                                                if (!varDup3.equals("")) {
                                                    listaErrores.add("Variable Duplicada en Sección:" + sec3.getNombre() + "(" + varDup3 + ")");
                                                }
                                                List<MetVariable> listaVariablesSec3 = bc.getMetVariableServicioRemote().listarVariablesPorSeccion(sec3.getIdSeccion());
                                                if (!listaVariablesSec3.isEmpty()) {
                                                    for (MetVariable v3 : listaVariablesSec3) {
                                                        if (!v3.getTipoComp().equals("LBL")) {
                                                            SPSSVariable spssv = new SPSSVariable(v3, obtenerCatalogoDeVariable(v3));
                                                            sintaxisSPSS = sintaxisSPSS + spssv.obtenerSintaxisVariableSPSS();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private String obtenerNombreObjetoValores(String tipoSeccion, String aliasSeccion, boolean soloCodigo) {
        String n = "";
        if (tipoSeccion.contains("V")) {
            n = nombreObjetoValoresVertical;
        } else {
            n = obtenerNombreDeListaValoresH(aliasSeccion, soloCodigo);
        }
        return n;
    }

    private String obtenerNombreDeListaValoresH(String aliasSeccion, boolean soloCodigo) {
        String n = "";
        if (soloCodigo) {
            for (MetSeccion sec : listaSeccionesH) {
                if (sec.getAlias().equals(aliasSeccion)) {
                    n = "lista" + sec.getAlias().replace(".", "") + ".filas";
                }
            }
        } else {
            int i = 0;
            for (MetSeccion sec : listaSeccionesH) {
                if (sec.getAlias().equals(aliasSeccion)) {
                    n = "listasValoresHorizontal.get(" + i + ")";
                }
                i++;
            }
        }

        return n;
    }

    private int obtenerIdxVerticalDeVariable(String tipoSeccion, int contadorVariablesV, int filaVariable) {
        int i = 0;
        if (tipoSeccion.contains("V")) {
            i = contadorVariablesV;
        } else {
            i = (filaVariable - 1);
        }
        return i;
    }

    private Catalogo obtenerCatalogoDeVariable(MetVariable var) {
        Catalogo cat = new Catalogo();
        try {
            if (var.getCodConcepto() != null && var.getCodConcepto().getCodTipoCatalogo() != null) {
                cat = new Catalogo(var.getCodConcepto().getCodTipoCatalogo().getIdTipoCatalogo(), var.getCodConcepto().getCodTipoCatalogo().getAlias());
                if (cat.getIdTipoCatalogo() != null) {
                    List<Integer> li = new ArrayList<Integer>();
                    li.add(cat.getIdTipoCatalogo());
                    List<MetCatalogo> listaCatalogo = bc.getMetCatalogoServicioRemote().listarCatalogosPorTipos(li);
                    if (!listaCatalogo.isEmpty()) {
                        List<ItemCatalogo> lic = new ArrayList<>();
                        for (MetCatalogo catalogo : listaCatalogo) {
                            ItemCatalogo ic = new ItemCatalogo(catalogo.getDescripcion(), catalogo.getValor(), "", "");
                            lic.add(ic);
                        }
                        cat.setItems(lic);
                    }

                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    private void generarListaDeSeccionesH() {
        try {
            listaSeccionesH = new ArrayList<>();
            listaSeccionesH = bc.getMetSeccionServicioRemote().listarSeccionesTipoHPorFormulario(formularioActual.getIdFormulario());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    private void generarObjetoListaValoresPruebaVertical() {
        try {
            valoresVertical = new ArrayList<>();
            for (int i = 1; i <= contadorVariables; i++) {
                valoresVertical.add(new ValorVistaPrevia());
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void generarObjetoListaValoresPruebaHorizontal() {
        try {

            listasValoresHorizontal = new ArrayList<>();
            for (MetSeccion sec : listaSeccionesH) {
                List<ValorVistaPrevia> lvp = new ArrayList<>();
                if (sec.getTipo().contains("HD")) {
                    ValorVistaPrevia vp = new ValorVistaPrevia();
                    lvp.add(vp);

                } else if (sec.getTipo().contains("HF")) {
                    for (int j = 1; j <= sec.getFilas(); j++) {
                        ValorVistaPrevia vp = new ValorVistaPrevia();
                        lvp.add(vp);
                    }
                }
                listasValoresHorizontal.add(lvp);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void construirNombreArchivo() {
        try {
            Date fa = new Date();
            nombreArchivoDescarga = "f_" + formularioActual.getIdFormulario() + "_" + sdf.format(fa);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void definirIniyFinPagina() {

        String dc = "\"";
        iniPagina = "<?xml version='1.0' encoding='UTF-8' ?>" + nl;

        iniPagina = iniPagina + "<!DOCTYPE html PUBLIC " + dc + "-//W3C//DTD XHTML 1.0 Transitional//EN" + dc + " " + dc + "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" + dc + ">" + nl;
        iniPagina = iniPagina + "<html xmlns=" + dc + "http://www.w3.org/1999/xhtml" + dc + nl;
        iniPagina = iniPagina + "xmlns:ui=" + dc + "http://java.sun.com/jsf/facelets" + dc + nl;
        iniPagina = iniPagina + "xmlns:h=" + dc + "http://java.sun.com/jsf/html" + dc + nl;
        iniPagina = iniPagina + "xmlns:p=" + dc + "http://primefaces.org/ui" + dc + nl;
        iniPagina = iniPagina + "xmlns:f=" + dc + "http://java.sun.com/jsf/core" + dc + nl;
        iniPagina = iniPagina + "xmlns:c=" + dc + "http://java.sun.com/jsp/jstl/core" + dc + ">" + nl;
        iniPagina = iniPagina + "<ui:composition template=" + dc + "/plantillas/inec_plantilla.xhtml" + dc + ">" + nl;
        iniPagina = iniPagina + "<ui:define name=" + dc + "contenido" + dc + ">" + nl;
        iniPagina = iniPagina + "<script src='../../../js/saltosValidaciones/js_saltos_mod.js' type='text/javascript'></script>" + nl;
        iniPagina = iniPagina + "<script src='../../../js/saltosValidaciones/js_validacion_mod.js' type='text/javascript'></script>" + nl;
        iniPagina = iniPagina + "<script src='../../../js/saltosValidaciones/js_configuracion.js' type='text/javascript'></script>" + nl;
        iniPagina = iniPagina + " <h:form id=" + dc + "frmCaptura" + dc + ">" + nl;
        iniPagina = iniPagina + "<p:growl id=" + dc + "msgs" + dc + " showDetail=" + dc + "true" + dc + "/>" + nl;
        iniPagina = iniPagina + "<center>" + nl;
        iniPagina = iniPagina + "<h:panelGrid id=" + dc + "pnlCaptura" + dc + ">" + nl;

        finPagina = "</h:panelGrid>" + nl;
        finPagina = finPagina + "</center>" + nl;
        finPagina = finPagina + "</h:form>" + nl;
        finPagina = finPagina + "</ui:define>" + nl;
        finPagina = finPagina + "</ui:composition>" + nl;
        iniPagina = iniPagina + "<div id='pantalla' class='pantalla' style='display:none'> " + nl;
        iniPagina = iniPagina + "<img id='cargando' src='../../../imagenes/cargando.gif'/></div> " + nl;
        finPagina = finPagina + "</html>" + nl;

    }

    private String idFormularioOcultoParaCargarSaltos(Integer codForm) {
        String btn = "";
        String dc = "\"";
        btn = "<br/><h:inputHidden id=\"idFormulario\" value=" + dc + codForm + dc + " />";

        return btn;
    }
   public String cargarSaltos(Integer codForm) {
        String saltos = "";       
        saltos = "<br/><h:inputHidden id=\"saltos\" value=\"#{saltoValidacionControlador.listarSaltos("+ codForm + ")}\" /> ";
        return saltos;
    }
     public String cargarValidaciones(Integer codForm) {
       String validaciones = "";                  
       validaciones = "<br/><h:inputHidden id=\"validaciones\" value=\"#{saltoValidacionControlador.listarValiadciones("+ codForm +")}\" />";
       return validaciones;
    }
    private void definirSintaxisInicialesGenerales() {
        sintaxisInicialesGenerales = "ALTER TYPE zonal(a1).\n"
                + "VARIABLE WIDTH zonal(2).\n"
                + "ADD VALUE LABELS zonal\n"
                + "'N' 'Norte' 'L' 'Litoral' 'C' 'Centro' 'S' 'Sur' .\n"
                + "\n"
                + "ALTER TYPE area(a1).\n"
                + "VARIABLE WIDTH area(2).\n"
                + "ADD VALUE LABELS area\n"
                + "'1' 'Urbano' '2' 'Rural'.\n"
                + "\n"
                + "ALTER TYPE prov(a2).\n"
                + "VARIABLE WIDTH prov(2).\n"
                + "ALTER TYPE cant(a2).\n"
                + "VARIABLE WIDTH cant(2).\n"
                + "ALTER TYPE parr(a2).\n"
                + "VARIABLE WIDTH parr(2).\n"
                + "ALTER TYPE cong(a6).\n"
                + "VARIABLE WIDTH cong(6).\n"
                + "ALTER TYPE oviv(a2).\n"
                + "VARIABLE WIDTH oviv(2).\n"
                + "ALTER TYPE nhog(a1).\n"
                + "VARIABLE WIDTH nhog(2).\n"
                + "ALTER TYPE zona(a3).\n"
                + "VARIABLE WIDTH zona(3).\n"
                + "ALTER TYPE sect(a3).\n"
                + "VARIABLE WIDTH sect(3).\n"
                + "ALTER TYPE manz(a6).\n"
                + "VARIABLE WIDTH manz(8).\n"
                + "ALTER TYPE edif(a3).\n"
                + "VARIABLE WIDTH edif(3).\n"
                + "ALTER TYPE nviv(a3).\n"
                + "VARIABLE WIDTH nviv(2).";
    }

    
    private String obtenerCondicion(String condicion) {
        String cond0 = "";
        String cond1 = "";
        if (condicion.contains("includes") || condicion.contains("Math.abs")) {
            String cond2 = "";
            if (condicion.contains("includes")) {
                cond0=" "+obtenerVariable(condicion)+" <>'' ) and ";
                String[] part3 = condicion.split(".includes");
                String[] part4 = part3[1].split("\\)");
                
                //part4[1] pendiente
                cond1 = cond0+part4[0].replace("\\(", "").replace("[", "").replace("]", "")
                        + ")::integer in " + part3[0].replace("]", ")").replace("[", "(");
                if (part4.length > 1) {
                    cond2 = part4[1];
                }
            }
            if (condicion.contains("Math.abs") || cond2.length() > 3) {
                cond1 = "(pendiente";
            }
        } else {
            if(!condicion.contains("!==\"\"")){
                cond0=" ("+obtenerVariable(condicion)+" <>'' ) and ";
            }
            
            cond1 = condicion.replace("[", "");
            cond1 = cond1.replace("!==\"\"", " is not null");
            if(cond1.contains("is not null")){
                cond1 = cond1.replace("]", "");
            }else{
                cond1 = cond1.replace("]", "::integer");
            }
            cond1 = cond1.replace("!==", "<>");
            cond1 = cond1.replace("==", "=");
            cond1 = cond1.replace("||", " or ");
            cond1="("+cond0+" ("+cond1+")) ";
            
        }
        return cond1;
    }

    private String obtenerVariable(String cond) {
        String var = "";
        if (cond.contains("]!==")) {
            String[] parts = cond.split("]!==");
            var = parts[0].replace("[", "");
        } else if (cond.contains("]==")) {
            String[] parts = cond.split("]==");
            var = parts[0].replace("[", "");
        } else if (cond.contains(">")) {
            String[] parts = cond.split("]>");
            var = parts[0].replace("[", "");
        } else if (cond.contains("]<")) {
            String[] parts = cond.split("<");
            var = parts[0].replace("[", "");
        } else if (cond.contains(">=")) {
            String[] parts = cond.split("]>=");
            var = parts[0].replace("[", "");
        } else if (cond.contains("<=")) {
            String[] parts = cond.split("]<=");
            var = parts[0].replace("[", "");
        } else if (cond.contains("includes")) {
            String[] parts = cond.split(".includes");
            String[] part1 = parts[1].split("\\)");
            var = part1[0].replace("\\(", "");
        }
            var=var.replace("[", "");
            var=var.replace("]", "");
        return var;
    }
    
     public void generarConsultasOmision() {
        try {
            int codFormulario = codFormOmisiones;
            String codSecciones=codSeccionesOmisiones;
            String tabla=tablaOmisiones;
            String sql = "SELECT v.identificador, s.salto "
                    + "  FROM metadato.v_variables_secciones_v v1, metadato.met_variable v, metadato.met_salto s "
                    + "  where v1.id_var=v.id_var and v.id_var=s.cod_var "
                    + "  and cod_formulario=" + codFormulario + " and s1_orden in ("+codSecciones+") "
                    + "  and v.estado_logico=true and s.estado_logico=true "
                    + "  order by cod_formulario,s1_orden,s2_orden,s3_orden,v_orden";
            List<Object[]> result = bc.getMetVariableServicioRemote().listarDiccionarioVariablesCorporativas(sql);
            String select="select '"+tablaOmisiones+"',iden,'OMISIÓN DESPUÉS DE ";
            String from=" from "+tabla+" where ";
            nombreArchivoDescarga="";
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            formularioActual.setIdFormulario(codFormulario);
            construirNombreArchivo();
                nombreArchivoDescarga = "sql_omisiones_" + nombreArchivoDescarga;
                File archivoDatos = new File(request.getSession().getServletContext().getRealPath("/archivos/" + nombreArchivoDescarga + ".txt"));
                Writer ftxt = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(archivoDatos), "UTF-8"));
                System.out.println(nombreArchivoDescarga);
                ftxt.write("--sintaxis f" +codFormulario+" sec:"+codSecciones+ nl);
            
            for (Object[] ao : result) {
                if (ao[1] != null) {
                    String[] arrayCondTotal = String.valueOf(ao[1]).split(";");
                    if (arrayCondTotal.length > 0) {
                        ftxt.write(select+String.valueOf(ao[0])+" en "+obtenerCondicionTotal(arrayCondTotal[0])[0]+"'"+from+obtenerCondicionTotal(arrayCondTotal[0])[1]+ nl);
                    }
                    if (arrayCondTotal.length > 1) {
                        ftxt.write(select+String.valueOf(ao[0])+" en "+obtenerCondicionTotal(arrayCondTotal[1])[0]+"'"+from+obtenerCondicionTotal(arrayCondTotal[1])[1]+ nl);
                    }
                    if (arrayCondTotal.length > 2) {
                        ftxt.write(select+String.valueOf(ao[0])+" en "+obtenerCondicionTotal(arrayCondTotal[2])[0]+"'"+from+obtenerCondicionTotal(arrayCondTotal[2])[1]+ nl);
                    }
                    if (arrayCondTotal.length > 3) {
                        ftxt.write(select+String.valueOf(ao[0])+" en "+obtenerCondicionTotal(arrayCondTotal[3])[0]+"'"+from+obtenerCondicionTotal(arrayCondTotal[3])[1]+ nl);
                    }
                    if (arrayCondTotal.length > 4) {
                        ftxt.write(select+String.valueOf(ao[0])+" en "+obtenerCondicionTotal(arrayCondTotal[4])[0]+"'"+from+obtenerCondicionTotal(arrayCondTotal[4])[1]+ nl);
                    }
                    if (arrayCondTotal.length > 5) {
                        ftxt.write(select+String.valueOf(ao[0])+" en "+obtenerCondicionTotal(arrayCondTotal[5])[0]+"'"+from+obtenerCondicionTotal(arrayCondTotal[5])[1]+ nl);
                    }
                }

            }
            ftxt.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    private String[] obtenerCondicionTotal(String val) {
        String[] r = new String[2];
        String[] parts = val.split("~");
        //part 1 es salto a donde va
        String part1 = parts[0];
        String varOmision = part1.replace("[", "").replace("]", "");
        r[0]=varOmision;
        varOmision = "(" + varOmision + " is null) and (";
        //part2 son todas las condiciones
        if(parts.length<2){
            System.out.println(part1);
        }
        String part2 = parts[1];
        String cond1 = "";
        String cond2 = "";
        String cond3 = "";
        String cond4 = "";
        String cond5 = "";
        //arraycond es cada condicion separada por &&
        String[] arrayCond = String.valueOf(part2).split("&&");
        if (!part1.contains("FIN")) {
            if (arrayCond.length > 0) {
                cond1 = obtenerCondicion(arrayCond[0]);
            }
            if (arrayCond.length > 1) {
                cond2 = " and " + obtenerCondicion(arrayCond[1]);
            }
            if (arrayCond.length > 2) {
                cond3 = " and " + obtenerCondicion(arrayCond[2]);
            }
            if (arrayCond.length > 3) {
                cond4 = " and " + obtenerCondicion(arrayCond[3]);
            }
            if (arrayCond.length > 4) {
                cond5 = " and " + obtenerCondicion(arrayCond[4]);
            }
        }
        r[1] = varOmision + cond1 + cond2 + cond3 + cond4 + cond5 + ")";
        return r;
    }
    
    

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" GETTERSYSETTERS">
    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }

    public MetOperativo getOperativoActual() {
        return operativoActual;
    }

    public void setOperativoActual(MetOperativo operativoActual) {
        this.operativoActual = operativoActual;
    }

    public List<MetOperativo> getListaOperativo() {
        return listaOperativo;
    }

    public void setListaOperativo(List<MetOperativo> listaOperativo) {
        this.listaOperativo = listaOperativo;
    }

    public MetFormulario getFormularioActual() {
        return formularioActual;
    }

    public void setFormularioActual(MetFormulario formularioActual) {
        this.formularioActual = formularioActual;
    }

    public List<MetFormulario> getListaFormularios() {
        return listaFormularios;
    }

    public void setListaFormularios(List<MetFormulario> listaFormularios) {
        this.listaFormularios = listaFormularios;
    }

    public String getNombreArchivoDescarga() {
        return nombreArchivoDescarga;
    }

    public void setNombreArchivoDescarga(String nombreArchivoDescarga) {
        this.nombreArchivoDescarga = nombreArchivoDescarga;
    }

    public List<String> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<String> listaErrores) {
        this.listaErrores = listaErrores;
    }

    public String getNombreControlador() {
        return nombreControlador;
    }

    public void setNombreControlador(String nombreControlador) {
        this.nombreControlador = nombreControlador;
    }

    public boolean getSoloCodigo() {
        return soloCodigo;
    }

    public void setSoloCodigo(boolean soloCodigo) {
        this.soloCodigo = soloCodigo;
    }

    public UsuarioControlador getUc() {
        return uc;
    }

    public void setUc(UsuarioControlador uc) {
        this.uc = uc;
    }

    public String getNombreObjetoValoresVertical() {
        return nombreObjetoValoresVertical;
    }

    public void setNombreObjetoValoresVertical(String nombreObjetoValoresVertical) {
        this.nombreObjetoValoresVertical = nombreObjetoValoresVertical;
    }

    public String getIniPagina() {
        return iniPagina;
    }

    public void setIniPagina(String iniPagina) {
        this.iniPagina = iniPagina;
    }

    public String getCodigoJSFGenerado() {
        return codigoJSFGenerado;
    }

    public void setCodigoJSFGenerado(String codigoJSFGenerado) {
        this.codigoJSFGenerado = codigoJSFGenerado;
    }

    public String getFinPagina() {
        return finPagina;
    }

    public void setFinPagina(String finPagina) {
        this.finPagina = finPagina;
    }

    public String getNombreObjetosListasValoresHorizontal() {
        return nombreObjetosListasValoresHorizontal;
    }

    public void setNombreObjetosListasValoresHorizontal(String nombreObjetosListasValoresHorizontal) {
        this.nombreObjetosListasValoresHorizontal = nombreObjetosListasValoresHorizontal;
    }

    public int getContadorVariables() {
        return contadorVariables;
    }

    public void setContadorVariables(int contadorVariables) {
        this.contadorVariables = contadorVariables;
    }

    public List<ValorVistaPrevia> getValoresVertical() {
        return valoresVertical;
    }

    public void setValoresVertical(List<ValorVistaPrevia> valoresVertical) {
        this.valoresVertical = valoresVertical;
    }

    public List<List<ValorVistaPrevia>> getListasValoresHorizontal() {
        return listasValoresHorizontal;
    }

    public void setListasValoresHorizontal(List<List<ValorVistaPrevia>> listasValoresHorizontal) {
        this.listasValoresHorizontal = listasValoresHorizontal;
    }

    public List<MetSeccion> getListaSeccionesH() {
        return listaSeccionesH;
    }

    public void setListaSeccionesH(List<MetSeccion> listaSeccionesH) {
        this.listaSeccionesH = listaSeccionesH;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getNl() {
        return nl;
    }

    public void setNl(String nl) {
        this.nl = nl;
    }
    
// </editor-fold>

    public int getCodFormOmisiones() {
        return codFormOmisiones;
    }

    public void setCodFormOmisiones(int codFormOmisiones) {
        this.codFormOmisiones = codFormOmisiones;
    }

    public String getCodSeccionesOmisiones() {
        return codSeccionesOmisiones;
    }

    public void setCodSeccionesOmisiones(String codSeccionesOmisiones) {
        this.codSeccionesOmisiones = codSeccionesOmisiones;
    }

    public String getTablaOmisiones() {
        return tablaOmisiones;
    }

    public void setTablaOmisiones(String tablaOmisiones) {
        this.tablaOmisiones = tablaOmisiones;
    }
}
