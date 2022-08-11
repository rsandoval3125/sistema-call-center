/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.clases;

import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import java.util.List;

/**
 *
 * @author dguano
 */
public class JSFSeccion {

    private MetSeccion seccion;
    private List<JSFFilaSeccion> listaFilasSeccion;
    private List<JSFSeccion> listaSeccionesHijas;

    private final String nl = "\n";
    private final String nt = "\t";
    private final String dc = "\"";

    public JSFSeccion(MetSeccion seccion, List<JSFFilaSeccion> listaFilasSeccion_, List<JSFSeccion> listaSeccionesHijas_) {
        this.seccion = seccion;
        this.listaFilasSeccion = listaFilasSeccion_;
        this.listaSeccionesHijas = listaSeccionesHijas_;
    }

    public String obtenerSeccionNivel1JSF() {
        String tab = "<p:tab id=" + dc + "s" + seccion.getIdSeccion() + dc + " title=" + dc + seccion.getTitulo() + dc + ">" + nl;
        tab = tab + "<p:panel header=" + dc + seccion.getDescripcion() + dc + ">";
        if (!listaFilasSeccion.isEmpty()) {
            for (JSFFilaSeccion f : listaFilasSeccion) {
                tab = tab + f.obtenerFilaSeccion() + nl;
            }
        }
        if (!listaSeccionesHijas.isEmpty()) {
            for (JSFSeccion sec2 : listaSeccionesHijas) {
                tab = tab + sec2.obtenerSeccionNivel2JSF() + nl;
            }
        }

        tab = tab + "</p:panel></p:tab>" + nl;
        return tab;

    }

    public String obtenerSeccionNivel2JSF() {
        String tab = "<p:panel header=" + dc + seccion.getTitulo() + dc + ">" + nl;
        tab = tab + "<h:panelGrid columns=" + dc + "1" + dc + " ";

        if (seccion.getTipo().contains("H")) {
            tab = tab + " border=" + dc + "1" + dc;
        }

        tab = tab + " >";

        if (!listaFilasSeccion.isEmpty()) {
            //if (seccion.getTipo().contains("HD")) {
            //        tab=tab+listaFilasSeccion.get(0).obtenerDataTableSeccionHD(seccion.getAlias());
            //} else {
                for (JSFFilaSeccion f : listaFilasSeccion) {
                    tab = tab + f.obtenerFilaSeccion() + nl;
                }
            //}

        }
        if (!listaSeccionesHijas.isEmpty()) {
            for (JSFSeccion sec3 : listaSeccionesHijas) {
                tab = tab + sec3.obtenerSeccionNivel3JSF() + nl;
            }
        }
        tab = tab + "</h:panelGrid>" + nl + "</p:panel>";
        return tab;

    }

    public String obtenerSeccionNivel3JSF() {
        String tab = "<p:panel header=" + dc + seccion.getTitulo() + dc + ">" + nl;
        tab = tab + "<h:panelGrid columns=" + dc + "1" + dc + ">";
        if (!listaFilasSeccion.isEmpty()) {
            for (JSFFilaSeccion f : listaFilasSeccion) {
                tab = tab + f.obtenerFilaSeccion() + nl;
            }
        }
        tab = tab + "</h:panelGrid>" + nl + "</p:panel>";
        return tab;

    }

}
