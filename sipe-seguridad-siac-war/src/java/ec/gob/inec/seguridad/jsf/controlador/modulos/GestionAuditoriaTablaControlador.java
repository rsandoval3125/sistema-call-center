/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.controlador.modulos;

import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.clases.Tabla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 * Controlador que permite activar o desactivar la auditoria en tablas de la
 * BDD.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionAuditoriaTablaControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionAuditoriaTablaControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private boolean habilitaEdicion;
    private DualListModel<Tabla> tablasTriggers;
    private List<Tabla> tablasTriggersAsignados;
    private List<Tabla> tablasTriggersNoAsignados;

    private String esquemaSelected;
    private List<String> esquemas;
    private Map<String, Boolean> permisosBoton;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            //    habilitaEdicionPermiso = false;           
            tablasTriggersAsignados = new ArrayList<Tabla>();
            tablasTriggersNoAsignados = new ArrayList<Tabla>();
            tablasTriggers = new DualListModel<Tabla>(tablasTriggersNoAsignados, tablasTriggersAsignados);
//            permisosBoton = vistaControlador.getPermisos();

            listarEsquemasTodos();
            listarTablasTriggersGuardados();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionAuditoriaTablaControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarTablasTrigersAsignados() {

    }

    public void onTransfer(TransferEvent event) {
        try {
            for (Object item : event.getItems()) {
                Tabla tabla = (Tabla) item;
                String nombreTabla = tabla.toString();
                if (event.isAdd()) {
                    LOGGER.log(Level.FINER, "Agregado " + nombreTabla);
                    if (getSegAuditoriaCabServicioRemote().validarTablaParaAuditar(tabla.getTableName())) {
                        getSegAuditoriaCabServicioRemote().activarAuditoria(nombreTabla);
                        addSuccessMessage("Activado la auditoria para " + nombreTabla);
                    } else {
                        addWarningMessage("No tiene el campo cod_auditoria_cab la tabla " + nombreTabla);
                    }
                } else if (event.isRemove()) {
                    LOGGER.log(Level.FINER, "Removido " + nombreTabla);
                    getSegAuditoriaCabServicioRemote().desactivarAuditoria(nombreTabla);
                    addSuccessMessage("Desactivado la auditoria para " + nombreTabla);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            addWarningMessage("No se puede activar o desactivar la auditoria en las tablas.");
        }
        listarTablasTriggersGuardados();
    }

    public void listarTablasTriggersGuardados() {
        try {
            tablasTriggersAsignados = getSegAuditoriaCabServicioRemote().listarTablasConTriggersAsignados();
            LOGGER.log(Level.INFO, "Tablas con auditoria " + tablasTriggersAsignados.size());
            List<String> tablas = new ArrayList<>();
            for (Tabla t : tablasTriggersAsignados) {
                tablas.add(t.getTableName());
            }
            LOGGER.log(Level.FINER, "Esquema seleccionado " + esquemaSelected);

            if (esquemaSelected != null && !esquemaSelected.equals("")) {                
                tablasTriggersNoAsignados = getSegAuditoriaCabServicioRemote().listarTablasSinTrigersNoAsignadosPorEsquema(tablas, esquemaSelected);
            } else {
                 LOGGER.log(Level.FINER, "Sin esquema");
                tablasTriggersNoAsignados = getSegAuditoriaCabServicioRemote().listarTablasSinTrigersNoAsignados(tablas);
            }

            LOGGER.log(Level.INFO, "Tablas sin auditoria " + tablasTriggersNoAsignados.size());

            tablasTriggers = new DualListModel<Tabla>(tablasTriggersNoAsignados, tablasTriggersAsignados);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarEsquemasTodos() throws Exception {
        esquemas = getSegAuditoriaCabServicioRemote().listarEsquemasBDD();
        //  RequestContext.getCurrentInstance().update("frmGestionUsuario:cmbEsquemas");
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
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

    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }

    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
    }

    public String getEsquemaSelected() {
        return esquemaSelected;
    }

    public void setEsquemaSelected(String esquemaSelected) {
        this.esquemaSelected = esquemaSelected;
    }

    public List<String> getEsquemas() {
        return esquemas;
    }

    public void setEsquemas(List<String> esquemas) {

        this.esquemas = esquemas;
    }

    public DualListModel<Tabla> getTablasTriggers() {
        return tablasTriggers;
    }

    public void setTablasTriggers(DualListModel<Tabla> tablasTriggers) {
        this.tablasTriggers = tablasTriggers;
    }

    public List<Tabla> getTablasTriggersAsignados() {
        return tablasTriggersAsignados;
    }

    public void setTablasTriggersAsignados(List<Tabla> tablasTriggersAsignados) {
        this.tablasTriggersAsignados = tablasTriggersAsignados;
    }

    public List<Tabla> getTablasTriggersNoAsignados() {
        return tablasTriggersNoAsignados;
    }

    public void setTablasTriggersNoAsignados(List<Tabla> tablasTriggersNoAsignados) {
        this.tablasTriggersNoAsignados = tablasTriggersNoAsignados;
    }
    //</editor-fold>

}
