/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios.prueba;

import java.util.Properties;
import java.util.logging.Level;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author cajila
 */
public class pruebaEnvioCorreo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        try {
//            System.out.println("INICIA ENVIO DE CORREO");
//            enviarEmail("smtp.gmail.com", "carlos.ajila.m@gmail.com", "carlos.ajila.m@gmail.com", "envio de prueba");
//            System.out.println("FIN ENVIO DE CORREO");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
    }
    
    public static boolean enviarEmail(String host, String de, String para, String mensaje) throws Exception {
        //System.out.println("Valores "+host+" - " +de+" - "+ para+" - "+ mensaje);
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.auth", false);
//        prop.put("mail.smtp.port", "587");
        try {
            Session session = Session.getInstance(prop);
            Message msg = getMessage(session, de, para, mensaje);
            Transport.send(msg);
            return true;
        } catch (Exception ex) {
             ex.printStackTrace();
            return false;
        }
    }

    private static MimeMessage getMessage(Session session, String from, String to, String mensaje) throws Exception {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setText(mensaje);
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setFrom(new InternetAddress(from, "INEC"));
            msg.setSubject("FORMULARIO AUTOCENSO ALMACENADO.");
            return msg;
        } catch (java.io.UnsupportedEncodingException ex) {
           ex.printStackTrace();
            return null;
        } catch (MessagingException ex) {
             ex.printStackTrace();
            return null;
        }
    }
    
}
