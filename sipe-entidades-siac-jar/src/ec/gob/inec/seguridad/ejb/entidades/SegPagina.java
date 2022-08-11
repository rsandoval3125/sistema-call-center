/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "seguridad.seg_pagina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegPagina.findAll", query = "SELECT s FROM SegPagina s")})
public class SegPagina implements Serializable {

     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pag")
    private Integer idPag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 125)
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private short nivel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ordn_imprime")
    private short ordnImprime;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 2147483647)
    @Column(name = "atributo")
    private String atributo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 125)
    @Column(name = "alias")
    private String alias;
    @JoinColumn(name = "cod_estado_pagina", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codEstadoPagina;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipo;
    @JoinColumn(name = "cod_apl", referencedColumnName = "id_apl")
    @ManyToOne(optional = false)
    private SegAplicacion codApl;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_pag")
    @ManyToOne
    private SegPagina codPadre;

    public SegPagina() {
    }

    public SegPagina(Integer idPag) {
        this.idPag = idPag;
    }

    public SegPagina(Integer idPag, String nombre, String descripcion, short nivel, short ordnImprime, String alias) {
        this.idPag = idPag;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.ordnImprime = ordnImprime;
        this.alias = alias;
    }

    public Integer getIdPag() {
        return idPag;
    }

    public void setIdPag(Integer idPag) {
        this.idPag = idPag;
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

    public short getNivel() {
        return nivel;
    }

    public void setNivel(short nivel) {
        this.nivel = nivel;
    }

    public short getOrdnImprime() {
        return ordnImprime;
    }

    public void setOrdnImprime(short ordnImprime) {
        this.ordnImprime = ordnImprime;
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

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public MetCatalogo getCodEstadoPagina() {
        return codEstadoPagina;
    }

    public void setCodEstadoPagina(MetCatalogo codEstadoPagina) {
        this.codEstadoPagina = codEstadoPagina;
    }

    public MetCatalogo getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(MetCatalogo codTipo) {
        this.codTipo = codTipo;
    }

    public SegAplicacion getCodApl() {
        return codApl;
    }

    public void setCodApl(SegAplicacion codApl) {
        this.codApl = codApl;
    }

    public SegPagina getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(SegPagina codPadre) {
        this.codPadre = codPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPag != null ? idPag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPagina)) {
            return false;
        }
        SegPagina other = (SegPagina) object;
        if ((this.idPag == null && other.idPag != null) || (this.idPag != null && !this.idPag.equals(other.idPag))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegPagina[ idPag=" + idPag + " ]";
    }
    
}
