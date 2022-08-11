/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.proceso.ejb.entidades.ProSeguimiento;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author dguano
 */
@Remote
public interface ProSeguimientoServicioRemote {

    public String crearMpeSeguimiento(ProSeguimiento seguim) throws Exception;

    public String editarMpeSeguimiento(ProSeguimiento seguim) throws Exception;

    public String eliminarMpeSeguimiento(Integer codseguim) throws Exception;

    public List<ProSeguimiento> listarMpeSeguimientoPorOperativo(Integer idOperativo) throws Exception;

    public ProSeguimiento buscarseguimPorId(Integer codseguim) throws Exception;

    public int contarMpeSeguimientoPorOperativo(Integer idOperativo) throws Exception;

    public List<ProSeguimiento> listarMpeSeguimientoPorSubproceso(Integer idSubp) throws Exception;

    public BigDecimal sumarAvanceCalculadoDelSubProceso(Integer idSubp) throws Exception;

    public BigDecimal sumarAvanceCalculadoDelaFase(Integer idFase) throws Exception;

    public BigDecimal sumarAvanceCalculadoDelOperativo(Integer idOpe) throws Exception;

    public ProSeguimiento buscarSeguimientoPorActividad(Integer idAct) throws Exception;

    public List<Object[]> listarActividadesConRetraso() throws Exception;

    public String obtenerFilaHtmlDeObjetoRetraso(Object[] o) throws Exception;

    public void enviarCorreoDeProgramacionPorOperativo(Integer opeid, String IP_SMTP, String EMAIL_FROM, String NAME_FROM, String URL_GPE) throws Exception;

    public String obtenerFilaHtmlDeObjetoProgramado(Object[] o) throws Exception;

    public String obtenerValorParametroGlobal(String nombre) throws Exception;
    
    public void ejecutarQueryNativo(String sql) throws Exception;
    
    public ProSeguimiento SeguimientoPorIdActividad(Integer idActividad) throws Exception;
}
