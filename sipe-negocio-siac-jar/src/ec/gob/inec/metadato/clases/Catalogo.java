/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.clases;

import java.util.List;

/**
 *
 * @author dguano
 */
public class Catalogo {
    
    private Integer idTipoCatalogo;
    private String alias;
    private List<ItemCatalogo> items;
    
     public Catalogo(){
         
     }

    public Catalogo(Integer idTipoCatalogo, String alias) {
        this.idTipoCatalogo = idTipoCatalogo;
        this.alias = alias;
    }
    
    
    

    public Integer getIdTipoCatalogo() {
        return idTipoCatalogo;
    }

    public void setIdTipoCatalogo(Integer idTipoCatalogo) {
        this.idTipoCatalogo = idTipoCatalogo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<ItemCatalogo> getItems() {
        return items;
    }

    public void setItems(List<ItemCatalogo> items) {
        this.items = items;
    }
    
    
    
    
}
