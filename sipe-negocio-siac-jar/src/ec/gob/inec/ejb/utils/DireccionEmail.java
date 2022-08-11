/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.ejb.utils;

/**
 * Objeto que persiste en memoria con informacion como direccion de correo y
 * tipo de envio
 *
 * @author mchasiguasin
 */
public class DireccionEmail {

    private String tipoEnvio;
    private String direccion;

    public DireccionEmail(String tipoEnvio, String direccion) {
        this.tipoEnvio = tipoEnvio;
        this.direccion = direccion;
    }

    /**
     * Método que permite recuperar el tipo de envio.
     *
     * @return Texto como TO, CC, CCO.
     */
    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    /**
     * Método que permite recuperar la direccion de correo.
     *
     * @return Direccion de correo como correo@dominio.com
     */
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
