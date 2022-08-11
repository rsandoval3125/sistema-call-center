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
@Table(name = "administracion.adm_tabla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmTabla.findAll", query = "SELECT a FROM AdmTabla a")})
public class AdmTabla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tabla")
    private Integer idTabla;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "alias")
    private String alias;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_crea")
    private Integer usuarioCrea;
    @Column(name = "usuario_baja")
    private Integer usuarioBaja;
    @Column(name = "usuario_modificacion")
    private Integer usuarioModificacion;
    @Column(name = "modificado")
    private Integer modificado;
    @Column(name = "basedatos")
    private Integer basedatos;

    public AdmTabla() {
    }

    public AdmTabla(Integer idTabla) {
        this.idTabla = idTabla;
    }

    public Integer getIdTabla() {
        return idTabla;
    }

    public void setIdTabla(Integer idTabla) {
        this.idTabla = idTabla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public Integer getUsuarioBaja() {
        return usuarioBaja;
    }

    public void setUsuarioBaja(Integer usuarioBaja) {
        this.usuarioBaja = usuarioBaja;
    }

    public Integer getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Integer usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Integer getModificado() {
        return modificado;
    }

    public void setModificado(Integer modificado) {
        this.modificado = modificado;
    }

    public Integer getBasedatos() {
        return basedatos;
    }

    public void setBasedatos(Integer basedatos) {
        this.basedatos = basedatos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTabla != null ? idTabla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmTabla)) {
            return false;
        }
        AdmTabla other = (AdmTabla) object;
        if ((this.idTabla == null && other.idTabla != null) || (this.idTabla != null && !this.idTabla.equals(other.idTabla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmTabla[ idTabla=" + idTabla + " ]";
    }
    
}
