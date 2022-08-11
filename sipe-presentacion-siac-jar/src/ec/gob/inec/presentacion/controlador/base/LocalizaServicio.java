/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.base;

import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.administracion.ejb.servicios.AdmParametroGlobalServicioRemote;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase que busca un EJB remoto.
 *
 * @author Marcel Chasiguasin
 * @version 1.0
 */
@ManagedBean
@ApplicationScoped
public class LocalizaServicio {

    private static final Logger LOGGER = Logger.getLogger(LocalizaServicio.class.getName());

    private Map<String, Object> services = new HashMap<String, Object>();

    private Context context;

    @ManagedProperty("#{propiedadesControlador}")
    private PropiedadesControlador propiedadesControlador;
    InitialContext contextAdministracion = null;
    private AdmParametroGlobalServicioRemote admParametroGlobalServicioRemote;
    private String numSer;
    private String nombreModulo;

    public LocalizaServicio() {
        super();
    }

    @PostConstruct
    public void initialize() {
        LOGGER.info("initialize()");
        try {
            numSer = propiedadesControlador.getPropertieValue("ipApppFin");
            if (contextAdministracion == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
                jdniPro3.put(Context.PROVIDER_URL, "http-remoting://" + propiedadesControlador.getPropertieValue("ipAPP"));
                jdniPro3.put(Context.SECURITY_PRINCIPAL, propiedadesControlador.getPropertieValue("userAPP"));
                jdniPro3.put(Context.SECURITY_CREDENTIALS, propiedadesControlador.getPropertieValue("claveApp"));
                jdniPro3.put("jboss.naming.client.ejb.context", true);
                jdniPro3.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false);
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", true);
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", false);
                contextAdministracion = new InitialContext(jdniPro3);
            }
            admParametroGlobalServicioRemote = (AdmParametroGlobalServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmParametroGlobalServicio!" + AdmParametroGlobalServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca un servicio EJB remoto.<br/>
     * <br/>
     * Ejemplo:<br/>
     * {@code SegAplicacionServicioRemote segAplicacionServicioRemote=getLocalizaServicio().lookupService(SegAplicacionServicioRemote.class);}
     *
     * @param <S> Interfaz remota.
     * @param clazz Clase remota.
     * @return Interfaz remota.
     */
    @SuppressWarnings("unchecked")
    public <S> S lookupService(Class<S> clazz) {
        LOGGER.info(String.format("lookupService( %s )", clazz.getSimpleName()));
        if (services.get(clazz.getName()) == null) {
            S service = null;
            try {
                context = getInitialContext(clazz.getSimpleName());
                if (context == null) {
                    LOGGER.log(Level.SEVERE, String.format("No existe una configuración para obtener el EJB %s", clazz.getName()));
                    return null;
                }
                String jndi = getLookupName(clazz);
                LOGGER.log(Level.INFO, String.format("Busancando: %s", jndi));
                service = (S) context.lookup(jndi);
                services.put(clazz.getName(), service);
                return service;

            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "No pudo obtener una referencia del EJB.", ex);
            } finally {
                if (context != null) {
                    try {
                        context.close();
                    } catch (NamingException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
                }
            }
        }
        LOGGER.log(Level.INFO, String.format("Servicio EJB recuperado de cache para: %s", clazz.getName()));
        return (S) services.get(clazz.getName());
    }

    private Context getInitialContext(String interfazRemota) {
        try {
//            List<AdmParametroGlobal> admParametroGlobales = admParametroGlobalServicioRemote.buscarXNumeroServidor(numSer);
            AdmParametroGlobal admParametroGlobalAdministracion = admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Administracion");
//            admParametroGlobales.add(admParametroGlobalAdministracion);
//            List<AdmParametroGlobal> sistema = admParametroGlobales.stream().filter(pg -> pg.getCondicion() != null && pg.getCondicion().toLowerCase().contains(interfazRemota.toLowerCase())).collect(Collectors.toList());
            String nombreSistema = "";
//            if (sistema.size() > 0) {
//                nombreSistema = sistema.get(0).getSistema();
//                LOGGER.log(Level.INFO, String.format("Nombre sistema: %s", nombreSistema));
//                nombreModulo = nombreSistema.toLowerCase().replaceAll("_", "-") + "-ejb";
//                if (nombreSistema.equals("SIPE_Administracion")) {
//                    return contextAdministracion;
//                }
//            } else {
//                return null;
//            }
            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());

            jndiProperties.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_" + nombreSistema + numSer).getValor());
            jndiProperties.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_" + nombreSistema + numSer).getValor());
            jndiProperties.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_" + nombreSistema + numSer).getValor());

            jndiProperties.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
            jndiProperties.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
            jndiProperties.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
            jndiProperties.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
            jndiProperties.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
            return new InitialContext(jndiProperties);
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return context;
    }

    private <S> String getLookupName(Class<S> clazz) {
        return nombreModulo + "/" + clazz.getSimpleName().replaceAll("Remote", "") + "!" + clazz.getName();
    }

    public PropiedadesControlador getPropiedadesControlador() {
        return propiedadesControlador;
    }

    public void setPropiedadesControlador(PropiedadesControlador propiedadesControlador) {
        this.propiedadesControlador = propiedadesControlador;
    }

}
