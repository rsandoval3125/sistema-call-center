/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.conexion;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.sun.rowset.CachedRowSetImpl;
//import com.sun.rowset.CachedRowSetImpl;
import ec.gob.inec.conexion.geojson.serializers.GeometrySerializer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.CachedRowSet;
import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;

/**
 *
 * @author lponce
 */
public class EjecutarSQL {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    /**
     * Ejecuta sentencias select multiple filas
     *
     * @param sql, sentencia a ejecutar
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return, objeto con filas
     */
    public CachedRowSet EjecutaSQL(String sql, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            Connection con;
            ConectarBD cbd = new ConectarBD();
            con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd);
            CachedRowSet crs = null;
            try (Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = st.executeQuery(sql)) {
                crs = new CachedRowSetImpl();
                crs.populate(rs);
            } finally {
                // cambio caja blanca 27/07/2021
                if (crs != null) {
                    crs.close();
                }
            }
            con.close();
            return crs;
        } catch (SQLException e) {
            System.err.println("error ejecutar1:" + e.getMessage());
            return null;
        }
    }

    /*public CachedRowSet EjecutaSQL2(String sql, String driver, ConectarBD.ListaDBMS tipoBD) {
        try {
            Connection con;
            ConectarBD cbd = new ConectarBD();
            //con = cbd.ConectarBD2(driver, tipoBD);
            con = cbd.getConnection();
            CachedRowSet crs = null;
            try (Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = st.executeQuery(sql)) {
                crs = new CachedRowSetImpl();
                crs.populate(rs);
//            } finally {
//                // cambio caja blanca 27/07/2021
//                if (crs != null) {
//                    crs.close();
//                }
//            }
            con.close();
            return crs;
        } catch (Exception e) {
            System.err.println("error ejecutar1:" + e.getMessage());
            return null;
        }
    }*/
    /**
     * Ejecuta sentencias select multiple filas
     *
     * @param sql, sentencia a ejecutar
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return, objeto con filas
     */
    public ResultSet EjecutaRsSQL(String sql, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            ConectarBD cbd = new ConectarBD();
            try (Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd); Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = st.executeQuery(sql)) {
                return rs;
            }
        } catch (SQLException e) {
            System.err.println("error ejecutar2:" + e.getMessage());
            return null;
        }
    }

    /**
     * Ejecuta sentencias select multiple filas
     *
     * @param sql, sentencia a ejecutar
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return, objeto con filas
     */
    public List<Object[]> EjecutaListSQL(String sql, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            ConectarBD cbd = new ConectarBD();
            List<Object[]> rows = new ArrayList<>();
            try (
                    Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd);
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = st.executeQuery(sql)) {
                ResultSetMetaData md = rs.getMetaData();
                int columns = md.getColumnCount();
                System.out.println("col EjecutaListSQL" + columns);
                while (rs.next()) {
                    Object[] row = new Object[columns];
                    for (int i = 1; i <= columns; ++i) {
                        row[i - 1] = rs.getObject(i);
                    }
                    /*System.out.println(row.toString());
                    System.out.println(row.toString());*/
                    rows.add(row);
                }
            }
            /*for (Object[] row : rows) {
                for (Object object : row) {
                    if (object != null) {
                        if (object.getClass().toString().equals("class org.postgis.PGgeometry")) {
                            PGgeometry pgGeometry = (PGgeometry) object;
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.addMixIn(org.postgis.Point.class, ec.gob.inec.conexion.MixIn.class);
                            object = mapper.writeValueAsString(pgGeometry);
                        }
                    }
                }
            }*/
            System.out.println("row EjecutaListSQL" + rows.size());
            return rows;
        } catch (SQLException e) {
            System.err.println("error ejecutar3:" + e.getMessage());
            return null;
        }
    }

    /**
     * Ejecuta sentencias select multiple filas
     *
     * @param sql, sentencia a ejecutar
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @param esJson
     * @return, objeto con filas
     */
    public DataTabla EjecutaListSQL2(String sql, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd, int esJson) {
        try {
            String separacion = Character.toString((char) 94);
            int contadora = 0;
            ConectarBD cbd = new ConectarBD();
            StringBuilder sbRes = new StringBuilder();
            List<Object> rows = new ArrayList<>();
            try (Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd); Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = st.executeQuery(sql)) {
                ResultSetMetaData md = rs.getMetaData();
                List<String> sbStrings = new LinkedList<>();
                int columns = md.getColumnCount();
                Map<Integer, String> columnaMap = new LinkedHashMap<>(columns);
                List<DataColumna> dataColumnas = new ArrayList<>();
                for (int i = 1; i <= columns; i++) {
                    columnaMap.put(i - 1, md.getColumnName(columns));
                    DataColumna dataColumna = new DataColumna();
                    dataColumna.setAutoincremental(md.isAutoIncrement(i));
                    dataColumna.setCaseSensitivo(md.isCaseSensitive(i));
                    dataColumna.setConSigno(md.isSigned(i));
                    dataColumna.setEscala(md.getScale(i));
                    dataColumna.setEsquema(md.getSchemaName(i));
                    dataColumna.setModificable(md.isReadOnly(i));
                    dataColumna.setMoneda(md.isCurrency(i));
                    dataColumna.setNombre(md.getColumnName(i));
                    dataColumna.setNombreCatalogoBD(md.getCatalogName(i));
                    dataColumna.setNombreClaseJava(md.getColumnClassName(i));
                    dataColumna.setNulo(md.isNullable(i));
                    dataColumna.setPosicion(i);
                    dataColumna.setPrecision(md.getPrecision(i));
                    dataColumna.setTabla(md.getTableName(i));
                    dataColumna.setTamanoColumna(md.getColumnDisplaySize(i));
                    dataColumna.setTextoPresentacion(md.getColumnLabel(i));
                    dataColumna.setTipoDato(md.getColumnType(i));
                    dataColumna.setTipoDatoString(md.getColumnTypeName(i));
                    dataColumnas.add(dataColumna);
                }
                DataTabla dataTabla = new DataTabla();
                dataTabla.setColumnas(columnaMap);
                dataTabla.setDataColumnas(dataColumnas);
                while (rs.next()) {
                    Object[] row = new Object[columns];
                    for (int i = 1; i <= columns; ++i) {
                        row[i - 1] = rs.getObject(i);
                        if (rs.getObject(i) != null) {
                            if (!rs.getObject(i).toString().equals("null")) {
                                if (md.getColumnTypeName(i).contains("char") || md.getColumnTypeName(i).contains("date") || md.getColumnTypeName(i).contains("time") || md.getColumnTypeName(i).contains("text")) {
                                    String valorObject = rs.getObject(i).toString().replace("^", "^^");
                                    sbRes.append(separacion).append(valorObject).append(separacion);
                                } else if (md.getColumnTypeName(i).contains("geometr")) {// && esJson == 1) {
                                    PGgeometry pgGeometryBD = (PGgeometry) rs.getObject(i);
                                    Geometry geometry = pgGeometryBD.getGeometry();
                                    JsonFactory factory = new JsonFactory();
                                    JsonGenerator geoJson;
                                    StringBuilder geoJsonText = new StringBuilder();
                                    // cambio caja blanca 27/07/2021
                                    BufferedReader reader = null;
                                    try {
                                        geoJson = factory.createGenerator(new File("output.json"), JsonEncoding.UTF8);

                                        GeometrySerializer geometrySerializer = new GeometrySerializer();
                                        /*SerializerProvider provider = new DefaultSerializerProvider() {
                                            @Override
                                            public DefaultSerializerProvider createInstance(SerializationConfig sc, SerializerFactory sf) {
                                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                            }
                                        };
                                        geometrySerializer.serialize(geometry, geoJson, provider);*/
                                        geoJson.close();
                                        File archivoGEOJSON = new File("output.json");
//                                        BufferedReader reader = null;
                                        reader = new BufferedReader(new FileReader(archivoGEOJSON));
                                        String text = null;
                                        while ((text = reader.readLine()) != null) {
                                            geoJsonText.append(text);
                                        }

                                    } catch (IOException ex) {
                                        Logger.getLogger(EjecutarSQL.class.getName()).log(Level.SEVERE, null, ex);
                                    } finally {
                                        try {
                                            // cambio caja blanca 27/07/2021
                                            if (reader != null) {
                                                reader.close();
                                            }
                                        } catch (IOException ex) {
                                            Logger.getLogger(EjecutarSQL.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    //JSONPObject jsonorigen = new JSONPObject("geometria", pgGeometryBD);
                                    /*System.out.println("Json: "+jsonorigen.getValue());
                                        System.out.println("Json: "+jsonorigen);*/
                                    System.out.println("Json: " + geoJsonText.toString());
                                    sbRes.append(separacion).append(geoJsonText.toString()).append(separacion);
                                    sbRes.append(separacion).append(geoJsonText.toString()).append(separacion);

                                    row[i - 1] = geoJsonText.toString();
                                } else {
                                    sbRes.append(rs.getObject(i));
                                }
                            }
                        }
                        if (i != columns) {
                            sbRes.append(",");
                        }
                    }
                    contadora++;
                    if (sbRes.length() > 9000000) {
                        //if (contadora == 100000) {
                        String sbString = sbRes.toString();
                        sbStrings.add(sbString);
                        sbRes = new StringBuilder();
                        contadora = 0;
                    } else {
                        sbRes.append("\n");
                    }
                    rows.add(row);
                }
                dataTabla.setTabla(rows);
                if (sbRes.length() > 2) {
                    sbRes = sbRes.delete(sbRes.length() - 1, sbRes.length());
                    sbStrings.add(sbRes.toString());
                }

                dataTabla.setDataStringBuilder(sbStrings);

                return dataTabla;
            }

        } catch (SQLException e) {
            System.err.println("error ejecutar4:" + e.getMessage());
            return null;
        }
    }

    /**
     * Ejecuta sentencias select multiple filas
     *
     * @param sql, sentencia a ejecutar
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return, objeto con filas
     */
    public List<Map<String, Object>> EjecutaListMapSQL(String sql, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            ConectarBD cbd = new ConectarBD();
            try (Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd); Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); ResultSet rs = st.executeQuery(sql)) {
                ResultSetMetaData md = rs.getMetaData();
                int columns = md.getColumnCount();
                List<Map<String, Object>> rows = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>(columns);
                    for (int i = 1; i <= columns; ++i) {
                        row.put(md.getColumnName(i), rs.getObject(i));
                    }
                    rows.add(row);
                }
                return rows;
            }
        } catch (SQLException e) {
            System.err.println("error ejecutar5:" + e.getMessage());
            return null;
        }
    }

    /**
     * Ejecuta sentencias select registro
     *
     * @param sql, sentencia a ejecutar
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return, objeto con filas
     */
    public int EjecutarSP(String sql, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            ConectarBD cbd = new ConectarBD();
            try (Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd); Statement st = con.createStatement()) {
                st.execute(sql);
                int res = st.getUpdateCount();
                return res;
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return -1;
        }
    }

    public String EjecutarQuery(String sql, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            ConectarBD cbd = new ConectarBD();
            try (Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd);
                    Statement st = con.createStatement()) {
                st.execute(sql);
                String getMessageQuery = "Consulta existosa";
                return getMessageQuery;
            }
        } catch (SQLException e) {
            System.err.print("sqlEx " + e.getMessage());
            return "Error: " + e.getMessage();

        }
    }

    public String EjecutarQueryBDD(String sql, Connection con) {
        String resultado;
        try {
            try (
                    Statement st = con.createStatement()) {
                Object obj = st.execute(sql);
                System.out.println(" obj" + obj + "" + obj.toString());
                resultado = "exito";

            }
        } catch (SQLException e) {
            System.err.print("sqlEx " + e.getMessage());
            resultado = "Error: " + e.getMessage();

        }
        return resultado;
    }

    /**
     * Ejecuta sentencias select registro
     *
     * @param sql, sentencia a ejecutar
     * @param datosList, datos en lista
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return, objeto con filas
     */
    public int EjecutarSPCopy(String sql, String datosList, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            System.out.println("inicio copy:" + datosList.length() + " : " + sql);
            ConectarBD cbd = new ConectarBD();
            Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd);
            System.out.println("conectado copy" + " : " + sql);
            CopyManager cpManager = ((PGConnection) con).getCopyAPI();
            //StringBuilder datosSB = new StringBuilder(datosList);
            //PushbackReader reader = new PushbackReader(new StringReader(""), datosSB.length());
            PushbackReader reader = new PushbackReader(new StringReader(""), datosList.length());
            System.out.println("reader ini copy" + " : " + sql);
            //reader.unread(datosSB.toString().toCharArray());
            reader.unread(datosList.toCharArray());
            System.out.println("reader copy" + reader.toString() + " : " + sql);
            int valor = (int) cpManager.copyIn(sql, reader);
            System.out.println("valor copy" + valor + " : " + sql);
            return valor;
        } catch (IOException | SQLException e) {
            System.out.println("error copy: " + sql);
            System.err.print(e.getMessage() + " : " + sql);
            return -1;
        }
    }

    /**
     * Ejecuta sentencias create tmp, select y drop tmp, multiple filas
     * Crea y recupera el reporte desde repo_procedimiento 22/03/2022
     * @param creaTmp, sentencia a ejecutar, create temp
     * @param consultaTmp, sentencia a ejecutar, select
     * @param sqlDrop sentencia a ejecutar, drop
     * @param driver nombre de la clase de la conexion
     * @param tipoBD tipo de conexion
     * @param ipsr ip de la base de datos
     * @param puerto puerto de la base de datos
     * @param usuario usuario de base de datos
     * @param password contraseña de base de datos
     * @param bdd nombre de base de datos
     * @return, objeto con filas
     */
    public List<Object[]> EjecutaTmpSQL(String creaTmp, String consultaTmp, String sqlDrop, String driver, ConectarBD.ListaDBMS tipoBD, String ipsr, int puerto, String usuario, String password, String bdd) {
        try {
            ConectarBD cbd = new ConectarBD();
            List<Object[]> rows = new ArrayList<>();
            try (
                    Connection con = cbd.ConectarBD(driver, tipoBD, ipsr, puerto, usuario, password, bdd);
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
                st.executeUpdate(creaTmp);
                int res = st.getUpdateCount();
                if (res != -1) {
                    ResultSet rs = st.executeQuery(consultaTmp);
                    ResultSetMetaData md = rs.getMetaData();
                    int columns = md.getColumnCount();
                    while (rs.next()) {
                        Object[] row = new Object[columns];
                        for (int i = 1; i <= columns; ++i) {
                            row[i - 1] = rs.getObject(i);
                        }
                        rows.add(row);
                    }
                    System.out.println("row creaTmp" + rows.size());
                } else {
                    System.out.println("no se creo tabla tmp: " + consultaTmp.split("from")[1]);
                }
                st.executeUpdate(sqlDrop);
                int resD = st.getUpdateCount();
                if (resD == -1) {
                    System.out.println("no se encontro la tabla tmp: " + sqlDrop.split("table")[1]);
                }
            }
            return rows;
        } catch (SQLException e) {
            System.err.println("error ejecutarTmp:" + e.getMessage());
            return null;
        }
    }
    //</editor-fold>
}
