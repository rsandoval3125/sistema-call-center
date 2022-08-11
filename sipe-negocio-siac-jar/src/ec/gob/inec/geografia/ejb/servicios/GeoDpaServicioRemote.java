/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.geografia.ejb.servicios;

import ec.gob.inec.geografia.ejb.entidades.GeoDpa;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author lponce
 */
@Remote
public interface GeoDpaServicioRemote {

    public List<GeoDpa> listarPorCampos(Map<String, Object> campos, String orden) throws Exception;

    public List<GeoDpa> listarPorCampos(List<Object[]> listaWhere, String orden) throws Exception;

    public List<GeoDpa> listarporPoligonoID(String polygonGeoJSON, String ids) throws Exception;

    public List<Object[]> listarporPoligonoEscala(String polygonGeoJSON, String escala) throws Exception;

    public GeoDpa insertar(GeoDpa dpa) throws Exception;

    public GeoDpa actualizar(GeoDpa dpa) throws Exception;

    public GeoDpa buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public Object obtenerCentroide(String dpa, int cobertura) throws Exception;
}
