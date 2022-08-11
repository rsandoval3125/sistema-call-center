/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;


import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_dispositivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmDispositivo.findAll", query = "SELECT a FROM AdmDispositivo a")})
public class AdmDispositivo implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dispositivo")
    private Integer idDispositivo;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 20)
    @Column(name = "codigo_inec")
    private String codigoInec;
    @Size(max = 15)
    @Column(name = "direccion_ip")
    private String direccionIp;
    @Size(max = 50)
    @Column(name = "serie")
    private String serie;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_zonal", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codZonal;

    public AdmDispositivo() {
    }

    public AdmDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public AdmDispositivo(Integer idDispositivo, boolean estadoLogico) {
        this.idDispositivo = idDispositivo;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoInec() {
        return codigoInec;
    }

    public void setCodigoInec(String codigoInec) {
        this.codigoInec = codigoInec;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
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

    public MetCatalogo getCodZonal() {
        return codZonal;
    }

    public void setCodZonal(MetCatalogo codZonal) {
        this.codZonal = codZonal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDispositivo != null ? idDispositivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmDispositivo)) {
            return false;
        }
        AdmDispositivo other = (AdmDispositivo) object;
        if ((this.idDispositivo == null && other.idDispositivo != null) || (this.idDispositivo != null && !this.idDispositivo.equals(other.idDispositivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmDispositivo[ idDispositivo=" + idDispositivo + " ]";
    }
    
}
