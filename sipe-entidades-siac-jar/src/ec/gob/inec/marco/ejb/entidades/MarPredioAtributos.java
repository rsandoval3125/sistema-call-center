/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.marco.ejb.entidades;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "marco.mar_predio_atributos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarPredioAtributos.findAll", query = "SELECT m FROM MarPredioAtributos m")})
public class MarPredioAtributos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pred_atri")
    private Integer idPredAtri;
    @Basic(optional = false)
    @NotNull
    @Column(name = "posee_atributo")
    private short poseeAtributo;
    @Size(max = 2147483647)
    @Column(name = "observacion_atributo")
    private String observacionAtributo;
    @JoinColumn(name = "cod_predio", referencedColumnName = "id_predio")
    @ManyToOne(optional = false)
    private MarPredio codPredio;
    @JoinColumn(name = "cod_atributo", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codAtributo;
    @JoinColumn(name = "cod_valor_atributo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codValorAtributo;

    public MarPredioAtributos() {
    }

    public MarPredioAtributos(Integer idPredAtri) {
        this.idPredAtri = idPredAtri;
    }

    public MarPredioAtributos(Integer idPredAtri, short poseeAtributo) {
        this.idPredAtri = idPredAtri;
        this.poseeAtributo = poseeAtributo;
    }

    public Integer getIdPredAtri() {
        return idPredAtri;
    }

    public void setIdPredAtri(Integer idPredAtri) {
        this.idPredAtri = idPredAtri;
    }

    public short getPoseeAtributo() {
        return poseeAtributo;
    }

    public void setPoseeAtributo(short poseeAtributo) {
        this.poseeAtributo = poseeAtributo;
    }

    public String getObservacionAtributo() {
        return observacionAtributo;
    }

    public void setObservacionAtributo(String observacionAtributo) {
        this.observacionAtributo = observacionAtributo;
    }

    public MarPredio getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(MarPredio codPredio) {
        this.codPredio = codPredio;
    }

    public MetCatalogo getCodAtributo() {
        return codAtributo;
    }

    public void setCodAtributo(MetCatalogo codAtributo) {
        this.codAtributo = codAtributo;
    }

    public MetCatalogo getCodValorAtributo() {
        return codValorAtributo;
    }

    public void setCodValorAtributo(MetCatalogo codValorAtributo) {
        this.codValorAtributo = codValorAtributo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPredAtri != null ? idPredAtri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPredioAtributos)) {
            return false;
        }
        MarPredioAtributos other = (MarPredioAtributos) object;
        if ((this.idPredAtri == null && other.idPredAtri != null) || (this.idPredAtri != null && !this.idPredAtri.equals(other.idPredAtri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.marco.ejb.entidades.MarPredioAtributos[ idPredAtri=" + idPredAtri + " ]";
    }
    
}
