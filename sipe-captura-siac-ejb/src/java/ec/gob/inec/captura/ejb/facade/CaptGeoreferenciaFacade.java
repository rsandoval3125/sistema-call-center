/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.ejb.entidades.CaptGeoreferencia;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jcerda
 */
@Stateless
public class CaptGeoreferenciaFacade extends AbstractFacade<CaptGeoreferencia> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptGeoreferenciaFacade() {
        super(CaptGeoreferencia.class);
    }
    
    
    
}
