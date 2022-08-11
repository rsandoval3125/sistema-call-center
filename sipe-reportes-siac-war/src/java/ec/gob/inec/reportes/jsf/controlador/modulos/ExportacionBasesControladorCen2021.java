/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.reportes.jsf.controlador.modulos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ec.gob.inec.administracion.ejb.entidades.AdmPeriodo;
import ec.gob.inec.metadato.ejb.entidades.MetCatalogo;
import ec.gob.inec.presentacion.controlador.base.BaseControlador;
import ec.gob.inec.presentacion.controlador.base.UsuarioControlador;
import ec.gob.inec.seguridad.ejb.entidades.SegRol;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rpua
 */
@ManagedBean(name = "ebcCenso2021")
@ViewScoped
public class ExportacionBasesControladorCen2021 {
    
    private static final Logger LOGGER = Logger.getLogger(ExportacionBasesControladorCen2021.class.getName());

    private String codZonal1;
    private List<SelectItem> listaZonales;
    private List<SelectItem> listaperiodos;
    private int idBase;
    private String nombreBase;
    private List<SelectItem> listaNombresBases;
    private List<BaseParaExportar> listaBases;
    private int periodo;
    private String per;
    private String tipoFiltro;
    private String tipoArchivoDescarga;
    private List<String> nombreColumnas;
    public List<Object[]> listaResultado;
    public List<Object[]> listaTemp;
    private String nombreArchivoDescarga;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmm");
    private List<ColumnModel> columns;
    @ManagedProperty(value = "#{baseControlador}")
    private BaseControlador bc;
    @ManagedProperty("#{usuarioControlador}")
    private UsuarioControlador uc;

    private boolean puedeDescargarBaseExcel;
    private boolean puedeDescargarBaseTexto;
    private Integer progresoConstruccion;
    /**
     * Creates a new instance of ExportacionBasesControladorEn2102
     */
    public ExportacionBasesControladorCen2021() {
    }
    
    //Clase auxiliar contiene las columnas del data table dinamico
    static public class BaseParaExportar implements Serializable {

        private int idBase;
        private String nombre;
        private String nombreVista;
        private int numCols;

        public BaseParaExportar(int idBase, String nombre, String nombreVista, int numCols) {
            this.idBase = idBase;
            this.nombre = nombre;
            this.nombreVista = nombreVista;
            this.numCols = numCols;
        }

        public int getIdBase() {
            return idBase;
        }

        public void setIdBase(int idBase) {
            this.idBase = idBase;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombreVista() {
            return nombreVista;
        }

        public void setNombreVista(String nombreVista) {
            this.nombreVista = nombreVista;
        }

        public int getNumCols() {
            return numCols;
        }

        public void setNumCols(int numCols) {
            this.numCols = numCols;
        }

    }

    @PostConstruct
    public void inicializar() {
        try {
            codZonal1 = "0";
            per = "0";
            //programacion temporal, en su momento debe funcionar para otros roles.
            String nombreRol = "COORD_CENSO";
            /*if (uc.usuarioTieneRol(nombreRol)) {
                //programacion para obtener codigo zonal desde un texto en campo titulo de personal
                System.out.println("Nombre titulo:" + uc.getUsuarioActual().getCodPersonal().getNombreTitulo());
                String tit = uc.getUsuarioActual().getCodPersonal().getNombreTitulo();
                if (tit != null && tit.contains("CZ")) {
                    System.out.println("Entra a condicion de zonales");
                    String[] parts = tit.split("CZ");
                    codZonal1 = parts[1];
                } else {
                    codZonal1 = "9";
                }
            } else {
                nombreRol = "";
            }*/

            //PROGRAMACION TEMPORAL SOLO PARA BORRAR SALTOS EN OBSERVACIONES ENREFAM
            /*String sqlLimpiarObs1 = "UPDATE CAPTURA.CAPT_CABECERA  SET OBS1=replace(OBS1,chr(13), '' ) WHERE OBS1 LIKE '%'||chr(13)||'%';UPDATE CAPTURA.CAPT_CABECERA  SET OBS1=replace(OBS1,chr(10), '' ) WHERE OBS1 LIKE '%'||chr(10)||'%';UPDATE CAPTURA.CAPT_CABECERA  SET OBS1=replace(OBS1,chr(9), '' ) WHERE OBS1 LIKE '%'||chr(9)||'%';";
            bc.getRepoReporteServicioRemote().ejecutarSentencia(sqlLimpiarObs1);*/
            System.out.println("BASES_" + nombreRol);
            List<MetCatalogo> listaBasesBD = bc.getMetCatalogoServicioRemote().listarCatalogoXAlias("BASES_" + nombreRol);
            if (!listaBasesBD.isEmpty()) {
                listaBases = new ArrayList<>();
                listaNombresBases = new ArrayList<>();
                for (MetCatalogo cat : listaBasesBD) {
                    BaseParaExportar bpe = new BaseParaExportar(cat.getIdCatalogo(), cat.getNombre(), cat.getDescripcion(), Integer.valueOf(cat.getValor()));
                    listaNombresBases.add(new SelectItem(bpe.idBase, bpe.nombre));
                    listaBases.add(bpe);
                }
            }
            puedeDescargarBaseExcel = false;
            puedeDescargarBaseTexto = false;
            tipoArchivoDescarga = "XLS";
            progresoConstruccion = 0;
            //codZonal1 = (String) bc.getSession().getAttribute("codZonal");
            //codZonal1="0";
            listaZonales = new ArrayList<SelectItem>();
            if (codZonal1.equals("0")) {
                listaZonales.add(new SelectItem("0", "Todas"));
                listaZonales.add(new SelectItem("3", "Centro"));
                listaZonales.add(new SelectItem("2", "Litoral"));
                listaZonales.add(new SelectItem("1", "Norte"));
                listaZonales.add(new SelectItem("4", "Sur"));
            } else {
                switch (Integer.valueOf(codZonal1)) {
                    case 3:
                        listaZonales.add(new SelectItem(codZonal1, "Centro"));
                        break;
                    case 2:
                        listaZonales.add(new SelectItem(codZonal1, "litoral"));
                        break;
                    case 1:
                        listaZonales.add(new SelectItem(codZonal1, "Norte"));
                        break;
                    case 4:
                        listaZonales.add(new SelectItem(codZonal1, "Sur"));
                        break;
                }

            }
            columns = new ArrayList<ColumnModel>();
            listaResultado = new ArrayList<Object[]>();
            consultarDatoGlobal();
            obtenerPeriodo1();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void consultarDatoGlobal() {
        try {
            /*dg = dgDao.buscarPorCampo("SgmDatoGlobal", "datoNemonico", "INFOCAPT_HORA_ULTIMA_CONSOLIDACION");
            if (dg == null) {
                dg = new SgmDatoGlobal();
            }*/
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarDatosCapturados() {
        try {
            /*List<Object[]> rslt = consultaDao.obtenerQueryNativo("SELECT infocapt.f_limpiar_datos_capturados();");
            if (!rslt.isEmpty()) {
                bc.addSuccessMessage("Datos limpiados correctamente");
            }*/
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void insertarDatosEnTablaTemporal() {
        try {
            /*List<Object[]> rslt = consultaDao.obtenerQueryNativo("SELECT infocapt.f_insertar_datosenemdu_tablatemp_"+anio+mes+"();");
            if (!rslt.isEmpty()) {
                bc.addSuccessMessage("Datos Consolidados correctamente. Puede obtener las bases.");
            }
            String fecha = sdf.format(java.util.Calendar.getInstance().getTime());
            consultaDao.ejecutarQueryNativo("update metadec.sgm_dato_global set dato_valor='" + fecha + "' where dato_nemonico='INFOCAPT_HORA_ULTIMA_CONSOLIDACION';");
            consultarDatoGlobal();*/
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerBase() {
        try {
            tipoArchivoDescarga = "TXT";//Se coloca por defecto
            progresoConstruccion = 0;
            puedeDescargarBaseExcel = false;
            puedeDescargarBaseTexto = false;
            listaResultado = new ArrayList<Object[]>();
            String nombreVista = "";
            int numCols = 0;
            for (BaseParaExportar bpe : listaBases) {
                if (idBase == bpe.getIdBase()) {
                    nombreVista = bpe.getNombreVista();
                    numCols = bpe.getNumCols();
                    nombreBase = bpe.getNombre().replace(" ", "_").toLowerCase();
                }
            }

            nombreColumnas = listarNombresColumnas(nombreVista); //error
            //System.out.println("valor " + nombreVista);
            numCols = nombreColumnas.size();
            if (!nombreVista.equals("") && numCols > 0) {
                List<Object[]> listaRes = new ArrayList<Object[]>();
                String condicion = obtenerCondicionPorZonal(codZonal1);
                String condicion2 = obtenerCondicionPorPeriodo(per);
                construirNombreArchivo();
                System.out.println("Sql "+"select * from " + nombreVista + " " + condicion+ " "+condicion2);
                listaRes = bc.getRepoReporteServicioRemote().listarRegistrosSelect("select * from " + nombreVista + " " + condicion+ " "+condicion2);
                //listaRes = bc.getCaptCargaControlEquipoServicioRemote().listarObjGenerico("select * from " + nombreVista + " " + condicion);
                if (!listaRes.isEmpty()) {
                    if (tipoArchivoDescarga.equals("XLS")) {
                        crearExcelconBase(listaRes, numCols, nombreColumnas);
                        Thread.sleep(3000);
                        puedeDescargarBaseExcel = true;
                    } else if (tipoArchivoDescarga.equals("TXT")) {
                        crearTextDelimTabconBase(listaRes, numCols, nombreColumnas);
                        Thread.sleep(3000);
                        puedeDescargarBaseTexto = true;
                    }

                    progresoConstruccion = 0;
                } else {
                    bc.addWarningMessage("No existen datos que exportar");
                }

                /*listaResultado = consultaDao.obtenerQueryNativo("SELECT * FROM infocapt.v_enemdu_m_paraspss_" + tipoBase + "_201801 " + condicion + ";");
                 construirNombreArchivo();
                 RequestContext requestContext = RequestContext.getCurrentInstance();
                 requestContext.execute("PF('dataTableResult').clearFilters();");*/
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private List<String> listarNombresColumnas(String nombreVista) {
        List<String> lista = new ArrayList<>();
        try {
            List<Object[]> listaRes = new ArrayList<Object[]>();
            String[] parts = nombreVista.split("\\.");
            String nombreSoloVista = nombreVista;
            if (parts.length == 2) {
                nombreSoloVista = parts[1];
            }
            String sql = "SELECT ordinal_position,column_name FROM information_schema.columns  WHERE table_name='" + nombreSoloVista + "' order by ordinal_position";
            System.out.println(sql);
            listaRes = bc.getRepoReporteServicioRemote().listarRegistrosSelect(sql);
            //listaRes = bc.getCaptCargaControlEquipoServicioRemote().listarObjGenerico(sql);

            if (!listaRes.isEmpty()) {
                for (Object[] o : listaRes) {
                    lista.add(String.valueOf(o[1]));
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public void crearTextDelimTabconBase(List<Object[]> listaConsulta, int numCols, List<String> nombresColumnas) {
        try {

            Integer intervalo = (listaConsulta.size() / 100) + 1;

            // Write the output to a file
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            File archivoDatos = new File(request.getSession().getServletContext().getRealPath("/archivos/" + nombreArchivoDescarga + ".txt"));

            Writer ftxt = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(archivoDatos), "UTF-8"));

            String filaCab = "";
            for (int y = 0; y < numCols; y++) {
                filaCab = filaCab + nombresColumnas.get(y) + "\t";
            }
            filaCab = filaCab + "\n";
            ftxt.write(filaCab);

            for (int i = 0; i < listaConsulta.size(); i++) {
                String fila = "";
                // Creating cells
                for (int j = 0; j < numCols; j++) {

                    if (j == 0) {
                        fila = (String) listaConsulta.get(i)[j];
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
                    }

                }
                fila = fila + "\n";
                ftxt.write(fila);
                progresoConstruccion = i / intervalo;

                //System.out.println("Fila:" + i);
            }
            /*StringBuilder sb = new StringBuilder();
            sb.append("Test String");

            File f = new File("d:\\test.zip");
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry e = new ZipEntry("mytext.txt");
            out.putNextEntry(e);

            byte[] data = sb.toString().getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();

            out.close();*/

            ftxt.close();
            System.out.println(nombreArchivoDescarga);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearExcelconBase(List<Object[]> listaConsulta, int numCols, List<String> nombresColumnas) {
        try {

            Integer intervalo = (listaConsulta.size() / 100) + 1;

            // Create a Workbook
            Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

            /* CreationHelper helps us create instances for various things like DataFormat, 
             Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Sheet
            Sheet sheet = workbook.createSheet(nombreBase);

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.RED.getIndex());
            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            // Create a first Row
            Row headerRow = sheet.createRow(0);
            // Creating cells of first row
            for (int y = 0; y < numCols; y++) {
                Cell cell = headerRow.createCell(y);
                cell.setCellValue(nombresColumnas.get(y));
                cell.setCellStyle(headerCellStyle);
            }

            // Create a Row
            for (int i = 0; i < listaConsulta.size(); i++) {
                Row row = sheet.createRow(i + 1);

                // Creating cells
                for (int j = 0; j < numCols; j++) {
                    Cell cell = row.createCell(j);
                    if (listaConsulta.get(i)[j] instanceof String) {
                        cell.setCellValue((String) listaConsulta.get(i)[j]);
                    }
                    if (listaConsulta.get(i)[j] instanceof Integer) {
                        cell.setCellValue((Integer) listaConsulta.get(i)[j]);
                    }
                }
                progresoConstruccion = i / intervalo;
                //System.out.println("Fila:" + i);
            }

            // Resize all columns to fit the content size
            for (int x = 0; x < numCols; x++) {
                sheet.autoSizeColumn(x);
            }

            // Write the output to a file
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            File archivoDatos = new File(request.getSession().getServletContext().getRealPath("/archivos/" + nombreArchivoDescarga + ".xlsx"));
            FileOutputStream fileOut = new FileOutputStream(archivoDatos);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println(nombreArchivoDescarga);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public String obtenerCondicionPorZonal(String codZonal) {
        String s = "";
        try {
            if (codZonal.equals("0")) {
                
                s = " where zonal in ('1','2','3','4')";

            } else {
                s = " where zonal in ('" + codZonal + "')";
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    public String obtenerCondicionPorPeriodo (String periodo) {
        String s = "";
        try {
            if (periodo.equals("0")) {

            } else {
                s = " and periodo in ('" + periodo + "')";
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    public void obtenerPeriodo1(){
        List<String> lista = new ArrayList<>();
        String sql;    
        try {
            List<Object[]> listaTemp2 = new ArrayList<Object[]>();
            sql = "SELECT id_periodo,nombre FROM administracion.adm_periodo where estado_logico= true and cod_fase in (SELECT id_fase FROM proceso.pro_fase where cod_operativo in (SELECT id_operativo FROM metadato.met_operativo where identificador ='ENEMDU_202102M') and descripcion = 'Procesamiento ' and estado_logico = true) order by id_periodo;";
            listaTemp2 = bc.getRepoReporteServicioRemote().listarRegistrosSelect(sql);
            //System.out.println("Sql existente:" + sql);
            //System.out.println("Tamaño de la lista:" + listaTemp2.size() + " vacia :" + listaTemp2.isEmpty());
            //System.out.println("Dato:" + listaTemp.get(0).toString());
            if (listaTemp2.isEmpty()) {
                listaperiodos = new ArrayList<SelectItem>();
                listaperiodos.add(new SelectItem("0", "Todos"));
            }else {
                listaperiodos = new ArrayList<SelectItem>();
                listaperiodos.add(new SelectItem("0", "Todos"));
                for (Object[] o : listaTemp2) {
                    lista.add(String.valueOf(o[1]));
                    listaperiodos.add(new SelectItem(String.valueOf(o[1]), String.valueOf(o[1])));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ExportacionBasesControladorCen2021.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerPeriodo(){
        String sql;
            
        try {
            sql = "SELECT nombre FROM administracion.adm_periodo where estado_logico= true and cod_fase in (SELECT id_fase FROM proceso.pro_fase where cod_operativo in (SELECT id_operativo FROM metadato.met_operativo where identificador ='ENEMDU_202102M') and descripcion = 'Levantamiento de información' and estado_logico = true) order by id_periodo;";
            listaTemp = bc.getMueModeloMuestraServicioRemote().listarObjGenerico(sql);
            //System.out.println("Sql existente:" + sql);
            //System.out.println("Tamaño de la lista:" + listaTemp.size() + " vacia :" + listaTemp.isEmpty());
            //System.out.println("Dato:" + listaTemp.get(0).toString());
            if (listaTemp.isEmpty()) {
                listaperiodos = new ArrayList<SelectItem>();
                listaperiodos.add(new SelectItem("0", "Todos"));
            }else {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(listaTemp); // converts to json
                //System.out.println(json);
                String cadena = json.replace("\n", "").replace("\"", "").replaceAll("[\\[\\](){}]", "").replace(" ", "");
                //System.out.println("cadena: "+cadena);
                List<String> myList = new ArrayList<String>(Arrays.asList(cadena.split(",")));
                
                listaperiodos = new ArrayList<SelectItem>();
                listaperiodos.add(new SelectItem("0", "Todos"));
                if(myList.size()>0){
                    for (int i = 0; i < myList.size(); i++) {
                         listaperiodos.add(new SelectItem(myList.get(i), myList.get(i)));
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ExportacionBasesControladorCen2021.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void construirNombreArchivo() {
        try {
            Date fa = new Date();
            nombreArchivoDescarga = "sipe_reportes_" + nombreBase + "_cz" + codZonal1 + "_" + sdf.format(fa);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    //Clase auxiliar contiene las columnas del data table dinamico
    static public class ColumnModel implements Serializable {

        private String header;
        private String valor;

        public ColumnModel(String header, String valor) {
            this.header = header;
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

    public List<Object[]> getListaResultado() {
        return listaResultado;
    }

    public void setListaResultado(List<Object[]> listaResultado) {
        this.listaResultado = listaResultado;
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

    public String getNombreArchivoDescarga() {
        return nombreArchivoDescarga;
    }

    public void setNombreArchivoDescarga(String nombreArchivoDescarga) {
        this.nombreArchivoDescarga = nombreArchivoDescarga;
    }

    public String getCodZonal1() {
        return codZonal1;
    }

    public void setCodZonal1(String codZonal1) {
        this.codZonal1 = codZonal1;
    }

    public List<SelectItem> getListaZonales() {
        return listaZonales;
    }

    public void setListaZonales(List<SelectItem> listaZonales) {
        this.listaZonales = listaZonales;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public boolean isPuedeDescargarBaseExcel() {
        return puedeDescargarBaseExcel;
    }

    public void setPuedeDescargarBaseExcel(boolean puedeDescargarBaseExcel) {
        this.puedeDescargarBaseExcel = puedeDescargarBaseExcel;
    }

    public Integer getProgresoConstruccionExcel() {
        return progresoConstruccion;
    }

    public void setProgresoConstruccionExcel(Integer progresoConstruccionExcel) {
        this.progresoConstruccion = progresoConstruccionExcel;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    public String getTipoArchivoDescarga() {
        return tipoArchivoDescarga;
    }

    public void setTipoArchivoDescarga(String tipoArchivoDescarga) {
        this.tipoArchivoDescarga = tipoArchivoDescarga;
    }

    public boolean isPuedeDescargarBaseTexto() {
        return puedeDescargarBaseTexto;
    }

    public void setPuedeDescargarBaseTexto(boolean puedeDescargarBaseTexto) {
        this.puedeDescargarBaseTexto = puedeDescargarBaseTexto;
    }

    public Integer getProgresoConstruccion() {
        return progresoConstruccion;
    }

    public void setProgresoConstruccion(Integer progresoConstruccion) {
        this.progresoConstruccion = progresoConstruccion;
    }

    public int getIdBase() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase = idBase;
    }

    public String getNombreBase() {
        return nombreBase;
    }

    public void setNombreBase(String nombreBase) {
        this.nombreBase = nombreBase;
    }

    public List<SelectItem> getListaNombresBases() {
        return listaNombresBases;
    }

    public void setListaNombresBases(List<SelectItem> listaNombresBases) {
        this.listaNombresBases = listaNombresBases;
    }

    public UsuarioControlador getUc() {
        return uc;
    }

    public void setUc(UsuarioControlador uc) {
        this.uc = uc;
    }

    public List<String> getNombreColumnas() {
        return nombreColumnas;
    }

    public void setNombreColumnas(List<String> nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }

    public List<Object[]> getListaTemp() {
        return listaTemp;
    }

    public void setListaTemp(List<Object[]> listaTemp) {
        this.listaTemp = listaTemp;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public List<SelectItem> getListaperiodos() {
        return listaperiodos;
    }

    public void setListaperiodos(List<SelectItem> listaperiodos) {
        this.listaperiodos = listaperiodos;
    }
}
