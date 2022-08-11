/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.ejb.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * {Insert class description here}
 *
 * @author mchasiguasin
 */
public class Convert {

    private static final Logger LOGGER = Logger.getLogger(Convert.class.getName());

    public static BufferedImage tif2png(String archivo) {
        BufferedImage tif=null;
        try {
            String archivoJPG = archivo.toLowerCase().replace(".tif", ".png");
            tif = ImageIO.read(new File(archivo));
            ImageIO.write(tif, "png", new File(archivoJPG));
            LOGGER.log(Level.INFO, Thread.currentThread().getName() + ": Renombrado archivo de " + archivo + " a " + archivoJPG);

            new File(archivo).delete();
            LOGGER.log(Level.INFO, Thread.currentThread().getName() + ": Borrado archivo " + archivo);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, Thread.currentThread().getName(), e);
        }
        return tif;
    }
}
