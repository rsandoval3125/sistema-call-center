/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.clases.CaptInfoMuestra;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vespinoza
 */
@Stateless
public class CaptCargaControlFacade extends AbstractFacade<CaptCargaControl> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptCargaControlFacade() {
        super(CaptCargaControl.class);
    }

    public CaptInfoMuestra obtenerInfMuestraHogarPorIdMueDet(Integer idMueDet) throws Exception {

        String sql = "SELECT * from muestra.v_infcaptura_hogar where id_mue_det=" + idMueDet;
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        CaptInfoMuestra im = new CaptInfoMuestra();
        if (!lo.isEmpty()) {
            Object[] ao = lo.get(0);
            // columna 0 con id_mue_det
            String area = (ao[1] != null ? String.valueOf(ao[1]) : "");
            String prov = (ao[2] != null ? String.valueOf(ao[2]) : "");
            String cant = (ao[3] != null ? String.valueOf(ao[3]) : "");
            String parr = (ao[4] != null ? String.valueOf(ao[4]) : "");
            String cong = (ao[5] != null ? String.valueOf(ao[5]) : "");
            String oviv = (ao[6] != null ? String.valueOf(ao[6]) : "");
            String nhog = (ao[7] != null ? String.valueOf(ao[7]) : "");
            String thog = (ao[8] != null ? String.valueOf(ao[8]) : "");
            String zona = (ao[9] != null ? String.valueOf(ao[9]) : "");
            String sect = (ao[10] != null ? String.valueOf(ao[10]) : "");
            String manz = (ao[11] != null ? String.valueOf(ao[11]) : "");
            String edif = (ao[12] != null ? String.valueOf(ao[12]) : "");
            String nviv = (ao[13] != null ? String.valueOf(ao[13]) : "");

            im = new CaptInfoMuestra(area, prov, cant, parr, cong, oviv, nhog, thog, zona, sect, manz, edif, nviv);

        }
        return im;
    }

    public CaptCargaControl listarCaptCargaControlPorClaveCtr12PorFor(String clave, String control1, String control2, Integer codFormulario) throws Exception {
        String sql = "select o from CaptCargaControl o "
                + "where o.clave =:clave and o.control1 =:control1 and "
                + "o.control2 =:control2 and o.codFormulario.idFormulario =:codFormulario";
        Query q = em.createQuery(sql);
        q.setParameter("clave", clave).setParameter("control1", control1).setParameter("control2", control2).setParameter("codFormulario", codFormulario);
        List<CaptCargaControl> lst = q.getResultList();
        if (!lst.isEmpty()) {
            if (lst.size() > 1) {
                System.out.println("Hay más de una clave con esos parametros.");
            }
            //deberia devolver siempre un solo registro.
            return lst.get(0);
        } else {
            return new CaptCargaControl();
        }
    }

    public List<CaptCargaControl> listarPorClaveCtr12PorMuestra(String clave, String control1, String control2, Integer codMueDet) {
        String sql = "select o from CaptCargaControl o "
                + "where o.clave =:clave and o.control1 =:control1 and "
                + "o.control2 =:control2 and o.codMueDet.idMueDet =:codMueDet and o.estadoLogico=true ORDER BY codFormulario.idFormulario ";
        Query q = em.createQuery(sql);
        q.setParameter("clave", clave).setParameter("control1", control1).setParameter("control2", control2).setParameter("codMueDet", codMueDet);
        List<CaptCargaControl> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControl> listarCaptCargaControlPorClaveCtrPorFor(String clave, String control1, String control2, Integer codFormulario) throws Exception {
        String sql = "select o from CaptCargaControl o "
                + "where o.estadoLogico=true and o.clave =:clave and o.control1 =:control1 and "
                + "o.control2 =:control2 and o.codFormulario.idFormulario =:codFormulario";
        Query q = em.createQuery(sql);
        q.setParameter("clave", clave).setParameter("control1", control1).setParameter("control2", control2).setParameter("codFormulario", codFormulario);
        List<CaptCargaControl> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControl> listarCaptCargaControlPorClaveCtr3PorFor(String clave, String control1, String control2, String control3, Integer codFormulario) throws Exception {
        String sql = "select o from CaptCargaControl o "
                + "where o.estadoLogico=true and o.clave =:clave and o.control1 =:control1 and "
                + "o.control2 =:control2 and o.control3 =:control3 and o.codFormulario.idFormulario =:codFormulario";
        Query q = em.createQuery(sql);
        q.setParameter("clave", clave).setParameter("control1", control1).setParameter("control2", control2).setParameter("control3", control3).setParameter("codFormulario", codFormulario);
        List<CaptCargaControl> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControl> listarPorClaveCtr1PorMuestraPorFrm(String clave, String control1, Integer codMueDet, Integer codFormulario) {
        String sql = "select o from CaptCargaControl o "
                + "where o.clave =:clave and o.control1 =:control1 and estadoLogico = true and "
                + "o.codMueDet.idMueDet =:codMueDet and o.codFormulario.idFormulario = :codFormulario order by control2 ";
        Query q = em.createQuery(sql);
        q.setParameter("clave", clave).setParameter("control1", control1).setParameter("codMueDet", codMueDet).setParameter("codFormulario", codFormulario);
        List<CaptCargaControl> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControl> listarCargaRepetidaPrecenso(List<String> listaClavesAGenerar, String tipo) throws Exception {
        String sql = "select c from CaptCargaControl c "
                + "where c.estadoLogico=true and clave in :listaClavesAGenerar and tipo=:tipo ";
        Query q = em.createQuery(sql);
        q.setParameter("listaClavesAGenerar", listaClavesAGenerar);
        q.setParameter("tipo", tipo);
        List<CaptCargaControl> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControl> listarCargaPorClaveFormularioLike(String identificadorDPA, String codFormulario, String tipo, String estRenumerado) {
        String sql = " select c from CaptCargaControl c  "
                + " where c.clave like :identificador "
                + " and c.codFormulario.codificacion=:formlario "
                + " and c.estadoLogico=true "
                + " and c.tipo=:tipoSec"
                + " and c.control5=:estadoRenum order by cast(substring(c.clave,7,3) as integer),cast(substring(c.clave,10,3) as integer) asc";
        Query q = em.createQuery(sql);
        q.setParameter("identificador", "%" + identificadorDPA + "%");
        q.setParameter("formlario", codFormulario);
        q.setParameter("tipoSec", tipo);
        q.setParameter("estadoRenum", estRenumerado);
        List<CaptCargaControl> list = q.getResultList();
        return list;
    }

    public CaptCargaControl cargarCaptCargaControlPorClave2020(String clave, String formulario) throws Exception {
        String sql = " select distinct(cc) from CaptCargaControl cc,CaptCabecera ca "
                + " where ca.codCarCon=cc.idCarCon"
                + " and ca.estadoLogico=true "
                + " and cc.estadoLogico=true "
                + " and substring(ca.info4,1,12)=:clave "
                + " and cc.codFormulario.codificacion =:codFormulario ";
        Query q = em.createQuery(sql);
        q.setParameter("clave", clave).setParameter("codFormulario", formulario);
        List<CaptCargaControl> lst = q.getResultList();
        if (!lst.isEmpty()) {
            if (lst.size() > 1) {
                System.out.println("Hay más de una clave con esos parametros.");
            }
            //deberia devolver siempre un solo registro.
            return lst.get(0);
        } else {
            return new CaptCargaControl();
        }
    }

    public List<String> listarConglomeradosPorUsuario(Integer codUsuario) {
        String sql = "SELECT distinct ce.codCarCon.clave FROM CaptCargaControlEquipo ce "
                + "  WHERE ce.estadoLogico=true AND ce.codCarTra.estadoLogico=true AND ce.codCarCon.estadoLogico=true"
                + "  AND ce.codCarTra.codEquTraDet.codRolUsu.codUsuario.idUsuario=:codUsuario "
                + "  ORDER BY ce.codCarCon.clave";
        Query q = em.createQuery(sql);
        q.setParameter("codUsuario", codUsuario);
        List<String> lst = q.getResultList();
        return lst;
    }
}
