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
@Table(name = "administracion.adm_operacion_estadistica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmOperacionEstadistica.findAll", query = "SELECT a FROM AdmOperacionEstadistica a")})
public class AdmOperacionEstadistica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ope_est")
    private Integer idOpeEst;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "sigla")
    private String sigla;
    @Basic(optional = false)
    @Column(name = "nivel")
    private short nivel;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_institucion", referencedColumnName = "id_institucion")
    @ManyToOne
    private AdmInstitucion codInstitucion;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_ope_est")
    @ManyToOne
    private AdmOperacionEstadistica codPadre;
    @JoinColumn(name = "cod_organigrama", referencedColumnName = "id_organigrama")
    @ManyToOne
    private AdmOrganigrama codOrganigrama;

    public AdmOperacionEstadistica() {
    }

    public AdmOperacionEstadistica(Integer idOpeEst) {
        this.idOpeEst = idOpeEst;
    }

    public AdmOperacionEstadistica(Integer idOpeEst, String nombre, String descripcion, String sigla, short nivel, boolean estadoLogico) {
        this.idOpeEst = idOpeEst;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sigla = sigla;
        this.nivel = nivel;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdOpeEst() {
        return idOpeEst;
    }

    public void setIdOpeEst(Integer idOpeEst) {
        this.idOpeEst = idOpeEst;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public short getNivel() {
        return nivel;
    }

    public void setNivel(short nivel) {
        this.nivel = nivel;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public AdmInstitucion getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(AdmInstitucion codInstitucion) {
        this.codInstitucion = codInstitucion;
    }


    public AdmOperacionEstadistica getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(AdmOperacionEstadistica codPadre) {
        this.codPadre = codPadre;
    }

    public AdmOrganigrama getCodOrganigrama() {
        return codOrganigrama;
    }

    public void setCodOrganigrama(AdmOrganigrama codOrganigrama) {
        this.codOrganigrama = codOrganigrama;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpeEst != null ? idOpeEst.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmOperacionEstadistica)) {
            return false;
        }
        AdmOperacionEstadistica other = (AdmOperacionEstadistica) object;
        if ((this.idOpeEst == null && other.idOpeEst != null) || (this.idOpeEst != null && !this.idOpeEst.equals(other.idOpeEst))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica[ idOpeEst=" + idOpeEst + " ]";
    }

    
}
