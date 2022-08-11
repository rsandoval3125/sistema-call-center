/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.cache.clases;

import ec.gob.inec.administracion.ejb.servicios.AdmParametroGlobalServicioRemote;
import ec.gob.inec.geografia.ejb.entidades.GeoCobertura;
import ec.gob.inec.geografia.ejb.servicios.GeoCoberturaServicioRemote;
import ec.gob.inec.geografia.ejb.servicios.GeoDpaServicioRemote;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
import ec.gob.inec.metadato.ejb.servicios.MetCatalogoServicioRemote;
import ec.gob.inec.metadato.ejb.servicios.MetTipoCatalogoServicioRemote;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Transactional;

/**
 *
 * @author lponce
 */
@Singleton
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class CacheTimer {

    private static final Logger LOGGER = Logger.getLogger(CacheTimer.class.getName());

    private Properties properties;

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private int contador;
    List<GeoCobertura> geoCoberturaList;
    List<MetCatalogo> metCatalogoList;
    List<MetTipoCatalogo> metTipoCatalogoList;

    InitialContext contextAdministracion = null;
    InitialContext contextGeografia = null;
    InitialContext contextMetadato = null;

    private AdmParametroGlobalServicioRemote admParametroGlobalServicioRemote;

    private GeoCoberturaServicioRemote geoCoberturaServicioRemote;
    private GeoDpaServicioRemote geoDpaServicioRemote;

    private MetCatalogoServicioRemote metCatalogoServicioRemote;
    private MetTipoCatalogoServicioRemote metTipoCatalogoServicioRemote;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public CacheTimer(int contador) {
        this.contador = contador;
    }

    public CacheTimer() {
      //  System.out.println("ingresa cach");
        contador = 0;
        properties = new Properties();
        geoCoberturaList = new ArrayList<>();
        metCatalogoList = new ArrayList<>();
        metTipoCatalogoList = new ArrayList<>();

    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public List<GeoCobertura> getGeoCoberturaList() {
        return geoCoberturaList;
    }

    public void setGeoCoberturaList(List<GeoCobertura> geoCoberturaList) {
        this.geoCoberturaList = geoCoberturaList;
    }

    public List<MetCatalogo> getMetCatalogoList() {
        return metCatalogoList;
    }

    public void setMetCatalogoList(List<MetCatalogo> metCatalogoList) {
        this.metCatalogoList = metCatalogoList;
    }

    public List<MetTipoCatalogo> getMetTipoCatalogoList() {
        return metTipoCatalogoList;
    }

    public void setMetTipoCatalogoList(List<MetTipoCatalogo> metTipoCatalogoList) {
        this.metTipoCatalogoList = metTipoCatalogoList;
    }

    //</editor-fold>
    //<editor-fold desc="metodos ejb remoto" defaultstate="collapsed">
    private void inicializaContextLookUp() {
        try {
            obtenerContextAdministracion();
           // System.out.println("CONTEXT ADM" + contextAdministracion);
            inicializaLookUPAdministracion();
           // System.out.println("var ADM" + admParametroGlobalServicioRemote);
           // System.out.println("cache propied ip" + properties.getProperty("ipApppFin"));
            obtenerContextMetadato(properties.getProperty("ipApppFin"));
            //System.out.println("CONTEXT met" + contextMetadato);
            inicializaLookUPMetadato();
           // System.out.println("var met" + metCatalogoServicioRemote);
//            obtenerContextGeografia(properties.getProperty("ipApppFin"));
            //System.out.println("CONTEXT GEO" + contextGeografia);
//            inicializaLookUPGeografia();
           // System.out.println("var GEO" + geoCoberturaServicioRemote);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPAdministracion() {
        try {
            admParametroGlobalServicioRemote = (AdmParametroGlobalServicioRemote) contextAdministracion.lookup("sipe-administracion-siac-ejb/AdmParametroGlobalServicio!" + AdmParametroGlobalServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPGeografia() {
        try {
            geoCoberturaServicioRemote = (GeoCoberturaServicioRemote) contextGeografia.lookup("sipe-geografia-ejb/GeoCoberturaServicio!" + GeoCoberturaServicioRemote.class.getName());
            geoDpaServicioRemote = (GeoDpaServicioRemote) contextGeografia.lookup("sipe-geografia-ejb/GeoDpaServicio!" + GeoDpaServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void inicializaLookUPMetadato() {
        try {
            metCatalogoServicioRemote = (MetCatalogoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetCatalogoServicio!" + MetCatalogoServicioRemote.class.getName());
            metTipoCatalogoServicioRemote = (MetTipoCatalogoServicioRemote) contextMetadato.lookup("sipe-metadato-siac-ejb/MetTipoCatalogoServicio!" + MetTipoCatalogoServicioRemote.class.getName());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public InitialContext obtenerContextAdministracion() {
        try {
//            System.out.println("cache propied ip2" + properties.getProperty("ipAPP"));
            System.out.println("cache propied us" + properties.getProperty("userAPP"));
//            System.out.println("cache propied ps" + properties.getProperty("claveApp"));
            if (contextAdministracion == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
                jdniPro3.put(Context.PROVIDER_URL, "http-remoting://" + properties.getProperty("ipAPP"));
                jdniPro3.put(Context.SECURITY_PRINCIPAL, properties.getProperty("userAPP"));
                jdniPro3.put(Context.SECURITY_CREDENTIALS, properties.getProperty("claveApp"));
                jdniPro3.put("jboss.naming.client.ejb.context", true);
                jdniPro3.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false);
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", true);
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
                jdniPro3.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", false);
                contextAdministracion = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextAdministracion;
    }

    public InitialContext obtenerContextGeografia(String numSer) {
        try {
            if (contextGeografia == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Geografia" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Geografia" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Geografia" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextGeografia = new InitialContext(jdniPro3);

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextGeografia;
    }

    public InitialContext obtenerContextMetadato(String numSer) {
        try {
            if (contextMetadato == null) {
                Properties jdniPro3 = new Properties();
                jdniPro3.put(Context.URL_PKG_PREFIXES, admParametroGlobalServicioRemote.buscarXNombre("URL_PKG_PREFIXES").getValor());
                jdniPro3.put(Context.INITIAL_CONTEXT_FACTORY, admParametroGlobalServicioRemote.buscarXNombre("INITIAL_CONTEXT_FACTORY").getValor());
                jdniPro3.put(Context.PROVIDER_URL, admParametroGlobalServicioRemote.buscarXNombre("PROVIDER_URL_SIPE_Metadato" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_PRINCIPAL, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_PRINCIPAL_SIPE_Metadato" + numSer).getValor());
                jdniPro3.put(Context.SECURITY_CREDENTIALS, admParametroGlobalServicioRemote.buscarXNombre("SECURITY_CREDENTIALS_SIPE_Metadato" + numSer).getValor());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("EJB_CONTEXT").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOANONYMOUS").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SSL_ENABLED").getCondicion()));
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getValor(), admParametroGlobalServicioRemote.buscarXNombre("SASL_DISALLOWED_MECHANISMS").getCondicion());
                jdniPro3.put(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getValor(), Boolean.parseBoolean(admParametroGlobalServicioRemote.buscarXNombre("SASL_POLICY_NOPLAINTEXT").getCondicion()));
                contextMetadato = new InitialContext(jdniPro3);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return contextMetadato;
    }

    //</editor-fold>
    //<editor-fold desc="metodos" defaultstate="collapsed">
    @Schedule(second = "*/60", minute = "*", hour = "*")
    public void recuperaDatos() {
        try {
            contador++;
            String fileName = System.getProperty("jboss.server.config.dir") + "/parametros.properties";
            try (FileInputStream fis = new FileInputStream(fileName)) {
                properties.load(fis);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CacheTimer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CacheTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
            inicializaContextLookUp();
            //geoCoberturaServicioRemote = (GeoCoberturaServicioRemote) contextGeografia.lookup("sipe-geografia-ejb/GeoCoberturaServicio!" + GeoCoberturaServicioRemote.class.getName());
//            System.out.println("valor actual: " + contador);
            Map<String, Object> campos = new LinkedHashMap<>();
            campos.put("estadoLogico", Boolean.TRUE);
//            geoCoberturaList = geoCoberturaServicioRemote.listarPorCampos(campos, "indice");
//            geoCoberturaServicioRemote = null;
            metCatalogoList = metCatalogoServicioRemote.listarTodosActivos();
            metTipoCatalogoList = metTipoCatalogoServicioRemote.listarTodo();
//            System.out.println("tamaño cob actual: " + geoCoberturaList.size());
//            System.out.println("tamaño cat actual: " + metCatalogoList.size());
//            System.out.println("tamaño tip actual: " + metTipoCatalogoList.size());
        } catch (Exception ex) {
            System.out.println("Error????");
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GeoCobertura getCoberturaxID(int id) {
//        System.out.println("id cobertura: " + id);
        try {
            return geoCoberturaList.stream().filter(p -> p.getIdCobertura() == id).collect(Collectors.toList()).get(0);
        } catch (Exception er) {
            return null;
        }
    }

    public GeoCobertura getCoberturaxIndice(int indice) {
        try {
            return geoCoberturaList.stream().filter(p -> p.getIndice() == indice).collect(Collectors.toList()).get(0);
        } catch (Exception er) {
            //Logger.getLogger(CacheControlador.class.getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }

    public MetCatalogo getCatalogoxID(int id) {
        try {
            return metCatalogoList.stream().filter(p -> p.getIdCatalogo() == id).collect(Collectors.toList()).get(0);
        } catch (Exception er) {
            return null;
        }
    }

    public List<MetCatalogo> getCatalogoxTipo(int id) {
        try {
            return metCatalogoList.stream().filter(p -> p.getCodTipoCatalogo().getIdTipoCatalogo() == id).collect(Collectors.toList());
        } catch (Exception er) {
            return null;
        }
    }

    public MetTipoCatalogo getTipoCatalogoxID(int id) {
        try {
            return metTipoCatalogoList.stream().filter(p -> p.getIdTipoCatalogo() == id).collect(Collectors.toList()).get(0);
        } catch (Exception er) {
            return null;
        }
    }

    //</editor-fold>
}
