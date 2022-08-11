/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.dato.general;

import ec.gob.inec.conexion.ConectarBD;
import ec.gob.inec.conexion.EjecutarSQL;
import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.ejb.utils.ReflexionEntidad;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author lponce
 */
public class ObtieneConexion {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    private Properties properties;
    //</editor-fold>

    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    /**
     * Obtiene parametros de conexion, dado el nombre de la conexion
     *
     * @param nombreconexion, string cadena de conexion
     * @return, BaseDatos con los parametros de conexion
     */
    public AdmBaseDatos obtenerConexion(String nombreconexion, String tipoCifrado, String parametroAES) {
        EjecutarSQL esql = new EjecutarSQL();
        try {
            AdmBaseDatos bd = new AdmBaseDatos();
            properties = new Properties();
            System.out.println("Inicio");
            properties = System.getProperties();
            //System.
            String fileName = System.getProperty("jboss.server.config.dir") + "/parametros.properties";
            try (FileInputStream fis = new FileInputStream(fileName)) {
                properties.load(fis);
                System.err.println("proCon" + properties.size());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ObtieneConexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ObtieneConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            //CachedRowSet crs_res = esql.EjecutaSQL("select * from administracion.base_datos where nombre='" + nombreconexion + "' ", "org.postgresql.Driver", ConectarBD.ListaDBMS.postgres, properties.getProperty("ipBaseDatosInicial"), Integer.parseInt(properties.getProperty("puertoBaseDatosInicial")), properties.getProperty("userBaseDatosInicial"), properties.getProperty("passBaseDatosInicial"), properties.getProperty("baseBaseDatosInicial"));
            CachedRowSet crs_res = esql.EjecutaSQL("select * from administracion.adm_base_datos where alias='" + nombreconexion + "' ", "org.postgresql.Driver", ConectarBD.ListaDBMS.postgres, properties.getProperty("ipBaseDatosInicial"), Integer.parseInt(properties.getProperty("puertoBaseDatosInicial")), properties.getProperty("userBaseDatosInicial"), properties.getProperty("passBaseDatosInicial"), properties.getProperty("baseBaseDatosInicial"));
            while (crs_res.next()) {
                bd.setIdBasDat(Integer.parseInt(crs_res.getObject(1).toString()));
                bd.setNombre(crs_res.getObject(2).toString());
                bd.setDriver(crs_res.getObject(3).toString());
                bd.setRdbms(crs_res.getObject(4).toString());
                bd.setIp(crs_res.getObject(5).toString());
                bd.setPuerto(Integer.parseInt(crs_res.getObject(6).toString()));
                bd.setUsuario(crs_res.getObject(7).toString());
                String passwordDesencripta = crs_res.getObject(8).toString();
                  // cambio caja blanca 27/07/2021
                if (!"".equals(tipoCifrado)) {
                    passwordDesencripta = ReflexionEntidad.desencripta(tipoCifrado, passwordDesencripta, parametroAES);
                }
                bd.setPassword(passwordDesencripta);
                bd.setNombrebdd(crs_res.getObject(9).toString());
            }
            return bd;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    /*public AdmBaseDatos obtenerConexion2(String nombreconexion, String tipoCifrado, String parametroAES) {
        EjecutarSQL esql = new EjecutarSQL();
        try {
            AdmBaseDatos bd = new AdmBaseDatos();
            //String parametroAES = "InecDiradGiapeZ1";
            //CachedRowSet crs_res = esql.EjecutaSQL("select * from administracion.base_datos where nombre='" + nombreconexion + "' ", "org.postgresql.Driver", ConectarBD.ListaDBMS.postgres, properties.getProperty("ipBaseDatosInicial"), Integer.parseInt(properties.getProperty("puertoBaseDatosInicial")), properties.getProperty("userBaseDatosInicial"), properties.getProperty("passBaseDatosInicial"), properties.getProperty("baseBaseDatosInicial"));
            CachedRowSet crs_res = esql.EjecutaSQL2("select * from administracion.adm_base_datos where alias='" + nombreconexion + "' ", "org.postgresql.Driver", ConectarBD.ListaDBMS.postgres);
            while (crs_res.next()) {
                bd.setIdBasDat(Integer.parseInt(crs_res.getObject(1).toString()));
                bd.setNombre(crs_res.getObject(2).toString());
                bd.setDriver(crs_res.getObject(3).toString());
                bd.setRdbms(crs_res.getObject(4).toString());
                bd.setIp(crs_res.getObject(5).toString());
                bd.setPuerto(Integer.parseInt(crs_res.getObject(6).toString()));
                bd.setUsuario(crs_res.getObject(7).toString());
                String passwordDesencripta = ReflexionEntidad.desencripta(tipoCifrado, crs_res.getObject(8).toString(), parametroAES);
                bd.setPassword(passwordDesencripta);
                bd.setNombrebdd(crs_res.getObject(9).toString());
            }
            return bd;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }*/

    //</editor-fold>
}
