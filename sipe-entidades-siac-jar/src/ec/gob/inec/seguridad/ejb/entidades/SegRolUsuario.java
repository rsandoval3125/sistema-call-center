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
@Table(name = "seguridad.seg_rol_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegRolUsuario.findAll", query = "SELECT s FROM SegRolUsuario s")})
public class SegRolUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_usu")
    private Integer idRolUsu;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_rol", referencedColumnName = "id_rol")
    @ManyToOne(optional = false)
    private SegRol codRol;
    @JoinColumn(name = "cod_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuario;

    public SegRolUsuario() {
    }

    public SegRolUsuario(Integer idRolUsu) {
        this.idRolUsu = idRolUsu;
    }

    public Integer getIdRolUsu() {
        return idRolUsu;
    }

    public void setIdRolUsu(Integer idRolUsu) {
        this.idRolUsu = idRolUsu;
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

    public SegRol getCodRol() {
        return codRol;
    }

    public void setCodRol(SegRol codRol) {
        this.codRol = codRol;
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
        hash += (idRolUsu != null ? idRolUsu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegRolUsuario)) {
            return false;
        }
        SegRolUsuario other = (SegRolUsuario) object;
        if ((this.idRolUsu == null && other.idRolUsu != null) || (this.idRolUsu != null && !this.idRolUsu.equals(other.idRolUsu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario[ idRolUsu=" + idRolUsu + " ]";
    }
    
}
