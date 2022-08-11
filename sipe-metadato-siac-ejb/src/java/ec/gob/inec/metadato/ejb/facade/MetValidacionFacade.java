/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetValidacion;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vespinoza
 */
@Stateless
public class MetValidacionFacade extends AbstractFacade<MetValidacion> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetValidacionFacade() {
        super(MetValidacion.class);
    }

    public List<MetValidacion> listarValidacionXSeccion(Integer codSecc) throws Exception {
        String sql = "SELECT v "
                + "FROM MetValidacion v , MetVariable b"
                + "  WHERE v.codVar=b.idVar  "
                + "  AND  b.codSeccion.idSeccion=:codSecc "
                + "  AND v.estadoLogico = true "
                + "  AND b.estadoLogico = true"
                + "  ORDER BY v.codVar";
        Query q = em.createQuery(sql);
        q.setParameter("codSecc", codSecc);
        return q.getResultList();
    }

    public List<Object[]> listarValidacion(Integer codForm) throws Exception {
        String sql = "SELECT * from metadato.fn_listar_validacionnew(:codForm) ";
        Query q = em.createNativeQuery(sql);
        q.setParameter("codForm", codForm);
        return q.getResultList();
    }

}
