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
@Table(name = "proceso.pro_dipla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProDipla.findAll", query = "SELECT p FROM ProDipla p")})
public class ProDipla implements Serializable {

     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dipla")
    private Integer idDipla;
    @Column(name = "fila")
    private Integer fila;
    @Size(max = 30)
    @Column(name = "origen")
    private String origen;
    @Size(max = 30)
    @Column(name = "memorando")
    private String memorando;
    @Size(max = 30)
    @Column(name = "financiamiento")
    private String financiamiento;
    @Size(max = 30)
    @Column(name = "fuente_de_financiamiento")
    private String fuenteDeFinanciamiento;
    @Size(max = 30)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Size(max = 30)
    @Column(name = "division")
    private String division;
    @Size(max = 30)
    @Column(name = "estructura")
    private String estructura;
    @Size(max = 200)
    @Column(name = "lineamiento")
    private String lineamiento;
    @Size(max = 100)
    @Column(name = "programa")
    private String programa;
    @Size(max = 15)
    @Column(name = "partidas_programacion")
    private String partidasProgramacion;
    @Size(max = 300)
    @Column(name = "partidas")
    private String partidas;
    @Size(max = 15)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 60)
    @Column(name = "grupo_de_gasto")
    private String grupoDeGasto;
    @Size(max = 300)
    @Column(name = "justificativo")
    private String justificativo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "programa_total_vigente")
    private BigDecimal programaTotalVigente;
    @Column(name = "programa_total_inicial")
    private BigDecimal programaTotalInicial;
    @Column(name = "enero_p")
    private BigDecimal eneroP;
    @Column(name = "febrero_p")
    private BigDecimal febreroP;
    @Column(name = "marzo_p")
    private BigDecimal marzoP;
    @Column(name = "abril_p")
    private BigDecimal abrilP;
    @Column(name = "mayo_p")
    private BigDecimal mayoP;
    @Column(name = "junio_p")
    private BigDecimal junioP;
    @Column(name = "julio_p")
    private BigDecimal julioP;
    @Column(name = "agosto_p")
    private BigDecimal agostoP;
    @Column(name = "septiembre_p")
    private BigDecimal septiembreP;
    @Column(name = "octubre_p")
    private BigDecimal octubreP;
    @Column(name = "noviembre_p")
    private BigDecimal noviembreP;
    @Column(name = "diciembre_p")
    private BigDecimal diciembreP;
    @Column(name = "enero_i")
    private BigDecimal eneroI;
    @Column(name = "febrero_i")
    private BigDecimal febreroI;
    @Column(name = "marzo_i")
    private BigDecimal marzoI;
    @Column(name = "abril_i")
    private BigDecimal abrilI;
    @Column(name = "mayo_i")
    private BigDecimal mayoI;
    @Column(name = "junio_i")
    private BigDecimal junioI;
    @Column(name = "julio_i")
    private BigDecimal julioI;
    @Column(name = "agosto_i")
    private BigDecimal agostoI;
    @Column(name = "septiembre_i")
    private BigDecimal septiembreI;
    @Column(name = "octubre_i")
    private BigDecimal octubreI;
    @Column(name = "noviembre_i")
    private BigDecimal noviembreI;
    @Column(name = "diciembre_i")
    private BigDecimal diciembreI;
    @Column(name = "enero_m")
    private BigDecimal eneroM;
    @Column(name = "febrero_m")
    private BigDecimal febreroM;
    @Column(name = "marzo_m")
    private BigDecimal marzoM;
    @Column(name = "abril_m")
    private BigDecimal abrilM;
    @Column(name = "mayo_m")
    private BigDecimal mayoM;
    @Column(name = "junio_m")
    private BigDecimal junioM;
    @Column(name = "julio_m")
    private BigDecimal julioM;
    @Column(name = "agosto_m")
    private BigDecimal agostoM;
    @Column(name = "septiembre_m")
    private BigDecimal septiembreM;
    @Column(name = "octubre_m")
    private BigDecimal octubreM;
    @Column(name = "noviembre_m")
    private BigDecimal noviembreM;
    @Column(name = "diciembre_m")
    private BigDecimal diciembreM;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_seguimiento_dipla", referencedColumnName = "id_seguimiento_e_d")
    @ManyToOne
    private ProSeguimientoEsigefDipla codSeguimientoDipla;

    public ProDipla() {
    }

    public ProDipla(Integer idDipla) {
        this.idDipla = idDipla;
    }

    public ProDipla(Integer idDipla, boolean estadoLogico) {
        this.idDipla = idDipla;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdDipla() {
        return idDipla;
    }

    public void setIdDipla(Integer idDipla) {
        this.idDipla = idDipla;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMemorando() {
        return memorando;
    }

    public void setMemorando(String memorando) {
        this.memorando = memorando;
    }

    public String getFinanciamiento() {
        return financiamiento;
    }

    public void setFinanciamiento(String financiamiento) {
        this.financiamiento = financiamiento;
    }

    public String getFuenteDeFinanciamiento() {
        return fuenteDeFinanciamiento;
    }

    public void setFuenteDeFinanciamiento(String fuenteDeFinanciamiento) {
        this.fuenteDeFinanciamiento = fuenteDeFinanciamiento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getLineamiento() {
        return lineamiento;
    }

    public void setLineamiento(String lineamiento) {
        this.lineamiento = lineamiento;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getPartidasProgramacion() {
        return partidasProgramacion;
    }

    public void setPartidasProgramacion(String partidasProgramacion) {
        this.partidasProgramacion = partidasProgramacion;
    }

    public String getPartidas() {
        return partidas;
    }

    public void setPartidas(String partidas) {
        this.partidas = partidas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getGrupoDeGasto() {
        return grupoDeGasto;
    }

    public void setGrupoDeGasto(String grupoDeGasto) {
        this.grupoDeGasto = grupoDeGasto;
    }

    public String getJustificativo() {
        return justificativo;
    }

    public void setJustificativo(String justificativo) {
        this.justificativo = justificativo;
    }

    public BigDecimal getProgramaTotalVigente() {
        return programaTotalVigente;
    }

    public void setProgramaTotalVigente(BigDecimal programaTotalVigente) {
        this.programaTotalVigente = programaTotalVigente;
    }

    public BigDecimal getProgramaTotalInicial() {
        return programaTotalInicial;
    }

    public void setProgramaTotalInicial(BigDecimal programaTotalInicial) {
        this.programaTotalInicial = programaTotalInicial;
    }

    public BigDecimal getEneroP() {
        return eneroP;
    }

    public void setEneroP(BigDecimal eneroP) {
        this.eneroP = eneroP;
    }

    public BigDecimal getFebreroP() {
        return febreroP;
    }

    public void setFebreroP(BigDecimal febreroP) {
        this.febreroP = febreroP;
    }

    public BigDecimal getMarzoP() {
        return marzoP;
    }

    public void setMarzoP(BigDecimal marzoP) {
        this.marzoP = marzoP;
    }

    public BigDecimal getAbrilP() {
        return abrilP;
    }

    public void setAbrilP(BigDecimal abrilP) {
        this.abrilP = abrilP;
    }

    public BigDecimal getMayoP() {
        return mayoP;
    }

    public void setMayoP(BigDecimal mayoP) {
        this.mayoP = mayoP;
    }

    public BigDecimal getJunioP() {
        return junioP;
    }

    public void setJunioP(BigDecimal junioP) {
        this.junioP = junioP;
    }

    public BigDecimal getJulioP() {
        return julioP;
    }

    public void setJulioP(BigDecimal julioP) {
        this.julioP = julioP;
    }

    public BigDecimal getAgostoP() {
        return agostoP;
    }

    public void setAgostoP(BigDecimal agostoP) {
        this.agostoP = agostoP;
    }

    public BigDecimal getSeptiembreP() {
        return septiembreP;
    }

    public void setSeptiembreP(BigDecimal septiembreP) {
        this.septiembreP = septiembreP;
    }

    public BigDecimal getOctubreP() {
        return octubreP;
    }

    public void setOctubreP(BigDecimal octubreP) {
        this.octubreP = octubreP;
    }

    public BigDecimal getNoviembreP() {
        return noviembreP;
    }

    public void setNoviembreP(BigDecimal noviembreP) {
        this.noviembreP = noviembreP;
    }

    public BigDecimal getDiciembreP() {
        return diciembreP;
    }

    public void setDiciembreP(BigDecimal diciembreP) {
        this.diciembreP = diciembreP;
    }

    public BigDecimal getEneroI() {
        return eneroI;
    }

    public void setEneroI(BigDecimal eneroI) {
        this.eneroI = eneroI;
    }

    public BigDecimal getFebreroI() {
        return febreroI;
    }

    public void setFebreroI(BigDecimal febreroI) {
        this.febreroI = febreroI;
    }

    public BigDecimal getMarzoI() {
        return marzoI;
    }

    public void setMarzoI(BigDecimal marzoI) {
        this.marzoI = marzoI;
    }

    public BigDecimal getAbrilI() {
        return abrilI;
    }

    public void setAbrilI(BigDecimal abrilI) {
        this.abrilI = abrilI;
    }

    public BigDecimal getMayoI() {
        return mayoI;
    }

    public void setMayoI(BigDecimal mayoI) {
        this.mayoI = mayoI;
    }

    public BigDecimal getJunioI() {
        return junioI;
    }

    public void setJunioI(BigDecimal junioI) {
        this.junioI = junioI;
    }

    public BigDecimal getJulioI() {
        return julioI;
    }

    public void setJulioI(BigDecimal julioI) {
        this.julioI = julioI;
    }

    public BigDecimal getAgostoI() {
        return agostoI;
    }

    public void setAgostoI(BigDecimal agostoI) {
        this.agostoI = agostoI;
    }

    public BigDecimal getSeptiembreI() {
        return septiembreI;
    }

    public void setSeptiembreI(BigDecimal septiembreI) {
        this.septiembreI = septiembreI;
    }

    public BigDecimal getOctubreI() {
        return octubreI;
    }

    public void setOctubreI(BigDecimal octubreI) {
        this.octubreI = octubreI;
    }

    public BigDecimal getNoviembreI() {
        return noviembreI;
    }

    public void setNoviembreI(BigDecimal noviembreI) {
        this.noviembreI = noviembreI;
    }

    public BigDecimal getDiciembreI() {
        return diciembreI;
    }

    public void setDiciembreI(BigDecimal diciembreI) {
        this.diciembreI = diciembreI;
    }

    public BigDecimal getEneroM() {
        return eneroM;
    }

    public void setEneroM(BigDecimal eneroM) {
        this.eneroM = eneroM;
    }

    public BigDecimal getFebreroM() {
        return febreroM;
    }

    public void setFebreroM(BigDecimal febreroM) {
        this.febreroM = febreroM;
    }

    public BigDecimal getMarzoM() {
        return marzoM;
    }

    public void setMarzoM(BigDecimal marzoM) {
        this.marzoM = marzoM;
    }

    public BigDecimal getAbrilM() {
        return abrilM;
    }

    public void setAbrilM(BigDecimal abrilM) {
        this.abrilM = abrilM;
    }

    public BigDecimal getMayoM() {
        return mayoM;
    }

    public void setMayoM(BigDecimal mayoM) {
        this.mayoM = mayoM;
    }

    public BigDecimal getJunioM() {
        return junioM;
    }

    public void setJunioM(BigDecimal junioM) {
        this.junioM = junioM;
    }

    public BigDecimal getJulioM() {
        return julioM;
    }

    public void setJulioM(BigDecimal julioM) {
        this.julioM = julioM;
    }

    public BigDecimal getAgostoM() {
        return agostoM;
    }

    public void setAgostoM(BigDecimal agostoM) {
        this.agostoM = agostoM;
    }

    public BigDecimal getSeptiembreM() {
        return septiembreM;
    }

    public void setSeptiembreM(BigDecimal septiembreM) {
        this.septiembreM = septiembreM;
    }

    public BigDecimal getOctubreM() {
        return octubreM;
    }

    public void setOctubreM(BigDecimal octubreM) {
        this.octubreM = octubreM;
    }

    public BigDecimal getNoviembreM() {
        return noviembreM;
    }

    public void setNoviembreM(BigDecimal noviembreM) {
        this.noviembreM = noviembreM;
    }

    public BigDecimal getDiciembreM() {
        return diciembreM;
    }

    public void setDiciembreM(BigDecimal diciembreM) {
        this.diciembreM = diciembreM;
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

    public ProSeguimientoEsigefDipla getCodSeguimientoDipla() {
        return codSeguimientoDipla;
    }

    public void setCodSeguimientoDipla(ProSeguimientoEsigefDipla codSeguimientoDipla) {
        this.codSeguimientoDipla = codSeguimientoDipla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDipla != null ? idDipla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProDipla)) {
            return false;
        }
        ProDipla other = (ProDipla) object;
        if ((this.idDipla == null && other.idDipla != null) || (this.idDipla != null && !this.idDipla.equals(other.idDipla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProDipla[ idDipla=" + idDipla + " ]";
    }
    
}
