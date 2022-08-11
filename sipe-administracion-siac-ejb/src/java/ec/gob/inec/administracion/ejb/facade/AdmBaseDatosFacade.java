/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.administracion.ejb.entidades.AdmBaseDatos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author jaraujo
 */
@Stateless
public class AdmBaseDatosFacade extends AbstractFacade<AdmBaseDatos> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmBaseDatosFacade() {
        super(AdmBaseDatos.class);
    }
    
    
    public List<AdmBaseDatos> listarTodosActivos() throws Exception {
        String sql = " SELECT a "
                + "  FROM AdmBaseDatos a "
                + "  where a.estadoLogico = true order by a.nombre asc";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
}
