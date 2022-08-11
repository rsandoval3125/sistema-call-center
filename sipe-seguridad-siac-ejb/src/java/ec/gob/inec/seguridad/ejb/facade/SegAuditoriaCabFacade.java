/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.seguridad.clases.Tabla;
import ec.gob.inec.seguridad.ejb.entidades.SegAuditoriaCab;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mchasiguasin
 */
@Stateless
public class SegAuditoriaCabFacade extends AbstractFacade<SegAuditoriaCab> {

    @PersistenceContext(unitName = "sipe-seguridad-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegAuditoriaCabFacade() {
        super(SegAuditoriaCab.class);
    }

    /**
     * Valida si una tabla tiene el campo cod_auditoria_cab y de tipo varchar.
     *
     * @param tabla Nombre de la tabla.
     * @return Boolean, indicando con true si la tabla es auditable, y false
     * caso contrario.
     */
    public boolean validarTabla(String tabla) {
        String sql = "SELECT attname, pg_type.typname, pg_attribute.attnum\n"
                + "      FROM pg_attribute, pg_class, pg_type \n"
                + "      WHERE attrelid = pg_class.oid \n"
                + "      AND pg_attribute.attisdropped = False \n"
                + "      AND relname = :tabla AND  pg_type.typname='varchar' and attname='cod_auditoria_cab'\n"
                + "      AND attnum > 0 \n"
                + "      AND atttypid = pg_type.oid";
        Query q = em.createNativeQuery(sql);
        q.setParameter("tabla", tabla);
        return q.getResultList().size() > 0;
    }

    /**
     * Asocia un trigger a la tabla.
     *
     * @param tabla Nombre de la tabla.
     */
    public void activarAuditoria(String tabla) {
        String sql = "CREATE TRIGGER tg_auditar"
                + " AFTER INSERT OR UPDATE OR DELETE ON " + tabla
                + " FOR EACH ROW EXECUTE PROCEDURE seguridad.auditoria()";
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    /**
     * Elimina la asociacion del triger(tg_auditar) con la tabla.
     *
     * @param tabla Nombre de la tabla.
     */
    public void desactivarAuditoria(String tabla) {
        String sql = "DROP TRIGGER tg_auditar ON " + tabla;
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    /**
     * Consulta los esquemas que tiene la base de datos.
     *
     * @return List, listado de esquemas de POSTGRESQL.
     */
    public List<String> consultarEsquemaBDD() {
        String sql = " select nspname from pg_catalog.pg_namespace ORDER BY nspname";
        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    /**
     * Consulta tablas que tienen el triger tg_auditar.
     *
     * @return List, con nombres de tablas.
     */
    public List<Tabla> consultarTablasConTriggersBDD() {
        List<Tabla> respuesta = new ArrayList<>();
        String sql = "select  distinct  schemaname, tablename  from  pg_trigger RIGHT JOIN pg_class ON pg_class.oid=pg_trigger.tgrelid, pg_tables"
                + " where pg_tables.tablename=pg_class.relname  and tgname='tg_auditar' ORDER BY schemaname, tablename";
        Query q = em.createNativeQuery(sql);
        for (Object o : q.getResultList()) {
            Object[] reg = (Object[]) o;
            Tabla t = new Tabla((String) reg[0], (String) reg[1]);
            respuesta.add(t);
        }
        return respuesta;
    }

    /**
     * Consulta tablas que no tienen el triger (tg_auditar).
     *
     * @param tablas Nombres de tablas con triggers
     * @param esquema Nombre del esquema
     * @return List, listado de tablas sin triggers.
     */
    public List<Tabla> consultarTablasSinTriggersPorEsquemaBDD(List<String> tablas, String esquema) {
        List<Tabla> respuesta = new ArrayList<>();
        String sql = "select  distinct  schemaname, tablename  from pg_tables"
                + "  where  pg_tables.tablename NOT IN(SELECT pg_class.relname FROM pg_trigger RIGHT JOIN pg_class ON pg_class.oid=pg_trigger.tgrelid WHERE tgname='tg_auditar') and schemaname=:schemaname " + (tablas.size() > 0 ? " and pg_tables.tablename not in (:tablas)" : "") + " ORDER BY schemaname, tablename";
        Query q = em.createNativeQuery(sql);

        q.setParameter("schemaname", esquema);
        if (tablas.size() > 0) {
            q.setParameter("tablas", tablas);
        }

        for (Object o : q.getResultList()) {
            Object[] reg = (Object[]) o;
            Tabla t = new Tabla((String) reg[0], (String) reg[1]);
            respuesta.add(t);
        }
        return respuesta;
    }

    /**
     * Consulta tablas que no tienen asociado trigers(tg_auditar).
     *
     * @param tablas Nombres de tablas con trigers.
     * @return List, listado de tablas sin triggers.
     */
    public List<Tabla> consultarTablasSinTriggersBDD(List<String> tablas) {
        List<Tabla> respuesta = new ArrayList<>();
        String sql = "select  distinct  schemaname, tablename  from pg_tables"
                + " where pg_tables.tablename NOT IN(SELECT pg_class.relname FROM pg_trigger RIGHT JOIN pg_class ON pg_class.oid=pg_trigger.tgrelid WHERE tgname='tg_auditar') " + (tablas.size() > 0 ? " and pg_tables.tablename not in (:tablas)" : "") + " ORDER BY schemaname, tablename";
        Query q = em.createNativeQuery(sql);
        if (tablas.size() > 0) {
            q.setParameter("tablas", tablas);
        }

        for (Object o : q.getResultList()) {
            Object[] reg = (Object[]) o;
            Tabla t = new Tabla((String) reg[0], (String) reg[1]);
            respuesta.add(t);
        }
        return respuesta;
    }

}
