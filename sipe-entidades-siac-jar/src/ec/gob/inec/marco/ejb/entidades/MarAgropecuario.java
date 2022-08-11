/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.marco.ejb.entidades;

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
@Table(name = "marco.mar_agropecuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarAgropecuario.findAll", query = "SELECT m FROM MarAgropecuario m")})
public class MarAgropecuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_agropecuario")
    private Integer idAgropecuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;
    @JoinColumn(name = "cod_predio", referencedColumnName = "id_predio")
    @ManyToOne(optional = false)
    private MarPredio codPredio;

    public MarAgropecuario() {
    }

    public MarAgropecuario(Integer idAgropecuario) {
        this.idAgropecuario = idAgropecuario;
    }

    public MarAgropecuario(Integer idAgropecuario, String descripcion, short estado) {
        this.idAgropecuario = idAgropecuario;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getIdAgropecuario() {
        return idAgropecuario;
    }

    public void setIdAgropecuario(Integer idAgropecuario) {
        this.idAgropecuario = idAgropecuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public MarPredio getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(MarPredio codPredio) {
        this.codPredio = codPredio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAgropecuario != null ? idAgropecuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarAgropecuario)) {
            return false;
        }
        MarAgropecuario other = (MarAgropecuario) object;
        if ((this.idAgropecuario == null && other.idAgropecuario != null) || (this.idAgropecuario != null && !this.idAgropecuario.equals(other.idAgropecuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.marco.ejb.entidades.MarAgropecuario[ idAgropecuario=" + idAgropecuario + " ]";
    }
    
}
