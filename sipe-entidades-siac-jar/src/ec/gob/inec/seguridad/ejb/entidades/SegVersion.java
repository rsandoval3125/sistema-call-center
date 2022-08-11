/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

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
@Table(name = "seguridad.seg_version")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegVersion.findAll", query = "SELECT s FROM SegVersion s")})
public class SegVersion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_version")
    private Integer idVersion;
    @Column(name = "proyecto")
    private Boolean proyecto;
    @Column(name = "fechahora_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraCreacion;
    @Column(name = "fechahora_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraEdicion;
    @Size(max = 200)
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "num_version")
    private Short numVersion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_aplicacion", referencedColumnName = "id_apl")
    @ManyToOne
    private SegAplicacion codAplicacion;
    @JoinColumn(name = "cod_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuario;

    public SegVersion() {
    }

    public SegVersion(Integer idVersion) {
        this.idVersion = idVersion;
    }

    public SegVersion(Integer idVersion, boolean estadoLogico) {
        this.idVersion = idVersion;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Integer idVersion) {
        this.idVersion = idVersion;
    }

    public Boolean getProyecto() {
        return proyecto;
    }

    public void setProyecto(Boolean proyecto) {
        this.proyecto = proyecto;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Short getNumVersion() {
        return numVersion;
    }

    public void setNumVersion(Short numVersion) {
        this.numVersion = numVersion;
    }

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public SegAplicacion getCodAplicacion() {
        return codAplicacion;
    }

    public void setCodAplicacion(SegAplicacion codAplicacion) {
        this.codAplicacion = codAplicacion;
    }

    public SegUsuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(SegUsuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVersion != null ? idVersion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegVersion)) {
            return false;
        }
        SegVersion other = (SegVersion) object;
        if ((this.idVersion == null && other.idVersion != null) || (this.idVersion != null && !this.idVersion.equals(other.idVersion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegVersion[ idVersion=" + idVersion + " ]";
    }
    
}
