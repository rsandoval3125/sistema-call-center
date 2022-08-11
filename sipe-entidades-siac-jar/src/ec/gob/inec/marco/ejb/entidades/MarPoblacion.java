/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.marco.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "marco.mar_poblacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarPoblacion.findAll", query = "SELECT m FROM MarPoblacion m")})
public class MarPoblacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_poblacion")
    private Integer idPoblacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "primer_nombre")
    private String primerNombre;
    @Size(max = 128)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Size(max = 128)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "fecha_defuncion")
    @Temporal(TemporalType.DATE)
    private Date fechaDefuncion;
    @JoinColumn(name = "cod_madre", referencedColumnName = "id_poblacion")
    @ManyToOne
    private MarPoblacion codMadre;
    @JoinColumn(name = "cod_padre", referencedColumnName = "id_poblacion")
    @ManyToOne
    private MarPoblacion codPadre;
    @JoinColumn(name = "cod_predio", referencedColumnName = "id_predio")
    @ManyToOne(optional = false)
    private MarPredio codPredio;
    @JoinColumn(name = "cod_genero", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codGenero;
    @JoinColumn(name = "cod_nivel_instruccion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codNivelInstruccion;
    @JoinColumn(name = "cod_sexo", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codSexo;

    public MarPoblacion() {
    }

    public MarPoblacion(Integer idPoblacion) {
        this.idPoblacion = idPoblacion;
    }

    public MarPoblacion(Integer idPoblacion, String identificacion, String primerNombre, String primerApellido, Date fechaNacimiento) {
        this.idPoblacion = idPoblacion;
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIdPoblacion() {
        return idPoblacion;
    }

    public void setIdPoblacion(Integer idPoblacion) {
        this.idPoblacion = idPoblacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(Date fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public MarPoblacion getCodMadre() {
        return codMadre;
    }

    public void setCodMadre(MarPoblacion codMadre) {
        this.codMadre = codMadre;
    }

    public MarPoblacion getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(MarPoblacion codPadre) {
        this.codPadre = codPadre;
    }

    public MarPredio getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(MarPredio codPredio) {
        this.codPredio = codPredio;
    }

    public MetCatalogo getCodGenero() {
        return codGenero;
    }

    public void setCodGenero(MetCatalogo codGenero) {
        this.codGenero = codGenero;
    }

    public MetCatalogo getCodNivelInstruccion() {
        return codNivelInstruccion;
    }

    public void setCodNivelInstruccion(MetCatalogo codNivelInstruccion) {
        this.codNivelInstruccion = codNivelInstruccion;
    }

    public MetCatalogo getCodSexo() {
        return codSexo;
    }

    public void setCodSexo(MetCatalogo codSexo) {
        this.codSexo = codSexo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPoblacion != null ? idPoblacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPoblacion)) {
            return false;
        }
        MarPoblacion other = (MarPoblacion) object;
        if ((this.idPoblacion == null && other.idPoblacion != null) || (this.idPoblacion != null && !this.idPoblacion.equals(other.idPoblacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.marco.ejb.entidades.MarPoblacion[ idPoblacion=" + idPoblacion + " ]";
    }
    
}
