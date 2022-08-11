/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.geografia.ejb.entidades;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "geografia.geo_dpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeoDpa.findAll", query = "SELECT g FROM GeoDpa g")})
public class GeoDpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dpa")
    private Integer idDpa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 32)
    @Column(name = "codigo_padre")
    private String codigoPadre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 225)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "codigo_geografico")
    private String codigoGeografico;
    @Size(max = 32)
    @Column(name = "codigo_geografico_padre")
    private String codigoGeograficoPadre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "geometria_geojson")
    private String geometriaGeojson;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modificado")
    private int modificado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_hijos")
    private int numeroHijos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numeracion")
    private String numeracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "shape")
    private String shape;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_cobertura", referencedColumnName = "indice")
    @ManyToOne(optional = false)
    private GeoCobertura codCobertura;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codTipo;

    public GeoDpa() {
    }

    public GeoDpa(Integer idDpa) {
        this.idDpa = idDpa;
    }

    public GeoDpa(Integer idDpa, String codigo, String nombre, String descripcion, String codigoGeografico, String geometriaGeojson, int modificado, int numeroHijos, String numeracion, String shape) {
        this.idDpa = idDpa;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoGeografico = codigoGeografico;
        this.geometriaGeojson = geometriaGeojson;
        this.modificado = modificado;
        this.numeroHijos = numeroHijos;
        this.numeracion = numeracion;
        this.shape = shape;
    }

    public Integer getIdDpa() {
        return idDpa;
    }

    public void setIdDpa(Integer idDpa) {
        this.idDpa = idDpa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPadre() {
        return codigoPadre;
    }

    public void setCodigoPadre(String codigoPadre) {
        this.codigoPadre = codigoPadre;
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

    public String getCodigoGeografico() {
        return codigoGeografico;
    }

    public void setCodigoGeografico(String codigoGeografico) {
        this.codigoGeografico = codigoGeografico;
    }

    public String getCodigoGeograficoPadre() {
        return codigoGeograficoPadre;
    }

    public void setCodigoGeograficoPadre(String codigoGeograficoPadre) {
        this.codigoGeograficoPadre = codigoGeograficoPadre;
    }

    public String getGeometriaGeojson() {
        return geometriaGeojson;
    }

    public void setGeometriaGeojson(String geometriaGeojson) {
        this.geometriaGeojson = geometriaGeojson;
    }

    public int getModificado() {
        return modificado;
    }

    public void setModificado(int modificado) {
        this.modificado = modificado;
    }

    public int getNumeroHijos() {
        return numeroHijos;
    }

    public void setNumeroHijos(int numeroHijos) {
        this.numeroHijos = numeroHijos;
    }

    public String getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(String numeracion) {
        this.numeracion = numeracion;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public GeoCobertura getCodCobertura() {
        return codCobertura;
    }

    public void setCodCobertura(GeoCobertura codCobertura) {
        this.codCobertura = codCobertura;
    }

    public MetCatalogo getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(MetCatalogo codTipo) {
        this.codTipo = codTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDpa != null ? idDpa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeoDpa)) {
            return false;
        }
        GeoDpa other = (GeoDpa) object;
        if ((this.idDpa == null && other.idDpa != null) || (this.idDpa != null && !this.idDpa.equals(other.idDpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.geografia.ejb.entidades.GeoDpa[ idDpa=" + idDpa + " ]";
    }
    
}
