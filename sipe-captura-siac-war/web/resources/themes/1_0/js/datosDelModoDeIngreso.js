let codigo = "";
let cntLin = 0;
let tipCod = 0; // Valores posibles: 1-CEDULA ECUATORIANA, 2-MRZ 2 (Pasaporte), 3-MRZ 3

function init(){
	codigo = "";
	cntLin = 0;
	tipCod = 0;
}

function identificacion(){

	codigo = $('#frm\\:codigo').val().trim();
	
	let lineas = codigo.split("\n");
	
	cntLin = lineas.length;
	
	if(codigo.length != 0) tipCod = cntLin;
}

function chequeoDeSeguridad(){
	
	switch(tipCod){
	case 1: chequeoDeCedulaEcuatoriana();
	break;
	case 2: chequeoDeMRZ2();
	break;
	case 3: chequeoDeMRZ3();
	}
}

function procesamientoDeDatos(){
	identificacion();
	chequeoDeSeguridad();
}