/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.entidades;

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
@Table(name = "reportes.repo_reporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepoReporte.findAll", query = "SELECT r FROM RepoReporte r")})
public class RepoReporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reporte")
    private Integer idReporte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "campo")
    private String campo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_filtro", referencedColumnName = "id_filtro")
    @ManyToOne(optional = false)
    private RepoFiltro codFiltro;

    public RepoReporte() {
    }

    public RepoReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public RepoReporte(Integer idReporte, String campo, String nombre, boolean estadoLogico) {
        this.idReporte = idReporte;
        this.campo = campo;
        this.nombre = nombre;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
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

    public RepoFiltro getCodFiltro() {
        return codFiltro;
    }

    public void setCodFiltro(RepoFiltro codFiltro) {
        this.codFiltro = codFiltro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReporte != null ? idReporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepoReporte)) {
            return false;
        }
        RepoReporte other = (RepoReporte) object;
        if ((this.idReporte == null && other.idReporte != null) || (this.idReporte != null && !this.idReporte.equals(other.idReporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.reportes.ejb.entidades.RepoReporte[ idReporte=" + idReporte + " ]";
    }
    
}
