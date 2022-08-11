/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "muestra.ae_generacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AeGeneracion.findAll", query = "SELECT a FROM AeGeneracion a")})
public class AeGeneracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_generacion")
    private Integer idGeneracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechahora_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "sector")
    private String sector;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_viv_area")
    private int numVivArea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_viv_max_area")
    private int numVivMaxArea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_viv_min_area")
    private int numVivMinArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "estado1")
    private String estado1;
    @Size(max = 2)
    @Column(name = "estado2")
    private String estado2;
    @Column(name = "fechahora_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraModificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Size(max = 2)
    @Column(name = "tipo_generacion")
    private String tipoGeneracion;
    @Size(max = 2147483647)
    @Column(name = "param_ponderado")
    private String paramPonderado;

    public AeGeneracion() {
    }

    public AeGeneracion(Integer idGeneracion) {
        this.idGeneracion = idGeneracion;
    }

    public AeGeneracion(Integer idGeneracion, Date fechahoraCreacion, String sector, int numVivArea, int numVivMaxArea, int numVivMinArea, String estado1, boolean estadoLogico) {
        this.idGeneracion = idGeneracion;
        this.fechahoraCreacion = fechahoraCreacion;
        this.sector = sector;
        this.numVivArea = numVivArea;
        this.numVivMaxArea = numVivMaxArea;
        this.numVivMinArea = numVivMinArea;
        this.estado1 = estado1;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdGeneracion() {
        return idGeneracion;
    }

    public void setIdGeneracion(Integer idGeneracion) {
        this.idGeneracion = idGeneracion;
    }

    public Date getFechahoraCreacion() {
        return fechahoraCreacion;
    }

    public void setFechahoraCreacion(Date fechahoraCreacion) {
        this.fechahoraCreacion = fechahoraCreacion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getNumVivArea() {
        return numVivArea;
    }

    public void setNumVivArea(int numVivArea) {
        this.numVivArea = numVivArea;
    }

    public int getNumVivMaxArea() {
        return numVivMaxArea;
    }

    public void setNumVivMaxArea(int numVivMaxArea) {
        this.numVivMaxArea = numVivMaxArea;
    }

    public int getNumVivMinArea() {
        return numVivMinArea;
    }

    public void setNumVivMinArea(int numVivMinArea) {
        this.numVivMinArea = numVivMinArea;
    }

    public String getEstado1() {
        return estado1;
    }

    public void setEstado1(String estado1) {
        this.estado1 = estado1;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

    public Date getFechahoraModificacion() {
        return fechahoraModificacion;
    }

    public void setFechahoraModificacion(Date fechahoraModificacion) {
        this.fechahoraModificacion = fechahoraModificacion;
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

    public String getTipoGeneracion() {
        return tipoGeneracion;
    }

    public void setTipoGeneracion(String tipoGeneracion) {
        this.tipoGeneracion = tipoGeneracion;
    }

    public String getParamPonderado() {
        return paramPonderado;
    }

    public void setParamPonderado(String paramPonderado) {
        this.paramPonderado = paramPonderado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGeneracion != null ? idGeneracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AeGeneracion)) {
            return false;
        }
        AeGeneracion other = (AeGeneracion) object;
        if ((this.idGeneracion == null && other.idGeneracion != null) || (this.idGeneracion != null && !this.idGeneracion.equals(other.idGeneracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.AeGeneracion[ idGeneracion=" + idGeneracion + " ]";
    }
    
}
