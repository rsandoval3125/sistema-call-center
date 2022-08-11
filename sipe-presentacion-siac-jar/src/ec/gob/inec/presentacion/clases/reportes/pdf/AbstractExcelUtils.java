/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases.reportes.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.apache.poi.hssf.record.ExtendedFormatRecord.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

public class AbstractExcelUtils {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">
    static final String EMPTY = "";
    private static final short EXCEL_COLUMN_WIDTH_FACTOR = 256;
    private static final int UNIT_OFFSET_LENGTH = 7;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get and set">
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="métodos">
    /**
     * @param alignment
     * @return
     * @deprecated
     */
    public static String getAlign(short alignment) {
        //int align = alignment;
        return getAlign(HorizontalAlignment.forInt(alignment));
    }

    public static String getAlign(HorizontalAlignment alignment) {
        switch (alignment) {
            case CENTER:
                return "center";
            case CENTER_SELECTION:
                return "center";
            case FILL:
                return "";
            case GENERAL:
                return "";
            case JUSTIFY:
                return "justify";
            case LEFT:
                return "left";
            case RIGHT:
                return "right";
        }
        return "";
    }

    public static String getBorderStyle(short xlsBorder) {
        String borderStyle;
        switch (xlsBorder) {
            case NONE:
                borderStyle = "none";
                break;
            case DASH_DOT:
            case DASH_DOT_DOT:
            case DOTTED:
            case HAIR:
            case MEDIUM_DASH_DOT:
            case MEDIUM_DASH_DOT_DOT:
            case SLANTED_DASH_DOT:
                borderStyle = "dotted";
                break;
            case DASHED:
            case MEDIUM_DASHED:
                borderStyle = "dashed";
                break;
            case DOUBLE:
                borderStyle = "double";
                break;
            default:
                borderStyle = "solid";
        }
        return borderStyle;
    }

    public static String getBorderWidth(short xlsBorder) {
        String borderWidth;
        /*String xlsBorderInt = String.valueOf(xlsBorder);
        final String a=String.valueOf(MEDIUM_DASH_DOT.getCode());*/
        switch (xlsBorder) {
            case MEDIUM_DASH_DOT:
            case MEDIUM_DASH_DOT_DOT:
            case MEDIUM_DASHED:
                borderWidth = "2pt";
                break;
            case THICK:
                borderWidth = "thick";
                break;
            case SLANTED_DASH_DOT:
            case DASHED:
            case DOUBLE:
            default:
                borderWidth = "thin";
        }
        return borderWidth;
    }

    public static String getColor(HSSFColor color) {
        StringBuilder stringBuilder = new StringBuilder(7);
        stringBuilder.append('#');
        for (short s : color.getTriplet()) {
            if (s < 10) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(s));
        }
        String result = stringBuilder.toString();
        if (result.equals("#ffffff")) {
            return "white";
        }
        if (result.equals("#c0c0c0")) {
            return "silver";
        }
        if (result.equals("#808080")) {
            return "gray";
        }
        if (result.equals("#000000")) {
            return "black";
        }
        return result;
    }

    public static int getColumnWidthInPx(int widthUnits) {
        int pixels = widthUnits / 256 * 7;

        int offsetWidthUnits = widthUnits % 256;
        pixels += Math.round(offsetWidthUnits / 36.57143F);

        return pixels;
    }

    public static CellRangeAddress getMergedRange(CellRangeAddress[][] mergedRanges, int rowNumber, int columnNumber) {
        CellRangeAddress[] mergedRangeRowInfo = rowNumber < mergedRanges.length ? mergedRanges[rowNumber] : null;

        CellRangeAddress cellRangeAddress = (mergedRangeRowInfo != null) && (columnNumber < mergedRangeRowInfo.length) ? mergedRangeRowInfo[columnNumber] : null;

        return cellRangeAddress;
    }

    static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static HSSFWorkbook loadXls(File xlsFile)
            throws IOException {
        FileInputStream inputStream = new FileInputStream(xlsFile);
        try {
            return new HSSFWorkbook(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
//</editor-fold>

}
