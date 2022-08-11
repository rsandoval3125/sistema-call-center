/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "seguridad.seg_control_texto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegControlTexto.findAll", query = "SELECT s FROM SegControlTexto s")})
public class SegControlTexto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_con_txt")
    private Integer idConTxt;
    @Column(name = "componente")
    private String componente;
    @Basic(optional = false)
    @Column(name = "texto")
    private String texto;
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @JoinColumn(name = "cod_idioma", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codIdioma;
    @JoinColumn(name = "cod_pag", referencedColumnName = "id_pag")
    @ManyToOne(optional = false)
    private SegPagina codPag;

    public SegControlTexto() {
    }

    public SegControlTexto(Integer idConTxt) {
        this.idConTxt = idConTxt;
    }

    public SegControlTexto(Integer idConTxt, String texto) {
        this.idConTxt = idConTxt;
        this.texto = texto;
    }

    public Integer getIdConTxt() {
        return idConTxt;
    }

    public void setIdConTxt(Integer idConTxt) {
        this.idConTxt = idConTxt;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public MetCatalogo getCodIdioma() {
        return codIdioma;
    }

    public void setCodIdioma(MetCatalogo codIdioma) {
        this.codIdioma = codIdioma;
    }

    public SegPagina getCodPag() {
        return codPag;
    }

    public void setCodPag(SegPagina codPag) {
        this.codPag = codPag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConTxt != null ? idConTxt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegControlTexto)) {
            return false;
        }
        SegControlTexto other = (SegControlTexto) object;
        if ((this.idConTxt == null && other.idConTxt != null) || (this.idConTxt != null && !this.idConTxt.equals(other.idConTxt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegControlTexto[ idConTxt=" + idConTxt + " ]";
    }
    
}
