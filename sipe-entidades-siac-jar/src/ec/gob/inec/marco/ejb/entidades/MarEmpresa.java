/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.marco.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "marco.mar_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarEmpresa.findAll", query = "SELECT m FROM MarEmpresa m")})
public class MarEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empresa")
    private Integer idEmpresa;
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
    @Size(min = 1, max = 20)
    @Column(name = "identificador")
    private String identificador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "primer_nombre_gerente")
    private String primerNombreGerente;
    @Size(max = 128)
    @Column(name = "segundo_nombre_gerente")
    private String segundoNombreGerente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "primer_apellido_gerente")
    private String primerApellidoGerente;
    @Size(max = 128)
    @Column(name = "segundo_apellido_gerente")
    private String segundoApellidoGerente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "primer_nombre_informante")
    private String primerNombreInformante;
    @Size(max = 128)
    @Column(name = "segundo_nombre_informante")
    private String segundoNombreInformante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "primer_apellido_informante")
    private String primerApellidoInformante;
    @Size(max = 128)
    @Column(name = "segundo_apellido_informante")
    private String segundoApellidoInformante;
    @Size(max = 2147483647)
    @Column(name = "descripcion_actividad_economica")
    private String descripcionActividadEconomica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion_gerente")
    private String identificacionGerente;
    @Size(max = 20)
    @Column(name = "identificacion_informante")
    private String identificacionInformante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "telefono_empresa")
    private String telefonoEmpresa;
    @Size(max = 255)
    @Column(name = "email_empresa")
    private String emailEmpresa;
    @Size(max = 255)
    @Column(name = "web_empresa")
    private String webEmpresa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ventas")
    private BigDecimal ventas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_empleado")
    private long numeroEmpleado;
    @JoinColumn(name = "cod_predio", referencedColumnName = "id_predio")
    @ManyToOne(optional = false)
    private MarPredio codPredio;
    @JoinColumn(name = "cod_actividad_economica", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codActividadEconomica;
    @JoinColumn(name = "cod_tamano", referencedColumnName = "id_catalogo")
    @ManyToOne(optional = false)
    private MetCatalogo codTamano;

    public MarEmpresa() {
    }

    public MarEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public MarEmpresa(Integer idEmpresa, String codificacion, String descripcion, String identificador, String primerNombreGerente, String primerApellidoGerente, String primerNombreInformante, String primerApellidoInformante, String identificacionGerente, String telefonoEmpresa, BigDecimal ventas, long numeroEmpleado) {
        this.idEmpresa = idEmpresa;
        this.codificacion = codificacion;
        this.descripcion = descripcion;
        this.identificador = identificador;
        this.primerNombreGerente = primerNombreGerente;
        this.primerApellidoGerente = primerApellidoGerente;
        this.primerNombreInformante = primerNombreInformante;
        this.primerApellidoInformante = primerApellidoInformante;
        this.identificacionGerente = identificacionGerente;
        this.telefonoEmpresa = telefonoEmpresa;
        this.ventas = ventas;
        this.numeroEmpleado = numeroEmpleado;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getPrimerNombreGerente() {
        return primerNombreGerente;
    }

    public void setPrimerNombreGerente(String primerNombreGerente) {
        this.primerNombreGerente = primerNombreGerente;
    }

    public String getSegundoNombreGerente() {
        return segundoNombreGerente;
    }

    public void setSegundoNombreGerente(String segundoNombreGerente) {
        this.segundoNombreGerente = segundoNombreGerente;
    }

    public String getPrimerApellidoGerente() {
        return primerApellidoGerente;
    }

    public void setPrimerApellidoGerente(String primerApellidoGerente) {
        this.primerApellidoGerente = primerApellidoGerente;
    }

    public String getSegundoApellidoGerente() {
        return segundoApellidoGerente;
    }

    public void setSegundoApellidoGerente(String segundoApellidoGerente) {
        this.segundoApellidoGerente = segundoApellidoGerente;
    }

    public String getPrimerNombreInformante() {
        return primerNombreInformante;
    }

    public void setPrimerNombreInformante(String primerNombreInformante) {
        this.primerNombreInformante = primerNombreInformante;
    }

    public String getSegundoNombreInformante() {
        return segundoNombreInformante;
    }

    public void setSegundoNombreInformante(String segundoNombreInformante) {
        this.segundoNombreInformante = segundoNombreInformante;
    }

    public String getPrimerApellidoInformante() {
        return primerApellidoInformante;
    }

    public void setPrimerApellidoInformante(String primerApellidoInformante) {
        this.primerApellidoInformante = primerApellidoInformante;
    }

    public String getSegundoApellidoInformante() {
        return segundoApellidoInformante;
    }

    public void setSegundoApellidoInformante(String segundoApellidoInformante) {
        this.segundoApellidoInformante = segundoApellidoInformante;
    }

    public String getDescripcionActividadEconomica() {
        return descripcionActividadEconomica;
    }

    public void setDescripcionActividadEconomica(String descripcionActividadEconomica) {
        this.descripcionActividadEconomica = descripcionActividadEconomica;
    }

    public String getIdentificacionGerente() {
        return identificacionGerente;
    }

    public void setIdentificacionGerente(String identificacionGerente) {
        this.identificacionGerente = identificacionGerente;
    }

    public String getIdentificacionInformante() {
        return identificacionInformante;
    }

    public void setIdentificacionInformante(String identificacionInformante) {
        this.identificacionInformante = identificacionInformante;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getWebEmpresa() {
        return webEmpresa;
    }

    public void setWebEmpresa(String webEmpresa) {
        this.webEmpresa = webEmpresa;
    }

    public BigDecimal getVentas() {
        return ventas;
    }

    public void setVentas(BigDecimal ventas) {
        this.ventas = ventas;
    }

    public long getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public MarPredio getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(MarPredio codPredio) {
        this.codPredio = codPredio;
    }

    public MetCatalogo getCodActividadEconomica() {
        return codActividadEconomica;
    }

    public void setCodActividadEconomica(MetCatalogo codActividadEconomica) {
        this.codActividadEconomica = codActividadEconomica;
    }

    public MetCatalogo getCodTamano() {
        return codTamano;
    }

    public void setCodTamano(MetCatalogo codTamano) {
        this.codTamano = codTamano;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarEmpresa)) {
            return false;
        }
        MarEmpresa other = (MarEmpresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.marco.ejb.entidades.MarEmpresa[ idEmpresa=" + idEmpresa + " ]";
    }
    
}
