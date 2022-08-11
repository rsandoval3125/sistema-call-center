/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "administracion.adm_base_datos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmBaseDatos.findAll", query = "SELECT a FROM AdmBaseDatos a")})
public class AdmBaseDatos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bas_dat")
    private Integer idBasDat;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 50)
    @Column(name = "driver")
    private String driver;
    @Size(max = 50)
    @Column(name = "rdbms")
    private String rdbms;
    @Size(max = 50)
    @Column(name = "ip")
    private String ip;
    @Column(name = "puerto")
    private Integer puerto;
    @Size(max = 250)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 250)
    @Column(name = "password")
    private String password;
    @Size(max = 100)
    @Column(name = "nombrebdd")
    private String nombrebdd;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 20)
    @Column(name = "alias")
    private String alias;

    public AdmBaseDatos() {
    }

    public AdmBaseDatos(Integer idBasDat) {
        this.idBasDat = idBasDat;
    }

    public AdmBaseDatos(Integer idBasDat, boolean estadoLogico) {
        this.idBasDat = idBasDat;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdBasDat() {
        return idBasDat;
    }

    public void setIdBasDat(Integer idBasDat) {
        this.idBasDat = idBasDat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRdbms() {
        return rdbms;
    }

    public void setRdbms(String rdbms) {
        this.rdbms = rdbms;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPuerto() {
        return puerto;
    }

    public void setPuerto(Integer puerto) {
        this.puerto = puerto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombrebdd() {
        return nombrebdd;
    }

    public void setNombrebdd(String nombrebdd) {
        this.nombrebdd = nombrebdd;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBasDat != null ? idBasDat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmBaseDatos)) {
            return false;
        }
        AdmBaseDatos other = (AdmBaseDatos) object;
        if ((this.idBasDat == null && other.idBasDat != null) || (this.idBasDat != null && !this.idBasDat.equals(other.idBasDat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos[ idBasDat=" + idBasDat + " ]";
    }
    
}
