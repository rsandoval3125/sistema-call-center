/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.ejb.utils.ReflexionEntidad;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author jaraujo
 */
@ManagedBean
@ViewScoped
public class ConexionReporteControlador {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">
    private static final Logger LOGGER = Logger.getLogger(ConexionReporteControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vc;

    private AdmBaseDatos admBDD;
    private AdmBaseDatos admBDDAnterior;
    private List<AdmBaseDatos> lstAdmBDDConexion;
    private boolean habilitaEdicion;
    private List<AdmColumna> columnasAEcnriptar;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get and set">
    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }

    public AdmBaseDatos getAdmBDD() {
        return admBDD;
    }

    public void setAdmBDD(AdmBaseDatos admBDD) {
        this.admBDD = admBDD;
    }

    public List<AdmBaseDatos> getLstAdmBDDConexion() {
        return lstAdmBDDConexion;
    }

    public void setLstAdmBDDConexion(List<AdmBaseDatos> lstAdmBDDConexion) {
        this.lstAdmBDDConexion = lstAdmBDDConexion;
    }

    public VistaControlador getVc() {
        return vc;
    }

    public void setVc(VistaControlador vc) {
        this.vc = vc;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="constructor">
    public ConexionReporteControlador() {

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="métodos">
    @PostConstruct
    public void inicializar() {
        try {
            admBDD = new AdmBaseDatos();
            admBDDAnterior = new AdmBaseDatos();
            columnasAEcnriptar = bc.getAdmColumnaServicioRemote().consultarColumnasAEncriptarPorTabla("administracion.adm_base_datos");
            listarAdmBDDTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void nuevaConexion() {
        try {
            admBDD = new AdmBaseDatos();
            admBDDAnterior = new AdmBaseDatos();
            habilitaEdicion = false;
            listarAdmBDDTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarConn() {
        try {
            List<AdmBaseDatos> lstSistemas = bc.getAdmBaseDatosServicioRemote().listarTodo();
            boolean crearConn = false;
            String parametroAES = bc.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();

            if (lstSistemas != null) {
                if (habilitaEdicion == false) {
                    AdmBaseDatos admBDDTemp = admBDD;
                    //ReflexionEntidad.encriptaCampos(admBDDTemp, columnasAEcnriptar, parametroAES);
                    System.out.println("tamaño=" + lstSistemas.size());
                    crearConn = lstSistemas.size() <= 0 ? true : false;
                    for (AdmBaseDatos base : lstSistemas) {
                        if (admBDDTemp.getRdbms().equals(base.getRdbms())) {
                            if (admBDDTemp.getNombrebdd().equals(base.getNombrebdd()) && admBDDTemp.getIp().equals(base.getIp())) {
                                if (admBDDTemp.getUsuario().equals(base.getUsuario())) {
                                    //if (admBDDTemp.getPassword().equals(base.getPassword())) {
                                    if (base.getEstadoLogico() == false) {
                                        bc.addWarningMessage("Se recomienda activar la conexión existente, " + base.getNombre());
                                        crearConn = false;
                                        break;
                                    }
                                    crearConn = false;
                                    break;
                                    /*} else {
                                        crearConn = true;
                                    }*/
                                } else {
                                    crearConn = true;
                                }
                            } else {
                                crearConn = true;
                            }
                        } else {
                            crearConn = true;
                        }
                    }
                } else {
                    crearConn = true;
                }
            } else {
                crearConn = true;
            }

            if (crearConn) {
                if (habilitaEdicion == false) {
                    ReflexionEntidad.encriptaCampos(admBDD, columnasAEcnriptar, parametroAES);
                    admBDD.setCodAuditoriaCab(vc.getCodAuditoria("PAG_INSRT"));
                    admBDD.setAlias(admBDD.getAlias().toLowerCase().replaceAll("\\s", ""));
                    bc.getAdmBaseDatosServicioRemote().crearConexion(admBDD);
                    bc.addSuccessMessage("Registro Guardado");
                } else {
                    ReflexionEntidad.encriptaCampos(admBDDAnterior, admBDD, columnasAEcnriptar, parametroAES);
                    admBDD.setCodAuditoriaCab(vc.getCodAuditoria("PAG_MODIF"));
                    admBDD.setAlias(admBDD.getAlias().toLowerCase().replaceAll("\\s", ""));
                    bc.getAdmBaseDatosServicioRemote().editarConexion(admBDD);
                    bc.addSuccessMessage("Registro Actualizado");
                    habilitaEdicion = false;
                }
                listarAdmBDDTodos();
                admBDD = new AdmBaseDatos();
            } else {
                //admBDD.setPassword("");
                bc.addErrorMessage("Ya existe la conexión ingresada");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            bc.addWarningMessage("No se puede guardar el registro");
        }
    }

    public void eliminarConn(AdmBaseDatos admBDDEliminar) {
        try {
            admBDDEliminar.setEstadoLogico(false);
            admBDDEliminar.setCodAuditoriaCab(vc.getCodAuditoria("PAG_ELIM"));
            bc.getAdmBaseDatosServicioRemote().editarConexion(admBDDEliminar);
            bc.addSuccessMessage("Registro dado de baja");
            admBDD = new AdmBaseDatos();
            listarAdmBDDTodos();
            habilitaEdicion = false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            bc.addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void editarConn(AdmBaseDatos admBDDEditar) {
        try {
            admBDD = new AdmBaseDatos();
            admBDD = admBDDEditar;
            habilitaEdicion = true;
            admBDD = (AdmBaseDatos) SerializationUtils.clone(admBDDEditar);
            admBDDAnterior = (AdmBaseDatos) SerializationUtils.clone(admBDDEditar);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarAdmBDDTodos() throws Exception {
        try {
            lstAdmBDDConexion = bc.getAdmBaseDatosServicioRemote().listarTodosActivos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cancelarEdicion() throws Exception {
        try {
            admBDD = new AdmBaseDatos();
            bc.addSuccessMessage("Edición Cancelada");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

}
