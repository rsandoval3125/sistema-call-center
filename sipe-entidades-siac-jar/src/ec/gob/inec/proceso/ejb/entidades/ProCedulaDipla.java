/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

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
@Table(name = "proceso.pro_cedula_dipla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProCedulaDipla.findAll", query = "SELECT p FROM ProCedulaDipla p")})
public class ProCedulaDipla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cedula_dipla")
    private Integer idCedulaDipla;
    @Column(name = "planificado")
    private Boolean planificado;
    @Size(max = 50)
    @Column(name = "alias")
    private String alias;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "total")
    private Boolean total;
    @Column(name = "comprobacion")
    private Boolean comprobacion;
    @JoinColumn(name = "cod_ejercicio", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codEjercicio;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipo;

    public ProCedulaDipla() {
    }

    public ProCedulaDipla(Integer idCedulaDipla) {
        this.idCedulaDipla = idCedulaDipla;
    }

    public ProCedulaDipla(Integer idCedulaDipla, boolean estadoLogico) {
        this.idCedulaDipla = idCedulaDipla;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCedulaDipla() {
        return idCedulaDipla;
    }

    public void setIdCedulaDipla(Integer idCedulaDipla) {
        this.idCedulaDipla = idCedulaDipla;
    }

    public Boolean getPlanificado() {
        return planificado;
    }

    public void setPlanificado(Boolean planificado) {
        this.planificado = planificado;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public Boolean getTotal() {
        return total;
    }

    public void setTotal(Boolean total) {
        this.total = total;
    }

    public Boolean getComprobacion() {
        return comprobacion;
    }

    public void setComprobacion(Boolean comprobacion) {
        this.comprobacion = comprobacion;
    }

    public MetCatalogo getCodEjercicio() {
        return codEjercicio;
    }

    public void setCodEjercicio(MetCatalogo codEjercicio) {
        this.codEjercicio = codEjercicio;
    }

    public MetCatalogo getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(MetCatalogo codTipo) {
        this.codTipo = codTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCedulaDipla != null ? idCedulaDipla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProCedulaDipla)) {
            return false;
        }
        ProCedulaDipla other = (ProCedulaDipla) object;
        if ((this.idCedulaDipla == null && other.idCedulaDipla != null) || (this.idCedulaDipla != null && !this.idCedulaDipla.equals(other.idCedulaDipla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProCedulaDipla[ idCedulaDipla=" + idCedulaDipla + " ]";
    }
    
}
