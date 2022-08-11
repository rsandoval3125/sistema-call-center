/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

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
@Table(name = "proceso.pro_valor_numerico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProValorNumerico.findAll", query = "SELECT p FROM ProValorNumerico p")})
public class ProValorNumerico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_valor_numerico")
    private Integer idValorNumerico;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "total")
    private Boolean total;
    @Column(name = "sumatoria")
    private Boolean sumatoria;
    @Column(name = "validado")
    private Boolean validado;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "numero_columna")
    private Integer numeroColumna;
    @JoinColumn(name = "cod_numero_ejercicio", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codNumeroEjercicio;
    @JoinColumn(name = "cod_base_con_inicial", referencedColumnName = "id_base_con_inicial")
    @ManyToOne
    private ProBaseConsolidadaInicial codBaseConInicial;
    @JoinColumn(name = "cod_cedula_dipla", referencedColumnName = "id_cedula_dipla")
    @ManyToOne
    private ProCedulaDipla codCedulaDipla;
    @JoinColumn(name = "cod_nombre_columna", referencedColumnName = "id_nombre_columna")
    @ManyToOne
    private ProNombreColumna codNombreColumna;

    public ProValorNumerico() {
    }

    public ProValorNumerico(Integer idValorNumerico) {
        this.idValorNumerico = idValorNumerico;
    }

    public ProValorNumerico(Integer idValorNumerico, boolean estadoLogico) {
        this.idValorNumerico = idValorNumerico;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdValorNumerico() {
        return idValorNumerico;
    }

    public void setIdValorNumerico(Integer idValorNumerico) {
        this.idValorNumerico = idValorNumerico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getTotal() {
        return total;
    }

    public void setTotal(Boolean total) {
        this.total = total;
    }

    public Boolean getSumatoria() {
        return sumatoria;
    }

    public void setSumatoria(Boolean sumatoria) {
        this.sumatoria = sumatoria;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    public Integer getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(Integer numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public MetCatalogo getCodNumeroEjercicio() {
        return codNumeroEjercicio;
    }

    public void setCodNumeroEjercicio(MetCatalogo codNumeroEjercicio) {
        this.codNumeroEjercicio = codNumeroEjercicio;
    }

    public ProBaseConsolidadaInicial getCodBaseConInicial() {
        return codBaseConInicial;
    }

    public void setCodBaseConInicial(ProBaseConsolidadaInicial codBaseConInicial) {
        this.codBaseConInicial = codBaseConInicial;
    }

    public ProCedulaDipla getCodCedulaDipla() {
        return codCedulaDipla;
    }

    public void setCodCedulaDipla(ProCedulaDipla codCedulaDipla) {
        this.codCedulaDipla = codCedulaDipla;
    }

    public ProNombreColumna getCodNombreColumna() {
        return codNombreColumna;
    }

    public void setCodNombreColumna(ProNombreColumna codNombreColumna) {
        this.codNombreColumna = codNombreColumna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValorNumerico != null ? idValorNumerico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProValorNumerico)) {
            return false;
        }
        ProValorNumerico other = (ProValorNumerico) object;
        if ((this.idValorNumerico == null && other.idValorNumerico != null) || (this.idValorNumerico != null && !this.idValorNumerico.equals(other.idValorNumerico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProValorNumerico[ idValorNumerico=" + idValorNumerico + " ]";
    }
    
}
