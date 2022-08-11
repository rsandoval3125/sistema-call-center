/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var dato=[];
var areglo=[];

var tipos=[];
var periodos=[];
var efe=[];

function cargarGrafico(){
    
     var datos=$.parseJSON(document.getElementById('frmExporBases:datoJSON').value);
     console.log(document.getElementById('frmExporBases:datoJSON').value);
     console.log(datos);
        
         
         console.log(dato);
          
          if(datos[0].hasOwnProperty("zonal")){

                        datos.forEach(cargarPeriodos);
function myFunction(item, index) {


for (var i = 0; i < periodos.length; i++){ 
 
  if(item.periodo===periodos[i] && item.tipo==="Efectividad Originales"){
efe.push(item.porc_efectividad);
 }
if(item.periodo===periodos[i] && item.tipo==="Efectividad Total"){
areglo.push(item.porc_efectividad);}

}
}

function cargarPeriodos(item, index) {

 if(!periodos.includes(item.periodo)){
periodos.push(item.periodo);
// dato.push({"name":item.nombre,"data":areglo});
}

 
}
console.log(periodos);

datos.forEach(myFunction);
dato.push({"name":"Efectividad Originales","data":efe});
 dato.push({"name":"Efectividad Total","data":areglo});
console.log(dato);

var chart = Highcharts.chart('contenedorGrafico', {

    chart: {
        type: 'column'
    },

    title: {
        text: ''
    },

    subtitle: {
        text: ''
    },

    legend: {
        align: 'right',
        verticalAlign: 'middle',
        layout: 'vertical'
    },

    xAxis: {
        categories:periodos,
        title: {
            text: 'Periodo'
        },
        labels: {
            x: -10
        }
    },

    yAxis: {
        allowDecimals: true,
        title: {
            text: ' '
        }
    },

    series:dato,

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                },
                yAxis: {
                    labels: {
                        align: 'left',
                        x: 0,
                        y: -5
                    },
                    title: {
                        text: null
                    }
                },
                subtitle: {
                    text: null
                },
                credits: {
                    enabled: false
                }
            }
        }]
    }
});
          }
          
          
          
          if(datos[0].hasOwnProperty("formulario")){
                var arregloC=[];
                var arregloSe=[];
                var arregloSo=[];
                for (var i = 0; i < datos.length; i++){
                         
                               
                                    if(datos[i].formulario===2) {
                                        
                                       arregloC.push(datos[i].porcentaje); 
                                      
                                    }
                                    if(datos[i].formulario===3) {
                                         
                                       arregloSe.push(datos[i].porcentaje); 
                                    
                                    }
                                    if(datos[i].formulario===4) {
                                        
                                       arregloSo.push(datos[i].porcentaje); 
                                    
                                    }    
                            } 
                            
                             
            
                            var chart = Highcharts.chart('contenedorGrafico', {
                                
                                    chart: {
                                        type: 'column'
                                    },

                                    title: {
                                        text: ''
                                    },

                                    subtitle: {
                                        text: ''
                                    },

                                    legend: {
                                        align: 'right',
                                        verticalAlign: 'middle',
                                        layout: 'vertical'
                                    },

                                    xAxis: {
                                        categories: ['Completa','Rechazo','Incompleta','Mujer no ubicada'],
                                        labels: {
                                            x: -10
                                        }
                                    },

                                    yAxis: {
                                        allowDecimals: false,
                                        title: {
                                            text: ''
                                        }
                                    },
                                   
//                                   
                                         series: [{
                                                name: 'F2 - Mujeres Casadas',
                                                data: arregloC
                                            }, {
                                                name: 'F3 - Muejeres Separadas',
                                                data: arregloSe
                                            }, {
                                                name: 'F4 - Mujeres Solteras',
                                                data: arregloSo
                                            }],
                                        

                                    responsive: {
                                        rules: [{
                                            condition: {
                                                maxWidth: 500
                                            },
                                            chartOptions: {
                                                legend: {
                                                    align: 'center',
                                                    verticalAlign: 'bottom',
                                                    layout: 'horizontal'
                                                },
                                                yAxis: {
                                                    labels: {
                                                        align: 'left',
                                                        x: 0,
                                                        y: -5
                                                    },
                                                    title: {
                                                        text: null
                                                    }
                                                },
                                                subtitle: {
                                                    text: null
                                                },
                                                credits: {
                                                    enabled: false
                                                }
                                            }
                                        }]
                                    }
                                });
          }
          
          
          
          if(datos[0].hasOwnProperty("descripción") && datos[0].hasOwnProperty("porcentaje") ){
              dato=[];
 for (var i = 0; i < datos.length; i++){
   areglo=[];
   
  console.log(datos[i]);
  if(datos[i].porcentaje!==100.00){
areglo.push(datos[i].porcentaje);
 dato.push({"name":datos[i].descripción,"data":areglo});
  }
}
                           var chart = Highcharts.chart('contenedorGrafico', {

    chart: {
        type: 'column'
    },

    title: {
        text: ''
    },

    subtitle: {
        text: ''
    },

    legend: {
        align: 'right',
        verticalAlign: 'middle',
        layout: 'vertical'
    },

    xAxis: {
        categories: [' '],
        labels: {
            x: -10
        }
    },

    yAxis: {
        allowDecimals: true,
        title: {
            text: ' '
        }
    },

    series:dato,

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                },
                yAxis: {
                    labels: {
                        align: 'left',
                        x: 0,
                        y: -5
                    },
                    title: {
                        text: null
                    }
                },
                subtitle: {
                    text: null
                },
                credits: {
                    enabled: false
                }
            }
        }]
    }
}
);
          }
                              
      if(datos[0].hasOwnProperty("número Viviendas") && datos[0].hasOwnProperty("resultado") ){
          dato=[];
     for (var i = 0; i < datos.length; i++){
   areglo=[];
    
     console.log(datos[i]);
   areglo.push(datos[i]['número Viviendas']);
     dato.push({"name":datos[i].resultado,"data":areglo});
}
                           var chart = Highcharts.chart('contenedorGrafico', {

    chart: {
        type: 'column'
    },

    title: {
        text: ''
    },

    subtitle: {
        text: ''
    },

    legend: {
        align: 'right',
        verticalAlign: 'middle',
        layout: 'vertical'
    },

    xAxis: {
        categories: [' '],
        labels: {
            x: -10
        }
    },

    yAxis: {
        allowDecimals: true,
        title: {
            text: 'Cantidad'
        }
    },

    series:dato,

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                },
                yAxis: {
                    labels: {
                        align: 'left',
                        x: 0,
                        y: -5
                    },
                    title: {
                        text: null
                    }
                },
                subtitle: {
                    text: null
                },
                credits: {
                    enabled: false
                }
            }
        }]
    }
}
);
          }
}

