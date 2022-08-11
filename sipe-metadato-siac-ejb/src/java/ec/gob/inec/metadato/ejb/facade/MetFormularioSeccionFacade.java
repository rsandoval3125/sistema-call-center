/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetFormularioSeccion;
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
public class MetFormularioSeccionFacade extends AbstractFacade<MetFormularioSeccion> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetFormularioSeccionFacade() {
        super(MetFormularioSeccion.class);
    }
    
    public List<MetFormularioSeccion> listarTodosActivos() throws Exception {
        String sql = "select c from MetFormularioSeccion c "
                + "where c.estadoLogico=true "
                + "order by c.codFormulario asc ";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    public List<MetFormularioSeccion> listarPorId(Integer formSeccion) throws Exception { 
        String sql = "select c from MetFormularioSeccion c "
                + "where c.estadoLogico=true and c.idFormularioSeccion=:formSeccion "
                + "order by c.codFormulario asc ";
        Query q = em.createQuery(sql);
        return q.getResultList();
    
    }
    
    public  MetFormularioSeccion bucarFormSecPoridForXidSecc(Integer codFormulario, Integer codSeccion) throws Exception{
         System.out.println("Valores "+codFormulario +"  "+ codSeccion);
          String sql = "select c from MetFormularioSeccion c "
                + "where c.estadoLogico=true and c.codFormulario.idFormulario=:codFormulario "
                + "and c.codSeccion.idSeccion =:codSeccion ";
                Query q=em.createQuery(sql);
                q.setParameter("codFormulario", codFormulario);
                q.setParameter("codSeccion", codSeccion);
                List<MetFormularioSeccion> lst=q.getResultList();
                System.out.println("Sale");
                if(!lst.isEmpty()){
                    if(lst.size()>1){
                        System.out.println("Hay más de un Formulario-Seccion.");
                    }
                    //deberia devolver siempre un solo registro.
                    return lst.get(0);

                }else{
                    return new MetFormularioSeccion();
                }
    
    } 
}
