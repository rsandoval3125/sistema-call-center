/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.muestra.ejb.entidades.MueMuestraDetalle;
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
@Table(name = "distribucion.dis_carga_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisCargaTrabajo.findAll", query = "SELECT d FROM DisCargaTrabajo d")})
public class DisCargaTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_car_tra")
    private Integer idCarTra;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "estado_ctrl_export")
    private Boolean estadoCtrlExport;
    @Column(name = "orden_investigacion")
    private Integer ordenInvestigacion;
    @Size(max = 2147483647)
    @Column(name = "ruta")
    private String ruta;
    @JoinColumn(name = "cod_jornada", referencedColumnName = "id_periodo")
    @ManyToOne
    private AdmPeriodo codJornada;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_car_tra")
    @ManyToOne
    private DisCargaTrabajo codPadre;
    @JoinColumn(name = "cod_equ_tra_det_asig", referencedColumnName = "id_equ_tra_det")
    @ManyToOne
    private DisEquipoTrabajoDetalle codEquTraDetAsig;
    @JoinColumn(name = "cod_equ_tra_det", referencedColumnName = "id_equ_tra_det")
    @ManyToOne
    private DisEquipoTrabajoDetalle codEquTraDet;
    @JoinColumn(name = "tipo_operacion_sector_disp", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo tipoOperacionSectorDisp;
    @JoinColumn(name = "estado_asignacion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo estadoAsignacion;
    @JoinColumn(name = "cod_mue_det", referencedColumnName = "id_mue_det")
    @ManyToOne
    private MueMuestraDetalle codMueDet;

    public DisCargaTrabajo() {
    }

    public DisCargaTrabajo(Integer idCarTra) {
        this.idCarTra = idCarTra;
    }

    public DisCargaTrabajo(Integer idCarTra, boolean estadoLogico) {
        this.idCarTra = idCarTra;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdCarTra() {
        return idCarTra;
    }

    public void setIdCarTra(Integer idCarTra) {
        this.idCarTra = idCarTra;
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

    public Boolean getEstadoCtrlExport() {
        return estadoCtrlExport;
    }

    public void setEstadoCtrlExport(Boolean estadoCtrlExport) {
        this.estadoCtrlExport = estadoCtrlExport;
    }

    public Integer getOrdenInvestigacion() {
        return ordenInvestigacion;
    }

    public void setOrdenInvestigacion(Integer ordenInvestigacion) {
        this.ordenInvestigacion = ordenInvestigacion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public AdmPeriodo getCodJornada() {
        return codJornada;
    }

    public void setCodJornada(AdmPeriodo codJornada) {
        this.codJornada = codJornada;
    }

    public DisCargaTrabajo getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(DisCargaTrabajo codPadre) {
        this.codPadre = codPadre;
    }

    public DisEquipoTrabajoDetalle getCodEquTraDetAsig() {
        return codEquTraDetAsig;
    }

    public void setCodEquTraDetAsig(DisEquipoTrabajoDetalle codEquTraDetAsig) {
        this.codEquTraDetAsig = codEquTraDetAsig;
    }

    public DisEquipoTrabajoDetalle getCodEquTraDet() {
        return codEquTraDet;
    }

    public void setCodEquTraDet(DisEquipoTrabajoDetalle codEquTraDet) {
        this.codEquTraDet = codEquTraDet;
    }

    public MetCatalogo getTipoOperacionSectorDisp() {
        return tipoOperacionSectorDisp;
    }

    public void setTipoOperacionSectorDisp(MetCatalogo tipoOperacionSectorDisp) {
        this.tipoOperacionSectorDisp = tipoOperacionSectorDisp;
    }

    public MetCatalogo getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(MetCatalogo estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
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
        hash += (idCarTra != null ? idCarTra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisCargaTrabajo)) {
            return false;
        }
        DisCargaTrabajo other = (DisCargaTrabajo) object;
        if ((this.idCarTra == null && other.idCarTra != null) || (this.idCarTra != null && !this.idCarTra.equals(other.idCarTra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.distribuciontrabajo.ejb.entidades.DisCargaTrabajo[ idCarTra=" + idCarTra + " ]";
    }
    
}
