/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "metadato.met_catalogo_enlace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetCatalogoEnlace.findAll", query = "SELECT m FROM MetCatalogoEnlace m")})
public class MetCatalogoEnlace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_catenl")
    private Integer idCatenl;
    @Column(name = "tipo_enlace")
    private String tipoEnlace;
    @Column(name = "version")
    private String version;
    @Column(name = "fechahora_enlace")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraEnlace;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_tipo_catalogo1", referencedColumnName = "id_tipo_catalogo")
    @ManyToOne
    private MetTipoCatalogo codTipoCatalogo1;
    @JoinColumn(name = "cod_tipo_catalogo2", referencedColumnName = "id_tipo_catalogo")
    @ManyToOne
    private MetTipoCatalogo codTipoCatalogo2;

    public MetCatalogoEnlace() {
    }

    public MetCatalogoEnlace(Integer idCatenl) {
        this.idCatenl = idCatenl;
    }

    public MetCatalogoEnlace(Integer idCatenl, boolean estadoLogico) {
        this.idCatenl = idCatenl;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCatenl() {
        return idCatenl;
    }

    public void setIdCatenl(Integer idCatenl) {
        this.idCatenl = idCatenl;
    }

    public String getTipoEnlace() {
        return tipoEnlace;
    }

    public void setTipoEnlace(String tipoEnlace) {
        this.tipoEnlace = tipoEnlace;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getFechahoraEnlace() {
        return fechahoraEnlace;
    }

    public void setFechahoraEnlace(Date fechahoraEnlace) {
        this.fechahoraEnlace = fechahoraEnlace;
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

    public MetTipoCatalogo getCodTipoCatalogo1() {
        return codTipoCatalogo1;
    }

    public void setCodTipoCatalogo1(MetTipoCatalogo codTipoCatalogo1) {
        this.codTipoCatalogo1 = codTipoCatalogo1;
    }

    public MetTipoCatalogo getCodTipoCatalogo2() {
        return codTipoCatalogo2;
    }

    public void setCodTipoCatalogo2(MetTipoCatalogo codTipoCatalogo2) {
        this.codTipoCatalogo2 = codTipoCatalogo2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatenl != null ? idCatenl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetCatalogoEnlace)) {
            return false;
        }
        MetCatalogoEnlace other = (MetCatalogoEnlace) object;
        if ((this.idCatenl == null && other.idCatenl != null) || (this.idCatenl != null && !this.idCatenl.equals(other.idCatenl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetCatalogoEnlace[ idCatenl=" + idCatenl + " ]";
    }
    
}
