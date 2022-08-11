/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_correo_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmCorreoCab.findAll", query = "SELECT a FROM AdmCorreoCab a")})
public class AdmCorreoCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_correo_cab")
    private Integer idCorreoCab;
    @Size(max = 250)
    @Column(name = "alias")
    private String alias;
    @Column(name = "fechahora_registra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraRegistra;
    @Size(max = 250)
    @Column(name = "asunto")
    private String asunto;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_tipo_prioridad", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoPrioridad;

    public AdmCorreoCab() {
    }

    public AdmCorreoCab(Integer idCorreoCab) {
        this.idCorreoCab = idCorreoCab;
    }

    public Integer getIdCorreoCab() {
        return idCorreoCab;
    }

    public void setIdCorreoCab(Integer idCorreoCab) {
        this.idCorreoCab = idCorreoCab;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getFechahoraRegistra() {
        return fechahoraRegistra;
    }

    public void setFechahoraRegistra(Date fechahoraRegistra) {
        this.fechahoraRegistra = fechahoraRegistra;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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


    public MetCatalogo getCodTipoPrioridad() {
        return codTipoPrioridad;
    }

    public void setCodTipoPrioridad(MetCatalogo codTipoPrioridad) {
        this.codTipoPrioridad = codTipoPrioridad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorreoCab != null ? idCorreoCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmCorreoCab)) {
            return false;
        }
        AdmCorreoCab other = (AdmCorreoCab) object;
        if ((this.idCorreoCab == null && other.idCorreoCab != null) || (this.idCorreoCab != null && !this.idCorreoCab.equals(other.idCorreoCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmCorreoCab[ idCorreoCab=" + idCorreoCab + " ]";
    }
    
}
