/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.reportes.ejb.entidades.RepoSubreporte;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jaraujo
 */
@Stateless
public class RepoSubreporteFacade extends AbstractFacade<RepoSubreporte> {

    @PersistenceContext(unitName = "sipe-reportes-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RepoSubreporteFacade() {
        super(RepoSubreporte.class);
    }

    public Object[] ejecutarFuncion(String procedimiento, String argumento) {
        //System.out.println("Proc:"+procedimiento);
        //System.out.println("Arg:"+argumento);
        Query q = em.createNativeQuery("select proConexion1,sentencia, tablatemporal, dropTable from reportes.func_recuperar_sql (:proc ,:argumen)");
        q.setParameter("proc", procedimiento);
        q.setParameter("argumen", argumento);
        return (Object[]) q.getSingleResult();
    }

}
