
package ec.gob.inec.captuta.jsf.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.primefaces.PrimeFaces;


public class UtilSiac {
	

    private static final Properties p = System.getProperties();
    public static final String JNDI_SIPE_NO_XA = "java:/siacDS";
    public static final String IMG_ESCUDO = "gobierno_nacional_rep_ecuador.png"; 
    public static final String IMG_INEC = "INEC.png";
    public static final String IMG_PIE_PAGINA = "pie_pagina_2.jpg";
    private static final String sep = p.getProperty("file.separator");

    public static boolean estaEnBlanco(String s){
            return s == null || s.trim().isEmpty();
    }
    public static int digitoVerificador(@NotNull String codigo) {

    	HashMap<String, Integer> letrasDigitos = new HashMap<>();
    	letrasDigitos.put("A", 10);
        letrasDigitos.put("B", 11);
        letrasDigitos.put("C", 12);
        letrasDigitos.put("D", 13);
        letrasDigitos.put("E", 14);
        letrasDigitos.put("F", 15);
        letrasDigitos.put("G", 16);
        letrasDigitos.put("H", 17);
        letrasDigitos.put("I", 18);
        letrasDigitos.put("J", 19);
        letrasDigitos.put("K", 20);
        letrasDigitos.put("L", 21);
        letrasDigitos.put("M", 22);
        letrasDigitos.put("N", 23);
        letrasDigitos.put("O", 24);
        letrasDigitos.put("P", 25);
        letrasDigitos.put("Q", 26);
        letrasDigitos.put("R", 27);
        letrasDigitos.put("S", 28);
        letrasDigitos.put("T", 29);
        letrasDigitos.put("U", 30);
        letrasDigitos.put("V", 31);
        letrasDigitos.put("W", 32);
        letrasDigitos.put("X", 33);
        letrasDigitos.put("Y", 34);
        letrasDigitos.put("Z", 35);
        letrasDigitos.put("<", 0);
    	
        List<Integer> nuevoCodigo = new ArrayList<Integer>();
        String cod;

        for (char c : codigo.toCharArray()) {

            cod = c + "";

            if ("0123456789".contains(cod)) {

                nuevoCodigo.add(Integer.parseInt(cod));
            }
            else {

                nuevoCodigo.add(letrasDigitos.get(cod));
            }
        }

        Integer sum = 0;
        int[] ponderacion = { 7, 3, 1 };
        int p = 0;

        for (Integer n : nuevoCodigo) {

            sum += (ponderacion[p % 3] * n);

            p++;
        }

        return sum % 10;
    }
    
   public static Connection getConnection(String jndiDataSourceName) {
        Connection connection = null;
        try {
          InitialContext context = new InitialContext();
          DataSource dataSource = (DataSource) context.lookup(jndiDataSourceName);
          connection = dataSource.getConnection();
        } catch (NamingException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return connection;
    }	


	
    /****
     * Permite obtener la fecha actual del sistema y devuelve un calendar
     * @return
     */
    public static java.util.Calendar fechaActualEnCalendar(){
            java.util.Calendar _c = GregorianCalendar.getInstance();
            _c.setTime(new Date());
            //_c.set(Calendar.HOUR_OF_DAY, 23);
            //_c.set(Calendar.MINUTE, 59);
            //_c.set(Calendar.SECOND, 59);
            //_c.set(Calendar.MILLISECOND, 999);
            return _c;
    }
    
    public static void enviarVariableVista(String variable, boolean valor) {
            PrimeFaces requestContext = PrimeFaces.current();
            requestContext.ajax().addCallbackParam(variable, valor);
    }
    
    public static void presentaMensaje(Severity severity, String mensaje) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, "MENSAJE DEL SISTEMA", mensaje));
    }

    public static void presentaMensaje(Severity severity, String mensaje, String variable, boolean valor) {
            presentaMensaje(severity, mensaje);
            enviarVariableVista(variable, valor);
    }
    
    public static String getRutaReportes() {
        return FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes")+ sep;
    }
    
        
    public static void mostrarMensaje(String mensaje, String icon, String title) {
        try {
             PrimeFaces requestContext = PrimeFaces.current();
             requestContext.executeScript("swal('" + title + "', '" + mensaje + "', '" + icon + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

	
}

