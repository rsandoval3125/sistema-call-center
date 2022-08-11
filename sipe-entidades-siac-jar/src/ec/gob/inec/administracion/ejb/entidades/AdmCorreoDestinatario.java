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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_correo_destinatario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmCorreoDestinatario.findAll", query = "SELECT a FROM AdmCorreoDestinatario a")})
public class AdmCorreoDestinatario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_correo_destinatario")
    private Integer idCorreoDestinatario;
    @Column(name = "estado_destinatario")
    private Boolean estadoDestinatario;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_correo_cab", referencedColumnName = "id_correo_cab")
    @ManyToOne
    private AdmCorreoCab codCorreoCab;
    @JoinColumn(name = "cod_personal", referencedColumnName = "id_personal")
    @ManyToOne
    private AdmPersonal codPersonal;
    @JoinColumn(name = "cod_tipo_destinatario", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoDestinatario;

    public AdmCorreoDestinatario() {
    }

    public AdmCorreoDestinatario(Integer idCorreoDestinatario) {
        this.idCorreoDestinatario = idCorreoDestinatario;
    }

    public Integer getIdCorreoDestinatario() {
        return idCorreoDestinatario;
    }

    public void setIdCorreoDestinatario(Integer idCorreoDestinatario) {
        this.idCorreoDestinatario = idCorreoDestinatario;
    }

    public Boolean getEstadoDestinatario() {
        return estadoDestinatario;
    }

    public void setEstadoDestinatario(Boolean estadoDestinatario) {
        this.estadoDestinatario = estadoDestinatario;
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

    public AdmCorreoCab getCodCorreoCab() {
        return codCorreoCab;
    }

    public void setCodCorreoCab(AdmCorreoCab codCorreoCab) {
        this.codCorreoCab = codCorreoCab;
    }

    public AdmPersonal getCodPersonal() {
        return codPersonal;
    }

    public void setCodPersonal(AdmPersonal codPersonal) {
        this.codPersonal = codPersonal;
    }

    public MetCatalogo getCodTipoDestinatario() {
        return codTipoDestinatario;
    }

    public void setCodTipoDestinatario(MetCatalogo codTipoDestinatario) {
        this.codTipoDestinatario = codTipoDestinatario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorreoDestinatario != null ? idCorreoDestinatario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmCorreoDestinatario)) {
            return false;
        }
        AdmCorreoDestinatario other = (AdmCorreoDestinatario) object;
        if ((this.idCorreoDestinatario == null && other.idCorreoDestinatario != null) || (this.idCorreoDestinatario != null && !this.idCorreoDestinatario.equals(other.idCorreoDestinatario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario[ idCorreoDestinatario=" + idCorreoDestinatario + " ]";
    }
    
}
