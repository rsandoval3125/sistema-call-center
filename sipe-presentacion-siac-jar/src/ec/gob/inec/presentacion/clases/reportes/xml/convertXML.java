/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases.reportes.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author jaraujo
 */
public class convertXML {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">
    private static boolean setupDone = false;
    private static Field JSONObjectMapField = null;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="métodos">
    public String convert_json(String json_value, Map<String, Object> map) {
        String xml = "";
        try {

            JSONObject jsoObject;
            JSONObject jsoObject2 = new JSONObject(json_value);
            //Object objeJson = jsoObject.get("sipe-repo");
            /*String info = jsoObject.getJSONObject("sipe-repo").optString("informacion");
            String filtro = jsoObject.getJSONObject("sipe-repo").optString("filtro");
            String cabecera = jsoObject.getJSONObject("sipe-repo").optString("cabecera");
            
            JSONObject jsoObject3 = new JSONObject();
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("informacion", info);
            data.put("filtro", filtro);
            data.put("cabecera", cabecera);*/
            jsoObject = create();
            jsoObject.put("sipe-repo", map);
            //jsoObject.put("sipe-repo", json_value);

            //jsoObject3.put("sipe-repo", data);
            /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String prettyJson = gson.toJson(jsoObject);*/
 /*Iterator<?> permisos = jsoObject.keys();
            while (permisos.hasNext()) {
                String key = (String) permisos.next();
                JSONObject jsonObject = jsoObject.getJSONObject(key);
                System.out.println("qw "+key);
            }*/
         //   System.out.println("jsoObject " + jsoObject);
            xml = xml + XML.toString(jsoObject);
          //  System.out.println("xml " + xml);

        } catch (Exception e) {
            System.out.println(e);
        }
        xml = xml + "";
        String xml2 = prettyFormat(xml);
        try (FileWriter file = new FileWriter("C:\\testXml.xml")) {

            file.write(xml2);
            file.flush();

        } catch (IOException e) {
        }

        return xml2;
    }

    private static void setupFieldAccessor() {
        if (!setupDone) {
            setupDone = true;
            try {
                JSONObjectMapField = JSONObject.class.getDeclaredField("map");
                JSONObjectMapField.setAccessible(true);
            } catch (NoSuchFieldException ignored) {
                //log.warning("JSONObject implementation has changed, returning unmodified instance");
            }
        }
    }

    private static JSONObject create() {
        setupFieldAccessor();
        JSONObject result = new JSONObject();
        try {
            if (JSONObjectMapField != null) {
                JSONObjectMapField.set(result, new LinkedHashMap<>());
            }
        } catch (IllegalAccessException ignored) {
        }
        return result;
    }

    /*   public String format(String xml) {

        try {
            InputSource src = new InputSource(new StringReader(xml));
            Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

        //May need this: System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");


            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    public static String prettyFormat(String input) {
        return prettyFormat(input, 2);
    }

    /*public static void main(String[] paramArrayOfString) {
        convertJSON json = new convertJSON();
        List<Object[]> fil = new ArrayList<>();
        String[] filString1 = {"fil1", "fil2", "fil3"};
        String[] filString2 = {"fil4", "fil5", "fil6"};
        fil.add(filString1);
        fil.add(filString2);

        List<Object[]> col = new ArrayList<>();
        String[] colString1 = {"col1", "col2", "col3", "1"};
        String[] colString2 = {"col5", "col6", "col7", "2"};
        col.add(colString1);
        col.add(colString2);

        Map<String, Object> filtros = new LinkedHashMap<>();
        filtros.put("Periodo", "40-2018 ABRIL");
        filtros.put("Zonal", "Centro");

        List<Object[]> resumenTabla = new ArrayList<>();
        Object[] res = new Object[3];
        res[0] = "";
        res[1] = "Resumen";
        res[2] = 249;
        resumenTabla.add(0, res);
        Object[] res1 = new Object[3];
        res1[0] = "";
        res1[1] = "Resumen2";
        res1[2] = 249;
        resumenTabla.add(1, res1);

        List<Object[]> footerTabla = new ArrayList<>();
        Object[] foo = new Object[6];
        foo[0] = "Centro";
        foo[1] = "";
        foo[2] = "";
        foo[3] = "";
        foo[4] = 249;
        foo[5] = "";
        footerTabla.add(0, foo);
        Object[] foo1 = new Object[6];
        foo1[0] = "Total";
        foo1[1] = "";
        foo1[2] = "";
        foo1[3] = "";
        foo1[4] = 249;
        foo1[5] = "";
        footerTabla.add(1, foo1);

        String fileJson = json.devolverJSON("reporte1", "subreporte1", col, fil, "13/08/2018", filtros, resumenTabla, footerTabla, "xml");
        convertXML xml = new convertXML();
        xml.convert_json(fileJson, json.getMapJSONXml());
     */
    //xml.format("<root><filtro><Zonal>Centro</Zonal><Periodo>40-2018 ABRIL</Periodo></filtro><datos><row1><col 5>fil3</col 5><col1>fil2</col1></row1><row2><col5>fil6</col5><col1>fil5</col1></row2></datos><información><fecha>13/08/2018</fecha><subreporte>subreporte1</subreporte><reporte>reporte1</reporte></información><cabecera><Resumen>249</Resumen><Resumen2>249</Resumen2></cabecera><pie><Centro>249</Centro><Total>249</Total></pie></root>");
    /*xml.convert_json("{\n" +
"  \"sipe-repo\": {\n" +
"    \"informacion\": {\n" +
"      \"reporte\": \"nom Reporte\",\n" +
"      \"subreporte\": \"Resumen\",\n" +
"      \"fecha\": \"2018-08-13_17:58\"\n" +
"    },\n" +
"    \"filtro\": {},\n" +
"    \"cabecera\": {\n" +
"      \"Resumen\": 249,\n" +
"      \"Resumen2\": 249\n" +
"    },\n" +
"    \"datos\": {\n" +
"      \"row1\": {\n" +
"        \"TotalEmpresas\": 82,\n" +
"        \"Zonal\": \"2\",\n" +
"        \"Investigador\": \"ineccentro01\"\n" +
"      },\n" +
"      \"row2\": {\n" +
"        \"TotalEmpresas\": 83,\n" +
"        \"Zonal\": \"2\",\n" +
"        \"Investigador\": \"ineccentro02\"\n" +
"      },\n" +
"      \"row3\": {\n" +
"        \"TotalEmpresas\": 84,\n" +
"        \"Zonal\": \"2\",\n" +
"        \"Investigador\": \"ineccentro03\"\n" +
"      }\n" +
"    },\n" +
"    \"pie\": {\n" +
"      \"Centro\": 249,\n" +
"      \"Total\": 249\n" +
"    }\n" +
"  }\n" +
"}");*/
    //}

    /*public  String prettyPrintXml(String xml) {

    final StringWriter sw;

    try {
        final OutputFormat format = OutputFormat.createPrettyPrint();
        final org.dom4j.Document document = DocumentHelper.parseText(xml);
        sw = new StringWriter();
        final XMLWriter writer = new XMLWriter(sw, format);
        writer.write(document);
    }
    catch (Exception e) {
        throw new RuntimeException("Error pretty printing xml:\n" + xml, e);
    }
    return sw.toString();
}
    
    public String prettyFormat(String input) {
    return prettyFormat(input, "2");
}

public String prettyFormat(String input, String indent) {
    Source xmlInput = new StreamSource(new StringReader(input));
    StringWriter stringWriter = new StringWriter();
    try {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", indent);
        transformer.transform(xmlInput, new StreamResult(stringWriter));

        String pretty = stringWriter.toString();
        pretty = pretty.replace("\r\n", "\n");
        return pretty;              
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}*/
//</editor-fold>    
}
