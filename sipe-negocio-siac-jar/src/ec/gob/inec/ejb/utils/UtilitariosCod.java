/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.ejb.utils;

import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author dvaldas
 */
public class UtilitariosCod {

    private static final Logger LOGGER = Logger.getLogger(UtilitariosCod.class.getName());

    public UtilitariosCod() {
    }

    public String obtieneValor(String recurso, String propiedad) {
        try {
            return ResourceBundle.getBundle(recurso).getString(propiedad);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return "No existe mensaje";
    }

    /**
     * Obtener data source name
     *
     * @return
     */
    public Connection getConexion() {
        try {
            Connection con = dataSource().getConnection();
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    public DataSource dataSource() {
        try {
            DataSource ds;
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/siacDS");
            return ds;
        } catch (Exception e) {
            return null;
        }
    }

}
