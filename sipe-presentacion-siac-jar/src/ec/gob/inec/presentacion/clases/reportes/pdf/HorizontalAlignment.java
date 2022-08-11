/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.presentacion.clases.reportes.pdf;

public enum HorizontalAlignment {
    GENERAL, LEFT, CENTER, RIGHT, FILL, JUSTIFY, CENTER_SELECTION, DISTRIBUTED;

    private HorizontalAlignment() {
    }

    public short getCode() {
        return (short) ordinal();
    }

    public static HorizontalAlignment forInt(int code) {
        if ((code < 0) || (code >= values().length)) {
            throw new IllegalArgumentException("Invalid HorizontalAlignment code: " + code);
        }
        return values()[code];
    }
}
