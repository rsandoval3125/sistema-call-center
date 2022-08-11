/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases.reportes.json;

/**
 *
 * @author jaraujo
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class convertJSON {

    //<editor-fold defaultstate="collapsed" desc="atributos-propiedades">
    Map<String, Object> mapJSONXml = new LinkedHashMap<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get and set">
    public Map<String, Object> getMapJSONXml() {
        return mapJSONXml;
    }

    public void setMapJSONXml(Map<String, Object> mapJSONXml) {
        this.mapJSONXml = mapJSONXml;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="métodos">
    public String devolverJSON(String reporte, String pestaña, List<Object[]> nombresColumnas, List<Object[]> filas, String fecha, Map<String, Object> filtros, List<Object[]> resumenTabla, List<Object[]> footerTabla, String tipo) {
        try {
            List<String> col = new ArrayList<>();
            for (int c = 0; c < nombresColumnas.size(); c++) {
                col.add(nombresColumnas.get(c)[8].toString());
            }

            Map<String, Object> nivel0 = new LinkedHashMap<>();
            List<Object> data = new ArrayList<>();
            Map<String, Object> data2 = new LinkedHashMap<>();
            Map<String, Object> informacion = new LinkedHashMap<>();
            //Map<String, Object> nivel2 = new LinkedHashMap<>();
            Map<String, Object> objNivel2 = new LinkedHashMap<>();
            Map<Object, Object> resumenTablaRepo = new LinkedHashMap<>();
            Map<Object, Object> footerTablaRepo = new LinkedHashMap<>();

            for (int i = 0; i < filas.size(); i++) {
                Map<String, Object> objectAConvert = new HashMap<>();
                for (int j = 0; j < nombresColumnas.size(); j++) {
                    //String columna = nombresColumnas.get(i)[0].toString();
                    Object fila = filas.get(i)[Integer.valueOf(nombresColumnas.get(j)[3].toString())];
                    objectAConvert.put(col.get(j), fila);
                }
                data.add(objectAConvert);

                data2.put("row" + (i + 1), objectAConvert);
                //objectAConvert2.put(i, objectAConvert);
            }

            for (int i = 0; i < resumenTabla.size(); i++) {
                resumenTablaRepo.put(resumenTabla.get(i)[1], resumenTabla.get(i)[2]);
            }
            for (int i = 0; i < footerTabla.size(); i++) {
                footerTablaRepo.put(footerTabla.get(i)[0], footerTabla.get(i)[4]);
            }

            String nivel1 = "informacion";

            informacion.put("reporte", reporte);
            informacion.put("subreporte", pestaña);
            informacion.put("fecha", fecha);

            objNivel2.put(nivel1, informacion);
            objNivel2.put("filtro", filtros);
            objNivel2.put("cabecera", resumenTablaRepo);

            if (tipo.equals("json")) {
                objNivel2.put("datos", data);
            } else if (tipo.equals("xml")) {
                objNivel2.put("datos", data2);
            }
            objNivel2.put("pie", footerTablaRepo);

            Gson prettyGson2 = new GsonBuilder().setPrettyPrinting().create();
            String reprePretty2 = null;
            if (tipo.equals("json")) {
                reprePretty2 = prettyGson2.toJson(objNivel2);
            } else if (tipo.equals("xml")) {
                //nivel0.put("sipe-repo", objNivel2);
                //reprePretty2 = prettyGson2.toJson(nivel0);
                //setMapJSONXml(nivel0);
                reprePretty2 = prettyGson2.toJson(objNivel2);
                setMapJSONXml(objNivel2);
            }
            //String reprePretty2 = prettyGson2.toJson(objNivel2);

            /*try (FileWriter file = new FileWriter("C:\\test2.json")) {

                file.write(reprePretty2);
                file.flush();

            } catch (IOException e) {
                Logger.getLogger(convertJSON.class.getName()).log(Level.SEVERE, null, e);
            }*/
            return reprePretty2;
        } catch (Exception e) {
            Logger.getLogger(convertJSON.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
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

        json.devolverJSON("reporte1", "subreporte1", col, fil, "13/08/2018", filtros, resumenTabla, footerTabla, "json");

    }*/
//</editor-fold>
}
