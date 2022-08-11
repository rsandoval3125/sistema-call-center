/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.ejb.utils;

import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clase utilitario para el envio de correos.
 *
 * @author mchasiguasin
 */
public class Email {

    private static final Logger LOGGER = Logger.getLogger(Email.class.getName());

    public static boolean enviar(String host, String from, Map<String, DireccionEmail> destinatarios, String asunto, String nombreAplicacion, String mensaje) {
        // capturaParametros();       
        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);

        try {
            Session session = Session.getInstance(prop);
            Message msg = getMessage(session, asunto, nombreAplicacion, from, destinatarios, mensaje);
            Transport.send(msg);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static MimeMessage getMessage(Session session, String asunto, String nombreAplicacion, String from, Map<String, DireccionEmail> destinatarios, String mensaje) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setText(mensaje);

            for (Map.Entry<String, DireccionEmail> dir : destinatarios.entrySet()) {

                switch (dir.getValue().getTipoEnvio()) {
                    case "TO":
                        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dir.getValue().getDireccion()));
                        break;
                    case "CC":
                        msg.addRecipient(Message.RecipientType.CC, new InternetAddress(dir.getValue().getDireccion()));
                        break;
                    case "CCO":
                        msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(dir.getValue().getDireccion()));
                        break;
                    default:
                        LOGGER.log(Level.WARNING, null, "No se encontro un tipo de destinatario para el correo " + dir.getValue().getDireccion());
                        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dir.getValue().getDireccion()));
                }
            }

            msg.setFrom(new InternetAddress(from, nombreAplicacion));
            msg.setSubject(nombreAplicacion + " INEC - " + asunto); // asunto
            msg.setSentDate(new Date());
            msg.setContent(mensaje, "text/html");

            return msg;
        } catch (java.io.UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;

        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
