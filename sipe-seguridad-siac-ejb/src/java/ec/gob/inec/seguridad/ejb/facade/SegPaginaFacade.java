/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
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
public class SegPaginaFacade extends AbstractFacade<SegPagina> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegPaginaFacade() {
        super(SegPagina.class);
    }

    public List<SegPagina> listarPaginasActivasPorAplicacion(Integer idApl) throws Exception {
        String sql = "   SELECT p "
                + "  FROM SegPagina p "
                + "where p.estadoLogico = true and p.codApl.idApl=:idApl  order by nombre asc";

        Query q = em.createQuery(sql);
        q.setParameter("idApl", idApl);
        List<SegPagina> resultado = q.getResultList();
        return resultado;
    }

    public List<SegPagina> listarTodosActivos() throws Exception {
        String sql = "   SELECT p "
                + "  FROM SegPagina p LEFT JOIN FETCH p.codEstadoPagina LEFT JOIN FETCH p.codTipo LEFT JOIN FETCH p.codApl"
                + " WHERE p.estadoLogico = true order by p.idPag asc";

        Query q = em.createQuery(sql);
        List<SegPagina> resultado = q.getResultList();
        return resultado;
    }

    public List<SegPagina> listarTodosActivosSinUno(Integer idPag) throws Exception {
        String sql = "   SELECT p "
                + "  FROM SegPagina p "
                + "where p.estadoLogico = true and idPag<>:idPag  order by nombre asc";
        Query q = em.createQuery(sql);
        q.setParameter("idPag", idPag);
        List<SegPagina> resultado = q.getResultList();

        return resultado;
    }
    }
