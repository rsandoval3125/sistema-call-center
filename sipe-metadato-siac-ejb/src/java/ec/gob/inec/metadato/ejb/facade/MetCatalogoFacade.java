/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
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
public class MetCatalogoFacade extends AbstractFacade<MetCatalogo> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetCatalogoFacade() {
        super(MetCatalogo.class);
    }

    public List<MetCatalogo> listarCatalogoXAlias(String aliasCat) throws Exception {
        String sql = "  SELECT c "
                + "  FROM MetTipoCatalogo tc , MetCatalogo c "
                + "  where tc.alias =:aliasCat "
                + "  and tc.estadoLogico = true "
                + "  and tc.idTipoCatalogo = c.codTipoCatalogo "
                + "  and c.estadoLogico = true "
                + "  order by c.orden asc ";
        Query q = em.createQuery(sql);
        q.setParameter("aliasCat", aliasCat);
        return q.getResultList();
    }

    public List<MetCatalogo> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception {
        String sql = " SELECT c "
                + "  FROM MetCatalogo c , DisFaseOperativo foe"
                + "  where c.idCatalogo = foe.codFase"
                + "  and c.estadoLogico = true "
                + "  and foe.codOperativo =:codOper "
                + "   order by c.orden asc ";
        Query q = em.createQuery(sql);
        q.setParameter("codOper", codOper);

        return q.getResultList();
    }

    public List<MetCatalogo> listarCatalogoNoAsignados(List<MetCatalogo> lstCats, String aliasCat) throws Exception {
        String sql = " SELECT c "
                + "  FROM MetTipoCatalogo tc , MetCatalogo c "
                + "  where tc.alias =:aliasCat "
                + "  and tc.estadoLogico = true "
                + "  and tc.idTipoCatalogo = c.codTipoCatalogo "
                + "  and c.estadoLogico = true "
                + " and c not in (:lstCats) "
                + "  order by c.orden asc ";
        Query q = em.createQuery(sql);
        q.setParameter("lstCats", lstCats);
        q.setParameter("aliasCat", aliasCat);
        return q.getResultList();
    }

    public List<MetCatalogo> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception {
        String sql = "select c from MetCatalogo c "
                + "where c.estadoLogico=true and c.codTipoCatalogo.idTipoCatalogo in :codigosTipos_ "
                + "order by c.codTipoCatalogo.idTipoCatalogo,c.orden ";
        Query q = em.createQuery(sql);
        q.setParameter("codigosTipos_", codigosTipos_);
        return q.getResultList();
    }

    public List<MetCatalogo> listarTodosActivos() throws Exception {
        String sql = "select c from MetCatalogo c "
                + "where c.estadoLogico=true "
                + "order by c.nombre asc ";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }

    public List<MetCatalogo> listarTodosActivosDistintoAlId(Integer idCatalogo) throws Exception {
        String sql = "select c from MetCatalogo c "
                + "where c.estadoLogico=true and c.idCatalogo <> :idCatalogo "
                + "order by c.nombre asc ";
        Query q = em.createQuery(sql);
        q.setParameter("idCatalogo", idCatalogo);
        return q.getResultList();
    }

    public List<MetCatalogo> listarCatalogosPorPadre1(Integer codPadre1) throws Exception {
        String sql = "select c from MetCatalogo c "
                + "where c.estadoLogico=true and c.codPadre1.idCatalogo=:codPadre1 "
                + "order by c.orden ";
        Query q = em.createQuery(sql);
        q.setParameter("codPadre1", codPadre1);
        return q.getResultList();
    }
    
    public List<MetCatalogo> listarCatalogosPorPadre2(Integer codPadre2, String aliasTipCat) throws Exception {
        String sql = "select c from MetCatalogo c, MetTipoCatalogo tc "
                + "where c.estadoLogico=true "
                + "  and tc.estadoLogico = true "
                + "  and tc.alias =:aliasTipCat "
                + "  and tc.idTipoCatalogo = c.codTipoCatalogo "
                + "  and c.codPadre2.idCatalogo=:codPadre2 "
                + "order by c.orden ";
        Query q = em.createQuery(sql);
        q.setParameter("codPadre2", codPadre2);
        q.setParameter("aliasTipCat", aliasTipCat);
        return q.getResultList();
    }
    
    public List<MetCatalogo> listarCatalogosIpc(String valor, String aliasTipCat) throws Exception {
        String sql = "select c from MetCatalogo c, MetTipoCatalogo tc "
                + "where c.estadoLogico=true "
                + "  and tc.estadoLogico = true "
                + "  and tc.alias =:aliasTipCat "
                + "  and tc.idTipoCatalogo = c.codTipoCatalogo "
                + "  and c.valor like :valor "
                + "  order by c.orden ";
        Query q = em.createQuery(sql);
        q.setParameter("valor", "%" + valor + "%");
        q.setParameter("aliasTipCat", aliasTipCat);
        return q.getResultList();
    }

    public MetCatalogo buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
        String sql = "select c from MetCatalogo c "
                + " where c.codTipoCatalogo.idTipoCatalogo =:idTipoCatalogo AND   c.nombre =:nombre ";
        Query q = em.createQuery(sql);
        q.setParameter("idTipoCatalogo", idTipoCatalogo);
        q.setParameter("nombre", nombre);
        List<MetCatalogo> retorno = q.getResultList();

        if (retorno != null && !retorno.isEmpty()) {
            return retorno.get(0);
        } else {
            return null;
        }
    }

    public MetCatalogo buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
        String sql = " SELECT  a "
                + " FROM MetCatalogo a "
                + " WHERE a.codTipoCatalogo.idTipoCatalogo =:idTipoCatalogo AND a.nombre =:nombre  ";
        Query q = em.createQuery(sql);
        q.setParameter("idTipoCatalogo", idTipoCatalogo);
        q.setParameter("nombre", nombre);
        List<MetCatalogo> retorno = q.getResultList();

        if (retorno != null && !retorno.isEmpty()) {
            return retorno.get(0);
        } else {
            return null;
        }
    }

    public MetCatalogo buscarCatalogoValZonalPorCodProvincia(String alias, String valor) throws Exception {
        String sql = " select czn"
                + " from MetCatalogo czn,MetCatalogo ca "
                + " where ca.valor=:catValor "
                + " and czn.idCatalogo=ca.codPadre2 and ca.codTipoCatalogo.alias=:catAlias";
           Query q = em.createQuery(sql);
        q.setParameter("catAlias", alias);
        q.setParameter("catValor", valor);
        List<MetCatalogo> retorno = q.getResultList();
        if (retorno != null && !retorno.isEmpty()) {
            return retorno.get(0);
        } else {
            return null;
        }
    }
    
    public List<MetCatalogo> listarCatalogosPorTipoCatalogo(Integer codTipoCatalogo) throws Exception {
        String sql = "select c from MetCatalogo c "
                + "where c.estadoLogico=true and c.codTipoCatalogo.idTipoCatalogo=:codTipoCatalogo "
                + "order by c.orden ";
        Query q = em.createQuery(sql);
        q.setParameter("codTipoCatalogo", codTipoCatalogo);
        return q.getResultList();
    }
    
    public MetCatalogo buscarPorIdTipoCatalogoYValor(Integer idTipoCatalogo, String valor) throws Exception {
        String sql = "select c from MetCatalogo c "
                + " where c.codTipoCatalogo.idTipoCatalogo =:idTipoCatalogo AND   c.valor =:valor ";
        Query q = em.createQuery(sql);
        q.setParameter("idTipoCatalogo", idTipoCatalogo);
        q.setParameter("valor", valor);
        List<MetCatalogo> retorno = q.getResultList();

        if (retorno != null && !retorno.isEmpty()) {
            return retorno.get(0);
        } else {
            return null;
        }
    }
}
