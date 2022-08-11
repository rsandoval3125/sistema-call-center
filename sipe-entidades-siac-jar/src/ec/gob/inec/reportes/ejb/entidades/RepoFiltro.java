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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "reportes.repo_filtro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepoFiltro.findAll", query = "SELECT r FROM RepoFiltro r")})
public class RepoFiltro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filtro")
    private Integer idFiltro;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "catalogo")
    private String catalogo;
    @Column(name = "sql")
    private String sql;

    public RepoFiltro() {
    }

    public RepoFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public RepoFiltro(Integer idFiltro, String nombre) {
        this.idFiltro = idFiltro;
        this.nombre = nombre;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFiltro != null ? idFiltro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepoFiltro)) {
            return false;
        }
        RepoFiltro other = (RepoFiltro) object;
        if ((this.idFiltro == null && other.idFiltro != null) || (this.idFiltro != null && !this.idFiltro.equals(other.idFiltro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.reportes.ejb.entidades.RepoFiltro[ idFiltro=" + idFiltro + " ]";
    }
    
}
