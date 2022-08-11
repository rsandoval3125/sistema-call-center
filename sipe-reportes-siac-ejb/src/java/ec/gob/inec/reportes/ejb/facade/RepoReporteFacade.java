/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;

import ec.gob.inec.reportes.ejb.entidades.RepoReporte;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.jpa.HibernateQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

/**
 *
 * @author jaraujo
 */
@Stateless
public class RepoReporteFacade extends AbstractFacade<RepoReporte> {

    @PersistenceContext(unitName = "sipe-reportes-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RepoReporteFacade() {
        super(RepoReporte.class);
    }

    public List<RepoReporte> listarTodosActivos() throws Exception {
        String sql = "   SELECT p "
                + "  FROM RepoReporte p "
                + "where p.estadoLogico = true order by p.nombre asc";

        Query q = em.createQuery(sql);
        List<RepoReporte> resultado = q.getResultList();
        return resultado;
    }

    public List<Object[]> lstCabeceraAsigCargas() throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append("select * from ( ");
        sql.append("select distinct rolususuperc, supervisorc, telefonosuperc, 1, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ");
        sql.append("union all ");
        sql.append("select distinct rolususuper, supervisor, telefonosuper, 2, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ");
        sql.append("union all ");
        sql.append("select distinct rolusuact, actualizador, telefonoact, 3, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ");
        sql.append("union all ");
        sql.append("select distinct tipoconductorsuperc, conductorsuperc, telefonocondsuper, 4, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ");
        sql.append("union all ");
        sql.append("select distinct tipoconductorsuper, conductorsuper, telefonocondsuper, 5, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ) personal ");
        sql.append("order by 4, 2 ");

        Query q = em.createNativeQuery(sql.toString());
        return q.getResultList();
    }
    
    public List<Object[]> lstCabeceraAsigCargasEQ() throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append("select * from ( ");
        sql.append("select distinct rolususuper, supervisor, telefonosuper, 2, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ");
        sql.append("union all ");
        sql.append("select distinct rolusuact, actualizador, telefonoact, 3, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ");
        sql.append("union all ");
        sql.append("select distinct tipoconductorsuper, conductorsuper, telefonocondsuper, 5, fecha_inicio, fecha_fin, cod_equ_tra_cab, b.nombre ");
        sql.append("from temp_repo_carto_asignacargas a, distribucion.dis_equipo_trabajo b ");
        sql.append("where a.cod_equ_tra_cab = b.id_equ_tra ");
        sql.append("and fecha_inicio is not null ) personal ");
        sql.append("order by 4, 2 ");

        Query q = em.createNativeQuery(sql.toString());
        return q.getResultList();
    }
    
    
     public  List<Map<String,Object>> listObjetoNativoColumnasYTipo(String sql) {
    
    
    

 List<Map<String,Object>> res=new ArrayList<>();

Query dbQuery =  em.createNativeQuery(sql);
   
  org.hibernate.Query hibernateQuery =((HibernateQuery)dbQuery).getHibernateQuery();
   hibernateQuery.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
res = hibernateQuery.list();
            
        return res;
 
  }

 
  
}
