/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "muestra.ae_generacion_historial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AeGeneracionHistorial.findAll", query = "SELECT a FROM AeGeneracionHistorial a")})
public class AeGeneracionHistorial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generacion_historial")
    private Integer idGeneracionHistorial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechahora_historial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraHistorial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "detalle")
    private String detalle;
    @JoinColumn(name = "cod_generacion", referencedColumnName = "id_generacion")
    @ManyToOne(optional = false)
    private AeGeneracion codGeneracion;
    @JoinColumn(name = "cod_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private SegUsuario codUsuario;

    public AeGeneracionHistorial() {
    }

    public AeGeneracionHistorial(Integer idGeneracionHistorial) {
        this.idGeneracionHistorial = idGeneracionHistorial;
    }

    public AeGeneracionHistorial(Integer idGeneracionHistorial, Date fechahoraHistorial, String detalle) {
        this.idGeneracionHistorial = idGeneracionHistorial;
        this.fechahoraHistorial = fechahoraHistorial;
        this.detalle = detalle;
    }

    public Integer getIdGeneracionHistorial() {
        return idGeneracionHistorial;
    }

    public void setIdGeneracionHistorial(Integer idGeneracionHistorial) {
        this.idGeneracionHistorial = idGeneracionHistorial;
    }

    public Date getFechahoraHistorial() {
        return fechahoraHistorial;
    }

    public void setFechahoraHistorial(Date fechahoraHistorial) {
        this.fechahoraHistorial = fechahoraHistorial;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public AeGeneracion getCodGeneracion() {
        return codGeneracion;
    }

    public void setCodGeneracion(AeGeneracion codGeneracion) {
        this.codGeneracion = codGeneracion;
    }

    public SegUsuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(SegUsuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGeneracionHistorial != null ? idGeneracionHistorial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AeGeneracionHistorial)) {
            return false;
        }
        AeGeneracionHistorial other = (AeGeneracionHistorial) object;
        if ((this.idGeneracionHistorial == null && other.idGeneracionHistorial != null) || (this.idGeneracionHistorial != null && !this.idGeneracionHistorial.equals(other.idGeneracionHistorial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.AeGeneracionHistorial[ idGeneracionHistorial=" + idGeneracionHistorial + " ]";
    }
    
}
