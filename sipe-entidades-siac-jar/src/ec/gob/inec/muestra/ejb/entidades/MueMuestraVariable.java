/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "muestra.mue_muestra_variable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueMuestraVariable.findAll", query = "SELECT m FROM MueMuestraVariable m")})
public class MueMuestraVariable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mue_variable")
    private Integer idMueVariable;
    @Size(max = 500)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 500)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "longitud")
    private Integer longitud;
    @Column(name = "presision")
    private Integer presision;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 250)
    @Column(name = "alias")
    private String alias;
    @JoinColumn(name = "cod_catalogo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codCatalogo;

    public MueMuestraVariable() {
    }

    public MueMuestraVariable(Integer idMueVariable) {
        this.idMueVariable = idMueVariable;
    }

    public Integer getIdMueVariable() {
        return idMueVariable;
    }

    public void setIdMueVariable(Integer idMueVariable) {
        this.idMueVariable = idMueVariable;
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

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public Integer getPresision() {
        return presision;
    }

    public void setPresision(Integer presision) {
        this.presision = presision;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public MetCatalogo getCodCatalogo() {
        return codCatalogo;
    }

    public void setCodCatalogo(MetCatalogo codCatalogo) {
        this.codCatalogo = codCatalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMueVariable != null ? idMueVariable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueMuestraVariable)) {
            return false;
        }
        MueMuestraVariable other = (MueMuestraVariable) object;
        if ((this.idMueVariable == null && other.idMueVariable != null) || (this.idMueVariable != null && !this.idMueVariable.equals(other.idMueVariable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueMuestraVariable[ idMueVariable=" + idMueVariable + " ]";
    }
    
}
