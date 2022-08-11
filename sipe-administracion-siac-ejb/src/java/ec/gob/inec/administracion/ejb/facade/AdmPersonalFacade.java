/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajo;
import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisFaseOperativo;
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
public class AdmPersonalFacade extends AbstractFacade<AdmPersonal> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmPersonalFacade() {
        super(AdmPersonal.class);
    }

    // //   public List<AdmPersonal> listarPersonalEquipoAsignado(DisFaseOperativo disFaseOperativo1) throws Exception {
//        String sql = " SELECT ap "
//                + "  FROM DisEquipoTrabajoDetalle etd, SegRolUsuario ru, SegUsuario u , AdmPersonal ap "
//                + "  where etd.codFasOpe =:disFaseOperativo1 "
//                + "  and etd.codRolUsu = ru.idRolUsu "
//                + "  and etd.estadoLogico = true "
//                + "  and u.idUsuario = ru.codUsuario "
//                + "  and u.codPersonal = ap.idPersonal "
//                + "  order by ap.primerNombre, ap.primerApellido asc ";
//        Query q = em.createQuery(sql);
//        q.setParameter("disFaseOperativo1", disFaseOperativo1);     
//        return q.getResultList();
//    }
    public List<AdmPersonal> listarPersonalEquipoAsignado(DisFaseOperativo disFaseOperativo1) throws Exception {
        String sql = " SELECT ap "
                + "  FROM DisEquipoTrabajoDetalle etd, SegRolUsuario ru, SegUsuario u , AdmPersonal ap "
                + "  , DisFaseOperativo  foe , MetCatalogo cat "
                + "  where etd.codFasOpe =:disFaseOperativo1 "
                + "  and etd.codRolUsu = ru.idRolUsu "
                + "  and etd.estadoLogico = true "
                + "  and u.idUsuario = ru.codUsuario "
                + "  and u.codPersonal = ap.idPersonal "
                + "  and foe.idFasOpe = etd.codFasOpe "
                + "  and cat.idCatalogo = foe.codAsignacionUsuario "
                + "  and cat.alias = 'TIP_ASI_DIS' "
                + "  order by ap.primerNombre, ap.primerApellido asc ";
        Query q = em.createQuery(sql);
        q.setParameter("disFaseOperativo1", disFaseOperativo1);
        return q.getResultList();
    }

    public List<AdmPersonal> listarPersonalEquipoAsignadoEquipo(DisFaseOperativo disFaseOperativo1, DisEquipoTrabajo disEquipoTrabajo) throws Exception {
        String sql = " SELECT ap "
                + "  FROM DisEquipoTrabajoDetalle etd, SegRolUsuario ru, SegUsuario u , AdmPersonal ap "
                + "  where etd.codFasOpe =:disFaseOperativo1 "
                + "  and etd.codRolUsu = ru.idRolUsu "
                + "  and etd.estadoLogico = true "
                + "  and u.idUsuario = ru.codUsuario "
                + "  and u.codPersonal = ap.idPersonal "
                + "  and (etd.codEquTraCab =:disEquipoTrabajoCab or etd.codEquTraCab is null) "
                + "  order by ap.primerNombre, ap.primerApellido asc ";
        Query q = em.createQuery(sql);
        q.setParameter("disFaseOperativo1", disFaseOperativo1);
        q.setParameter("disEquipoTrabajoCab", disEquipoTrabajo);
        return q.getResultList();
    }

    public List<AdmPersonal> listarPersonalEquipoDisponible(SegRol codRol) throws Exception {
        String sql = " SELECT ap "
                + "  FROM AdmPersonal ap, SegUsuario u , SegRolUsuario ru "
                + "  where ap.idPersonal = u.codPersonal "
                + "  and ru.codUsuario = u.idUsuario "
                + "  and ru.codRol =:codRol "
                + "  and ru.estadoLogico = true "
                + "  order by ap.primerNombre, ap.primerApellido asc ";
        Query q = em.createQuery(sql);
        q.setParameter("codRol", codRol);
        return q.getResultList();
    }

    public List<AdmPersonal> listarPersonalEquipoNoAsignado(List<AdmPersonal> lstAdmPer, SegRol codRol) throws Exception {
        String sql = " SELECT ap "
                + "  FROM AdmPersonal ap, SegUsuario u , SegRolUsuario ru "
                + "  where ap.idPersonal = u.codPersonal "
                + "  and ru.codUsuario = u.idUsuario "
                + "  and ru.codRol =:codRol "
                + "  and ru.estadoLogico = true "
                + " and ap not in (:lstAdmPer) "
                + "  order by ap.primerNombre, ap.primerApellido asc ";
        Query q = em.createQuery(sql);
        q.setParameter("codRol", codRol);
        q.setParameter("lstAdmPer", lstAdmPer);
        return q.getResultList();
    }

    public List<AdmPersonal> listarPersonalEquipoDisponibleTodos(List<AdmPersonal> lstAdmPer, DisFaseOperativo disFaseOperativo1, DisEquipoTrabajo disEquipoTrabajo, SegRol codRol) throws Exception {
        String sql = " SELECT ap "
                + "  FROM AdmPersonal ap, SegUsuario u , SegRolUsuario ru "
                + "  where ap.idPersonal = u.codPersonal "
                + "  and ru.codUsuario = u.idUsuario "
                + "  and ru.codRol =:codRol "
                + "  and ru.estadoLogico = true "
                + "  and ap not in (:lstAdmPer) "
                + "  and ap.idPersonal not in ( "
                + "  SELECT ap.idPersonal "
                + "  FROM DisEquipoTrabajoDetalle etd, SegRolUsuario ru, SegUsuario u , AdmPersonal ap "
                + "  where etd.codFasOpe =:disFaseOperativo1 "
                + "  and etd.codRolUsu = ru.idRolUsu "
                + "  and etd.estadoLogico = true "
                + "  and u.idUsuario = ru.codUsuario "
                + "  and u.codPersonal = ap.idPersonal "
                + "  and etd.codEquTraCab <>:disEquipoTrabajoCab "
                + "  ) "
                + "  order by ap.primerNombre, ap.primerApellido asc ";
        Query q = em.createQuery(sql);
        q.setParameter("codRol", codRol);
        q.setParameter("disFaseOperativo1", disFaseOperativo1);
        q.setParameter("disEquipoTrabajoCab", disEquipoTrabajo);
        q.setParameter("lstAdmPer", lstAdmPer);
        return q.getResultList();
    }

    public AdmPersonal listarPersonalXRolUsu(Integer codRolUsu) throws Exception {
        String sql = " SELECT ap "
                + "  FROM AdmPersonal ap, SegUsuario u , SegRolUsuario ru "
                + "  where ap.idPersonal = u.codPersonal "
                + "  and ru.codUsuario = u.idUsuario "
                + "  and ru.idRolUsu =:codRolUsu "
                + "  and ru.estadoLogico = true "
                + "  order by ap.primerNombre, ap.primerApellido asc ";
        Query q = em.createQuery(sql);
        q.setParameter("codRolUsu", codRolUsu);
        List<AdmPersonal> resultado = q.getResultList();
        if (resultado.size() > 0) {
            return (AdmPersonal) resultado.get(0);
        } else {
            return null;
        }
    }

    public List<AdmPersonal> listarTodosActivos() throws Exception {
        String sql = "   SELECT p "
                + "  FROM AdmPersonal p "
                + "where p.estadoLogico = true order by p.idPersonal asc";
        Query q = em.createQuery(sql);
        List<AdmPersonal> resultado = q.getResultList();
        return resultado;
    }

    public Integer tieneCargasPendientes(Integer idPersonal) throws Exception {
        String sql = "SELECT count(ct)\n"
                + "  FROM distribucion.dis_equipo_trabajo_detalle etd , distribucion.dis_carga_trabajo ct , seguridad.seg_rol_usuario ru , seguridad.seg_usuario u , administracion.adm_personal p\n"
                + "  where p.id_personal = :idPersonal\n"
                + "  and u.cod_personal = p.id_personal\n"
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
        q.setParameter("idPersonal", idPersonal);
        return ((Number) q.getSingleResult()).intValue();
    }
}
