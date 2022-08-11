/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.ejb.entidades.CaptCargaControlEquipo;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vespinoza
 */
@Stateless
public class CaptCargaControlEquipoFacade extends AbstractFacade<CaptCargaControlEquipo> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptCargaControlEquipoFacade() {
        super(CaptCargaControlEquipo.class);
    }

    public List<CaptCargaControlEquipo> listarPorCodCarCon(Integer codCarCon) {
        String sql = "select o from CaptCargaControlEquipo o "
                + "where o.estadoLogico=true and o.codCarCon.estadoLogico=true and o.codCarCon.idCarCon =:codCarCon "
                + "ORDER BY 4 ASC";
        Query q = em.createQuery(sql);
        q.setParameter("codCarCon", codCarCon);
        List<CaptCargaControlEquipo> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControlEquipo> listarPorCodigosCargaTrabajo(List<Integer> codigos) {
        String sql = "select o from CaptCargaControlEquipo o "
                + "where o.estadoLogico=true and o.codCarTra.estadoLogico=true and o.codCarTra.idCarTra in :codigos ";
        Query q = em.createQuery(sql);
        q.setParameter("codigos", codigos);
        List<CaptCargaControlEquipo> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControlEquipo> listarPorUsuarioYOperativo(Integer codUsuario, Integer codOperativo) {
        String sql = "select ce from CaptCargaControlEquipo ce where ce.estadoLogico=true and ce.codCarTra.estadoLogico=true and ce.codCarTra.codEquTraDet.codRolUsu.codUsuario.idUsuario=:codUsuario and ce.codCarTra.codEquTraDet.codFasOpe.codOperativo.idOperativo=:codOperativo order by ce.codCarCon.clave";
        Query q = em.createQuery(sql);
        q.setParameter("codUsuario", codUsuario);
        q.setParameter("codOperativo", codOperativo);
        List<CaptCargaControlEquipo> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControlEquipo> listarPorUsuarioYOperativoYClaveSector(Integer codUsuario, Integer codOperativo, String claveSector) {

        List<String> listaSectores = new ArrayList<>();
        listaSectores.add(claveSector);
        String sql = "select ce from CaptCargaControlEquipo ce where ce.estadoLogico=true and ce.codCarTra.estadoLogico=true and ce.codCarTra.codEquTraDet.codRolUsu.codUsuario.idUsuario=:codUsuario and ce.codCarTra.codEquTraDet.codFasOpe.codOperativo.idOperativo=:codOperativo and ce.codCarCon.clave in :listaSectores  order by ce.codCarCon.clave desc";

        if (claveSector.length() == 12 && claveSector.substring(6, 9).equals("999")) {
            String clavePosibleSectorLA = claveSector.substring(0, 6) + "000" + claveSector.substring(9, 12);
            listaSectores.add(clavePosibleSectorLA);

        }
        Query q = em.createQuery(sql);
        q.setParameter("codUsuario", codUsuario);
        q.setParameter("codOperativo", codOperativo);
        q.setParameter("listaSectores", listaSectores);
        List<CaptCargaControlEquipo> lst = q.getResultList();
        return lst;
    }

    public List<CaptCargaControlEquipo> listarPorRolOperativoYClaveSector(String aliasRol, Integer codOperativo, String claveSector) {

        List<String> listaSectores = new ArrayList<>();
        listaSectores.add(claveSector);
        String sql = "select ce from CaptCargaControlEquipo ce where ce.estadoLogico=true and ce.codCarTra.estadoLogico=true and ce.codCarTra.codEquTraDet.codRolUsu.codRol.alias=:aliasRol and ce.codCarTra.codEquTraDet.codFasOpe.codOperativo.idOperativo=:codOperativo and ce.codCarCon.clave in :listaSectores  order by ce.codCarCon.clave desc";

        if (claveSector.length() == 12 && claveSector.substring(6, 9).equals("999")) {
            String clavePosibleSectorLA = claveSector.substring(0, 6) + "000" + claveSector.substring(9, 12);
            listaSectores.add(clavePosibleSectorLA);

        }
        Query q = em.createQuery(sql);
        q.setParameter("aliasRol", aliasRol);
        q.setParameter("codOperativo", codOperativo);
        q.setParameter("listaSectores", listaSectores);
        List<CaptCargaControlEquipo> lst = q.getResultList();
        return lst;
    }
  
}
