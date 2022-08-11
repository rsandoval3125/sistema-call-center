/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "muestra.mue_muestra_detalle_asignacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueMuestraDetalleAsignacion.findAll", query = "SELECT m FROM MueMuestraDetalleAsignacion m")})
public class MueMuestraDetalleAsignacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mue_det_asi")
    private Integer idMueDetAsi;
    @Size(max = 245)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_mue_det", referencedColumnName = "id_mue_det")
    @ManyToOne
    private MueMuestraDetalle codMueDet;

    public MueMuestraDetalleAsignacion() {
    }

    public MueMuestraDetalleAsignacion(Integer idMueDetAsi) {
        this.idMueDetAsi = idMueDetAsi;
    }

    public MueMuestraDetalleAsignacion(Integer idMueDetAsi, boolean estadoLogico) {
        this.idMueDetAsi = idMueDetAsi;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdMueDetAsi() {
        return idMueDetAsi;
    }

    public void setIdMueDetAsi(Integer idMueDetAsi) {
        this.idMueDetAsi = idMueDetAsi;
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

    public MueMuestraDetalle getCodMueDet() {
        return codMueDet;
    }

    public void setCodMueDet(MueMuestraDetalle codMueDet) {
        this.codMueDet = codMueDet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMueDetAsi != null ? idMueDetAsi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueMuestraDetalleAsignacion)) {
            return false;
        }
        MueMuestraDetalleAsignacion other = (MueMuestraDetalleAsignacion) object;
        if ((this.idMueDetAsi == null && other.idMueDetAsi != null) || (this.idMueDetAsi != null && !this.idMueDetAsi.equals(other.idMueDetAsi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueMuestraDetalleAsignacion[ idMueDetAsi=" + idMueDetAsi + " ]";
    }
    
}
