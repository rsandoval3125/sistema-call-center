/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoCab;
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
public class AdmCorreoCabFacade extends AbstractFacade<AdmCorreoCab> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmCorreoCabFacade() {
        super(AdmCorreoCab.class);
    }

    public List<AdmCorreoCab> listarTodosActivos() throws Exception {
        String sql = " SELECT cd FROM AdmCorreoCab cd "
                + " WHERE cd.estadoLogico=true"
                + " ORDER BY cd.idCorreoCab asc";
        Query q = em.createQuery(sql);
        List<AdmCorreoCab> resultado = q.getResultList();
        return resultado;
    }

}
