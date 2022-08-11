/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

import ec.gob.inec.geografia.ejb.entidades.GeoDpa;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_personal_historico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmPersonalHistorico.findAll", query = "SELECT a FROM AdmPersonalHistorico a")})
public class AdmPersonalHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_per_his")
    private Integer idPerHis;
    @Column(name = "cargo_descripcion")
    private String cargoDescripcion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_organigrama", referencedColumnName = "id_organigrama")
    @ManyToOne
    private AdmOrganigrama codOrganigrama;
    @JoinColumn(name = "cod_personal", referencedColumnName = "id_personal")
    @ManyToOne
    private AdmPersonal codPersonal;
    @JoinColumn(name = "cod_dpa", referencedColumnName = "id_dpa")
    @ManyToOne
    private GeoDpa codDpa;
    @JoinColumn(name = "cod_cargo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codCargo;
    @JoinColumn(name = "cod_nivel_salarial", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codNivelSalarial;
    @JoinColumn(name = "cod_tipo_contrato", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoContrato;

    public AdmPersonalHistorico() {
    }

    public AdmPersonalHistorico(Integer idPerHis) {
        this.idPerHis = idPerHis;
    }

    public AdmPersonalHistorico(Integer idPerHis, boolean estadoLogico) {
        this.idPerHis = idPerHis;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdPerHis() {
        return idPerHis;
    }

    public void setIdPerHis(Integer idPerHis) {
        this.idPerHis = idPerHis;
    }

    public String getCargoDescripcion() {
        return cargoDescripcion;
    }

    public void setCargoDescripcion(String cargoDescripcion) {
        this.cargoDescripcion = cargoDescripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    public AdmOrganigrama getCodOrganigrama() {
        return codOrganigrama;
    }

    public void setCodOrganigrama(AdmOrganigrama codOrganigrama) {
        this.codOrganigrama = codOrganigrama;
    }

    public AdmPersonal getCodPersonal() {
        return codPersonal;
    }

    public void setCodPersonal(AdmPersonal codPersonal) {
        this.codPersonal = codPersonal;
    }

    public GeoDpa getCodDpa() {
        return codDpa;
    }

    public void setCodDpa(GeoDpa codDpa) {
        this.codDpa = codDpa;
    }

    public MetCatalogo getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(MetCatalogo codCargo) {
        this.codCargo = codCargo;
    }

    public MetCatalogo getCodNivelSalarial() {
        return codNivelSalarial;
    }

    public void setCodNivelSalarial(MetCatalogo codNivelSalarial) {
        this.codNivelSalarial = codNivelSalarial;
    }

    public MetCatalogo getCodTipoContrato() {
        return codTipoContrato;
    }

    public void setCodTipoContrato(MetCatalogo codTipoContrato) {
        this.codTipoContrato = codTipoContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerHis != null ? idPerHis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmPersonalHistorico)) {
            return false;
        }
        AdmPersonalHistorico other = (AdmPersonalHistorico) object;
        if ((this.idPerHis == null && other.idPerHis != null) || (this.idPerHis != null && !this.idPerHis.equals(other.idPerHis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmPersonalHistorico[ idPerHis=" + idPerHis + " ]";
    }
    
}
