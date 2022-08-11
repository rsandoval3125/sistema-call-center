/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "seguridad.seg_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegUsuario.findAll", query = "SELECT s FROM SegUsuario s")})
public class SegUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 200)
    @Column(name = "clave")
    private String clave;
    @Column(name = "fechahora_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraCreacion;
    @Column(name = "fechahora_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraEdicion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Column(name = "fechahora_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraIni;
    @Column(name = "fechahora_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraFin;
    @JoinColumn(name = "cod_personal", referencedColumnName = "id_personal")
    @ManyToOne
    private AdmPersonal codPersonal;
    @JoinColumn(name = "cod_estado_usuario", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codEstadoUsuario;

    public SegUsuario() {
    }

    public SegUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechahoraCreacion() {
        return fechahoraCreacion;
    }

    public void setFechahoraCreacion(Date fechahoraCreacion) {
        this.fechahoraCreacion = fechahoraCreacion;
    }

    public Date getFechahoraEdicion() {
        return fechahoraEdicion;
    }

    public void setFechahoraEdicion(Date fechahoraEdicion) {
        this.fechahoraEdicion = fechahoraEdicion;
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

    public Date getFechahoraIni() {
        return fechahoraIni;
    }

    public void setFechahoraIni(Date fechahoraIni) {
        this.fechahoraIni = fechahoraIni;
    }

    public Date getFechahoraFin() {
        return fechahoraFin;
    }

    public void setFechahoraFin(Date fechahoraFin) {
        this.fechahoraFin = fechahoraFin;
    }

    public AdmPersonal getCodPersonal() {
        return codPersonal;
    }

    public void setCodPersonal(AdmPersonal codPersonal) {
        this.codPersonal = codPersonal;
    }

    public MetCatalogo getCodEstadoUsuario() {
        return codEstadoUsuario;
    }

    public void setCodEstadoUsuario(MetCatalogo codEstadoUsuario) {
        this.codEstadoUsuario = codEstadoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUsuario)) {
            return false;
        }
        SegUsuario other = (SegUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegUsuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
