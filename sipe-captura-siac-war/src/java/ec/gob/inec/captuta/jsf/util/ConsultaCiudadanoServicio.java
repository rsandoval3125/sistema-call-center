
package ec.gob.inec.captuta.jsf.util;

import com.google.gson.Gson;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import entidades.CiudadanoTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import org.apache.commons.lang.StringEscapeUtils;




/**
 *
 * @author cajila
 */
public class ConsultaCiudadanoServicio{
    
    public static String separadorParametro = "/";
    
      public String getToken(String urlSecurity, String usuario, String password){
        try {
            
            
            
            if(urlSecurity != null && usuario != null && password != null){
                Map<String, String> headers = new HashMap<>();
                headers.put("usuario", usuario);
                headers.put("password", password);
                String token = null;
                URL url = new URL(urlSecurity);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                for (String headerKey : headers.keySet()) {
                    conn.setRequestProperty(headerKey, headers.get(headerKey));
                }
                
                System.out.println("conn.getResponseCode() :  "+conn.getResponseCode());

                if (conn.getResponseCode() != 200) {
                      throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                token = conn.getHeaderField("token").toString();
                conn.disconnect();

                return token;
            }
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
        
    }
    
    public  CiudadanoTO consultaCiudadanoPorCedula(String cedula, String urlBase, String urlSecurity, String usuario, String password){
        try {
                
                String token = getToken(urlSecurity, usuario, password);
                
                URL url = new URL(contruirUrlDesaparecidos(cedula, urlBase));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
               conn.setRequestProperty("token", token);
//                Si NO encuentra información de la cédula devuelve 403
//                Si las credenciales de acceso (usuario o contraseña) son incorrectas devuelve 401
//                Si no existen credenciales de acceso (usuario o contraseña) devuelve 402
            
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                Gson gson = new Gson();
                String json = gson.toJson(br.readLine());
                CiudadanoTO ciudadano = new CiudadanoTO();
                json = this.removeQuotesAndUnescape(json);
                if(json.equals("403")){
                    ciudadano.setRespuesta(json+" NO encuentra información de la cédula");
                }else if(json.equals("402")){
                    ciudadano.setRespuesta(json+" No existen credenciales de acceso (usuario o contraseña)");
                }else if(json.equals("401")){
                    ciudadano.setRespuesta(json+" Las credenciales de acceso (usuario o contraseña) son incorrectas");
                }else if(json.equals("ErrorToken")){
                    ciudadano.setRespuesta(json);
                }else{
                    if(!json.equals("") && !json.isEmpty())
                       ciudadano = new Gson().fromJson(this.removeQuotesAndUnescape(json), CiudadanoTO.class);
                    else
                      ciudadano.setRespuesta("No hay datos del Ciudadano");  
                }
                conn.disconnect();
                return ciudadano;
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
//     public  CiudadanoTO consultaCiudadanoPorCedula(String cedula, String urlBase, String usuario, String password){
//        try {
//                String token = getToken(urlsecurity, usuario, password);
//                
//                Map<String, String> headers = new HashMap<>();
//                headers.put("usuario", usuario);
//                headers.put("password", password);
//            
//                URL url = new URL(contruirUrlDesaparecidos(cedula, urlBase));
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                for (String headerKey : headers.keySet()) {
//                    conn.setRequestProperty(headerKey, headers.get(headerKey));
//                }
////                Si NO encuentra información de la cédula devuelve 403
////                Si las credenciales de acceso (usuario o contraseña) son incorrectas devuelve 401
////                Si no existen credenciales de acceso (usuario o contraseña) devuelve 402
//            
//                if (conn.getResponseCode() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//                }
//                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//                Gson gson = new Gson();
//                String json = gson.toJson(br.readLine());
//                CiudadanoTO ciudadano = new CiudadanoTO();
//                json = this.removeQuotesAndUnescape(json);
//                if(json.equals("403")){
//                    ciudadano.setRespuesta(json+" NO encuentra información de la cédula");
//                }else if(json.equals("402")){
//                    ciudadano.setRespuesta(json+" No existen credenciales de acceso (usuario o contraseña)");
//                }else if(json.equals("401")){
//                    ciudadano.setRespuesta(json+" Las credenciales de acceso (usuario o contraseña) son incorrectas");
//                }else{
//                    if(!json.equals("") && !json.isEmpty())
//                       ciudadano = new Gson().fromJson(this.removeQuotesAndUnescape(json), CiudadanoTO.class);
//                    else
//                      ciudadano.setRespuesta("No hay datos del Ciudadano");  
//                }
//                conn.disconnect();
//                return ciudadano;
//         
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    
    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");

        return StringEscapeUtils.unescapeJava(noQuotes);
    }
 
    private String contruirUrlDesaparecidos(String cedulaAconsultar, String urlBase) {
            String url = new StringBuilder().append(urlBase)
                            .append(separadorParametro).append(cedulaAconsultar).toString();
            return url;
    }    
}













//    public  static void main(String[] args) {
//        System.out.println("1.-consulta por cedula");
//        ConsultaCiudadanoServicio sv = new ConsultaCiudadanoServicio();
//        CiudadanoTO ciudadano = sv.consultaCiudadanoPorCedula("1851688000");
//        System.out.println("1.- "+ciudadano.getCedulado().getApellido1());
//        System.out.println("2.- "+ciudadano.getCedulado().getApellido2());
//        System.out.println("3.- "+ciudadano.getCedulado().getCedula());
//        
//    }