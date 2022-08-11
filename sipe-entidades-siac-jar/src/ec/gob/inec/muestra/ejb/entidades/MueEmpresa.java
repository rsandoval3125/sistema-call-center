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
@Table(name = "muestra.mue_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueEmpresa.findAll", query = "SELECT m FROM MueEmpresa m")})
public class MueEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    @Size(max = 20)
    @Column(name = "identificador_ruc")
    private String identificadorRuc;
    @Size(max = 20)
    @Column(name = "identificador_diee")
    private String identificadorDiee;
    @Size(max = 250)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 250)
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 50)
    @Column(name = "cod_mue_ref")
    private String codMueRef;
    @JoinColumn(name = "cod_predio", referencedColumnName = "id_predio")
    @ManyToOne
    private MuePredio codPredio;

    public MueEmpresa() {
    }

    public MueEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdentificadorRuc() {
        return identificadorRuc;
    }

    public void setIdentificadorRuc(String identificadorRuc) {
        this.identificadorRuc = identificadorRuc;
    }

    public String getIdentificadorDiee() {
        return identificadorDiee;
    }

    public void setIdentificadorDiee(String identificadorDiee) {
        this.identificadorDiee = identificadorDiee;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
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

    public MuePredio getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(MuePredio codPredio) {
        this.codPredio = codPredio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueEmpresa)) {
            return false;
        }
        MueEmpresa other = (MueEmpresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueEmpresa[ idEmpresa=" + idEmpresa + " ]";
    }
    
}
