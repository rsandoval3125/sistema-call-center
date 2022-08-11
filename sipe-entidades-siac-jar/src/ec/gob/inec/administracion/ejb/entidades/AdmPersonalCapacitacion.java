/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "administracion.adm_personal_capacitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmPersonalCapacitacion.findAll", query = "SELECT a FROM AdmPersonalCapacitacion a")})
public class AdmPersonalCapacitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_per_cap")
    private Integer idPerCap;
    @Column(name = "nombre_capacitacion")
    private String nombreCapacitacion;
    @Column(name = "tiempo_capacitacion")
    private Integer tiempoCapacitacion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "institucional")
    private Short institucional;
    @Column(name = "devengado")
    private Short devengado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo")
    private BigDecimal costo;
    @Column(name = "costo_no_devengado")
    private BigDecimal costoNoDevengado;
    @Column(name = "aprobado")
    private Short aprobado;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_personal", referencedColumnName = "id_personal")
    @ManyToOne
    private AdmPersonal codPersonal;
    @JoinColumn(name = "cod_aprobacion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codAprobacion;
    @JoinColumn(name = "cod_tip_cap", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipCap;

    public AdmPersonalCapacitacion() {
    }

    public AdmPersonalCapacitacion(Integer idPerCap) {
        this.idPerCap = idPerCap;
    }

    public AdmPersonalCapacitacion(Integer idPerCap, boolean estadoLogico) {
        this.idPerCap = idPerCap;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdPerCap() {
        return idPerCap;
    }

    public void setIdPerCap(Integer idPerCap) {
        this.idPerCap = idPerCap;
    }

    public String getNombreCapacitacion() {
        return nombreCapacitacion;
    }

    public void setNombreCapacitacion(String nombreCapacitacion) {
        this.nombreCapacitacion = nombreCapacitacion;
    }

    public Integer getTiempoCapacitacion() {
        return tiempoCapacitacion;
    }

    public void setTiempoCapacitacion(Integer tiempoCapacitacion) {
        this.tiempoCapacitacion = tiempoCapacitacion;
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

    public Short getInstitucional() {
        return institucional;
    }

    public void setInstitucional(Short institucional) {
        this.institucional = institucional;
    }

    public Short getDevengado() {
        return devengado;
    }

    public void setDevengado(Short devengado) {
        this.devengado = devengado;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getCostoNoDevengado() {
        return costoNoDevengado;
    }

    public void setCostoNoDevengado(BigDecimal costoNoDevengado) {
        this.costoNoDevengado = costoNoDevengado;
    }

    public Short getAprobado() {
        return aprobado;
    }

    public void setAprobado(Short aprobado) {
        this.aprobado = aprobado;
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

    public AdmPersonal getCodPersonal() {
        return codPersonal;
    }

    public void setCodPersonal(AdmPersonal codPersonal) {
        this.codPersonal = codPersonal;
    }

    public MetCatalogo getCodAprobacion() {
        return codAprobacion;
    }

    public void setCodAprobacion(MetCatalogo codAprobacion) {
        this.codAprobacion = codAprobacion;
    }

    public MetCatalogo getCodTipCap() {
        return codTipCap;
    }

    public void setCodTipCap(MetCatalogo codTipCap) {
        this.codTipCap = codTipCap;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerCap != null ? idPerCap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmPersonalCapacitacion)) {
            return false;
        }
        AdmPersonalCapacitacion other = (AdmPersonalCapacitacion) object;
        if ((this.idPerCap == null && other.idPerCap != null) || (this.idPerCap != null && !this.idPerCap.equals(other.idPerCap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmPersonalCapacitacion[ idPerCap=" + idPerCap + " ]";
    }
    
}
