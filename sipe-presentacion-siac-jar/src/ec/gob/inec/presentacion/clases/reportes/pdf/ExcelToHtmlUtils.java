/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases.reportes.pdf;

import java.util.Arrays;
//import org.apache.poi.hssf.converter.AbstractExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelToHtmlUtils
        extends AbstractExcelUtils {

    //<editor-fold defaultstate="collapsed" desc="métodos">
    public static void appendAlign(StringBuilder style, short alignment) {//
        String cssAlign = getAlign(alignment);
        if (isEmpty(cssAlign)) {
            return;
        }
        style.append("text-align:");
        style.append(cssAlign);
        style.append(";");
    }

    public static CellRangeAddress[][] buildMergedRangesMap(HSSFSheet sheet) {
        CellRangeAddress[][] mergedRanges = new CellRangeAddress[1][];
        for (CellRangeAddress cellRangeAddress : sheet.getMergedRegions()) {
            int requiredHeight = cellRangeAddress.getLastRow() + 1;
            if (mergedRanges.length < requiredHeight) {
                CellRangeAddress[][] newArray = new CellRangeAddress[requiredHeight][];
                System.arraycopy(mergedRanges, 0, newArray, 0, mergedRanges.length);

                mergedRanges = newArray;
            }
            for (int r = cellRangeAddress.getFirstRow(); r <= cellRangeAddress.getLastRow(); r++) {
                int requiredWidth = cellRangeAddress.getLastColumn() + 1;

                CellRangeAddress[] rowMerged = mergedRanges[r];
                if (rowMerged == null) {
                    rowMerged = new CellRangeAddress[requiredWidth];
                    mergedRanges[r] = rowMerged;
                } else {
                    int rowMergedLength = rowMerged.length;
                    if (rowMergedLength < requiredWidth) {
                        CellRangeAddress[] newRow = new CellRangeAddress[requiredWidth];
                        System.arraycopy(rowMerged, 0, newRow, 0, rowMergedLength);

                        mergedRanges[r] = newRow;
                        rowMerged = newRow;
                    }
                }
                Arrays.fill(rowMerged, cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn() + 1, cellRangeAddress);
            }
        }
        return mergedRanges;
    }
//</editor-fold>

}
