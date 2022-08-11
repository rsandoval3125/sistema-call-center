/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.modulos;

import ec.gob.inec.captura.clases.CaptSeccionH;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptObservacion;
import java.util.List;

/**
 *
 * @author dguano
 */
public interface MetodosCaptura {
    
    
    
   public void inicializar();

    public void obtenerFormulario();

    public void cargarInformacionVDeCabeceraYElemento(CaptCabecera cab, Integer numElemento);
    public void cargarSeccionesHEnPagina(List<CaptSeccionH> secciones);
   
    public List<CaptSeccionH>  cargarListaSeccionesHParaGuardado();
    public void guardarElemento();
    
    public void guardarCabecerayCargaControl() ;
    

    
    public void cambiarDeElemento();

    public void regresarAInicio();
}
