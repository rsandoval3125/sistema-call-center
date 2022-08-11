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
@Table(name = "captura.capt_georeferencia_cambio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptGeoreferenciaCambio.findAll", query = "SELECT c FROM CaptGeoreferenciaCambio c")})
public class CaptGeoreferenciaCambio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_georef_cambio")
    private Integer idGeorefCambio;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Size(max = 2147483647)
    @Column(name = "valor")
    private String valor;
    @Size(max = 2)
    @Column(name = "estado_transmision")
    private String estadoTransmision;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_georef", referencedColumnName = "id_georef")
    @ManyToOne
    private CaptGeoreferencia codGeoref;

    public CaptGeoreferenciaCambio() {
    }

    public CaptGeoreferenciaCambio(Integer idGeorefCambio) {
        this.idGeorefCambio = idGeorefCambio;
    }

    public Integer getIdGeorefCambio() {
        return idGeorefCambio;
    }

    public void setIdGeorefCambio(Integer idGeorefCambio) {
        this.idGeorefCambio = idGeorefCambio;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEstadoTransmision() {
        return estadoTransmision;
    }

    public void setEstadoTransmision(String estadoTransmision) {
        this.estadoTransmision = estadoTransmision;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public CaptGeoreferencia getCodGeoref() {
        return codGeoref;
    }

    public void setCodGeoref(CaptGeoreferencia codGeoref) {
        this.codGeoref = codGeoref;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGeorefCambio != null ? idGeorefCambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptGeoreferenciaCambio)) {
            return false;
        }
        CaptGeoreferenciaCambio other = (CaptGeoreferenciaCambio) object;
        if ((this.idGeorefCambio == null && other.idGeorefCambio != null) || (this.idGeorefCambio != null && !this.idGeorefCambio.equals(other.idGeorefCambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptGeoreferenciaCambio[ idGeorefCambio=" + idGeorefCambio + " ]";
    }
    
}
