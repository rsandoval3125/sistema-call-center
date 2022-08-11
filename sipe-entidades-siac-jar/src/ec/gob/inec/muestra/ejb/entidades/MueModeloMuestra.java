/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
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
@Table(name = "muestra.mue_modelo_muestra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueModeloMuestra.findAll", query = "SELECT m FROM MueModeloMuestra m")})
public class MueModeloMuestra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_modelo_muestra")
    private Integer idModeloMuestra;
    @Size(max = 250)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modelo")
    private boolean modelo;
    @Size(max = 2147483647)
    @Column(name = "definicion_modelo")
    private String definicionModelo;
    @JoinColumn(name = "cod_formulario", referencedColumnName = "id_formulario")
    @ManyToOne
    private MetFormulario codFormulario;
    @JoinColumn(name = "cod_muestra", referencedColumnName = "id_muestra")
    @ManyToOne
    private MueMuestra codMuestra;

    public MueModeloMuestra() {
    }

    public MueModeloMuestra(Integer idModeloMuestra) {
        this.idModeloMuestra = idModeloMuestra;
    }

    public MueModeloMuestra(Integer idModeloMuestra, boolean estadoLogico, boolean modelo) {
        this.idModeloMuestra = idModeloMuestra;
        this.estadoLogico = estadoLogico;
        this.modelo = modelo;
    }

    public Integer getIdModeloMuestra() {
        return idModeloMuestra;
    }

    public void setIdModeloMuestra(Integer idModeloMuestra) {
        this.idModeloMuestra = idModeloMuestra;
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

    public boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public boolean getModelo() {
        return modelo;
    }

    public void setModelo(boolean modelo) {
        this.modelo = modelo;
    }

    public String getDefinicionModelo() {
        return definicionModelo;
    }

    public void setDefinicionModelo(String definicionModelo) {
        this.definicionModelo = definicionModelo;
    }

    public MetFormulario getCodFormulario() {
        return codFormulario;
    }

    public void setCodFormulario(MetFormulario codFormulario) {
        this.codFormulario = codFormulario;
    }

    public MueMuestra getCodMuestra() {
        return codMuestra;
    }

    public void setCodMuestra(MueMuestra codMuestra) {
        this.codMuestra = codMuestra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModeloMuestra != null ? idModeloMuestra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueModeloMuestra)) {
            return false;
        }
        MueModeloMuestra other = (MueModeloMuestra) object;
        if ((this.idModeloMuestra == null && other.idModeloMuestra != null) || (this.idModeloMuestra != null && !this.idModeloMuestra.equals(other.idModeloMuestra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueModeloMuestra[ idModeloMuestra=" + idModeloMuestra + " ]";
    }
    
}
