/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.jsf.controlador.modulos;

import ec.gob.inec.metadato.ejb.entidades.MetConcepto;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 * Controlador que permite administrar la pagina gestionVariables
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionVariableControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionVariableControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private MetVariable objVariableActual;
    private List<MetVariable> listarVariablees;
    private List<MetConcepto> conceptos;
    private List<MetSeccion> secciones;
    private boolean habilitaEdicion;
    private List<Object[]> listaVariablesCorp;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objVariableActual = new MetVariable();

            //listarVariablePorSeccion();
            conceptos = getMetConceptoServicioRemote().listarTodo();
            secciones = getMetSeccionServicioRemote().listarTodo();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionVariableControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void guardarVariable() {
        try {
            if (habilitaEdicion == false) {
                objVariableActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objVariableActual.setEstadoLogico(true);
                getMetVariableServicioRemote().crearVariable(objVariableActual);
                addSuccessMessage("Registro Guardado");
                //listarVariablePorSeccion();
                objVariableActual = new MetVariable();
            } else {
                objVariableActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getMetVariableServicioRemote().editarVariable(objVariableActual);
                addSuccessMessage("Registro Actualizado");
                //listarVariablePorSeccion();
                objVariableActual = new MetVariable();
                habilitaEdicion = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objVariableActual = new MetVariable();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarVariableCampos(MetVariable aplicacion) {
        try {
            objVariableActual = new MetVariable();
            objVariableActual = aplicacion;
            habilitaEdicion = true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void confirmaEliminar(MetVariable variable) {
        try {
            variable.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            variable.setEstadoLogico(false);
            getMetVariableServicioRemote().editarVariable(variable);
            addSuccessMessage("Registro Eliminado");
            //listarVariablePorSeccion();
            objVariableActual = new MetVariable();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarVariablePorSeccion(ValueChangeEvent event) throws Exception {
        if (event.getNewValue() != null) {
            for (MetSeccion sec : secciones) {
                if (sec.toString().equals(event.getNewValue().toString())) {
                    listarVariablees = getMetVariableServicioRemote().listarVariablesPorSeccion(sec.getIdSeccion().intValue());
                    break;
                }
            }
        }
    }
    
    public void listarDicVariablesCorp() throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append(" select con.identificador as cod_var, con.nombre as nom_var, var.pregunta, con.descripcion as def_var, ins.nombre as ins_rec, cat1.nombre as tip_var, cat2.nombre as tip_dat, cat3.nombre as for_dat, con.longitud_minima, con.longitud_maxima, con.rango_minimo, con.rango_maximo, tcat.nombre as cat_var, val.mensaje as val_var, org.nombre as ges_enc, oest.nombre as ope_est, ope.cod_responsable as reg_var ");
        sql.append(" from metadato.met_variable var ");
        sql.append(" LEFT JOIN metadato.met_validacion val ON val.cod_var = var.id_var, ");
        sql.append(" metadato.met_concepto con ");
        sql.append(" LEFT JOIN metadato.met_tipo_catalogo tcat ON tcat.id_tipo_catalogo = con.cod_tipo_catalogo, ");
        sql.append(" administracion.adm_institucion ins, metadato.met_catalogo cat1, metadato.met_catalogo cat2, metadato.met_catalogo cat3, metadato.met_seccion sec, administracion.adm_organigrama org, metadato.met_formulario_seccion fs, metadato.met_formulario form, metadato.met_operativo ope, administracion.adm_operacion_estadistica oest ");
        sql.append(" where var.cod_concepto = con.id_concepto ");
        sql.append(" and con.cod_institucion = ins.id_institucion ");
        sql.append(" and con.cod_cat_clasificacion = cat1.id_catalogo ");
        sql.append(" and con.cod_cat_seguridad = cat2.id_catalogo ");
        sql.append(" and con.cod_cat_dato = cat3.id_catalogo ");
        sql.append(" and var.cod_seccion = sec.id_seccion ");
        sql.append(" and sec.cod_organigrama = org.id_organigrama ");
        sql.append(" and (sec.id_seccion = fs.cod_seccion or sec.cod_seccion_padre = fs.cod_seccion) ");
        sql.append(" and fs.cod_formulario = form.id_formulario ");
        sql.append(" and form.cod_operativo = ope.id_operativo ");
        sql.append(" and ope.cod_ope = oest.id_ope_est ");

        listaVariablesCorp = getMetVariableServicioRemote().listarDiccionarioVariablesCorporativas(sql.toString());
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public MetVariable getObjVariableActual() {
        return objVariableActual;
    }

    public void setObjVariableActual(MetVariable objVariableActual) {
        this.objVariableActual = objVariableActual;
    }

    public List<MetVariable> getListarVariablees() {
        return listarVariablees;
    }

    public void setListarVariablees(List<MetVariable> listarVariablees) {
        this.listarVariablees = listarVariablees;
    }

    public List<MetConcepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<MetConcepto> conceptos) {
        this.conceptos = conceptos;
    }

    public List<MetSeccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<MetSeccion> secciones) {
        this.secciones = secciones;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }
    
    //</editor-fold>

    public List<Object[]> getListaVariablesCorp() {
        return listaVariablesCorp;
    }

    public void setListaVariablesCorp(List<Object[]> listaVariablesCorp) {
        this.listaVariablesCorp = listaVariablesCorp;
    }
}
