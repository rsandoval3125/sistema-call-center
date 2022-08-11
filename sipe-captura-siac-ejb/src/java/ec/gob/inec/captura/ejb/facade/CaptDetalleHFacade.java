/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.clases.CaptFilaH;
import ec.gob.inec.captura.clases.CaptSeccionH;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleH;
import ec.gob.inec.captura.ejb.entidades.OperadorAsignacion;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jcerda
 */
@Stateless
public class CaptDetalleHFacade extends AbstractFacade<CaptDetalleH> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptDetalleHFacade() {
        super(CaptDetalleH.class);
    }

    private String insertsDetallesHDeFormularioPorNElementos(String tipoFormulario, Integer codCab, List<CaptFilaH> listaFilasH, Integer numElementos) {
        String sql1 = "";
        String sqlN = "";

        if (tipoFormulario.equals("ME")) {
            List<CaptFilaH> listaFilasNH = new ArrayList<>();

            for (int e = 1; e <= numElementos; e++) {
                String sqlE = "insert into captura.capt_detalle_h (cod_cab,cod_seccion,num_det,orden) values ";
                if (e == 1) {
                    //en el primer elemento se crean todas las variables incluidas en las secciones 1HD,1HF,NHD,NHF
                    for (int j = 0; j < listaFilasH.size(); j++) {
                        if (j < (listaFilasH.size() - 1)) {
                            sqlE = sqlE + "(" + codCab + "," + listaFilasH.get(j).getCodSeccion() + ",1," + listaFilasH.get(j).getOrden() + "),";
                        } else {
                            sqlE = sqlE + "(" + codCab + "," + listaFilasH.get(j).getCodSeccion() + ",1," + listaFilasH.get(j).getOrden() + ")";
                        }
                        //aprovechamos la iteracion para llenar la lista auxiliar
                        if (listaFilasH.get(j).getTipoSeccion().equals("NHF") || listaFilasH.get(j).getTipoSeccion().equals("NHD")) {
                            listaFilasNH.add(listaFilasH.get(j));
                        }
                    }
                } else {
                    //a partir del segundo elemento se crean solo las variables incluidas en las secciones NHF Y NHD
                    for (int j = 0; j < listaFilasNH.size(); j++) {
                        if (j < (listaFilasNH.size() - 1)) {
                            sqlE = sqlE + "(" + codCab + "," + listaFilasNH.get(j).getCodSeccion() + "," + e + "," + listaFilasNH.get(j).getOrden() + "),";
                        } else {
                            sqlE = sqlE + "(" + codCab + "," + listaFilasNH.get(j).getCodSeccion() + "," + e + "," + listaFilasNH.get(j).getOrden() + ")";
                        }

                    }
                }
                sqlN = sqlN + sqlE + ";";
            }
        } else if (tipoFormulario.equals("1E")) {
            sql1 = "insert into captura.capt_detalle_h (cod_cab,cod_seccion,num_det,orden) values ";
            for (int i = 0; i < listaFilasH.size(); i++) {
                if (i < (listaFilasH.size() - 1)) {
                    sql1 = sql1 + "(" + codCab + "," + listaFilasH.get(i).getCodSeccion() + ",1," + listaFilasH.get(i).getOrden() + "),";
                } else {
                    sql1 = sql1 + "(" + codCab + "," + listaFilasH.get(i).getCodSeccion() + ",1," + listaFilasH.get(i).getOrden() + ")";
                }
            }
            sql1 = sql1 + ";";
        }
        return sql1 + "" + sqlN;
    }

    private String insertsDetallesHDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptFilaH> listaFilasH, Integer numElementoNuevo) {
        String sql1 = "";
        String sqlN = "";

        if (tipoFormulario.equals("ME")) {
            List<CaptFilaH> listaFilasNH = new ArrayList<>();
            for (int x = 0; x < listaFilasH.size(); x++) {
                if (listaFilasH.get(x).getTipoSeccion().equals("NHF") || listaFilasH.get(x).getTipoSeccion().equals("NHD")) {
                    listaFilasNH.add(listaFilasH.get(x));
                }
            }
            String sqlE = "insert into captura.capt_detalle_h (cod_cab,cod_seccion,num_det,orden) values ";
            if (numElementoNuevo == 1) {
                //en el primer elemento se crean todas las variables incluidas en las secciones 1V,NV,SV
                for (int j = 0; j < listaFilasH.size(); j++) {
                    if (j < (listaFilasH.size() - 1)) {
                        sqlE = sqlE + "(" + codCab + "," + listaFilasH.get(j).getCodSeccion() + ",1," + listaFilasH.get(j).getOrden() + "),";
                    } else {
                        sqlE = sqlE + "(" + codCab + "," + listaFilasH.get(j).getCodSeccion() + ",1," + listaFilasH.get(j).getOrden() + ")";
                    }

                }
            } else {
                //a partir del segundo elemento se crean solo las variables incluidas en las secciones NHF Y NHD
                for (int j = 0; j < listaFilasNH.size(); j++) {
                    if (j < (listaFilasNH.size() - 1)) {
                        sqlE = sqlE + "(" + codCab + "," + listaFilasNH.get(j).getCodSeccion() + "," + numElementoNuevo + "," + listaFilasNH.get(j).getOrden() + "),";
                    } else {
                        sqlE = sqlE + "(" + codCab + "," + listaFilasNH.get(j).getCodSeccion() + "," + numElementoNuevo + "," + listaFilasNH.get(j).getOrden() + ")";
                    }

                }
            }
            sqlN = sqlE + ";";

        } else if (tipoFormulario.equals("1E")) {
            if (numElementoNuevo == 1) {
                sql1 = "insert into captura.capt_detalle_h (cod_cab,cod_seccion,num_det,orden) values ";
                for (int i = 0; i < listaFilasH.size(); i++) {
                    if (i < (listaFilasH.size() - 1)) {
                        sql1 = sql1 + "(" + codCab + "," + listaFilasH.get(0).getCodSeccion() + ",1," + listaFilasH.get(i).getOrden() + "),";
                    } else {
                        sql1 = sql1 + "(" + codCab + "," + listaFilasH.get(0).getCodSeccion() + ",1," + listaFilasH.get(i).getOrden() + ")";
                    }
                }
                sql1 = sql1 + ";";
            }
        }
        return sql1 + "" + sqlN;
    }

    private String updatesDetallesHDeFormularioPorSeccion(CaptSeccionH seccionH) {
        String sql = "";
        int i = seccionH.getColumnas();
        for (CaptFilaH f : seccionH.getFilas()) {

            String sqlF = "update captura.capt_detalle_h set ";
            if (i == 1) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01());
            } else if (i == 2) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02());
            } else if (i == 3) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03());
            } else if (i == 4) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04());
            } else if (i == 5) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05());
            } else if (i == 6) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06());
            } else if (i == 7) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07());
            } else if (i == 8) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08());
            } else if (i == 9) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09());
            } else if (i == 10) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10());
            } else if (i == 11) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11());
            } else if (i == 12) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12());
            } else if (i == 13) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13());
            } else if (i == 14) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14());
            } else if (i == 15) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15());
            } else if (i == 16) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16());
            } else if (i == 17) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17());
            } else if (i == 18) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18());
            } else if (i == 19) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19());
            } else if (i == 20) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20());
            } else if (i == 21) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21());
            } else if (i == 22) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22());
            } else if (i == 23) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23());
            } else if (i == 24) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24());
            } else if (i == 25) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24()) + "," + obtenerSetValorColumna(25, f.getVal25());
            } else if (i == 26) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24()) + "," + obtenerSetValorColumna(25, f.getVal25()) + "," + obtenerSetValorColumna(26, f.getVal26());
            } else if (i == 27) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24()) + "," + obtenerSetValorColumna(25, f.getVal25()) + "," + obtenerSetValorColumna(26, f.getVal26()) + "," + obtenerSetValorColumna(27, f.getVal27());
            } else if (i == 28) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24()) + "," + obtenerSetValorColumna(25, f.getVal25()) + "," + obtenerSetValorColumna(26, f.getVal26()) + "," + obtenerSetValorColumna(27, f.getVal27()) + "," + obtenerSetValorColumna(28, f.getVal28());
            } else if (i == 29) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24()) + "," + obtenerSetValorColumna(25, f.getVal25()) + "," + obtenerSetValorColumna(26, f.getVal26()) + "," + obtenerSetValorColumna(27, f.getVal27()) + "," + obtenerSetValorColumna(28, f.getVal28()) + "," + obtenerSetValorColumna(29, f.getVal29());
            } else if (i == 30) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24()) + "," + obtenerSetValorColumna(25, f.getVal25()) + "," + obtenerSetValorColumna(26, f.getVal26()) + "," + obtenerSetValorColumna(27, f.getVal27()) + "," + obtenerSetValorColumna(28, f.getVal28()) + "," + obtenerSetValorColumna(29, f.getVal29()) + "," + obtenerSetValorColumna(30, f.getVal30());
            }
            /*else if (i == 31) {
                sqlF = sqlF + obtenerSetValorColumna(1, f.getVal01()) + "," + obtenerSetValorColumna(2, f.getVal02()) + "," + obtenerSetValorColumna(3, f.getVal03()) + "," + obtenerSetValorColumna(4, f.getVal04()) + "," + obtenerSetValorColumna(5, f.getVal05()) + "," + obtenerSetValorColumna(6, f.getVal06()) + "," + obtenerSetValorColumna(7, f.getVal07()) + "," + obtenerSetValorColumna(8, f.getVal08()) + "," + obtenerSetValorColumna(9, f.getVal09()) + "," + obtenerSetValorColumna(10, f.getVal10()) + "," + obtenerSetValorColumna(11, f.getVal11()) + "," + obtenerSetValorColumna(12, f.getVal12()) + "," + obtenerSetValorColumna(13, f.getVal13()) + "," + obtenerSetValorColumna(14, f.getVal14()) + "," + obtenerSetValorColumna(15, f.getVal15()) + "," + obtenerSetValorColumna(16, f.getVal16()) + "," + obtenerSetValorColumna(17, f.getVal17()) + "," + obtenerSetValorColumna(18, f.getVal18()) + "," + obtenerSetValorColumna(19, f.getVal19()) + "," + obtenerSetValorColumna(20, f.getVal20()) + "," + obtenerSetValorColumna(21, f.getVal21()) + "," + obtenerSetValorColumna(22, f.getVal22()) + "," + obtenerSetValorColumna(23, f.getVal23()) + "," + obtenerSetValorColumna(24, f.getVal24()) + "," + obtenerSetValorColumna(25, f.getVal25()) + "," + obtenerSetValorColumna(26, f.getVal26()) + "," + obtenerSetValorColumna(27, f.getVal27()) + "," + obtenerSetValorColumna(28, f.getVal28()) + "," + obtenerSetValorColumna(29, f.getVal29()) + "," + obtenerSetValorColumna(30, f.getVal30())+ "," + obtenerSetValorColumna(31, f.getVal31());
            }*/

            sqlF = sqlF + " where id_deth=" + f.getIdDetH() + ";";
            sql = sql + sqlF;
        }
        return sql;
    }

    private String obtenerSetValorColumna(int columna, String valor) {
        String set = "";
        String val = (valor != null ? "$aesc6$" + valor + "$aesc6$ " : "null");
        if (columna < 10) {
            set = " val0" + columna + "=" + val;
        } else {
            set = " val" + columna + "=" + val;
        }
        return set;
    }

    public List<CaptFilaH> listarFilasHPorCabeceraCreadayElemento(int idCab, int numDet) throws Exception {
        List<CaptFilaH> rslt = new ArrayList<>();
        String sql = "SELECT id_deth, cod_cab, cod_seccion, num_det, d.orden, s.tipo,s.columnas,"
                + "val01, val02, val03, val04, val05, val06, val07, val08, val09, val10,"
                + "val11, val12, val13, val14, val15, val16, val17, val18, val19, val20,"
                + "val21, val22, val23, val24, val25, val26, val27, val28, val29, val30"
                + "  FROM captura.capt_detalle_h d, metadato.met_seccion s where d.cod_seccion=s.id_seccion and cod_cab=" + idCab + " and num_det=" + numDet + " order by cod_seccion,d.orden";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            for (Object[] ao : lo) {
                CaptFilaH f = new CaptFilaH(Integer.valueOf(String.valueOf(ao[0])),
                        Integer.valueOf(String.valueOf(ao[1])),
                        Integer.valueOf(String.valueOf(ao[2])),
                        Integer.valueOf(String.valueOf(ao[3])),
                        Integer.valueOf(String.valueOf(ao[4])),
                        String.valueOf(ao[5]),
                        Integer.valueOf(String.valueOf(ao[6])),
                        ao[7] != null ? String.valueOf(ao[7]) : null,
                        ao[8] != null ? String.valueOf(ao[8]) : null,
                        ao[9] != null ? String.valueOf(ao[9]) : null,
                        ao[10] != null ? String.valueOf(ao[10]) : null,
                        ao[11] != null ? String.valueOf(ao[11]) : null,
                        ao[12] != null ? String.valueOf(ao[12]) : null,
                        ao[13] != null ? String.valueOf(ao[13]) : null,
                        ao[14] != null ? String.valueOf(ao[14]) : null,
                        ao[15] != null ? String.valueOf(ao[15]) : null,
                        ao[16] != null ? String.valueOf(ao[16]) : null,
                        ao[17] != null ? String.valueOf(ao[17]) : null,
                        ao[18] != null ? String.valueOf(ao[18]) : null,
                        ao[19] != null ? String.valueOf(ao[19]) : null,
                        ao[20] != null ? String.valueOf(ao[20]) : null,
                        ao[21] != null ? String.valueOf(ao[21]) : null,
                        ao[22] != null ? String.valueOf(ao[22]) : null,
                        ao[23] != null ? String.valueOf(ao[23]) : null,
                        ao[24] != null ? String.valueOf(ao[24]) : null,
                        ao[25] != null ? String.valueOf(ao[25]) : null,
                        ao[26] != null ? String.valueOf(ao[26]) : null,
                        ao[27] != null ? String.valueOf(ao[27]) : null,
                        ao[28] != null ? String.valueOf(ao[28]) : null,
                        ao[29] != null ? String.valueOf(ao[29]) : null,
                        ao[30] != null ? String.valueOf(ao[30]) : null,
                        ao[31] != null ? String.valueOf(ao[31]) : null,
                        ao[32] != null ? String.valueOf(ao[32]) : null,
                        ao[33] != null ? String.valueOf(ao[33]) : null,
                        ao[34] != null ? String.valueOf(ao[34]) : null,
                        ao[35] != null ? String.valueOf(ao[35]) : null,
                        ao[36] != null ? String.valueOf(ao[36]) : null);
                rslt.add(f);
            }
        }
        return rslt;
    }

    /*este metodo debera ser llamado en cache*/
    public List<CaptFilaH> listarFilasHPorFormulario(int idFormulario) throws Exception {
        List<CaptFilaH> rslt = new ArrayList<>();
        String sql = "SELECT id_seccion,v_fila,tipo_seccion FROM metadato.v_filas_secciones_h where cod_formulario=" + idFormulario;
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            for (Object[] ao : lo) {
                CaptFilaH f = new CaptFilaH(Integer.valueOf(String.valueOf(ao[0])), Integer.valueOf(String.valueOf(ao[1])), String.valueOf(ao[2]));
                rslt.add(f);
            }
        }
        return rslt;
    }

    public void actualizarDetallesHDeFormularioPorSeccion(CaptSeccionH seccionH) throws Exception {
        String sql = updatesDetallesHDeFormularioPorSeccion(seccionH);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public void actualizarDetallesHDeFormulario(List<CaptSeccionH> listaSeccionH) throws Exception {
        String sql = "";
        for (int i = 0; i < listaSeccionH.size(); i++) {
            sql = sql + updatesDetallesHDeFormularioPorSeccion(listaSeccionH.get(i));
        }
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public void crearDetallesHDeFormulario(String tipoFormulario, Integer codCab, List<CaptFilaH> listaVariables, Integer numElementos) throws Exception {
        String sql = insertsDetallesHDeFormularioPorNElementos(tipoFormulario, codCab, listaVariables, numElementos);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public void crearDetallesHDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptFilaH> listaVariables, Integer numNuevoElemento) throws Exception {
        String sql = insertsDetallesHDeFormularioPorElemento(tipoFormulario, codCab, listaVariables, numNuevoElemento);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public List<CaptFilaH> listarRegistrosPorCabPrecenso(Integer codCab) throws Exception {
        List<CaptFilaH> rslt = new ArrayList<>();
        String sql = "SELECT id_deth, cod_cab, cod_seccion, num_det, d.orden, s.tipo,s.columnas,"
                + "val01, val02, val03, val04, val05, val06, val07, val08, val09, val10,"
                + "val11, val12, val13, val14, val15, val16, val17, val18, val19, val20,"
                + "val21, val22, val23, val24, val25, val26, val27, val28, val29, val30, val31"
                + "  FROM captura.capt_detalle_h d, metadato.met_seccion s where d.cod_seccion=s.id_seccion and cod_cab=" + codCab + " order by num_det,orden";

        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            for (Object[] ao : lo) {
                CaptFilaH f = new CaptFilaH(Integer.valueOf(String.valueOf(ao[0])),
                        Integer.valueOf(String.valueOf(ao[1])),
                        Integer.valueOf(String.valueOf(ao[2])),
                        Integer.valueOf(String.valueOf(ao[3])),
                        Integer.valueOf(String.valueOf(ao[4])),
                        String.valueOf(ao[5]),
                        30,
                        ao[7] != null ? String.valueOf(ao[7]) : null,
                        ao[8] != null ? String.valueOf(ao[8]) : null,
                        ao[9] != null ? String.valueOf(ao[9]) : null,
                        ao[10] != null ? String.valueOf(ao[10]) : null,
                        ao[11] != null ? String.valueOf(ao[11]) : null,
                        ao[12] != null ? String.valueOf(ao[12]) : null,
                        ao[13] != null ? String.valueOf(ao[13]) : null,
                        ao[14] != null ? String.valueOf(ao[14]) : null,
                        ao[15] != null ? String.valueOf(ao[15]) : null,
                        ao[16] != null ? String.valueOf(ao[16]) : null,
                        ao[17] != null ? String.valueOf(ao[17]) : null,
                        ao[18] != null ? String.valueOf(ao[18]) : null,
                        ao[19] != null ? String.valueOf(ao[19]) : null,
                        ao[20] != null ? String.valueOf(ao[20]) : null,
                        ao[21] != null ? String.valueOf(ao[21]) : null,
                        ao[22] != null ? String.valueOf(ao[22]) : null,
                        ao[23] != null ? String.valueOf(ao[23]) : null,
                        ao[24] != null ? String.valueOf(ao[24]) : null,
                        ao[25] != null ? String.valueOf(ao[25]) : null,
                        ao[26] != null ? String.valueOf(ao[26]) : null,
                        ao[27] != null ? String.valueOf(ao[27]) : null,
                        ao[28] != null ? String.valueOf(ao[28]) : null,
                        ao[29] != null ? String.valueOf(ao[29]) : null,
                        ao[30] != null ? String.valueOf(ao[30]) : null,
                        ao[31] != null ? String.valueOf(ao[31]) : null,
                        ao[32] != null ? String.valueOf(ao[32]) : null,
                        ao[33] != null ? String.valueOf(ao[33]) : null,
                        ao[34] != null ? String.valueOf(ao[34]) : null,
                        ao[35] != null ? String.valueOf(ao[35]) : null,
                        ao[36] != null ? String.valueOf(ao[36]) : null);
                rslt.add(f);
            }
        }
        return rslt;
    }

    public void reordenamientoRegistrosPrecenso(Integer codCab, Integer numDetActual, Integer ordenActual, String colsOrdena, String colsWhere, String signoReordenamiento) throws Exception {

        String sqlA = "update captura.capt_detalle_h set ";
        String sqlB = "";
        if (colsOrdena.contains("val")) {
            sqlB = colsOrdena + "=(CAST(" + colsOrdena + " as integer)+1)||'' ";
        } else {
            sqlB = colsOrdena + "=" + colsOrdena + "+1 ";
        }
        String sqlC = "";
        if (colsWhere.equals("NUMDET")) {
            sqlC = " where cod_cab=" + codCab + " and num_det" + signoReordenamiento + numDetActual + ";";
        } else if (colsWhere.equals("ORDEN")) {
            sqlC = " where cod_cab=" + codCab + " and num_det=" + numDetActual + " and orden" + signoReordenamiento + ordenActual + ";";
        } else if (colsWhere.equals("ORDENVIV")) {
            sqlC = " where cod_cab=" + codCab + " and ((num_det=" + numDetActual + " and orden" + signoReordenamiento + ordenActual + ") OR (num_det>" + numDetActual + "));";
        }
        Query q = em.createNativeQuery(sqlA + sqlB + sqlC);
        q.executeUpdate();
    }

    public void eliminarYReordenarRegistrosPrecenso(CaptFilaH registroAEliminar, String tipoReg) throws Exception {
        try {
            int codCab = registroAEliminar.getCodCab();
            int idDeth = registroAEliminar.getIdDetH();
            int numDet = registroAEliminar.getNumDet();
            int orden = registroAEliminar.getOrden();
            String sql1 = "update captura.capt_detalle_h set ";
            String sql2 = "";
            String sql3 = "update captura.capt_detalle_h set ";
            String sql4 = "";
            if (tipoReg.equals("NEO")) {
                sql2 = " val04=(CAST(val04 as integer)-1)||'' where cod_cab=" + codCab + " and num_det> " + numDet + ";";
                sql4 = " num_det=num_det-1 where cod_cab=" + codCab + " and num_det> " + numDet + ";";

            } else if (tipoReg.equals("NEV")) {
                sql2 = " val04=(CAST(val04 as integer)-1)||'',val14=(CAST(val14 as integer)-1)||'' where cod_cab=" + codCab + " and num_det> " + numDet + ";";
                sql4 = " num_det=num_det-1 where cod_cab=" + codCab + " and num_det> " + numDet + ";";

            } else if (tipoReg.equals("SE")) {
                sql1 = "";
                sql4 = " num_det=num_det-1 where cod_cab=" + codCab + " and num_det> " + numDet + ";";
            } else if (tipoReg.equals("MEV")) {
                sql2 = " val14=(CAST(val14 as integer)-1)||'' where cod_cab=" + codCab + " and ((num_det> " + numDet + ") OR (NUM_DET=" + numDet + " AND ORDEN>" + orden + "));";
                sql4 = " orden=orden-1 where cod_cab=" + codCab + " and num_det= " + numDet + " and orden>" + orden + ";";
            } else if (tipoReg.equals("MEO")) {
                sql1 = "";
                sql4 = " orden=orden-1 where cod_cab=" + codCab + " and num_det= " + numDet + " and orden>" + orden + ";";
            }
            Query q = em.createNativeQuery(sql1 + sql2 + sql3 + sql4);
            q.executeUpdate();
            Query q2 = em.createNativeQuery("delete from captura.capt_detalle_h where id_deth=" + idDeth + ";");
            q2.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
    }

    public List<CaptDetalleH> listarDetHPorCab(Integer codCab) throws Exception {
        String sql = "SELECT c FROM CaptDetalleH c "
                + " where c.codCab.idCab =:codCab ";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab);
        List<CaptDetalleH> lst = q.getResultList();
        return lst;
    }
    
    public List<CaptDetalleH> listarDetHPorCabXSeccion(Integer codCab, Integer codSeccion) throws Exception {
        String sql = "SELECT c FROM CaptDetalleH c "
                + " where c.codCab.idCab =:codCab "
                + "and c.codSeccion.idSeccion =:codSeccion order by orden";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab).setParameter("codSeccion", codSeccion);
        List<CaptDetalleH> lst = q.getResultList();
        return lst;
    }

    public CaptDetalleH buscarDetHPorId(Integer idDeth) throws Exception {
        String sql = "SELECT c FROM CaptDetalleH c "
                + " where c.idDeth =:idDeth ";
        Query q = em.createQuery(sql);
        q.setParameter("idDeth", idDeth);
        List<CaptDetalleH> lst = q.getResultList();
        if (!lst.isEmpty()) {
            if (lst.size() > 1) {
                System.out.println("Hay más de un Detalleh.");
            }
            //deberia devolver siempre un solo registro.
            return lst.get(0);

        } else {
            return new CaptDetalleH();
        }
    }

    public List<CaptDetalleH> listarDetHPorCabOrderIpc(Integer codCab) throws Exception { //Metodo Especial para IPS
        String sql = "SELECT c FROM CaptDetalleH c "
                + " where c.codCab.idCab =:codCab order by val01,val03";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab);
        List<CaptDetalleH> lst = q.getResultList();
        return lst;
    }
    
       public List<CaptDetalleH> listarTodo() throws Exception {
         String sql = "SELECT c FROM CaptDetalleH c";                              
         Query q = em.createQuery(sql);
         List<CaptDetalleH> lst = q.getResultList();      
         return lst;
    }
       
    public List<CaptDetalleH> listarIncidenciasxFiltro(String prov, String can, String ciu) throws Exception {
         String sql = "SELECT c FROM CaptDetalleH c"
                    + " where c.val02=:prov and c.val04=:can and c.val06=:ciu order by c.idDeth";                              
         Query q = em.createQuery(sql);
         q.setParameter("prov", prov).setParameter("can", can).setParameter("ciu", ciu);
         List<CaptDetalleH> lst = q.getResultList();      
         return lst;
    }
       
    public List<CaptDetalleH> listarIncidencias(String varEstado, String prov, String can, String ciu) throws Exception {
         String sql = "SELECT c FROM CaptDetalleH c"
                 + " where c.val20!='CERRADO' and c.val02=:prov and c.val04=:can and c.val06=:ciu"
                 + " and c.val31 is null or c.val31!=:varEstado"              
                 + " order by c.idDeth";                             
         Query q = em.createQuery(sql);
         q.setParameter("varEstado", varEstado).setParameter("prov", prov).setParameter("can", can).setParameter("ciu", ciu);
         List<CaptDetalleH> lst = q.getResultList();      
         return lst;
    }
    
    public List<CaptDetalleH> listarAsignados(String varEstado) throws Exception {
         String sql = "SELECT c FROM CaptDetalleH c where c.val31=:varEstado "
                 + "and c.val20!='CERRADO' order by c.idDeth";                              
         Query q = em.createQuery(sql);
         q.setParameter("varEstado", varEstado);
         List<CaptDetalleH> lst = q.getResultList();      
         return lst;
    }
    
    public List<OperadorAsignacion> contarAsignacionPorUsuario(Integer codProvincia) throws Exception {
         String sql = "SELECT c.id_deth as idDetalle,p.id_personal as idOperador, cast(count(c.val32) as integer) as asignaciones,p.cod_dpa as codProvincia "
                 +"FROM administracion.adm_personal p " 
                 +"INNER JOIN seguridad.seg_usuario u ON p.id_personal = u.cod_personal " 
                 +"INNER JOIN seguridad.seg_rol_usuario ru ON  ru.cod_usuario = u.id_usuario " 
                 +"INNER JOIN seguridad.seg_rol r ON r.id_rol = ru.cod_rol " 
                 +"LEFT JOIN  captura.capt_detalle_h c ON p.id_personal = cast(c.val32 as integer) AND c.val20='ABIERTO' "
                 +"WHERE r.alias IN ('ROL_ENCUE') AND p.cod_dpa = "+codProvincia +" " 
                 +"GROUP BY p.id_personal,c.id_deth,p.cod_dpa " 
                 +"HAVING count(c.val32)< 8 " 
                 +"ORDER BY random() ";                             
        Query q = em.createNativeQuery(sql);
        System.out.println(sql);
        //q.setParameter("codProvincia", codProvincia);
        System.out.println(sql);
        List<Object[]> rows = q.getResultList();
        List<OperadorAsignacion> result = new ArrayList<>();
        for (Object[] row : rows) {
            result.add(new OperadorAsignacion(
                    row[0]!=null? (Long.valueOf(row[0].toString())).intValue():0,
                    row[1]!=null? (Long.valueOf(row[1].toString())).intValue():0,
                    row[2]!=null? (Long.valueOf(row[2].toString())).intValue():0,
                    row[3]!=null? (Long.valueOf(row[3].toString())).intValue():0));
        }
        
        return result;
    }
     
//    public Integer buscarUsuarioRandom() throws Exception {
//         String sql = "SELECT a.id_personal FROM seguridad.seg_usuario u, administracion.adm_personal a, seguridad.seg_rol r, seguridad.seg_rol_usuario ru "
//                    + "WHERE u.cod_personal = a.id_personal "
//                    +"AND u.id_usuario = ru.cod_usuario "
//                    +"AND r.id_rol = ru.cod_rol "
//                    +"AND r.id_rol IN (SELECT r.id_rol "
//                                    + "FROM seguridad.seg_rol r "
//                                    + "WHERE r.alias='ROL_ENCUE') "
//                    +"ORDER BY random()"
//                    +"limit 1";                            
//         Query q = em.createNativeQuery(sql);         
//         Integer num = (Integer) q.getSingleResult();
//         return num;
//   }   

}
