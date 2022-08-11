/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "proceso.pro_objeto_informacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProObjetoInformacion.findAll", query = "SELECT p FROM ProObjetoInformacion p")})
public class ProObjetoInformacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_objinf")
    private String idObjinf;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @Column(name = "estado_logico")
    private boolean estadoLogico;

    public ProObjetoInformacion() {
    }

    public ProObjetoInformacion(String idObjinf) {
        this.idObjinf = idObjinf;
    }

    public ProObjetoInformacion(String idObjinf, boolean estadoLogico) {
        this.idObjinf = idObjinf;
        this.estadoLogico = estadoLogico;
    }

    public String getIdObjinf() {
        return idObjinf;
    }

    public void setIdObjinf(String idObjinf) {
        this.idObjinf = idObjinf;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObjinf != null ? idObjinf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProObjetoInformacion)) {
            return false;
        }
        ProObjetoInformacion other = (ProObjetoInformacion) object;
        if ((this.idObjinf == null && other.idObjinf != null) || (this.idObjinf != null && !this.idObjinf.equals(other.idObjinf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProObjetoInformacion[ idObjinf=" + idObjinf + " ]";
    }
    
}
