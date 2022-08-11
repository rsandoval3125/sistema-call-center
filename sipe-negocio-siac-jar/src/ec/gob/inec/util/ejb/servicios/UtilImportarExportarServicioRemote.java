/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.util.ejb.servicios;

import javax.ejb.Remote;

/**
 *
 * @author lponce
 */
@Remote
public interface UtilImportarExportarServicioRemote {

    public int importarDatosCapturaWebMovil() throws Exception;

    public int exportarDatosCapturaWebMovil(String clave) throws Exception;

    public int iniciaEJB() throws Exception;

}
