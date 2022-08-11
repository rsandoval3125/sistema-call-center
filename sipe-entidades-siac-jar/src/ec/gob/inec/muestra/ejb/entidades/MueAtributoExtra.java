/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

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
@Table(name = "muestra.mue_atributo_extra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueAtributoExtra.findAll", query = "SELECT m FROM MueAtributoExtra m")})
public class MueAtributoExtra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atrib_extra")
    private Integer idAtribExtra;
    @Size(max = 2147483647)
    @Column(name = "id_atrib_valor")
    private String idAtribValor;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 2147483647)
    @Column(name = "detalle")
    private String detalle;
    @JoinColumn(name = "cod_cat_nom_atrib", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codCatNomAtrib;
    @JoinColumn(name = "cod_mue_det", referencedColumnName = "id_mue_det")
    @ManyToOne
    private MueMuestraDetalle codMueDet;

    public MueAtributoExtra() {
    }

    public MueAtributoExtra(Integer idAtribExtra) {
        this.idAtribExtra = idAtribExtra;
    }

    public Integer getIdAtribExtra() {
        return idAtribExtra;
    }

    public void setIdAtribExtra(Integer idAtribExtra) {
        this.idAtribExtra = idAtribExtra;
    }

    public String getIdAtribValor() {
        return idAtribValor;
    }

    public void setIdAtribValor(String idAtribValor) {
        this.idAtribValor = idAtribValor;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public MetCatalogo getCodCatNomAtrib() {
        return codCatNomAtrib;
    }

    public void setCodCatNomAtrib(MetCatalogo codCatNomAtrib) {
        this.codCatNomAtrib = codCatNomAtrib;
    }

    public MueMuestraDetalle getCodMueDet() {
        return codMueDet;
    }

    public void setCodMueDet(MueMuestraDetalle codMueDet) {
        this.codMueDet = codMueDet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtribExtra != null ? idAtribExtra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueAtributoExtra)) {
            return false;
        }
        MueAtributoExtra other = (MueAtributoExtra) object;
        if ((this.idAtribExtra == null && other.idAtribExtra != null) || (this.idAtribExtra != null && !this.idAtribExtra.equals(other.idAtribExtra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueAtributoExtra[ idAtribExtra=" + idAtribExtra + " ]";
    }
    
}
