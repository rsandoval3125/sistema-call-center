/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.administracion.ejb.entidades.AdmOperacionEstadistica;
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
public class AdmOperacionEstadisticaFacade extends AbstractFacade<AdmOperacionEstadistica> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmOperacionEstadisticaFacade() {
        super(AdmOperacionEstadistica.class);
    }

    public List<AdmOperacionEstadistica> listarTodosActivos() throws Exception {
        String sql = "   SELECT op "
                + "  FROM AdmOperacionEstadistica op "
                + "where op.estadoLogico = true order by op.idOpeEst asc";
        Query q = em.createQuery(sql);
        List<AdmOperacionEstadistica> resultado = q.getResultList();
        return resultado;
    }

    public List<AdmOperacionEstadistica> listarTodosActivosPorInstitucionYOrganigrama(Integer idInstitucion, Integer idOrganigrama) throws Exception {
        String sql = "   SELECT op "
                + "  FROM AdmOperacionEstadistica op "
                + "where op.estadoLogico = true and op.codInstitucion.idInstitucion=:idInstitucion and op.codOrganigrama.idOrganigrama=:idOrganigrama order by op.idOpeEst asc";
        Query q = em.createQuery(sql);
        q.setParameter("idInstitucion", idInstitucion);
        q.setParameter("idOrganigrama", idOrganigrama);
        List<AdmOperacionEstadistica> resultado = q.getResultList();
        return resultado;
    }

    public List<AdmOperacionEstadistica> listarTodosActivosSinUno(Integer idOpeEst) throws Exception {
        String sql = "   SELECT op "
                + "  FROM AdmOperacionEstadistica op "
                + "where op.estadoLogico = true and op.idOpeEst <> :idOpeEst";
        Query q = em.createQuery(sql);
        q.setParameter("idOpeEst", idOpeEst);
        List<AdmOperacionEstadistica> resultado = q.getResultList();
        return resultado;
    }
}
