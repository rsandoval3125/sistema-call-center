/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.HashMap;

/**
 *
 * @author jguano
 */
public class Objeto {
    private HashMap<String, Object> properties;

    //Create object with properties
    public Objeto(HashMap<String, Object> properties) {
        this.properties = properties;
    }
   public Objeto() {
      
    }
    //Set properties
    public Object setProperty(String key, Object value) {
        return this.properties.put(key, value); //Returns old value if existing
    }

    //Get properties
    public Object getProperty(String key) {
        return this.properties.getOrDefault(key, null);
    }

    @Override
    public String toString() {
        return "MyObject{" + "properties=" + properties + '}';
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }
    
    
}