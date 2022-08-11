/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "proceso.pro_seguimiento_presupuestario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProSeguimientoPresupuestario.findAll", query = "SELECT p FROM ProSeguimientoPresupuestario p")})
public class ProSeguimientoPresupuestario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seguimiento_presup")
    private Integer idSeguimientoPresup;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 500)
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_proceso")
    private boolean estadoProceso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @JoinColumn(name = "cod_tipo_usuario", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoUsuario;
    @JoinColumn(name = "cod_ejercicio", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codEjercicio;
    @JoinColumn(name = "cod_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuario;

    public ProSeguimientoPresupuestario() {
    }

    public ProSeguimientoPresupuestario(Integer idSeguimientoPresup) {
        this.idSeguimientoPresup = idSeguimientoPresup;
    }

    public ProSeguimientoPresupuestario(Integer idSeguimientoPresup, boolean estadoProceso, boolean estadoLogico) {
        this.idSeguimientoPresup = idSeguimientoPresup;
        this.estadoProceso = estadoProceso;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdSeguimientoPresup() {
        return idSeguimientoPresup;
    }

    public void setIdSeguimientoPresup(Integer idSeguimientoPresup) {
        this.idSeguimientoPresup = idSeguimientoPresup;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(boolean estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public MetCatalogo getCodTipoUsuario() {
        return codTipoUsuario;
    }

    public void setCodTipoUsuario(MetCatalogo codTipoUsuario) {
        this.codTipoUsuario = codTipoUsuario;
    }

    public MetCatalogo getCodEjercicio() {
        return codEjercicio;
    }

    public void setCodEjercicio(MetCatalogo codEjercicio) {
        this.codEjercicio = codEjercicio;
    }

    public SegUsuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(SegUsuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguimientoPresup != null ? idSeguimientoPresup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProSeguimientoPresupuestario)) {
            return false;
        }
        ProSeguimientoPresupuestario other = (ProSeguimientoPresupuestario) object;
        if ((this.idSeguimientoPresup == null && other.idSeguimientoPresup != null) || (this.idSeguimientoPresup != null && !this.idSeguimientoPresup.equals(other.idSeguimientoPresup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProSeguimientoPresupuestario[ idSeguimientoPresup=" + idSeguimientoPresup + " ]";
    }
    
}
