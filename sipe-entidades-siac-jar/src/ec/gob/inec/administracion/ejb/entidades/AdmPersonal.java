/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.entidades;

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
@Table(name = "administracion.adm_personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdmPersonal.findAll", query = "SELECT a FROM AdmPersonal a")})
public class AdmPersonal implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_personal")
    private Integer idPersonal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "primer_nombre")
    private String primerNombre;
    @Size(max = 20)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Size(max = 20)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Size(max = 120)
    @Column(name = "cargo_descripcion")
    private String cargoDescripcion;
    @Size(max = 100)
    @Column(name = "nombre_titulo")
    private String nombreTitulo;
    @Size(max = 40)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 2147483647)
    @Column(name = "contrasenia")
    private String contrasenia;
    @Size(max = 80)
    @Column(name = "facebook")
    private String facebook;
    @Size(max = 80)
    @Column(name = "twiter")
    private String twiter;
    @Size(max = 80)
    @Column(name = "correo_institucional")
    private String correoInstitucional;
    @Size(max = 80)
    @Column(name = "correo_personal")
    private String correoPersonal;
    @Size(max = 10)
    @Column(name = "telefono_institucional")
    private String telefonoInstitucional;
    @Size(max = 5)
    @Column(name = "extension_institucional")
    private String extensionInstitucional;
    @Size(max = 10)
    @Column(name = "telefono_local")
    private String telefonoLocal;
    @Size(max = 10)
    @Column(name = "telefono_celular")
    private String telefonoCelular;
    @Size(max = 1280)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @JoinColumn(name = "cod_institucion", referencedColumnName = "id_institucion")
    @ManyToOne
    private AdmInstitucion codInstitucion;
    @JoinColumn(name = "cod_organigrama", referencedColumnName = "id_organigrama")
    @ManyToOne
    private AdmOrganigrama codOrganigrama;
    @JoinColumn(name = "cod_dpa", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codDpa;
    @JoinColumn(name = "cod_nivel_instruccion", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codNivelInstruccion;
    @JoinColumn(name = "cod_nivel_salarial", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codNivelSalarial;
    @JoinColumn(name = "cod_cargo", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codCargo;
    @JoinColumn(name = "cod_tipo_contrato", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoContrato;

    public AdmPersonal() {
    }

    public AdmPersonal(Integer idPersonal) {
        this.idPersonal = idPersonal;
    }

    public AdmPersonal(Integer idPersonal, String identificacion, String primerNombre, String primerApellido, boolean estadoLogico) {
        this.idPersonal = idPersonal;
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(Integer idPersonal) {
        this.idPersonal = idPersonal;
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

    public String getCargoDescripcion() {
        return cargoDescripcion;
    }

    public void setCargoDescripcion(String cargoDescripcion) {
        this.cargoDescripcion = cargoDescripcion;
    }

    public String getNombreTitulo() {
        return nombreTitulo;
    }

    public void setNombreTitulo(String nombreTitulo) {
        this.nombreTitulo = nombreTitulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwiter() {
        return twiter;
    }

    public void setTwiter(String twiter) {
        this.twiter = twiter;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getTelefonoInstitucional() {
        return telefonoInstitucional;
    }

    public void setTelefonoInstitucional(String telefonoInstitucional) {
        this.telefonoInstitucional = telefonoInstitucional;
    }

    public String getExtensionInstitucional() {
        return extensionInstitucional;
    }

    public void setExtensionInstitucional(String extensionInstitucional) {
        this.extensionInstitucional = extensionInstitucional;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
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

    public AdmInstitucion getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(AdmInstitucion codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    public AdmOrganigrama getCodOrganigrama() {
        return codOrganigrama;
    }

    public void setCodOrganigrama(AdmOrganigrama codOrganigrama) {
        this.codOrganigrama = codOrganigrama;
    }

    public MetCatalogo getCodDpa() {
        return codDpa;
    }

    public void setCodDpa(MetCatalogo codDpa) {
        this.codDpa = codDpa;
    }

    public MetCatalogo getCodNivelInstruccion() {
        return codNivelInstruccion;
    }

    public void setCodNivelInstruccion(MetCatalogo codNivelInstruccion) {
        this.codNivelInstruccion = codNivelInstruccion;
    }

    public MetCatalogo getCodNivelSalarial() {
        return codNivelSalarial;
    }

    public void setCodNivelSalarial(MetCatalogo codNivelSalarial) {
        this.codNivelSalarial = codNivelSalarial;
    }

    public MetCatalogo getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(MetCatalogo codCargo) {
        this.codCargo = codCargo;
    }

    public MetCatalogo getCodTipoContrato() {
        return codTipoContrato;
    }

    public void setCodTipoContrato(MetCatalogo codTipoContrato) {
        this.codTipoContrato = codTipoContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonal != null ? idPersonal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmPersonal)) {
            return false;
        }
        AdmPersonal other = (AdmPersonal) object;
        if ((this.idPersonal == null && other.idPersonal != null) || (this.idPersonal != null && !this.idPersonal.equals(other.idPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.administracion.ejb.entidades.AdmPersonal[ idPersonal=" + idPersonal + " ]";
    }
       
}
