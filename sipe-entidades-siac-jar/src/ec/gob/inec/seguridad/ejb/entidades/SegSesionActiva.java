/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "seguridad.seg_sesion_activa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegSesionActiva.findAll", query = "SELECT s FROM SegSesionActiva s")})
public class SegSesionActiva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ses_act")
    private Integer idSesAct;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "identificador")
    private String identificador;
    @Column(name = "cod_usu")
    private Integer codUsu;
    @Column(name = "fechahora_reg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraReg;
    @Size(max = 2147483647)
    @Column(name = "atributos")
    private String atributos;

    public SegSesionActiva() {
    }

    public SegSesionActiva(Integer idSesAct) {
        this.idSesAct = idSesAct;
    }

    public SegSesionActiva(Integer idSesAct, String identificador) {
        this.idSesAct = idSesAct;
        this.identificador = identificador;
    }

    public Integer getIdSesAct() {
        return idSesAct;
    }

    public void setIdSesAct(Integer idSesAct) {
        this.idSesAct = idSesAct;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Integer getCodUsu() {
        return codUsu;
    }

    public void setCodUsu(Integer codUsu) {
        this.codUsu = codUsu;
    }

    public Date getFechahoraReg() {
        return fechahoraReg;
    }

    public void setFechahoraReg(Date fechahoraReg) {
        this.fechahoraReg = fechahoraReg;
    }

    public String getAtributos() {
        return atributos;
    }

    public void setAtributos(String atributos) {
        this.atributos = atributos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSesAct != null ? idSesAct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegSesionActiva)) {
            return false;
        }
        SegSesionActiva other = (SegSesionActiva) object;
        if ((this.idSesAct == null && other.idSesAct != null) || (this.idSesAct != null && !this.idSesAct.equals(other.idSesAct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegSesionActiva[ idSesAct=" + idSesAct + " ]";
    }
    
}
