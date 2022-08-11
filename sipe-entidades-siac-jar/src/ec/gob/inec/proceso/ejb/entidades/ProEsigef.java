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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "proceso.pro_esigef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProEsigef.findAll", query = "SELECT p FROM ProEsigef p")})
public class ProEsigef implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_esigef")
    private Integer idEsigef;
    @Column(name = "fila")
    private Integer fila;
    @Size(max = 300)
    @Column(name = "descripcion1")
    private String descripcion1;
    @Size(max = 300)
    @Column(name = "descripcion2")
    private String descripcion2;
    @Size(max = 300)
    @Column(name = "descripcion3")
    private String descripcion3;
    @Size(max = 300)
    @Column(name = "descripcion4")
    private String descripcion4;
    @Size(max = 300)
    @Column(name = "descripcion5")
    private String descripcion5;
    @Size(max = 300)
    @Column(name = "descripcion6")
    private String descripcion6;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "inicial")
    private BigDecimal inicial;
    @Column(name = "modificado")
    private BigDecimal modificado;
    @Column(name = "codificado_vigente")
    private BigDecimal codificadoVigente;
    @Column(name = "certificado")
    private BigDecimal certificado;
    @Column(name = "comprometido")
    private BigDecimal comprometido;
    @Column(name = "devengado")
    private BigDecimal devengado;
    @Column(name = "pagado")
    private BigDecimal pagado;
    @Column(name = "por_comprometer")
    private BigDecimal porComprometer;
    @Column(name = "por_devengar")
    private BigDecimal porDevengar;
    @Column(name = "por_pagar")
    private BigDecimal porPagar;
    @Column(name = "porcentaje_de_ejecucion")
    private BigDecimal porcentajeDeEjecucion;
    @Size(max = 15)
    @Column(name = "zonal")
    private String zonal;
    @Size(max = 15)
    @Column(name = "codigo2")
    private String codigo2;
    @Size(max = 15)
    @Column(name = "codigo3")
    private String codigo3;
    @Size(max = 15)
    @Column(name = "estructura")
    private String estructura;
    @Size(max = 15)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 15)
    @Column(name = "partida")
    private String partida;
    @Size(max = 15)
    @Column(name = "fuente_de_financiamiento")
    private String fuenteDeFinanciamiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @JoinColumn(name = "cod_seguimiento_esigef", referencedColumnName = "id_seguimiento_e_d")
    @ManyToOne
    private ProSeguimientoEsigefDipla codSeguimientoEsigef;

    public ProEsigef() {
    }

    public ProEsigef(Integer idEsigef) {
        this.idEsigef = idEsigef;
    }

    public ProEsigef(Integer idEsigef, boolean estadoLogico) {
        this.idEsigef = idEsigef;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdEsigef() {
        return idEsigef;
    }

    public void setIdEsigef(Integer idEsigef) {
        this.idEsigef = idEsigef;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    public String getDescripcion4() {
        return descripcion4;
    }

    public void setDescripcion4(String descripcion4) {
        this.descripcion4 = descripcion4;
    }

    public String getDescripcion5() {
        return descripcion5;
    }

    public void setDescripcion5(String descripcion5) {
        this.descripcion5 = descripcion5;
    }

    public String getDescripcion6() {
        return descripcion6;
    }

    public void setDescripcion6(String descripcion6) {
        this.descripcion6 = descripcion6;
    }

    public BigDecimal getInicial() {
        return inicial;
    }

    public void setInicial(BigDecimal inicial) {
        this.inicial = inicial;
    }

    public BigDecimal getModificado() {
        return modificado;
    }

    public void setModificado(BigDecimal modificado) {
        this.modificado = modificado;
    }

    public BigDecimal getCodificadoVigente() {
        return codificadoVigente;
    }

    public void setCodificadoVigente(BigDecimal codificadoVigente) {
        this.codificadoVigente = codificadoVigente;
    }

    public BigDecimal getCertificado() {
        return certificado;
    }

    public void setCertificado(BigDecimal certificado) {
        this.certificado = certificado;
    }

    public BigDecimal getComprometido() {
        return comprometido;
    }

    public void setComprometido(BigDecimal comprometido) {
        this.comprometido = comprometido;
    }

    public BigDecimal getDevengado() {
        return devengado;
    }

    public void setDevengado(BigDecimal devengado) {
        this.devengado = devengado;
    }

    public BigDecimal getPagado() {
        return pagado;
    }

    public void setPagado(BigDecimal pagado) {
        this.pagado = pagado;
    }

    public BigDecimal getPorComprometer() {
        return porComprometer;
    }

    public void setPorComprometer(BigDecimal porComprometer) {
        this.porComprometer = porComprometer;
    }

    public BigDecimal getPorDevengar() {
        return porDevengar;
    }

    public void setPorDevengar(BigDecimal porDevengar) {
        this.porDevengar = porDevengar;
    }

    public BigDecimal getPorPagar() {
        return porPagar;
    }

    public void setPorPagar(BigDecimal porPagar) {
        this.porPagar = porPagar;
    }

    public BigDecimal getPorcentajeDeEjecucion() {
        return porcentajeDeEjecucion;
    }

    public void setPorcentajeDeEjecucion(BigDecimal porcentajeDeEjecucion) {
        this.porcentajeDeEjecucion = porcentajeDeEjecucion;
    }

    public String getZonal() {
        return zonal;
    }

    public void setZonal(String zonal) {
        this.zonal = zonal;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    public String getCodigo3() {
        return codigo3;
    }

    public void setCodigo3(String codigo3) {
        this.codigo3 = codigo3;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getFuenteDeFinanciamiento() {
        return fuenteDeFinanciamiento;
    }

    public void setFuenteDeFinanciamiento(String fuenteDeFinanciamiento) {
        this.fuenteDeFinanciamiento = fuenteDeFinanciamiento;
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

    public ProSeguimientoEsigefDipla getCodSeguimientoEsigef() {
        return codSeguimientoEsigef;
    }

    public void setCodSeguimientoEsigef(ProSeguimientoEsigefDipla codSeguimientoEsigef) {
        this.codSeguimientoEsigef = codSeguimientoEsigef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEsigef != null ? idEsigef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProEsigef)) {
            return false;
        }
        ProEsigef other = (ProEsigef) object;
        if ((this.idEsigef == null && other.idEsigef != null) || (this.idEsigef != null && !this.idEsigef.equals(other.idEsigef))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProEsigef[ idEsigef=" + idEsigef + " ]";
    }
    
}
