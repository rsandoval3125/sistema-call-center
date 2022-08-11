/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.gob.inec.conexion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.postgis.Point;

/**
 *
 * @author lponce
 */
public class JsonMixin {
    //<editor-fold desc="Atributos-propiedades" defaultstate="collapsed">
    //</editor-fold>
    
    //<editor-fold desc="constructor" defaultstate="collapsed">
    //</editor-fold>
    
    //<editor-fold desc="get and set" defaultstate="collapsed">
    //</editor-fold>
    
    //<editor-fold desc="Metodos" defaultstate="collapsed">
    //</editor-fold>
}

abstract class MixIn {
  MixIn(@JsonProperty("x") double x, @JsonProperty("y") double y) { }

  // note: could alternatively annotate fields "w" and "h" as well -- if so, would need to @JsonIgnore getters
  @JsonProperty("x") abstract double getX(); // rename property
  @JsonProperty("y") abstract double getY(); // rename property
  @JsonIgnore abstract Point getFirstPoint(); // we don't need it!
  @JsonIgnore abstract Point getLastPoint(); // we don't need it!
  
}