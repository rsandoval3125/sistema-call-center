/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.entidades;

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
@Table(name = "reportes.repo_subreporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepoSubreporte.findAll", query = "SELECT r FROM RepoSubreporte r")})
public class RepoSubreporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subr")
    private Integer idSubr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "filtro")
    private String filtro;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 10)
    @Column(name = "ancho")
    private String ancho;
    @Column(name = "scroll")
    private Character scroll;
    @Size(max = 10)
    @Column(name = "swidth")
    private String swidth;
    @Column(name = "paginador")
    private Character paginador;
    @Size(max = 200)
    @Column(name = "fila_estilo")
    private String filaEstilo;
    @Column(name = "exportar")
    private Character exportar;
    @Size(max = 1000)
    @Column(name = "subtitulo")
    private String subtitulo;
    @JoinColumn(name = "cod_proc", referencedColumnName = "id_proc")
    @ManyToOne(optional = false)
    private RepoProcedimiento codProc;
    @JoinColumn(name = "cod_reporte", referencedColumnName = "id_reporte")
    @ManyToOne(optional = false)
    private RepoReporte codReporte;

    public RepoSubreporte() {
    }

    public RepoSubreporte(Integer idSubr) {
        this.idSubr = idSubr;
    }

    public RepoSubreporte(Integer idSubr, String nombre) {
        this.idSubr = idSubr;
        this.nombre = nombre;
    }

    public Integer getIdSubr() {
        return idSubr;
    }

    public void setIdSubr(Integer idSubr) {
        this.idSubr = idSubr;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    public Character getScroll() {
        return scroll;
    }

    public void setScroll(Character scroll) {
        this.scroll = scroll;
    }

    public String getSwidth() {
        return swidth;
    }

    public void setSwidth(String swidth) {
        this.swidth = swidth;
    }

    public Character getPaginador() {
        return paginador;
    }

    public void setPaginador(Character paginador) {
        this.paginador = paginador;
    }

    public String getFilaEstilo() {
        return filaEstilo;
    }

    public void setFilaEstilo(String filaEstilo) {
        this.filaEstilo = filaEstilo;
    }

    public Character getExportar() {
        return exportar;
    }

    public void setExportar(Character exportar) {
        this.exportar = exportar;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public RepoProcedimiento getCodProc() {
        return codProc;
    }

    public void setCodProc(RepoProcedimiento codProc) {
        this.codProc = codProc;
    }

    public RepoReporte getCodReporte() {
        return codReporte;
    }

    public void setCodReporte(RepoReporte codReporte) {
        this.codReporte = codReporte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubr != null ? idSubr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepoSubreporte)) {
            return false;
        }
        RepoSubreporte other = (RepoSubreporte) object;
        if ((this.idSubr == null && other.idSubr != null) || (this.idSubr != null && !this.idSubr.equals(other.idSubr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.reportes.ejb.entidades.RepoSubreporte[ idSubr=" + idSubr + " ]";
    }
    
}
