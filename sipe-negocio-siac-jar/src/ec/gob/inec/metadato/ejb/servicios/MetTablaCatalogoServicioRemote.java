///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ec.gob.inec.metadato.ejb.servicios;
//
//import ec.gob.inec.metadato.ejb.entidades.MetTablaCatalogo;
//import java.util.List;
//import javax.ejb.Remote;
//
///**
// *
// * @author vespinoza
// */
//@Remote
//public interface MetTablaCatalogoServicioRemote {
//
//    public String crearTablaCatalogo(MetTablaCatalogo admTablaCatalogo) throws Exception;
//
//    public String editarTablaCatalogo(MetTablaCatalogo admTablaCatalogo) throws Exception;
//
//    public String eliminarTablaCatalogo(MetTablaCatalogo admTablaCatalogo) throws Exception;
//
//    public List<MetTablaCatalogo> listarTodo() throws Exception;
//
//    public List<MetTablaCatalogo> listarTipoCatPorSecciones(List<Integer> codigosSeccion_) throws Exception;
//
//    public String existeDuplicidadDeAliasTablaCatalogo() throws Exception;
//
//    public MetTablaCatalogo consultaTablaCatalogoPorAlias(String alias) throws Exception;
//    
//    public List<MetTablaCatalogo> listarTodosActivos() throws Exception;
//    
//    public Boolean existeTablaCatalogoXAlias(String alias) throws Exception;
//}
