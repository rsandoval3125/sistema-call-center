package ec.gob.inec.ejb.utils;

import java.util.Date;
import java.util.Enumeration;
import netscape.ldap.*;

/**
 *
 * @author dvaldas
 */
public class SegUsuarioServicioLdap {

    /**
     * Permite Autenticar usuario y contraseña ante un servidor LDAP mediante
     * SSL
     *
     * @param usuario (String) Usuario ingresado para loggearse
     * @param password (String) Password ingresado para loggearse
     * @return true si el login se realizo exitosamen, false si hubo fallo
     */
    int respuesta;
    private String nombreRegistro;
    private String apellidoRegistro;
    private String mailRegistro;
    LDAPConnection ldAutPer = null;
    String[] ATTRS = {"givenName", "sn", "mail"};
    Date fechaActual;

    public int autenticacionPermanete(String usuario, String password, String hostServidor, int puerto) {
        try {
            ldAutPer = new LDAPConnection();
            ldAutPer.connect(hostServidor, puerto, usuario, password);
            if (ldAutPer.isAuthenticated()) {
                respuesta = 1; //Autenticado (Usuario permanete)                 
            } else {
                respuesta = 8;
            }
        } catch (LDAPException e) {
            switch (e.getLDAPResultCode()) {
                case LDAPException.INVALID_CREDENTIALS:
                    respuesta = 2; //Credenciales no válidas (usuario permanente)
                    break;
                case LDAPException.CONNECT_ERROR:
                    respuesta = 3; //Error de conexion de parametros (usuario permanente)
                    break;
            }
        }
        return respuesta;
    }

    public int autenticacionUsuarioExistente(String usuario, String password, String hostServidor, int puerto) {
        try {
            ldAutPer = new LDAPConnection();
            ldAutPer.connect(hostServidor, puerto, usuario, password);
            if (ldAutPer.isAuthenticated()) {
                respuesta = 4; //Usuario logueado autenticado
            } else {
                respuesta = 8; //Usuario logueado credenciales no validas
            }
        } catch (LDAPException e) {
            switch (e.getLDAPResultCode()) {
                case LDAPException.INVALID_CREDENTIALS:
                    respuesta = 5; //Usuario logueado credenciales no validas
                    break;
                case LDAPException.CONNECT_ERROR:
                    respuesta = 6; //Usuario logueado error parametros de conexion
                    break;
            }
        }
        return respuesta;
    }

    public int buscarExistenciaUsuario(String base, String ParamFiltro, String usuarioBusqueda, String password, String host, int port, String dominio) {
        int status = -1;
        String filtro = ParamFiltro + "=" + usuarioBusqueda;
        try {
            LDAPSearchResults res = ldAutPer.search(base, LDAPConnection.SCOPE_SUB, filtro, ATTRS, false);
            while (res.hasMoreElements()) {
                LDAPEntry entry = res.next();
                recorrerDatosBusquedaUsuarios(entry, ATTRS);
                String nombreUsuarioCompleto = usuarioBusqueda + dominio;
                autenticacionUsuarioExistente(nombreUsuarioCompleto, password, host, port);
                break;
            }
            status = 0;
        } catch (LDAPException e) {
            respuesta = 7; //Error de existencia El usuario no esta registrado en el active directory
            desconectaLdap();
        }
        return respuesta;
    }

    public void recorrerDatosBusquedaUsuarios(LDAPEntry entry, String[] attrs) {
        LDAPAttributeSet entryAttrs = entry.getAttributeSet();
        Enumeration attrEnum = entryAttrs.getAttributes();
        while ((attrEnum != null) && (attrEnum.hasMoreElements())) {
            LDAPAttribute attr = (LDAPAttribute) attrEnum.nextElement();
            Enumeration enumVals = attr.getStringValues();
            boolean hasVals = false;
            while ((enumVals != null) && (enumVals.hasMoreElements())) {
                String val = (String) enumVals.nextElement();
                if (attr.getName().equals("sn")) {
                    apellidoRegistro = val;
                }
                if (attr.getName().equals("givenName")) {
                    nombreRegistro = val;
                }
                if (attr.getName().equals("mail")) {
                    mailRegistro = val;
                }
                hasVals = true;
            }
        }
    }

    public void desconectaLdap() {
        if ((ldAutPer != null) && ldAutPer.isConnected()) {
            try {
                ldAutPer.disconnect();
            } catch (LDAPException e) {
                System.out.println("Error: " + e.toString());
            }
        }
    }

    /* public String getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(String nombreRegistro) {
        this.nombreRegistro = nombreRegistro;
    }

    public String getApellidoRegistro() {
        return apellidoRegistro;
    }

    public void setApellidoRegistro(String apellidoRegistro) {
        this.apellidoRegistro = apellidoRegistro;
    }

    public String getMailRegistro() {
        return mailRegistro;
    }

    public void setMailRegistro(String mailRegistro) {
        this.mailRegistro = mailRegistro;
    }*/
}
