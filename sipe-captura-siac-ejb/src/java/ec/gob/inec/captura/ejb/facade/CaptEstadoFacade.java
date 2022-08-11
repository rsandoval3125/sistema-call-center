/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.ejb.entidades.CaptEstado;
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
public class CaptEstadoFacade extends AbstractFacade<CaptEstado> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptEstadoFacade() {
        super(CaptEstado.class);
    }
    
    public List<CaptEstado> listarEstadoPorCabYNumDet(Integer codCab, Integer numDet) throws Exception {
        String sql = "SELECT c FROM CaptEstado c "
                + " where c.codCab.idCab =:codCab "
                + " and c.numDet =:numDet";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab);
        q.setParameter("numDet", numDet);
        List<CaptEstado> lst = q.getResultList();
        return lst;    
    }
}
