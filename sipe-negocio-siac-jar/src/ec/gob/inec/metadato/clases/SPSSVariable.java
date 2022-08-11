/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.clases;

import ec.gob.inec.metadato.ejb.entidades.MetVariable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dguano
 */
public class SPSSVariable {

    private MetVariable var;
    private String nombre;
    private String maxLength;
    private String label;
    private String valores;
    private String tipoDato;
    private Catalogo catalogo;

    private final String nl = "\n";
    private final String nt = "\t";
    private final String dc = "\"";

    private String sintaxisAlterType;
    private String sintaxisWidth;
    private String sintaxisVarLabel;
    private String sintaxisAddValueLabels;

    public SPSSVariable(MetVariable var_, Catalogo catalogo_) {
        var = var_;
        catalogo=catalogo_;
        if (var.getCodConcepto() != null) {
            definirNombre();
            definirTipoDato();
            definirMaxLength();
            definirLabel();
            
            definirAlterType();
            definirWidth();
            definirVarLabel();
            definirAddValueLabels();
        }

    }

    private void definirNombre() {
        nombre = var.getIdentificador().trim().replace(" ", "_");
    }

    private void definirTipoDato() {
        tipoDato = var.getCodConcepto().getCodCatDato().getAlias();
    }

    private void definirMaxLength() {
        if (var.getCodConcepto().getLongitudMaxima() != null && var.getCodConcepto().getLongitudMaxima() != 0) {
            maxLength = var.getCodConcepto().getLongitudMaxima() + "";
        } else {
            maxLength = "1";
        }
    }

    private void definirLabel() {
        if (var.getPregunta() != null && !var.getPregunta().equals("")) {
            label = html2stringDavid(var.getPregunta());
        }
    }
    
    private void definirAlterType(){
        String tipoDatoSPSS="";
        String reemplazo="";
        if(tipoDato.equals("TEXTO")){
            tipoDatoSPSS="a"+maxLength;
        }else if(tipoDato.equals("ENTERO")){
            tipoDatoSPSS="f"+maxLength+".0";
        }else if(tipoDato.equals("NUMERICO")){
            reemplazo="COMPUTE "+nombre+"=REPLACE("+nombre+",'.',',')." +nl+"exe."+nl;
            tipoDatoSPSS="f"+maxLength+".2";
        }
        sintaxisAlterType=reemplazo+ "ALTER TYPE "+nombre+"("+tipoDatoSPSS+").";
    }
    
    private void definirWidth(){
        sintaxisWidth="VARIABLE WIDTH "+nombre+"(10).";
        if(tipoDato.equals("TEXTO")){
            if(Integer.valueOf(maxLength)>50){
                sintaxisWidth="VARIABLE WIDTH "+nombre+"(50).";
            }else{
                sintaxisWidth="VARIABLE WIDTH "+nombre+"("+maxLength+").";
            }
                
            
        }else {
            sintaxisWidth="VARIABLE WIDTH "+nombre+"(5).";
        }
        
    }
    
    private void definirVarLabel(){
        sintaxisVarLabel="VARIABLE LABELS "+nombre+" '"+label+"'.";
    }
    
    private void definirAddValueLabels(){
        sintaxisAddValueLabels=obtenerValores(catalogo, tipoDato);
    }

    private String obtenerValores(Catalogo catalogo_, String tipoDato) {
        String valores = "";
        String c = "";
        if (tipoDato.equals("TEXTO")) {
            c = "'";
        }
        if (var.getCodConcepto().getCategorica()) {
            if (var.getCodConcepto().getCodTipoCatalogo() != null) {
                if (catalogo_ != null && catalogo_.getItems() != null && !catalogo_.getItems().isEmpty()) {
                    valores = "ADD VALUE LABELS " + nombre + " ";
                    for (ItemCatalogo it : catalogo_.getItems()) {
                        valores = valores + c + it.getItemValue() + c + " '" + it.getItemLabel() + "' "+nl;
                    }
                }
                valores = valores + ".";
            } else {
                //categoria con rango minimo maximo
                //construir catalogo sin opciones de base de datos
                int rmin = 0;
                int rmax = 0;
                valores = "ADD VALUE LABELS " + nombre + " ";
                if (var.getCodConcepto().getRangoMinimo() != null && var.getCodConcepto().getRangoMaximo() != null) {
                    try {
                        rmin = Integer.valueOf(var.getCodConcepto().getRangoMinimo());
                        rmax = Integer.valueOf(var.getCodConcepto().getRangoMaximo());

                        if (rmin >= rmax) {
                            rmin = 1;
                            rmax = 2;
                        }
                    } catch (NumberFormatException nfex) {
                        rmin = 1;
                        rmax = 2;
                        System.out.println("error en rangos minimos maximos de catalogo");
                    }
                } else {
                    rmin = 1;
                    rmax = 2;
                }
                List<ItemCatalogo> lstIc = new ArrayList<>();
                for (int i = rmin; i <= rmax; i++) {
                    ItemCatalogo ic = new ItemCatalogo(i + "", i + "", "", "");
                    lstIc.add(ic);
                }
                for (ItemCatalogo it : catalogo_.getItems()) {
                    valores = valores + c + it.getItemValue() + c + " '" + it.getItemLabel() + "' ";
                }
                valores = valores + ".";
            }
        }
        return valores;
    }

    public String obtenerSintaxisVariableSPSS() {
        String sintaxis="";
        sintaxis=sintaxisAlterType+nl;
        sintaxis=sintaxis+sintaxisWidth+nl;
        sintaxis=sintaxis+sintaxisVarLabel+nl;
        sintaxis=sintaxis+sintaxisAddValueLabels+nl;
        return sintaxis;
    }

    public MetVariable getVar() {
        return var;
    }

    public void setVar(MetVariable var) {
        this.var = var;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String html2stringDavid(String html){
        String s="";  
        s=html.replaceAll("<.*?>" , " ");
        s=s.replaceAll("&.*?;" , "");
        return s;
    }
    
    

}
