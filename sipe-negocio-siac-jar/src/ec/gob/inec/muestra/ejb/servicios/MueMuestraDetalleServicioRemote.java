/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.muestra.ejb.servicios;

import ec.gob.inec.distribuciontrabajo.ejb.entidades.DisEquipoTrabajoDetalle;
import ec.gob.inec.muestra.ejb.entidades.MueMuestra;
import ec.gob.inec.muestra.ejb.entidades.MueMuestraDetalle;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author vespinoza
 */
@Remote
public interface MueMuestraDetalleServicioRemote {

    public String crearMueDetalle(MueMuestraDetalle mueDetalle) throws Exception;

    public String editarMueDetalle(MueMuestraDetalle mueDeatlle) throws Exception;

    public List<MueMuestraDetalle> listarTodo() throws Exception;

    public List<MueMuestraDetalle> listarXMuestra(MueMuestra codMue) throws Exception;

    public List<MueMuestraDetalle> listarMuestraXEquipoDet(DisEquipoTrabajoDetalle codEquiDet) throws Exception;

    public List<MueMuestraDetalle> listarMuestraDisponible(MueMuestra codMue) throws Exception;

    public MueMuestraDetalle buscarMuestraDetActiva(Integer codMueDet) throws Exception;

    public List<MueMuestraDetalle> listarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public MueMuestraDetalle buscarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public String modificarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public int contarEjecutarConsulta(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public String eliminarDetalleMuestra(String codMue) throws Exception;

    public MueMuestraDetalle buscarCodMuestra(MueMuestra codMue, String idDpa) throws Exception;

    public List<Object[]> listarDetalle() throws Exception;

    public List<Object[]> listarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public Object[] buscarEjecutarConsultaObj(String nombreProdecimiento, List<Object> parametrosOrdenados) throws Exception;

    public List<MueMuestraDetalle> listarMuestraPorJornada(Integer codMue, String jornada) throws Exception;

    public MueMuestraDetalle buscarMueDet(Integer mueDet) throws Exception;

    public List<MueMuestraDetalle> listarMuestraPorJornadaActivos(Integer codMue, String jornada) throws Exception;

    public MueMuestraDetalle buscarMueDetXIdentificador(String identificador) throws Exception;

    public List<MueMuestraDetalle> buscarPorIdentificadorMuestra(String campo) throws Exception;

}
