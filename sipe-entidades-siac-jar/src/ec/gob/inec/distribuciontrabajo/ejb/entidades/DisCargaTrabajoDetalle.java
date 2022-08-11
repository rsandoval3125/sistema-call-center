/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.entidades;

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
@Table(name = "distribucion.dis_carga_trabajo_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisCargaTrabajoDetalle.findAll", query = "SELECT d FROM DisCargaTrabajoDetalle d")})
public class DisCargaTrabajoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_car_tra_det")
    private Integer idCarTraDet;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "id_car_tra", referencedColumnName = "id_car_tra")
    @ManyToOne
    private DisCargaTrabajo idCarTra;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipo;

    public DisCargaTrabajoDetalle() {
    }

    public DisCargaTrabajoDetalle(Integer idCarTraDet) {
        this.idCarTraDet = idCarTraDet;
    }

    public DisCargaTrabajoDetalle(Integer idCarTraDet, boolean estadoLogico) {
        this.idCarTraDet = idCarTraDet;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCarTraDet() {
        return idCarTraDet;
    }

    public void setIdCarTraDet(Integer idCarTraDet) {
        this.idCarTraDet = idCarTraDet;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public DisCargaTrabajo getIdCarTra() {
        return idCarTra;
    }

    public void setIdCarTra(DisCargaTrabajo idCarTra) {
        this.idCarTra = idCarTra;
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
        hash += (idCarTraDet != null ? idCarTraDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisCargaTrabajoDetalle)) {
            return false;
        }
        DisCargaTrabajoDetalle other = (DisCargaTrabajoDetalle) object;
        if ((this.idCarTraDet == null && other.idCarTraDet != null) || (this.idCarTraDet != null && !this.idCarTraDet.equals(other.idCarTraDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.distribuciontrabajo.ejb.entidades.DisCargaTrabajoDetalle[ idCarTraDet=" + idCarTraDet + " ]";
    }
    
}
