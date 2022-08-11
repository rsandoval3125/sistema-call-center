/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

import java.io.Serializable;
import javax.persistence.Transient;

/**
 *
 * @author magarcia
 */
public class OperadorAsignacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public OperadorAsignacion() {
        
    }

    public OperadorAsignacion(Integer idDetalle, Integer idOperador, Integer numAsignaciones, Integer codProvincia) {
        this.idDetalle = idDetalle;
        this.idOperador = idOperador;
        this.numAsignaciones = numAsignaciones;
        this.codProvincia = codProvincia;
    }
    
    @Transient
    private Integer idDetalle;
    @Transient
    private Integer idOperador;
    @Transient
    private Integer numAsignaciones;
    @Transient
    private Integer codProvincia;

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    public Integer getNumAsignaciones() {
        return numAsignaciones;
    }

    public void setNumAsignaciones(Integer numAsignaciones) {
        this.numAsignaciones = numAsignaciones;
    }   

    public Integer getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(Integer codProvincia) {
        this.codProvincia = codProvincia;
    }   
    
}
