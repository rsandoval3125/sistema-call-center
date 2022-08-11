/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.entidades;

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
@Table(name = "muestra.mue_muestra_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MueMuestraDetalle.findAll", query = "SELECT m FROM MueMuestraDetalle m")})
public class MueMuestraDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mue_det")
    private Integer idMueDet;
    @Size(max = 50)
    @Column(name = "identificador")
    private String identificador;
    @Size(max = 250)
    @Column(name = "cod_auditoria_cab")
    private String codAuditoriaCab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_logico")
    private boolean estadoLogico;
    @Column(name = "fechahora_act")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahoraAct;
    @Size(max = 50)
    @Column(name = "jornada")
    private String jornada;
    @Size(max = 2147483647)
    @Column(name = "ruta")
    private String ruta;
    @Size(max = 2147483647)
    @Column(name = "var01")
    private String var01;
    @Size(max = 2147483647)
    @Column(name = "var02")
    private String var02;
    @Size(max = 2147483647)
    @Column(name = "var03")
    private String var03;
    @Size(max = 2147483647)
    @Column(name = "var04")
    private String var04;
    @Size(max = 2147483647)
    @Column(name = "var05")
    private String var05;
    @Size(max = 2147483647)
    @Column(name = "var06")
    private String var06;
    @Size(max = 2147483647)
    @Column(name = "var07")
    private String var07;
    @Size(max = 2147483647)
    @Column(name = "var08")
    private String var08;
    @Size(max = 2147483647)
    @Column(name = "var09")
    private String var09;
    @Size(max = 2147483647)
    @Column(name = "var10")
    private String var10;
    @Size(max = 2147483647)
    @Column(name = "var11")
    private String var11;
    @Size(max = 2147483647)
    @Column(name = "var12")
    private String var12;
    @Size(max = 2147483647)
    @Column(name = "var13")
    private String var13;
    @Size(max = 2147483647)
    @Column(name = "var14")
    private String var14;
    @Size(max = 2147483647)
    @Column(name = "var15")
    private String var15;
    @Size(max = 2147483647)
    @Column(name = "var16")
    private String var16;
    @Size(max = 2147483647)
    @Column(name = "var17")
    private String var17;
    @Size(max = 2147483647)
    @Column(name = "var18")
    private String var18;
    @Size(max = 2147483647)
    @Column(name = "var19")
    private String var19;
    @Size(max = 2147483647)
    @Column(name = "var20")
    private String var20;
    @Size(max = 2147483647)
    @Column(name = "var21")
    private String var21;
    @Size(max = 2147483647)
    @Column(name = "var22")
    private String var22;
    @Size(max = 2147483647)
    @Column(name = "var23")
    private String var23;
    @Size(max = 2147483647)
    @Column(name = "var24")
    private String var24;
    @Size(max = 2147483647)
    @Column(name = "var25")
    private String var25;
    @Size(max = 2147483647)
    @Column(name = "var26")
    private String var26;
    @Size(max = 2147483647)
    @Column(name = "var27")
    private String var27;
    @Size(max = 2147483647)
    @Column(name = "var28")
    private String var28;
    @Size(max = 2147483647)
    @Column(name = "var29")
    private String var29;
    @Size(max = 2147483647)
    @Column(name = "var30")
    private String var30;
    @Size(max = 2147483647)
    @Column(name = "var31")
    private String var31;
    @Size(max = 2147483647)
    @Column(name = "var32")
    private String var32;
    @Size(max = 2147483647)
    @Column(name = "var33")
    private String var33;
    @Size(max = 2147483647)
    @Column(name = "var34")
    private String var34;
    @Size(max = 2147483647)
    @Column(name = "var35")
    private String var35;
    @Size(max = 2147483647)
    @Column(name = "var36")
    private String var36;
    @Size(max = 2147483647)
    @Column(name = "var37")
    private String var37;
    @Size(max = 2147483647)
    @Column(name = "var38")
    private String var38;
    @Size(max = 2147483647)
    @Column(name = "var39")
    private String var39;
    @Size(max = 2147483647)
    @Column(name = "var40")
    private String var40;
    @Size(max = 2147483647)
    @Column(name = "var41")
    private String var41;
    @Size(max = 2147483647)
    @Column(name = "var42")
    private String var42;
    @Size(max = 2147483647)
    @Column(name = "var43")
    private String var43;
    @Size(max = 2147483647)
    @Column(name = "var44")
    private String var44;
    @Size(max = 2147483647)
    @Column(name = "var45")
    private String var45;
    @Size(max = 2147483647)
    @Column(name = "var46")
    private String var46;
    @Size(max = 2147483647)
    @Column(name = "var47")
    private String var47;
    @Size(max = 2147483647)
    @Column(name = "var48")
    private String var48;
    @Size(max = 2147483647)
    @Column(name = "var49")
    private String var49;
    @Size(max = 2147483647)
    @Column(name = "var50")
    private String var50;
    @JoinColumn(name = "cod_tipo_ident", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoIdent;
    @JoinColumn(name = "cod_tipo_asig", referencedColumnName = "id_catalogo")
    @ManyToOne
    private MetCatalogo codTipoAsig;
    @JoinColumn(name = "cod_muestra", referencedColumnName = "id_muestra")
    @ManyToOne
    private MueMuestra codMuestra;

    public MueMuestraDetalle() {
    }

    public MueMuestraDetalle(Integer idMueDet) {
        this.idMueDet = idMueDet;
    }

    public MueMuestraDetalle(Integer idMueDet, boolean estadoLogico) {
        this.idMueDet = idMueDet;
        this.estadoLogico = estadoLogico;
    }

    public Integer getIdMueDet() {
        return idMueDet;
    }

    public void setIdMueDet(Integer idMueDet) {
        this.idMueDet = idMueDet;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public Date getFechahoraAct() {
        return fechahoraAct;
    }

    public void setFechahoraAct(Date fechahoraAct) {
        this.fechahoraAct = fechahoraAct;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getVar01() {
        return var01;
    }

    public void setVar01(String var01) {
        this.var01 = var01;
    }

    public String getVar02() {
        return var02;
    }

    public void setVar02(String var02) {
        this.var02 = var02;
    }

    public String getVar03() {
        return var03;
    }

    public void setVar03(String var03) {
        this.var03 = var03;
    }

    public String getVar04() {
        return var04;
    }

    public void setVar04(String var04) {
        this.var04 = var04;
    }

    public String getVar05() {
        return var05;
    }

    public void setVar05(String var05) {
        this.var05 = var05;
    }

    public String getVar06() {
        return var06;
    }

    public void setVar06(String var06) {
        this.var06 = var06;
    }

    public String getVar07() {
        return var07;
    }

    public void setVar07(String var07) {
        this.var07 = var07;
    }

    public String getVar08() {
        return var08;
    }

    public void setVar08(String var08) {
        this.var08 = var08;
    }

    public String getVar09() {
        return var09;
    }

    public void setVar09(String var09) {
        this.var09 = var09;
    }

    public String getVar10() {
        return var10;
    }

    public void setVar10(String var10) {
        this.var10 = var10;
    }

    public String getVar11() {
        return var11;
    }

    public void setVar11(String var11) {
        this.var11 = var11;
    }

    public String getVar12() {
        return var12;
    }

    public void setVar12(String var12) {
        this.var12 = var12;
    }

    public String getVar13() {
        return var13;
    }

    public void setVar13(String var13) {
        this.var13 = var13;
    }

    public String getVar14() {
        return var14;
    }

    public void setVar14(String var14) {
        this.var14 = var14;
    }

    public String getVar15() {
        return var15;
    }

    public void setVar15(String var15) {
        this.var15 = var15;
    }

    public String getVar16() {
        return var16;
    }

    public void setVar16(String var16) {
        this.var16 = var16;
    }

    public String getVar17() {
        return var17;
    }

    public void setVar17(String var17) {
        this.var17 = var17;
    }

    public String getVar18() {
        return var18;
    }

    public void setVar18(String var18) {
        this.var18 = var18;
    }

    public String getVar19() {
        return var19;
    }

    public void setVar19(String var19) {
        this.var19 = var19;
    }

    public String getVar20() {
        return var20;
    }

    public void setVar20(String var20) {
        this.var20 = var20;
    }

    public String getVar21() {
        return var21;
    }

    public void setVar21(String var21) {
        this.var21 = var21;
    }

    public String getVar22() {
        return var22;
    }

    public void setVar22(String var22) {
        this.var22 = var22;
    }

    public String getVar23() {
        return var23;
    }

    public void setVar23(String var23) {
        this.var23 = var23;
    }

    public String getVar24() {
        return var24;
    }

    public void setVar24(String var24) {
        this.var24 = var24;
    }

    public String getVar25() {
        return var25;
    }

    public void setVar25(String var25) {
        this.var25 = var25;
    }

    public String getVar26() {
        return var26;
    }

    public void setVar26(String var26) {
        this.var26 = var26;
    }

    public String getVar27() {
        return var27;
    }

    public void setVar27(String var27) {
        this.var27 = var27;
    }

    public String getVar28() {
        return var28;
    }

    public void setVar28(String var28) {
        this.var28 = var28;
    }

    public String getVar29() {
        return var29;
    }

    public void setVar29(String var29) {
        this.var29 = var29;
    }

    public String getVar30() {
        return var30;
    }

    public void setVar30(String var30) {
        this.var30 = var30;
    }

    public String getVar31() {
        return var31;
    }

    public void setVar31(String var31) {
        this.var31 = var31;
    }

    public String getVar32() {
        return var32;
    }

    public void setVar32(String var32) {
        this.var32 = var32;
    }

    public String getVar33() {
        return var33;
    }

    public void setVar33(String var33) {
        this.var33 = var33;
    }

    public String getVar34() {
        return var34;
    }

    public void setVar34(String var34) {
        this.var34 = var34;
    }

    public String getVar35() {
        return var35;
    }

    public void setVar35(String var35) {
        this.var35 = var35;
    }

    public String getVar36() {
        return var36;
    }

    public void setVar36(String var36) {
        this.var36 = var36;
    }

    public String getVar37() {
        return var37;
    }

    public void setVar37(String var37) {
        this.var37 = var37;
    }

    public String getVar38() {
        return var38;
    }

    public void setVar38(String var38) {
        this.var38 = var38;
    }

    public String getVar39() {
        return var39;
    }

    public void setVar39(String var39) {
        this.var39 = var39;
    }

    public String getVar40() {
        return var40;
    }

    public void setVar40(String var40) {
        this.var40 = var40;
    }

    public String getVar41() {
        return var41;
    }

    public void setVar41(String var41) {
        this.var41 = var41;
    }

    public String getVar42() {
        return var42;
    }

    public void setVar42(String var42) {
        this.var42 = var42;
    }

    public String getVar43() {
        return var43;
    }

    public void setVar43(String var43) {
        this.var43 = var43;
    }

    public String getVar44() {
        return var44;
    }

    public void setVar44(String var44) {
        this.var44 = var44;
    }

    public String getVar45() {
        return var45;
    }

    public void setVar45(String var45) {
        this.var45 = var45;
    }

    public String getVar46() {
        return var46;
    }

    public void setVar46(String var46) {
        this.var46 = var46;
    }

    public String getVar47() {
        return var47;
    }

    public void setVar47(String var47) {
        this.var47 = var47;
    }

    public String getVar48() {
        return var48;
    }

    public void setVar48(String var48) {
        this.var48 = var48;
    }

    public String getVar49() {
        return var49;
    }

    public void setVar49(String var49) {
        this.var49 = var49;
    }

    public String getVar50() {
        return var50;
    }

    public void setVar50(String var50) {
        this.var50 = var50;
    }

    public MetCatalogo getCodTipoIdent() {
        return codTipoIdent;
    }

    public void setCodTipoIdent(MetCatalogo codTipoIdent) {
        this.codTipoIdent = codTipoIdent;
    }

    public MetCatalogo getCodTipoAsig() {
        return codTipoAsig;
    }

    public void setCodTipoAsig(MetCatalogo codTipoAsig) {
        this.codTipoAsig = codTipoAsig;
    }

    public MueMuestra getCodMuestra() {
        return codMuestra;
    }

    public void setCodMuestra(MueMuestra codMuestra) {
        this.codMuestra = codMuestra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMueDet != null ? idMueDet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MueMuestraDetalle)) {
            return false;
        }
        MueMuestraDetalle other = (MueMuestraDetalle) object;
        if ((this.idMueDet == null && other.idMueDet != null) || (this.idMueDet != null && !this.idMueDet.equals(other.idMueDet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.gob.inec.muestra.ejb.entidades.MueMuestraDetalle[ idMueDet=" + idMueDet + " ]";
    }
    
}
