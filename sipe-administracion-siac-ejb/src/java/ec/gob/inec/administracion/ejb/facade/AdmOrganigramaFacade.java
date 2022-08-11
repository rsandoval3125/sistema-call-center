/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.administracion.ejb.entidades.AdmOrganigrama;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lponce
 */
@Stateless
public class AdmOrganigramaFacade extends AbstractFacade<AdmOrganigrama> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmOrganigramaFacade() {
        super(AdmOrganigrama.class);
    }

    public List<AdmOrganigrama> listarTodosActivos() throws Exception {
        String sql = "   SELECT o "
                + "  FROM AdmOrganigrama o "
                + "where o.estadoLogico = true order by o.idOrganigrama asc";
        Query q = em.createQuery(sql);
        List<AdmOrganigrama> resultado = q.getResultList();
        return resultado;
    }

    public List<AdmOrganigrama> listarTodosActivosPorInstitucion(Integer idInstitucion) throws Exception {
        String sql = "   SELECT o "
                + "  FROM AdmOrganigrama o "
                + "where o.estadoLogico = true and o.codInstitucion.idInstitucion=:idInstitucion order by o.idOrganigrama asc";
        Query q = em.createQuery(sql);
        q.setParameter("idInstitucion", idInstitucion);
        List<AdmOrganigrama> resultado = q.getResultList();
        return resultado;
    }

    public List<AdmOrganigrama> listarTodosActivosSinUno(Integer idOrganigrama) throws Exception {
        String sql = "   SELECT o "
                + "  FROM AdmOrganigrama o "
                + "where o.estadoLogico = true and o.idOrganigrama <> :idOrganigrama";
        Query q = em.createQuery(sql);
        q.setParameter("idOrganigrama", idOrganigrama);
        List<AdmOrganigrama> resultado = q.getResultList();
        return resultado;
    }
}
