/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

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
@Table(name = "seguridad.seg_rol_permiso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegRolPermiso.findAll", query = "SELECT s FROM SegRolPermiso s")})
public class SegRolPermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_permiso")
    private Integer idRolPermiso;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_permiso", referencedColumnName = "id_permiso")
    @ManyToOne
    private SegPermiso codPermiso;
    @JoinColumn(name = "cod_rol", referencedColumnName = "id_rol")
    @ManyToOne
    private SegRol codRol;

    public SegRolPermiso() {
    }

    public SegRolPermiso(Integer idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
    }

    public Integer getIdRolPermiso() {
        return idRolPermiso;
    }

    public void setIdRolPermiso(Integer idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
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

    public SegPermiso getCodPermiso() {
        return codPermiso;
    }

    public void setCodPermiso(SegPermiso codPermiso) {
        this.codPermiso = codPermiso;
    }

    public SegRol getCodRol() {
        return codRol;
    }

    public void setCodRol(SegRol codRol) {
        this.codRol = codRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolPermiso != null ? idRolPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegRolPermiso)) {
            return false;
        }
        SegRolPermiso other = (SegRolPermiso) object;
        if ((this.idRolPermiso == null && other.idRolPermiso != null) || (this.idRolPermiso != null && !this.idRolPermiso.equals(other.idRolPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegRolPermiso[ idRolPermiso=" + idRolPermiso + " ]";
    }
    
}
