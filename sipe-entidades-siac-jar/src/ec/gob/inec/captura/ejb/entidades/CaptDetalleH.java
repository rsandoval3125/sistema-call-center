/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.entidades;

import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "captura.capt_detalle_h")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaptDetalleH.findAll", query = "SELECT c FROM CaptDetalleH c")})
public class CaptDetalleH implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_deth")
    private Integer idDeth;
    @Size(max = 30)
    @Column(name = "clave")
    private String clave;
    @Size(max = 2)
    @Column(name = "estado_proc")
    private String estadoProc;
    @Column(name = "num_det")
    private Integer numDet;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 2147483647)
    @Column(name = "val01")
    private String val01;
    @Size(max = 2147483647)
    @Column(name = "val02")
    private String val02;
    @Size(max = 2147483647)
    @Column(name = "val03")
    private String val03;
    @Size(max = 2147483647)
    @Column(name = "val04")
    private String val04;
    @Size(max = 2147483647)
    @Column(name = "val05")
    private String val05;
    @Size(max = 2147483647)
    @Column(name = "val06")
    private String val06;
    @Size(max = 2147483647)
    @Column(name = "val07")
    private String val07;
    @Size(max = 2147483647)
    @Column(name = "val08")
    private String val08;
    @Size(max = 2147483647)
    @Column(name = "val09")
    private String val09;
    @Size(max = 2147483647)
    @Column(name = "val10")
    private String val10;
    @Size(max = 2147483647)
    @Column(name = "val11")
    private String val11;
    @Size(max = 2147483647)
    @Column(name = "val12")
    private String val12;
    @Size(max = 2147483647)
    @Column(name = "val13")
    private String val13;
    @Size(max = 2147483647)
    @Column(name = "val14")
    private String val14;
    @Size(max = 2147483647)
    @Column(name = "val15")
    private String val15;
    @Size(max = 2147483647)
    @Column(name = "val16")
    private String val16;
    @Size(max = 2147483647)
    @Column(name = "val17")
    private String val17;
    @Size(max = 2147483647)
    @Column(name = "val18")
    private String val18;
    @Size(max = 2147483647)
    @Column(name = "val19")
    private String val19;
    @Size(max = 2147483647)
    @Column(name = "val20")
    private String val20;
    @Size(max = 2147483647)
    @Column(name = "val21")
    private String val21;
    @Size(max = 2147483647)
    @Column(name = "val22")
    private String val22;
    @Size(max = 2147483647)
    @Column(name = "val23")
    private String val23;
    @Size(max = 2147483647)
    @Column(name = "val24")
    private String val24;
    @Size(max = 2147483647)
    @Column(name = "val25")
    private String val25;
    @Size(max = 2147483647)
    @Column(name = "val26")
    private String val26;
    @Size(max = 2147483647)
    @Column(name = "val27")
    private String val27;
    @Size(max = 2147483647)
    @Column(name = "val28")
    private String val28;
    @Size(max = 2147483647)
    @Column(name = "val29")
    private String val29;
    @Size(max = 2147483647)
    @Column(name = "val30")
    private String val30;
    @Size(max = 2147483647)
    @Column(name = "val31")
    private String val31;
    @Size(max = 2147483647)
    @Column(name = "val32")
    private String val32;
    @Size(max = 2147483647)
    @Column(name = "val33")
    private String val33;
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion; 
    @Column(name = "fechahora_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraEdicion; 
    @Column(name = "tipo_formulario")
    private String tipoFormulario;
    @Transient
    private String correoOperador;
    
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
     @Column(name = "codigo_tipo_identificacion")
    private String codigoTipoIdentificacion;
     
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    @Column(name = "codigo_tipo_usuario")
    private String codigoTipoUsuario;
      
    @Column(name = "numero_ciudadano_no_censado")
    private String numeroCiudadanoNoCensado;
    @Column(name = "codigo_censo_linea")
    private String codigoCensoLinea;
    
    @JoinColumn(name = "cod_cab", referencedColumnName = "id_cab")
    @ManyToOne
    private CaptCabecera codCab;
    @JoinColumn(name = "cod_seccion", referencedColumnName = "id_seccion")
    @ManyToOne
    private MetSeccion codSeccion;
    

    public CaptDetalleH() {
    }

    public CaptDetalleH(Integer idDeth) {
        this.idDeth = idDeth;
    }

    public Integer getIdDeth() {
        return idDeth;
    }

    public void setIdDeth(Integer idDeth) {
        this.idDeth = idDeth;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstadoProc() {
        return estadoProc;
    }

    public void setEstadoProc(String estadoProc) {
        this.estadoProc = estadoProc;
    }

    public Integer getNumDet() {
        return numDet;
    }

    public void setNumDet(Integer numDet) {
        this.numDet = numDet;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getVal01() {
        return val01;
    }

    public void setVal01(String val01) {
        this.val01 = val01;
    }

    public String getVal02() {
        return val02;
    }

    public void setVal02(String val02) {
        this.val02 = val02;
    }

    public String getVal03() {
        return val03;
    }

    public void setVal03(String val03) {
        this.val03 = val03;
    }

    public String getVal04() {
        return val04;
    }

    public void setVal04(String val04) {
        this.val04 = val04;
    }

    public String getVal05() {
        return val05;
    }

    public void setVal05(String val05) {
        this.val05 = val05;
    }

    public String getVal06() {
        return val06;
    }

    public void setVal06(String val06) {
        this.val06 = val06;
    }

    public String getVal07() {
        return val07;
    }

    public void setVal07(String val07) {
        this.val07 = val07;
    }

    public String getVal08() {
        return val08;
    }

    public void setVal08(String val08) {
        this.val08 = val08;
    }

    public String getVal09() {
        return val09;
    }

    public void setVal09(String val09) {
        this.val09 = val09;
    }

    public String getVal10() {
        return val10;
    }

    public void setVal10(String val10) {
        this.val10 = val10;
    }

    public String getVal11() {
        return val11;
    }

    public void setVal11(String val11) {
        this.val11 = val11;
    }

    public String getVal12() {
        return val12;
    }

    public void setVal12(String val12) {
        this.val12 = val12;
    }

    public String getVal13() {
        return val13;
    }

    public void setVal13(String val13) {
        this.val13 = val13;
    }

    public String getVal14() {
        return val14;
    }

    public void setVal14(String val14) {
        this.val14 = val14;
    }

    public String getVal15() {
        return val15;
    }

    public void setVal15(String val15) {
        this.val15 = val15;
    }

    public String getVal16() {
        return val16;
    }

    public void setVal16(String val16) {
        this.val16 = val16;
    }

    public String getVal17() {
        return val17;
    }

    public void setVal17(String val17) {
        this.val17 = val17;
    }

    public String getVal18() {
        return val18;
    }

    public void setVal18(String val18) {
        this.val18 = val18;
    }

    public String getVal19() {
        return val19;
    }

    public void setVal19(String val19) {
        this.val19 = val19;
    }

    public String getVal20() {
        return val20;
    }

    public void setVal20(String val20) {
        this.val20 = val20;
    }

    public String getVal21() {
        return val21;
    }

    public void setVal21(String val21) {
        this.val21 = val21;
    }

    public String getVal22() {
        return val22;
    }

    public void setVal22(String val22) {
        this.val22 = val22;
    }

    public String getVal23() {
        return val23;
    }

    public void setVal23(String val23) {
        this.val23 = val23;
    }

    public String getVal24() {
        return val24;
    }

    public void setVal24(String val24) {
        this.val24 = val24;
    }

    public String getVal25() {
        return val25;
    }

    public void setVal25(String val25) {
        this.val25 = val25;
    }

    public String getVal26() {
        return val26;
    }

    public void setVal26(String val26) {
        this.val26 = val26;
    }

    public String getVal27() {
        return val27;
    }

    public void setVal27(String val27) {
        this.val27 = val27;
    }

    public String getVal28() {
        return val28;
    }

    public void setVal28(String val28) {
        this.val28 = val28;
    }

    public String getVal29() {
        return val29;
    }

    public void setVal29(String val29) {
        this.val29 = val29;
    }

    public String getVal30() {
        return val30;
    }

    public void setVal30(String val30) {
        this.val30 = val30;
    }

    public String getVal31() {
        return val31;
    }

    public void setVal31(String val31) {
        this.val31 = val31;
    }

    public String getVal32() {
        return val32;
    }

    public void setVal32(String val32) {
        this.val32 = val32;
    }

    public String getVal33() {
        return val33;
    }

    public void setVal33(String val33) {
        this.val33 = val33;
    }
         
    public Date getFechahoraEdicion() {
        return fechahoraEdicion;
    }

    public void setFechahoraEdicion(Date fechahoraEdicion) {
        this.fechahoraEdicion = fechahoraEdicion;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }  

    public String getTipoFormulario() {
        return tipoFormulario;
    }

    public void setTipoFormulario(String tipoFormulario) {
        this.tipoFormulario = tipoFormulario;
    }  

    public CaptCabecera getCodCab() {
        return codCab;
    }

    public void setCodCab(CaptCabecera codCab) {
        this.codCab = codCab;
    }

    public MetSeccion getCodSeccion() {
        return codSeccion;
    }

    public void setCodSeccion(MetSeccion codSeccion) {
        this.codSeccion = codSeccion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNumeroCiudadanoNoCensado() {
        return numeroCiudadanoNoCensado;
    }

    public void setNumeroCiudadanoNoCensado(String numeroCiudadanoNoCensado) {
        this.numeroCiudadanoNoCensado = numeroCiudadanoNoCensado;
    }

    public String getCodigoCensoLinea() {
        return codigoCensoLinea;
    }

    public void setCodigoCensoLinea(String codigoCensoLinea) {
        this.codigoCensoLinea = codigoCensoLinea;
    }

    public String getCodigoTipoIdentificacion() {
        return codigoTipoIdentificacion;
    }

    public void setCodigoTipoIdentificacion(String codigoTipoIdentificacion) {
        this.codigoTipoIdentificacion = codigoTipoIdentificacion;
    }

    public String getCodigoTipoUsuario() {
        return codigoTipoUsuario;
    }

    public void setCodigoTipoUsuario(String codigoTipoUsuario) {
        this.codigoTipoUsuario = codigoTipoUsuario;
    }

    public String getCorreoOperador() {
        return correoOperador;
    }

    public void setCorreoOperador(String correoOperador) {
        this.correoOperador = correoOperador;
    }          

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeth != null ? idDeth.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaptDetalleH)) {
            return false;
        }
        CaptDetalleH other = (CaptDetalleH) object;
        if ((this.idDeth == null && other.idDeth != null) || (this.idDeth != null && !this.idDeth.equals(other.idDeth))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.captura.ejb.entidades.CaptDetalleH[ idDeth=" + idDeth + " ]";
    }
    
}
