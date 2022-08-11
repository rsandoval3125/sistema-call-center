/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.modulos;

import ec.gob.inec.captura.clases.CaptElementoControl;
import ec.gob.inec.captura.clases.CaptFilaH;
import ec.gob.inec.captura.clases.CaptSeccionH;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptCabecera;
import ec.gob.inec.captura.ejb.entidades.CaptCargaControl;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.ejb.entidades.CaptEstado;
import ec.gob.inec.captura.ejb.entidades.CaptObservacion;
import ec.gob.inec.metadato.ejb.entidades.MetSeccion;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author dguano
 */
@ManagedBean
@ViewScoped
public class BaseCapturaControlador {

    private static final Logger LOGGER = Logger.getLogger(BaseCapturaControlador.class.getName());

    @ManagedProperty("#{baseControlador}")
    private BaseControlador bc;

    /**
     * Creates a new instance of GeneracionFormulario
     */
    public BaseCapturaControlador() {
    }

    @PostConstruct
    public void inicializar() {
        try {

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarCabecerayCargaControl(CaptCabecera cab, CaptCargaControl cc) {
        try {
            bc.getCaptCabeceraServicioRemote().editarFormCabecera(cab);
            bc.getCaptCargaControlServicioRemote().editarCargaControl(cc);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardarCabecera(CaptCabecera cab) {
        try {
            bc.getCaptCabeceraServicioRemote().editarFormCabecera(cab);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarDetallesVTodoElementoActual(List<CaptVarV> listaDetallesV) {
        try {
            bc.getCaptDetalleVServicioRemote().actualizarDetallesVDeFormularioPorElemento(listaDetallesV);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public List<CaptVarV> obtenerInformacionVDeElemento(int idCab, int elementoSolicitado, int idFormulario) {
       List<CaptVarV> listaDetallesV = new ArrayList<>();
        try {
            listaDetallesV = bc.getCaptDetalleVServicioRemote().listarVariablesVPorCabeceraCreadayNumElemento(idCab, elementoSolicitado, idFormulario);
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return listaDetallesV;
    }
    public List<CaptSeccionH> obtenerInformacionHDeElemento( int idCab, int elementoSolicitado, int idFormulario) {
        List<CaptSeccionH> listaSeccionesH = new ArrayList<>();
        try {
            List<CaptFilaH> filasH = bc.getCaptDetalleHServicioRemote().listarFilasHPorCabeceraCreadayElemento(idCab, elementoSolicitado);
            if (!filasH.isEmpty()) {
                listaSeccionesH = bc.getCaptDetalleHServicioRemote().listarSeccionesHPorListaFilasFormulario(filasH);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return listaSeccionesH;
    }

    public void guardarDetallesVParcialmenteElementoActual(List<CaptVarV> listaDetallesV, int indiceMin, int indiceMax) {
        try {
            bc.getCaptDetalleVServicioRemote().actualizarDetallesVDeFormularioPorElementoParcial(listaDetallesV, indiceMin, indiceMax);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarElemento(CaptCabecera cab, int numeroElementoAEliminar) {
        try {
           bc.getCaptDetalleVServicioRemote().eliminarDetallesVDeFormularioPorElemento(cab.getIdCab(), numeroElementoAEliminar);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void agregarElementoVFormulario(CaptCabecera cab, int posicionNuevoElemento) {
        try {
          //la lista parametro listaVarsV debe venir de cache, por el momento se realiza la consulta en cada creacion
                
            List<CaptVarV> listaVarsV = this.listarVariablesVPorFormulario(cab.getCodCarCon().getCodFormulario().getIdFormulario());
                
            bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormularioPorElemento(cab.getCodCarCon().getCodFormulario().getTipo(), cab.getIdCab(), listaVarsV, posicionNuevoElemento);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este método debe ser programado y llamado en cache por cada formulario
     *
     * @return
     */
    private List<CaptVarV> listarVariablesVPorFormulario(Integer idFormulario) {
        List<CaptVarV> vars = new ArrayList<>();
        try {
            vars = bc.getCaptDetalleVServicioRemote().listarVariablesVPorFormulario(idFormulario);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return vars;
    }

    public void guardarTodasSeccionesHElementoActual(List<CaptSeccionH> listaSeccionesH) {
        try {
            if(!listaSeccionesH.isEmpty()){
                 bc.getCaptDetalleHServicioRemote().actualizarDetallesHDeFormulario(listaSeccionesH);
            }
           
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarSeccionHElementoActual(CaptSeccionH seccionH) {
        try {
            bc.getCaptDetalleHServicioRemote().actualizarDetallesHDeFormularioPorSeccion(seccionH);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearFilaDetallesHDElementoActual(CaptSeccionH seccionHD, String tipoFormulario) {
        try {
            int codCab = seccionHD.getFilas().get(0).getCodCab();
            int numEle = seccionHD.getFilas().get(0).getNumDet();
            int codSeccion = seccionHD.getFilas().get(0).getCodSeccion();
            int ultOrden = seccionHD.getFilas().size();

            CaptDetalleH cdh = new CaptDetalleH();
            cdh.setCodCab(new CaptCabecera(codCab));
            cdh.setCodSeccion(new MetSeccion(codSeccion));
            cdh.setNumDet(numEle);
            cdh.setOrden(ultOrden + 1);

            bc.getCaptDetalleHServicioRemote().crearDetalleH(cdh);
            //programar listar Filas de Seccion HD
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarFilaDetallesHDElementoActual(CaptSeccionH seccionHD, int idxAEliminar) {
        try {
            bc.getCaptDetalleHServicioRemote().eliminarDetalleH(new CaptDetalleH(seccionHD.getFilas().get(idxAEliminar).getIdDetH()));
            //programar listar Filas de Seccion HD
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarElementosControldeFormulario(List<CaptElementoControl> listaElementosControl, int idCab) {
        try {

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearEstadoDeNuevoElementoDeFormulario(CaptCabecera cab,int numNuevoElemento) {
        try {
            CaptEstado estado=new CaptEstado();
            estado.setCodCab(cab);
            estado.setNumDet(numNuevoElemento);
            estado.setFechahoraEdicion(new Date());
            estado.setEstado("C");
            bc.getCaptEstadoServicioRemote().crearCaptEstado(estado);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public List<CaptEstado>  listarEstadosFormulario( int idCab) {
       List<CaptEstado>  lst=new ArrayList<>();
        try {
            lst = bc.getCaptEstadoServicioRemote().listarEstadosPorCabecera(idCab);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    public void crearObservacionDeFormulario(CaptObservacion obs) {
        try {
            bc.getCaptObservacionServicioRemote().crearCaptObservacion(obs);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void editarObservacionDeFormulario(CaptObservacion obs) {
        try {
            bc.getCaptObservacionServicioRemote().editarCaptObservacion(obs);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarObservacionDeFormulario(CaptObservacion obs) {
        try {
            bc.getCaptObservacionServicioRemote().eliminarCaptObservacion(obs);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarObservacionesFormulario(List<CaptObservacion> listaObservaciones, int idCab) {
        try {
            listaObservaciones = bc.getCaptObservacionServicioRemote().listarObservacionesPorCabecera(idCab);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int calcularEdadAnios(String fecha_nac, String fecha_visita) {     //fecha_nac debe tener el formato dd/MM/yyyy
        Date fechaActual = new Date();
        //SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String hoy = fecha_visita;
        String[] dat1 = fecha_nac.split("/");
        String[] dat2 = hoy.split("/");
        int anos = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
        int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
        int dia = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
        if (mes < 0 // Aún no es el mes de su cumpleaños
 
                || (mes == 0 && dia < 0)) { // o es el mes pero no ha llegado el día.
 
            anos--;
 
        }
 
        return anos;
    }


    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }
    
    public void agregarElementoVFormularioConCalidad(CaptCabecera cab, int posicionNuevoElemento) {
        try {
          //la lista parametro listaVarsV debe venir de cache, por el momento se realiza la consulta en cada creacion
                
            List<CaptVarV> listaVarsV = this.listarVariablesVPorFormulario(cab.getCodFormulario().getIdFormulario());
                
            bc.getCaptDetalleVServicioRemote().crearDetallesVDeFormularioPorElemento(cab.getCodFormulario().getTipo(), cab.getIdCab(), listaVarsV, posicionNuevoElemento);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
