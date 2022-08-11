/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author lponce
 */
@ManagedBean(eager = true, name = "propiedadesControlador")
@ApplicationScoped
public class PropiedadesControlador implements Serializable {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    Properties properties;
    private ScheduledExecutorService scheduler;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public PropiedadesControlador() {
        System.out.println("Inicio Propiedades");
        properties = new Properties();
    }

    @PostConstruct
    protected void initialize() {
        System.out.println("inicianod cache runner");
        ejecutarTimer();
        scheduler = Executors.newScheduledThreadPool(2);// .newSingleThreadScheduledExecutor();
        Runnable leePropiedades;
        leePropiedades = () -> {
            ejecutarTimer();
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(leePropiedades, 1, 10, TimeUnit.SECONDS);
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    public void ejecutarTimer() {
        String fileName = System.getProperty("jboss.server.config.dir") + "/parametros.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropiedadesControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropiedadesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPropertieValue(String propertieKey) {
        /*System.out.println("bus: "+propertieKey);
        System.out.println("value: "+properties.getProperty(propertieKey));
        System.out.println("prop: "+properties.toString());*/
        return properties.getProperty(propertieKey);
    }
    //</editor-fold>
    //<editor-fold desc="Metodos EJB" defaultstate="collapsed">
    //</editor-fold>
}
