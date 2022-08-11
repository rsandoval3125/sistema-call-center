/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function ejecutarSaltoTipoRegistro1(evt, paramThis) {
    var tipoRegistro1 = paramThis.value;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode === 13 || charCode === 9) {
        document.getElementById(paramThis.name).style.border = null;
        if (Number(tipoRegistro1) === 1) {
            window.setTimeout(function () {
                document.getElementById('frmCaptura:tipoRegistro2Viv').focus();
            }, 0);

            fn_siguiente("", "tipoRegistro2Viv");
        } else if (Number(tipoRegistro1) === 2) {
            //document.getElementById('frmCaptura:tipoEdificio').value=null;
            $('select[id$="tipoEdificio"]').val('0');
            $('select[id$="tipoRegistro"]').css('background', 'transparent');
            $('select[id$="tipoEdificio"]').css('background', 'transparent');
            window.setTimeout(function () {
                document.getElementById('frmCaptura:tipoRegistro2Viv').focus();
            }, 0);

            fn_siguiente("", "tipoRegistro2Viv");
        } else if (Number(tipoRegistro1) === 3) {
            //document.getElementById('frmCaptura:tipoEdificio').value=null;
            $('select[id$="tipoEdificio"]').val('0');
            $('select[id$="tipoRegistro"]').css('background', 'transparent');
            $('select[id$="tipoEdificio"]').css('background', 'transparent');
            window.setTimeout(function () {
                document.getElementById('frmCaptura:tipoVivienda').focus();
            }, 0);

            fn_siguiente("", "tipoVivienda");
        } else {
            return false;
        }

    } else {
        return true;
    }
}
function fn_siguiente(id, siguienteId) {
    IDSALTO = siguienteId;
    $('select[id$="' + siguienteId + '"]').attr('disabled', false);
    $('select[id$="' + siguienteId + '"]').css({"background-color": "#81F781"}).focus();
    $('[id$="' + siguienteId + '"]').css({"color": "#000000"});

}



var nomTbComponente;
var valorTbComponente;

function evaluaComponente(ingresaValor) {
    nomTbComponente = ingresaValor.name;
    valorTbComponente = ingresaValor.value;
    evaluarControlesEntreMiembros();
}


function evaluarControlesEntreMiembros() {
    try {

        if ((nomTbComponente === 'frmCaptura:f1:F1_S2_7')) {
            //COMBO DE PARENTESCO
            var numEleActual = document.getElementById('frmCaptura:lblElemento').innerHTML;
            var sexoJefe = document.getElementById('frmCaptura:lstElementosControl:0:eleSex').innerHTML;
            var edadJefe = document.getElementById('frmCaptura:lstElementosControl:0:eleEdad').innerHTML;
            var tieneError = false;
            var mensajeError = '';
            if (Number(numEleActual) === Number(1)) {
                if (parseInt(valorTbComponente) !== 1) {
                    mensajeError = 'Primer elemento debe ser Jefe de Hogar';
                    tieneError = true;
                }
            } else {
                if (parseInt(valorTbComponente) === 1) {
                    mensajeError = 'Solo el primer elemento debe ser Jefe de Hogar';
                    tieneError = true;
                } else if (parseInt(valorTbComponente) === 2) {
                    var sexo = document.getElementById('frmCaptura:f1:F1_S2_2').value;
                    if (sexoJefe === 'M' && Number(sexo) === 2) {
                        mensajeError = 'Jefe y Conyuge del mismo sexo';
                        tieneError = true;
                    }
                    if (sexoJefe === 'H' && Number(sexo) === 1) {
                        mensajeError = 'Jefe y Conyuge del mismo sexo';
                        tieneError = true;
                    }
                } else if (parseInt(valorTbComponente) === 3) {
                    var edadHijo = document.getElementById('frmCaptura:f1:F1_S2_3_1').value;
                    var dif = Number(edadJefe) - Number(edadHijo);
                    if (dif < 13) {
                        mensajeError = 'Diferencia de edad en Jefe e Hijo menor a 13 años';
                        tieneError = true;
                    }
                } else if (parseInt(valorTbComponente) === 5) {
                    var edadNieto = document.getElementById('frmCaptura:f1:F1_S2_3_1').value;
                    var dif = Number(edadJefe) - Number(edadNieto);
                    if (dif < 25) {
                        mensajeError = 'Diferencia de edad en Jefe y Nieto menor a 25 años';
                        tieneError = true;
                    }
                }
            }
            if (tieneError === true) {
                alert(mensajeError);
                document.getElementById(nomTbComponente).value = '';
                document.getElementById(nomTbComponente).focus();
            }
        } else if ((nomTbComponente === 'frmCaptura:f1:F1_S2_16')) {
            //COMBO DE ESTADO CIVIL
            var numEleActual = document.getElementById('frmCaptura:lblElemento').innerHTML;
            if (Number(numEleActual) === Number(2)) {
                var ecJefe = document.getElementById('frmCaptura:lstElementosControl:0:eleEcivil').innerHTML;
                if (ecJefe !== null) {
                    var parAct = document.getElementById('frmCaptura:f1:F1_S2_7').value;
                    if (Number(parAct) === Number(2)) {
                        //if ((parseInt(valorTbComponente) === Number(1)) || Number(ecJefe) === Number(1)) {
                        if (parseInt(valorTbComponente) !== Number(ecJefe)) {
                            alert("Cónyuge con estado Civil diferente del Jefe de hogar");
                            document.getElementById(nomTbComponente).value = '';
                            document.getElementById(nomTbComponente).focus();
                        }
                        //}
                    }
                }
            }
        }
    } catch (e) {

    }
}

function scrollComponente(idComponente, booleanTop) {
    var element = document.getElementById(idComponente);
    element.scrollIntoView(booleanTop);
}

function guardarDesdeObservacionAmanzanado(idForm,event) {
    if (Number(document.getElementById('frmCaptura:f' + idForm + ':PAF1_S1_TIP_REG2').value) !== 1) {
        return ejecutarRCEnterHacia(event, rcGuardarReg);
    }
}

function guardarDesdeObservacionDisperso(idForm,event) {
    if (Number(document.getElementById('frmCaptura:f' + idForm + ':PDF1_S1_TIP_REG2').value) !== 1) {
        return ejecutarRCEnterHacia(event, rcGuardarReg);
    }
}

function ejecutarSaltoDesdeTipoReg1(evt, paramThis) {
    var tipoRegistro1 = paramThis.value;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode === 13 || charCode === 9) {
        if (Number(tipoRegistro1) === 1 || Number(tipoRegistro1) === 2 ) {
            document.getElementById('frmCaptura:tipoRegistro2Viv').value='';
            document.getElementById('frmCaptura:tipoRegistro2Viv').focus();
            return false;
        } else if (Number(tipoRegistro1) === 3) {
            document.getElementById('frmCaptura:btnCrearRegistro').style.border = '5px solid #00A337';
            document.getElementById('frmCaptura:btnCrearRegistro').focus();
            return false;
        } else {
            return false;
        }
    } else {
        return true;
    }
}

function ejecutarSaltoDesdeTipoReg2(evt, paramThis) {
    var tipoRegistro2 = paramThis.value;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode === 13 || charCode === 9) {
        if (Number(tipoRegistro2) === 1 || Number(tipoRegistro2) === 2 ) {
            document.getElementById('frmCaptura:btnCrearRegistro').style.border = '5px solid #00A337';
            document.getElementById('frmCaptura:btnCrearRegistro').focus();
            return false;
        } else {
            return false;
        }
    } else {
        return true;
    }
}
