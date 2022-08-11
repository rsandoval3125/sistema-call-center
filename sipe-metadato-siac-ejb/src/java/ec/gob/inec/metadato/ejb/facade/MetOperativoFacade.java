/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetOperativo;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.ArrayList;
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
public class MetOperativoFacade extends AbstractFacade<MetOperativo> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetOperativoFacade() {
        super(MetOperativo.class);
    }
    public List<MetOperativo> listarOperativosPorCodOpeEst(Integer codOpeEst_) throws Exception{
        //algunas entidades se encuentran en otro esquema por lo tanto se combina jpql con sql
        String sql="select o from MetOperativo o, AdmOperacionEstadistica e "
                + "where o.estadoLogico=true and o.codOpe=e.idOpeEst and "
                + "e.idOpeEst=:codOpeEst_ "
                + "order by e.nombre ";
        Query q=em.createQuery(sql);
        q.setParameter("codOpeEst_", codOpeEst_);
        List<MetOperativo> lst=q.getResultList();
        return lst;
    }
    
    public MetOperativo listarOperativosPorCodPeriodoYCodOpeEst(Integer codPer_, Integer codOpeEst_) throws Exception{
         //algunas entidades se encuentran en otro esquema por lo tanto se combina jpql con sql
        String sql="select o from MetOperativo o, AdmPeriodo p, AdmOperacionEstadistica e "
                + "where o.estadoLogico=true and o.codOpe=e.idOpeEst and o.codPer=p.idPeriodo and "
                + "e.idOpeEst=:codOpeEst_ and p.idPeriodo=:codPer_ "
                + "order by e.nombre,p.nombre";
        Query q=em.createQuery(sql);
        q.setParameter("codPer_", codPer_).setParameter("codOpeEst_", codOpeEst_);
        List<MetOperativo> lst=q.getResultList();
        if(!lst.isEmpty()){
            if(lst.size()>1){
                System.out.println("Hay más de un operativo con el mismo periodo.");
            }
            //deberia devolver siempre un solo registro.
            return lst.get(0);
            
        }else{
            return new MetOperativo();
        }
    }
    
    public List<MetOperativo> listarOperativosPorPeriodoYOpeEst(Integer codPer_, Integer codOpeEst_) throws Exception{
         //algunas entidades se encuentran en otro esquema por lo tanto se combina jpql con sql
        String sql="select o from MetOperativo o, AdmPeriodo p, AdmOperacionEstadistica e "
                + "where o.estadoLogico=true and o.codOpe=e.idOpeEst and o.codPer=p.idPeriodo and "
                + "e.idOpeEst=:codOpeEst_ and p.idPeriodo=:codPer_ "
                + "order by e.nombre,p.nombre";
        Query q=em.createQuery(sql);
        q.setParameter("codPer_", codPer_).setParameter("codOpeEst_", codOpeEst_);
        List<MetOperativo> lst=q.getResultList();
        return lst;   
    }
    
    public List<MetOperativo> listarOperativosPorPeriodoYOpeEstModeloValidado(Integer codPer_, Integer codOpeEst_) throws Exception{
         //algunas entidades se encuentran en otro esquema por lo tanto se combina jpql con sql
        String sql="select o from MetOperativo o, AdmPeriodo p, AdmOperacionEstadistica e "
                + "where o.estadoLogico=true and o.codOpe=e.idOpeEst and o.codPer=p.idPeriodo and "
                + "e.idOpeEst=:codOpeEst_ and p.idPeriodo=:codPer_ and "
                + "o.tieneModeloValidado = true "
                + "order by e.nombre,p.nombre";
        Query q=em.createQuery(sql);
        q.setParameter("codPer_", codPer_).setParameter("codOpeEst_", codOpeEst_);
        List<MetOperativo> lst=q.getResultList();
        return lst;   
    }
    
     public List<MetOperativo> listarTodosOperativo() throws Exception{
        //algunas entidades se encuentran en otro esquema por lo tanto se combina jpql con sql
        String sql="select o from MetOperativo o, AdmPeriodo p, AdmOperacionEstadistica e "
                + "where o.estadoLogico=true and o.codOpe=e.idOpeEst and o.codPer=p.idPeriodo "
                + "order by e.nombre,p.nombre";
        Query q = em.createQuery(sql);
        List<MetOperativo> lst=q.getResultList();
        return lst;
    }
    
    public List<MetOperativo> listarTodosOperativoPorId(Integer idOperativo ) throws Exception{
        //algunas entidades se encuentran en otro esquema por lo tanto se combina jpql con sql
        String sql="select o from MetOperativo o, AdmPeriodo p, AdmOperacionEstadistica e "
                + "where o.estadoLogico=true and o.codOpe=e.idOpeEst and o.codPer=p.idPeriodo "
                + "and o.idOperativo =:idOperativo "
                + "order by e.nombre,p.nombre";
        Query q = em.createQuery(sql);
        q.setParameter("idOperativo", idOperativo);
        List<MetOperativo> lst=q.getResultList();
        return lst;
    }
    
     public List<MetOperativo> listarOperativosIn(List<MetOperativo> lista) throws Exception {
        String sql = "select e from MetOperativo e"
                + " where e in :vlista"
                + " order by e.idOperativo";
        Query q = em.createQuery(sql);
        q.setParameter("vlista", lista);
        return q.getResultList();
    }
}
