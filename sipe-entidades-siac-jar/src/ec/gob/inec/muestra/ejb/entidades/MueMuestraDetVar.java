/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

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
@Table(name = "muestra.mue_muestra_det_var")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueMuestraDetVar.findAll", query = "SELECT m FROM MueMuestraDetVar m")})
public class MueMuestraDetVar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mue_det_var")
    private Integer idMueDetVar;
    @Size(max = 5)
    @Column(name = "muestra_det_var")
    private String muestraDetVar;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_muestra", referencedColumnName = "id_muestra")
    @ManyToOne
    private MueMuestra codMuestra;
    @JoinColumn(name = "cod_mue_variable", referencedColumnName = "id_mue_variable")
    @ManyToOne
    private MueMuestraVariable codMueVariable;

    public MueMuestraDetVar() {
    }

    public MueMuestraDetVar(Integer idMueDetVar) {
        this.idMueDetVar = idMueDetVar;
    }

    public Integer getIdMueDetVar() {
        return idMueDetVar;
    }

    public void setIdMueDetVar(Integer idMueDetVar) {
        this.idMueDetVar = idMueDetVar;
    }

    public String getMuestraDetVar() {
        return muestraDetVar;
    }

    public void setMuestraDetVar(String muestraDetVar) {
        this.muestraDetVar = muestraDetVar;
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

    public MueMuestra getCodMuestra() {
        return codMuestra;
    }

    public void setCodMuestra(MueMuestra codMuestra) {
        this.codMuestra = codMuestra;
    }

    public MueMuestraVariable getCodMueVariable() {
        return codMueVariable;
    }

    public void setCodMueVariable(MueMuestraVariable codMueVariable) {
        this.codMueVariable = codMueVariable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMueDetVar != null ? idMueDetVar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueMuestraDetVar)) {
            return false;
        }
        MueMuestraDetVar other = (MueMuestraDetVar) object;
        if ((this.idMueDetVar == null && other.idMueDetVar != null) || (this.idMueDetVar != null && !this.idMueDetVar.equals(other.idMueDetVar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueMuestraDetVar[ idMueDetVar=" + idMueDetVar + " ]";
    }
    
}
