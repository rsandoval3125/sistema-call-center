/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
import ec.gob.inec.muestra.ejb.entidades.MueMuestraDetalle;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "captura.capt_carga_control")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptCargaControl.findAll", query = "SELECT c FROM CaptCargaControl c")})
public class CaptCargaControl implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_car_con")
    private Integer idCarCon;
    @Size(max = 5)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 1)
    @Column(name = "zonal")
    private String zonal;
    @Size(max = 30)
    @Column(name = "clave")
    private String clave;
    @Column(name = "estado_formulario")
    private Integer estadoFormulario;
    @Column(name = "estado_transmision")
    private Integer estadoTransmision;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(max = 10)
    @Column(name = "control1")
    private String control1;
    @Size(max = 10)
    @Column(name = "control2")
    private String control2;
    @Size(max = 10)
    @Column(name = "control3")
    private String control3;
    @Size(max = 50)
    @Column(name = "control4")
    private String control4;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 20)
    @Column(name = "control5")
    private String control5;
    @JoinColumn(name = "cod_formulario", referencedColumnName = "id_formulario")
    @ManyToOne
    private MetFormulario codFormulario;
    @JoinColumn(name = "cod_mue_det", referencedColumnName = "id_mue_det")
    @ManyToOne
    private MueMuestraDetalle codMueDet;

    public CaptCargaControl() {
    }

    public CaptCargaControl(Integer idCarCon) {
        this.idCarCon = idCarCon;
    }

    public CaptCargaControl(Integer idCarCon, boolean estadoLogico) {
        this.idCarCon = idCarCon;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCarCon() {
        return idCarCon;
    }

    public void setIdCarCon(Integer idCarCon) {
        this.idCarCon = idCarCon;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getZonal() {
        return zonal;
    }

    public void setZonal(String zonal) {
        this.zonal = zonal;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getEstadoFormulario() {
        return estadoFormulario;
    }

    public void setEstadoFormulario(Integer estadoFormulario) {
        this.estadoFormulario = estadoFormulario;
    }

    public Integer getEstadoTransmision() {
        return estadoTransmision;
    }

    public void setEstadoTransmision(Integer estadoTransmision) {
        this.estadoTransmision = estadoTransmision;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getControl1() {
        return control1;
    }

    public void setControl1(String control1) {
        this.control1 = control1;
    }

    public String getControl2() {
        return control2;
    }

    public void setControl2(String control2) {
        this.control2 = control2;
    }

    public String getControl3() {
        return control3;
    }

    public void setControl3(String control3) {
        this.control3 = control3;
    }

    public String getControl4() {
        return control4;
    }

    public void setControl4(String control4) {
        this.control4 = control4;
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

    public String getControl5() {
        return control5;
    }

    public void setControl5(String control5) {
        this.control5 = control5;
    }

    public MetFormulario getCodFormulario() {
        return codFormulario;
    }

    public void setCodFormulario(MetFormulario codFormulario) {
        this.codFormulario = codFormulario;
    }

    public MueMuestraDetalle getCodMueDet() {
        return codMueDet;
    }

    public void setCodMueDet(MueMuestraDetalle codMueDet) {
        this.codMueDet = codMueDet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCarCon != null ? idCarCon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptCargaControl)) {
            return false;
        }
        CaptCargaControl other = (CaptCargaControl) object;
        if ((this.idCarCon == null && other.idCarCon != null) || (this.idCarCon != null && !this.idCarCon.equals(other.idCarCon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptCargaControl[ idCarCon=" + idCarCon + " ]";
    }
    
}
