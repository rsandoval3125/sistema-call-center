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
@Table(name = "captura.capt_observacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptObservacion.findAll", query = "SELECT c FROM CaptObservacion c")})
public class CaptObservacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_observacion")
    private Integer idObservacion;
    @Size(max = 30)
    @Column(name = "clave")
    private String clave;
    @Column(name = "num_det")
    private Integer numDet;
    @Size(max = 50)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fechahora_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraEdicion;
    @Size(max = 3)
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "cod_cab", referencedColumnName = "id_cab")
    @ManyToOne
    private CaptCabecera codCab;

    public CaptObservacion() {
    }

    public CaptObservacion(Integer idObservacion) {
        this.idObservacion = idObservacion;
    }

    public Integer getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Integer idObservacion) {
        this.idObservacion = idObservacion;
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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObservacion != null ? idObservacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptObservacion)) {
            return false;
        }
        CaptObservacion other = (CaptObservacion) object;
        if ((this.idObservacion == null && other.idObservacion != null) || (this.idObservacion != null && !this.idObservacion.equals(other.idObservacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptObservacion[ idObservacion=" + idObservacion + " ]";
    }
    
}
