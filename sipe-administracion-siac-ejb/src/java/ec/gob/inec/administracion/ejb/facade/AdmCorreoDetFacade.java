/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Stateless
public class AdmCorreoDetFacade extends AbstractFacade<AdmCorreoDet> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmCorreoDetFacade() {
        super(AdmCorreoDet.class);
    }

    public List<AdmCorreoDet> consultarPorIdCorreoCab(Integer idCorreoCab) throws Exception {
        String sql = "SELECT cd "
                + "  FROM AdmCorreoDet cd "
                + " WHERE cd.estadoLogico=true and cd.codCorreoCab.idCorreoCab=:idCorreoCab";
        Query q = em.createQuery(sql);
        q.setParameter("idCorreoCab", idCorreoCab);
        List<AdmCorreoDet> resultado = q.getResultList();
        return resultado;
    }

}
