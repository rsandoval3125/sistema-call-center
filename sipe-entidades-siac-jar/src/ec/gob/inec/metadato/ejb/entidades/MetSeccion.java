/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
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
@Table(name = "metadato.met_seccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetSeccion.findAll", query = "SELECT m FROM MetSeccion m")})
public class MetSeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seccion")
    private Integer idSeccion;
    @Size(max = 3)
    @Column(name = "tipo")
    private String tipo;
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
    @Column(name = "nuevo_registros")
    private Short nuevoRegistros;
    @Column(name = "eliminar_registros")
    private Short eliminarRegistros;
    @Column(name = "reemplazar_registros")
    private Short reemplazarRegistros;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 2147483647)
    @Column(name = "subtitulo")
    private String subtitulo;
    @Column(name = "muestra_titulo")
    private Short muestraTitulo;
    @Column(name = "muestra_subtitulo")
    private Short muestraSubtitulo;
    @Size(max = 2147483647)
    @Column(name = "estilo")
    private String estilo;
    @Column(name = "filas")
    private Integer filas;
    @Column(name = "columnas")
    private Integer columnas;
    @Column(name = "suma_fila")
    private Short sumaFila;
    @Column(name = "suma_columna")
    private Short sumaColumna;
    @Column(name = "suma_total")
    private Short sumaTotal;
    @Column(name = "fila_orden")
    private Short filaOrden;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "nivel")
    private Integer nivel;
    @Size(max = 2147483647)
    @Column(name = "tooltip")
    private String tooltip;
    @Column(name = "autocalculo")
    private Short autocalculo;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 40)
    @Column(name = "alias")
    private String alias;
    @JoinColumn(name = "cod_institucion", referencedColumnName = "id_institucion")
    @ManyToOne
    private AdmInstitucion codInstitucion;
    @JoinColumn(name = "cod_organigrama", referencedColumnName = "id_organigrama")
    @ManyToOne
    private AdmOrganigrama codOrganigrama;
    @JoinColumn(name = "cod_seccion_padre", referencedColumnName = "id_seccion")
    @ManyToOne
    private MetSeccion codSeccionPadre;

    public MetSeccion() {
    }

    public MetSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public MetSeccion(Integer idSeccion, String nombre, String descripcion, String titulo, boolean estadoLogico) {
        this.idSeccion = idSeccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Short getNuevoRegistros() {
        return nuevoRegistros;
    }

    public void setNuevoRegistros(Short nuevoRegistros) {
        this.nuevoRegistros = nuevoRegistros;
    }

    public Short getEliminarRegistros() {
        return eliminarRegistros;
    }

    public void setEliminarRegistros(Short eliminarRegistros) {
        this.eliminarRegistros = eliminarRegistros;
    }

    public Short getReemplazarRegistros() {
        return reemplazarRegistros;
    }

    public void setReemplazarRegistros(Short reemplazarRegistros) {
        this.reemplazarRegistros = reemplazarRegistros;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public Short getMuestraTitulo() {
        return muestraTitulo;
    }

    public void setMuestraTitulo(Short muestraTitulo) {
        this.muestraTitulo = muestraTitulo;
    }

    public Short getMuestraSubtitulo() {
        return muestraSubtitulo;
    }

    public void setMuestraSubtitulo(Short muestraSubtitulo) {
        this.muestraSubtitulo = muestraSubtitulo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getColumnas() {
        return columnas;
    }

    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

    public Short getSumaFila() {
        return sumaFila;
    }

    public void setSumaFila(Short sumaFila) {
        this.sumaFila = sumaFila;
    }

    public Short getSumaColumna() {
        return sumaColumna;
    }

    public void setSumaColumna(Short sumaColumna) {
        this.sumaColumna = sumaColumna;
    }

    public Short getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(Short sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    public Short getFilaOrden() {
        return filaOrden;
    }

    public void setFilaOrden(Short filaOrden) {
        this.filaOrden = filaOrden;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Short getAutocalculo() {
        return autocalculo;
    }

    public void setAutocalculo(Short autocalculo) {
        this.autocalculo = autocalculo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    public AdmInstitucion getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(AdmInstitucion codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    public AdmOrganigrama getCodOrganigrama() {
        return codOrganigrama;
    }

    public void setCodOrganigrama(AdmOrganigrama codOrganigrama) {
        this.codOrganigrama = codOrganigrama;
    }


    public MetSeccion getCodSeccionPadre() {
        return codSeccionPadre;
    }

    public void setCodSeccionPadre(MetSeccion codSeccionPadre) {
        this.codSeccionPadre = codSeccionPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeccion != null ? idSeccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetSeccion)) {
            return false;
        }
        MetSeccion other = (MetSeccion) object;
        if ((this.idSeccion == null && other.idSeccion != null) || (this.idSeccion != null && !this.idSeccion.equals(other.idSeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetSeccion[ idSeccion=" + idSeccion + " ]";
    }
    
}
