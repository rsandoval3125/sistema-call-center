/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "metadato.met_catalogo_ciuo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetCatalogoCiuo.findAll", query = "SELECT m FROM MetCatalogoCiuo m")})
public class MetCatalogoCiuo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_catalogo_ciuo")
    private Integer idCatalogoCiuo;
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
    @Size(max = 20)
    @Column(name = "alias")
    private String alias;
    @Size(max = 50)
    @Column(name = "valor")
    private String valor;
    @Column(name = "orden")
    private BigInteger orden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private long nivel;
    @Size(max = 2147483647)
    @Column(name = "estilo")
    private String estilo;
    @Size(max = 255)
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "modificado_cache")
    private Integer modificadoCache;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_padre1", referencedColumnName = "id_catalogo_ciuo")
    @ManyToOne
    private MetCatalogoCiuo codPadre1;
    @JoinColumn(name = "cod_padre2", referencedColumnName = "id_catalogo_ciuo")
    @ManyToOne
    private MetCatalogoCiuo codPadre2;
    @JoinColumn(name = "cod_padre3", referencedColumnName = "id_catalogo_ciuo")
    @ManyToOne
    private MetCatalogoCiuo codPadre3;
    @JoinColumn(name = "cod_tipo_catalogo", referencedColumnName = "id_tipo_catalogo")
    @ManyToOne(optional = false)
    private MetTipoCatalogo codTipoCatalogo;

    public MetCatalogoCiuo() {
    }

    public MetCatalogoCiuo(Integer idCatalogoCiuo) {
        this.idCatalogoCiuo = idCatalogoCiuo;
    }

    public MetCatalogoCiuo(Integer idCatalogoCiuo, String nombre, String descripcion, long nivel, boolean estadoLogico) {
        this.idCatalogoCiuo = idCatalogoCiuo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCatalogoCiuo() {
        return idCatalogoCiuo;
    }

    public void setIdCatalogoCiuo(Integer idCatalogoCiuo) {
        this.idCatalogoCiuo = idCatalogoCiuo;
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

    public MetCatalogoCiuo getCodPadre1() {
        return codPadre1;
    }

    public void setCodPadre1(MetCatalogoCiuo codPadre1) {
        this.codPadre1 = codPadre1;
    }

    public MetCatalogoCiuo getCodPadre2() {
        return codPadre2;
    }

    public void setCodPadre2(MetCatalogoCiuo codPadre2) {
        this.codPadre2 = codPadre2;
    }

    public MetCatalogoCiuo getCodPadre3() {
        return codPadre3;
    }

    public void setCodPadre3(MetCatalogoCiuo codPadre3) {
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
        hash += (idCatalogoCiuo != null ? idCatalogoCiuo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetCatalogoCiuo)) {
            return false;
        }
        MetCatalogoCiuo other = (MetCatalogoCiuo) object;
        if ((this.idCatalogoCiuo == null && other.idCatalogoCiuo != null) || (this.idCatalogoCiuo != null && !this.idCatalogoCiuo.equals(other.idCatalogoCiuo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetCatalogoCiuo[ idCatalogoCiuo=" + idCatalogoCiuo + " ]";
    }
    
}
