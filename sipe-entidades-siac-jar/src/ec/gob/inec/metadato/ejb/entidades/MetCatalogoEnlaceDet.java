/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

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
@Table(name = "metadato.met_catalogo_enlace_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetCatalogoEnlaceDet.findAll", query = "SELECT m FROM MetCatalogoEnlaceDet m")})
public class MetCatalogoEnlaceDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_catenldet")
    private Integer idCatenldet;
    @Column(name = "etiqueta1")
    private String etiqueta1;
    @Column(name = "valor1")
    private String valor1;
    @Column(name = "etiqueta2")
    private String etiqueta2;
    @Column(name = "valor2")
    private String valor2;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_catenl", referencedColumnName = "id_catenl")
    @ManyToOne
    private MetCatalogoEnlace codCatenl;

    public MetCatalogoEnlaceDet() {
    }

    public MetCatalogoEnlaceDet(Integer idCatenldet) {
        this.idCatenldet = idCatenldet;
    }

    public MetCatalogoEnlaceDet(Integer idCatenldet, boolean estadoLogico) {
        this.idCatenldet = idCatenldet;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCatenldet() {
        return idCatenldet;
    }

    public void setIdCatenldet(Integer idCatenldet) {
        this.idCatenldet = idCatenldet;
    }

    public String getEtiqueta1() {
        return etiqueta1;
    }

    public void setEtiqueta1(String etiqueta1) {
        this.etiqueta1 = etiqueta1;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getEtiqueta2() {
        return etiqueta2;
    }

    public void setEtiqueta2(String etiqueta2) {
        this.etiqueta2 = etiqueta2;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public MetCatalogoEnlace getCodCatenl() {
        return codCatenl;
    }

    public void setCodCatenl(MetCatalogoEnlace codCatenl) {
        this.codCatenl = codCatenl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatenldet != null ? idCatenldet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetCatalogoEnlaceDet)) {
            return false;
        }
        MetCatalogoEnlaceDet other = (MetCatalogoEnlaceDet) object;
        if ((this.idCatenldet == null && other.idCatenldet != null) || (this.idCatenldet != null && !this.idCatenldet.equals(other.idCatenldet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetCatalogoEnlaceDet[ idCatenldet=" + idCatenldet + " ]";
    }
    
}
