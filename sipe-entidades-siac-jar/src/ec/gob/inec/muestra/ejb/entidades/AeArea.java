/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "muestra.ae_area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AeArea.findAll", query = "SELECT a FROM AeArea a")})
public class AeArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_area")
    private Integer idArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "num_area")
    private String numArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo_area")
    private String tipoArea;
    @Size(max = 3)
    @Column(name = "manz_ini")
    private String manzIni;
    @Size(max = 3)
    @Column(name = "manz_fin")
    private String manzFin;
    @Size(max = 2147483647)
    @Column(name = "desde")
    private String desde;
    @Size(max = 2147483647)
    @Column(name = "hasta")
    private String hasta;
    @Column(name = "num_viv_ocup")
    private Integer numVivOcup;
    @Column(name = "num_viv_total")
    private Integer numVivTotal;
    @Size(max = 2)
    @Column(name = "estado1")
    private String estado1;
    @Size(max = 2)
    @Column(name = "estado2")
    private String estado2;
    @Size(max = 50)
    @Column(name = "manz_sin_viv")
    private String manzSinViv;
    @Size(max = 3)
    @Column(name = "edif_desde")
    private String edifDesde;
    @Size(max = 3)
    @Column(name = "edif_hasta")
    private String edifHasta;
    @Column(name = "peso_ponderado")
    private BigDecimal pesoPonderado;
    @JoinColumn(name = "cod_generacion", referencedColumnName = "id_generacion")
    @ManyToOne(optional = false)
    private AeGeneracion codGeneracion;

    public AeArea() {
    }

    public AeArea(Integer idArea) {
        this.idArea = idArea;
    }

    public AeArea(Integer idArea, String numArea, String tipoArea) {
        this.idArea = idArea;
        this.numArea = numArea;
        this.tipoArea = tipoArea;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNumArea() {
        return numArea;
    }

    public void setNumArea(String numArea) {
        this.numArea = numArea;
    }

    public String getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(String tipoArea) {
        this.tipoArea = tipoArea;
    }

    public String getManzIni() {
        return manzIni;
    }

    public void setManzIni(String manzIni) {
        this.manzIni = manzIni;
    }

    public String getManzFin() {
        return manzFin;
    }

    public void setManzFin(String manzFin) {
        this.manzFin = manzFin;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public Integer getNumVivOcup() {
        return numVivOcup;
    }

    public void setNumVivOcup(Integer numVivOcup) {
        this.numVivOcup = numVivOcup;
    }

    public Integer getNumVivTotal() {
        return numVivTotal;
    }

    public void setNumVivTotal(Integer numVivTotal) {
        this.numVivTotal = numVivTotal;
    }

    public String getEstado1() {
        return estado1;
    }

    public void setEstado1(String estado1) {
        this.estado1 = estado1;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

    public String getManzSinViv() {
        return manzSinViv;
    }

    public void setManzSinViv(String manzSinViv) {
        this.manzSinViv = manzSinViv;
    }

    public String getEdifDesde() {
        return edifDesde;
    }

    public void setEdifDesde(String edifDesde) {
        this.edifDesde = edifDesde;
    }

    public String getEdifHasta() {
        return edifHasta;
    }

    public void setEdifHasta(String edifHasta) {
        this.edifHasta = edifHasta;
    }

    public BigDecimal getPesoPonderado() {
        return pesoPonderado;
    }

    public void setPesoPonderado(BigDecimal pesoPonderado) {
        this.pesoPonderado = pesoPonderado;
    }

 

    public AeGeneracion getCodGeneracion() {
        return codGeneracion;
    }

    public void setCodGeneracion(AeGeneracion codGeneracion) {
        this.codGeneracion = codGeneracion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AeArea)) {
            return false;
        }
        AeArea other = (AeArea) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.AeArea[ idArea=" + idArea + " ]";
    }

}
