/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
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
@Table(name = "metadato.met_concepto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetConcepto.findAll", query = "SELECT m FROM MetConcepto m")})
public class MetConcepto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_concepto")
    private Integer idConcepto;
    @Basic(optional = false)
    @Column(name = "identificador")
    private String identificador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "longitud_minima")
    private Integer longitudMinima;
    @Column(name = "longitud_maxima")
    private Integer longitudMaxima;
    @Column(name = "rango_minimo")
    private String rangoMinimo;
    @Column(name = "rango_maximo")
    private String rangoMaximo;
    @Basic(optional = false)
    @Column(name = "categorica")
    private boolean categorica;
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_institucion", referencedColumnName = "id_institucion")
    @ManyToOne(optional = false)
    private AdmInstitucion codInstitucion;
    @JoinColumn(name = "cod_cat_dato", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codCatDato;
    @JoinColumn(name = "cod_cat_clasificacion", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codCatClasificacion;
    @JoinColumn(name = "cod_cat_investigacion", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codCatInvestigacion;
    @JoinColumn(name = "cod_cat_seguridad", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codCatSeguridad;
    @JoinColumn(name = "cod_cat_concepto", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codCatConcepto;
    @JoinColumn(name = "cod_tipo_catalogo", referencedColumnName = "id_tipo_catalogo")
    @ManyToOne
    private MetTipoCatalogo codTipoCatalogo;

    public MetConcepto() {
    }

    public MetConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public MetConcepto(Integer idConcepto, String identificador, String nombre, String descripcion, boolean categorica, Date fechaCreacion) {
        this.idConcepto = idConcepto;
        this.identificador = identificador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categorica = categorica;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public String getRangoMinimo() {
        return rangoMinimo;
    }

    public void setRangoMinimo(String rangoMinimo) {
        this.rangoMinimo = rangoMinimo;
    }

    public String getRangoMaximo() {
        return rangoMaximo;
    }

    public void setRangoMaximo(String rangoMaximo) {
        this.rangoMaximo = rangoMaximo;
    }

    public boolean getCategorica() {
        return categorica;
    }

    public void setCategorica(boolean categorica) {
        this.categorica = categorica;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
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

    public AdmInstitucion getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(AdmInstitucion codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    public MetCatalogo getCodCatDato() {
        return codCatDato;
    }

    public void setCodCatDato(MetCatalogo codCatDato) {
        this.codCatDato = codCatDato;
    }

    public MetCatalogo getCodCatClasificacion() {
        return codCatClasificacion;
    }

    public void setCodCatClasificacion(MetCatalogo codCatClasificacion) {
        this.codCatClasificacion = codCatClasificacion;
    }

    public MetCatalogo getCodCatInvestigacion() {
        return codCatInvestigacion;
    }

    public void setCodCatInvestigacion(MetCatalogo codCatInvestigacion) {
        this.codCatInvestigacion = codCatInvestigacion;
    }

    public MetCatalogo getCodCatSeguridad() {
        return codCatSeguridad;
    }

    public void setCodCatSeguridad(MetCatalogo codCatSeguridad) {
        this.codCatSeguridad = codCatSeguridad;
    }

    public MetCatalogo getCodCatConcepto() {
        return codCatConcepto;
    }

    public void setCodCatConcepto(MetCatalogo codCatConcepto) {
        this.codCatConcepto = codCatConcepto;
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
        hash += (idConcepto != null ? idConcepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetConcepto)) {
            return false;
        }
        MetConcepto other = (MetConcepto) object;
        if ((this.idConcepto == null && other.idConcepto != null) || (this.idConcepto != null && !this.idConcepto.equals(other.idConcepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetConcepto[ idConcepto=" + idConcepto + " ]";
    }
    
}
