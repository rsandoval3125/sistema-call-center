/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vespinoza
 */
@Entity
@Table(name = "seguridad.seg_auditoria_cab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SegAuditoriaCab.findAll", query = "SELECT s FROM SegAuditoriaCab s")})
public class SegAuditoriaCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_auditoria_cab")
    private Integer idAuditoriaCab;
    @Column(name = "codigo_pagina")
    private Short codigoPagina;
    @Basic(optional = false)
    @Column(name = "codigo_evento")
    private short codigoEvento;
    @Basic(optional = false)
    @Column(name = "fechahora_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraRegistro;
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    @Column(name = "session")
    private String session;
    @Basic(optional = false)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "accion")
    private String accion;
    @Basic(optional = false)
    @Column(name = "esquema_tabla")
    private String esquemaTabla;
    @Basic(optional = false)
    @Column(name = "nombre_tabla")
    private String nombreTabla;
    @Column(name = "nombre_campo_pk")
    private String nombreCampoPk;
    @Column(name = "valor_campo_pk")
    private String valorCampoPk;
    @Column(name = "navegador")
    private String navegador;
    @Column(name = "sistema_operativo")
    private String sistemaOperativo;

    public SegAuditoriaCab() {
    }

    public SegAuditoriaCab(Integer idAuditoriaCab) {
        this.idAuditoriaCab = idAuditoriaCab;
    }

    public SegAuditoriaCab(Integer idAuditoriaCab, short codigoEvento, Date fechahoraRegistro, String ip, String nombreUsuario, String accion, String esquemaTabla, String nombreTabla) {
        this.idAuditoriaCab = idAuditoriaCab;
        this.codigoEvento = codigoEvento;
        this.fechahoraRegistro = fechahoraRegistro;
        this.ip = ip;
        this.nombreUsuario = nombreUsuario;
        this.accion = accion;
        this.esquemaTabla = esquemaTabla;
        this.nombreTabla = nombreTabla;
    }

    public Integer getIdAuditoriaCab() {
        return idAuditoriaCab;
    }

    public void setIdAuditoriaCab(Integer idAuditoriaCab) {
        this.idAuditoriaCab = idAuditoriaCab;
    }

    public Short getCodigoPagina() {
        return codigoPagina;
    }

    public void setCodigoPagina(Short codigoPagina) {
        this.codigoPagina = codigoPagina;
    }

    public short getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(short codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public Date getFechahoraRegistro() {
        return fechahoraRegistro;
    }

    public void setFechahoraRegistro(Date fechahoraRegistro) {
        this.fechahoraRegistro = fechahoraRegistro;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getEsquemaTabla() {
        return esquemaTabla;
    }

    public void setEsquemaTabla(String esquemaTabla) {
        this.esquemaTabla = esquemaTabla;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getNombreCampoPk() {
        return nombreCampoPk;
    }

    public void setNombreCampoPk(String nombreCampoPk) {
        this.nombreCampoPk = nombreCampoPk;
    }

    public String getValorCampoPk() {
        return valorCampoPk;
    }

    public void setValorCampoPk(String valorCampoPk) {
        this.valorCampoPk = valorCampoPk;
    }

    public String getNavegador() {
        return navegador;
    }

    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuditoriaCab != null ? idAuditoriaCab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegAuditoriaCab)) {
            return false;
        }
        SegAuditoriaCab other = (SegAuditoriaCab) object;
        if ((this.idAuditoriaCab == null && other.idAuditoriaCab != null) || (this.idAuditoriaCab != null && !this.idAuditoriaCab.equals(other.idAuditoriaCab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaCab[ idAuditoriaCab=" + idAuditoriaCab + " ]";
    }
    
}
