/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoCiuo;
import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rmoreano
 */
@Stateless
public class MetCatalogoCiuoFacade extends AbstractFacade<MetCatalogoCiuo> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetCatalogoCiuoFacade() {
        super(MetCatalogoCiuo.class);
    }
    
     public List<MetCatalogoCiuo> listarCatalogoXAlias(String aliasCat) throws Exception {
        String sql = "  SELECT c "
                + "  FROM MetTipoCatalogo tc , MetCatalogoCiuo c "
                + "  where tc.alias =:aliasCat "
                + "  and tc.estadoLogico = true "
                + "  and tc.idTipoCatalogo = c.codTipoCatalogo "
                + "  and c.estadoLogico = true "
                + "  order by c.orden asc ";
        Query q = em.createQuery(sql);
        q.setParameter("aliasCat", aliasCat);
        return q.getResultList();
    }

    public List<MetCatalogoCiuo> listarCatalogoFasesXOperTodas(MetOperativo codOper) throws Exception {
        String sql = " SELECT c "
                + "  FROM MetCatalogoCiuo c , DisFaseOperativo foe"
                + "  where c.idCatalogoCiuo = foe.codFase"
                + "  and c.estadoLogico = true "
                + "  and foe.codOperativo =:codOper "
                + "   order by c.orden asc ";
        Query q = em.createQuery(sql);
        q.setParameter("codOper", codOper);
      
        return q.getResultList();
    }

    public List<MetCatalogoCiuo> listarCatalogoNoAsignados(List<MetCatalogoCiuo> lstCats, String aliasCat) throws Exception {
        String sql = " SELECT c "
                + "  FROM MetTipoCatalogo tc , MetCatalogoCiuo c "
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

    public List<MetCatalogoCiuo> listarCatalogosPorTipos(List<Integer> codigosTipos_) throws Exception {
        String sql = "select c from MetCatalogoCiuo c "
                + "where c.estadoLogico=true and c.codTipoCatalogo.idTipoCatalogo in :codigosTipos_ "
                + "order by c.codTipoCatalogo.idTipoCatalogo,c.orden ";
        Query q = em.createQuery(sql);
        q.setParameter("codigosTipos_", codigosTipos_);
        return q.getResultList();
    }

    public List<MetCatalogoCiuo> listarTodosActivos() throws Exception {
        String sql = "select c from MetCatalogoCiuo c "
                + "where c.estadoLogico=true "
                + "order by c.nombre asc ";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }

    public List<MetCatalogoCiuo> listarTodosActivosDistintoAlId(Integer idCatalogoCiuo) throws Exception {
        String sql = "select c from MetCatalogoCiuo c "
                + "where c.estadoLogico=true and c.idCatalogoCiuo <> :idCatalogoCiuo "
                + "order by c.nombre asc ";
        Query q = em.createQuery(sql);
        q.setParameter("idCatalogoCiuo", idCatalogoCiuo);
        return q.getResultList();
    }
    
     public List<MetCatalogoCiuo> listarCatalogosPorPadre1(Integer codPadre1) throws Exception {
        String sql = "select c from MetCatalogoCiuo c "
                + "where c.estadoLogico=true and c.codPadre1.idCatalogoCiuo=:codPadre1 "
                + "order by c.orden ";
        Query q = em.createQuery(sql);
        q.setParameter("codPadre1", codPadre1);
        return q.getResultList();
    }
    
   public MetCatalogoCiuo buscarPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre) throws Exception {
      String sql = "select c from MetCatalogoCiuo c "
              + " where c.codTipoCatalogo.idTipoCatalogo =:idTipoCatalogo AND   c.nombre =:nombre ";
      Query q = em.createQuery(sql);
      q.setParameter("idTipoCatalogo", idTipoCatalogo);
      q.setParameter("nombre", nombre);
      List<MetCatalogoCiuo> retorno = q.getResultList();

      if (retorno != null && !retorno.isEmpty()) {
         return retorno.get(0);
      } else {
         return null;
      }
   }
   
   public MetCatalogoCiuo buscarCatalogoPorIdTipoCatalogoYNombre(Integer idTipoCatalogo, String nombre ) throws Exception{
      String sql =" SELECT  a "
                + " FROM MetCatalogoCiuo a "
                + " WHERE a.codTipoCatalogo.idTipoCatalogo =:idTipoCatalogo AND a.nombre =:nombre  ";
      Query q =em.createQuery(sql);
      q.setParameter("idTipoCatalogo", idTipoCatalogo);
      q.setParameter("nombre", nombre);
      List<MetCatalogoCiuo> retorno =q.getResultList();
      
      if (retorno != null && !retorno.isEmpty()) {
         return retorno.get(0);
      } else {
         return null;
      }
   }
}
