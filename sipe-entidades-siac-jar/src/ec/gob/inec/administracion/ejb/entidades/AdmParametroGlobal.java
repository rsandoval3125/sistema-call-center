/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_parametro_global")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmParametroGlobal.findAll", query = "SELECT a FROM AdmParametroGlobal a")})
public class AdmParametroGlobal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parametro")
    private Integer idParametro;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "sistema")
    private String sistema;
    @Column(name = "condicion")
    private String condicion;
    @Column(name = "valor")
    private String valor;
    @Column(name = "fecha_vigencia_ini")
    @Temporal(TemporalType.DATE)
    private Date fechaVigenciaIni;
    @Column(name = "fecha_vigencia_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaVigenciaFin;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;

    public AdmParametroGlobal() {
    }

    public AdmParametroGlobal(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public AdmParametroGlobal(Integer idParametro, boolean estadoLogico) {
        this.idParametro = idParametro;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getFechaVigenciaIni() {
        return fechaVigenciaIni;
    }

    public void setFechaVigenciaIni(Date fechaVigenciaIni) {
        this.fechaVigenciaIni = fechaVigenciaIni;
    }

    public Date getFechaVigenciaFin() {
        return fechaVigenciaFin;
    }

    public void setFechaVigenciaFin(Date fechaVigenciaFin) {
        this.fechaVigenciaFin = fechaVigenciaFin;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmParametroGlobal)) {
            return false;
        }
        AdmParametroGlobal other = (AdmParametroGlobal) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal[ idParametro=" + idParametro + " ]";
    }
    
}
