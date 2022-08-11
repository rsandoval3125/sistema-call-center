/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.controlador.base;

import ec.gob.inec.clases.GeoCodigo;
import ec.gob.inec.geografia.ejb.entidades.GeoCobertura;
import ec.gob.inec.geografia.ejb.entidades.GeoDpa;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;


/**
 *
 * @author rmoreano
 */
@ManagedBean()
@ViewScoped
public class GeoControlador implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(GeoControlador.class.getName());

    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    @ManagedProperty("#{baseControlador}")
    private BaseControlador baseControlador;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador usuarioControlador;
    private int tipoActualizacion;

    private String ultimaUbicacion;

    //</editor-fold>
    //<editor-fold desc="constructor" defaultstate="collapsed">
    public GeoControlador() {
    }

    @PostConstruct
    public void init() {
        // System.out.println("Inicio init GEO Controlador");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (!request.getParameterMap().isEmpty()) {
            if (request.getParameterMap().containsKey("a")) {
                String idSession = request.getParameter("a");
                //  System.out.println("x=" + idSession);
            }
        }

        // System.out.println("Fin init GEO Controlador");
    }
//</editor-fold> 
    //<editor-fold desc="get and set" defaultstate="collapsed">

    public BaseControlador getBaseControlador() {
        return baseControlador;
    }

    public void setBaseControlador(BaseControlador baseControlador) {
        this.baseControlador = baseControlador;
    }

    public UsuarioControlador getUsuarioControlador() {
        return usuarioControlador;
    }

    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }

    public String getUltimaUbicacion() {
        return ultimaUbicacion;
    }

    public void setUltimaUbicacion(String ultimaUbicacion) {
        this.ultimaUbicacion = ultimaUbicacion;
    }

    //</editor-fold>
    //<editor-fold desc="Metodos" defaultstate="collapsed"> 
    public void actualizacionGeneral() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        tipoActualizacion = Integer.valueOf(params.get("tipoActualizacion"));
        System.out.println("metodo actualizacion general, tipoAct:"+tipoActualizacion);
        switch (tipoActualizacion) {
            case 0:
                String ultimaUbicacionStr = params.get("ubicacion");
                setUltimaUbicacion(ultimaUbicacionStr);
                break;
            case 1:
                recuperaCapas();
                break;
            case 2:
                String geoJson = params.get("poligono");
                String escala = params.get("escala");
                recuperaMapa(geoJson, escala);
                break;
            case 3:
                String geoParametros = params.get("dpa");
                actualizaBDD(geoParametros);
                break;
            case 4:
                String ids = params.get("ids");
                recuperaId(ids);
                break;
            case 5:
                String latitud = params.get("lat");
                String longitud = params.get("lon");
                geoCodigoINEC(Double.valueOf(latitud), Double.valueOf(longitud));
                break;
            case 6:
                String geocodigo = params.get("geocodigo");
                String division = params.get("division");
                geoCodigoINEC(geocodigo, Integer.valueOf(division));
                break;
            case 7:
                /*List<GeoDpa> geoDpaList2 = new ArrayList<>();
                //JSONArray jSONArray = new JSONArray("[{\"nombre\":\"a\"}]");
                for (int i = 0; i < 100; i++) {
                    GeoDpa geoDpa = new GeoDpa();
                    geoDpa.setIdDpa(i);
                    GeoCobertura geoCobertura = baseControlador.getCacheTimer().getCoberturaxIndice(9);
                    //geoCobertura.setCodTipoGeometria(null);
                    MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(1);
                    geoDpa.setCodCobertura(geoCobertura);
                    geoDpa.setCodTipo(metCatalogo);
                    geoDpa.setCodigo("aaaa" + i);
                    geoDpa.setCodigoGeografico("aaaaf" + i);
                    geoDpa.setCodigoGeograficoPadre("aaaas" + i);
                    geoDpa.setCodigoPadre("aaaab" + i);
                    geoDpa.setDescripcion("aaaavv" + i);
                    geoDpa.setEstadoLogico(true);
                    geoDpa.setGeometriaArcgis("");
                    geoDpa.setGeometriaGeojson("");
                    geoDpa.setGeometriaLatitudlongitud("");
                    geoDpa.setModificado(1);
                    geoDpa.setNombre("aaaasss" + i);
                    geoDpa.setNumeracion("00" + i);
                    geoDpa.setNumeroHijos(0);
                    geoDpaList2.add(geoDpa);
                }
                JSONArray jSONArray = new JSONArray(geoDpaList2);*/
                PrimeFaces reqCtx = PrimeFaces.current();
                //reqCtx.update("@(#yourElementId)");
                reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
                //reqCtx.ajax().addCallbackParam("capas", jSONArray.toString());
                

                
                break;
            case 8:
                String claveDPA = params.get("dpa");
                buscarJSON(claveDPA);
                break;
        }
    }

    private void recuperaCapas() {
        Map<String, Object> campos = new LinkedHashMap<>();
        campos.put("estadoLogico", true);
        try {

            List<GeoCobertura> geoCoberturaList = baseControlador.getCacheTimer().getGeoCoberturaList();
            //baseControlador.getGeoCoberturaServicioRemote().listarPorCampos(campos, "indice");
            //  System.out.println("geolista: " + geoCoberturaList.size());
            //JSONObject jSONObject = new JSONObject(geoCoberturaList);

            JSONArray jSONArray = new JSONArray(geoCoberturaList);
            PrimeFaces reqCtx = PrimeFaces.current();
            //reqCtx.update("@(#yourElementId)");
            reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
            reqCtx.ajax().addCallbackParam("capas", jSONArray.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void recuperaMapa(String poligonoGeoJSon, String coberturas) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(new java.util.Date());
            System.out.println("Fecha de recepcion JS: " + timeStamp);
            List<Object[]> geoDpaList = baseControlador.getGeoDpaServicioRemote().listarporPoligonoEscala(poligonoGeoJSon, coberturas);
            timeStamp = new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(new java.util.Date());
            System.out.println("Fecha de recepcion EJB: " + timeStamp);
            List<GeoDpa> geoDpaList2 = new ArrayList<>();
            int cont = 0;
            if (geoDpaList != null) {
                for (int j = 0; j < geoDpaList.size(); j++) {
                    GeoCobertura geoCobertura = baseControlador.getCacheTimer().getCoberturaxIndice(Integer.valueOf(String.valueOf(geoDpaList.get(j)[7])));
                    MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(Integer.valueOf(String.valueOf(geoDpaList.get(j)[8])));;
                    GeoDpa geoDpa = new GeoDpa();
                    geoDpa.setIdDpa(Integer.valueOf(String.valueOf(geoDpaList.get(j)[0])));
                    geoDpa.setCodCobertura(geoCobertura);
                    geoDpa.setCodTipo(metCatalogo);
                    geoDpa.setCodigo(String.valueOf(geoDpaList.get(j)[1]));
                    geoDpa.setCodigoGeografico(String.valueOf(geoDpaList.get(j)[5]));
                    geoDpa.setCodigoGeograficoPadre(String.valueOf(geoDpaList.get(j)[6]));
                    geoDpa.setCodigoPadre(String.valueOf(geoDpaList.get(j)[2]));
                    geoDpa.setDescripcion(String.valueOf(geoDpaList.get(j)[4]));
                    geoDpa.setEstadoLogico(Boolean.valueOf(String.valueOf(geoDpaList.get(j)[15])));
                    geoDpa.setGeometriaGeojson(String.valueOf(geoDpaList.get(j)[9]));
                    geoDpa.setModificado(Integer.valueOf(String.valueOf(geoDpaList.get(j)[12])));
                    geoDpa.setNombre(String.valueOf(geoDpaList.get(j)[3]));
                    geoDpa.setNumeracion(String.valueOf(geoDpaList.get(j)[14]));
                    geoDpa.setNumeroHijos(Integer.valueOf(String.valueOf(geoDpaList.get(j)[13])));
                    geoDpaList2.add(geoDpa);
                }
                JSONArray jSONArray = new JSONArray(geoDpaList2);
                String JSONAstr = jSONArray.toString();
                timeStamp = new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(new java.util.Date());
                System.out.println("Fecha de envio JS: " + timeStamp);
                PrimeFaces reqCtx = PrimeFaces.current();
                //reqCtx.update("@(#yourElementId)");
                reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
                reqCtx.ajax().addCallbackParam("geometrias", JSONAstr);
            }else{
                System.out.println("ERROR DE DPA NULO");
                baseControlador.addErrorMessage("Error no se puede recuperar DPA.");
             }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void actualizaBDD(String dpa) {
        try {
            System.out.println(dpa);
            JSONObject dpaObject = new JSONObject(dpa);
            String operacion = dpaObject.getString("operacion");
            JSONObject dpaPropiedades = dpaObject.getJSONObject("propiedades");
            Integer idCob = dpaObject.getInt("codigoCapa");
            GeoCobertura geoCobertura = baseControlador.getCacheTimer().getCoberturaxID(idCob);
            GeoCobertura geoCoberturaPadre = baseControlador.getCacheTimer().getCoberturaxIndice(geoCobertura.getCodCoberturaPadre());
            int codigoMet = dpaPropiedades.getJSONObject("codTipo").getInt("idCatalogo");
            MetCatalogo metCatalogo = baseControlador.getCacheTimer().getCatalogoxID(codigoMet);

            GeoDpa geoDpa = new GeoDpa();
            geoDpa.setCodCobertura(geoCobertura);
            geoDpa.setCodTipo(metCatalogo);
            geoDpa.setCodigo(dpaPropiedades.getString("codigo"));
            geoDpa.setCodigoGeografico(dpaPropiedades.getString("codigoGeografico"));
            geoDpa.setCodigoGeograficoPadre(dpaPropiedades.getString("codigoGeograficoPadre"));
            geoDpa.setCodigoPadre(dpaPropiedades.getString("codigoPadre"));
            geoDpa.setDescripcion(dpaPropiedades.getString("descripcion"));
            //geoDpa.setGeometriaArcgis(operacion);
            geoDpa.setGeometriaGeojson(dpaPropiedades.getString("geometriaGeojson"));
            //geoDpa.setGeometriaLatitudlongitud(operacion);
            geoDpa.setModificado(1);
            geoDpa.setNombre(dpaPropiedades.getString("nombre"));
            geoDpa.setNumeracion(String.valueOf(dpaPropiedades.get("numeracion")));
            geoDpa.setNumeroHijos(dpaPropiedades.getInt("numeroHijos"));
            List<GeoDpa> geoDpaList = new ArrayList<>();
            //= baseControlador.getGeoDpaServicioRemote().listarporPoligonoEscala(poligonoGeoJSon, escala);
            Map<String, Object> campos = new LinkedHashMap<>();
            campos.put("codigo", geoDpa.getCodigoPadre());
            campos.put("codCobertura", geoCoberturaPadre);
            switch (operacion) {
                case "insertar":
                    geoDpa.setEstadoLogico(Boolean.TRUE);
                    geoDpa = baseControlador.getGeoDpaServicioRemote().insertar(geoDpa);
                    geoDpaList.add(geoDpa);
                    geoDpa = new GeoDpa();
                    geoDpa = baseControlador.getGeoDpaServicioRemote().listarPorCampos(campos, "").get(0);
                    geoDpaList.add(geoDpa);
                    break;
                case "actualizar":
                    geoDpa.setIdDpa(dpaPropiedades.getInt("idDpa"));
                    geoDpa.setEstadoLogico(Boolean.TRUE);
                    geoDpa = baseControlador.getGeoDpaServicioRemote().actualizar(geoDpa);
                    geoDpaList.add(geoDpa);
                    break;
                case "eliminar":
                    geoDpa.setIdDpa(dpaPropiedades.getInt("idDpa"));
                    geoDpa.setEstadoLogico(Boolean.FALSE);
                    geoDpa = baseControlador.getGeoDpaServicioRemote().actualizar(geoDpa);
                    geoDpaList.add(geoDpa);
                    geoDpa = new GeoDpa();
                    geoDpa = baseControlador.getGeoDpaServicioRemote().listarPorCampos(campos, "").get(0);
                    geoDpaList.add(geoDpa);
                    break;
            }

            JSONArray jSONArray = new JSONArray(geoDpaList);
            System.out.println("Fecha de envio JS: " + jSONArray.toString());
            PrimeFaces reqCtx = PrimeFaces.current();
            reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
            reqCtx.ajax().addCallbackParam("geometrias", jSONArray.toString());
        } catch (Exception er) {
            Logger.getLogger(GeoControlador.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    private void recuperaId(String ids) {
        try {
            List<Object[]> camposObjectList = new LinkedList<>();
            Object[] objects = new Object[5];
            objects[0] = "idDpa";
            objects[1] = "in";
            objects[2] = ids;
            camposObjectList.add(objects);
            List<GeoDpa> geoDpaList = baseControlador.getGeoDpaServicioRemote().listarPorCampos(camposObjectList, "");
            System.out.println("recuperado: " + geoDpaList.size());
            JSONArray jSONArray = new JSONArray(geoDpaList);
            PrimeFaces reqCtx = PrimeFaces.current();
            reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
            reqCtx.ajax().addCallbackParam("geometrias", jSONArray.toString());
        } catch (Exception er) {
            Logger.getLogger(GeoControlador.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    private void geoCodigoINEC(Double latitud, Double longitud) {
        GeoCodigo geoCodigo = new GeoCodigo();
        JSONArray jSONArray = new JSONArray();

        List<Integer> divs = new LinkedList<>();
        divs.add(2);
        divs.add(4);
        divs.add(5);
        divs.add(6);
        divs.add(7);
        divs.add(8);
        for (int j = 0; j < divs.size(); j++) {
            String codificacion = geoCodigo.latLonTOGeoCodigoInec(divs.get(j), latitud, longitud);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("division", divs.get(j));
            jSONObject.put("valor", codificacion);
            jSONArray.put(jSONObject);
        }
        PrimeFaces reqCtx = PrimeFaces.current();
        reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
        reqCtx.ajax().addCallbackParam("codigos", jSONArray.toString());
    }

    private void geoCodigoINEC(String geocodigo, int division) {
        GeoCodigo geoCodigo = new GeoCodigo();
        List<Double> coordenadas = geoCodigo.geoCodigoInecToLanLon(division, geocodigo);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("latitud", coordenadas.get(0));
        jSONObject.put("longitud", coordenadas.get(1));
        PrimeFaces reqCtx = PrimeFaces.current();
        reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
        reqCtx.ajax().addCallbackParam("codigos", jSONObject.toString());
    }

    private void ant() {
        /*double xmin = Double.parseDouble(params.get("longitudMinima"));
        double xmax = Double.parseDouble(params.get("longitudMaxima"));
        double ymin = Double.parseDouble(params.get("latitudMinima"));
        double ymax = Double.parseDouble(params.get("latitudMaxima"));
        int wkid = Integer.valueOf(params.get("wkid"));
        int nivel = Integer.valueOf(params.get("nivel"));
        String nombreConexion = "geoportal";
        double buffer = Double.parseDouble(propiedades.getPropertieValue("buffer"));
        List<AnyTypeArray> geoList = actualizaBDController.recuperaGeoInecExtPol(nombreConexion, xmin, xmax, ymin, ymax, buffer, wkid, nivel);
        geoListObjectList = new ArrayList<>();//cacheController.getGeoListObjectListIndice();
        for (GeoListObject geoListObjectCache : cacheController.getGeoListObjectListIndice()) {
            GeoListObject geoListObject = new GeoListObject();
            geoListObject.setNivel(geoListObjectCache.getNivel());
            geoListObject.setNombre(geoListObjectCache.getNombre());
            geoListObjectList.add(geoListObject);
        }

        JSONObject jsondestino = new JSONObject();
        for (AnyTypeArray geoDPAList : geoList) {
            List<Object> geoDPA = geoDPAList.getItem();
            GeoListObject geoListObject;
            try {
                geoListObject = geoListObjectList.stream().filter(p -> p.getNombre().equals(geoDPA.get(4).toString())).collect(Collectors.toList()).get(0);
                GeoObject geoObject;
                geoObject = new GeoObject();
                geoObject.setListaObjetos(geoDPA);
                geoObject.setIdentificadorDPA(geoDPA.get(0).toString());
                if (geoDPA.get(1) != null) {
                    geoObject.setNombreDPA(geoDPA.get(1).toString());
                    System.out.println(geoDPA.get(1).toString());
                }
                geoObject.setNivelDPA((int) geoDPA.get(3));
                geoObject.setNombreGrupoDPA((String) geoDPA.get(4));
                jsondestino = new JSONObject();
                if (geoDPA.get(2) != null) {
                    String jsongeometry = geoDPA.get(2).toString();
                    jsondestino.put("geometry", jsongeometry);
                }
                JSONObject jsonattributes = new JSONObject();
                jsonattributes.put("id", geoDPA.get(0).toString());
                jsonattributes.put("nombre", geoDPA.get(1).toString());
                jsonattributes.put("valor", 0);
                jsondestino.put("attributes", jsonattributes);
                geoObject.setGeometriaJsonJS(jsondestino);
                geoListObject.getGeoObjectList().add(geoObject);

                System.out.println("geo list loc" + geoListObject.getGeoObjectList().size());
                System.out.println("geo list loc" + geoListObject.getNombre());
            } catch (Exception e) {
                System.out.println("err geo list bus" + (String) geoDPA.get(4));
            }

        }

        jsondestino = new JSONObject();

        for (GeoListObject geoListObject : geoListObjectList) {
            System.out.println("list json:" + geoListObject.getNombre());
            JSONObject jsonNivel = new JSONObject();
            jsonNivel.put("id", geoListObject.getNivel());
            jsonNivel.put("nombre", geoListObject.getNombre());
            JSONObject jsongeometrias = new JSONObject();
            for (GeoObject geoObject : geoListObject.getGeoObjectList()) {
                JSONObject jsonObjeto = new JSONObject();
                jsonObjeto.put("id", geoObject.getIdentificadorDPA());
                jsonObjeto.put("nombre", geoObject.getNombreDPA());
                jsonObjeto.put("grafico", geoObject.getGeometriaJsonJS());
                jsongeometrias.put(geoObject.getIdentificadorDPA(), jsonObjeto);
            }
            jsonNivel.put("geometrias", jsongeometrias);
            jsondestino.put(String.valueOf(geoListObject.getNombre()), jsonNivel);
        }*/
    }

    private void recuperaValorDPa(String dpa) {

    }

    public void buscarJSON(String claveMapaVector) {
        try {
            List<String> lisGeoDatos = new ArrayList<String>();
            // Validando que el ingreso del parametro no llegue hasta zonal
            /*if (claveMapaVector.equals("ECUADOR")) {
                lisGeoDatos = new ArrayList<String>();
                String codProvin = "";
                for (int i = 1; i <= 24; i++) {
                    if (i >= 1 && i < 10) {
                        codProvin = "p0" + i;
                        lisGeoDatos.add(codProvin);
                    } else if (i >= 10) {
                        codProvin = "p" + i;
                        lisGeoDatos.add(codProvin);
                    }
                }
                //lisGeoDatos.add("shapeEcu");
                //lisGeoDatos.add("shapeGala");
                centrideGeoDato = "CENTROIDE";
            } else {*/
            lisGeoDatos = new ArrayList<String>();
            //lisGeoDatos.add("shapeEcu");
            //lisGeoDatos.add("shapeGala");
            List<Object[]> lstGeo = new ArrayList<Object[]>();
            lstGeo = baseControlador.getActualizaBDServicioRemote().recuperaInformacionListJSONINFOCAPT("geoportal", claveMapaVector, 1);

            /*ActualizaBDWebService port = serviceGEO.getActualizaBDWebServicePort();
                
                lstGeo = port.recuperaInformacionListJSONINFOCAPT("geoportal", claveMapaVector, 1);*/
            if (lstGeo.size() > 0) {
                String geoDato = "";
                for (Object[] datos : lstGeo) {
                    String[] datosJSON = new String[6];
                    datosJSON[0] = datos[0].toString(); //Código
                    datosJSON[1] = datos[1].toString(); //Nombre
                    datosJSON[2] = datos[2].toString(); //Geo Dato
                    datosJSON[3] = datos[3].toString(); //Centroide
                    datosJSON[4] = datos[4].toString(); //Tipo de geoDato
                    datosJSON[5] = datos[5].toString(); //Codigo de localidad                                          
                    //--Construyo GeoJson con atributos--//
                    /*if (datosJSON[0].equals(claveMapaVector)) {
                        centrideGeoDato = "{" + "\"features\"" + ":" + "[" + "{" + "\"type\"" + ":" + "\"Feature\"" + "," + "\"properties\"" + ":" + "{" + "\"CODIGO\"" + ":" + "\"" + datosJSON[0] + "\"" + "," + "\"NOMBRE\"" + ":" + "\"" + datosJSON[1].replace("\"", "") + "\"" + "}" + "," + "\"geometry\"" + ":" + datosJSON[3] + "}" + "]" + "}";
                    }*/
                    geoDato = "{" + "\"features\"" + ":" + "[" + "{" + "\"type\"" + ":" + "\"Feature\"" + "," + "\"properties\"" + ":" + "{" + "\"CODIGO\"" + ":" + "\"" + datosJSON[0] + "\"" + "," + "\"NOMBRE\"" + ":" + "\"" + datosJSON[1].replace("\"", "") + "\"" + "," + "\"TIPOGEODATO\"" + ":" + "\"" + datosJSON[4] + "\"" + "," + "\"CODLOCALIDAD\"" + ":" + "\"" + datosJSON[5] + "\"" + "}" + "," + "\"geometry\"" + ":" + datosJSON[2] + "}" + "]" + "}";
                    lisGeoDatos.add(geoDato);

                    PrimeFaces reqCtx = PrimeFaces.current();
                    //reqCtx.update("@(#yourElementId)");
                    reqCtx.ajax().addCallbackParam("tipoActualizacion", tipoActualizacion);
                    reqCtx.ajax().addCallbackParam("geometrias", lisGeoDatos.toString());
                }
            } else {
                baseControlador.addWarningMessage("!No se encontro resultados del parámetro ingresado¡");
            }
            //}
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
    //</editor-fold>
}
