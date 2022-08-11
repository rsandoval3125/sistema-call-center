/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "proceso.pro_indicador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProIndicador.findAll", query = "SELECT p FROM ProIndicador p")})
public class ProIndicador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_indicador")
    private Integer idIndicador;
    @Size(max = 3)
    @Column(name = "tipo_indicador")
    private String tipoIndicador;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "periodo")
    private Integer periodo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "umbral")
    private BigDecimal umbral;
    @Size(max = 3)
    @Column(name = "optimo")
    private String optimo;
    @Column(name = "fecha_ejecucion")
    @Temporal(TemporalType.DATE)
    private Date fechaEjecucion;
    @Column(name = "fecha_programada")
    @Temporal(TemporalType.DATE)
    private Date fechaProgramada;
    @Size(max = 3)
    @Column(name = "resultado")
    private String resultado;
    @Size(max = 500)
    @Column(name = "motivo_alerta")
    private String motivoAlerta;
    @Size(max = 500)
    @Column(name = "accion_emprendida")
    private String accionEmprendida;
    @Size(max = 500)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "numerador")
    private Integer numerador;
    @Column(name = "denominador")
    private Integer denominador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_nombre_indicador", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codNombreIndicador;
    @JoinColumn(name = "cod_responsable", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codResponsable;
    @JoinColumn(name = "cod_fase", referencedColumnName = "id_fase")
    @ManyToOne
    private ProFase codFase;

    public ProIndicador() {
    }

    public ProIndicador(Integer idIndicador) {
        this.idIndicador = idIndicador;
    }

    public ProIndicador(Integer idIndicador, boolean estadoLogico) {
        this.idIndicador = idIndicador;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(Integer idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getTipoIndicador() {
        return tipoIndicador;
    }

    public void setTipoIndicador(String tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getUmbral() {
        return umbral;
    }

    public void setUmbral(BigDecimal umbral) {
        this.umbral = umbral;
    }

    public String getOptimo() {
        return optimo;
    }

    public void setOptimo(String optimo) {
        this.optimo = optimo;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMotivoAlerta() {
        return motivoAlerta;
    }

    public void setMotivoAlerta(String motivoAlerta) {
        this.motivoAlerta = motivoAlerta;
    }

    public String getAccionEmprendida() {
        return accionEmprendida;
    }

    public void setAccionEmprendida(String accionEmprendida) {
        this.accionEmprendida = accionEmprendida;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getNumerador() {
        return numerador;
    }

    public void setNumerador(Integer numerador) {
        this.numerador = numerador;
    }

    public Integer getDenominador() {
        return denominador;
    }

    public void setDenominador(Integer denominador) {
        this.denominador = denominador;
    }

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public MetCatalogo getCodNombreIndicador() {
        return codNombreIndicador;
    }

    public void setCodNombreIndicador(MetCatalogo codNombreIndicador) {
        this.codNombreIndicador = codNombreIndicador;
    }

    public MetCatalogo getCodResponsable() {
        return codResponsable;
    }

    public void setCodResponsable(MetCatalogo codResponsable) {
        this.codResponsable = codResponsable;
    }

    public ProFase getCodFase() {
        return codFase;
    }

    public void setCodFase(ProFase codFase) {
        this.codFase = codFase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIndicador != null ? idIndicador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProIndicador)) {
            return false;
        }
        ProIndicador other = (ProIndicador) object;
        if ((this.idIndicador == null && other.idIndicador != null) || (this.idIndicador != null && !this.idIndicador.equals(other.idIndicador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProIndicador[ idIndicador=" + idIndicador + " ]";
    }
    
}
