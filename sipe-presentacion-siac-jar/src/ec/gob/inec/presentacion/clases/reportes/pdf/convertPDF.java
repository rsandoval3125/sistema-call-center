package ec.gob.inec.presentacion.clases.reportes.pdf;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.OutputStream;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class convertPDF {

    //<editor-fold defaultstate="collapsed" desc="métodos">
    /*public static void main(String[] args) throws Exception {
		File file = new File("C:\\as.xls");
		
		//only works with .xls - see the other example for .xlsx files
		Document doc = ExcelToHtmlConverter.process(file);
                Document iText_xls_2_pdf = new Document();

		debugHtml(doc);
		writePdf(doc);
	}*/
    public void convertExcelPdf(HSSFWorkbook wb, OutputStream out) {
        try {
            Document doc = ExcelToHtmlConverter.process(wb);
            //debugHtml(doc);
            writePdf(doc, out);
        } catch (IOException ex) {
            Logger.getLogger(convertPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(convertPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(convertPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public static void debugHtml(Document doc) throws Exception {
		DOMSource source = new DOMSource(doc);
		FileWriter writer = new FileWriter(new File("C:\\as.html"));
		StreamResult result = new StreamResult(writer);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.transform(source, result);
	}*/
    public void writePdf(Document doc, OutputStream paramOut) throws Exception {
        //FileOutputStream out = new FileOutputStream("C:\\as.pdf");
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        renderer.layout();
        renderer.createPDF(paramOut);
        paramOut.flush();

    }
//</editor-fold>

}
