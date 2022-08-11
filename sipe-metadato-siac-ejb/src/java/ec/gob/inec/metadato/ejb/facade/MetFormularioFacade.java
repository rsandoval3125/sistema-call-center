/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetFormulario;
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
public class MetFormularioFacade extends AbstractFacade<MetFormulario> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetFormularioFacade() {
        super(MetFormulario.class);
    }
    
    public List<MetFormulario> listarFormulariosPorOperativo(Integer codOpe_) throws Exception{
        String sql="select f from MetFormulario f "
                + "where f.estadoLogico=true and f.codOperativo.idOperativo=:codOpe_"
                + " order by f.nombre";
        Query q=em.createQuery(sql);
        q.setParameter("codOpe_", codOpe_);
        List<MetFormulario> lst=q.getResultList();
        return lst;
    }
    
     public List<MetFormulario> listarFormulariosPorId(Integer formularioId) throws Exception{
        String sql="select f from MetFormulario f "
                + "where f.estadoLogico=true and f.idFormulario=:formularioId"
                + " order by f.nombre";
        Query q=em.createQuery(sql);
        q.setParameter("formularioId", formularioId);
        List<MetFormulario> lst=q.getResultList();
        return lst;
    }
      public List<MetFormulario> listarFormsPorMuestra(short muestraId) throws Exception{
        String sql="select f from MetFormulario f "
                + "where f.estadoLogico=true and f.muestra=:muestraID "
                + "order by f.nombre";
        Query q=em.createQuery(sql);
        q.setParameter("muestraID", muestraId);
        List<MetFormulario> lst=q.getResultList();
        return lst;
    }
    
    public List<MetFormulario> listarFormulariosTodos() throws Exception{
        String sql="select f from MetFormulario f "
                + "where f.estadoLogico=true "
                + "order by f.nombre";
        Query q=em.createQuery(sql);
        List<MetFormulario> lst=q.getResultList();
        return lst;
    }
    
    public  MetFormulario bucarFormPorAlias(String codificacion) throws Exception{
          String sql = "select c from MetFormulario c "
                + "where c.estadoLogico=true "
                + "and c.codificacion =:codificacion ";
                Query q=em.createQuery(sql);
                q.setParameter("codificacion", codificacion);
                List<MetFormulario> lst=q.getResultList();
                if(!lst.isEmpty()){
                    if(lst.size()>1){
                        System.out.println("Hay más de un Formulario.");
                    }
                    //deberia devolver siempre un solo registro.
                    return lst.get(0);

                }else{
                    return new MetFormulario();
                }
    
    }
}
