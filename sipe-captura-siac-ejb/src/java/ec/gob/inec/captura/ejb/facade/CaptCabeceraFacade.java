/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jcerda
 */
@Stateless
public class CaptCabeceraFacade extends AbstractFacade<CaptCabecera> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptCabeceraFacade() {
        super(CaptCabecera.class);
    }

    public CaptCabecera listarCaptCabeceraPorId(Integer idCab) throws Exception {
        String sql = "select o from CaptCabecera o "
                + "where idCab =:idCab ";
        Query q = em.createQuery(sql);
        q.setParameter("idCab", idCab);
        List<CaptCabecera> lst = q.getResultList();
        if (!lst.isEmpty()) {
            if (lst.size() > 1) {
                System.out.println("Hay más de una cabecera con el mismo id.");
            }
            //deberia devolver siempre un solo registro.
            return lst.get(0);

        } else {
            return new CaptCabecera();
        }
    }

    public CaptCabecera buscarPorCodCarCon(Integer codCarCon) throws Exception {
        String sql = "select o from CaptCabecera o "
                + "where o.codCarCon.idCarCon =:codCarCon and o.estadoLogico = true ";
        Query q = em.createQuery(sql);
        q.setParameter("codCarCon", codCarCon);
        List<CaptCabecera> lst = q.getResultList();
        if (!lst.isEmpty()) {
            if (lst.size() > 1) {
                System.out.println("Hay más de una cabecera con el mismo carga control id.");
            }
            //deberia devolver siempre un solo registro.
            return lst.get(0);

        } else {
            return new CaptCabecera();
        }
    }

    public List<CaptCabecera> listarFormulariosRepetidosPrecenso(List<String> listaClavesAGenerar) throws Exception {
        String sql = "select c from CaptCabecera c "
                + "where c.estadoLogico=true and clave in :listaClavesAGenerar ";
        Query q = em.createQuery(sql);
        q.setParameter("listaClavesAGenerar", listaClavesAGenerar);
        List<CaptCabecera> lst = q.getResultList();
        return lst;
    }

    //Llama las funciones para las omisiones ENSANUT F1
    public List<Object[]> omisionesF1(Integer idCab) throws Exception {
        List<Object[]> lista = new ArrayList<Object[]>();
        Integer val = 0;
        Query q = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f1_v(" + idCab + ")");
        val = (Integer) q.getSingleResult();
        if (val == 1) {
            Query q2 = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f1(" + idCab + ")");
            lista = q2.getResultList();
        }

        return lista;
    }
    //Llama las funciones para las omisiones ENSANUT F3

    public List<Object[]> omisionesF3(Integer idCab) throws Exception {
        List<Object[]> lista = new ArrayList<Object[]>();
        Integer val = 0;
        Query q = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f3_v(" + idCab + ")");
        val = (Integer) q.getSingleResult();
        if (val == 1) {
            Query q2 = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f3(" + idCab + ")");
            lista = q2.getResultList();
        }

        return lista;
    }
    //Llama las funciones para las omisiones ENSANUT F4

    public List<Object[]> omisionesF4(Integer idCab) throws Exception {
        List<Object[]> lista = new ArrayList<Object[]>();
        Integer val = 0;
        Query q = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f4_v(" + idCab + ")");
        val = (Integer) q.getSingleResult();
        if (val == 1) {
            Query q2 = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f4(" + idCab + ")");
            lista = q2.getResultList();
        }

        return lista;
    }
    
    //Llama las funciones para las omisiones ENSANUT F2

    public List<Object[]> omisionesF2(Integer idCab) throws Exception {
        List<Object[]> lista = new ArrayList<Object[]>();
        Integer val = 0;
        Query q = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f2_v(" + idCab + ")");
        val = (Integer) q.getSingleResult();
        if (val == 1) {
            Query q2 = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f2(" + idCab + ")");
            lista = q2.getResultList();
        }

        return lista;
    }
    
    //Llama las funciones para las omisiones ENSANUT F5

    public List<Object[]> omisionesF5(Integer idCab) throws Exception {
        List<Object[]> lista = new ArrayList<Object[]>();
        Integer val = 0;
        Query q = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f5_v(" + idCab + ")");
        val = (Integer) q.getSingleResult();
        if (val == 1) {
            Query q2 = em.createNativeQuery("SELECT * from captura.f_ensanut_omisiones_f5(" + idCab + ")");
            lista = q2.getResultList();
        }

        return lista;
    }

    public List<Object[]> listarVarCatPorFormulario(Integer idFormulario) {
        String sql = "SELECT  r.id_var "
                + "FROM metadato.tmp_variables_secciones_v v, metadato.met_variable r, metadato.met_concepto c\n"
                + "where r.cod_concepto=c.id_concepto and v.id_var=r.id_var and c.categorica=true and cod_formulario=:idFormulario";
        Query q = em.createNativeQuery(sql);
        q.setParameter("idFormulario", idFormulario);
        return q.getResultList();
    }

    public List<Object[]> listarVarCatFueraRango(Integer idCab, Integer idVar) {
        String sql = "SELECT 'En pregunta '||r.identificador || ' existe un valor fuera de rango del catálogo.'\n"
                + "FROM captura.capt_cabecera c, captura.capt_detalle_v v, metadato.met_variable r, metadato.met_concepto p, metadato.met_tipo_catalogo c1, metadato.met_catalogo c2\n"
                + "WHERE v.cod_cab=c.id_cab and v.cod_var=r.id_var and r.cod_concepto=p.id_concepto and p.cod_tipo_catalogo=c1.id_tipo_catalogo\n"
                + "      and c2.cod_tipo_catalogo=c1.id_tipo_catalogo and p.categorica=true and c.estado_logico=true\n"
                + "      and c.id_cab=:idCab and r.id_var=:idVar  AND v.valor not in(SELECT c2.valor\n"
                + "FROM captura.capt_cabecera c, captura.capt_detalle_v v, metadato.met_variable r, metadato.met_concepto p, metadato.met_tipo_catalogo c1, metadato.met_catalogo c2\n"
                + "WHERE v.cod_cab=c.id_cab and v.cod_var=r.id_var and r.cod_concepto=p.id_concepto and p.cod_tipo_catalogo=c1.id_tipo_catalogo\n"
                + "      and c2.cod_tipo_catalogo=c1.id_tipo_catalogo and p.categorica=true and c.estado_logico=true\n"
                + "      and c.id_cab=:idCab and r.id_var=:idVar group by r.id_var, c2.valor) AND  v.valor!=''\n"
                + "GROUP BY  r.id_var,v.valor ";
        Query q = em.createNativeQuery(sql);
        q.setParameter("idCab", idCab);
        q.setParameter("idVar", idVar);
        return q.getResultList();
    }
     public List<Object[]> listarVarCatFueraRangoF1(Integer idCab, Integer idVar, Integer numDet) {
        String sql = "SELECT 'En pregunta '||r.identificador || ' existe un valor fuera de rango del catálogo.'\n"
                + "FROM captura.capt_cabecera c, captura.capt_detalle_v v, metadato.met_variable r, metadato.met_concepto p, metadato.met_tipo_catalogo c1, metadato.met_catalogo c2\n"
                + "WHERE v.cod_cab=c.id_cab and v.cod_var=r.id_var and r.cod_concepto=p.id_concepto and p.cod_tipo_catalogo=c1.id_tipo_catalogo\n"
                + "      and c2.cod_tipo_catalogo=c1.id_tipo_catalogo and p.categorica=true and c.estado_logico=true\n"
                + "      and c.id_cab=:idCab and r.id_var=:idVar and v.num_det=:numDet AND v.valor not in(SELECT c2.valor\n"
                + "FROM captura.capt_cabecera c, captura.capt_detalle_v v, metadato.met_variable r, metadato.met_concepto p, metadato.met_tipo_catalogo c1, metadato.met_catalogo c2\n"
                + "WHERE v.cod_cab=c.id_cab and v.cod_var=r.id_var and r.cod_concepto=p.id_concepto and p.cod_tipo_catalogo=c1.id_tipo_catalogo\n"
                + "      and c2.cod_tipo_catalogo=c1.id_tipo_catalogo and p.categorica=true and c.estado_logico=true\n"
                + "      and c.id_cab=:idCab and r.id_var=:idVar group by r.id_var, c2.valor) AND  v.valor!=''\n"
                + "GROUP BY  r.id_var,v.valor ";
        Query q = em.createNativeQuery(sql);
        q.setParameter("idCab", idCab);
        q.setParameter("idVar", idVar);
        q.setParameter("numDet", numDet);
        return q.getResultList();
    }
     public String trasladarInfDetVHaciaBasePorIdCab(String nombreTablaBase,Integer codCab) throws Exception{
        
        Query q1 = getEntityManager().createNativeQuery("select captura.f_val_f1_"+nombreTablaBase+"("+codCab+");");
        List<Object[]> lst= q1.getResultList();
        String s="";
        if(!lst.isEmpty()){
            s="OK";
        } 
        return s;
    }
     public String obtenerValidacionIntegridadPorBaseIdCab(String nombreTablaBase,Integer codCab) throws Exception{
        
        Query q1 = getEntityManager().createNativeQuery("select * from captura.f_val_f2_"+nombreTablaBase+"("+codCab+")");
        List<Object[]> lst= q1.getResultList();
        String s="";
        String estado="true";
        if(!lst.isEmpty()){
            for(Object[] o:lst){
                s=s+","+String.valueOf(o[1])+" ("+String.valueOf(o[0]+")");
            }
            estado="false";
            
        } 
        Query q2 = getEntityManager().createNativeQuery("update captura.capt_val_"+nombreTablaBase+" set estado="+estado+" where cod_cab="+codCab+";");
        q2.executeUpdate();
        return s;
    }
     
     public String ejecutarQueryNativo(String sql) throws Exception {
        String s = "";
        try {
            Query q = em.createNativeQuery(sql);
            q.getSingleResult();
            //q.executeUpdate();
            s = "OK";
            System.out.println(s);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return s;
    }
     
    public List<Object[]> obtenerQueryNativo(String sql) throws Exception {
        List<Object[]> lst = new ArrayList<Object[]>();
        try {
            Query q = em.createNativeQuery(sql);
            lst = q.getResultList();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lst;
    }
}
