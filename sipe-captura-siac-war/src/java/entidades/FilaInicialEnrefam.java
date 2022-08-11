/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;


import ec.gob.inec.captura.clases.CaptVarV;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class FilaInicialEnrefam {
    
    private String nombre1;
     private String nombre2;
      private String apellido1;
       private String apellido2;
       private Integer sexo;
       private Integer edad;
       private Integer parentesco;
       private Integer seguro1;
       private Integer seguro2;
        private Integer etnia;
         private String etnia2;
        private Integer estado;
        private List<CaptVarV> listaDetalles;
 public FilaInicialEnrefam() {
         listaDetalles=new ArrayList<CaptVarV>();
    }
     
    public void trasladarValoresADetalle(){
     
        if(!listaDetalles.isEmpty()){
           
          
                listaDetalles.get(0).setValor(nombre1);
           
                listaDetalles.get(1).setValor(nombre2);
                listaDetalles.get(2).setValor(apellido1);
                listaDetalles.get(3).setValor(apellido2);
                listaDetalles.get(4).setValor((sexo==null)?null:sexo.toString());
                listaDetalles.get(5).setValor((edad==null)?null:edad.toString());
                listaDetalles.get(6).setValor((parentesco==null)?null:parentesco.toString());
                listaDetalles.get(7).setValor((seguro1==null)?null:seguro1.toString());
                listaDetalles.get(8).setValor((seguro2==null)?null:seguro2.toString());
                  listaDetalles.get(9).setValor((etnia==null)?null:etnia.toString());
                  listaDetalles.get(10).setValor((etnia2));
                  listaDetalles.get(11).setValor((estado==null)?null:estado.toString());
            
        }
    }
    
      public void trasladarDetalleAValores(){
     
        if(!listaDetalles.isEmpty()){
          
          nombre1=listaDetalles.get(0).getValor();
          nombre2=listaDetalles.get(1).getValor();
          apellido1=listaDetalles.get(2).getValor();
         apellido2=listaDetalles.get(3).getValor();
         sexo=(Objects.isNull(listaDetalles.get(4).getValor()) || listaDetalles.get(4).getValor().isEmpty())?null:Integer.valueOf(listaDetalles.get(4).getValor());
          edad=(Objects.isNull(listaDetalles.get(5).getValor()) || listaDetalles.get(5).getValor().isEmpty())?null:Integer.valueOf(listaDetalles.get(5).getValor());
           parentesco=(Objects.isNull(listaDetalles.get(6).getValor()) || listaDetalles.get(6).getValor().isEmpty())?null:Integer.valueOf(listaDetalles.get(6).getValor());
         seguro1=(Objects.isNull(listaDetalles.get(7).getValor()) || listaDetalles.get(7).getValor().isEmpty())?null:Integer.valueOf(listaDetalles.get(7).getValor());
           seguro2=(Objects.isNull(listaDetalles.get(8).getValor()) || listaDetalles.get(8).getValor().isEmpty())?null:Integer.valueOf(listaDetalles.get(8).getValor());
           etnia=(Objects.isNull(listaDetalles.get(9).getValor()) || listaDetalles.get(9).getValor().isEmpty())?null:Integer.valueOf(listaDetalles.get(9).getValor());
          etnia2=listaDetalles.get(10).getValor();
         estado=(Objects.isNull(listaDetalles.get(11).getValor()) || listaDetalles.get(11).getValor().isEmpty())?null:Integer.valueOf(listaDetalles.get(11).getValor());
          
               
            
        }
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

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getParentesco() {
        return parentesco;
    }

    public void setParentesco(Integer parentesco) {
        this.parentesco = parentesco;
    }

    public Integer getSeguro1() {
        return seguro1;
    }

    public void setSeguro1(Integer seguro1) {
        this.seguro1 = seguro1;
    }

    public Integer getSeguro2() {
        return seguro2;
    }

    public void setSeguro2(Integer seguro2) {
        this.seguro2 = seguro2;
    }

   

    public Integer getEtnia() {
        return etnia;
    }

    public void setEtnia(Integer etnia) {
        this.etnia = etnia;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getEtnia2() {
        return etnia2;
    }

    public void setEtnia2(String etnia2) {
        this.etnia2 = etnia2;
    }

    public List<CaptVarV> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<CaptVarV> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }


 

    @Override
    public String toString() {
        return "FilaInicialEnrefam{" + "nombre1=" + nombre1 + ", nombre2=" + nombre2 + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", sexo=" + sexo + ", edad=" + edad + ", parentesco=" + parentesco + ", seguro1=" + seguro1 + ", seguro2=" + seguro2 + ", etnia=" + etnia + ", etnia2=" + etnia2 + ", estado=" + estado + ", listaDetalles=" + listaDetalles + '}';
    }

   
       
            
    
}