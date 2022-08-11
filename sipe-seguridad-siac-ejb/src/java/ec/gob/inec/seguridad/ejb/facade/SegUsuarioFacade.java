/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegUsuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUtil;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author lponce
 */
@Stateless
public class SegUsuarioFacade extends AbstractFacade<SegUsuario> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegUsuarioFacade() {
        super(SegUsuario.class);
    }

    public List<Object[]> listarSoloActivos() throws Exception {
        String sql = " SELECT u.id_usuario, u.nombre, u.cod_personal, p.primer_nombre ||' '|| p.segundo_nombre ||' '|| p.primer_apellido ||' '|| p.segundo_apellido, u.fechahora_ini, u.fechahora_fin, u.cod_estado_usuario, c.nombre as estado \n"
                + " FROM seguridad.seg_usuario u, administracion.adm_personal p, metadato.met_catalogo c\n"
                + " WHERE u.estado_logico=true AND u.cod_personal=p.id_personal AND u.cod_estado_usuario=c.id_catalogo\n"
                + " ORDER BY u.id_usuario asc";
        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    public List<SegUsuario> listarTodosActivos() throws Exception {
        String sql = " SELECT u "
                + "  FROM SegUsuario u LEFT JOIN FETCH u.codPersonal p LEFT JOIN FETCH u.codEstadoUsuario e "
                // + " LEFT JOIN FETCH e.codTipoCatalogo LEFT JOIN FETCH e.codPadre1 LEFT JOIN FETCH e.codPadre2 LEFT JOIN FETCH e.codPadre3"
                + " LEFT JOIN FETCH p.codInstitucion LEFT JOIN FETCH p.codOrganigrama LEFT JOIN FETCH p.codDpa LEFT JOIN FETCH p.codNivelInstruccion LEFT JOIN FETCH p.codNivelSalarial LEFT JOIN FETCH p.codCargo LEFT JOIN FETCH p.codTipoContrato"
                + "  where u.estadoLogico = true order by u.idUsuario asc";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }

    public SegUsuario buscarPorCorreo(String correo) throws Exception {
        String sql = " SELECT u "
                + "  FROM SegUsuario u "
                + "  where u.estadoLogico = true and lower(u.codPersonal.correoInstitucional)=:correo or lower(u.codPersonal.correoPersonal)=:correo";
        Query q = em.createQuery(sql);
        q.setParameter("correo", correo.toLowerCase());
        return (SegUsuario) q.getSingleResult();
    }

    public Integer tieneCargasPendientes(Integer idUsuario) throws Exception {
        String sql = "SELECT count(ct)\n"
                + "  FROM distribucion.dis_equipo_trabajo_detalle etd , distribucion.dis_carga_trabajo ct , seguridad.seg_rol_usuario ru , seguridad.seg_usuario u\n"
                + "  where u.id_usuario = :idUsuario\n"
                + "  and u.id_usuario = ru.cod_usuario\n"
                + "  and ru.estado_logico = true\n"
                + "  and ru.id_rol_usu = etd.cod_rol_usu\n"
                + "  and etd.estado_logico = true\n"
                + "  and ct.cod_equ_tra_det = etd.id_equ_tra_det\n"
                + "  and ct.estado_logico = true\n"
                + "  and ct.estado_asignacion in (select id_catalogo FROM metadato.met_catalogo c, metadato.met_tipo_catalogo tip \n"
                + "				where c.alias not in ('EST_TERM_DISP','EST_TERM')\n"
                + "				and tip.id_tipo_catalogo = c.cod_tipo_catalogo and tip.alias = 'EST_ASIG')";
        Query q = em.createNativeQuery(sql);
        q.setParameter("idUsuario", idUsuario);
        return ((Number) q.getSingleResult()).intValue();
    }

    public Integer tieneCargasPendientes(Integer idUsuario, Integer idRol) throws Exception {
        String sql = "SELECT count(ct)\n"
                + "  FROM distribucion.dis_equipo_trabajo_detalle etd , distribucion.dis_carga_trabajo ct , seguridad.seg_rol_usuario ru\n"
                + "  where  ru.cod_rol = :idRol \n"
                + "  and ru.cod_usuario = :idUsuario\n"
                + "  and ru.estado_logico = true\n"
                + "  and ru.id_rol_usu = etd.cod_rol_usu\n"
                + "  and etd.estado_logico = true\n"
                + "  and ct.cod_equ_tra_det = etd.id_equ_tra_det\n"
                + "  and ct.estado_logico = true\n"
                + "  and ct.estado_asignacion in (select id_catalogo FROM metadato.met_catalogo c, metadato.met_tipo_catalogo tip \n"
                + "				where c.alias not in ('EST_TERM_DISP','EST_TERM')\n"
                + "				and tip.id_tipo_catalogo = c.cod_tipo_catalogo and tip.alias = 'EST_ASIG');";
        Query q = em.createNativeQuery(sql);
        q.setParameter("idUsuario", idUsuario);
        q.setParameter("idRol", idRol);
        return ((Number) q.getSingleResult()).intValue();
    }
}
