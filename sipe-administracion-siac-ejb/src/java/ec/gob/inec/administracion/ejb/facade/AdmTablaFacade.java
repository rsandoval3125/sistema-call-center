/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;


import ec.gob.inec.administracion.ejb.entidades.AdmTabla;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mchasiguasin
 */
@Stateless
public class AdmTablaFacade extends AbstractFacade<AdmTabla> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmTablaFacade() {
        super(AdmTabla.class);
    }

    public List<AdmTabla> listarTodosActivos() throws Exception {
        String sql = "   SELECT d "
                + "  FROM AdmTabla d "
                + " order by d.nombre asc";
        Query q = em.createQuery(sql);
        List<AdmTabla> resultado = q.getResultList();
        return resultado;
    }
}
