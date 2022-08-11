/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "muestra.mue_vivienda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueVivienda.findAll", query = "SELECT m FROM MueVivienda m")})
public class MueVivienda implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vivienda")
    private Integer idVivienda;
    @Size(max = 250)
    @Column(name = "identificador_viv")
    private String identificadorViv;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 50)
    @Column(name = "cod_mue_ref")
    private String codMueRef;
    @Size(max = 20)
    @Column(name = "patio")
    private String patio;
    @Size(max = 20)
    @Column(name = "piso")
    private String piso;
    @Size(max = 100)
    @Column(name = "num_mun_ref")
    private String numMunRef;
    @Size(max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "orden")
    private Integer orden;
    @JoinColumn(name = "cod_predio", referencedColumnName = "id_predio")
    @ManyToOne
    private MuePredio codPredio;

    public MueVivienda() {
    }

    public MueVivienda(Integer idVivienda) {
        this.idVivienda = idVivienda;
    }

    public Integer getIdVivienda() {
        return idVivienda;
    }

    public void setIdVivienda(Integer idVivienda) {
        this.idVivienda = idVivienda;
    }

    public String getIdentificadorViv() {
        return identificadorViv;
    }

    public void setIdentificadorViv(String identificadorViv) {
        this.identificadorViv = identificadorViv;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getCodMueRef() {
        return codMueRef;
    }

    public void setCodMueRef(String codMueRef) {
        this.codMueRef = codMueRef;
    }

    public String getPatio() {
        return patio;
    }

    public void setPatio(String patio) {
        this.patio = patio;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getNumMunRef() {
        return numMunRef;
    }

    public void setNumMunRef(String numMunRef) {
        this.numMunRef = numMunRef;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public MuePredio getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(MuePredio codPredio) {
        this.codPredio = codPredio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVivienda != null ? idVivienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueVivienda)) {
            return false;
        }
        MueVivienda other = (MueVivienda) object;
        if ((this.idVivienda == null && other.idVivienda != null) || (this.idVivienda != null && !this.idVivienda.equals(other.idVivienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueVivienda[ idVivienda=" + idVivienda + " ]";
    }
    
}
