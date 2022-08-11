/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.administracion.ejb.entidades.AdmColumna;
import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.ejb.utils.Cifrado;
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
public class AdmColumnaFacade extends AbstractFacade<AdmColumna> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmColumnaFacade() {
        super(AdmColumna.class);
    }

    public List<AdmColumna> listarTodosActivos() throws Exception {
        String sql = "   SELECT d "
                + "  FROM AdmColumna d "
                + " order by d.idColumna asc";
        Query q = em.createQuery(sql);
        List<AdmColumna> resultado = q.getResultList();
        return resultado;
    }

    public List<AdmColumna> consultarColumnasAEncriptarPorTabla(String tabla) throws Exception {
        String sql = "   SELECT c "
                + "  FROM AdmColumna c "
                + " WHERE c.encr=true and c.codTabla.nombre=:nombre"
                + " order by c.nombre asc";
        Query q = em.createQuery(sql);
        q.setParameter("nombre", tabla);
        List<AdmColumna> resultado = q.getResultList();
        return resultado;
    }

    public AdmColumna consultarColumnaPKPorTabla(String tabla) throws Exception {
        String sql = "   SELECT c "
                + "  FROM AdmColumna c "
                + " WHERE c.esPk=1 and c.codTabla.nombre=:nombre";
        Query q = em.createQuery(sql);
        q.setParameter("nombre", tabla);

        return (AdmColumna) q.getSingleResult();
    }

    public AdmColumna consultarPorTablaYColumna(String tabla, String columna) throws Exception {
        String sql = " SELECT c "
                + " FROM AdmColumna c "
                + " WHERE c.codTabla.nombre=:tabla and c.nombre=:columna";
        Query q = em.createQuery(sql);
        q.setParameter("tabla", tabla);
        q.setParameter("columna", columna);
        return (AdmColumna) q.getSingleResult();
    }

    public void encriptarCampoPorTabla(String tabla, String campo, String tipoEncriptacion) throws Exception {
        AdmColumna columnaPK = consultarColumnaPKPorTabla(tabla);
        String pkCampo = columnaPK.getNombre();
        //Genero el  update a ejecutar por registro
        StringBuilder sbSQLUpdate = new StringBuilder();
        sbSQLUpdate.append("UPDATE ").append(tabla);
        sbSQLUpdate.append(" SET ").append(campo).append("=:").append(campo);
        sbSQLUpdate.append(" WHERE ").append(pkCampo).append("=:").append(pkCampo);

        //Consulto el campo a encriptar en la tabla, para luego iterar.
        StringBuilder sbSQLSelect = new StringBuilder();
        sbSQLSelect.append("SELECT ").append(pkCampo).append(",").append(campo).append(" FROM ").append(tabla);
        Query q = getEntityManager().createNativeQuery(sbSQLSelect.toString());
        List resp = q.getResultList();
        for (Object obj : resp) {
            Object[] registro = (Object[]) obj;
            //Encripto el campo de la tabla.
            String encriptado = "";
            switch (tipoEncriptacion) {
                case "MD5":
                    encriptado = Cifrado.encriptaMD5(registro[1].toString());
                    break;
                case "AES":
                    String sql = " SELECT pg "
                            + " FROM AdmParametroGlobal pg "
                            + " WHERE pg.nombre=:nombre";
                    Query qPG = getEntityManager().createQuery(sql);
                    qPG.setParameter("nombre", "AES_ACCESS");
                    AdmParametroGlobal tmpParametroGLobal = (AdmParametroGlobal) qPG.getSingleResult();
                    String aesInec = tmpParametroGLobal.getValor();// "InecDiradGiapeZ1";
                            
                    encriptado = Cifrado.encriptaAES(aesInec, registro[1].toString());
                    break;
                default:
                    throw new Exception("No existe una implementacion para el tipo de encriptación " + tipoEncriptacion);
            }

            Query qUpdate = getEntityManager().createNativeQuery(sbSQLUpdate.toString());
            qUpdate.setParameter(campo, encriptado);
            qUpdate.setParameter(pkCampo, registro[0]);
            qUpdate.executeUpdate();
        }
    }
}
