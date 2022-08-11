/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;

import ec.gob.inec.metadato.ejb.entidades.MetVariable;
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
public class MetVariableFacade extends AbstractFacade<MetVariable> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetVariableFacade() {
        super(MetVariable.class);
    }
    
    public List<MetVariable> listarVariablesPorSeccion(Integer codSeccion_) throws Exception{
        String sql="select v from MetVariable v "
                + "where v.estadoLogico=true and v.codSeccion.idSeccion=:codSeccion_ "
                + "order by v.fila,v.orden";
        Query q=em.createQuery(sql);
        q.setParameter("codSeccion_", codSeccion_);
        List<MetVariable> lst=q.getResultList();
        return lst;
    }
    
    public String existeDuplicidadDeIdentificadorDeVariablesEnSeccion(Integer codSeccion_) throws Exception{
        String sql="select v.identificador,count(v) from MetVariable v "
                + "where v.estadoLogico=true and v.codSeccion.idSeccion=:codSeccion_ "
                + "group by v.identificador "
                + "having count(v)>1";
        Query q=em.createQuery(sql);
        q.setParameter("codSeccion_", codSeccion_);
        String res="";
        List<Object[]> lst=q.getResultList();
        if(lst.size()>0){
            //muestra solo la primera variable duplicada
            res= String.valueOf(lst.get(0)[0]);
        }
        return res;
        
    }
    
    public List<Object[]> listarDiccionarioVariablesCorporativas(String sql) {
        Query q = getEntityManager().createNativeQuery(sql);
        return q.getResultList();
    }
    
    public List<Object> listarVariablesNVPorFormulario(Integer idFormulario) {
        String sql="SELECT  identificador "
                + "FROM metadato.v_variables_secciones_v "
                + "where cod_formulario=:idFormulario and tipo_seccion='NV'";
        Query q=em.createNativeQuery(sql);
        q.setParameter("idFormulario", idFormulario);
        return q.getResultList();
    }
    
    public MetVariable buscarVariablePorIdentificador(String identificador) throws Exception{
        String sql= "SELECT v "
                   +"FROM MetVariable v "
                   +"where v.identificador = :identificador";
        Query q=em.createQuery(sql);
        q.setParameter("identificador", identificador);
        List<MetVariable> lst=q.getResultList();
        if(!lst.isEmpty()){
            if(lst.size()>1){
                System.out.println("Hay más de una variable con el mismo identificador.");
            }
            //deberia devolver siempre un solo registro.
            return lst.get(0);
            
        }else{
            return new MetVariable();
        }
        
    }
    
    public Boolean generarCrosstab(String sql) {
        try{
            Query q=em.createNativeQuery(sql);
            List<Object> resultado = q.getResultList();
            if (resultado == null || resultado.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }catch(Exception ex){
            return false;
        }
        
    }
}
