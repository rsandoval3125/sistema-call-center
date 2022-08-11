/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.clases;

/**
 *
 * @author dguano
 */
public class ItemCatalogo {
    
    private String itemLabel;
    private String itemValue;
    private String itemIcon;
    private String itemLabel2;

    public ItemCatalogo(String itemLabel, String itemValue, String itemIcon, String itemLabel2) {
        this.itemLabel = itemLabel;
        this.itemValue = itemValue;
        this.itemIcon = itemIcon;
        this.itemLabel2 = itemLabel2;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemLabel2() {
        return itemLabel2;
    }

    public void setItemLabel2(String itemLabel2) {
        this.itemLabel2 = itemLabel2;
    }
    
    
    
}
