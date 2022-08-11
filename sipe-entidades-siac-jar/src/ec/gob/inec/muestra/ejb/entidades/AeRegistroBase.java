/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

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
@Table(name = "muestra.ae_registro_base")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AeRegistroBase.findAll", query = "SELECT a FROM AeRegistroBase a")})
public class AeRegistroBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_base")
    private Integer idRegistroBase;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "num_manz")
    private String numManz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_det")
    private int numDet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @Size(max = 3)
    @Column(name = "num_edif")
    private String numEdif;
    @Size(max = 5)
    @Column(name = "num_piso")
    private String numPiso;
    @Size(max = 3)
    @Column(name = "num_vivi")
    private String numVivi;
    @Size(max = 1)
    @Column(name = "con_vivi")
    private String conVivi;
    @Size(max = 2)
    @Column(name = "cod_vcol")
    private String codVcol;
    @Size(max = 1)
    @Column(name = "tip_otro")
    private String tipOtro;
    @Size(max = 2)
    @Column(name = "cod_otro")
    private String codOtro;
    @Size(max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 2147483647)
    @Column(name = "jefe_hogar")
    private String jefeHogar;
    @Column(name = "piso_ini")
    private Integer pisoIni;
    @Column(name = "piso_fin")
    private Integer pisoFin;
    @Size(max = 20)
    @Column(name = "geocodigo")
    private String geocodigo;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 2147483647)
    @Column(name = "desc_reg_sin_vivienda")
    private String descRegSinVivienda;
    @Size(max = 3)
    @Column(name = "num_habitantes")
    private String numHabitantes;
    @Column(name = "val_ponderado")
    private BigDecimal valPonderado;
    @JoinColumn(name = "cod_area", referencedColumnName = "id_area")
    @ManyToOne
    private AeArea codArea;
    @JoinColumn(name = "cod_generacion", referencedColumnName = "id_generacion")
    @ManyToOne(optional = false)
    private AeGeneracion codGeneracion;

    public AeRegistroBase() {
    }

    public AeRegistroBase(Integer idRegistroBase) {
        this.idRegistroBase = idRegistroBase;
    }

    public AeRegistroBase(Integer idRegistroBase, String numManz, int numDet, int orden) {
        this.idRegistroBase = idRegistroBase;
        this.numManz = numManz;
        this.numDet = numDet;
        this.orden = orden;
    }

    public Integer getIdRegistroBase() {
        return idRegistroBase;
    }

    public void setIdRegistroBase(Integer idRegistroBase) {
        this.idRegistroBase = idRegistroBase;
    }

    public String getNumManz() {
        return numManz;
    }

    public void setNumManz(String numManz) {
        this.numManz = numManz;
    }

    public int getNumDet() {
        return numDet;
    }

    public void setNumDet(int numDet) {
        this.numDet = numDet;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getNumEdif() {
        return numEdif;
    }

    public void setNumEdif(String numEdif) {
        this.numEdif = numEdif;
    }

    public String getNumPiso() {
        return numPiso;
    }

    public void setNumPiso(String numPiso) {
        this.numPiso = numPiso;
    }

    public String getNumVivi() {
        return numVivi;
    }

    public void setNumVivi(String numVivi) {
        this.numVivi = numVivi;
    }

    public String getConVivi() {
        return conVivi;
    }

    public void setConVivi(String conVivi) {
        this.conVivi = conVivi;
    }

    public String getCodVcol() {
        return codVcol;
    }

    public void setCodVcol(String codVcol) {
        this.codVcol = codVcol;
    }

    public String getTipOtro() {
        return tipOtro;
    }

    public void setTipOtro(String tipOtro) {
        this.tipOtro = tipOtro;
    }

    public String getCodOtro() {
        return codOtro;
    }

    public void setCodOtro(String codOtro) {
        this.codOtro = codOtro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getJefeHogar() {
        return jefeHogar;
    }

    public void setJefeHogar(String jefeHogar) {
        this.jefeHogar = jefeHogar;
    }

    public Integer getPisoIni() {
        return pisoIni;
    }

    public void setPisoIni(Integer pisoIni) {
        this.pisoIni = pisoIni;
    }

    public Integer getPisoFin() {
        return pisoFin;
    }

    public void setPisoFin(Integer pisoFin) {
        this.pisoFin = pisoFin;
    }

    public String getGeocodigo() {
        return geocodigo;
    }

    public void setGeocodigo(String geocodigo) {
        this.geocodigo = geocodigo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDescRegSinVivienda() {
        return descRegSinVivienda;
    }

    public void setDescRegSinVivienda(String descRegSinVivienda) {
        this.descRegSinVivienda = descRegSinVivienda;
    }

    public String getNumHabitantes() {
        return numHabitantes;
    }

    public void setNumHabitantes(String numHabitantes) {
        this.numHabitantes = numHabitantes;
    }
 
    public AeArea getCodArea() {
        return codArea;
    }

    public void setCodArea(AeArea codArea) {
        this.codArea = codArea;
    }

    public AeGeneracion getCodGeneracion() {
        return codGeneracion;
    }

    public void setCodGeneracion(AeGeneracion codGeneracion) {
        this.codGeneracion = codGeneracion;
    }

    public BigDecimal getValPonderado() {
        return valPonderado;
    }

    public void setValPonderado(BigDecimal valPonderado) {
        this.valPonderado = valPonderado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroBase != null ? idRegistroBase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AeRegistroBase)) {
            return false;
        }
        AeRegistroBase other = (AeRegistroBase) object;
        if ((this.idRegistroBase == null && other.idRegistroBase != null) || (this.idRegistroBase != null && !this.idRegistroBase.equals(other.idRegistroBase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.AeRegistroBase[ idRegistroBase=" + idRegistroBase + " ]";
    }

}
