/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
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
@Table(name = "proceso.pro_actividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProActividad.findAll", query = "SELECT p FROM ProActividad p")})
public class ProActividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actividad")
    private Integer idActividad;
    @Size(max = 10)
    @Column(name = "numeracion")
    private String numeracion;
    @Size(max = 200)
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
    @Size(max = 2)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "verificable_obligatorio")
    private Boolean verificableObligatorio;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "uso_distribucion_trab")
    private Boolean usoDistribucionTrab;
    @Size(max = 50)
    @Column(name = "alias")
    private String alias;
    @JoinColumn(name = "tipo_accion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo tipoAccion;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_actividad")
    @ManyToOne
    private ProActividad codPadre;
    @JoinColumn(name = "cod_subpro", referencedColumnName = "id_subpro")
    @ManyToOne
    private ProSubproceso codSubpro;
    @JoinColumn(name = "cod_apl", referencedColumnName = "id_apl")
    @ManyToOne
    private SegAplicacion codApl;

    public ProActividad() {
    }

    public ProActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public ProActividad(Integer idActividad, boolean estadoLogico) {
        this.idActividad = idActividad;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getVerificableObligatorio() {
        return verificableObligatorio;
    }

    public void setVerificableObligatorio(Boolean verificableObligatorio) {
        this.verificableObligatorio = verificableObligatorio;
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

    public Boolean getUsoDistribucionTrab() {
        return usoDistribucionTrab;
    }

    public void setUsoDistribucionTrab(Boolean usoDistribucionTrab) {
        this.usoDistribucionTrab = usoDistribucionTrab;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public MetCatalogo getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(MetCatalogo tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public ProActividad getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(ProActividad codPadre) {
        this.codPadre = codPadre;
    }

    public ProSubproceso getCodSubpro() {
        return codSubpro;
    }

    public void setCodSubpro(ProSubproceso codSubpro) {
        this.codSubpro = codSubpro;
    }

    public SegAplicacion getCodApl() {
        return codApl;
    }

    public void setCodApl(SegAplicacion codApl) {
        this.codApl = codApl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProActividad)) {
            return false;
        }
        ProActividad other = (ProActividad) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProActividad[ idActividad=" + idActividad + " ]";
    }
    
}
