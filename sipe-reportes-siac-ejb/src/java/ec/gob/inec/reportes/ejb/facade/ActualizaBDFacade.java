/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.facade;

import ec.gob.inec.conexion.ConectarBD;
import ec.gob.inec.conexion.EjecutarSQL;
import ec.gob.inec.dato.general.ObtieneConexion;
import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.dato.general.ColumnaValor;
import ec.gob.inec.dato.postgres.GeneraSQLPostgres;
import ec.gob.inec.dato.sybase.GeneraSQLSybase;
import ec.gob.inec.ejb.utils.ReflexionEntidad;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jaraujo
 */
@Stateless
public class ActualizaBDFacade {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    String nombre_conexion;
    AdmBaseDatos bd;
    ObtieneConexion obtieneConexion;
    String tipoCifrado;
    String parametroAES;

    @PersistenceContext(unitName = "sipe-reportes-siac-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public ActualizaBDFacade() {
        bd = new AdmBaseDatos();
    }

    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    public String getNombre_conexion() {
        return nombre_conexion;
    }

    public void setNombre_conexion(String nombre_conexion) {
        this.nombre_conexion = nombre_conexion;
    }

    public AdmBaseDatos getBd() {
        return bd;
    }

    public void setBd(AdmBaseDatos bd) {
        this.bd = bd;
    }

    public ObtieneConexion getObtieneConexion() {
        return obtieneConexion;
    }

    public void setObtieneConexion(ObtieneConexion obtieneConexion) {
        this.obtieneConexion = obtieneConexion;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    /**
     * recupera la información de la base de datos perteneciente a la conexion,
     * tabla y filtro indicado
     *
     * @param nombre_conexion, nombre de la conexion para recuperar los
     * parametros
     * @param tabla, nombre de la tabla de donde se recupera los datos
     * @param usuario, usuario que realiza la peticion
     * @param wheremw, Map<Columna,Object>, que componen el where
     * @param limite , entero limite de recuperacion de registros
     * @return, lista de objetos de resultado
     */
    public List<Object[]> recuperaInformacionListValor(String nombre_conexion, String tabla, int usuario, List<ColumnaValor> wheremw, int limite) {
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        Map<ColumnaValor, Object> wherem = new LinkedHashMap<>();
        if (wheremw != null) {
            for (ColumnaValor objects : wheremw) {
                wherem.put(objects, objects.getValorObjeto());
            }
        }

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr;
        switch (DBMS) {
            case sybase:
                GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = gensqlSybase.generaSelectValor(tabla, wherem, 1, limite);
                break;
            default:
                GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                sqlstr = gensql.generaSelectValor(tabla, wherem, 1, limite);
                break;
        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            System.out.println("sera2");
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sqlstr, bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    /**
     * recupera la información de la base de datos perteneciente a la conexion,
     * tabla y filtro indicado
     *
     * @param nombre_conexion, nombre de la conexion para recuperar los
     * parametros
     * @param tabla, nombre de la tabla de donde se recupera los datos
     * @param usuario, usuario que realiza la peticion
     * @param limite , entero limite de recuperacion de registros
     * @return, lista de objetos de resultado
     */
    public List<Object[]> recuperaInformacionListValor2(String nombre_conexion, String tabla, int usuario, int limite) {
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        Map<ColumnaValor, Object> wherem = new LinkedHashMap<>();

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr;
        switch (DBMS) {
            case sybase:
                GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = gensqlSybase.generaSelectValor(tabla, wherem, 1, limite);
                System.err.println("sqlstr " + sqlstr);
                break;
            default:
                GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                sqlstr = gensql.generaSelectValor(tabla, wherem, 1, limite);
                break;
        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            System.out.println("sera2");
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sqlstr, bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    /**
     * Obtiene los parametros de conexion, con el nombre de la conexion 22/03/2022
     */
    private void recuperaConexion() {
        try {
            System.out.println("rec con");
            System.out.println(nombre_conexion);
            //obtieneConexion = new ObtieneConexion();
            //bd = obtieneConexion.obtenerConexion(nombre_conexion);
            //bd = obtieneConexion.obtenerConexion2(nombre_conexion, tipoCifrado, parametroAES);
            String sql = "select * from administracion.adm_base_datos where alias= :aliasConexion and estado_logico = true";
            Query q = em.createNativeQuery(sql);
            q.setParameter("aliasConexion", nombre_conexion);

            Object[] objBD = (Object[]) q.getSingleResult();
            bd.setIdBasDat(Integer.parseInt(objBD[0].toString()));
            bd.setNombre(objBD[1].toString());
            bd.setDriver(objBD[2].toString());
            bd.setRdbms(objBD[3].toString());
            bd.setIp(objBD[4].toString());
            bd.setPuerto(Integer.parseInt(objBD[5].toString()));
            bd.setUsuario(objBD[6].toString());
            String passwordDesencripta = objBD[7].toString();
            if (!"".equals(tipoCifrado)) {
                passwordDesencripta = ReflexionEntidad.desencripta(tipoCifrado, passwordDesencripta, parametroAES);
            }
            bd.setPassword(passwordDesencripta);
            bd.setNombrebdd(objBD[8].toString());

            System.out.println(bd);
        } catch (Exception ex) {
            Logger.getLogger(ActualizaBDFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int ejecutaSql(String nombre_conexion, String sqlstr) {
        EjecutarSQL ejesql = new EjecutarSQL();
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        int valorres = -1;
        if (!sqlstr.equals("")) {
            valorres = ejesql.EjecutarSP(sqlstr, bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
        }
        return valorres;
    }

    public List<Object[]> recuperaInformacionListTablas(String nombreConexion, String esquema) {
        this.nombre_conexion = nombreConexion;
        recuperaConexion();

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr;
        switch (DBMS) {
            case sybase:
                //GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = "select name\n"
                        + "from sysobjects \n"
                        + "where type = 'U'\n"
                        + "order by name";
                //System.err.println("sqlstrTbl1 " + sqlstr);
                break;
            default:
                //GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                sqlstr = "SELECT table_name FROM information_schema.tables WHERE table_schema= " + "'" + esquema + "'" + " AND table_type='BASE TABLE'";
                //sqlstr = "SELECT table_name FROM information_schema.tables WHERE table_schema= "+"'"+esquema+"'"+" AND table_type='BASE TABLE'";
                /*sqlstr = "select b.table_name, b.column_name\n"
                        + "from information_schema.tables a, information_schema.columns b\n"
                        + "where a.table_name = b.table_name\n"
                        + "and b.table_schema = "+"'"+esquema+"'"+"\n"
                        + "and a.table_type='BASE TABLE'\n"
                        + "order by b.table_name, b.column_name";*/
                //System.err.println("sqlstrTbl2 " + sqlstr);
                break;

        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            System.out.println("sera2");
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sqlstr, bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    public List<Object[]> recuperaInformacionColumnas(String nombreConexion, String nomTabla, String esquema) {
        this.nombre_conexion = nombreConexion;
        recuperaConexion();

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr;
        switch (DBMS) {
            case sybase:
                //GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = "SELECT syscolumns.name FROM sysobjects \n"
                        + "JOIN syscolumns ON sysobjects.id = syscolumns.id\n"
                        + "WHERE sysobjects.name LIKE " + "'" + nomTabla + "'";
                //System.err.println("sqlstrCol1 " + sqlstr);
                break;
            default:
                //GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                sqlstr = "SELECT column_name\n"
                        + "FROM information_schema.columns\n"
                        + "WHERE table_schema = " + "'" + esquema + "'" + "\n"
                        + "AND table_name = " + "'" + nomTabla + "'";
                //System.err.println("sqlstrCol2 " + sqlstr);
                break;

        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            System.out.println("sera2");
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sqlstr, bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    public List<Object[]> recuperaDependencias(String nombreConexion, String nomTabla, String esquema) {
        this.nombre_conexion = nombreConexion;
        recuperaConexion();

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr = "";
        StringBuilder sql = new StringBuilder();
        switch (DBMS) {
            case sybase:
                //GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = "SELECT syscolumns.name FROM sysobjects \n"
                        + "JOIN syscolumns ON sysobjects.id = syscolumns.id\n"
                        + "WHERE sysobjects.name LIKE " + "'" + nomTabla + "'";
                //System.err.println("sqlstrCol1 " + sqlstr);
                break;
            default:
                //GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                /*sqlstr = "SELECT column_name\n"
                        + "FROM information_schema.columns\n"
                        + "WHERE table_schema = " + "'" + esquema + "'" + "\n"
                        + "AND table_name = " + "'" + nomTabla + "'";*/

                sql.append("SELECT ist.table_schema AS OnTableSchema ");
                sql.append(",ist.table_name AS OnTableName ");
                sql.append(",tForeignKeyInformation.AgainstTableSchema AS AgainstTableSchema ");
                sql.append(",tForeignKeyInformation.AgainstTableName AS AgainstTableName ");
                sql.append("FROM INFORMATION_SCHEMA.TABLES AS ist ");
                sql.append("LEFT JOIN ( ");

                sql.append("SELECT KCU1.table_schema AS OnTableSchema ");
                sql.append(",KCU1.table_name AS OnTableName ");
                sql.append(",KCU2.table_schema AS AgainstTableSchema ");
                sql.append(",KCU2.table_name AS AgainstTableName ");
                sql.append("FROM information_schema.referential_constraints AS RC ");

                sql.append("INNER JOIN information_schema.key_column_usage AS KCU1 ");
                sql.append("ON KCU1.constraint_catalog = RC.constraint_catalog ");
                sql.append("AND KCU1.constraint_schema = RC.constraint_schema ");
                sql.append("AND KCU1.constraint_name = RC.constraint_name ");

                sql.append("INNER JOIN information_schema.key_column_usage AS KCU2 ");
                sql.append("ON KCU2.constraint_catalog =  RC.constraint_catalog ");
                sql.append("AND KCU2.constraint_schema = RC.unique_constraint_schema ");
                sql.append("AND KCU2.constraint_name = RC.unique_constraint_name ");
                sql.append("AND KCU2.ordinal_position = KCU1.ordinal_position ");

                sql.append("INNER JOIN INFORMATION_SCHEMA.COLUMNS AS isc ");
                sql.append("ON isc.table_name = KCU1.table_name ");
                sql.append("AND isc.table_schema = KCU1.table_schema ");
                sql.append("AND isc.table_catalog = KCU1.table_catalog ");
                sql.append("AND isc.column_name = KCU1.column_name ");

                sql.append("WHERE KCU1.table_name <> KCU2.table_name ");
                sql.append("AND KCU1.table_schema= " + "'").append(esquema).append("' ");
                sql.append("GROUP BY KCU1.table_schema ");
                sql.append(",KCU1.table_name ");
                sql.append(",KCU2.table_schema ");
                sql.append(",KCU2.table_name ");
                sql.append(") AS tForeignKeyInformation ");
                sql.append("ON tForeignKeyInformation.OnTableName = ist.table_name ");
                sql.append("AND tForeignKeyInformation.OnTableSchema = ist.table_schema ");

                sql.append("WHERE ist.table_type = 'BASE TABLE' ");
                sql.append("AND ist.table_schema NOT IN ('pg_catalog', 'information_schema') ");
                sql.append("AND table_schema= " + "'").append(esquema).append("' ");
                sql.append("AND tForeignKeyInformation.AgainstTableName <>'' ");
                sql.append("AND ist.table_name = " + "'").append(nomTabla).append("'" + " OR tForeignKeyInformation.AgainstTableName = " + "'").append(nomTabla).append("' ");
                sql.append("ORDER BY OnTableSchema, OnTableName ");

                //System.err.println("sqlDep " + sql);
                break;

        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            System.out.println("sera2");
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sql.toString(), bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    public List<Object[]> columnaReferencias(String nombreConexion, String nomTabla, String esquema) {
        this.nombre_conexion = nombreConexion;
        recuperaConexion();

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr = "";
        StringBuilder sql = new StringBuilder();
        switch (DBMS) {
            case sybase:
                //GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = "SELECT syscolumns.name FROM sysobjects \n"
                        + "JOIN syscolumns ON sysobjects.id = syscolumns.id\n"
                        + "WHERE sysobjects.name LIKE " + "'" + nomTabla + "'";
                //System.err.println("sqlstrCol1 " + sqlstr);
                break;
            default:
                //GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                /*sqlstr = "SELECT column_name\n"
                        + "FROM information_schema.columns\n"
                        + "WHERE table_schema = " + "'" + esquema + "'" + "\n"
                        + "AND table_name = " + "'" + nomTabla + "'";*/

                sql.append("SELECT ist.table_schema AS OnTableSchema ");
                sql.append(",ist.table_name AS OnTableName ");
                sql.append(",tForeignKeyInformation.refOnTable AS refOnTable ");
                sql.append(",tForeignKeyInformation.AgainstTableSchema AS AgainstTableSchema ");
                sql.append(",tForeignKeyInformation.AgainstTableName AS AgainstTableName ");
                sql.append(",tForeignKeyInformation.refAgainstTable AS refAgainstTable ");
                sql.append("FROM INFORMATION_SCHEMA.TABLES AS ist ");
                sql.append("LEFT JOIN ( ");

                sql.append("SELECT KCU1.table_schema AS OnTableSchema ");
                sql.append(",KCU1.table_name AS OnTableName ");
                sql.append(",iscA.COLUMN_NAME as refOnTable ");
                sql.append(",KCU2.table_schema AS AgainstTableSchema ");
                sql.append(",KCU2.table_name AS AgainstTableName ");
                sql.append(",iscB.COLUMN_NAME as refAgainstTable ");
                sql.append("FROM information_schema.referential_constraints AS RC ");

                sql.append("INNER JOIN information_schema.key_column_usage AS KCU1 ");
                sql.append("ON KCU1.constraint_catalog = RC.constraint_catalog ");
                sql.append("AND KCU1.constraint_schema = RC.constraint_schema ");
                sql.append("AND KCU1.constraint_name = RC.constraint_name ");

                sql.append("INNER JOIN information_schema.key_column_usage AS KCU2 ");
                sql.append("ON KCU2.constraint_catalog =  RC.constraint_catalog ");
                sql.append("AND KCU2.constraint_schema = RC.unique_constraint_schema ");
                sql.append("AND KCU2.constraint_name = RC.unique_constraint_name ");
                sql.append("AND KCU2.ordinal_position = KCU1.ordinal_position ");

                sql.append("INNER JOIN INFORMATION_SCHEMA.COLUMNS AS iscA ");
                sql.append("ON iscA.table_name = KCU1.table_name ");
                sql.append("AND iscA.table_schema = KCU1.table_schema ");
                sql.append("AND iscA.table_catalog = KCU1.table_catalog ");
                sql.append("AND iscA.column_name = KCU1.column_name ");

                sql.append("INNER JOIN INFORMATION_SCHEMA.COLUMNS AS iscB ");
                sql.append("ON iscB.table_name = KCU2.table_name ");
                sql.append("AND iscB.table_schema = KCU2.table_schema ");
                sql.append("AND iscB.table_catalog = KCU2.table_catalog ");
                sql.append("AND iscB.column_name = KCU2.column_name ");

                sql.append("WHERE KCU1.table_name <> KCU2.table_name ");
                sql.append("AND KCU1.table_schema= " + "'").append(esquema).append("' ");
                sql.append("GROUP BY KCU1.table_schema ");
                sql.append(",KCU1.table_name ");
                sql.append(",KCU2.table_schema ");
                sql.append(",KCU2.table_name ");
                sql.append(",iscA.COLUMN_NAME ");
                sql.append(",iscB.COLUMN_NAME ");
                sql.append(") AS tForeignKeyInformation ");
                sql.append("ON tForeignKeyInformation.OnTableName = ist.table_name ");
                sql.append("AND tForeignKeyInformation.OnTableSchema = ist.table_schema ");

                sql.append("WHERE ist.table_type = 'BASE TABLE' ");
                sql.append("AND ist.table_schema NOT IN ('pg_catalog', 'information_schema') ");
                sql.append("AND table_schema= " + "'").append(esquema).append("' ");
                sql.append("AND tForeignKeyInformation.AgainstTableName <>'' ");
                sql.append("AND ist.table_name = " + "'").append(nomTabla).append("'" + " OR tForeignKeyInformation.AgainstTableName = " + "'").append(nomTabla).append("' ");
                sql.append("ORDER BY OnTableSchema, OnTableName ");

                //System.err.println("sqlDep " + sql);
                break;

        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            System.out.println("sera2");
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sql.toString(), bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    public String ejecutaSql2(String nombre_conexion, String sqlstr) {
        EjecutarSQL ejesql = new EjecutarSQL();
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        String valorres = "Error en la consulta";
        if (!sqlstr.equals("")) {
            valorres = ejesql.EjecutarQuery(sqlstr, bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
        }
        return valorres;
    }

    public List<Object[]> recuperarLstEsquemas(String nombreConexion) {
        this.nombre_conexion = nombreConexion;
        recuperaConexion();

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr;
        switch (DBMS) {
            case sybase:
                //GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = " ";
                //System.err.println("sqlstrCol1 " + sqlstr);
                break;
            default:
                //GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                sqlstr = "SELECT catalog_name, schema_name\n"
                        + "FROM information_schema.schemata  \n"
                        + "order by schema_name";
                //System.err.println("sqlstrCol2 " + sqlstr);
                break;

        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            System.out.println("sera2");
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sqlstr, bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    public void pasarParametrosEncriptacion(String tipoCifrado, String parametroAES) {
        this.tipoCifrado = tipoCifrado;
        this.parametroAES = parametroAES;

    }

    public List<Object[]> recuperaInformacionListJSONINFOCAPT(String nombre_conexion, String codigo, int usuario) {
        //this.nombre_conexion = nombre_conexion;

        recuperaConexion();
        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            /*StringBuilder sql = new StringBuilder("select b.codigocartografia,b.descripcion,ST_AsGeoJSON(b.shape)a, ST_AsGeoJSON(ST_Centroid(b.shape)) b,  b.tipocobertura, substring(b.codigocartografia from '..$') ");
            sql.append("from sde.coberturas2 a , sde.coberturas2 b where ST_Intersects( ST_Expand(a.shape,0.001),b.shape) ");
            sql.append("and a.codigocartografia='").append(codigo).append("' and b.tipocobertura in (5,6,7,8,9,12); ");*/
            StringBuilder sql = new StringBuilder("select max(b.codigocartografia) codigocartografia,b.descripcion,");
            sql.append("case when b.tipocobertura=9 then ST_AsGeoJSON(case when ST_Azimuth(ST_Centroid(a.shape), ST_Line_Interpolate_Point(ST_ForceRHR(ST_MakeLine(b.shape)), 0.01)) > ST_Azimuth(ST_Centroid(a.shape), ST_Line_Interpolate_Point(ST_ForceRHR(ST_MakeLine(b.shape)), 0.99)) then ST_Reverse(ST_MakeLine(b.shape)) else ST_MakeLine(b.shape) end) else ST_AsGeoJSON(ST_Union(b.shape)) end a,");
            //sql.append("case when b.tipocobertura=9 then ST_AsGeoJSON(ST_ForceRHR(ST_MakeLine(b.shape))) else ST_AsGeoJSON(ST_Union(b.shape)) end a,");
            sql.append("ST_AsGeoJSON(ST_Centroid(ST_Union(b.shape))) b,");
            sql.append("b.tipocobertura, substring(max(b.codigocartografia) from '..$') ");
            sql.append("from sde.coberturas2 a , sde.coberturas2 b where ST_Intersects( ST_Expand(a.shape,0.001),b.shape) ");
            sql.append("and a.codigocartografia='").append(codigo).append("' and b.tipocobertura in (5,6,7,8,9,12) group by b.descripcion,b.tipocobertura, a.shape order by b.tipocobertura; ");
            System.out.println("sera2");
            System.out.println(sql.toString());
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sql.toString(), bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("sera3");
            return listsyb2;
        }
        return null;
    }

    public List<Object[]> recuperaInformacionListSQL(String nombre_conexion, String sql) {
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            List<Object[]> listsyb2 = esql.EjecutaListSQL(sql, bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            return listsyb2;
        }
        return null;
    }

    public int ejecutaSqlCopy(String nombre_conexion, String sqlstr, String datosList) {
        String tipoCifradoM = "AES";
        String nombreAES = "AES_ACCESS";
        String sqlm = "select valor from administracion.adm_parametro_global where nombre = :nombreAES ";
        Query q = em.createNativeQuery(sqlm);
        q.setParameter("nombreAES", nombreAES);
        String valorAES = (String) q.getSingleResult();
        pasarParametrosEncriptacion(tipoCifradoM, valorAES);

        EjecutarSQL ejesql = new EjecutarSQL();
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        int valorres = -1;
        if (!sqlstr.equals("")) {
            valorres = ejesql.EjecutarSPCopy(sqlstr, datosList, bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
        }
        return valorres;
    }

    public List<Object[]> recuperaInformacionListSQLMobile(String nombre_conexion, String sql) {
        //String parametroAES = baseControlador.getAdmParametroGlobalServicioRemote().buscarXNombre("AES_ACCESS").getValor();
        String tipoCifradoM = "AES";
        String nombreAES = "AES_ACCESS";
        String sqlm = "select valor from administracion.adm_parametro_global where nombre = :nombreAES ";

        Query q = em.createNativeQuery(sqlm);
        q.setParameter("nombreAES", nombreAES);
        String valorAES = (String) q.getSingleResult();
        pasarParametrosEncriptacion(tipoCifradoM, valorAES);

        if (bd != null) {
            List<Object[]> recuperaInformacionMobile = recuperaInformacionListSQL(nombre_conexion, sql);
            return recuperaInformacionMobile;
        }
        return null;
    }

    public int ejecutaSqlMobile(String nombre_conexion, String sqlstr) {
        String tipoCifradoM = "AES";
        String nombreAES = "AES_ACCESS";
        String sqlm = "select valor from administracion.adm_parametro_global where nombre = :nombreAES ";
        Query q = em.createNativeQuery(sqlm);
        q.setParameter("nombreAES", nombreAES);
        String valorAES = (String) q.getSingleResult();
        pasarParametrosEncriptacion(tipoCifradoM, valorAES);

        EjecutarSQL ejesql = new EjecutarSQL();
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        int valorres = -1;
        if (!sqlstr.equals("")) {
            valorres = ejesql.EjecutarSP(sqlstr, bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
        }
        return valorres;
    }

    public AdmBaseDatos recuperaParametros(String nombre_conexion) {
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        return bd;
    }

    public String creaURL(String rdbms, String ipsr, int puerto, String bdd) {
        ConectarBD.ListaDBMS tipoBD = null;
        switch (rdbms) {
            case "postgres":
                tipoBD = ConectarBD.ListaDBMS.postgres;
                break;
            case "mysql":
                tipoBD = ConectarBD.ListaDBMS.mysql;
                break;
            case "sybase":
                tipoBD = ConectarBD.ListaDBMS.sybase;
                break;
            default:
                break;
        }
        ConectarBD traerUrl = new ConectarBD();
        String url = traerUrl.CreaURL(tipoBD, ipsr, puerto, bdd);        
        return url;
    }

    /**
     * crea y recupera la información de la base de datos perteneciente a la
     * conexion, tabla temporal y filtro indicado
     *
     * @param nombre_conexion, nombre de la conexion para recuperar los
     * parametros 22/03/2022
     * @param sqlCrea, nombre de la tabla de donde se recupera los datos
     * @param tabla, nombre de la tabla de donde se recupera los datos
     * @param sqlDrop, sql para borrar la tabla temporal
     * @param usuario, usuario que realiza la peticion
     * @param limite , entero limite de recuperacion de registros
     * @return, lista de objetos de resultado
     */
    public List<Object[]> creaRecuperaInfTablaTmp(String nombre_conexion, String sqlCrea, String tabla, String sqlDrop, int usuario, int limite) {
        this.nombre_conexion = nombre_conexion;
        recuperaConexion();
        Map<ColumnaValor, Object> wherem = new LinkedHashMap<>();

        ConectarBD.ListaDBMS DBMS = ConectarBD.ListaDBMS.valueOf(bd.getRdbms());
        String sqlstr;
        switch (DBMS) {
            case sybase:
                GeneraSQLSybase gensqlSybase = new GeneraSQLSybase();
                sqlstr = gensqlSybase.generaSelectValor(tabla, wherem, 1, limite);
                break;
            default:
                GeneraSQLPostgres gensql = new GeneraSQLPostgres();
                sqlstr = gensql.generaSelectValor(tabla, wherem, 1, limite);
                break;
        }
        EjecutarSQL esql = new EjecutarSQL();
        if (bd != null) {
            List<Object[]> listsyb2 = esql.EjecutaTmpSQL(sqlCrea, sqlstr, sqlDrop, bd.getDriver(), DBMS, bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
            System.out.println("seratmp");
            return listsyb2;
        }
        return null;
    }
//    </editor-fold>
}
