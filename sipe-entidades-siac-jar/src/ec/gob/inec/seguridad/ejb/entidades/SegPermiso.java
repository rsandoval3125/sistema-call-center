/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "seguridad.seg_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPermiso.findAll", query = "SELECT s FROM SegPermiso s")})
public class SegPermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_permiso")
    private Integer idPermiso;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Basic(optional = false)
    @Column(name = "alias")
    private String alias;
    @JoinColumn(name = "cod_accion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codAccion;
    @JoinColumn(name = "cod_pag", referencedColumnName = "id_pag")
    @ManyToOne(optional = false)
    private SegPagina codPag;

    public SegPermiso() {
    }

    public SegPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public SegPermiso(Integer idPermiso, String alias) {
        this.idPermiso = idPermiso;
        this.alias = alias;
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public MetCatalogo getCodAccion() {
        return codAccion;
    }

    public void setCodAccion(MetCatalogo codAccion) {
        this.codAccion = codAccion;
    }

    public SegPagina getCodPag() {
        return codPag;
    }

    public void setCodPag(SegPagina codPag) {
        this.codPag = codPag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPermiso != null ? idPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPermiso)) {
            return false;
        }
        SegPermiso other = (SegPermiso) object;
        if ((this.idPermiso == null && other.idPermiso != null) || (this.idPermiso != null && !this.idPermiso.equals(other.idPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegPermiso[ idPermiso=" + idPermiso + " ]";
    }
    
}
