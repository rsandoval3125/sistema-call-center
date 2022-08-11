/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.metadato.clases;

/**
 *
 * @author jcerda
 */
public class Condicional {
    
    private String identificador1;
    private String identificador2;
    private String operador;
    private String valorPregunta;
    private String conector;

    public Condicional() {

    }

    public Condicional(String operador) {
        this.operador = operador;
    }

    public Condicional(String identificador1,String identificador2,String operador, String valorPregunta) {

        this.identificador1=identificador1;
        this.identificador2=identificador2;
        this.operador = operador;
        this.valorPregunta = valorPregunta;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getValorPregunta() {
        return valorPregunta;
    }

    public void setValorPregunta(String valorPregunta) {
        this.valorPregunta = valorPregunta;
    }

    public String getConector() {
        return conector;
    }

    public void setConector(String conector) {
        this.conector = conector;
    }

    public String getIdentificador1() {
        return identificador1;
    }

  
    public void setIdentificador1(String identificador1) {
        this.identificador1 = identificador1;
    }

    public String getIdentificador2() {
        return identificador2;
    }

    public void setIdentificador2(String identificador2) {
        this.identificador2 = identificador2;
    }

}
