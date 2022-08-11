/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.servicios; 
import javax.ejb.Local;
import netscape.ldap.LDAPEntry;

/**
 *
 * @author dvaldas
 */
@Local
public interface SegUsuarioServicioLdapLocal {

    public int autenticacionPermanete(String usuario, String password, String hostServidor, int puerto) throws Exception;

    public int autenticacionUsuarioExistente(String usuario, String password, String hostServidor, int puerto) throws Exception;

    public int buscarExistenciaUsuario(String base, String ParamFiltro, String usuarioBusqueda, String password, String host, int port, String dominio) throws Exception;

    public void recorrerDatosBusquedaUsuarios(LDAPEntry entry, String[] attrs) throws Exception;

    public void desconectaLdap() throws Exception;

}
