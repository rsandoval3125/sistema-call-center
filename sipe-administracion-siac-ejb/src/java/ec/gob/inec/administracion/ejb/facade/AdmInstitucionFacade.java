/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.administracion.ejb.entidades.AdmInstitucion;
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
public class AdmInstitucionFacade extends AbstractFacade<AdmInstitucion> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmInstitucionFacade() {
        super(AdmInstitucion.class);
    }

    public List<AdmInstitucion> listarTodosActivos() throws Exception {
        String sql = "   SELECT i "
                + "  FROM AdmInstitucion i "
                + "where i.estadoLogico = true order by i.idInstitucion asc";
        Query q = em.createQuery(sql);
        List<AdmInstitucion> resultado = q.getResultList();
        return resultado;
    }
}
