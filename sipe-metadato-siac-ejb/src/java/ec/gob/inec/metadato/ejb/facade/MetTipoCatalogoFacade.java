/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetTipoCatalogo;
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
public class MetTipoCatalogoFacade extends AbstractFacade<MetTipoCatalogo> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetTipoCatalogoFacade() {
        super(MetTipoCatalogo.class);
    }
    
    public List<MetTipoCatalogo> listarTipoCatPorSecciones(List<Integer> codigosSeccion_) throws Exception{
        String sql="select distinct v.codConcepto.codTipoCatalogo from MetVariable v "
                + "where v.estadoLogico=true and v.codConcepto.codTipoCatalogo.estadoLogico=true and "
                + "v.codConcepto.estadoLogico=true and"
                + "v.codSeccion.idSeccion in :codigosSeccion_";
        Query q=em.createQuery(sql);
        q.setParameter("codigosSeccion_", codigosSeccion_);
        return q.getResultList();
    }
    
    public String existeDuplicidadDeAliasTipoCatalogo() throws Exception{
        String sql="select t.alias,count(t) from MetTipoCatalogo t "
                + "where t.estadoLogico=true "
                + "group by t.alias "
                + "having count(t)>1";
        Query q=em.createQuery(sql);
        String res="";
        List<Object[]> lst=q.getResultList();
        if(lst.size()>0){
            //muestra solo el primer alias duplicado
            res= String.valueOf(lst.get(0)[0]);
        }
        return res;
    }
    
    public List<MetTipoCatalogo> listarTodosActivos() throws Exception {
        String sql = "select c from MetTipoCatalogo c "
                + "where c.estadoLogico=true "
                + "order by c.nombre asc ";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
}
