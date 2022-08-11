/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dgarcia
 */
@Stateless
public class MetSeccionFacade extends AbstractFacade<MetSeccion> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetSeccionFacade() {
        super(MetSeccion.class);
    }

    public List<MetSeccion> listarSeccionesNivel1PorFormulario(Integer codFor_) throws Exception {
        String sql = "select fs.codSeccion from MetFormularioSeccion fs "
                + "where fs.codSeccion.codSeccionPadre is null and  fs.codSeccion.nivel=1 and "
                + "fs.codSeccion.estadoLogico=true and fs.estadoLogico=true and fs.codFormulario.idFormulario=:codFor_ "
                + "order by fs.codSeccion.orden";
        Query q = em.createQuery(sql);
        q.setParameter("codFor_", codFor_);
        List<MetSeccion> lst = q.getResultList();
        return lst;
    }

    public boolean seccionTieneHijos(Integer idSeccion_) throws Exception {
        String sql = "select count(s) from MetSeccion s "
                + "where s.estadoLogico=true and s.codSeccionPadre.idSeccion=:idSeccion_ ";
        Query q = em.createQuery(sql);
        q.setParameter("idSeccion_", idSeccion_);
        int c = ((Long) q.getSingleResult()).intValue();
        if (c > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean seccionTieneVariables(Integer idSeccion_) throws Exception {
        String sql = "select count(v) from MetVariable v "
                + "where v.estadoLogico=true and s.codSeccion.idSeccion=:idSeccion_ ";
        Query q = em.createQuery(sql);
        q.setParameter("idSeccion_", idSeccion_);
        int c = ((Long) q.getSingleResult()).intValue();
        if (c > 0) {
            return true;
        } else {
            return false;
        }

    }

    public List<MetSeccion> listarSeccionesHijasPorNivel(Integer idSeccion_, Integer nivel_) throws Exception {
        String sql = "select s from MetSeccion s "
                + "where s.estadoLogico=true and s.codSeccionPadre.idSeccion=:idSeccion_ and s.nivel=:nivel_ "
                + "order by s.orden";
        Query q = em.createQuery(sql);
        q.setParameter("idSeccion_", idSeccion_).setParameter("nivel_", nivel_);
        List<MetSeccion> lst = q.getResultList();
        return lst;
    }

    public List<MetSeccion> listarTodas() throws Exception {
        String sql = "select a from MetSeccion a "
                + "where a.estadoLogico=true "
                + "order by a.nombre";
        Query q = em.createQuery(sql);
        List<MetSeccion> lst = q.getResultList();
        return lst;
    }

    public List<MetSeccion> listarSeccionPorId(Integer idSeccion) throws Exception {
        String sql = "select f from MetSeccion f "
                + "where f.estadoLogico=true and f.idSeccion=:idSeccion"
                + " order by f.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("idSeccion", idSeccion);
        List<MetSeccion> lst = q.getResultList();
        return lst;
    }

    public List<MetSeccion> listarSoloSeccion() throws Exception {
        String sql = "select f from MetSeccion f "
                + "where f.estadoLogico=true and f.nivel=1 "
                + "order by f.nombre";
        Query q = em.createQuery(sql);
        List<MetSeccion> lst = q.getResultList();
        return lst;
    }

    public boolean accionSeccionPorNombreNivelOrden(String nombreSec, Integer nivelSec, Integer ordenSec) throws Exception {
        String sql = "select a from MetSeccion a "
                + "where a.estadoLogico = true and a.nombre =:nombreSec "
                + "and a.nivel=:nivelSec and a.orden =:ordenSec "
                + "order by a.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("nombreSec", nombreSec);
        q.setParameter("nivelSec", nivelSec);
        q.setParameter("ordenSec", ordenSec);
        List<MetSeccion> lst = q.getResultList();
        if (lst == null || lst.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean accionPorNombre(String nombreSec) throws Exception {
        String sql = "select a from MetSeccion a "
                + "where a.estadoLogico = true and a.nombre =:nombreSec "
                + "order by a.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("nombreSec", nombreSec);
        List<MetSeccion> lst = q.getResultList();
        if (lst == null || lst.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<MetSeccion> listarSecPorFomulario(Integer codFormulario) throws Exception {
        String sql = "select a from MetSeccion a , MetFormularioSeccion b "
                + "where a = b.codSeccion and a.nivel = 1 and b.estadoLogico = true "
                + "and b.codFormulario.idFormulario =:codFormulario "
                + "order by a.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("codFormulario", codFormulario);
        List<MetSeccion> lst = q.getResultList();
        return lst;
    }

    public List<MetSeccion> listarSeccionesNoAsignados(List<MetSeccion> lstSec) throws Exception {
        String sql = "select c from MetSeccion c "
                + "where c.estadoLogico = true and c.nivel = 1 "
                + "and c not in (:lstSec)"
                + "order by c.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("lstSec", lstSec);
        return q.getResultList();
    }

    public boolean accionSubsecciones(Integer codSeccion) throws Exception {
        System.out.println("cod " + codSeccion);
        String sql = "selec e from MetSeccion e "
                + "where e.estadoLogico = true and e.codSeccionPadre =:codSeccion "
                + "order by e.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("codSeccion", codSeccion);
        List<MetSeccion> lst = q.getResultList();
        if (lst == null || lst.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<MetSeccion> listarSeccionesTipoHPorFormulario(Integer codFormulario) throws Exception {
        List<MetSeccion> lsh = new ArrayList<>();
        List<MetSeccion> ls1 = listarSeccionesNivel1PorFormulario(codFormulario);
        if (!ls1.isEmpty()) {
            for (MetSeccion s1 : ls1) {
                if (s1.getTipo().contains("H")) {
                    lsh.add(s1);
                }
                if (seccionTieneHijos(s1.getIdSeccion())) {
                    List<MetSeccion> ls2 = listarSeccionesHijasPorNivel(s1.getIdSeccion(), 2);
                    if (!ls2.isEmpty()) {
                        for (MetSeccion s2 : ls2) {
                            if (s2.getTipo().contains("H")) {
                                lsh.add(s2);
                            }
                            if (seccionTieneHijos(s2.getIdSeccion())) {
                                List<MetSeccion> ls3 = listarSeccionesHijasPorNivel(s2.getIdSeccion(), 3);
                                if (!ls3.isEmpty()) {
                                    for (MetSeccion s3 : ls3) {
                                        if (s3.getTipo().contains("H")) {
                                            lsh.add(s3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return lsh;
    }
    public List<MetSeccion> listarSeccRecursivoForm(Integer codFormulario) throws Exception {
        String sql = " select a from MetSeccion a  "
                + "where a.estadoLogico=true  and a.idSeccion in(select codSeccion from MetFormularioSeccion where codFormulario.idFormulario=:codForm)  "
                + " or a.codSeccionPadre in(select codSeccion from MetFormularioSeccion where codFormulario.idFormulario=:codForm) "
                + "order by a.nombre ";
        Query q = em.createQuery(sql);
        q.setParameter("codForm", codFormulario);
        List<MetSeccion> lst = q.getResultList();
        return lst;
    }
}
