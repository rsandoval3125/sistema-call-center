/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.seguridad.clases;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
public class Tabla implements Serializable {

    private String schemaname;
    private String tableName;

    public Tabla(String schemaname, String tableName) {
        this.schemaname = schemaname;
        this.tableName = tableName;
    }

    public String getSchemaname() {
        return schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return this.schemaname + "." + this.tableName;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

}
