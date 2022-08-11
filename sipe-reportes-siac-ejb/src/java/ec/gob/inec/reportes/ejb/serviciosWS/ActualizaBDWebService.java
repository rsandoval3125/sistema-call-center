/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.serviciosWS;


import ec.gob.inec.reportes.ejb.servicios.ActualizaBDServicioLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
/**
 *
 * @author Jcerda
 */
@WebService(serviceName = "ActualizaBDWebService")
//@XmlSeeAlso({org.postgis.PGgeometry.class, org.postgis.Geometry.class, org.postgis.ComposedGeom.class, org.postgis.Polygon.class, org.postgis.Point.class, org.postgis.LineString.class, org.postgis.LinearRing.class, org.postgis.MultiLineString.class, org.postgis.MultiPoint.class, org.postgis.MultiPolygon.class, org.postgis.PointComposedGeom.class})
//@XmlSeeAlso({org.postgis.PGgeometry.class, org.postgis.Geometry.class})
@Stateless()
public class ActualizaBDWebService {

    @EJB
    private ActualizaBDServicioLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

  
    /**
     * Web service operation
     */
    

    @WebMethod(operationName = "ejecutaSqlMobile")
    public int ejecutaSqlMobile(@WebParam(name = "nombre_conexion") String nombre_conexion, @WebParam(name = "sqlstr") String sqlstr) {
        return ejbRef.ejecutaSqlMobile(nombre_conexion, sqlstr);
    }
    
    @WebMethod(operationName = "recuperaInformacionListSQLMobile")
    public List<Object[]> recuperaInformacionListSQLlMobile(@WebParam(name = "nombre_conexion") String nombre_conexion, @WebParam(name = "sql") String sql,@WebParam(name = "usuario") String usuario) {
        return ejbRef.recuperaInformacionListSQLMobile(nombre_conexion, sql);
    }
    @WebMethod(operationName = "ejecutaSqlCopy")
    public int ejecutaSqlCopy(@WebParam(name = "nombre_conexion") String nombre_conexion, @WebParam(name = "sqlstr") String sqlstr, @WebParam(name = "datosList") String datosList) {
        return ejbRef.ejecutaSqlCopy(nombre_conexion, sqlstr, datosList);
    }
             

}
