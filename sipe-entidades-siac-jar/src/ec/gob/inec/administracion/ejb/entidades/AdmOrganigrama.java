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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_organigrama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmOrganigrama.findAll", query = "SELECT a FROM AdmOrganigrama a")})
public class AdmOrganigrama implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_organigrama")
    private Integer idOrganigrama;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "alias")
    private String alias;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "mision")
    private String mision;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "vision")
    private String vision;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "objetivo")
    private String objetivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "producto")
    private String producto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_institucion", referencedColumnName = "id_institucion")
    @ManyToOne(optional = false)
    private AdmInstitucion codInstitucion;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_organigrama")
    @ManyToOne
    private AdmOrganigrama codPadre;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codTipo;
    @JoinColumn(name = "cod_dpa", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codDpa;

    public AdmOrganigrama() {
    }

    public AdmOrganigrama(Integer idOrganigrama) {
        this.idOrganigrama = idOrganigrama;
    }

    public AdmOrganigrama(Integer idOrganigrama, String nombre, String alias, String mision, String vision, String objetivo, String producto, Date fechaInicio, String telefono, int nivel, boolean estadoLogico) {
        this.idOrganigrama = idOrganigrama;
        this.nombre = nombre;
        this.alias = alias;
        this.mision = mision;
        this.vision = vision;
        this.objetivo = objetivo;
        this.producto = producto;
        this.fechaInicio = fechaInicio;
        this.telefono = telefono;
        this.nivel = nivel;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdOrganigrama() {
        return idOrganigrama;
    }

    public void setIdOrganigrama(Integer idOrganigrama) {
        this.idOrganigrama = idOrganigrama;
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

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public AdmInstitucion getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(AdmInstitucion codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    public AdmOrganigrama getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(AdmOrganigrama codPadre) {
        this.codPadre = codPadre;
    }

    public MetCatalogo getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(MetCatalogo codTipo) {
        this.codTipo = codTipo;
    }

    public MetCatalogo getCodDpa() {
        return codDpa;
    }

    public void setCodDpa(MetCatalogo codDpa) {
        this.codDpa = codDpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrganigrama != null ? idOrganigrama.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmOrganigrama)) {
            return false;
        }
        AdmOrganigrama other = (AdmOrganigrama) object;
        if ((this.idOrganigrama == null && other.idOrganigrama != null) || (this.idOrganigrama != null && !this.idOrganigrama.equals(other.idOrganigrama))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama[ idOrganigrama=" + idOrganigrama + " ]";
    }
    
}
