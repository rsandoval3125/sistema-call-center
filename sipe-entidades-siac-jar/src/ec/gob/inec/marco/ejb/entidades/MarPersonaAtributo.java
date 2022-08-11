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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "marco.mar_persona_atributo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarPersonaAtributo.findAll", query = "SELECT m FROM MarPersonaAtributo m")})
public class MarPersonaAtributo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "di_per_atri")
    private Integer diPerAtri;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "posee_atributo")
    private Short poseeAtributo;
    @JoinColumn(name = "cod_persona", referencedColumnName = "id_poblacion")
    @ManyToOne(optional = false)
    private MarPoblacion codPersona;
    @JoinColumn(name = "cod_atributo", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codAtributo;
    @JoinColumn(name = "cod_valor_atributo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codValorAtributo;

    public MarPersonaAtributo() {
    }

    public MarPersonaAtributo(Integer diPerAtri) {
        this.diPerAtri = diPerAtri;
    }

    public Integer getDiPerAtri() {
        return diPerAtri;
    }

    public void setDiPerAtri(Integer diPerAtri) {
        this.diPerAtri = diPerAtri;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Short getPoseeAtributo() {
        return poseeAtributo;
    }

    public void setPoseeAtributo(Short poseeAtributo) {
        this.poseeAtributo = poseeAtributo;
    }

    public MarPoblacion getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(MarPoblacion codPersona) {
        this.codPersona = codPersona;
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
        hash += (diPerAtri != null ? diPerAtri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPersonaAtributo)) {
            return false;
        }
        MarPersonaAtributo other = (MarPersonaAtributo) object;
        if ((this.diPerAtri == null && other.diPerAtri != null) || (this.diPerAtri != null && !this.diPerAtri.equals(other.diPerAtri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.marco.ejb.entidades.MarPersonaAtributo[ diPerAtri=" + diPerAtri + " ]";
    }
    
}
