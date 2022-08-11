/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var datos = [];
var valNombre;
var valApellido;
var valEdad;
var valParentesco;
var valSeg1;
var valSeg2;
var valEstadoCivil;
var nomCompActual;
var valCompActual;
var valProximoSalto;
var colorComActual;
var colorComProximo;
var inputedadPadre;
var valCompEdadPadre;
var inputedadHijo;
var inputedadNieto;
var filaCompActual;

var LETRAS = " áéíóúabcdefghijklmnñopqrstuvwxyz";
var ENTEROS = "0123456789";
var ALFANUMERICOS = " áéíóúabcdefghijklmnñopqrstuvwxyz0123456789";
var ESPECIALES = [8, 9, 39]; //37,46 eliminados
var contador = 2000;
function tempAlert(msg)
{
    $("[id^='alertas']").remove();
    var el = document.createElement("div");
    el.setAttribute("style", "border-style: solid;border-color: coral;z-index:" + contador + ";position:fixed;top:40%;left:20%;background-color:white;");
    el.setAttribute("id", "alertas" + contador);
    contador = contador + 1;
    el.innerHTML = msg;
    setTimeout(function () {
        el.parentNode.removeChild(el);
    }, 6000);
    document.body.appendChild(el);
}
function  extraeNumComponente(nombreComponente) {
    nomComponente = nombreComponente.name;
    //console.log(nomComponente);
    var txt = nomComponente;
    var numb = txt.replace(/[^0-9]/g, '');
    //console.log(numb);
    return numb;
}


function fn_teclaEspeciales(key) {
    var tecla_especial = false;
    if (ESPECIALES.includes(key)) {
        tecla_especial = true;
    }
    return tecla_especial;
}

function fn_letras(e, idPreguntaSig) {

    var key = e.which;
    var key2 = e.keyCode;
    var tecla = String.fromCharCode(key).toLowerCase();
    var tecla_especial = fn_teclaEspeciales(key2);
    if (LETRAS.indexOf(tecla) === -1 && !tecla_especial) {
        if (key === 13 || key === 9) {
            cargarSalto(idPreguntaSig);
            return false;
        }
        return false;
    }


}
function cargarSalto(ingresaValor) {
    colorComActual = "5px solid #ed9c11";//Tomate
    nomCompActual = ingresaValor.name;
    valCompActual = ingresaValor.value;
    filaCompActual = extraeNumComponente(ingresaValor);
    //console.log(document.getElementById('frmCaptura:tblCovid:0:inputparentesco').value);


    document.getElementById(nomCompActual).style.border = colorComActual;
    //Primer Nombre


    if (nomCompActual.indexOf('inputnombreU') !== -1) {
        valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputnombreD';
        document.getElementById(nomCompActual).style.border = null;
    }
    //Segundo nombre
    if (nomCompActual.indexOf('inputnombreD') !== -1) {
        valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputapellidoU';
        document.getElementById(nomCompActual).style.border = null;
    }

    //Primer Apellido
    if (nomCompActual.indexOf('inputapellidoU') !== -1) {
        valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputapellidoD';
        document.getElementById(nomCompActual).style.border = null;
    }
    //Segundo Apellido
    if (nomCompActual.indexOf('inputapellidoD') !== -1) {
        valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputparentesco';
        document.getElementById(nomCompActual).style.border = null;
    }

    //Parentesco
    if (nomCompActual.indexOf('inputparentesco') !== -1) {
        if ((valCompActual < 14 && valCompActual > 0)) {
            valParentesco = ingresaValor.value;
            console.log(validaccionesVarParentesco(extraeNumComponente(ingresaValor)));
            valProximoSalto = validaccionesVarParentesco(extraeNumComponente(ingresaValor));
            //valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputsexo';

            document.getElementById(nomCompActual).style.border = null;
        } else if (valCompActual > 13 || valCompActual <= 0) {

            valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputparentesco';
            document.getElementById('frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputparentesco').value = null;
            document.getElementById(nomCompActual).style.border = null;

        }

    }

    //Sexo
    if (nomCompActual.indexOf('inputsexo') !== -1) {
        valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputedad';
        document.getElementById(nomCompActual).style.border = null;
    }

    //Edad
    if (nomCompActual.indexOf('inputedad') !== -1) {
        valEdad = ingresaValor.value;
        valCompEdadPadre = ingresaValor.name;

        if (valEdad >= 0 || valEdad < 121) {
            valProximoSalto = 'frmCaptura:tblCovid:' + String(Number(extraeNumComponente(ingresaValor)) + 1) + ':inputnombreU';
            //valProximoSalto = 'frmCaptura:btnFin';
            document.getElementById(nomCompActual).style.border = null;
        }
        if (valEdad < 0 || valEdad > 120) {
            mensajeGrowl([{name: 'y', value: 'Ingrese edad entre 0 a 120 años.'}]);
            valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputedad';
            document.getElementById('frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputedad').value = null;
            document.getElementById(nomCompActual).style.border = null;
        }
        if (Number(extraeNumComponente(ingresaValor)) === 0) {
            inputedadPadre = parseInt(valEdad);
        }
    }

    //seguro 1

    if (nomCompActual.indexOf('inputseguroU') !== -1) {
        if ((valCompActual < 11 && valCompActual > 0)) {
            valSeg1 = ingresaValor.value;
            valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputseguroD';
            document.getElementById(nomCompActual).style.border = null;

        } else if (valCompActual < 1 || valCompActual > 10) {
            valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputseguroU';
            document.getElementById('frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputseguroU').value = null;
            document.getElementById(nomCompActual).style.border = null;
        }
    }

    // seguro 2

    if (nomCompActual.indexOf('inputseguroD') !== -1) {
        segurosOk = Boolean();

        if ((valCompActual < 11 && valCompActual > 0)) {
            valSeg2 = ingresaValor.value;

            if (Number(valSeg1) > 0 && Number(valSeg1) < 10) {
                if (Boolean(validarSeguros()) === false) {
                    segurosOk = Boolean(false);

                    valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputseguroD';
                    document.getElementById('frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputseguroD').value = null;
                    document.getElementById(nomCompActual).style.border = null;
                } else {
                    segurosOk = Boolean(true);
                }
            } else {
                segurosOk = Boolean(true);
            }

        } else if (valCompActual < 1 || valCompActual > 10) {
            valProximoSalto = 'frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputseguroD';
            document.getElementById('frmCaptura:tblCovid:' + extraeNumComponente(ingresaValor) + ':inputseguroD').value = null;
            document.getElementById(nomCompActual).style.border = null;

            segurosOk = Boolean(true);
        }

        if (Boolean(segurosOk) === true) {
            if ((document.getElementById('frmCaptura:tblCovid:' + String(Number(extraeNumComponente(ingresaValor)) + 1) + ':inputnombreU')) !== null) {
                valProximoSalto = 'frmCaptura:tblCovid:' + String(Number(extraeNumComponente(ingresaValor)) + 1) + ':inputnombreU';
                document.getElementById(nomCompActual).style.border = null;
            } else {
                valProximoSalto = 'frmCaptura:btnFin';
                document.getElementById(nomCompActual).style.border = null;
            }
        }
    }

    console.log(valProximoSalto);

    colorComProximo = "5px solid #00A337"; //
    document.getElementById(valProximoSalto).disabled = false;
    document.getElementById(valProximoSalto).focus();
    document.getElementById(valProximoSalto).style.border = colorComProximo;
}

function enterSoloNumeros(evt, idPreguntaSig) {
    
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode === 13 || charCode === 9) {
        cargarSalto(idPreguntaSig);
        return false;
    } else {
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        } else {
            return true;
        }
    }
}

function enterSoloNumerosPorCatalogo(evt, idPreguntaSig, cat) {

    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode === 13 || charCode === 9) {
        cargarSalto(idPreguntaSig);
        return false;
    } else {

        if (charCode > 31 && (charCode < 48 || charCode > 57)) { //48-0 57-9
            return false;
        }
        if (charCode !== 49 && charCode !== 50 && cat === 0) {
            return false;
        }
        if ((charCode < 48 || charCode > 57) && cat === 1) {
            return false;
        }
        if ((charCode < 49 || charCode > 55) && cat === 2) {
            return false;
        } else {
            return true;
        }
    }

}

//function validaccionesVarParentesco(indexComponente) {

//    var proxSalto;
//    try {
//        //var numero=Number(document.getElementById('frmCaptura:ne').value);
//        //console.log(Number(document.getElementById('frmCaptura:ne').value));
//        if (Number(Number(indexComponente)) === Number(0)) {
//            if (Number(valParentesco) >= 1) {
//                if (Boolean(jefeMayorEdad(indexComponente)) === true
//                        && Boolean(esJefeHogar(indexComponente)) === true) {
//                    var num = Number(indexComponente) + 1;
//                    if ((document.getElementById('frmCaptura:tblCovid:' + String(Number(Number(indexComponente))) + 1) + ':inputnombreU') === null) {
//                        proxSalto = 'frmCaptura:btnFin';
//                    } else {
//                        proxSalto = 'frmCaptura:tblCovid:' + String(Number(indexComponente)) + ':inputseguroU';
//                    }
//                } else {
//                    document.getElementById(nomCompActual.toString()).value = '';
//                    proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
//                }
//            } else {
//                proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
//            }
//        } else {
//            if (Number(valParentesco) >= 1) {
//                if (Boolean(jefeMayorEdad(indexComponente)) === true
//                        && Boolean(esJefeHogar(indexComponente)) === true
//                        && Boolean(esConyugueMenorEdad(indexComponente)) === true
//                        && Boolean(jefeHogarRegistrado(indexComponente)) === true
//                        && Boolean(esYernoNueraMenorEdad(indexComponente)) === true
//                        && Boolean(esPadreSuegroMenorEdad(indexComponente)) === true
//                        && Boolean(esEmpDomeMenorEdad(indexComponente)) === true
//                        && Boolean(difrenciaEdadJefeHijo(indexComponente)) === true
//                        && Boolean(diferenciaEdadJefeNieto(indexComponente)) === true
//                        && Boolean(jefeConyugeMismoSexo()) === true
//                        // && Boolean(esOrdenado(indexComponente)) === true
//                        )
//                {
//                    var num = Number(indexComponente);
//                    if ((document.getElementById('frmCaptura:tblCovid:' + String(Number(Number(indexComponente))) + 1) + ':inputnombreU') === null) {
//                        proxSalto = 'frmCaptura:btnFin';
//                    } else {
//                        proxSalto = 'frmCaptura:tblCovid:' + num + ':inputseguroU';
//                    }
//                } else {
//                    document.getElementById(nomCompActual.toString()).value = '';
//                    proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
//                }
//            } else {
//                proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
//            }
//        }
//
//
//    } catch (err) {
//        mensajeGrowl([{name: 'y', value: "Error: " + err}]);
//    }
//    return proxSalto;
//}

function validaccionesVarParentesco(indexComponente) {

    var proxSalto;
    try {
        //var numero=Number(document.getElementById('frmCaptura:ne').value);
        //console.log(Number(document.getElementById('frmCaptura:ne').value));
        if (Number(Number(indexComponente)) === Number(0)) {
            if (Number(valParentesco) >= 1) {
                if (Boolean(jefeMayorEdad(indexComponente)) === true
                        && Boolean(esJefeHogar(indexComponente)) === true) {
                    var num = Number(indexComponente) + 1;
                    if ((document.getElementById('frmCaptura:tblCovid:' + String(Number(Number(indexComponente))) + 1) + ':inputnombreU') === null) {
                        //proxSalto = 'frmCaptura:btnFin';
                        proxSalto = 'frmCaptura:tblCovid:' + String(Number(indexComponente)) + ':inputparentesco';
                    } else {
                        proxSalto = 'frmCaptura:tblCovid:' + String(Number(indexComponente)) + ':inputsexo';
                    }
                } else {
                    document.getElementById(nomCompActual.toString()).value = '';
                    proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
                }
            } else {
                proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
            }
        } else {
            if (Number(valParentesco) >= 1) {
                if (Boolean(jefeMayorEdad(indexComponente)) === true
                        && Boolean(esJefeHogar(indexComponente)) === true
                        //&& Boolean(esConyugueMenorEdad(indexComponente)) === true                        
                        && Boolean(jefeHogarRegistrado(indexComponente)) === true                                               
                        //&& Boolean(esYernoNueraMenorEdad(indexComponente)) === true
                        //&& Boolean(esPadreSuegroMenorEdad(indexComponente)) === true
                        //&& Boolean(esEmpDomeMenorEdad(indexComponente)) === true
                        //&& Boolean(difrenciaEdadJefeHijo(indexComponente)) === true
                        //&& Boolean(diferenciaEdadJefeNieto(indexComponente)) === true
                        //&& Boolean(jefeConyugeMismoSexo()) === true
                        // && Boolean(esOrdenado(indexComponente)) === true
                        )
                {
                    var num = Number(indexComponente);
                    if ((document.getElementById('frmCaptura:tblCovid:' + String(Number(Number(indexComponente))) + 1) + ':inputnombreU') === null) {
                        proxSalto = 'frmCaptura:btnFin';
                    } else {
                        proxSalto = 'frmCaptura:tblCovid:' + num + ':inputsexo';
                    }
                } else {
                    document.getElementById(nomCompActual.toString()).value = '';
                    proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
                }
            } else {
                proxSalto = 'frmCaptura:tblCovid:' + indexComponente + ':inputparentesco';
            }
        }


    } catch (err) {
        mensajeGrowl([{name: 'y', value: "Error: " + err}]);
    }
    return proxSalto;
}

function  esOrdenado(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) > 0) {
        var indexAnt = 0;
        indexAnt = indexComponente - 1;
        if (Number(valParentesco) < Number(document.getElementById('frmCaptura:tblCovid:' + indexAnt + ':inputparentesco').value)) {
            valorRetorno = Boolean(false);
            mensajeGrowl([{name: 'y', value: 'Existe un error en el orden de ingreso de los miembros'}]);
            document.getElementById(nomCompActual.toString()).value = '';
        } else {
            valorRetorno = Boolean(true);
        }
    } else {
        valorRetorno = Boolean(true);
    }

    return valorRetorno;
}

function  esJefeHogar(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) === 0 && Number(valParentesco) !== Number(1)) {
        valorRetorno = Boolean(false);
        mensajeGrowl([{name: 'y', value: 'El 1er miembro en registrar debe ser: Representante del hogar'}]);
        document.getElementById(nomCompActual.toString()).value = '';
    } else {
        if (valCompEdadPadre === 'frmCaptura:tblCovid:0:inputedad') { //Capturo la inputedad del padre
            inputedadPadre = parseInt(valEdad);
        }
        valorRetorno = Boolean(true);
    }

    return valorRetorno;
}

function jefeHogarRegistrado(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) !== 0 && Number(valParentesco) === Number(1)) {
        valorRetorno = Boolean(false);
        mensajeGrowl([{name: 'y', value: 'Representante de Hogar: Ya esta registrado'}]);
        document.getElementById(nomCompActual.toString()).value = '';
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}

function jefeMayorEdad(indexComponente) {
    valorRetorno = Boolean();
    if (Number(valParentesco) === 1 && valEdad < 12 && Number(indexComponente) === 0) {
        valorRetorno = Boolean(true);
        mensajeGrowl([{name: 'y', value: 'Representante de Hogar: Edad menor de 12 años'}]);
        //document.getElementById(nomCompActual.toString()).value = '';
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}

function difrenciaEdadJefeHijo(indexComponente) {
    try {
        valorRetorno = Boolean();
        if (Number(indexComponente) !== 0 && Number(valParentesco) === Number(3)) { //Es hijo
            inputedadHijo = valEdad;
            var difEdadPadreHijo = parseInt(inputedadPadre) - parseInt(inputedadHijo);
            if (difEdadPadreHijo < 13) {
                valorRetorno = Boolean(true);
                mensajeGrowl([{name: 'y', value: "Diferencia de edad entre el  Jefe y el Hijo  es menor de  13 años. ERROR 18"}]);
            } else {
                valorRetorno = Boolean(true);
            }
        } else {
            valorRetorno = Boolean(true);
        }
    } catch (err) {
        mensajeGrowl([{name: 'y', value: "Error: " + err.message}]);
    }
    return valorRetorno;
}

function  diferenciaEdadJefeNieto(indexComponente) {
    try {
        valorRetorno = Boolean();
        if (Number(indexComponente) !== 0 && Number(valParentesco) === Number(5)) { //Es nieto
            inputedadNieto = valEdad;
            var difEdadPadreNieto = parseInt(inputedadPadre) - parseInt(inputedadNieto);
            if (difEdadPadreNieto < 25) {
                valorRetorno = Boolean(true);
                mensajeGrowl([{name: 'y', value: "Diferencia de edad entre el  Jefe y el Nieto/a  es menor de  25 años. ERROR 20"}]);
            } else {
                valorRetorno = Boolean(true);
            }
        } else {
            valorRetorno = Boolean(true);
        }
    } catch (err) {
        mensajeGrowl([{name: 'y', value: "Error: " + err.message}]);
    }
    return valorRetorno;
}

function esConyugueMenorEdad(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) !== 0 && Number(valParentesco) === 2 && valEdad < 12) { //Es conyugue
        valorRetorno = Boolean(true);
        mensajeGrowl([{name: 'y', value: 'Conyugue: Edad menor de 12 años. ERROR 11'}]);
        document.getElementById(nomCompActual.toString()).value = '';
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}
function esJefeMenorEdad(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) !== 0 && Number(valParentesco) === 2 && valEdad < 13) { //Es conyugue
        valorRetorno = Boolean(false);
        mensajeGrowl([{name: 'y', value: 'Conyugue: Edad menor de 12 años'}]);
        document.getElementById(nomCompActual.toString()).value = '';
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}
function esYernoNueraMenorEdad(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) !== 0 && Number(valParentesco) === 4 && valEdad < 12) { //Es conyugue
        valorRetorno = Boolean(false);
        mensajeGrowl([{name: 'y', value: 'Yerno o Nuera: Edad menor de 12 años'}]);
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}



function esPadreSuegroMenorEdad(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) !== 0 && Number(valParentesco) === Number(6) && Number(valEdad) < 25) { //Es Padre suegro
        valorRetorno = Boolean(false);
        mensajeGrowl([{name: 'y', value: 'Padres o suegros: Edad menor 25 a años'}]);
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}
function esEmpDomeMenorEdad(indexComponente) {
    valorRetorno = Boolean();
    if (Number(indexComponente) !== 0 && Number(valParentesco) === Number(8) && valEdad < 10) { //Es emplinputedado domestico
        valorRetorno = Boolean(true);
        mensajeGrowl([{name: 'y', value: 'Empleado/a doméstico:Edad  menor a 10 años. ERROR 15'}]);
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}

function jefeConyugeMismoSexo() {
    valorRetorno = Boolean();
    var valorParentescoMiembro1 = document.getElementById('frmCaptura:tblCovid:0:inputparentesco').value;
    var valorParentescoMiembro2 = document.getElementById('frmCaptura:tblCovid:1:inputparentesco').value;
    if (Number(valorParentescoMiembro1) === Number(1) && Number(valorParentescoMiembro2) === Number(2)) {
        var valorSexoMiembro1H = document.getElementById('frmCaptura:tblCovid:0:inputsexo').value;
        var valorSexoMiembro2M = document.getElementById('frmCaptura:tblCovid:1:inputsexo').value;
        if ((Number(valorSexoMiembro1H) === Number(valorSexoMiembro2M))) {
            valorRetorno = Boolean(false);
            mensajeGrowl([{name: 'y', value: 'Jefe y Cónyuge con mismo sexo. ERROR 8'}]);
        } else {
            valorRetorno = Boolean(true);
        }
    } else {
        valorRetorno = Boolean(true);
    }
    return valorRetorno;
}

function validarSeguros() {
    try {
        if (Number(valSeg1) === Number(valSeg2)) {
            mensajeGrowl([{name: 'y', value: "¡Seguros: Altern. 1 y Altern. 2 deben ser diferentes !"}]);
            return false;
        } else {
            return true;
        }
    } catch (err) {
        mensajeGrowl([{name: 'y', value: "Error: " + err}]);
    }
}

function fn_rangoReemplazo(a, b, c, nombre, dispo) {

    var valor = $('[id$="' + nombre + '"]').val();
    if ((dispo < 1)) {
        mensajeGrowl([{name: 'y', value: "¡No hay mas reemplazos disponibles para este conglomerado!"}]);
        $('[id$="' + nombre + '"]').val('');
        $('[id$="' + nombre + '"]').focus();
        return false;
    } else if (valor !== '' && (valor !== a && valor !== b && valor !== c)) {
        mensajeGrowl([{name: 'y', value: "Valor incorrecto al rango"}]);
        $('[id$="' + nombre + '"]').val('');
        $('[id$="' + nombre + '"]').focus();
        return false;
    }


}

function fn_rangoReemplazo(a, b, c, nombre) {

    var valor = $('[id$="' + nombre + '"]').val();
    if (valor !== '' && (valor !== a && valor !== b && valor !== c)) {
        mensajeGrowl([{name: 'y', value: "Valor incorrecto al rango"}]);
        $('[id$="' + nombre + '"]').val('');
        $('[id$="' + nombre + '"]').focus();
        return false;
    }


}

function fn_rangoReemplazoMulti(a, b, c, d, e, f, g, h, i, j, k, l, nombre) {
    var valor = $('[id$="' + nombre + '"]').val();
    if (valor !== '' && (valor !== a && valor !== b && valor !== c && valor !== d && valor !== e && valor !== f && valor !== g && valor !== h && valor !== i && valor !== j && valor !== k && valor !== l)) {
        mensajeGrowl([{name: 'y', value: "Valor incorrecto al rango"}]);
        $('[id$="' + nombre + '"]').val('');
        $('[id$="' + nombre + '"]').focus();
        return false;
    }
}

function ejecutarSaltoEnterHaciaVacio(evt, idPreguntaSig, idOtraPreguntaSig, nombre) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    console.log(charCode);
    if (charCode === 13 || charCode === 9) {
        if (document.getElementById(nombre.name).value !== '') {
            try {

                $(':focus').blur();
                $('[id$="_S1_OBS"]').off();
                document.getElementById(idPreguntaSig).focus();
                document.getElementById(idPreguntaSig).style.border = "solid #58ACFA";
            } catch (e) {
                $(':focus').blur();
                $('[id$="_S1_OBS"]').off();
                document.getElementById(idOtraPreguntaSig).focus();
            }
            return false;
        } else {


            return false;
        }
    } else {
        return true;
    }

}

function graficarReporte() {
    console.log(document.getElementById('frmExporBases:datoJSON').value);
}
function ejecSaltoEnterHaciaCovIndiceConf(evt, idPreguntaSig, idOtraPreguntaSig) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    console.log(charCode);
    if (charCode === 13 || charCode === 9) {
        try {

            $('[id$="' + idPreguntaSig + '"]').css({"background-color": "#81F781"}).removeAttr('readonly');
            $('[id$="' + idPreguntaSig + '"]').css({"background-color": "#81F781"}).focus();//Color verde pastel #81F781
            $('[id$="' + idPreguntaSig + '"]').css({"background-color": "#81F781"}).select();//Color verde pastel #81F781

            if (idPreguntaSig === 'frmCaptura:f1:btnVGuardarTodo') {
                alert("A finalizado !Guarde los cambios! ");
            }

        } catch (e) {
            $(':focus').blur();
            $('[id$="_S1_OBS"]').off();
            document.getElementById(idOtraPreguntaSig).focus();
        }
        return false;
    } else {
        return true;
    }

}

var ESPECIALES2 = [8, 9, 45];
var ALFANUMERICOS2 = " \x3A\xE1\xE9\xED\xF3\xFAabcdefghijklmn\xF1opqrstuvwxyz0123456789";

function fn_teclaEspeciales2(key) {
    var tecla_especial = false;
    if (ESPECIALES2.includes(key)) {
        tecla_especial = true;
    }
    return tecla_especial;
}

function fn_alfaNumerico2(e) {
    var key = e.which;
    var key2 = e.keyCode;
    var tecla = String.fromCharCode(key).toLowerCase();
    var tecla_especial = fn_teclaEspeciales2(key2);
    if (ALFANUMERICOS2.indexOf(tecla) === -1 && !tecla_especial)
        return false;
}
