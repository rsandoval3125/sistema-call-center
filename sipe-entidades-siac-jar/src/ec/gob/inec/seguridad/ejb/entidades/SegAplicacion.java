/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

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
@Table(name = "seguridad.seg_aplicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAplicacion.findAll", query = "SELECT s FROM SegAplicacion s")})
public class SegAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_apl")
    private Integer idApl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 125)
    @Column(name = "url")
    private String url;
    @Size(max = 2147483647)
    @Column(name = "configuraciones")
    private String configuraciones;
    @Size(max = 125)
    @Column(name = "alias")
    private String alias;
    @Column(name = "fecha_reg")
    @Temporal(TemporalType.DATE)
    private Date fechaReg;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_tipo_apl", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codTipoApl;
    @JoinColumn(name = "cod_estado_apl", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codEstadoApl;

    public SegAplicacion() {
    }

    public SegAplicacion(Integer idApl) {
        this.idApl = idApl;
    }

    public SegAplicacion(Integer idApl, String nombre, String descripcion) {
        this.idApl = idApl;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getIdApl() {
        return idApl;
    }

    public void setIdApl(Integer idApl) {
        this.idApl = idApl;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(String configuraciones) {
        this.configuraciones = configuraciones;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
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

    public MetCatalogo getCodTipoApl() {
        return codTipoApl;
    }

    public void setCodTipoApl(MetCatalogo codTipoApl) {
        this.codTipoApl = codTipoApl;
    }

    public MetCatalogo getCodEstadoApl() {
        return codEstadoApl;
    }

    public void setCodEstadoApl(MetCatalogo codEstadoApl) {
        this.codEstadoApl = codEstadoApl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idApl != null ? idApl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAplicacion)) {
            return false;
        }
        SegAplicacion other = (SegAplicacion) object;
        if ((this.idApl == null && other.idApl != null) || (this.idApl != null && !this.idApl.equals(other.idApl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegAplicacion[ idApl=" + idApl + " ]";
    }
    
}
