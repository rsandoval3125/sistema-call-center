/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "captura.capt_datos_temp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptDatosTemp.findAll", query = "SELECT c FROM CaptDatosTemp c")})
public class CaptDatosTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_temp")
    private Integer idTemp;
    @Size(max = 5)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 50)
    @Column(name = "cod_fuente")
    private String codFuente;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(max = 2147483647)
    @Column(name = "datos")
    private String datos;
    @Size(max = 2)
    @Column(name = "estado_transmision")
    private String estadoTransmision;
    @Size(max = 2)
    @Column(name = "estado_proceso")
    private String estadoProceso;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;

    public CaptDatosTemp() {
    }

    public CaptDatosTemp(Integer idTemp) {
        this.idTemp = idTemp;
    }

    public Integer getIdTemp() {
        return idTemp;
    }

    public void setIdTemp(Integer idTemp) {
        this.idTemp = idTemp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodFuente() {
        return codFuente;
    }

    public void setCodFuente(String codFuente) {
        this.codFuente = codFuente;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getEstadoTransmision() {
        return estadoTransmision;
    }

    public void setEstadoTransmision(String estadoTransmision) {
        this.estadoTransmision = estadoTransmision;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemp != null ? idTemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptDatosTemp)) {
            return false;
        }
        CaptDatosTemp other = (CaptDatosTemp) object;
        if ((this.idTemp == null && other.idTemp != null) || (this.idTemp != null && !this.idTemp.equals(other.idTemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptDatosTemp[ idTemp=" + idTemp + " ]";
    }
    
}
