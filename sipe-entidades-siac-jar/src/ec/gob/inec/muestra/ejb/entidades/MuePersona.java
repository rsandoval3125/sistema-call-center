/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "muestra.mue_persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MuePersona.findAll", query = "SELECT m FROM MuePersona m")})
public class MuePersona implements Serializable {

   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Integer idPersona;
    @Size(max = 20)
    @Column(name = "identificador_ced")
    private String identificadorCed;
    @Size(max = 250)
    @Column(name = "primer_nombre")
    private String primerNombre;
    @Size(max = 250)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    @Size(max = 250)
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Size(max = 250)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Column(name = "sexo")
    private Short sexo;
    @Column(name = "edad")
    private Short edad;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Column(name = "estado_logico")
    private Boolean estadoLogico;
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 50)
    @Column(name = "cod_mue_ref")
    private String codMueRef;
    @Size(max = 50)
    @Column(name = "identificador_per")
    private String identificadorPer;
    @Size(max = 10)
    @Column(name = "cod_ref")
    private String codRef;

    public MuePersona() {
    }

    public MuePersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getIdentificadorCed() {
        return identificadorCed;
    }

    public void setIdentificadorCed(String identificadorCed) {
        this.identificadorCed = identificadorCed;
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

    public Short getSexo() {
        return sexo;
    }

    public void setSexo(Short sexo) {
        this.sexo = sexo;
    }

    public Short getEdad() {
        return edad;
    }

    public void setEdad(Short edad) {
        this.edad = edad;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodMueRef() {
        return codMueRef;
    }

    public void setCodMueRef(String codMueRef) {
        this.codMueRef = codMueRef;
    }

    public String getIdentificadorPer() {
        return identificadorPer;
    }

    public void setIdentificadorPer(String identificadorPer) {
        this.identificadorPer = identificadorPer;
    }

    public String getCodRef() {
        return codRef;
    }

    public void setCodRef(String codRef) {
        this.codRef = codRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MuePersona)) {
            return false;
        }
        MuePersona other = (MuePersona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MuePersona[ idPersona=" + idPersona + " ]";
    }
    
}
