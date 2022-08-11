/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import ec.gob.inec.ejb.utils.ReflexionEntidad;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
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
import org.apache.commons.lang.SerializationUtils;
import org.primefaces.model.DualListModel;

/**
 * Controlador que permite administrar información de los usuarios.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionUsuarioControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionUsuarioControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private SegUsuario objUsuarioActual = new SegUsuario();
    private List<Object[]> listarUsuarioes;
    private List<MetCatalogo> estados;
    private boolean habilitaEdicion;
    private Date fechaReg;
    private DualListModel<SegRol> roles;
    private List<SegRol> rolesAsignados;
    private List<SegRol> rolesNoAsignados;
    private AdmPersonal personaActual;
    private Map<Integer, String> mapaCargos;
    private Map<Integer, String> mapaNombrePersona;
    private List<AdmPersonal> listaPersonal;
    private Map<Integer, String> mapaCorreoInstitucionalPersona;
    private List<AdmColumna> columnasAEcnriptar;
    private SegUsuario objUsuarioAnterior;
    private Map<String, Boolean> permisosBoton;
    private MetCatalogo catalogoUsuarioInactivo;
    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">

    @PostConstruct
    public void inicializar() {
        try {
            habilitaEdicion = false;
            objUsuarioActual = new SegUsuario();
            objUsuarioAnterior = new SegUsuario();
            personaActual = new AdmPersonal();
            fechaReg = new Date();
            rolesAsignados = new ArrayList<SegRol>();
            rolesNoAsignados = new ArrayList<SegRol>();
            roles = new DualListModel<SegRol>(rolesNoAsignados, rolesAsignados);
            permisosBoton = vistaControlador.getPermisos();
            listarUsuarioTodos();

            columnasAEcnriptar = getAdmColumnaServicioRemote().consultarColumnasAEncriptarPorTabla("seguridad.seg_usuario");
            catalogoUsuarioInactivo = getMetCatalogoServicioRemote().buscarCatalogoXAlias("USU_INA");
            estados = getMetCatalogoServicioRemote().listarCatalogoXAlias("EST_USU");

            listaPersonal = getAdmPersonalServicioRemote().listarTodo();
            mapaNombrePersona = new HashMap<>();
            mapaCorreoInstitucionalPersona = new HashMap<>();
            mapaCargos = new HashMap<>();
            for (AdmPersonal p : listaPersonal) {
                String nombreCompleto = p.getPrimerNombre() + " " + (p.getSegundoNombre() == null ? "" : p.getSegundoNombre()) + " " + p.getPrimerApellido() + " " + (p.getSegundoApellido() == null ? "" : p.getSegundoApellido());
                mapaNombrePersona.put(p.getIdPersonal(), nombreCompleto);
                mapaCorreoInstitucionalPersona.put(p.getIdPersonal(), p.getCorreoInstitucional());
                mapaCargos.put(p.getIdPersonal(), p.getCodCargo() == null ? "" : p.getCodCargo().getNombre());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionUsuarioControlador() {
    }
    //</editor-fold> 
    //<editor-fold desc="Metodos" defaultstate="collapsed">

    /*  public void cargaPicLstParametroApli() {
        System.out.println("cargaPicLstParametroApli()");
        try {
            rolesAsignados = getSegRolServicioRemote().listarRolesDeUsuarioAsignados(objUsuarioActual.getIdUsuario());
            System.out.println("rolesAsignados=" + rolesAsignados.size());
            if (rolesAsignados.isEmpty()) {
                if (aplicacionActual.getIdApl() != null) {
                    rolesNoAsignados = getSegRolServicioRemote().listarRolesPorAplicacion(aplicacionActual.getIdApl());
                } else {
                    rolesNoAsignados = getSegRolServicioRemote().listarRolesActivos();
                }
            } else {
                rolesNoAsignados = getSegRolServicioRemote().listarRolesDeUsuarioNoAsignadosPorAplicacion(rolesAsignados, aplicacionActual.getIdApl());
            }
            System.out.println("rolesNoAsignados=" + rolesNoAsignados.size());
            roles = new DualListModel<SegRol>(rolesNoAsignados, rolesAsignados);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }*/
    public void guardarRolesAsignadosDeUsuario() {
        try {
            for (SegRol rol : roles.getTarget()) {
                if (getSegRolUsuarioServicioRemote().existeUsuarioRol(objUsuarioActual.getIdUsuario(), rol.getIdRol())) {
                } else {
                    //grabar   
                    rol.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                    SegRolUsuario ur = new SegRolUsuario();
                    ur.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                    ur.setCodUsuario(objUsuarioActual);
                    ur.setCodRol(rol);
                    ur.setEstadoLogico(true);
                    //getSegUsuarioServicioRemote().editarUsuario(objUsuarioActual);
                    getSegRolUsuarioServicioRemote().crearRolUsuario(ur);
                    listarRolesGuardados();
                }
            }
            for (SegRol rol : roles.getSource()) {
                if (getSegRolUsuarioServicioRemote().existeUsuarioRol(objUsuarioActual.getIdUsuario(), rol.getIdRol())) {
                    if (getSegUsuarioServicioRemote().tieneCargasPendientes(objUsuarioActual.getIdUsuario(), rol.getIdRol()) > 0) {
                        SegRol segRol = getSegRolServicioRemote().buscarXId(rol.getIdRol());
                        addWarningMessage("No puede eliminar el rol " + segRol.getNombre() + " del usuario, por que tiene cargas pendientes.");
                        continue;
                    }
                    //elimina
                    SegRolUsuario ur = getSegRolUsuarioServicioRemote().buscarPorUsuarioRol(objUsuarioActual.getIdUsuario(), rol.getIdRol());
                    ur.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
                    ur.setEstadoLogico(false);
                    getSegRolUsuarioServicioRemote().editarRolUsuario(ur);
                    listarRolesGuardados();
                }
            }
            addSuccessMessage("Roles del Usuario Actualizados");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarRolesGuardados() {
        try {
            rolesAsignados = getSegRolServicioRemote().listarRolesDeUsuarioAsignados(objUsuarioActual.getIdUsuario());
            System.out.println("rolesAsignados=" + rolesAsignados.size());
            if (rolesAsignados.isEmpty()) {
                rolesNoAsignados = getSegRolServicioRemote().listarRolesActivos();
            } else {
                rolesNoAsignados = getSegRolServicioRemote().listarRolesDeUsuarioNoAsignados(rolesAsignados);
            }
            System.out.println("rolesNoAsignados=" + rolesNoAsignados.size());
            roles = new DualListModel<SegRol>(rolesNoAsignados, rolesAsignados);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarUsuario() {
        try {
            if (!objUsuarioActual.getNombre().equals(objUsuarioAnterior.getNombre())) {
                // if (listarUsuarioes.stream().filter(p -> p.getNombre().toLowerCase().equals(objUsuarioActual.getNombre().toLowerCase())).count() > 0) {
                if (listarUsuarioes.stream().filter(p -> p[1].toString().toLowerCase().equals(objUsuarioActual.getNombre().toLowerCase())).count() > 0) {
                    addWarningMessage("Ya existe ese usuario.");
                    return;
                }
            }
            if (objUsuarioActual.getFechahoraIni() != null && objUsuarioActual.getFechahoraFin() != null) {
                if (objUsuarioActual.getFechahoraIni().after(objUsuarioActual.getFechahoraFin())) {
                    addWarningMessage("Fecha vigencia inicio no puede ser mayor a Fecha vigencia fin");
                    return;
                }
            }          
            if (objUsuarioActual.getCodEstadoUsuario().getIdCatalogo().equals(catalogoUsuarioInactivo.getIdCatalogo())) {
                if (getSegUsuarioServicioRemote().tieneCargasPendientes(objUsuarioActual.getIdUsuario()) > 0) {
                    addWarningMessage("No puede cambiar a inactivo el usuario " + objUsuarioActual.getNombre() + ", por que tiene cargas pendientes.");
                    return;
                }
            }
            String parametroAES = getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
            objUsuarioActual.setCodPersonal(personaActual);
            if (habilitaEdicion == false) {
                ReflexionEntidad.encriptaCampos(objUsuarioActual, columnasAEcnriptar, parametroAES);
                objUsuarioActual.setFechahoraCreacion(fechaReg);
                objUsuarioActual.setEstadoLogico(true);
                objUsuarioActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                getSegUsuarioServicioRemote().crearUsuario(objUsuarioActual);
                addSuccessMessage("Registro Guardado");
                listarUsuarioTodos();
                refrescaNuevo();
            } else {
                ReflexionEntidad.encriptaCampos(objUsuarioAnterior, objUsuarioActual, columnasAEcnriptar, parametroAES);
                objUsuarioActual.setFechahoraEdicion(fechaReg);
                objUsuarioActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                getSegUsuarioServicioRemote().editarUsuario(objUsuarioActual);
                addSuccessMessage("Registro Actualizado");
                listarUsuarioTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objUsuarioActual = new SegUsuario();
            objUsuarioAnterior = new SegUsuario();
            personaActual = new AdmPersonal();
            rolesAsignados = new ArrayList<SegRol>();
            rolesNoAsignados = new ArrayList<SegRol>();
            roles = new DualListModel<SegRol>(rolesAsignados, rolesAsignados);
            habilitaEdicion = false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
//public void recuperarUsuarioCampos(SegUsuario usuario) {

    public void recuperarUsuarioCampos(Object[] parmUsuario) {
        try {
            SegUsuario usuario = getSegUsuarioServicioRemote().buscarxID(Integer.parseInt(parmUsuario[0].toString()));
            objUsuarioActual = new SegUsuario();
            objUsuarioActual = (SegUsuario) SerializationUtils.clone(usuario);
            for (AdmPersonal p : listaPersonal) {
                if (p.getIdPersonal().equals(Integer.parseInt(parmUsuario[2].toString()))) {
                    personaActual = p;
                    break;
                }
            }
            objUsuarioAnterior = (SegUsuario) SerializationUtils.clone(usuario);
            habilitaEdicion = true;
            listarRolesGuardados();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }
    //public void confirmaEliminar(SegUsuario usuario) {

    public void confirmaEliminar(Object[] parmUsuario) {
        try {
            if (getSegUsuarioServicioRemote().tieneCargasPendientes(Integer.parseInt(parmUsuario[0].toString())) > 0) {
                addWarningMessage("No puede eliminar el usuario " + parmUsuario[1].toString() + ", por que tiene cargas pendientes.");
                return;
            }
            SegUsuario usuario = getSegUsuarioServicioRemote().buscarxID(Integer.parseInt(parmUsuario[0].toString()));
            usuario.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            usuario.setEstadoLogico(false);
            getSegUsuarioServicioRemote().editarUsuario(usuario);
            addSuccessMessage("Registro Eliminado");
            listarUsuarioTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarUsuarioTodos() throws Exception {
        listarUsuarioes = getSegUsuarioServicioRemote().listarSoloActivos();//.listarTodosActivos();       
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public SegUsuario getObjUsuarioActual() {
        return objUsuarioActual;
    }

    public void setObjUsuarioActual(SegUsuario objUsuarioActual) {
        this.objUsuarioActual = objUsuarioActual;
    }

    public List<Object[]> getListarUsuarioes() {
        return listarUsuarioes;
    }

    public void setListarUsuarioes(List<Object[]> listarUsuarioes) {
        this.listarUsuarioes = listarUsuarioes;
    }

    public List<MetCatalogo> getEstados() {
        return estados;
    }

    public void setEstados(List<MetCatalogo> estados) {
        this.estados = estados;
    }

    public DualListModel<SegRol> getRoles() {
        return roles;
    }

    public void setRoles(DualListModel<SegRol> roles) {
        this.roles = roles;
    }

    public AdmPersonal getPersonaActual() {
        return personaActual;
    }

    public void setPersonaActual(AdmPersonal personaActual) {
        this.personaActual = personaActual;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    /*  public UsuarioControlador getUsuarioControlador() {
    return usuarioControlador;
    }
    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
    this.usuarioControlador = usuarioControlador;
    }*/
    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public Map<Integer, String> getMapaCargos() {
        return mapaCargos;
    }

    public void setMapaCargos(Map<Integer, String> mapaCargos) {
        this.mapaCargos = mapaCargos;
    }

    public Map<Integer, String> getMapaNombrePersona() {
        return mapaNombrePersona;
    }

    public void setMapaNombrePersona(Map<Integer, String> mapaNombrePersona) {
        this.mapaNombrePersona = mapaNombrePersona;
    }

    public List<AdmPersonal> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(List<AdmPersonal> listaPersonal) {
        this.listaPersonal = listaPersonal;
    }

    public Map<Integer, String> getMapaCorreoInstitucionalPersona() {
        return mapaCorreoInstitucionalPersona;
    }

    public void setMapaCorreoInstitucionalPersona(Map<Integer, String> mapaCorreoInstitucionalPersona) {
        this.mapaCorreoInstitucionalPersona = mapaCorreoInstitucionalPersona;
    }

    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }

    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
    }

    //</editor-fold>       
}
