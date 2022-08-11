/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

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
@Table(name = "captura.capt_carga_georeferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptCargaGeoreferencia.findAll", query = "SELECT c FROM CaptCargaGeoreferencia c")})
public class CaptCargaGeoreferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cargeoref")
    private Integer idCargeoref;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_car_con", referencedColumnName = "id_car_con")
    @ManyToOne
    private CaptCargaControl codCarCon;
    @JoinColumn(name = "cod_georef", referencedColumnName = "id_georef")
    @ManyToOne
    private CaptGeoreferencia codGeoref;

    public CaptCargaGeoreferencia() {
    }

    public CaptCargaGeoreferencia(Integer idCargeoref) {
        this.idCargeoref = idCargeoref;
    }

    public Integer getIdCargeoref() {
        return idCargeoref;
    }

    public void setIdCargeoref(Integer idCargeoref) {
        this.idCargeoref = idCargeoref;
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

    public CaptCargaControl getCodCarCon() {
        return codCarCon;
    }

    public void setCodCarCon(CaptCargaControl codCarCon) {
        this.codCarCon = codCarCon;
    }

    public CaptGeoreferencia getCodGeoref() {
        return codGeoref;
    }

    public void setCodGeoref(CaptGeoreferencia codGeoref) {
        this.codGeoref = codGeoref;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargeoref != null ? idCargeoref.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptCargaGeoreferencia)) {
            return false;
        }
        CaptCargaGeoreferencia other = (CaptCargaGeoreferencia) object;
        if ((this.idCargeoref == null && other.idCargeoref != null) || (this.idCargeoref != null && !this.idCargeoref.equals(other.idCargeoref))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptCargaGeoreferencia[ idCargeoref=" + idCargeoref + " ]";
    }
    
}
