/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.entidades;

import ec.gob.inec.administracion.ejb.entidades.AdmDispositivo;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
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
@Table(name = "distribucion.dis_equipo_trabajo_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisEquipoTrabajoDetalle.findAll", query = "SELECT d FROM DisEquipoTrabajoDetalle d")})
public class DisEquipoTrabajoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equ_tra_det")
    private Integer idEquTraDet;
    @Size(max = 2)
    @Column(name = "zonal")
    private String zonal;
    @Size(max = 2)
    @Column(name = "per_inves")
    private String perInves;
    @Size(max = 3)
    @Column(name = "num_e")
    private String numE;
    @Size(max = 3)
    @Column(name = "num_m")
    private String numM;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_dispositivo", referencedColumnName = "id_dispositivo")
    @ManyToOne
    private AdmDispositivo codDispositivo;
    @JoinColumn(name = "cod_equ_tra_cab", referencedColumnName = "id_equ_tra")
    @ManyToOne
    private DisEquipoTrabajo codEquTraCab;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_equ_tra_det")
    @ManyToOne
    private DisEquipoTrabajoDetalle codPadre;
    @JoinColumn(name = "cod_fas_ope", referencedColumnName = "id_fas_ope")
    @ManyToOne
    private DisFaseOperativo codFasOpe;
    @JoinColumn(name = "cod_rol_usu", referencedColumnName = "id_rol_usu")
    @ManyToOne
    private SegRolUsuario codRolUsu;
    @JoinColumn(name = "cod_rol_cond", referencedColumnName = "id_rol_usu")
    @ManyToOne
    private SegRolUsuario codRolCond;

    public DisEquipoTrabajoDetalle() {
    }

    public DisEquipoTrabajoDetalle(Integer idEquTraDet) {
        this.idEquTraDet = idEquTraDet;
    }

    public DisEquipoTrabajoDetalle(Integer idEquTraDet, boolean estadoLogico) {
        this.idEquTraDet = idEquTraDet;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdEquTraDet() {
        return idEquTraDet;
    }

    public void setIdEquTraDet(Integer idEquTraDet) {
        this.idEquTraDet = idEquTraDet;
    }

    public String getZonal() {
        return zonal;
    }

    public void setZonal(String zonal) {
        this.zonal = zonal;
    }

    public String getPerInves() {
        return perInves;
    }

    public void setPerInves(String perInves) {
        this.perInves = perInves;
    }

    public String getNumE() {
        return numE;
    }

    public void setNumE(String numE) {
        this.numE = numE;
    }

    public String getNumM() {
        return numM;
    }

    public void setNumM(String numM) {
        this.numM = numM;
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

    public AdmDispositivo getCodDispositivo() {
        return codDispositivo;
    }

    public void setCodDispositivo(AdmDispositivo codDispositivo) {
        this.codDispositivo = codDispositivo;
    }

    public DisEquipoTrabajo getCodEquTraCab() {
        return codEquTraCab;
    }

    public void setCodEquTraCab(DisEquipoTrabajo codEquTraCab) {
        this.codEquTraCab = codEquTraCab;
    }

    public DisEquipoTrabajoDetalle getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(DisEquipoTrabajoDetalle codPadre) {
        this.codPadre = codPadre;
    }

    public DisFaseOperativo getCodFasOpe() {
        return codFasOpe;
    }

    public void setCodFasOpe(DisFaseOperativo codFasOpe) {
        this.codFasOpe = codFasOpe;
    }

    public SegRolUsuario getCodRolUsu() {
        return codRolUsu;
    }

    public void setCodRolUsu(SegRolUsuario codRolUsu) {
        this.codRolUsu = codRolUsu;
    }

    public SegRolUsuario getCodRolCond() {
        return codRolCond;
    }

    public void setCodRolCond(SegRolUsuario codRolCond) {
        this.codRolCond = codRolCond;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquTraDet != null ? idEquTraDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisEquipoTrabajoDetalle)) {
            return false;
        }
        DisEquipoTrabajoDetalle other = (DisEquipoTrabajoDetalle) object;
        if ((this.idEquTraDet == null && other.idEquTraDet != null) || (this.idEquTraDet != null && !this.idEquTraDet.equals(other.idEquTraDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajoDetalle[ idEquTraDet=" + idEquTraDet + " ]";
    }
    
}
