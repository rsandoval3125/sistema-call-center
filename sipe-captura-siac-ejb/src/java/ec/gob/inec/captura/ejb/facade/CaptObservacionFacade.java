/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.ejb.entidades.CaptObservacion;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jcerda
 */
@Stateless
public class CaptObservacionFacade extends AbstractFacade<CaptObservacion> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptObservacionFacade() {
        super(CaptObservacion.class);
    }
    
    public List<CaptObservacion> listarObservacionesPorCabYNumDet(Integer codCab, Integer numDet) throws Exception {
        String sql = "select c from CaptObservacion c "
                + "where c.codCab.idCab = :codCab and "
                + " c.numDet =:numDet  ORDER BY c.fechahoraEdicion DESC";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab).setParameter("numDet", numDet);
        List<CaptObservacion> lst = q.getResultList();
        return lst;
    }
    
    public List<CaptObservacion> listarObservacionesPorCabYClave(Integer codCab, String clave) throws Exception {
        String sql = "select c from CaptObservacion c "
                + "where c.codCab.idCab = :codCab and "
                + " c.clave =:clave  ORDER BY c.fechahoraEdicion DESC";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab).setParameter("clave", clave);
        List<CaptObservacion> lst = q.getResultList();
        return lst;
    }
    
    public List<CaptObservacion> listarObservacionesPorCabYNumDetYTipo(Integer codCab, Integer numDet, String tipo) throws Exception {
        String sql = "select c from CaptObservacion c "
                + "where c.codCab.idCab = :codCab and "
                + " c.numDet =:numDet  and "
                + " c.tipo =:tipo ORDER BY c.fechahoraEdicion DESC";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab).setParameter("numDet", numDet).setParameter("tipo", tipo);
        List<CaptObservacion> lst = q.getResultList();
        return lst;
    }
    
    public List<CaptObservacion> listarObservacionesPorCabYNumDetXTipoDif(Integer codCab, Integer numDet, String tipo) throws Exception {
        String sql = "select c from CaptObservacion c "
                + "where c.codCab.idCab = :codCab and "
                + " c.numDet =:numDet  and "
                + " c.tipo <>:tipo ORDER BY c.fechahoraEdicion DESC";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab).setParameter("numDet", numDet).setParameter("tipo", tipo);
        List<CaptObservacion> lst = q.getResultList();
        return lst;
    }
}
