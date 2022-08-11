/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisCargaTrabajo;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "captura.capt_carga_control_equipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptCargaControlEquipo.findAll", query = "SELECT c FROM CaptCargaControlEquipo c")})
public class CaptCargaControlEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_car_con_equ")
    private Integer idCarConEqu;
    @Column(name = "estado_carg_cont")
    private Integer estadoCargCont;
    @Size(max = 5)
    @Column(name = "control")
    private String control;
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_car_con", referencedColumnName = "id_car_con")
    @ManyToOne
    private CaptCargaControl codCarCon;
    @JoinColumn(name = "cod_car_tra", referencedColumnName = "id_car_tra")
    @ManyToOne
    private DisCargaTrabajo codCarTra;
    @Transient
    private boolean estadoSegunRolyControl2;

    public CaptCargaControlEquipo() {
    }

    public CaptCargaControlEquipo(Integer idCarConEqu) {
        this.idCarConEqu = idCarConEqu;
    }

    public CaptCargaControlEquipo(Integer idCarConEqu, boolean estadoLogico) {
        this.idCarConEqu = idCarConEqu;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCarConEqu() {
        return idCarConEqu;
    }

    public void setIdCarConEqu(Integer idCarConEqu) {
        this.idCarConEqu = idCarConEqu;
    }

    public Integer getEstadoCargCont() {
        return estadoCargCont;
    }

    public void setEstadoCargCont(Integer estadoCargCont) {
        this.estadoCargCont = estadoCargCont;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public CaptCargaControl getCodCarCon() {
        return codCarCon;
    }

    public void setCodCarCon(CaptCargaControl codCarCon) {
        this.codCarCon = codCarCon;
    }

    public DisCargaTrabajo getCodCarTra() {
        return codCarTra;
    }

    public void setCodCarTra(DisCargaTrabajo codCarTra) {
        this.codCarTra = codCarTra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCarConEqu != null ? idCarConEqu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptCargaControlEquipo)) {
            return false;
        }
        CaptCargaControlEquipo other = (CaptCargaControlEquipo) object;
        if ((this.idCarConEqu == null && other.idCarConEqu != null) || (this.idCarConEqu != null && !this.idCarConEqu.equals(other.idCarConEqu))) {
            return false;
        }
        return true;
    }

    public boolean getEstadoSegunRolyControl2() {
        estadoSegunRolyControl2 = obtenerEstadoSegunRolyControl2();
        return estadoSegunRolyControl2;
    }

    public void setEstadoSegunRolyControl2(boolean estadoSegunRolyControl2) {
        this.estadoSegunRolyControl2 = estadoSegunRolyControl2;
    }

    private boolean obtenerEstadoSegunRolyControl2() {

        boolean e = false;
        if (codCarCon != null && codCarTra != null) {
            String control2 = codCarCon.getControl2() != null ? codCarCon.getControl2() : "";
            String aliasRol = codCarTra.getCodEquTraDet().getCodRolUsu().getCodRol().getAlias();

            boolean flag1 = false;
            if (control2 != null) {
                if (control2.equals("VAL" + aliasRol)) {
                    e = true;
                    flag1 = true;
                }

                if (!flag1) {
                    if (aliasRol.equals("DIG_PRE") && (control2.equals("VALSUP_CAM")
                            || control2.equals("VALREV_PRE")
                            || control2.equals("VALDIG_CAR")
                            || control2.equals("VALCON_CAL"))) {
                        e = true;
                    } else if (aliasRol.equals("SUP_CAM") && (control2.equals("VALREV_PRE")
                            || control2.equals("VALDIG_CAR")
                            || control2.equals("VALCON_CAL"))) {
                        e = true;
                    } else if (aliasRol.equals("REV_PRE") && (control2.equals("VALDIG_CAR")
                            || control2.equals("VALCON_CAL"))) {
                        e = true;
                    } else if (aliasRol.equals("DIG_CAR") && (control2.equals("VALCON_CAL"))) {
                        e = true;
                    }
                }
            }
        }
        return e;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo[ idCarConEqu=" + idCarConEqu + " ]";
    }

}
