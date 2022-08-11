/*
 * Copyright 2011-2015 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ec.gob.inec.presentacion.clases.reportes.excel;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datalist.DataList;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.component.row.Row;
import org.primefaces.component.rowexpansion.RowExpansion;
import org.primefaces.component.subtable.SubTable;
import org.primefaces.component.summaryrow.SummaryRow;
import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.util.Constants;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

/**
 * <code>Exporter</code> component.
 *
 * @author Sudheer Jonna / last modified by $Author$
 * @since 0.7.0
 */
public class PDFExporter extends Exporter {

    private static final Logger LOGGER = Logger.getLogger(PDFExporter.class.getName());
    private Font cellFont;
    private Font facetFont;
    private Color facetBackground;
    private Float facetFontSize;
    private Color facetFontColor;
    private String facetFontStyle;
    private String fontName;
    private Float cellFontSize;
    private Color cellFontColor;
    private String cellFontStyle;
    private int datasetPadding;
    private String orientation;
    private List<Object[]> lstFor2Carto;
    private DataTable styleTable;
    private Integer numeroPag;

    public Integer getNumeroPag() {
        return numeroPag;
    }

    public void setNumeroPag(Integer numeroPag) {
        this.numeroPag = numeroPag;
    }

    @Override
    //public void export(ActionEvent event, String tableId, FacesContext context, String filename, String tableTitle, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, boolean subTable) throws IOException {
    public void export(ActionEvent event, String tableId, FacesContext context, String filename, List<String> subtitulos, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, boolean subTable, String titulo, List<Object[]> resumenFiltro, List<Object[]> lstFor2) throws IOException {

        try {
            Document document = new Document();
            if (orientation.equalsIgnoreCase("Landscape")) {
                document.setPageSize(PageSize.A3.rotate());
                document.setMargins(20, 20, 10, 10);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            if (preProcessor != null) {
                preProcessor.invoke(context.getELContext(), new Object[]{document});
            }

            lstFor2Carto = new ArrayList<>();
            for (Object[] obj : lstFor2) {
                lstFor2Carto.add(obj);
            }

            StringTokenizer st = new StringTokenizer(tableId, ",");
            while (st.hasMoreElements()) {
                String tableName = (String) st.nextElement();
                UIComponent component = SearchExpressionFacade.resolveComponent(context, event.getComponent(), tableName);
                if (component == null) {
                    throw new FacesException("Cannot find component \"" + tableName + "\" in view.");
                }
                if (!(component instanceof DataTable || component instanceof DataList)) {
                    throw new FacesException("Unsupported datasource target:\"" + component.getClass().getName() + "\", exporter must target a PrimeFaces DataTable/DataList.");
                }

                if (!document.isOpen()) {
                    document.open();
                }

                if (titulo != null && !titulo.isEmpty() && !tableId.contains("" + ",")) {

                    Font tableTitleFont = FontFactory.getFont(FontFactory.TIMES, encodingType, 16);
                    tableTitleFont.setStyle(Font.BOLD);
                    Paragraph title = new Paragraph(titulo, tableTitleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);

                    /*Paragraph preface = new Paragraph();
                    addEmptyLine(preface, 1);
                    document.add(preface);*/
                }

                for (String subtitulo : subtitulos) {
                    if (subtitulo != null && !subtitulo.isEmpty() && !subtitulo.contains("" + ",")) {
                        Font tableSubTitleFont = FontFactory.getFont(FontFactory.TIMES, encodingType, 13);
                        tableSubTitleFont.setStyle(Font.BOLD);
                        Paragraph subtitle = new Paragraph(subtitulo, tableSubTitleFont);
                        subtitle.setAlignment(Element.ALIGN_CENTER);
                        document.add(subtitle);
                    }
                }

                Paragraph prefaceSbt = new Paragraph();
                addEmptyLine(prefaceSbt, 1);
                document.add(prefaceSbt);

                int indexLstFor2 = resumenFiltro.size() - 1;
                int indexLstFor2NoIncremental = resumenFiltro.size() - 1;

                Font boldFor2 = FontFactory.getFont(FontFactory.TIMES, encodingType, 8);
                boldFor2.setStyle(Font.BOLD);
                Font normalFor2 = FontFactory.getFont(FontFactory.TIMES, encodingType, 8);
                normalFor2.setStyle(Font.NORMAL);

                List<Object[]> resumenFiltroFor2 = new ArrayList<>();
                /*for (Object[] obj : resumenFiltro) {
                    resumenFiltroFor2.add(obj);
                }*/
                if (!lstFor2.isEmpty()) {
                    imagenINEC(document);
                    /*for (Object[] obj : lstFor2) {
                        resumenFiltroFor2.add(obj);
                    }*/

                    PdfPTable tableZonal = new PdfPTable(2);
                    tableZonal.setTotalWidth(180);
                    tableZonal.setLockedWidth(true);
                    tableZonal.setWidths(new float[]{9, 27});

                    PdfPCell cellZonal = new PdfPCell(new Paragraph(String.valueOf(resumenFiltro.get(0)[0]), boldFor2));
                    cellZonal.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cellZonal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableZonal.addCell(cellZonal);

                    cellZonal = new PdfPCell(new Paragraph(String.valueOf(resumenFiltro.get(0)[1]), normalFor2));
                    cellZonal.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellZonal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tableZonal.addCell(cellZonal);
                    document.add(tableZonal);

                    //TABLA 2
                    PdfPTable mainTable = new PdfPTable(2);
                    mainTable.setWidthPercentage(100.0f);

                    //Tabla izquierda
                    PdfPCell firstTableCell = new PdfPCell();
                    firstTableCell.setBorder(PdfPCell.NO_BORDER);

                    PdfPTable table1 = new PdfPTable(13);
                    table1.setTotalWidth(700);
                    table1.setLockedWidth(true);
                    table1.setWidths(new float[]{9, 9, 9, 9, 3, 7, 3, 8, 3, 10, 3, 10, 3});
                    table1.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell cell = new PdfPCell(new Paragraph("UBICACIÓN GEOGRÁFICA", boldFor2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setColspan(4);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Paragraph("", boldFor2));
                    cell.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(cell);

                    //cell = new PdfPCell(new Paragraph("FORMULARIO__DE__", boldFor2));
                    cell = new PdfPCell();
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setColspan(8);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Paragraph("División Político Administrativa", normalFor2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setColspan(2);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Paragraph("División de Unidades Estadísticas", normalFor2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setColspan(2);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell();
                    cell.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Paragraph("PELIGROSIDAD", boldFor2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setColspan(2);
                    table1.addCell(cell);

                    cell = new PdfPCell(new Paragraph("ACCESO", boldFor2));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setColspan(6);
                    table1.addCell(cell);

                    firstTableCell.addElement(tablaFor2(indexLstFor2NoIncremental, indexLstFor2, lstFor2, resumenFiltro, resumenFiltroFor2, boldFor2, normalFor2, cell, table1));
                    mainTable.addCell(firstTableCell);

                    //Tabla derecha
                    PdfPCell secondTableCell = new PdfPCell();
                    secondTableCell.setBorder(PdfPCell.NO_BORDER);

                    PdfPTable table2 = new PdfPTable(1);
                    table2.setTotalWidth(140);
                    table2.setLockedWidth(true);
                    table2.setWidths(new float[]{140});
                    table2.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    PdfPCell cellT2 = new PdfPCell();
                    cellT2 = new PdfPCell(new Paragraph("DOCUMENTO DE IDENTIFICACIÓN", boldFor2));
                    cellT2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellT2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table2.addCell(cellT2);

                    cellT2 = new PdfPCell(new Paragraph("1 = Cédula Ecuatoriana\n"
                            + "2 = Pasaporte o cédula extranjera\n"
                            + "3 = Otro documento\n"
                            + "4 = No tiene\n"
                            + "5 = No sabe/No responde\n" 
                            + "6 = Cédula de otro miembro del hogar ", normalFor2));
                    cellT2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    cellT2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table2.addCell(cellT2);

                    secondTableCell.addElement(table2);
                    mainTable.addCell(secondTableCell);

                    document.add(mainTable);

                } else if (!resumenFiltro.isEmpty()) {
                    /*for (Object[] obj : resumenFiltro) {
                        resumenFiltroFor2.add(obj);
                    }*/
                    PdfPTable table = new PdfPTable(2);
                    table.setTotalWidth(350);
                    table.setLockedWidth(true);

                    PdfPCell cell = null;
                    document.add(tablaFor2(indexLstFor2NoIncremental, indexLstFor2, lstFor2, resumenFiltro, resumenFiltroFor2, boldFor2, normalFor2, cell, table));
                    //document.add(table);
                }

                Paragraph prefacef = new Paragraph();
                addEmptyLine(prefacef, 1);
                document.add(prefacef);

                PdfPTable pdf;
                DataList list = null;
                DataTable table = null;
                if (component instanceof DataList) {
                    list = (DataList) component;
                    pdf = exportPDFTable(context, list, pageOnly, encodingType);
                } else {
                    table = (DataTable) component;
                    styleTable = table;
                    pdf = exportPDFTable(context, table, pageOnly, selectionOnly, encodingType, subTable);//
                }
                pdf.setTotalWidth(1150);//2300-A1**
                pdf.setLockedWidth(true);
                float[] a = new float[pdf.getNumberOfColumns()];
                /*for (int i = 0; i < a.length; i++) {
                    if (!lstFor2.isEmpty()) {
                        if ((i >= 0 && i <= 3) || i == 5 || (i >= 9 && i <= 18) || i == 21 || i == 26 || i == 29) {
                            a[i] = 4;
                        } else if (i == 31) {
                            a[i] = 7;
                        } else {
                            a[i] = 16;
                        }
                    } else {
                        a[i] = 12;
                    }
                }*/
                int i = 0;
                for (UIColumn col : styleTable.getColumns()) {
                    if (!lstFor2Carto.isEmpty()) {
                        if (col.getHeaderText().replaceAll("\\s", "").equals("TIPODEVÍAPRINCIPAL")
                                || col.getHeaderText().replaceAll("\\s", "").equals("TIPODEVÍASECUNDARIA")
                                || col.getHeaderText().replaceAll("\\s", "").equals("No.(PLACA MUNICIPAL)")) {
                            a[i] = 7;
                        } else if (col.getHeaderText().replaceAll("\\s", "").equals("NÚMERO")
                                || col.getHeaderText().replaceAll("\\s", "").equals("CÓDIGOÚNICOELÉCTRICONACIONAL(CUEN)")
                                || col.getHeaderText().replaceAll("\\s", "").equals("PRIMERNOMBRE")
                                || col.getHeaderText().replaceAll("\\s", "").equals("SEGUNDONOMBRE")
                                || col.getHeaderText().replaceAll("\\s", "").equals("PRIMERAPELLIDO")
                                || col.getHeaderText().replaceAll("\\s", "").equals("SEGUNDOAPELLIDO")) {
                            a[i] = 11;
                        } else if (col.getHeaderText().replaceAll("\\s", "").equals("CÓDIGOVIV.COLECTIVA")) {
                            a[i] = 9;
                        } else if (col.getStyleClass().contains("csscartovertical")) {
                            a[i] = 4;
                        } else {
                            a[i] = 15;
                        }
                    } else {
                        a[i] = 12;
                    }
                    i++;
                }
                pdf.setWidths(a);

                if (pdf != null) {
                    document.add(pdf);
                }
                // add a couple of blank lines
                Paragraph preface = new Paragraph();
                addEmptyLine(preface, datasetPadding);
                document.add(preface);
            }
            document.close();

            if (postProcessor != null) {
                postProcessor.invoke(context.getELContext(), new Object[]{document});
            }

            writePDFToResponse(context.getExternalContext(), baos, filename);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable tablaFor2(int indexLstFor2NoIncremental, int indexLstFor2, List<Object[]> lstFor2, List<Object[]> resumenFiltro, List<Object[]> resumenFiltroFor2, Font boldFor2, Font normalFor2, PdfPCell cell, PdfPTable table) {
        int n = 0;
        if (!resumenFiltro.isEmpty()) {
            for (Object[] obj : resumenFiltro) {
                resumenFiltroFor2.add(obj);
            }
            n = resumenFiltro.size();
        }
        if (!lstFor2.isEmpty()) {
            resumenFiltroFor2.remove(0);
            n = resumenFiltro.size() - 1;
            for (Object[] obj : lstFor2) {
                resumenFiltroFor2.add(obj);
            }
            n = 3;
        }

        for (int m = 0; m < n; m++) {
            if (lstFor2.isEmpty()) {
                cell = new PdfPCell(new Paragraph(String.valueOf(resumenFiltroFor2.get(m)[0]), boldFor2));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(String.valueOf(resumenFiltroFor2.get(m)[1]), normalFor2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            } else if (!lstFor2.isEmpty()) {
                if (m < indexLstFor2NoIncremental) {
                    for (int j = 0; j < indexLstFor2NoIncremental; j += 3) {
                        if (m + j < indexLstFor2NoIncremental) {
                            cell = new PdfPCell(new Paragraph(String.valueOf(resumenFiltroFor2.get(m + j)[0]), boldFor2));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(String.valueOf(resumenFiltroFor2.get(m + j)[1]), normalFor2));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);
                        }
                    }
                    cell = new PdfPCell();
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);

                    if (indexLstFor2 < lstFor2.size() - 1) {
                        for (int j = 0; j < lstFor2.size(); j += 3) {
                            if (indexLstFor2 + j < resumenFiltroFor2.size()) {
                                cell = new PdfPCell(new Paragraph(String.valueOf(resumenFiltroFor2.get(indexLstFor2 + j)[0]), boldFor2));
                                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(cell);

                                String validar = "";
                                if (String.valueOf(resumenFiltroFor2.get(indexLstFor2 + j)[1]).equals("false")) {
                                    validar = "";
                                } else {
                                    validar = "X";
                                }
                                cell = new PdfPCell(new Paragraph(validar, normalFor2));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(cell);
                            } else {
                                cell = new PdfPCell();
                                table.addCell(cell);

                                cell = new PdfPCell();
                                table.addCell(cell);
                            }
                        }
                    }
                }
            }
            indexLstFor2++;
        }
        return table;
    }

    protected PdfPTable exportPDFTable(FacesContext context, DataTable table, boolean pageOnly, boolean selectionOnly, String encoding, boolean subTable) {
        if (!("-".equalsIgnoreCase(encoding))) {
            createCustomFonts(encoding);//
        }
        int columnsCount = getColumnsCount(table);
        PdfPTable pdfTable = null;
        if (subTable) {
            int subTableCount = table.getRowCount();
            SubTable subtable = table.getSubTable();
            int subTableColumnsCount = getColumnsCount(subtable);
            pdfTable = new PdfPTable(subTableColumnsCount);

            if (table.getHeader() != null) {
                tableFacet(context, pdfTable, table, subTableColumnsCount, "header");
            }

            tableColumnGroup(pdfTable, table, "header");

            int i = 0;
            while (subTableCount > 0) {

                subTableCount--;
                table.setRowIndex(i);
                i++;
                subtable = table.getSubTable();

                if (subtable.getHeader() != null) {
                    tableFacet(context, pdfTable, subtable, subTableColumnsCount, "header");
                }

                if (hasHeaderColumn(subtable)) {
                    addColumnFacets(subtable, pdfTable, ColumnType.HEADER);
                }

                if (pageOnly) {
                    exportPageOnly(context, table, pdfTable);
                } else if (selectionOnly) {
                    exportSelectionOnly(context, table, pdfTable);
                } else {
                    subTableExportAll(context, subtable, pdfTable);
                }

                if (hasFooterColumn(subtable)) {

                    addColumnFacets(subtable, pdfTable, ColumnType.FOOTER);
                }

                if (subtable.getFooter() != null) {
                    tableFacet(context, pdfTable, subtable, subTableColumnsCount, "footer");
                }

                subtable.setRowIndex(-1);
            }

            tableColumnGroup(pdfTable, table, "footer");

            if (table.hasFooterColumn()) {
                tableFacet(context, pdfTable, table, subTableColumnsCount, "footer");
            }

            return pdfTable;
        } else {

            if (columnsCount == 0) {
                return null;
            }

            pdfTable = new PdfPTable(columnsCount);

            if (table.getHeader() != null) {
                tableFacet(context, pdfTable, table, columnsCount, "header");
            }

            if (!subTable) {
                pdfTable.setHeaderRows(2);
                tableColumnGroup(pdfTable, table, "header");//
            }

            if (hasHeaderColumn(table)) {//
                addColumnFacets(table, pdfTable, ColumnType.HEADER);
            }
            if (pageOnly) {
                exportPageOnly(context, table, pdfTable);
            } else if (selectionOnly) {
                exportSelectionOnly(context, table, pdfTable);
            } else {
                exportAll(context, table, pdfTable);
            }

            if (table.hasFooterColumn()) {
                addColumnFacets(table, pdfTable, ColumnType.FOOTER);
            }
            if (!subTable) {
                tableColumnGroup(pdfTable, table, "footer");
            }
            if (table.getFooter() != null) {

                tableFacet(context, pdfTable, table, columnsCount, "footer");
            }

            table.setRowIndex(-1);

            return pdfTable;
        }

    }

    protected PdfPTable exportPDFTable(FacesContext context, DataList list, boolean pageOnly, String encoding) {

        if (!("-".equalsIgnoreCase(encoding))) {
            createCustomFonts(encoding);
        }
        int first = list.getFirst();
        int rowCount = list.getRowCount();
        int rowsToExport = first + list.getRows();

        PdfPTable pdfTable = new PdfPTable(1);
        if (list.getHeader() != null) {
            String value = exportValue(FacesContext.getCurrentInstance(), list.getHeader());
            PdfPCell cell = new PdfPCell(new Paragraph(value, this.facetFont));
            if (facetBackground != null) {
                cell.setBackgroundColor(facetBackground);
            }
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
            pdfTable.completeRow();

        }

        StringBuilder builder = new StringBuilder();
        String output = null;

        if (pageOnly) {
            output = exportPageOnly(first, list, rowsToExport, builder);
        } else {
            output = exportAll(list, rowCount, builder);
        }

        pdfTable.addCell(new Paragraph(output, cellFont));
        pdfTable.completeRow();

        if (list.getFooter() != null) {
            String value = exportValue(FacesContext.getCurrentInstance(), list.getFooter());
            PdfPCell cell = new PdfPCell(new Paragraph(value, this.facetFont));
            if (facetBackground != null) {
                cell.setBackgroundColor(facetBackground);
            }
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
            pdfTable.completeRow();

        }

        return pdfTable;
    }

    protected void exportPageOnly(FacesContext context, DataTable table, PdfPTable pdfTable) {
        int first = table.getFirst();
        int rowsToExport = first + table.getRows();

        tableColumnGroup(pdfTable, table, "header");

        for (int rowIndex = first; rowIndex < rowsToExport; rowIndex++) {
            exportRow(table, pdfTable, rowIndex);
        }

        tableColumnGroup(pdfTable, table, "footer");
    }

    protected String exportPageOnly(int first, DataList list, int rowsToExport, StringBuilder input) {
        String output = "";
        for (int rowIndex = first; rowIndex < rowsToExport; rowIndex++) {
            output = addColumnValues(list, input);
        }
        return output;

    }

    protected void exportSelectionOnly(FacesContext context, DataTable table, PdfPTable pdfTable) {
        Object selection = table.getSelection();
        String var = table.getVar();

        if (selection != null) {
            Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

            if (selection.getClass().isArray()) {
                int size = Array.getLength(selection);

                for (int i = 0; i < size; i++) {
                    requestMap.put(var, Array.get(selection, i));

                    exportCells(table, pdfTable);
                }
            } else {
                requestMap.put(var, selection);

                exportCells(table, pdfTable);
            }
        }
    }

    protected void exportAll(FacesContext context, DataTable table, PdfPTable pdfTable) {//
        int first = table.getFirst();
        int rowCount = table.getRowCount();
        boolean lazy = table.isLazy();

        if (lazy) {
            if (rowCount > 0) {
                table.setFirst(0);
                table.setRows(rowCount);
                table.clearLazyCache();
                table.loadLazyData();
            }

            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                exportRow(table, pdfTable, rowIndex);
            }

            //restore
            table.setFirst(first);
            table.setRowIndex(-1);
            table.clearLazyCache();
            table.loadLazyData();
        } else {
            //tableColumnGroup(pdfTable, table, "header");//
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                exportRow(table, pdfTable, rowIndex);
            }
            //tableColumnGroup(pdfTable, table, "footer");
            //restore
            table.setFirst(first);
        }

    }

    protected void subTableExportAll(FacesContext context, SubTable table, PdfPTable pdfTable) {
        int first = table.getFirst();
        int rowCount = table.getRowCount();
        int rows = table.getRows();
        boolean lazy = false;

        if (lazy) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (rowIndex % rows == 0) {
                    table.setFirst(rowIndex);
                }
            }

            //restore
            table.setFirst(first);

        } else {
            tableColumnGroup(pdfTable, table, "header");

            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                subTableExportRow(table, pdfTable, rowIndex);
            }

            tableColumnGroup(pdfTable, table, "footer");
            //restore
            table.setFirst(first);
        }
    }

    protected String exportAll(DataList list, int rowCount, StringBuilder input) {
        String output = "";
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            list.setRowIndex(rowIndex);
            output = addColumnValues(list, input);
        }

        return output;
    }

    protected void tableFacet(FacesContext context, PdfPTable pdfTable, DataTable table, int columnCount, String facetType) {
        Map<String, UIComponent> map = table.getFacets();
        UIComponent component = map.get(facetType);
        if (component != null) {
            String headerValue = null;
            if (component instanceof HtmlCommandButton) {
                headerValue = exportValue(context, component);
            } else if (component instanceof HtmlCommandLink) {
                headerValue = exportValue(context, component);
            } else if (component instanceof UIPanel || component instanceof OutputPanel) {
                StringBuilder header = new StringBuilder("");
                for (UIComponent child : component.getChildren()) {
                    headerValue = exportValue(context, child);
                    header.append(headerValue);
                }
                PdfPCell cell = new PdfPCell(new Paragraph(header.toString(), this.facetFont));
                if (facetBackground != null) {
                    cell.setBackgroundColor(facetBackground);
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //addColumnAlignments(component,cell);
                cell.setColspan(columnCount);
                pdfTable.addCell(cell);
                pdfTable.completeRow();
                return;
            } else {
                headerValue = exportFacetValue(context, component);
            }
            PdfPCell cell = new PdfPCell(new Paragraph(headerValue, this.facetFont));
            if (facetBackground != null) {
                cell.setBackgroundColor(facetBackground);
            }
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //addColumnAlignments(component,cell);
            cell.setColspan(columnCount);
            pdfTable.addCell(cell);
            pdfTable.completeRow();

        }
    }

    protected void tableFacet(FacesContext context, PdfPTable pdfTable, SubTable table, int columnCount, String facetType) {
        Map<String, UIComponent> map = table.getFacets();
        UIComponent component = map.get(facetType);
        if (component != null) {
            String headerValue = null;
            if (component instanceof HtmlCommandButton) {
                headerValue = exportValue(context, component);
            } else if (component instanceof HtmlCommandLink) {
                headerValue = exportValue(context, component);
            } else if (component instanceof UIPanel || component instanceof OutputPanel) {
                StringBuilder header = new StringBuilder("");
                for (UIComponent child : component.getChildren()) {
                    headerValue = exportValue(context, child);
                    header.append(headerValue);
                }
                PdfPCell cell = new PdfPCell(new Paragraph(header.toString(), this.facetFont));
                if (facetBackground != null) {
                    cell.setBackgroundColor(facetBackground);
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //addColumnAlignments(component,cell);
                cell.setColspan(columnCount);
                pdfTable.addCell(cell);
                pdfTable.completeRow();
                return;
            } else {
                headerValue = exportFacetValue(context, component);
            }
            PdfPCell cell = new PdfPCell(new Paragraph(headerValue, this.facetFont));
            if (facetBackground != null) {
                cell.setBackgroundColor(facetBackground);
            }
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // addColumnAlignments(component,cell);
            cell.setColspan(columnCount);
            pdfTable.addCell(cell);
            pdfTable.completeRow();

        }
    }

    protected void tableColumnGroup(PdfPTable pdfTable, DataTable table, String facetType) {//combinar celdas
        ColumnGroup cg = table.getColumnGroup(facetType);
        List<UIComponent> headerComponentList = null;
        if (cg != null) {
            headerComponentList = cg.getChildren();
        }
        if (headerComponentList != null) {
            int numHeader = 1;
            for (UIComponent component : headerComponentList) {
                if (component instanceof Row) {
                    Row row = (Row) component;
                    int col = 0;
                    for (UIComponent rowComponent : row.getChildren()) {
                        UIColumn column = (UIColumn) rowComponent;
                        String value = null;
                        if (column.isRendered() && column.isExportable()) {
                            if (facetType.equalsIgnoreCase("header")) {
                                value = column.getHeaderText();
                            } else {
                                value = column.getFooterText();
                            }
                            int rowSpan = column.getRowspan();
                            int colSpan = column.getColspan();
                            PdfPCell cell1 = new PdfPCell(new Paragraph(value, this.facetFont));
                            if (facetBackground != null) {
                                cell1.setBackgroundColor(facetBackground);
                            }
                            if (rowSpan > 1) {
                                cell1.setVerticalAlignment(Element.ALIGN_CENTER);
                                cell1.setRowspan(rowSpan);

                            }
                            if (colSpan > 1) {
                                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell1.setColspan(colSpan);

                            }
                            // addColumnAlignments(component,cell);
                            if (facetType.equalsIgnoreCase("header")) {
                                if (numHeader == headerComponentList.size()) {
                                    PdfPCell cell2 = new PdfPCell(new Paragraph(value, this.facetFont));
                                    if (!lstFor2Carto.isEmpty()) {
                                        cell2.setFixedHeight(100f);
                                        if (column.getStyleClass().contains("csscartovertical")) {
                                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                            cell2.setRotation((short) 90);
                                        } else {
                                            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                        }
                                    } else {
                                        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    }
                                    pdfTable.addCell(cell2);
                                } else {
                                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                }
                            }
                            if (numHeader != headerComponentList.size()) {
                                pdfTable.addCell(cell1);
                            }
                        }
                        col++;
                    }
                }
                numHeader++;
            }
        }
        pdfTable.completeRow();

    }

    protected void tableColumnGroup(PdfPTable pdfTable, SubTable table, String facetType) {
        ColumnGroup cg = table.getColumnGroup(facetType);
        List<UIComponent> headerComponentList = null;
        if (cg != null) {
            headerComponentList = cg.getChildren();
        }
        if (headerComponentList != null) {
            for (UIComponent component : headerComponentList) {
                if (component instanceof Row) {
                    Row row = (Row) component;
                    for (UIComponent rowComponent : row.getChildren()) {
                        UIColumn column = (UIColumn) rowComponent;
                        String value = null;
                        if (facetType.equalsIgnoreCase("header")) {
                            value = column.getHeaderText();
                        } else {
                            value = column.getFooterText();
                        }
                        int rowSpan = column.getRowspan();
                        int colSpan = column.getColspan();
                        PdfPCell cell = new PdfPCell(new Paragraph(value, this.facetFont));
                        if (facetBackground != null) {
                            cell.setBackgroundColor(facetBackground);
                        }
                        if (rowSpan > 1) {
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setRowspan(rowSpan);

                        }
                        if (colSpan > 1) {
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setColspan(colSpan);

                        }
                        // addColumnAlignments(component,cell);
                        if (facetType.equalsIgnoreCase("header")) {
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        }
                        pdfTable.addCell(cell);

                    }
                }

            }
        }
        pdfTable.completeRow();

    }

    protected void exportRow(DataTable table, PdfPTable pdfTable, int rowIndex) {//
        table.setRowIndex(rowIndex);

        if (!table.isRowAvailable()) {
            return;
        }

        exportCells(table, pdfTable);
        SummaryRow sr = table.getSummaryRow();

        if (sr != null && sr.isInView()) {
            for (UIComponent summaryComponent : sr.getChildren()) {
                UIColumn column = (UIColumn) summaryComponent;
                StringBuilder builder = new StringBuilder();

                for (UIComponent component : column.getChildren()) {
                    if (component.isRendered()) {
                        String value = exportValue(FacesContext.getCurrentInstance(), component);

                        if (value != null) {
                            builder.append(value);
                        }
                    }
                }
                int rowSpan = column.getRowspan();
                int colSpan = column.getColspan();
                PdfPCell cell = new PdfPCell(new Paragraph(builder.toString(), this.facetFont));
                if (facetBackground != null) {
                    cell.setBackgroundColor(facetBackground);
                }
                if (rowSpan > 1) {
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(rowSpan);

                }
                if (colSpan > 1) {
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setColspan(colSpan);

                }
                pdfTable.addCell(cell);
            }
        }
    }

    protected void subTableExportRow(SubTable table, PdfPTable pdfTable, int rowIndex) {
        table.setRowIndex(rowIndex);

        if (!table.isRowAvailable()) {
            return;
        }

        subTableExportCells(table, pdfTable);
    }

    protected void exportCells(DataTable table, PdfPTable pdfTable) {//
        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }

            if (col.isRendered() && col.isExportable()) {
                if (col.getSelectionMode() != null) {
                    pdfTable.addCell(new Paragraph(col.getSelectionMode(), this.cellFont));
                    continue;
                }
                addColumnValue(pdfTable, col.getChildren(), this.cellFont, "data");
            }

        }
        pdfTable.completeRow();
        FacesContext context = null;
        if (table.getRowIndex() == 0) {
            for (UIComponent component : table.getChildren()) {
                if (component instanceof RowExpansion) {
                    RowExpansion rowExpansion = (RowExpansion) component;
                    if (rowExpansion.getChildren() != null) {
                        if (rowExpansion.getChildren().get(0) instanceof DataTable) {
                            DataTable childTable = (DataTable) rowExpansion.getChildren().get(0);
                            childTable.setRowIndex(-1);
                        }
                        if (rowExpansion.getChildren().get(0) instanceof DataList) {
                            DataList childList = (DataList) rowExpansion.getChildren().get(0);
                            childList.setRowIndex(-1);
                        }
                    }

                }
            }
        }
        for (UIComponent component : table.getChildren()) {
            if (component instanceof RowExpansion) {
                RowExpansion rowExpansion = (RowExpansion) component;
                if (rowExpansion.getChildren() != null) {
                    if (rowExpansion.getChildren().get(0) instanceof DataTable) {
                        DataTable childTable = (DataTable) rowExpansion.getChildren().get(0);
                        PdfPTable pdfTableChild = exportPDFTable(context, childTable, false, false, "-", false);
                        PdfPCell cell = new PdfPCell();
                        cell.addElement(pdfTableChild);
                        cell.setColspan(pdfTable.getNumberOfColumns());
                        pdfTable.addCell(cell);
                    }
                    if (rowExpansion.getChildren().get(0) instanceof DataList) {
                        DataList list = (DataList) rowExpansion.getChildren().get(0);
                        PdfPTable pdfTableChild = exportPDFTable(context, list, false, "-");
                        pdfTableChild.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                        PdfPCell cell = new PdfPCell();
                        cell.addElement(pdfTableChild);
                        cell.setColspan(pdfTable.getNumberOfColumns());
                    }
                }

            }
            pdfTable.completeRow();
        }

    }

    protected void subTableExportCells(SubTable table, PdfPTable pdfTable) {
        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }

            if (col.isRendered() && col.isExportable()) {
                addColumnValue(pdfTable, col.getChildren(), this.cellFont, "data");
            }
        }
    }

    protected void addColumnFacets(DataTable table, PdfPTable pdfTable, ColumnType columnType) {//
        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            PdfPCell cell = null;
            if (col.isRendered() && col.isExportable() && col.getFacet(columnType.facet()) != null) {
                if (col.getHeaderText() != null && columnType.name().equalsIgnoreCase("header")) {
                    cell = new PdfPCell(new Paragraph(col.getHeaderText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    pdfTable.addCell(cell);
                } else if (col.getFooterText() != null && columnType.name().equalsIgnoreCase("footer")) {
                    cell = new PdfPCell(new Paragraph(col.getFooterText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    pdfTable.addCell(cell);
                } else {
                    addColumnValue(pdfTable, col.getFacet(columnType.facet()), this.facetFont, columnType.name());
                }
            }
        }
    }

    protected void addColumnFacets(SubTable table, PdfPTable pdfTable, ColumnType columnType) {
        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }
            PdfPCell cell = null;
            if (col.isRendered() && col.isExportable()) {
                if (col.getHeaderText() != null && columnType.name().equalsIgnoreCase("header")) {
                    cell = new PdfPCell(new Paragraph(col.getHeaderText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cell);
                } else if (col.getFooterText() != null && columnType.name().equalsIgnoreCase("footer")) {
                    cell = new PdfPCell(new Paragraph(col.getFooterText(), this.facetFont));
                    if (facetBackground != null) {
                        cell.setBackgroundColor(facetBackground);
                    }
                    pdfTable.addCell(cell);
                } else {

                    addColumnValue(pdfTable, col.getFacet(columnType.facet()), this.facetFont, columnType.name());
                }
            }
        }
    }

    protected void addColumnValue(PdfPTable pdfTable, UIComponent component, Font font, String columnType) {
        String value = component == null ? "" : exportValue(FacesContext.getCurrentInstance(), component);
        PdfPCell cell = new PdfPCell(new Paragraph(value, font));

        if (facetBackground != null) {
            cell.setBackgroundColor(facetBackground);
        }
        if (columnType.equalsIgnoreCase("header")) {
            cell = addFacetAlignments(component, cell);
        } else {
            cell = addColumnAlignments(component, cell);
        }
        pdfTable.addCell(cell);
    }

    protected void addColumnValue(PdfPTable pdfTable, List<UIComponent> components, Font font, String columnType) {//valores cuerpo
        StringBuilder builder = new StringBuilder();

        for (UIComponent component : components) {
            if (component.isRendered()) {
                String value = exportValue(FacesContext.getCurrentInstance(), component);

                if (value != null) {
                    builder.append(value);
                }
            }
        }
        PdfPCell cell = new PdfPCell(new Paragraph(builder.toString(), font));
        for (UIComponent component : components) {
            cell = addColumnAlignments(component, cell);
        }
        if (columnType.equalsIgnoreCase("header")) {
            for (UIComponent component : components) {
                cell = addFacetAlignments(component, cell);
            }
        }
        pdfTable.addCell(cell);
    }

    protected PdfPCell addColumnAlignments(UIComponent component, PdfPCell cell) {
        if (component instanceof HtmlOutputText) {
            HtmlOutputText output = (HtmlOutputText) component;
            if (output.getStyle() != null && output.getStyle().contains("left")) {
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            }
            if (output.getStyle() != null && output.getStyle().contains("right")) {
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            }
            if (output.getStyle() != null && output.getStyle().contains("center")) {
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }
        return cell;
    }

    protected PdfPCell addFacetAlignments(UIComponent component, PdfPCell cell) {
        if (component instanceof HtmlOutputText) {
            HtmlOutputText output = (HtmlOutputText) component;
            if (output.getStyle() != null && output.getStyle().contains("left")) {
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            } else if (output.getStyle() != null && output.getStyle().contains("right")) {
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            } else {
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }
        return cell;
    }

    public void customFormat(String facetBackground, String facetFontSize, String facetFontColor, String facetFontStyle, String fontName, String cellFontSize, String cellFontColor, String cellFontStyle, String datasetPadding, String orientation) {

        this.facetFontSize = new Float(facetFontSize);
        this.cellFontSize = new Float(cellFontSize);
        this.datasetPadding = Integer.parseInt(datasetPadding);
        this.orientation = orientation;

        if (facetBackground != null) {
            this.facetBackground = Color.decode(facetBackground);
        }
        if (facetFontColor != null) {
            this.facetFontColor = Color.decode(facetFontColor);
        }
        if (cellFontColor != null) {
            this.cellFontColor = Color.decode(cellFontColor);
        }
        if (fontName != null) {
            this.fontName = fontName;
        }
        if (facetFontStyle.equalsIgnoreCase("NORMAL")) {
            this.facetFontStyle = "" + Font.NORMAL;
        }
        if (facetFontStyle.equalsIgnoreCase("BOLD")) {
            this.facetFontStyle = "" + Font.BOLD;
        }
        if (facetFontStyle.equalsIgnoreCase("ITALIC")) {
            this.facetFontStyle = "" + Font.ITALIC;
        }

        if (cellFontStyle.equalsIgnoreCase("NORMAL")) {
            this.cellFontStyle = "" + Font.NORMAL;
        }
        if (cellFontStyle.equalsIgnoreCase("BOLD")) {
            this.cellFontStyle = "" + Font.BOLD;
        }
        if (cellFontStyle.equalsIgnoreCase("ITALIC")) {
            this.cellFontStyle = "" + Font.ITALIC;
        }

    }

    protected void createCustomFonts(String encoding) {

        if (fontName != null && FontFactory.getFont(fontName).getBaseFont() != null) {
            this.cellFont = FontFactory.getFont(fontName, encoding);
            this.facetFont = FontFactory.getFont(fontName, encoding, Font.DEFAULTSIZE, Font.BOLD);
        } else {
            this.cellFont = FontFactory.getFont(FontFactory.TIMES, encoding);
            this.facetFont = FontFactory.getFont(FontFactory.TIMES, encoding, Font.DEFAULTSIZE, Font.BOLD);
        }
        if (facetFontColor != null) {
            this.facetFont.setColor(facetFontColor);
        }
        if (facetFontSize != null) {
            this.facetFont.setSize(facetFontSize);
        }
        if (facetFontStyle != null) {
            this.facetFont.setStyle(Integer.valueOf(facetFontStyle));
        }
        if (cellFontColor != null) {
            this.cellFont.setColor(cellFontColor);
        }
        if (cellFontSize != null) {
            this.cellFont.setSize(cellFontSize);
        }
        if (cellFontStyle != null) {
            this.cellFont.setStyle(cellFontStyle);
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    protected void writePDFToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) throws IOException, DocumentException {
        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Expires", "0");
        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        externalContext.setResponseContentLength(baos.size());
        externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());
        OutputStream out = externalContext.getResponseOutputStream();
        baos.writeTo(out);
        externalContext.responseFlushBuffer();
    }

    public void imagenINEC(Document document) {
        try {
            String fileName = System.getProperty("jboss.home.dir") + "/welcome-content/imagenes/INEC.png";
            Image imagen = Image.getInstance(fileName);
            imagen.scaleAbsoluteHeight(60f);//80
            imagen.scaleAbsoluteWidth(190);//250
            imagen.setAbsolutePosition(20, 770);//40-1500
            imagen.setAlignment(Element.ALIGN_LEFT);
            document.add(imagen);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void exportCartografia(ActionEvent event, String tableId, FacesContext facesContext, String outputFileName, List<String> subtitulosValue, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, boolean subTable, String titleInec, List<Object[]> resumenFiltro, List<Object[]> lstFor2, ZipOutputStream zos) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
