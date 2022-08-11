/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lponce
 */
@Stateless
public class AdmParametroGlobalFacade extends AbstractFacade<AdmParametroGlobal> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmParametroGlobalFacade() {
        super(AdmParametroGlobal.class);
    }

    public List<AdmParametroGlobal> listarTodosActivos() throws Exception {
        String sql = "   SELECT pg "
                + "  FROM AdmParametroGlobal pg "
                + "where pg.estadoLogico = true order by pg.idParametro asc";
        Query q = em.createQuery(sql);
        List<AdmParametroGlobal> resultado = q.getResultList();
        return resultado;
    }
}
