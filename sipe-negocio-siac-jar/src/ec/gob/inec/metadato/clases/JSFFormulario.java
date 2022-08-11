/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.clases;

import java.util.List;

/**
 *
 * @author dguano
 */
public class JSFFormulario {
    
    private List<JSFSeccion> listaSecciones;
    private final String nl = "\n";
    private final String nt = "\t";
    private final String dc = "\"";
    private Integer idFormulario;
    
    public JSFFormulario(List<JSFSeccion> listaSecciones_,Integer idFormulario){
        listaSecciones=listaSecciones_;
        this.idFormulario=idFormulario;
    }
    
    public String obtenerFormulario(){
        String f="<p:tabView id="+dc+"f"+idFormulario+dc+" widgetVar="+dc+"tvf"+idFormulario+dc+">"+nl;
        if(!listaSecciones.isEmpty()){
            for(JSFSeccion sec:listaSecciones){
                f=f+sec.obtenerSeccionNivel1JSF()+nl;
            }
        }
        f=f+"</p:tabView>";
        return f;
    }
    
    
}
