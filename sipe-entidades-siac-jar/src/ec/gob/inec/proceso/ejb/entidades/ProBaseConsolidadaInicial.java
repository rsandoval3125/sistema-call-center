/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
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
@Table(name = "proceso.pro_base_consolidada_inicial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProBaseConsolidadaInicial.findAll", query = "SELECT p FROM ProBaseConsolidadaInicial p")})
public class ProBaseConsolidadaInicial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_base_con_inicial")
    private Integer idBaseConInicial;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_estructura", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codEstructura;
    @JoinColumn(name = "cod_fuenete_financiamiento", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codFueneteFinanciamiento;
    @JoinColumn(name = "cod_anio", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codAnio;
    @JoinColumn(name = "cod_partida", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codPartida;
    @JoinColumn(name = "cod_zonal", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codZonal;

    public ProBaseConsolidadaInicial() {
    }

    public ProBaseConsolidadaInicial(Integer idBaseConInicial) {
        this.idBaseConInicial = idBaseConInicial;
    }

    public ProBaseConsolidadaInicial(Integer idBaseConInicial, boolean estadoLogico) {
        this.idBaseConInicial = idBaseConInicial;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdBaseConInicial() {
        return idBaseConInicial;
    }

    public void setIdBaseConInicial(Integer idBaseConInicial) {
        this.idBaseConInicial = idBaseConInicial;
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

    public MetCatalogo getCodEstructura() {
        return codEstructura;
    }

    public void setCodEstructura(MetCatalogo codEstructura) {
        this.codEstructura = codEstructura;
    }

    public MetCatalogo getCodFueneteFinanciamiento() {
        return codFueneteFinanciamiento;
    }

    public void setCodFueneteFinanciamiento(MetCatalogo codFueneteFinanciamiento) {
        this.codFueneteFinanciamiento = codFueneteFinanciamiento;
    }

    public MetCatalogo getCodAnio() {
        return codAnio;
    }

    public void setCodAnio(MetCatalogo codAnio) {
        this.codAnio = codAnio;
    }

    public MetCatalogo getCodPartida() {
        return codPartida;
    }

    public void setCodPartida(MetCatalogo codPartida) {
        this.codPartida = codPartida;
    }

    public MetCatalogo getCodZonal() {
        return codZonal;
    }

    public void setCodZonal(MetCatalogo codZonal) {
        this.codZonal = codZonal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBaseConInicial != null ? idBaseConInicial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProBaseConsolidadaInicial)) {
            return false;
        }
        ProBaseConsolidadaInicial other = (ProBaseConsolidadaInicial) object;
        if ((this.idBaseConInicial == null && other.idBaseConInicial != null) || (this.idBaseConInicial != null && !this.idBaseConInicial.equals(other.idBaseConInicial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProBaseConsolidadaInicial[ idBaseConInicial=" + idBaseConInicial + " ]";
    }
    
}
