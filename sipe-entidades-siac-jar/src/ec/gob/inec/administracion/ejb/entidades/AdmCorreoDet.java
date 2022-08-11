/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

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
@Table(name = "administracion.adm_correo_det")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmCorreoDet.findAll", query = "SELECT a FROM AdmCorreoDet a")})
public class AdmCorreoDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_correo_det")
    private Integer idCorreoDet;
    @Size(max = 2147483647)
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "parametro_sistema")
    private Boolean parametroSistema;
    @Column(name = "posicion_parametro")
    private Character posicionParametro;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_correo_cab", referencedColumnName = "id_correo_cab")
    @ManyToOne
    private AdmCorreoCab codCorreoCab;

    public AdmCorreoDet() {
    }

    public AdmCorreoDet(Integer idCorreoDet) {
        this.idCorreoDet = idCorreoDet;
    }

    public Integer getIdCorreoDet() {
        return idCorreoDet;
    }

    public void setIdCorreoDet(Integer idCorreoDet) {
        this.idCorreoDet = idCorreoDet;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Boolean getParametroSistema() {
        return parametroSistema;
    }

    public void setParametroSistema(Boolean parametroSistema) {
        this.parametroSistema = parametroSistema;
    }

    public Character getPosicionParametro() {
        return posicionParametro;
    }

    public void setPosicionParametro(Character posicionParametro) {
        this.posicionParametro = posicionParametro;
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

    public AdmCorreoCab getCodCorreoCab() {
        return codCorreoCab;
    }

    public void setCodCorreoCab(AdmCorreoCab codCorreoCab) {
        this.codCorreoCab = codCorreoCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorreoDet != null ? idCorreoDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmCorreoDet)) {
            return false;
        }
        AdmCorreoDet other = (AdmCorreoDet) object;
        if ((this.idCorreoDet == null && other.idCorreoDet != null) || (this.idCorreoDet != null && !this.idCorreoDet.equals(other.idCorreoDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet[ idCorreoDet=" + idCorreoDet + " ]";
    }
    
}
