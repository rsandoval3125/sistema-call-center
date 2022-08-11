/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
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
@Table(name = "muestra.mue_muestra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueMuestra.findAll", query = "SELECT m FROM MueMuestra m")})
public class MueMuestra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_muestra")
    private Integer idMuestra;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 50)
    @Column(name = "alias")
    private String alias;
    @JoinColumn(name = "cod_ope_est", referencedColumnName = "id_ope_est")
    @ManyToOne
    private AdmOperacionEstadistica codOpeEst;
    @JoinColumn(name = "cod_periodo", referencedColumnName = "id_periodo")
    @ManyToOne
    private AdmPeriodo codPeriodo;
    @JoinColumn(name = "tipo", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo tipo;

    public MueMuestra() {
    }

    public MueMuestra(Integer idMuestra) {
        this.idMuestra = idMuestra;
    }

    public MueMuestra(Integer idMuestra, String nombre, String descripcion, boolean estadoLogico) {
        this.idMuestra = idMuestra;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(Integer idMuestra) {
        this.idMuestra = idMuestra;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public AdmOperacionEstadistica getCodOpeEst() {
        return codOpeEst;
    }

    public void setCodOpeEst(AdmOperacionEstadistica codOpeEst) {
        this.codOpeEst = codOpeEst;
    }

    public AdmPeriodo getCodPeriodo() {
        return codPeriodo;
    }

    public void setCodPeriodo(AdmPeriodo codPeriodo) {
        this.codPeriodo = codPeriodo;
    }

    public MetCatalogo getTipo() {
        return tipo;
    }

    public void setTipo(MetCatalogo tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMuestra != null ? idMuestra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueMuestra)) {
            return false;
        }
        MueMuestra other = (MueMuestra) object;
        if ((this.idMuestra == null && other.idMuestra != null) || (this.idMuestra != null && !this.idMuestra.equals(other.idMuestra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueMuestra[ idMuestra=" + idMuestra + " ]";
    }
    
}
