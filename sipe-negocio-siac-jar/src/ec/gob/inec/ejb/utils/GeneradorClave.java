/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.ejb.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilitario que permite generar claves dinamicamente.
 *
 * @author mchasiguasin
 */
public class GeneradorClave {

    public static String NUMEROS = "0123456789";

    public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    public static String ESPECIALES = "ñÑ";

    //
    public static String getPinNumber() {
        return getClave(NUMEROS, 4);
    }

    public static String getClave() {
        return getClave(8);
    }

    /**
     * Genera claves dinamicamente con NUMERO + LETRAS MAYUSCULAS + LETRAS
     * MINUSCULAS {@code GeneradorClave.getClave(8);}
     * <strong>Ejm. ST2wBuEW</strong>
     *
     * @param length Tamaño de la clave a generar.
     * @return Texto generado dinámicamente.
     */
    public static String getClave(int length) {
        return getClave(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
    }

    public static String getClave(String key, int length) {
        String pswd = "";

        for (int i = 0; i < length; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }

        return pswd;
    }

}
