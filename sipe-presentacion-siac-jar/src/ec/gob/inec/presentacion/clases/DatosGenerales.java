/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases;

import java.util.logging.Logger;

/**
 *
 * @author lponce
 */
public class DatosGenerales {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(DatosGenerales.class.getName());

    private String paginahome;
    private String paginahomeCompleto;
    private String titulo;
    private String detalleSistema;
    private String imagenproyecto;

    private String imagenpie;
    private String imageninec;
    private String derechosreservados;
    private String direccionresponsable;

    private boolean sesioncaducada;
    private String sesioncaducadaheader;
    private String sesioncaducadawidgetVar;
    private String sesioncaducadatexto;
    private String sesioncaducadaboton;

    private String usuarionologueadoheader;
    private boolean existeusuario;
    private String usuarionologueadotexto;

    private String dialogwidgetVar;
    private String dialogGifwidgetVar;
    private String dialogimagen;
    private String dialogGifimagen;
    private String dialogheader;
    private String dialogGifheader;
    private String dialogGiffooter;

    private String backgroundImage;

    private int idPagina;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public DatosGenerales() {
       // System.out.println("dgr");
    }

    /*@PostConstruct
    public void init() {
        sesioncaducada = false;
    }*/
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagenproyecto() {
        return imagenproyecto;
    }

    public void setImagenproyecto(String imagenproyecto) {
        this.imagenproyecto = imagenproyecto;
    }

    public String getImagenpie() {
        return imagenpie;
    }

    public void setImagenpie(String imagenpie) {
        this.imagenpie = imagenpie;
    }

    public String getDerechosreservados() {
        return derechosreservados;
    }

    public void setDerechosreservados(String derechosreservados) {
        this.derechosreservados = derechosreservados;
    }

    public String getDireccionresponsable() {
        return direccionresponsable;
    }

    public void setDireccionresponsable(String direccionresponsable) {
        this.direccionresponsable = direccionresponsable;
    }

    public String getImageninec() {
        return imageninec;
    }

    public void setImageninec(String imageninec) {
        this.imageninec = imageninec;
    }

    public boolean isSesioncaducada() {
        return sesioncaducada;
    }

    public void setSesioncaducada(boolean sesioncaducada) {
        this.sesioncaducada = sesioncaducada;
    }

    public String getSesioncaducadaheader() {
        return sesioncaducadaheader;
    }

    public void setSesioncaducadaheader(String sesioncaducadaheader) {
        this.sesioncaducadaheader = sesioncaducadaheader;
    }

    public String getSesioncaducadawidgetVar() {
        return sesioncaducadawidgetVar;
    }

    public void setSesioncaducadawidgetVar(String sesioncaducadawidgetVar) {
        this.sesioncaducadawidgetVar = sesioncaducadawidgetVar;
    }

    public String getSesioncaducadatexto() {
        return sesioncaducadatexto;
    }

    public void setSesioncaducadatexto(String sesioncaducadatexto) {
        this.sesioncaducadatexto = sesioncaducadatexto;
    }

    public String getSesioncaducadaboton() {
        return sesioncaducadaboton;
    }

    public void setSesioncaducadaboton(String sesioncaducadaboton) {
        this.sesioncaducadaboton = sesioncaducadaboton;
    }

    public String getPaginahome() {
        return paginahome;
    }

    public void setPaginahome(String paginahome) {
        this.paginahome = paginahome;
    }

    public String getUsuarionologueadoheader() {
        return usuarionologueadoheader;
    }

    public void setUsuarionologueadoheader(String usuarionologueadoheader) {
        this.usuarionologueadoheader = usuarionologueadoheader;
    }

    public boolean isExisteusuario() {
        return existeusuario;
    }

    public void setExisteusuario(boolean existeusuario) {
        this.existeusuario = existeusuario;
    }

    public String getUsuarionologueadotexto() {
        return usuarionologueadotexto;
    }

    public void setUsuarionologueadotexto(String usuarionologueadotexto) {
        this.usuarionologueadotexto = usuarionologueadotexto;
    }

    public String getDialogwidgetVar() {
        return dialogwidgetVar;
    }

    public void setDialogwidgetVar(String dialogwidgetVar) {
        this.dialogwidgetVar = dialogwidgetVar;
    }

    public String getDialogGifwidgetVar() {
        return dialogGifwidgetVar;
    }

    public void setDialogGifwidgetVar(String dialogGifwidgetVar) {
        this.dialogGifwidgetVar = dialogGifwidgetVar;
    }

    public String getDialogimagen() {
        return dialogimagen;
    }

    public void setDialogimagen(String dialogimagen) {
        this.dialogimagen = dialogimagen;
    }

    public String getDialogGifimagen() {
        return dialogGifimagen;
    }

    public void setDialogGifimagen(String dialogGifimagen) {
        this.dialogGifimagen = dialogGifimagen;
    }

    public String getDialogheader() {
        return dialogheader;
    }

    public void setDialogheader(String dialogheader) {
        this.dialogheader = dialogheader;
    }

    public String getDialogGifheader() {
        return dialogGifheader;
    }

    public void setDialogGifheader(String dialogGifheader) {
        this.dialogGifheader = dialogGifheader;
    }

    public String getDialogGiffooter() {
        return dialogGiffooter;
    }

    public void setDialogGiffooter(String dialogGiffooter) {
        this.dialogGiffooter = dialogGiffooter;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getDetalleSistema() {
        return detalleSistema;
    }

    public void setDetalleSistema(String detalleSistema) {
        this.detalleSistema = detalleSistema;
    }

    public String getPaginahomeCompleto() {
        return paginahomeCompleto;
    }

    public void setPaginahomeCompleto(String paginahomeCompleto) {
        this.paginahomeCompleto = paginahomeCompleto;
    }

    public int getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(int idPagina) {
        this.idPagina = idPagina;
    }
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    //</editor-fold>

}
