/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.entidades;

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
@Table(name = "metadato.met_variable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetVariable.findAll", query = "SELECT m FROM MetVariable m")})
public class MetVariable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_var")
    private Integer idVar;
    @Size(max = 20)
    @Column(name = "identificador")
    private String identificador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;
    @Size(max = 3)
    @Column(name = "tipo_comp")
    private String tipoComp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pregunta")
    private String pregunta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "muestra_descripcion")
    private boolean muestraDescripcion;
    @Size(max = 1)
    @Column(name = "ubi_descripcion")
    private String ubiDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tooltip")
    private String tooltip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fila")
    private int fila;
    @Basic(optional = false)
    @NotNull
    @Column(name = "columna")
    private int columna;
    @Column(name = "colspan")
    private Integer colspan;
    @Column(name = "rowspan")
    private Integer rowspan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estatico")
    private int estatico;
    @Size(max = 200)
    @Column(name = "style_comp")
    private String styleComp;
    @Size(max = 200)
    @Column(name = "style_label")
    private String styleLabel;
    @Size(max = 10)
    @Column(name = "styleclass_comp")
    private String styleclassComp;
    @Size(max = 10)
    @Column(name = "styleclass_label")
    private String styleclassLabel;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Size(max = 2147483647)
    @Column(name = "js")
    private String js;
    @JoinColumn(name = "cod_concepto", referencedColumnName = "id_concepto")
    @ManyToOne(optional = false)
    private MetConcepto codConcepto;
    @JoinColumn(name = "cod_seccion", referencedColumnName = "id_seccion")
    @ManyToOne(optional = false)
    private MetSeccion codSeccion;

    public MetVariable() {
    }

    public MetVariable(Integer idVar) {
        this.idVar = idVar;
    }

    public MetVariable(Integer idVar, int orden, String pregunta, String descripcion, boolean muestraDescripcion, String tooltip, int fila, int columna, int estatico, boolean estadoLogico) {
        this.idVar = idVar;
        this.orden = orden;
        this.pregunta = pregunta;
        this.descripcion = descripcion;
        this.muestraDescripcion = muestraDescripcion;
        this.tooltip = tooltip;
        this.fila = fila;
        this.columna = columna;
        this.estatico = estatico;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdVar() {
        return idVar;
    }

    public void setIdVar(Integer idVar) {
        this.idVar = idVar;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getTipoComp() {
        return tipoComp;
    }

    public void setTipoComp(String tipoComp) {
        this.tipoComp = tipoComp;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getMuestraDescripcion() {
        return muestraDescripcion;
    }

    public void setMuestraDescripcion(boolean muestraDescripcion) {
        this.muestraDescripcion = muestraDescripcion;
    }

    public String getUbiDescripcion() {
        return ubiDescripcion;
    }

    public void setUbiDescripcion(String ubiDescripcion) {
        this.ubiDescripcion = ubiDescripcion;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }

    public Integer getRowspan() {
        return rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    public int getEstatico() {
        return estatico;
    }

    public void setEstatico(int estatico) {
        this.estatico = estatico;
    }

    public String getStyleComp() {
        return styleComp;
    }

    public void setStyleComp(String styleComp) {
        this.styleComp = styleComp;
    }

    public String getStyleLabel() {
        return styleLabel;
    }

    public void setStyleLabel(String styleLabel) {
        this.styleLabel = styleLabel;
    }

    public String getStyleclassComp() {
        return styleclassComp;
    }

    public void setStyleclassComp(String styleclassComp) {
        this.styleclassComp = styleclassComp;
    }

    public String getStyleclassLabel() {
        return styleclassLabel;
    }

    public void setStyleclassLabel(String styleclassLabel) {
        this.styleclassLabel = styleclassLabel;
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

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public MetConcepto getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(MetConcepto codConcepto) {
        this.codConcepto = codConcepto;
    }

    public MetSeccion getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(MetSeccion codSeccion) {
        this.codSeccion = codSeccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVar != null ? idVar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetVariable)) {
            return false;
        }
        MetVariable other = (MetVariable) object;
        if ((this.idVar == null && other.idVar != null) || (this.idVar != null && !this.idVar.equals(other.idVar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.metadato.ejb.entidades.MetVariable[ idVar=" + idVar + " ]";
    }
    
}
