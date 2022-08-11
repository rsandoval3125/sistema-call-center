/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
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
@Table(name = "proceso.pro_seguimiento_esigef_dipla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProSeguimientoEsigefDipla.findAll", query = "SELECT p FROM ProSeguimientoEsigefDipla p")})
public class ProSeguimientoEsigefDipla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seguimiento_e_d")
    private Integer idSeguimientoED;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_carga_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaCargaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin_carga")
    @Temporal(TemporalType.DATE)
    private Date fechaFinCarga;
    @Column(name = "fecha_guardada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGuardada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "validado1")
    private boolean validado1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "validado2")
    private boolean validado2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "validado3")
    private boolean validado3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "mes_seleccionado")
    private Boolean mesSeleccionado;
    @Size(max = 1)
    @Column(name = "tipo_archivo")
    private String tipoArchivo;
    @JoinColumn(name = "cod_anio", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codAnio;
    @JoinColumn(name = "cod_usuario_responsable", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuarioResponsable;
    @JoinColumn(name = "cod_usuario_validado1", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuarioValidado1;
    @JoinColumn(name = "cod_usuario_validado2", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuarioValidado2;
    @JoinColumn(name = "cod_usuario_validado3", referencedColumnName = "id_usuario")
    @ManyToOne
    private SegUsuario codUsuarioValidado3;

    public ProSeguimientoEsigefDipla() {
    }

    public ProSeguimientoEsigefDipla(Integer idSeguimientoED) {
        this.idSeguimientoED = idSeguimientoED;
    }

    public ProSeguimientoEsigefDipla(Integer idSeguimientoED, Date fechaCargaInicio, Date fechaFinCarga, boolean validado1, boolean validado2, boolean validado3, boolean estadoLogico) {
        this.idSeguimientoED = idSeguimientoED;
        this.fechaCargaInicio = fechaCargaInicio;
        this.fechaFinCarga = fechaFinCarga;
        this.validado1 = validado1;
        this.validado2 = validado2;
        this.validado3 = validado3;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdSeguimientoED() {
        return idSeguimientoED;
    }

    public void setIdSeguimientoED(Integer idSeguimientoED) {
        this.idSeguimientoED = idSeguimientoED;
    }

    public Date getFechaCargaInicio() {
        return fechaCargaInicio;
    }

    public void setFechaCargaInicio(Date fechaCargaInicio) {
        this.fechaCargaInicio = fechaCargaInicio;
    }

    public Date getFechaFinCarga() {
        return fechaFinCarga;
    }

    public void setFechaFinCarga(Date fechaFinCarga) {
        this.fechaFinCarga = fechaFinCarga;
    }

    public Date getFechaGuardada() {
        return fechaGuardada;
    }

    public void setFechaGuardada(Date fechaGuardada) {
        this.fechaGuardada = fechaGuardada;
    }

    public boolean getValidado1() {
        return validado1;
    }

    public void setValidado1(boolean validado1) {
        this.validado1 = validado1;
    }

    public boolean getValidado2() {
        return validado2;
    }

    public void setValidado2(boolean validado2) {
        this.validado2 = validado2;
    }

    public boolean getValidado3() {
        return validado3;
    }

    public void setValidado3(boolean validado3) {
        this.validado3 = validado3;
    }

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(Boolean mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public MetCatalogo getCodAnio() {
        return codAnio;
    }

    public void setCodAnio(MetCatalogo codAnio) {
        this.codAnio = codAnio;
    }

    public SegUsuario getCodUsuarioResponsable() {
        return codUsuarioResponsable;
    }

    public void setCodUsuarioResponsable(SegUsuario codUsuarioResponsable) {
        this.codUsuarioResponsable = codUsuarioResponsable;
    }

    public SegUsuario getCodUsuarioValidado1() {
        return codUsuarioValidado1;
    }

    public void setCodUsuarioValidado1(SegUsuario codUsuarioValidado1) {
        this.codUsuarioValidado1 = codUsuarioValidado1;
    }

    public SegUsuario getCodUsuarioValidado2() {
        return codUsuarioValidado2;
    }

    public void setCodUsuarioValidado2(SegUsuario codUsuarioValidado2) {
        this.codUsuarioValidado2 = codUsuarioValidado2;
    }

    public SegUsuario getCodUsuarioValidado3() {
        return codUsuarioValidado3;
    }

    public void setCodUsuarioValidado3(SegUsuario codUsuarioValidado3) {
        this.codUsuarioValidado3 = codUsuarioValidado3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguimientoED != null ? idSeguimientoED.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProSeguimientoEsigefDipla)) {
            return false;
        }
        ProSeguimientoEsigefDipla other = (ProSeguimientoEsigefDipla) object;
        if ((this.idSeguimientoED == null && other.idSeguimientoED != null) || (this.idSeguimientoED != null && !this.idSeguimientoED.equals(other.idSeguimientoED))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProSeguimientoEsigefDipla[ idSeguimientoED=" + idSeguimientoED + " ]";
    }
    
}
