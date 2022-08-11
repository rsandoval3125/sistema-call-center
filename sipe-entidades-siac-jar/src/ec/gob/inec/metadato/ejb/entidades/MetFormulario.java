/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

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
@Table(name = "metadato.met_formulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetFormulario.findAll", query = "SELECT m FROM MetFormulario m")})
public class MetFormulario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_formulario")
    private Integer idFormulario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "codificacion")
    private String codificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "estilo")
    private String estilo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Column(name = "muestra")
    private Short muestra;
    @Size(max = 2147483647)
    @Column(name = "requerimiento")
    private String requerimiento;
    @Column(name = "nuevo_registros")
    private Short nuevoRegistros;
    @Column(name = "eliminar_registros")
    private Short eliminarRegistros;
    @Column(name = "reemplazar_registros")
    private Short reemplazarRegistros;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "movil")
    private Short movil;
    @Column(name = "web")
    private Short web;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 3)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 32)
    @Column(name = "coberturas")
    private String coberturas;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_formulario")
    @ManyToOne
    private MetFormulario codPadre;
    @JoinColumn(name = "cod_operativo", referencedColumnName = "id_operativo")
    @ManyToOne
    private MetOperativo codOperativo;

    public MetFormulario() {
    }

    public MetFormulario(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public MetFormulario(Integer idFormulario, String nombre, String codificacion, String estilo, Date fechaHora, boolean estadoLogico) {
        this.idFormulario = idFormulario;
        this.nombre = nombre;
        this.codificacion = codificacion;
        this.estilo = estilo;
        this.fechaHora = fechaHora;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodificacion() {
        return codificacion;
    }

    public void setCodificacion(String codificacion) {
        this.codificacion = codificacion;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Short getMuestra() {
        return muestra;
    }

    public void setMuestra(Short muestra) {
        this.muestra = muestra;
    }

    public String getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(String requerimiento) {
        this.requerimiento = requerimiento;
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

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Short getMovil() {
        return movil;
    }

    public void setMovil(Short movil) {
        this.movil = movil;
    }

    public Short getWeb() {
        return web;
    }

    public void setWeb(Short web) {
        this.web = web;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCoberturas() {
        return coberturas;
    }

    public void setCoberturas(String coberturas) {
        this.coberturas = coberturas;
    }

    public MetFormulario getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(MetFormulario codPadre) {
        this.codPadre = codPadre;
    }

    public MetOperativo getCodOperativo() {
        return codOperativo;
    }

    public void setCodOperativo(MetOperativo codOperativo) {
        this.codOperativo = codOperativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormulario != null ? idFormulario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetFormulario)) {
            return false;
        }
        MetFormulario other = (MetFormulario) object;
        if ((this.idFormulario == null && other.idFormulario != null) || (this.idFormulario != null && !this.idFormulario.equals(other.idFormulario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetFormulario[ idFormulario=" + idFormulario + " ]";
    }
    
}
