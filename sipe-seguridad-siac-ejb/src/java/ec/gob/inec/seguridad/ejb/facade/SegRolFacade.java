/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
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
public class SegRolFacade extends AbstractFacade<SegRol> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegRolFacade() {
        super(SegRol.class);
    }

    public List<SegRol> listarRolXUsuarioContrasenia(String nombre, String contrasenia) throws Exception {
        String sql = "   SELECT r "
                + "  FROM SegUsuario u, SegRolUsuario ru , SegRol r "
                + "  where u.estadoLogico = true "
                + "  and u.nombre =:nombre "
                + "  and u.clave =:contrasenia "
                + "  and u.idUsuario = ru.codUsuario "
                + "  and r.idRol = ru.codRol "
                + "  and r.estadoLogico = true ";
        Query q = em.createQuery(sql);
        q.setParameter("nombre", nombre);
        q.setParameter("contrasenia", contrasenia);
        return q.getResultList();
    }

    public List<SegRol> listarRolesNotInFase(MetOperativo codOper) throws Exception {
        String sql = "   SELECT r "
                + "  FROM SegRol r "
                + "where r.estadoLogico = true "
                + "and r.idRol not in (select foe.codRol from DisFaseOperativo foe "
                + "where foe.codOperativo =:codOper "
                + "and foe.estadoLogico = true "
                + "and foe.codRol is not null) ";
        Query q = em.createQuery(sql);
        q.setParameter("codOper", codOper);
        return q.getResultList();
    }

    public List<SegRol> listarRolesDeUsuarioAsiganado(Integer codUsuario) throws Exception {
        String sql = "SELECT r "
                + "  FROM SegRol r "
                + "where r.estadoLogico = true "
                + "and r.idRol in (select ru.codRol.idRol from SegRolUsuario ru "
                + "where ru.codUsuario.idUsuario =:codUsuario "
                + "and ru.estadoLogico = true )";

        Query q = em.createQuery(sql);
        q.setParameter("codUsuario", codUsuario);
        return q.getResultList();
    }

    public List<SegRol> listarRolesDeUsuarioNoAsiganado(List<SegRol> roles) throws Exception {
        String sql = "select r from SegRol r where r not in (:roles) and r.estadoLogico = true order by r.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("roles", roles);
        return q.getResultList();
    }

    /*  public List<SegRol> listarRolesPorAplicacion(Integer codApl) throws Exception {
        String sql = "   SELECT r "
                + "  FROM SegRol r "
                + "where r.estadoLogico = true "
                + "and r.idRol in (select ru.codRol.idRol from SegRolUsuario ru "
                + "where ru.codApl.idApl =:codApl "
                + "and ru.estadoLogico = true )";

        Query q = em.createQuery(sql);
        q.setParameter("codApl", codApl);
        return q.getResultList();
    }

    public List<SegRol> listarRolesDeUsuarioNoAsignadosPorAplicacion(List<SegRol> roles, Integer codApl) throws Exception {
        String sql = "select r from SegRol r where r not in (:roles) and r.estadoLogico = true and r.idRol in (select ru.codRol.idRol from SegRolUsuario ru where ru.codApl.idApl =:codApl and ru.estadoLogico = true) order by r.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("roles", roles).setParameter("codApl", codApl);
        return q.getResultList();
    }*/
    public List<SegRol> listarRolesActivos() throws Exception {
        String sql = "   SELECT r "
                + "  FROM SegRol r "
                + "where r.estadoLogico = true order by r.idRol asc ";

        Query q = em.createQuery(sql);
        return q.getResultList();
    }

    public Integer tieneCargasPendientes(Integer idRol) throws Exception {
        String sql = "SELECT count(ct)\n"
                + "  FROM distribucion.dis_equipo_trabajo_detalle etd , distribucion.dis_carga_trabajo ct , seguridad.seg_rol_usuario ru ,  seguridad.seg_rol r\n"
                + " where r.id_rol = :idRol\n"
                + " and ru.cod_rol = r.id_rol\n"
                + " and ru.estado_logico = true\n"
                + " and ru.id_rol_usu = etd.cod_rol_usu\n"
                + " and etd.estado_logico = true\n"
                + " and ct.cod_equ_tra_det = etd.id_equ_tra_det\n"
                + " and ct.estado_logico = true\n"
                + " and ct.estado_asignacion in (select id_catalogo FROM metadato.met_catalogo c, metadato.met_tipo_catalogo tip \n"
                + "				where c.alias not in ('EST_TERM_DISP','EST_TERM')\n"
                + "				and tip.id_tipo_catalogo = c.cod_tipo_catalogo and tip.alias = 'EST_ASIG')";
        Query q = em.createNativeQuery(sql);
        q.setParameter("idRol", idRol);
        return ((Number) q.getSingleResult()).intValue();
    }
}
