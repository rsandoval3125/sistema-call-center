/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_columna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmColumna.findAll", query = "SELECT a FROM AdmColumna a")})
public class AdmColumna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_columna")
    private Integer idColumna;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "alias")
    private String alias;
    @Column(name = "longitud_minima")
    private Integer longitudMinima;
    @Column(name = "longitud_maxima")
    private Integer longitudMaxima;
    @Column(name = "valor_minimo")
    private String valorMinimo;
    @Column(name = "valor_maximo")
    private String valorMaximo;
    @Column(name = "definicion")
    private Integer definicion;
    @Column(name = "cadena_numerica")
    private Integer cadenaNumerica;
    @Column(name = "es_pk")
    private Integer esPk;
    @Column(name = "obligatorio")
    private Integer obligatorio;
    @Column(name = "numero_decimales")
    private Integer numeroDecimales;
    @Column(name = "orden_impresion")
    private Integer ordenImpresion;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "fila")
    private Integer fila;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_crea")
    private Integer usuarioCrea;
    @Column(name = "usuario_baja")
    private Integer usuarioBaja;
    @Column(name = "usuario_modificacion")
    private Integer usuarioModificacion;
    @Column(name = "encr")
    private Boolean encr;
    @JoinColumn(name = "cod_tabla", referencedColumnName = "id_tabla")
    @ManyToOne
    private AdmTabla codTabla;
    @JoinColumn(name = "cod_tipo_control", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoControl;
    @JoinColumn(name = "cod_tipo_dato", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoDato;
    @JoinColumn(name = "cod_tipo_encr", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoEncr;

    public AdmColumna() {
    }

    public AdmColumna(Integer idColumna) {
        this.idColumna = idColumna;
    }

    public Integer getIdColumna() {
        return idColumna;
    }

    public void setIdColumna(Integer idColumna) {
        this.idColumna = idColumna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getLongitudMinima() {
        return longitudMinima;
    }

    public void setLongitudMinima(Integer longitudMinima) {
        this.longitudMinima = longitudMinima;
    }

    public Integer getLongitudMaxima() {
        return longitudMaxima;
    }

    public void setLongitudMaxima(Integer longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    public String getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public String getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Integer getDefinicion() {
        return definicion;
    }

    public void setDefinicion(Integer definicion) {
        this.definicion = definicion;
    }

    public Integer getCadenaNumerica() {
        return cadenaNumerica;
    }

    public void setCadenaNumerica(Integer cadenaNumerica) {
        this.cadenaNumerica = cadenaNumerica;
    }

    public Integer getEsPk() {
        return esPk;
    }

    public void setEsPk(Integer esPk) {
        this.esPk = esPk;
    }

    public Integer getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Integer obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Integer getNumeroDecimales() {
        return numeroDecimales;
    }

    public void setNumeroDecimales(Integer numeroDecimales) {
        this.numeroDecimales = numeroDecimales;
    }

    public Integer getOrdenImpresion() {
        return ordenImpresion;
    }

    public void setOrdenImpresion(Integer ordenImpresion) {
        this.ordenImpresion = ordenImpresion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public Integer getUsuarioBaja() {
        return usuarioBaja;
    }

    public void setUsuarioBaja(Integer usuarioBaja) {
        this.usuarioBaja = usuarioBaja;
    }

    public Integer getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Integer usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Boolean getEncr() {
        return encr;
    }

    public void setEncr(Boolean encr) {
        this.encr = encr;
    }

    public AdmTabla getCodTabla() {
        return codTabla;
    }

    public void setCodTabla(AdmTabla codTabla) {
        this.codTabla = codTabla;
    }

    public MetCatalogo getCodTipoControl() {
        return codTipoControl;
    }

    public void setCodTipoControl(MetCatalogo codTipoControl) {
        this.codTipoControl = codTipoControl;
    }

    public MetCatalogo getCodTipoDato() {
        return codTipoDato;
    }

    public void setCodTipoDato(MetCatalogo codTipoDato) {
        this.codTipoDato = codTipoDato;
    }

    public MetCatalogo getCodTipoEncr() {
        return codTipoEncr;
    }

    public void setCodTipoEncr(MetCatalogo codTipoEncr) {
        this.codTipoEncr = codTipoEncr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColumna != null ? idColumna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmColumna)) {
            return false;
        }
        AdmColumna other = (AdmColumna) object;
        if ((this.idColumna == null && other.idColumna != null) || (this.idColumna != null && !this.idColumna.equals(other.idColumna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmColumna[ idColumna=" + idColumna + " ]";
    }
    
}
