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
@Table(name = "proceso.pro_valor_texto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProValorTexto.findAll", query = "SELECT p FROM ProValorTexto p")})
public class ProValorTexto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_valor_texto")
    private Integer idValorTexto;
    @Size(max = 500)
    @Column(name = "texto")
    private String texto;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "numero_columna")
    private Integer numeroColumna;
    @JoinColumn(name = "cod_numero_ejercicio", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codNumeroEjercicio;
    @JoinColumn(name = "cod_base_con_inicial", referencedColumnName = "id_base_con_inicial")
    @ManyToOne
    private ProBaseConsolidadaInicial codBaseConInicial;
    @JoinColumn(name = "cod_cedula_dipla", referencedColumnName = "id_cedula_dipla")
    @ManyToOne
    private ProCedulaDipla codCedulaDipla;
    @JoinColumn(name = "cod_nombre_columna", referencedColumnName = "id_nombre_columna")
    @ManyToOne
    private ProNombreColumna codNombreColumna;

    public ProValorTexto() {
    }

    public ProValorTexto(Integer idValorTexto) {
        this.idValorTexto = idValorTexto;
    }

    public ProValorTexto(Integer idValorTexto, boolean estadoLogico) {
        this.idValorTexto = idValorTexto;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdValorTexto() {
        return idValorTexto;
    }

    public void setIdValorTexto(Integer idValorTexto) {
        this.idValorTexto = idValorTexto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    public Integer getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(Integer numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public MetCatalogo getCodNumeroEjercicio() {
        return codNumeroEjercicio;
    }

    public void setCodNumeroEjercicio(MetCatalogo codNumeroEjercicio) {
        this.codNumeroEjercicio = codNumeroEjercicio;
    }

    public ProBaseConsolidadaInicial getCodBaseConInicial() {
        return codBaseConInicial;
    }

    public void setCodBaseConInicial(ProBaseConsolidadaInicial codBaseConInicial) {
        this.codBaseConInicial = codBaseConInicial;
    }

    public ProCedulaDipla getCodCedulaDipla() {
        return codCedulaDipla;
    }

    public void setCodCedulaDipla(ProCedulaDipla codCedulaDipla) {
        this.codCedulaDipla = codCedulaDipla;
    }

    public ProNombreColumna getCodNombreColumna() {
        return codNombreColumna;
    }

    public void setCodNombreColumna(ProNombreColumna codNombreColumna) {
        this.codNombreColumna = codNombreColumna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValorTexto != null ? idValorTexto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProValorTexto)) {
            return false;
        }
        ProValorTexto other = (ProValorTexto) object;
        if ((this.idValorTexto == null && other.idValorTexto != null) || (this.idValorTexto != null && !this.idValorTexto.equals(other.idValorTexto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.proceso.ejb.entidades.ProValorTexto[ idValorTexto=" + idValorTexto + " ]";
    }
    
}
