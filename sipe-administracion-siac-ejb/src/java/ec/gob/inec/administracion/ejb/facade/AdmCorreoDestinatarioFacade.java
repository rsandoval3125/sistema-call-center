/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.administracion.ejb.facade;

import ec.gob.inec.administracion.ejb.entidades.AdmCorreoCab;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDestinatario;
import ec.gob.inec.administracion.ejb.entidades.AdmCorreoDet;
import ec.gob.inec.administracion.ejb.entidades.AdmParametroGlobal;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.ejb.utils.DireccionEmail;
import ec.gob.inec.ejb.utils.Email;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
@Stateless
public class AdmCorreoDestinatarioFacade extends AbstractFacade<AdmCorreoDestinatario> {

    @PersistenceContext(unitName = "sipe-administracion-siac-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmCorreoDestinatarioFacade() {
        super(AdmCorreoDestinatario.class);
    }

    public List<AdmCorreoDestinatario> consultarPorIdCorreoCab(Integer idCorreoCab) throws Exception {
        String sql = "SELECT cd "
                + "  FROM AdmCorreoDestinatario cd "
                + " WHERE cd.estadoLogico=true and cd.codCorreoCab.idCorreoCab=:idCorreoCab";
        Query q = em.createQuery(sql);
        q.setParameter("idCorreoCab", idCorreoCab);
        List<AdmCorreoDestinatario> resultado = q.getResultList();
        return resultado;
    }

    public void enviarCorreo(String alias, Map<String, String> params) throws Exception {
        enviarCorreo(alias, "", params);
    }

    public void enviarCorreo(String alias, String direccionDestino, Map<String, String> params) throws Exception {

        String sql = " SELECT pg FROM AdmParametroGlobal pg "
                + " WHERE pg.estadoLogico=true and pg.nombre=:nombre";
        Query q = em.createQuery(sql);
        q.setParameter("nombre", "CORREO_SERVIDOR");
        AdmParametroGlobal servidor = (AdmParametroGlobal) q.getSingleResult();
        q.setParameter("nombre", "CORREO_DIRECCION_ORIGEN");
        AdmParametroGlobal correoFrom = (AdmParametroGlobal) q.getSingleResult();

        String css = "<style type='text/css'>.tg  {border-collapse:collapse;border-spacing:0;}.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}.tg .tg-u227{font-weight:bold;background-color:#bbdaff;text-align:center}.tg .tg-znc0{font-weight:bold;color:#010066}</style>";
        String html = "<table class='tg'>\n"
                + "  <tr>\n"
                + "    <th class='tg-u227' colspan='2'>[CABECERA] - INEC<br></th>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "     <td class='tg-031e' colspan=2>[CONTENIDO]"
                + "     </td>\n"
                + "  </tr>\n"
                + "  <tr>\n"
                + "     <td class='tg-znc0'>Detalles:</td>\n"
                + "     <td class='tg-031e'> <ul> [PIE]"
                + "     </ul></td>\n"
                + "  </tr>\n"
                + "</table>";
        String nombreAplicacion = params.get("APLICACION");
        html = html.replace("[CABECERA]", nombreAplicacion);

        String sqlCorreoCab = " SELECT cc FROM AdmCorreoCab cc "
                + " WHERE cc.estadoLogico=true and cc.alias=:alias";
        Query qCorreoCab = em.createQuery(sqlCorreoCab);
        qCorreoCab.setParameter("alias", alias);
        AdmCorreoCab admCorreoCab = (AdmCorreoCab) qCorreoCab.getSingleResult();
        Integer idCorreoCab = admCorreoCab.getIdCorreoCab();
        String asuntoCorreo = admCorreoCab.getAsunto();
        String importancia = admCorreoCab.getCodTipoPrioridad().getNombre();
        params.put("CONT_IMPORTANCIA", "Importancia: " + importancia);
        StringBuilder sbContenido = new StringBuilder();
        for (Entry<String, String> item : params.entrySet()) {
            if (item.getKey().startsWith("CONT")) {
                sbContenido.append("<p>").append(item.getValue()).append("</p>");
            }
        }

        boolean aceptaParamsSistema = false;
        StringBuilder sbPie = new StringBuilder();
        String sqlCorreoDet = " SELECT cd FROM AdmCorreoDet cd "
                + " WHERE cd.estadoLogico=true and cd.codCorreoCab.idCorreoCab=:idCorreoCab";
        Query qCorreoDet = em.createQuery(sqlCorreoDet);
        System.out.println("idCorreoCab=" + idCorreoCab);
        qCorreoDet.setParameter("idCorreoCab", idCorreoCab);
        List<AdmCorreoDet> admCorreoDets = qCorreoDet.getResultList();
        for (AdmCorreoDet cd : admCorreoDets) {
            aceptaParamsSistema = cd.getParametroSistema();
            sbPie.append("<li>").append(cd.getDetalle()).append("</li>");
        }
        html = html.replace("[PIE]", sbPie.toString());
        if (aceptaParamsSistema) {
            html = html.replace("[CONTENIDO]", sbContenido.toString());
        } else {
            html = html.replace("[CONTENIDO]", "");
        }
        Map<String, DireccionEmail> correosTo = new HashMap<>();
        if (direccionDestino.equals("")) {
            List<AdmCorreoDestinatario> correosDestinatarios = consultarPorIdCorreoCab(idCorreoCab);
            for (AdmCorreoDestinatario correoDestinatario : correosDestinatarios) {
                if (correoDestinatario.getEstadoDestinatario()) {//Solo se envia correos a los destinatarios activos
                    String direccionCorreo = correoDestinatario.getCodPersonal().getCorreoInstitucional();
                    correosTo.put(direccionCorreo + correoDestinatario.getCodTipoDestinatario().getAlias(), new DireccionEmail(correoDestinatario.getCodTipoDestinatario().getAlias(), direccionCorreo));
                }
            }
        } else {
            correosTo.put(direccionDestino, new DireccionEmail("TO", direccionDestino));
        }
        Email.enviar(servidor.getValor(), correoFrom.getValor(), correosTo, asuntoCorreo, nombreAplicacion, css + html);
    }
}
