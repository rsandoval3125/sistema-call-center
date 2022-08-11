/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.ejb.entidades.CaptDatosTemp;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jcerda
 */
@Stateless
public class CaptDatosTempFacade extends AbstractFacade<CaptDatosTemp> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptDatosTempFacade() {
        super(CaptDatosTemp.class);
    }
    
}
