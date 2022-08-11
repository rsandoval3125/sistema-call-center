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
@Table(name = "metadato.met_salto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetSalto.findAll", query = "SELECT m FROM MetSalto m")})
public class MetSalto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_salto")
    private Integer idSalto;
    @Column(name = "salto")
    private String salto;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_var", referencedColumnName = "id_var")
    @ManyToOne
    private MetVariable codVar;

    public MetSalto() {
    }

    public MetSalto(Integer idSalto) {
        this.idSalto = idSalto;
    }

    public MetSalto(Integer idSalto, boolean estadoLogico) {
        this.idSalto = idSalto;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdSalto() {
        return idSalto;
    }

    public void setIdSalto(Integer idSalto) {
        this.idSalto = idSalto;
    }

    public String getSalto() {
        return salto;
    }

    public void setSalto(String salto) {
        this.salto = salto;
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

    public MetVariable getCodVar() {
        return codVar;
    }

    public void setCodVar(MetVariable codVar) {
        this.codVar = codVar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSalto != null ? idSalto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetSalto)) {
            return false;
        }
        MetSalto other = (MetSalto) object;
        if ((this.idSalto == null && other.idSalto != null) || (this.idSalto != null && !this.idSalto.equals(other.idSalto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetSalto[ idSalto=" + idSalto + " ]";
    }
    
}
