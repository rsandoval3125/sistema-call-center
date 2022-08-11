/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jaraujo
 */
@Remote
public interface ActualizaBDServicioRemote {

    public List<Object[]> recuperaInformacionListValor2(String nombre_conexion, String tabla, int usuario, int limite) throws Exception;

    public int ejecutaSql(String nombre_conexion, String sqlstr) throws Exception;

    public List<Object[]> recuperaNombresTbl(String nombreConexion, String esquema) throws Exception;

    public List<Object[]> recuperaNombresColumnas(String nombreConexion, String nomTabla, String esquema) throws Exception;

    public List<Object[]> recuperaDependencias(String nombreConexion, String nomTabla, String esquema) throws Exception;

    public List<Object[]> columnaReferencias(String nombreConexion, String nomTabla, String esquema) throws Exception;

    public String ejecutaSql2(String nombre_conexion, String sqlstr) throws Exception;

    public List<Object[]> recuperarEsquemas(String nombreConexion) throws Exception;

    public void pasarParametrosEncriptacion(String tipoCifrado, String parametroAES) throws Exception;

    public List<Object[]> recuperaInformacionListJSONINFOCAPT(String nombre_conexion, String codigo, int usuario) throws Exception;

    public List<Object[]> recuperaInformacionListSQL(String nombre_conexion, String sql) throws Exception;

    public int ejecutaSqlCopy(String nombre_conexion, String sqlstr, String datosList);

    public List<Object[]> recuperaInformacionListSQLMobile(String nombre_conexion, String sql);

    public int ejecutaSqlMobile(String nombre_conexion, String sqlstr);

    public AdmBaseDatos recuperaParametros(String nombre_conexion) throws Exception;

    public String crearURL(String rdbms, String ipsr, int puerto, String bdd) throws Exception;

    public List<Object[]> creaRecuperaInfTablaTmp(String nombre_conexion, String sqlCrea, String sqlDrop, String tabla, int usuario, int limite) throws Exception;

}
