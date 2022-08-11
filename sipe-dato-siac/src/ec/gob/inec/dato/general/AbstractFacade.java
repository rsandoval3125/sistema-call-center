/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.dato.general;

import ec.gob.inec.conexion.ConectarBD;
import ec.gob.inec.conexion.EjecutarSQL;
import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase general para editar
 *
 * @author lponce
 */
public class AbstractFacade {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    // cambio caja blanca 27/07/2021
    private String tabla;
    private AdmBaseDatos bd;
    private List<Object> datos;
    private List<AdmColumna> columnas;
    private ResultSet resultado;

    private EjecutarSQL ejesql;
    //</editor-fold>

    //<editor-fold desc="constructor" defaultstate="collapsed">
    public AbstractFacade(String tabla, AdmBaseDatos bd, List<Object> datos, List<AdmColumna> columnas) {
        this.tabla = tabla;
        this.bd = bd;
        this.datos = datos;
        this.columnas = columnas;
        ejesql = new EjecutarSQL();
    }
    //</editor-fold>

    //<editor-fold desc="get and set" defaultstate="collapsed">
    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public AdmBaseDatos getBd() {
        return bd;
    }

    public void setBd(AdmBaseDatos bd) {
        this.bd = bd;
    }

    public List<Object> getDatos() {
        return datos;
    }

    public void setDatos(List<Object> datos) {
        this.datos = datos;
    }

    public List<AdmColumna> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<AdmColumna> columnas) {
        this.columnas = columnas;
    }

    public EjecutarSQL getEjesql() {
        return ejesql;
    }

    public void setEjesql(EjecutarSQL ejesql) {
        this.ejesql = ejesql;
    }

    public ResultSet getResultado() {
        return resultado;
    }

    public void setResultado(ResultSet resultado) {
        this.resultado = resultado;
    }
    //</editor-fold>

    //<editor-fold desc="Metodos" defaultstate="collapsed">
    /**
     * Edita un registro
     *
     * @param nombreconexion, cadena del nombre de conexion a base de dato
     * @return, resultado de la ejecucion
     */
    public int editar(String nombreconexion) {
        StringBuilder stsql = new StringBuilder("select * from ");
        int codigoPK = 0;
        stsql.append(tabla);
        stsql.append(" where ");
        //for(Object objvar : columnas){
        for (int i = 0; i < columnas.size(); i++) {
            if (columnas.get(i).toString().contains("pk")) {
                stsql.append(columnas.get(i).toString());
                codigoPK = i;
            }
        }
        stsql.append("=");
        stsql.append(datos.get(codigoPK).toString());
        stsql.append(";");
        ObtieneConexion obtcon = new ObtieneConexion();
        bd = obtcon.obtenerConexion(nombreconexion, "", "");

        ResultSet resrs = ejesql.EjecutaRsSQL(stsql.toString(), bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
        if (resrs != null) {
            try {
                if (resrs.first()) {
                    return insertar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                return actualizar();
            }
        } else {
            return actualizar();
        }
        return 0;
    }

    /**
     * actualiza un registro
     *
     * @return, resultado de la ejecucion
     */
    public int actualizar() {
        StringBuilder stsql = new StringBuilder("update ");
        stsql.append(tabla);
        StringBuilder whereupd = new StringBuilder();
        StringBuilder valoresupdt = new StringBuilder();
        int tienecolumnas = 0;
        for (int i = 0; i < columnas.size(); i++) {
            if (!columnas.get(i).toString().contains("pk")) {
                if (tienecolumnas > 0) {
                    valoresupdt.append(",");
                }
                valoresupdt.append(((AdmColumna) columnas.get(i)).getNombre());
                valoresupdt.append("=");
                valoresupdt.append(formatearvalorcampo((AdmColumna) columnas.get(i), datos.get(i)));
                tienecolumnas++;
            } else {
                whereupd.append(((AdmColumna) columnas.get(i)).getNombre());
                whereupd.append("=");
                whereupd.append(formatearvalorcampo((AdmColumna) columnas.get(i), datos.get(i)));
            }
        }
        stsql.append(" set ");
        stsql.append(valoresupdt);
        stsql.append(whereupd);
        stsql.append(";");
        int res = ejesql.EjecutarSP(stsql.toString(), bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
        return res;
    }

    /**
     * Inserta un registro
     *
     * @return, resultado de la ejecucion
     */
    public int insertar() {
        StringBuilder stsql = new StringBuilder("insert into ");
        stsql.append(tabla);
        StringBuilder columnasins = new StringBuilder();
        StringBuilder valoresins = new StringBuilder();
        int tienecolumnas = 0;
        for (int i = 0; i < columnas.size(); i++) {
            if (!columnas.get(i).toString().contains("pk")) {
                if (tienecolumnas > 0) {
                    columnasins.append(",");
                    valoresins.append(",");
                }
                columnasins.append(((AdmColumna) columnas.get(i)).getNombre());
                tienecolumnas++;
                valoresins.append(formatearvalorcampo((AdmColumna) columnas.get(i), datos.get(i)));
            }
        }
        stsql.append("(");
        stsql.append(columnasins);
        stsql.append(" values(");
        stsql.append(valoresins);
        stsql.append(" );");
        int res = ejesql.EjecutarSP(stsql.toString(), bd.getDriver(), ConectarBD.ListaDBMS.valueOf(bd.getRdbms()), bd.getIp(), bd.getPuerto(), bd.getUsuario(), bd.getPassword(), bd.getNombrebdd());
        return res;
    }

    /**
     * Elimina un registro
     *
     * @return, resultado de la ejecucion
     */
    public int eliminar() {
        return 0;
    }

    /**
     * genera el tipo de dato
     *
     * @param col , Admcolumna
     * @param dat , datps de objeto
     * @return, resultado de la ejecucion
     */
    public String formatearvalorcampo(AdmColumna col, Object dat) {
        if (col.getCodTipoDato().toString().contains("character") || col.getCodTipoDato().toString().contains("time") || col.getCodTipoDato().toString().contains("date")) {
            return "'" + dat.toString() + "'";
        } else {
            return dat.toString();
        }
    }
    //</editor-fold>

}
