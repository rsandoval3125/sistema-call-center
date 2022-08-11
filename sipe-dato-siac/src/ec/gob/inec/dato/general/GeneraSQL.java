/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.dato.general;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
//import ec.gob.inec.giiinec.entidades.ColumnaValor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author lponce
 */
public class GeneraSQL {
    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    //</editor-fold>

    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    /**
     * Crea string con sentencia update para ser ejecutada en la base de datos
     *
     * @param tabla, Nombre de la tabla, puede ser compuesta esquema.tabla,
     * base.dueño.tabla, etc
     * @param columnas, Map&lt;AdmColumna, Object&gt; objecto key, valor, de todas las
     * columnas de la tabla
     * @return, cadena update
     */
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
            updatecamposb.append(";");
            return updatecamposb.toString();
        }
    }

    /**
     * Crea string con sentencia where para ser ejecutada en la base de datos
     *
     * @param columnas, Map&lt;AdmColumna, Object&gt; objecto key, valor, de las columnas
     * pk de la tabla
     * @param tipo, entero que indica el tipo de where a generar 1: pk de tabla,
     * 2: pk logico, 3: pk y pkl, 4: pk o pkl, 5:todo
     * @return, cadena where
     */
    public String generaWhere(Map<AdmColumna, Object> columnas, int tipo) {
        StringBuilder wherest = new StringBuilder(" where ");
        StringBuilder whereost = new StringBuilder("( ");
        int cuentacampos = 0;
        int cuentacamposor = 0;
        if (columnas.isEmpty()) {
            return "";
        } else {
            if (tipo == 4) {
                wherest.append("(");
            }
            for (Map.Entry<AdmColumna, Object> var : columnas.entrySet()) {
                AdmColumna col = var.getKey();
                /*if (cuentacampos > 0 && tipo != 2) {
                    wherest.append(" and ");
                }
                if (cuentacamposor > 0 && tipo != 1) {
                    whereost.append(" and ");
                }*/
                switch (tipo) {
                    case 1:  //solo pk
                    case 2: //solo pk2
                    case 3: //pk1 y pk2
                        if (col.getEsPk() == 1 && (tipo == 1 || tipo == 3)) {
                            wherest.append(cuentacampos > 0 ? " and " : "");
                            wherest.append(col.getNombre());
                            wherest.append("=");
                            wherest.append(generaValueTipo(var));
                        }
                        if (col.getEsPk() == 2 && (tipo == 2 || tipo == 3)) {
                            wherest.append(cuentacampos > 0 ? " and " : "");
                            wherest.append(col.getNombre());
                            wherest.append("=");
                            wherest.append(generaValueTipo(var));
                        }
                        cuentacampos++;
                        break;
                    case 4: //pk1 o pk2
                        if (col.getEsPk() == 1) {
                            wherest.append(cuentacampos > 0 ? " and " : "");
                            wherest.append(col.getNombre());
                            wherest.append("=");
                            wherest.append(generaValueTipo(var));
                            cuentacampos++;
                        }
                        if (col.getEsPk() == 2) {
                            whereost.append(cuentacamposor > 0 ? " and " : "");
                            whereost.append(col.getNombre());
                            whereost.append("=");
                            whereost.append(generaValueTipo(var));
                            cuentacamposor++;
                        }
                        break;
                    case 5:
                        wherest.append(cuentacampos > 0 ? " and " : "");
                        wherest.append(col.getNombre());
                        wherest.append("=");
                        wherest.append(generaValueTipo(var));
                        cuentacampos++;
                        break;
                    default:
              // cambio caja blanca 27/07/2021

                }

            }
            if (tipo == 4) {
                if (wherest.length() < 9) {
                    wherest = new StringBuilder(" where ");
                } else {
                    wherest.append(")");
                }
                if (whereost.length() > 10) {
                    if (wherest.length() > 9) {
                        wherest.append(" or ");
                    }
                    wherest.append(whereost);
                    wherest.append(")");
                }

            }
            return wherest.toString();
        }
    }

    /**
     * Crea string con sentencia where valor para ser ejecutada en la base de
     * datos
     *
     * @param columnas, Map&lt;ColumnaValor, Object&gt; objecto key, valor, de las
     * columnas pk de la tabla
     * @param tipo, entero que indica el tipo de where a generar 1: pk de tabla,
     * 2: pk logico, 3: pk y pkl, 4: pk o pkl, 5:todo
     * @return, cadena where
     */
    public String generaWhereValor(Map<ColumnaValor, Object> columnas, int tipo) {
        StringBuilder wherest = new StringBuilder(" where ");
        StringBuilder whereost = new StringBuilder("( ");
        int cuentacampos = 0;
        int cuentacamposor = 0;
        if (columnas.isEmpty()) {
            return "";
        } else {
            if (tipo == 4) {
                wherest.append("(");
            }
            for (Map.Entry<ColumnaValor, Object> var : columnas.entrySet()) {
                ColumnaValor col = var.getKey();
                switch (tipo) {
                    case 1:  //solo pk
                    case 2: //solo pk2
                    case 3: //pk1 y pk2
                        if (col.getColumnaIzquierda().getEsPk() == 1 && (tipo == 1 || tipo == 3)) {
                            wherest.append(cuentacampos > 0 ? " and " : "");
                            wherest.append(col.getColumnaIzquierda().getNombre());
                            wherest.append(col.getComparacion());
                            if (col.getColumnaDerecha() != null) {
                                wherest.append(col.getColumnaDerecha().getNombre());
                            } else {
                                wherest.append(generaValueTipoValor(var));
                            }
                        }
                        if (col.getColumnaIzquierda().getEsPk() == 2 && (tipo == 2 || tipo == 3)) {
                            wherest.append(cuentacampos > 0 ? " and " : "");
                            wherest.append(col.getColumnaIzquierda().getNombre());
                            wherest.append(col.getComparacion());
                            if (col.getColumnaDerecha() != null) {
                                wherest.append(col.getColumnaDerecha().getNombre());
                            } else {
                                wherest.append(generaValueTipoValor(var));
                            }
                        }
                        cuentacampos++;
                        break;
                    case 4: //pk1 o pk2
                        if (col.getColumnaIzquierda().getEsPk() == 1) {
                            wherest.append(cuentacampos > 0 ? " and " : "");
                            wherest.append(col.getColumnaIzquierda().getNombre());
                            wherest.append(col.getComparacion());
                            if (col.getColumnaDerecha() != null) {
                                wherest.append(col.getColumnaDerecha().getNombre());
                            } else {
                                wherest.append(generaValueTipoValor(var));
                            }
                            cuentacampos++;
                        }
                        if (col.getColumnaIzquierda().getEsPk() == 2) {
                            whereost.append(cuentacamposor > 0 ? " and " : "");
                            whereost.append(col.getColumnaIzquierda().getNombre());
                            wherest.append(col.getComparacion());
                            if (col.getColumnaDerecha() != null) {
                                wherest.append(col.getColumnaDerecha().getNombre());
                            } else {
                                wherest.append(generaValueTipoValor(var));
                            }
                            cuentacamposor++;
                        }
                        break;
                    case 5:
                        wherest.append(cuentacampos > 0 ? " and " : "");
                        wherest.append(col.getColumnaIzquierda().getNombre());
                        wherest.append(col.getComparacion());
                        if (col.getColumnaDerecha() != null) {
                            wherest.append(col.getColumnaDerecha().getNombre());
                        } else {
                            wherest.append(generaValueTipoValor(var));
                        }
                        cuentacampos++;
                        break;
                    default:
              // cambio caja blanca 27/07/2021
                }

            }
            if (tipo == 4) {
                if (wherest.length() < 9) {
                    wherest = new StringBuilder(" where ");
                } else {
                    wherest.append(")");
                }
                if (whereost.length() > 10) {
                    if (wherest.length() > 9) {
                        wherest.append(" or ");
                    }
                    wherest.append(whereost);
                    wherest.append(")");
                }

            }
            return wherest.toString();
        }
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
                selectsb.append(";");
            }
        }
        return selectsb.toString();
    }

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
     * @param limit limite de datos
     * @return, cadena select
     */
    public String generaSelectValor(String tabla, Map<ColumnaValor, Object> columnas, int tipo, int limit) {
        StringBuilder selectsb = new StringBuilder("select * from ");
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
        if (limit > 0) {
            selectsb.append(" limit ");
            selectsb.append(limit);
        }
        return selectsb.toString();
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
            insertsb.append(");");
            return insertsb.toString();
        }
    }

    /**
     * Crea string con valor segun el tipo de dato
     *
     * @param var, objeto clave, valor en la que la clave es tipo columna y el
     * valor es string
     * @return, cadena de valor
     */
    public String generaValueTipo(Map.Entry<AdmColumna, Object> var) {
        StringBuilder valuesb = new StringBuilder("");
        AdmColumna col = var.getKey();
        if (var.getValue() == null || var.getValue().toString().equals("")) {
            valuesb.append("null");
        } else {
            int tipoCampo = col.getCodTipoDato().getIdCatalogo();
            switch (tipoCampo) {
                case 1:
                case 3:
                case 5:
                case 6:

                    valuesb.append("'");
                    valuesb.append(var.getValue().toString());
                    valuesb.append("'");

                    break;
                case 2:
                case 4:
                case 7:
                    valuesb.append(var.getValue().toString());
                    break;
                default:
                // cambio caja blanca 27/07/2021
            }
        }
        return valuesb.toString();
    }

    /**
     * Crea string con valor segun el tipo de dato Valor
     *
     * @param var, objeto clave, valor en la que la clave es tipo columna y el
     * valor es string
     * @return, cadena de valor
     */
    public String generaValueTipoValor(Map.Entry<ColumnaValor, Object> var) {
        StringBuilder valuesb = new StringBuilder("");
        ColumnaValor col = var.getKey();
        if (var.getValue() == null || var.getValue().toString().equals("")) {
            valuesb.append("null");
        } else {
            int tipoCampo = col.getColumnaIzquierda().getCodTipoDato().getIdCatalogo();
            switch (tipoCampo) {
                case 1:
                case 3:
                case 5:
                case 6:

                    valuesb.append("'");
                    valuesb.append(var.getValue().toString());
                    valuesb.append("'");

                    break;
                case 2:
                case 4:
                case 7:
                    valuesb.append(var.getValue().toString());
                    break;
                default:
                // cambio caja blanca 27/07/2021     
            }
        }
        return valuesb.toString();
    }

    /**
     * Crea string con sentencia delete para ser ejecutada en la base de datos
     *
     * @param tabla, Nombre de la tabla, puede ser compuesta esquema.tabla,
     * base.dueño.tabla, etc
     * @param columnas, Map&lt;AdmColumna, Object&gt; objecto key, valor, de todas las
     * columnas de la tabla
     * @return, cadena delete
     */
    public String generaDelete(String tabla, Map<AdmColumna, Object> columnas) {
        StringBuilder deletesb = new StringBuilder("delete from  ");
        deletesb.append(tabla);
        Map<AdmColumna, Object> pkcol = new HashMap<AdmColumna, Object>();
        if (columnas.isEmpty()) {
            return "";
        } else {
            for (Map.Entry<AdmColumna, Object> var : columnas.entrySet()) {
                AdmColumna col = var.getKey();
                if (col.getEsPk() == 1) {
                    pkcol.put(col, var.getValue());
                }
            }
        }
        deletesb.append(generaWhere(pkcol, 1));
        deletesb.append(";");
        return deletesb.toString();
    }
    //</editor-fold>
}
