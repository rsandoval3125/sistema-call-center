/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.servicios;

import com.sun.mail.smtp.SMTPTransport;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.facade.CaptCabeceraFacade;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author rmoreano
 */
@Stateless
public class CaptCabeceraServicio implements CaptCabeceraServicioLocal, CaptCabeceraServicioRemote {

    private static final Logger LOGGER = Logger.getLogger(CaptCabeceraServicio.class.getName());
    
    @EJB
    private CaptCabeceraFacade cabeceraDao;
    private final String ENT = "CaptCabecera";
    private final String ID = "idCab";
    private final String CLAVE = "clave";

    @Override
    public String crearFormCabecera(CaptCabecera captCabecera) throws Exception {
        cabeceraDao.crear(captCabecera);
        return "Se ha creado la " + ENT + ":" + captCabecera.getIdCab();
    }

    @Override
    public String editarFormCabecera(CaptCabecera captCabecera) throws Exception {
        cabeceraDao.editar(captCabecera);
        return "Se ha editado la " + ENT + ":" + captCabecera.getIdCab();
    }

    @Override
    public String eliminarFormCabecera(Integer idCabecera) throws Exception {
        cabeceraDao.eliminarGenerico(ENT, ID, idCabecera);
        return "Se ha eliminado la " + ENT + ":" + idCabecera;
    }

    @Override
    public CaptCabecera buscarFormCabPorClave(String claveForm) throws Exception {
        return cabeceraDao.buscarPorPartes(ENT, CLAVE, claveForm);
    }

    @Override
    public CaptCabecera buscarFormCabPorId(Integer pidCab) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("idCab", pidCab);
        return cabeceraDao.buscarPorCampos(ENT, campos);
    }

    @Override
    public List<CaptCabecera> listarFormCabPorClave(String calveForm) throws Exception {
        return cabeceraDao.listarContienePorCampoOrdenada(ENT, CLAVE, calveForm, "idCab", "asc");
    }

    public List<CaptCabecera> listarCabecerasPorParametros(Map<String, Object> parametros) throws Exception { //Se agrega estado logico DA02042020
        return cabeceraDao.listarPorCampos("CaptCabecera", parametros, "codCarCon,clave,codCarCon.control1,codCarCon.codFormulario.codificacion,estadoLogico");
    }

    public CaptCabecera crearCaptCabecera(CaptCabecera captCabecera) throws Exception {
        cabeceraDao.crear(captCabecera);
        return captCabecera;
    }

    public CaptCabecera editarCaptCabecera(CaptCabecera captCabecera) throws Exception {
        cabeceraDao.editar(captCabecera);
        return captCabecera;
    }

    @Override
    public CaptCabecera listarCaptCabeceraPorId(Integer idCab) throws Exception {
        return cabeceraDao.listarCaptCabeceraPorId(idCab);
    }

    @Override
    public CaptCabecera buscarPorCodCarCon(Integer codCarCon) throws Exception {
        return cabeceraDao.buscarPorCodCarCon(codCarCon);
    }

    public List<CaptCabecera> listarFormulariosRepetidosPrecenso(List<String> listaClavesAGenerar) throws Exception {
        return cabeceraDao.listarFormulariosRepetidosPrecenso(listaClavesAGenerar);
    }
    //PARA LAS OMISIONES

    public List<Object[]> omisionesF1(Integer idCab) throws Exception {
        return cabeceraDao.omisionesF1(idCab);
    }

    public List<Object[]> omisionesF3(Integer idCab) throws Exception {
        return cabeceraDao.omisionesF3(idCab);
    }

    public List<Object[]> omisionesF4(Integer idCab) throws Exception {
        return cabeceraDao.omisionesF4(idCab);
    }

    public List<Object[]> listarVarCatPorFormulario(Integer idFormulario) {
        return cabeceraDao.listarVarCatPorFormulario(idFormulario);
    }

    public List<Object[]> listarVarCatFueraRango(Integer idCab, Integer idVar) {
        return cabeceraDao.listarVarCatFueraRango(idCab, idVar);
    }

    public List<Object[]> listarVarCatFueraRangoF1(Integer idCab, Integer idVar, Integer numDet) {
        return cabeceraDao.listarVarCatFueraRangoF1(idCab, idVar, numDet);
    }

    public List<Object[]> omisionesF2(Integer idCab) throws Exception {
        return cabeceraDao.omisionesF2(idCab);
    }

    public List<Object[]> omisionesF5(Integer idCab) throws Exception {
        return cabeceraDao.omisionesF5(idCab);
    }

    @Override
    public CaptCabecera buscarFormCabPorCodCarConYCodForm(Object codCarCon, Object codFormulario, String nomRol) throws Exception {
        Map<String, Object> campos = new HashMap<String, Object>();
        campos.put("codCarCon", codCarCon);
        campos.put("codFormulario", codFormulario);
        campos.put("estadoLogico", true);
        campos.put("info1", nomRol);
        return cabeceraDao.buscarPorCampos(ENT, campos);
    }

    public String trasladarInfDetVHaciaBasePorIdCab(String nombreTablaBase, Integer codCab) throws Exception {
        return cabeceraDao.trasladarInfDetVHaciaBasePorIdCab(nombreTablaBase, codCab);
    }

    public String obtenerValidacionIntegridadPorBaseIdCab(String nombreTablaBase, Integer codCab) throws Exception {
        return cabeceraDao.obtenerValidacionIntegridadPorBaseIdCab(nombreTablaBase, codCab);
    }

    @Override
    public CaptCabecera buscarCabeceraPorClaveInfo4(String claveForm, String columna) throws Exception {
        return cabeceraDao.buscarPorPartes(ENT, columna, claveForm);
    }
    
    @Override
    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {      
        return cabeceraDao.listarEjecutarConsultaObj(nombreProdecimiento,parametrosOrdenados );
    }
    
    @Override
    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento , List<Object> parametrosOrdenados) throws Exception {          
        return cabeceraDao.buscarEjecutarConsultaObj(nombreProdecimiento ,parametrosOrdenados );
    }
    
    @Override
    public List<CaptCabecera> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception {      
        return cabeceraDao.listarEjecutarConsulta(nombreProdecimiento,parametrosOrdenados );
    }
    
    @Override
     public boolean enviarEmail(
             String smptHost, 
             String from, 
             String TO, 
             String mensaje, 
             List<File> listAdjunto, 
             String port, 
             String smtp_username, 
             String smtp_password, 
             String host) throws Exception {
                int smtpPort = Integer.parseInt(port);//587
                String FROMNAME = "INEC - SIAC";
                Properties props = new Properties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.smtp.port", smtpPort); 
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
		try {
                    
			Session session = Session.getInstance(props);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from,FROMNAME));
                        message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			message.setSubject("FORMULARIO ATENCIÓN AL CIUDADANO", "UTF-8");
			message.setSentDate(new Date());
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mensaje, "text/html; charset=UTF-8");
                        System.out.println("2.- smtpPort : "+smtpPort);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (listAdjunto != null && !listAdjunto.isEmpty()) {
				for (File adjunto : listAdjunto) {
					messageBodyPart = new MimeBodyPart();
					messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(adjunto)));
					messageBodyPart.setFileName(adjunto.getName());
					multipart.addBodyPart(messageBodyPart);
				}
			}
			message.setContent(multipart);
                        Transport transport = session.getTransport();
                        transport.connect(host, smtp_username, smtp_password);
                        transport.sendMessage(message, message.getAllRecipients());
                         return true;
		}catch (MessagingException e) {
                        System.out.println("The email was not sent.");
                        System.out.println("2.- Error message: " + e.getMessage());
                        LOGGER.log(Level.SEVERE, null, e);
                        return false;
		}
    }
     
//    public boolean enviarEmail(String host, String de, String para, String mensaje, List<File> listAdjunto) throws Exception {
//        //System.out.println("Valores "+host+" - " +de+" - "+ para+" - "+ mensaje);
//        Properties prop = new Properties();
//        prop.put("mail.smtp.host", host);
//        try {
//            Session session = Session.getInstance(prop);
////            Message msg = getMessage(session, de, para, mensaje);
//            Message msg;
//            if (listAdjunto != null && !listAdjunto.isEmpty()) {
//                 msg = getMessageConAdjunto(session, para, de, mensaje, listAdjunto);
//            }else{
//                 msg = getMessage(session, de, para, mensaje);
//            }
//            Transport.send(msg);
//            return true;
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }
    
//    private static MimeMessage getMessage(Session session, String from, String to, String mensaje) throws Exception {
//        try {
//            MimeMessage msg = new MimeMessage(session);
//            msg.setText(mensaje);
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            msg.setFrom(new InternetAddress(from, "INEC"));
//            msg.setSubject("FORMULARIO ATENCIÓN AL CIUDADANO.");
//            return msg;
//        } catch (java.io.UnsupportedEncodingException ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//            return null;
//        } catch (MessagingException ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    
//    private static MimeMessage getMessageConAdjunto(Session session, String from, String to, String mensaje, List<File> listAdjunto) throws Exception {
//
//        try {
//            MimeMessage msg = new MimeMessage(session);
//            msg.setText(mensaje);
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            msg.setFrom(new InternetAddress(from, "INEC"));
//            msg.setSubject("FORMULARIO ATENCIÓN AL CIUDADANO.");
//            BodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setContent(mensaje, "text/html; charset=UTF-8");
//            Multipart multipart = new MimeMultipart();
//	    multipart.addBodyPart(messageBodyPart);
//            if (listAdjunto != null && !listAdjunto.isEmpty()) {
//                for (File adjunto : listAdjunto) {
//                    messageBodyPart = new MimeBodyPart();
//                    messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(adjunto)));
//                    messageBodyPart.setFileName(adjunto.getName());
//                    multipart.addBodyPart(messageBodyPart);
//                        
//                }
//            }
//            msg.setContent(multipart);
//            return msg;
//        } catch (java.io.UnsupportedEncodingException ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//            return null;
//        } catch (MessagingException ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }

}
