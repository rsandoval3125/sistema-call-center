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
@Table(name = "seguridad.seg_auditoria_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAuditoriaDet.findAll", query = "SELECT s FROM SegAuditoriaDet s")})
public class SegAuditoriaDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_det")
    private Integer idAuditoriaDet;
    @Column(name = "nombre_campo")
    private String nombreCampo;
    @Column(name = "valor_antiguo")
    private String valorAntiguo;
    @Column(name = "valor_nuevo")
    private String valorNuevo;
    @JoinColumn(name = "cod_auditoria_cab", referencedColumnName = "id_auditoria_cab")
    @ManyToOne
    private SegAuditoriaCab codAuditoriaCab;

    public SegAuditoriaDet() {
    }

    public SegAuditoriaDet(Integer idAuditoriaDet) {
        this.idAuditoriaDet = idAuditoriaDet;
    }

    public Integer getIdAuditoriaDet() {
        return idAuditoriaDet;
    }

    public void setIdAuditoriaDet(Integer idAuditoriaDet) {
        this.idAuditoriaDet = idAuditoriaDet;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getValorAntiguo() {
        return valorAntiguo;
    }

    public void setValorAntiguo(String valorAntiguo) {
        this.valorAntiguo = valorAntiguo;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public SegAuditoriaCab getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(SegAuditoriaCab codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaDet != null ? idAuditoriaDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAuditoriaDet)) {
            return false;
        }
        SegAuditoriaDet other = (SegAuditoriaDet) object;
        if ((this.idAuditoriaDet == null && other.idAuditoriaDet != null) || (this.idAuditoriaDet != null && !this.idAuditoriaDet.equals(other.idAuditoriaDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaDet[ idAuditoriaDet=" + idAuditoriaDet + " ]";
    }
    
}
