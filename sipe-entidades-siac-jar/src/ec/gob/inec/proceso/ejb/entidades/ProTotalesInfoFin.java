/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "proceso.pro_totales_info_fin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProTotalesInfoFin.findAll", query = "SELECT p FROM ProTotalesInfoFin p")})
public class ProTotalesInfoFin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tot_info_fin")
    private Integer idTotInfoFin;
    @Column(name = "anio")
    private Integer anio;
    @Size(max = 2)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "valor")
    private BigInteger valor;
    @Size(max = 50)
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;

    public ProTotalesInfoFin() {
    }

    public ProTotalesInfoFin(Integer idTotInfoFin) {
        this.idTotInfoFin = idTotInfoFin;
    }

    public Integer getIdTotInfoFin() {
        return idTotInfoFin;
    }

    public void setIdTotInfoFin(Integer idTotInfoFin) {
        this.idTotInfoFin = idTotInfoFin;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTotInfoFin != null ? idTotInfoFin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProTotalesInfoFin)) {
            return false;
        }
        ProTotalesInfoFin other = (ProTotalesInfoFin) object;
        if ((this.idTotInfoFin == null && other.idTotInfoFin != null) || (this.idTotInfoFin != null && !this.idTotInfoFin.equals(other.idTotInfoFin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProTotalesInfoFin[ idTotInfoFin=" + idTotInfoFin + " ]";
    }
    
}
