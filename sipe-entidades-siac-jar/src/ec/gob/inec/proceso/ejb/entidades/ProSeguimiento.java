/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "proceso.pro_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProSeguimiento.findAll", query = "SELECT p FROM ProSeguimiento p")})
public class ProSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seg")
    private Integer idSeg;
    @Column(name = "fecha_programacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramacion;
    @Column(name = "fecha_ini_prog")
    @Temporal(TemporalType.DATE)
    private Date fechaIniProg;
    @Column(name = "dias_prog")
    private Integer diasProg;
    @Column(name = "fecha_fin_prog")
    @Temporal(TemporalType.DATE)
    private Date fechaFinProg;
    @Column(name = "num_revisiones")
    private Integer numRevisiones;
    @Column(name = "porc_avance")
    private Integer porcAvance;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "avance_calculado")
    private BigDecimal avanceCalculado;
    @Column(name = "nivel_revision")
    private Integer nivelRevision;
    @Column(name = "fecha_fin_real")
    @Temporal(TemporalType.DATE)
    private Date fechaFinReal;
    @Size(max = 2147483647)
    @Column(name = "observaciones_generales")
    private String observacionesGenerales;
    @Column(name = "tienearchivos")
    private Boolean tienearchivos;
    @Column(name = "avance_calculado_nivel1")
    private BigDecimal avanceCalculadoNivel1;
    @Column(name = "avance_calculado_nivel0")
    private BigDecimal avanceCalculadoNivel0;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 2)
    @Column(name = "estado_seguimiento")
    private String estadoSeguimiento;
    @JoinColumn(name = "cod_actividad", referencedColumnName = "id_actividad")
    @ManyToOne
    private ProActividad codActividad;
    @JoinColumn(name = "cod_usu_asigna", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuAsigna;
    @JoinColumn(name = "cod_usu_asignado", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuAsignado;

    public ProSeguimiento() {
    }

    public ProSeguimiento(Integer idSeg) {
        this.idSeg = idSeg;
    }

    public ProSeguimiento(Integer idSeg, boolean estadoLogico) {
        this.idSeg = idSeg;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdSeg() {
        return idSeg;
    }

    public void setIdSeg(Integer idSeg) {
        this.idSeg = idSeg;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public Date getFechaIniProg() {
        return fechaIniProg;
    }

    public void setFechaIniProg(Date fechaIniProg) {
        this.fechaIniProg = fechaIniProg;
    }

    public Integer getDiasProg() {
        return diasProg;
    }

    public void setDiasProg(Integer diasProg) {
        this.diasProg = diasProg;
    }

    public Date getFechaFinProg() {
        return fechaFinProg;
    }

    public void setFechaFinProg(Date fechaFinProg) {
        this.fechaFinProg = fechaFinProg;
    }

    public Integer getNumRevisiones() {
        return numRevisiones;
    }

    public void setNumRevisiones(Integer numRevisiones) {
        this.numRevisiones = numRevisiones;
    }

    public Integer getPorcAvance() {
        return porcAvance;
    }

    public void setPorcAvance(Integer porcAvance) {
        this.porcAvance = porcAvance;
    }

    public BigDecimal getAvanceCalculado() {
        return avanceCalculado;
    }

    public void setAvanceCalculado(BigDecimal avanceCalculado) {
        this.avanceCalculado = avanceCalculado;
    }

    public Integer getNivelRevision() {
        return nivelRevision;
    }

    public void setNivelRevision(Integer nivelRevision) {
        this.nivelRevision = nivelRevision;
    }

    public Date getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(Date fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public String getObservacionesGenerales() {
        return observacionesGenerales;
    }

    public void setObservacionesGenerales(String observacionesGenerales) {
        this.observacionesGenerales = observacionesGenerales;
    }

    public Boolean getTienearchivos() {
        return tienearchivos;
    }

    public void setTienearchivos(Boolean tienearchivos) {
        this.tienearchivos = tienearchivos;
    }

    public BigDecimal getAvanceCalculadoNivel1() {
        return avanceCalculadoNivel1;
    }

    public void setAvanceCalculadoNivel1(BigDecimal avanceCalculadoNivel1) {
        this.avanceCalculadoNivel1 = avanceCalculadoNivel1;
    }

    public BigDecimal getAvanceCalculadoNivel0() {
        return avanceCalculadoNivel0;
    }

    public void setAvanceCalculadoNivel0(BigDecimal avanceCalculadoNivel0) {
        this.avanceCalculadoNivel0 = avanceCalculadoNivel0;
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

    public String getEstadoSeguimiento() {
        return estadoSeguimiento;
    }

    public void setEstadoSeguimiento(String estadoSeguimiento) {
        this.estadoSeguimiento = estadoSeguimiento;
    }

    public ProActividad getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(ProActividad codActividad) {
        this.codActividad = codActividad;
    }

    public SegUsuario getCodUsuAsigna() {
        return codUsuAsigna;
    }

    public void setCodUsuAsigna(SegUsuario codUsuAsigna) {
        this.codUsuAsigna = codUsuAsigna;
    }

    public SegUsuario getCodUsuAsignado() {
        return codUsuAsignado;
    }

    public void setCodUsuAsignado(SegUsuario codUsuAsignado) {
        this.codUsuAsignado = codUsuAsignado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeg != null ? idSeg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProSeguimiento)) {
            return false;
        }
        ProSeguimiento other = (ProSeguimiento) object;
        if ((this.idSeg == null && other.idSeg != null) || (this.idSeg != null && !this.idSeg.equals(other.idSeg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProSeguimiento[ idSeg=" + idSeg + " ]";
    }
    
}
