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
@Table(name = "muestra.mue_predio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MuePredio.findAll", query = "SELECT m FROM MuePredio m")})
public class MuePredio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_predio")
    private Integer idPredio;
    @Size(max = 50)
    @Column(name = "identificador_dpa")
    private String identificadorDpa;
    @Size(max = 2147483647)
    @Column(name = "calle_principal")
    private String callePrincipal;
    @Size(max = 2147483647)
    @Column(name = "calle_segundaria")
    private String calleSegundaria;
    @Size(max = 150)
    @Column(name = "municipio_num")
    private String municipioNum;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 50)
    @Column(name = "cod_mue_ref")
    private String codMueRef;
    @Size(max = 3)
    @Column(name = "area")
    private String area;
    @Size(max = 3)
    @Column(name = "prov")
    private String prov;
    @Size(max = 3)
    @Column(name = "cant")
    private String cant;
    @Size(max = 3)
    @Column(name = "parr")
    private String parr;
    @Size(max = 4)
    @Column(name = "zona")
    private String zona;
    @Size(max = 4)
    @Column(name = "sect")
    private String sect;
    @Size(max = 4)
    @Column(name = "manz")
    private String manz;
    @Size(max = 4)
    @Column(name = "edif")
    private String edif;
    @Size(max = 2)
    @Column(name = "zonal")
    private String zonal;

    public MuePredio() {
    }

    public MuePredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public String getIdentificadorDpa() {
        return identificadorDpa;
    }

    public void setIdentificadorDpa(String identificadorDpa) {
        this.identificadorDpa = identificadorDpa;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public String getCalleSegundaria() {
        return calleSegundaria;
    }

    public void setCalleSegundaria(String calleSegundaria) {
        this.calleSegundaria = calleSegundaria;
    }

    public String getMunicipioNum() {
        return municipioNum;
    }

    public void setMunicipioNum(String municipioNum) {
        this.municipioNum = municipioNum;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getParr() {
        return parr;
    }

    public void setParr(String parr) {
        this.parr = parr;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSect() {
        return sect;
    }

    public void setSect(String sect) {
        this.sect = sect;
    }

    public String getManz() {
        return manz;
    }

    public void setManz(String manz) {
        this.manz = manz;
    }

    public String getEdif() {
        return edif;
    }

    public void setEdif(String edif) {
        this.edif = edif;
    }

    public String getZonal() {
        return zonal;
    }

    public void setZonal(String zonal) {
        this.zonal = zonal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPredio != null ? idPredio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MuePredio)) {
            return false;
        }
        MuePredio other = (MuePredio) object;
        if ((this.idPredio == null && other.idPredio != null) || (this.idPredio != null && !this.idPredio.equals(other.idPredio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MuePredio[ idPredio=" + idPredio + " ]";
    }
    
}
