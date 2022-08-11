/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.ejb.utils;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Con reflexion accede a los atributos del Objeto.
 *
 * @author mchasiguasin
 */
public class ReflexionEntidad {

    public static String encripta(String tipoEncriptacion, String valorAEncriptar, String valorAES) throws Exception {
        String encriptado = "";
        switch (tipoEncriptacion) {
            case "MD5":
                encriptado = Cifrado.encriptaMD5(valorAEncriptar);
                break;
            case "AES":
                String aesInec = valorAES;
                encriptado = Cifrado.encriptaAES(aesInec, valorAEncriptar);
                break;
        }
        return encriptado;
    }

    /**
     * Encripta el valor de los atributos de un Objeto.
     *
     * @param obj Objeto entidad nuevo. Los atributos de este objeto seran
     * encriptados.
     * @param columnas Listado de campos que se van a encriptar.
     * @param casoAES Clave semilla para cifrado en caso de requerirse.
     */
    public static void encriptaCampos(Object obj, List<AdmColumna> columnas, String casoAES) {
        for (AdmColumna c : columnas) {
            try {
                Object valor = getProperty(obj, c.getNombre());
                String encriptado = encripta(c.getCodTipoEncr().getNombre(), valor.toString(), casoAES);
                setProperty(obj, c.getNombre(), encriptado);
            } catch (Exception ex) {
                Logger.getLogger(ReflexionEntidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Encripta el valor de los atributos de un Objeto.
     *
     * @param oldObj Objeto entidad anterior
     * @param newObj Objeto entidad nuevo. Los atributos de este objeto seran
     * encriptados.
     * @param columnas Listado de campos que se van a encriptar.
     * @param casoAES
     */
    public static void encriptaCampos(Object oldObj, Object newObj, List<AdmColumna> columnas, String casoAES) {
        for (AdmColumna c : columnas) {
            try {
                Object oldValor = getProperty(oldObj, c.getNombre());
                Object newValor = getProperty(newObj, c.getNombre());
                if (!newValor.toString().equals(oldValor.toString())) {
                    String encriptado = encripta(c.getCodTipoEncr().getNombre(), newValor.toString(), casoAES);
                    setProperty(newObj, c.getNombre(), encriptado);
                }
            } catch (Exception ex) {
                Logger.getLogger(ReflexionEntidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String desencripta(String tipoEncriptacion, String valorAEncriptar, String valorAES) throws Exception {
        String desencriptado = "";
        switch (tipoEncriptacion) {
            case "MD5":
                System.out.println("NO IMPLEMENTADO TODAVIA....");
                desencriptado = valorAEncriptar;
                break;
            case "AES":
                String aesInec = valorAES;
                desencriptado = Cifrado.desencriptaAES(aesInec, valorAEncriptar);
                break;
        }
        return desencriptado;
    }

    public static void desencriptaCampos(Object obj, List<AdmColumna> columnas, String casoAES) {
        for (AdmColumna c : columnas) {
            try {
                Object valor = getProperty(obj, c.getNombre());
                String encriptado = desencripta(c.getCodTipoEncr().getNombre(), valor.toString(), casoAES);
                setProperty(obj, c.getNombre(), encriptado);
            } catch (Exception ex) {
                Logger.getLogger(ReflexionEntidad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Capturar la propiedad de un objeto. Por ejemplo si ud. quiere obtener el
     * valor de la propiedad foo del objeto bar, ud. normalmente llamaria asi
     * {@code bar.getFoo()}. Pero con este método ud. debe llamar asi:
     * {@code ReflexionEntidad.getProperty(bar, "foo")}
     *
     * @param obj Objeto que tiene la propiedad que quiere obtener el valor.
     * @param property Nombre de la propiedad.
     * @return El valor de la propiedad o null si no tiene datos.
     */
    public static Object getProperty(Object obj, String property) {
        Object respuesta = null;

        try {
            /* String nombreMetodo = toPropiedadCamelCase(property);// property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
            Class clazz = obj.getClass();
            Method metodo = clazz.getMethod(nombreMetodo, null);
            respuesta = metodo.invoke(obj, null);*/
            Field field = obj.getClass().getDeclaredField(toAtributoCamelCase(property));
            field.setAccessible(true);
            respuesta = field.get(obj);
        } catch (Exception ex) {
            Logger.getLogger(ReflexionEntidad.class.getName()).log(Level.SEVERE, null, ex);
        }

        return respuesta;
    }

    /**
     * Asigna un valor a un atributo de un Objeto.
     *
     * @param obj Objeto Entidad.
     * @param property Nombre del atributo.
     * @param valor Valor que se va asignar al atriburo.
     * @return El Objeto boolean, es true si fue exitoso y false en case que dio
     * algun error.
     */
    public static boolean setProperty(Object obj, String property, Object valor) {
        try {
            Class clazz = obj.getClass();
            Field campo = clazz.getDeclaredField(toAtributoCamelCase(property));
            campo.setAccessible(true);
            campo.set(obj, valor);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ReflexionEntidad.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private static String toPropiedadCamelCase(String s) {
        String[] parts = s.split("_");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return "get" + camelCaseString;
    }

    private static String toAtributoCamelCase(String s) {
        String[] parts = s.split("_");
        String camelCaseString = "";
        boolean primeraVez = true;
        for (String part : parts) {
            if (primeraVez) {
                primeraVez = false;
                camelCaseString = part.toLowerCase();
            } else {
                camelCaseString = camelCaseString + toProperCase(part);
            }
        }
        return camelCaseString;
    }

    private static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase()
                + s.substring(1).toLowerCase();
    }

}
