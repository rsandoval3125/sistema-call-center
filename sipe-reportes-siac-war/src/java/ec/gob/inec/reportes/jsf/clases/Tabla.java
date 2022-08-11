/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.jsf.clases;

import java.io.Serializable;
 
public class Tabla implements Serializable, Comparable<Tabla> {
 
    public String nombre;
     
    public String esquema;
     
    public Tabla(String nombre, String esquema) {
        this.nombre = nombre;
        this.esquema = esquema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }
  
    //Eclipse Generated hashCode and equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((esquema == null) ? 0 : esquema.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tabla other = (Tabla) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (esquema == null) {
            if (other.esquema != null)
                return false;
        } else if (!esquema.equals(other.esquema))
            return false;        
        return true;
    }
 
    @Override
    public String toString() {
        return nombre;
    }
 
    public int compareTo(Tabla document) {
        return this.getNombre().compareTo(document.getNombre());
    }
}  