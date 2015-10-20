var map;
var lat  = document.getElementsByName('latitud')[0].value;
var lng  = document.getElementsByName('longitud')[0].value;
var zoom = 15;

function show_map(localizacion) {
	
	//obtener localizacion
	if(lat == 0.0 && lng == 0.0){
		lat = localizacion.coords.latitude;
		lng = localizacion.coords.longitude;
	}
	console.debug('lat: ' + lat);
	console.debug('lng: ' + lng);
	var myLatlng = new google.maps.LatLng(lat,lng);
	
	//inicializar Mapa
	map = new google.maps.Map(document.getElementById('map'), {
		zoom: zoom,
	   	center: myLatlng,
	   	mapTypeId: google.maps.MapTypeId.SATELLITE,
		disableDefaultUI: true	   
	});
	
	//marcador
	var marker = new google.maps.Marker({
		position: myLatlng,
		map: map,
		draggable: true,
	 	title: 'Estas Aqui!'
	});
		
	marker.addListener('dragend', function() {
		document.getElementsByName('longitud')[0].value = marker.getPosition().lng();
		document.getElementsByName('latitud')[0].value = marker.getPosition().lat();
	});							
}			
       
function geolocalizarme(){				
	if ( navigator.geolocation ){
		console.debug('Geolocalizando...');
		navigator.geolocation.getCurrentPosition(show_map);				
	}else{
		console.error('Geolocation NO soportado');
	}
}	

google.maps.event.addDomListener(window, 'load', geolocalizarme);