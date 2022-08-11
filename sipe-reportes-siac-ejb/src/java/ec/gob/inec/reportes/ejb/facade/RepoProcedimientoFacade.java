/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.facade;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.reportes.ejb.entidades.RepoProcedimiento;
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
public class RepoProcedimientoFacade extends AbstractFacade<RepoProcedimiento> {

    @PersistenceContext(unitName = "sipe-reportes-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RepoProcedimientoFacade() {
        super(RepoProcedimiento.class);
    }

    public boolean existeEjecutarSQL(String funcion) throws Exception {

        Query q = em.createNativeQuery(funcion);
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

    public boolean existeEjecutarFuncion(String vnombreFuncion, List<Object> parametrosOrdenados) throws Exception {
        //RepoProcedimiento procConsulta = buscarProcedimientoXNombre(vnombreProcedimiento);
        StringBuilder sql = new StringBuilder("select cast(" + vnombreFuncion + "(");
        int numParametros = 1;
        for (Object obj : parametrosOrdenados) {
            String par = obj.toString().split("\\=")[0].split("\\:")[1];
            String valor = obj.toString().split("\\=")[1];
            sql.append(par + "\\:\\=" + valor);
            if (numParametros != parametrosOrdenados.size()) {
                sql.append(",");
            } else {
                sql.append(") as text)");
            }
            numParametros++;
        }
        Query q = em.createNativeQuery(sql.toString());
        Object result = q.getSingleResult();
        if (result != null) {
            if (result.toString().length() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
