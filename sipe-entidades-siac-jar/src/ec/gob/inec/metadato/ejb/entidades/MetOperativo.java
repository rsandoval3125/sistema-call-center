/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
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
@Table(name = "metadato.met_operativo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetOperativo.findAll", query = "SELECT m FROM MetOperativo m")})
public class MetOperativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_operativo")
    private Integer idOperativo;
    @Column(name = "cod_ambito")
    private Integer codAmbito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "antecedentes")
    private String antecedentes;
    @Size(max = 2147483647)
    @Column(name = "resumen")
    private String resumen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "identificador")
    private String identificador;
    @Size(max = 2147483647)
    @Column(name = "tipo_dato")
    private String tipoDato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "unidad_analisis")
    private String unidadAnalisis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "alcance_tematico")
    private String alcanceTematico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "universo_estudio")
    private String universoEstudio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "productor")
    private String productor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_investigacion")
    @Temporal(TemporalType.DATE)
    private Date fechaInvestigacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @Column(name = "cod_responsable")
    private Integer codResponsable;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "tiene_modelo_validado")
    private Boolean tieneModeloValidado;
    @JoinColumn(name = "cod_ope", referencedColumnName = "id_ope_est")
    @ManyToOne
    private AdmOperacionEstadistica codOpe;
    @JoinColumn(name = "cod_per", referencedColumnName = "id_periodo")
    @ManyToOne
    private AdmPeriodo codPer;

    public MetOperativo() {
    }

    public MetOperativo(Integer idOperativo) {
        this.idOperativo = idOperativo;
    }

    public MetOperativo(Integer idOperativo, String antecedentes, String identificador, String unidadAnalisis, String alcanceTematico, String universoEstudio, String productor, Date fechaInvestigacion, Date fechaPublicacion, boolean estadoLogico) {
        this.idOperativo = idOperativo;
        this.antecedentes = antecedentes;
        this.identificador = identificador;
        this.unidadAnalisis = unidadAnalisis;
        this.alcanceTematico = alcanceTematico;
        this.universoEstudio = universoEstudio;
        this.productor = productor;
        this.fechaInvestigacion = fechaInvestigacion;
        this.fechaPublicacion = fechaPublicacion;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdOperativo() {
        return idOperativo;
    }

    public void setIdOperativo(Integer idOperativo) {
        this.idOperativo = idOperativo;
    }

    public Integer getCodAmbito() {
        return codAmbito;
    }

    public void setCodAmbito(Integer codAmbito) {
        this.codAmbito = codAmbito;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getUnidadAnalisis() {
        return unidadAnalisis;
    }

    public void setUnidadAnalisis(String unidadAnalisis) {
        this.unidadAnalisis = unidadAnalisis;
    }

    public String getAlcanceTematico() {
        return alcanceTematico;
    }

    public void setAlcanceTematico(String alcanceTematico) {
        this.alcanceTematico = alcanceTematico;
    }

    public String getUniversoEstudio() {
        return universoEstudio;
    }

    public void setUniversoEstudio(String universoEstudio) {
        this.universoEstudio = universoEstudio;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public Date getFechaInvestigacion() {
        return fechaInvestigacion;
    }

    public void setFechaInvestigacion(Date fechaInvestigacion) {
        this.fechaInvestigacion = fechaInvestigacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getCodResponsable() {
        return codResponsable;
    }

    public void setCodResponsable(Integer codResponsable) {
        this.codResponsable = codResponsable;
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

    public Boolean getTieneModeloValidado() {
        return tieneModeloValidado;
    }

    public void setTieneModeloValidado(Boolean tieneModeloValidado) {
        this.tieneModeloValidado = tieneModeloValidado;
    }

    public AdmOperacionEstadistica getCodOpe() {
        return codOpe;
    }

    public void setCodOpe(AdmOperacionEstadistica codOpe) {
        this.codOpe = codOpe;
    }

    public AdmPeriodo getCodPer() {
        return codPer;
    }

    public void setCodPer(AdmPeriodo codPer) {
        this.codPer = codPer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperativo != null ? idOperativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetOperativo)) {
            return false;
        }
        MetOperativo other = (MetOperativo) object;
        if ((this.idOperativo == null && other.idOperativo != null) || (this.idOperativo != null && !this.idOperativo.equals(other.idOperativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetOperativo[ idOperativo=" + idOperativo + " ]";
    }
    
}
