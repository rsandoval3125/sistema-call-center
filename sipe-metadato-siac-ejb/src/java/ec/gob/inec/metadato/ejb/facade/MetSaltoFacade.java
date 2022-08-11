/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.ejb.facade;


import ec.gob.inec.metadato.ejb.entidades.MetSalto;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;

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
public class MetSaltoFacade extends AbstractFacade<MetSalto> {

    @PersistenceContext(unitName = "sipe-metadato-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetSaltoFacade() {
        super(MetSalto.class);
    }

    public List<MetSalto> listarSaltoXSeccion(Integer codSecc) throws Exception {
        String sql = "SELECT s "
                + "FROM MetSalto s , MetVariable v "
                + "  WHERE s.codVar=v.idVar  "
                + "  AND  v.codSeccion.idSeccion=:codSecc "
                + "  AND s.estadoLogico = true "
                + "  AND v.estadoLogico = true "
                + "  ORDER BY s.codVar ";
        Query q = em.createQuery(sql);
        q.setParameter("codSecc", codSecc);
        return q.getResultList();
    }

    public List<MetSeccion> listarSeccionXForm(Integer codForm) throws Exception {
        String sql = "SELECT c "
                + "FROM MetSeccion  c, metFormulario f, metFormularioSeccion  fs "
                + "WHERE  c.idSeccion=fs.codSeccio"
                + "AND fs.codFormulario=:codForm"
                + "AND fs.codFormulario=f.idFormulario"
                + "AND c.estadoLogico = true "
                + "AND f.estadoLogico = true"
                + "ORDER BY 1 ";

        Query q = em.createQuery(sql);
        q.setParameter("codForm", codForm);
        return q.getResultList();
    }

    public List<Object []> listarSaltos(Integer codForm) throws Exception {
        String sql = "SELECT orden, identificador, salto from metadato.fn_listar_saltosnew(:codForm)";
        Query q = em.createNativeQuery(sql);
        q.setParameter("codForm", codForm);
        return q.getResultList();
    }
}
