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
@Table(name = "proceso.pro_estructura_ampliada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProEstructuraAmpliada.findAll", query = "SELECT p FROM ProEstructuraAmpliada p")})
public class ProEstructuraAmpliada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estruc_ampliada")
    private Integer idEstrucAmpliada;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_actividad", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codActividad;
    @JoinColumn(name = "cod_estructura", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codEstructura;
    @JoinColumn(name = "cod_financiamiento", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codFinanciamiento;
    @JoinColumn(name = "cod_anio", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codAnio;
    @JoinColumn(name = "cod_operacion_direccion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codOperacionDireccion;
    @JoinColumn(name = "cod_siglas_proyecto", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codSiglasProyecto;
    @JoinColumn(name = "cod_tipo_actividad", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoActividad;

    public ProEstructuraAmpliada() {
    }

    public ProEstructuraAmpliada(Integer idEstrucAmpliada) {
        this.idEstrucAmpliada = idEstrucAmpliada;
    }

    public ProEstructuraAmpliada(Integer idEstrucAmpliada, boolean estadoLogico) {
        this.idEstrucAmpliada = idEstrucAmpliada;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdEstrucAmpliada() {
        return idEstrucAmpliada;
    }

    public void setIdEstrucAmpliada(Integer idEstrucAmpliada) {
        this.idEstrucAmpliada = idEstrucAmpliada;
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

    public MetCatalogo getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(MetCatalogo codActividad) {
        this.codActividad = codActividad;
    }

    public MetCatalogo getCodEstructura() {
        return codEstructura;
    }

    public void setCodEstructura(MetCatalogo codEstructura) {
        this.codEstructura = codEstructura;
    }

    public MetCatalogo getCodFinanciamiento() {
        return codFinanciamiento;
    }

    public void setCodFinanciamiento(MetCatalogo codFinanciamiento) {
        this.codFinanciamiento = codFinanciamiento;
    }

    public MetCatalogo getCodAnio() {
        return codAnio;
    }

    public void setCodAnio(MetCatalogo codAnio) {
        this.codAnio = codAnio;
    }

    public MetCatalogo getCodOperacionDireccion() {
        return codOperacionDireccion;
    }

    public void setCodOperacionDireccion(MetCatalogo codOperacionDireccion) {
        this.codOperacionDireccion = codOperacionDireccion;
    }

    public MetCatalogo getCodSiglasProyecto() {
        return codSiglasProyecto;
    }

    public void setCodSiglasProyecto(MetCatalogo codSiglasProyecto) {
        this.codSiglasProyecto = codSiglasProyecto;
    }

    public MetCatalogo getCodTipoActividad() {
        return codTipoActividad;
    }

    public void setCodTipoActividad(MetCatalogo codTipoActividad) {
        this.codTipoActividad = codTipoActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstrucAmpliada != null ? idEstrucAmpliada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProEstructuraAmpliada)) {
            return false;
        }
        ProEstructuraAmpliada other = (ProEstructuraAmpliada) object;
        if ((this.idEstrucAmpliada == null && other.idEstrucAmpliada != null) || (this.idEstrucAmpliada != null && !this.idEstrucAmpliada.equals(other.idEstrucAmpliada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProEstructuraAmpliada[ idEstrucAmpliada=" + idEstrucAmpliada + " ]";
    }
    
}
