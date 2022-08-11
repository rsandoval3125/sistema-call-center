/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "metadato.met_tipo_catalogo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetTipoCatalogo.findAll", query = "SELECT m FROM MetTipoCatalogo m")})
public class MetTipoCatalogo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_catalogo")
    private Integer idTipoCatalogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1280)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1280)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "alias")
    private String alias;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @Column(name = "modificado_cache")
    private Integer modificadoCache;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_tipo_uso", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoUso;

    public MetTipoCatalogo() {
    }

    public MetTipoCatalogo(Integer idTipoCatalogo) {
        this.idTipoCatalogo = idTipoCatalogo;
    }

    public MetTipoCatalogo(Integer idTipoCatalogo, String nombre, String descripcion, String alias, int orden, boolean estadoLogico) {
        this.idTipoCatalogo = idTipoCatalogo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.alias = alias;
        this.orden = orden;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdTipoCatalogo() {
        return idTipoCatalogo;
    }

    public void setIdTipoCatalogo(Integer idTipoCatalogo) {
        this.idTipoCatalogo = idTipoCatalogo;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Integer getModificadoCache() {
        return modificadoCache;
    }

    public void setModificadoCache(Integer modificadoCache) {
        this.modificadoCache = modificadoCache;
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

    public MetCatalogo getCodTipoUso() {
        return codTipoUso;
    }

    public void setCodTipoUso(MetCatalogo codTipoUso) {
        this.codTipoUso = codTipoUso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCatalogo != null ? idTipoCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetTipoCatalogo)) {
            return false;
        }
        MetTipoCatalogo other = (MetTipoCatalogo) object;
        if ((this.idTipoCatalogo == null && other.idTipoCatalogo != null) || (this.idTipoCatalogo != null && !this.idTipoCatalogo.equals(other.idTipoCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo[ idTipoCatalogo=" + idTipoCatalogo + " ]";
    }
    
}
