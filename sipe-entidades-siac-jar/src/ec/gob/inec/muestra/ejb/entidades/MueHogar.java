/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "muestra.mue_hogar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueHogar.findAll", query = "SELECT m FROM MueHogar m")})
public class MueHogar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_hogar")
    private Integer idHogar;
    @Size(max = 100)
    @Column(name = "identificador_hog")
    private String identificadorHog;
    @Column(name = "cod_hogar")
    private Integer codHogar;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 50)
    @Column(name = "cod_mue_ref")
    private String codMueRef;
    @Column(name = "num_personas")
    private Integer numPersonas;
    @JoinColumn(name = "cod_cat_jerarquia", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codCatJerarquia;
    @JoinColumn(name = "cod_persona", referencedColumnName = "id_persona")
    @ManyToOne
    private MuePersona codPersona;
    @JoinColumn(name = "cod_vivienda", referencedColumnName = "id_vivienda")
    @ManyToOne
    private MueVivienda codVivienda;

    public MueHogar() {
    }

    public MueHogar(Integer idHogar) {
        this.idHogar = idHogar;
    }

    public Integer getIdHogar() {
        return idHogar;
    }

    public void setIdHogar(Integer idHogar) {
        this.idHogar = idHogar;
    }

    public String getIdentificadorHog() {
        return identificadorHog;
    }

    public void setIdentificadorHog(String identificadorHog) {
        this.identificadorHog = identificadorHog;
    }

    public Integer getCodHogar() {
        return codHogar;
    }

    public void setCodHogar(Integer codHogar) {
        this.codHogar = codHogar;
    }

    public String getCodAuditoriaCab() {
        return codAuditoriaCab;
    }

    public void setCodAuditoriaCab(String codAuditoriaCab) {
        this.codAuditoriaCab = codAuditoriaCab;
    }

    public Boolean getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(Boolean estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public String getCodMueRef() {
        return codMueRef;
    }

    public void setCodMueRef(String codMueRef) {
        this.codMueRef = codMueRef;
    }

    public Integer getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(Integer numPersonas) {
        this.numPersonas = numPersonas;
    }

    public MetCatalogo getCodCatJerarquia() {
        return codCatJerarquia;
    }

    public void setCodCatJerarquia(MetCatalogo codCatJerarquia) {
        this.codCatJerarquia = codCatJerarquia;
    }

    public MuePersona getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(MuePersona codPersona) {
        this.codPersona = codPersona;
    }

    public MueVivienda getCodVivienda() {
        return codVivienda;
    }

    public void setCodVivienda(MueVivienda codVivienda) {
        this.codVivienda = codVivienda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHogar != null ? idHogar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueHogar)) {
            return false;
        }
        MueHogar other = (MueHogar) object;
        if ((this.idHogar == null && other.idHogar != null) || (this.idHogar != null && !this.idHogar.equals(other.idHogar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueHogar[ idHogar=" + idHogar + " ]";
    }
    
}
