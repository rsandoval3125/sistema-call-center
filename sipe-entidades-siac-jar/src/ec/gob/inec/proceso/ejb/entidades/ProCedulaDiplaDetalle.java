/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

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
@Table(name = "proceso.pro_cedula_dipla_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProCedulaDiplaDetalle.findAll", query = "SELECT p FROM ProCedulaDiplaDetalle p")})
public class ProCedulaDiplaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cedula_dipla_detalle")
    private Integer idCedulaDiplaDetalle;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 2147483647)
    @Column(name = "informacion")
    private String informacion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_cedula_dipla", referencedColumnName = "id_cedula_dipla")
    @ManyToOne
    private ProCedulaDipla codCedulaDipla;

    public ProCedulaDiplaDetalle() {
    }

    public ProCedulaDiplaDetalle(Integer idCedulaDiplaDetalle) {
        this.idCedulaDiplaDetalle = idCedulaDiplaDetalle;
    }

    public ProCedulaDiplaDetalle(Integer idCedulaDiplaDetalle, boolean estadoLogico) {
        this.idCedulaDiplaDetalle = idCedulaDiplaDetalle;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCedulaDiplaDetalle() {
        return idCedulaDiplaDetalle;
    }

    public void setIdCedulaDiplaDetalle(Integer idCedulaDiplaDetalle) {
        this.idCedulaDiplaDetalle = idCedulaDiplaDetalle;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
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

    public ProCedulaDipla getCodCedulaDipla() {
        return codCedulaDipla;
    }

    public void setCodCedulaDipla(ProCedulaDipla codCedulaDipla) {
        this.codCedulaDipla = codCedulaDipla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCedulaDiplaDetalle != null ? idCedulaDiplaDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProCedulaDiplaDetalle)) {
            return false;
        }
        ProCedulaDiplaDetalle other = (ProCedulaDiplaDetalle) object;
        if ((this.idCedulaDiplaDetalle == null && other.idCedulaDiplaDetalle != null) || (this.idCedulaDiplaDetalle != null && !this.idCedulaDiplaDetalle.equals(other.idCedulaDiplaDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProCedulaDiplaDetalle[ idCedulaDiplaDetalle=" + idCedulaDiplaDetalle + " ]";
    }
    
}
