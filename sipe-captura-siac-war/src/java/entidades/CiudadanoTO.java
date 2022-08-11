/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author cajila
 */
public class CiudadanoTO {
    private CeduladoReq cedulado;
    private String respuesta;

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public CiudadanoTO(CeduladoReq cedulado) {
        this.cedulado = cedulado;
    }

    
    public CiudadanoTO() {
    }
    

    public CeduladoReq getCedulado() {
        return cedulado;
    }

    public void setCedulado(CeduladoReq cedulado) {
        this.cedulado = cedulado;
    }
    
    public class CeduladoReq {

    private String cedula;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String fechaNacimiento;
    private String codLugarNacimiento;
    private String codSexo;
    private String vTecnica;
    private String fechaExpedicionCed;

    public CeduladoReq() {
    }

    public CeduladoReq(String cedula, String nombre1, String nombre2, String apellido1, String apellido2, 
            String fechaNacimiento, String codLugarNacimiento, String codSexo, String vTecnica, String fechaExpedicionCed) {
        this.cedula = cedula;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.codLugarNacimiento = codLugarNacimiento;
        this.codSexo = codSexo;
        this.vTecnica = vTecnica;
        this.fechaExpedicionCed = fechaExpedicionCed;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
        
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCodLugarNacimiento() {
        return codLugarNacimiento;
    }

    public void setCodLugarNacimiento(String codLugarNacimiento) {
        this.codLugarNacimiento = codLugarNacimiento;
    }

    public String getCodSexo() {
        return codSexo;
    }

    public void setCodSexo(String codSexo) {
        this.codSexo = codSexo;
    }

    public String getvTecnica() {
        return vTecnica;
    }

    public void setvTecnica(String vTecnica) {
        this.vTecnica = vTecnica;
    }

    public String getFechaExpedicionCed() {
        return fechaExpedicionCed;
    }

    public void setFechaExpedicionCed(String fechaExpedicionCed) {
        this.fechaExpedicionCed = fechaExpedicionCed;
    }
}

}
