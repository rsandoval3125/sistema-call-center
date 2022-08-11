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
@Table(name = "reportes.repo_procedimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepoProcedimiento.findAll", query = "SELECT r FROM RepoProcedimiento r")})
public class RepoProcedimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proc")
    private Integer idProc;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "sql")
    private String sql;
    @Column(name = "variables")
    private String variables;
    @Basic(optional = false)
    @Column(name = "conexion")
    private String conexion;

    public RepoProcedimiento() {
    }

    public RepoProcedimiento(Integer idProc) {
        this.idProc = idProc;
    }

    public RepoProcedimiento(Integer idProc, String nombre, String sql, String conexion) {
        this.idProc = idProc;
        this.nombre = nombre;
        this.sql = sql;
        this.conexion = conexion;
    }

    public Integer getIdProc() {
        return idProc;
    }

    public void setIdProc(Integer idProc) {
        this.idProc = idProc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getConexion() {
        return conexion;
    }

    public void setConexion(String conexion) {
        this.conexion = conexion;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProc != null ? idProc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepoProcedimiento)) {
            return false;
        }
        RepoProcedimiento other = (RepoProcedimiento) object;
        if ((this.idProc == null && other.idProc != null) || (this.idProc != null && !this.idProc.equals(other.idProc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento[ idProc=" + idProc + " ]";
    }
    
}
