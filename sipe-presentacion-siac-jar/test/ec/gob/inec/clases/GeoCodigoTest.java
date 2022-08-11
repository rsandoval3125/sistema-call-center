/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.clases;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lponce
 */
public class GeoCodigoTest {
    
    public GeoCodigoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of latLonTOGeoCodigoInec method, of class GeoCodigo.
     */
    @Test
    public void testLatLonTOGeoCodigoInec() {
        System.out.println("latLonTOGeoCodigoInec");
        int divisiones = 5;
        double latitud = -0.209733;
        double longitud = -78.502586;
        GeoCodigo instance = new GeoCodigo();
        String expResult = "CHJBIFPQ";
        String result = instance.latLonTOGeoCodigoInec(divisiones, latitud, longitud);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        System.out.println("lat lon res: "+result);
    }

    /**
     * Test of geoCodigoInecToLanLon method, of class GeoCodigo.
     */
    @Test
    public void testGeoCodigoInecToLanLon() {
        System.out.println("geoCodigoInecToLanLon");
        int divisiones = 5;
        String geocodigo = "CHJBIFPQ";
        GeoCodigo instance = new GeoCodigo();
        List<Double> expResult = null;
        List<Double> result = instance.geoCodigoInecToLanLon(divisiones, geocodigo);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        System.out.println("res: "+result.size());
        System.out.println("lat: "+result.get(0));
        System.out.println("lon: "+result.get(1));
    }

    
}
