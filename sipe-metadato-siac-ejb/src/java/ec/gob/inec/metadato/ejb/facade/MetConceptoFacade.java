/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetConcepto;
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
public class MetConceptoFacade extends AbstractFacade<MetConcepto> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetConceptoFacade() {
        super(MetConcepto.class);
    }
    
    public List<MetConcepto> listarTodosActivos() throws Exception {
        String sql = "select c from MetConcepto c "
                + "where c.estadoLogico=true "
                + "order by c.nombre asc ";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
}
