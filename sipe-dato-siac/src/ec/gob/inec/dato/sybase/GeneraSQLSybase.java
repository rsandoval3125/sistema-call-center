/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.dato.sybase;

import ec.gob.inec.dato.general.GeneraSQL;
import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.dato.general.ColumnaValor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lponce
 */
public class GeneraSQLSybase extends GeneraSQL {
    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    //</editor-fold>

    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    /**
     * Crea string con sentencia select * con mayor o menor para ser ejecutada
     * en la base de datos
     *
     * @param tabla, Nombre de la tabla, puede ser compuesta esquema.tabla,
     * base.dueño.tabla, etc
     * @param columnas, Map&lt;ColumnaValor, Object&gt; objecto key, valor, de todas
     * las columnas de la tabla
     * @param tipo, tipo de select, 0 para control interno, 1 para select de
     * usuario
     * @param limit, limite de consulta SQL
     * @return, cadena select
     */
    @Override
    public String generaSelectValor(String tabla, Map<ColumnaValor, Object> columnas, int tipo, int limit) {
        StringBuilder selectsb = new StringBuilder("select ");
        if (limit > 0) {
            selectsb.append("top ");
            selectsb.append(limit);
        }
        selectsb.append(" * from ");
        selectsb.append(tabla);
        Map<ColumnaValor, Object> pkcol = new HashMap<ColumnaValor, Object>();
        if (!columnas.isEmpty()) {
            for (Map.Entry<ColumnaValor, Object> var : columnas.entrySet()) {
                ColumnaValor col = var.getKey();
                if (col.getColumnaIzquierda().getEsPk() >= 1) {
                    pkcol.put(col, var.getValue());
                }
                if (tipo == 1) {
                    pkcol.put(col, var.getValue());
                }
            }
            if (!pkcol.isEmpty()) {
                selectsb.append(generaWhereValor(pkcol, 4));
                //selectsb.append(";");
            }
        }
        return selectsb.toString();
        }    
    
    /**
     * Crea string con sentencia select * para ser ejecutada en la base de datos
     *
     * @param tabla, Nombre de la tabla, puede ser compuesta esquema.tabla,
     * base.dueño.tabla, etc
     * @param columnas, Map&lt;AdmColumna, Object&gt; objecto key, valor, de todas las
     * columnas de la tabla
     * @param tipo, tipo de select, 0 para control interno, 1 para select de
     * usuario
     * @return, cadena select
     */
    @Override
    public String generaSelect(String tabla, Map<AdmColumna, Object> columnas, int tipo) {
        StringBuilder selectsb = new StringBuilder("select * from ");
        selectsb.append(tabla);
        Map<AdmColumna, Object> pkcol = new HashMap<AdmColumna, Object>();
        if (!columnas.isEmpty()) {
            for (Map.Entry<AdmColumna, Object> var : columnas.entrySet()) {
                AdmColumna col = var.getKey();
                if (col.getEsPk() >= 1) {
                    pkcol.put(col, var.getValue());
                }
                if (tipo == 1) {
                    pkcol.put(col, var.getValue());
                }
            }
            if (!pkcol.isEmpty()) {
                selectsb.append(generaWhere(pkcol, 4));
            }
        }
        return selectsb.toString();
    }
    
    /**
     * Crea string con sentencia update para ser ejecutada en la base de datos
     *
     * @param tabla, Nombre de la tabla, puede ser compuesta esquema.tabla,
     * base.dueño.tabla, etc
     * @param columnas, Map&lt;AdmColumna, Object&gt; objecto key, valor, de todas las
     * columnas de la tabla
     * @return, cadena update
     */
    @Override
    public String generaUpdate(String tabla, Map<AdmColumna, Object> columnas) {
        StringBuilder updatecamposb = new StringBuilder("update ");
        updatecamposb.append(tabla);
        updatecamposb.append(" set ");
        Map<AdmColumna, Object> pkcol = new HashMap<AdmColumna, Object>();
        int cuentacampos = 0;
        if (columnas.isEmpty()) {
            return "";
        } else {
            for (Map.Entry<AdmColumna, Object> var : columnas.entrySet()) {
                if (var.getValue() != null && !var.getValue().toString().equals("")) {
                    if (cuentacampos > 0) {
                        updatecamposb.append(" , ");
                    }
                    AdmColumna col = var.getKey();
                    updatecamposb.append(col.getNombre());
                    updatecamposb.append("=");
                    if (col.getEsPk() == 1) {
                        pkcol.put(col, var.getValue());
                    }
                    updatecamposb.append(generaValueTipo(var));

                    cuentacampos++;
                }
            }
            updatecamposb.append(generaWhere(pkcol, 1));
            return updatecamposb.toString();
        }
    }
    
    /**
     * Crea string con sentencia Insert para ser ejecutada en la base de datos
     *
     * @param tabla, Nombre de la tabla, puede ser compuesta esquema.tabla,
     * base.dueño.tabla, etc
     * @param columnas, Map&lt;AdmColumna, Object&gt; objecto key, valor, de todas las
     * columnas de la tabla
     * @return, cadena insert
     */
    @Override
    public String generaInsert(String tabla, Map<AdmColumna, Object> columnas) {
        StringBuilder insertsb = new StringBuilder("insert into  ");
        StringBuilder valuessb = new StringBuilder(") VALUES (");
        insertsb.append(tabla);
        insertsb.append(" (");
        Map<AdmColumna, Object> pkcol = new HashMap<AdmColumna, Object>();
        int cuentacampos = 0;
        if (columnas.isEmpty()) {
            return "";
        } else {
            for (Map.Entry<AdmColumna, Object> var : columnas.entrySet()) {
                AdmColumna col = var.getKey();
                if (col.getCodTipoDato().getIdCatalogo() != 7) {
                    if (col.getEsPk() == 1 && var.getValue().toString().equals("0")) {
                          // cambio caja blanca 27/07/2021
//                        col = col;
                    } else {
                        if (cuentacampos > 0) {
                            insertsb.append(" , ");
                            valuessb.append(" , ");
                        }
                        insertsb.append(col.getNombre());
                        if (col.getEsPk() == 1) {
                            pkcol.put(col, var.getValue());
                        }
                        if (var.getValue() == null || var.getValue().toString().equals("")) {
                            valuessb.append("null");
                        } else {
                            valuessb.append(generaValueTipo(var));
                        }
                        cuentacampos++;
                    }
                }
            }
            insertsb.append(valuessb);
            return insertsb.toString();
        }
    }
    
    
    //</editor-fold>
}
