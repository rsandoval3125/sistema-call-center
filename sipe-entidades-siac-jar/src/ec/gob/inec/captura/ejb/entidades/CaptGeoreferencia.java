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
@Table(name = "captura.capt_georeferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptGeoreferencia.findAll", query = "SELECT c FROM CaptGeoreferencia c")})
public class CaptGeoreferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_georef")
    private Integer idGeoref;
    @Size(max = 30)
    @Column(name = "clave")
    private String clave;
    @Size(max = 100)
    @Column(name = "nombre_area_ref")
    private String nombreAreaRef;
    @Size(max = 30)
    @Column(name = "codigo_area_ref")
    private String codigoAreaRef;
    @Size(max = 2147483647)
    @Column(name = "inf_vectorial_area")
    private String infVectorialArea;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;

    public CaptGeoreferencia() {
    }

    public CaptGeoreferencia(Integer idGeoref) {
        this.idGeoref = idGeoref;
    }

    public Integer getIdGeoref() {
        return idGeoref;
    }

    public void setIdGeoref(Integer idGeoref) {
        this.idGeoref = idGeoref;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreAreaRef() {
        return nombreAreaRef;
    }

    public void setNombreAreaRef(String nombreAreaRef) {
        this.nombreAreaRef = nombreAreaRef;
    }

    public String getCodigoAreaRef() {
        return codigoAreaRef;
    }

    public void setCodigoAreaRef(String codigoAreaRef) {
        this.codigoAreaRef = codigoAreaRef;
    }

    public String getInfVectorialArea() {
        return infVectorialArea;
    }

    public void setInfVectorialArea(String infVectorialArea) {
        this.infVectorialArea = infVectorialArea;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGeoref != null ? idGeoref.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptGeoreferencia)) {
            return false;
        }
        CaptGeoreferencia other = (CaptGeoreferencia) object;
        if ((this.idGeoref == null && other.idGeoref != null) || (this.idGeoref != null && !this.idGeoref.equals(other.idGeoref))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptGeoreferencia[ idGeoref=" + idGeoref + " ]";
    }
    
}
