/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetVariable;
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
@Table(name = "captura.capt_detalle_v")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptDetalleV.findAll", query = "SELECT c FROM CaptDetalleV c")})
public class CaptDetalleV implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "detv_id")
    private Integer detvId;
    @Size(max = 30)
    @Column(name = "clave")
    private String clave;
    @Column(name = "num_det")
    private Integer numDet;
    @Size(max = 2147483647)
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "cod_cab", referencedColumnName = "id_cab")
    @ManyToOne(optional = false)
    private CaptCabecera codCab;
    @JoinColumn(name = "cod_var", referencedColumnName = "id_var")
    @ManyToOne(optional = false)
    private MetVariable codVar;

    public CaptDetalleV() {
    }

    public CaptDetalleV(Integer detvId) {
        this.detvId = detvId;
    }

    public CaptDetalleV(Integer detvId, String valor) {
        this.detvId = detvId;
        this.valor = valor;
    }

    public Integer getDetvId() {
        return detvId;
    }

    public void setDetvId(Integer detvId) {
        this.detvId = detvId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getNumDet() {
        return numDet;
    }

    public void setNumDet(Integer numDet) {
        this.numDet = numDet;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CaptCabecera getCodCab() {
        return codCab;
    }

    public void setCodCab(CaptCabecera codCab) {
        this.codCab = codCab;
    }

    public MetVariable getCodVar() {
        return codVar;
    }

    public void setCodVar(MetVariable codVar) {
        this.codVar = codVar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detvId != null ? detvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptDetalleV)) {
            return false;
        }
        CaptDetalleV other = (CaptDetalleV) object;
        if ((this.detvId == null && other.detvId != null) || (this.detvId != null && !this.detvId.equals(other.detvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptDetalleV[ detvId=" + detvId + " ]";
    }
    
}
