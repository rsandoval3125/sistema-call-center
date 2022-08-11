/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.clases;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author lponce
 */
public class GeoCodigo {

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    List<CodigosGeo> codigosGeos;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public GeoCodigo() {
        codigosGeos = new LinkedList<>();
        List<Integer> divs = new LinkedList<>();
        divs.add(2);
        divs.add(4);
        divs.add(5);
        divs.add(6);
        divs.add(7);
        divs.add(8);
        for (int j = 0; j < divs.size(); j++) {
            List<String> codigos = new LinkedList<>();
            CodigosGeo codigosGeo = new CodigosGeo();
            int codigoActual=divs.get(j);
            codigosGeo.division = codigoActual;
            switch (codigoActual) {
                case 2:
                    for (int i = 0; i < 4; i++) {
                        char ch = (char) (i + 65);
                        codigos.add(String.valueOf(ch));
                    }
                    codigosGeo.codigos = codigos;
                    codigosGeo.zoomIni = 11;
                    codigosGeo.zoomFin = 22;
                    break;
                case 4:
                    for (int i = 0; i < 16; i++) {
                        char ch = (char) (i + 65);
                        codigos.add(String.valueOf(ch));
                    }
                    codigosGeo.codigos = codigos;
                    codigosGeo.zoomIni = 3;
                    codigosGeo.zoomFin = 11;
                    break;
                case 5:
                    for (int i = 0; i < 25; i++) {
                        char ch = (char) (i + 65);
                        codigos.add(String.valueOf(ch));
                    }
                    codigosGeo.codigos = codigos;
                    codigosGeo.zoomIni = 3;
                    codigosGeo.zoomFin = 10; //puede ser 9
                    break;
                case 6:
                    for (int i = 0; i < 26; i++) {
                        char ch = (char) (i + 65);
                        codigos.add(String.valueOf(ch));
                    }
                    for (int i = 0; i < 10; i++) {
                        char ch = (char) (i + 48);
                        codigos.add(String.valueOf(ch));
                    }
                    codigosGeo.codigos = codigos;
                    codigosGeo.zoomIni = 3;
                    codigosGeo.zoomFin = 9; //puede ser 8
                    break;
                case 7:
                    for (int i = 0; i < 26; i++) {
                        char ch = (char) (i + 65);
                        codigos.add(String.valueOf(ch));
                    }
                    for (int i = 0; i < 23; i++) {
                        char ch = (char) (i + 97);
                        codigos.add(String.valueOf(ch));
                    }
                    codigosGeo.codigos = codigos;
                    codigosGeo.zoomIni = 3;
                    codigosGeo.zoomFin = 8;
                    break;
                case 8:
                    for (int i = 0; i < 26; i++) {
                        char ch = (char) (i + 65);
                        codigos.add(String.valueOf(ch));
                    }
                    for (int i = 0; i < 10; i++) {
                        char ch = (char) (i + 48);
                        codigos.add(String.valueOf(ch));
                    }
                    for (int i = 0; i < 26; i++) {
                        char ch = (char) (i + 97);
                        codigos.add(String.valueOf(ch));
                    }
                    codigos.add("*");
                    codigos.add("-");
                    codigosGeo.codigos = codigos;
                    codigosGeo.zoomIni = 2;
                    codigosGeo.zoomFin = 7;
                    break;
            }
            codigosGeos.add(codigosGeo);
        }
    }

    //</editor-fold> 
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed"> 
    public String latLonTOGeoCodigoInec(int divisiones, double latitud, double longitud) {
        String codigo = "";
        int xant = 0, yant = 0;
        System.out.println("divis: "+divisiones);
        System.out.println("divis: "+codigosGeos.size());
        CodigosGeo codigosGeo = codigosGeos.stream().filter(x -> x.division == divisiones).collect(Collectors.toList()).get(0);
        for (int i = 0; i <= codigosGeo.zoomFin; i++) {
            int potencia = (int) Math.pow(divisiones, i);
            int x = (int) Math.floor(potencia * (longitud + 180) / 360);
            int y = (int) Math.floor(potencia * (1 - (Math.log(Math.tan(latitud * Math.PI / 180) + (1 / Math.cos(latitud * Math.PI / 180))) / Math.PI)) / 2);
            int indice = divisiones * (x - divisiones * xant) + (y - divisiones * yant);
            if (i >= codigosGeo.zoomIni) {
                codigo = codigo + codigosGeo.codigos.get(indice);
            }
            xant = x;
            yant = y;
        }
        return codigo;
    }

    public List<Double> geoCodigoInecToLanLon(int divisiones, String geocodigo) {
        CodigosGeo codigosGeo = codigosGeos.stream().filter(x -> x.division == divisiones).collect(Collectors.toList()).get(0);
        List<Double> resultado = encontrarIndice(0, 0, 0, geocodigo, codigosGeo);
        return resultado;
    }

    public List<Double> encontrarIndice(int zoomAct, int xAnt, int yAnt, String geocodigo, CodigosGeo codigosGeo) {
        int xMax, yMax, xMin, yMin, potencia;
        double latmaxdecE, latmindecE, lonmaxdecE, lonmindecE, latmaxdecG, latmindecG, lonmaxdecG, lonmindecG;
        List<Double> resultado = new LinkedList<>();
        latmaxdecE = 1.660;
        latmindecE = -5.020;
        lonmaxdecE = -75.188;
        lonmindecE = -81.085;
        latmaxdecG = 0.650;
        latmindecG = -1.670;
        lonmaxdecG = -89.230;
        lonmindecG = -91.680;
        potencia = (int) Math.pow(codigosGeo.division, zoomAct);
        if (zoomAct <= codigosGeo.zoomIni) {
            xMax = (int) Math.ceil((lonmaxdecE + 180.0) / 360.0 * potencia);
            xMin = (int) Math.floor((lonmindecE + 180.0) / 360.0 * potencia);
            yMax = (int) Math.ceil(potencia * (1.0 - (Math.log(Math.tan(latmindecE * Math.PI / 180.0) + (1.0 / Math.cos(latmindecE * Math.PI / 180))) / Math.PI)) / 2);
            yMin = (int) Math.floor(potencia * (1.0 - (Math.log(Math.tan(latmaxdecE * Math.PI / 180.0) + (1.0 / Math.cos(latmaxdecE * Math.PI / 180))) / Math.PI)) / 2);
        } else {
            xMax = (int) xAnt * codigosGeo.division + codigosGeo.division;
            xMin = (int) xAnt * codigosGeo.division;
            yMin = (int) yAnt * codigosGeo.division;
            yMax = (int) yAnt * codigosGeo.division + codigosGeo.division;
        }
        for (int i = xMin; i < xMax; i++) {
            for (int j = yMin; j < yMax; j++) {
                if (resultado.isEmpty()) {
                    if (zoomAct >= codigosGeo.zoomIni) {
                        int indice = codigosGeo.division * (i - codigosGeo.division * xAnt) + (j - codigosGeo.division * yAnt);
                        String codigo = geocodigo.split(Pattern.quote("+"))[0];
                        codigo = codigo.substring(zoomAct - codigosGeo.zoomIni, zoomAct - codigosGeo.zoomIni + 1);
                        int indiceCodigo = codigosGeo.codigos.indexOf(codigo);
                        if (indice == indiceCodigo) {
                            System.out.println("zoom: " + zoomAct + " x: " + i + " y: " + j);
                            if (zoomAct == codigosGeo.zoomFin) {
                                double longitud = 360 * (double) i / (double) potencia - 180;
                                double latitud = Math.atan(Math.sinh(Math.PI - 2 * Math.PI * (double) j / (double) potencia)) * 180 / Math.PI;
                                resultado.add(latitud);
                                resultado.add(longitud);
                                i = xMax;
                            } else if (zoomAct <= codigosGeo.zoomFin) {
                                resultado = encontrarIndice(zoomAct + 1, i, j, geocodigo, codigosGeo);
                            }
                            j = yMax;
                        }
                    } else if (zoomAct <= codigosGeo.zoomFin) {
                        resultado = encontrarIndice(zoomAct + 1, i, j, geocodigo, codigosGeo);
                    }
                }
            }
        }
        return resultado;
    }
    //</editor-fold>
}

class CodigosGeo {

    int division;
    List<String> codigos;
    int zoomIni;
    int zoomFin;

    public CodigosGeo() {
    }

    public CodigosGeo(int division, List<String> codigos, int zoomIni, int zoomFin) {
        this.division = division;
        this.codigos = codigos;
        this.zoomIni = zoomIni;
        this.zoomFin = zoomFin;
    }

}
