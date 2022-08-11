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
@Table(name = "metadato.met_formulario_seccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetFormularioSeccion.findAll", query = "SELECT m FROM MetFormularioSeccion m")})
public class MetFormularioSeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_formulario_seccion")
    private Integer idFormularioSeccion;
    @Basic(optional = false)
    @Column(name = "orden")
    private int orden;
    @Column(name = "texto_adicional")
    private String textoAdicional;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_formulario", referencedColumnName = "id_formulario")
    @ManyToOne
    private MetFormulario codFormulario;
    @JoinColumn(name = "cod_seccion", referencedColumnName = "id_seccion")
    @ManyToOne(optional = false)
    private MetSeccion codSeccion;

    public MetFormularioSeccion() {
    }

    public MetFormularioSeccion(Integer idFormularioSeccion) {
        this.idFormularioSeccion = idFormularioSeccion;
    }

    public MetFormularioSeccion(Integer idFormularioSeccion, int orden, boolean estadoLogico) {
        this.idFormularioSeccion = idFormularioSeccion;
        this.orden = orden;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdFormularioSeccion() {
        return idFormularioSeccion;
    }

    public void setIdFormularioSeccion(Integer idFormularioSeccion) {
        this.idFormularioSeccion = idFormularioSeccion;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getTextoAdicional() {
        return textoAdicional;
    }

    public void setTextoAdicional(String textoAdicional) {
        this.textoAdicional = textoAdicional;
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

    public MetFormulario getCodFormulario() {
        return codFormulario;
    }

    public void setCodFormulario(MetFormulario codFormulario) {
        this.codFormulario = codFormulario;
    }

    public MetSeccion getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(MetSeccion codSeccion) {
        this.codSeccion = codSeccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormularioSeccion != null ? idFormularioSeccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetFormularioSeccion)) {
            return false;
        }
        MetFormularioSeccion other = (MetFormularioSeccion) object;
        if ((this.idFormularioSeccion == null && other.idFormularioSeccion != null) || (this.idFormularioSeccion != null && !this.idFormularioSeccion.equals(other.idFormularioSeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetFormularioSeccion[ idFormularioSeccion=" + idFormularioSeccion + " ]";
    }
    
}
