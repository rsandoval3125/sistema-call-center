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
public class JSFFilaSeccion {
    
    private List<JSFVariable> listaVariablesPorFila;
    private final String nl = "\n";
    private final String nt = "\t";
    private final String dc = "\"";
    
    public JSFFilaSeccion(List<JSFVariable> listaVariablesPorFila_){
        listaVariablesPorFila=listaVariablesPorFila_;
    }
    
    public String obtenerFilaSeccion(){
        String numeroCols="1";
        String br="";
        if(!listaVariablesPorFila.isEmpty()){
            numeroCols=listaVariablesPorFila.size()+"";
            
            if(listaVariablesPorFila.size()==1 && listaVariablesPorFila.get(0).getVar().getTipoComp().equals("LBL")){
                br="<br/>";
            }
        }
        
        String f=nl+br+"<h:panelGrid columns="+dc+numeroCols+dc+">";
        
        for(JSFVariable v:listaVariablesPorFila){
            f=f+v.obtenerVariableJSF()+nl;
        }
        
        f=f+"</h:panelGrid>"+nl;
        return f;
        
    }
    
    public String obtenerDataTableSeccionHD(String aliasSeccion){
        String br="";
        String nombreControlador="";
        String nombreLista="";
        if(!listaVariablesPorFila.isEmpty()){
            nombreControlador=listaVariablesPorFila.get(0).getNombreControlador();
            nombreLista=listaVariablesPorFila.get(0).getNombreListaValores();
        }
        
        String f=nl+br+"<p:dataTable id="+dc+"lista"+aliasSeccion.replace(".", "")+dc+" value="+dc+"#{"+nombreControlador+"."+nombreLista+"}"+dc+" var="+dc+"fila"+dc+" >";
        
        for(JSFVariable v:listaVariablesPorFila){
            v.setNombreControlador("fila");
            String col="";
            col=v.getVar().getColumna()<10?"val0"+v.getVar().getColumna():"val"+v.getVar().getColumna();
            v.setNombreObjetoValue(col);
            f=f+nl+"<p:column headerText="+dc+v.getLabel()+dc+">"+nl;
            
            //f=f+v.obtenerComponenteJsf();
            //se programan las siguientes 2 lineas hasta probar
            String value = "value=" + dc + "#{" + v.getNombreControlador() + "." + v.getNombreObjetoValue() + "}" + dc;
            f=f+"<p:inputText "+value+"/>";
            
            f=f+nl+"</p:column>";
        }
        
        f=f+"</p:dataTable>"+nl;
        return f;
        
    }
    
}
