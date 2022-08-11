/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases.reportes.excel;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.column.Column;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datalist.DataList;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.subtable.SubTable;
import org.primefaces.expression.SearchExpressionFacade;
import org.primefaces.util.Constants;

public class ExcelExporterGeneral extends Exporter {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">
    private static final Logger LOGGER = Logger.getLogger(ExcelExporterGeneral.class.getName());
    private final CellStyle cellStyle;
    private final CellStyle cellStyleFecha;
    private Short cellFontSize;
    private Color cellFontColor;
    private String cellFontStyle;
    Font cellFont;

    private final CellStyle cellStyleLeftAlign;
    private final CellStyle cellStyleCenterAlign;
    private final CellStyle cellStyleRightAlign;

    private final CellStyle facetStyle;
    private final CellStyle facetStyleLeftAlign;
    private final CellStyle facetStyleCenterAlign;
    private final CellStyle facetStyleRightAlign;
    private Color facetBackground;
    private Short facetFontSize;
    private Color facetFontColor;
    private String facetFontStyle;

    Font titleFont;
    Font tituloFont;
    Font subtituloFont;

    private String fontName;
    private String datasetPadding;

    XSSFWorkbook wb;
    private int rowIndex;
    private int colIndex;
    private int cellIndex;
    private int cont = 0;
    //private String estadoExportacion;
    private Integer numeroPag;
    private String tipoFile;

    private final CellStyle csBorderAllBoldLeft;
    private final CellStyle csBorderAllCenter;
    private final CellStyle csCenterBold;
    private final CellStyle csCenter;
    private final CellStyle csBorderAllCenterBold;

    private final CellStyle csTitulo;
    private final CellStyle csSubtitulo;

    private final CellStyle csHeaderHorizontal;
    private final CellStyle csHeaderVertical;

//    private List<Object[]> lstFor2Carto;
    private int param1, param2;
    private String clave;
    private DataTable styleTable;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructor">
    public ExcelExporterGeneral() {

        wb = new XSSFWorkbook();
        //***ESTILO PARA TITULOS***//
        titleFont = wb.createFont();
        //titleFont.setItalic(true);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        tituloFont = wb.createFont();
        tituloFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        tituloFont.setFontHeightInPoints((short) 23);

        subtituloFont = wb.createFont();
        subtituloFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        subtituloFont.setFontHeightInPoints((short) 18);

        cellStyle = wb.createCellStyle();
        cellStyleFecha = wb.createCellStyle();
        cellFont = wb.createFont();
        facetStyle = wb.createCellStyle();//negrita

        facetStyleLeftAlign = wb.createCellStyle();
        facetStyleCenterAlign = wb.createCellStyle();
        facetStyleRightAlign = wb.createCellStyle();
        cellStyleLeftAlign = wb.createCellStyle();
        cellStyleCenterAlign = wb.createCellStyle();
        cellStyleRightAlign = wb.createCellStyle();

        createCustomFonts();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyleFecha.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd hh:mm:ss"));
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleFecha.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleFecha.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleFecha.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleFecha.setBorderLeft(XSSFCellStyle.BORDER_THIN);

        facetStyleLeftAlign.setAlignment((short) CellStyle.ALIGN_LEFT);
        facetStyleCenterAlign.setAlignment((short) CellStyle.ALIGN_CENTER);
        facetStyleCenterAlign.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        facetStyleCenterAlign.setWrapText(true);
        facetStyleRightAlign.setAlignment((short) CellStyle.ALIGN_RIGHT);
        cellStyleLeftAlign.setAlignment((short) CellStyle.ALIGN_LEFT);
        cellStyleCenterAlign.setAlignment((short) CellStyle.ALIGN_CENTER);
        cellStyleRightAlign.setAlignment((short) CellStyle.ALIGN_RIGHT);

        csBorderAllBoldLeft = wb.createCellStyle();
        csBorderAllBoldLeft.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        csBorderAllBoldLeft.setBorderTop(XSSFCellStyle.BORDER_THIN);
        csBorderAllBoldLeft.setBorderRight(XSSFCellStyle.BORDER_THIN);
        csBorderAllBoldLeft.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        csBorderAllBoldLeft.setAlignment((short) CellStyle.ALIGN_LEFT);
        csBorderAllBoldLeft.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csBorderAllBoldLeft.setWrapText(true);
        csBorderAllBoldLeft.setFont(titleFont);

        csBorderAllCenterBold = wb.createCellStyle();
        csBorderAllCenterBold.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenterBold.setBorderTop(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenterBold.setBorderRight(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenterBold.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenterBold.setAlignment((short) CellStyle.ALIGN_CENTER);
        csBorderAllCenterBold.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csBorderAllCenterBold.setWrapText(true);
        csBorderAllCenterBold.setFont(titleFont);

        csBorderAllCenter = wb.createCellStyle();
        csBorderAllCenter.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenter.setBorderTop(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenter.setBorderRight(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenter.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        csBorderAllCenter.setAlignment((short) CellStyle.ALIGN_CENTER);
        csBorderAllCenter.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);

        csCenterBold = wb.createCellStyle();
        csCenterBold.setAlignment((short) CellStyle.ALIGN_CENTER);
        csCenterBold.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csCenterBold.setWrapText(true);
        csCenterBold.setFont(titleFont);

        csCenter = wb.createCellStyle();
        csCenter.setAlignment((short) CellStyle.ALIGN_CENTER);
        csCenter.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csCenter.setWrapText(true);

        csTitulo = wb.createCellStyle();
        csTitulo.setAlignment((short) CellStyle.ALIGN_CENTER);
        csTitulo.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csTitulo.setFont(tituloFont);

        csSubtitulo = wb.createCellStyle();
        csSubtitulo.setAlignment((short) CellStyle.ALIGN_CENTER);
        csSubtitulo.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csSubtitulo.setFont(subtituloFont);

        cellFontStyle = "";
        facetFontStyle = "";

        csHeaderHorizontal = wb.createCellStyle();
        csHeaderVertical = wb.createCellStyle();

        csHeaderHorizontal.setAlignment((short) CellStyle.ALIGN_CENTER);
        csHeaderHorizontal.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csHeaderHorizontal.setWrapText(true);
        csHeaderVertical.setAlignment((short) CellStyle.ALIGN_CENTER);
        csHeaderVertical.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        csHeaderVertical.setWrapText(true);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get and set">
    public Integer getNumeroPag() {
        return numeroPag;
    }

    public void setNumeroPag(Integer numeroPag) {
        this.numeroPag = numeroPag;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="métodos">
    @Override
    public void exportCartografia(ActionEvent event, String tableId, FacesContext context, String filename, List<String> subtitulos, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, boolean subTable, String titulo, List<Object[]> resumenFiltro, List<Object[]> lstFor2, ZipOutputStream zos) throws IOException {
        cont++;
        String safeName = WorkbookUtil.createSafeSheetName(subtitulos.get(0));
        Sheet sheet = wb.createSheet(safeName);
        //System.err.println("crea pagina " + wb.getNumberOfSheets());
        if (preProcessor != null) {
            preProcessor.invoke(context.getELContext(), new Object[]{wb});
        }
//        lstFor2Carto = new ArrayList<>();
//        if (!lstFor2.isEmpty()) {
//            for (Object[] obj : lstFor2) {
//                lstFor2Carto.add(obj);
//            }
//        }
        int maxColumns = 0;
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

            DataList list;
            DataTable table;

            table = (DataTable) component;
            styleTable = table;
            int columnsCount = 0;//getColumnsCount(table);
            for (UIColumn col : table.getColumns()) {
                if (col.isVisible()) {
                    columnsCount++;
                }
            }

            colIndex = 0;

            if (titulo != null && !titulo.isEmpty() && !titulo.contains("" + ",")) {
                Row tituloRow = sheet.createRow(sheet.getLastRowNum());
                rowIndex = sheet.getLastRowNum();
                sheet.addMergedRegion(new CellRangeAddress(
                        rowIndex, //first row (0-based)
                        rowIndex, //last row  (0-based)
                        colIndex, //first column (0-based)
                        colIndex + columnsCount //last column  (0-based)
                ));
                cellIndex = tituloRow.getLastCellNum() == -1 ? 0 : tituloRow.getLastCellNum();
                Cell cell = tituloRow.createCell(cellIndex);
                cell.setCellValue(new XSSFRichTextString(titulo));
                cell.setCellStyle(csTitulo);
            }

            for (String subtitulo : subtitulos) {
                if (subtitulo != null && !subtitulo.isEmpty() && !subtitulo.contains("" + ",")) {
                    Row subtituloRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    rowIndex = sheet.getLastRowNum();
                    sheet.addMergedRegion(new CellRangeAddress(
                            rowIndex, //first row (0-based)
                            rowIndex, //last row  (0-based)
                            colIndex, //first column (0-based)
                            colIndex + columnsCount //last column  (0-based)
                    ));
                    cellIndex = subtituloRow.getLastCellNum() == -1 ? 0 : subtituloRow.getLastCellNum();
                    Cell cell = subtituloRow.createCell(cellIndex);
                    cell.setCellValue(new XSSFRichTextString(subtitulo));
                    cell.setCellStyle(csSubtitulo);
                }
            }

            sheet.createRow(sheet.getLastRowNum() + 2);

            List<Object[]> resumenFiltroFor2 = new ArrayList<>();

            if (!lstFor2.isEmpty()) {
                imagenINEC(wb, sheet);
                Row rowCab1 = sheet.createRow(sheet.getLastRowNum() + 1);
                Cell cellCab1 = null;

                cellCab1 = rowCab1.createCell(0);
                cellCab1.setCellValue(new XSSFRichTextString("GRUPO No. "));
                cellCab1.setCellStyle(csBorderAllBoldLeft);

                cellCab1 = rowCab1.createCell(1);
                String nomGrupo = "";                
                for (int j = 0; j < lstFor2.size(); j++) {
                    if (String.valueOf(lstFor2.get(j)[6]).equals(clave)) {
                        nomGrupo = String.valueOf(lstFor2.get(j)[7]);
                        break;
                    }                    
                }
                cellCab1.setCellValue(new XSSFRichTextString(nomGrupo));
                cellCab1.setCellStyle(csBorderAllCenter);

                cellCab1 = rowCab1.createCell(5);
                cellCab1.setCellValue(new XSSFRichTextString("PERIODO DE ACTUALIZACIÓN"));
                cellCab1.setCellStyle(csBorderAllCenterBold);

                rowIndex = sheet.getLastRowNum();
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 6, 7));
                cellCab1 = rowCab1.createCell(6);
                cellCab1.setCellValue(new XSSFRichTextString("del " + String.valueOf(lstFor2.get(0)[4]) + " al " + String.valueOf(lstFor2.get(0)[5])));
                cellCab1.setCellStyle(csBorderAllCenterBold);
                Cell cellCab7 = rowCab1.createCell(7);
                cellCab7.setCellStyle(csBorderAllCenterBold);

                Row rowCab2 = sheet.createRow(sheet.getLastRowNum() + 1);
                Cell cellCab2 = null;

                cellCab2 = rowCab2.createCell(5);
                cellCab2.setCellValue(new XSSFRichTextString("ROL"));
                cellCab2.setCellStyle(csBorderAllCenterBold);

                cellCab2 = rowCab2.createCell(6);
                cellCab2.setCellValue(new XSSFRichTextString("NOMBRE"));
                cellCab2.setCellStyle(csBorderAllCenterBold);

                cellCab2 = rowCab2.createCell(7);
                cellCab2.setCellValue(new XSSFRichTextString("No. CELULAR"));
                cellCab2.setCellStyle(csBorderAllCenterBold);
            }

            if (!resumenFiltro.isEmpty()) {
                for (Object[] obj : resumenFiltro) {
                    resumenFiltroFor2.add(obj);
                }
                if (!lstFor2.isEmpty()) {
                    int i = 0;
                    for (Object[] obj : lstFor2) {
                        if (String.valueOf(lstFor2.get(i)[6]).equals(clave)) {
                            resumenFiltroFor2.add(obj);
                        }
                        i++;
                    }
                }

                int indexLstFor1 = resumenFiltro.size();

                for (int m = 0; m < resumenFiltroFor2.size(); m++) {
                    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                    cellIndex = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                    Cell cell = null;
                    boolean encab1 = false, encab2 = false;

                    if (m < resumenFiltro.size()) {
                        cell = row.createCell(cellIndex);
                        String value = String.valueOf(resumenFiltroFor2.get(m)[0]);
                        cell.setCellValue(new XSSFRichTextString(value));
                        cell.setCellStyle(csBorderAllBoldLeft);

                        cell = row.createCell(cellIndex + 1);
                        cell.setCellValue(new XSSFRichTextString(String.valueOf(resumenFiltroFor2.get(m)[1])));
                        cell.setCellStyle(csBorderAllCenter);
                    } else {
                        encab1 = true;
                    }

                    if (indexLstFor1 < resumenFiltroFor2.size()) {
                        //if (String.valueOf(resumenFiltroFor2.get(indexLstFor1)[6]).equals(clave)) {
                        cell = row.createCell(cellIndex + 5);
                        String value = String.valueOf(resumenFiltroFor2.get(indexLstFor1)[0]);
                        cell.setCellValue(new XSSFRichTextString(value));
                        cell.setCellStyle(csBorderAllBoldLeft);

                        cell = row.createCell(cellIndex + 6);
                        String validar = "";
                        if (String.valueOf(resumenFiltroFor2.get(indexLstFor1)[1]).equals("false")) {
                            validar = "";
                        } else if (String.valueOf(resumenFiltroFor2.get(indexLstFor1)[1]).equals("true")) {
                            validar = "X";
                        } else {
                            validar = String.valueOf(resumenFiltroFor2.get(indexLstFor1)[1]);
                        }
                        cell.setCellValue(new XSSFRichTextString(validar));
                        cell.setCellStyle(csBorderAllCenter);

                        cell = row.createCell(cellIndex + 7);
                        String cedula = String.valueOf(resumenFiltroFor2.get(indexLstFor1)[2]);
                        if ("null".equals(cedula)) {
                            cedula = "";
                        }
                        cell.setCellValue(new XSSFRichTextString(cedula));
                        cell.setCellStyle(csBorderAllCenter);
                        //}
                    } else {
                        encab2 = true;
                    }

                    indexLstFor1++;
                    if (encab1 && encab2) {
                        break;
                    }
                }
            }
            sheet.createRow(sheet.getLastRowNum() + 1);

            if (component instanceof DataList) {
                list = (DataList) component;

                if (list.getHeader() != null) {
                    tableFacet(context, sheet, list, "header");
                }
                if (pageOnly) {
                    exportPageOnly(context, list, sheet);
                } else {
                    exportAll(context, list, sheet);
                }
            } else {

                if (table.getHeader() != null && !subTable) {
                    tableFacet(context, sheet, table, columnsCount, "header");

                }
                if (!subTable) {
                    tableColumnGroup(sheet, table, "header");//ingresa header
                }

                addColumnFacets(table, sheet, Exporter.ColumnType.HEADER);

                if (pageOnly) {
                    exportPageOnly(context, table, sheet);
                } else if (selectionOnly) {
                    exportSelectionOnly(context, table, sheet);
                } else {
                    exportAll(context, table, sheet, subTable);//
                }

                if (table.hasFooterColumn() && !subTable) {
                    addColumnFacets(table, sheet, Exporter.ColumnType.FOOTER);
                }
                if (!subTable) {
                    tableColumnGroup(sheet, table, "footer");
                }
                table.setRowIndex(-1);
                //int cols = table.getColumnsCount();
                if (maxColumns < columnsCount) {
                    maxColumns = columnsCount;
                }
            }
            sheet.createRow(sheet.getLastRowNum() + 1);
            //System.err.println("ultima fila " + sheet.getLastRowNum());
        }

        if (postProcessor != null) {
            postProcessor.invoke(context.getELContext(), new Object[]{wb});
        }

        if (!subTable) {
            int i = 0;
            for (UIColumn col : styleTable.getColumns()) {
                if (!lstFor2.isEmpty()) {
                    if (col.getStyleClass().contains("csscartovertical")) {
                        sheet.setColumnWidth(i, 1346);
                    } else if (i == 31) {
                        sheet.setColumnWidth(i, 1884);
                    } else {
                        sheet.setColumnWidth(i, 4000);
                    }
                } else {
                    sheet.setColumnWidth(i, 3500);
                }
                i++;
            }
            /*for (int i = 0; i < maxColumns; i++) {
                if (!lstFor2Carto.isEmpty()) {
                    if ((i >= 0 && i <= 3) || i == 5 || (i >= 9 && i <= 18) || i == 21 || i == 26 || i == 29) {
                        sheet.setColumnWidth(i, 1346);
                    } else if (i == 31) {
                        sheet.setColumnWidth(i, 1884);
                    } else {
                        sheet.setColumnWidth(i, 4000);
                    }
                } else {
                    sheet.setColumnWidth(i, 3500);
                }
            }*/
        }

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(PrintSetup.A3_PAPERSIZE);

        sheet.setPrintGridlines(true);
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        wb.write(outByteStream);
        addEntry(zos, filename, outByteStream);
        wb.close();
        outByteStream.close();
        cont = 0;
        //System.err.println("Libro Exportado... ");
    }

    public void filasAExportar(int paramFila1, int paramFila2, String clave) {
        param1 = paramFila1;
        param2 = paramFila2;
        this.clave = clave;
    }

    public void addEntry(ZipOutputStream zip, String filename, ByteArrayOutputStream os) {
        try {
            byte[] bytes = os.toByteArray();
            ((ZipOutputStream) zip).putNextEntry(new ZipEntry(filename + ".xlsx"));
            ((ZipOutputStream) zip).write(bytes);
            zip.flush();
            ((ZipOutputStream) zip).closeEntry();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void imagenINEC(XSSFWorkbook wb, Sheet sheet) {
        try {
            String fileName = System.getProperty("jboss.home.dir") + "/welcome-content/imagenes/INEC.png";
            //System.err.println("11 " + fileName);
            InputStream inputStream = new FileInputStream(fileName);
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            int pictureureIdx = wb.addPicture(imageBytes, XSSFWorkbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(0);
            anchor.setRow1(0);
            Picture pict = drawing.createPicture(anchor, pictureureIdx);
            pict.resize(5, 3.5);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteColumn(Sheet sheet, int columnToDelete) {
        int maxColumn = 0;
        for (int r = 0; r < sheet.getLastRowNum() + 1; r++) {
            Row row = sheet.getRow(r);

            // if no row exists here; then nothing to do; next!
            if (row == null) {
                continue;
            }

            // if the row doesn't have this many columns then we are good; next!
            int lastColumn = row.getLastCellNum();
            if (lastColumn > maxColumn) {
                maxColumn = lastColumn;
            }

            if (lastColumn < columnToDelete) {
                continue;
            }

            for (int x = columnToDelete + 1; x < lastColumn + 1; x++) {
                Cell oldCell = row.getCell(x - 1);
                if (oldCell != null) {
                    row.removeCell(oldCell);
                }
            }
        }

        // Adjust the column widths
        for (int c = 0; c < maxColumn; c++) {
            sheet.setColumnWidth(c, sheet.getColumnWidth(c + 1));
        }
    }

    protected void exportAll(FacesContext context, DataTable table, Sheet sheet, Boolean subTable) {

        int first = table.getFirst();
        int rowCount = table.getRowCount();
        boolean lazy = table.isLazy();
        int i = 0;
        if (subTable) {
            int subTableCount = table.getRowCount();
            SubTable subtable = table.getSubTable();
            int subTableColumnsCount = getColumnsCount(subtable);

            if (table.getHeader() != null) {
                tableFacet(context, sheet, table, subTableColumnsCount, "header");
            }

            tableColumnGroup(sheet, table, "header");

            while (subTableCount > 0) {

                subTableCount--;
                table.setRowIndex(i);
                i++;
                if (subtable.getHeader() != null) {
                    tableFacet(context, sheet, subtable, subTableColumnsCount, "header");
                }

                if (hasHeaderColumn(subtable)) {
                    addColumnFacets(subtable, sheet, Exporter.ColumnType.HEADER);
                }

                exportAll(context, subtable, sheet);

                if (hasFooterColumn(subtable)) {

                    addColumnFacets(subtable, sheet, Exporter.ColumnType.FOOTER);
                }

                if (subtable.getFooter() != null) {
                    tableFacet(context, sheet, subtable, subTableColumnsCount, "footer");
                }

                subtable.setRowIndex(-1);
                subtable = table.getSubTable();
            }

            tableColumnGroup(sheet, table, "footer");

            if (table.hasFooterColumn()) {
                tableFacet(context, sheet, table, subTableColumnsCount, "footer");
            }
        } else if (lazy) {
            if (rowCount > 0) {
                table.setFirst(0);
                table.setRows(rowCount);
                table.clearLazyCache();
                table.loadLazyData();
            }
            for (int rowIndexTable = 0; rowIndexTable < rowCount; rowIndexTable++) {
                exportRow(table, sheet, rowIndexTable);
            }

            //restore
            table.setFirst(first);
            table.setRowIndex(-1);
            table.clearLazyCache();
            table.loadLazyData();
        } else {
            for (int rowIndexTable = 0; rowIndexTable < rowCount; rowIndexTable++) {//controlar numero de filas exportadas
                if (param1 <= rowIndexTable && rowIndexTable < param2) {
                    exportRow(table, sheet, rowIndexTable);
                }
            }
            //restore
            table.setFirst(first);
        }
    }

    protected void exportAll(FacesContext context, SubTable table, Sheet sheet) {
        int rowCount = table.getRowCount();

        tableColumnGroup(sheet, table, "header");
        if (hasHeaderColumn(table)) {
            addColumnFacets(table, sheet, Exporter.ColumnType.HEADER);
        }
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            exportRow(table, sheet, rowIndex);
        }
        if (hasFooterColumn(table)) {
            addColumnFacets(table, sheet, Exporter.ColumnType.FOOTER);
        }
        tableColumnGroup(sheet, table, "footer");

    }

    protected void exportAll(FacesContext context, DataList list, Sheet sheet) {
        int first = list.getFirst();
        int rowCount = list.getRowCount();
        int rows = list.getRows();
        boolean lazy = false;

        if (lazy) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (rowIndex % rows == 0) {
                    list.setFirst(rowIndex);
                    // table.loadLazyData();
                }

                exportRow(list, sheet, rowIndex);
            }

            //restore
            list.setFirst(first);
            // table.loadLazyData();
        } else {

            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                exportRow(list, sheet, rowIndex);
            }
            //restore
            list.setFirst(first);
        }

    }

    protected void exportPageOnly(FacesContext context, DataTable table, Sheet sheet) {//
        int first = table.getFirst();
        int rowsToExport = first + table.getRows();

        for (int rowIndex = first; rowIndex < rowsToExport; rowIndex++) {
            exportRow(table, sheet, rowIndex);
        }
    }

    protected void exportPageOnly(FacesContext context, DataList list, Sheet sheet) {
        int first = list.getFirst();
        int rowsToExport = first + list.getRows();

        for (int rowIndex = first; rowIndex < rowsToExport; rowIndex++) {
            exportRow(list, sheet, rowIndex);
        }
    }

    protected void exportSelectionOnly(FacesContext context, DataTable table, Sheet sheet) {
        Object selection = table.getSelection();
        String var = table.getVar();

        if (selection != null) {
            Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

            if (selection.getClass().isArray()) {
                int size = Array.getLength(selection);

                for (int i = 0; i < size; i++) {
                    requestMap.put(var, Array.get(selection, i));
                    exportCells(table, sheet);
                }
            } else if (List.class.isAssignableFrom(selection.getClass())) {
                List<?> list = (List<?>) selection;
                for (Iterator<? extends Object> it = list.iterator(); it.hasNext();) {
                    requestMap.put(var, it.next());
                    exportCells(table, sheet);
                }
            } else {
                requestMap.put(var, selection);
                exportCells(table, sheet);
            }
        }
    }

    protected void tableFacet(FacesContext context, Sheet sheet, DataTable table, int columnCount, String facetType) {
        Map<String, UIComponent> map = table.getFacets();
        UIComponent component = map.get(facetType);
        if (component != null) {
            String headerValue;
            if (component instanceof HtmlCommandButton) {
                headerValue = exportValue(context, component);
            } else if (component instanceof HtmlCommandLink) {
                headerValue = exportValue(context, component);
            } else if (component instanceof UIPanel) {
                StringBuilder header = new StringBuilder("");
                for (UIComponent child : component.getChildren()) {
                    headerValue = exportValue(context, child);
                    header.append(headerValue);
                }
                headerValue = header.toString();
            } else {
                headerValue = exportFacetValue(context, component);
            }

            int sheetRowIndex = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(sheetRowIndex);
            Cell cell = row.createCell((short) 0);
            cell.setCellValue(headerValue);
            cell.setCellStyle(facetStyle);

            sheet.addMergedRegion(new CellRangeAddress(
                    sheetRowIndex, //first row (0-based)
                    sheetRowIndex, //last row  (0-based)
                    0, //first column (0-based)
                    columnCount - 1 //last column  (0-based)
            ));

        }
    }

    protected void tableFacet(FacesContext context, Sheet sheet, SubTable table, int columnCount, String facetType) {
        Map<String, UIComponent> map = table.getFacets();
        UIComponent component = map.get(facetType);
        if (component != null) {
            String headerValue;
            if (component instanceof HtmlCommandButton) {
                headerValue = exportValue(context, component);
            } else if (component instanceof HtmlCommandLink) {
                headerValue = exportValue(context, component);
            } else if (component instanceof UIPanel) {
                StringBuilder header = new StringBuilder("");
                for (UIComponent child : component.getChildren()) {
                    headerValue = exportValue(context, child);
                    header.append(headerValue);
                }
                headerValue = header.toString();
            } else {
                headerValue = exportFacetValue(context, component);
            }

            int sheetRowIndex = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(sheetRowIndex);
            Cell cell = row.createCell((short) 0);
            cell.setCellValue(headerValue);
            cell.setCellStyle(facetStyle);

            sheet.addMergedRegion(new CellRangeAddress(
                    sheetRowIndex, //first row (0-based)
                    sheetRowIndex, //last row  (0-based)
                    0, //first column (0-based)
                    columnCount - 1 //last column  (0-based)
            ));

        }
    }

    protected void tableFacet(FacesContext context, Sheet sheet, DataList list, String facetType) {
        Map<String, UIComponent> map = list.getFacets();
        UIComponent component = map.get(facetType);
        if (component != null) {
            String headerValue;
            if (component instanceof HtmlCommandButton) {
                headerValue = exportValue(context, component);
            } else if (component instanceof HtmlCommandLink) {
                headerValue = exportValue(context, component);
            } else {
                headerValue = exportFacetValue(context, component);
            }

            int sheetRowIndex = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(sheetRowIndex);
            Cell cell = row.createCell((short) 0);
            cell.setCellValue(headerValue);
            cell.setCellStyle(facetStyle);

            sheet.addMergedRegion(new CellRangeAddress(
                    sheetRowIndex, //first row (0-based)
                    sheetRowIndex, //last row  (0-based)
                    0, //first column (0-based)
                    1 //last column  (0-based)
            ));

        }
    }

    private int calculateColumnOffset(Sheet sheet, int row, int col) {
        for (int j = 0; j < sheet.getNumMergedRegions(); j++) {
            CellRangeAddress merged = sheet.getMergedRegion(j);
            if (merged.isInRange(row, col)) {
                col = merged.getLastColumn() + 1;
            }
        }
        return col;
    }

    private void putText(Row xlRow, short col, String text, int rowIndex, int numHeader, String styleClass) {
        Font titleFont2 = wb.createFont();
        titleFont2.setBoldweight(Font.BOLDWEIGHT_BOLD);

        csHeaderHorizontal.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        csHeaderHorizontal.setBorderTop(XSSFCellStyle.BORDER_THIN);
        csHeaderHorizontal.setBorderRight(XSSFCellStyle.BORDER_THIN);
        csHeaderHorizontal.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        csHeaderHorizontal.setFont(titleFont2);

        csHeaderVertical.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        csHeaderVertical.setBorderTop(XSSFCellStyle.BORDER_THIN);
        csHeaderVertical.setBorderRight(XSSFCellStyle.BORDER_THIN);
        csHeaderVertical.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        csHeaderVertical.setFont(titleFont2);
        csHeaderVertical.setRotation((short) 90);

        Cell cell;
        cell = xlRow.createCell(col);

        for (int j = 0; j < xlRow.getSheet().getNumMergedRegions(); j++) {
            CellRangeAddress merged = xlRow.getSheet().getMergedRegion(j);
            if (merged.isInRange(rowIndex, col)) {
                for (int i = merged.getFirstRow(); i <= merged.getLastRow(); i++) {
                    for (int k = merged.getFirstColumn(); k < merged.getLastColumn(); k++) {
                        cell = xlRow.createCell((short) ++col);
                        cell.setCellStyle(csHeaderHorizontal);//estilo aplica solo para las filas combinadas                        
                    }
                }
            }
            cell.setCellValue(text);
            if (numHeader == 2) {
                //if (!lstFor2Carto.isEmpty()) {
                //if ((col >= 0 && col <= 3) || col == 5 || (col >= 9 && col <= 18) || col == 21 || col == 26 || col == 29 || col == 31) {
                if (styleClass.contains("csscartovertical")) {
                    cell.setCellStyle(csHeaderVertical);
                } else {
                    cell.setCellStyle(csHeaderHorizontal);
                }
                /*} else {
                    cell.setCellStyle(csHeaderHorizontal);
                }*/
            } else {
                cell.setCellStyle(csHeaderHorizontal);//estilo aplica para toda la tabla
            }
        }
    }

    protected void tableColumnGroup(Sheet sheet, DataTable table, String facetType) {
        facetStyleCenterAlign.setAlignment((short) CellStyle.ALIGN_CENTER);
        facetStyleCenterAlign.setVerticalAlignment((short) CellStyle.VERTICAL_CENTER);
        facetStyleCenterAlign.setWrapText(true);

        ColumnGroup cg = table.getColumnGroup(facetType);
        List<UIComponent> headerComponentList = null;
        if (cg != null) {
            headerComponentList = cg.getChildren();
        }
        if (headerComponentList != null) {
            int numHeader = 1;
            for (UIComponent component : headerComponentList) {
                if (component instanceof org.primefaces.component.row.Row) {
                    org.primefaces.component.row.Row row = (org.primefaces.component.row.Row) component;
                    int rowIndex = sheet.getLastRowNum() + 1;
                    //System.err.println("rowIndexqq " + rowIndex);
                    Row xlRow = sheet.createRow(rowIndex);
                    int colIndex = 0;
                    for (UIComponent rowComponent : row.getChildren()) {
                        UIColumn column = (UIColumn) rowComponent;
                        if (!column.isRendered() || !column.isExportable()) {
                            continue;
                        }
                        if (column.isRendered() && column.isExportable() && column.isVisible()) {
                            String text = facetType.equalsIgnoreCase("header") ? column.getHeaderText() : column.getFooterText();
                            //System.err.println("col " + text);
                            // by default column has 1 rowspan && colspan
                            int rowSpan = column.getRowspan() - 1;
                            int colSpan = column.getColspan() - 1;
                            if (rowSpan > 0 && colSpan > 0) {
                                colIndex = calculateColumnOffset(sheet, rowIndex, colIndex);
                                sheet.addMergedRegion(new CellRangeAddress(
                                        rowIndex, //first row (0-based)
                                        rowIndex + rowSpan, //last row  (0-based)
                                        colIndex, //first column (0-based)
                                        colIndex + colSpan //last column  (0-based)
                                ));
                                putText(xlRow, (short) colIndex, text, rowIndex, numHeader, column.getStyleClass());
                                colIndex = colIndex + colSpan;
                                //System.err.println("ingresa 1 ");
                            } else if (rowSpan > 0) {
                                sheet.addMergedRegion(new CellRangeAddress(
                                        rowIndex, //first row (0-based)
                                        rowIndex + rowSpan, //last row  (0-based)
                                        colIndex, //first column (0-based)
                                        colIndex //last column  (0-based)
                                ));
                                putText(xlRow, (short) colIndex, text, rowIndex, numHeader, column.getStyleClass());
                                //System.err.println("ingresa 2 ");
                            } else if (colSpan > 0) {
                                colIndex = calculateColumnOffset(sheet, rowIndex, colIndex);
                                sheet.addMergedRegion(new CellRangeAddress(
                                        rowIndex, //first row (0-based)
                                        rowIndex, //last row  (0-based)
                                        colIndex, //first column (0-based)
                                        colIndex + colSpan //last column  (0-based)
                                ));
                                putText(xlRow, (short) colIndex, text, rowIndex, numHeader, column.getStyleClass());
                                colIndex = colIndex + colSpan;
                                //System.err.println("ingresa 3 ");
                            } else {
                                colIndex = calculateColumnOffset(sheet, rowIndex, colIndex);
                                putText(xlRow, (short) colIndex, text, rowIndex, numHeader, column.getStyleClass());//2da fila
                                //System.err.println("ingresa 4 ");
                            }
                            colIndex++;
                        }
                    }
                }
                numHeader++;
            }
        }
    }

    protected void tableColumnGroup(Sheet sheet, SubTable table, String facetType) {
        ColumnGroup cg = table.getColumnGroup(facetType);
        List<UIComponent> headerComponentList = null;
        if (cg != null) {
            headerComponentList = cg.getChildren();
        }
        if (headerComponentList != null) {
            for (UIComponent component : headerComponentList) {
                if (component instanceof org.primefaces.component.row.Row) {
                    org.primefaces.component.row.Row row = (org.primefaces.component.row.Row) component;
                    int sheetRowIndex = sheet.getPhysicalNumberOfRows() > 0 ? sheet.getLastRowNum() + 1 : 0;
                    Row xlRow = sheet.createRow(sheetRowIndex);
                    int i = 0;
                    for (UIComponent rowComponent : row.getChildren()) {
                        UIColumn column = (UIColumn) rowComponent;
                        String value;
                        if (facetType.equalsIgnoreCase("header")) {
                            value = column.getHeaderText();
                        } else {
                            value = column.getFooterText();
                        }
                        int rowSpan = column.getRowspan();
                        int colSpan = column.getColspan();

                        Cell cell;

                        if (rowSpan > 1 || colSpan > 1) {

                            if (rowSpan > 1) {
                                cell = xlRow.createCell((short) i);
                                Boolean rowSpanFlag = false;
                                for (int j = 0; j < sheet.getNumMergedRegions(); j++) {
                                    CellRangeAddress merged = sheet.getMergedRegion(j);
                                    if (merged.isInRange(sheetRowIndex, i)) {
                                        rowSpanFlag = true;
                                    }

                                }
                                if (!rowSpanFlag) {
                                    cell.setCellStyle(cellStyle);
                                    cell.setCellValue(value);
                                    sheet.addMergedRegion(new CellRangeAddress(
                                            sheetRowIndex, //first row (0-based)
                                            sheetRowIndex + rowSpan - 1, //last row  (0-based)
                                            i, //first column (0-based)
                                            i //last column  (0-based)
                                    ));
                                }
                            }
                            if (colSpan > 1) {
                                cell = xlRow.createCell((short) i);
                                for (int j = 0; j < sheet.getNumMergedRegions(); j++) {
                                    CellRangeAddress merged = sheet.getMergedRegion(j);
                                    if (merged.isInRange(sheetRowIndex, i)) {
                                        cell = xlRow.createCell((short) ++i);
                                    }
                                }
                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(value);
                                sheet.addMergedRegion(new CellRangeAddress(
                                        sheetRowIndex, //first row (0-based)
                                        sheetRowIndex, //last row  (0-based)
                                        i, //first column (0-based)
                                        i + colSpan - 1 //last column  (0-based)
                                ));
                                i = i + colSpan - 1;
                            }
                        } else {
                            cell = xlRow.createCell((short) i);
                            for (int j = 0; j < sheet.getNumMergedRegions(); j++) {
                                CellRangeAddress merged = sheet.getMergedRegion(j);
                                if (merged.isInRange(sheetRowIndex, i)) {
                                    cell = xlRow.createCell((short) ++i);
                                }
                            }
                            cell.setCellValue(value);
                            cell.setCellStyle(facetStyle);

                        }
                        i++;
                    }
                }

            }
        }

    }

    protected void exportRow(DataTable table, Sheet sheet, int rowIndexTable) {//
        table.setRowIndex(rowIndexTable);
        if (!table.isRowAvailable()) {
            return;
        }

        exportCells(table, sheet);
    }

    protected void exportRow(SubTable table, Sheet sheet, int rowIndex) {
        table.setRowIndex(rowIndex);

        if (!table.isRowAvailable()) {
            return;
        }

        exportCells(table, sheet);
    }

    protected void exportRow(DataList list, Sheet sheet, int rowIndex) {
        list.setRowIndex(rowIndex);

        if (!list.isRowAvailable()) {
            return;
        }

        exportCells(list, sheet);
    }

    protected void exportCells(DataTable table, Sheet sheet) {//util
        rowIndex = sheet.getLastRowNum() + 1;
        //System.err.println("sheetRowIndex5 " + sheetRowIndex);
        Row row = sheet.createRow(rowIndex);

        for (UIColumn col : table.getColumns()) {
            String clase;
            clase = col.getStyleClass();
            if (clase != null) {
                if (clase.contains("TEXTO")) {
                    clase = "TEXTO";
                }
                if (clase.contains("ENTERO")) {
                    clase = "ENTERO";
                }
                if (clase.contains("NUMERICO")) {
                    clase = "NUMERICO";
                }
                if (clase.contains("FECHA")) {
                    clase = "FECHA";
                }
                if (col instanceof DynamicColumn) {
                    ((DynamicColumn) col).applyStatelessModel();
                }

                if (col.isRendered() && col.isExportable() && col.isVisible()) {
                    addColumnValue(row, col.getChildren(), "content", clase);
                }
            } else if (col.isRendered() && col.isExportable() && col.isVisible()) {
                addColumnValue(row, col.getChildren(), "content");
            }
        }
    }

    protected void exportCells(SubTable table, Sheet sheet) {
        int sheetRowIndex = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(sheetRowIndex);

        for (UIColumn col : table.getColumns()) {
            String clase;
            clase = col.getStyleClass();
            if (clase.contains("TEXTO")) {
                clase = "TEXTO";
            }
            if (clase.contains("ENTERO")) {
                clase = "ENTERO";
            }
            if (clase.contains("NUMERICO")) {
                clase = "NUMERICO";
            }
            if (clase.contains("FECHA")) {
                clase = "FECHA";
            }
            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }

            if (col.isRendered() && col.isExportable()) {
                addColumnValue(row, col.getChildren(), "content", clase);
            }
        }
    }

    protected void exportCells(DataList list, Sheet sheet) {
        int sheetRowIndex = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(sheetRowIndex);

        for (UIComponent component : list.getChildren()) {
            if (component instanceof Column) {
                UIColumn column = (UIColumn) component;
                for (UIComponent childComponent : column.getChildren()) {
                    int cellIndex = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                    Cell cell = row.createCell(cellIndex);
                    if (component.isRendered()) {
                        String value = exportValue(FacesContext.getCurrentInstance(), childComponent);
                        cell.setCellValue(new XSSFRichTextString(value));
                        cell.setCellStyle(cellStyle);
                    }
                }

            } else {
                int cellIndex = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
                Cell cell = row.createCell(cellIndex);
                if (component.isRendered()) {
                    String value = exportValue(FacesContext.getCurrentInstance(), component);
                    cell.setCellValue(new XSSFRichTextString(value));
                    cell.setCellStyle(cellStyle);
                }
            }
        }

    }

    protected void addColumnFacets(DataTable table, Sheet sheet, Exporter.ColumnType columnType) {
        int sheetRowIndex = sheet.getLastRowNum() + 1;
        //System.err.println("sheetRowIndex8 " + sheetRowIndex);
        Row rowHeader = null;

        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }

            if (col.isRendered() && col.isExportable() && col.getFacet(columnType.facet()) != null) {
                if (rowHeader == null) {
                    rowHeader = sheet.createRow(sheetRowIndex);
                }
                addColumnValue(rowHeader, col.getFacet(columnType.facet()), "facet");
            }
        }

    }

    protected void addColumnFacets(SubTable table, Sheet sheet, Exporter.ColumnType columnType) {
        int sheetRowIndex = sheet.getPhysicalNumberOfRows() > 0 ? sheet.getLastRowNum() + 1 : 0;
        Row rowHeader = sheet.createRow(sheetRowIndex);

        for (UIColumn col : table.getColumns()) {

            if (col instanceof DynamicColumn) {
                ((DynamicColumn) col).applyStatelessModel();
            }

            if (col.isRendered() && col.isExportable()) {
                addColumnValue(rowHeader, col.getFacet(columnType.facet()), "facet");
            }
        }
    }

    protected void addColumnValue(Row row, UIComponent component, String type) {
        int cellIndex = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
        Cell cell = row.createCell(cellIndex);
        String value = component == null ? "" : exportValue(FacesContext.getCurrentInstance(), component);
        cell.setCellValue(new XSSFRichTextString(value));
        if (type.equalsIgnoreCase("facet")) {
            addFacetAlignments(component, cell);
        } else {
            addColumnAlignments(component, cell);
        }

    }

    protected void addColumnValue(Row row, List<UIComponent> components, String type) {
        int cellIndex = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
        Cell cell = row.createCell(cellIndex);
        //cellStyle.setDataFormat(Short.valueOf("0"));
        StringBuilder builder = new StringBuilder();
        FacesContext context = FacesContext.getCurrentInstance();
        if (components.size() == 1) {
            String value = exportValue(context, components.get(0));//lee valores del body
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        } else {
            for (UIComponent component : components) {
                if (component.isRendered()) {
                    String value = exportValue(context, component);
                    if (value != null) {
                        builder.append(value);
                    }
                }
            }

            cell.setCellValue(new XSSFRichTextString(builder.toString()));
            cell.setCellStyle(cellStyle);
        }
    }

    protected void addColumnValue(Row row, List<UIComponent> components, String type, String claseColumna) {
        int cellIndex = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
        Cell cell = row.createCell(cellIndex);
        //cellStyle.setDataFormat(Short.valueOf("0"));
        StringBuilder builder = new StringBuilder();
        FacesContext context = FacesContext.getCurrentInstance();
        if (components.size() == 1) {
            String value = exportValue(context, components.get(0));//lee valores del body
            if (claseColumna.equals("ENTERO")) {
                try {
                    cell.setCellValue(Integer.valueOf(value));
                } catch (Exception ex) {
                    //Logger.getLogger(ExcelExporter.class.getName()).log(Level.SEVERE, null, ex);
                    //cell.setCellValue(value);
                }
                cell.setCellStyle(cellStyle);
            }
            if (claseColumna.equals("NUMERICO")) {
                //if (value.matches("\\d*\\.?\\d+")) {
                try {
                    cell.setCellValue(Double.valueOf(value));
                } catch (Exception ex) {
                    Logger.getLogger(ExcelExporter.class.getName()).log(Level.SEVERE, null, ex);
                    //cell.setCellValue(value);
                }
                cell.setCellStyle(cellStyle);
            }
            if (claseColumna.equals("TEXTO")) {
                cell.setCellValue(value);
                cell.setCellStyle(cellStyle);
            }
            if (claseColumna.equals("FECHA")) {
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    cell.setCellValue(format.parse(value));
                } catch (ParseException ex) {
                    //Logger.getLogger(ExcelExporter.class.getName()).log(Level.SEVERE, null, ex);
                    cell.setCellValue(value);
                }
                cell.setCellStyle(cellStyleFecha);
            }
        } else {
            for (UIComponent component : components) {
                if (component.isRendered()) {
                    String value = exportValue(context, component);
                    if (value != null) {
                        builder.append(value);
                    }
                }
            }

            cell.setCellValue(new XSSFRichTextString(builder.toString()));
            cell.setCellStyle(cellStyle);
        }
        /*if (type.equalsIgnoreCase("facet")) {
            for (UIComponent component : components) {
                addFacetAlignments(component, cell);
            }
        } else {
            for (UIComponent component : components) {
                addColumnAlignments(component, cell);
            }
        }*/

        //bordes de celdas en el body
    }

    protected void addColumnAlignments(UIComponent component, Cell cell) {//util
        if (component instanceof HtmlOutputText) {
            HtmlOutputText output = (HtmlOutputText) component;
            if (output.getStyle() != null && output.getStyle().contains("left")) {
                cell.setCellStyle(cellStyleLeftAlign);
            }
            if (output.getStyle() != null && output.getStyle().contains("right")) {
                cell.setCellStyle(cellStyleRightAlign);
            }
            if (output.getStyle() != null && output.getStyle().contains("center")) {
                cell.setCellStyle(cellStyleCenterAlign);
            }
        }
    }

    protected void addFacetAlignments(UIComponent component, Cell cell) {
        if (component instanceof HtmlOutputText) {
            HtmlOutputText output = (HtmlOutputText) component;
            if (output.getStyle() != null && output.getStyle().contains("left")) {
                cell.setCellStyle(facetStyleLeftAlign);
            } else if (output.getStyle() != null && output.getStyle().contains("right")) {
                cell.setCellStyle(facetStyleRightAlign);
            } else {
                cell.setCellStyle(facetStyleCenterAlign);
            }
        }
    }

    public void customFormat(String facetBackground, String facetFontSize, String facetFontColor, String facetFontStyle, String fontName, String cellFontSize, String cellFontColor, String cellFontStyle, String datasetPadding) {
        if (facetBackground != null) {
            this.facetBackground = Color.decode(facetBackground);
        }
        if (facetFontColor != null) {
            this.facetFontColor = Color.decode(facetFontColor);
        }
        if (fontName != null) {
            this.fontName = fontName;
        }
        if (cellFontColor != null) {
            this.cellFontColor = Color.decode(cellFontColor);
        }

        this.facetFontSize = Short.valueOf(facetFontSize);
        this.facetFontStyle = facetFontStyle;
        this.cellFontSize = Short.valueOf(cellFontSize);
        this.cellFontStyle = cellFontStyle;
        this.datasetPadding = datasetPadding;

    }

    protected void createCustomFonts() {

        Font facetFont = wb.createFont();
        if (cellFontColor != null) {
            XSSFColor cellColor = new XSSFColor(cellFontColor);
            ((XSSFFont) cellFont).setColor(cellColor);
        }
        if (cellFontSize != null) {
            cellFont.setFontHeightInPoints((short) cellFontSize);
        }
        if (cellFontStyle != null) {
            if (cellFontStyle.equalsIgnoreCase("BOLD")) {
                cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            }
            if (cellFontStyle.equalsIgnoreCase("ITALIC")) {
                cellFont.setItalic(true);
            }
        }
        if (facetFontStyle != null) {
            if (facetFontStyle.equalsIgnoreCase("BOLD")) {
                facetFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            }
            if (facetFontStyle.equalsIgnoreCase("ITALIC")) {
                facetFont.setItalic(true);
            }
        }
        if (fontName != null) {
            cellFont.setFontName(fontName);
            facetFont.setFontName(fontName);
        }

        if (facetBackground != null) {
            XSSFColor backgroundColor = new XSSFColor(facetBackground);
            ((XSSFCellStyle) facetStyle).setFillForegroundColor(backgroundColor);
            ((XSSFCellStyle) facetStyleLeftAlign).setFillForegroundColor(backgroundColor);
            ((XSSFCellStyle) facetStyleCenterAlign).setFillForegroundColor(backgroundColor);
            ((XSSFCellStyle) facetStyleRightAlign).setFillForegroundColor(backgroundColor);
            facetStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            facetStyleLeftAlign.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            facetStyleCenterAlign.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            facetStyleRightAlign.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        }

        if (facetFontColor != null) {
            XSSFColor facetColor = new XSSFColor(facetFontColor);
            ((XSSFFont) facetFont).setColor(facetColor);

        }
        if (facetFontSize != null) {
            facetFont.setFontHeightInPoints((short) facetFontSize);
        }

        cellStyle.setFont(cellFont);
        cellStyleFecha.setFont(cellFont);
        cellStyleLeftAlign.setFont(cellFont);
        cellStyleCenterAlign.setFont(cellFont);
        cellStyleRightAlign.setFont(cellFont);

        facetStyle.setFont(facetFont);
        facetStyleLeftAlign.setFont(facetFont);
        facetStyleCenterAlign.setFont(facetFont);
        facetStyleRightAlign.setFont(facetFont);
        //facetStyle.setAlignment(CellStyle.ALIGN_CENTER);

    }

    protected void writeExcelToResponse(ExternalContext externalContext, org.apache.poi.ss.usermodel.Workbook generatedExcel, String filename) throws IOException {

        externalContext.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        externalContext.setResponseHeader("Expires", "0");
        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + filename + ".xlsx");
        externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());

        OutputStream out = externalContext.getResponseOutputStream();
        generatedExcel.write(out);
        externalContext.responseFlushBuffer();
    }
//</editor-fold>

    @Override
    public void export(ActionEvent event, String tableId, FacesContext facesContext, String outputFileName, List<String> subtitulosValue, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, boolean subTable, String titleInec, List<Object[]> resumenFiltro, List<Object[]> lstFor2) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
