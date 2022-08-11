/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.reportes.ejb.facade.ActualizaBDFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.EJB;

/**
 *
 * @author jaraujo
 */
@Stateless
public class ActualizaBDServicio implements ActualizaBDServicioLocal, ActualizaBDServicioRemote {

    @EJB
    private ActualizaBDFacade actualizaBDFacade;

    @Override
    public List<Object[]> recuperaInformacionListValor2(String nombre_conexion, String tabla, int usuario, int limite) {
        return actualizaBDFacade.recuperaInformacionListValor2(nombre_conexion, tabla, usuario, limite);
    }

    @Override
    public int ejecutaSql(String nombre_conexion, String sqlstr) {
        return actualizaBDFacade.ejecutaSql(nombre_conexion, sqlstr);
    }

    @Override
    public List<Object[]> recuperaNombresTbl(String nombreConexion, String esquema) {
        return actualizaBDFacade.recuperaInformacionListTablas(nombreConexion, esquema);
    }

    @Override
    public List<Object[]> recuperaNombresColumnas(String nombreConexion, String nomTabla, String esquema) {
        return actualizaBDFacade.recuperaInformacionColumnas(nombreConexion, nomTabla, esquema);
    }

    @Override
    public List<Object[]> recuperaDependencias(String nombreConexion, String nomTabla, String esquema) {
        return actualizaBDFacade.recuperaDependencias(nombreConexion, nomTabla, esquema);
    }

    @Override
    public List<Object[]> columnaReferencias(String nombreConexion, String nomTabla, String esquema) {
        return actualizaBDFacade.columnaReferencias(nombreConexion, nomTabla, esquema);
    }

    @Override
    public String ejecutaSql2(String nombre_conexion, String sqlstr) {
        return actualizaBDFacade.ejecutaSql2(nombre_conexion, sqlstr);
    }

    @Override
    public List<Object[]> recuperarEsquemas(String nombreConexion) {
        return actualizaBDFacade.recuperarLstEsquemas(nombreConexion);
    }

    @Override
    public void pasarParametrosEncriptacion(String tipoCifrado, String parametroAES) {
        actualizaBDFacade.pasarParametrosEncriptacion(tipoCifrado, parametroAES);
    }

    @Override
    public List<Object[]> recuperaInformacionListJSONINFOCAPT(String nombre_conexion, String codigo, int usuario) {
        return actualizaBDFacade.recuperaInformacionListJSONINFOCAPT(nombre_conexion, codigo, usuario);
    }

    @Override
    public List<Object[]> recuperaInformacionListSQL(String nombre_conexion, String sql) {
        return actualizaBDFacade.recuperaInformacionListSQL(nombre_conexion, sql);
    }

    public int ejecutaSqlCopy(String nombre_conexion, String sqlstr, String datosList) {
        return actualizaBDFacade.ejecutaSqlCopy(nombre_conexion, sqlstr, datosList);
    }

    @Override
    public List<Object[]> recuperaInformacionListSQLMobile(String nombre_conexion, String sql) {
        return actualizaBDFacade.recuperaInformacionListSQLMobile(nombre_conexion, sql);
    }

    @Override
    public int ejecutaSqlMobile(String nombre_conexion, String sqlstr) {
        return actualizaBDFacade.ejecutaSqlMobile(nombre_conexion, sqlstr);
    }

    @Override
    public AdmBaseDatos recuperaParametros(String nombre_conexion) {
        return actualizaBDFacade.recuperaParametros(nombre_conexion);
    }

    @Override
    public String crearURL(String rdbms, String ipsr, int puerto, String bdd) {
        return actualizaBDFacade.creaURL(rdbms, ipsr, puerto, bdd);
    }

    @Override
    public List<Object[]> creaRecuperaInfTablaTmp(String nombre_conexion, String sqlCrea, String sqlDrop, String tabla, int usuario, int limite) {
        return actualizaBDFacade.creaRecuperaInfTablaTmp(nombre_conexion, sqlCrea, sqlDrop, tabla, usuario, limite);
    }
}
