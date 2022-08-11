/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "captura.capt_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptEstado.findAll", query = "SELECT c FROM CaptEstado c")})
public class CaptEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado")
    private Integer idEstado;
    @Size(max = 30)
    @Column(name = "clave")
    private String clave;
    @Column(name = "num_det")
    private Integer numDet;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechahora_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraEdicion;
    @JoinColumn(name = "cod_cab", referencedColumnName = "id_cab")
    @ManyToOne
    private CaptCabecera codCab;

    public CaptEstado() {
    }

    public CaptEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getNumDet() {
        return numDet;
    }

    public void setNumDet(Integer numDet) {
        this.numDet = numDet;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechahoraEdicion() {
        return fechahoraEdicion;
    }

    public void setFechahoraEdicion(Date fechahoraEdicion) {
        this.fechahoraEdicion = fechahoraEdicion;
    }

    public CaptCabecera getCodCab() {
        return codCab;
    }

    public void setCodCab(CaptCabecera codCab) {
        this.codCab = codCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptEstado)) {
            return false;
        }
        CaptEstado other = (CaptEstado) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptEstado[ idEstado=" + idEstado + " ]";
    }
    
}
