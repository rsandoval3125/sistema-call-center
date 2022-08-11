/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "metadato.met_catalogo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetCatalogo.findAll", query = "SELECT m FROM MetCatalogo m")})
public class MetCatalogo implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_catalogo")
    private Integer idCatalogo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "alias")
    private String alias;
    @Column(name = "valor")
    private String valor;
    @Column(name = "orden")
    private BigInteger orden;
    @Basic(optional = false)
    @Column(name = "nivel")
    private long nivel;
    @Column(name = "estilo")
    private String estilo;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "modificado_cache")
    private Integer modificadoCache;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico; 
    @JoinColumn(name = "cod_padre1", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codPadre1;
    @JoinColumn(name = "cod_padre2", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codPadre2;
    @JoinColumn(name = "cod_padre3", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codPadre3;
    @JoinColumn(name = "cod_tipo_catalogo", referencedColumnName = "id_tipo_catalogo")
    @ManyToOne(optional = false)
    private MetTipoCatalogo codTipoCatalogo;

    public MetCatalogo() {
    }

    public MetCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public MetCatalogo(Integer idCatalogo, String nombre, String descripcion, long nivel, boolean estadoLogico) {
        this.idCatalogo = idCatalogo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public long getNivel() {
        return nivel;
    }

    public void setNivel(long nivel) {
        this.nivel = nivel;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public MetCatalogo getCodPadre1() {
        return codPadre1;
    }

    public void setCodPadre1(MetCatalogo codPadre1) {
        this.codPadre1 = codPadre1;
    }

    public MetCatalogo getCodPadre2() {
        return codPadre2;
    }

    public void setCodPadre2(MetCatalogo codPadre2) {
        this.codPadre2 = codPadre2;
    }

    public MetCatalogo getCodPadre3() {
        return codPadre3;
    }

    public void setCodPadre3(MetCatalogo codPadre3) {
        this.codPadre3 = codPadre3;
    }

    public MetTipoCatalogo getCodTipoCatalogo() {
        return codTipoCatalogo;
    }

    public void setCodTipoCatalogo(MetTipoCatalogo codTipoCatalogo) {
        this.codTipoCatalogo = codTipoCatalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatalogo != null ? idCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetCatalogo)) {
            return false;
        }
        MetCatalogo other = (MetCatalogo) object;
        if ((this.idCatalogo == null && other.idCatalogo != null) || (this.idCatalogo != null && !this.idCatalogo.equals(other.idCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetCatalogo[ idCatalogo=" + idCatalogo + " ]";
    }

  
}
