/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.reportes.ejb.entidades.RepoFiltro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jaraujo
 */
@Stateless
public class RepoFiltroFacade extends AbstractFacade<RepoFiltro> {

    @PersistenceContext(unitName = "sipe-reportes-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RepoFiltroFacade() {
        super(RepoFiltro.class);
    }

    public List<Object[]> listarEjecutarConsulta(String select) throws Exception {
        Query q = em.createNativeQuery(select);
        List<Object[]> resultado = q.getResultList();
        if (resultado.size() > 0) {
            return resultado;
        } else {
            return null;
        }
    }

    public List<Object[]> listarFiltrosSeparados(int param1, int param2, String clave) throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append("select count(a.clave), a.clave ");
        sql.append("from captura.capt_detalle_h a, ");
        sql.append("captura.capt_cabecera b ");
        sql.append("where a.cod_cab = b.id_cab ");
        sql.append("and substring(b.clave,:param1,:param2) = :clave ");
        sql.append("and b.estado_logico = true ");
        sql.append("group by a.clave ");
        sql.append("order by a.clave ");

        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("param1", param1);
        q.setParameter("param2", param2);
        q.setParameter("clave", clave);
        return q.getResultList();
    }

    public List<Object[]> listarFiltrosSeparados2020(int param1, int param2, String clave) throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append("select count(b.info4), b.info4 ");
        sql.append("from captura.capt_detalle_h a, ");
        sql.append("captura.capt_cabecera b ");
        sql.append("where a.cod_cab = b.id_cab ");
        sql.append("and substring(b.info4,:param1,:param2) = :clave ");
        sql.append("and b.estado_logico = true ");
        sql.append("group by b.info4 ");
        sql.append("order by b.info4 ");

        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("param1", param1);
        q.setParameter("param2", param2);
        q.setParameter("clave", clave);
        return q.getResultList();
    }

    public List<Object[]> listarEquiposSeparados() throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append("select count(cod_equ_tra_cab), cod_equ_tra_cab, nom_equipo ");
        sql.append("from temp_repo_carto_asignacargas ");
        sql.append("where actualizador <> '' ");
        sql.append("group by cod_equ_tra_cab, nom_equipo ");
        sql.append("order by cod_equ_tra_cab ");

        Query q = em.createNativeQuery(sql.toString());
        return q.getResultList();
    }
}
