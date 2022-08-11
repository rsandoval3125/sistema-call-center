/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.entidades;

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
@Table(name = "distribucion.dis_equipo_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisEquipoTrabajo.findAll", query = "SELECT d FROM DisEquipoTrabajo d")})
public class DisEquipoTrabajo implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equ_tra")
    private Integer idEquTra;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "orden")
    private Integer orden;
    @JoinColumn(name = "cod_zonal", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codZonal;
    @JoinColumn(name = "cod_tipo_levant", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoLevant;

    public DisEquipoTrabajo() {
    }

    public DisEquipoTrabajo(Integer idEquTra) {
        this.idEquTra = idEquTra;
    }

    public DisEquipoTrabajo(Integer idEquTra, boolean estadoLogico) {
        this.idEquTra = idEquTra;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdEquTra() {
        return idEquTra;
    }

    public void setIdEquTra(Integer idEquTra) {
        this.idEquTra = idEquTra;
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public MetCatalogo getCodZonal() {
        return codZonal;
    }

    public void setCodZonal(MetCatalogo codZonal) {
        this.codZonal = codZonal;
    }

    public MetCatalogo getCodTipoLevant() {
        return codTipoLevant;
    }

    public void setCodTipoLevant(MetCatalogo codTipoLevant) {
        this.codTipoLevant = codTipoLevant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquTra != null ? idEquTra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisEquipoTrabajo)) {
            return false;
        }
        DisEquipoTrabajo other = (DisEquipoTrabajo) object;
        if ((this.idEquTra == null && other.idEquTra != null) || (this.idEquTra != null && !this.idEquTra.equals(other.idEquTra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajo[ idEquTra=" + idEquTra + " ]";
    }
    
}
