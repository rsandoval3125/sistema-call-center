/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.ejb.utils;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Utilitario que permite cifrar cadenas de texto
 *
 * @author mchasiguasin
 */
public class Cifrado {

    /**
     * Encriptación tipo MD5
     *
     * @param clear Texto en claro a ser cifrado.
     * @return Texto cifrado.
     * @throws Exception Si existe algun error en el proceso de cifrado.
     */
    public static String encriptaMD5(String clear) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clear.getBytes());
        int size = b.length;
        StringBuffer h = new StringBuffer(size);
        //algoritmo y arreglo md5
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        //clave encriptada
        return h.toString();
    }

    /**
     * Encriptación de tipo AES
     *
     * @param clave Clave semilla que permite cifrar un texto
     * @param value Texto en claro a ser cifrado.
     * @return Texto cifrado.
     * @throws Exception Si existe algun error en el proceso de cifrado.
     */
    public static String encriptaAES(String clave, String value) throws Exception {
        /*--------Equivalente en JavaScript---------------------------------
            var key = CryptoJS.enc.Utf8.parse('InecDiradGiapeZ1'); 
            var iv = CryptoJS.enc.Hex.parse('20e3e679f7a3fc6297494450ddca7741'); 
            var encrypted = CryptoJS.AES.encrypt("marcelch", key, {iv: iv }); 
            console.log(''+encrypted);*/
        String iv = "20e3e679f7a3fc6297494450ddca7741";//Vector de inicializacion
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKey sks = new SecretKeySpec(clave.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(hex(iv)));
        byte[] encriptado = cipher.doFinal(value.getBytes());
        return DatatypeConverter.printBase64Binary(encriptado);

    }

    public static String desencriptaAES(String clave, String value) throws Exception {
        String iv = "20e3e679f7a3fc6297494450ddca7741";

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKeySpec sks = new SecretKeySpec(clave.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, sks, new IvParameterSpec(hex(iv)));
        byte[] desencriptado = cipher.doFinal(Base64.getDecoder().decode(value));

        return new String(desencriptado, "UTF-8");

    }

    public static byte[] hex(String str) {
        return DatatypeConverter.parseHexBinary(str);
    }
}
