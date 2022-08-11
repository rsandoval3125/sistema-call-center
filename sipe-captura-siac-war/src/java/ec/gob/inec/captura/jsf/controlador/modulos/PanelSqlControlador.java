/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.jsf.controlador.modulos;

import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author david
 */
//@Named(value = "panelSqlControlador")
@ManagedBean
@ViewScoped
public class PanelSqlControlador implements Serializable {

    //LOGGER
    private static final Logger LOGGER = Logger.getLogger(PanelSqlControlador.class.getName());

    private String tipoAccion;
    private String sqlAEjecutar;
    private String resultadoEjecutar;
    private List<Object[]> resultadoConsulta;
    private List<ColumnModel> columns;
    private String nombreArchivoResultado;
    private int numCols;
    private Integer progresoConstruccion;

    @ManagedProperty(value = "#{baseControlador}")
    private BaseControlador bc;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmm");

   

    /**
     *
     * Creates a new instance of PanelSqlControlador
     *
     */
    public PanelSqlControlador() {

    }

    @PostConstruct
    public void inicializar() {
        try {

            tipoAccion = "S";
            sqlAEjecutar = "";
            resultadoEjecutar = "";
            resultadoConsulta = new ArrayList<>();
            columns = new ArrayList<>();
            nombreArchivoResultado = "";

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void runSQL() {
        try {
            progresoConstruccion = 0;
            resultadoConsulta = new ArrayList<>();
            resultadoEjecutar = "";
            columns = new ArrayList<>();
            if (tipoAccion.equals("S") && numCols > 0) {
                if (!sqlAEjecutar.isEmpty()) {
                    resultadoConsulta = bc.getRepoReporteServicioRemote().listarRegistrosSelect(sqlAEjecutar);
                    if (!resultadoConsulta.isEmpty()) {
                        if (resultadoConsulta.size() <= 100 && numCols <= 25) {
                            columns = new ArrayList<>();
                            for (int i = 0; i < numCols; i++) {
                                columns.add(new ColumnModel(i, "c" + i, 50));
                            }
                        } else {
                            construirNombreArchivo();
                            crearTextDelimTabconBase(resultadoConsulta);
                            resultadoConsulta = new ArrayList<>();
                        }
                        bc.addSuccessMessage("Consulta generada correctamente");
                    } else {
                        bc.addWarningMessage("No hay datos que mostrar.");
                    }
                } else {
                    bc.addErrorMessage("Parámetros no válidos");
                }

            } else if (tipoAccion.equals("E")) {
                if (!sqlAEjecutar.isEmpty()) {
                    resultadoEjecutar = bc.getRepoReporteServicioRemote().ejecutarSentencia(sqlAEjecutar);
                    if (resultadoEjecutar.equals("OK")) {
                        bc.addSuccessMessage("Sentencia ejecutada correctamente");
                    } else {
                        bc.addWarningMessage("Problemas con la ejecución:" + resultadoEjecutar);
                    }
                } else {
                    bc.addErrorMessage("Parámetros no válidos");
                }
            } else {
                bc.addErrorMessage("Parámetros no válidos");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String s = errors.toString();
            bc.addErrorMessage(s);
        }

    }

    public void crearTextDelimTabconBase(List<Object[]> listaConsulta) {

        try {
            Integer intervalo = (listaConsulta.size() / 100) + 1;
            // Write the output to a file
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            File archivoDatos = new File(request.getSession().getServletContext().getRealPath("/archivos/" + nombreArchivoResultado + ".txt"));
            Writer ftxt = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(archivoDatos), "UTF-8"));
            for (int i = 0; i < listaConsulta.size(); i++) {
                String fila = "";
                // Creating cells
                for (int j = 0; j < numCols; j++) {
                    if (j == 0) {
                        Object o = listaConsulta.get(i)[j];
                        if (o == null) {
                            o = "";
                        }

                        if (o instanceof String) {
                            fila = (String) o;
                        }

                        if (o instanceof Integer) {
                            fila = "" + (Integer) o;
                        }

                        if (o instanceof Boolean) {
                            fila = "" + (Boolean) o;
                        }

                        if (o instanceof Date) {
                            fila = "" + (Date) o;
                        }

                        if (o instanceof BigDecimal) {
                            fila = "" + (BigDecimal) o;
                        }

                    } else {
                        Object o = listaConsulta.get(i)[j];
                        if (o == null) {
                            o = "";
                        }

                        if (o instanceof String) {
                            fila = fila + "\t" + (String) o;
                        }

                        if (o instanceof Integer) {
                            fila = fila + "\t" + (Integer) o;
                        }

                        if (o instanceof Boolean) {
                            fila = fila + "\t" + (Boolean) o;
                        }

                        if (o instanceof Date) {
                            fila = fila + "\t" + (Date) o;
                        }

                        if (o instanceof BigDecimal) {
                            fila = fila + "\t" + (BigDecimal) o;
                        }

                    }

                }

                fila = fila + "\n";
                ftxt.write(fila);
                progresoConstruccion = i / intervalo;

               
            }

            ftxt.close();
            System.out.println(nombreArchivoResultado);

        } catch (Exception ex) {

            LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getSqlAEjecutar() {
        return sqlAEjecutar;
    }

    public void setSqlAEjecutar(String sqlAEjecutar) {
        this.sqlAEjecutar = sqlAEjecutar;
    }

    public String getResultadoEjecutar() {
        return resultadoEjecutar;
    }

    public void setResultadoEjecutar(String resultadoEjecutar) {
        this.resultadoEjecutar = resultadoEjecutar;
    }

    public List<Object[]> getResultadoConsulta() {
        return resultadoConsulta;
    }

    public void setResultadoConsulta(List<Object[]> resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }

    public String getNombreArchivoResultado() {
        return nombreArchivoResultado;
    }

    public void setNombreArchivoResultado(String nombreArchivoResultado) {
        this.nombreArchivoResultado = nombreArchivoResultado;
    }

    public void setProgresoConstruccion(Integer progresoConstruccion) {
        this.progresoConstruccion = progresoConstruccion;
    }

    public Integer getProgresoConstruccion() {
        return progresoConstruccion;
    }

    public void construirNombreArchivo() {
        try {
            Date fa = new Date();
            nombreArchivoResultado = "result_infocapt" + "_" + sdf.format(fa);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    //Clase auxiliar contiene las columnas del data table dinamico
    static public class ColumnModel implements Serializable {
        private int indice;
        private String valor;
        private int ancho;

        public ColumnModel(int indice, String valor, int ancho) {
            this.indice = indice;
            this.valor = valor;
            this.ancho = ancho;
        }

        public String getValor() {
            return valor;
        }

        public int getIndice() {
            return indice;
        }

        public void setIndice(int indice) {
            this.indice = indice;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        public int getAncho() {
            return ancho;
        }

        public void setAncho(int ancho) {
            this.ancho = ancho;
        }
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public BaseControlador getBc() {
        return bc;
    }

    public void setBc(BaseControlador bc) {
        this.bc = bc;
    }

}
