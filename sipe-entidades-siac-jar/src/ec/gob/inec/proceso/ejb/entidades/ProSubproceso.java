/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "proceso.pro_subproceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProSubproceso.findAll", query = "SELECT p FROM ProSubproceso p")})
public class ProSubproceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subpro")
    private Integer idSubpro;
    @Size(max = 10)
    @Column(name = "numeracion")
    private String numeracion;
    @Size(max = 70)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "orden")
    private Integer orden;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso")
    private BigDecimal peso;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 50)
    @Column(name = "alias")
    private String alias;
    @JoinColumn(name = "cod_fase", referencedColumnName = "id_fase")
    @ManyToOne
    private ProFase codFase;
    @Transient
    private Double avance;


    public ProSubproceso() {
    }

    public ProSubproceso(Integer idSubpro) {
        this.idSubpro = idSubpro;
    }

    public ProSubproceso(Integer idSubpro, boolean estadoLogico) {
        this.idSubpro = idSubpro;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdSubpro() {
        return idSubpro;
    }

    public void setIdSubpro(Integer idSubpro) {
        this.idSubpro = idSubpro;
    }

    public String getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(String numeracion) {
        this.numeracion = numeracion;
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
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

    public ProFase getCodFase() {
        return codFase;
    }

    public void setCodFase(ProFase codFase) {
        this.codFase = codFase;
    }

    public Double getAvance() {
        return avance;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubpro != null ? idSubpro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProSubproceso)) {
            return false;
        }
        ProSubproceso other = (ProSubproceso) object;
        if ((this.idSubpro == null && other.idSubpro != null) || (this.idSubpro != null && !this.idSubpro.equals(other.idSubpro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProSubproceso[ idSubpro=" + idSubpro + " ]";
    }
    
}
