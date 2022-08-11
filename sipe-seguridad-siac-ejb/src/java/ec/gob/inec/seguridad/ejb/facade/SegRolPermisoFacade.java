/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.ejb.entidades.SegRolPermiso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lponce
 */
@Stateless
public class SegRolPermisoFacade extends AbstractFacade<SegRolPermiso> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegRolPermisoFacade() {
        super(SegRolPermiso.class);
    }

    public boolean existePermisoRol(Integer codRol, Integer codPermiso) throws Exception {
        String sql = "select count(ur) from SegRolPermiso ur where ur.estadoLogico = true and ur.codPermiso.idPermiso=:codPermiso and ur.codRol.idRol=:codRol";
        Query q = em.createQuery(sql);
        q.setParameter("codPermiso", codPermiso).setParameter("codRol", codRol);
        Long num = (Long) q.getSingleResult();
        if (num.intValue() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public SegRolPermiso buscarPorPermisoRol(Integer codRol, Integer codPermiso) throws Exception {
        String sql = "select ur from SegRolPermiso ur  where ur.estadoLogico = true and ur.codPermiso.idPermiso=:codPermiso and ur.codRol.idRol=:codRol";
        Query q = em.createQuery(sql);
        q.setParameter("codPermiso", codPermiso).setParameter("codRol", codRol);
        return (SegRolPermiso) q.getResultList().get(0);
    }
}
