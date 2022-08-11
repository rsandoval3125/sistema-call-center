/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.postgresql.util.PGobject;
import java.util.logging.Logger;

/**
 *
 * @author lponce
 */
public class ConectarBD {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    // cambio caja blanca 27/07/2021
    private static final Logger LOGGER = Logger.getLogger(ConectarBD.class.getName());

    /**
     * Base de datos aceptadas postgres,mysql,sybase
     */
    public enum ListaDBMS {

        postgres, mysql, sybase
    };
    //</editor-fold>

    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    /**
     * Crea una conexion con cualquier base de datos
     *
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return la conexion creada
     */
    public Connection ConectarBD(String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        Connection con;
        try {
            Class.forName(driver);
            String url = CreaURL(tipoBD, ipsr, puerto, bdd);
            con = DriverManager.getConnection(url, usuario, password);
            /*if (driver.contains("postgres")) {
                ((org.postgresql.PGConnection) con).addDataType("geometry",(Class<? extends PGobject>) Class.forName("org.postgis.PGgeometry") );
                  ((org.postgresql.PGConnection) con).addDataType("box3d",(Class<? extends PGobject>) Class.forName("org.postgis.PGgbox3d") );
                //((org.postgresql.jdbc4.Jdbc4Connection) con).addDataType("geometry", "org.postgis.PGgeometry");
                //((org.postgresql.jdbc4.Jdbc4Connection) con).addDataType("box3d", "org.postgis.PGbox3d");
            }*/
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("error conectar:" + e.getMessage());
            return null;
        }
        /*finally {  // cambio caja blanca 27/07/2021
            try {
                if(con!=null){
                     con.close();  // Multiple streams were opened. Only the last is closed.
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }

    /*public Connection ConectarBD2(String driver, ConectarBD.ListaDBMS tipoBD) {
        Connection con;
        try {
            Class.forName(driver);
            con = getConnection();
            if (driver.contains("postgres")) {
                ((org.postgresql.jdbc4.Jdbc4Connection) con).addDataType("geometry", "org.postgis.PGgeometry");
                ((org.postgresql.jdbc4.Jdbc4Connection) con).addDataType("box3d", "org.postgis.PGbox3d");
            }
            return con;
        } catch (ClassNotFoundException e) {
            System.err.println("error conectar:" + e.getMessage());
            return null;
        }
    }*/
    /**
     * Crea la cadena de conexion
     *
     * @param tipoBD tipo de conexion
     * @param ipsr, ip de la base de datos
     * @param puerto, puerto de la base de datos
     * @param bdd nombre de base de datos
     * @return, cadena de conexion
     */
    public String CreaURL(ListaDBMS tipoBD, String ipsr, int puerto, String bdd) {
        String url = "jdbc:";
        String hostsr = ipsr + ":" + puerto;
        String iniurl = "";
        String finurl = "";
        switch (tipoBD) {
            case postgres:
                iniurl = "postgresql://";
                finurl = bdd;
                break;
            case mysql:
                iniurl = "mysql://";
                finurl = tipoBD.name();
                break;
            case sybase:
                iniurl = "sybase:Tds:";
                //finurl = "SIPC";
                finurl = bdd;
                break;
            default:
            // cambio caja blanca 27/07/2021
        }
        url = url + iniurl + hostsr + "/" + finurl;
        return url;
    }
    //se comenta para crear una conexión desde el ejb ActualizaBDFacade, método recuperaConexion 22/03/2022
    /*public Connection getConnection() {
        Connection connection = null;
        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:/sipeSpcDS");
            connection = dataSource.getConnection();
        } catch (Exception e) {
            // cambio caja blanca 27/07/2021
//            e.printStackTrace();
           LOGGER.log(Level.SEVERE, null, e);
        }
        return connection;
    }*/
    //</editor-fold>
}
