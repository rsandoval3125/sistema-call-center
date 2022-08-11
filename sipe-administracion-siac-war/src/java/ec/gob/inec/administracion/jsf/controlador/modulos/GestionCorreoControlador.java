/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.jsf.controlador.modulos;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoCab;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet;
import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.VistaControlador;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang.SerializationUtils;

/**
 * Controlador que permite admiistrar la información de correos a enviar.
 *
 * @author mchasiguasin
 */
@ManagedBean
@ViewScoped
public class GestionCorreoControlador extends BaseControlador {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private static final Logger LOGGER = Logger.getLogger(GestionCorreoControlador.class.getName());
    @ManagedProperty("#{vistaControlador}")
    private VistaControlador vistaControlador;
    private AdmCorreoCab objCorreoActual;
    private List<AdmCorreoCab> listarCorreoes;
    private boolean habilitaEdicion;

    private AdmCorreoDet admCorreoDetActual;
    private List<AdmCorreoDet> listarCorreosDet;

    private MetCatalogo selectedTipoPrioridad;
    private List<MetCatalogo> tipoPrioridades;

    private Map<String, String> posicionParametros;

    private AdmCorreoDestinatario admCorreoDestinatarioActual;
    private List<AdmCorreoDestinatario> listarCorreosDestinario;

    private List<MetCatalogo> tiposDestinatarios;
    private List<String> selectedTiposDestinatarios;

    private AdmPersonal personaActual;
    private Map<Integer, MetCatalogo> mapTiposDestinatarios;
    private Map<Integer, AdmPersonal> mapPersonal;
    private Map<String, Boolean> permisosBoton;
    private AdmCorreoCab objCorreoAnterior;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    @PostConstruct
    public void inicializar() {
        try {
            /*  Map<String, String> params = new LinkedHashMap<>();
            params.put("APLICACION", "Sistema de Gestión Muestral");
            params.put("CONT_APLICACION", "Sistema de Gestión Muestral");
            params.put("CONT_NOVEDADES", "Reporte de novedades N°: " + 0010);
            params.put("CONT_ESTADO", "Estado: " + "CREADO");
            params.put("CONT_RESPONSABLE", "Responsable: " + "MARCEL CHASIGUASIN Y");
            String formatoNormal = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(new Date());
            params.put("CONT_FECHA_GESTION", "Fecha de gestión: " + formatoNormal);
            getAdmCorreoDestinatarioServicioRemote().enviarCorreo("PUB_RADIO", params);*/
            habilitaEdicion = false;
            admCorreoDetActual = new AdmCorreoDet();
            objCorreoActual = new AdmCorreoCab();
            objCorreoAnterior = new AdmCorreoCab();
            listarCorreosDestinario = new ArrayList<>();
            personaActual = new AdmPersonal();
            permisosBoton = vistaControlador.getPermisos();
            tipoPrioridades = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIPO_PRIORIDAD");
            tiposDestinatarios = getMetCatalogoServicioRemote().listarCatalogoXAlias("TIPO_DESTINATARIO");
            mapTiposDestinatarios = new HashMap<>();
            for (MetCatalogo mc : tiposDestinatarios) {
                mapTiposDestinatarios.put(mc.getIdCatalogo(), mc);
            }
            List<AdmPersonal> tmpListadoPersonal = getAdmPersonalServicioRemote().listarTodosActivos();
            mapPersonal = new HashMap<>();
            for (AdmPersonal p : tmpListadoPersonal) {
                mapPersonal.put(p.getIdPersonal(), p);
            }
            posicionParametros = new HashMap<>();
            posicionParametros.put("Superior", "S");
            posicionParametros.put("Inferior", "I");
            listarCorreoTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionCorreoControlador() {
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">  
    public void refrescaNuevoDestinatario() {
        System.out.println("refrescaNuevoDestinatario");
        admCorreoDestinatarioActual = new AdmCorreoDestinatario();
        selectedTiposDestinatarios = new ArrayList<>();
        personaActual = new AdmPersonal();
    }

    public void agregarDestinatario() {
        for (String tipoD : selectedTiposDestinatarios) {
            if (!existeDestinatarioConTipo(personaActual, Integer.valueOf(tipoD))) {
                AdmCorreoDestinatario tempDesctinatario = new AdmCorreoDestinatario();
                tempDesctinatario.setIdCorreoDestinatario(0);
                tempDesctinatario.setEstadoLogico(true);
                tempDesctinatario.setEstadoDestinatario(true);
                tempDesctinatario.setCodPersonal(mapPersonal.get(personaActual.getIdPersonal()));
                tempDesctinatario.setCodTipoDestinatario(mapTiposDestinatarios.get(Integer.valueOf(tipoD)));
                listarCorreosDestinario.add(tempDesctinatario);
            }
        }
        //Borrar los que no selecciona
        List<MetCatalogo> tmpTiposDestinaratioBorrar = new ArrayList<>();
        for (MetCatalogo mc : tiposDestinatarios) {
            boolean encontrado = false;
            for (String tipoD : selectedTiposDestinatarios) {
                if (mc.getIdCatalogo().equals(Integer.valueOf(tipoD))) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                tmpTiposDestinaratioBorrar.add(mc);
            }
        }
        for (MetCatalogo mc : tmpTiposDestinaratioBorrar) {
            listarCorreosDestinario.removeIf(i -> (i.getCodPersonal().getIdPersonal().equals(personaActual.getIdPersonal()) && i.getCodTipoDestinatario().getIdCatalogo().equals(mc.getIdCatalogo())));
        }
    }

    public void cargarTipoDestinatarios() {
        List<AdmCorreoDestinatario> tmpCorreoDestinatarios = new ArrayList<>();
        for (AdmCorreoDestinatario cd : listarCorreosDestinario) {
            if (cd.getCodPersonal().getIdPersonal().equals(personaActual.getIdPersonal())) {
                tmpCorreoDestinatarios.add(cd);
            }
        }
        selectedTiposDestinatarios = new ArrayList<>();
        for (AdmCorreoDestinatario cd : tmpCorreoDestinatarios) {
            selectedTiposDestinatarios.add(cd.getCodTipoDestinatario().getIdCatalogo().toString());
        }
    }

    public void confirmaEliminarDestinatario(AdmCorreoDestinatario admCorreoDestinatario) {
        listarCorreosDestinario.removeIf(i -> (i.getCodPersonal().getIdPersonal().equals(admCorreoDestinatario.getCodPersonal().getIdPersonal()) && i.getCodTipoDestinatario().getIdCatalogo().equals(admCorreoDestinatario.getCodTipoDestinatario().getIdCatalogo())));
        String nombreCompleto = admCorreoDestinatario.getCodPersonal().getPrimerNombre() + ((admCorreoDestinatario.getCodPersonal().getSegundoNombre() == null) ? "" : admCorreoDestinatario.getCodPersonal().getSegundoNombre()) + " " + admCorreoDestinatario.getCodPersonal().getPrimerApellido() + ((admCorreoDestinatario.getCodPersonal().getSegundoApellido() == null) ? "" : admCorreoDestinatario.getCodPersonal().getSegundoApellido());
        addSuccessMessage("El destinatario " + nombreCompleto + " fue eliminado temporalmente de la lista, para eliminarlo definitivamente presionar el botón guardar");
    }

    private boolean existeDestinatarioConTipo(AdmPersonal admPersonalActual, Integer tipoDestinatario) {
        for (AdmCorreoDestinatario cd : listarCorreosDestinario) {
            if (cd.getCodPersonal().getIdPersonal().equals(admPersonalActual.getIdPersonal()) && cd.getCodTipoDestinatario().getIdCatalogo().equals(tipoDestinatario)) {
                return true;
            }
        }
        return false;
    }

    public void guardarCorreo() {
        try {
            /* Map<String, String> params = new LinkedHashMap<>();
            params.put("APLICACION", "Sistema de Gestión Muestral");
            params.put("CONT_APLICACION", "Sistema de Gestión Muestral");
            params.put("CONT_NOVEDADES", "Reporte de novedades N°: " + 0010);
            params.put("CONT_ESTADO", "Estado: " + "CREADO");
            params.put("CONT_RESPONSABLE", "Responsable: " + "MARCEL CHASIGUASIN Y");
            String formatoNormal = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(new Date());
            params.put("CONT_FECHA_GESTION", "Fecha de gestión: " + formatoNormal);
            getAdmCorreoDestinatarioServicioRemote().enviarCorreo("SISGEM_NOTIFICA_NOVEDAD_MYC", params);*/
            if (listarCorreosDestinario.size() < 1) {
                addWarningMessage("Agregar información de destinatarios.");
                return;
            }
            if (!objCorreoActual.getAlias().equals(objCorreoAnterior.getAlias())) {
                if (listarCorreoes.stream().filter(p -> Objects.nonNull(p.getAlias())).filter(p -> p.getAlias().toLowerCase().equals(objCorreoActual.getAlias().toLowerCase())).count() > 0) {
                    addWarningMessage("Ya existe un correo con el mismo alias.");
                    return;
                }
            }
            if (habilitaEdicion == false) {
                // objCorreoActual.setFechaReg(fechaReg);
                objCorreoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                objCorreoActual.setFechahoraRegistra(new Date());
                objCorreoActual.setEstadoLogico(true);
                for (AdmCorreoDestinatario admCorreoDestinatario : listarCorreosDestinario) {
                    admCorreoDestinatario.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                    admCorreoDestinatario.setEstadoLogico(true);
                }
                admCorreoDetActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_INSRT"));
                admCorreoDetActual.setEstadoLogico(true);
                admCorreoDetActual.setCodCorreoCab(objCorreoActual);
                getAdmCorreoCabServicioRemote().crearCorreo(objCorreoActual, admCorreoDetActual, listarCorreosDestinario);
                addSuccessMessage("Registro Guardado");
                listarCorreoTodos();
                refrescaNuevo();
            } else {
                objCorreoActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                for (AdmCorreoDestinatario admCorreoDestinatario : listarCorreosDestinario) {
                    admCorreoDestinatario.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                    admCorreoDestinatario.setEstadoLogico(true);
                }
                admCorreoDetActual.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_MODIF"));
                admCorreoDetActual.setEstadoLogico(true);
                getAdmCorreoCabServicioRemote().editarCorreo(objCorreoActual, admCorreoDetActual, listarCorreosDestinario);
                addSuccessMessage("Registro Actualizado");
                listarCorreoTodos();
                refrescaNuevo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede guardar el registro");
        }
    }

    public void refrescaNuevo() {
        try {
            objCorreoActual = new AdmCorreoCab();
            objCorreoAnterior = new AdmCorreoCab();
            listarCorreosDestinario = new ArrayList<>();
            admCorreoDetActual = new AdmCorreoDet();
            habilitaEdicion = false;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void recuperarCorreoCampos(AdmCorreoCab correoCab) {
        try {
            objCorreoActual = new AdmCorreoCab();
            objCorreoActual = (AdmCorreoCab) SerializationUtils.clone(correoCab);
            objCorreoAnterior = (AdmCorreoCab) SerializationUtils.clone(correoCab);
            listarCorreosDet = getAdmCorreoDetServicioRemote().consultarPorIdCorreoCab(correoCab.getIdCorreoCab());
            admCorreoDetActual = (listarCorreosDet.size() > 0) ? listarCorreosDet.get(0) : new AdmCorreoDet();
            listarCorreosDestinario = getAdmCorreoDestinatarioServicioRemote().consultarPorIdCorreoCab(correoCab.getIdCorreoCab());

            habilitaEdicion = true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void confirmaEliminar(AdmCorreoCab admCorreoCab) {
        try {
            admCorreoCab.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
            admCorreoCab.setEstadoLogico(false);
            getAdmCorreoCabServicioRemote().editarCorreoCab(admCorreoCab);
            List<AdmCorreoDestinatario> tmpListaCorreosDestinario = getAdmCorreoDestinatarioServicioRemote().consultarPorIdCorreoCab(admCorreoCab.getIdCorreoCab());
            for (AdmCorreoDestinatario cd : tmpListaCorreosDestinario) {
                cd.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
                cd.setEstadoLogico(false);
                getAdmCorreoDestinatarioServicioRemote().editarCorreoDestinatario(cd);
            }
            List<AdmCorreoDet> tmpListaCorreosDet = getAdmCorreoDetServicioRemote().consultarPorIdCorreoCab(admCorreoCab.getIdCorreoCab());
            for (AdmCorreoDet cd : tmpListaCorreosDet) {
                cd.setCodAuditoriaCab(vistaControlador.getCodAuditoria("PAG_ELIM"));
                cd.setEstadoLogico(false);
                getAdmCorreoDetServicioRemote().editarCorreoDet(cd);
            }
            addSuccessMessage("Registro Eliminado");
            listarCorreoTodos();
            refrescaNuevo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el registro");
        }
    }

    public void listarCorreoTodos() throws Exception {
        listarCorreoes = getAdmCorreoCabServicioRemote().listarTodosActivos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public boolean isHabilitaEdicion() {
        return habilitaEdicion;
    }

    public void setHabilitaEdicion(boolean habilitaEdicion) {
        this.habilitaEdicion = habilitaEdicion;
    }

    public AdmCorreoCab getObjCorreoActual() {
        return objCorreoActual;
    }

    public void setObjCorreoActual(AdmCorreoCab objCorreoActual) {
        this.objCorreoActual = objCorreoActual;
    }

    public List<AdmCorreoCab> getListarCorreoes() {
        return listarCorreoes;
    }

    public void setListarCorreoes(List<AdmCorreoCab> listarCorreoes) {
        this.listarCorreoes = listarCorreoes;
    }

    public VistaControlador getVistaControlador() {
        return vistaControlador;
    }

    public void setVistaControlador(VistaControlador vistaControlador) {
        this.vistaControlador = vistaControlador;
    }

    public AdmCorreoDet getAdmCorreoDetActual() {
        return admCorreoDetActual;
    }

    public void setAdmCorreoDetActual(AdmCorreoDet admCorreoDetActual) {
        this.admCorreoDetActual = admCorreoDetActual;
    }

    public List<AdmCorreoDet> getListarCorreosDet() {
        return listarCorreosDet;
    }

    public void setListarCorreosDet(List<AdmCorreoDet> listarCorreosDet) {
        this.listarCorreosDet = listarCorreosDet;
    }

    public MetCatalogo getSelectedTipoPrioridad() {
        return selectedTipoPrioridad;
    }

    public void setSelectedTipoPrioridad(MetCatalogo selectedTipoPrioridad) {
        this.selectedTipoPrioridad = selectedTipoPrioridad;
    }

    public List<MetCatalogo> getTipoPrioridades() {
        return tipoPrioridades;
    }

    public void setTipoPrioridades(List<MetCatalogo> tipoPrioridades) {
        this.tipoPrioridades = tipoPrioridades;
    }

    public Map<String, String> getPosicionParametros() {
        return posicionParametros;
    }

    public void setPosicionParametros(Map<String, String> posicionParametros) {
        this.posicionParametros = posicionParametros;
    }

    public AdmCorreoDestinatario getAdmCorreoDestinatarioActual() {
        return admCorreoDestinatarioActual;
    }

    public void setAdmCorreoDestinatarioActual(AdmCorreoDestinatario admCorreoDestinatarioActual) {
        this.admCorreoDestinatarioActual = admCorreoDestinatarioActual;
    }

    public List<AdmCorreoDestinatario> getListarCorreosDestinario() {
        return listarCorreosDestinario;
    }

    public void setListarCorreosDestinario(List<AdmCorreoDestinatario> listarCorreosDestinario) {
        this.listarCorreosDestinario = listarCorreosDestinario;
    }

    public List<MetCatalogo> getTiposDestinatarios() {
        return tiposDestinatarios;
    }

    public void setTiposDestinatarios(List<MetCatalogo> tiposDestinatarios) {
        this.tiposDestinatarios = tiposDestinatarios;
    }

    public List<String> getSelectedTiposDestinatarios() {
        return selectedTiposDestinatarios;
    }

    public void setSelectedTiposDestinatarios(List<String> selectedTiposDestinatarios) {
        this.selectedTiposDestinatarios = selectedTiposDestinatarios;
    }

    public AdmPersonal getPersonaActual() {
        return personaActual;
    }

    public void setPersonaActual(AdmPersonal personaActual) {
        this.personaActual = personaActual;
    }

    public Map<Integer, AdmPersonal> getMapPersonal() {
        return mapPersonal;
    }

    public void setMapPersonal(Map<Integer, AdmPersonal> mapPersonal) {
        this.mapPersonal = mapPersonal;
    }

    public Map<String, Boolean> getPermisosBoton() {
        return permisosBoton;
    }

    public void setPermisosBoton(Map<String, Boolean> permisosBoton) {
        this.permisosBoton = permisosBoton;
    }
    //</editor-fold>
}
