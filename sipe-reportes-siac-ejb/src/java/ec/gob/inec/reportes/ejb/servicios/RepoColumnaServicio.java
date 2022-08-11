/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.ejb.servicios;

import ec.gob.inec.reportes.ejb.entidades.RepoColumna;
import ec.gob.inec.reportes.ejb.entidades.RepoSubreporte;
import ec.gob.inec.reportes.ejb.facade.RepoColumnaFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jaraujo
 */
@Stateless
public class RepoColumnaServicio implements RepoColumnaServicioRemote, RepoColumnaServicioLocal {

    @EJB
    private RepoColumnaFacade repoColumnaFacade;
    private String ENTIDAD_repoColumnaFacade = "RepoColumna";

    @Override
    public String crearColumna(RepoColumna repoColumna) throws Exception {
        repoColumnaFacade.crear(repoColumna);
        return "se ha creado la Columna";
    }

    @Override
    public String editarColumna(RepoColumna repoColumna) throws Exception {
        repoColumnaFacade.editar(repoColumna);
        return "se ha modificado la Columna";
    }

    @Override
    public String eliminarColumna(RepoColumna repoColumna) throws Exception {
        repoColumnaFacade.eliminar(repoColumna);
        return "se ha eliminado la Columna";
    }

    @Override
    public List<RepoColumna> listarTodo() throws Exception {
        return repoColumnaFacade.listarOrdenada(ENTIDAD_repoColumnaFacade, "idColumna", "asc");
    }

    @Override
    public List<RepoColumna> listarColumnasPorSubReporte(RepoSubreporte codSubr) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("codSubr", codSubr);
        return repoColumnaFacade.listarPorCampos(ENTIDAD_repoColumnaFacade, campos, "orden");
    }
}
