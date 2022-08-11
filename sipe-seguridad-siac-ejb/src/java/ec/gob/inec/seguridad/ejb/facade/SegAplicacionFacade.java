/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegAplicacion;
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
public class SegAplicacionFacade extends AbstractFacade<SegAplicacion> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegAplicacionFacade() {
        super(SegAplicacion.class);
    }

    public List<SegAplicacion> listarTodosActivos() throws Exception {
        String sql = " SELECT a "
                + "  FROM SegAplicacion a "
                + "  where a.estadoLogico = true order by a.nombre asc";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public List<SegAplicacion> listaAplicacionesPorUsuarioAsignado(Integer idUsuario) throws Exception{
        String sql = "select distinct s.codActividad.codApl from ProSeguimiento s where s.codUsuAsignado.idUsuario=:idUsuario and s.codActividad.codApl.alias not in ('NA') ";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("idUsuario", idUsuario);
        return q.getResultList();
    }

}
