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
@Table(name = "marco.mar_predio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarPredio.findAll", query = "SELECT m FROM MarPredio m")})
public class MarPredio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_predio")
    private Integer idPredio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_dpa")
    private int codDpa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "calle_principal")
    private String callePrincipal;
    @Size(max = 255)
    @Column(name = "calle_secundaria")
    private String calleSecundaria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 2147483647)
    @Column(name = "imagen")
    private String imagen;

    public MarPredio() {
    }

    public MarPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public MarPredio(Integer idPredio, int codDpa, String descripcion, String callePrincipal, String referencia) {
        this.idPredio = idPredio;
        this.codDpa = codDpa;
        this.descripcion = descripcion;
        this.callePrincipal = callePrincipal;
        this.referencia = referencia;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public int getCodDpa() {
        return codDpa;
    }

    public void setCodDpa(int codDpa) {
        this.codDpa = codDpa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public String getCalleSecundaria() {
        return calleSecundaria;
    }

    public void setCalleSecundaria(String calleSecundaria) {
        this.calleSecundaria = calleSecundaria;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPredio != null ? idPredio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPredio)) {
            return false;
        }
        MarPredio other = (MarPredio) object;
        if ((this.idPredio == null && other.idPredio != null) || (this.idPredio != null && !this.idPredio.equals(other.idPredio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.marco.ejb.entidades.MarPredio[ idPredio=" + idPredio + " ]";
    }
    
}
