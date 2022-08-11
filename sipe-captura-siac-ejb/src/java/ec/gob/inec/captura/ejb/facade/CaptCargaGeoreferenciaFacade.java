/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.ejb.entidades.CaptCargaGeoreferencia;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jcerda
 */
@Stateless
public class CaptCargaGeoreferenciaFacade extends AbstractFacade<CaptCargaGeoreferencia> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptCargaGeoreferenciaFacade() {
        super(CaptCargaGeoreferencia.class);
    }
    
}
