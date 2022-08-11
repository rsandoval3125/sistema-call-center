function paginacion(args){

	let url = "https://" + window.location.host + "/simiec-consultaSiipne/rest/pasajeros?";

	let datos = new Map();

	let apellidos = $('#form\\:apellidos').val();
	let nombres = $('#form\\:nombres').val();
	let fechaDeNacimiento = $('#form\\:fechaDeNacimiento_input').val();
	let tipoDeDocumento = $('#form\\:tipoDeDocumento_input').val();
	let numeroDeDocumento = $('#form\\:numero').val();

	let yaHayParametro = false;
	
	if(!!apellidos){
		url = url + "apellidos=" + escape(apellidos);
		yaHayParametro = true;
	}

	if(!!nombres){
		if(yaHayParametro) url = url + "&";
		url = url + "nombres=" + escape(nombres);
		yaHayParametro = true;
	}

	if(!!fechaDeNacimiento){
		if(yaHayParametro) url = url + "&";
		url = url + "fechaDeNacimiento=" + escape(fechaDeNacimiento);
		yaHayParametro = true;
	}

	if(!!tipoDeDocumento){
		if(yaHayParametro) url = url + "&";
		url = url + "tipoDeDocumento=" + escape(tipoDeDocumento);
		yaHayParametro = true;
	}

	if(!!numeroDeDocumento){
		if(yaHayParametro) url = url + "&";
		url = url + "numeroDeDocumento=" + escape(numeroDeDocumento);
		yaHayParametro = true;
	}

	let pagina = args[0]['value'];	
	if(!!args){ 
		if(!!args[0]){ 
			if(!!pagina){
				if(yaHayParametro) url = url + "&";
				url = url + "pagina=" + escape(pagina);
			}
		}
	}
	
	$.ajax({url: url,
		beforeSend: function(){
			PF('peBlockUI').block();
		}
	})
	.done(function(json){
		
		let datos = json['datos'];

		let dlength = datos.length;
		
		// Se eliminan las filas;
		tbody = $('table[id="dtable"] > tbody').empty();
		
		// Creación del contenido de la tabla
		for(var i = 0; i < dlength; i++){
			tbody.append("" +
				"<tr data-ri='0' class='ui-widget-content' role='row'>"
				+ "<td role='gridcell' class='col-d-num'>" + (pagina*10 + i + 1) + "</td>"
				+ "<td role='gridcell'>" + (!!datos[i]['apellidos'] ? datos[i]['apellidos'] : '') + "</td>"
				+ "<td role='gridcell'>" + (!!datos[i]['nombres'] ? datos[i]['nombres'] : '') + "</td>"
				+ "<td role='gridcell'>" + (!!datos[i]['fechaDeNacimiento'] ? datos[i]['fechaDeNacimiento'] : '') + "</td>"
				+ "<td role='gridcell'>" + (!!datos[i]['nacionalidad'] ? datos[i]['nacionalidad'] : '') + "</td>"
				+ "<td role='gridcell'>" + (!!datos[i]['docs'] ? datos[i]['docs'] : '') + "</td>"
				+ "<td role='gridcell' class='columna-acciones'>"
				
				+ "<button class='ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only btn btn-outline btn-md btn-acciones' onclick=\"PrimeFaces.bcn(this,event,[function(event){datos([{name: 'id', value: '" + datos[i]['id'] + "'}])},function(event){PrimeFaces.ab({});return false;}]);\" title='Datos' type='submit' role='button' aria-disabled='false'>"
				+ "<span class=\"ui-button-icon-left ui-icon ui-c fa fa-search\"></span>"
				+ "<span class=\"ui-button-text ui-c\">Datos</span>"
				+ "</button>"
				    
				+ "<button class='ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only btn btn-outline btn-sm btn-acciones' onclick=\"PrimeFaces.bcn(this,event,[function(event){movimientosMigratorios([{name: 'id', value: '" + datos[i]['id'] + "'}])},function(event){PrimeFaces.ab({});return false;}]);\" title='Movimientos Migratorios' type='submit' role='button' aria-disabled='false'>"
				+ "<span class='ui-button-icon-left ui-icon ui-c fa fa-suitcase'></span>"
				+ "<span class='ui-button-text ui-c'>Movimientos Migratorios</span>"
				+ "</button>"
				    
				+ "<button class='ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only btn btn-outline btn-sm btn-acciones' onclick=\"PrimeFaces.bcn(this,event,[function(event){alertas([{name: 'id', value: '" + datos[i]['id'] + "'}])},function(event){PrimeFaces.ab({});return false;}]);\" title='Alertas' type='submit' role='button' aria-disabled='false'>"
				+ "<span class='ui-button-icon-left ui-icon ui-c fa fa-exclamation-triangle'></span>"
				+ "<span class='ui-button-text ui-c'>Alertas</span>"
				+ "</button>"
				
				+ "</td>"
				+ "</tr>");
		}
		
		let cantidadTotalDeResultados = json['cantidadTotal'];
		let cantidadDePaginas = Math.floor(cantidadTotalDeResultados/10);
		let q = cantidadTotalDeResultados % 10;
		
		if(q != 0) cantidadDePaginas = cantidadDePaginas + 1;
		
		// Se eliminan los botones
		spanBotones = $('.ui-paginator-pages').empty();
		
		// Se habilitan los botones extremos
		$('.btn-ctrl-pag').removeClass('ui-state-disabled');
		
		let seDebeDeshabilitar = false;
		
		// Creación de los botones
		for(var i = 0; i < dlength; i++){
			
			seDebeDeshabilitar = (pagina == 'F' || pagina == i || pagina == 'L');
			
			spanBotones.append("<a class=\"ui-paginator-page ui-state-default ui-corner-all " + (seDebeDeshabilitar ? ' ui-state-disabled' : '' ) + " \" "
				+ "aria-label=\"Página " + (i+1) + "\" "
				+ "href='#' "
				+ (!seDebeDeshabilitar ? " onclick=\" paginacion([{name: 'pagina', value: '" + i + "'}]); \" " : "" ) + ">" + (i + 1) + "</a>");
		}
		
		// Lógica de bloqueo/desbloqueo de botones extremos
		if(pagina == (cantidadDePaginas - 1)){ 
			$('#btn-ctrl-pag-L').addClass('ui-state-disabled');
			$('#btn-ctrl-pag-N').addClass('ui-state-disabled');
		}
		if(pagina == 0){
			$('#btn-ctrl-pag-F').addClass('ui-state-disabled');
			$('#btn-ctrl-pag-P').addClass('ui-state-disabled');
		}
		
		$.unblockUI();
	})
	.fail(function( jqxhr, textStatus, error ) {
	    var err = textStatus + ", " + error;
	    console.log( "Request Failed: " + err );
	});
}