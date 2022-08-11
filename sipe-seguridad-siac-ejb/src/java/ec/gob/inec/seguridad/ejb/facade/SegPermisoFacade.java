/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegPagina;
import ec.gob.inec.seguridad.ejb.entidades.SegPermiso;
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
public class SegPermisoFacade extends AbstractFacade<SegPermiso> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegPermisoFacade() {
        super(SegPermiso.class);
    }

    public List<SegPermiso> listarPermisosDeRolAsignados(Integer codRol) throws Exception {
        String sql = "SELECT r "
                + "  FROM SegPermiso r LEFT JOIN FETCH r.codAccion LEFT JOIN FETCH r.codPag "
                + "where r.estadoLogico = true "
                + "and r.idPermiso in (select ru.codPermiso.idPermiso from SegRolPermiso ru "
                + "where ru.codRol.idRol =:codRol "
                + "and ru.estadoLogico = true ) order by r.alias";

        Query q = em.createQuery(sql);
        q.setParameter("codRol", codRol);
        return q.getResultList();
    }

    public List<SegPermiso> listarPermisosDeRolNoAsignados(List<SegPermiso> permisos) throws Exception {
        String sql = "select r from SegPermiso r LEFT JOIN FETCH r.codAccion LEFT JOIN FETCH r.codPag where r not in (:permisos) and r.estadoLogico = true order by r.alias";
        Query q = em.createQuery(sql);
        q.setParameter("permisos", permisos);
        return q.getResultList();
    }

    public List<SegPermiso> listarPermisosActivos() throws Exception {
        String sql = "   SELECT r "
                + "  FROM SegPermiso r LEFT JOIN FETCH r.codAccion LEFT JOIN FETCH r.codPag"
                + " where r.estadoLogico = true order by r.alias asc ";

        Query q = em.createQuery(sql);
        return q.getResultList();
    }

    public List<SegPermiso> listarPermisosDeRolNoAsignadosPorAplicacion(List<SegPermiso> permisos, Integer idApl) throws Exception {
        String sql = "select r from SegPermiso r  LEFT JOIN FETCH r.codAccion LEFT JOIN FETCH r.codPag where r not in (:permisos) and r.estadoLogico = true and r.codPag.codApl.idApl=:idApl order by r.alias";
        Query q = em.createQuery(sql);
        q.setParameter("permisos", permisos);
        q.setParameter("idApl", idApl);
        return q.getResultList();
    }

    public List<SegPermiso> listarPermisosActivosPorAplicacion(Integer idApl) throws Exception {
        String sql = " SELECT p "
                + "  FROM SegPermiso p LEFT JOIN FETCH p.codAccion LEFT JOIN FETCH p.codPag"
                + "where p.estadoLogico = true  and p.codPag.codApl.idApl=:idApl order by p.alias asc ";

        Query q = em.createQuery(sql);
        q.setParameter("idApl", idApl);
        return q.getResultList();
    }

    public List<SegPermiso> listarPermisosPorPagina(List<SegPagina> paginas) throws Exception {
        System.out.println("************************+listarPermisosPorPagina");
        String sql = "SELECT r "
                + "  FROM SegPermiso r LEFT JOIN FETCH r.codAccion LEFT JOIN FETCH r.codPag "
                + "where r.codPag in(:paginas)  order by r.codAccion.nombre";

        Query q = em.createQuery(sql);
        q.setParameter("paginas", paginas);
        return q.getResultList();
    }

    public List<SegPermiso> listarPermisosPorUsuarioYPagina(String usuario, Integer idPag) throws Exception {
        String sql = "SELECT p "
                + " FROM SegRolUsuario ru, SegRolPermiso rp, SegPermiso p left join fetch p.codAccion a left join fetch p.codPag pag left join fetch a.codTipoCatalogo"
                + " WHERE ru.codUsuario.nombre=:usuario and ru.codRol=rp.codRol"
                + " and ru.estadoLogico=true and rp.estadoLogico=true and p.estadoLogico=true"
                + " and rp.codPermiso.idPermiso=p.idPermiso and p.codPag.idPag=:idPag";

        Query q = em.createQuery(sql);
        q.setParameter("usuario", usuario);
        q.setParameter("idPag", idPag);
        return q.getResultList();
    }
}
