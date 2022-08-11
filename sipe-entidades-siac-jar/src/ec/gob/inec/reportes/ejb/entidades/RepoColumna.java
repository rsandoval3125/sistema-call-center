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
@Table(name = "reportes.repo_columna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepoColumna.findAll", query = "SELECT r FROM RepoColumna r")})
public class RepoColumna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_columna")
    private Integer idColumna;
    @Size(max = 100)
    @Column(name = "campo")
    private String campo;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "atributo")
    private String atributo;
    @Size(max = 20)
    @Column(name = "componente")
    private String componente;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_subr", referencedColumnName = "id_subr")
    @ManyToOne(optional = false)
    private RepoSubreporte codSubr;

    public RepoColumna() {
    }

    public RepoColumna(Integer idColumna) {
        this.idColumna = idColumna;
    }

    public Integer getIdColumna() {
        return idColumna;
    }

    public void setIdColumna(Integer idColumna) {
        this.idColumna = idColumna;
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

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public RepoSubreporte getCodSubr() {
        return codSubr;
    }

    public void setCodSubr(RepoSubreporte codSubr) {
        this.codSubr = codSubr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColumna != null ? idColumna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepoColumna)) {
            return false;
        }
        RepoColumna other = (RepoColumna) object;
        if ((this.idColumna == null && other.idColumna != null) || (this.idColumna != null && !this.idColumna.equals(other.idColumna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.reportes.ejb.entidades.RepoColumna[ idColumna=" + idColumna + " ]";
    }
    
}
