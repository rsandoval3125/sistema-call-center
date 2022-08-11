/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.facade;

import ec.gob.inec.captura.clases.CaptElementoControl;
import ec.gob.inec.captura.clases.CaptVarV;
import ec.gob.inec.captura.ejb.entidades.CaptDetalleV;
import ec.gob.inec.negocio.ejb.facade.AbstractFacade;
import java.math.BigInteger;
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
public class CaptDetalleVFacade extends AbstractFacade<CaptDetalleV> {

    @PersistenceContext(unitName = "SIPE_CapturaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CaptDetalleVFacade() {
        super(CaptDetalleV.class);
    }

    private String insertsDetallesVDeFormularioPorNElementos(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numElementos) {
        String sql1 = "";
        String sqlN = "";

        if (tipoFormulario.equals("ME")) {
            List<CaptVarV> listaVariablesNV = new ArrayList<>();

            for (int e = 1; e <= numElementos; e++) {
                String sqlE = "insert into captura.capt_detalle_v (cod_cab,num_det,cod_var) values ";
                if (e == 1) {
                    //en el primer elemento se crean todas las variables incluidas en las secciones 1V,NV,SV
                    for (int j = 0; j < listaVariables.size(); j++) {
                        if (j < (listaVariables.size() - 1)) {
                            sqlE = sqlE + "(" + codCab + ",1," + listaVariables.get(j).getCodVar() + "),";
                        } else {
                            sqlE = sqlE + "(" + codCab + ",1," + listaVariables.get(j).getCodVar() + ")";
                        }
                        //aprovechamos la iteracion para llenar la lista auxiliar
                        if (listaVariables.get(j).getTipoSeccion().equals("NV")) {
                            listaVariablesNV.add(listaVariables.get(j));
                        }
                    }
                } else {
                    //a partir del segundo elemento se crean solo las variables incluidas en las secciones NV
                    for (int j = 0; j < listaVariablesNV.size(); j++) {
                        if (j < (listaVariablesNV.size() - 1)) {
                            sqlE = sqlE + "(" + codCab + "," + e + "," + listaVariablesNV.get(j).getCodVar() + "),";
                        } else {
                            sqlE = sqlE + "(" + codCab + "," + e + "," + listaVariablesNV.get(j).getCodVar() + ")";
                        }

                    }
                }
                sqlN = sqlN + sqlE + ";";
            }
        } else if (tipoFormulario.equals("1E")) {
            sql1 = "insert into captura.capt_detalle_v (cod_cab,num_det,cod_var) values ";
            for (int i = 0; i < listaVariables.size(); i++) {
                if (i < (listaVariables.size() - 1)) {
                    sql1 = sql1 + "(" + codCab + ",1," + listaVariables.get(i).getCodVar() + "),";
                } else {
                    sql1 = sql1 + "(" + codCab + ",1," + listaVariables.get(i).getCodVar() + ")";
                }
            }
            sql1 = sql1 + ";";
        }
        return sql1 + "" + sqlN;
    }

    private String insertsDetallesVDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numElementoNuevo) {
        String sql1 = "";
        String sqlN = "";

        if (tipoFormulario.equals("ME")) {
            List<CaptVarV> listaVariablesNV = new ArrayList<>();
            for (int x = 0; x < listaVariables.size(); x++) {
                if (listaVariables.get(x).getTipoSeccion().equals("NV")) {
                    listaVariablesNV.add(listaVariables.get(x));
                }
            }
            String sqlE = "insert into captura.capt_detalle_v (cod_cab,num_det,cod_var) values ";
            if (numElementoNuevo == 1) {
                //en el primer elemento se crean todas las variables incluidas en las secciones 1V,NV,SV
                for (int j = 0; j < listaVariables.size(); j++) {
                    if (j < (listaVariables.size() - 1)) {
                        sqlE = sqlE + "(" + codCab + ",1," + listaVariables.get(j).getCodVar() + "),";
                    } else {
                        sqlE = sqlE + "(" + codCab + ",1," + listaVariables.get(j).getCodVar() + ")";
                    }

                }
            } else {
                //a partir del segundo elemento se crean solo las variables incluidas en las secciones NV
                for (int j = 0; j < listaVariablesNV.size(); j++) {
                    if (j < (listaVariablesNV.size() - 1)) {
                        sqlE = sqlE + "(" + codCab + "," + numElementoNuevo + "," + listaVariablesNV.get(j).getCodVar() + "),";
                    } else {
                        sqlE = sqlE + "(" + codCab + "," + numElementoNuevo + "," + listaVariablesNV.get(j).getCodVar() + ")";
                    }

                }
            }
            sqlN = sqlE + ";";

        } else if (tipoFormulario.equals("1E")) {
            if (numElementoNuevo == 1) {
                sql1 = "insert into captura.capt_detalle_v (cod_cab,num_det,cod_var) values ";
                for (int i = 0; i < listaVariables.size(); i++) {
                    if (i < (listaVariables.size() - 1)) {
                        sql1 = sql1 + "(" + codCab + ",1," + listaVariables.get(i).getCodVar() + "),";
                    } else {
                        sql1 = sql1 + "(" + codCab + ",1," + listaVariables.get(i).getCodVar() + ")";
                    }
                }
                sql1 = sql1 + ";";
            }
        }
        return sql1 + "" + sqlN;
    }

    private String updatesDetallesVDeFormularioPorElemento(List<CaptVarV> listaVariables) {
        String sql = "";
        for (CaptVarV v : listaVariables) {

            if (v.getValorAnterior() == null) {
                v.setValorAnterior("");
            }
            if (v.getValor() == null) {
                v.setValor("");
            }
            if (!v.getValorAnterior().equals(v.getValor())) {
                sql = sql + "update captura.capt_detalle_v set valor='" + v.getValor().replace("'", "") + "' where detv_id=" + v.getIdDetV() + ";";
            }

        }
        return sql;
    }

    private String updatesDetallesVDeFormularioPorElementoParcialmente(List<CaptVarV> listaVariables, int idxInicial, int idxFinal) {
        String sql = "";
        for (int i = idxInicial; i <= idxFinal; i++) {

            if (listaVariables.get(i).getValorAnterior() == null) {
                listaVariables.get(i).setValorAnterior("");
            }
            if (listaVariables.get(i).getValor() == null) {
                listaVariables.get(i).setValor("");
            }

            if (!listaVariables.get(i).getValorAnterior().equals(listaVariables.get(i).getValor())) {
                sql = sql + "update captura.capt_detalle_v set valor='" + listaVariables.get(i).getValor().replace("'", "") + "' where detv_id=" + listaVariables.get(i).getIdDetV() + ";";
            }
        }
        return sql;
    }

    public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElemento(int idCab, int numDet, int idFormulario) throws Exception {
        List<CaptVarV> rslt = new ArrayList<>();
        String sql = "SELECT DETV_ID,COD_CAB,ID_VAR,NUM_DET,VALOR,TIPO_SECCION FROM metadato.v_variables_secciones_v A LEFT JOIN captura.capt_detalle_v B on A.id_var = B.cod_var AND B.NUM_DET=" + numDet + " AND  B.cod_cab=" + idCab + " WHERE A.COD_FORMULARIO=" + idFormulario + " order by a.s1_orden,a.s2_orden,a.v_orden";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            for (Object[] ao : lo) {
                CaptVarV v = new CaptVarV(new BigInteger(ao[0] != null ? String.valueOf(ao[0]) : "0"), Integer.valueOf(ao[1] != null ? String.valueOf(ao[1]) : "0"), Integer.valueOf(String.valueOf(ao[2])), Integer.valueOf(ao[3] != null ? String.valueOf(ao[3]) : "0"), ao[4] != null ? String.valueOf(ao[4]) : null, String.valueOf(ao[5]), ao[4] != null ? String.valueOf(ao[4]) : null);
                rslt.add(v);
            }
        }

        return rslt;
    }
 public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElementoyRangoVariables(int idCab, int numDet, int idFormulario, int idVarIni, int idVarFin) throws Exception {
        List<CaptVarV> rslt = new ArrayList<>();
        String sql = "SELECT DETV_ID,COD_CAB,ID_VAR,NUM_DET,VALOR,TIPO_SECCION FROM metadato.v_variables_secciones_v A LEFT JOIN captura.capt_detalle_v B on A.id_var = B.cod_var AND B.NUM_DET=" + numDet + " AND  B.cod_cab=" + idCab + " WHERE A.COD_FORMULARIO=" + idFormulario + "and (A.id_var BETWEEN "+ idVarIni +" and " + idVarFin +")"+" order by a.s1_orden,a.s2_orden,a.v_orden";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            for (Object[] ao : lo) {
                CaptVarV v = new CaptVarV(new BigInteger(ao[0] != null ? String.valueOf(ao[0]) : "0"), Integer.valueOf(ao[1] != null ? String.valueOf(ao[1]) : "0"), Integer.valueOf(String.valueOf(ao[2])), Integer.valueOf(ao[3] != null ? String.valueOf(ao[3]) : "0"), ao[4] != null ? String.valueOf(ao[4]) : null, String.valueOf(ao[5]), ao[4] != null ? String.valueOf(ao[4]) : null);
                rslt.add(v);
            }
        }

        return rslt;
    }
    /*este metodo debera ser llamado en cache*/
    public List<CaptVarV> listarVariablesVPorFormulario(int idFormulario) throws Exception {
        List<CaptVarV> rslt = new ArrayList<>();
        String sql = "SELECT id_var, tipo_seccion FROM metadato.v_variables_secciones_v where cod_formulario=" + idFormulario;
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            for (Object[] ao : lo) {
                CaptVarV v = new CaptVarV(BigInteger.ZERO, 0, Integer.valueOf(String.valueOf(ao[0])), 0, null, String.valueOf(ao[1]), null);
                rslt.add(v);
            }
        }

        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCab(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and ((COD_VAR>=89 AND COD_VAR<=107) OR COD_VAR=125) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, "
                + "v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text, v020 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String codEnlistamiento = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String nombresApellidos = (ao[2] != null ? String.valueOf(ao[2]) : "") + " " + (ao[3] != null ? String.valueOf(ao[3]) : "") + " " + (ao[4] != null ? String.valueOf(ao[4]) : "") + " " + (ao[5] != null ? String.valueOf(ao[5]) : "");
                String sexo = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String edadAnios = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String fechaNac = (ao[9] != null ? String.valueOf(ao[9]) : "") + "-" + (ao[10] != null ? String.valueOf(ao[10]) : "") + "-" + (ao[11] != null ? String.valueOf(ao[11]) : "");
                String cod = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String seleccion = (ao[13] != null ? String.valueOf(ao[13]) : "") + "-" + (ao[14] != null ? String.valueOf(ao[14]) : "") + "-" + (ao[15] != null ? String.valueOf(ao[15]) : "") + "-" + (ao[16] != null ? String.valueOf(ao[16]) : "") + "-" + (ao[17] != null ? String.valueOf(ao[17]) : "") + "-" + (ao[18] != null ? String.valueOf(ao[18]) : "");
                String parentesco = (ao[19] != null ? String.valueOf(ao[19]) : "");
                String ecivil = (ao[20] != null ? String.valueOf(ao[20]) : "");
                String edadMeses = (ao[8] != null ? String.valueOf(ao[8]) : "");
                CaptElementoControl ec = new CaptElementoControl(numero, nombresApellidos, sexo, edadAnios, fechaNac, cod, seleccion, parentesco, ecivil, edadMeses, codEnlistamiento);
                rslt.add(ec);
                i++;

            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlMultiF1PorCab(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = " SELECT * FROM CROSSTAB "
                + "('SELECT  cast(v.cod_cab as text)||v.num_det, v.detv_id, v.valor FROM captura.capt_detalle_v v,metadato.met_variable m WHERE v.cod_cab=" + codCab + " and "
                + "(v.cod_var in "
                + "(select id_var from metadato.met_variable where identificador in "
                + "(''MLT_S1_1n1'',''MLT_S1_1n2'',''MLT_S1_1a1'',''MLT_S1_1a2'',''MLT_S1_2'',''MLT_S1_3'',''MLT_S1_3Ad'',''MLT_S1_3Am'',''MLT_S1_3Aa'',''MLT_S1_3b'',''MLT_S1_4'',''MLT_S1_6'' )"
                + ")and v.cod_var=m.id_var) "
                + "ORDER BY v.cod_cab,v.num_det, m.cod_seccion,m.orden')  CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, "
                + "v010 text, v011 text, v012 text);";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String nombresApellidos = (ao[1] != null ? String.valueOf(ao[1]) : "") + " " + (ao[2] != null ? String.valueOf(ao[2]) : "") + " " + (ao[3] != null ? String.valueOf(ao[3]) : "") + " " + (ao[4] != null ? String.valueOf(ao[4]) : "");
                String sexo = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String edadAnios = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String fechaNac = (ao[7] != null ? String.valueOf(ao[7]) : "") + "-" + (ao[8] != null ? String.valueOf(ao[8]) : "") + "-" + (ao[9] != null ? String.valueOf(ao[9]) : "");
                String infSelec = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String parentesco = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String ecivil = (ao[12] != null ? String.valueOf(ao[12]) : "");
                CaptElementoControl ec = new CaptElementoControl(numero, nombresApellidos, sexo, edadAnios, fechaNac, infSelec, parentesco, ecivil, null, null, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCabParaValidar(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and ((COD_VAR>=89 AND COD_VAR<=107) OR COD_VAR=125) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, "
                + "v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text, v020 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String cod = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String nombresApellidos = (ao[2] != null ? String.valueOf(ao[2]) : "") + " " + (ao[3] != null ? String.valueOf(ao[3]) : "") + " " + (ao[4] != null ? String.valueOf(ao[4]) : "") + " " + (ao[5] != null ? String.valueOf(ao[5]) : "");
                String sexo = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String edadAnios = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String diaNaci = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String mesNaci = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String anioNaci = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String edadMeses = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String ecivil = (ao[20] != null ? String.valueOf(ao[20]) : "");
                CaptElementoControl ec = new CaptElementoControl(numero, cod, nombresApellidos, sexo, edadAnios, diaNaci, mesNaci, anioNaci, edadMeses, ecivil, null);
                rslt.add(ec);
                i++;

            }
        }
        return rslt;
    }

    public void crearDetallesVDeFormulario(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numElementos) throws Exception {
        String sql = insertsDetallesVDeFormularioPorNElementos(tipoFormulario, codCab, listaVariables, numElementos);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public void crearDetallesVDeFormularioPorElemento(String tipoFormulario, Integer codCab, List<CaptVarV> listaVariables, Integer numNuevoElemento) throws Exception {
        String sql = insertsDetallesVDeFormularioPorElemento(tipoFormulario, codCab, listaVariables, numNuevoElemento);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public void actualizarDetallesVDeFormularioPorElemento(List<CaptVarV> listaVariables) throws Exception {
        String sql = updatesDetallesVDeFormularioPorElemento(listaVariables);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public void actualizarDetallesVDeFormularioPorElementoParcial(List<CaptVarV> listaVariables, int idxInicial, int idxFinal) throws Exception {
        String sql = updatesDetallesVDeFormularioPorElementoParcialmente(listaVariables, idxInicial, idxFinal);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    public void eliminarElementoDeCuestionarioyReordenar(int codCab, int numElementoEliminar) throws Exception {
        String sql = "UPDATE captura.capt_cabecera SET num_det=(num_det-1) WHERE id_cab=" + codCab + ";"
                + "DELETE FROM captura.capt_detalle_v WHERE cod_cab=" + codCab + " AND num_det=" + numElementoEliminar + ";"
                + "DELETE FROM captura.capt_estado WHERE cod_cab=" + codCab + " AND num_det=" + numElementoEliminar + ";"
                + "DELETE FROM captura.capt_observacion WHERE cod_cab=" + codCab + " AND num_det=" + numElementoEliminar + ";"
                + "UPDATE captura.capt_detalle_v SET num_det=(num_det-1)WHERE cod_cab=" + codCab + " AND num_det>" + numElementoEliminar + ";"
                + "UPDATE captura.capt_estado SET num_det=(num_det-1)WHERE cod_cab=" + codCab + " AND num_det>" + numElementoEliminar + ";"
                + "UPDATE captura.capt_observacion SET num_det=(num_det-1)WHERE cod_cab=" + codCab + " AND num_det>" + numElementoEliminar + ";";
        Query q = getEntityManager().createNativeQuery(sql);
        q.executeUpdate();

    }

    public void reordenarElementosDeCuestionarioAntesDeInsertar(int codCab, int numElementoNuevo) throws Exception {
        String sql = "UPDATE captura.capt_detalle_v SET num_det=(num_det+1)WHERE cod_cab=" + codCab + " AND num_det>=" + numElementoNuevo + ";"
                + "UPDATE captura.capt_estado SET num_det=(num_det+1)WHERE cod_cab=" + codCab + " AND num_det>=" + numElementoNuevo + ";"
                + "UPDATE captura.capt_observacion SET num_det=(num_det+1)WHERE cod_cab=" + codCab + " AND num_det>=" + numElementoNuevo + ";";
        Query q = getEntityManager().createNativeQuery(sql);
        q.executeUpdate();

    }

    public List<CaptElementoControl> listarElementosControlEnsanutF1PorCabVal(Integer codCab, Integer numDet) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + "  and num_det=" + numDet + " and ((COD_VAR>=89 AND COD_VAR<=125)OR (COD_VAR>=10673 AND COD_VAR<=10674)) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, "
                + "v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text, v020 text,"
                + "v021 text, v022 text, v023 text, v024 text, v025 text, v026 text, v027 text, \n"
                + "                 v028 text, v029 text, v030 text, v031 text, v032 text, v033 text, v034 text, v035 text, v036 text, v037 text, v038 text, v039 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String codEnlistamiento = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String primerNombre = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String primerApellido = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String sexo = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String edadAnios = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String fnDia = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String fnMes = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String fnAnio = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String identMiembro = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String sec8 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String frm2 = (ao[14] != null ? String.valueOf(ao[14]) : "");
                String frm3 = (ao[15] != null ? String.valueOf(ao[15]) : "");
                String frm4 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String frm5 = (ao[17] != null ? String.valueOf(ao[17]) : "");
                String frm6 = (ao[18] != null ? String.valueOf(ao[18]) : "");
                String parentesco = (ao[19] != null ? String.valueOf(ao[19]) : "");
                String seguro = (ao[20] != null ? String.valueOf(ao[20]) : "");
                String ecivil = (ao[37] != null ? String.valueOf(ao[37]) : "");
                String tieneCed = (ao[38] != null ? String.valueOf(ao[38]) : "");
                String cedula = (ao[39] != null ? String.valueOf(ao[39]) : "");

                String codPadre = (ao[34] != null ? String.valueOf(ao[34]) : "");
                String codMadre = (ao[36] != null ? String.valueOf(ao[36]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, codEnlistamiento, primerNombre, primerApellido, sexo, edadAnios, fnDia, fnMes, fnAnio, identMiembro, sec8, frm2,
                        frm3, frm4, frm5, frm6, parentesco, seguro, ecivil, tieneCed, cedula, codPadre, codMadre);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF3PorCabVal(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and ((COD_VAR>=1934 AND COD_VAR<=1939)or COD_VAR=10973 or COD_VAR=1958  or COD_VAR=2012  or COD_VAR=2035) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text,v008 text, v009 text, v010 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String codEnlistamiento = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String nombres = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String fndia = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String fnmes = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String fnanio = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String edad = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String seleccionado = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String preg300 = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String preg400 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String preg403 = (ao[10] != null ? String.valueOf(ao[10]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, codEnlistamiento, nombres, fndia, fnmes, fnanio, edad, seleccionado, preg300, preg400, preg403);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF4PorCabVal(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        //System.out.println(" codCab:" + codCab);
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and ((COD_VAR>=2082 AND COD_VAR<=2089)or COD_VAR=2092 or COD_VAR=2093 or (COD_VAR>=2102 AND COD_VAR<=2104)\n"
                + "or COD_VAR=2119 or COD_VAR=2121 or COD_VAR=2123 or COD_VAR=2125 or COD_VAR=2126 or COD_VAR=2127 or COD_VAR=2128) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;

                String codEnlistamiento = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String nombres = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String fndia = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String fnmes = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String fnanio = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String edad = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String seleccionado = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String preg200 = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String preg202 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String preg300 = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String preg301 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String preg302 = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String preg400 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String preg402 = (ao[14] != null ? String.valueOf(ao[14]) : "");
                String preg404 = (ao[15] != null ? String.valueOf(ao[15]) : "");
                String preg406 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String preg407 = (ao[17] != null ? String.valueOf(ao[17]) : "");
                String preg408 = (ao[18] != null ? String.valueOf(ao[18]) : "");
                String preg409 = (ao[19] != null ? String.valueOf(ao[19]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, codEnlistamiento, nombres, fndia, fnmes, fnanio, edad, seleccionado, preg200, preg202, preg300, preg301, preg302, preg400, preg402, preg404, preg406, preg407, preg408, preg409, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF4V(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        System.out.println(" codCab:" + codCab);
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and (COD_VAR>=2104 AND COD_VAR<=2112) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;

                String var_302 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String var_303_1 = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String var_303_2 = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String var_303_3 = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String var_303_4 = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String var_303_5 = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String var_303_6 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String var_303_7 = (ao[8] != null ? String.valueOf(ao[8]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, var_302, var_303_1, var_303_2, var_303_3, var_303_4, var_303_5, var_303_6, var_303_7, null, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }
 public List<CaptVarV> listarVariablesVPorCabeceraCreadayNumElementoIdentificadores(int idCab, int numDet, int idFormulario, String identificadores) throws Exception {
        List<CaptVarV> rslt = new ArrayList<>();
        String sql = "";
        String[] arrIdentificadores=identificadores.split(",",-1);
        for(int i=0;i<arrIdentificadores.length;i++){
       sql =sql+"SELECT DETV_ID,COD_CAB,ID_VAR,NUM_DET,VALOR,TIPO_SECCION FROM metadato.v_variables_secciones_v A LEFT JOIN captura.capt_detalle_v B on A.id_var = B.cod_var AND B.NUM_DET=" + numDet + " AND  B.cod_cab=" + idCab + " WHERE A.COD_FORMULARIO=" + idFormulario + "and A.identificador='"+arrIdentificadores[i]+"' union all ";
        }
        Query q = em.createNativeQuery(sql.substring(0, sql.length() - 10));
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            for (Object[] ao : lo) {
                CaptVarV v = new CaptVarV(new BigInteger(ao[0] != null ? String.valueOf(ao[0]) : "0"), Integer.valueOf(ao[1] != null ? String.valueOf(ao[1]) : "0"), Integer.valueOf(String.valueOf(ao[2])), Integer.valueOf(ao[3] != null ? String.valueOf(ao[3]) : "0"), ao[4] != null ? String.valueOf(ao[4]) : null, String.valueOf(ao[5]), ao[4] != null ? String.valueOf(ao[4]) : null);
                rslt.add(v);
            }
        }

        return rslt;
    }
    public List<CaptElementoControl> listarElementosControlEnsanutF2PorCabVal(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and ((COD_VAR>=5714 AND COD_VAR<=5721) or COD_VAR=13951 or COD_VAR=1778 or COD_VAR=1780 or COD_VAR=1782 or COD_VAR=1784 or COD_VAR=1786 or COD_VAR=1788 or COD_VAR=1790 or COD_VAR=1792 or COD_VAR=1794 or COD_VAR=1854 or COD_VAR=1863  ) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String F2_S1_100 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String F2_S1_100_1 = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String F2_S1_100_2 = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String F2_S1_100_3 = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String F2_S1_101_1 = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String F2_S2_200 = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String F2_S8_800_1 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String F2_S8_800_2 = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String F2_S8_800_3 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String F2_S8_800_4 = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String F2_S8_800_5 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String F2_S8_800_6 = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String F2_S8_800_7 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String F2_S8_800_8 = (ao[14] != null ? String.valueOf(ao[14]) : "");
                String F2_S8_800_9 = (ao[15] != null ? String.valueOf(ao[15]) : "");
                String F2_S9_900 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String F2_S10_1000 = (ao[17] != null ? String.valueOf(ao[17]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, F2_S1_100, F2_S1_100_1,
                        F2_S1_100_2, F2_S1_100_3, F2_S1_101_1, F2_S2_200, F2_S8_800_1, F2_S8_800_2,
                        F2_S8_800_3, F2_S8_800_4, F2_S8_800_5, F2_S8_800_6, F2_S8_800_7, F2_S8_800_8,
                        F2_S8_800_9, F2_S9_900, F2_S10_1000);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlEnsanutF5PorCabVal(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and ( COD_VAR=3714 or COD_VAR=3724 or COD_VAR=3732 or COD_VAR=3735 or COD_VAR=3737 or (COD_VAR>=3739 and COD_VAR<=3746) or COD_VAR=3748 or COD_VAR=3755 or COD_VAR=3757 or COD_VAR=3759 or COD_VAR=3761 or COD_VAR=3763 or COD_VAR=3765 ) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text, v020 text)";

        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String F5_S2_200 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String F5_S2_206 = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String F5_S2_211 = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String F5_S2_213 = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String F5_S2_215 = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String F5_S3_301_1 = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String F5_S3_301_2 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String F5_S3_301_3 = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String F5_S3_301_4 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String F5_S3_301_5 = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String F5_S3_301_6 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String F5_S3_301_7 = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String F5_S3_302_1 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String F5_S3_303_1 = (ao[14] != null ? String.valueOf(ao[14]) : "");
                String F5_S3_307_1 = (ao[15] != null ? String.valueOf(ao[15]) : "");
                String F5_S3_307_3 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String F5_S3_307_5 = (ao[17] != null ? String.valueOf(ao[17]) : "");
                String F5_S3_307_7 = (ao[18] != null ? String.valueOf(ao[18]) : "");
                String F5_S3_307_9 = (ao[19] != null ? String.valueOf(ao[19]) : "");
                String F5_S3_307_11 = (ao[20] != null ? String.valueOf(ao[20]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, F5_S2_200, F5_S2_206,
                        F5_S2_211, F5_S2_213, F5_S2_215, F5_S3_301_1, F5_S3_301_2, F5_S3_301_3,
                        F5_S3_301_4, F5_S3_301_5, F5_S3_301_6, F5_S3_301_7, F5_S3_302_1, F5_S3_303_1,
                        F5_S3_307_1, F5_S3_307_3, F5_S3_307_5, F5_S3_307_7, F5_S3_307_9, F5_S3_307_11);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    //para validación de Sección 6 
    public List<CaptElementoControl> listarElementosF1Sec6(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + "  and num_det=1 and (COD_VAR>=364 AND COD_VAR<=378) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, "
                + "v010 text, v011 text, v012 text, v013 text, v014 text, v015 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String s6_2_1 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String s6_2_2 = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String s6_2_3 = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String s6_2_4 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String s6_2_5 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String s6_2_6 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String s6_2_7 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String s6_2_8 = (ao[15] != null ? String.valueOf(ao[15]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, s6_2_1, s6_2_2, s6_2_3, s6_2_4, s6_2_5, s6_2_6, s6_2_7, s6_2_8, null, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }
    //para validación de Sección 8 

    public List<CaptElementoControl> listarElementosF1Sec8(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and COD_VAR=397 ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;

                String codSec8 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                System.out.println("codSec8:" + codSec8);
                CaptElementoControl ec = new CaptElementoControl(numero, codSec8, null, null, null, null, null, null, null, null, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }
    //Valida seccion 7

    public List<CaptElementoControl> listarElementosF1Sec7(Integer codCab, Integer numDet) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + "  and num_det=" + numDet + " and (COD_VAR>=384 AND COD_VAR<=386) ORDER BY cod_cab,num_det, detv_id')"
                + " CT (iden text , v001 text, v002 text, v003 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String medicionDia = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String medicionMes = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String medicionAnio = (ao[3] != null ? String.valueOf(ao[3]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, medicionDia, medicionMes, medicionAnio, null, null, null, null, null, null, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    //Para validar Códigos Frm3
    public List<CaptElementoControl> listarElementosValFrm3(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = "SELECT * FROM CROSSTAB ("
                + "'SELECT  cast(cod_cab as text)||num_det, detv_id, valor FROM captura.capt_detalle_v "
                + " WHERE cod_cab=" + codCab + " and (cod_var>=1934 and cod_var<=1939)')"
                + " CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text)";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String codEnlistamiento = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String fndia = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String fnmes = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String fnanio = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String edad = (ao[6] != null ? String.valueOf(ao[6]) : "");

                CaptElementoControl ec = new CaptElementoControl(numero, codEnlistamiento, fndia, fnmes, fnanio, edad, null, null, null, null, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlFrmConCalCampo(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = " SELECT * FROM CROSSTAB "
                + "('SELECT  cast(v.cod_cab as text)||v.num_det, v.detv_id, v.valor FROM captura.capt_detalle_v v,metadato.met_variable m WHERE v.cod_cab=" + codCab + " and "
                + "(v.cod_var in "
                + "(select id_var from metadato.met_variable where identificador in "
                + "(''PFC1_S1_SUP'',''PFC1_S1_ENC'',''PFC1_S1_FEC'',''PFC1_S1_PAR'',''PFC1_S1_ZON'',''PFC1_S1_SEC'',''PFC1_S1_AMA'',''PFC1_S1_DIS'',''PFC1_S1_NUM_EDI'',''PFC1_S1_NUM_VIV'',''PFC1_S1_DIR'',''PFC1_S1_CON_OCU'',''PFC1_S1_NOM_JEF'',''PFC1_S1_NUM_HAB'',''GPFC1_S1_GEO_COD'',''PFC1_S1_NUM_PIS'',''PFC1_S1_VIVS'',''PFC1_S1_EST_ECO'',''PFC1_S1_NUM_PLA'',''PFC1_S1_ACC_EDI'',''PFC1_S1_INI_REC'',''PFC1_S1_ING_EDI'',''PFC1_S1_PEL_AMA'',''PFC1_S1_OMI_EDI'',''PFC1_S1_EDI_DIB'',''PFC1_S1_ACT_VER'',''PFC1_S1_CON_FOR'',''PFC1_S1_ACT_ACU_CAM'',''PFC1_S1_PEL_DIS'',''PFC1_S1_ACC_SEC_AMA'',''PFC1_S1_ACT_MAP'' )"
                + ")and v.cod_var=m.id_var) "
                + "ORDER BY v.cod_cab,v.num_det, m.cod_seccion,m.orden')  CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, "
                + "v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text, v020 text, v021 text, v022 text, v023 text, v024 text, v025 text, v026 text, v027 text, v028 text, v029 text, v030 text, v031 text);";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String p01 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String p02 = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String p03 = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String p04 = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String p05 = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String p06 = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String p07 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String p08 = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String p09 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String p10 = (ao[10] != null ? String.valueOf(ao[10]) : "");                
                String p11 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String p12 = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String p13 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String p14 = (ao[14] != null ? String.valueOf(ao[14]) : "");
                String p15 = (ao[15] != null ? String.valueOf(ao[15]) : "");
                String p16 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String p17 = (ao[17] != null ? String.valueOf(ao[17]) : "");
                String p18 = (ao[18] != null ? String.valueOf(ao[18]) : "");
                String p19 = (ao[19] != null ? String.valueOf(ao[19]) : "");
                String p20 = (ao[20] != null ? String.valueOf(ao[20]) : "");
                String p21 = (ao[21] != null ? String.valueOf(ao[21]) : "");
                String p22 = (ao[22] != null ? String.valueOf(ao[22]) : "");
                String p23 = (ao[23] != null ? String.valueOf(ao[23]) : "");
                String p24 = (ao[24] != null ? String.valueOf(ao[24]) : "");
                String p25 = (ao[25] != null ? String.valueOf(ao[25]) : "");
                String p26 = (ao[26] != null ? String.valueOf(ao[26]) : "");
                String p27 = (ao[27] != null ? String.valueOf(ao[27]) : "");
                String p28 = (ao[28] != null ? String.valueOf(ao[28]) : "");
                String p29 = (ao[29] != null ? String.valueOf(ao[29]) : "");
                String p30 = (ao[30] != null ? String.valueOf(ao[30]) : "");
                String p31 = (ao[31] != null ? String.valueOf(ao[31]) : "");
                CaptElementoControl ec = new CaptElementoControl(numero, p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlFrmConCalDigita(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = " SELECT * FROM CROSSTAB "
                + "('SELECT  cast(v.cod_cab as text)||v.num_det, v.detv_id, v.valor FROM captura.capt_detalle_v v,metadato.met_variable m WHERE v.cod_cab=" + codCab + " and "
                + "(v.cod_var in "
                + "(select id_var from metadato.met_variable where identificador in "
                + "(''PFD1_S1_ID_SEC'',''PFD1_S1_DIG'',''PFD1_S1_CAR'',''PFD1_S1_UBI_GEO'',''PFD1_S1_OBS'',''PFD1_S1_TIP_VIA'',''PFD1_S1_NUM_EDI'',''PFD1_S1_NUM_VIV'',''PFD1_S1_ING'',''PFD1_S1_SIM'',''PFD1_S1_UBI_VER'',''PFD1_S1_NOM_NUM_LOC'',''PFD1_S1_REF'',''PFD1_S1_ETI_INF'',''PFD1_S1_COD'',''PFD1_S1_COL_CAT'',''PFD1_S1_VAL_REG'',''PFD1_S1_ERR_COB'',''PFD1_S1_GEN_UBI_REG'',''PFD1_S1_TAR_ID'',''PFD1_S1_ACT_MAP'' )"
                + ")and v.cod_var=m.id_var) "
                + "ORDER BY v.cod_cab,v.num_det, m.cod_seccion,m.orden')  CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text, v020 text, v021 text "
                + ");";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String p01 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String p02 = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String p03 = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String p04 = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String p05 = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String p06 = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String p07 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String p08 = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String p09 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String p10 = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String p11 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String p12 = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String p13 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String p14 = (ao[14] != null ? String.valueOf(ao[14]) : "");
                String p15 = (ao[15] != null ? String.valueOf(ao[15]) : "");
                String p16 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String p17 = (ao[17] != null ? String.valueOf(ao[17]) : "");
                String p18 = (ao[18] != null ? String.valueOf(ao[18]) : "");
                String p19 = (ao[19] != null ? String.valueOf(ao[19]) : "");
                String p20 = (ao[20] != null ? String.valueOf(ao[20]) : "");
                String p21 = (ao[21] != null ? String.valueOf(ao[21]) : "");
                CaptElementoControl ec = new CaptElementoControl(numero, p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    public List<CaptElementoControl> listarElementosControlFrmConCalRevisa(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = " SELECT * FROM CROSSTAB "
                + "('SELECT  cast(v.cod_cab as text)||v.num_det, v.detv_id, v.valor FROM captura.capt_detalle_v v,metadato.met_variable m WHERE v.cod_cab=" + codCab + " and "
                + "(v.cod_var in "
                + "(select id_var from metadato.met_variable where identificador in "
                + "(''PFR1_S1_REP'',''PFR1_S1_PRO'',''PFR1_S1_CAN'',''PFR1_S1_PAR'',''PFR1_S1_ZON'',''PFR1_S1_SEC'',''PFR1_S1_MAN_LOC'',''PFR1_S1_TOT_EDI'',''PFR1_S1_TOT_VIV'',''PFR1_S1_PUN_INI_REC'',''PFR1_S1_DIR'',''PFR1_S1_NUM_PIS'',''PFR1_S1_GEO_COD'',''PFR1_S1_ACC_GEO'',''PFR1_S1_CON_INF'',''PFR1_S1_REG_TIP_VIA'',''PFR1_S1_JEF_AYU'',''PFR1_S1_VER_LIM'',''PFR1_S1_ACT_CAR_MAP'' )"
                + ")and v.cod_var=m.id_var) "
                + "ORDER BY v.cod_cab,v.num_det, m.cod_seccion,m.orden')  CT (iden text , v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text "
                + ");";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String p01 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String p02 = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String p03 = (ao[3] != null ? String.valueOf(ao[3]) : "");
                String p04 = (ao[4] != null ? String.valueOf(ao[4]) : "");
                String p05 = (ao[5] != null ? String.valueOf(ao[5]) : "");
                String p06 = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String p07 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String p08 = (ao[8] != null ? String.valueOf(ao[8]) : "");
                String p09 = (ao[9] != null ? String.valueOf(ao[9]) : "");
                String p10 = (ao[10] != null ? String.valueOf(ao[10]) : "");
                String p11 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String p12 = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String p13 = (ao[13] != null ? String.valueOf(ao[13]) : "");
                String p14 = (ao[14] != null ? String.valueOf(ao[14]) : "");
                String p15 = (ao[15] != null ? String.valueOf(ao[15]) : "");
                String p16 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String p17 = (ao[17] != null ? String.valueOf(ao[17]) : "");
                String p18 = (ao[18] != null ? String.valueOf(ao[18]) : "");
                String p19 = (ao[19] != null ? String.valueOf(ao[19]) : "");
                CaptElementoControl ec = new CaptElementoControl(numero, p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }

    private String updateVacios(Integer codCab) {
        String sql = "";
        sql = sql + "update captura.capt_detalle_v set valor=replace(valor,'',null) where valor='' and cod_cab=" + codCab + ";";
        return sql;
    }

    public void updateVaciosFrmEnsanut(Integer codCab) throws Exception {
        //System.out.println("actualiza vacios");
        String sql = updateVacios(codCab);
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }
    
    public List<CaptElementoControl> listarElementosControlCodAsisPorCab(Integer codCab) throws Exception {
        List<CaptElementoControl> rslt = new ArrayList<>();
        String sql = " SELECT * FROM CROSSTAB "
                + "('SELECT  cast(v.cod_cab as text) || v.num_det, v.detv_id, v.valor "
                + "FROM captura.capt_detalle_v v, metadato.met_variable m "
                + "WHERE v.cod_cab = " + codCab 
                + " and m.identificador in (''CPV_S3_012_5_input'',''CPV_S4_008'',''CPV_S4_008_1_input'',''CPV_S4_008_2_input'',''CPV_S4_008_3_input'',''CPV_S4_008_4_input'',''CPV_S4_009'',''CPV_S4_009_1_input'',''CPV_S4_009_2_input'',''CPV_S4_009_3_input'',''CPV_S4_009_4_input'',''CPV_S4_010'',''CPV_S4_010_1_input'',''CPV_S4_010_2_input'',''CPV_S4_010_3_input'',''CPV_S4_010_4_input'',''CPV_S4_012_input'',''CPV_S4_014_input'',''CPV_S4_015_input'',''CPV_S4_025_input'' ,''CPV_S4_026_input'') "
                + "and v.cod_var = m.id_var "
                + "ORDER BY v.cod_cab, v.num_det, m.cod_seccion, m.orden')  CT (iden text, v001 text, v002 text, v003 text, v004 text, v005 text, v006 text, v007 text, v008 text, v009 text, v010 text, v011 text, v012 text, v013 text, v014 text, v015 text, v016 text, v017 text, v018 text, v019 text, v020 text, v021 text);";
        Query q = em.createNativeQuery(sql);
        List<Object[]> lo = q.getResultList();
        if (!lo.isEmpty()) {
            int i = 1;
            for (Object[] ao : lo) {
                int numero = i;
                String s3p12 = (ao[1] != null ? String.valueOf(ao[1]) : "");
                String p8 = (ao[2] != null ? String.valueOf(ao[2]) : "");
                String p8_1 = (ao[3] != null ? String.valueOf(ao[3]) : "") + (ao[4] != null ? String.valueOf(ao[4]) : "") + (ao[5] != null ? String.valueOf(ao[5]) : "");
                String p8_2 = (ao[6] != null ? String.valueOf(ao[6]) : "");
                String p9 = (ao[7] != null ? String.valueOf(ao[7]) : "");
                String p9_1 = (ao[8] != null ? String.valueOf(ao[8]) : "") + (ao[9] != null ? String.valueOf(ao[9]) : "") + (ao[10] != null ? String.valueOf(ao[10]) : "");
                String p9_2 = (ao[11] != null ? String.valueOf(ao[11]) : "");
                String p10 = (ao[12] != null ? String.valueOf(ao[12]) : "");
                String p10_1 = (ao[13] != null ? String.valueOf(ao[13]) : "") + (ao[14] != null ? String.valueOf(ao[14]) : "") + (ao[15] != null ? String.valueOf(ao[15]) : "");
                String p10_2 = (ao[16] != null ? String.valueOf(ao[16]) : "");
                String p12 = (ao[17] != null ? String.valueOf(ao[17]) : "");
                String p14 = (ao[18] != null ? String.valueOf(ao[18]) : "");
                String p15 = (ao[19] != null ? String.valueOf(ao[19]) : "");
                String p25 = (ao[20] != null ? String.valueOf(ao[20]) : "");
                String p26 = (ao[21] != null ? String.valueOf(ao[21]) : "");
                CaptElementoControl ec = new CaptElementoControl(numero, s3p12, p8, p8_1, p8_2, p9, p9_1, p9_2, p10, p10_1, p10_2, p12, p14, p15, p25, p26, null, null);
                rslt.add(ec);
                i++;
            }
        }
        return rslt;
    }
    
    public List<CaptDetalleV> listaValoresVariableXCab(Integer codCab , Integer idVar) throws Exception {
        String sql = "select o from CaptDetalleV o "
                + "where o.codCab.idCab =:codCab and "
                + "o.codVar.idVar =:idVar ORDER BY  numDet";
        Query q = em.createQuery(sql);
        q.setParameter("codCab", codCab).setParameter("idVar", idVar);
        List<CaptDetalleV> lst = q.getResultList();
        return lst;
    }
}
