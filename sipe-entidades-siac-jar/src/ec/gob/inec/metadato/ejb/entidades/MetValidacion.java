/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "metadato.met_validacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetValidacion.findAll", query = "SELECT m FROM MetValidacion m")})
public class MetValidacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_valida")
    private Integer idValida;
    @Column(name = "condicion")
    private String condicion;
    @Column(name = "tipo_validacion")
    private Short tipoValidacion;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "preg_control")
    private String pregControl;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_var", referencedColumnName = "id_var")
    @ManyToOne
    private MetVariable codVar;

    public MetValidacion() {
    }

    public MetValidacion(Integer idValida) {
        this.idValida = idValida;
    }

    public Integer getIdValida() {
        return idValida;
    }

    public void setIdValida(Integer idValida) {
        this.idValida = idValida;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public Short getTipoValidacion() {
        return tipoValidacion;
    }

    public void setTipoValidacion(Short tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPregControl() {
        return pregControl;
    }

    public void setPregControl(String pregControl) {
        this.pregControl = pregControl;
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

    public MetVariable getCodVar() {
        return codVar;
    }

    public void setCodVar(MetVariable codVar) {
        this.codVar = codVar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValida != null ? idValida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetValidacion)) {
            return false;
        }
        MetValidacion other = (MetValidacion) object;
        if ((this.idValida == null && other.idValida != null) || (this.idValida != null && !this.idValida.equals(other.idValida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetValidacion[ idValida=" + idValida + " ]";
    }
    
}
