/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.marco.ejb.entidades;

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
@Table(name = "marco.mar_vivienda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarVivienda.findAll", query = "SELECT m FROM MarVivienda m")})
public class MarVivienda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vivienda")
    private Integer idVivienda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "codificacion")
    private String codificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
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
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 50)
    @Column(name = "telefono_local")
    private String telefonoLocal;
    @Size(max = 50)
    @Column(name = "telefono_celular")
    private String telefonoCelular;
    @Size(max = 128)
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "codigo_municipal")
    private String codigoMunicipal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "piso")
    private String piso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "departamento")
    private String departamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "bloque")
    private String bloque;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;
    @JoinColumn(name = "codigo_predio", referencedColumnName = "id_predio")
    @ManyToOne(optional = false)
    private MarPredio codigoPredio;
    @JoinColumn(name = "codigo_actividad_economica", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codigoActividadEconomica;
    @JoinColumn(name = "codigo_estado_vivienda", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codigoEstadoVivienda;

    public MarVivienda() {
    }

    public MarVivienda(Integer idVivienda) {
        this.idVivienda = idVivienda;
    }

    public MarVivienda(Integer idVivienda, String codificacion, String descripcion, String primerNombre, String primerApellido, String identificacion, String codigoMunicipal, String piso, String departamento, String bloque, short estado) {
        this.idVivienda = idVivienda;
        this.codificacion = codificacion;
        this.descripcion = descripcion;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.identificacion = identificacion;
        this.codigoMunicipal = codigoMunicipal;
        this.piso = piso;
        this.departamento = departamento;
        this.bloque = bloque;
        this.estado = estado;
    }

    public Integer getIdVivienda() {
        return idVivienda;
    }

    public void setIdVivienda(Integer idVivienda) {
        this.idVivienda = idVivienda;
    }

    public String getCodificacion() {
        return codificacion;
    }

    public void setCodificacion(String codificacion) {
        this.codificacion = codificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefonoLocal() {
        return telefonoLocal;
    }

    public void setTelefonoLocal(String telefonoLocal) {
        this.telefonoLocal = telefonoLocal;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCodigoMunicipal() {
        return codigoMunicipal;
    }

    public void setCodigoMunicipal(String codigoMunicipal) {
        this.codigoMunicipal = codigoMunicipal;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public MarPredio getCodigoPredio() {
        return codigoPredio;
    }

    public void setCodigoPredio(MarPredio codigoPredio) {
        this.codigoPredio = codigoPredio;
    }

    public MetCatalogo getCodigoActividadEconomica() {
        return codigoActividadEconomica;
    }

    public void setCodigoActividadEconomica(MetCatalogo codigoActividadEconomica) {
        this.codigoActividadEconomica = codigoActividadEconomica;
    }

    public MetCatalogo getCodigoEstadoVivienda() {
        return codigoEstadoVivienda;
    }

    public void setCodigoEstadoVivienda(MetCatalogo codigoEstadoVivienda) {
        this.codigoEstadoVivienda = codigoEstadoVivienda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVivienda != null ? idVivienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarVivienda)) {
            return false;
        }
        MarVivienda other = (MarVivienda) object;
        if ((this.idVivienda == null && other.idVivienda != null) || (this.idVivienda != null && !this.idVivienda.equals(other.idVivienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.marco.ejb.entidades.MarVivienda[ idVivienda=" + idVivienda + " ]";
    }
    
}
