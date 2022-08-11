/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
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
public class AdmPeriodoFacade extends AbstractFacade<AdmPeriodo> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmPeriodoFacade() {
        super(AdmPeriodo.class);
    }

    public List<AdmPeriodo> listarTodosActivos() throws Exception {
        String sql = "   SELECT p "
                + "  FROM AdmPeriodo p "
                + "where p.estadoLogico = true order by p.idPeriodo asc";
        Query q = em.createQuery(sql);
        List<AdmPeriodo> resultado = q.getResultList();
        return resultado;
    }
}
