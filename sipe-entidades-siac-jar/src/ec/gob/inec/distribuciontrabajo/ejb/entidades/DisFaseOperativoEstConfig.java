/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.proceso.ejb.entidades.ProFase;
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
@Table(name = "distribucion.dis_fase_operativo_est_config")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisFaseOperativoEstConfig.findAll", query = "SELECT d FROM DisFaseOperativoEstConfig d")})
public class DisFaseOperativoEstConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fas_ope_est")
    private Integer idFasOpeEst;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_estado", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codEstado;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipo;
    @JoinColumn(name = "cod_operativo", referencedColumnName = "id_operativo")
    @ManyToOne
    private MetOperativo codOperativo;
    @JoinColumn(name = "cod_fase", referencedColumnName = "id_fase")
    @ManyToOne
    private ProFase codFase;

    public DisFaseOperativoEstConfig() {
    }

    public DisFaseOperativoEstConfig(Integer idFasOpeEst) {
        this.idFasOpeEst = idFasOpeEst;
    }

    public DisFaseOperativoEstConfig(Integer idFasOpeEst, boolean estadoLogico) {
        this.idFasOpeEst = idFasOpeEst;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdFasOpeEst() {
        return idFasOpeEst;
    }

    public void setIdFasOpeEst(Integer idFasOpeEst) {
        this.idFasOpeEst = idFasOpeEst;
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

    public MetCatalogo getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(MetCatalogo codEstado) {
        this.codEstado = codEstado;
    }

    public MetCatalogo getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(MetCatalogo codTipo) {
        this.codTipo = codTipo;
    }

    public MetOperativo getCodOperativo() {
        return codOperativo;
    }

    public void setCodOperativo(MetOperativo codOperativo) {
        this.codOperativo = codOperativo;
    }

    public ProFase getCodFase() {
        return codFase;
    }

    public void setCodFase(ProFase codFase) {
        this.codFase = codFase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFasOpeEst != null ? idFasOpeEst.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisFaseOperativoEstConfig)) {
            return false;
        }
        DisFaseOperativoEstConfig other = (DisFaseOperativoEstConfig) object;
        if ((this.idFasOpeEst == null && other.idFasOpeEst != null) || (this.idFasOpeEst != null && !this.idFasOpeEst.equals(other.idFasOpeEst))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativoEstConfig[ idFasOpeEst=" + idFasOpeEst + " ]";
    }
    
}