/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.administracion.ejb.entidades.AdmDispositivo;
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
public class AdmDispositivoFacade extends AbstractFacade<AdmDispositivo> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmDispositivoFacade() {
        super(AdmDispositivo.class);
    }

    public List<AdmDispositivo> listarTodosActivos() throws Exception {
        String sql = "   SELECT d "
                + "  FROM AdmDispositivo d "
                + "where d.estadoLogico = true order by d.idDispositivo asc";
        Query q = em.createQuery(sql);
        List<AdmDispositivo> resultado = q.getResultList();
        return resultado;
    }

    public Integer tieneCargasPendientes(String serie) throws Exception {
        String sql = "select distinct count(ct)\n"
                + "  from distribucion.dis_carga_trabajo ct, distribucion.dis_equipo_trabajo_detalle etd, administracion.adm_dispositivo d \n"
                + "  where  ct.cod_equ_tra_det=etd.id_equ_tra_det and etd.cod_dispositivo=d.id_dispositivo and etd.estado_logico=true and ct.estado_logico=true and d.estado_logico=true \n"
                + "  and d.serie=:serie \n"
                + "  and ct.estado_asignacion in (select id_catalogo FROM metadato.met_catalogo c, metadato.met_tipo_catalogo tip \n"
                + "				where c.alias not in ('EST_TERM_DISP','EST_TERM')\n"
                + "				and tip.id_tipo_catalogo = c.cod_tipo_catalogo and tip.alias = 'EST_ASIG')";
        Query q = em.createNativeQuery(sql);
        q.setParameter("serie", serie);
        return ((Number) q.getSingleResult()).intValue();
    }

    public Integer tieneMiembroDeEquipo(String serie) throws Exception {
        String sql = "select distinct count(etd)\n"
                + "  from distribucion.dis_equipo_trabajo_detalle etd, administracion.adm_dispositivo d \n"
                + "  where   etd.cod_dispositivo=d.id_dispositivo and etd.estado_logico=true and d.estado_logico=true and \n"
                + "  d.serie=:serie";
        Query q = em.createNativeQuery(sql);
        q.setParameter("serie", serie);
        return ((Number) q.getSingleResult()).intValue();
    }
}
