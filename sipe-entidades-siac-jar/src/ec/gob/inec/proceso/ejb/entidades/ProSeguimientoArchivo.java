/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "proceso.pro_seguimiento_archivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProSeguimientoArchivo.findAll", query = "SELECT p FROM ProSeguimientoArchivo p")})
public class ProSeguimientoArchivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_segarc")
    private Integer idSegarc;
    @Size(max = 1)
    @Column(name = "tipo1")
    private String tipo1;
    @Size(max = 1)
    @Column(name = "tipo2")
    private String tipo2;
    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "archivo")
    private byte[] archivo;
    @Size(max = 500)
    @Column(name = "url")
    private String url;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Size(max = 100)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_seg", referencedColumnName = "id_seg")
    @ManyToOne
    private ProSeguimiento codSeg;

    public ProSeguimientoArchivo() {
    }

    public ProSeguimientoArchivo(Integer idSegarc) {
        this.idSegarc = idSegarc;
    }

    public ProSeguimientoArchivo(Integer idSegarc, boolean estadoLogico) {
        this.idSegarc = idSegarc;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdSegarc() {
        return idSegarc;
    }

    public void setIdSegarc(Integer idSegarc) {
        this.idSegarc = idSegarc;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public ProSeguimiento getCodSeg() {
        return codSeg;
    }

    public void setCodSeg(ProSeguimiento codSeg) {
        this.codSeg = codSeg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegarc != null ? idSegarc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProSeguimientoArchivo)) {
            return false;
        }
        ProSeguimientoArchivo other = (ProSeguimientoArchivo) object;
        if ((this.idSegarc == null && other.idSegarc != null) || (this.idSegarc != null && !this.idSegarc.equals(other.idSegarc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProSeguimientoArchivo[ idSegarc=" + idSegarc + " ]";
    }
    
}
