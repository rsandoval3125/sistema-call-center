/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_operacion_estadistica_dpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmOperacionEstadisticaDpa.findAll", query = "SELECT a FROM AdmOperacionEstadisticaDpa a")})
public class AdmOperacionEstadisticaDpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ope_est_dpa")
    private Integer idOpeEstDpa;
    @Column(name = "cod_dpa")
    private Integer codDpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_operacion_estadistica", referencedColumnName = "id_ope_est")
    @ManyToOne(optional = false)
    private AdmOperacionEstadistica codOperacionEstadistica;
    @JoinColumn(name = "cod_tipo_dpa", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codTipoDpa;

    public AdmOperacionEstadisticaDpa() {
    }

    public AdmOperacionEstadisticaDpa(Integer idOpeEstDpa) {
        this.idOpeEstDpa = idOpeEstDpa;
    }

    public AdmOperacionEstadisticaDpa(Integer idOpeEstDpa, boolean estadoLogico) {
        this.idOpeEstDpa = idOpeEstDpa;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdOpeEstDpa() {
        return idOpeEstDpa;
    }

    public void setIdOpeEstDpa(Integer idOpeEstDpa) {
        this.idOpeEstDpa = idOpeEstDpa;
    }

    public Integer getCodDpa() {
        return codDpa;
    }

    public void setCodDpa(Integer codDpa) {
        this.codDpa = codDpa;
    }

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public AdmOperacionEstadistica getCodOperacionEstadistica() {
        return codOperacionEstadistica;
    }

    public void setCodOperacionEstadistica(AdmOperacionEstadistica codOperacionEstadistica) {
        this.codOperacionEstadistica = codOperacionEstadistica;
    }

    public MetCatalogo getCodTipoDpa() {
        return codTipoDpa;
    }

    public void setCodTipoDpa(MetCatalogo codTipoDpa) {
        this.codTipoDpa = codTipoDpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpeEstDpa != null ? idOpeEstDpa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmOperacionEstadisticaDpa)) {
            return false;
        }
        AdmOperacionEstadisticaDpa other = (AdmOperacionEstadisticaDpa) object;
        if ((this.idOpeEstDpa == null && other.idOpeEstDpa != null) || (this.idOpeEstDpa != null && !this.idOpeEstDpa.equals(other.idOpeEstDpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadisticaDpa[ idOpeEstDpa=" + idOpeEstDpa + " ]";
    }
    
}
