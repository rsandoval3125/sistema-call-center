/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.proceso.ejb.servicios;

import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.proceso.ejb.entidades.ProBaseConsolidadaInicial;
import ec.gob.inec.proceso.ejb.entidades.ProIndicador;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mpaucar
 */
@Remote
public interface ProBaseConsolidadaInicialServicioRemote {
    public String crearBaseConsolidadaInicialServicioRemote(ProBaseConsolidadaInicial baseConsolidada) throws Exception;
    public String editarBaseConsolidadaInicialServicioRemote(ProBaseConsolidadaInicial baseConsolidada) throws Exception;
    public String eliminarBaseConsolidadaInicialServicioRemote(Integer idBaseConsolidada) throws Exception;
    public ProBaseConsolidadaInicial buscarBaseConsolidadaPorID (Integer idBaseConsolidada) throws Exception; 
    public List <ProBaseConsolidadaInicial> ListaPorAnio(Integer idAnio) throws Exception;
    public List <ProBaseConsolidadaInicial> ListaPorAnio01(MetCatalogo codAnio) throws Exception;
    public boolean existeIdAnioIdZonalIdEstructuraIdPartidaIdFuenteDeFinanciamiento(Integer idAnio, Integer idZonal, Integer idEstructura, Integer idPartida, Integer idFuenteDeFinanciamiento) throws Exception;
    public boolean existePorAnioYEstructura(MetCatalogo codAnio, MetCatalogo codEstructura)throws Exception;
    public boolean existePorAnioYFueneteFinanciamiento(MetCatalogo codAnio, MetCatalogo codFueneteFinanciamiento)throws Exception;
    public boolean existePorAnioYPartida(MetCatalogo codAnio, MetCatalogo codPartida)throws Exception;
    public boolean existePorAnioYZonal(MetCatalogo codAnio, MetCatalogo codZonal)throws Exception;
    public long cuentaCodAnioCodZonalCodEstructuraCodPartidaCodFuenteFinanciamiento(MetCatalogo codAnio, MetCatalogo codZonal, MetCatalogo codEstructura, MetCatalogo codPartida, MetCatalogo codFueneteFinanciamiento)throws Exception;
    public boolean existePorIdZonalIdEstructuraIdPartidaIdFuenteDeFinanciaminetCodAnio(String idZonal, String idEstructura, String idPartida, String idFueneteFinanciamiento, MetCatalogo idAnio) throws Exception;
    public ProBaseConsolidadaInicial buscarPorIdZonalIdEstructuraIdPartidaIdFuenteDeFinanciaminetCodAnio (String idZonal, String idEstructura, String idPartida, String idFueneteFinanciamiento, MetCatalogo idAnio) throws Exception;
            
}
