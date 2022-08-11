/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.administracion.ejb.entidades.AdmPersonal;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import ec.gob.inec.seguridad.ejb.entidades.SegRolUsuario;
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
public class SegRolUsuarioFacade extends AbstractFacade<SegRolUsuario> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegRolUsuarioFacade() {
        super(SegRolUsuario.class);
    }
    
    public SegRolUsuario buscarRolUsuarioFase(AdmPersonal vcodPersonal, SegRol vcodRol) throws Exception {
        String sql = " SELECT ru "
                + "  FROM SegUsuario u , SegRolUsuario ru  "
                + "  where u.codPersonal =:vcodPersonal "
                + "  and u.idUsuario = ru.codUsuario "
                + "  and ru.codRol =:vcodRol "
                + "  and ru.estadoLogico = true ";
        Query q = em.createQuery(sql);
        q.setParameter("vcodPersonal", vcodPersonal);
        q.setParameter("vcodRol", vcodRol);
         List<SegRolUsuario> resultado = q.getResultList();
        if (resultado.size() > 0) {
            return (SegRolUsuario) resultado.get(0);
        } else {
            return null;
        }
    }
     public boolean existeUsuarioRol(Integer codUsuario, Integer codRol) throws Exception {
        String sql = "select count(ur) from SegRolUsuario ur where ur.estadoLogico = true and ur.codUsuario.idUsuario=:codUsuario and ur.codRol.idRol=:codRol";
        Query q = em.createQuery(sql);
        q.setParameter("codUsuario", codUsuario).setParameter("codRol", codRol);
        Long num = (Long) q.getSingleResult();
        if (num.intValue() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public SegRolUsuario buscarPorUsuarioRol(Integer codUsuario, Integer codRol) throws Exception {
        String sql = "select ur from SegRolUsuario ur  where ur.estadoLogico = true and ur.codUsuario.idUsuario=:codUsuario and ur.codRol.idRol=:codRol";
        Query q = em.createQuery(sql);
        q.setParameter("codUsuario", codUsuario).setParameter("codRol", codRol);
        return (SegRolUsuario) q.getResultList().get(0);
    }
}
