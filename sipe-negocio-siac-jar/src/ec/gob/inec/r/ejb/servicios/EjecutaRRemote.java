/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.r.ejb.servicios;

//import ec.gob.inec.conexion.DataTabla;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author lponce
 */
@Remote
public interface EjecutaRRemote {

    public int nuevaConexion();

    public void cerrarConexion(int indice);

    public List<String> ejecutarSentencias(int indice, List<String> sentencias);

    //public DataTabla recuperarArchivo(String pathFile, int tipoFile);
}
