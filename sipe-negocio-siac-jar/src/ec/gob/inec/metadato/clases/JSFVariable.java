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
public class JSFVariable {

    private MetVariable var;
    private String id;
    private String name;
    private String widgetVar;
    private String required;
    private String maxLength;
    private String size;
    private String lstyle;
    private String lstyleClass;
    private String pstyle;
    private String pstyleClass;
    private String label;
    private String cols;
    private String rows;
    private String textoTooltip;
    private int rmin;
    private int rmax;

    private String tipoSeccion;
    private String nombreControlador;
    private String nombreListaValores;
    private int idxVertical;
    private int columna;
    private String nombreObjetoValue;

    private final String onKeyPress = "";
    private final String onClick = "";

    private Catalogo catalogo;

    private final String nl = "\n";
    private final String nt = "\t";
    private final String dc = "\"";
    private final String htmlBR = "&lt;br&gt;";

    public JSFVariable(MetVariable var_, Catalogo catalogo_, String tipoSeccion_, String nombreControlador_, String nombreListaValores_, int idxVertical_, int columna_) {
        var = var_;
        tipoSeccion = tipoSeccion_;
        nombreControlador = nombreControlador_;
        nombreListaValores = nombreListaValores_;
        idxVertical = idxVertical_;
        columna = columna_;
        if (var.getCodConcepto() != null) {
            definirId();
            definirName();//no se utiliza en JSF
            definirWidgetVar();
            definirRequired();
            definirMaxLength();
            definirSize();
            definirStyleComp();
            definirStyleClassComp();
            definirColsyRows();
            definirSelectItems(catalogo_);
            definirNombreObjetoValue();
            if (var.getMuestraDescripcion()) {
                definirLabel();
                definirStyleClassLabel();
                definirStyleLabel();
            } else {
                label = "";
            }
        } else {
            //para variables sin componentes, solo descriptivas
            definirLabel();
            definirStyleClassLabel();
            definirStyleLabel();
        }

    }

    private void definirId() {
        id = var.getIdentificador().trim().replace(" ", "_");
    }

    private void definirName() {
        name = var.getIdentificador().trim().replace(" ", "_");
    }

    private void definirWidgetVar() {
        widgetVar = var.getIdentificador().trim().replace(" ", "_");
    }

    private void definirRequired() {
        //if (var.getCodConcepto().getLongitudMinima() == null || var.getCodConcepto().getLongitudMinima() == 0) {
        required = "false";
        //} else {
        //    required = "true";
        //}
    }

    private void definirMaxLength() {
        if (var.getCodConcepto().getLongitudMaxima() != null && var.getCodConcepto().getLongitudMaxima() != 0) {
            maxLength = var.getCodConcepto().getLongitudMaxima() + "";
        } else {
            maxLength = "1";
        }
    }

    private void definirSize() {
        if (var.getCodConcepto().getLongitudMaxima() != null && var.getCodConcepto().getLongitudMaxima() != 0) {
            size = (var.getCodConcepto().getLongitudMaxima() + 1) + "";
        } else {
            size = "2";
        }
    }

    private void definirStyleLabel() {
        if (var.getStyleLabel() != null && !var.getStyleLabel().equals("") && !var.getStyleLabel().equals(" ")) {
            lstyle = var.getStyleLabel();
        } else {
            lstyle = "NA";
        }
    }

    private void definirStyleClassLabel() {
        if (var.getStyleclassLabel() != null && !var.getStyleclassLabel().equals("") && !var.getStyleclassLabel().equals(" ")) {
            lstyleClass = var.getStyleclassLabel();
        } else {
            lstyleClass = "NA";
        }
    }

    private void definirStyleComp() {
        if (var.getStyleComp() != null && !var.getStyleComp().equals("") && !var.getStyleComp().equals(" ")) {
            pstyle = var.getStyleComp();
        } else {
            pstyle = "NA";
        }
    }

    private void definirStyleClassComp() {
        if (var.getStyleclassComp() != null && !var.getStyleclassComp().equals("") && !var.getStyleclassComp().equals(" ")) {
            pstyleClass = var.getStyleclassComp();
        } else {
            pstyleClass = "NA";
        }
    }

    private void definirLabel() {
        if (var.getPregunta() != null && !var.getPregunta().equals("")) {
            label = var.getPregunta();
            label = label.replace("<", "&lt;");
            label = label.replace(">", "&gt;");
            label = label.replace("\"", "'");
        }
    }

    private void definirColsyRows() {
        if (var.getTipoComp() != null && var.getTipoComp().startsWith("TA") && var.getTipoComp().length() == 3 && var.getCodConcepto().getLongitudMaxima() != null && var.getCodConcepto().getLongitudMaxima() != 0) {

            try {
                String numrows = var.getTipoComp().substring(2, 3);
                int r = Integer.valueOf(numrows);
                rows = r + "";
            } catch (NumberFormatException nfex) {
                rows = "1";
                System.out.println("error en tipo comp text area");
            }
            int r1 = var.getCodConcepto().getLongitudMaxima() / Integer.valueOf(rows);
            cols = (r1 + 5) + "";
        } else {
            cols = "10";
            rows = "2";
        }

    }

    private void definirSelectItems(Catalogo catalogo_) {

        if (var.getCodConcepto().getCategorica()) {
            if (var.getCodConcepto().getCodTipoCatalogo() != null && catalogo_ != null) {
                catalogo = catalogo_;
                if (catalogo.getItems() != null && catalogo.getItems().size() > 1) {
                    rmin = Integer.valueOf(catalogo.getItems().get(0).getItemValue());
                    rmax = Integer.valueOf(catalogo.getItems().get(catalogo.getItems().size() - 1).getItemValue());
                } else {
                    rmin = 1;
                    rmax = 2;
                }

            } else {
                //construir catalogo sin opciones de base de datos
                catalogo = new Catalogo(0, "");
                rmin = 0;
                rmax = 0;
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
                catalogo.setItems(lstIc);

            }
        }
    }

    public void definirNombreObjetoValue() {
        if (tipoSeccion.contains("V")) {
            nombreObjetoValue = nombreListaValores + ".get(" + idxVertical + ").valor";
        } else if (tipoSeccion.contains("H")) {
            String col = "";
            col = (columna < 10) ? "0" + columna : "" + columna;
            nombreObjetoValue = nombreListaValores + ".get(" + idxVertical + ").val" + col;
        }
    }

    public String obtenerLabelJSF(boolean tieneFor) {
        String lfor = "";
        if (tieneFor) {
            lfor = " for=" + dc + id + dc + " ";
        }
        String l = nl + "<p:outputLabel escape=" + dc + "false" + dc + " value=" + dc + label + dc + " " + lfor;
        if (lstyle != null && !lstyle.equals("NA")) {
            l = l + " style=" + dc + lstyle + dc + " ";
        }
        if (lstyleClass != null && !lstyleClass.equals("NA")) {
            l = l + " styleClass=" + dc + lstyleClass + dc + " ";
        }
        l = l + " />" + nl;
        return l;
    }

    public String obtenerComponenteJsf() {
        String c = nl + "";
        String value = "value=" + dc + "#{" + nombreControlador + "." + nombreObjetoValue + "}" + dc;
        String c1 = "";
        String opciones = "";
        String fin = "";
        if (var.getTipoComp().contains("IT")) {
              c = "<p:inputText alt="+"\""+idxVertical+"\""+" "+"id=" + dc + id + dc + " widgetVar=" + dc + widgetVar + dc + " " + value
                    + " size=" + dc + size + dc + " autocomplete=" + dc + "off" + dc + " maxlength=" + dc + maxLength + dc + " required=" + dc + required + dc + " ";

            if (var.getTipoComp().equals("ITN")) {
                //INPUT TEXT NUMERICO
                c = c + " onkeypress=" + dc + "return fn_entero(event)" + dc;
            } else if (var.getTipoComp().equals("ITA")) {
                //INPUT TEXT ALFABETICO
                c = c + " onkeypress=" + dc + "return fn_letras(event)" + dc;
            } else if (var.getTipoComp().equals("ITT")) {
                //INPUT TEXT ALFANUMERICO
                c = c + " onkeypress=" + dc + "return fn_alfaNumerico(event)" + dc;
            } else if (var.getTipoComp().equals("ITD")) {
                //INPUT TEXT DECIMAL
                c = c + " onkeypress=" + dc + "return fn_decimal(event, 2,'" + id + "')" + dc;
            }

            if (var.getCodConcepto().getCategorica()) {
                //INPUTTEXT CON OPCIONES
                c = c + " onblur=" + dc + "return fn_rango(" + rmin + "," + rmax + ",'" + id + "')" + dc;
            }
            c1 = "";
            fin = " />";

        } else if (var.getTipoComp().startsWith("TA")) {
            c = "<p:inputTextarea id=" + dc + id + dc + " widgetVar=" + dc + widgetVar + dc + " " + value
                    + " maxlength=" + dc + maxLength + dc + " required=" + dc + required + dc + " cols=" + dc + cols + dc + " rows=" + dc + rows + dc;
            c1 = "";
            fin = " />";

        } else if (var.getTipoComp().contains("CH")) {
            c = "<p:selectBooleanCheckbox id=" + dc + id + dc + " widgetVar=" + dc + widgetVar + dc + " " + value
                    + " required=" + dc + required + dc;
            c1 = "";
            fin = " />";

        } else if (var.getTipoComp().contains("SM")) {
            c = "<p:selectOneMenu id=" + dc + id + dc + " widgetVar=" + dc + widgetVar + dc + " " + value
                    + " required=" + dc + required + dc;
            opciones = obtenerItemsDeListaJSF(catalogo);
            c1 = ">";
            fin = nl + "</p:selectOneMenu>";

        } else if (var.getTipoComp().contains("SR")) {

            String col = "";
            try {
                String numcols = var.getTipoComp().substring(2, 3);
                int n = Integer.valueOf(numcols);
                col = n + "";
            } catch (NumberFormatException nfex) {
                col = "1";
                System.out.println("error en tipo comp radio");
            }

            c = "<p:selectOneRadio id=" + dc + id + dc + " widgetVar=" + dc + widgetVar + dc + " " + value
                    + " required=" + dc + required + dc + " layout=" + dc + "grid" + dc + " columns=" + dc + col + dc + " ";

            c1 = ">";
            opciones = obtenerItemsDeListaJSF(catalogo);
            fin = nl + "</p:selectOneRadio>";

        } else if (var.getTipoComp().contains("LBL")) {
            //pendiente programar
        } else if (var.getTipoComp().contains("VJS")||var.getTipoComp().contains("BJS")) {
            
            c = "<p:inputText id=" + dc + id + dc + " widgetVar=" + dc + widgetVar + dc + " " + value
                    + " size=" + dc + size + dc + " autocomplete=" + dc + "off" + dc + " maxlength=" + dc + maxLength + dc + " readonly=" + dc + "true" + dc + " ";
            c1 = "";
            fin = " />";
        }

        if (pstyle != null && !pstyle.equals("NA")) {
            c = c + " style=" + dc + pstyle + dc + " ";
        }
        if (pstyleClass != null && !pstyleClass.equals("NA")) {
            c = c + " styleClass=" + dc + pstyleClass + dc + " ";
        }
        c = c + c1 + opciones + fin;

        return c;
    }

    public String obtenerItemsDeListaJSF(Catalogo catalogo_) {
        String lista = nl + "";
        if (!catalogo_.getItems().isEmpty()) {
            for (ItemCatalogo it : catalogo_.getItems()) {
                lista = lista + "<f:selectItem itemLabel=" + dc + it.getItemLabel() + dc + " itemValue=" + dc + it.getItemValue() + dc + "/>" + nl;
            }
        }
        return lista;
    }

    public String obtenerTooltipDeOpcionesJSF(Catalogo catalogo_) {
        String pos="";
        if(var.getUbiDescripcion().equals("N")){
            pos="right";
        }else {
            pos="top";
        }
        String tooltip = nl + "<p:tooltip id=" + dc + "tt" + id + dc + " for=" + dc + id + dc + " showEvent=" + dc + "focus" + dc + " hideEvent=" + dc + "blur" + dc + " position=" + dc + pos + dc + " escape=" + dc + "false" + dc + " value=" + dc;
        if (catalogo_ != null && catalogo_.getItems() != null && !catalogo_.getItems().isEmpty()) {
            for (ItemCatalogo it : catalogo_.getItems()) {
                tooltip = tooltip + it.getItemValue() + " - " + it.getItemLabel() + htmlBR;
            }
        }
        tooltip = tooltip + dc + "/>" + nl;
        return tooltip;
    }

    public String obtenerVariableJSF() {
        String border = "";
        if (var.getCodSeccion().getTipo().contains("H")) {
            border = "border=" + dc + "1" + dc;
        }
        String v = nl + "<h:panelGrid " + border + " columns=" + dc;
        String p = "";
        if (var.getUbiDescripcion() != null && !var.getUbiDescripcion().equals("") && !var.getUbiDescripcion().equals(" ")) {
            if (var.getUbiDescripcion().equals("N") || var.getUbiDescripcion().equals("S")) {
                v = v + "1" + dc + ">";
            } else {
                v = v + "2" + dc + ">";
            }
        } else {
            v = v + "2" + dc + ">";
        }

        boolean tieneFor=true;
        if(var.getTipoComp().equals("LBL")){
            tieneFor=false;
        }
        if (var.getUbiDescripcion() != null && !var.getUbiDescripcion().equals("") && !var.getUbiDescripcion().equals(" ")) {
            if (var.getUbiDescripcion().equals("N") || var.getUbiDescripcion().equals("O")) {
                v = v + obtenerLabelJSF(tieneFor) + obtenerComponenteJsf();
            } else {
                v = v + obtenerComponenteJsf() + nl + obtenerLabelJSF(tieneFor);
            }
        } else {
            v = v + obtenerLabelJSF(tieneFor) + nl + obtenerComponenteJsf();
        }

        if (var.getCodConcepto().getCategorica()) {
            if (var.getTipoComp().contains("SM") || var.getTipoComp().contains("SR")) {
                v = v + nl + "</h:panelGrid>" + nl;
            } else {
                v = v + nl + obtenerTooltipDeOpcionesJSF(catalogo) + nl + "</h:panelGrid>" + nl;
            }
        } else {
            v = v + nl + "</h:panelGrid>" + nl;
        }

        return v;
    }

    public MetVariable getVar() {
        return var;
    }

    public void setVar(MetVariable var) {
        this.var = var;
    }

    public String getNombreControlador() {
        return nombreControlador;
    }

    public void setNombreControlador(String nombreControlador) {
        this.nombreControlador = nombreControlador;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNombreObjetoValue() {
        return nombreObjetoValue;
    }

    public void setNombreObjetoValue(String nombreObjetoValue) {
        this.nombreObjetoValue = nombreObjetoValue;
    }

    public String getNombreListaValores() {
        return nombreListaValores;
    }

    public void setNombreListaValores(String nombreListaValores) {
        this.nombreListaValores = nombreListaValores;
    }

}
