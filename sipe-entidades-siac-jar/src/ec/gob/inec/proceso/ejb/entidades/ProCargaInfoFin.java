/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "proceso.pro_carga_info_fin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProCargaInfoFin.findAll", query = "SELECT p FROM ProCargaInfoFin p")})
public class ProCargaInfoFin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_car_info_fin")
    private Integer idCarInfoFin;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;

    public ProCargaInfoFin() {
    }

    public ProCargaInfoFin(Integer idCarInfoFin) {
        this.idCarInfoFin = idCarInfoFin;
    }

    public ProCargaInfoFin(Integer idCarInfoFin, boolean estadoLogico) {
        this.idCarInfoFin = idCarInfoFin;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCarInfoFin() {
        return idCarInfoFin;
    }

    public void setIdCarInfoFin(Integer idCarInfoFin) {
        this.idCarInfoFin = idCarInfoFin;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCarInfoFin != null ? idCarInfoFin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProCargaInfoFin)) {
            return false;
        }
        ProCargaInfoFin other = (ProCargaInfoFin) object;
        if ((this.idCarInfoFin == null && other.idCarInfoFin != null) || (this.idCarInfoFin != null && !this.idCarInfoFin.equals(other.idCarInfoFin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProCargaInfoFin[ idCarInfoFin=" + idCarInfoFin + " ]";
    }
    
}
