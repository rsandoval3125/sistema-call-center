/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
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
@Table(name = "proceso.pro_seguimiento_revisor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProSeguimientoRevisor.findAll", query = "SELECT p FROM ProSeguimientoRevisor p")})
public class ProSeguimientoRevisor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_segrev")
    private Integer idSegrev;
    @Column(name = "nivel")
    private Integer nivel;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 1)
    @Column(name = "estado_revision")
    private String estadoRevision;
    @JoinColumn(name = "cod_seg", referencedColumnName = "id_seg")
    @ManyToOne
    private ProSeguimiento codSeg;
    @JoinColumn(name = "cod_usu", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsu;

    public ProSeguimientoRevisor() {
    }

    public ProSeguimientoRevisor(Integer idSegrev) {
        this.idSegrev = idSegrev;
    }

    public Integer getIdSegrev() {
        return idSegrev;
    }

    public void setIdSegrev(Integer idSegrev) {
        this.idSegrev = idSegrev;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
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

    public String getEstadoRevision() {
        return estadoRevision;
    }

    public void setEstadoRevision(String estadoRevision) {
        this.estadoRevision = estadoRevision;
    }

    public ProSeguimiento getCodSeg() {
        return codSeg;
    }

    public void setCodSeg(ProSeguimiento codSeg) {
        this.codSeg = codSeg;
    }

    public SegUsuario getCodUsu() {
        return codUsu;
    }

    public void setCodUsu(SegUsuario codUsu) {
        this.codUsu = codUsu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegrev != null ? idSegrev.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProSeguimientoRevisor)) {
            return false;
        }
        ProSeguimientoRevisor other = (ProSeguimientoRevisor) object;
        if ((this.idSegrev == null && other.idSegrev != null) || (this.idSegrev != null && !this.idSegrev.equals(other.idSegrev))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProSeguimientoRevisor[ idSegrev=" + idSegrev + " ]";
    }
    
}
