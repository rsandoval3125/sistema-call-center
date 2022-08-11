/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.controlador.modulos;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegPermiso;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegRolPermiso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

/**
 * Controlador que permite administrar los roles y permisos de usuarios.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionRolControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionRolControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private SegRol objRolActual;
    private List<SegRol> listarRoles;
    private boolean habilitaEdicion;
    private DualListModel<SegPermiso> permisos;
    private List<SegPermiso> permisosAsignados;
    private List<SegPermiso> permisosNoAsignados;

    private SegAplicacion aplicacionActual;
    private List<SegAplicacion> aplicaciones;

    private SegPagina objPaginaActual;
    private List<SegPagina> listarPaginas;
    // private List<String> selectedAcciones;
    private List<MetCatalogo> acciones;
    private List<MetCatalogo> selectedAccs;
    //  private boolean habilitaEdicionPermiso;
    private boolean selectAll; // +getter +setter

    private Map<Integer, List<SegPermiso>> mapaPermisos;

    private SegAplicacion aplicacionSelected;
    private Map<String, Boolean> permisosBoton;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            //    habilitaEdicionPermiso = false;
            objRolActual = new SegRol();
            permisosAsignados = new ArrayList<SegPermiso>();
            permisosNoAsignados = new ArrayList<SegPermiso>();
            permisos = new DualListModel<SegPermiso>(permisosNoAsignados, permisosAsignados);
            permisosBoton = vistaControlador.getPermisos();
            aplicaciones = getSegAplicacionServicioRemote().listarTodosActivos();
            acciones = getMetCatalogoServicioRemote().listarCatalogoXAlias("ACC_PAG");
            cargarPaginasPermisos();
            listarRolTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionRolControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void cargarPaginasPermisos() {
        try {
            if (aplicacionActual != null) {
                listarPaginas = getSegPaginaServicioRemote().listarPaginasActivasPorAplicacion(aplicacionActual.getIdApl());
            } else {
                listarPaginas = getSegPaginaServicioRemote().listarTodosActivos();
            }
            mapaPermisos = new HashMap<>();

            List<SegPermiso> tmpPermisos = getSegPermisoServicioRemote().listarPermisosPorPagina(listarPaginas);
            for (SegPermiso permiso : tmpPermisos) {
                //System.out.println("Id Pag=" + pagina.getIdPag() + "Permisos cuenta=" + tmpPermisos.size());
                List<SegPermiso> permisos = mapaPermisos.get(permiso.getCodPag().getIdPag());
                if (permisos == null) {
                    permisos = new ArrayList<SegPermiso>();
                }
                permisos.add(permiso);
                mapaPermisos.put(permiso.getCodPag().getIdPag(), permisos);
            }
            /*  for (SegPagina pagina : listarPaginas) {
                List<SegPermiso> tmpPermisos = new ArrayList<>();
                for (SegPermiso permiso : pagina.getSegPermisoList()) {
                    if (permiso.getEstadoLogico()) {
                        tmpPermisos.add(permiso);
                    }
                }
                pagina.setSegPermisoList(tmpPermisos);
            }*/
 /*   for (SegPagina pagina : listarPaginas) {
                List<SegPermiso> permisos = new ArrayList<SegPermiso>();
                for (MetCatalogo catalogo : acciones) {
                    boolean encontrado = false;
                    for (SegPermiso permiso : pagina.getSegPermisoList()) {
                        if (catalogo.getIdCatalogo() == permiso.getCodAccion()) {
                            encontrado = true;
                            permisos.add(permiso);
                            break;
                        }
                    }
                    if (!encontrado) {
                        Permiso perm = new Permiso();
                        perm.setActivo(true);
                        perm.setIdPermiso(-1);
                        perm.setAlias(catalogo.getNombre());
                        perm.setCodAccion(catalogo.getIdCatalogo());
                        perm.setEstadoLogico(false);
                        permisos.add(perm);
                    }
                }
                pagina.setSegPermisoList(permisos);
            }*/
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void recuperarPaginaPermisosCampos(SegPagina pagina) {
        try {
            objPaginaActual = new SegPagina();
            objPaginaActual = pagina;
            //  selectedAcciones = new ArrayList<String>();
            selectedAccs = new ArrayList<MetCatalogo>();
            for (MetCatalogo catalogo : acciones) {
                List<SegPermiso> tmpPermisos = mapaPermisos.get(pagina.getIdPag());
                if (tmpPermisos == null) {
                    tmpPermisos = new ArrayList<SegPermiso>();
                }
                catalogo.setDescripcion(pagina.getCodApl().getAlias() + " - " + pagina.getAlias() + " - " + catalogo.getAlias());
                for (SegPermiso permiso : tmpPermisos) {
                    if (catalogo.getIdCatalogo().equals(permiso.getCodAccion().getIdCatalogo()) && permiso.getEstadoLogico()) {
                        // selectedAcciones.add(catalogo.getIdCatalogo().toString());     
                        System.out.println(catalogo.getNombre());
                        selectedAccs.add(catalogo);
                        break;
                    }
                }
            }
            // habilitaEdicionPermiso = true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void guardarPagina() {
        try {
            List<SegPermiso> anteriorPermsisos = mapaPermisos.get(objPaginaActual.getIdPag());
            if (anteriorPermsisos == null) {
                anteriorPermsisos = new ArrayList<SegPermiso>();
            }
            for (SegPermiso permiso : anteriorPermsisos) {
                permiso.setEstadoLogico(false);
                getSegPermisoServicioRemote().editarPermiso(permiso);
            }
            for (MetCatalogo catAccion : selectedAccs) {
                SegPermiso permisoEncontrado = null;
                for (SegPermiso permiso : anteriorPermsisos) {
                    if (catAccion.getIdCatalogo().equals(permiso.getCodAccion().getIdCatalogo())) {
                        permisoEncontrado = permiso;
                        break;
                    }
                }
                if (permisoEncontrado != null) {//Actualizar                    
                    permisoEncontrado.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                    permisoEncontrado.setAlias(objPaginaActual.getCodApl().getAlias() + " - " + objPaginaActual.getAlias() + " - " + getAliasAccion(catAccion.getIdCatalogo()));
                    permisoEncontrado.setEstadoLogico(true);
                    getSegPermisoServicioRemote().editarPermiso(permisoEncontrado);
                    // addSuccessMessage("Registro Actualizado");
                } else {//Insertar
                    SegPermiso permiso = new SegPermiso();
                    permiso.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                    permiso.setCodPag(objPaginaActual);
                    permiso.setAlias(objPaginaActual.getCodApl().getAlias() + " - " + objPaginaActual.getAlias() + " - " + getAliasAccion(catAccion.getIdCatalogo()));
                    permiso.setCodAccion(new MetCatalogo(catAccion.getIdCatalogo()));
                    permiso.setEstadoLogico(true);
                    getSegPermisoServicioRemote().crearPermiso(permiso);
                    // addSuccessMessage("Registro Guardado");
                }
            }
            cargarPaginasPermisos();
            // habilitaEdicionPermiso = false;
            objPaginaActual = new SegPagina();
            selectedAccs = new ArrayList<MetCatalogo>();
            selectAll = false;
            if (habilitaEdicion) {
                System.out.println("----Lista Permisos en PickList");
                listarPermisosGuardados();
            }
            addSuccessMessage("Registro Guardado");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    private String getAliasAccion(Integer idCatalogo) {
        for (MetCatalogo mc : acciones) {
            if (idCatalogo.equals(mc.getIdCatalogo())) {
                return mc.getAlias();
            }
        }
        return "";
    }

    public void confirmaEliminarPaginaPermisos(SegPagina pagina) {
        try {
            for (SegPermiso permiso : mapaPermisos.get(pagina.getIdPag())) {
                permiso.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
                permiso.setEstadoLogico(false);
                getSegPermisoServicioRemote().editarPermiso(permiso);
            }
            addSuccessMessage("Registro Eliminado");
            listarRolTodos();
            objPaginaActual = new SegPagina();
            // habilitaEdicionPermiso = false;
            listarPermisosGuardados();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void guardarPermisosAsignadosDeRol() {
        try {
            for (SegPermiso permiso : permisos.getTarget()) {
                if (getSegRolPermisoServicioRemote().existePermisoRol(objRolActual.getIdRol(), permiso.getIdPermiso())) {
                } else {
                    //grabar  
                    permiso.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                    SegRolPermiso pr = new SegRolPermiso();
                    pr.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                    pr.setCodRol(objRolActual);
                    pr.setCodPermiso(permiso);
                    pr.setEstadoLogico(true);
                    getSegRolServicioRemote().editarRol(objRolActual);
                    getSegRolPermisoServicioRemote().crearRolPermiso(pr);

                }
            }
            for (SegPermiso permiso : permisos.getSource()) {
                if (getSegRolPermisoServicioRemote().existePermisoRol(objRolActual.getIdRol(), permiso.getIdPermiso())) {
                    //elimina
                    SegRolPermiso pr = getSegRolPermisoServicioRemote().buscarPorPermisoRol(objRolActual.getIdRol(), permiso.getIdPermiso());
                    pr.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
                    pr.setCodRol(objRolActual);
                    pr.setCodPermiso(permiso);
                    pr.setEstadoLogico(false);
                    getSegRolPermisoServicioRemote().editarRolPermiso(pr);

                }
            }
            listarPermisosGuardados();
            addSuccessMessage("Roles del Usuario Actualizados");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarPermisosGuardados() {
        try {
            permisosAsignados = getSegPermisoServicioRemote().listarPermisosDeRolAsignados(objRolActual.getIdRol());
            System.out.println("rolesAsignados=" + permisosAsignados.size());
            if (permisosAsignados.isEmpty()) {
                if (aplicacionSelected != null && aplicacionSelected.getIdApl() != null) {
                    permisosNoAsignados = getSegPermisoServicioRemote().listarPermisosActivosPorAplicacion(aplicacionSelected.getIdApl());
                } else {
                    permisosNoAsignados = getSegPermisoServicioRemote().listarPermisosActivos();
                }
            } else {
                if (aplicacionSelected != null && aplicacionSelected.getIdApl() != null) {
                    permisosNoAsignados = getSegPermisoServicioRemote().listarPermisosDeRolNoAsignadosPorAplicacion(permisosAsignados, aplicacionSelected.getIdApl());
                } else {
                    permisosNoAsignados = getSegPermisoServicioRemote().listarPermisosDeRolNoAsignados(permisosAsignados);
                }
            }
            System.out.println("rolesNoAsignados=" + permisosNoAsignados.size());
            permisos = new DualListModel<SegPermiso>(permisosNoAsignados, permisosAsignados);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarRol() {
        try {
            if (habilitaEdicion == false) {
                // objRolActual.setFechaReg(fechaReg);
                objRolActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objRolActual.setEstadoLogico(true);
                getSegRolServicioRemote().crearRol(objRolActual);
                addSuccessMessage("Registro Guardado");
                listarRolTodos();
                objRolActual = new SegRol();
            } else {
                objRolActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getSegRolServicioRemote().editarRol(objRolActual);
                addSuccessMessage("Registro Actualizado");
                listarRolTodos();
                objRolActual = new SegRol();
                habilitaEdicion = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objRolActual = new SegRol();
            habilitaEdicion = false;
            aplicacionSelected = new SegAplicacion();
            permisosAsignados = new ArrayList<SegPermiso>();
            permisosNoAsignados = new ArrayList<SegPermiso>();
            permisos = new DualListModel<SegPermiso>(permisosNoAsignados, permisosAsignados);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarRolCampos(SegRol rol) {
        try {
            objRolActual = new SegRol();
            objRolActual = rol;
            habilitaEdicion = true;
            aplicacionSelected = new SegAplicacion();
            listarPermisosGuardados();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void confirmaEliminar(SegRol rol) {
        try {
            if (getSegRolServicioRemote().tieneCargasPendientes(rol.getIdRol()) > 0) {
                addWarningMessage("No puede eliminar el rol "+rol.getNombre()+", por que tiene cargas pendientes.");
                return;
            }
            rol.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            rol.setEstadoLogico(false);
            getSegRolServicioRemote().editarRol(rol);
            addSuccessMessage("Registro Eliminado");
            listarRolTodos();
            objRolActual = new SegRol();
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarRolTodos() throws Exception {
        listarRoles = getSegRolServicioRemote().listarRolesActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public SegRol getObjRolActual() {
        return objRolActual;
    }

    public void setObjRolActual(SegRol objRolActual) {
        this.objRolActual = objRolActual;
    }

    public List<SegRol> getListarRoles() {
        return listarRoles;
    }

    public void setListarRoles(List<SegRol> listarRoles) {
        this.listarRoles = listarRoles;
    }

    public DualListModel<SegPermiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(DualListModel<SegPermiso> permisos) {
        this.permisos = permisos;
    }

    public SegAplicacion getAplicacionActual() {
        return aplicacionActual;
    }

    public void setAplicacionActual(SegAplicacion aplicacionActual) {
        this.aplicacionActual = aplicacionActual;
    }

    public List<SegAplicacion> getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(List<SegAplicacion> aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public List<SegPagina> getListarPaginas() {
        return listarPaginas;
    }

    public void setListarPaginas(List<SegPagina> listarPaginas) {
        this.listarPaginas = listarPaginas;
    }

    public List<MetCatalogo> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<MetCatalogo> acciones) {
        this.acciones = acciones;
    }

    public SegPagina getObjPaginaActual() {
        return objPaginaActual;
    }

    public void setObjPaginaActual(SegPagina objPaginaActual) {
        this.objPaginaActual = objPaginaActual;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public Map<Integer, List<SegPermiso>> getMapaPermisos() {
        return mapaPermisos;
    }

    public void setMapaPermisos(Map<Integer, List<SegPermiso>> mapaPermisos) {
        this.mapaPermisos = mapaPermisos;
    }

    public SegAplicacion getAplicacionSelected() {
        return aplicacionSelected;
    }

    public void setAplicacionSelected(SegAplicacion aplicacionSelected) {
        this.aplicacionSelected = aplicacionSelected;
    }

    public List<MetCatalogo> getSelectedAccs() {
        return selectedAccs;
    }

    public void setSelectedAccs(List<MetCatalogo> selectedAccs) {
        this.selectedAccs = selectedAccs;
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
    //</editor-fold>

}
