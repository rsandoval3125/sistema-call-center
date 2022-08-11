
package ec.gob.inec.captuta.jsf.util;


import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidadorUtil {
    private static final String PATRON_NUMERO_ENTERO = "\\d*";
    


    /**
     * Permite validar que la cédula ingresada sea correcta.
     *
     * @param cedula
     *            El número de cédula a validar.
     * @return true, si la cédula ingresada es correcta.
     */
    public static boolean validaCedula(String cedula) {

            try {
                    int[] modulo9 = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    boolean valorRetorno = true;
                    Pattern pat = null;
                    Matcher mat = null;
                    pat = Pattern.compile("^([a-zA-Z\\s+]{2,80})$");
                    mat = pat.matcher(cedula);
                    BigDecimal verif = new BigDecimal(0);
                    if (mat.find()) {
                            return false;
                    } else {
                            if (cedula.length() != 10) {
                                    valorRetorno = false;
                            } else {
                                    for (int i = 0; i < 9; i++) {
                                            BigDecimal temp = new BigDecimal(new BigDecimal(
                                                            cedula.substring(i, (i + 1))).multiply(
                                                            new BigDecimal(modulo9[i])).toString());
                                            if (temp.doubleValue() > 9) {
                                                    temp = temp.subtract(new BigDecimal("9"));
                                            }
                                            verif = verif.add(temp);
                                    }
                                    if (verif.doubleValue() % 10 == 0) {
                                            if (Integer.parseInt(cedula.substring(9, 10)) == 0) {
                                                    valorRetorno = true;
                                            } else {
                                                    valorRetorno = false;
                                            }
                                    } else if ((10 - (verif.doubleValue() % 10)) == Integer
                                                    .parseInt(cedula.substring(9, 10))) {
                                            valorRetorno = true;
                                    } else {
                                            valorRetorno = false;
                                    }
                            }
                            return valorRetorno;
                    }
            } catch (Exception e) {
                    return false;
            }
    }
    
    /**
	 * Permite validar si el RUC ingresado es correcto.
	 *
	 * @param RUC
	 *            El ruc a validar.
	 * @return true, si el ruc ingresado es correcto.
	 */
    public static boolean validaRUC(String RUC) {

            int[] modulo11 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            boolean valorRetorno = true;
            BigDecimal verif = new BigDecimal(0);
            Pattern pat = null;
            Matcher mat = null;
            pat = Pattern.compile("^([a-zA-Z\\s+]{2,80})$");
            mat = pat.matcher(RUC);
            if (mat.find()) {
                    return false;
            } else {
                    if (RUC.length() < 13) {
                            valorRetorno = false;
                    } else if (Integer.parseInt(RUC.substring(0, 2)) < 1
                                    || Integer.parseInt(RUC.substring(0, 2)) > 22) {
                            valorRetorno = false;
                    } else {
                            if (Integer.parseInt(RUC.substring(2, 3)) < 0
                                            || (Integer.parseInt(RUC.substring(2, 3)) > 6 && Integer
                                                            .parseInt(RUC.substring(2, 3)) < 9)) {
                                    valorRetorno = false;
                            } else {
                                    if (Integer.parseInt(RUC.substring(2, 3)) == 9) { // sociedad
                                            // privada
                                            // o
                                            // extranjeros
                                            if (!RUC.substring(10, 13).equals("001")) {
                                                    valorRetorno = false;
                                            } else {
                                                    modulo11[0] = 4;
                                                    modulo11[1] = 3;
                                                    modulo11[2] = 2;
                                                    modulo11[3] = 7;
                                                    modulo11[4] = 6;
                                                    modulo11[5] = 5;
                                                    modulo11[6] = 4;
                                                    modulo11[7] = 3;
                                                    modulo11[8] = 2;
                                                    for (int i = 0; i < 9; i++) {
                                                            verif = verif.add(new BigDecimal(RUC.substring(
                                                                            i, (i + 1))).multiply(new BigDecimal(
                                                                            modulo11[i])));
                                                    }
                                                    if (verif.doubleValue() % 11 == 0) {
                                                            if (Integer.parseInt(RUC.substring(9, 10)) == 0) {
                                                                    valorRetorno = true;
                                                            } else {
                                                                    valorRetorno = false;
                                                            }
                                                    } else if ((11 - (verif.doubleValue() % 11)) == Integer
                                                                    .parseInt(RUC.substring(9, 10))) {
                                                            valorRetorno = true;
                                                    } else {
                                                            valorRetorno = false;
                                                    }
                                            }
                                    } else if (Integer.parseInt(RUC.substring(2, 3)) == 6) { // sociedades
                                            // públicas
                                            if (!RUC.substring(10, 13).equals("001")) {
                                                    valorRetorno = false;
                                            } else {
                                                    modulo11[0] = 3;
                                                    modulo11[1] = 2;
                                                    modulo11[2] = 7;
                                                    modulo11[3] = 6;
                                                    modulo11[4] = 5;
                                                    modulo11[5] = 4;
                                                    modulo11[6] = 3;
                                                    modulo11[7] = 2;
                                                    for (int i = 0; i < 8; i++) {
                                                            verif = verif.add(new BigDecimal(RUC.substring(
                                                                            i, (i + 1))).multiply(new BigDecimal(
                                                                            modulo11[i])));
                                                    }
                                                    if (verif.doubleValue() % 11 == 0) {
                                                            if (Integer.parseInt(RUC.substring(8, 9)) == 0) {
                                                                    valorRetorno = true;
                                                            } else {
                                                                    valorRetorno = false;
                                                            }
                                                    } else if ((11 - (verif.doubleValue() % 11)) == Integer
                                                                    .parseInt(RUC.substring(8, 9))) {
                                                            valorRetorno = true;
                                                    } else {
                                                            valorRetorno = false;
                                                    }
                                            }
                                    } else if (Integer.parseInt(RUC.substring(2, 3)) < 6
                                                    && Integer.parseInt(RUC.substring(2, 3)) >= 0) { // personas
                                            // naturales
                                            if (!RUC.substring(10, 13).equals("001")) {
                                                    valorRetorno = false;
                                            } else {
                                                    valorRetorno = validaCedula(RUC.substring(0, 10));
                                            }
                                    }
                            }
                    }
                    return valorRetorno;
            }
    }
    
    
    /**
     * Comprueba si es email.
     *
     * @param email
     *            El email.
     * @return true, si es email.
     */
    public static boolean validaEmail(String email) {

            Pattern pat = null;
            Matcher mat = null;
            String formato = "^[_a-z0-9-]+(\\\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\\\.[a-z0-9-]+)*(\\\\.[a-z]{2,3})$";
            // ^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$
            pat = Pattern.compile(formato);

            mat = pat.matcher(email);
            if (mat.find()) {
                    return true;
            } else {
                    return false;
            }
    }

    
    public static String validarDobleEspacios(String cadena){
        try {
            String resultado = cadena.replaceAll(" +", " ");
            return resultado.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static void consultaCedulaApi(){
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

  
        

}
