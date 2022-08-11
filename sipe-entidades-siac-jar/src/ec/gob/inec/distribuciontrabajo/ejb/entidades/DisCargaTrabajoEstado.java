/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.entidades;

import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisCargaTrabajo;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "distribucion.dis_carga_trabajo_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisCargaTrabajoEstado.findAll", query = "SELECT d FROM DisCargaTrabajoEstado d")})
public class DisCargaTrabajoEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_car_tra_est")
    private Integer idCarTraEst;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @JoinColumn(name = "cod_car_tra", referencedColumnName = "id_car_tra")
    @ManyToOne
    private DisCargaTrabajo codCarTra;
    @JoinColumn(name = "estado_asignacion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo estadoAsignacion;

    public DisCargaTrabajoEstado() {
    }

    public DisCargaTrabajoEstado(Integer idCarTraEst) {
        this.idCarTraEst = idCarTraEst;
    }

    public DisCargaTrabajoEstado(Integer idCarTraEst, boolean estadoLogico) {
        this.idCarTraEst = idCarTraEst;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCarTraEst() {
        return idCarTraEst;
    }

    public void setIdCarTraEst(Integer idCarTraEst) {
        this.idCarTraEst = idCarTraEst;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    public DisCargaTrabajo getCodCarTra() {
        return codCarTra;
    }

    public void setCodCarTra(DisCargaTrabajo codCarTra) {
        this.codCarTra = codCarTra;
    }

    public MetCatalogo getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(MetCatalogo estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCarTraEst != null ? idCarTraEst.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisCargaTrabajoEstado)) {
            return false;
        }
        DisCargaTrabajoEstado other = (DisCargaTrabajoEstado) object;
        if ((this.idCarTraEst == null && other.idCarTraEst != null) || (this.idCarTraEst != null && !this.idCarTraEst.equals(other.idCarTraEst))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.distribuciontrabajo.ejb.entidades.DisCargaTrabajoEstado[ idCarTraEst=" + idCarTraEst + " ]";
    }
    
}
