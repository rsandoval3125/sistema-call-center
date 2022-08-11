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
@Table(name = "proceso.pro_actividad_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProActividadItem.findAll", query = "SELECT p FROM ProActividadItem p")})
public class ProActividadItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_actitem")
    private Integer idActitem;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_actividad", referencedColumnName = "id_actividad")
    @ManyToOne
    private ProActividad codActividad;
    @JoinColumn(name = "cod_objinf", referencedColumnName = "id_objinf")
    @ManyToOne
    private ProObjetoInformacion codObjinf;

    public ProActividadItem() {
    }

    public ProActividadItem(Integer idActitem) {
        this.idActitem = idActitem;
    }

    public ProActividadItem(Integer idActitem, boolean estadoLogico) {
        this.idActitem = idActitem;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdActitem() {
        return idActitem;
    }

    public void setIdActitem(Integer idActitem) {
        this.idActitem = idActitem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public ProActividad getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(ProActividad codActividad) {
        this.codActividad = codActividad;
    }

    public ProObjetoInformacion getCodObjinf() {
        return codObjinf;
    }

    public void setCodObjinf(ProObjetoInformacion codObjinf) {
        this.codObjinf = codObjinf;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActitem != null ? idActitem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProActividadItem)) {
            return false;
        }
        ProActividadItem other = (ProActividadItem) object;
        if ((this.idActitem == null && other.idActitem != null) || (this.idActitem != null && !this.idActitem.equals(other.idActitem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProActividadItem[ idActitem=" + idActitem + " ]";
    }
    
}
