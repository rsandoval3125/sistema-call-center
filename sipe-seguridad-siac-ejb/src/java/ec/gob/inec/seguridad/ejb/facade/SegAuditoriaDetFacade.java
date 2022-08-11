/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaDet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lponce
 */
@Stateless
public class SegAuditoriaDetFacade extends AbstractFacade<SegAuditoriaDet> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegAuditoriaDetFacade() {
        super(SegAuditoriaDet.class);
    }
    
}
