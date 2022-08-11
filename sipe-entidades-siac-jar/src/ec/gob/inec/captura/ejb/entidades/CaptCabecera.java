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
@Table(name = "captura.capt_cabecera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptCabecera.findAll", query = "SELECT c FROM CaptCabecera c")})
public class CaptCabecera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cab")
    private Integer idCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 30)
    @Column(name = "clave")
    private String clave;
    @Column(name = "num_det")
    private Integer numDet;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Size(max = 2)
    @Column(name = "estado1")
    private String estado1;
    @Size(max = 2)
    @Column(name = "estado2")
    private String estado2;
    @Size(max = 2)
    @Column(name = "estado3")
    private String estado3;
    @Size(max = 4)
    @Column(name = "estado4")
    private String estado4;
    @Size(max = 200)
    @Column(name = "info1")
    private String info1;
    @Size(max = 200)
    @Column(name = "info2")
    private String info2;
    @Size(max = 100)
    @Column(name = "info3")
    private String info3;
    @Size(max = 2147483647)
    @Column(name = "obs1")
    private String obs1;
    @Size(max = 2147483647)
    @Column(name = "obs2")
    private String obs2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechahora_guardado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraGuardado;
    @Column(name = "fechahora_validacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraValidacion;
    @Column(name = "fechahora_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraEdicion;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Size(max = 100)
    @Column(name = "info4")
    private String info4;
    @Size(max = 100)
    @Column(name = "info5")
    private String info5;
    @Size(max = 100)
    @Column(name = "info6")
    private String info6;
    @Size(max = 100)
    @Column(name = "info7")
    private String info7;
    @JoinColumn(name = "cod_cab_padre", referencedColumnName = "id_cab")
    @ManyToOne
    private CaptCabecera codCabPadre;
    @JoinColumn(name = "cod_car_con", referencedColumnName = "id_car_con")
    @ManyToOne
    private CaptCargaControl codCarCon;
    @JoinColumn(name = "cod_formulario", referencedColumnName = "id_formulario")
    @ManyToOne
    private MetFormulario codFormulario;
    @JoinColumn(name = "cod_muestra", referencedColumnName = "id_mue_det")
    @ManyToOne(optional = false)
    private MueMuestraDetalle codMuestra;

    public CaptCabecera() {
    }

    public CaptCabecera(Integer idCab) {
        this.idCab = idCab;
    }

    public CaptCabecera(Integer idCab, Date fechaCreacion, Date fechahoraGuardado) {
        this.idCab = idCab;
        this.fechaCreacion = fechaCreacion;
        this.fechahoraGuardado = fechahoraGuardado;
    }

    public Integer getIdCab() {
        return idCab;
    }

    public void setIdCab(Integer idCab) {
        this.idCab = idCab;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getNumDet() {
        return numDet;
    }

    public void setNumDet(Integer numDet) {
        this.numDet = numDet;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado1() {
        return estado1;
    }

    public void setEstado1(String estado1) {
        this.estado1 = estado1;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

    public String getEstado3() {
        return estado3;
    }

    public void setEstado3(String estado3) {
        this.estado3 = estado3;
    }

    public String getEstado4() {
        return estado4;
    }

    public void setEstado4(String estado4) {
        this.estado4 = estado4;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getObs1() {
        return obs1;
    }

    public void setObs1(String obs1) {
        this.obs1 = obs1;
    }

    public String getObs2() {
        return obs2;
    }

    public void setObs2(String obs2) {
        this.obs2 = obs2;
    }

    public Date getFechahoraGuardado() {
        return fechahoraGuardado;
    }

    public void setFechahoraGuardado(Date fechahoraGuardado) {
        this.fechahoraGuardado = fechahoraGuardado;
    }

    public Date getFechahoraValidacion() {
        return fechahoraValidacion;
    }

    public void setFechahoraValidacion(Date fechahoraValidacion) {
        this.fechahoraValidacion = fechahoraValidacion;
    }

    public Date getFechahoraEdicion() {
        return fechahoraEdicion;
    }

    public void setFechahoraEdicion(Date fechahoraEdicion) {
        this.fechahoraEdicion = fechahoraEdicion;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getInfo6() {
        return info6;
    }

    public void setInfo6(String info6) {
        this.info6 = info6;
    }

    public String getInfo7() {
        return info7;
    }

    public void setInfo7(String info7) {
        this.info7 = info7;
    }

    public CaptCabecera getCodCabPadre() {
        return codCabPadre;
    }

    public void setCodCabPadre(CaptCabecera codCabPadre) {
        this.codCabPadre = codCabPadre;
    }

    public CaptCargaControl getCodCarCon() {
        return codCarCon;
    }

    public void setCodCarCon(CaptCargaControl codCarCon) {
        this.codCarCon = codCarCon;
    }

    public MetFormulario getCodFormulario() {
        return codFormulario;
    }

    public void setCodFormulario(MetFormulario codFormulario) {
        this.codFormulario = codFormulario;
    }

    public MueMuestraDetalle getCodMuestra() {
        return codMuestra;
    }

    public void setCodMuestra(MueMuestraDetalle codMuestra) {
        this.codMuestra = codMuestra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCab != null ? idCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptCabecera)) {
            return false;
        }
        CaptCabecera other = (CaptCabecera) object;
        if ((this.idCab == null && other.idCab != null) || (this.idCab != null && !this.idCab.equals(other.idCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptCabecera[ idCab=" + idCab + " ]";
    }
    
}
