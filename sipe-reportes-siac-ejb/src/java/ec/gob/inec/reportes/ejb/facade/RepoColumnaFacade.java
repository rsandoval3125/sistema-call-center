/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.reportes.ejb.entidades.RepoColumna;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jaraujo
 */
@Stateless
public class RepoColumnaFacade extends AbstractFacade<RepoColumna> {

    @PersistenceContext(unitName = "sipe-reportes-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RepoColumnaFacade() {
        super(RepoColumna.class);
    }

}
