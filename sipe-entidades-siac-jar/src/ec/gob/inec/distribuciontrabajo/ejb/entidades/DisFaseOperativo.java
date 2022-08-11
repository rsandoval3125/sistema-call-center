/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.distribuciontrabajo.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.muestra.ejb.entidades.MueMuestra;
import ec.gob.inec.proceso.ejb.entidades.ProActividad;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
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
@Table(name = "distribucion.dis_fase_operativo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisFaseOperativo.findAll", query = "SELECT d FROM DisFaseOperativo d")})
public class DisFaseOperativo implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fas_ope")
    private Integer idFasOpe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private short orden;
    @Column(name = "asignacion_dispositivo")
    private Boolean asignacionDispositivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_personal")
    private int codigoPersonal;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "estado_reasignacion")
    private Boolean estadoReasignacion;
    @Column(name = "fase_requerida")
    private Boolean faseRequerida;
    @Column(name = "asignacion_conductor")
    private Boolean asignacionConductor;
    @Column(name = "orden_investigacion")
    private Boolean ordenInvestigacion;
    @Column(name = "asignacion_sectores_max")
    private Integer asignacionSectoresMax;
    @Column(name = "aplica_reversa")
    private Boolean aplicaReversa;
    @JoinColumn(name = "cod_asignacion_usuario", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codAsignacionUsuario;
    @JoinColumn(name = "cod_tipo_distribucion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoDistribucion;
    @JoinColumn(name = "cod_tipo_jornada", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoJornada;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipo;
    @JoinColumn(name = "cod_operativo", referencedColumnName = "id_operativo")
    @ManyToOne
    private MetOperativo codOperativo;
    @JoinColumn(name = "cod_muestra_trabajo", referencedColumnName = "id_muestra")
    @ManyToOne
    private MueMuestra codMuestraTrabajo;
    @JoinColumn(name = "cod_actividad", referencedColumnName = "id_actividad")
    @ManyToOne
    private ProActividad codActividad;
    @JoinColumn(name = "cod_rol", referencedColumnName = "id_rol")
    @ManyToOne
    private SegRol codRol;

    public DisFaseOperativo() {
    }

    public DisFaseOperativo(Integer idFasOpe) {
        this.idFasOpe = idFasOpe;
    }

    public DisFaseOperativo(Integer idFasOpe, short orden, int codigoPersonal, boolean estadoLogico) {
        this.idFasOpe = idFasOpe;
        this.orden = orden;
        this.codigoPersonal = codigoPersonal;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdFasOpe() {
        return idFasOpe;
    }

    public void setIdFasOpe(Integer idFasOpe) {
        this.idFasOpe = idFasOpe;
    }

    public short getOrden() {
        return orden;
    }

    public void setOrden(short orden) {
        this.orden = orden;
    }

    public Boolean getAsignacionDispositivo() {
        return asignacionDispositivo;
    }

    public void setAsignacionDispositivo(Boolean asignacionDispositivo) {
        this.asignacionDispositivo = asignacionDispositivo;
    }

    public int getCodigoPersonal() {
        return codigoPersonal;
    }

    public void setCodigoPersonal(int codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
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

    public Boolean getEstadoReasignacion() {
        return estadoReasignacion;
    }

    public void setEstadoReasignacion(Boolean estadoReasignacion) {
        this.estadoReasignacion = estadoReasignacion;
    }

    public Boolean getFaseRequerida() {
        return faseRequerida;
    }

    public void setFaseRequerida(Boolean faseRequerida) {
        this.faseRequerida = faseRequerida;
    }

    public Boolean getAsignacionConductor() {
        return asignacionConductor;
    }

    public void setAsignacionConductor(Boolean asignacionConductor) {
        this.asignacionConductor = asignacionConductor;
    }

    public Boolean getOrdenInvestigacion() {
        return ordenInvestigacion;
    }

    public void setOrdenInvestigacion(Boolean ordenInvestigacion) {
        this.ordenInvestigacion = ordenInvestigacion;
    }

    public Integer getAsignacionSectoresMax() {
        return asignacionSectoresMax;
    }

    public void setAsignacionSectoresMax(Integer asignacionSectoresMax) {
        this.asignacionSectoresMax = asignacionSectoresMax;
    }

    public Boolean getAplicaReversa() {
        return aplicaReversa;
    }

    public void setAplicaReversa(Boolean aplicaReversa) {
        this.aplicaReversa = aplicaReversa;
    }

    public MetCatalogo getCodAsignacionUsuario() {
        return codAsignacionUsuario;
    }

    public void setCodAsignacionUsuario(MetCatalogo codAsignacionUsuario) {
        this.codAsignacionUsuario = codAsignacionUsuario;
    }

    public MetCatalogo getCodTipoDistribucion() {
        return codTipoDistribucion;
    }

    public void setCodTipoDistribucion(MetCatalogo codTipoDistribucion) {
        this.codTipoDistribucion = codTipoDistribucion;
    }

    public MetCatalogo getCodTipoJornada() {
        return codTipoJornada;
    }

    public void setCodTipoJornada(MetCatalogo codTipoJornada) {
        this.codTipoJornada = codTipoJornada;
    }

    public MetCatalogo getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(MetCatalogo codTipo) {
        this.codTipo = codTipo;
    }

    public MetOperativo getCodOperativo() {
        return codOperativo;
    }

    public void setCodOperativo(MetOperativo codOperativo) {
        this.codOperativo = codOperativo;
    }

    public MueMuestra getCodMuestraTrabajo() {
        return codMuestraTrabajo;
    }

    public void setCodMuestraTrabajo(MueMuestra codMuestraTrabajo) {
        this.codMuestraTrabajo = codMuestraTrabajo;
    }

    public ProActividad getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(ProActividad codActividad) {
        this.codActividad = codActividad;
    }

    public SegRol getCodRol() {
        return codRol;
    }

    public void setCodRol(SegRol codRol) {
        this.codRol = codRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFasOpe != null ? idFasOpe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisFaseOperativo)) {
            return false;
        }
        DisFaseOperativo other = (DisFaseOperativo) object;
        if ((this.idFasOpe == null && other.idFasOpe != null) || (this.idFasOpe != null && !this.idFasOpe.equals(other.idFasOpe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativo[ idFasOpe=" + idFasOpe + " ]";
    }
    
}
