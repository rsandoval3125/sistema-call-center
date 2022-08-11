/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

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
@Table(name = "proceso.pro_nombre_columna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProNombreColumna.findAll", query = "SELECT p FROM ProNombreColumna p")})
public class ProNombreColumna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nombre_columna")
    private Integer idNombreColumna;
    @Size(max = 150)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "solicitado")
    private Boolean solicitado;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "requerido")
    private Boolean requerido;
    @JoinColumn(name = "cod_tipo_dato", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoDato;
    @JoinColumn(name = "cod_anio_ejecucion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codAnioEjecucion;
    @JoinColumn(name = "cod_tipo_archivo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoArchivo;

    public ProNombreColumna() {
    }

    public ProNombreColumna(Integer idNombreColumna) {
        this.idNombreColumna = idNombreColumna;
    }

    public ProNombreColumna(Integer idNombreColumna, boolean estadoLogico) {
        this.idNombreColumna = idNombreColumna;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdNombreColumna() {
        return idNombreColumna;
    }

    public void setIdNombreColumna(Integer idNombreColumna) {
        this.idNombreColumna = idNombreColumna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(Boolean solicitado) {
        this.solicitado = solicitado;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    public Boolean getRequerido() {
        return requerido;
    }

    public void setRequerido(Boolean requerido) {
        this.requerido = requerido;
    }

    public MetCatalogo getCodTipoDato() {
        return codTipoDato;
    }

    public void setCodTipoDato(MetCatalogo codTipoDato) {
        this.codTipoDato = codTipoDato;
    }

    public MetCatalogo getCodAnioEjecucion() {
        return codAnioEjecucion;
    }

    public void setCodAnioEjecucion(MetCatalogo codAnioEjecucion) {
        this.codAnioEjecucion = codAnioEjecucion;
    }

    public MetCatalogo getCodTipoArchivo() {
        return codTipoArchivo;
    }

    public void setCodTipoArchivo(MetCatalogo codTipoArchivo) {
        this.codTipoArchivo = codTipoArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNombreColumna != null ? idNombreColumna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProNombreColumna)) {
            return false;
        }
        ProNombreColumna other = (ProNombreColumna) object;
        if ((this.idNombreColumna == null && other.idNombreColumna != null) || (this.idNombreColumna != null && !this.idNombreColumna.equals(other.idNombreColumna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProNombreColumna[ idNombreColumna=" + idNombreColumna + " ]";
    }
    
}
