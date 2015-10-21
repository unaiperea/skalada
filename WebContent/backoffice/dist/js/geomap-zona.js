var map;
var lat  = document.getElementsByName('latitud')[0].value;
var lng  = document.getElementsByName('longitud')[0].value;
var zoom = 13;

function show_map(localizacion) {
	
	//obtener localizacion
	if(lat == 0.0 && lng == 0.0){
		lat = localizacion.coords.latitude;
		lng = localizacion.coords.longitude;
	}
	var center = new google.maps.LatLng(lat,lng);
	
	//inicializar Mapa
	map = new google.maps.Map(document.getElementById('map'), {
		zoom: zoom,
	   	center: center,
	   	mapTypeId: google.maps.MapTypeId.HYBRID,
		disableDefaultUI: true	   
	});
	
	//marcador
	var circle = new google.maps.Circle({
		strokeColor: '#FF0000',
	    strokeOpacity: 0.8,
	    strokeWeight: 2,
	    fillColor: '#FF0000',
	    fillOpacity: 0.35,
	    map: map,
	    center: center,
	    radius: 1500,	//en metros
	    title: document.getElementsByName("nombre")[0].value,
	    draggable: true
	});
	
	circle.addListener('dragend', function() {
		document.getElementsByName('longitud')[0].value = circle.getCenter().lng();
		document.getElementsByName('latitud')[0].value = circle.getCenter().lat();
	});							
}			
       
function geolocalizarme(){				
	if ( navigator.geolocation ){
		navigator.geolocation.getCurrentPosition(show_map);				
	}else{
		console.error('Geolocation NO soportado');
	}
}	

google.maps.event.addDomListener(window, 'load', geolocalizarme);