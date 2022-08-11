/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.geografia.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "geografia.geo_cobertura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeoCobertura.findAll", query = "SELECT g FROM GeoCobertura g")})
public class GeoCobertura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cobertura")
    private Integer idCobertura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indice")
    private int indice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cod_cobertura_padre")
    private Integer codCoberturaPadre;
    @Column(name = "cod_cobertura_hijo")
    private Integer codCoberturaHijo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_longitud")
    private int codigoLongitud;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "zoom_minimo")
    private BigDecimal zoomMinimo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zoom_maximo")
    private BigDecimal zoomMaximo;
    @Column(name = "permite_edicion")
    private Integer permiteEdicion;
    @Column(name = "cod_simbolo_interno")
    private Integer codSimboloInterno;
    @Column(name = "transparencia_interno")
    private Integer transparenciaInterno;
    @Column(name = "rojo_interno")
    private Integer rojoInterno;
    @Column(name = "azul_interno")
    private Integer azulInterno;
    @Column(name = "verde_interno")
    private Integer verdeInterno;
    @Column(name = "tamanio_interno")
    private BigDecimal tamanioInterno;
    @Column(name = "transparencia_externo")
    private Integer transparenciaExterno;
    @Column(name = "rojo_externo")
    private Integer rojoExterno;
    @Column(name = "azul_externo")
    private Integer azulExterno;
    @Column(name = "verde_externo")
    private Integer verdeExterno;
    @Column(name = "tamanio_externo")
    private BigDecimal tamanioExterno;
    @Column(name = "transparencia_etiqueta")
    private Integer transparenciaEtiqueta;
    @Column(name = "rojo_etiqueta")
    private Integer rojoEtiqueta;
    @Column(name = "azul_etiqueta")
    private Integer azulEtiqueta;
    @Column(name = "verde_etiqueta")
    private Integer verdeEtiqueta;
    @Column(name = "tamanio_etiqueta")
    private BigDecimal tamanioEtiqueta;
    @Column(name = "cod_tipo_etiqueta")
    private Integer codTipoEtiqueta;
    @Column(name = "cod_etiqueta_lugar")
    private Integer codEtiquetaLugar;
    @Size(max = 2147483647)
    @Column(name = "expresion_etiqueta")
    private String expresionEtiqueta;
    @Column(name = "cod_simbolo_externo")
    private Integer codSimboloExterno;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 64)
    @Column(name = "codigo_estandar")
    private String codigoEstandar;
    @JoinColumn(name = "cod_tipo_geometria", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoGeometria;

    public GeoCobertura() {
    }

    public GeoCobertura(Integer idCobertura) {
        this.idCobertura = idCobertura;
    }

    public GeoCobertura(Integer idCobertura, int indice, String nombre, int codigoLongitud, BigDecimal zoomMinimo, BigDecimal zoomMaximo) {
        this.idCobertura = idCobertura;
        this.indice = indice;
        this.nombre = nombre;
        this.codigoLongitud = codigoLongitud;
        this.zoomMinimo = zoomMinimo;
        this.zoomMaximo = zoomMaximo;
    }

    public Integer getIdCobertura() {
        return idCobertura;
    }

    public void setIdCobertura(Integer idCobertura) {
        this.idCobertura = idCobertura;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodCoberturaPadre() {
        return codCoberturaPadre;
    }

    public void setCodCoberturaPadre(Integer codCoberturaPadre) {
        this.codCoberturaPadre = codCoberturaPadre;
    }

    public Integer getCodCoberturaHijo() {
        return codCoberturaHijo;
    }

    public void setCodCoberturaHijo(Integer codCoberturaHijo) {
        this.codCoberturaHijo = codCoberturaHijo;
    }

    public int getCodigoLongitud() {
        return codigoLongitud;
    }

    public void setCodigoLongitud(int codigoLongitud) {
        this.codigoLongitud = codigoLongitud;
    }

    public BigDecimal getZoomMinimo() {
        return zoomMinimo;
    }

    public void setZoomMinimo(BigDecimal zoomMinimo) {
        this.zoomMinimo = zoomMinimo;
    }

    public BigDecimal getZoomMaximo() {
        return zoomMaximo;
    }

    public void setZoomMaximo(BigDecimal zoomMaximo) {
        this.zoomMaximo = zoomMaximo;
    }

    public Integer getPermiteEdicion() {
        return permiteEdicion;
    }

    public void setPermiteEdicion(Integer permiteEdicion) {
        this.permiteEdicion = permiteEdicion;
    }

    public Integer getCodSimboloInterno() {
        return codSimboloInterno;
    }

    public void setCodSimboloInterno(Integer codSimboloInterno) {
        this.codSimboloInterno = codSimboloInterno;
    }

    public Integer getTransparenciaInterno() {
        return transparenciaInterno;
    }

    public void setTransparenciaInterno(Integer transparenciaInterno) {
        this.transparenciaInterno = transparenciaInterno;
    }

    public Integer getRojoInterno() {
        return rojoInterno;
    }

    public void setRojoInterno(Integer rojoInterno) {
        this.rojoInterno = rojoInterno;
    }

    public Integer getAzulInterno() {
        return azulInterno;
    }

    public void setAzulInterno(Integer azulInterno) {
        this.azulInterno = azulInterno;
    }

    public Integer getVerdeInterno() {
        return verdeInterno;
    }

    public void setVerdeInterno(Integer verdeInterno) {
        this.verdeInterno = verdeInterno;
    }

    public BigDecimal getTamanioInterno() {
        return tamanioInterno;
    }

    public void setTamanioInterno(BigDecimal tamanioInterno) {
        this.tamanioInterno = tamanioInterno;
    }

    public Integer getTransparenciaExterno() {
        return transparenciaExterno;
    }

    public void setTransparenciaExterno(Integer transparenciaExterno) {
        this.transparenciaExterno = transparenciaExterno;
    }

    public Integer getRojoExterno() {
        return rojoExterno;
    }

    public void setRojoExterno(Integer rojoExterno) {
        this.rojoExterno = rojoExterno;
    }

    public Integer getAzulExterno() {
        return azulExterno;
    }

    public void setAzulExterno(Integer azulExterno) {
        this.azulExterno = azulExterno;
    }

    public Integer getVerdeExterno() {
        return verdeExterno;
    }

    public void setVerdeExterno(Integer verdeExterno) {
        this.verdeExterno = verdeExterno;
    }

    public BigDecimal getTamanioExterno() {
        return tamanioExterno;
    }

    public void setTamanioExterno(BigDecimal tamanioExterno) {
        this.tamanioExterno = tamanioExterno;
    }

    public Integer getTransparenciaEtiqueta() {
        return transparenciaEtiqueta;
    }

    public void setTransparenciaEtiqueta(Integer transparenciaEtiqueta) {
        this.transparenciaEtiqueta = transparenciaEtiqueta;
    }

    public Integer getRojoEtiqueta() {
        return rojoEtiqueta;
    }

    public void setRojoEtiqueta(Integer rojoEtiqueta) {
        this.rojoEtiqueta = rojoEtiqueta;
    }

    public Integer getAzulEtiqueta() {
        return azulEtiqueta;
    }

    public void setAzulEtiqueta(Integer azulEtiqueta) {
        this.azulEtiqueta = azulEtiqueta;
    }

    public Integer getVerdeEtiqueta() {
        return verdeEtiqueta;
    }

    public void setVerdeEtiqueta(Integer verdeEtiqueta) {
        this.verdeEtiqueta = verdeEtiqueta;
    }

    public BigDecimal getTamanioEtiqueta() {
        return tamanioEtiqueta;
    }

    public void setTamanioEtiqueta(BigDecimal tamanioEtiqueta) {
        this.tamanioEtiqueta = tamanioEtiqueta;
    }

    public Integer getCodTipoEtiqueta() {
        return codTipoEtiqueta;
    }

    public void setCodTipoEtiqueta(Integer codTipoEtiqueta) {
        this.codTipoEtiqueta = codTipoEtiqueta;
    }

    public Integer getCodEtiquetaLugar() {
        return codEtiquetaLugar;
    }

    public void setCodEtiquetaLugar(Integer codEtiquetaLugar) {
        this.codEtiquetaLugar = codEtiquetaLugar;
    }

    public String getExpresionEtiqueta() {
        return expresionEtiqueta;
    }

    public void setExpresionEtiqueta(String expresionEtiqueta) {
        this.expresionEtiqueta = expresionEtiqueta;
    }

    public Integer getCodSimboloExterno() {
        return codSimboloExterno;
    }

    public void setCodSimboloExterno(Integer codSimboloExterno) {
        this.codSimboloExterno = codSimboloExterno;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getCodigoEstandar() {
        return codigoEstandar;
    }

    public void setCodigoEstandar(String codigoEstandar) {
        this.codigoEstandar = codigoEstandar;
    }

    public MetCatalogo getCodTipoGeometria() {
        return codTipoGeometria;
    }

    public void setCodTipoGeometria(MetCatalogo codTipoGeometria) {
        this.codTipoGeometria = codTipoGeometria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCobertura != null ? idCobertura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeoCobertura)) {
            return false;
        }
        GeoCobertura other = (GeoCobertura) object;
        if ((this.idCobertura == null && other.idCobertura != null) || (this.idCobertura != null && !this.idCobertura.equals(other.idCobertura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.geografia.ejb.entidades.GeoCobertura[ idCobertura=" + idCobertura + " ]";
    }
    
}
