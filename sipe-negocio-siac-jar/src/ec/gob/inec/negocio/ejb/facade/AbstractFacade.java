/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.negocio.ejb.facade;

import ec.gob.inec.ejb.utils.UtilitariosCod;
import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import javax.sql.DataSource;

/**
 *
 * @author lponce
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    //<editor-fold desc="crear, actualizar, eliminar" defaultstate="collapsed">
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void crear(T entity) {
        getEntityManager().persist(entity);
    }

    public void editar(T entity) {
        getEntityManager().merge(entity);
    }

    public void eliminar(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public int ejecutarActualizar(String sql, Map<String, Object> parms) {
        Query q = getEntityManager().createQuery(sql);
        for (String key : parms.keySet()) {
            Object obj = parms.get(key);
            q.setParameter(key, obj);
        }
        return q.executeUpdate();
    }

    public void eliminarPor3Campos(String ventidad, String vcampo1, Object vvalor1, String vcampo2, Object vvalor2, String vcampo3, Object vvalor3) throws Exception {
        String sql = "delete from " + ventidad + " e where e." + vcampo1 + " =:vvalor1 and e." + vcampo2 + "=:vvalor2 and e." + vcampo3 + "=:vvalor3";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1).setParameter("vvalor2", vvalor2).setParameter("vvalor3", vvalor3);
        q.executeUpdate();

    }

    public void eliminarPor2Campos(String ventidad, String vcampo1, Object vvalor1, String vcampo2, Object vvalor2) throws Exception {
        String sql = "delete from " + ventidad + " e where e." + vcampo1 + " =:vvalor1 and e." + vcampo2 + "=:vvalor2";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1).setParameter("vvalor2", vvalor2);
        q.executeUpdate();

    }

    public void eliminarGenerico(String ventidad, String vcampo, Object vvalor) throws Exception {
        String sql = " delete  from " + ventidad + " e where e." + vcampo + "=:vvalor";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor", vvalor);
        q.executeUpdate();
    }

    public void modificarGenerico(String ventidad, String vcampo, Integer cod_preg, String vcampo2, Object vvalorCondicion, String vcampo3, Object vvalorFlujo) throws Exception {
        String sql = " update " + ventidad + " set " + vcampo2 + "=:vvalor1, " + vcampo3 + "=:vvalor2 where " + vcampo + "=:vvalor3";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor2", vvalorFlujo);
        q.setParameter("vvalor1", vvalorCondicion);
        q.setParameter("vvalor3", cod_preg);
        q.executeUpdate();
    }

    public String modificarEjecutarConsulta(String vnombreProcedimiento, List<Object> parametrosOrdenados) throws Exception {
        RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        Query q = getEntityManager().createQuery(procConsulta.getSql());
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            q.setParameter(numParametros, obj);
            numParametros++;
        }
        q.executeUpdate();
        return "Actualización Ejecutada";
    }

//</editor-fold>
    //<editor-fold desc="find generico" defaultstate="collapsed">
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//</editor-fold>

    //<editor-fold desc="contar" defaultstate="collapsed">
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int contar() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int contarPorCampos(String ventidad, Map<String, Object> campos) throws Exception {
        StringBuilder sql = new StringBuilder("select count(e) from " + ventidad + " e ");
        if (!campos.isEmpty()) {
            sql.append("where ");
            int numCampos = 0;
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                numCampos++;
                if (numCampos > 1) {
                    sql.append(" and ");
                }
                sql.append("e." + entry.getKey() + "=:v" + entry.getKey().replace(".", ""));
            }
            Query q = getEntityManager().createQuery(sql.toString());
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                q.setParameter("v" + entry.getKey().replace(".", ""), entry.getValue());
            }

            return ((Long) q.getSingleResult()).intValue();
        } else {
            return 0;
        }
    }

    public int contarEjecutarConsulta(String vnombreProcedimiento, List<Object> parametrosOrdenados) throws Exception {
        RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        Query q = getEntityManager().createQuery(procConsulta.getSql());
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            q.setParameter(numParametros, obj);
            numParametros++;
        }
        Object result = q.getSingleResult();
        if (result != null) {
            return ((Integer) result);
        } else {
            return 0;
        }
    }

    /*public int contarPorCampo(String ventidad, String vcampo1, Object vvalor1) throws Exception {
        String sql = "select count(e) from " + ventidad + " e where e." + vcampo1 + "=:vvalor1";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int contarPor2Campos(String ventidad, String vcampo1, Object vvalor1, String vcampo2, Object vvalor2) throws Exception {
        String sql = "select count(e) from " + ventidad + " e where e." + vcampo1 + "=:vvalor1 and e." + vcampo2 + "=:vvalor2";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1).setParameter("vvalor2", vvalor2);
        return ((Long) q.getSingleResult()).intValue();
    }*/
    //</editor-fold>
    //<editor-fold desc="listarPor Campos" defaultstate="collapsed">
    public List<T> listarPor2CamposyCampoNotNullOrdenada(String ventidad, String vcampo1, Object vvalor1, String vcampo2, Object vvalor2, String campoNNull, String vcampoOrd, String vforma) throws Exception {
        String sql = "select e from " + ventidad + " e where e." + vcampo1 + " =:vvalor1 and e." + vcampo2 + "=:vvalor2 and e." + campoNNull + " is not null order by e." + vcampoOrd + " " + vforma;
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1).setParameter("vvalor2", vvalor2);
        return q.getResultList();

    }

    public List<T> listarPorRangoFecha(String ventidad, String vcampo1, Object vvalor1, Object vvalor2, String vcampoOrd, String vforma) throws Exception {
        String sql = "select e from " + ventidad + " e where e." + vcampo1 + " between :vvalor1 and :vvalor2 order by e." + vcampoOrd + " " + vforma;
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1).setParameter("vvalor2", vvalor2);
        return q.getResultList();

    }

    public List<T> listarContienePorCampoOrdenada(String ventidad, String vcampo1, Object vvalor1, String vcampoOrd, String vforma) throws Exception {
        String sql = "select e from " + ventidad + " e where e." + vcampo1 + "  like :vvalor1 order by e." + vcampoOrd + " " + vforma;
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", "%" + vvalor1 + "%");
        return q.getResultList();
    }

    public List<T> listarOrdenada(String ventidad, String vcampoOrd, String vforma) throws Exception {
        String sql = "select e from " + ventidad + " e order by e." + vcampoOrd + " " + vforma;
        Query q = getEntityManager().createQuery(sql);
        return q.getResultList();

    }

    public List<T> listarOrdenadaNotIn(String ventidad, String vcampoNotIn, Object vvalorNotIn, String vcampoOrd, String vforma) throws Exception {
        String sql = "select e from " + ventidad + " e where e." + vcampoNotIn + " not in (:vvalorNotIn) order  by e." + vcampoOrd + " " + vforma;
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalorNotIn", vvalorNotIn);
        return q.getResultList();

    }

    public List<T> listarPorCampos(String ventidad, Map<String, Object> campos, String order) throws Exception {
        // System.out.println("llamar buscar listar campos: ");
        StringBuilder sql = new StringBuilder("select e from " + ventidad + " e ");
        if (!campos.isEmpty()) {
            sql.append("where ");
            int numCampos = 0;
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                numCampos++;
                if (numCampos > 1) {
                    sql.append(" and ");
                }
                sql.append("e." + entry.getKey() + "=:v" + entry.getKey().replace(".", ""));
                // System.out.println("valores: " + entry.getKey() + " : " + entry.getValue());
            }
            if (!order.equals("")) {
                sql.append(" order by " + order);
            }
            //  System.out.println("sql: " + sql.toString());
            Query q = getEntityManager().createQuery(sql.toString());
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                q.setParameter("v" + entry.getKey().replace(".", ""), entry.getValue());
            }

            List<T> resultado = q.getResultList();
            if (resultado.size() > 0) {
                return resultado;
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public T buscarPorCampos(String ventidad, Map<String, Object> campos) throws Exception {
        StringBuilder sql = new StringBuilder("select e from " + ventidad + " e ");
        if (!campos.isEmpty()) {
            sql.append("where ");
            int numCampos = 0;
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                numCampos++;
                if (numCampos > 1) {
                    sql.append(" and ");
                }
                sql.append("e." + entry.getKey() + "=:v" + entry.getKey());
            }
            Query q = getEntityManager().createQuery(sql.toString());
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                q.setParameter("v" + entry.getKey(), entry.getValue());
            }

            List<T> resultado = q.getResultList();
            if (resultado.size() > 0) {
                return (T) resultado.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<T> listarPorCamposReemplazo(String ventidad, Map<String, Object> campos, String order) throws Exception {
        // System.out.println("llamar buscar listar campos: ");
        StringBuilder sql = new StringBuilder("select e from " + ventidad + " e ");
        if (!campos.isEmpty()) {
            sql.append("where ");
            int numCampos = 0;
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                numCampos++;
                if (numCampos > 1) {
                    sql.append(" and ");
                }
                sql.append("e." + entry.getKey() + "=:v" + entry.getKey().replace(".", ""));
                // System.out.println("valores: " + entry.getKey() + " : " + entry.getValue());
            }

            //  System.out.println("sql: " + sql.toString());
            sql.append(" and control1 in ('9','10','11')");

            if (!order.equals("")) {
                sql.append(" order by " + order);
            }
            Query q = getEntityManager().createQuery(sql.toString());
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                q.setParameter("v" + entry.getKey().replace(".", ""), entry.getValue());
            }

            List<T> resultado = q.getResultList();
            if (resultado.size() > 0) {
                return resultado;
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public List<T> listarPorCampos(String ventidad, List<Object[]> listaWhere, String orden) throws Exception {
        StringBuilder sql = new StringBuilder("select e from " + ventidad + " e ");
        if (!listaWhere.isEmpty()) {
            sql.append("where ");
            for (int i = 0; i < listaWhere.size(); i++) {
                if (i > 1) {
                    sql.append("and ");
                }
                String tipoOperador = String.valueOf(listaWhere.get(i)[1]);
                sql.append("e.").append(listaWhere.get(i)[0]).append(" ").append(tipoOperador).append(" ");
                switch (tipoOperador) {
                    case "in":
                        sql.append("(").append(":v").append(String.valueOf(listaWhere.get(i)[0]).replace(".", "")).append(")");
                        break;
                    default:
                        sql.append(":v").append(String.valueOf(listaWhere.get(i)[0]).replace(".", ""));
                        break;
                }
                sql.append(" ");
            }
            if (!orden.equals("")) {
                sql.append(" order by " + orden);
            }
            // System.out.println("sql: " + sql.toString());
            Query q = getEntityManager().createQuery(sql.toString());
            for (int i = 0; i < listaWhere.size(); i++) {
                String tipoOperador = String.valueOf(listaWhere.get(i)[1]);
                switch (tipoOperador) {
                    case "like":
                        q.setParameter("v" + String.valueOf(listaWhere.get(i)[0]).replace(".", ""), String.valueOf(listaWhere.get(i)[3]) == "1" ? "%" : "" + String.valueOf(listaWhere.get(i)[2]) + String.valueOf(listaWhere.get(i)[4]) == "1" ? "%" : "");
                        break;
                    case "in":
                        List<String> idsArr = Arrays.asList(String.valueOf(listaWhere.get(i)[2]).split("\\s*,\\s*"));

                        //    System.out.println("clase" + entityClass.getName());
                        Field f = entityClass.getDeclaredField(String.valueOf(listaWhere.get(i)[0]));
                        // System.out.println("campo" + f.getName());
                        f.setAccessible(true);
                        //  System.out.println("tipo" + f.getType().getName());
                        Class c = f.getType();
                        List<?> lista = null;
                        switch (f.getType().getName()) {
                            case "java.lang.Integer":
                                lista = stringListToU(idsArr, Integer::parseInt);
                                break;
                            case "java.lang.String":
                                lista = idsArr;
                                break;
                            case "java.lang.Long":
                                lista = stringListToU(idsArr, Long::valueOf);
                                break;
                        }

                        q.setParameter("v" + String.valueOf(listaWhere.get(i)[0]).replace(".", ""), lista);
                        break;
                    default:
                        q.setParameter("v" + String.valueOf(listaWhere.get(i)[0]).replace(".", ""), listaWhere.get(i)[2]);
                        break;
                }
            }

            List<T> resultado = q.getResultList();
            if (resultado.size() > 0) {
                return resultado;
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public RepoProcedimiento buscarProcedimientoXNombre(String vnombre) throws Exception {
        String sql = " SELECT p "
                + "  FROM RepoProcedimiento p "
                + "where  p.nombre =:vnombre  ";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vnombre", vnombre);
        List<RepoProcedimiento> resultado = q.getResultList();
        if (resultado.size() > 0) {
            return (RepoProcedimiento) resultado.get(0);
        } else {
            return null;
        }
    }

    public List<T> listarEjecutarConsulta(String vnombreProcedimiento, List<Object> parametrosOrdenados) throws Exception {
        RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        Query q = getEntityManager().createQuery(procConsulta.getSql());
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            q.setParameter(numParametros, obj);
            numParametros++;
        }
        return q.getResultList();
    }

    public List<Object[]> listarEjecutarConsultaObj(String vnombreProcedimiento, List<Object> parametrosOrdenados) throws Exception {
        RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        Query q = getEntityManager().createNativeQuery(procConsulta.getSql());
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            q.setParameter(numParametros, obj);
            numParametros++;
        }
        return q.getResultList();
    }

    public T buscarEjecutarConsulta(String vnombreProcedimiento, List<Object> parametrosOrdenados) throws Exception {
        RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        Query q = getEntityManager().createQuery(procConsulta.getSql());
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            q.setParameter(numParametros, obj);
            numParametros++;
        }
        List<T> resultado = q.getResultList();
        if (resultado.size() > 0) {
            return (T) resultado.get(0);
        } else {
            return null;
        }
    }

    public Object[] buscarEjecutarConsultaObj(String vnombreProcedimiento, List<Object> parametrosOrdenados) throws Exception {
        RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        Query q = getEntityManager().createNativeQuery(procConsulta.getSql());
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            q.setParameter(numParametros, obj);
            numParametros++;
        }
        List<T> resultado = q.getResultList();
        if (resultado.size() > 0) {
            return (Object[]) resultado.get(0);
        } else {
            return null;
        }
    }

    public T buscarPorPartes(String ventidad, String vcampo1, Object vvalor1) throws Exception {

        String sql = "select e from " + ventidad + " e where e." + vcampo1 + " like :vvalor1";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", "%" + vvalor1 + "%");
        return (T) q.getResultList().get(0);
    }
//</editor-fold>

    //<editor-fold desc="existe" defaultstate="collapsed">
    public boolean existePorCampos(String ventidad, Map<String, Object> campos, String order) throws Exception {
        StringBuilder sql = new StringBuilder("select count(e) from " + ventidad + " e ");
        if (!campos.isEmpty()) {
            sql.append("where ");
            int numCampos = 0;
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                numCampos++;
                if (numCampos > 1) {
                    sql.append(" and ");
                }
                sql.append("e." + entry.getKey() + "=:v" + entry.getKey());
            }
            Query q = getEntityManager().createQuery(sql.toString());
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                q.setParameter("v" + entry.getKey(), entry.getValue());
            }

            Long num = (Long) q.getSingleResult();
            if (num.intValue() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean existeEjecutarConsulta(String vnombreProcedimiento, List<Object> parametrosOrdenados) throws Exception {
        RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        Query q = getEntityManager().createQuery(procConsulta.getSql());
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            q.setParameter(numParametros, obj);
            numParametros++;
        }
        Object result = q.getSingleResult();
        if (result != null) {
            Long num = (Long) result;
            if (num.intValue() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /*public boolean existePorCampo(String ventidad, String vcampo1, Object vvalor1) throws Exception {
        String sql = "select count(e) from " + ventidad + " e where e." + vcampo1 + "=:vvalor1";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1);
        Long num = (Long) q.getSingleResult();
        if (num.intValue() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean existePor2Campos(String ventidad, String vcampo1, Object vvalor1, String vcampo2, Object vvalor2) throws Exception {
        String sql = "select count(e) from " + ventidad + " e where e." + vcampo1 + "=:vvalor1 and e." + vcampo2 + "=:vvalor2";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1).setParameter("vvalor2", vvalor2);
        Long num = (Long) q.getSingleResult();
        if (num.intValue() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean existePor3Campos(String ventidad, String vcampo1, Object vvalor1, String vcampo2, Object vvalor2, String vcampo3, Object vvalor3) throws Exception {
        String sql = "select count(e) from " + ventidad + " e where e." + vcampo1 + "=:vvalor1 and e." + vcampo2 + "=:vvalor2 and e." + vcampo3 + "=:vvalor3";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("vvalor1", vvalor1).setParameter("vvalor2", vvalor2).setParameter("vvalor3", vvalor3);
        Long num = (Long) q.getSingleResult();
        if (num.intValue() > 0) {
            return true;
        } else {
            return false;
        }

    }*/
    //</editor-fold>
    private <T, U> List<U> stringListToU(List<T> list, Function<T, U> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    //-----------Metodos genéricos para el manejo de SQL Nativo D.A.13062019------
    public List<Object[]> listObjetoNativoGenerico(String sentenciaSql) throws Exception {
        Query q = getEntityManager().createNativeQuery(sentenciaSql);
        return q.getResultList();
    }

    public String ejecQueryNativoGenerico(String sqlNoResults) {
        String s = "";
        try {
            Query q = getEntityManager().createNativeQuery(sqlNoResults);
            q.executeUpdate();
            s = "OK";
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            s = errors.toString();
        }
        return s;
    }
    
    public Connection conseguirConexion(){
        UtilitariosCod uti = new UtilitariosCod();        
        return uti.getConexion();
    } 
    
}
