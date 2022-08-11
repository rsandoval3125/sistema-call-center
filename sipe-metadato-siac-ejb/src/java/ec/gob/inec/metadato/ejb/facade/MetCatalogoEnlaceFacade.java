/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogoEnlace;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetCatalogoEnlaceFacade extends AbstractFacade<MetCatalogoEnlace> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetCatalogoEnlaceFacade() {
        super(MetCatalogoEnlace.class);
    }
    
}
